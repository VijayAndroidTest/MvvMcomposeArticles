package com.example.articlemvvm

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
class ArticlesRepository {

    private val apiService: ApiService by lazy {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spaceflightnewsapi.net/v4/")
            .client(client)  // Add the client with logging interceptor
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        retrofit.create(ApiService::class.java)
    }

    suspend fun getArticles(limit: Int, offset: Int): ArticlesResponse {
        try {
            return apiService.getArticles(limit, offset)
        } catch (e: Exception) {
            throw e
        }
    }
}