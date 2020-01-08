# Debugging Kotlin in Xcode

By this point you should be able to build and run the KaMP Kit in iOS using Xcode. Great! Maybe you've changed a variable and want to see if it actually updated successfully, but how do you do that? Well we at Touchlab have actually created a way to **debug kotlin code in Xcode**. You may have noticed in the code some mentions of xcodeSync, well that's actually the first step in being able to debug in Xcode. 

### Kotlin XCode Sync
[Kotlin Xcode Sync](https://github.com/touchlab/KotlinXcodeSync) is a plugin that's added to the project that easily syncs the common kotlin code with Xcode. This means you can browse the shared code from within Xcode. It adds a kotlin folder in the Xcode project, which contains the shared library folder with all the shared code.

So now you have the kotlin code visible in Xcode, but how do you actually debug it during runtime? That's where our Xcode-kotlin plugin comes in.

### Kotlin Native Xcode Plugin
The [Kotlin Native Xcode Plugin](https://github.com/touchlab/xcode-kotlin) adds basic highlighting, allows you to set breakpoints and includes llvm support to view data in the debug window. You can find the steps to install this plugin on its readMe, but it's as simple as running a couple of bash scripts.
