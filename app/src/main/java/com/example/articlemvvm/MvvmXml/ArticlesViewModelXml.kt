package com.example.articlemvvm.MvvmXml

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articlemvvm.MvvMcompose.ApiService
import com.example.articlemvvm.MvvMcompose.Article
import com.example.articlemvvm.MvvMcompose.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModelXml @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>(emptyList())
    val articles: LiveData<List<Article>> = _articles

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchArticles(10, 0)
    }

    fun fetchArticles(limit: Int, offset: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // Directly use the injected apiService
                val response = apiService.getArticles(limit, offset)
                _articles.value = response.results
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch articles: ${e.message}"
                Log.e("ArticlesViewModelXml", "Error fetching articles: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}