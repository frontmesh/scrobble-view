package com.frontmatic.scrobbleview.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ToggleOff
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

data class NavMenuItem(val icon: ImageVector, val iconSelected: ImageVector, val title: String)

@Composable
fun BottomNavBar() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        NavMenuItem(title = "Home", iconSelected = Icons.Filled.Menu, icon = Icons.Outlined.Menu),
        NavMenuItem(title = "Options", iconSelected = Icons.Filled.ToggleOn, icon = Icons.Outlined.ToggleOff),
        NavMenuItem(title = "Settings", iconSelected = Icons.Filled.Settings, icon = Icons.Outlined.Settings)
    )
    NavigationBar() {
        items.forEachIndexed { index, item ->
            val selected = index == selectedItem
            val icon = if (selected) item.iconSelected else item.icon
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selected,
                onClick = { selectedItem = index }
            )
        }
    }
}
