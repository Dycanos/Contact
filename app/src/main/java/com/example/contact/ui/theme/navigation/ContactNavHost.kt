package com.example.contact.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.contact.ui.theme.person.ContactAddDestination
import com.example.contact.ui.theme.person.ContactAddScreen
import com.example.contact.ui.theme.home.HomeDestination
import com.example.contact.ui.theme.home.HomeScreen
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
            HomeScreen(
                navigateToPersonEntry = { navController.navigate(ContactAddDestination.route) },
                navigateToPersonUpdate = { navController.navigate(ContactUpdateDestination.route) }
            )
        }
        composable(route = ContactAddDestination.route) {
            ContactAddScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = ContactUpdateDestination.route) {
            ContactUpdateScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}


