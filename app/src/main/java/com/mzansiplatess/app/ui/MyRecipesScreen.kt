package com.mzansiplatess.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
fun MyRecipesScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    
    val categories = listOf("All", "Breakfast", "Lunch", "Dinner", "Desserts", "Snacks")
    val recipes = remember {
        listOf(
            RecipeItem("Bobotie", "Traditional South African dish", "Dinner", "2 hours"),
            RecipeItem("Biltong", "Dried cured meat", "Snacks", "3 days"),
            RecipeItem("Malva Pudding", "Sweet dessert", "Desserts", "1 hour"),
            RecipeItem("Boerewors", "Farmers sausage", "Lunch", "30 min"),
            RecipeItem("Chakalaka", "Spicy vegetable relish", "Side", "20 min"),
            RecipeItem("Koeksisters", "Sweet fried pastry", "Desserts", "2 hours"),
            RecipeItem("Milk Tart", "Traditional tart", "Desserts", "1.5 hours"),
            RecipeItem("Potjiekos", "Slow-cooked stew", "Dinner", "3 hours")
        )
    }
    
    val filteredRecipes = recipes.filter { recipe ->
        (selectedCategory == "All" || recipe.category == selectedCategory) &&
        recipe.name.contains(searchQuery, ignoreCase = true)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "My Recipes",
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Navigate to add recipe */ },
                containerColor = Color(0xFFFF6F00)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Recipe",
                    tint = Color.White
                )
            }
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
                placeholder = { Text("Search recipes...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            )
            
            // Category Filter
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        selected = selectedCategory == category,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFFF6F00),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Recipes List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredRecipes) { recipe ->
                    RecipeCard(recipe = recipe)
                }
            }
        }
    }
}

@Composable
private fun RecipeCard(recipe: RecipeItem) {
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
                Text(
                    text = recipe.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = recipe.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = recipe.category,
                        fontSize = 12.sp,
                        color = Color(0xFFFF6F00),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = recipe.cookingTime,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

data class RecipeItem(
    val name: String,
    val description: String,
    val category: String,
    val cookingTime: String
)

@Preview(showBackground = true)
@Composable
fun MyRecipesScreenPreview() {
    MyRecipesScreen(navController = androidx.navigation.compose.rememberNavController())
}
