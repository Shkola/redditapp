package com.reddit.sdk.remote

import com.reddit.sdk.RedditListing

interface RemoteStorage {
    suspend fun getTop(after: String?, limit: Int): RedditListing
}