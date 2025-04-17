package com.example.articlemvvm.MvvMcompose

import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getArticles(limit: Int, offset: Int): ArticlesResponse {
        return apiService.getArticles(limit, offset)
    }
}