#!/usr/bin/env bash
#
# Rename KaMP Kit for your own project: package, app name, Gradle project
# name, Xcode target names and bundle IDs.
#
# This is the executable form of docs/RENAME_PROJECT.md. Read that file if you
# want to know why each step is the way it is, or to run the steps by hand.

set -euo pipefail

OLD_PKG="co.touchlab.kampkit"
OLD_PKG_PATH="co/touchlab/kampkit"
OLD_PROJECT="KaMPKit"
OLD_IOS="KaMPKitiOS"

PKG=""
APP_NAME=""
PROJECT=""
IOS_NAME=""
DRY_RUN=false
ASSUME_YES=false
SKIP_FORMAT=false
FORCE=false

die()  { printf 'error: %s\n' "$*" >&2; exit 1; }
note() { printf '%s\n' "$*" >&2; }

usage() {
cat <<'USAGE'
Usage: scripts/rename.sh --package <pkg> --project <Name> [options]

Required:
  -p, --package  <pkg>   New Kotlin package and Android applicationId.
                         Lowercase, at least two segments (com.example.myapp).
  -n, --project  <Name>  New Gradle project name. Also used for the SQLDelight
                         database class and the Compose theme, so it must be a
                         valid Kotlin identifier (MyApp).

Optional:
  -a, --app-name <text>  Android launcher label.      Default: --project
  -i, --ios-name <Name>  Xcode project/target/scheme. Default: <project>iOS
      --dry-run          Replay the whole rename in a throwaway clone and show
                         the resulting diff. Changes nothing here.
      --skip-format      Skip the ktlintFormat pass (you will then need to run
                         it yourself, or ktlintCheck will fail).
  -y, --yes              Do not ask for confirmation.
      --force            Proceed even with a dirty working tree.
  -h, --help             Show this help.

Example:
  scripts/rename.sh -p com.example.myapp -n MyApp -a "My App" --dry-run
USAGE
}

while [ $# -gt 0 ]; do
  case "$1" in
    -p|--package)  [ $# -ge 2 ] || die "$1 needs a value"; PKG="$2";      shift 2 ;;
    -n|--project)  [ $# -ge 2 ] || die "$1 needs a value"; PROJECT="$2";  shift 2 ;;
    -a|--app-name) [ $# -ge 2 ] || die "$1 needs a value"; APP_NAME="$2"; shift 2 ;;
    -i|--ios-name) [ $# -ge 2 ] || die "$1 needs a value"; IOS_NAME="$2"; shift 2 ;;
    --dry-run)     DRY_RUN=true;     shift ;;
    --skip-format) SKIP_FORMAT=true; shift ;;
    -y|--yes)      ASSUME_YES=true;  shift ;;
    --force)       FORCE=true;       shift ;;
    -h|--help)     usage; exit 0 ;;
    *)             usage >&2; die "unknown argument: $1" ;;
  esac
done

# ---------------------------------------------------------------- validation

[ -n "$PKG" ]     || { usage >&2; die "--package is required"; }
[ -n "$PROJECT" ] || { usage >&2; die "--project is required"; }

[ -n "$APP_NAME" ] || APP_NAME="$PROJECT"
[ -n "$IOS_NAME" ] || IOS_NAME="${PROJECT}iOS"

printf '%s' "$PKG" | grep -qE '^[a-z][a-z0-9_]*(\.[a-z][a-z0-9_]*)+$' \
  || die "--package must be lowercase with at least two dot-separated segments: got '$PKG'"

for seg in $(printf '%s' "$PKG" | tr '.' ' '); do
  case " abstract assert boolean break byte case catch char class const continue default do double else enum extends final finally float for goto if implements import instanceof int interface long native new package private protected public return short static strictfp super switch synchronized this throw throws transient try void volatile while fun val var object typealias when is in as " in
    *" $seg "*) die "--package segment '$seg' is a reserved word" ;;
  esac
done

printf '%s' "$PROJECT" | grep -qE '^[A-Za-z][A-Za-z0-9_]*$' \
  || die "--project must be a valid identifier (letters, digits, underscore; no spaces): got '$PROJECT'"

printf '%s' "$IOS_NAME" | grep -qE '^[A-Za-z][A-Za-z0-9_]*$' \
  || die "--ios-name must be a valid identifier: got '$IOS_NAME'"

