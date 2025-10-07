# MzansiPlatess Navigation Setup

## Overview
This document explains how to integrate the new navigation system with your existing MzansiPlatess app.

## New Screens Added

### 1. EditProfileScreen
- **Route**: `SettingsRoutes.EDIT_PROFILE`
- **Features**: Edit user profile information, change profile picture
- **Navigation**: Accessible from Account Settings → Profile Card

### 2. ChangePasswordScreen
- **Route**: `SettingsRoutes.CHANGE_PASSWORD`
- **Features**: Change user password with security validation
- **Navigation**: Accessible from Account Settings → Security Settings

### 3. MyRecipesScreen
- **Route**: `SettingsRoutes.MY_RECIPES`
- **Features**: View and manage user's recipes, add new recipes
- **Navigation**: Accessible from Account Settings → Recipe Management

### 4. FavoritesScreen
- **Route**: `SettingsRoutes.FAVORITES`
- **Features**: View and manage favorite recipes
- **Navigation**: Accessible from Account Settings → Recipe Management

### 5. ShoppingListScreen
- **Route**: `SettingsRoutes.SHOPPING_LIST`
- **Features**: Manage shopping list with checkboxes and progress tracking
- **Navigation**: Accessible from Account Settings → Recipe Management

## Integration Steps

### Step 1: Update Your MainActivity
Replace your existing MainActivity with the provided `MainActivity.kt` or integrate the navigation:

```kotlin
@Composable
fun YourMainApp() {
    val navController = rememberNavController()
    
    // Use SettingsNavigation for the settings flow
    SettingsNavigation(navController = navController)
}
```

### Step 2: Update Your Main Navigation
If you have a main navigation setup, add the settings navigation as a nested graph:

```kotlin
NavHost(navController = navController, startDestination = "home") {
    // Your existing routes
    composable("home") { HomeScreen() }
    composable("recipes") { RecipesScreen() }
    
    // Settings navigation
    navigation(
        startDestination = "account_settings",
        route = "settings"
    ) {
        composable("account_settings") {
            AccountSettingsScreen(navController = navController)
        }
        composable(SettingsRoutes.EDIT_PROFILE) {
            EditProfileScreen(navController = navController)
        }
        composable(SettingsRoutes.CHANGE_PASSWORD) {
            ChangePasswordScreen(navController = navController)
        }
        composable(SettingsRoutes.MY_RECIPES) {
            MyRecipesScreen(navController = navController)
        }
        composable(SettingsRoutes.FAVORITES) {
            FavoritesScreen(navController = navController)
        }
        composable(SettingsRoutes.SHOPPING_LIST) {
            ShoppingListScreen(navController = navController)
        }
    }
}
```

### Step 3: Navigation from Other Screens
To navigate to settings from other parts of your app:

```kotlin
// Navigate to settings
navController.navigate("settings")

// Navigate to specific settings screen
navController.navigate(SettingsRoutes.EDIT_PROFILE)
navController.navigate(SettingsRoutes.MY_RECIPES)
```

## Navigation Features

### 1. Back Navigation
All screens include proper back navigation using `navController.popBackStack()`

### 2. Consistent UI
- All screens use the same orange theme (`Color(0xFFFF6F00)`)
- Consistent TopAppBar design
- Material Design 3 components

### 3. State Management
- Each screen manages its own state
- Proper state handling for forms and interactions

## Customization

### Theme Colors
To change the app theme, update the color values in each screen:
```kotlin
Color(0xFFFF6F00) // Primary orange color
```

### Adding New Screens
1. Create a new screen composable
2. Add route to `SettingsRoutes` object
3. Add composable to `SettingsNavigation` function
4. Update navigation calls in `SettingsNavigationHandler`

### Data Integration
- Connect screens to your ViewModels
- Add data persistence as needed
- Integrate with your existing data models

## Testing

### Preview Functions
Each screen includes a preview function for testing:
```kotlin
@Preview(showBackground = true)
@Composable
fun ScreenNamePreview() {
    ScreenName(navController = rememberNavController())
}
```

### Navigation Testing
Use `TestNavHostController` for navigation testing:
```kotlin
val testNavController = TestNavHostController(LocalContext.current)
```

## Troubleshooting

### Common Issues
1. **Import Errors**: Make sure all imports are correct
2. **Navigation Errors**: Check route names match exactly
3. **Preview Errors**: Use `rememberNavController()` in previews

### Dependencies
Ensure you have the required dependencies in your `build.gradle.kts`:
```kotlin
implementation("androidx.navigation:navigation-compose:2.7.5")
implementation("androidx.compose.material3:material3:1.1.2")
```

## Next Steps
1. Test all navigation flows
2. Integrate with your existing data models
3. Add proper error handling
4. Implement data persistence
5. Add unit tests for navigation logic
