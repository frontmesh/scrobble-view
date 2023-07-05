package com.frontmatic.scrobbleview.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.frontmatic.scrobbleview.ui.components.BottomNavBar
import com.frontmatic.scrobbleview.ui.screens.friends.FriendsScreen
import com.ramcosta.composedestinations.annotation.Destination


@Destination(
    route = "home"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var selectedItem by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when(selectedItem) {
                            0 -> "Scrobble View"
                            1 -> "Friends"
                            2 -> "Settings"
                            else -> "Scrobble View"
                        },
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                },
            )
        },
        bottomBar = {
            BottomNavBar(
                onNavigationItemClick = { selectedItem = it },
            )
        }

    ) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            when (selectedItem) {
                0 -> ChartsScreen()
                1 -> FriendsScreen()
                2 -> SettingsScreen()
            }
        }
    }
}