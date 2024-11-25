// SearchResultsSection.kt
package com.example.myminiappdavid.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.myminiappdavid.api.Meal

@Composable
fun SearchResultsSection(
    isSearchActive: Boolean,
    searchQuery: String,
    searchResults: List<Meal>,
    onMealClick: (String) -> Unit,
    onClearSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (isSearchActive || searchResults.isNotEmpty()) {
            TextButton(
                onClick = onClearSearch,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Clear Search")
            }
        }

        when {
            searchResults.isNotEmpty() -> {
                Text("Search Results:", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                MealList(
                    meals = searchResults,
                    onMealClick = onMealClick
                )
            }

            isSearchActive -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("No results found for \"$searchQuery\"")
                }
            }
        }
    }
}