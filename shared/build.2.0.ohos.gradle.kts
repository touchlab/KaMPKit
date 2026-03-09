/*
 * KaMPKit shared — OHOS Kotlin/Native build (KBA Kotlin 2.0.21).
 *
 * Produces libshared.so for ohosArm64.
 * Only compiles src/ohosMain/kotlin/ — OhosExports.kt is fully self-contained.
 * commonMain is cleared to avoid unresolvable dependencies (Koin, Ktor, SQLDelight, etc.)
 */
plugins {
    kotlin("multiplatform")
}

kotlin {
    ohosArm64 {
        binaries.sharedLib {
            baseName = "shared"
            freeCompilerArgs += "-Xadd-light-debug=enable"
            if (debuggable) {
                freeCompilerArgs += "-Xbinary=sourceInfoType=libbacktrace"
            }
        }
    }

    sourceSets {
        // Exclude commonMain — its dependencies have no ohos_arm64 variant
        val commonMain by getting {
            kotlin.setSrcDirs(emptyList<String>())
        }
        // ohosArm64Main is the leaf native source set — has access to kotlinx.cinterop
        // Sources live in ohos/native/ohosMain/kotlin/ so DWARF paths end with
        // "ohos/native/ohosMain/kotlin/..." — matching the paths DevEco uses for breakpoints.
        val ohosArm64Main by getting {
            kotlin.setSrcDirs(listOf("../ohos/native/ohosMain/kotlin"))
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlin.experimental.ExperimentalNativeApi")
            }
        }
    }
}
