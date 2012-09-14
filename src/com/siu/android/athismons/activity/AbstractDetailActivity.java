package com.siu.android.athismons.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import com.actionbarsherlock.view.MenuItem;
import com.siu.android.andutils.activity.tracker.TrackedSherlockActivity;
import com.siu.android.andutils.util.WebUtils;
import com.siu.android.athismons.R;
import com.siu.android.athismons.model.Detail;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AbstractDetailActivity<T extends Detail> extends TrackedSherlockActivity {

    public static final String EXTRA = "extra";
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_ARTICLES = "extra_articles";
    public static final String EXTRA_ZOOM_LEVEL = "extra_zoom_level";

    protected WebView webView;
    protected T element;

    protected ImageButton zoomPlusButton;
    protected ImageButton zoomMinusButton;
    protected ImageButton shareButton;

    protected int zoomLevel;
    protected int position;

    @Override
    protected void onCreate(Bundle savedInstanceState, int layout) {
        super.onCreate(savedInstanceState, layout);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        webView = (WebView) findViewById(R.id.webview);
        zoomPlusButton = (ImageButton) findViewById(R.id.bottom_bar_zoom_plus);
        zoomMinusButton = (ImageButton) findViewById(R.id.bottom_bar_zoom_minus);
        shareButton = (ImageButton) findViewById(R.id.bottom_bar_share);

        element = (T) getIntent().getExtras().get(EXTRA);

        if (savedInstanceState != null) {
//            articles = savedInstanceState.getParcelableArrayList(EXTRA_ARTICLES);
            position = savedInstanceState.getInt(EXTRA_POSITION);
            zoomLevel = savedInstanceState.getInt(EXTRA_ZOOM_LEVEL);

            if (zoomLevel == 0) {
                setEnabledBottomBarButton(zoomMinusButton, false);
            } else if (zoomLevel == WebSettings.TextSize.values().length - 1) {
                setEnabledBottomBarButton(zoomPlusButton, false);
            }

        } else {
            position = getIntent().getExtras().getInt(EXTRA_POSITION);
            zoomLevel = 2;
        }

        initBottomBar();
        initWebview();

        WebUtils.loadToWeview(webView, element.getContent());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(EXTRA_POSITION, position);
        outState.putInt(EXTRA_ZOOM_LEVEL, zoomLevel);
//        outState.putParcelableArrayList(EXTRA_ARTICLES, (ArrayList<Article>) articles);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initBottomBar() {
        zoomPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomLevel++;
                webView.getSettings().setTextSize(WebSettings.TextSize.values()[zoomLevel]);

                if (!zoomMinusButton.isEnabled()) {
                    setEnabledBottomBarButton(zoomMinusButton, true);
                }

                if (zoomLevel == WebSettings.TextSize.values().length - 1) {
                    setEnabledBottomBarButton(zoomPlusButton, false);
                }
            }
        });

        zoomMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomLevel--;
                webView.getSettings().setTextSize(WebSettings.TextSize.values()[zoomLevel]);

                if (!zoomPlusButton.isEnabled()) {
                    setEnabledBottomBarButton(zoomPlusButton, true);
                }

                if (zoomLevel == 0) {
                    setEnabledBottomBarButton(zoomMinusButton, false);
                }
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, element.getTitle());

//                String url;
//                if (DataAccessLayer.getInstance().getMenuItem() == Universe.MEDICINE) {
//                    url = getString(R.string.article_activity_share_medicine_url);
//                } else {
//                    url = getString(R.string.article_activity_share_pharmacy_url);
//                }
//
//                url = String.format(url, currentArticle.getRubId(), currentArticle.getExtId());

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, element.getUrl());

                startActivity(Intent.createChooser(sharingIntent, "Partager l'article"));
            }
        });
    }

    private void initWebview() {
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setTextSize(WebSettings.TextSize.values()[zoomLevel]);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                AbstractDetailActivity.this.setSupportProgressBarIndeterminateVisibility(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                AbstractDetailActivity.this.setSupportProgressBarIndeterminateVisibility(false);
            }
        });
    }

    protected void setEnabledBottomBarButton(ImageButton button, boolean enabled) {
        if (enabled) {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
        }
    }
}
