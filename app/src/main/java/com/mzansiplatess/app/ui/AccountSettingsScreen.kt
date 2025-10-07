package com.mzansiplatess.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mzansiplatess.app.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mzansiplatess.app.ui.viewmodel.AppSettingsViewModel
import com.mzansiplatess.app.ui.viewmodel.AppThemeMode
import androidx.compose.ui.tooling.preview.Preview
import android.content.Context
import android.content.Intent
import android.net.Uri
// import androidx.navigation.testing.TestNavHostController

// Navigation routes for the app
object SettingsRoutes {
    const val EDIT_PROFILE = "edit_profile"
    const val CHANGE_PASSWORD = "change_password"
    const val MY_RECIPES = "my_recipes"
    const val FAVORITES = "favorites"
    const val SHOPPING_LIST = "shopping_list"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettingsScreen(
    navController: NavHostController,
    appSettingsViewModel: AppSettingsViewModel? = null
) {
    // ==================== STATE MANAGEMENT ====================
    val context = LocalContext.current
    val settingsVm: AppSettingsViewModel = appSettingsViewModel ?: viewModel()
    val settings by settingsVm.settings.collectAsState()

    // Dialog states
    val dialogState = remember {
        mutableStateOf(DialogState.None)
    }


    // ==================== UI COMPOSITION ====================
    Scaffold(
        topBar = { SettingsTopBar(navController) }
    ) { innerPadding ->
        SettingsContent(
            innerPadding = innerPadding,
            settings = settings,
            settingsVm = settingsVm,
            dialogState = dialogState,
            navController = navController
        )
    }

    // ==================== DIALOGS ====================
    SettingsDialogs(
        dialogState = dialogState,
        settingsVm = settingsVm
    )
}


// ==================== DIALOG STATE ====================
enum class DialogState {
    None,
    PrivacyPolicy,
    AboutApp,
    HelpSupport,
    DataUsage,
    DietaryRestrictions,
    LogoutConfirmation
}

// ==================== TOP BAR ====================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsTopBar(navController: NavHostController) {
    LargeTopAppBar(
                title = { 
                    Text(
                        "Account Settings", 
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
        colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(0xFFFF6F00)
                )
            )
        }

// ==================== MAIN CONTENT ====================
@Composable
private fun SettingsContent(
    innerPadding: PaddingValues,
    settings: com.mzansiplatess.app.ui.viewmodel.AppSettings,
    settingsVm: AppSettingsViewModel,
    dialogState: MutableState<DialogState>,
    navController: NavHostController
) {
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Profile Section
        item(key = "profile") {
            ProfileCard(navController)
            }

            // Account Settings Section
        item(key = "account_settings") {
            AccountSettingsSection(navController)
        }

        // Recipe Management Section
        item(key = "recipe_management") {
            RecipeManagementSection(navController)
        }

        // Recipe Preferences Section
        item(key = "recipe_preferences") {
            RecipePreferencesSection(
                onDifficultyFilterChange = { /* Difficulty filter logic */ },
                onCookingTimeFilterChange = { /* Cooking time filter logic */ },
                onDietaryRestrictionsChange = { dialogState.value = DialogState.DietaryRestrictions }
            )
        }

        // Recipe Management Section
        item(key = "recipe_management") {
            RecipeManagementSection(navController)
        }

            // Notification Settings Section
        item(key = "notification_settings") {
                NotificationSettingsSection(
                    settings = settings,
                onPushNotificationChange = { settingsVm.setPushNotifications(it) },
                onPromotionsChange = { settingsVm.setPromotions(it) },
                onAppUpdatesChange = { settingsVm.setAppUpdates(it) }
                )
            }

            // Theme Settings Section
        item(key = "theme_settings") {
                ThemeSettingsSection(
                    currentTheme = settings.themeMode,
                onThemeChange = { settingsVm.setTheme(it) }
            )
        }

        // Display Settings Section
        item(key = "display_settings") {
            DisplaySettingsSection(
                settings = settings,
                onFontSizeChange = { /* Font size change logic */ },
                onAnimationsChange = { /* Animation toggle logic */ }
            )
        }

        // Accessibility Section
        item(key = "accessibility") {
            AccessibilitySection()
        }

        // Privacy & Security Section
        item(key = "privacy_security") {
            PrivacySecuritySection(dialogState)
        }

        // App Information Section
        item(key = "app_information") {
            AppInformationSection(dialogState)
        }

        // Advanced Settings Section
        item(key = "advanced_settings") {
            AdvancedSettingsSection(
                settings = settings,
                onDebugModeChange = { /* Debug mode toggle */ },
                onAnalyticsChange = { /* Analytics toggle */ }
            )
        }
    }
}

