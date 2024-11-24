package com.example.myminiappdavid.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myminiappdavid.data.LocalIngredients

@Composable
fun IngredientItem(
    ingredient: LocalIngredients,
    onIngredientClicked: (LocalIngredients) -> Unit,
    onDeleteClicked: (LocalIngredients) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onIngredientClicked(ingredient) }, shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Text(
                    text = ingredient.ingredients ?: "", style = TextStyle(
                        fontSize = 18.sp,
                    ), modifier = Modifier.padding(end = 20.dp, start = 10.dp)
                )
            }
            IconButton(onClick = { onDeleteClicked(ingredient) }) {
                Icon(
                    imageVector = Icons.Filled.Close, contentDescription = "Delete"
                )
            }
        }
    }
}

