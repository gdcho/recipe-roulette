package com.example.myminiappdavid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myminiappdavid.api.Meal
import com.example.myminiappdavid.viewmodels.MealViewModel

@Composable
fun MealDetailScreen(
    mealId: String,
    viewModel: MealViewModel,
    onBack: () -> Unit
) {
    val meal by viewModel.selectedMeal.collectAsState()
    val isLoading by viewModel.mealDetailsLoading.collectAsState()

    // Fetch meal details when this screen is displayed
    LaunchedEffect(mealId) {
        viewModel.fetchMealDetails(mealId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(meal?.strMeal ?: "Meal Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->  // Accept the paddingValues parameter here
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),  // Apply padding here
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            meal?.let {
                MealContent(meal = it, paddingValues = paddingValues)  // Pass paddingValues
            } ?: Text(
                "Meal details not found.",
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun MealContent(meal: Meal, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)  // Apply paddingValues here
            .padding(16.dp)          // Additional padding if needed
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = meal.strMeal,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = rememberAsyncImagePainter(meal.strMealThumb),
            contentDescription = "Meal Image",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Ingredients:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        meal.getIngredientList().forEach { ingredient ->
            Text(
                text = "• $ingredient",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Instructions:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = meal.strInstructions ?: "No instructions available.",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.align(Alignment.Start)
        )
    }
}
