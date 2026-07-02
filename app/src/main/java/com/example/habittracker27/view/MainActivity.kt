package com.example.habittracker27.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.habittracker27.R
import com.example.habittracker27.Util.buildDb
import com.example.habittracker27.databinding.ActivityMainBinding
import com.example.habittracker27.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController

        CoroutineScope(Dispatchers.IO).launch {
            val db = buildDb(this@MainActivity)

            if (db.userDao().countUser() == 0) {
                db.userDao().insertUser(
                    User(
                        username = "student",
                        password = "123"
                    )
                )
            }
        }
    }
}