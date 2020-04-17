package co.touchlab.kampstarter

import kotlinx.coroutines.CoroutineScope

expect abstract class BaseTest(){
    fun <T> runTest(block: suspend CoroutineScope.() -> T)
}