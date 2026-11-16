# Renaming KaMP Kit for Your Project

Turn this starter into *your* app: your package name, your app name, your bundle IDs — without
breaking the Gradle, Xcode, or Android builds.

This file is written to be handed directly to a coding agent (Claude Code, Cursor, Copilot, …).
Everything the agent needs is here, so it should not need to explore the repo first.

## Use it with an AI agent

Fill in the four values and paste this:

```
Follow docs/RENAME_PROJECT.md to rename this project. Do not scan the repo first;
that file lists every file and string that needs to change.

  package         = com.example.myapp
  app name        = My App
  project name    = MyApp
  iOS target name = MyAppiOS

Run the steps in order, then run the verification in step 7 and report the results.
```

Prefer a fresh session with no other context loaded. The whole job is ~6 shell commands and one
build; it should not cost more than a couple of tool calls.

**No agent?** Every step below is a copy-pasteable shell command. Run them yourself in order.

**Just want it done?** `scripts/rename.sh` is these same steps with argument checking and a
dry-run mode:

```bash
./scripts/rename.sh --package com.example.myapp --project MyApp --app-name "My App" --dry-run
```

The dry run replays the whole rename in a throwaway clone and prints the resulting diff, so you can
see exactly what it will touch before committing to it. Drop `--dry-run` to apply. Run
`./scripts/rename.sh --help` for the rest of the flags.

The rest of this file is the manual version, and explains the reasoning the script can't.

## Step 0 — Inputs

| Variable | Old value | What it is |
| --- | --- | --- |
| `NEW_PKG` | `co.touchlab.kampkit` | Kotlin package + Android `applicationId` + iOS bundle ID root |
| `NEW_APP_NAME` | `KaMP Kit` | Android launcher label (`app_name` string) |
| `NEW_PROJECT` | `KaMPKit` | Gradle `rootProject.name`, CI workflow names |
| `NEW_IOS` | `KaMPKitiOS` | Xcode project, target, scheme, and directory names |

Derived automatically: `NEW_PKG_PATH` (slash form), `NEW_DB` (SQLDelight class), `NEW_SETTINGS_KEY`.

Start clean — commit or stash anything in progress, and work on a branch:

```bash
git checkout -b rename-project
git status --porcelain   # must be empty
```

Set the variables (edit the first four lines):

```bash
NEW_PKG="com.example.myapp"
NEW_APP_NAME="My App"
NEW_PROJECT="MyApp"
NEW_IOS="MyAppiOS"

NEW_PKG_PATH="${NEW_PKG//.//}"
NEW_DB="${NEW_PROJECT}Db"
NEW_SETTINGS_KEY="$(echo "$NEW_PROJECT" | tr '[:lower:]' '[:upper:]')_SETTINGS"
```

## Step 1 — Replace strings

**Order matters.** `KaMPKit` is a prefix of `KaMPKitiOS` and `KaMPKitDb`, so the longer tokens are
replaced first. Perl applies the substitutions top to bottom on each line, so keep them in this
order. Run it as one block.

```bash
git ls-files \
  | grep -Ev '\.(png|jpg|jpeg|gif|ico|webp|jar|keystore|xcuserstate)$' \
  | tr '\n' '\0' | xargs -0 perl -pi -e "
      # iOS test bundle IDs — before the KaMPKitiOS rename
      s/\btouchlab\.KaMPKitiOSTests\b/${NEW_PKG}.ios.tests/g;
      s/\btouchlab\.KaMPKitiOSUITests\b/${NEW_PKG}.ios.uitests/g;

      # Kotlin package, dot form and slash form. Anchored on the full
      # 'co.touchlab.kampkit' so the co.touchlab:kermit and co.touchlab.skie
      # dependency coordinates are left alone.
      s/\bco\.touchlab\.kampkit\b/${NEW_PKG}/g;
      s{\bco/touchlab/kampkit\b}{${NEW_PKG_PATH}}g;

      # Xcode names. No trailing \b here on purpose: it has to match the
      # KaMPKitiOSTests / KaMPKitiOSUITests target names too, and there is no
      # word boundary between 'KaMPKitiOS' and 'Tests'.
      s/\bKaMPKitiOS/${NEW_IOS}/g;

      # SQLDelight class and on-disk database file name
      s/\bKaMPKitDb\b/${NEW_DB}/g;
      s/\bKampkitDb\b/${NEW_DB}/g;

      # Settings / UserDefaults key, shared by MainApp.kt and Koin.swift
      s/\bKAMPSTARTER_SETTINGS\b/${NEW_SETTINGS_KEY}/g;

      # Project name — last, now that the longer tokens are gone. Also no
      # trailing \b, so it picks up the KaMPKitTheme composable.
      s/\bKaMPKit/${NEW_PROJECT}/g;
    "

# Android launcher label
perl -pi -e "s/\bKaMP Kit\b/${NEW_APP_NAME}/g" app/src/main/res/values/strings.xml
```

