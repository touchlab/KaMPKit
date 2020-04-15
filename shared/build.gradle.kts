import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("co.touchlab.native.cocoapods")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(Versions.min_sdk)
        targetSdkVersion(Versions.target_sdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

kotlin {
    android()
    //Revert to just ios() when gradle plugin can properly resolve it
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos")?:false
    if(onPhone){
        iosArm64("ios")
    }else{
        iosX64("ios")
    }
    targets.getByName<KotlinNativeTarget>("ios").compilations["main"].kotlinOptions.freeCompilerArgs +=
        listOf("-Xobjc-generics", "-Xg0")

    version = "1.1"

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib-common", Versions.kotlin))
        implementation(Deps.SqlDelight.runtime)
        implementation(Deps.ktor.commonCore)
        implementation(Deps.ktor.commonJson)
        implementation(Deps.Coroutines.common)
        implementation(Deps.stately)
        implementation(Deps.multiplatformSettings)
        implementation(Deps.koinCore)
        implementation(Deps.ktor.commonSerialization)

    }

    sourceSets["commonTest"].dependencies {
        implementation(Deps.multiplatformSettingsTest)
        implementation(Deps.KotlinTest.common)
        implementation(Deps.KotlinTest.annotations)
    }

    sourceSets["androidMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.SqlDelight.driverAndroid)
        implementation(Deps.ktor.jvmCore)
        implementation(Deps.ktor.jvmJson)
        implementation(Deps.Coroutines.jdk)
        implementation(Deps.Coroutines.android)
        implementation(Deps.ktor.androidSerialization)
    }

    sourceSets["androidTest"].dependencies {
        implementation(Deps.KotlinTest.jvm)
        implementation(Deps.KotlinTest.junit)
        implementation(Deps.AndroidXTest.core)
        implementation(Deps.AndroidXTest.junit)
        implementation(Deps.AndroidXTest.runner)
        implementation(Deps.AndroidXTest.rules)
        implementation(Deps.Coroutines.test)
        implementation("org.robolectric:robolectric:4.3")
    }

    sourceSets["iosMain"].dependencies {
        implementation(Deps.SqlDelight.driverIos)
        implementation(Deps.ktor.ios)
        implementation(Deps.ktor.iosCore)
        implementation(Deps.ktor.iosJson)
        implementation(Deps.Coroutines.native) {
            version {
                strictly("1.3.5-native-mt")
            }
        }
        implementation(Deps.ktor.iosSerialization)
    }

    cocoapodsext {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPStarter"
        isStatic = false
    }
}

sqldelight {
    database("KampstarterDb") {
        packageName = "co.touchlab.kampstarter.db"
    }
}
