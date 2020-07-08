# Debugging Kotlin in Xcode

By this point you should be able to build and run the KaMP Kit in iOS using Xcode. Great! Maybe you've changed a variable and want to see if it actually updated successfully, but how do you do that? Well we at Touchlab have actually created a way to **debug kotlin code in Xcode**.

### Kotlin Native Xcode Plugin
The [Kotlin Native Xcode Plugin](https://github.com/touchlab/xcode-kotlin) adds basic highlighting, allows you to set breakpoints and includes llvm support to view data in the debug window. You can find the steps to install this plugin on its readMe, but it's as simple as running a couple of bash scripts.

### Kotlin Source in Xcode
To take advantage of the plugin you will want to add references to your kotlin code in XCode. This will allow you to add breakpoints and edit kotlin without switching to Android Studio. You probably wont want to do your primary kotlin coding like this, but it's helpful when debugging.

To add the Kotlin source:
1. Right click in the project explorer

![](Screenshots/AddFiles.png)

2. In the finder opened, select the kotlin source you want included (commonMain and iosMain). Be sure to select "Create folder references for any added folders"

![](Screenshots/FolderRef.png)
