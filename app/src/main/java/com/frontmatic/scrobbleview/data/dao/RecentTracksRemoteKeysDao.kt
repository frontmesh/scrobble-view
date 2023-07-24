package com.frontmatic.scrobbleview.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontmatic.scrobbleview.data.model.RecentTracksRemoteKeys

@Dao
interface RecentTracksRemoteKeysDao {
    @Query("SELECT * FROM recent_tracks_remote_keys WHERE name = :name")
    suspend fun getRemoteKeys(name: String): RecentTracksRemoteKeys?

    @Query("SELECT * FROM recent_tracks_remote_keys ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getFirstRemoteKey(): RecentTracksRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(remoteKeys: List<RecentTracksRemoteKeys>)

    @Query("DELETE FROM recent_tracks_remote_keys")
    suspend fun deleteAll()
}