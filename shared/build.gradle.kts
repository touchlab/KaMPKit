plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint {
        isWarningsAsErrors = true
        isAbortOnError = true
    }
}

version = "1.0"

android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

kotlin {
    android()
    ios()
    // Don't add this until all dependencies support M1 targets
    // iosSimulatorArm64()
    // sourceSets["iosSimulatorArm64Main"].dependsOn(sourceSets["iosMain"])
    // sourceSets["iosSimulatorArm64Test"].dependsOn(sourceSets["iosTest"])

    version = "1.1"

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    sourceSets["commonMain"].dependencies {
        implementation(libs.koin.core)
        implementation(libs.coroutines.core)
        implementation(libs.sqlDelight.coroutinesExt)
        implementation(libs.bundles.ktor.common)
        implementation(libs.touchlab.stately)
        implementation(libs.multiplatformSettings.common)
        implementation(libs.kotlinx.dateTime)
        api(libs.touchlab.kermit)
    }

    sourceSets["commonTest"].dependencies {
        implementation(libs.bundles.shared.commonTest)
    }

    sourceSets.matching { it.name.endsWith("Test") }
        .configureEach {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }

    sourceSets["androidMain"].dependencies {
        implementation(libs.sqlDelight.android)
        implementation(libs.ktor.client.okHttp)
    }

    sourceSets["androidTest"].dependencies {
        implementation(libs.bundles.shared.androidTest)
    }

    sourceSets["iosMain"].dependencies {
        implementation(libs.sqlDelight.native)
        implementation(libs.ktor.client.ios)
        implementation(libs.coroutines.core)
        val coroutineCore = libs.coroutines.core.get()
        implementation("${coroutineCore.module.group}:${coroutineCore.module.name}:${coroutineCore.versionConstraint.displayName}") {
            version {
                strictly(libs.versions.coroutines.native.get())
            }
        }
    }

    cocoapods {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPKit"
        framework {
            isStatic = false // SwiftUI preview requires dynamic framework
        }
        ios.deploymentTarget = "12.4"
        podfile = project.file("../ios/Podfile")
    }
}

sqldelight {
    database("KaMPKitDb") {
        packageName = "co.touchlab.kampkit.db"
    }
}
