package repository

import com.murua.githubissues.core.common.Dispatcher
import com.murua.githubissues.core.common.IssuesDispatchers.IO
import com.murua.githubissues.core.network.IssuesNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import model.IssueItem
import model.asDataModel
import javax.inject.Inject

class IssuesRepositoryImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkDataSource: IssuesNetworkDataSource
) : IssuesRepository {

    override suspend fun getIssues(): Flow<List<IssueItem>> {
        return flow {
            emit(networkDataSource.getIssues()
                .map { it.asDataModel() }
            )
        }
            .flowOn(ioDispatcher)
    }
}