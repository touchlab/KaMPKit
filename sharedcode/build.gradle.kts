plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    jvm("android")

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
    }

    cocoapods {
        summary = "Lots of KaMP Stuff"
        homepage = "https://github.com/touchlab/KaMPStarter"
    }
}
