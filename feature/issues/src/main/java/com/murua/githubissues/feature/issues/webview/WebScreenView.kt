package com.murua.githubissues.feature.issues.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewRoute(
    url: String = "",
    onPopBackStack: () -> Unit
) {
    if (url.isEmpty()) {
        onPopBackStack()
    } else {
        WebViewComponent(url)
    }
}

@Composable
fun WebViewComponent(
    url: String
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        },
        modifier = Modifier
            .fillMaxSize()
    )
}