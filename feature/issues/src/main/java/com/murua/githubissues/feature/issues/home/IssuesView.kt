package com.murua.githubissues.feature.issues.home

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.murua.githubissues.feature.issues.R
import kotlinx.coroutines.launch
import model.IssueItem
import model.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssuesRoute(
    modifier: Modifier = Modifier,
    viewModel: IssuesViewModel = hiltViewModel<IssuesViewModel>(),
    onIssueClick: (IssueItem) -> Unit,
) {
    val issuesUiState: IssuesUiState by viewModel.issuesState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (issuesUiState == IssuesUiState.Default) {
            viewModel.getIssues()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.issues_topbar_title),
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = {
            IssuesScreen(
                modifier = modifier
                    .padding(it),
                issuesUiState = issuesUiState,
                onIssueClick = onIssueClick,
                onShowErrorSnackBar = {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            )
        }
    )
}

@VisibleForTesting
@Composable
fun IssuesScreen(
    modifier: Modifier = Modifier,
    issuesUiState: IssuesUiState,
    onIssueClick: (IssueItem) -> Unit,
    onShowErrorSnackBar: (String) -> Unit
) {
    val state = rememberLazyListState()
    val context = LocalContext.current

    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (issuesUiState) {
                IssuesUiState.Loading -> item {
                    LoadingState()
                }

                is IssuesUiState.Error -> {
                    onShowErrorSnackBar(context.getString(R.string.issues_error_message))
                }

                is IssuesUiState.Success -> {
                    issueCardItems(
                        items = issuesUiState.data,
                        onIssueClick = onIssueClick
                    )
                }

                else -> {}
            }
        }
    }
}

fun LazyListScope.issueCardItems(
    itemModifier: Modifier = Modifier,
    items: List<IssueItem>,
    onIssueClick: (IssueItem) -> Unit
) = items(
    items = items,
    key = { it.id },
    itemContent = { issue ->
        IssueCard(
            modifier = itemModifier,
            issueItem = issue,
            onClick = {
                onIssueClick(issue)
            }
        )
    },
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueCard(
    modifier: Modifier = Modifier,
    issueItem: IssueItem,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IssueAvatarImage(issueItem.avatarUrl)
            Box(
                modifier = Modifier.padding(8.dp),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    IssueTitle(
                        modifier = Modifier.fillMaxWidth((.8f)),
                        issueItem.title
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    IssueState(issueItem.state.stateName)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IssueAvatarImage(url: String) {
    GlideImage(
        model = url,
        contentDescription = null,
        loading = placeholder(R.drawable.ic_launcher_foreground),
        failure = placeholder(R.drawable.ic_launcher_foreground),
        modifier = Modifier
            .width(70.dp)
            .height(70.dp)
            .clip(CircleShape)
    )
}

@Composable
fun IssueTitle(
    modifier: Modifier = Modifier,
    issueTitle: String
) {
    Text(
        modifier = modifier,
        text = issueTitle,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
fun IssueState(
    state: String,
) {
    Text(
        state,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentSize(),
        color = MaterialTheme.colorScheme.primary,
    )
}

@Preview
@Composable
fun CardPreview() {
    MaterialTheme {
        IssueCard(
            issueItem = IssueItem(
                123,
                "https://gravatar.com/avatar/f016cfd6cdc0df90931f7e9190a88bb6?s=400&d=robohash&r=x",
                "12/12/12",
                "Título",
                "Descrição da Issue",
                "https://api.github.com/repos/octocat/Hello-World/issues/1347",
                state = State.OPEN),
            onClick = {}
        )
    }
}
