package com.siu.android.athismons.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import com.actionbarsherlock.app.ActionBar;
import com.siu.android.andutils.activity.tracker.TrackedSherlockFragmentActivity;
import com.siu.android.athismons.R;
import com.siu.android.athismons.fragment.*;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class TabsActivity extends TrackedSherlockFragmentActivity {

    public static final String EXTRA_SELECTED_TAB_ID = "selected_tab_id";
    private static final String[] FRAGMENTS = {NewsFragment.class.getName(), AgendaFragment.class.getName(), IncidentFragment.class.getName(), MenuFragment.class.getName(), DirectoryFragment.class.getName(), PersonalSpaceFragment.class.getName()};

    private SpinnerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.main_activity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        adapter = ArrayAdapter.createFromResource(this, R.array.tabs,
                android.R.layout.simple_spinner_dropdown_item);

        actionBar.setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {

            private int lastPosition = -1;

            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                String selectedTag = FRAGMENTS[itemPosition];

                Fragment fragment = getSupportFragmentManager().findFragmentByTag(selectedTag);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                /** Used to avoid the same fragment being reattached. */
                if (lastPosition != itemPosition) {
                    /** Means there was a previous fragment attached. */
                    if (lastPosition != -1) {
                        Fragment lastFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENTS[lastPosition]);
                        if (lastFragment != null) {
                            ft.detach(lastFragment);
                        }
                    }

                    if (fragment == null) {
                        /** The fragment is being added for the first time. */
                        fragment = Fragment.instantiate(TabsActivity.this, selectedTag);
                        ft.add(android.R.id.content, fragment, selectedTag);
                    } else {
                        ft.attach(fragment);
                    }

                    ft.commit();
                }

                /**
                 * The newly attached fragment will be the last position if changed.
                 */
                lastPosition = itemPosition;

                return true;
            }
        });

//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        ActionBar.Tab tab = createTab("ACTUALITES", R.drawable.ic_tabs_news)
//                .setTabListener(new TabListener<NewsFragment>(this, "News", NewsFragment.class));
//        actionBar.addTab(tab);
//
//        tab = createTab("AGENDA", R.drawable.ic_tabs_agenda)
//                .setTabListener(new TabListener<AgendaFragment>(this, "Agenda", AgendaFragment.class));
//        actionBar.addTab(tab);
//
//        tab = createTab("INCIDENTS", R.drawable.ic_tabs_incident)
//                .setTabListener(new TabListener<IncidentFragment>(this, "Incidents", IncidentFragment.class));
//        actionBar.addTab(tab);
//
//        tab = createTab("MENUS", R.drawable.ic_tabs_menu)
//                .setTabListener(new TabListener<MenuFragment>(this, "Menus", MenuFragment.class));
//        actionBar.addTab(tab);
//
//        tab = createTab("RESSOURCES", R.drawable.ic_tabs_resources)
//                .setTabListener(new TabListener<ResourcesFragment>(this, "Resources", ResourcesFragment.class));
//        actionBar.addTab(tab);

        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt(EXTRA_SELECTED_TAB_ID));
        } else {
            actionBar.setSelectedNavigationItem(0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_SELECTED_TAB_ID, getSupportActionBar().getSelectedNavigationIndex());
    }

    private ActionBar.Tab createTab(String title, int icon) {
        ActionBar.Tab tab = getSupportActionBar().newTab();

//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            tab.setCustomView(R.layout.tab);
//            ((TextView) tab.getCustomView().findViewById(R.id.tab_title)).setText(title);
//            ((ImageView) tab.getCustomView().findViewById(R.id.tab_icon)).setImageResource(icon);
//        } else {
//            tab.setIcon(icon);
//            tab.setText(title);
//        }

        tab.setIcon(icon);
        tab.setText(title);

        return tab;
    }
}
