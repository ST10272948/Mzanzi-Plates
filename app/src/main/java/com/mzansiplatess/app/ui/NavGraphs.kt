package com.mzansiplatess.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mzansiplatess.app.ui.viewmodel.LanguageViewModel

@Composable
fun AppNavHost(
    navController: NavHostController, 
    languageViewModel: LanguageViewModel,
    appSettingsViewModel: com.mzansiplatess.app.ui.viewmodel.AppSettingsViewModel? = null
) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }

        // Main app screens with bottom navigation
        composable("home") { HomeScreen(navController, languageViewModel) }
        composable("favourites") { FavouritesScreen(navController) }
        composable("recipes") { RecipesScreen(navController) }
        composable("events") { EventsScreen(navController) }
        composable("profile") { ProfileScreen(navController, languageViewModel) }

        // Settings stack - Complete settings navigation
        composable("account_settings") { 
            AccountSettingsScreen(
                navController = navController,
                appSettingsViewModel = appSettingsViewModel
            ) 
        }
        composable("edit_profile") { EditProfileScreen(navController) }
        composable("change_password") { ChangePasswordScreen(navController) }
        composable("my_recipes") { MyRecipesScreen(navController) }
        composable("favorites") { FavoritesScreen(navController) }
        composable("shopping_list") { ShoppingListScreen(navController) }
    }
}
