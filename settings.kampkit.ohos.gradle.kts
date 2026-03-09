/*
 * KaMPKit standalone OHOS settings — only builds :shared for ohosArm64.
 * Use with: ./gradlew -c settings.kampkit.ohos.gradle.kts :shared:link[Debug|Release]SharedLibOhosArm64
 */
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://mirrors.tencent.com/nexus/repository/maven-public/") }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        google()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://mirrors.tencent.com/nexus/repository/maven-public/") }
    }
}

rootProject.buildFileName = "build.kampkit.ohos.gradle.kts"

include(":shared")
project(":shared").buildFileName = "build.2.0.ohos.gradle.kts"
