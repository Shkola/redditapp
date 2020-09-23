package com.reddit.sdk.remote

import com.reddit.sdk.RedditListing
import com.reddit.sdk.RedditNews

internal fun RedditListingResponse.toRedditListing() = RedditListing(
    news = data.children.toRedditNews(),
    before = data.before,
    after = data.after
)

private fun List<RedditNewsDataResponse>.toRedditNews(): List<RedditNews> = map { it.news.toRedditNews() }

private fun RedditNewsResponse.toRedditNews(): RedditNews =
    RedditNews(
        author = author,
        title = title,
        created = created,
        thumbnail = thumbnail,
        url = url,
        commentsNumber = commentsNumber
    )