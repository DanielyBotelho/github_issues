package com.murua.githubissues.feature.issues

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.IssueItem

@Composable
fun IssueDetailsRoute(
    modifier: Modifier = Modifier,
    sharedUiState: IssueItem?,
    onWebViewClick: (String) -> Unit,
    onPopBackStack: () -> Unit
) {

    if (sharedUiState != null) {
        IssueDetailScreen(
            details = sharedUiState,
            onWebViewClick = onWebViewClick
        )
    } else {
        onPopBackStack()
    }
}

@Composable
fun IssueDetailScreen(
    details: IssueItem,
    onWebViewClick: (String) -> Unit
) {

    Column {
        IssueAvatarImage(details.avatarUrl)
        Text(
            details.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 24.dp)
        )
        Text(
            details.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            details.date,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp)
        )
        Row() {
            Button(
                onClick = { onWebViewClick(details.url) }
            ) {
                Text("See on WebView")
            }
        }
    }
}