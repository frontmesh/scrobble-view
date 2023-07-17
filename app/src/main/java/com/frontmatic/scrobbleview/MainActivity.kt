package com.frontmatic.scrobbleview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.frontmatic.scrobbleview.ui.components.BottomNavBar
import com.frontmatic.scrobbleview.ui.components.RootScaffold
import com.frontmatic.scrobbleview.ui.components.TopBar
import com.frontmatic.scrobbleview.ui.screens.NavGraphs
import com.frontmatic.scrobbleview.ui.screens.destinations.Destination
import com.frontmatic.scrobbleview.ui.screens.destinations.SetupScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.SplashScreenDestination
import com.frontmatic.scrobbleview.ui.theme.ScrobbleViewTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val startRoute = NavGraphs.root.startRoute

            ScrobbleViewTheme {
                // A surface container using the 'background' color from the theme
                RootScaffold(
                    navController = navController,
                    startRoute = startRoute,
                    topBar = { destination, navBackStackEntry ->
                        if (destination.shouldShowScaffoldElements) {
                            TopBar(destination = destination, navBackStackEntry = navBackStackEntry)
                        }
                    },
                    bottomBar = { destination ->
                        if (destination.shouldShowScaffoldElements) {
                            BottomNavBar(navController = navController)
                        }
                    },

                    ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                        DestinationsNavHost(
                            navController = navController,
                            navGraph = NavGraphs.root
                        )
                    }
                }
            }
        }
    }
}

private val Destination.shouldShowScaffoldElements get() =
    when (this) {
        is SplashScreenDestination -> false
        is SetupScreenDestination -> false
        else -> true
    }