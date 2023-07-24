package com.frontmatic.scrobbleview.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontmatic.scrobbleview.data.model.TopTrack

@Dao
interface TopTrackDao {
    @Query("SELECT * FROM top_tracks")
    fun getAll(): PagingSource<Int, TopTrack>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(friends: List<TopTrack>)
    @Query("DELETE FROM top_tracks")
    fun deleteAll()
}