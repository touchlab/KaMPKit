package co.touchlab.kampstarter

import co.touchlab.kampstarter.ktor.DogApiImpl
import co.touchlab.kampstarter.ktor.KtorApi
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(platformModule, coreModule)
}

val coreModule = module {
    single { DatabaseHelper(get()) }
    single<KtorApi> { DogApiImpl() }
}

expect val platformModule: Module