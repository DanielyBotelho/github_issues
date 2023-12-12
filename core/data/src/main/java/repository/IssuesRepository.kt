package repository

import com.murua.githubissues.core.common.ApiResult
import kotlinx.coroutines.flow.Flow
import model.IssueItem

interface IssuesRepository {
    suspend fun getIssues(): Flow<ApiResult<List<IssueItem>>>
}