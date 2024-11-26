package com.example.myminiappdavid.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myminiappdavid.api.Meal
import com.example.myminiappdavid.api.MealApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State


class MealViewModel(private val mealApiService: MealApiService) : ViewModel() {

    private val _randomMeal = MutableStateFlow<Meal?>(null)
    val randomMeal: StateFlow<Meal?> = _randomMeal

    private val _filteredMeals = MutableStateFlow<List<Meal>>(emptyList())
    val filteredMeals: StateFlow<List<Meal>> = _filteredMeals

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _searchResults = MutableStateFlow<List<Meal>>(emptyList())
    val searchResults: StateFlow<List<Meal>> = _searchResults

    private val _searchLoading = MutableStateFlow(false)
    val searchLoading: StateFlow<Boolean> = _searchLoading

    private val _mealsByIngredient = MutableStateFlow<List<Meal>>(emptyList())
    val mealsByIngredient: StateFlow<List<Meal>> = _mealsByIngredient

    private val _ingredientLoading = MutableStateFlow(false)
    val ingredientLoading: StateFlow<Boolean> = _ingredientLoading

    private val _selectedMeal = MutableStateFlow<Meal?>(null)
    val selectedMeal: StateFlow<Meal?> = _selectedMeal

    private val _mealDetailsLoading = MutableStateFlow(false)
    val mealDetailsLoading: StateFlow<Boolean> = _mealDetailsLoading

    val searchQuery: MutableState<String> = mutableStateOf("")

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun fetchRandomMeal() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val response = mealApiService.getRandomMeal()
                Log.i("MealViewModel", "API Response: $response")
                _randomMeal.value = response.meals.firstOrNull() ?: Meal(
                    strMeal = "Unknown Meal", strMealThumb = "", idMeal = "N/A"
                )
            } catch (e: Exception) {
                _randomMeal.value = null // Handle gracefully
                e.printStackTrace() // Log the exception for debugging
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            _loading.value = true
            _filteredMeals.value = mealApiService.getMealsByCategory(category).meals
            _loading.value = false
        }
    }

    fun searchMeals(query: String) {
        viewModelScope.launch {
            try {
                _searchLoading.value = true
                val response = mealApiService.searchMealsByName(query)
                _searchResults.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                _searchResults.value = emptyList()
                e.printStackTrace()
            } finally {
                _searchLoading.value = false
            }
        }
    }

    fun clearSearchResults() {
        _searchResults.value = emptyList()
    }

    fun fetchMealsByIngredient(ingredient: String) {
        viewModelScope.launch {
            try {
                _ingredientLoading.value = true
                val response = mealApiService.getMealsByIngredient(ingredient)
                _mealsByIngredient.value = response.meals
            } catch (e: Exception) {
                _mealsByIngredient.value = emptyList()
                e.printStackTrace()
            } finally {
                _ingredientLoading.value = false
            }
        }
    }

    fun fetchMealDetails(id: String) {
        viewModelScope.launch {
            try {
                _mealDetailsLoading.value = true
                val response = mealApiService.getMealDetails(id)
                _selectedMeal.value = response.meals.firstOrNull()
            } catch (e: Exception) {
                _selectedMeal.value = null
                e.printStackTrace()
            } finally {
                _mealDetailsLoading.value = false
            }
        }
    }

}
