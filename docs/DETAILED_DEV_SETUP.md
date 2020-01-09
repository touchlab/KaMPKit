# KMP Development Environment Setup

Not assuming anything if you're an iOS developer. You may not have the Android/JVM setup necessary to run everything. 



## Install JDK

You'll need a JDK (Java Development Kit), preferably version 8. We recommend 
[Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/macos-install.html). Download the pkg 
installer and go through the setup instructions.

Some alternative options, if desired:

- [SDKMan](https://sdkman.io/) - JDK version manager and installer.
- [AdoptOpenJDK](https://adoptopenjdk.net/) - Alternate JDK distribution



## Install the IDE(s)

You'll also need either Android Studio, IntelliJ, or both. Android Studio is an Android development focused skin of IntelliJ, which is more platform agnostic. If you don't have either, we recommend installing both through the [Jetbrains Toolbox](https://www.jetbrains.com/toolbox-app/download/download-thanks.html).

If you just want one or the other, you can use the following links:

- [Android Studio docs installation guide](https://developer.android.com/studio/install) (includes download link)
- [IntelliJ download link](https://www.jetbrains.com/idea/download/#section=mac) (select the Community version)
- [IntelliJ setup guide](https://www.jetbrains.com/help/idea/run-for-the-first-time.html)



## Open IDE

Once you have your IDE installed, open it. If it's Android Studio, select **Open an Existing Android Studio Project** and if it's IntelliJ select **Import Project**. In the finder that opens up, select the root directory of your clone of this repository.

Opening this project in Android Studio should automatically configure the project's `local.properties` file. If for some reason it doesn't, or if you open the project in IntelliJ, you'll need to configure this file manually. To do so, open `local.properties`, and set the value of `sdk.dir` to `/Users/[YOUR_USERNAME]/Library/Android/sdk`.

On the left, above the project structure (or the Project Navigator in Xcode-ese), there's a dropdown menu above your project's root directory. Make sure that it's set to "Project" (_for context: the IDE may think that you're working on a traditional Android project and set this menu to "Android" or make some similar mistake, and organize the files in the navigator accordingly_).



## Install an Android Emulator

The Android corollary to a Simulator is an Emulator. To install an Emulator, you need to open the Android Virtual Device (AVD) Manager, which is the corollary to the Device and Simulators window in Xcode. 

If you're in Android Studio, go to Tools -> AVD Manager. If you're in IntelliJ, there's one extra step: go to Tools -> Android -> AVD Manager. After this first step, the process is the same in Android Studio and IntelliJ. Select **+ Create New Virtual Device...**. 

You'll have a large choice of devices to choose from, but we recommend you install the newest, largest Pixel device to emulate (Pixel 3 XL at the time this is being written). Go to the next step and select the newest API level, and then go to the last step and select **Finish**.



## Next Steps

You're KMP development environment is ready now. Your next step should be to go to the [APP_BUILD.md doc](APP_BUILD.md), which focuses on building this project, as well as running it on both Android and iOS.



## Further Reading

If you want a more in-depth guide to getting a KMP project up and running for those coming from an iOS background, check out [this walkthrough](https://www.infoq.com/articles/kotlin-multiplatform-ios-developers/) that we wrote (and are maintaining).


