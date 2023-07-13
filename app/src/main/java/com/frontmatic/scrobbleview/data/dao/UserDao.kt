package com.frontmatic.scrobbleview.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frontmatic.scrobbleview.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    fun getSelectedUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

}