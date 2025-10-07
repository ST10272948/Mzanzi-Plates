package com.mzansiplatess.app.network

import com.mzansiplatess.app.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Optional: attach auth header if token available (replace getAuthToken() accordingly)
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()
        val token: String? = null // TODO: wire to your auth storage if needed
        if (!token.isNullOrEmpty()) {
            builder.header("Authorization", "Bearer $token")
        }
        chain.proceed(builder.build())
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}



