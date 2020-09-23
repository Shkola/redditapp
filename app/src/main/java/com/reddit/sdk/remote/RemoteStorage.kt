package com.reddit.sdk.remote

import com.reddit.sdk.RedditListing

internal interface RemoteStorage {
    suspend fun getTop(after: String?, limit: Int): RedditListing
}