package com.example.myminiappdavid.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myminiappdavid.main.IngredientState
import com.example.myminiappdavid.main.IngredientsAddedState
import com.example.myminiappdavid.ui.components.*
import com.example.myminiappdavid.data.LocalIngredients
import com.example.myminiappdavid.viewmodels.MealViewModel

@Composable
fun InputIngredientsScreen(
    ingredientState: IngredientState,
    ingredientsAddedState: IngredientsAddedState,
    viewModel: MealViewModel,
    onMealClick: (String) -> Unit
) {
    val context = LocalContext.current

    // Effect to refresh ingredients on initial load
    LaunchedEffect(Unit) {
        ingredientState.refresh()
    }

    var ingredientName by remember { mutableStateOf(ingredientsAddedState.name) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        TopAppBar(title = { Text("Ingredients") })

        // Custom labeled text field for ingredient input
        CustomLabeledTextField(label = "Enter your ingredients",
            value = ingredientName,
            onValueChanged = { newValue ->
                ingredientName = newValue
                ingredientsAddedState.name = newValue // Update state
            })

        Spacer(modifier = Modifier.height(16.dp))

        // Add and Refresh buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StyledButton("Add") {
                if (ingredientName.isNotBlank()) {
                    try {
                        val ingredient = LocalIngredients(
                            ingredients = ingredientName
                        )
                        ingredientState.add(ingredient)
                        ingredientState.refresh() // Refresh the list after adding
                        Toast.makeText(context, "Ingredient added successfully", Toast.LENGTH_SHORT)
                            .show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            context, "Error adding ingredient: ${e.message}", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(context, "Please enter an ingredient", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display saved ingredients from database
        Text(
            text = "Saved Ingredients:",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display ingredients from state
        LazyColumn {
            items(ingredientState.ingredients) { ingredient ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ingredient.ingredients?.let { ingredientText ->
                        Text(text = ingredientText,
                            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    // Fetch meals by selected ingredient
                                    viewModel.fetchMealsByIngredient(ingredientText)
                                })
                    }

                    // Delete button
                    StyledButton("Delete") {
                        try {
                            ingredientState.delete(ingredient)
                            Toast.makeText(context, "Ingredient deleted", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Error deleting ingredient: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display meals filtered by selected ingredient
        Text(
            text = "Meals with Selected Ingredient:",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val mealsByIngredient by viewModel.mealsByIngredient.collectAsState()
        val ingredientLoading by viewModel.ingredientLoading.collectAsState()

        if (ingredientLoading) {
            CircularProgressIndicator()
        } else {
            MealList(meals = mealsByIngredient, onMealClick = onMealClick)
        }
    }
}
