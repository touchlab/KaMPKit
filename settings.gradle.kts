pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://api.touchlab.dev/public")
    }
}

include(":app", ":shared")
rootProject.name = "KaMPKit"
