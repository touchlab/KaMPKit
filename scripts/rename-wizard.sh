#!/usr/bin/env bash
#
# A wizard walks a human through a manual procedure, step by step.
#
# Everything above the "STAGES" marker is the wizard library: do not hand-edit
# it. Author the per-step stages below the marker.

set -euo pipefail

# ──────────────────────────────────────────────────────────────────────────
# Wizard library: delightful, consistent UX, identical across every wizard.
# ──────────────────────────────────────────────────────────────────────────

if [[ -t 1 ]] && command -v tput >/dev/null 2>&1 && [[ "$(tput colors 2>/dev/null || echo 0)" -ge 8 ]]; then
  BOLD=$(tput bold); DIM=$(tput dim); RESET=$(tput sgr0)
  BLUE=$(tput setaf 4); GREEN=$(tput setaf 2); YELLOW=$(tput setaf 3)
else
  BOLD=""; DIM=""; RESET=""; BLUE=""; GREEN=""; YELLOW=""
fi

# Author sets this at the top of the stages section.
TOTAL_STAGES=0

_STAGE_INDEX=0
ENV_FILE="${ENV_FILE:-.env}"
WRITTEN_ENV=()    # KEYs written to ENV_FILE this run
WRITTEN_SECRET=() # secret NAMEs set this run
SKIPPED=()        # things we couldn't do (e.g. gh missing)

# _clear wipes the terminal so only the current step is on screen. No-op when
# output isn't a terminal, so piped logs stay readable.
_clear() {
  [[ -t 1 ]] || return 0
  if command -v tput >/dev/null 2>&1 && tput clear 2>/dev/null; then return 0; fi
  printf '\033[2J\033[3J\033[H'
}

# banner "Title" shows the opening frame: what this wizard does.
banner() {
  _clear
  printf '\n%s%s  %s%s\n' "$BOLD" "$BLUE" "$1" "$RESET"
  printf '%s  %s stages%s\n\n' "$DIM" "$TOTAL_STAGES" "$RESET"
  printf '%s  You drive the browser; this wizard tells you exactly what to do and\n' "$DIM"
  printf '  captures the values you copy back. Stop any time with Ctrl-C and re-run\n'
  printf '  later, since it remembers values already saved.%s\n' "$RESET"
  pause "Ready to start?"
}

# stage "Name" clears the screen, then announces a stage and shows progress.
# Clearing keeps only the current step on screen.
stage() {
  _clear
  _STAGE_INDEX=$((_STAGE_INDEX + 1))
  printf '\n%s%s▸ Stage %s/%s · %s%s\n' \
    "$BOLD" "$BLUE" "$_STAGE_INDEX" "$TOTAL_STAGES" "$1" "$RESET"
}

# say "..." prints a plain instruction line.
say()  { printf '  %s\n' "$1"; }
# step "..." is a numbered-feeling action the human takes in the browser.
step() { printf '  %s•%s %s\n' "$BLUE" "$RESET" "$1"; }
note() { printf '  %s%s%s\n' "$DIM" "$1" "$RESET"; }
warn() { printf '  %s⚠ %s%s\n' "$YELLOW" "$1" "$RESET"; }

# open_url URL opens it in the human's browser, cross-platform incl. WSL.
open_url() {
  local url="$1"
  printf '  %s↗ opening%s %s\n' "$GREEN" "$RESET" "$url"
  { if   command -v wslview     >/dev/null 2>&1; then wslview "$url"
    elif command -v explorer.exe >/dev/null 2>&1; then explorer.exe "$url"
    elif command -v xdg-open    >/dev/null 2>&1; then xdg-open "$url"
    elif command -v open        >/dev/null 2>&1; then open "$url"
    else false; fi
  } >/dev/null 2>&1 || warn "couldn't open a browser, so visit it manually: $url"
}

# pause "msg" waits for the human to confirm they've done the manual part.
pause() {
  printf '  %s%s%s ' "$DIM" "${1:-Press Enter to continue}" "$RESET"
  read -r _ || true
}

