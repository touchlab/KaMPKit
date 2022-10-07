# Sample App Build

## Prerequisites
Before you build the app you will require these items:

* JVM 8
* Android SDK and Android Studio or IntelliJ
* Mac with Xcode 11+ for the iOS build

For more details, check out the [DETAILED_DEV_SETUP](DETAILED_DEV_SETUP.md) document.

### 1) Clone the app
Run the following on the command line
```
git clone https://github.com/touchlab/KaMPKit.git
```

### 2) Build Android
1. Open the project in Android Studio/IntelliJ and wait for indexing to finish.
2. Make sure you see the run config for the Android app                    
![](runconfig.png)
3. Run the Android app on either the Emulator or a phone. If the app builds correctly, you should see this:

![](Screenshots/kampScreenshotAndroid.png)

### 3) Build iOS

1. [Optional] Run gradle build. If you are more familiar with Android it may be easier to run the gradle build and confirm that the shared library builds properly before moving into Xcode land, but this isn't necessary. The shared library will also build when run in Xcode.
   1. Open a Terminal window or use the one at the bottom of Android Studio/IntelliJ. 
   2. Navigate to the project's root directory (`KaMPKit/` - not `KaMPKit/ios/` - which is iOS project's root directory). 
   3. Run the command `./gradlew build` which will build the shared library.
2. Open Xcode **workspace** project in the `ios/` folder: `KaMPKitiOS.xcworkspace`.
3. Run the iOS app on either the Simulator or a phone. If the app builds correctly, you should see this:

![](Screenshots/kampScreenshotiOS.png)

## Did that work?

Congratulations! You have a functional sample app to start working from. Head back to the [README.md](../README.md#Sanity-Check) for next steps.

### Common Issues

See [TROUBLESHOOTING](TROUBLESHOOTING.md)

### CI Hosts
Running your common tests against iOS and testing your native iOS code require a macOS machine. In Github Actions you can specify the machine you want to run your jobs on:

```yaml
jobs:
   build:
      runs-on: macos-latest
```
Most CI hosts, including Github Actions charge for macOS hosts at a higher rate than linux, so it's worthwhile to reduce macOS build times. In KaMP Kit we do this by splitting into two workflows, `KaMPKit-Android.yml` and `KaMPKit-iOS.yml`. Each workflow excludes builds that only have changes to the opposite platform or docs only changes.

```yaml
pull_request:
 paths-ignore:
   - "**.md"
   - "*.png"
   - docs
   - app
```

### Contact

If you're having issues, you can view the [contact Document here](https://github.com/touchlab/KaMPKit/blob/master/CONTACT_US.md) for contact information.
