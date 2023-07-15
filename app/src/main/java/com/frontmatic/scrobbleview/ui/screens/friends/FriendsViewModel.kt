package com.frontmatic.scrobbleview.ui.screens.friends

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.frontmatic.scrobbleview.data.UseCases
import com.frontmatic.scrobbleview.data.model.Friend
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val useCases: UseCases,
): ViewModel() {
    val friends: Flow<PagingData<Friend>> = useCases.getAllFriends()
}