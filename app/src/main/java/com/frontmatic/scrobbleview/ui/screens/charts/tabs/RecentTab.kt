package com.frontmatic.scrobbleview.ui.screens.charts.tabs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.frontmatic.scrobbleview.R
import com.frontmatic.scrobbleview.ui.components.ListItem
import com.frontmatic.scrobbleview.ui.theme.PAGE_PADDING
import com.frontmatic.scrobbleview.util.handlePagingResult

@Composable
fun RecentTab(
    recentTabViewModel: RecentTabViewModel = hiltViewModel(),
) {
    val recentTracks = recentTabViewModel.recentTracks.collectAsLazyPagingItems()

    val result = handlePagingResult(collection = recentTracks)

    if (result) {
        LazyColumn(contentPadding = PaddingValues(PAGE_PADDING)) {
            items(count = recentTracks.itemCount) { index ->
                val track = recentTracks[index]!!
                ListItem(
                    imageUrl = track.largeImage,
                    imageDescription = track.name + " by " + track.artist.name,
                    imagePlaceholder = R.drawable.ic_headphones,
                    mainText = track.name,
                    secondaryText = track.artist.name,
                    onClick = { /*TODO*/ })
            }
        }
    }
}

