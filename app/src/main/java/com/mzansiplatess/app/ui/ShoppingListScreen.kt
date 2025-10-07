package com.mzansiplatess.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(navController: NavHostController) {
    var newItem by remember { mutableStateOf("") }
    var shoppingItems by remember { mutableStateOf(
        listOf(
            ShoppingItem("Ground beef", 500, "g", false),
            ShoppingItem("Onions", 2, "pieces", false),
            ShoppingItem("Curry powder", 1, "tbsp", false),
            ShoppingItem("Bread", 1, "loaf", true),
            ShoppingItem("Milk", 1, "liter", false),
            ShoppingItem("Eggs", 6, "pieces", false),
            ShoppingItem("Raisins", 50, "g", false),
            ShoppingItem("Bay leaves", 3, "pieces", false)
        )
    )}
    
    val completedCount = shoppingItems.count { it.isCompleted }
    val totalCount = shoppingItems.size
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Shopping List",
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF6F00)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add new item */ },
                containerColor = Color(0xFFFF6F00)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item",
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
            // Progress Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E8)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = Color(0xFFFF6F00),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Shopping Progress",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                        Text(
                            text = "$completedCount of $totalCount items completed",
                            fontSize = 14.sp,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }
            
            // Add New Item
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newItem,
                        onValueChange = { newItem = it },
                        placeholder = { Text("Add new item...") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (newItem.isNotBlank()) {
                                shoppingItems = shoppingItems + ShoppingItem(newItem.trim(), 1, "piece", false)
                                newItem = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F00))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Shopping List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(shoppingItems) { item ->
                    ShoppingItemCard(
                        item = item,
                        onToggleComplete = { 
                            shoppingItems = shoppingItems.map { 
                                if (it == item) it.copy(isCompleted = !it.isCompleted) else it 
                            }
                        },
                        onDelete = {
                            shoppingItems = shoppingItems.filter { it != item }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ShoppingItemCard(
    item: ShoppingItem,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isCompleted) Color(0xFFF5F5F5) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = { onToggleComplete() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFFF6F00)
                )
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (item.isCompleted) Color.Gray else Color.Black,
                    textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = "${item.quantity} ${item.unit}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete item",
                    tint = Color.Red
                )
            }
        }
    }
}

data class ShoppingItem(
    val name: String,
    val quantity: Int,
    val unit: String,
    val isCompleted: Boolean
)

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    ShoppingListScreen(navController = androidx.navigation.compose.rememberNavController())
}
