package com.example.myminiappdavid.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myminiappdavid.state.IngredientState
import com.example.myminiappdavid.state.IngredientsAddedState
import com.example.myminiappdavid.ui.components.*
import com.example.myminiappdavid.data.LocalIngredients
import com.example.myminiappdavid.viewmodels.MealViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InputIngredientsScreen(
    ingredientState: IngredientState,
    ingredientsAddedState: IngredientsAddedState,
    viewModel: MealViewModel,
    onMealClick: (String) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        ingredientState.refresh()
    }

    var ingredientName by remember { mutableStateOf(ingredientsAddedState.name) }
    var selectedIngredient by remember { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Ingredients", color = Color.White) },
            backgroundColor = Color(0xFF6751A5)
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            MealSearchBar(searchQuery = ingredientName,
                onSearchQueryChange = { newValue ->
                    ingredientName = newValue
                    ingredientsAddedState.name = newValue
                },
                onSearch = {
                    if (ingredientName.isNotBlank()) {
                        val transformedName =
                            ingredientName.lowercase().replaceFirstChar { it.uppercaseChar() }

                        try {
                            val ingredient = LocalIngredients(ingredients = transformedName)
                            ingredientState.add(ingredient)
                            ingredientState.refresh()
                            Toast.makeText(
                                context, "Ingredient added", Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                context, "Error adding ingredient: ${e.message}", Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(context, "Please enter an ingredient", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                onClear = { ingredientName = "" },
                placeholder = "Add ingredients",
                buttonText = "Add"
            )

            Text(
                text = "Saved Ingredients:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Click on the ingredients to see associated meals",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            IngredientList(ingredients = ingredientState.ingredients,
                onIngredientClick = { ingredientText ->
                    selectedIngredient = ingredientText
                    viewModel.fetchMealsByIngredient(ingredientText)
                },
                onDeleteClick = { ingredient ->
                    try {
                        ingredientState.delete(ingredient)
                        Toast.makeText(context, "Ingredient deleted", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            context, "Error deleting ingredient: ${e.message}", Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            Spacer(modifier = Modifier.height(24.dp))

            if (selectedIngredient.isNotEmpty()) {
                Text(
                    text = "$selectedIngredient Meals",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            val mealsByIngredient by viewModel.mealsByIngredient.collectAsState()
            val ingredientLoading by viewModel.ingredientLoading.collectAsState()

            if (ingredientLoading) {
                CircularProgressIndicator()
            } else if (selectedIngredient.isNotEmpty()) {
                MealList(meals = mealsByIngredient, onMealClick = onMealClick)
            }
        }
    }
}
