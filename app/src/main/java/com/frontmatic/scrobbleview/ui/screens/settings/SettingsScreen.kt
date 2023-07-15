package com.frontmatic.scrobbleview.ui.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.frontmatic.scrobbleview.BuildConfig
import com.frontmatic.scrobbleview.R
import com.frontmatic.scrobbleview.data.model.User
import com.frontmatic.scrobbleview.ui.screens.destinations.SetupScreenDestination
import com.frontmatic.scrobbleview.ui.theme.INFO_ICON_SIZE
import com.frontmatic.scrobbleview.ui.theme.PAGE_PADDING
import com.frontmatic.scrobbleview.util.formatNumberWithSeparator
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun SettingsScreen(
    model: SettingsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PAGE_PADDING)
    ) {

        LaunchedEffect(key1 = model.userUIState) {
            model.getUserInfo()
        }

        if (model.userUIState is UserUIState.Success) {
            val user = (model.userUIState as UserUIState.Success).user
            SettingsUserImage(user, onUserChange = {
                navigator.navigate(SetupScreenDestination)
            })
            SettingsUserInfo(user)
        }

        AppVersion();
    }
}

@Composable
fun SettingsUserImage(user: User, onUserChange: () -> Unit) {
    Row (
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Column(modifier = Modifier
            .fillMaxWidth(0.2f)
            .padding(start = 8.dp)) {
            AsyncImage(
                model = user.largeImage,
                error = painterResource(R.drawable.ic_headphones),
                contentDescription = stringResource(R.string.friend_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
                ) {
            Column(
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.bodyLarge
                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = user.realname,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = user.country,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Button(
                    modifier = Modifier
                        .alpha(0.8f),
                    onClick = onUserChange,
                ) {
                    Text(text = "Change user")
                }
            }
        }
    }
}

@Composable
fun SettingsUserInfo(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PAGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
    ) {
        InfoBox(
            icon = painterResource(id = R.drawable.ic_splash_logo),
            iconColor =  MaterialTheme.colorScheme.primary,
            bigText = "Scrobbles",
            smallText = formatNumberWithSeparator(user.playCount),
            textColor = MaterialTheme.colorScheme.onBackground,
        )

        InfoBox(
            icon = painterResource(id = R.drawable.ic_mic),
            iconColor =  MaterialTheme.colorScheme.primary,
            bigText = "Artists",
            smallText = formatNumberWithSeparator(user.artistCount),
            textColor = MaterialTheme.colorScheme.onBackground,
        )
    }
}


@Composable
fun InfoBox(
    icon: Painter,
    iconColor: Color,
    bigText: String,
    smallText: String,
    textColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .size(INFO_ICON_SIZE)
                .padding(end = 8.dp)
        )
        Column {
            Text(
                text = bigText,
                color = textColor,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Black
            )
            Text(
                modifier = Modifier.alpha(0.5f),
                text = smallText,
                color = textColor,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun AppVersion() {

    // on below line we are creating a column
    Column(
        // on below line we are adding a modifier to it
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.app_name),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
        )

        val version =
            "Version: " + BuildConfig.VERSION_NAME

        Text(
            text = version,
            style = MaterialTheme.typography.bodyLarge,
        )

    }

}


@Preview(showBackground = true)
@Composable
fun PreviewInfoBox() {
    InfoBox(
        icon = painterResource(id = R.drawable.ic_splash_logo),
        iconColor = MaterialTheme.colorScheme.tertiary,
        bigText = "Scrobbles",
        smallText = "20,000",
        textColor = MaterialTheme.colorScheme.onSurface,
    )
}
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewDarkInfoBox() {
    InfoBox(
        icon = painterResource(id = R.drawable.ic_splash_logo),
        iconColor = MaterialTheme.colorScheme.tertiary,
        bigText = "Scrobbles",
        smallText = "20,000",
        textColor = MaterialTheme.colorScheme.onSurface,
    )
}