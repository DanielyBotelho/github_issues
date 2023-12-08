package di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.IssuesRepository
import repository.IssuesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsSessionRepository(
        sessionRepository: IssuesRepositoryImpl,
    ): IssuesRepository
}