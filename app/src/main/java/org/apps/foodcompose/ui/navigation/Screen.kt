package org.apps.foodcompose.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("About")
    object DetailFood : Screen("home/{foodId}") {
        fun createRoute(foodId: Long) = "home/$foodId"
    }
}