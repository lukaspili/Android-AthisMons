package com.siu.android.athismons.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import com.siu.android.andutils.adapter.SimpleViewHolder;
import com.siu.android.athismons.R;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class MenuViewHolder extends SimpleViewHolder {

    TextView title;
    ImageView image;

    @Override
    public void init() {
        title = (TextView) row.findViewById(R.id.menu_row_title);
        image = (ImageView) row.findViewById(R.id.menu_row_image);
    }
}