> The file list is piped through `xargs -0` rather than expanded from a variable, because zsh (the
> macOS default shell) does not word-split unquoted variables the way bash does.

<details>
<summary>What each replacement lands on</summary>

- `co.touchlab.kampkit` → `applicationId` and `shared` `namespace`, ~40 Kotlin `package`/`import`
  lines, SQLDelight `packageName`, the app `namespace` (`…​.android`), the iOS bundle ID (`…​.ios`),
  and the two `android:name` attributes in `AndroidManifest.xml`.
- `KaMPKitiOS` → `project.pbxproj` (paths, target names, `TEST_HOST`, product names), the shared
  scheme, `@testable import`, and the `scheme:` in `.github/workflows/KaMPKit-iOS.yml`.
- `KaMPKitDb` / `KampkitDb` → the generated SQLDelight class and the SQLite file name in
  `KoinAndroid.kt` / `KoinIOS.kt`. Renaming the file name gives a fresh DB on next install, which is
  what you want for a new project.
- `KaMPKit` → `rootProject.name` in `settings.gradle.kts`, the `KaMPKitTheme` composable in
  `Theme.kt`, and the CI workflow names and badges.
</details>

## Step 2 — Move the package directories

Moving the files is the half that find-and-replace cannot do. The `-empty -delete` at the end is
deliberate: it prunes only directories that are actually empty, so a new package starting with `co.`
(e.g. `co.example.demo`) will not blow away the `co` directory it now shares. That case is the one
that tripped up earlier community scripts, and it is verified to work here.

```bash
for ROOT in \
  shared/src/commonMain/kotlin \
  shared/src/commonTest/kotlin \
  shared/src/androidMain/kotlin \
  shared/src/androidHostTest/kotlin \
  shared/src/iosMain/kotlin \
  shared/src/iosTest/kotlin \
  shared/src/commonMain/sqldelight \
  app/src/main/kotlin
do
  mkdir -p "$ROOT/$NEW_PKG_PATH"
  git mv "$ROOT"/co/touchlab/kampkit/* "$ROOT/$NEW_PKG_PATH"/
  find "$ROOT/co" -type d -empty -delete
done
```

## Step 3 — Rename the Xcode directories

```bash
git mv ios/KaMPKitiOS.xcodeproj       "ios/$NEW_IOS.xcodeproj"
git mv ios/KaMPKitiOS                 "ios/$NEW_IOS"
git mv ios/KaMPKitiOSTests            "ios/${NEW_IOS}Tests"
git mv ios/KaMPKitiOSUITests          "ios/${NEW_IOS}UITests"

git mv "ios/${NEW_IOS}Tests/KaMPKitiOSTests.swift"     "ios/${NEW_IOS}Tests/${NEW_IOS}Tests.swift"
git mv "ios/${NEW_IOS}UITests/KaMPKitiOSUITests.swift" "ios/${NEW_IOS}UITests/${NEW_IOS}UITests.swift"

SCHEMES="ios/$NEW_IOS.xcodeproj/xcshareddata/xcschemes"
git mv "$SCHEMES/KaMPKitiOS.xcscheme" "$SCHEMES/$NEW_IOS.xcscheme"
```

`xcodeproj=./ios` in `gradle.properties` points at the *directory*, not the project file, so it needs
no edit as long as `ios/` holds exactly one `.xcodeproj`.

## Step 4 — Rename the CI workflows (optional)

```bash
git mv .github/workflows/KaMPKit-Android.yml ".github/workflows/$NEW_PROJECT-Android.yml"
git mv .github/workflows/KaMPKit-iOS.yml     ".github/workflows/$NEW_PROJECT-iOS.yml"
```

