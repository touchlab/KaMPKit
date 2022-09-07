package co.touchlab.kampkit

import kotlin.reflect.KClass

@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
expect annotation class RunWith(val value: KClass<out Runner>)
expect abstract class Runner
expect class AndroidJUnit4 : Runner

@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
expect annotation class Config(
    val sdk: IntArray = [],
    val minSdk: Int = -1,
    val maxSdk: Int = -1,
    val manifest: String = "--default",
    val application: KClass<out Application> = Application::class,
    val packageName: String = "",
    val qualifiers: String = "",
    val resourceDir: String = "res",
    val assetDir: String = "assets",
    val shadows: Array<KClass<*>> = [],
    val instrumentedPackages: Array<String> = [],
    val libraries: Array<String> = []
)

expect open class Application
