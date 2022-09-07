pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":app", ":shared")
rootProject.name = "KaMPKit"

enableFeaturePreview("VERSION_CATALOGS")
