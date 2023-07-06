package com.frontmatic.scrobbleview.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.frontmatic.scrobbleview.R
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(error: LoadState.Error? = null) {

    val messsage by remember {
        mutableStateOf(error?.let { parseErrorMessage(error = it) })
    }

    val icon by remember {
        mutableStateOf(R.drawable.network_error)
    }

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(2000)
    )

    LaunchedEffect(key1 = true, block = {
        startAnimation = true
    })

    messsage?.let { EmptyContent(alphaAnim = alphaAnim, icon = icon, messsage = it) }
}

@Composable
fun EmptyContent(alphaAnim: Float = 1f, icon: Int, messsage: String) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .alpha(alphaAnim)
                .size(120.dp),
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.network_error_icon),
            tint = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface
        )
        Text(
            modifier = Modifier
                .alpha(alphaAnim)
                .padding(top = 16.dp),
            text = messsage,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface
        )
    }
}

fun parseErrorMessage(error: LoadState.Error): String {
    return when (error.error) {
        is SocketTimeoutException -> "Server unavailable."
        is ConnectException -> "Internet unavailable."
        else -> "Unknown error."
    }
}


@Composable
@Preview
fun EmptyScreenPreview() {
    EmptyScreen()
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun EmptyScreenPreviewDark() {
    EmptyScreen()
}

@Composable
@Preview(showBackground = true)
fun EmptyContentPreview() {
    EmptyContent(icon = R.drawable.network_error, messsage = "Internet unavailable.")
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun EmptyContentPreviewDark() {
    EmptyContent(icon = R.drawable.network_error, messsage = "Internet unavailable.")
}