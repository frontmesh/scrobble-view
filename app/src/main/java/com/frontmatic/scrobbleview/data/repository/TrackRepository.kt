package com.frontmatic.scrobbleview.data.repository

import com.frontmatic.scrobbleview.data.ScrobbleDatabase
import com.frontmatic.scrobbleview.data.api.LastFMApi
import com.frontmatic.scrobbleview.data.model.TrackInfo
import com.frontmatic.scrobbleview.data.model.response.TrackInfoResponse
import com.frontmatic.scrobbleview.data.source.DataStoreOperations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import javax.inject.Inject


sealed class TrackKey {
    data class Mbid(val mbid: String) : TrackKey()
    data class NameAndArtist(val track: String, val artist: String) : TrackKey()
}


class TrackRepository @Inject constructor(
    private val api: LastFMApi,
    private val dataStore: DataStoreOperations,
    database: ScrobbleDatabase,
) {
    private val trackInfoDao = database.trackInfoDao()

    private val trackFetcher  = Fetcher.of { key: TrackKey ->
        when (key) {
            is TrackKey.Mbid -> api.getTrackInfo(
            mbid = key.mbid,
            user = null,
            track = null,
            artist = null,
        )
            is TrackKey.NameAndArtist -> api.getTrackInfo(
                mbid = null,
                user = null,
                track = key.track,
                artist = key.artist
            )
        }

    }

    private val trackSourceOfTruth = SourceOfTruth.of(
        reader = { mbid: String ->
            trackInfoDao.getOne(mbid)
        },
        writer = { _: Any, trackInfo: TrackInfo ->
            trackInfoDao.addOne(trackInfo)
        },
        delete = trackInfoDao::deleteOne,
        deleteAll = trackInfoDao::deleteAll,
    )


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val trackInfoStore = StoreBuilder
        .from<TrackKey, TrackInfoResponse>(
            fetcher = trackFetcher,
//            sourceOfTruth = trackSourceOfTruth
        ).build()


    fun getTrackInfoByArtistAndTrack(artist: String, track: String): Flow<TrackInfo> = flow {
        trackInfoStore.stream(StoreReadRequest.fresh(TrackKey.NameAndArtist(track, artist)))
            .firstOrNull { it is StoreReadResponse.Data }
            .let { (it as StoreReadResponse.Data).value }
    }

}