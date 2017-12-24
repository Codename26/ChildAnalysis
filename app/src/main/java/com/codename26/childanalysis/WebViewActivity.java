package com.codename26.childanalysis;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebView = findViewById(R.id.webView);
        mProgressBar = findViewById(R.id.progressBar);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient(mProgressBar));
      //  mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("https://gradusnik.net/eritrocity-krovi/");
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
