package com.siu.android.athismons.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import com.actionbarsherlock.view.MenuItem;
import com.siu.android.andutils.activity.tracker.TrackedSherlockActivity;
import com.siu.android.andutils.util.WebUtils;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.Menu;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class MenuDetailActivity extends TrackedSherlockActivity {

    public static final String EXTRA = "extra";

    private TextView title;
    private WebView webView;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.menu_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menu = (Menu) getIntent().getExtras().get(EXTRA);

        title = (TextView) findViewById(R.id.menu_detail_title);
        webView = (WebView) findViewById(R.id.webview);

        title.setText(menu.getTitle());
        WebUtils.loadToWeview(webView, menu.getDescription());
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
