package com.frontmatic.scrobbleview.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.frontmatic.scrobbleview.ui.screens.destinations.ChartsScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.Destination
import com.frontmatic.scrobbleview.ui.screens.destinations.FriendsScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.SettingsScreenDestination
import com.frontmatic.scrobbleview.ui.screens.settings.SettingsScreen
import com.frontmatic.scrobbleview.ui.screens.settings.SettingsViewModel
import com.frontmatic.scrobbleview.ui.screens.settings.UserUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    destination: Destination,
    navBackStackEntry: NavBackStackEntry?
) {
    TopAppBar(
        title = {
            Text(
                text = destination.topBarTitle(navBackStackEntry),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    )
}

@Composable
fun Destination.topBarTitle(navBackStackEntry: NavBackStackEntry?): String {
    return when (this) {
        ChartsScreenDestination -> {
            val titleText = "Charts"
            val viewModel = hiltViewModel<SettingsViewModel>()

            val userState = viewModel.userUIState

            if (userState is UserUIState.Success) {
                "$titleText for $userState.user.name"
            } else {
                "$titleText is it working?"
            }
        }
        FriendsScreenDestination -> "Friends"
        SettingsScreenDestination -> "Settings"
        else -> "Scrobble View"
    }
}