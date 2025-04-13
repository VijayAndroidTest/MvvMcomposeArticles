package com.example.articlemvvm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class   ArticlesViewModel : ViewModel() {
    private val repository = ArticlesRepository()
    val articles = mutableStateOf<List<Article>>(emptyList())
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    init {
        fetchArticles(10, 0)
    }

    fun fetchArticles(limit: Int, offset: Int) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val response = repository.getArticles(limit, offset)
                articles.value = response.results
            } catch (e: Exception) {
                errorMessage.value = "Failed to fetch articles: ${e.message}"
                Log.e("ArticlesViewModel", "Error fetching articles: ${e.message}", e)
            } finally {
                isLoading.value = false
            }
        }
    }
}
