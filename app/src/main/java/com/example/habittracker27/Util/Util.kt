package com.example.habittracker27.Util

import android.content.Context
import com.example.habittracker27.model.AppDatabase

const val DB_NAME = "habitdb"

fun buildDb(context: Context): AppDatabase {
    return AppDatabase.buildDatabase(context)
}
