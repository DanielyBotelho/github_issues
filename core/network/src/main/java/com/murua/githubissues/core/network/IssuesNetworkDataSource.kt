package com.murua.githubissues.core.network

import com.murua.githubissues.core.network.model.Issue

interface IssuesNetworkDataSource {
    suspend fun getIssues(): List<Issue>
}