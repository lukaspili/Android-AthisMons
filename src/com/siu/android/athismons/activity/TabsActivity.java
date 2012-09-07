package com.siu.android.athismons.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
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

        ActionBar.Tab tab = createTab("ACTUALITES", R.drawable.ic_tabs_news)
                .setTabListener(new TabListener<NewsFragment>(this, "News", NewsFragment.class));
        actionBar.addTab(tab);

        tab = createTab("AGENDA", R.drawable.ic_tabs_agenda)
                .setTabListener(new TabListener<AgendaSummaryFragment>(this, "Agenda", AgendaSummaryFragment.class));
        actionBar.addTab(tab);

        tab = createTab("INCIDENTS", R.drawable.ic_tabs_incident)
                .setTabListener(new TabListener<IncidentFragment>(this, "Incidents", IncidentFragment.class));
        actionBar.addTab(tab);

        tab = createTab("MENUS", R.drawable.ic_tabs_menu)
                .setTabListener(new TabListener<MenuFragment>(this, "Menus", MenuFragment.class));
        actionBar.addTab(tab);

        tab = createTab("RESSOURCES", R.drawable.ic_tabs_resources)
                .setTabListener(new TabListener<ResourcesFragment>(this, "Resources", ResourcesFragment.class));
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

    private ActionBar.Tab createTab(String title, int icon) {
        ActionBar.Tab tab = getSupportActionBar().newTab();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            tab.setCustomView(R.layout.tab);
            ((TextView) tab.getCustomView().findViewById(R.id.tab_title)).setText(title);
            ((ImageView) tab.getCustomView().findViewById(R.id.tab_icon)).setImageResource(icon);
        } else {
            tab.setIcon(icon);
            tab.setText(title);
        }

        return tab;
    }
}
