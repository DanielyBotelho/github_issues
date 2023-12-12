package com.murua.githubissues.core.network.di

import com.murua.githubissues.core.network.IssuesNetworkDataSource
import com.murua.githubissues.core.network.retrofit.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {

    @Binds
    fun binds(impl: RetrofitNetwork): IssuesNetworkDataSource
}