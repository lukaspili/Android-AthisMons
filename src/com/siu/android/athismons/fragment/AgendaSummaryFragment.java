package com.siu.android.athismons.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.siu.android.athismons.R;
import com.siu.android.athismons.actionbar.TabSherlockFragment;
import com.siu.android.athismons.activity.AgendaListActivity;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AgendaSummaryFragment extends TabSherlockFragment {

    private ImageButton expositionButton, activityButton, showButton, sportButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agenda_summary_fragment, container, false);
        expositionButton = (ImageButton) view.findViewById(R.id.agenda_summary_exposition);
        activityButton = (ImageButton) view.findViewById(R.id.agenda_summary_activities);
        showButton = (ImageButton) view.findViewById(R.id.agenda_summary_shows);
        sportButton = (ImageButton) view.findViewById(R.id.agenda_summary_sport);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        expositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AgendaListActivity.class));
            }
        });

        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AgendaListActivity.class));
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AgendaListActivity.class));
            }
        });

        sportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AgendaListActivity.class));
            }
        });
    }

    @Override
    public void tabUnselected() {
    }
}
