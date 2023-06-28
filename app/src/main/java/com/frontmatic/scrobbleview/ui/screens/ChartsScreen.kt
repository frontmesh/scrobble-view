package com.frontmatic.scrobbleview.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.frontmatic.scrobbleview.ui.components.TabRow
import com.frontmatic.scrobbleview.ui.components.TabTitle


@Composable
fun ChartsScreen() {
    var selectedTabPosition by remember { mutableStateOf(0) }

    val items = listOf(
        "Recent", "Seven Days", "One Month", "Three Months", "Six Months", "One Year", "Overall"
    )

    Column(
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .horizontalScroll(rememberScrollState())
        ) {
            TabRow(
                selectedTabPosition = selectedTabPosition
            ) {
                items.forEachIndexed { index, s ->
                    TabTitle(
                        s,
                        position = index,
                        color = if (selectedTabPosition == index)
                            MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary,
                    ) { selectedTabPosition = index }
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = items[selectedTabPosition],
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}
