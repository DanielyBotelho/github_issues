package com.murua.githubissues.feature.issues.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.murua.githubissues.feature.issues.R
import com.murua.githubissues.feature.issues.home.IssueAvatarImage
import model.IssueItem

@Composable
fun IssueDetailsRoute(
    sharedUiState: IssueItem?,
    onWebViewClick: (String) -> Unit,
    onPopBackStack: () -> Unit
) {
    if (sharedUiState != null) {
        IssueDetailScreen(
            details = sharedUiState,
            onWebViewClick = onWebViewClick,
            onPopBackStack = onPopBackStack
        )
    } else {
        onPopBackStack()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDetailScreen(
    details: IssueItem,
    onWebViewClick: (String) -> Unit,
    onPopBackStack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp),
                title = {
                    Text(
                        text = stringResource(R.string.issue_topbar_title),
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onPopBackStack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(
                                R.string.issue_back_button_text
                            ),
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        content = {
            IssueDetailsView(
                modifier = Modifier
                    .padding(it),
                details = details,
                onWebViewClick = onWebViewClick
            )
        }
    )
}

@Composable
fun IssueDetailsView(
    modifier: Modifier,
    details: IssueItem,
    onWebViewClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IssueAvatarImage(details.avatarUrl)
        Text(
            details.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 24.dp),
            textAlign = TextAlign.Center
        )
        Text(
            details.date,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            details.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Button(
                onClick = { onWebViewClick(details.url) }
            ) {
                Text(stringResource(R.string.issue_webview_button_title))
            }
        }
    }
}