package com.example.habittracker27.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.habittracker27.Util.buildDb
import com.example.habittracker27.model.Habit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailHabitViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val habitLD = MutableLiveData<Habit>()
    private val job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.IO

    fun addHabit(list: List<Habit>) {
        launch {
            val db = buildDb(getApplication())
            db.habitDao().insertHabit(*list.toTypedArray())
        }
    }

    fun fetch(id: Int) {
        launch {
            val db = buildDb(getApplication())
            val habit = db.habitDao().selectHabit(id)
            if (habit != null) {
                habitLD.postValue(habit)
            }
        }
    }

    fun updateHabit(habit: Habit){
        launch{
            val db = buildDb(getApplication())
            db.habitDao().updateHabit(habit)
        }
    }
}