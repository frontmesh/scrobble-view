package com.frontmatic.scrobbleview.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontmatic.scrobbleview.data.model.TrackInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackInfoDao {
    @Query("SELECT * FROM track_info WHERE name = :name")
    fun getOne(name: String): Flow<TrackInfo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOne(trackInfo: TrackInfo)

    @Query("DELETE FROM track_info WHERE name = :name")
    fun deleteOne(name: String)

    @Query("DELETE FROM track_info")
    fun deleteAll()
}