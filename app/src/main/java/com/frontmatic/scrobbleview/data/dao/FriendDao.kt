package com.frontmatic.scrobbleview.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontmatic.scrobbleview.data.model.Friend

@Dao
interface FriendDao {
    @Query("SELECT * FROM friends")
    fun getAll(): PagingSource<Int, Friend>

    @Query("SELECT * FROM friends WHERE name = :name")
    fun getByName(name: String): Friend

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(friends: List<Friend>)

    @Query("DELETE FROM friends")
    suspend fun deleteAll()
}