// ==================== DIALOGS ====================
@Composable
private fun SettingsDialogs(
    dialogState: MutableState<DialogState>,
    settingsVm: AppSettingsViewModel
) {
    when (dialogState.value) {
        DialogState.PrivacyPolicy -> {
            PrivacyPolicyDialog(
                onDismiss = { dialogState.value = DialogState.None }
            )
        }
        DialogState.AboutApp -> {
            AboutAppDialog(
                onDismiss = { dialogState.value = DialogState.None }
            )
        }
        DialogState.HelpSupport -> {
            HelpSupportDialog(
                onDismiss = { dialogState.value = DialogState.None }
            )
        }
        DialogState.DataUsage -> {
            DataUsageDialog(
                onDismiss = { dialogState.value = DialogState.None }
            )
        }
        DialogState.DietaryRestrictions -> {
            DietaryRestrictionsDialog(
                onDismiss = { dialogState.value = DialogState.None }
            )
        }
        DialogState.LogoutConfirmation -> {
            LogoutConfirmationDialog(
                onDismiss = { dialogState.value = DialogState.None },
                onConfirm = { /* Implement logout logic */ }
            )
        }
        DialogState.None -> { /* No dialog */ }
    }
}

// Data classes for settings
data class SettingsItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)

// ==================== SECTION COMPONENTS ====================

// Profile Card Component
@Composable
private fun ProfileCard(navController: NavHostController) {
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
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Otshepegile Mojadibe",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "youremail@example.com",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            
            IconButton(
                onClick = { navController.navigate("edit_profile") }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = Color(0xFFFF6F00)
                )
            }
        }
    }
}

// Account Settings Section
@Composable
private fun AccountSettingsSection(navController: NavHostController) {
    SettingsSection(
        title = "Account Settings",
        items = listOf(
            SettingsItem(
                title = "Edit Profile",
                icon = Icons.Default.Edit,
                onClick = { navController.navigate("edit_profile") }
            ),
            SettingsItem(
                title = "Change Password",
                icon = Icons.Default.Lock,
                onClick = { navController.navigate("change_password") }
            ),
            SettingsItem(
                title = "My Recipes",
                icon = Icons.Default.Settings,
                onClick = { navController.navigate("my_recipes") }
            )
        )
    )
}

// Recipe Management Section
@Composable
private fun RecipeManagementSection(navController: NavHostController) {
    SettingsSection(
        title = "Recipe Management",
        items = listOf(
            SettingsItem(
                title = "My Favorites",
                icon = Icons.Default.Star,
                onClick = { navController.navigate("favorites") }
            ),
            SettingsItem(
                title = "Shopping List",
                icon = Icons.Default.Settings,
                onClick = { navController.navigate("shopping_list") }
            ),
        )
    )
}

// Accessibility Section
@Composable
private fun AccessibilitySection() {
    SettingsSection(
        title = "Accessibility",
        items = listOf(
            SettingsItem(
                title = "High Contrast",
                icon = Icons.Default.Visibility,
                onClick = { /* High contrast toggle */ }
            ),
            SettingsItem(
                title = "Screen Reader",
                icon = Icons.Default.Accessibility,
                onClick = { /* Screen reader settings */ }
            ),
            SettingsItem(
                title = "Large Text",
                icon = Icons.Default.Visibility,
                onClick = { /* Large text toggle */ }
            )
        )
    )
}

// Privacy & Security Section
@Composable
private fun PrivacySecuritySection(
    dialogState: MutableState<DialogState>
) {
    SettingsSection(
        title = "Privacy & Security",
        items = listOf(
            SettingsItem(
                title = "Privacy Policy",
                icon = Icons.Default.PrivacyTip,
                onClick = { dialogState.value = DialogState.PrivacyPolicy }
            ),
            SettingsItem(
                title = "Data Usage",
                icon = Icons.Default.Storage,
                onClick = { dialogState.value = DialogState.DataUsage }
            ),
            SettingsItem(
                title = "Clear Cache",
                icon = Icons.Default.Download,
                onClick = { /* TODO: Implement clear cache functionality */ }
            )
        )
    )
}

