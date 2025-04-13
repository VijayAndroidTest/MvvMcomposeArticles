package com.example.articlemvvm.MvvmXml

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.articlemvvm.MvvMcompose.Article
import com.example.articlemvvm.MvvMcompose.ArticlesViewModel
import com.example.articlemvvm.R

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorMessageTextView: TextView
    private lateinit var btnMvvmCompose: Button
    private lateinit var articlesAdapter: ArticlesAdapter
    private val articles = mutableListOf<Article>()

    private lateinit var viewModel: ArticlesViewModelXml // <-- use the XML ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewArticles)
        progressBar = findViewById(R.id.progressBar)
        errorMessageTextView = findViewById(R.id.error_message)
        btnMvvmCompose = findViewById(R.id.btnMvvmCompose)

        recyclerView.layoutManager = LinearLayoutManager(this)
        articlesAdapter = ArticlesAdapter(articles)
        recyclerView.adapter = articlesAdapter

        btnMvvmCompose.setOnClickListener {
            val intent = Intent(this, com.example.articlemvvm.MvvMcompose.MainActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this)[ArticlesViewModelXml::class.java] // <-- ViewModel for XML

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.articles.observe(this) { articlesList ->
            if (articlesList.isNotEmpty()) {
                progressBar.visibility = View.GONE
                errorMessageTextView.visibility = View.GONE

                articles.clear()
                articles.addAll(articlesList)
                articlesAdapter.notifyDataSetChanged()
            }
        }

        viewModel.isLoading.observe(this) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                progressBar.visibility = View.GONE
                errorMessageTextView.visibility = View.VISIBLE
                errorMessageTextView.text = message
            }
        }
    }
}