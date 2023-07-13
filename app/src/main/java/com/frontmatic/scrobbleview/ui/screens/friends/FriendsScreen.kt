package com.frontmatic.scrobbleview.ui.screens.friends

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.frontmatic.scrobbleview.R
import com.frontmatic.scrobbleview.data.model.Friend
import com.frontmatic.scrobbleview.data.model.Image
import com.frontmatic.scrobbleview.ui.components.EmptyScreen
import com.frontmatic.scrobbleview.ui.components.ShimmerEffect
import com.frontmatic.scrobbleview.ui.theme.PAGE_PADDING


@Composable
fun FriendItem (
    friend: Friend
) {
    val handler = LocalUriHandler.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .fillMaxHeight(0.1f)
            .background(color = MaterialTheme.colorScheme.background)
            .clickable {
                handler.openUri(friend.url)
            }
    ) {
        Row (modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.fillMaxWidth(0.15f)) {
                AsyncImage(
                    model = friend.largeImage,
                    error = painterResource(R.drawable.ic_headphones),
                    contentDescription = stringResource(R.string.friend_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = friend.name,
                    style = MaterialTheme.typography.bodyLarge
                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = friend.realname,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun FriendsScreen(
    friendsViewModel: FriendsViewModel = hiltViewModel()
) {
    val friends = friendsViewModel.friends.collectAsLazyPagingItems()

    val result = handlePagingResult(friends = friends)

    if (result) {
        LazyColumn(contentPadding = PaddingValues(PAGE_PADDING)) {
            items(count = friends.itemCount) { index ->
                FriendItem(friend = friends[index]!!)
            }
        }
    }
}

@Composable
fun handlePagingResult(friends: LazyPagingItems<Friend>): Boolean {
    friends.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(error = error)
                false
            }
            else -> true
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FriendItemPreview() {
    FriendItem(friend = Friend(
        name = "Jon",
        country= "Netherlands",
        realname= "Jon Doe",
        url= "https://www.last.fm/user/JohnDoe",
        image = listOf(
                Image(
                    size = "small",
                    url =
                    "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
                ),
            )
        )
    )
}