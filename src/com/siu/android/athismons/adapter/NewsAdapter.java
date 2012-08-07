package com.siu.android.athismons.adapter;

import android.content.Context;
import android.view.View;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.siu.android.andutils.adapter.SimpleAdapter;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.News;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class NewsAdapter extends SimpleAdapter<News, NewsViewHolder> {

    public NewsAdapter(Context context, List<News> newses) {
        super(context, R.layout.news_row, newses);
    }

    @Override
    protected void configure(NewsViewHolder viewHolder, News news) {
        if (StringUtils.isNotEmpty(news.getCategory())) {
            viewHolder.category.setText(news.getCategory());
        } else {
            viewHolder.category.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(news.getImage())) {
            UrlImageViewHelper.setUrlDrawable(viewHolder.image, news.getImage());
        } else {
            viewHolder.image.setVisibility(View.GONE);
        }

        viewHolder.title.setText(news.getTitle());
        viewHolder.pubDate.setText(news.getPubDate());
    }

    @Override
    protected NewsViewHolder createViewHolder() {
        return new NewsViewHolder();
    }
}
