plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(Versions.compile_sdk)
    buildToolsVersion = Versions.buildToolsVersion
    defaultConfig {
        applicationId = "co.touchlab.kampkit"
        minSdkVersion(Versions.min_sdk)
        targetSdkVersion(Versions.target_sdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        coreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = true
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Deps.AndroidX.recyclerView)
    implementation(Deps.AndroidX.swipeRefresh)
    implementation(Deps.material)
    coreLibraryDesugaring(Deps.desugarJdkLibs)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.core_ktx)
    implementation(Deps.Ktor.androidCore)
    implementation(Deps.AndroidX.constraintlayout)
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
    implementation(Deps.AndroidX.lifecycle_livedata)
    implementation(Deps.AndroidX.lifecycle_extension)
    testImplementation(Deps.junit)
}
