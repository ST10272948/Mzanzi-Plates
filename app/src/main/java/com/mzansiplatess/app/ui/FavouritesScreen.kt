package com.mzansiplatess.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
fun FavouritesScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf("Restaurants") }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = { FavouritesTopBar(searchQuery) { searchQuery = it } },
        bottomBar = { BottomNavigationBar(navController, "favourites") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Tab selection
            TabSelection(selectedTab) { selectedTab = it }
            
            // Content based on selected tab
            when (selectedTab) {
                "Restaurants" -> FavouriteRestaurantsList()
                "Recipes" -> FavouriteRecipesList()
                "Events" -> FavouriteEventsList()
            }
        }
    }
}

@Composable
fun FavouritesTopBar(searchQuery: String, onSearchChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF9800))
            .padding(16.dp)
    ) {
        Text(
            text = "Favourites",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Your saved restaurants, recipes & events",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.9f)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Search favourites...", color = Color.Gray) },
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
fun TabSelection(selectedTab: String, onTabSelected: (String) -> Unit) {
    val tabs = listOf("Restaurants", "Recipes", "Events")
    
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        items(tabs) { tab ->
            FilterChip(
                onClick = { onTabSelected(tab) },
                label = { Text(tab) },
                selected = selectedTab == tab,
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
fun FavouriteRestaurantsList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(getFavouriteRestaurants()) { restaurant ->
            FavouriteRestaurantCard(restaurant)
        }
    }
}

@Composable
fun FavouriteRecipesList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(getFavouriteRecipes()) { recipe ->
            FavouriteRecipeCard(recipe)
        }
    }
}

@Composable
fun FavouriteEventsList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(getFavouriteEvents()) { event ->
            FavouriteEventCard(event)
        }
    }
}

@Composable
fun FavouriteRestaurantCard(restaurant: FavouriteRestaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = restaurant.image),
                contentDescription = restaurant.name,
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
                        text = restaurant.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = restaurant.description,
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
                            Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFF9800),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(restaurant.rating.toString(), fontSize = 12.sp, color = Color.Gray)
                    }
                    
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Remove from favourites",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { /* TODO: Remove from favourites */ }
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteRecipeCard(recipe: Recipe) {
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
                        maxLines = 2
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
                    
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Remove from favourites",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { /* TODO: Remove from favourites */ }
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteEventCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = event.image),
                contentDescription = event.name,
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
                        text = event.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = event.location,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.dateTime,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Remove from favourites",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { /* TODO: Remove from favourites */ }
                    )
                }
            }
        }
    }
}

// Sample data
data class FavouriteRestaurant(
    val name: String,
    val description: String,
    val rating: Double,
    val image: Int
)

fun getFavouriteRestaurants(): List<FavouriteRestaurant> = listOf(
    FavouriteRestaurant("Braai Spot", "Authentic SA Braai", 4.5, R.drawable.ic_logo),
    FavouriteRestaurant("Kota King", "Best Kotas in town", 4.8, R.drawable.ic_logo),
    FavouriteRestaurant("Shisa Nyama", "Local grill experience", 4.2, R.drawable.ic_logo)
)

fun getFavouriteRecipes(): List<Recipe> = listOf(
    Recipe("Biltong", "Traditional dried meat snack", "2 days", 4.7, R.drawable.ic_logo),
    Recipe("Milk Tart", "Custard tart dessert", "1 hour", 4.8, R.drawable.ic_logo),
    Recipe("Bunny Chow", "Hollowed bread with curry", "45 mins", 4.6, R.drawable.ic_logo)
)

fun getFavouriteEvents(): List<Event> = listOf(
    Event(
        "Cape Town Food Festival",
        "Celebrate local cuisine with top chefs",
        "Green Point Stadium, Cape Town",
        "Sat, 15 Dec • 10:00 AM",
        "R150",
        245,
        R.drawable.ic_logo,
        "Food Festival"
    ),
    Event(
        "Traditional Braai Masterclass",
        "Learn the art of South African braai",
        "Johannesburg Food Academy",
        "Sun, 16 Dec • 2:00 PM",
        "R200",
        32,
        R.drawable.ic_logo,
        "Cooking Class"
    )
)
