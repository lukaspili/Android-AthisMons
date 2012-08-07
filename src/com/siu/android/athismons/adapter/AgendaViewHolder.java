package com.siu.android.athismons.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import com.siu.android.andutils.adapter.SimpleViewHolder;
import com.siu.android.athismons.R;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AgendaViewHolder extends SimpleViewHolder {

    TextView title, category;
    ImageView image;

    @Override
    public void init() {
        category = (TextView) row.findViewById(R.id.agenda_row_category);
        title = (TextView) row.findViewById(R.id.agenda_row_title);
        image = (ImageView) row.findViewById(R.id.agenda_row_image);
    }
}
