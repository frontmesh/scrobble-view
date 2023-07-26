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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.frontmatic.scrobbleview.R
import com.frontmatic.scrobbleview.data.api.RequestPeriod
import com.frontmatic.scrobbleview.data.model.TopTrack
import com.frontmatic.scrobbleview.ui.components.ListItem
import com.frontmatic.scrobbleview.ui.theme.PAGE_PADDING
import com.frontmatic.scrobbleview.util.handlePagingResult
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopTrackTab(
    tracks: Flow<PagingData<TopTrack>>,
    isRefreshing: Boolean,
) {

    val topTracks = tracks.collectAsLazyPagingItems()
    val result = handlePagingResult(collection = topTracks)
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
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
            PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}