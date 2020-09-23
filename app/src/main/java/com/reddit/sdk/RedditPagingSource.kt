package com.reddit.sdk

import androidx.paging.PagingSource
import com.reddit.sdk.remote.RemoteStorage
import retrofit2.HttpException
import java.io.IOException


class RedditPagingSource(
    private val remoteStorage: RemoteStorage
) : PagingSource<String, RedditNews>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditNews> {
        val after = params.key
        return try {
            val response = remoteStorage.getTop(after, params.loadSize)
            LoadResult.Page(
                data = response.news,
                prevKey = response.before,
                nextKey = if (response.news.isEmpty()) null else response.after
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
