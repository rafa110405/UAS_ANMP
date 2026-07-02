package com.example.habittracker27.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabit(vararg habits: Habit)

    @Query("SELECT * FROM habit")
    fun selectAllHabit(): List<Habit>

    @Query("SELECT * FROM habit WHERE id = :id")
    fun selectHabit(id: Int): Habit?

    @Update
    fun updateHabit(habitItem: Habit)

    @Delete
    fun deleteHabit(habitItem: Habit)
}
