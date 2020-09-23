package com.reddit.client.top

import com.reddit.sdk.RedditNews

fun RedditNews.toTopRedditUi(
    description: String,
    commentsCount: String,
    onSaveImage: () -> Unit,
) = TopRedditUi(
    title = title,
    description = description,
    commentsCount = commentsCount,
    thumbnail = thumbnail,
    url = url,
    onSaveImage = onSaveImage
)