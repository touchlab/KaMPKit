package co.touchlab.kampkit.base

import com.russhwolf.settings.Settings
import kotlinx.datetime.Clock

interface StaleDataDelegate {

    fun updateLastTime(key: StaleDataKey)

    fun isDataStale(key: StaleDataKey): Boolean

}