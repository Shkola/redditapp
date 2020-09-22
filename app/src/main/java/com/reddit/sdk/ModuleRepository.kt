package com.reddit.sdk

import com.reddit.sdk.remote.RemoteStorage
import javax.inject.Inject

internal class ModuleRepository @Inject constructor(
        private val remoteStorage: RemoteStorage
) :ModuleApi{
    override suspend fun getTop(after: String, limit: String): RedditListing = remoteStorage.getTop(after, limit)
}