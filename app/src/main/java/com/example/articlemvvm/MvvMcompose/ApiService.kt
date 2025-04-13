package com.example.articlemvvm.MvvMcompose

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("articles/")
    suspend fun getArticles(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): ArticlesResponse
}