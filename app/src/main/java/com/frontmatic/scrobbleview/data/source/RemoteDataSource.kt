package com.frontmatic.scrobbleview.data.source

import androidx.paging.PagingData
import com.frontmatic.scrobbleview.data.api.RequestPeriod
import com.frontmatic.scrobbleview.data.model.Friend
import com.frontmatic.scrobbleview.data.model.RecentTrack
import com.frontmatic.scrobbleview.data.model.TopTrack
import com.frontmatic.scrobbleview.data.model.Track
import com.frontmatic.scrobbleview.data.model.response.TrackInfoResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllFriends(): Flow<PagingData<Friend>>
    fun getAllRecentTracks(): Flow<PagingData<RecentTrack>>
    fun getAllTopTracks(period: RequestPeriod): Flow<PagingData<TopTrack>>
}