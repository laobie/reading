package com.jaeger.reading.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jaeger.reading.R;

/**
 * Created by Jaeger on 1/28/028.
 */
public class BrowseActivity extends ActionBarActivity {

    private WebView browseBooksView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_layout);
        browseBooksView = (WebView) findViewById(R.id.browseBooksView);
        browseBooksView.getSettings().setJavaScriptEnabled(true);
        browseBooksView.loadUrl("http://book.douban.com/tag/%E9%A6%99%E6%B8%AF%E4%B8" +
                "%AD%E6%96%87%E5%A4%A7%E5%AD%A6%E6%8E%A8%E8%8D%90%E4%B9%A6%E5%8D%95");
        browseBooksView.setWebViewClient(new initWebView());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && browseBooksView.canGoBack()){
            browseBooksView.goBack();
            return true;
        }
        return false;
    }

    private class initWebView extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }
}
