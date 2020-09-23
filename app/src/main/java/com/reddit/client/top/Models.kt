package com.reddit.client.top

import com.reddit.sdk.ImageUrl


data class TopRedditUi(
    val title: String,
    val description: String,
    val commentsCount: String,
    val thumbnail: ImageUrl,
    val url: String,
    val onSaveImage: () -> Unit,
    val onOpenImage: () -> Unit
)