package com.frontmatic.scrobbleview.ui.screens.track

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@Destination(
    navArgsDelegate = TrackScreenNavArgs::class,
//    style = DestinationStyleBottomSheet::class
)
@Composable
fun TrackDetailScreen(
    viewModel: TrackDetailViewModel = hiltViewModel(),

) {
    val track by viewModel.trackInfo.collectAsState(initial = null)

    if (track != null) {
        Text(
            text = "TrackDetailScreen",
            modifier = Modifier.fillMaxSize()
        )

        Text(text = track!!.name)
    }

    Text(
        text = "TrackDetailScreen",
        modifier = Modifier.fillMaxSize()
    )
}