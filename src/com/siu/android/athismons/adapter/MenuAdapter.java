package com.siu.android.athismons.adapter;

import android.content.Context;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.siu.android.andutils.adapter.SimpleAdapter;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.Menu;
import org.apache.commons.lang3.StringUtils;

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

        if (StringUtils.isNotEmpty(menu.getPicture())) {
//            viewHolder.image.setVisibility(View.VISIBLE);
            UrlImageViewHelper.setUrlDrawable(viewHolder.image, menu.getPicture());
        } else {
            viewHolder.image.setImageResource(R.drawable.default_row_image);
//            viewHolder.image.setVisibility(View.GONE);
        }
    }

    @Override
    protected MenuViewHolder createViewHolder() {
        return new MenuViewHolder();
    }
}
