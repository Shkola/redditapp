package com.reddit.di.modules

import android.content.Context
import com.google.gson.Gson
import com.readystatesoftware.chuck.ChuckInterceptor
import com.reddit.BuildConfig
import com.reddit.sdk.remote.RedditApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetModule {
    @Provides
    @Singleton
    fun provideOkHttp(@ApplicationContext context: Context): OkHttpClient =
            OkHttpClient.Builder()
                    .apply {
                        addInterceptor(ChuckInterceptor(context))
                        connectTimeout(45, TimeUnit.SECONDS)
                        readTimeout(45, TimeUnit.SECONDS)
                    }.build()

    @Provides
    @Singleton
    fun provideRedditApi(okHttpClient: OkHttpClient): RedditApi = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(RedditApi::class.java)
}