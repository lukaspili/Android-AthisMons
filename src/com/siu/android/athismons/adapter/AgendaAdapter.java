package com.siu.android.athismons.adapter;

import android.content.Context;
import android.view.View;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.siu.android.andutils.adapter.SimpleAdapter;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.Agenda;
import com.siu.android.athismons.dao.model.News;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AgendaAdapter extends SimpleAdapter<Agenda, AgendaViewHolder> {

    public AgendaAdapter(Context context, List<Agenda> agendas) {
        super(context, R.layout.agenda_row, agendas);
    }

    @Override
    protected void configure(AgendaViewHolder viewHolder, Agenda agenda) {
        if (StringUtils.isNotEmpty(agenda.getCategory())) {
            viewHolder.category.setText(agenda.getCategory());
        } else {
            viewHolder.category.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(agenda.getImage())) {
            UrlImageViewHelper.setUrlDrawable(viewHolder.image, agenda.getImage());
        } else {
            viewHolder.image.setVisibility(View.GONE);
        }

        viewHolder.title.setText(agenda.getTitle());
    }

    @Override
    protected AgendaViewHolder createViewHolder() {
        return new AgendaViewHolder();
    }
}
