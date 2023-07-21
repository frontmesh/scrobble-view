package com.frontmatic.scrobbleview.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontmatic.scrobbleview.data.model.RecentTrack
import com.frontmatic.scrobbleview.data.model.Track

@Dao
interface RecentTrackDao {

    @Query("SELECT * FROM recent_tracks")
    fun getAllRecentTracks(): PagingSource<Int, RecentTrack>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecentTracks(recentTracks: List<RecentTrack>)

    @Query("DELETE FROM recent_tracks")
    fun deleteAll()
}