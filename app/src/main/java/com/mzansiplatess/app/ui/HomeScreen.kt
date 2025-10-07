package com.mzansiplatess.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.statusBarsPadding
import com.mzansiplatess.app.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mzansiplatess.app.ui.viewmodel.LanguageViewModel


// ----------------- Main Home Screen -----------------
@Composable
fun HomeScreen(navController: NavHostController, languageViewModel: LanguageViewModel) {
    var selectedTab by remember { mutableStateOf("home") }

    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = { BottomNavigationBar(navController, "home") }
    ) { innerPadding ->
        val restaurants = listOf(
            Restaurant("Braai Spot", "Authentic SA Braai", 4.5, R.drawable.ic_logo),
            Restaurant("Kota King", "Best Kotas in town", 4.8, R.drawable.ic_logo),
            Restaurant("Shisa Nyama", "Local grill experience", 4.2, R.drawable.ic_logo)
        )

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { QuickFiltersSection() }
            item { FilterChipsRow() }
            item { FeaturedRestaurantsHeader() }
            items(restaurants) { restaurant ->
                RestaurantCard(restaurant)
            }
        }
    }
}

// ----------------- Top Bar -----------------
@Composable
fun HomeTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF9800)) // orange theme
            .statusBarsPadding()
            .padding(12.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            placeholder = { Text("Search restaurants, recipes, or locations") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            shape = RoundedCornerShape(12.dp)
        )
    }
}

// ----------------- Quick Filters Section -----------------
@Composable
fun QuickFiltersSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Quick Filters",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.FilterList, contentDescription = "Filter Icon")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "More Filters",
                fontSize = 14.sp,
                color = Color(0xFFFF9800)
            )
        }
    }
}


// ----------------- Filter Chips Row -----------------
@Composable
fun FilterChipsRow() {
    val filters = listOf("Nearby", "Popular", "Traditional", "Vegetarian")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 8.dp)) {
        items(filters) { filter ->
            AssistChip(
                onClick = { /* TODO: handle filter */ },
                label = { Text(filter) },
                shape = RoundedCornerShape(50),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.White,
                    labelColor = Color(0xFFFF9800)
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)

            )
        }
    }
}

// ----------------- Featured Restaurants Header -----------------
@Composable
fun FeaturedRestaurantsHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Featured Restaurants", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("View All", fontSize = 14.sp, color = Color(0xFFFF9800))
    }
}

// ----------------- Restaurant Data -----------------
data class Restaurant(val name: String, val description: String, val rating: Double, val image: Int)

@Composable
fun RestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = restaurant.image),
                contentDescription = restaurant.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(restaurant.name, fontWeight = FontWeight.Bold)
                Text(restaurant.description, fontSize = 12.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFA000),
                        modifier = Modifier.size(16.dp)
                    )
                    Text("${restaurant.rating}", fontSize = 12.sp)
                }
            }
        }
    }
}

// This BottomNavigationBar is now handled by the unified BottomNavigationBar.kt
// Keeping this for reference but it's not used anymore

