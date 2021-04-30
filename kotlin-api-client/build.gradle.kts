plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

group = "com.myworkout"
version = "--matching-the-api-version--"

val kotlin_version = "1.4.32"
val coroutines_version = "1.3.8"
val serialization_version = "1.1.0"
val ktor_version = "1.5.3"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    ios { binaries { framework { freeCompilerArgs += "-Xobjc-generics" } } }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serialization_version")
                api("io.ktor:ktor-client-core:$ktor_version")
                api("io.ktor:ktor-client-json:$ktor_version")
                api("io.ktor:ktor-client-serialization:$ktor_version")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.ktor:ktor-client-mock:$ktor_version")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk7"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val iosMain by getting {
            dependencies {
                api("io.ktor:ktor-client-ios:$ktor_version")
            }
        }

        val iosTest by getting

        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
            }
        }
    }
}
