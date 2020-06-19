package co.touchlab.kampkit

import co.touchlab.kampkit.db.KaMPKitDb
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.NSLogLogger
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

val namedUserDefaults = named("userDefaults")
actual val platformModule = module {
    single<Settings> {
        // Note that Koin can't bind Objective-C types because they have no KClass. So we bind NSUserDefaults as Any
        //  instead and we use a Qualifier to specify what we're looking for.
        // All this ceremony isn't really necessary since we could just initialize UserDefaults in place. But it serves
        //  to demo passing Swift dependencies into Koin in more complex scenarios.
        AppleSettings(get<Any>(namedUserDefaults) as NSUserDefaults)
    }
    single<SqlDriver> { NativeSqliteDriver(KaMPKitDb.Schema, "KampkitDb") }

    val baseKermit = Kermit(NSLogLogger()).withTag("KampKit")
    factory { (tag: String?) -> if (tag != null) baseKermit.withTag(tag) else baseKermit }
}

fun KoinApplication.module(moduleDeclaration: Module.() -> Unit) {
    modules(org.koin.dsl.module(moduleDeclaration = moduleDeclaration))
}

fun Module.single(
    qualifier: Qualifier,
    definition: () -> Any
) {
    single(qualifier) { definition() }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, null) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier, null)
}
