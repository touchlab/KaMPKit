# Architecture Overview

This doc goes over the overall architecture of the app, the libraries usage and the locations of files and directories.

## Overall Architecture

The KaMPStarter kit, whether running in Android or iOS, starts with the platforms View (MainActivity / ViewController). There it creates the BreedModel, passes in a callback, and calls the BreedModel. The BreedModel is in the common MultiPlatform code.












# Coroutines and Ktor

The version of coroutines in the sample app are currently in development. Previous versions supported only single
 threads on native, but this version supports crossing thread boundaries. However, because of the way the internals work
 to use ktor, you'll need a separate scope. See [`ktorScope`](https://github.com/touchlab/KaMPStarter/blob/0473a6ebdd5e1293864cce107fa4af2089e48ef0/shared/src/commonMain/kotlin/co/touchlab/kampstarter/models/BaseModel.kt#L13) in `BaseModel.kt`. This is a temporary workaround.
