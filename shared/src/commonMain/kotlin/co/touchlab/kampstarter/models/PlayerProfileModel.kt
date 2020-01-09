package co.touchlab.kampstarter.models

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerProfileModel (val playerId:Long){

    suspend fun updateDb(){
        val l = playerId
        withContext(Dispatchers.Default){
            doSomething(l)
        }
    }

}


fun doSomething(l:Long){}