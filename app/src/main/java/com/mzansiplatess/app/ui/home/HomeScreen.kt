package com.mzansiplatess.app.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mzansiplatess.app.model.Restaurant

@Composable
fun HomeScreenComposable(
    viewModel: HomeViewModel = viewModel() // uses default constructor
) {
    val restaurants by viewModel.restaurants.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadRestaurants() }

    Scaffold { inner ->
        Box(modifier = Modifier.padding(inner)) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else if (error != null) {
                Text("Error: $error")
            } else {
                LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(restaurants) { r ->
                        RestaurantRow(r)
                    }
                }
            }
        }
    }
}

@Composable
fun RestaurantRow(restaurant: Restaurant) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp)) {
            val image = restaurant.image_url
            if (!image.isNullOrEmpty()) {
                AsyncImage(model = image, contentDescription = restaurant.name, modifier = Modifier.size(80.dp))
            } else {
                // placeholder
                Box(modifier = Modifier.size(80.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(restaurant.name, style = MaterialTheme.typography.titleMedium)
                restaurant.description?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
                restaurant.city?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
            }
        }
    }
}


