package co.touchlab.kampkit

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.kampkit.db.KaMPKitDb
import co.touchlab.kampkit.models.BreedRepository
import co.touchlab.kampkit.models.BreedViewModel
import co.touchlab.kampkit.models.IBreedRepository
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.darwin.Darwin
import kotlin.time.Clock
import kotlinx.coroutines.Dispatchers
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

fun initKoinIos(userDefaults: NSUserDefaults, appInfo: AppInfo, doOnStartup: () -> Unit): KoinApplication = initKoin(
    module {
        single<Settings> { NSUserDefaultsSettings(userDefaults) }
        single { appInfo }
        single { doOnStartup }
    },
)

actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(KaMPKitDb.Schema, "KampkitDb") }

    single { Darwin.create() }

    single { BreedViewModel(get(), getWith("BreedViewModel")) }
}

actual val coreModule = module {
    single {
        DatabaseHelper(
            get(),
            getWith("DatabaseHelper"),
            Dispatchers.Default,
        )
    }
    single<co.touchlab.kampkit.ktor.DogApi> {
        co.touchlab.kampkit.ktor.DogApiImpl(
            getWith("DogApiImpl"),
            get(),
        )
    }
    single<Clock> {
        Clock.System
    }
    val baseLogger =
        Logger(config = StaticConfig(logWriterList = listOf(platformLogWriter())), "KampKit")
    factory { (tag: String?) -> if (tag != null) baseLogger.withTag(tag) else baseLogger }
    single<IBreedRepository> {
        BreedRepository(
            get(),
            get(),
            get(),
            getWith("BreedRepository"),
            get(),
        )
    }
}

// Access from Swift to create a logger
@Suppress("unused")
fun Koin.loggerWithTag(tag: String) = get<Logger>(qualifier = null) { parametersOf(tag) }

@Suppress("unused") // Called from Swift
object KotlinDependencies : KoinComponent {
    fun getBreedViewModel() = getKoin().get<BreedViewModel>()
}
