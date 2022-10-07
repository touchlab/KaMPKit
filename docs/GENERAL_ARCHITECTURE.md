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

KaMP Kit is broken up into three different directories: 
* shared
* app
* ios

The app directory holds the android version of the app, and all the android code. As a default, Android Studio will name the project "app" when creating it. Even though this can be confusing for kmp this is the default.

Similarly the ios directory holds the iOS version of the app, which contains an Xcode project and a Workspace. We want to use the workspace as it contains the shared library.

Finally the shared directory holds the shared code. The shared directory is actually an android library that is referenced from the app project. This library contains directories for the different platforms as well as directories for testing.

  * androidMain
  * iosMain
  * commonMain
  * androidTest
  * iosTest
  * commonTest

Each of these directories has the same folder structure: the language type, then the package name.
  i.e. *"kotlin/co/touchlab/kampkit/"*

## Overall Architecture

#### Platform
KaMP Kit, whether running in Android or iOS, starts with the platforms View (`MainActivity` / `ViewController`). These are the standard UI classes for each platform and they are launched when the app starts. They are responsible for all the UI, including dealing with the RecyclerView/UITableView, getting input from the user and handling the views lifecycle.
#### ViewModel

From the platforms views we then have the ViewModel layer which is responsible for connecting our
shared data and the views. If you want your shared viewmodel to be an `androidx.lifecycle.ViewModel`
on the Android side, you can take either a composition or inheritence approach. The composition
approach has a `CommonViewModel` that's just an object, and then wraps it in Android or iOS classes
that do the platform-specific work. An advantage is flexibility - the platform view models can do
extra platform-specific transformations more easily, and can more easily expose platform-specific
members that aren't delegated to common, but on the other hand Android has a wrapper layer that will
often be a no-op. For this project we chose the inheritence approach, because Android can use the
common viewmodel directly. To enable sharing of presentation logic between platforms, we
define `expect abstract class ViewModel` in `commonMain`, with platform specific implementations
provided in `androidMain` and `iosMain`. The android implementation simply extends the Jetpack
ViewModel, while an equivalent is implemented for iOS. We don't want to manage the coroutine
lifecycle on Android because the platform has its own scope handling. We just want to make sure our
viewmodel-layer coroutines are tied to the provided viewModelScope, and Android can directly consume
coroutine artifacts. On iOS, we need to manage that more explicitly. That means we have our own
scope and iOS consumers need to explicitly close it when the screen ends. We use `MainScope()` to
define coroutine scopes. This is a function from the kotlinx library that's just implemented
as `CoroutineScope(Dispatchers.Main + SupervisorJob())`. You may want to also add an error-handler
or other things, so consider a custom function in that case. An additional class `CallbackViewModel`
is also included for the iOS implementation. This acts as a wrapper for our ViewModel implementation
to make it easier to interact with from swift. We want to be able to wrap our flows in callbacks
that can be wired into our Swift code. Then inside our iOS viewmodel we define an extension function
on Flow to make conversion and scope-handling easy. There are different ways you might want to
consume this depending on your stack/architecture. We convert `FlowAdapter`s to `Publisher`s which
we then map inside `ObservableObjects` to `Published` values that can be easily observed from
SwiftUI views. With these platform specific implementations we can now implement our
ViewModel (`BreedViewModel`) in the common MultiPlatform code.

#### Repository
The `BreedRepository` is in the common MultiPlatform code, and handles the data access functionality. The `BreedRepository` references the Multiplatform-Settings, and two helper classes: `DogApiImpl` (which implements `DogApi`) and `DatabaseHelper`. The `DatabaseHelper` and `DogApiImpl` both use Multiplatform libraries to retrieve data and send it back to the `BreedRepository`. 

> note that the BreedRepository references the interface DogApi. This is so we can test the Model using a Mock Api

In this implementation the ViewModel listens to the database as a flow, so that when any changes occur to the database it will then call the callback it was passed. When breeds are requested the model retrieves the information from the network, then saves the data to the database. This triggers the database flow to send the new data to the Platform to update the display.

In Short:
**Platform -> BreedCallbackViewModel (iOS only) -> BreedViewModel -> BreedRepository -> DogApiImpl -> BreedModel -> DatabaseHelper -> BreedRepository -> BreedViewModel -> BreedCallbackViewModel (iOS only) -> Platform**

You may be asking where the Multiplatform-settings comes in. When the BreedModel is told to get breeds from the network, it first checks to see if it's done a network request within the past hour. If it has then it decides not to update the breeds. 

## Kotlinx Coroutines

