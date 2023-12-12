package com.murua.githubissues.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.murua.githubissues.feature.issues.composeIssueDetails
import com.murua.githubissues.feature.issues.composeIssues
import com.murua.githubissues.feature.issues.composeWebView
import com.murua.githubissues.feature.issues.issuesRoute

@Composable
fun GithubIssuesNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = issuesRoute,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composeIssues(navController = navController)
        composeIssueDetails(navController = navController)
        composeWebView(navController = navController)
    }
}
