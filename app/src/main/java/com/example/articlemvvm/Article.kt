package com.example.articlemvvm

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article(
    val id: Int,
    val title: String,
    val authors: List<Author>,
    val url: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "news_site") val newsSite: String,
    val summary: String,
    @Json(name = "published_at") val publishedAt: String,
    @Json(name = "updated_at") val updatedAt: String,
    val featured: Boolean
)

@JsonClass(generateAdapter = true)
data class Author(
    val name: String,
    val socials: Socials?
)

@JsonClass(generateAdapter = true)
data class Socials(
    val x: String?,
    val youtube: String?,
    val instagram: String?,
    val linkedin: String?,
    val mastodon: String?,
    val bluesky: String?
)

@JsonClass(generateAdapter = true)
data class ArticlesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Article>
)