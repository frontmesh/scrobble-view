package com.frontmatic.scrobbleview.ui.screens.settings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frontmatic.scrobbleview.R
import com.frontmatic.scrobbleview.ui.theme.PAGE_PADDING
import com.frontmatic.scrobbleview.util.getThemedBackgroundModifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = "setup",
)
@Composable
fun SetupScreen(
    navigator: DestinationsNavigator,
) {

    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 100,
        )
    )

    LaunchedEffect(true) {
        startAnimation = true
    }

    Setup(alphaAnim = alphaAnim)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setup(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    alphaAnim: Float = 1f,
    onButtonClick: (String) -> Unit = {}
) {

    var username by settingsViewModel.searchUsername
    val setupModifer = getThemedBackgroundModifier()

    Column(
        modifier = setupModifer
            .fillMaxSize()
            .padding(PAGE_PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.alpha(alphaAnim),
            text = stringResource(R.string.enter_username),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            TextField(
                modifier = Modifier.alpha(alphaAnim),
                value = username,
                onValueChange = { value ->
                    settingsViewModel.updateSearchUsername(value)
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_splash_logo),
                        contentDescription = stringResource(R.string.app_logo),
                        tint = Color.Black
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                modifier = Modifier
                    .alpha(
                        when {
                            alphaAnim > 0.5f &&
                                    username.isEmpty() -> 0.5f

                            else -> alphaAnim
                        }
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .size(56.dp)
                ,
                onClick = {
                    onButtonClick(username)
                    settingsViewModel.checkLastFMUser()
                }, enabled = username.isNotEmpty()
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(R.string.app_logo),
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
@Preview
fun SetupScreenPreview() {
    SetupScreen(navigator = EmptyDestinationsNavigator)
}

@Composable
@Preview
fun SetupPreview() {
    Setup()
}