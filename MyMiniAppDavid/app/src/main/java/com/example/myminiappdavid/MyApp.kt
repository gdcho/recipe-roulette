package com.example.myminiappdavid

import android.app.Application
import androidx.room.Room
import com.example.myminiappdavid.data.AppDataBase
import com.example.myminiappdavid.data.IngredientsRepository

class MyApp : Application() {

    private val db by lazy {

        Room.databaseBuilder(
            applicationContext, AppDataBase::class.java, "my-database"
        ).allowMainThreadQueries().build()
    }

    val userRepository by lazy {
        IngredientsRepository(db.ingredientsDao())
    }
}