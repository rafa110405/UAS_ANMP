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
            habitList.postValue(
                db.habitDao().selectAllHabit()
            )
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