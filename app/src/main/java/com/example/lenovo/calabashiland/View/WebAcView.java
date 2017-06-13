package com.example.lenovo.calabashiland.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.lenovo.calabashiland.R;

public class WebAcView extends AppCompatActivity {
//private ProgressBar progressBar;
    private WebView webView;
private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_ac_view);
        initView();
    }
    private void initView() {
        url=getIntent().getStringExtra("url");
        webView= (WebView) findViewById(R.id.webview_drawlayout);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.requestFocus();
        webView.loadUrl("file:///"+url);


    }
}
