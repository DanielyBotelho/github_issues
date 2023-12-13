package repository

import kotlinx.coroutines.flow.Flow
import model.IssueItem

interface IssuesRepository {
    suspend fun getIssues(): Flow<List<IssueItem>>
}