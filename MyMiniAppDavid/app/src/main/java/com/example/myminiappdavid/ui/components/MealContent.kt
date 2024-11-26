package com.example.myminiappdavid.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myminiappdavid.api.Meal

@Composable
fun MealContent(meal: Meal) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = meal.strMeal, style = MaterialTheme.typography.h5, textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier.size(280.dp), shape = RoundedCornerShape(8.dp), elevation = 6.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(meal.strMealThumb),
                    contentDescription = "Meal Image",
                    modifier = Modifier.size(280.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
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
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 10.dp)
        )
    }
}