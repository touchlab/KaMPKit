plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
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
        vectorDrawables {
            useSupportLibrary = true
        }
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
        viewBinding = true
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
        kotlinCompilerVersion = "1.4.32"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Deps.AndroidX.recyclerView)
    implementation(Deps.AndroidX.swipeRefresh)
    implementation(Deps.material)
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.0-beta02")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.12.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
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
    testImplementation(Deps.junit)
}
