package com.mzansiplatess.app.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mzansiplatess.app.R
import androidx.compose.foundation.layout.statusBarsPadding
import com.mzansiplatess.app.ui.viewmodel.LanguageViewModel

@Composable
fun ProfileScreen(navController: NavHostController, languageViewModel: LanguageViewModel) {
    Scaffold(
        topBar = { ProfileTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ProfileHeader()
            Spacer(modifier = Modifier.height(16.dp))
            AchievementsSection()
            Spacer(modifier = Modifier.height(24.dp))
            SettingsSection(navController, languageViewModel)
        }
    }
}

@Composable
fun ProfileTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .padding(12.dp)
    ) {
        Text(
            "Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

// ----------------- Profile Header -----------------
@Composable
fun ProfileHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Change Profile",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(24.dp)
                            .clickable { /* TODO: Pick photo */ }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text("John Doe", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text("Johannesburg, South Africa", fontSize = 14.sp, color = Color.Gray)
                    Text("Food Enthusiast", fontSize = 14.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 1.dp)

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ProfileStat("0", "Reviews")
                ProfileStat("0", "Favourites")
                ProfileStat("0", "Recipes")
                ProfileStat("0", "Followers")
                ProfileStat("0", "Following")
            }
        }
    }
}

@Composable
fun ProfileStat(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(number, fontWeight = FontWeight.Bold, color = Color(0xFFFF9800), fontSize = 16.sp)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

// ----------------- Achievements Section -----------------
@Composable
fun AchievementsSection() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.EmojiEvents, contentDescription = "Achievements", tint = Color(0xFFFF9800))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Achievements", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AchievementItem("Food Explorer", "Visited 25+ restaurants")
                AchievementItem("Traditional Taste", "Tried 10 traditional dishes")
                AchievementItem("Review Master", "Written 25+ reviews")
                AchievementItem("Recipe Creator", "Shared 5+ recipes")
            }
        }
    }
}

@Composable
fun AchievementItem(title: String, description: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(description, fontSize = 12.sp, color = Color.Gray)
    }
}

// ----------------- Settings Section -----------------
@Composable
fun SettingsSection(navController: NavHostController, languageViewModel: LanguageViewModel) {
    Column {
        Text("Settings", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))

        SettingItem("Account Settings", Icons.Default.Person) { navController.navigate("account_settings") }
        SettingItem("Edit Profile", Icons.Default.Edit) { navController.navigate("edit_profile") }
        SettingItem("Change Password", Icons.Default.Lock) { navController.navigate("change_password") }

        SettingItem("Privacy Settings", Icons.Default.Favorite) {
            // Navigate to Privacy screen if exists
        }

        SettingItem("Help & Support", Icons.Default.Help) {
            // Navigate to Help screen if exists
        }
    }
}

@Composable
fun SettingItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {
        Icon(icon, contentDescription = title, tint = Color(0xFFFF9800))
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, fontSize = 14.sp)
    }
}
