# Debugging Kotlin in Xcode

By this point you should be able to build and run the KaMP Kit in iOS using XCode. Great! Maybe you've changed a variable and want to see if it actually updated successfully, but how do you do that? well we've actually created a way to debug *kotlin* code in **XCode**. You may have noticed in the code some mentions of xcodeSync, well that's actually the first step in being able to debug in Xcode. 

### Kotlin XCode Sync
[Kotlin Xcode Sync](https://github.com/touchlab/KotlinXcodeSync) is a plugin that's added to the project that easily syncs the common kotlin code with XCode. This means you can browse the shared code from within XCode. It adds a kotlin folder in the xcode project, which contains the shared library folder with all the shared code.

So now you have the kotlin code visible in XCode, but how do you actually debug it during runtime? That's where our XCode-kotlin plugin comes in.

### Kotlin Native XCode Plugin
The [Kotlin Native Xcode Plugin](https://github.com/touchlab/xcode-kotlin) adds basic highlighting, allows you to set breakpoints and includes llvm support to view data in the debug window. You can find the steps to install this plugin on its readMe, but it's as simple as running a couple of bash scripts.
