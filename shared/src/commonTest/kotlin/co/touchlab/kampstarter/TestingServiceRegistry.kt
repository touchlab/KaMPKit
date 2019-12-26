package co.touchlab.kampstarter

import co.touchlab.kampstarter.ktor.KtorApi
import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

object TestingServiceRegistry {

    internal fun appStart(helper: DatabaseHelper, settings: Settings, ktorApi: KtorApi){
        val coreModule = module {
            single { helper }
            single { settings }
            single { ktorApi }
        }

        startKoin { modules(coreModule) }

    }

    internal fun appEnd(){
        stopKoin()
    }
}