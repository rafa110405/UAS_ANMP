package com.example.habittracker27.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "is_logged_in")
    var isLoggedIn: Int = 0
)
