package com.example.myminiappdavid.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myminiappdavid.ui.screens.FilteredMealsScreen
import com.example.myminiappdavid.ui.screens.InputIngredientsScreen
import com.example.myminiappdavid.ui.screens.MealDetailScreen
import com.example.myminiappdavid.ui.screens.RandomMealScreen
import com.example.myminiappdavid.viewmodels.MealViewModel
import com.example.myminiappdavid.state.IngredientState
import com.example.myminiappdavid.state.IngredientsAddedState

sealed class Screen(val route: String, val title: String = "", val icon: Int? = null) {
    object RandomMeal : Screen("randomMeal", "Random", android.R.drawable.ic_menu_search)
    object InputIngredients :
        Screen("inputIngredients", "Ingredients", android.R.drawable.ic_menu_edit)

    object FilteredMeals : Screen("filteredMeals", "Meals", android.R.drawable.ic_menu_info_details)

    object MealDetail : Screen("mealDetail/{mealId}") {
        fun createRoute(mealId: String) = "mealDetail/$mealId"
    }
}

@Composable
fun AppNavigation(
    mealViewModel: MealViewModel,
    ingredientState: IngredientState,
    ingredientsAddedState: IngredientsAddedState
) {
    val navController = rememberNavController()

    Scaffold(bottomBar = { BottomBar(navController) }) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.RandomMeal.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.RandomMeal.route) {
                RandomMealScreen(mealViewModel)
            }
            composable(Screen.InputIngredients.route) {
                InputIngredientsScreen(ingredientState = ingredientState,
                    ingredientsAddedState = ingredientsAddedState,
                    viewModel = mealViewModel,
                    onMealClick = { mealId ->
                        navController.navigate(Screen.MealDetail.createRoute(mealId))
                    })
            }
            composable(Screen.FilteredMeals.route) {
                FilteredMealsScreen(viewModel = mealViewModel, onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                })
            }
            composable(
                route = Screen.MealDetail.route,
                arguments = listOf(navArgument("mealId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
                MealDetailScreen(mealId = mealId,
                    viewModel = mealViewModel,
                    onBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        Screen.RandomMeal, Screen.InputIngredients, Screen.FilteredMeals
    )

    BottomNavigation(backgroundColor = Color(0xFF6751A5)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            BottomNavigationItem(icon = {
                screen.icon?.let {
                    Icon(
                        painter = androidx.compose.ui.res.painterResource(id = it),
                        contentDescription = screen.title,
                        tint = if (isSelected) Color.White else Color.Gray
                    )
                }
            },
                label = {
                    Text(
                        text = screen.title,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray
            )

        }
    }
}