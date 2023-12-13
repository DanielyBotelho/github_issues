package com.murua.githubissues.feature.issues

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.murua.githubissues.feature.issues.details.IssueDetailsRoute
import com.murua.githubissues.feature.issues.home.IssuesRoute
import com.murua.githubissues.feature.issues.webview.WebViewRoute

const val issuesRoute = "issues_route"
const val issueDetailsRoute = "issue_details_route"
const val issueWebViewRoute = "webview?item="
const val issueWebViewParamKey = "url"

fun NavGraphBuilder.composeIssues(navController: NavHostController) {
    composable(route = issuesRoute) {
        val viewModel = sharedViewModel<SharedViewModel>(navController = navController)

        IssuesRoute(
            onIssueClick = { item ->
                viewModel.updateState(item)
                navController.navigate(issueDetailsRoute)
            }
        )
    }
}

fun NavGraphBuilder.composeIssueDetails(navController: NavHostController) {
    composable(route = issueDetailsRoute) {
        val viewModel = sharedViewModel<SharedViewModel>(navController = navController)
        val state by viewModel.sharedState.collectAsStateWithLifecycle()

        IssueDetailsRoute(
            sharedUiState = state,
            onWebViewClick = { url ->
                navController.navigate("$issueWebViewRoute$url")
                             },
            onPopBackStack = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.composeWebView(navController: NavHostController) {
    composable(
        route = "$issueWebViewRoute{$issueWebViewParamKey}",
        arguments = listOf(navArgument(issueWebViewParamKey) { type = NavType.StringType })
    ) {
        WebViewRoute(
            url = it.arguments?.getString(issueWebViewParamKey) ?: "",
            onPopBackStack = { navController.popBackStack() }
        )
    }
}

@Composable
inline fun <reified T : ViewModel> sharedViewModel(navController: NavController): T {
    return viewModel(navController.getBackStackEntry(issuesRoute))
}