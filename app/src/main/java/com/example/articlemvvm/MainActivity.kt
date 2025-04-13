package com.example.articlemvvm
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArticlesScreen()
        }
    }
}

@Composable
fun ArticlesScreen(viewModel: ArticlesViewModel = viewModel()) {
    val articles by viewModel.articles
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage ?: "Unknown error", color = Color.Red)
            }
        }
        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(articles) { article ->
                    ArticleItem(article = article)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = article.summary, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "News Site: ${article.newsSite}",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}