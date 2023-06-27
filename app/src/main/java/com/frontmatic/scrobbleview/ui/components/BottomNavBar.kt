package com.frontmatic.scrobbleview.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StackedLineChart
import androidx.compose.material.icons.outlined.Person3
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StackedLineChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
        NavMenuItem(title = "Charts", iconSelected = Icons.Filled.StackedLineChart, icon = Icons.Outlined.StackedLineChart),
        NavMenuItem(title = "Friends", iconSelected = Icons.Filled.Person3, icon = Icons.Outlined.Person3),
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
                onClick = { selectedItem = index },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}
