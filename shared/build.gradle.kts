
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    jvm("android")

    var buildForDevice = project.findProperty("kotlin.native.cocoapods.target") == "ios_arm"

    if (buildForDevice) {
        iosArm64("ios64")
        iosArm32("ios32")
    } else {
        iosX64("ios")
    }

    version = "1.0"

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation(Dependancies.ktor.commonCore)
        implementation(Dependancies.ktor.commonJson)
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
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
