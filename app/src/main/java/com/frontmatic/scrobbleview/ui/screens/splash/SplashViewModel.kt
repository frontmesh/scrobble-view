package com.frontmatic.scrobbleview.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.scrobbleview.data.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _usernameSet = MutableStateFlow(false)
    val usernameSet: StateFlow<Boolean> = _usernameSet

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _usernameSet.value = useCases.getUsername().stateIn(viewModelScope).value.isNotEmpty()
        }
    }
}