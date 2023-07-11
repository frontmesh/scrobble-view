package com.frontmatic.scrobbleview.ui.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontmatic.scrobbleview.data.UseCases
import com.frontmatic.scrobbleview.data.api.LastFMApi
import com.frontmatic.scrobbleview.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

sealed interface UserUIState {
    object Loading : UserUIState
    data class Success(val user: User) : UserUIState
    data class Error(val errorBody: ResponseBody?) : UserUIState
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCases: UseCases,
    private val api: LastFMApi,
): ViewModel() {
    var userUIState: UserUIState by mutableStateOf(UserUIState.Loading)
    private val _searchUsername = mutableStateOf("")
    val searchUsername = _searchUsername

    fun updateSearchUsername(username: String) {
        _searchUsername.value = username
    }

    fun checkLastFMUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val username = searchUsername.value
            val res = api.getUserInfo(user = username)
            if (res.isSuccessful) {
                val user = res.body()!!.user
                useCases.saveUsername(username)
                useCases.saveUserInfoUseCase(user)
                userUIState = UserUIState.Success(user)
            } else {
                userUIState = UserUIState.Error(res.errorBody())
            }
        }
    }
}