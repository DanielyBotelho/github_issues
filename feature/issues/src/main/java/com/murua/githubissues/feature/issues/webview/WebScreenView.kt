package com.murua.githubissues.feature.issues.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewRoute(
    modifier: Modifier = Modifier,
    url: String = "",
    onCloseClick: () -> Unit,
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
    val density = LocalDensity.current.density
    val context = LocalView.current.context

    // AndroidView to embed WebView in Compose
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                // Set WebViewClient to handle url loading
                webViewClient = WebViewClient()
            }
        },
        update = { webView ->
            // Load the provided URL into the WebView
            webView.loadUrl(url)
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp * density) // Adjust padding as needed
    )
}