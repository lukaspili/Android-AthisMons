package com.siu.android.athismons.activity;

import android.os.Bundle;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.Agenda;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AgendaDetailActivity extends AbstractDetailActivity<Agenda> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.news_detail);
    }
}
