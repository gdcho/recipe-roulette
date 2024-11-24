package com.example.myminiappdavid.data

class IngredientsRepository(private val ingredientsDAO: IngredientsDAO) {

    fun getAll(): List<LocalIngredients> {
        return ingredientsDAO.getAll()
    }

    fun deleteEntity(user: LocalIngredients) {
        ingredientsDAO.delete(user)
    }

    fun add(user: LocalIngredients) {
        ingredientsDAO.add(user)
    }
}