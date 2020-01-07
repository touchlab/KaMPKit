# Sample App Build

## Prerequisits
Before you build the app you will require these items:

* JVM 8
* Android SDK and Studio/Intellij
* Xcode 11+ for the iOS build

For more details, check out the iOS_Dev_Setup document [here](IOS_DEV_SETUP.md)

### 1) Clone the app
Run the following on the command line 
```
git clone https://github.com/touchlab/KaMPStarter.git
```

### 2) Build Android
1. Open the project in Android Studio/Intellij and wait for indexing to finish.
1. Make sure you see the run config for the android app 
![](runconfig.png)
1. Run the Android app in either the simulator or a phone. If the app builds correctly, you should see this:

![](Screenshots/kampScreenshotAndroid.png)

### 3) Build iOS

1. Run gradle build, which builds the shared library.
1. Open Xcode **workspace** project, `KaMPStarteriOS.xcworkspace`.
1. Run the iOS app in either the simulator or a phone. If the app builds correctly, you should see this:

![](Screenshots/kampScreenshotiOS.png)

## Did that work?

Congratulations! You have a functional sample app to start working from. Head back to the [README.md](README.md) for
 next
 steps.

### Common Issues

One of the first things to check if you have an issue is the `local.properties` file. Make sure that the sdk.dir variable is pointing to your android sdk location.

### Contact

If you're having issues, you can view the [contact Document here](https://github.com/touchlab/KaMPStarter/blob/master/CONTACT_US.md) for contact information
