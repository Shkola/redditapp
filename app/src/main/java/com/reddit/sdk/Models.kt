package com.reddit.sdk

internal data class RedditListing(
        val children: List<RedditNews>,
        val after: String?,
        val before: String?
)

internal data class RedditNews(
        val author: String,
        val title: String,
        val created: Long,
        val thumbnail: String,
        val url: String
)