package com.frontmatic.scrobbleview.ui.screens.settings

import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

sealed interface UserUIState {
    object Idle : UserUIState
    object Loading : UserUIState
    data class Success(val user: User) : UserUIState
    data class Error(val errorBody: ResponseBody?) : UserUIState
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCases: UseCases,
    private val api: LastFMApi,
): ViewModel() {
    var userUIState: UserUIState by mutableStateOf(UserUIState.Idle)
        private set
    private val _searchUsername = mutableStateOf("")
    val searchUsername = _searchUsername


    fun updateSearchUsername(username: String) {
        _searchUsername.value = username
    }

    fun checkLastFMUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userUIState = UserUIState.Loading
            val queryUsername = searchUsername.value
            val storedUserInfo = useCases.getUserInfoByName(queryUsername)

            if (storedUserInfo != null) {
                useCases.saveUsername(queryUsername)
                useCases.saveUserChanged(true)
                userUIState = UserUIState.Success(storedUserInfo)
            } else {
                fetchUserInfo()
            }
        }
    }

    suspend fun fetchUserInfo() {
        val storedUsername = useCases.getUsername()
        val queryUsername = searchUsername.value
        val res = api.getUserInfo(user = queryUsername)
        userUIState = if (res.isSuccessful) {
            val user = res.body()!!.user

            // only skip all data refresh relating to user if this is the first time the user is being saved
            if (!storedUsername.equals("")) {
                useCases.saveUserChanged(true)
            }

            useCases.saveUsername(queryUsername)
            useCases.saveUserInfo(user)
            UserUIState.Success(user)
        } else {
            UserUIState.Error(res.errorBody())
        }
    }

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val storedUsername = useCases.getUsername().first()
            val user = useCases.getUserInfoByName(storedUsername)
            userUIState = user?.let { UserUIState.Success(it) } ?: UserUIState.Idle
        }
    }
}