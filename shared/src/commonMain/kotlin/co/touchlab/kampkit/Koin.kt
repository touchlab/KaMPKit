package co.touchlab.kampkit

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.koin.core.KoinApplication
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.ksp.generated.module

fun initKoin(appModule: org.koin.core.module.Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            PlatformModule().module,
            CoreModule().module
        )
    }

    // Dummy initialization logic, making use of appModule declarations for demonstration purposes.
    val koin = koinApplication.koin
    // doOnStartup is a lambda which is implemented in Swift on iOS side
    val doOnStartup = koin.get<() -> Unit>()
    doOnStartup.invoke()

    val kermit = koin.get<Logger> { parametersOf(null) }
    // AppInfo is a Kotlin interface with separate Android and iOS implementations
    val appInfo = koin.get<AppInfo>()
    kermit.v { "App Id ${appInfo.appId}" }

    return koinApplication
}

@Module
@ComponentScan
class CoreModule {
    @Single
    fun clock(): Clock = Clock.System

    @Factory
    fun logger(tag: String? = null): Logger {
        // platformLogWriter() is a relatively simple config option, useful for local debugging. For production
        // uses you *may* want to have a more robust configuration from the native platform. In KaMP Kit,
        // that would likely go into platformModule expect/actual.
        // See https://github.com/touchlab/Kermit
        val baseLogger =
            Logger(config = StaticConfig(logWriterList = listOf(platformLogWriter())), "KampKit")
        return if (tag != null) baseLogger.withTag(tag) else baseLogger
    }

    @Factory
    fun coroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

internal inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

// Simple function to clean up the syntax a bit
fun KoinComponent.injectLogger(tag: String): Lazy<Logger> = inject { parametersOf(tag) }

@Module
expect class PlatformModule()
