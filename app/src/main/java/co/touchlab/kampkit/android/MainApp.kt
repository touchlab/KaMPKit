package co.touchlab.kampkit.android

import android.app.Application
import android.content.Context
import co.touchlab.kampkit.initKoin
import org.koin.dsl.module

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            modules(module { single<Context> { this@MainApp } })
        }
    }
}