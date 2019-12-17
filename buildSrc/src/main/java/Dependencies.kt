object Versions {
    val min_sdk = 21
    val target_sdk = 29
    val compile_sdk = 29

    val kotlin = "1.3.60"
    val android_x = "1.1.0"
    val android_gradle_plugin = "3.4.1"
    val buildToolsVersion = "29.0.0"
    val junit = "4.12"

    val ktor = "1.2.6"
    val coroutines = "1.2.0"
}

object Deps {
    val app_compat_x = "androidx.appcompat:appcompat:${Versions.android_x}"
    val core_ktx = "androidx.core:core-ktx:${Versions.android_x}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.android_x}"
    val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    val junit = "junit:junit:${Versions.junit}"
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val androidCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    object ktor {
        val commonCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        val commonJson = "io.ktor:ktor-client-json:${Versions.ktor}"
        val jvmCore =     "io.ktor:ktor-client-core-jvm:${Versions.ktor}"
        val androidCore = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
        val jvmJson =     "io.ktor:ktor-client-json-jvm:${Versions.ktor}"
        val ios =         "io.ktor:ktor-client-ios:${Versions.ktor}"
        val iosCore =     "io.ktor:ktor-client-core-native:${Versions.ktor}"
        val iosJson =     "io.ktor:ktor-client-json-native:${Versions.ktor}"

    }
}