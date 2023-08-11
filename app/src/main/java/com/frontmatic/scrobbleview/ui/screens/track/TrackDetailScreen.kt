package com.frontmatic.scrobbleview.ui.screens.track

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@Destination(
    route = "track_detail",
    style = DestinationStyleBottomSheet::class
)
@Composable
fun TrackDetailScreen(
    mbid: String,
    viewModel: TrackDetailViewModel = hiltViewModel(),
) {

    Text(text = "TrackDetailScreen")
}