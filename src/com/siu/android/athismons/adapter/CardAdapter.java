package com.siu.android.athismons.adapter;

import android.content.Context;
import com.siu.android.andutils.adapter.SimpleAdapter;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.Card;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class CardAdapter extends SimpleAdapter<Card, CardViewHolder> {

    public CardAdapter(Context context, List<Card> cards) {
        super(context, R.layout.card_row, cards);
    }

    @Override
    protected void configure(CardViewHolder viewHolder, Card card) {
        viewHolder.title.setText(card.getTitle());
    }

    @Override
    protected CardViewHolder createViewHolder() {
        return new CardViewHolder();
    }
}
