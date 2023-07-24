package com.frontmatic.scrobbleview.ui.screens.charts.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.frontmatic.scrobbleview.R
import com.frontmatic.scrobbleview.ui.components.ListItem
import com.frontmatic.scrobbleview.ui.theme.PAGE_PADDING
import com.frontmatic.scrobbleview.util.handlePagingResult

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SevenDaysTab(
    sevenDaysTabViewModel: SevenDaysTabViewModel = hiltViewModel(),
) {

    val topTracks = sevenDaysTabViewModel.topTracks.collectAsLazyPagingItems()
    val refreshing by sevenDaysTabViewModel.isRefreshing
    val result = handlePagingResult(collection = topTracks)
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            topTracks.refresh()
        }
    )

    if (result) {
        Box(Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(
                contentPadding = PaddingValues(PAGE_PADDING),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(count = topTracks.itemCount) { index ->
                    val track = topTracks[index]!!
                    ListItem(
                        imageUrl = track.largeImage,
                        imageDescription = track.name + " by " + track.artist.name,
                        imagePlaceholder = R.drawable.ic_headphones,
                        mainText = track.name,
                        secondaryText = track.artist.name,
                        trailingText = "${track.playcount.toString()} plays",
                        onClick = { /*TODO*/ })
                }
            }
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}