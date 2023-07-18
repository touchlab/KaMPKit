plugins {
    id("com.android.application")
    id("org.jetbrains.compose")
    kotlin("android")
}

android {
    namespace = "co.touchlab.kampkit.android"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "co.touchlab.kampkit"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    lint {
        warningsAsErrors = false
        abortOnError = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

// Using Compose gradle plugin v 1.4.1 which supports at most Kotlin 1.8.1
// And SQLDelight 2.0+ which supports at least Kotlin 1.8.2
// Quick workaround until we get some easier to match versions.
compose {
    kotlinCompilerPlugin.set("org.jetbrains.compose.compiler:compiler:1.4.8")
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.bundles.app.ui)
    implementation(libs.multiplatformSettings.common)
    implementation(libs.kotlinx.dateTime)
    coreLibraryDesugaring(libs.android.desugaring)
    implementation(libs.koin.android)
    testImplementation(libs.junit)
}
