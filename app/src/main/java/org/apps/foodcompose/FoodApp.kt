package org.apps.foodcompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.apps.foodcompose.ui.theme.FoodComposeTheme
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.apps.foodcompose.ui.navigation.NavigationItem
import org.apps.foodcompose.ui.navigation.Screen
import org.apps.foodcompose.ui.screen.detail.DetailScreen
import org.apps.foodcompose.ui.screen.home.HomeScreen
import org.apps.foodcompose.ui.screen.about.AboutScreen

@Composable
fun FoodApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailFood.route){
                BottomBar(navController)
            }
        },
        modifier = modifier
    ){ innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ){
                composable(Screen.Home.route) {
                    HomeScreen(
                        navigateToDetail = { foodId ->
                            navController.navigate(Screen.DetailFood.createRoute(foodId))
                        }
                    )
                }
                composable(Screen.About.route) {
                    AboutScreen()
                }
                composable(
                    route = Screen.DetailFood.route,
                    arguments = listOf(navArgument("foodId") { type = NavType.LongType }),
                ) {
                    val id = it.arguments?.getLong("foodId") ?: -1L
                    DetailScreen(
                        foodId = id,
                        navigateBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                screen = Screen.Home,
                contentDescription = "home_page"
            ),
            NavigationItem(
                title = "About",
                icon = Icons.Default.Person,
                screen = Screen.About,
                contentDescription = "about_page"
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodAppPreview() {
    FoodComposeTheme {
        FoodApp()
    }
}