We use a new version of Kotlinx Coroutines that uses a new memory model that solves the problems
with multithreading and freezing objects. To learn more
check [Migration Guide](https://github.com/JetBrains/kotlin/blob/master/kotlin-native/NEW_MM.md)
and [our Blogpost](https://touchlab.co/testing-the-kotlin-native-memory-model/).

See [DogApiImpl.kt](https://github.com/touchlab/KaMPKit/blob/5376b4c2dd4be7f2436e10dddbf56b0d5ab33443/shared/src/commonMain/kotlin/co/touchlab/kampkit/ktor/DogApiImpl.kt#L36)
and [BreedModel.kt](https://github.com/touchlab/KaMPKit/blob/b2e8a330f8c12429255711c4c55a328885615d8b/shared/src/commonMain/kotlin/co/touchlab/kampkit/models/BreedModel.kt#L49)

## Libraries and Dependencies

If you're familiar with Android projects then you know that the apps dependencies are stored in the build.gradle. Since shared is an android library, it also contains its own build.gradle where it stores its own dependencies. If you open *"shared/build.gradle.kts"* you will see **sourceSets** corresponding to the directories in the shared project. 

Each part of the shared library can declare its own dependencies in these source sets. For example the multiplatform-settings library is only declared in **commonMain** and **commonTest**, since the library uses gradle metadata to pull in the correct platform specific dependencies. Other libraries that may require platform specific variables, such as SqlDelight, require platform specific dependencies. With this example you can see that **commonMain** has `sqlDelight.runtime` and **androidMain** has `sqlDelight.driverAndroid`.

Below is some information about some of the libraries used in the project.

* [SqlDelight](#SqlDelight)
* [Ktor](#Ktor)
* [Multiplatform Settings](#Multiplatform-Settings)
* [Koin](#Koin)
* [Stately](#Stately)
* [Turbine](#Turbine)

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

Ktor is a multiplatform networking library for building asynchronous clients. Again since it is a multiplatform library, it  uses code stored in commonMain. Even though all of Ktors code is in **commonMain**, there are some platform specific dependencies needed in the build.gradle. 

### Multiplatform Settings
Documentation: https://github.com/russhwolf/multiplatform-settings

Usage in the project: *commonMain/kotlin/co/touchlab/kampkit/models/BreedModel.kt*

Multiplatform settings really speaks for itself, it persists data by storing it in settings. It is being used in the BreedModel, and acts similarly to a `HashMap` or `Dictionary`. Much like SqlDelight the actual internals of the settings are platform specific, so the settings are passed in from the platform and all of the actual saving and loading is in the common code.

### Koin
Documentation: https://insert-koin.io/

Usage in the project: *commonMain/kotlin/co/touchlab/kampkit/Koin.kt*

Koin is a lightweight dependency injection framework. It is being used in the *koin.kt* file to inject modules into the BreedModel. You can tell which variables are being injected in the BreedModel because they are being set using `by inject()`. In our implementation we are separating our injections into two different modules: the `coreModule` and the `platformModule`. As you can guess the platformModule contains injections requiring platform specific implementations (SqlDelight and Multiplatform Settings). The coreModule contains the Ktor implementation and the Database Helper, which actually takes from the platformModule.

### Stately
Documentation: https://github.com/touchlab/Stately
Stately is a state utility library used to help with state management in Kotlin Multiplatform (Made
by us!).

**Note:** Threading in K/N can be hard to grasp at first and this document isn't the place to go into it
in detail. If you want to find out more about thread check out this post 
[here](https://medium.com/@kpgalligan/kotlin-native-stranger-threads-ep-2-208523d63c8f)

## Testing

With KMP you can share tests between platforms, however since we have platform specific drivers and
dependencies our tests need to be run by the individual platforms. In short we can share tests but
we have to run them separately as android and iOS. The shared tests can be found in the commonTest
directory, while the implementations can be found in the androidTest and iosTest directories. You
may be thinking: Weren't the libraries injected? How does the dependency injection work? Well that
is actually handled in the `TestUtil.kt` file in commonTest. In order to pass the platform specific
libraries(SqlDelight requires a platform driver) into the BreedRepository for testing we need to
inject them. For running tests we use `kotlinx.coroutines.test.runTest`. For specifying a test
runner we use `@RunWith()` annotation. Note that the actual `testDbConnection()` implementations are
in the *TestUtilAndroid.kt* and *TestUtilIOS.kt* files.

### Turbine
Check out the [Repository](https://github.com/cashapp/turbine) for more info.
Turbine is a small testing library for kotlinx.coroutines Flow. 
For example: `BreedViewModelTest.kt`.

#### Android
On the android side we are using AndroidRunner to run the tests because we want to use android specifics in our tests. If you're not using android specific methods then you don't need to use AndroidRunner. The android tests are run can be easily run in Android Studio by right clicking on the shared folder, and selecting `Run 'All Tests'`.

#### iOS
iOS tests have their own gradle task allowing them to run with an iOS simulator. You can simply go to the terminal and run `./gradlew iosTest`.
