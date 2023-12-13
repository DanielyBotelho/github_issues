package com.murua.githubissues.core.domain

import kotlinx.coroutines.flow.Flow
import model.IssueItem
import repository.IssuesRepository
import javax.inject.Inject

class GetIssuesUseCase @Inject constructor(
    private val issuesRepository: IssuesRepository
) {
    suspend operator fun invoke(): Flow<List<IssueItem>> {
        return issuesRepository.getIssues()
    }
}
