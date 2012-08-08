package com.siu.android.athismons.adapter;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.siu.android.andutils.adapter.SimpleViewHolder;
import com.siu.android.athismons.R;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class NewsViewHolder extends SimpleViewHolder {

    TextView title, category, pubDate;
    ImageView image;

    @Override
    public void init() {
        category = (TextView) row.findViewById(R.id.news_row_category);
        title = (TextView) row.findViewById(R.id.news_row_title);
        pubDate = (TextView) row.findViewById(R.id.news_row_date);
        image = (ImageView) row.findViewById(R.id.news_row_image);
    }
}
