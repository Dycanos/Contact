package com.example.contact.ui.theme.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.contact.ui.theme.home.HomeDestination
import com.example.contact.ui.theme.home.HomeScreen
import java.io.Console

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun ContactNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            Log.d("Test","Ici")
            HomeScreen(
                navigateToPersonEntry = {  },
                navigateToPersonUpdate = {  }
            )
        }
    }
}
