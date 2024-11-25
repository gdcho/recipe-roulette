package com.example.myminiappdavid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myminiappdavid.ui.components.CategorySelector
import com.example.myminiappdavid.ui.components.MealList
import com.example.myminiappdavid.ui.components.MealSearchBar
import com.example.myminiappdavid.ui.components.SearchResultsSection
import com.example.myminiappdavid.viewmodels.MealViewModel

@Composable
fun FilteredMealsScreen(viewModel: MealViewModel, onMealClick: (String) -> Unit) {
    val meals by viewModel.filteredMeals.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isSearchLoading by viewModel.searchLoading.collectAsState()
    val categories = listOf(
        "Beef", "Breakfast", "Chicken", "Dessert", "Goat", "Lamb",
        "Pasta", "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian"
    )
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    LaunchedEffect(selectedCategory) {
        if (!isSearchActive) {
            viewModel.fetchMealsByCategory(selectedCategory)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Meals", color = Color.White) },
                backgroundColor = Color(0xFF6751A5)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            MealSearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onSearch = {
                    isSearchActive = true
                    viewModel.searchMeals(searchQuery)
                },
                onClear = { searchQuery = "" }
            )

            CategorySelector(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                enabled = !isSearchActive
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isSearchLoading || isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                isSearchActive -> {
                    SearchResultsSection(
                        isSearchActive = isSearchActive,
                        searchQuery = searchQuery,
                        searchResults = searchResults,
                        onMealClick = onMealClick,
                        onClearSearch = {
                            searchQuery = ""
                            isSearchActive = false
                            viewModel.clearSearchResults()
                            viewModel.fetchMealsByCategory(selectedCategory)
                        }
                    )
                }

                else -> {
                    Text("$selectedCategory meals", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(8.dp))
                    MealList(meals = meals, onMealClick = onMealClick)
                }
            }
        }
    }
}