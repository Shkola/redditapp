package com.reddit.sdk.remote

import com.google.gson.annotations.SerializedName
import com.reddit.sdk.ImageUrl


class RedditListingResponse(
    @SerializedName("data") val data: RedditListingDataResponse
)

class RedditListingDataResponse(
    @SerializedName("children") val children: List<RedditNewsDataResponse>,
    @SerializedName("after") val after: String?,
    @SerializedName("before") val before: String?
)

class RedditNewsDataResponse(
    @SerializedName("data") val news: RedditNewsResponse
)

class RedditNewsResponse(
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("num_comments") val commentsNumber: Int,
    @SerializedName("created") val created: Long,
    @SerializedName("thumbnail") val thumbnail: ImageUrl,
    @SerializedName("url") val url: String
)