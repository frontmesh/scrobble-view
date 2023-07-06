package com.frontmatic.scrobbleview.util

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.frontmatic.scrobbleview.ui.theme.Orange80
import com.frontmatic.scrobbleview.ui.theme.Red80

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