// App Information Section
@Composable
private fun AppInformationSection(
    dialogState: MutableState<DialogState>
) {
    SettingsSection(
        title = "App Information",
        items = listOf(
            SettingsItem(
                title = "About MzansiPlatess",
                icon = Icons.Default.Info,
                onClick = { dialogState.value = DialogState.AboutApp }
            ),
            SettingsItem(
                title = "Rate App",
                icon = Icons.Default.Star,
                onClick = { /* TODO: Implement rate app functionality */ }
            ),
            SettingsItem(
                title = "Share App",
                icon = Icons.Default.Share,
                onClick = { /* TODO: Implement share app functionality */ }
            ),
            SettingsItem(
                title = "Help & Support",
                icon = Icons.Default.Help,
                onClick = { dialogState.value = DialogState.HelpSupport }
            ),
            SettingsItem(
                title = "Report Bug",
                icon = Icons.Default.BugReport,
                onClick = { /* Report bug logic */ }
            )
        )
    )
}

// Logout Confirmation Dialog
@Composable
private fun LogoutConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Logout") },
        text = { Text("Are you sure you want to logout?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Logout", color = Color(0xFFFF6F00))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Settings Section Component
@Composable
private fun SettingsSection(
    title: String,
    items: List<SettingsItem>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
            )
            
            items.forEachIndexed { index, item ->
                SettingsItemRow(
                    item = item,
                    showDivider = index < items.size - 1
                )
            }
        }
    }
}

// Individual Settings Item Row
@Composable
private fun SettingsItemRow(
    item: SettingsItem,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { item.onClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = Color(0xFFFF6F00),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Go",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
        if (showDivider) {
            HorizontalDivider(
                color = Color(0xFFE0E0E0),
                thickness = 1.dp,
                modifier = Modifier.padding(start = 52.dp)
            )
        }
    }
}

// Notification Settings Section
@Composable
private fun NotificationSettingsSection(
    settings: com.mzansiplatess.app.ui.viewmodel.AppSettings,
    onPushNotificationChange: (Boolean) -> Unit,
    onPromotionsChange: (Boolean) -> Unit,
    onAppUpdatesChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Text(
                text = "Notification Settings",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
            )
            
            NotificationToggleItem(
                title = "New Recipe Notifications",
                checked = settings.pushNotifications,
                onCheckedChange = onPushNotificationChange,
                showDivider = true
            )
            
            NotificationToggleItem(
                title = "Cooking Reminders",
                checked = settings.promotions,
                onCheckedChange = onPromotionsChange,
                showDivider = true
            )
            
            NotificationToggleItem(
                title = "Recipe Updates",
                checked = settings.appUpdates,
                onCheckedChange = onAppUpdatesChange,
                showDivider = false
            )
        }
    }
}

// Notification Toggle Item
@Composable
private fun NotificationToggleItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                color = Color.Black
            )
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFFFF6F00),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        }
        if (showDivider) {
            HorizontalDivider(
                color = Color(0xFFE0E0E0),
                thickness = 1.dp
            )
        }
    }
}

// Theme Settings Section
@Composable
private fun ThemeSettingsSection(
    currentTheme: AppThemeMode,
    onThemeChange: (AppThemeMode) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Text(
                text = "Theme Settings",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
            )
            
            val themes = listOf(
                "System" to AppThemeMode.SYSTEM,
                "Light" to AppThemeMode.LIGHT,
                "Dark" to AppThemeMode.DARK
            )
            
            themes.forEachIndexed { index, (label, mode) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onThemeChange(mode) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (currentTheme == mode),
                        onClick = { onThemeChange(mode) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFFFF6F00)
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = label,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
                if (index < themes.size - 1) {
                    HorizontalDivider(
                        color = Color(0xFFE0E0E0),
                        thickness = 1.dp,
                        modifier = Modifier.padding(start = 52.dp)
                    )
                }
            }
        }
    }
}


