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

class DashboardViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.IO
    val habitLD = MutableLiveData<List<Habit>>()
    val totalHabitLD = MutableLiveData<Int>()
    val completedHabitLD = MutableLiveData<Int>()
    val progressHabitLD = MutableLiveData<Int>()

    fun refresh() {
        launch {

            val db = buildDb(getApplication())

            val list = db.habitDao().selectAllHabit()

            habitLD.postValue(list)
            totalHabitLD.postValue(list.size)
            completedHabitLD.postValue(
                list.count { it.progress >= it.goal }
            )
            progressHabitLD.postValue(
                list.count { it.progress < it.goal }
            )
        }
    }
}