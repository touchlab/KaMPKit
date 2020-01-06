# Architecture Overview

This doc goes over the overall architecture of the app, the libraries usage and the locations of files and directories.

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

# Coroutines and Ktor

The version of coroutines in the sample app are currently in development. Previous versions supported only single
 threads on native, but this version supports crossing thread boundaries. However, because of the way the internals work
 to use ktor, you'll need a separate scope. See [`ktorScope`](https://github.com/touchlab/KaMPStarter/blob/0473a6ebdd5e1293864cce107fa4af2089e48ef0/shared/src/commonMain/kotlin/co/touchlab/kampstarter/models/BaseModel.kt#L13) in `BaseModel.kt`. This is a temporary workaround.

## Libraries and Dependencies

If you're familiar with Android projects then you know that the apps dependencies are stored in the build.gradle. Since shared is an android library, it also contains its own build.gradle where it stores its own dependencies. If you open *"shared/build.gradle.kts"* you will see **sourceSets** corresponding to the directories in the shared project. 

Each part of the shared library can declare its own dependencies in these source sets. For example the multiplatform-settings library is only declared in **commonMain** and **commonTest**, since the library uses gradle metadata to pull in the correct platform specific dependencies. Other libraries that may require platform specific variables, such as SqlDelight, require platform specific dependencies. With this example you can see that **commonMain** has `sqlDelight.runtime` and **androidMain** has `sqlDelight.driverAndroid`.

Below is some information about some of the libraries used in the project.

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