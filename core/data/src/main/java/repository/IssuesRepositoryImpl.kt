package repository

import IssuesNetworkDataSource
import Result
import asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.IssueItem
import javax.inject.Inject

class IssuesRepositoryImpl @Inject constructor(
    private val networkDataSource: IssuesNetworkDataSource
): IssuesRepository {

    override suspend fun getIssues(): Flow<Result<List<IssueItem>>> = flow<List<IssueItem>> {
        networkDataSource.getIssues()
            .map { IssueItem(it.title, it.state) }
    }.asResult()
}