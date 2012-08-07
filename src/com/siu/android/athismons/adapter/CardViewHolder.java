package com.siu.android.athismons.adapter;

import android.widget.TextView;
import com.siu.android.andutils.adapter.SimpleViewHolder;
import com.siu.android.athismons.R;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class CardViewHolder extends SimpleViewHolder {

    TextView title;

    @Override
    public void init() {
        title = (TextView) row.findViewById(R.id.card_row_title);
    }
}
