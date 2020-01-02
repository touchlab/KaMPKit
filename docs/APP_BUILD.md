# Sample App Build

Walk through cloning app. If Android Studio or Intellij not installed, link to other doc.

Describe:

. shared
. app
. ios


The KaMPStarter kit is broken up into three different directories: 
* shared
* app
* ios

The app directory is the android project and version of the app. As a default Android Studio will name the project "app" when creating it. Even though this can be confusing for kmp this is the default.

As you may have guessed, the ios directory holds the iOS version of the app and the shared directory holds the shared code. 

The shared directory is actually an android library that is referenced from the app project. This library contains directories for the different platforms as well as directories for testing.

  * androidMain
  * iosMain
  * commonMain
  * androidTest
  * iosTest
  * commonTest
  
Each of these directories has the same folder structure: the language type, then the package name.
  i.e. "kotlin/co/touchlab/kampstarter/"
  
## SqlDelight

SqlDelight is a multi-platform SQL library that generates type-safe APIs from SQL Statements. Since it is a multi-platform library, it naturally uses code stored in commonMain. SQL statements are stored in the sqldelight directory, in .sq files.
ex: "commonMain/sqldelight/co/touchlab/kampstarter/Table.sq"
The APIs are stored in the build folder, and referenced from the DatabaseHelper (also in commonMain). Normally sql queries are called, and a result is given, but what if you want to get sql query as a flow? We've added Coroutine Extensions to the shared code, which adds the "asFlow" function that converts queries into flows. Behind the scenes this creates a Query Listener that when a query result has changed, emits the new value to the flow.

If you have any more questions on SqlDelight, or want to know more you can find the project here: https://github.com/cashapp/sqldelight







Walk through where dependencies are configured in shared.
 and point to SQLDelight docs.

Point out where Ktor call happens, and point to Ktor docs and/or tutorial.

Point out where multiplatform settings is being used. Link to those docs.
