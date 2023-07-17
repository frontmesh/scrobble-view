package com.frontmatic.scrobbleview.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.frontmatic.scrobbleview.ui.screens.appCurrentDestinationAsState
import com.frontmatic.scrobbleview.ui.screens.destinations.Destination
import com.frontmatic.scrobbleview.ui.screens.startAppDestination
import com.ramcosta.composedestinations.spec.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScaffold(
    startRoute: Route,
    navController : NavController,
    topBar: @Composable (Destination, NavBackStackEntry?) -> Unit,
    bottomBar: @Composable (Destination) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry = navController.currentBackStackEntry
    val destination = navController.appCurrentDestinationAsState().value
        ?: startRoute.startAppDestination

    Scaffold(
        topBar = { topBar(destination, navBackStackEntry) },
        bottomBar = { bottomBar(destination) },
    )
    {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            content(it)
        }
    }
}