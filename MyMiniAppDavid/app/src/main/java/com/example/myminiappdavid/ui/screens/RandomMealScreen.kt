package com.example.myminiappdavid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myminiappdavid.ui.components.MealContent
import com.example.myminiappdavid.viewmodels.MealViewModel

@Composable
fun RandomMealScreen(viewModel: MealViewModel) {
    val meal by viewModel.randomMeal.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Random Meal", color = Color.White) },
            backgroundColor = Color(0xFF6751A5)
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.fetchRandomMeal() },
                modifier = Modifier.height(50.dp).padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Generate Random Meal", color = Color.White, fontSize = 18.sp)
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                meal?.let {
                    MealContent(meal = it)
                } ?: Text(
                    text = "Press the button for a random meal.",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                )
            }
        }
    }
}