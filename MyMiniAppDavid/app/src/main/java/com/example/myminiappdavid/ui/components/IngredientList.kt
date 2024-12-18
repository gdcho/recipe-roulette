package com.example.myminiappdavid.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myminiappdavid.data.LocalIngredients

@Composable
fun IngredientList(
    ingredients: List<LocalIngredients>,
    onIngredientClick: (String) -> Unit,
    onDeleteClick: (LocalIngredients) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(ingredients) { ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .clickable {
                        ingredient.ingredients?.let { ingredientText ->
                            onIngredientClick(ingredientText)
                        }
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ingredient.ingredients ?: "",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { onDeleteClick(ingredient) }, modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear, contentDescription = "View Details"
                    )
                }
            }

            Divider(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f), thickness = 1.dp
            )
        }
    }
}
