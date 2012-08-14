package com.siu.android.athismons.adapter;

import android.content.Context;
import com.siu.android.andutils.adapter.SimpleAdapter;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.Menu;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class MenuAdapter extends SimpleAdapter<Menu, MenuViewHolder> {

    public MenuAdapter(Context context, List<Menu> menus) {
        super(context, R.layout.menu_row, menus);
    }

    @Override
    protected void configure(MenuViewHolder viewHolder, Menu menu) {
        viewHolder.title.setText(menu.getTitle());
    }

    @Override
    protected MenuViewHolder createViewHolder() {
        return new MenuViewHolder();
    }
}
