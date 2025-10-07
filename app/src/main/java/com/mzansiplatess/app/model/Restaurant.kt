package com.mzansiplatess.app.model

data class Restaurant(
    val _id: String? = null,
    val name: String,
    val description: String,
    val city: String,
    val address: String,
    val phone: String,
    val image_url: String,
    val rating: Double
)


