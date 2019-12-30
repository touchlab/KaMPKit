# iOS App Developer Setup

Not assuming anything if you're an iOS developer. You may not have the Android/JVM setup necessary to run everything. 

## Install JDK

You'll need a JDK (Java Development Kit), preferably version 8. We recommend 
[Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/macos-install.html). Download the pkg 
installer and go through the setup instructions.

Some alternative options, if desired:

- [SDKMan](https://sdkman.io/) - JDK version manager and installer.
- [AdoptOpenJDK](https://adoptopenjdk.net/) - Alternate JDK distribution


## Install Android and IDE's

You'll also need either Android Studio, IntelliJ, or both. Android Studio is an Android development focused skin of IntelliJ, which is more platform agnostic. If you don't have either, we recommend installing both through the Jetbrains Toolbox: https://www.jetbrains.com/toolbox-app/download/download-thanks.html.

If you just want one or the other, you can use the following links:

- Android Studio docs installation guide (includes download link): https://developer.android.com/studio/install
- IntelliJ download link (select the Community version): https://www.jetbrains.com/idea/download/#section=mac 
- IntelliJ setup guide: https://www.jetbrains.com/help/idea/run-for-the-first-time.html

## Open

Opening this project in Android Studio should automatically configure its `local.properties` file. If for some reason it doesn't, or if you open the project in IntelliJ, you'll need to configure this file manually.

Once you have Android Studio installed, open it and select **Open an Existing Android Studio Project**. In the finder that opens up, select the root directory of your clone of this repository.

On the left, above the project structure (or the Project Navigator in Xcode-ese), there's a dropdown menu above your project's root directory. Make sure that it's set to "Project" (the IDE may think that you're working on an Android project and set this menu to "Android" or make some similar mistake).

## Build & Run

To build the project, go to the Terminal tab at the bottom of Android Studio and run the comand **./gradlew build**.

## Further Reading

If you want a more in-depth guide to getting a KMP project up and running with an iOS background, check out this walkthrough that we wrote (and are maintaining): https://www.infoq.com/articles/kotlin-multiplatform-ios-developers/.