[ "$PKG" != "$OLD_PKG" ]         || die "--package is already $OLD_PKG; nothing to do"
[ "$PROJECT" != "$OLD_PROJECT" ] || die "--project is already $OLD_PROJECT; nothing to do"

case "$PKG" in
  "$OLD_PKG".*) die "--package cannot be nested under $OLD_PKG" ;;
esac

PKG_PATH=$(printf '%s' "$PKG" | tr '.' '/')
DB_NAME="${PROJECT}Db"
SETTINGS_KEY="$(printf '%s' "$PROJECT" | tr '[:lower:]' '[:upper:]')_SETTINGS"

# ------------------------------------------------------------- preconditions

git rev-parse --show-toplevel >/dev/null 2>&1 || die "not inside a git repository"
cd "$(git rev-parse --show-toplevel)"

[ -f settings.gradle.kts ] || die "settings.gradle.kts not found; run this from the KaMPKit repo"

grep -q "rootProject.name = \"$OLD_PROJECT\"" settings.gradle.kts \
  || die "settings.gradle.kts does not say rootProject.name = \"$OLD_PROJECT\" — this project looks like it has already been renamed"

if [ -n "$(git status --porcelain)" ] && ! $FORCE && ! $DRY_RUN; then
  die "working tree is dirty. Commit or stash first, or pass --force"
fi

# ------------------------------------------------------------------- summary

cat >&2 <<SUMMARY

  package          $OLD_PKG  ->  $PKG
  source dirs      $OLD_PKG_PATH  ->  $PKG_PATH
  project name     $OLD_PROJECT  ->  $PROJECT
  app label        KaMP Kit  ->  $APP_NAME
  Xcode target     $OLD_IOS  ->  $IOS_NAME
  database class   ${OLD_PROJECT}Db  ->  $DB_NAME
  settings key     KAMPSTARTER_SETTINGS  ->  $SETTINGS_KEY

SUMMARY

if ! $DRY_RUN && ! $ASSUME_YES; then
  [ -t 0 ] || die "not a terminal; re-run with --yes (or --dry-run first)"
  printf 'Proceed? [y/N] ' >&2
  read -r reply
  case "$reply" in [yY]|[yY][eE][sS]) ;; *) note "aborted"; exit 1 ;; esac
fi

# -------------------------------------------------------------------- rename

list_files() {
  git ls-files | grep -Ev '\.(png|jpg|jpeg|gif|ico|webp|jar|keystore|xcuserstate)$'
}

# git mv that tolerates a no-op or an already-moved path.
move() {
  if [ "$1" = "$2" ]; then return 0; fi
  if [ ! -e "$1" ]; then return 0; fi
  git mv "$1" "$2"
}

