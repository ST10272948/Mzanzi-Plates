package com.mzansiplatess.app.model

data class Recipe(
    val _id: String? = null,
    val name: String,
    val description: String,
    val category: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val prepTime: Int, // in minutes
    val cookTime: Int, // in minutes
    val servings: Int,
    val difficulty: String, // "Easy", "Medium", "Hard"
    val rating: Double,
    val image_url: String,
    val author: String? = null,
    val tags: List<String> = emptyList(),
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    val totalTime: Int get() = prepTime + cookTime
    
    val difficultyLevel: Int get() = when (difficulty.lowercase()) {
        "easy" -> 1
        "medium" -> 2
        "hard" -> 3
        else -> 1
    }
    
    fun getFormattedTime(): String {
        return when {
            totalTime < 60 -> "${totalTime}m"
            totalTime % 60 == 0 -> "${totalTime / 60}h"
            else -> "${totalTime / 60}h ${totalTime % 60}m"
        }
    }
    
    fun getFormattedPrepTime(): String = if (prepTime < 60) "${prepTime}m" else "${prepTime / 60}h"
    fun getFormattedCookTime(): String = if (cookTime < 60) "${cookTime}m" else "${cookTime / 60}h"
}
