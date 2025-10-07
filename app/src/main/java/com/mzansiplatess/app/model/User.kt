package com.mzansiplatess.app.model

data class User(
    val _id: String? = null,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null,
    val phone: String? = null,
    val city: String? = null,
    val bio: String? = null,
    val preferences: UserPreferences? = null,
    val isEmailVerified: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class UserPreferences(
    val dietaryRestrictions: List<String> = emptyList(),
    val favoriteCuisines: List<String> = emptyList(),
    val notificationSettings: NotificationSettings = NotificationSettings(),
    val language: String = "en",
    val currency: String = "ZAR"
)

data class NotificationSettings(
    val pushNotifications: Boolean = true,
    val emailNotifications: Boolean = true,
    val eventReminders: Boolean = true,
    val newRecipes: Boolean = true,
    val restaurantUpdates: Boolean = true
)
