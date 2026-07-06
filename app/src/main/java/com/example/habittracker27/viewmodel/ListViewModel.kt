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

class ListViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val habitList = MutableLiveData<List<Habit>>()
    val loadingLD = MutableLiveData<Boolean>()
    val habitLoadErrorLD = MutableLiveData<Boolean>()
    private val job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.postValue(true)
        habitLoadErrorLD.postValue(false)

        launch {
            val db = buildDb(getApplication())
            val currentHabits = db.habitDao().selectAllHabit()
            
            if (currentHabits.isEmpty()) {
                seedHabits()
            } else {
                habitList.postValue(currentHabits)
                loadingLD.postValue(false)
            }
        }
    }

    private fun seedHabits() {
        launch {
            val db = buildDb(getApplication())
            val dummyHabits = listOf(
                Habit(name = "Read Books", description = "Read at least 20 pages a day", goal = 20, progress = 5, unit = "pages", icon = com.example.habittracker27.R.drawable.baseline_menu_book_24),
                Habit(name = "Drink Water", description = "Drink 8 glasses of water", goal = 8, progress = 3, unit = "glasses", icon = com.example.habittracker27.R.drawable.baseline_water_drop_24),
                Habit(name = "Meditation", description = "Focus on your breath for 15 minutes", goal = 15, progress = 5, unit = "minutes", icon = com.example.habittracker27.R.drawable.baseline_self_improvement_24)
            )
            db.habitDao().insertHabit(*dummyHabits.toTypedArray())
            habitList.postValue(db.habitDao().selectAllHabit())
            loadingLD.postValue(false)
        }
    }

    fun increaseProgress(habit: Habit) {
        launch {
            val db = buildDb(getApplication())
            if (habit.progress < habit.goal) {
                habit.progress++
                db.habitDao().updateHabit(habit)
            }
            habitList.postValue(
                db.habitDao().selectAllHabit()
            )
        }
    }

    fun decreaseProgress(habit: Habit) {
        launch {
            val db = buildDb(getApplication())
            if (habit.progress > 0) {
                habit.progress--
                db.habitDao().updateHabit(habit)
            }
            habitList.postValue(
                db.habitDao().selectAllHabit()
            )
        }
    }
}