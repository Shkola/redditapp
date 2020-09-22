package com.reddit.di.modules

import com.reddit.sdk.ModuleApi
import com.reddit.sdk.ModuleRepository
import com.reddit.sdk.remote.RemoteStorage
import com.reddit.sdk.remote.RemoteStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
internal abstract class SdkModule {

    @Binds
    abstract fun bindRemoteStorage(remoteStorage: RemoteStorageImpl): RemoteStorage

    @Binds
    abstract fun bindModuleApi(moduleApi: ModuleRepository): ModuleApi
}