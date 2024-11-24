package com.example.myminiappdavid.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myminiappdavid.data.LocalIngredients

class IngredientsAddedState {

    var uid by mutableStateOf<Int?>(null)
    var name by mutableStateOf("")

}
