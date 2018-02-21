package com.codename26.childanalysis.Analysis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.codename26.childanalysis.MainActivity;
import com.codename26.childanalysis.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String mUrl = "https://gradusnik.net";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.WEBVIEWURL)){
            mUrl = intent.getStringExtra(MainActivity.WEBVIEWURL);
        }
        mWebView = findViewById(R.id.webView);
        mProgressBar = findViewById(R.id.progressBar);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient(mProgressBar));
      //  mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUrl);
    }

    private class MyWebViewClient extends WebViewClient{
        private ProgressBar progressBar;
        public MyWebViewClient(ProgressBar mProgressBar){
            progressBar = mProgressBar;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
