package com.example.myminiappdavid.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class MealApiService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getRandomMeal(): MealResponse {
        return client.get("https://www.themealdb.com/api/json/v1/1/random.php").body()
    }

    suspend fun getMealsByCategory(category: String): MealsListResponse {
        return client.get("https://www.themealdb.com/api/json/v1/1/filter.php?c=$category").body()
    }

    suspend fun searchMealsByName(query: String): MealResponse {
        val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
        return client.get("https://www.themealdb.com/api/json/v1/1/search.php?s=$encodedQuery")
            .body()
    }

    suspend fun getMealsByIngredient(ingredient: String): MealsListResponse {
        val encodedIngredient = java.net.URLEncoder.encode(ingredient, "UTF-8")
        return client.get("https://www.themealdb.com/api/json/v1/1/filter.php?i=$encodedIngredient")
            .body()
    }

    suspend fun getMealDetails(id: String): MealResponse {
        return client.get("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$id").body()
    }
}

@Serializable
data class MealResponse(
    val meals: List<Meal>
)

@Serializable
data class MealsListResponse(
    val meals: List<Meal>
)

@Serializable
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strDrinkAlternate: String? = null,
    val strCategory: String? = null,
    val strArea: String? = null,
    val strInstructions: String? = null,
    val strMealThumb: String,
    val strTags: String? = null,
    val strYoutube: String? = null,
    val strIngredient1: String? = null,
    val strIngredient2: String? = null,
    val strIngredient3: String? = null,
    val strIngredient4: String? = null,
    val strIngredient5: String? = null,
    val strIngredient6: String? = null,
    val strIngredient7: String? = null,
    val strIngredient8: String? = null,
    val strIngredient9: String? = null,
    val strIngredient10: String? = null,
    val strIngredient11: String? = null,
    val strIngredient12: String? = null,
    val strIngredient13: String? = null,
    val strIngredient14: String? = null,
    val strIngredient15: String? = null,
    val strIngredient16: String? = null,
    val strIngredient17: String? = null,
    val strIngredient18: String? = null,
    val strIngredient19: String? = null,
    val strIngredient20: String? = null,
    val strMeasure1: String? = null,
    val strMeasure2: String? = null,
    val strMeasure3: String? = null,
    val strMeasure4: String? = null,
    val strMeasure5: String? = null,
    val strMeasure6: String? = null,
    val strMeasure7: String? = null,
    val strMeasure8: String? = null,
    val strMeasure9: String? = null,
    val strMeasure10: String? = null,
    val strMeasure11: String? = null,
    val strMeasure12: String? = null,
    val strMeasure13: String? = null,
    val strMeasure14: String? = null,
    val strMeasure15: String? = null,
    val strMeasure16: String? = null,
    val strMeasure17: String? = null,
    val strMeasure18: String? = null,
    val strMeasure19: String? = null,
    val strMeasure20: String? = null,
    val strSource: String? = null,
    val dateModified: String? = null
) {
    fun getIngredientList(): List<String> {
        return (1..20).mapNotNull { index ->
            val ingredient =
                this::class.members.find { it.name == "strIngredient$index" }?.call(this) as? String
            val measure =
                this::class.members.find { it.name == "strMeasure$index" }?.call(this) as? String
            if (!ingredient.isNullOrBlank()) {
                if (!measure.isNullOrBlank()) {
                    "$ingredient - $measure"
                } else {
                    ingredient
                }
            } else {
                null
            }
        }
    }
}
