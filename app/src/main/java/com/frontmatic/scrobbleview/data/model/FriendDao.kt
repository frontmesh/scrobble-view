package com.frontmatic.scrobbleview.data.model

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FriendDao {

    @Query("SELECT * FROM friends ORDER BY name ASC")
    fun getAllFriends(): PagingSource<Int, Friend>

    @Query("SELECT * FROM friends WHERE name = :name")
    fun getSelectedFriend(name: String): Friend

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFriends(friends: List<Friend>)

    @Query("DELETE FROM friends")
    suspend fun deleteAllFriends()
}