package com.siu.android.athismons.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.siu.android.athismons.R;
import com.siu.android.athismons.activity.NewsDetailActivity;
import com.siu.android.athismons.adapter.NewsAdapter;
import com.siu.android.athismons.dao.model.News;
import com.siu.android.athismons.task.NewsLoadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class NewsFragment extends SherlockFragment {

    private ListView listView;
    private NewsAdapter adapter;

    private NewsLoadTask newsLoadTask;

    private List<News> newses = new ArrayList<News>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new NewsAdapter(getActivity(), newses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.EXTRA, adapter.getItem(i));
                startActivity(intent);
            }
        });

        if (null != newsLoadTask) {
            newsLoadTask.setFragment(this);
            getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
        } else {
            startNewsLoadTask();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (null != newsLoadTask) {
            newsLoadTask.setFragment(null);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.news_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.news_fragment_menu_refresh) {
            Toast.makeText(getActivity(), R.string.news_fragment_refreshing, Toast.LENGTH_SHORT).show();
            startNewsLoadTask();
            return true;
        }

        return false;
    }

    private void startNewsLoadTask() {
        if (null != newsLoadTask) {
            newsLoadTask.cancel(true);
            newsLoadTask = null;
        }

        getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
        newsLoadTask = new NewsLoadTask(this);
        newsLoadTask.execute();
    }

    public void onNewsLoadTaskProgress(List<News> loadedNewses) {
        if (null == loadedNewses || loadedNewses.isEmpty()) {
            return;
        }

        newses.clear();
        newses.addAll(loadedNewses);
        adapter.notifyDataSetChanged();
    }

    public void onNewsLoadTaskFinished(List<News> loadedNewses) {
        newsLoadTask = null;
        getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);

        if (null == loadedNewses) {
            Toast.makeText(getActivity(), R.string.news_fragment_loading_internet_failure, Toast.LENGTH_LONG).show();
            return;
        }

        newses.clear();
        newses.addAll(loadedNewses);
        adapter.notifyDataSetChanged();
    }
}
