package com.siu.android.athismons.activity;

import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import com.siu.android.andutils.activity.tracker.TrackedSherlockFragmentActivity;
import com.siu.android.athismons.R;
import com.siu.android.athismons.actionbar.TabListener;
import com.siu.android.athismons.fragment.*;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class TabsActivity extends TrackedSherlockFragmentActivity {

    public final static String EXTRA_SELECTED_TAB_ID = "selected_tab_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.main_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.tab_news)
                .setTabListener(new TabListener<NewsFragment>(this, "News", NewsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.tab_agenda)
                .setTabListener(new TabListener<AgendaFragment>(this, "Agenda", AgendaFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.tab_incidents)
                .setTabListener(new TabListener<IncidentFragment>(this, "Incidents", IncidentFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.tab_menus)
                .setTabListener(new TabListener<MenuFragment>(this, "Menus", MenuFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.tab_directory)
                .setTabListener(new TabListener<DirectoryFragment>(this, "Directory", DirectoryFragment.class));
        actionBar.addTab(tab);

        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt(EXTRA_SELECTED_TAB_ID));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_SELECTED_TAB_ID, getSupportActionBar().getSelectedNavigationIndex());
    }
}
