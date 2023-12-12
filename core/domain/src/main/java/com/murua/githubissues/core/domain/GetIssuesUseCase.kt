package com.murua.githubissues.core.domain

import com.murua.githubissues.core.common.ApiResult
import kotlinx.coroutines.flow.Flow
import model.IssueItem
import repository.IssuesRepository
import javax.inject.Inject

class GetIssuesUseCase @Inject constructor(
    private val issuesRepository: IssuesRepository
) {
    suspend operator fun invoke(): Flow<ApiResult<List<IssueItem>>> {
        return issuesRepository.getIssues()
    }
}
