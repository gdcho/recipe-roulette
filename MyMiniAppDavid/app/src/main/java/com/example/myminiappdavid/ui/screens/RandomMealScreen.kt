package com.example.myminiappdavid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.myminiappdavid.viewmodels.MealViewModel

@Composable
fun RandomMealScreen(viewModel: MealViewModel) {
    val meal by viewModel.randomMeal.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(title = { Text("Random Meal") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.fetchRandomMeal() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Generate Random Meal")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            meal?.let {
                Text(
                    text = it.strMeal,
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Image with loading state
                Box(
                    modifier = Modifier.size(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val painter = rememberAsyncImagePainter(it.strMealThumb)

                    // Show loading spinner while image is loading
                    if (painter.state is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator()
                    }

                    Image(
                        painter = painter,
                        contentDescription = "Meal Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ingredients:",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))

                it.getIngredientList().forEach { ingredient ->
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
                    text = it.strInstructions ?: "No instructions available.",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.Start)
                )
            } ?: Text(
                text = "Press the button for a random meal.",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
    }
}