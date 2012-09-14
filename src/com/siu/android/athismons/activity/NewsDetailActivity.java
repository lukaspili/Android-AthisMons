package com.siu.android.athismons.activity;

import android.os.Bundle;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.News;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class NewsDetailActivity extends AbstractDetailActivity<News> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.news_detail);
    }
}
