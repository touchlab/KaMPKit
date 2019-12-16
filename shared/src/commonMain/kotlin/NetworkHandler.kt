package com.touchlab.shared

import com.touchlab.shared.ktorExample.KtorApiImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object NetworkHandler {

    fun getKtorExample() = CoroutineScope(Dispatchers.Main).launch {
        val result = KtorApiImpl.getThingJson()
        print(result)
    }
}