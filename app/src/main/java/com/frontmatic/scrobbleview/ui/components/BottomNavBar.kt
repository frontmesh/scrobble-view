package com.frontmatic.scrobbleview.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.frontmatic.scrobbleview.ui.screens.NavGraphs
import com.frontmatic.scrobbleview.ui.screens.destinations.ChartsScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.FriendsScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.isRouteOnBackStack

data class NavMenuItem(
    val icon: ImageVector,
    val iconSelected: ImageVector,
    val title: String,
    val direction: DirectionDestinationSpec
)


val bottomNavItems = listOf(
    NavMenuItem(
        title = "Charts",
        iconSelected = Icons.Filled.MusicNote,
        icon = Icons.Outlined.MusicNote,
        direction = ChartsScreenDestination
    ),
    NavMenuItem(
        title = "Friends",
        iconSelected = Icons.Filled.People,
        icon = Icons.Outlined.People,
        direction = FriendsScreenDestination
    ),
    NavMenuItem(
        title = "Settings",
        iconSelected = Icons.Filled.Settings,
        icon = Icons.Outlined.Settings,
        direction = SettingsScreenDestination
    )
)

@Composable
fun BottomNavBar(
    navController: NavController,
) {

    NavigationBar() {
        bottomNavItems.forEach { item ->
            val isCurrentDestOnBackStack = navController.isRouteOnBackStack(item.direction)
            val icon = if (isCurrentDestOnBackStack) item.iconSelected else item.icon
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = isCurrentDestOnBackStack,
                onClick = {
                    navController.navigate(item.direction) {
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}
