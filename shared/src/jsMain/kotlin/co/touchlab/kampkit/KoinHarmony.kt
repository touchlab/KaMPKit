package co.touchlab.kampkit

import co.touchlab.kampkit.models.BreedViewModel
import co.touchlab.kampkit.models.IBreedRepository
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.russhwolf.settings.Settings
import io.ktor.client.engine.js.Js
import kotlin.time.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * HarmonyOS / OpenHarmony 入口：Common + Platform Main。
 * OHOS 端先调用 initKoinHarmony(appInfo, doOnStartup)，再通过 KotlinDependenciesHarmony.getBreedViewModel() 获取数据。
 */
fun initKoinHarmony(appModule: Module) = initKoin(appModule)

fun initKoinHarmony(appInfo: AppInfo, doOnStartup: () -> Unit) = initKoin(
    module {
        single { appInfo }
        single { doOnStartup }
    },
)

@Suppress("unused")
object KotlinDependenciesHarmony : KoinComponent {
    fun getBreedViewModel(): BreedViewModel = org.koin.core.context.GlobalContext.get().get()
}

actual val platformModule: Module = module {
    single<Settings> { MemorySettings() }
    single { Js.create() }
    single { BreedViewModel(get(), getWith("BreedViewModel")) }
}

actual val coreModule: Module = module {
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
        HarmonyBreedRepository(
            get(),
            get(),
            getWith("BreedRepository"),
            get(),
        )
    }
}
