package com.reddit.sdk.remote

import com.reddit.sdk.RedditListing

internal interface RemoteStorage {
    fun getTop(after: String, limit: String): RedditListing
}