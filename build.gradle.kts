
// https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.gradleVersions)
    alias(libs.plugins.ktlint) apply false

    kotlin("multiplatform") version libs.versions.kotlin.get() apply false
    kotlin("plugin.serialization") version libs.versions.kotlin.get() apply false
    id("com.squareup.sqldelight") version libs.versions.sqlDelight.get() apply false
    id("com.android.library") version libs.versions.android.gradle.plugin.get() apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    // TODO libs doesn't resolve if we do this
    // apply(plugin = libs.plugins.ktlint.get().pluginId)
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        enableExperimentalRules.set(true)
        verbose.set(true)
        filter {
            exclude { it.file.path.contains("build/") }
        }
    }

    afterEvaluate {
        tasks.named("check").configure {
            dependsOn(tasks.getByName("ktlintCheck"))
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
