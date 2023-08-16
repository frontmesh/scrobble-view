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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.frontmatic.scrobbleview.R
import com.frontmatic.scrobbleview.ui.components.ListItem
import com.frontmatic.scrobbleview.ui.theme.PAGE_PADDING
import com.frontmatic.scrobbleview.util.handlePagingResult
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.frontmatic.scrobbleview.ui.screens.destinations.TrackDetailScreenDestination
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecentTab(
    navigator: DestinationsNavigator,
    recentTabViewModel: RecentTabViewModel = hiltViewModel(),
) {
    val recentTracks = recentTabViewModel.recentTracks.collectAsLazyPagingItems()
    val result = handlePagingResult(collection = recentTracks)
    var refreshing by recentTabViewModel.isRefreshing
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            recentTracks.refresh()
        }
    )

    if (result) {
        Box(Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(
                contentPadding = PaddingValues(PAGE_PADDING),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(count = recentTracks.itemCount) { index ->
                    val track = recentTracks[index]!!
                    ListItem(
                        imageUrl = track.largeImage,
                        imageDescription = track.name + " by " + track.artist.name,
                        imagePlaceholder = R.drawable.ic_headphones,
                        mainText = track.name,
                        secondaryText = track.artist.name,
                        trailingText = if (track.date != null) fromSecondsSinceEpoch(track.date.uts) else "Now playing",
                        trailingTextBold = track.date == null,
                        onClick = {
                            navigator.navigate(
                                TrackDetailScreenDestination(
                                    track.artist.name,
                                    track.name,
                                )
                            )
                        })
                }
            }
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}


fun fromSecondsSinceEpoch(timestamp: Long): String {
    return Date(timestamp * 1000).toRelativeTime()
}

fun Date.toRelativeTime(): String {
    val currentTimeMillis = System.currentTimeMillis()
    val timeDiffMillis = currentTimeMillis - this.time

    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeDiffMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiffMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(timeDiffMillis)
    val days = TimeUnit.MILLISECONDS.toDays(timeDiffMillis)

    return when {
        seconds < 60 -> "$seconds seconds ago"
        minutes < 60 -> "$minutes minutes ago"
        hours < 24 -> "$hours hours ago"
        days < 2 -> "yesterday"
        days < 7 -> "$days days ago"
        else -> SimpleDateFormat("dd-MM-yyyy").format(this) // For dates older than 1 week, format as yyyy-MM-dd
    }
}