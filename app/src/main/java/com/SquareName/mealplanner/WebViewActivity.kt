package com.SquareName.mealplanner

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    private lateinit var myWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        this.title = "WebView"
        myWebView = findViewById(R.id.webview)
        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient = WebViewClient()
        myWebView.loadUrl(intent.getStringExtra("url"))
    }

    //戻るボタン
    override fun onBackPressed() {
        if (myWebView != null && myWebView.canGoBack()) myWebView.goBack()
        else super.onBackPressed()
    }
}