package com.mzansiplatess.app.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String? = null
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") { launchSingleTop = true } }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favourites") },
            label = { Text("Favourites") },
            selected = currentRoute == "favourites",
            onClick = { navController.navigate("favourites") { launchSingleTop = true } }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Recipes") },
            label = { Text("Recipes") },
            selected = currentRoute == "recipes",
            onClick = { navController.navigate("recipes") { launchSingleTop = true } }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Events") },
            label = { Text("Events") },
            selected = currentRoute == "events",
            onClick = { navController.navigate("events") { launchSingleTop = true } }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == "profile",
            onClick = { navController.navigate("profile") { launchSingleTop = true } }
        )
    }
}

