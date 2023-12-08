package repository

import kotlinx.coroutines.flow.Flow
import model.IssueItem
import Result

interface IssuesRepository {
    suspend fun getIssues(): Flow<Result<List<IssueItem>>>
}