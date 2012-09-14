package com.siu.android.athismons.activity;

import android.os.Bundle;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.Menu;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class MenuDetailActivity extends AbstractDetailActivity<Menu> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.news_detail);
    }
}
