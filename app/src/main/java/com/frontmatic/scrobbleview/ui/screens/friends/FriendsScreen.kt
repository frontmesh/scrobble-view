package com.frontmatic.scrobbleview.ui.screens.friends

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.frontmatic.scrobbleview.R
import com.frontmatic.scrobbleview.ui.components.ListItem
import com.frontmatic.scrobbleview.ui.theme.PAGE_PADDING
import com.frontmatic.scrobbleview.util.handlePagingResult
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterialApi::class)
@Destination(
    route = "friends"
)
@Composable
fun FriendsScreen(
    friendsViewModel: FriendsViewModel = hiltViewModel(),
) {

    val friends = friendsViewModel.friends.collectAsLazyPagingItems()
    var refreshing by friendsViewModel.isRefreshing

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            friends.refresh()
        }
    )

    val result = handlePagingResult(collection = friends)

    if (result) {
        val handler = LocalUriHandler.current
        Box(Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(contentPadding = PaddingValues(PAGE_PADDING)) {
                items(count = friends.itemCount) { index ->
                    val friend = friends[index]!!
                    ListItem(
                        imageUrl = friend.largeImage,
                        imageDescription = friend.name + " by " + friend.realname,
                        imagePlaceholder = R.drawable.ic_headphones,
                        mainText = friend.name,
                        secondaryText = friend.realname,
                        onClick = { handler.openUri(friend.url) }
                    )
                }
            }
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}
