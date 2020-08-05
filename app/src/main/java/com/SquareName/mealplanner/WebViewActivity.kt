package com.SquareName.mealplanner

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    private lateinit var myWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        myWebView = findViewById(R.id.webview)
        myWebView.run {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(intent.getStringExtra("url"))
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)


        myWebView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                supportActionBar?.title = getString(R.string.loading)
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.d("URL", url)
                supportActionBar?.title = view.title
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                finish()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            finish() //Activityを閉じる
        }
        return super.onOptionsItemSelected(item)
    }

    //戻るボタン
    override fun onBackPressed() {
        myWebView?.let {
            if (it.canGoBack()) it.goBack()
            else super.onBackPressed()
        } ?: super.onBackPressed()
    }
}
