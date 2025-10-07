package com.mzansiplatess.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mzansiplatess.app.R

@Composable
fun RecipesScreen(navController: NavHostController) {
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = { RecipesTopBar(searchQuery) { searchQuery = it } },
        bottomBar = { BottomNavigationBar(navController, "recipes") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Category filters
            CategoryFilters(selectedCategory) { selectedCategory = it }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Featured recipe
            FeaturedRecipeCard()
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Recipes list
            Text(
                text = "Popular Recipes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(getSampleRecipes()) { recipe ->
                    RecipeCard(recipe)
                }
            }
        }
    }
}

@Composable
fun RecipesTopBar(searchQuery: String, onSearchChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF9800))
            .padding(16.dp)
    ) {
        Text(
            text = "Recipes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Search recipes...", color = Color.Gray) },
            leadingIcon = { 
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) 
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun CategoryFilters(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val categories = listOf("All", "Traditional", "Braai", "Vegetarian", "Desserts", "Beverages")
    
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) { category ->
            FilterChip(
                onClick = { onCategorySelected(category) },
                label = { Text(category) },
                selected = selectedCategory == category,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFFF9800),
                    selectedLabelColor = Color.White,
                    containerColor = Color.White
                )
            )
        }
    }
}

@Composable
fun FeaturedRecipeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Featured Recipe",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Black.copy(alpha = 0.3f)
                    )
            )
            
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Featured: Traditional Bobotie",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Classic South African comfort food",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = "Time",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("45 mins", color = Color.White, fontSize = 12.sp)
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("4.8", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = recipe.image),
                contentDescription = recipe.name,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = recipe.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = recipe.description,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 2
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = "Time",
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(recipe.duration, fontSize = 12.sp, color = Color.Gray)
                    }
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFF9800),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(recipe.rating.toString(), fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

// Sample data
data class Recipe(
    val name: String,
    val description: String,
    val duration: String,
    val rating: Double,
    val image: Int,
    val category: String = "Traditional"
)

fun getSampleRecipes(): List<Recipe> = listOf(
    Recipe("Biltong", "Traditional dried meat snack", "2 days", 4.7, R.drawable.ic_logo),
    Recipe("Koeksisters", "Sweet twisted pastries", "2 hours", 4.5, R.drawable.ic_logo),
    Recipe("Milk Tart", "Custard tart dessert", "1 hour", 4.8, R.drawable.ic_logo),
    Recipe("Chakalaka", "Spicy vegetable relish", "30 mins", 4.3, R.drawable.ic_logo),
    Recipe("Bunny Chow", "Hollowed bread with curry", "45 mins", 4.6, R.drawable.ic_logo),
    Recipe("Rooibos Tea", "Traditional herbal tea", "5 mins", 4.4, R.drawable.ic_logo)
)
