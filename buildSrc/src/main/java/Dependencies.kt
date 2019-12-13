object Versions {
    val min_sdk = 15
    val target_sdk = 29
    val compile_sdk = 29

    val kotlin = "1.3.61"
    val android_x = "1.1.0"
    val android_gradle_plugin = "3.5.3"
    val buildToolsVersion = "29.0.0"
    val junit = "4.12"
}

object Deps {
    val app_compat_x = "androidx.appcompat:appcompat:${Versions.android_x}"
    val core_ktx = "androidx.core:core-ktx:${Versions.android_x}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.android_x}"
    val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    val junit = "junit:junit:${Versions.junit}"
}