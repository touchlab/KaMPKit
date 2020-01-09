# Common Issues

## Intellij/Android Studio

Problem: IDE can't find Android sdk. The error will look something like this: 

```Error: SDK location not found. Define location with sdk.dir in the local.properties file or with an ANDROID_HOME environment variable.```

Solution: Add a `local.properties` file to the root folder, and point `sdk.dir` at your Android SDK. Likely the
 following: 

```
sdk.dir=/Users/[you]/Library/Android/sdk
```

## Xcode Build

Problem: Build failed with `No such module 'shared'`

Solution: You may have opened `KaMPStarteriOS.xcodeproj` instead of `KaMPStarteriOS.xcworkspace`.

## More to Come!

[Let us know](../CONTACT_US.md) what issues you run into