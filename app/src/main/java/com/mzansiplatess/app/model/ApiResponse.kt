package com.mzansiplatess.app.model

// Generic API response wrapper
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val error: String? = null,
    val timestamp: String? = null
)

// Paginated response for lists
data class PaginatedResponse<T>(
    val success: Boolean,
    val data: List<T>,
    val pagination: Pagination,
    val message: String? = null,
    val error: String? = null
)

data class Pagination(
    val page: Int,
    val limit: Int,
    val total: Int,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrev: Boolean
)

// Search and filter parameters
data class SearchParams(
    val query: String? = null,
    val category: String? = null,
    val city: String? = null,
    val minRating: Double? = null,
    val maxPrice: Double? = null,
    val tags: List<String> = emptyList(),
    val page: Int = 1,
    val limit: Int = 20,
    val sortBy: String? = null, // "rating", "price", "distance", "createdAt"
    val sortOrder: String = "desc" // "asc" or "desc"
)

// Common response for operations that don't return data
data class SuccessResponse(
    val success: Boolean,
    val message: String,
    val timestamp: String? = null
)
