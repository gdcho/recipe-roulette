package com.example.myminiappdavid.data

class IngredientsRepository(private val ingredientsDAO: IngredientsDAO) {

    fun getAll(): List<LocalIngredients> {
        return ingredientsDAO.getAll()
    }

    fun deleteEntity(ingredients: LocalIngredients) {
        ingredientsDAO.delete(ingredients)
    }

    fun add(ingredients: LocalIngredients) {
        ingredientsDAO.add(ingredients)
    }
}