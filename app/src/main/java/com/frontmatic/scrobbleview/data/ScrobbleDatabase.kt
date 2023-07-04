package com.frontmatic.scrobbleview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.frontmatic.scrobbleview.data.model.Friend
import com.frontmatic.scrobbleview.data.model.FriendDao
import com.frontmatic.scrobbleview.data.model.FriendRemoteKeys
import com.frontmatic.scrobbleview.data.model.FriendRemoteKeysDao

@Database(entities = [Friend::class, FriendRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class ScrobbleDatabase: RoomDatabase() {
    abstract fun friendDao(): FriendDao
    abstract fun friendRemoteKeysDao(): FriendRemoteKeysDao
}