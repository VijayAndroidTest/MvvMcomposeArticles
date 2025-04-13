package com.example.articlemvvm.MvvMcompose

class ArticlesRepository(
    private val apiService: ApiService = ApiClient.apiService
) {
    suspend fun getArticles(limit: Int, offset: Int): ArticlesResponse {
        return try {
            apiService.getArticles(limit, offset)
        } catch (e: Exception) {
            throw e
        }
    }
}