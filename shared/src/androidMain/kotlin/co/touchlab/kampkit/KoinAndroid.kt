package co.touchlab.kampkit

import android.content.Context
import android.content.SharedPreferences
import co.touchlab.kampkit.db.KaMPKitDb
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
actual class PlatformModule {

    @Single
    fun sqlDriver(context: Context): SqlDriver = AndroidSqliteDriver(
        KaMPKitDb.Schema,
        context,
        "KampkitDb"
    )

    @Single
    fun settings(delegate: SharedPreferences): Settings = SharedPreferencesSettings(delegate)

    @Single
    fun httpClientEngine(): HttpClientEngine = OkHttp.create()
}
