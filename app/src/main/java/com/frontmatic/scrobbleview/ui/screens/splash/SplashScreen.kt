package com.frontmatic.scrobbleview.ui.screens.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.scrobbleview.R
import com.frontmatic.scrobbleview.ui.screens.destinations.ChartsScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.SetupScreenDestination
import com.frontmatic.scrobbleview.ui.screens.destinations.SplashScreenDestination
import com.frontmatic.scrobbleview.util.getThemedBackgroundModifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.delay

@RootNavGraph(start = true)
@Destination(
    route = "splash",
    start = true
)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val usernameSet by splashViewModel.usernameSet.collectAsState()

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val scaleValue by animateFloatAsState(
        targetValue = if (startAnimation) 0.7f else 0f,
        animationSpec = tween(
            durationMillis = 1500,
            delayMillis = 50,
            easing = {
                OvershootInterpolator(2.5f).getInterpolation(it)
            }
        )
    )

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1500,
            delayMillis = 100,
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1500)

        val destination = if (usernameSet) {
            ChartsScreenDestination
        } else {
            SetupScreenDestination
        }

        navigator.navigate(destination) {
            popUpTo(SplashScreenDestination.route) {
                inclusive = true
            }
        }
    }

    Splash(scaleValue = scaleValue, alphaAnim = alphaAnim)
}


@Composable
fun Splash(scaleValue: Float = 0.2f, alphaAnim: Float = 1f) {

    val splashModifier = getThemedBackgroundModifier()

    Box(
        modifier = splashModifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(scaleValue)
                .alpha(alphaAnim),
            painter = painterResource(id = R.drawable.ic_splash_logo),
            contentDescription = stringResource(R.string.app_logo),
            tint = Color.White
        )
    }
}


@Preview
@Composable
fun SplashPreview() {
    Splash()
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashDarkPreview() {
    Splash()
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenPreview() {
    SplashScreen(navigator = EmptyDestinationsNavigator)
}