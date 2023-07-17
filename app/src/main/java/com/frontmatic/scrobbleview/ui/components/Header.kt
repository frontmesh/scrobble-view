package com.frontmatic.scrobbleview.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.frontmatic.scrobbleview.ui.theme.ScrobbleViewTheme

@Composable
fun Header(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview
@Composable
private fun HeaderPreview() {
    ScrobbleViewTheme() {
        Header(text = "User")
    }
}