package co.touchlab.kampkit

import co.touchlab.kampkit.db.KaMPKitDb
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.NSLogLogger
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.suparnatural.core.concurrency.Immutability
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(KaMPKitDb.Schema, "KampkitDb") }

    val baseKermit = Kermit(NSLogLogger()).withTag("KampKit")
    factory { (tag: String?) -> if (tag != null) baseKermit.withTag(tag) else baseKermit }
}

object KoinIOS {
    private var koin: KoinApplication? by Immutability(null)

    fun initialize(userDefaults: NSUserDefaults) {
        koin = initKoin {
            modules(
                module {
                    single<Settings> { AppleSettings(userDefaults) }
                }
            )
        }
    }

    fun get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
        val kClazz = getOriginalKotlinClass(objCClass)!!
        return koin!!.koin.get(kClazz, qualifier) { parametersOf(parameter) }
    }

    fun get(objCClass: ObjCClass, parameter: Any): Any {
        val kClazz = getOriginalKotlinClass(objCClass)!!
        return koin!!.koin.get(kClazz, null) { parametersOf(parameter) }
    }

    fun get(objCClass: ObjCClass, qualifier: Qualifier?): Any {
        val kClazz = getOriginalKotlinClass(objCClass)!!
        return koin!!.koin.get(kClazz, qualifier, null)
    }
}
