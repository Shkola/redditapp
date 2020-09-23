package com.reddit.sdk

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.reddit.sdk.remote.RemoteStorage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10

internal class ModuleRepository @Inject constructor(
    private val remoteStorage: RemoteStorage
) : ModuleApi {
    override fun getTop(): Flow<PagingData<RedditNews>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { RedditPagingSource(remoteStorage) }
        ).flow
    }
}