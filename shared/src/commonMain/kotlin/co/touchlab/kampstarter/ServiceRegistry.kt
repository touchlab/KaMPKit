package co.touchlab.kampstarter

import org.koin.core.context.startKoin
import org.koin.dsl.module

object ServiceRegistry {
    fun appStart(helper: DatabaseHelper){
        val coreModule = module {
            single { helper }
        }

        startKoin { modules(coreModule) }
    }
}