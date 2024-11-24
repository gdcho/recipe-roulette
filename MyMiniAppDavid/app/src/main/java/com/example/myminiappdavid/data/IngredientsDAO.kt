package com.example.myminiappdavid.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete

@Dao
interface IngredientsDAO {

    @Query("SELECT * FROM ingredients_table")
    fun getAll(): List<LocalIngredients>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(ingredients: LocalIngredients)

    @Delete
    fun delete(ingredients: LocalIngredients)
}
