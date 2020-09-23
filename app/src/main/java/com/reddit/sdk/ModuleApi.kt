package com.reddit.sdk

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

internal interface ModuleApi {
    fun getTop(): Flow<PagingData<RedditNews>>
}