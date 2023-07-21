package com.frontmatic.scrobbleview.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontmatic.scrobbleview.data.model.Friend

@Dao
interface FriendDao  {
    @Query("SELECT * FROM friends")
    fun getAllFriends(): PagingSource<Int, Friend>

    @Query("SELECT * FROM friends WHERE name = :name")
    fun getSelectedFriend(name: String): Friend

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFriends(friends: List<Friend>)

    @Query("DELETE FROM friends")
    suspend fun deleteAllFriends()
}