package com.frontmatic.scrobbleview.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.frontmatic.scrobbleview.R
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
    val settingsViewModel = hiltViewModel<SettingsViewModel>()

    LaunchedEffect(true) {
        settingsViewModel.getUserInfo()
    }
    val userState = settingsViewModel.userUIState

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (userState is UserUIState.Success && destination != SettingsScreenDestination) {
                    val user = userState.user
                    AsyncImage(
                        model = user.largeImage,
                        error = painterResource(R.drawable.ic_headphones),
                        contentDescription = stringResource(R.string.friend_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                }
                Text(
                    text = destination.topBarTitle(navBackStackEntry, settingsViewModel),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    )
}

@Composable
fun Destination.topBarTitle(navBackStackEntry: NavBackStackEntry?, settingsViewModel: SettingsViewModel): String {
    return when (this) {
        ChartsScreenDestination -> "Charts"
        FriendsScreenDestination -> "Friends"
        SettingsScreenDestination -> "Settings"
        else -> "Scrobble View"
    }
}