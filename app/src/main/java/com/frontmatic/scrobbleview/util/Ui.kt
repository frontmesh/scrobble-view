package com.frontmatic.scrobbleview.util

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.frontmatic.scrobbleview.ui.theme.Orange80
import com.frontmatic.scrobbleview.ui.theme.Red80

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.frontmatic.scrobbleview.data.model.Friend
import com.frontmatic.scrobbleview.ui.components.EmptyScreen
import com.frontmatic.scrobbleview.ui.components.ShimmerEffect

@Composable
fun getThemedBackgroundModifier(): Modifier {
    return if (isSystemInDarkTheme()) Modifier.background(
        Color.Black
    ) else Modifier.background(
        Brush.verticalGradient(
            listOf(Orange80, Red80)
        )
    )
}

fun Context.sendMail(
    to: String,
    subject: String,
    onError: () -> Unit
) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)

        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        onError.invoke()
    } catch (t: Throwable) {
        onError.invoke()
    }
}

fun Context.hasNotificationPermission(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val result = ContextCompat.checkSelfPermission(this, permission)

        return result == PackageManager.PERMISSION_GRANTED
    }

    return true
}


@Composable
fun <T: Any> handlePagingResult(collection: LazyPagingItems<T>): Boolean {
    collection.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(error = error)
                false
            }
            else -> true
        }
    }
}