# confirm "question" is a y/N gate; returns success on yes.
confirm() {
  local reply=""
  printf '  %s? %s [y/N] ' "$YELLOW" "$1"
  read -r reply || true
  [[ "$reply" =~ ^[Yy] ]]
}

# _existing KEY: current value of KEY in ENV_FILE, if any, with write_env's single
# quotes or a plain dotenv double-quoted value undone.
_existing() {
  [[ -f "$ENV_FILE" ]] || return 1
  local line value sq="'\\''"; line=$(grep -E "^${1}=" "$ENV_FILE" | tail -n1) || return 1
  value="${line#*=}"
  if [[ "$value" == \'*\' ]]; then value="${value:1:${#value}-2}"; value="${value//"$sq"/\'}"
  elif [[ "$value" == \"*\" ]]; then
    value="${value:1:${#value}-2}"
    [[ "$value" == *[\\\$\"]* ]] && return 1   # escapes, $ or ": can't decode safely; ask again
  fi
  printf '%s' "$value"
}

# ask KEY "Prompt" reads a value into $KEY. Offers the existing .env value as
# a default on re-runs (Enter keeps it). Visible input (non-secret), edited
# with Readline. Fails at EOF with no input.
ask() {
  local key="$1" prompt="$2" current input s=$'\001' e=$'\002' p rc=0
  current=$(_existing "$key" || true)
  # Readline needs the colour codes wrapped in \001..\002 to place the cursor.
  p="  $s$BOLD$e$prompt$s$RESET$e "
  [[ -n "$current" ]] && p+="$s$DIM$e[Enter keeps current]$s$RESET$e "
  read -e -r -p "$p" input || rc=1
  if (( rc )) && [[ -z "$input" ]]; then return 1; fi
  [[ -z "$input" && -n "$current" ]] && input="$current"
  printf -v "$key" '%s' "$input"
}

# ask_secret KEY "Prompt" is like ask, but input is hidden.
ask_secret() {
  local key="$1" prompt="$2" current input rc=0
  current=$(_existing "$key" || true)
  if [[ -n "$current" ]]; then
    printf '  %s%s%s %s[Enter keeps current]%s ' "$BOLD" "$prompt" "$RESET" "$DIM" "$RESET"
  else
    printf '  %s%s%s ' "$BOLD" "$prompt" "$RESET"
  fi
  read -rs input || rc=1
  printf '\n'
  if (( rc )) && [[ -z "$input" ]]; then return 1; fi
  [[ -z "$input" && -n "$current" ]] && input="$current"
  printf -v "$key" '%s' "$input"
}

# write_env KEY VALUE upserts KEY='VALUE' into ENV_FILE (replaces any existing
# line) and sets $KEY. Idempotent. Creates ENV_FILE at mode 0600; writes through
# a symlink and keeps an existing file's mode.
write_env() {
  local key="$1" value="$2" tmp sq="'\\''"
  [[ -e "$ENV_FILE" ]] || (umask 077 && : > "$ENV_FILE")
  tmp=$(mktemp)
  grep -vE "^${key}=" "$ENV_FILE" > "$tmp" || true
  printf "%s='%s'\n" "$key" "${value//\'/$sq}" >> "$tmp"
  cat "$tmp" > "$ENV_FILE"
  rm -f "$tmp"
  printf -v "$key" '%s' "$value"
  WRITTEN_ENV+=("$key")
  printf '  %s✓ wrote%s %s → %s\n' "$GREEN" "$RESET" "$key" "$ENV_FILE"
}

# set_secret NAME VALUE sets a GitHub Actions repo secret via gh. Falls back
# to a warning (and records it) if gh is unavailable or unauthenticated.
set_secret() {
  local name="$1" value="$2"
  if command -v gh >/dev/null 2>&1 && gh auth status >/dev/null 2>&1; then
    if printf '%s' "$value" | gh secret set "$name" >/dev/null 2>&1; then
      WRITTEN_SECRET+=("$name")
      printf '  %s✓ set%s GitHub secret %s\n' "$GREEN" "$RESET" "$name"
      return
    fi
  fi
  SKIPPED+=("GitHub secret $name (set it manually: gh secret set $name)")
  warn "skipped GitHub secret $name: gh not ready; set it later"
}

# set_var NAME VALUE sets a GitHub Actions repo variable (non-secret).
set_var() {
  local name="$1" value="$2"
  if command -v gh >/dev/null 2>&1 && gh auth status >/dev/null 2>&1; then
    if gh variable set "$name" --body "$value" >/dev/null 2>&1; then
      printf '  %s✓ set%s GitHub variable %s\n' "$GREEN" "$RESET" "$name"
      return
    fi
  fi
  SKIPPED+=("GitHub variable $name")
  warn "skipped GitHub variable $name, gh not ready; set it later"
}

# finish clears, then shows a closing summary of everything configured.
finish() {
  _clear
  printf '\n%s%s  ✓ Setup complete%s\n' "$BOLD" "$GREEN" "$RESET"
  (( ${#WRITTEN_ENV[@]} ))    && note "wrote ${#WRITTEN_ENV[@]} value(s) to $ENV_FILE: ${WRITTEN_ENV[*]}"
  (( ${#WRITTEN_SECRET[@]} )) && note "set ${#WRITTEN_SECRET[@]} GitHub secret(s): ${WRITTEN_SECRET[*]}"
  if (( ${#SKIPPED[@]} )); then
    printf '\n'; warn "still to do by hand:"
    for s in "${SKIPPED[@]}"; do note "  - $s"; done
  fi
  printf '\n'
}

# ──────────────────────────────────────────────────────────────────────────
# STAGES: author this section. One stage() per step the human takes.
# Replace the example below. Set TOTAL_STAGES to match the stages you write.
# Keep every stage inside run_wizard and the last line as is: bash then parses
# the whole file before the first prompt, so an edit mid-run can't break it.
# ──────────────────────────────────────────────────────────────────────────

run_wizard() {

TOTAL_STAGES=10

# The old names are split across string literals on purpose: rename.sh rewrites
# every tracked file, this one included, and must not turn them into the new
# names. Keeping them split lets the wizard be re-run after the rename.
OLD_PROJECT="KaMP""Kit"
OLD_IOS="${OLD_PROJECT}iOS"
OLD_PKG="co.touchlab.""kampkit"

ROOT=$(git rev-parse --show-toplevel 2>/dev/null) \
  || { printf 'Run this from inside the %s repo.\n' "$OLD_PROJECT" >&2; exit 1; }
cd "$ROOT"
[[ -f settings.gradle.kts ]] \
  || { printf 'settings.gradle.kts not found; is this the %s repo?\n' "$OLD_PROJECT" >&2; exit 1; }

# Answers live inside .git so they never show up in a diff or a commit, and
# survive re-runs (Enter keeps the previous answer).
ENV_FILE="$(git rev-parse --absolute-git-dir)/rename-wizard.env"

already_renamed() { ! grep -q "rootProject.name = \"$OLD_PROJECT\"" settings.gradle.kts; }
gh_ready() { command -v gh >/dev/null 2>&1 && gh auth status >/dev/null 2>&1; }
on_mac() { [[ "$(uname -s)" == Darwin ]]; }

# ask_default KEY "Prompt" DEFAULT: ask, showing DEFAULT when there is no saved
# answer; an empty reply takes the default.
ask_default() {
  local key="$1" prompt="$2" default="$3"
  if _existing "$key" >/dev/null 2>&1; then ask "$key" "$prompt"
  else ask "$key" "$prompt [$default]"; fi
  [[ -n "${!key}" ]] || printf -v "$key" '%s' "$default"
}

RESERVED=" abstract assert boolean break byte case catch char class const continue default do double else enum extends final finally float for goto if implements import instanceof int interface long native new package private protected public return short static strictfp super switch synchronized this throw throws transient try void volatile while fun val var object typealias when is in as "

check_pkg() {
  local seg
  [[ "$1" =~ ^[a-z][a-z0-9_]*(\.[a-z][a-z0-9_]*)+$ ]] \
    || { echo "Use lowercase, dot-separated segments, at least two (com.example.myapp)."; return 1; }
  for seg in ${1//./ }; do
    [[ "$RESERVED" == *" $seg "* ]] && { echo "'$seg' is a reserved word in Kotlin or Java."; return 1; }
  done
  [[ "$1" != "$OLD_PKG" ]] || { echo "That's the current package. Pick your own."; return 1; }
  [[ "$1" != "$OLD_PKG".* ]] || { echo "Can't nest under $OLD_PKG."; return 1; }
}

check_ident() {
  [[ "$1" =~ ^[A-Za-z][A-Za-z0-9_]*$ ]] \
    || { echo "Letters, digits and underscores only, starting with a letter. No spaces."; return 1; }
  [[ "$1" != "$OLD_PROJECT" && "$1" != "$OLD_IOS" ]] || { echo "That's the current name. Pick your own."; return 1; }
}

# ask_valid KEY "Prompt" DEFAULT CHECKFN: re-asks until CHECKFN accepts.
ask_valid() {
  local key="$1" prompt="$2" default="$3" check="$4" err
  while :; do
    ask_default "$key" "$prompt" "$default"
    err=$("$check" "${!key}") && return 0
    warn "$err"
  done
}

banner "Make KaMP Kit yours"

# ── 1 ─────────────────────────────────────────────────────────────────────
stage "Preflight"
if already_renamed; then
  say "This project has already been renamed: settings.gradle.kts no longer says $OLD_PROJECT."
  note "Picking up where you left off. The preview and apply stages will be skipped."
else
  while [[ -n "$(git status --porcelain)" ]]; do
    warn "Your working tree has uncommitted changes:"
    git status --short | head -15 | sed 's/^/      /'
    step "Commit or stash them in another terminal. The rename has to start from a clean tree,"
    step "so that 'git diff' afterwards shows only the rename and 'git reset --hard' undoes it."
    pause "Press Enter to check again"
  done
  say "${GREEN}✓${RESET} Working tree is clean."
fi
BRANCH=$(git branch --show-current)
say "You're on branch ${BOLD}${BRANCH:-(detached HEAD)}${RESET}."
if [[ "$BRANCH" == "main" || "$BRANCH" == "master" ]] && ! already_renamed; then
  if confirm "Do the rename on a new 'rename-project' branch?"; then
    git switch -q -c rename-project 2>/dev/null || git switch -q rename-project
    say "${GREEN}✓${RESET} Switched to rename-project."
  fi
fi
[[ -x scripts/rename.sh ]] || already_renamed \
  || { warn "scripts/rename.sh is missing; the wizard needs it to do the rename."; exit 1; }
pause

# ── 2 ─────────────────────────────────────────────────────────────────────
stage "Choose your names"
if already_renamed && _existing PROJECT >/dev/null 2>&1; then
  PKG=$(_existing PKG); PROJECT=$(_existing PROJECT)
  APP_NAME=$(_existing APP_NAME); IOS_NAME=$(_existing IOS_NAME)
  say "Already renamed with these names (they can't change now):"
else
  [[ -z "$(_existing PKG 2>/dev/null || true)" ]] || note "Press Enter to keep the answers from last time."
  if already_renamed; then
    warn "The project was renamed outside the wizard. Enter the names that were used,"
    warn "so the remaining stages know what to look for."
  fi
  say "These names replace the KaMP Kit ones everywhere: Gradle, Android, Xcode and CI."
  printf '\n'
  ask_valid PKG "Package & Android applicationId:" "com.example.myapp" check_pkg
  last=${PKG##*.}
  ask_valid PROJECT "Project name (Gradle, DB class, theme):" \
    "$(tr '[:lower:]' '[:upper:]' <<<"${last:0:1}")${last:1}" check_ident
  while :; do
    ask_default APP_NAME "App name on the launcher:" "$PROJECT"
    [[ -n "${APP_NAME// /}" ]] && break
    warn "The app name can't be blank."
  done
  ask_valid IOS_NAME "Xcode project / target / scheme:" "${PROJECT}iOS" check_ident
fi

SETTINGS_KEY="$(tr '[:lower:]' '[:upper:]' <<<"$PROJECT")_SETTINGS"
printf '\n'
printf '  %-20s %s%s%s\n' \
  "package" "$BOLD" "$PKG" "$RESET" \
  "source dirs" "$BOLD" "${PKG//.//}" "$RESET" \
  "project" "$BOLD" "$PROJECT" "$RESET" \
  "launcher label" "$BOLD" "$APP_NAME" "$RESET" \
  "Xcode target" "$BOLD" "$IOS_NAME" "$RESET" \
  "iOS bundle ID" "$BOLD" "$PKG.ios" "$RESET" \
  "iOS test bundles" "$BOLD" "$PKG.ios.tests, $PKG.ios.uitests" "$RESET" \
  "database class" "$BOLD" "${PROJECT}Db" "$RESET" \
  "settings key" "$BOLD" "$SETTINGS_KEY" "$RESET"
printf '\n'
if ! already_renamed; then
  if ! confirm "Use these names?"; then
    note "Nothing changed. Re-run the wizard to pick different names."
    exit 0
  fi
fi
write_env PKG "$PKG"
write_env PROJECT "$PROJECT"
write_env APP_NAME "$APP_NAME"
write_env IOS_NAME "$IOS_NAME"
pause

RENAME_ARGS=(-p "$PKG" -n "$PROJECT" -a "$APP_NAME" -i "$IOS_NAME")

# ── 3 ─────────────────────────────────────────────────────────────────────
stage "Preview the rename"
if already_renamed; then
  note "Already applied. Nothing to preview."
else
  say "Replaying the rename in a throwaway clone. Nothing in this repo changes yet."
  preview=$(mktemp)
  if ! scripts/rename.sh "${RENAME_ARGS[@]}" --dry-run >"$preview" 2>&1; then
    warn "The dry run failed:"
    tail -20 "$preview" | sed 's/^/      /'
    exit 1
  fi
  files=$(grep -E '^[ MADRC?]{2} ' "$preview" || true)
  printf '\n'
  say "${BOLD}$(grep -c . <<<"$files")${RESET} files will change:"
  note "  $(grep -c '^R' <<<"$files" || true) moved (package directories, Xcode folders, CI workflows)"
  note "  $(grep -c '^M' <<<"$files" || true) edited in place"
  printf '\n'
  if confirm "Page through the full file list?"; then
    ${PAGER:-less} <<<"$files" || true
  fi
  rm -f "$preview"
fi
pause

# ── 4 ─────────────────────────────────────────────────────────────────────
stage "Apply the rename"
if already_renamed; then
  note "Already applied. Skipping."
  pause
else
  say "This rewrites strings, moves the package directories, renames the Xcode project,"
  say "then re-sorts imports with ktlintFormat. Everything goes through git, so you can"
  say "undo it with ${BOLD}git reset --hard${RESET}."
  printf '\n'
  if ! confirm "Apply the rename to this repo now?"; then
    note "Stopped before changing anything. Re-run the wizard whenever you're ready."
    exit 0
  fi
  say "Renaming… the ktlintFormat pass takes a minute or two."
  log=$(mktemp)
  if ! scripts/rename.sh "${RENAME_ARGS[@]}" --yes >"$log" 2>&1; then
    warn "rename.sh failed:"
    tail -20 "$log" | sed 's/^/      /'
    warn "Inspect with 'git status'. 'git reset --hard' backs out a partial rename."
    exit 1
  fi
  if grep -q "ktlintFormat failed" "$log"; then
    warn "The rename is applied, but ktlintFormat failed. Imports may be out of order."
    SKIPPED+=("Re-sort imports: ./gradlew ktlintFormat --no-build-cache")
  else
    say "${GREEN}✓${RESET} Renamed and imports re-sorted."
  fi
  rm -f "$log"
  note "$(git status --porcelain | grep -c .) files changed. See 'git status' for the list."
  pause
fi

# ── 5 ─────────────────────────────────────────────────────────────────────
stage "Apple signing team & ownership"
PBX="ios/$IOS_NAME.xcodeproj/project.pbxproj"
[[ -f "$PBX" ]] || { warn "$PBX not found. Was the rename applied with these names?"; exit 1; }
say "The rename leaves two Touchlab leftovers in the iOS project: Touchlab's Apple"
say "Developer Team ID, which stops you signing for a device, and Touchlab as the"
say "organization and copyright holder."
printf '\n'
note "Simulator builds don't need a team, so a blank answer is fine for now. On a free"
note "Apple ID, leave it blank and pick your Personal Team in Xcode later (stage 7)."
printf '\n'
if [[ -z "$(_existing APPLE_TEAM_ID 2>/dev/null || true)" ]] \
   && confirm "Open developer.apple.com to look up your Team ID?"; then
  open_url "https://developer.apple.com/account"
  step "Sign in, scroll to ${BOLD}Membership details${RESET}, and copy ${BOLD}Team ID${RESET} (10 characters)."
fi
while :; do
  ask APPLE_TEAM_ID "Team ID (blank to leave unset):"
  APPLE_TEAM_ID=$(tr '[:lower:]' '[:upper:]' <<<"${APPLE_TEAM_ID// /}")
  [[ -z "$APPLE_TEAM_ID" || "$APPLE_TEAM_ID" =~ ^[A-Z0-9]{10}$ ]] && break
  warn "A Team ID is exactly 10 letters and digits, like AB12CD34EF."
done
while :; do
  ask_default ORG_NAME "Organization / copyright holder:" "$(git config user.name || echo "$PROJECT")"
  [[ -n "${ORG_NAME// /}" && "$ORG_NAME" != *[\"\\]* ]] && break
  warn "Must be non-empty and contain no quotes or backslashes."
done

NEW_TEAM="${APPLE_TEAM_ID:-\"\"}" NEW_ORG="$ORG_NAME" perl -pi -e '
  s/^(\s*DEVELOPMENT_TEAM = ).*;/${1}$ENV{NEW_TEAM};/;
  s/^(\s*ORGANIZATIONNAME = ).*;/${1}"$ENV{NEW_ORG}";/;
' "$PBX"
git ls-files -z 'ios/*.swift' | NEW_ORG="$ORG_NAME" YEAR="$(date +%Y)" \
  xargs -0 perl -pi -e 's/Copyright © \d{4} Touchlab\./Copyright © $ENV{YEAR} $ENV{NEW_ORG}./'
write_env APPLE_TEAM_ID "$APPLE_TEAM_ID"
write_env ORG_NAME "$ORG_NAME"
printf '\n'
say "${GREEN}✓${RESET} Team set to ${BOLD}${APPLE_TEAM_ID:-(none)}${RESET} in all $(grep -c 'DEVELOPMENT_TEAM' "$PBX") build configurations"
say "${GREEN}✓${RESET} Organization and Swift copyright headers now say ${BOLD}$ORG_NAME${RESET}"
pause

# ── 6 ─────────────────────────────────────────────────────────────────────
stage "Clear caches & verify the build"
say "IDE and build caches still hold the old module and package names, and they"
say "cause confusing errors."
if confirm "Delete .idea caches, Gradle build dirs and $OLD_IOS DerivedData?"; then
  rm -rf .idea/caches .idea/modules.xml .idea/gradle.xml .gradle build app/build shared/build
  rm -rf "$HOME/Library/Developer/Xcode/DerivedData/${OLD_IOS}-"*
  say "${GREEN}✓${RESET} Caches cleared."
else
  SKIPPED+=("Clear stale caches: rm -rf .idea/caches .gradle build app/build shared/build")
fi
printf '\n'
GRADLE_CHECK="./gradlew ktlintCheck :app:assembleDebug :shared:compileKotlinIosSimulatorArm64"
if confirm "Run the Gradle check now? (lint, Android build, iOS framework; a few minutes)"; then
  if $GRADLE_CHECK --quiet; then
    say "${GREEN}✓${RESET} Gradle check passed."
  else
    warn "Gradle check failed. See the output above."
    SKIPPED+=("Fix and re-run: $GRADLE_CHECK")
  fi
else
  SKIPPED+=("Verify Gradle: $GRADLE_CHECK")
fi
if on_mac && command -v xcodebuild >/dev/null 2>&1; then
  printf '\n'
  if confirm "Build the iOS app for the simulator too? (several minutes)"; then
    if xcodebuild -quiet -project "ios/$IOS_NAME.xcodeproj" -scheme "$IOS_NAME" \
         -sdk iphonesimulator -destination 'generic/platform=iOS Simulator' build; then
      say "${GREEN}✓${RESET} iOS build passed."
    else
      warn "iOS build failed. See the output above."
      SKIPPED+=("Fix the iOS build: open ios/$IOS_NAME.xcodeproj")
    fi
  else
    SKIPPED+=("Verify iOS: xcodebuild -project ios/$IOS_NAME.xcodeproj -scheme $IOS_NAME -sdk iphonesimulator build")
  fi
fi
pause

# ── 7 ─────────────────────────────────────────────────────────────────────
stage "Reopen the IDEs"
say "Reopen both projects so the IDEs pick up the new names."
printf '\n'
if on_mac && [[ -d "/Applications/Android Studio.app" ]] && confirm "Open the project in Android Studio?"; then
  open -a "Android Studio" "$ROOT"
else
  step "Open $ROOT in Android Studio or IntelliJ."
fi
step "Wait for Gradle sync to finish, then run the ${BOLD}app${RESET} configuration."
step "The launcher should say ${BOLD}$APP_NAME${RESET}."
printf '\n'
if on_mac && confirm "Open ios/$IOS_NAME.xcodeproj in Xcode?"; then
  open "ios/$IOS_NAME.xcodeproj"
fi
step "In Xcode, select the ${BOLD}$IOS_NAME${RESET} target, then ${BOLD}Signing & Capabilities${RESET}."
if [[ -z "$APPLE_TEAM_ID" ]]; then
  step "Pick your team in the Team dropdown. A Personal Team works for your own device."
  note "That writes DEVELOPMENT_TEAM into project.pbxproj. Commit it if you're the only developer."
else
  step "Check that Team shows your team and the Bundle Identifier is ${BOLD}$PKG.ios${RESET}."
fi
step "Run on a simulator."
pause "Both apps running? Press Enter"

# ── 8 ─────────────────────────────────────────────────────────────────────
stage "Your GitHub repo"
ORIGIN=$(git remote get-url origin 2>/dev/null || true)
say "origin is currently ${BOLD}${ORIGIN:-(not set)}${RESET}."
say "Point it at your own repo, and update the README CI badges and PR template to match."
printf '\n'
DEFAULT_OWNER=$(gh_ready && gh api user -q .login 2>/dev/null || echo "your-name")
while :; do
  ask_default GITHUB_REPO "GitHub repo, owner/name (- to skip):" "$DEFAULT_OWNER/$PROJECT"
  [[ "$GITHUB_REPO" == "-" || "$GITHUB_REPO" =~ ^[A-Za-z0-9-]+/[A-Za-z0-9._-]+$ ]] && break
  warn "Use the owner/name form, e.g. octocat/$PROJECT."
done
if [[ "$GITHUB_REPO" == "-" ]]; then
  SKIPPED+=("Point origin at your own repo and update the README badges")
else
  write_env GITHUB_REPO "$GITHUB_REPO"
  if [[ "$ORIGIN" == https://* ]]; then REPO_URL="https://github.com/$GITHUB_REPO.git"
  else REPO_URL="git@github.com:$GITHUB_REPO.git"; fi

  # 1. Make sure the repo exists on GitHub.
  if gh_ready && gh repo view "$GITHUB_REPO" >/dev/null 2>&1; then
    say "${GREEN}✓${RESET} github.com/$GITHUB_REPO exists."
  elif gh_ready && confirm "Create github.com/$GITHUB_REPO as a private repo now?"; then
    gh repo create "$GITHUB_REPO" --private >/dev/null
    say "${GREEN}✓${RESET} Created github.com/$GITHUB_REPO (private)."
  else
    open_url "https://github.com/new"
    step "Create ${BOLD}$GITHUB_REPO${RESET}. Leave it empty: no README, license or .gitignore."
    note "If it already exists, just continue."
    pause "Created? Press Enter"
  fi

  # 2. Remotes: keep Touchlab's repo as upstream so you can pull KaMP Kit updates.
  if [[ -n "$ORIGIN" && "$ORIGIN" != *"$GITHUB_REPO"* ]]; then
    if [[ "$ORIGIN" == *[Tt]ouchlab/* ]] && ! git remote get-url upstream >/dev/null 2>&1; then
      git remote rename origin upstream
      say "${GREEN}✓${RESET} Kept Touchlab's repo as remote ${BOLD}upstream${RESET}."
    elif confirm "Replace origin ($ORIGIN) with $REPO_URL?"; then
      git remote remove origin
    fi
  fi
  if ! git remote get-url origin >/dev/null 2>&1; then
    git remote add origin "$REPO_URL"
    say "${GREEN}✓${RESET} origin → $REPO_URL"
  fi

  # 3. Badges and links. The rename already turned touchlab/<old> into touchlab/<new>.
  for f in README.md .github/pull_request_template.md; do
    [[ -f "$f" ]] || continue
    FROM="touchlab/$PROJECT" TO="$GITHUB_REPO" perl -pi -e 's/\b\Q$ENV{FROM}\E\b/$ENV{TO}/g' "$f"
    say "${GREEN}✓${RESET} $f now links to $GITHUB_REPO"
  done
fi
pause

# ── 9 ─────────────────────────────────────────────────────────────────────
stage "Starter cleanup (optional)"
say "Strip out the sample-project material so the repo looks like yours."
printf '\n'
if confirm "Remove the starter docs and images (docs/, kampkit.png, tl2.png, CONTACT_US.md, PR template)?"; then
  git rm -rq --ignore-unmatch docs kampkit.png tl2.png CONTACT_US.md .github/pull_request_template.md
  say "${GREEN}✓${RESET} Removed."
fi
if confirm "Replace README.md with a short one for $APP_NAME?"; then
  {
    if [[ "$GITHUB_REPO" != "-" && -n "$GITHUB_REPO" ]]; then
      for p in Android iOS; do
        printf '[![%s](https://img.shields.io/github/actions/workflow/status/%s/%s-%s.yml?branch=main&logo=%s)](https://github.com/%s/actions/workflows/%s-%s.yml)\n' \
          "$p" "$GITHUB_REPO" "$PROJECT" "$p" "$p" "$GITHUB_REPO" "$PROJECT" "$p"
      done
      printf '\n'
    fi
    printf '# %s\n\n' "$APP_NAME"
    printf 'A Kotlin Multiplatform app for Android and iOS, started from [KaMP Kit](https://github.com/touchlab/%s).\n' "$OLD_PROJECT"
  } > README.md
  say "${GREEN}✓${RESET} README.md rewritten."
fi
if confirm "Remove the rename tooling (scripts/rename.sh and this wizard)? You only need it once."; then
  git rm -q --ignore-unmatch scripts/rename.sh scripts/rename-wizard.sh
  say "${GREEN}✓${RESET} Removed. This run carries on: it's already loaded in memory."
fi
printf '\n'
note "Kept: LICENSE.txt (its terms say what attribution you owe), and the Kermit and SKIE"
note "dependencies, which really are Touchlab libraries."
pause

# ── 10 ────────────────────────────────────────────────────────────────────
stage "Commit"
git add -A
CHANGED=$(git diff --cached --name-only | grep -c . || true)
if [[ "$CHANGED" -eq 0 ]]; then
  note "Nothing to commit."
else
  say "${BOLD}$CHANGED${RESET} files staged on ${BOLD}$(git branch --show-current)${RESET}."
  if confirm "Commit them as \"Rename project to $PROJECT\"?"; then
    git commit -q -m "Rename project to $PROJECT"
    say "${GREEN}✓${RESET} Committed $(git rev-parse --short HEAD)."
  else
    note "Left staged. Review with 'git diff --cached' and commit when ready."
  fi
fi
pause

finish
if git remote get-url origin >/dev/null 2>&1 && [[ "$(git remote get-url origin)" != *[Tt]ouchlab/* ]]; then
  say "Next: ${BOLD}git push -u origin $(git branch --show-current)${RESET}"
fi
note "Your answers are saved in $ENV_FILE for re-runs."
printf '\n'
}

run_wizard "$@"; exit
