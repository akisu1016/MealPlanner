package com.SquareName.mealplanner

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    private lateinit var myWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        myWebView = findViewById(R.id.webview)
        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient = WebViewClient()
        myWebView.loadUrl(intent.getStringExtra("url"))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setTitle("読み込み中")


        myWebView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                supportActionBar?.setTitle(view.title)
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == android.R.id.home) {
            finish() //Activityを閉じる
        }
        return super.onOptionsItemSelected(item)
    }

    //戻るボタン
    override fun onBackPressed() {
        if (myWebView != null && myWebView.canGoBack()) myWebView.goBack()
        else super.onBackPressed()
    }
}