plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = Versions.compile_sdk
    buildToolsVersion = Versions.buildToolsVersion
    defaultConfig {
        applicationId = "co.touchlab.kampkit"
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packagingOptions {
        resources.excludes.add("META-INF/*.kotlin_module")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lint {
        isWarningsAsErrors = true
        isAbortOnError = true
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.compose
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Deps.material)
    coreLibraryDesugaring(Deps.desugarJdkLibs)
    implementation(Deps.AndroidX.core_ktx)
    implementation(Deps.Ktor.androidCore)
    implementation(Deps.SqlDelight.runtimeJdk)
    implementation(Deps.SqlDelight.driverAndroid)
    implementation(Deps.Coroutines.common)
    implementation(Deps.Coroutines.android)
    implementation(Deps.multiplatformSettings)
    implementation(Deps.koinCore)
    implementation(Deps.koinAndroid)
    implementation(Deps.AndroidX.lifecycle_runtime)
    implementation(Deps.AndroidX.lifecycle_viewmodel)
    implementation(Deps.AndroidX.lifecycle_viewmodel_extensions)

    implementation(Deps.Compose.activityCompose)
    implementation(Deps.Compose.ui)
    implementation(Deps.Compose.uiTooling)
    implementation(Deps.Compose.foundation)
    implementation(Deps.Compose.material)
    implementation(Deps.Compose.Accompanist.swipeRefresh)

    testImplementation(Deps.junit)
}
