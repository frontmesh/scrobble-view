package com.frontmatic.scrobbleview.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.frontmatic.scrobbleview.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scrobble View") },
            )
        },
        bottomBar = {
            BottomNavBar()
        }

    ) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {

        }
    }
}