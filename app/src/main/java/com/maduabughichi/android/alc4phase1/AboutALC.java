package com.maduabughichi.android.alc4phase1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

public class AboutALC extends AppCompatActivity {
    private final static String mUrl = "https://andela.com/alc/";
    public static int PIC_WIDTH = 500;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_alc);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mWebView = findViewById(R.id.aboutAndelaWebview);
        mWebView.setPadding(0, 0, 0, 0);
        mWebView.setInitialScale(getScale());
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mProgressBar = findViewById(R.id.progressBar);
        checkNetworkState();
        if (savedInstanceState != null) {
            mWebView.restoreState(savedInstanceState);
            mProgressBar.setVisibility(View.GONE);
            mWebView.getSettings().setJavaScriptEnabled(true);
            webSettings();
        } else {
            mWebView.getSettings().setJavaScriptEnabled(true);
            webSettings();
            mWebView.loadUrl(mUrl);
        }

        //enableStrictMode();
    }
/*
    private void enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build();
            StrictMode.setThreadPolicy(policy);

        }
    }
*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mWebView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void checkNetworkState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo == null || !networkInfo.isConnected()) {
            Button retry = findViewById(R.id.retryButton);
            Group noNetworkGroup = findViewById(R.id.groupViews);
            noNetworkGroup.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            retry.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onClick(View view) {
                    AboutALC.this.recreate();
                    mWebView.getSettings().setJavaScriptEnabled(true);
                    AboutALC.this.webSettings();
                }
            });
        }
    }

    private void webSettings() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private int getScale() {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        Double val = (double) width / (double) PIC_WIDTH;
        val = val * 100d;
        return val.intValue();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
