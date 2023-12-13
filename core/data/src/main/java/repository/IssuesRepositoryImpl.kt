package repository

import com.murua.githubissues.core.common.ApiResult
import com.murua.githubissues.core.common.asResult
import com.murua.githubissues.core.network.IssuesNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import model.IssueItem
import model.asDataModel
import javax.inject.Inject

class IssuesRepositoryImpl @Inject constructor(
    private val networkDataSource: IssuesNetworkDataSource
) : IssuesRepository {

    override suspend fun getIssues(): Flow<ApiResult<List<IssueItem>>> {
        return flowOf(networkDataSource.getIssues())
            .map { list ->
                list.map {
                    it.asDataModel()
                }
            }
            .asResult()
    }
}