package com.frontmatic.scrobbleview.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontmatic.scrobbleview.data.model.FriendRemoteKeys

@Dao
interface FriendRemoteKeysDao {
    @Query("SELECT * FROM friend_remote_keys WHERE name = :name")
    suspend fun getRemoteKeys(name: String): FriendRemoteKeys?

    @Query("SELECT * FROM friend_remote_keys ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getFirstRemoteKey(): FriendRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(remoteKeys: List<FriendRemoteKeys>)

    @Query("DELETE FROM friend_remote_keys")
    suspend fun deleteAll()
}