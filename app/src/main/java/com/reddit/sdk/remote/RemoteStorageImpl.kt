package com.reddit.sdk.remote

import com.reddit.sdk.RedditListing
import javax.inject.Inject

internal class RemoteStorageImpl @Inject constructor(
        private val redditApi: RedditApi
) : RemoteStorage {
    override fun getTop(after: String, limit: String): RedditListing {
        TODO("Not yet implemented")
    }
}
