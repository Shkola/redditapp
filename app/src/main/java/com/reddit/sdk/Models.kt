package com.reddit.sdk

import java.util.*

data class RedditListing(
    val news: List<RedditNews>,
    val after: String?,
    val before: String?
)

data class RedditNews(
    val author: String,
    val title: String,
    val created: Long,
    val commentsNumber: Int,
    val thumbnail: ImageUrl,
    val url: String
)

val RedditNews.timeDelta
    get() = Calendar.getInstance().timeInMillis - created

typealias ImageUrl = String