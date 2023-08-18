package com.frontmatic.scrobbleview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.frontmatic.scrobbleview.data.model.Friend
import com.frontmatic.scrobbleview.data.dao.FriendDao
import com.frontmatic.scrobbleview.data.model.FriendRemoteKeys
import com.frontmatic.scrobbleview.data.dao.FriendRemoteKeysDao
import com.frontmatic.scrobbleview.data.dao.RecentTrackDao
import com.frontmatic.scrobbleview.data.dao.RecentTracksRemoteKeysDao
import com.frontmatic.scrobbleview.data.dao.TopTrackDao
import com.frontmatic.scrobbleview.data.dao.TopTracksRemoteKeysDao
import com.frontmatic.scrobbleview.data.dao.TrackInfoDao
import com.frontmatic.scrobbleview.data.dao.UserDao
import com.frontmatic.scrobbleview.data.model.RecentTrack
import com.frontmatic.scrobbleview.data.model.RecentTracksRemoteKeys
import com.frontmatic.scrobbleview.data.model.TopTrack
import com.frontmatic.scrobbleview.data.model.TopTracksRemoteKeys
import com.frontmatic.scrobbleview.data.model.TrackInfo
import com.frontmatic.scrobbleview.data.model.User

@Database(
    entities = [
        Friend::class,
        FriendRemoteKeys::class,
        User::class,
        RecentTrack::class,
        RecentTracksRemoteKeys::class,
        TopTrack::class,
        TopTracksRemoteKeys::class,
        TrackInfo::class,
   ],
    version = 1
)
@TypeConverters(DatabaseConverter::class)
abstract class ScrobbleDatabase: RoomDatabase() {
    abstract fun friendDao(): FriendDao
    abstract fun friendRemoteKeysDao(): FriendRemoteKeysDao
    abstract fun userDao(): UserDao
    abstract fun recentTrackDao(): RecentTrackDao
    abstract fun recentTracksRemoteKeysDao(): RecentTracksRemoteKeysDao
    abstract fun topTrackDao(): TopTrackDao
    abstract fun topTracksRemoteKeysDao(): TopTracksRemoteKeysDao
    abstract fun trackInfoDao(): TrackInfoDao
}