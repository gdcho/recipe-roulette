package com.example.myminiappdavid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myminiappdavid.ui.components.MealContent
import com.example.myminiappdavid.viewmodels.MealViewModel

@Composable
fun MealDetailScreen(
    mealId: String, viewModel: MealViewModel, onBack: () -> Unit
) {
    val meal by viewModel.selectedMeal.collectAsState()
    val isLoading by viewModel.mealDetailsLoading.collectAsState()

    LaunchedEffect(mealId) {
        viewModel.fetchMealDetails(mealId)
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(meal?.strMeal ?: "Meal Details", color = Color.White) },
            backgroundColor = Color(0xFF6751A5),
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            })
    }) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            meal?.let {
                MealContent(meal = it)
            } ?: Text(
                "Meal details not found.", modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

