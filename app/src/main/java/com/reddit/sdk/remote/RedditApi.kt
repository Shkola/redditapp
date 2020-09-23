package com.reddit.sdk.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface RedditApi {

    @GET("/top.json")
    suspend fun getTop(@Query("after") after: String?,
                       @Query("limit") limit: Int): RedditListingResponse
}
