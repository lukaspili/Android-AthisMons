package com.siu.android.athismons.actionbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class TabListener<T extends TabSherlockFragment> implements ActionBar.TabListener {

    private final SherlockFragmentActivity activity;
    private final String tag;
    private final Class<T> clazz;
    private final Bundle args;
    private T fragment;

    public TabListener(SherlockFragmentActivity activity, String tag, Class<T> clz) {
        this(activity, tag, clz, null);
    }

    public TabListener(SherlockFragmentActivity activity, String tag, Class<T> clazz, Bundle args) {
        this.activity = activity;
        this.tag = tag;
        this.clazz = clazz;
        this.args = args;

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        fragment = (T) activity.getSupportFragmentManager().findFragmentByTag(tag);

        //Avec ce code on charge 2 fois le dernier tab et on perd des trucs (sur le impactfragment)
        /*
        if(fragment != null) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(fragment);
            ft.commit();
        }
        */
    }

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (fragment == null) {
            fragment = (T) Fragment.instantiate(activity, clazz.getName(), args);
            ft.add(android.R.id.content, fragment, tag);
        } else {
            ft.attach(fragment);
        }
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (fragment != null) {
            fragment.tabUnselected();
            ft.detach(fragment);
        }
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

}