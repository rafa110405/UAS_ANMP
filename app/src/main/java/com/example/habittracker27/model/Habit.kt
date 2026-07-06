package com.example.habittracker27.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "goal")
    var goal: Int,
    @ColumnInfo(name = "progress")
    var progress: Int,
    @ColumnInfo(name = "unit")
    var unit: String,
    @ColumnInfo(name = "icon")
    var icon: Int
)
