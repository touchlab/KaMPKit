

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("app.cash.sqldelight")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
    }
    namespace = "co.touchlab.kampkit"
}

version = "1.2"

kotlin {
    android()
    ios()
    // Note: iosSimulatorArm64 target requires that all dependencies have M1 support
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlin.time.ExperimentalTime")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material) // for PullRefreshIndicator
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(libs.koin.core)
                implementation(libs.coroutines.core)
                implementation(libs.sqlDelight.coroutinesExt)
                implementation(libs.bundles.ktor.common)
                implementation(libs.multiplatformSettings.common)
                implementation(libs.kotlinx.dateTime)
                api(libs.touchlab.kermit)
                api(libs.moko.resources)
                api(libs.moko.resources.compose)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.shared.commonTest)
            }
        }
        val androidMain by getting {
            // Below line adds a temporary workaround for https://github.com/icerockdev/moko-resources/issues/531
            kotlin.srcDirs("build/generated/moko/androidMain/src")
            dependencies {
                implementation(libs.compose.activity)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.sqlDelight.android)
                implementation(libs.ktor.client.okHttp)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.bundles.shared.androidTest)
            }
        }
        val iosMain by getting {
            resources.srcDirs("build/generated/moko/iosMain/src")
            dependencies {
                implementation(libs.sqlDelight.native)
                implementation(libs.ktor.client.ios)
                api(libs.touchlab.kermit.simple)
            }
        }
        val iosTest by getting
        val iosSimulatorArm64Main by getting {
            resources.srcDirs("build/generated/moko/iosSimulatorArm64Main/src")
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }

    sourceSets.matching { it.name.endsWith("Test") }
        .configureEach {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }

    cocoapods {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPKit"
        framework {
            // Below line is needed for Compose Multiplatform, see https://github.com/JetBrains/compose-multiplatform/issues/3178
            isStatic = true
            linkerOpts("-lsqlite3")
            export(libs.touchlab.kermit.simple)
            export(libs.moko.resources)
            export(libs.moko.graphics)
        }
        ios.deploymentTarget = "14.0"
        podfile = project.file("../ios/Podfile")
    }
}

// Using Compose gradle plugin v 1.4.1 which supports at most Kotlin 1.8.1
// And SQLDelight 2.0+ which supports at least Kotlin 1.8.2
// Quick workaround until we get some easier to match versions.
compose {
    kotlinCompilerPlugin.set("org.jetbrains.compose.compiler:compiler:1.4.8")
}

sqldelight {
    databases.create("KaMPKitDb") {
        packageName.set("co.touchlab.kampkit.db")
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "co.touchlab.kampkit" // required
    // multiplatformResourcesSourceSet = "iosSimulatorArm64Main"
    multiplatformResourcesClassName = "MR" // optional, default MR
}
