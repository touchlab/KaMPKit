package com.nbc.identity.viewmodel

import com.nbc.identity.MainScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("unused")
class iOSTestViewModel : TestViewModelInterface by TestImplementation()
{
    override val scope: MainScope = MainScope(Dispatchers.Main, log)

    fun startObserving(onReturn: (String) -> Unit){
        scope.launch {
            stateFlow.collect {
                onReturn(it)
            }
        }
    }
}