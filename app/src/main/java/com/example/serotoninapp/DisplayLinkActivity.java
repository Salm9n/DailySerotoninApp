package com.example.serotoninapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DisplayLinkActivity extends AppCompatActivity {

    private static final String TAG = "DisplayLinkActivity";

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_link);

        Intent intent = getIntent();
        String link = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.d(TAG,"LINK URL: " + link.toString());

        webview = (WebView) findViewById(R.id.view1);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(link);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(DisplayLinkActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}