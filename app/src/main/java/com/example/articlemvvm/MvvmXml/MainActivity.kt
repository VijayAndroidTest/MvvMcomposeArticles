package com.example.articlemvvm.MvvmXml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import android.view.View
import android.content.Intent
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.articlemvvm.MvvMcompose.Article
import com.example.articlemvvm.R

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorMessageTextView: TextView
    private lateinit var btnMvvmCompose: Button
    private lateinit var articlesAdapter: ArticlesAdapter
    private val articles = mutableListOf<Article>()

    private lateinit var viewModel: ArticlesViewModelXml

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

        viewModel = ViewModelProvider(this)[ArticlesViewModelXml::class.java]

        checkBiometricSupportAndAuthenticate()
    }

    private fun checkBiometricSupportAndAuthenticate() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> showBiometricPrompt()
            else -> {
                Toast.makeText(this, "Biometric authentication not supported", Toast.LENGTH_SHORT).show()
                observeViewModel() // fallback without biometric
            }
        }
    }

    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    observeViewModel() // load data only on successful auth
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Auth error: $errString", Toast.LENGTH_SHORT).show()
                    finish() // or provide fallback
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authenticate")
            .setSubtitle("Use biometrics to access articles")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
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