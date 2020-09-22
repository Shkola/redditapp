package com.reddit.sdk

internal interface ModuleApi {
    suspend fun getTop( after: String, limit: String): RedditListing
}