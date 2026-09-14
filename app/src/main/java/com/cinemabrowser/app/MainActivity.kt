package com.cinemabrowser.app

import android.app.Activity
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import android.content.Intent
import android.net.Uri

class MainActivity : Activity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clearPrivateData()

        webView = WebView(this)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = false
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            cacheMode = WebSettings.LOAD_NO_CACHE
            saveFormData = false
            // Use a normal mobile Chrome-style UA instead of the Android WebView UA.
            // Some search providers reject requests that identify themselves as WebView clients.
            userAgentString = "Mozilla/5.0 (Linux; Android 15) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/140.0.0.0 Mobile Safari/537.36"
        }
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val uri = request.url
                if (uri.scheme == "http" || uri.scheme == "https") return false
                return try {
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                    true
                } catch (_: Exception) { true }
            }

            override fun onPageFinished(view: WebView, url: String) {
                // Keep cache/session state during the current visit; all local data is cleared
                // when a new app session starts or the activity is destroyed.
                // Google can occasionally show an anti-automation page for a shared/mobile IP.
                // If that happens, fall back to DuckDuckGo so the search bar remains usable.
                val parsed = Uri.parse(url)
                if (parsed.host?.contains("google.com", ignoreCase = true) == true &&
                    parsed.path?.contains("/sorry", ignoreCase = true) == true) {
                    val originalQuery = parsed.getQueryParameter("q")
                    if (!originalQuery.isNullOrBlank()) {
                        view.loadUrl("https://duckduckgo.com/?q=${Uri.encode(originalQuery)}")
                    } else {
                        view.loadUrl("https://duckduckgo.com/")
                    }
                }
            }
        }
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl("file:///android_asset/dashboard/index.html")
        setContentView(webView)
    }

    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }

    override fun onDestroy() {
        clearPrivateData()
        webView.destroy()
        super.onDestroy()
    }

    private fun clearPrivateData() {
        try {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
            android.webkit.WebStorage.getInstance().deleteAllData()
            if (::webView.isInitialized) {
                webView.clearHistory()
                webView.clearCache(true)
                webView.clearFormData()
            }
        } catch (_: Exception) { }
    }
}