// Recipe Preferences Section
@Composable
private fun RecipePreferencesSection(
    onDifficultyFilterChange: (String) -> Unit,
    onCookingTimeFilterChange: (String) -> Unit,
    onDietaryRestrictionsChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Text(
                text = "Recipe Preferences",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
            )
            
            // Difficulty Level Selection
            Text(
                text = "Preferred Difficulty",
                fontSize = 15.sp,
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 4.dp)
            )
            
            val difficultyLevels = listOf("Easy", "Medium", "Hard", "Expert")
            var selectedDifficulty by remember { mutableStateOf("Easy") }
            
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(difficultyLevels) { level ->
                    FilterChip(
                        onClick = { 
                            selectedDifficulty = level
                            onDifficultyFilterChange(level)
                        },
                        label = { Text(level) },
                        selected = selectedDifficulty == level,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFFF6F00),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Cooking Time Preference
            Text(
                text = "Cooking Time",
                fontSize = 15.sp,
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 4.dp)
            )
            
            val cookingTimes = listOf("Quick (15-30 min)", "Medium (30-60 min)", "Long (1+ hours)")
            var selectedCookingTime by remember { mutableStateOf("Quick (15-30 min)") }
            
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cookingTimes) { time ->
                    FilterChip(
                        onClick = { 
                            selectedCookingTime = time
                            onCookingTimeFilterChange(time)
                        },
                        label = { Text(time) },
                        selected = selectedCookingTime == time,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFFF6F00),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Dietary Restrictions
            SettingsItemRow(
                item = SettingsItem(
                    title = "Dietary Restrictions",
                    icon = Icons.Default.Settings,
                    onClick = { /* Show dietary dialog */ }
                ),
                showDivider = false
            )
        }
    }
}

// Dietary Restrictions Dialog
@Composable
private fun DietaryRestrictionsDialog(
    onDismiss: () -> Unit
) {
    val dietaryOptions = listOf(
        "Vegetarian", "Vegan", "Gluten-Free", "Dairy-Free", 
        "Nut-Free", "Halal", "Kosher", "Low-Carb", "Keto"
    )
    var selectedRestrictions by remember { mutableStateOf(setOf<String>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Dietary Restrictions") },
        text = {
            Column {
                Text("Select your dietary preferences:")
                Spacer(modifier = Modifier.height(8.dp))
                dietaryOptions.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { 
                                selectedRestrictions = if (selectedRestrictions.contains(option)) {
                                    selectedRestrictions - option
                                } else {
                                    selectedRestrictions + option
                                }
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = selectedRestrictions.contains(option),
                            onCheckedChange = { 
                                selectedRestrictions = if (selectedRestrictions.contains(option)) {
                                    selectedRestrictions - option
                                } else {
                                    selectedRestrictions + option
                                }
                            },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFF6F00))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(option)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Save", color = Color(0xFFFF6F00))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Display Settings Section
@Composable
private fun DisplaySettingsSection(
    settings: com.mzansiplatess.app.ui.viewmodel.AppSettings,
    onFontSizeChange: (String) -> Unit,
    onAnimationsChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
            Column {
            Text(
                text = "Display Settings",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
            )
            
            // Font Size Selection
            Text(
                text = "Font Size",
                fontSize = 15.sp,
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 4.dp)
            )
            
            val fontSizes = listOf("Small", "Medium", "Large", "Extra Large")
            var selectedFontSize by remember { mutableStateOf("Medium") }
            
            LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(fontSizes) { size ->
                    FilterChip(
                        onClick = { 
                            selectedFontSize = size
                            onFontSizeChange(size)
                        },
                        label = { Text(size) },
                        selected = selectedFontSize == size,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFFF6F00),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Animations Toggle
            NotificationToggleItem(
                title = "Enable Animations",
                checked = true, // Default to true
                onCheckedChange = onAnimationsChange,
                showDivider = false
            )
        }
    }
}

// Advanced Settings Section
@Composable
private fun AdvancedSettingsSection(
    settings: com.mzansiplatess.app.ui.viewmodel.AppSettings,
    onDebugModeChange: (Boolean) -> Unit,
    onAnalyticsChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Text(
                text = "Advanced Settings",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
            )
            
            NotificationToggleItem(
                title = "Debug Mode",
                checked = false, // Default to false
                onCheckedChange = onDebugModeChange,
                showDivider = true
            )
            
            NotificationToggleItem(
                title = "Analytics & Crash Reports",
                checked = true, // Default to true
                onCheckedChange = onAnalyticsChange,
                showDivider = false
            )
        }
    }
}

// Privacy Policy Dialog
@Composable
private fun PrivacyPolicyDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Privacy Policy") },
        text = {
            Column {
                Text("Your privacy is important to us. This app collects minimal data necessary for functionality:")
                Spacer(modifier = Modifier.height(8.dp))
                Text("• App usage statistics (anonymized)")
                Text("• Crash reports for app improvement")
                Text("• User preferences and settings")
                Spacer(modifier = Modifier.height(8.dp))
                Text("We do not sell or share your personal data with third parties.")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("I Understand", color = Color(0xFFFF6F00))
            }
        }
    )
}

