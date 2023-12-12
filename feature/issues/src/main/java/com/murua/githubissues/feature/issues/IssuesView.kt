package com.murua.githubissues.feature.issues

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import model.IssueItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssuesRoute(
    modifier: Modifier = Modifier,
    viewModel: IssuesViewModel = hiltViewModel<IssuesViewModel>(),
    onIssueClick: (IssueItem) -> Unit,
) {
    val issuesUiState: IssuesUiState by viewModel.issuesState.collectAsStateWithLifecycle()

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
                        text = "Github Issues",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        content = {
            IssuesScreen(
                issuesUiState = issuesUiState,
                modifier = modifier
                    .padding(it),
                onIssueClick = onIssueClick
            )
        }
    )
}

@VisibleForTesting
@Composable
fun IssuesScreen(
    issuesUiState: IssuesUiState,
    modifier: Modifier = Modifier,
    onIssueClick: (IssueItem) -> Unit
) {
    val state = rememberLazyListState()

    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            when (issuesUiState) {
                IssuesUiState.Loading -> item {
                    LoadingState()
                }

                is IssuesUiState.Error -> { println("Erro...") }
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
    items: List<IssueItem>,
    itemModifier: Modifier = Modifier,
    onIssueClick: (IssueItem) -> Unit
) = items(
    items = items,
    key = { it.id },
    itemContent = { issue ->
        IssueCard(
            issueItem = issue,
            onClick = {
                onIssueClick(issue)
            },
            modifier = itemModifier,
        )
    },
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueCard(
    issueItem: IssueItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .padding(16.dp)
    ) {
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            IssueAvatarImage(issueItem.avatarUrl)
            Box(
                modifier = Modifier.padding(8.dp),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    IssueTitle(
                        issueItem.title,
                        modifier = Modifier.fillMaxWidth((.8f)),
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
        model  = url,
        contentDescription = null,
        loading = placeholder(R.drawable.ic_launcher_foreground),
        failure = placeholder(R.drawable.ic_launcher_foreground),
        modifier = Modifier
            .width(70.dp)
            .height(70.dp)
    )
}

@Composable
fun IssueTitle(
    issueTitle: String,
    modifier: Modifier = Modifier,
) {
    Text(issueTitle, style = MaterialTheme.typography.titleMedium, modifier = modifier)
}

@Composable
fun IssueState(
    state: String,
) {
    Text(state, style = MaterialTheme.typography.bodyLarge)
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
