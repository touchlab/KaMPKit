// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(Deps.android_gradle_plugin)

        classpath(kotlin("gradle-plugin", version = "1.3.61"))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}