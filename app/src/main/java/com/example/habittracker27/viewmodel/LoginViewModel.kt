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
            loginResult.postValue(user != null)
        }
    }
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.IO
}