// About App Dialog
@Composable
private fun AboutAppDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("About MzansiPlatess") },
        text = {
            Column {
                Text("Version: 1.0.0", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("MzansiPlatess is your go-to app for discovering and sharing authentic South African recipes.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Features:")
                Text("• Browse traditional recipes")
                Text("• Save your favorites")
                Text("• Share with friends")
                Text("• Multi-language support")
                Spacer(modifier = Modifier.height(8.dp))
                Text("© 2024 MzansiPlatess. All rights reserved.")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = Color(0xFFFF6F00))
            }
        }
    )
}

// Help & Support Dialog
@Composable
private fun HelpSupportDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Help & Support") },
        text = {
            Column {
                Text("Need help? We're here for you!")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Contact us:")
                Text("• Email: support@mzansiplatess.com")
                Text("• Phone: +27 11 123 4567")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Frequently Asked Questions:")
                Text("• How to save recipes?")
                Text("• How to change language?")
                Text("• How to share recipes?")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Visit our website for more help: www.mzansiplatess.com")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Got it", color = Color(0xFFFF6F00))
            }
        }
    )
}

// Data Usage Dialog
@Composable
private fun DataUsageDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Data Usage") },
        text = {
            Column {
                Text("Data Usage Information:")
                Spacer(modifier = Modifier.height(8.dp))
                Text("• App Size: 25.6 MB")
                Text("• Cache: 12.3 MB")
                Text("• User Data: 2.1 MB")
                Text("• Total: 40.0 MB")
                Spacer(modifier = Modifier.height(8.dp))
                Text("You can clear cache to free up space.")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Clear Cache", color = Color(0xFFFF6F00))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Preview(showBackground = true, name = "Account Settings Screen")
@Composable
fun AccountSettingsScreenPreview() {
    
    AccountSettingsScreen(navController = rememberNavController())
}

@Preview(showBackground = true, name = "Profile Card")
@Composable
fun ProfileCardPreview() {
    val mockNavController = rememberNavController()
    ProfileCard(navController = mockNavController)
}

@Preview(showBackground = true, name = "Settings Section")
@Composable
fun SettingsSectionPreview() {
    SettingsSection(
        title = "Account Settings",
        items = listOf(
            SettingsItem(
                title = "Edit Profile",
                icon = Icons.Default.Edit,
                onClick = { }
            ),
            SettingsItem(
                title = "Change Password",
                icon = Icons.Default.Lock,
                onClick = { }
            )
        )
    )
}

@Preview(showBackground = true, name = "Notification Settings")
@Composable
fun NotificationSettingsPreview() {
    val mockSettings = com.mzansiplatess.app.ui.viewmodel.AppSettings(
        pushNotifications = true,
        promotions = false,
        appUpdates = true,
        themeMode = AppThemeMode.SYSTEM,
        languageCode = "en"
    )
    
    NotificationSettingsSection(
        settings = mockSettings,
        onPushNotificationChange = { },
        onPromotionsChange = { },
        onAppUpdatesChange = { }
    )
}

@Preview(showBackground = true, name = "Theme Settings")
@Composable
fun ThemeSettingsPreview() {
    ThemeSettingsSection(
        currentTheme = AppThemeMode.SYSTEM,
        onThemeChange = { }
    )
}

@Preview(showBackground = true, name = "Recipe Preferences")
@Composable
fun RecipePreferencesPreview() {
    RecipePreferencesSection(
        onDifficultyFilterChange = { },
        onCookingTimeFilterChange = { },
        onDietaryRestrictionsChange = { }
    )
}

