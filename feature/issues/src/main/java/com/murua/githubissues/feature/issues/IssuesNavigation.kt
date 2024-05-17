package com.murua.githubissues.feature.issues

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.murua.githubissues.feature.issues.details.IssueDetailsRoute
import com.murua.githubissues.feature.issues.home.IssuesRoute
import com.murua.githubissues.feature.issues.webview.WebViewRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.composeIssues(navController: NavHostController) {
    composable<IssuesScreen> {
        val viewModel = sharedViewModel<SharedViewModel>(navController = navController)

        IssuesRoute(
            onIssueClick = { item ->
                viewModel.updateState(item)
                navController.navigate(IssueDetailsScreen)
            }
        )
    }
}

fun NavGraphBuilder.composeIssueDetails(navController: NavHostController) {
    composable<IssueDetailsScreen> {
        val viewModel = sharedViewModel<SharedViewModel>(navController = navController)
        val state by viewModel.sharedState.collectAsStateWithLifecycle()

        IssueDetailsRoute(
            sharedUiState = state,
            onWebViewClick = { url ->
                navController.navigate(IssueWebViewScreen(url))
                             },
            onPopBackStack = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.composeWebView(navController: NavHostController) {
    composable<IssueWebViewScreen> {
        val webViewScreen: IssueWebViewScreen = it.toRoute()

        WebViewRoute(
            url = webViewScreen.url,
            onPopBackStack = { navController.popBackStack() }
        )
    }
}

@Composable
inline fun <reified T : ViewModel> sharedViewModel(navController: NavController): T {
    return viewModel(navController.getBackStackEntry(IssuesScreen))
}

@Serializable
open class IssuesGraphDestination

@Serializable
object IssuesScreen: IssuesGraphDestination()

@Serializable
object IssueDetailsScreen: IssuesGraphDestination()

@Serializable
data class IssueWebViewScreen(val url: String): IssuesGraphDestination()