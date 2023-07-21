package com.frontmatic.scrobbleview.ui.screens.friends

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.frontmatic.scrobbleview.R
import com.frontmatic.scrobbleview.ui.components.ListItem
import com.frontmatic.scrobbleview.ui.theme.PAGE_PADDING
import com.frontmatic.scrobbleview.util.handlePagingResult
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = "friends"
)
@Composable
fun FriendsScreen(
    friendsViewModel: FriendsViewModel = hiltViewModel(),
) {

    val friends = friendsViewModel.friends.collectAsLazyPagingItems()

    val result = handlePagingResult(collection = friends)

    if (result) {
        val handler = LocalUriHandler.current
        LazyColumn(contentPadding = PaddingValues(PAGE_PADDING)) {
            items(count = friends.itemCount) { index ->
                val friend = friends[index]!!
                ListItem(
                    imageUrl = friend.largeImage,
                    imageDescription = friend.name + " by " + friend.realname ,
                    imagePlaceholder = R.drawable.ic_headphones,
                    mainText = friend.name,
                    secondaryText = friend.realname,
                    onClick = {  handler.openUri(friend.url) }
                )
            }

        }
    }
}
