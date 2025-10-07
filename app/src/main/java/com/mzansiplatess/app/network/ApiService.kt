package com.mzansiplatess.app.network

import com.mzansiplatess.app.model.Restaurant
import com.mzansiplatess.app.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("restaurants")
    suspend fun getRestaurants(): Response<List<Restaurant>>

    @GET("restaurants/{id}")
    suspend fun getRestaurant(@Path("id") id: String): Response<Restaurant>

    @POST("users")
    suspend fun createUser(@Body user: User): Response<Unit>
}


