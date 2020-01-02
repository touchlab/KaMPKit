# Architecture Overview

This doc goes over the overall architecture of the app, the libraries usage and the locations of files and directories.

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
