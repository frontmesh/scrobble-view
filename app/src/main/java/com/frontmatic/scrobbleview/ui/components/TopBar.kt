package com.frontmatic.scrobbleview.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavBackStackEntry
import com.frontmatic.scrobbleview.ui.screens.destinations.ChartsScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.Destination
import com.frontmatic.scrobbleview.ui.screens.destinations.FriendsScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.SettingsScreenDestination
import com.frontmatic.scrobbleview.ui.screens.settings.SettingsScreen

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
        ChartsScreenDestination -> "Charts"
        FriendsScreenDestination -> "Friends"
        SettingsScreenDestination -> "Settings"
        else -> "Scrobble View"
    }
}