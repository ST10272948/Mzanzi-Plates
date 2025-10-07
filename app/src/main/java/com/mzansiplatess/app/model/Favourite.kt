package com.mzansiplatess.app.model

data class Favourite(
    val _id: String? = null,
    val userId: String,
    val itemType: FavouriteType,
    val itemId: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

enum class FavouriteType {
    RESTAURANT,
    RECIPE,
    EVENT
}

// Helper data classes for UI display
data class FavouriteRestaurant(
    val favouriteId: String,
    val restaurant: Restaurant
)

data class FavouriteRecipe(
    val favouriteId: String,
    val recipe: Recipe
)

data class FavouriteEvent(
    val favouriteId: String,
    val event: Event
)
