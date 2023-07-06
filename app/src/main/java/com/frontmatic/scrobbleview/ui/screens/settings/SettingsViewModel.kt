package com.frontmatic.scrobbleview.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.scrobbleview.data.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _searchUsername = mutableStateOf("")
    val searchUsername = _searchUsername

    fun updateSearchUsername(username: String) {
        _searchUsername.value = username
    }

    fun saveUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.saveUsername(username)
        }
    }
}