do_rename() {
  # Step 1 — strings. Values are passed through the environment rather than
  # interpolated into the perl source, so an app name containing $ @ \ or /
  # cannot break out. Order matters: KaMPKit is a prefix of KaMPKitiOS,
  # KaMPKitDb and KaMPKitTheme, and perl applies these top to bottom.
  export NEW_PKG="$PKG" NEW_PKG_PATH="$PKG_PATH" NEW_IOS="$IOS_NAME" \
         NEW_DB="$DB_NAME" NEW_KEY="$SETTINGS_KEY" NEW_PROJECT="$PROJECT"

  list_files | tr '\n' '\0' | xargs -0 perl -pi -e '
      s/\btouchlab\.KaMPKitiOSTests\b/$ENV{NEW_PKG}.ios.tests/g;
      s/\btouchlab\.KaMPKitiOSUITests\b/$ENV{NEW_PKG}.ios.uitests/g;
      s/\bco\.touchlab\.kampkit\b/$ENV{NEW_PKG}/g;
      s{\bco/touchlab/kampkit\b}{$ENV{NEW_PKG_PATH}}g;
      s/\bKaMPKitiOS/$ENV{NEW_IOS}/g;
      s/\bKaMPKitDb\b/$ENV{NEW_DB}/g;
      s/\bKampkitDb\b/$ENV{NEW_DB}/g;
      s/\bKAMPSTARTER_SETTINGS\b/$ENV{NEW_KEY}/g;
      s/\bKaMPKit/$ENV{NEW_PROJECT}/g;
  '

  NEW_APP_NAME="$APP_NAME" perl -pi -e 's/\bKaMP Kit\b/$ENV{NEW_APP_NAME}/g' \
    app/src/main/res/values/strings.xml

  # Step 2 — package directories. The -empty -delete only prunes directories
  # that are genuinely empty, so a new package starting with "co." keeps the
  # "co" directory it now shares with co/touchlab.
  for root in \
    shared/src/commonMain/kotlin \
    shared/src/commonTest/kotlin \
    shared/src/androidMain/kotlin \
    shared/src/androidHostTest/kotlin \
    shared/src/iosMain/kotlin \
    shared/src/iosTest/kotlin \
    shared/src/commonMain/sqldelight \
    app/src/main/kotlin
  do
    [ -d "$root/$OLD_PKG_PATH" ] || continue
    mkdir -p "$root/$PKG_PATH"
    git mv "$root/$OLD_PKG_PATH"/* "$root/$PKG_PATH"/
    if [ -d "$root/co" ]; then find "$root/co" -type d -empty -delete; fi
  done

  # Step 3 — Xcode directories.
  move "ios/$OLD_IOS.xcodeproj" "ios/$IOS_NAME.xcodeproj"
  move "ios/$OLD_IOS"           "ios/$IOS_NAME"
  move "ios/${OLD_IOS}Tests"    "ios/${IOS_NAME}Tests"
  move "ios/${OLD_IOS}UITests"  "ios/${IOS_NAME}UITests"
  move "ios/${IOS_NAME}Tests/${OLD_IOS}Tests.swift"     "ios/${IOS_NAME}Tests/${IOS_NAME}Tests.swift"
  move "ios/${IOS_NAME}UITests/${OLD_IOS}UITests.swift" "ios/${IOS_NAME}UITests/${IOS_NAME}UITests.swift"
  schemes="ios/$IOS_NAME.xcodeproj/xcshareddata/xcschemes"
  move "$schemes/$OLD_IOS.xcscheme" "$schemes/$IOS_NAME.xcscheme"

  # Step 4 — CI workflows.
  move ".github/workflows/$OLD_PROJECT-Android.yml" ".github/workflows/$PROJECT-Android.yml"
  move ".github/workflows/$OLD_PROJECT-iOS.yml"     ".github/workflows/$PROJECT-iOS.yml"
}

if $DRY_RUN; then
  preview=$(mktemp -d)
  trap 'rm -rf "$preview"' EXIT
  note "Previewing in a throwaway clone (nothing here is touched)..."
  git clone --quiet --no-hardlinks . "$preview/kampkit"
  ( cd "$preview/kampkit" && do_rename >/dev/null && git add -A && git status --short )
  note ""
  note "Dry run only. Re-run without --dry-run to apply."
  exit 0
fi

do_rename

# Step 5 — imports. The new package sorts differently from co.touchlab.*, so
# ktlint's import-ordering rule fires across most files. --no-build-cache is
# required: the format tasks declare only a report as output while editing
# sources in place, so a cache hit silently skips the actual formatting.
if $SKIP_FORMAT; then
  note ""
  note "Skipped ktlintFormat. Run this before ktlintCheck:"
  note "  ./gradlew ktlintFormat --no-build-cache"
else
  note ""
  note "Re-sorting imports (./gradlew ktlintFormat --no-build-cache)..."
  gradle_log=$(mktemp)
  if ./gradlew ktlintFormat --no-build-cache --quiet >"$gradle_log" 2>&1; then
    rm -f "$gradle_log"
  else
    note ""
    note "ktlintFormat failed:"
    grep -v -e "^WARNING:" -e "sun.misc.Unsafe" "$gradle_log" | tail -20 >&2
    note ""
    note "The rename itself is applied. Re-run the format step yourself:"
    note "  ./gradlew ktlintFormat --no-build-cache"
    rm -f "$gradle_log"
  fi
fi

cat >&2 <<DONE

Done. Review with 'git diff' and 'git status', then:

  rm -rf .idea/caches .idea/modules.xml .idea/gradle.xml
  rm -rf ~/Library/Developer/Xcode/DerivedData/$OLD_IOS-*

  ./gradlew ktlintCheck :app:assembleDebug :shared:compileKotlinIosSimulatorArm64
  xcodebuild -project ios/$IOS_NAME.xcodeproj -scheme $IOS_NAME -sdk iphonesimulator build

The README badges still point at touchlab/KaMPKit, and prose in README.md and
docs/ still mentions KaMP Kit. See docs/RENAME_PROJECT.md for what to delete.
DONE
