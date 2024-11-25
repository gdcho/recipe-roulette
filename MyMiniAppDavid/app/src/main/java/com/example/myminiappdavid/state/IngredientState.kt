package com.example.myminiappdavid.state

import androidx.compose.runtime.toMutableStateList
import com.example.myminiappdavid.data.LocalIngredients
import com.example.myminiappdavid.data.IngredientsRepository

class IngredientState(private val repository: IngredientsRepository) {

    val ingredients = repository.getAll().toMutableStateList()

    fun add(localIngredients: LocalIngredients) {
        repository.add(localIngredients)
    }

    fun refresh() {
        ingredients.apply {
            clear()
            addAll(repository.getAll())
        }
    }

    fun delete(localUser: LocalIngredients) {
        ingredients.remove(localUser)
        repository.deleteEntity(localUser)
    }

}