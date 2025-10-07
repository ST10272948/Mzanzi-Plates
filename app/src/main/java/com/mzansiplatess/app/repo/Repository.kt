package com.mzansiplatess.app.repo

import com.mzansiplatess.app.model.Restaurant
import com.mzansiplatess.app.model.User
import com.mzansiplatess.app.network.ApiClient
import retrofit2.Response

class Repository {
    private val api = ApiClient.apiService

    suspend fun getRestaurants(): List<Restaurant> {
        val response = api.getRestaurants()
        if (response.isSuccessful) {
            return response.body().orEmpty()
        } else {
            throw HttpExceptionWithBody(response.code(), response.errorBody()?.string())
        }
    }

    suspend fun getRestaurant(id: String): Restaurant {
        val response = api.getRestaurant(id)
        if (response.isSuccessful) {
            return requireNotNull(response.body()) { "Empty body" }
        } else {
            throw HttpExceptionWithBody(response.code(), response.errorBody()?.string())
        }
    }

    suspend fun createUser(user: User): Response<Unit> = api.createUser(user)
}

class HttpExceptionWithBody(
    val statusCode: Int,
    val errorBody: String?
) : Exception("HTTP $statusCode: ${errorBody ?: "no body"}")


