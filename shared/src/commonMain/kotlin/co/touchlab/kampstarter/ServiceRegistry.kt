package co.touchlab.kampstarter

import kotlinx.atomicfu.atomic

object ServiceRegistry {
    private val _helper = atomic<DatabaseHelper?>(null)
    fun appStart(helper: DatabaseHelper){
        _helper.value = helper
    }

    val helper: DatabaseHelper
        get() = _helper.value!!
}