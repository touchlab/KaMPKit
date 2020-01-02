package co.touchlab.kampstarter.android

import android.app.Application
import android.content.Context
import co.touchlab.kampstarter.initKoin
import org.koin.dsl.module

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            modules(module { single<Context> { this@MainApp } })
        }
    }
}