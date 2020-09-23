package com.reddit.sdk.remote

import com.reddit.sdk.RedditListing
import javax.inject.Inject

class RemoteStorageImpl @Inject constructor(
    private val redditApi: RedditApi
) : RemoteStorage {
    override suspend fun getTop(after: String?, limit: Int): RedditListing =
        redditApi.getTop(after, limit).toRedditListing()
}