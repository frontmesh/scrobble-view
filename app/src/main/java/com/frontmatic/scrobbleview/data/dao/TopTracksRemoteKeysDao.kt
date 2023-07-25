package com.frontmatic.scrobbleview.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontmatic.scrobbleview.data.api.RequestPeriod
import com.frontmatic.scrobbleview.data.model.TopTracksRemoteKeys

@Dao
interface TopTracksRemoteKeysDao {
    @Query("SELECT * FROM top_tracks_remote_keys WHERE name = :name")
    suspend fun getRemoteKeys(name: String): TopTracksRemoteKeys?

    @Query("SELECT * FROM top_tracks_remote_keys WHERE name = :name AND period = :period")
    suspend fun getRemoteKeysByPeriod(name: String, period: RequestPeriod): TopTracksRemoteKeys?

    @Query("SELECT * FROM top_tracks_remote_keys ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getFirstRemoteKey(): TopTracksRemoteKeys?

    @Query("SELECT * FROM top_tracks_remote_keys WHERE period = :period ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getFirstRemoteKeyByPeriod(period: RequestPeriod): TopTracksRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(remoteKeys: List<TopTracksRemoteKeys>)

    @Query("DELETE FROM top_tracks_remote_keys")
    suspend fun deleteAll()

    @Query("DELETE FROM top_tracks_remote_keys WHERE period = :period")
    suspend fun deleteAllByPeriod(period: RequestPeriod)
}