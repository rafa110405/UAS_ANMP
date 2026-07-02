package com.example.habittracker27.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.habittracker27.Util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()

    val loginResult = MutableLiveData<Boolean>()

    fun login(username:String,password:String){
        launch{
            val db = buildDb(getApplication())
            val user = db.userDao().login(username,password)
            if (user != null) {
                val sharedPref = getApplication<Application>().getSharedPreferences(
                    "habit_tracker_prefs", android.content.Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("is_logged_in", true)
                    apply()
                }
                loginResult.postValue(true)
            } else {
                loginResult.postValue(false)
            }
        }
    }

    fun checkLoginStatus() {
        val sharedPref = getApplication<Application>().getSharedPreferences("habit_tracker_prefs", android.content.Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)
        if (isLoggedIn) {
            loginResult.postValue(true)
        }
    }
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.IO

}