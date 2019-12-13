
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    jvm("android")

    val buildForDevice = project.findProperty("kotlin.native.cocoapods.target") == "ios_arm"

    if (buildForDevice) {
        iosArm64("ios64")
        iosArm32("ios32")
    } else {
        iosX64("ios")
    }

    version = "1.0"

    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib-common", Versions.kotlin))
        implementation(Dependancies.ktor.commonCore)
        implementation(Dependancies.ktor.commonJson)
    }

    sourceSets["androidMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Dependancies.ktor.jvmCore)
        implementation(Dependancies.ktor.jvmJson)
    }

    sourceSets["iosMain"].dependencies {
        implementation(Dependancies.ktor.ios)
        implementation(Dependancies.ktor.iosCore)
        implementation(Dependancies.ktor.iosJson)
    }

    cocoapods {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPStarter"
    }
}
