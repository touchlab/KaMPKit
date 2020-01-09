# Architecture Overview

This doc goes over the overall architecture of the app, the libraries usage and the locations of files and directories.

* [Structure of the Project](#Structure-of-the-Project)
* [Overall Architecture](#Overall-Architecture)
* [Coroutines and Ktor](#Coroutines-and-Ktor)
* [Libraries and Dependencies](#Libraries-and-Dependencies)
  * [SqlDelight](#SqlDelight) - Database
  * [Ktor](#Ktor) - Networking
  * [Multiplatform Settings](#Multiplatform-Settings) - Settings
  * [Koin](#Koin) - Dependency Injection
  * [Stately](#Stately) - State Utility
* [Testing](#Testing)

## Structure of the Project

The KaMPStarter kit is broken up into three different directories: 
* shared
* app
* ios

The app directory holds the android version of the app, and all the android code. As a default, Android Studio will name the project "app" when creating it. Even though this can be confusing for kmp this is the default.

Similarly the ios directory holds the iOS version of the app, which contains an XCode project and a Workspace. We want to use the workspace as it contains the shared library.

Finally the shared directory holds the shared code. The shared directory is actually an android library that is referenced from the app project. This library contains directories for the different platforms as well as directories for testing.

  * androidMain
  * iosMain
  * commonMain
  * androidTest
  * iosTest
  * commonTest

Each of these directories has the same folder structure: the language type, then the package name.
  i.e. *"kotlin/co/touchlab/kampstarter/"*

## Overall Architecture

The KaMPStarter kit, whether running in Android or iOS, starts with the platforms View (`MainActivity` / `ViewController`). There it creates the `BreedModel`, passes in a callback, and calls the BreedModel. The BreedModel is in the common MultiPlatform code, so we are already in the common code. The BreedModel references the Multiplatform-Settings, and two helper classes: `DogApiImpl` (which implements the KtorApi) and `DatabaseHelper`. The DatabseHelper and DogApiImpl both use the Multiplatform libraries to retrieve data and send it back to the BreedModel. 

> note that the BreedModel references the interface KtorApi. This is so we can test the Model using a Mock Api

In this implementation the BreedModel listens to the database as a flow, so that when any changes occur to the database it will then call the callback it was passed. When breeds are requested the model retrieves the information from the network, then saves the data to the database. This triggers the database flow to send the new data to the Platform to update the display.

In Short:
**Platform -> BreedModel -> DogApiImpl -> BreedModel -> DatabaseHelper -> BreedModel -> Platform**

You may be asking where the Multiplatform-settings comes in. When the BreedModel is told to get breeds from the network, it first checks to see if it's done a network request within the past hour. If it has then it decides not to update the breeds. 

## Kotlinx Coroutines

Because of Kotlin/Native's unique threading and state model, coroutines support has been limited to a single thread until very recently. Back in November, a [pull request](https://github.com/Kotlin/kotlinx.coroutines/pull/1648) arrived in the kotlinx.coroutines repo with support for multithreaded coroutines. That is still a PR and is still experimental, but we've included that version in the sample app because it'll be live in the near future. As an alternative, many applications are using [CoroutineWorker](https://github.com/Autodesk/coroutineworker) in production.

So, to be clear, ***we're using a version of [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) that is still in development***. In our experience it works well, and will likely be merged into the main repo soon, so it makes sense to learn that version.

### *... but Ktor*

Ktor on iOS was designed to work with the single-threaded version of coroutines. We're using that version with the experimental coroutines. We've found a bug that we're reporting to Jetbrains. The current workaround is to have one scope for Ktor, and another for everything else.

```kotlin
internal val mainScope = MainScope(Dispatchers.Main)
internal val ktorScope = MainScope(Dispatchers.Main)
```

See [BaseModel.kt](https://github.com/touchlab/KaMPStarter/blob/master/shared/src/commonMain/kotlin/co/touchlab/kampstarter/models/BaseModel.kt#L11)

## Libraries and Dependencies

If you're familiar with Android projects then you know that the apps dependencies are stored in the build.gradle. Since shared is an android library, it also contains its own build.gradle where it stores its own dependencies. If you open *"shared/build.gradle.kts"* you will see **sourceSets** corresponding to the directories in the shared project. 

Each part of the shared library can declare its own dependencies in these source sets. For example the multiplatform-settings library is only declared in **commonMain** and **commonTest**, since the library uses gradle metadata to pull in the correct platform specific dependencies. Other libraries that may require platform specific variables, such as SqlDelight, require platform specific dependencies. With this example you can see that **commonMain** has `sqlDelight.runtime` and **androidMain** has `sqlDelight.driverAndroid`.

Below is some information about some of the libraries used in the project.

* [SqlDelight](#SqlDelight)
* [Ktor](#Ktor)
* [Multiplatform Settings](#Multiplatform-Settings)
* [Koin](#Koin)
* [Stately](#Stately)

### SqlDelight
Documentation: https://github.com/cashapp/sqldelight

Usage in the project: *commonMain/kotlin/co/touchlab/kampstarter/DatabaseHelper.kt*

SQL Location in the project: *commonMain/sqldelight/co/touchlab/kampstarter/Table.sq*
                      

SqlDelight is a multiplatform SQL library that generates type-safe APIs from SQL Statements. Since it is a multiplatform library, it naturally uses code stored in commonMain. SQL statements are stored in the sqldelight directory, in .sq files.
ex: *"commonMain/sqldelight/co/touchlab/kampstarter/Table.sq"*

Even though the SQL queries and main bulk of the library are in the common code, there are some platform specific drivers required from Android and iOS in order to work correctly on each platform. These are the `AndroidSqliteDriver` and the `NativeSqliteDriver`(for iOS). These are passed in from platform specific code, in this case injected into the **BreedModel**. The APIs are stored in the build folder, and referenced from the `DatabaseHelper` (also in commonMain). 

   ##### Flow
   Normally sql queries are called, and a result is given, but what if you want to get sql query as a flow? We've added Coroutine Extensions to the shared code, which adds the `asFlow` function that converts queries into flows. Behind the scenes this creates a Query Listener that when a query result has changed, emits the new value to the flow.

### Ktor
Documentation: https://ktor.io/

Usage in the project: *commonMain/kotlin/co/touchlab/kampstarter/ktor/DogApiImpl.kt*

Ktor is a multiplatform networking library for building asynchronous clients. Again since it is a multiplatform library, it  uses code stored in commonMain. Even though all of Ktors code is in **commonMain**, there are some platform specific dependencies needed in the build.gradle. 

### Multiplatform Settings
Documentation: https://github.com/russhwolf/multiplatform-settings

Usage in the project: *commonMain/kotlin/co/touchlab/kampstarter/models/BreedModel.kt*

Multiplatform settings really speaks for itself, it persists data by storing it in settings. It is being used in the BreedModel, and acts similarly to a `HashMap` or `Dictionary`. Much like SqlDelight the actual internals of the settings are platform specific, so the settings are passed in from the platform and all of the actual saving and loading is in the common code.

### Koin
Documentation: https://insert-koin.io/

Usage in the project: *commonMain/kotlin/co/touchlab/kampstarter/Koin.kt*

Koin is a lightweight dependency injection framework. It is being used in the *koin.kt* file to inject modules into the BreedModel. You can tell which variables are being injected in the BreedModel because they are being set using `by inject()`. In our implementation we are separating our injections into two different modules: the `coreModule` and the `platformModule`. As you can guess the platformModule contains injections requiring platform specific implementations (SqlDelight and Multiplatform Settings). The coreModule contains the Ktor implementation and the Database Helper, which actually takes from the platformModule.

**Note:** We are using a custom build of Koin for KaMP Kit. This is a [forked version](https://github.com/kpgalligan/koin/tree/kpg/khan) designed to work well with Kotlin/Native's state model. Essentially, it is source-compatible with regular Koin. The difference is that all config is done in the main thread, and unless specified, all injecting is also done in the main thread. This is enforced in Native as well as Android to maintain consistency. You can freeze the objects you pul in there, but Koin will not freeze them unless told to do so. We'll be presenting this architecture to the Koin author after some experimentation.

### Stately
Documentation: https://github.com/touchlab/Stately

Usage in the project: *commonMain/kotlin/co/touchlab/kampstarter/sqldelight/CoroutinesExtensions.kt*

Stately is a state utility library used to help with state management in Kotlin Multiplatform (Made by us!). Basically Kotlin/Native has a fairly different model of concurrency, and has different rules to reduce concurrency related issues. Objects in K/N can become `frozen`, which basically means they are immutable but can be shared across all threads. 

**Note:** Threading in K/N can be hard to grasp at first and this document isn't the place to go into it
in detail. If you want to find out more about thread check out this post 
[here](https://medium.com/@kpgalligan/kotlin-native-stranger-threads-ep-2-208523d63c8f)

In KaMPKit we are using `ensureNeverFrozen()` in the BreedModel and DogApiImpl because we don't want them frozen. What this does is it throws an exception if the object ever becomes frozen, so that we can know exactly when it freezes and don't run into issues later on. The other place we are using Stately is `freeze` in the CoroutinesExtensions. Here we are freezing the channel so that it can offer new Query Results on any thread. Since we are not modifying the channel, simply offering with it, this will not cause frozen issues.

## Testing

With KMP you can share tests between platforms, however since we have platform specific drivers and dependencies our tests need to be run by the individual platforms. In short we can share tests but we have to run them separately as android and iOS. The shared tests can be found in the commonTest directory, while the implementations can be found in the androidTest and iosTest directories, specifically with *PlatformTest.kt*. The PlatformTests contain classes for testing that are subclassing the abstract shared tests, so that they can be run in their platform. The function `runTest` is also implemented in PlatformTest, which makes sure the tests are running synchronously.

You may be thinking: Weren't the libraries injected? How does the dependency injection work? Well that is actually handled in the `TestingServiceRegistry` object in commonTest. In order to pass the platform specific libraries(SqlDelight requires a platform driver) into the BreedModel for testing we need to inject them. Before the tests in BreedModelTest we are calling `appStart` and passing in our libraries to be injected and starting Koin. Then after the tests we are stopping Koin by calling `appEnd`. Note that the actual `testDbConnection()` implementations are in the *SqlDelightTest.kt* files.

#### Android
On the android side we are using AndroidRunner to run the tests because we want to use android specifics in our tests. If you're not using android specific methods then you don't need to use AndroidRunner. The android tests are run can be easily run in Android Studio by right clicking on the shared folder, and selecting `Run 'All Tests'`.

#### iOS
The iOS side can seem a bit tricky at first, but is just as simple to test. The tests are not run from XCode, instead we've created an `iOSTest` task in the shared build.gradle.kts(located in the shared folder). You can simply go to the terminal and run `./gradlew iosTest`.
