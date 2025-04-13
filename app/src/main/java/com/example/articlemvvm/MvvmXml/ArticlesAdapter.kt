package com.example.articlemvvm.MvvmXml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.articlemvvm.MvvMcompose.Article
import com.example.articlemvvm.R

class ArticlesAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = articles.size

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.article_title)
        private val summaryTextView: TextView = itemView.findViewById(R.id.article_summary)
        private val newsSiteTextView: TextView = itemView.findViewById(R.id.article_news_site)

        fun bind(article: Article) {
            titleTextView.text = article.title
            summaryTextView.text = article.summary
            newsSiteTextView.text = "News Site: ${article.newsSite}"
        }
    }
}