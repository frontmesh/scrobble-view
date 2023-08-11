package com.frontmatic.scrobbleview

//import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.plusAssign
import com.frontmatic.scrobbleview.ui.components.BottomNavBar
import com.frontmatic.scrobbleview.ui.components.RootScaffold
import com.frontmatic.scrobbleview.ui.components.TopBar
import com.frontmatic.scrobbleview.ui.screens.NavGraphs
import com.frontmatic.scrobbleview.ui.screens.destinations.Destination
import com.frontmatic.scrobbleview.ui.screens.destinations.SetupScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.SplashScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.TrackDetailScreenDestination
import com.frontmatic.scrobbleview.ui.theme.ScrobbleViewTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
        ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val engine = rememberAnimatedNavHostEngine()
            val navController = engine.rememberNavController()
            val bottomSheetNavigator = rememberBottomSheetNavigator()
//            val sheetState = rememberModalBottomSheetState()
//            val bottomSheetNavigator = remember { BottomSheetNavigator() }
            navController.navigatorProvider += bottomSheetNavigator
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
                )
                    {

                        ModalBottomSheetLayout(
                            bottomSheetNavigator = bottomSheetNavigator,
                            sheetShape = RoundedCornerShape(16.dp),
                        ) {
                            DestinationsNavHost(
                                navController = navController,
                                engine = engine,
                                navGraph = NavGraphs.root
                            )
                        }
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
        is TrackDetailScreenDestination -> false
        else -> true
    }