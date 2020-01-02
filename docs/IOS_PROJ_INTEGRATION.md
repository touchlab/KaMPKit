# Integrating with Existing iOS Projects

There are two primary ways to add a KMP library to your existing iOS project: with or without Cocoapods. Because of
 its simplicity and ease-of-use, we recommend that you use Cocoapods.

 If you don't want to use Cocoapods to add a KMP library to your iOS project, then you can follow the steps in [this
  guide](https://play.kotlinlang.org/hands-on/Targeting%20iOS%20and%20Android%20with%20Kotlin%20Multiplatform/01_Introduction) from Jetbrains about how to add the library to your iOS project manually.

If you don't have Cocoapods installed, then follow the instructions in their [official installation guide](https://guides.cocoapods.org/using/getting-started.html).


## Create Podfile

If your iOS project doesn't have a `Podfile` yet, you'll need one. If your project is already using Cocoapods, then
 skip ahead to the next section.

In the command line, run `touch Podfile` in your iOS project's root directory. Then paste the following into your new
 `Podfile`:

 ```
use_frameworks!

platform :ios, '9.0'

install! 'cocoapods', :deterministic_uuids => false

target 'YourIosAppTargetName' do
    // Pods go here
end
```

Now, replace `YourIosAppTargetName` with, you guessed it, your iOS app's target name. In the KaMPStarter iOS sample
 app, that would be `KaMPStarterIos`.


## Add KMP Pod

Add the following line in your `target` block (replace `// Pods go here` in our example above):

```
    pod 'shared', :path => '~/[PATH_TO_KaMPStarter/shared/]'
```

Next, replace  `~/[PATH_TO_KaMPStarter/shared/]` with the path to your `KaMPStarter/shared/` directory. For example:
```
    pod 'shared', :path => '~/Desktop/KaMPStarter/shared/'
```
This path can be either absolute or relative, but we realize that your KaMPStarter project and your existing iOS
 project might be in very different places, so we're using an absolute path as an example for simplicity's sake.


## Install and Run

Save the changes to your `Podfile`. Go back to the command line, and in your iOS project's root directory, run `pod
 install`.

 This command will create a `Pods/` folder and a `.xcworkspace` file in your iOS project's root directory. Open the
  `.xcworkspace` file. Remember that if your project was already using Cocoapods, and you had your `.xcworkspace
   ` file open in Xcode, you need to close and reopen it.

From now on, you will work out of the `.xcworkspace` file instead of the `.xcodeproj` file (which is part of
 your `.xcworkspace`). To use code from your `shared` KMP library, at the top of the `.swift` file where you
  want to use it, add:

 ```
import shared
```
