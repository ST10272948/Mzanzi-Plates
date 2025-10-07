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
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EventsScreen(navController: NavHostController) {
    var selectedFilter by remember { mutableStateOf("All") }

    Scaffold(
        topBar = { EventsTopBar() },
        bottomBar = { BottomNavigationBar(navController, "events") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Filter chips
            EventFilters(selectedFilter) { selectedFilter = it }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Upcoming events
            Text(
                text = "Upcoming Events",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(getSampleEvents()) { event ->
                    EventCard(event)
                }
            }
        }
    }
}

@Composable
fun EventsTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF9800))
            .padding(16.dp)
    ) {
        Text(
            text = "Events",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Discover food events in your area",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.9f)
        )
    }
}

@Composable
fun EventFilters(selectedFilter: String, onFilterSelected: (String) -> Unit) {
    val filters = listOf("All", "Today", "This Week", "Food Festival", "Cooking Class", "Wine Tasting")
    
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(filters) { filter ->
            FilterChip(
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) },
                selected = selectedFilter == filter,
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
fun EventCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Event image
            Image(
                painter = painterResource(id = event.image),
                contentDescription = event.name,
                modifier = Modifier
                    .width(140.dp)
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
                        maxLines = 2,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = event.location,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = "Date",
                            tint = Color(0xFFFF9800),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = event.dateTime,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = event.price,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF9800)
                        )
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.People,
                                contentDescription = "Attendees",
                                tint = Color.Gray,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${event.attendees} going",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

// Sample data
data class Event(
    val name: String,
    val description: String,
    val location: String,
    val dateTime: String,
    val price: String,
    val attendees: Int,
    val image: Int,
    val category: String = "Food Festival"
)

fun getSampleEvents(): List<Event> = listOf(
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
    ),
    Event(
        "Wine & Cheese Pairing",
        "Explore local wines with artisanal cheeses",
        "Stellenbosch Winery",
        "Fri, 20 Dec • 6:00 PM",
        "R300",
        18,
        R.drawable.ic_logo,
        "Wine Tasting"
    ),
    Event(
        "Street Food Safari",
        "Food truck tour of Johannesburg",
        "Maboneng Precinct",
        "Sat, 22 Dec • 12:00 PM",
        "R80",
        156,
        R.drawable.ic_logo,
        "Food Festival"
    ),
    Event(
        "Baking Workshop",
        "Master traditional South African desserts",
        "Pretoria Culinary School",
        "Sun, 23 Dec • 9:00 AM",
        "R180",
        24,
        R.drawable.ic_logo,
        "Cooking Class"
    ),
    Event(
        "Craft Beer Festival",
        "Local breweries showcase their best",
        "Durban Harbour",
        "Sat, 28 Dec • 3:00 PM",
        "R120",
        89,
        R.drawable.ic_logo,
        "Food Festival"
    )
)
