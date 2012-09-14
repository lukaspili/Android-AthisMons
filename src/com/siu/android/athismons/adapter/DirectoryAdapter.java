package com.siu.android.athismons.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.siu.android.andutils.adapter.SimpleAdapter;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.Directory;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DirectoryAdapter extends SimpleAdapter<Directory, DirectoryViewHolder> {

    public DirectoryAdapter(Context context, List<Directory> directories) {
        super(context, R.layout.directory_row, directories);
    }

    @Override
    protected void configure(DirectoryViewHolder viewHolder, Directory directory) {
        viewHolder.title.setText(directory.getTitle());

        if (StringUtils.isNotEmpty(directory.getListPicture())) {
            viewHolder.image.setVisibility(View.VISIBLE);
            UrlImageViewHelper.setUrlDrawable(viewHolder.image, directory.getListPicture());
        } else {
            viewHolder.image.setVisibility(View.GONE);
        }
    }

    @Override
    protected DirectoryViewHolder createViewHolder() {
        return new DirectoryViewHolder();
    }
}
