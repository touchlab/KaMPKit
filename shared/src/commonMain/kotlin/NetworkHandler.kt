package com.touchlab.shared

import com.touchlab.shared.ktorExample.KtorApiImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object NetworkHandler {

    fun getKtorExample() = GlobalScope.launch{
        val result = KtorApiImpl.getThingJson()
        print(result)
    }
}