package com.example.myminiappdavid.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myminiappdavid.api.Meal
import com.example.myminiappdavid.data.LocalIngredients
import com.example.myminiappdavid.main.IngredientState
import com.example.myminiappdavid.main.IngredientsAddedState

@Composable
fun MealCard(meal: Meal) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(meal.strMealThumb),
                contentDescription = "Meal Image",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = meal.strMeal,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun LoadingSpinner(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CircularProgressIndicator() {
    TODO("Not yet implemented")
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        readOnly = readOnly,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CustomLabeledTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 70.dp, bottom = 4.dp)
        )
        CustomTextField(
            label = label,
            value = value,
            onValueChange = onValueChanged
        )
    }
}

@Composable
fun ButtonRow(ingredientState: IngredientState, ingredientsAddedState: IngredientsAddedState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StyledButton("Add") {
            val ingredient = LocalIngredients(
                uid = ingredientsAddedState.uid,
                ingredients = ingredientsAddedState.name
            )
            ingredientState.add(ingredient)
        }
        StyledButton("Refresh") {
            ingredientState.refresh()
        }
    }
}

@Composable
fun StyledButton(text: String, onClick: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = Modifier.padding(4.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(50.dp)
    ) {
        Text(text, fontSize = 22.sp)
    }
}