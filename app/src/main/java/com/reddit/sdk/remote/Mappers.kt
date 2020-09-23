package com.reddit.sdk.remote

import com.reddit.sdk.RedditListing
import com.reddit.sdk.RedditNews

fun RedditListingResponse.toRedditListing() = RedditListing(
    news = data.children.toRedditNews(),
    before = data.before,
    after = data.after
)

private fun List<RedditNewsDataResponse>.toRedditNews(): List<RedditNews> = map { it.news.toRedditNews() }

private fun RedditNewsResponse.toRedditNews(): RedditNews =
    RedditNews(
        author = author,
        title = title,
        created = created * 1000,
        thumbnail = thumbnail,
        url = url,
        commentsNumber = commentsNumber
    )