plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.squareup.sqldelight")
}

kotlin {
    jvm("android")

    val buildForDevice = project.findProperty("kotlin.native.cocoapods.target") == "ios_arm"

    if (buildForDevice) {
        iosArm64("ios64")
    } else {
        iosX64("ios")
    }

    version = "1.0"

    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib-common", Versions.kotlin))
        implementation(Deps.SqlDelight.runtime)
    }

    sourceSets["androidMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.SqlDelight.driverAndroid)
    }

    sourceSets["iosMain"].dependencies {
        implementation(Deps.SqlDelight.driverIos)
    }

    cocoapods {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPStarter"
    }
}

sqldelight {
    database("KampstarterDb") {
        packageName = "co.touchlab.kampstarter.db"
    }
}