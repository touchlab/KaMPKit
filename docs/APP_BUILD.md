# Sample App Build

## Building The App

### Prerequisits
Before you build the app you will require these items:

* JVM 8
* Android SDK and Studio/Intellij
* Xcode 11+ for the iOS build

For more details, check out the iOS_Dev_Setup document [here](https://github.com/touchlab/KaMPStarter/blob/master/docs/IOS_DEV_SETUP.md)

### Steps to Build
1. Clone the project from github 
```
git clone https://github.com/touchlab/KaMPStarter.git
```
1. Open the project in Android Studio/Intellij and wait for indexing to finish.
1. Run the Android app in either the simulator or a phone. Show successful app screenshot.
![](https://github.com/touchlab/KaMPStarter/blob/ks/docs2/docs/Screenshots/kampScreenshotAndroid.png)
1. Run gradle build, which builds the shared library.
1. Open Xcode **workspace** project.
1. Run the iOS app in either the simulator or a phone. Show successful app screenshot.
[](https://github.com/touchlab/KaMPStarter/blob/master/docs/Screenshots/kampScreenshotiOS.png)

### Common Issues

One of the first things to check if you have an issue is the `local.properties` file. Make sure that the sdk.dir variable is pointing to your android sdk location.

### Contact

If you're having issues, you can view the [contact Document here](https://github.com/touchlab/KaMPStarter/blob/master/CONTACT_US.md) for contact information
