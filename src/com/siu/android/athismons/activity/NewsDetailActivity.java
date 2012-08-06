package com.siu.android.athismons.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.actionbarsherlock.view.MenuItem;
import com.siu.android.andutils.activity.tracker.TrackedSherlockActivity;
import com.siu.android.andutils.util.WebUtils;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.News;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class NewsDetailActivity extends TrackedSherlockActivity {

    public static final String EXTRA = "extra";

    private TextView category, title;
    private WebView webView;

    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.news_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        news = (News) getIntent().getExtras().get(EXTRA);

        category = (TextView) findViewById(R.id.news_detail_category);
        title = (TextView) findViewById(R.id.news_detail_title);
        webView = (WebView) findViewById(R.id.webview);

        if (null != news.getCategory()) {
            category.setText(news.getCategory());
        } else {
            category.setVisibility(View.GONE);
        }

        title.setText(news.getTitle());
        WebUtils.loadToWeview(webView, news.getDescription());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