The badge URLs at the top of `README.md` still point at `touchlab/KaMPKit`. Update them to your own
repo, or delete them.

## Step 5 — Re-sort imports

Easy to miss: your new package sorts differently than `co.touchlab.*` did against the other imports,
so ktlint's `import-ordering` rule will now fail across most files. Let it fix itself:

```bash
./gradlew ktlintFormat --no-build-cache
```

`--no-build-cache` matters here. The format tasks are cacheable, but their only declared output is a
report — the source files are edited in place. On a build-cache hit Gradle restores the report and
skips the task, so your imports never get reordered and step 7 fails with `import-ordering`
violations you just "fixed". If that happens, `./gradlew ktlintFormat --rerun-tasks` forces it.

## Step 6 — Clear stale caches

IDE and build caches hold the old module and package names and will produce confusing errors.

```bash
rm -rf .idea/caches .idea/modules.xml .idea/gradle.xml .gradle build app/build shared/build
rm -rf ~/Library/Developer/Xcode/DerivedData/KaMPKitiOS-*
```

Then reopen the project in Android Studio / IntelliJ and let it re-sync.

## Step 7 — Verify

```bash
./gradlew ktlintCheck :app:assembleDebug :shared:compileKotlinIosSimulatorArm64
```

```bash
xcodebuild -project "ios/$NEW_IOS.xcodeproj" -scheme "$NEW_IOS" \
  -sdk iphonesimulator -destination 'generic/platform=iOS Simulator' build
```

Finally, check for leftovers:

```bash
git ls-files -z | xargs -0 grep -nI 'kampkit\|KaMPKit\|KaMP Kit\|kampstarter' 2>/dev/null
```

Remaining hits should only be:

- `co.touchlab:kermit` / `co.touchlab.skie` in `gradle/libs.versions.toml` — real Maven coordinates,
  leave them.
- One prose mention of "KaMP Kit" in a comment in `shared/…/Koin.kt`.
- Links and prose in `README.md`, `docs/`, `CONTACT_US.md`, `LICENSE.txt` — yours to rewrite or
  delete.

Nothing else should match. A hit in `settings.gradle.kts`, `build.gradle.kts`, `project.pbxproj`, or
any `.kt`/`.swift` package line means a step did not take.

## What not to rename

| Thing | Why |
| --- | --- |
| The `shared` Gradle module | Its name is the default Apple framework `baseName`, which is what `import shared` in the Swift files resolves to. Renaming means updating `settings.gradle.kts`, the directory, every Swift `import`, and the Xcode framework search paths. If you want a different framework name, set `baseName = "MyShared"` inside the `binaries.framework { }` block in `shared/build.gradle.kts` and update the Swift imports — leave the module alone. |
| `co.touchlab:kermit`, `co.touchlab.skie` | Touchlab's published libraries. The `co.touchlab` there is the *vendor's* group ID, not yours. Step 1 anchors on the full `co.touchlab.kampkit` precisely to avoid these. |
| `touchlab-*` aliases in `gradle/libs.versions.toml` | Version catalog keys for those same libraries. |
| `BreedRepository`, `DogApi`, the `Breed` table | The sample feature. Delete it once your own code lands rather than renaming it. |

## Cleaning out the starter content

Once the build is green, this is what is safe to delete when you want your repo to stop looking like
a sample:

```bash
git rm -r docs kampkit.png tl2.png CONTACT_US.md .github/pull_request_template.md
git rm README.md   # then write your own
```

Keep `LICENSE.txt` — see its terms for what attribution you owe.

## Notes

- Verified on macOS (zsh). The commands are POSIX plus `git` and `perl`, so Linux should behave the
  same; on Windows use WSL or Git Bash, both of which ship `perl`.
- `perl -pi -e` is used instead of `sed -i` because the `-i` flag is spelled differently on BSD
  (macOS) and GNU (Linux) `sed`.
- Everything runs through `git mv` and tracked files only, so `git diff` shows the full change and
  `git checkout .` backs it all out.
- Verified end to end on this repo: the sequence above produces a green
  `ktlintCheck :app:assembleDebug :shared:compileKotlinIosSimulatorArm64`, an APK with the new
  `applicationId`, and an Xcode project whose three targets and scheme all carry the new name.

