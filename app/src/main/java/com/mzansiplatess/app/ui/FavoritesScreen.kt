package com.mzansiplatess.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    
    val favoriteRecipes = remember {
        listOf(
            FavoriteRecipe("Bobotie", "Traditional South African dish with spiced minced meat", "4.8", "2 hours", true),
            FavoriteRecipe("Malva Pudding", "Sweet and sticky South African dessert", "4.9", "1 hour", true),
            FavoriteRecipe("Biltong", "Dried cured meat snack", "4.7", "3 days", true),
            FavoriteRecipe("Milk Tart", "Traditional South African tart", "4.6", "1.5 hours", true),
            FavoriteRecipe("Potjiekos", "Slow-cooked stew in a cast iron pot", "4.8", "3 hours", true),
            FavoriteRecipe("Koeksisters", "Sweet fried pastry in syrup", "4.5", "2 hours", true)
        )
    }
    
    val filteredRecipes = favoriteRecipes.filter { recipe ->
        recipe.name.contains(searchQuery, ignoreCase = true) ||
        recipe.description.contains(searchQuery, ignoreCase = true)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "My Favorites",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle search */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF6F00)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search favorites...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            )
            
            // Favorites Count
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E8)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color(0xFFFF6F00),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "${filteredRecipes.size} favorite recipes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Favorites List
            if (filteredRecipes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (searchQuery.isEmpty()) "No favorites yet" else "No matching favorites",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Start adding recipes to your favorites!",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredRecipes) { recipe ->
                        FavoriteRecipeCard(recipe = recipe)
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteRecipeCard(recipe: FavoriteRecipe) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = recipe.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = Color(0xFFFF6F00),
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = recipe.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "★ ${recipe.rating}",
                            fontSize = 12.sp,
                            color = Color(0xFFFF6F00),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = recipe.cookingTime,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            // Action Buttons
            Column(
                horizontalAlignment = Alignment.End
            ) {
                IconButton(
                    onClick = { /* Remove from favorites */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Remove from favorites",
                        tint = Color(0xFFFF6F00)
                    )
                }
            }
        }
    }
}

data class FavoriteRecipe(
    val name: String,
    val description: String,
    val rating: String,
    val cookingTime: String,
    val isFavorite: Boolean
)

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(navController = androidx.navigation.compose.rememberNavController())
}
