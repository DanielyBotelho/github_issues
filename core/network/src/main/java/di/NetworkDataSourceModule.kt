package di

import IssuesNetworkDataSource
import NetworkBuilder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {
    @Binds
    fun NetworkBuilder.binds(): IssuesNetworkDataSource
}