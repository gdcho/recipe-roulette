package com.example.myminiappdavid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myminiappdavid.api.MealApiService
import com.example.myminiappdavid.navigation.AppNavigation
import com.example.myminiappdavid.viewmodels.MealViewModel
import androidx.compose.runtime.remember
import com.example.myminiappdavid.main.IngredientState
import com.example.myminiappdavid.main.IngredientsAddedState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mealApiService = MealApiService()
        val mealViewModel = MealViewModel(mealApiService)
        val ingredientsRepository = (application as MyApp).userRepository

        setContent {
            val ingredientState = remember { IngredientState(ingredientsRepository) }
            val ingredientsAddedState = remember { IngredientsAddedState() }

            AppNavigation(
                mealViewModel = mealViewModel,
                ingredientState = ingredientState,
                ingredientsAddedState = ingredientsAddedState
            )
        }
    }
}