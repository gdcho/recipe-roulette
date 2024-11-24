package com.example.myminiappdavid.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalIngredients::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun ingredientsDao(): IngredientsDAO
}