package com.reddit.client.top


data class TopRedditUi(
    val title: String,
    val description: String,
    val commentsCount: String,
    val thumbnail: String,
    val url: String,
    val onSaveImage: () -> Unit
)