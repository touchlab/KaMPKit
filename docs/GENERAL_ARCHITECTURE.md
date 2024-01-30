# Architecture Overview

 In this guide, we'll provide you with a clear understanding of the app's structure, the usage of libraries, and the locations of important files and directories.

* [Structure of the Project](#Structure-of-the-Project)
* [Overall Architecture](#Overall-Architecture)
* [Kotlinx Coroutines](#kotlinx-Coroutines)
* [Libraries and Dependencies](#Libraries-and-Dependencies)
  * [SKIE](#SKIE) - Swift-friendly API generator
  * [Kermit](#Kermit) - Logging
  * [SqlDelight](#SqlDelight) - Database
  * [Ktor](#Ktor) - Networking
  * [Multiplatform Settings](#Multiplatform-Settings) - Settings
  * [Koin](#Koin) - Dependency Injection
* [Testing](#Testing)

## Structure of the Project

KaMP Kit is organized into three main directories:
* shared
* app
* ios

The app directory contains the Android version of the app, complete with Android-specific code. The name "app" is the default name assigned by Android Studio during project creation

Similarly, the ios directory houses the iOS version of the app. This directory includes an Xcode project and workspace. For better compatibility, it's recommended to use the workspace as it incorporates the shared library.

The **shared** directory is crucial as it contains the shared codebase. The shared directory is actually a library project that is referenced from the app project. Within this library, you'll find separate directories for various platforms and testing:

  * androidMain
  * iosMain
  * commonMain
  * androidUnitTest
  * iosTest
  * commonTest

Each of these directories maintains a consistent structure: the programming language followed by the package name (e.g., *"kotlin/co/touchlab/kampkit/"*).

## Overall Architecture

#### Platform
KaMP Kit app, whether running in Android or iOS, starts with the platforms View (`MainActivity` / `ViewController`). These components serve as the standard UI interfaces for each platform and initiate upon app launch. They handle all aspects of the user interface, including RecyclerView/UITableView, user input, and view lifecycle management.

#### ViewModel

From the platforms views we then have the ViewModel layer that bridges our shared data with the views.

If you want your shared viewmodel to be an `androidx.lifecycle.ViewModel`
on the Android side, you can take either a composition or inheritence approach.

For this project we chose the inheritence approach, because Android can use the
common viewmodel directly. To enable sharing of presentation logic between platforms, we
define `expect abstract class ViewModel` in `commonMain`, with platform specific implementations
provided in `androidMain` and `iosMain`. The android implementation simply extends the Jetpack
ViewModel, while an equivalent is implemented for iOS.

`ViewModel` sharing used to a bit more convoluted but now with Touchlab's [Skie](#Skie) tool, iOS code can reference the common `BreedViewModel` directly.

#### Repository
The `BreedRepository` resides in the common Multiplatform code and handles data access functions. This repository references the `Multiplatform-Settings` library, as well as two auxiliary classes: `DogApiImpl` (implementing `DogApi`) and `DatabaseHelper`. Both `DatabaseHelper` and `DogApiImpl` utilize Multiplatform libraries to fetch and manage data, forwarding it to the `BreedRepository`.

> Note that the BreedRepository references the interface DogApi. This is so we can test the Model using a Mock Api

In this implementation the ViewModel listens to the database as a flow, so that when any changes occur to the database it will then call the callback it was passed. When breed data is requested, the model fetches it from the network and saves it to the database. This, in turn, triggers the database flow to update the platform for display updates.

In Short:
**Platform -> BreedViewModel -> BreedRepository -> DogApiImpl -> BreedModel -> DatabaseHelper -> BreedRepository -> BreedViewModel -> Platform**

You may be asking where the `Multiplatform-settings` comes in. When the BreedModel is told to get breeds from the network, it first checks to see if it's done a network request within the past hour. If it has then it decides not to update the breeds.

## Kotlinx Coroutines

We use a new version of Kotlinx Coroutines that uses a new memory model that resolves the multithreading and object freezing concerns. To learn more, refer to the [Migration Guide](https://github.com/JetBrains/kotlin/blob/master/kotlin-native/NEW_MM.md)
and [our Blogpost](https://touchlab.co/testing-the-kotlin-native-memory-model/).

 Explore the implementations in [DogApiImpl.kt](https://github.com/touchlab/KaMPKit/blob/5376b4c2dd4be7f2436e10dddbf56b0d5ab33443/shared/src/commonMain/kotlin/co/touchlab/kampkit/ktor/DogApiImpl.kt#L36)
and [BreedModel.kt](https://github.com/touchlab/KaMPKit/blob/b2e8a330f8c12429255711c4c55a328885615d8b/shared/src/commonMain/kotlin/co/touchlab/kampkit/models/BreedModel.kt#L49)

## Libraries and Dependencies

If you're familiar with Android projects then you know that the apps dependencies are stored in the `build.gradle.kts`. Since shared is a library project, it also contains its own `build.gradle.kts` where it defines its own dependencies. If you open *`shared/build.gradle.kts`* you will see **`sourceSets`** corresponding to the directories in the shared project.

Each part of the shared library can declare its own dependencies in these source sets. For example the `multiplatform-settings` library is only declared for the **commonMain** and **commonTest**, since the multiplatform gradle plugin uses hierarchical project structure to pull in the correct platform specific dependencies. Other libraries like `SqlDelight`, which necessitate platform-specific variables, require distinct platform dependencies. Consider the example of `commonMain` using `sqlDelight.runtime`, while `androidMain` utilizes `sqlDelight.driverAndroid`.

Below is some information about some of the libraries used in the project.

* [SKIE](#SKIE)
* [Kermit](#Kermit)
* [SqlDelight](#SqlDelight)
* [Ktor](#Ktor)
* [Multiplatform Settings](#Multiplatform-Settings)
* [Koin](#Koin)
* [Turbine](#Turbine)

### SKIE

Documentation: https://skie.touchlab.co/intro

SKIE is setup as a Gradle plugin. SKIE runs during compile-time, generating Kotlin IR and Swift code. The Swift code is compiled and linked directly into the Xcode Framework produced by the Kotlin compiler, requiring no changes for your code distribution.

SKIE streamlines iOS code, reducing the preceding boilerplate. Suspend functions and flows are automatically translated into Swift-style async functions or streams. Additionally, SKIE simplifies the conversion between Kotlin sealed classes and Swift enums, facilitating more idiomatic and exhaustive switches in Swift.

### Kermit

Documentation: https://kermit.touchlab.co/

Kermit is a Kotlin Multiplatform logging library. It's as easy as it can get logging library. The default platform `LogWriter` is readily available without any setup hassles.

### SqlDelight
Documentation: [https://github.com/cashapp/sqldelight](https://github.com/cashapp/sqldelight)

Usage in the project: *commonMain/kotlin/co/touchlab/kampkit/DatabaseHelper.kt*

SQL Location in the project: *commonMain/sqldelight/co/touchlab/kampkit/Table.sq*

SqlDelight is a multiplatform SQL library that generates type-safe APIs from SQL Statements. Since it is a multiplatform library, it naturally uses code stored in commonMain. SQL statements are stored in the sqldelight directory, in .sq files.
ex: *"commonMain/sqldelight/co/touchlab/kampkit/Table.sq"*

Even though the SQL queries and main bulk of the library are in the common code, there are some platform specific drivers required from Android and iOS in order to work correctly on each platform. These are the `AndroidSqliteDriver` and the `NativeSqliteDriver`(for iOS). These are passed in from platform specific code, in this case injected into the **BreedModel**. The APIs are stored in the build folder, and referenced from the `DatabaseHelper` (also in commonMain).

   ##### Flow
   Normally sql queries are called, and a result is given, but what if you want to get sql query as a flow? We've added Coroutine Extensions to the shared code, which adds the `asFlow` function that converts queries into flows. Behind the scenes this creates a Query Listener that when a query result has changed, emits the new value to the flow.

### Ktor
Documentation: https://ktor.io/

Usage in the project: *commonMain/kotlin/co/touchlab/kampkit/ktor/DogApiImpl.kt*

Ktor, a multiplatform networking library, facilitates asynchronous client creation. Although the entirety of Ktor's code is housed in `commonMain`, specific platform dependencies are necessary for proper functionality. These dependencies are outlined in the build.gradle.

### Multiplatform Settings
Documentation: https://github.com/russhwolf/multiplatform-settings

Usage in the project: *commonMain/kotlin/co/touchlab/kampkit/models/BreedModel.kt*

Multiplatform settings really speaks for itself. It persists data by storing it in settings. It is being used in the `BreedModel`, and acts similarly to a `HashMap` or `Dictionary`. Much like `SqlDelight` the actual internals of the settings are platform specific, so the settings are passed in from the platform and all of the actual saving and loading is in the common code.

### Koin
Documentation: https://insert-koin.io/

Usage in the project: *commonMain/kotlin/co/touchlab/kampkit/Koin.kt*

Koin is a lightweight dependency injection framework. It is being used in the *koin.kt* file to inject modules into the BreedModel.

Injected variables within the BreedModel are marked using by inject(). We've structured injections into two modules: coreModule and platformModule. The former houses `Ktor` and `Database Helper` implementations, while the latter encompasses platform-specific dependencies (`SqlDelight` and `Multiplatform Settings`).

## Testing

With KMP, tests can be shared across platforms. However, due to platform-specific drivers and dependencies, tests must be executed on individual platforms. In essence, while tests can be shared, they must be run separately for Android and iOS.

The shared tests can be found in the `commonTest`
directory, while the implementations can be found in the `androidTest` and `iosTest` directories.

Dependency injection for testing is managed in the `TestUtil.kt` file in `commonTest`. This file facilitates the injection of platform-specific libraries (for instance, `SqlDelight` requiring a platform driver) into the `BreedRepository` to enable effective testing.

For running tests we use `kotlinx.coroutines.test.runTest`. For specifying a test
runner we use `@RunWith()` annotation. Platform-specific implementations of `testDbConnection()` are stored in *TestUtilAndroid.kt* and *TestUtilIOS.kt*.

### Turbine
Check out the [Repository](https://github.com/cashapp/turbine) for more info.

Turbine is a small testing library for `kotlinx.coroutines Flow`.
A practical example can be found in `BreedViewModelTest.kt`.

### Android
On the android side we are using `AndroidRunner` to run the tests because we want to use android specifics in our tests. If you're not using android specific methods then you don't need to use `AndroidRunner`. The android tests are run can be easily run in Android Studio by right clicking on the folder, and selecting `Run 'All Tests'`.

### iOS
iOS tests have their own gradle task allowing them to run with an iOS simulator. You can simply go to the terminal and run `./gradlew iosTest`.
