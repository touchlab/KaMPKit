package co.touchlab.kampkit

import kotlin.reflect.KClass

@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
expect annotation class RunWith(val value: KClass<out Runner>)
expect abstract class Runner
expect class AndroidJUnit4 : Runner
