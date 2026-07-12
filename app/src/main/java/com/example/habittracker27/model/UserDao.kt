package com.example.habittracker27.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg users: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun login(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE is_logged_in = 1 LIMIT 1")
    fun getActiveUser(): User?

    @Query("SELECT COUNT(*) FROM user")
    fun countUser(): Int
}
