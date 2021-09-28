package com.nbc.identity.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbc.identity.viewmodel.TestImplementation
import com.nbc.identity.viewmodel.TestViewModelInterface

class AndroidTestViewModel : ViewModel(), TestViewModelInterface by TestImplementation()
{
    override val scope = viewModelScope
}