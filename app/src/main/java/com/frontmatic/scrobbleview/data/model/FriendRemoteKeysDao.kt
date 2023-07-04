package com.frontmatic.scrobbleview.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FriendRemoteKeysDao {
    @Query("SELECT * FROM friend_remote_keys WHERE name = :name")
    suspend fun getRemoteKeys(name: String): FriendRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(remoteKeys: List<FriendRemoteKeys>)

    @Query("DELETE FROM friend_remote_keys")
    suspend fun deleteAllRemoteKeys()
}