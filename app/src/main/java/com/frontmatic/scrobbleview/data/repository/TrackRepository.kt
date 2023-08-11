package com.frontmatic.scrobbleview.data.repository

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import com.frontmatic.scrobbleview.data.ScrobbleDatabase
import com.frontmatic.scrobbleview.data.api.LastFMApi
import com.frontmatic.scrobbleview.data.model.TrackInfo
import com.frontmatic.scrobbleview.data.source.DataStoreOperations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val api: LastFMApi,
    private val dataStore: DataStoreOperations,
    database: ScrobbleDatabase,
    dispatcher: CoroutineDispatcher
) {
    private val trackInfoDao = database.trackInfoDao()

    private val trackFetcher  = Fetcher.of { mbid: String ->
        api.getTrackInfo(
            mbid = mbid,
            user = null,
            track = null,
            artist = null,
        )
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
        .from(
            fetcher = trackFetcher,
//            sourceOfTruth = trackSourceOfTruth
        ).build()
//
//    val trackInfoStore = StoreBuilder
//        .from(
//            fetcher = trackFetcher,
//            sourceOfTruth = trackSourceOfTruth
//        ).build()

}