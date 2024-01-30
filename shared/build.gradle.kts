import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.skie)
}

android {
    namespace = "co.touchlab.kampkit"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

version = "1.2"

kotlin {
    // https://kotlinlang.org/docs/multiplatform-expect-actual.html#expected-and-actual-classes
    // To suppress this warning about usage of expected and actual classes
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
    androidTarget {
        @Suppress("OPT_IN_USAGE")
        unitTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlin.time.ExperimentalTime")
            }
        }

        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.coroutines.core)
            implementation(libs.sqlDelight.coroutinesExt)
            implementation(libs.bundles.ktor.common)
            implementation(libs.multiplatformSettings.common)
            implementation(libs.kotlinx.dateTime)
            implementation(libs.touchlab.skie.annotations)
            api(libs.touchlab.kermit)
        }
        commonTest.dependencies {
            implementation(libs.bundles.shared.commonTest)
        }
        androidMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.sqlDelight.android)
            implementation(libs.ktor.client.okHttp)
        }
        getByName("androidUnitTest").dependencies {
            implementation(libs.bundles.shared.androidTest)
        }
        iosMain.dependencies {
            implementation(libs.sqlDelight.native)
            implementation(libs.ktor.client.ios)
            api(libs.touchlab.kermit.simple)
        }
    }

    cocoapods {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPKit"
        framework {
            isStatic = false // SwiftUI preview requires dynamic framework
            linkerOpts("-lsqlite3")
            export(libs.touchlab.kermit.simple)
        }
        extraSpecAttributes["swift_version"] = "\"5.0\"" // <- SKIE Needs this!
        podfile = project.file("../ios/Podfile")
    }
}

sqldelight {
    databases.create("KaMPKitDb") {
        packageName.set("co.touchlab.kampkit.db")
    }
}
