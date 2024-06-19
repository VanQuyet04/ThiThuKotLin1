package com.example.thithukotlin1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thithukotlin1.ui.screens.HomeScreen
import com.example.thithukotlin1.ui.screens.WelcomeScreen

enum class ROUTE_SCREEN_NAME{
    WELCOME,
    HOME
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ROUTE_SCREEN_NAME.WELCOME.name) {
        composable(ROUTE_SCREEN_NAME.WELCOME.name) {
            WelcomeScreen(navController)
        }
        composable(ROUTE_SCREEN_NAME.HOME.name) {
            HomeScreen()
        }
    }
}