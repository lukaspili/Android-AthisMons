package com.siu.android.athismons.activity;

import android.os.Bundle;
import android.webkit.WebView;
import com.actionbarsherlock.view.MenuItem;
import com.siu.android.andutils.activity.tracker.TrackedSherlockActivity;
import com.siu.android.andutils.util.WebUtils;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.Agenda;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AgendaDetailActivity extends TrackedSherlockActivity {

    public static final String EXTRA = "extra";

    private WebView webView;
    private Agenda agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.news_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        agenda = (Agenda) getIntent().getExtras().get(EXTRA);
        webView = (WebView) findViewById(R.id.webview);

        WebUtils.loadToWeview(webView, agenda.getDescription());
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
