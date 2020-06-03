package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.printThrowable
import co.touchlab.kermit.Kermit
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

open class BaseModel : KoinComponent {
    private val log: Kermit by inject { parametersOf("BaseModel") }

    open fun onDestroy() {
        log.v { "onDestroy called" }
    }
}