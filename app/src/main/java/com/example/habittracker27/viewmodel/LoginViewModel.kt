package com.example.habittracker27.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.habittracker27.Util.buildDb
import com.example.habittracker27.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()

    val loginResult = MutableLiveData<Boolean>()
    val activeUserLD = MutableLiveData<User?>()

    fun login(username:String,password:String){
        launch{
            val db = buildDb(getApplication())
            val user = db.userDao().login(username,password)
            if (user != null) {
                user.isLoggedIn = 1
                db.userDao().updateUser(user)
                loginResult.postValue(true)
            } else {
                loginResult.postValue(false)
            }
        }
    }

    fun checkLoginStatus() {
        launch {
            val db = buildDb(getApplication())
            val user = db.userDao().getActiveUser()
            activeUserLD.postValue(user)
        }
    }

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.IO
}
