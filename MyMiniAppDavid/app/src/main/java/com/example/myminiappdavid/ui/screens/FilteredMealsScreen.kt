package com.example.myminiappdavid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myminiappdavid.api.Meal
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
    var dropdownExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    LaunchedEffect(selectedCategory) {
        if (!isSearchActive) {
            viewModel.fetchMealsByCategory(selectedCategory)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TopAppBar(title = { Text("All Meals") })

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar with Clear Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Meals") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    isSearchActive = true
                    viewModel.searchMeals(searchQuery)
                }
            ) {
                Text("Search")
            }
        }

        if (isSearchActive || searchResults.isNotEmpty()) {
            TextButton(
                onClick = {
                    searchQuery = ""
                    isSearchActive = false
                    viewModel.clearSearchResults()
                    viewModel.fetchMealsByCategory(selectedCategory)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Clear Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Category dropdown
        Box {
            Button(
                onClick = { dropdownExpanded = true },
                enabled = !isSearchActive
            ) {
                Text(selectedCategory)
            }
            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(onClick = {
                        selectedCategory = category
                        dropdownExpanded = false
                    }) {
                        Text(category)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display content based on state
        when {
            isSearchLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            isSearchActive && searchResults.isNotEmpty() -> {
                Text("Search Results:", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                MealList(
                    meals = searchResults,
                    onMealClick = onMealClick
                )
            }
            isSearchActive && searchResults.isEmpty() -> {
                Text("No results found for \"$searchQuery\"")
            }
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                Text("Meals in $selectedCategory:", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                MealList(meals = meals, onMealClick = onMealClick)
            }
        }
    }
}

@Composable
fun MealList(meals: List<Meal>, onMealClick: (String) -> Unit) {
    if (meals.isEmpty()) {
        Text("No meals found.")
    } else {
        LazyColumn {
            items(meals) { meal ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onMealClick(meal.idMeal) }
                        .padding(8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(meal.strMealThumb),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(meal.strMeal, style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}

