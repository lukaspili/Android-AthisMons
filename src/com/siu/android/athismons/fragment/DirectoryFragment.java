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
import com.siu.android.andutils.http.HttpManager;
import com.siu.android.athismons.R;
import com.siu.android.athismons.actionbar.TabSherlockFragment;
import com.siu.android.athismons.activity.DirectoryDetailActivity;
import com.siu.android.athismons.adapter.DirectoryAdapter;
import com.siu.android.athismons.dao.model.Directory;
import com.siu.android.athismons.task.DirectoryLoadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DirectoryFragment extends TabSherlockFragment {

    private ListView listView;
    private DirectoryAdapter adapter;

    private DirectoryLoadTask loadTask;

    private List<Directory> directories = new ArrayList<Directory>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.directory_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new DirectoryAdapter(getActivity(), directories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DirectoryDetailActivity.class);
                intent.putExtra(DirectoryDetailActivity.EXTRA, adapter.getItem(i));
                startActivity(intent);
            }
        });

        // task is already running
        if (null != loadTask) {
            getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
        }
        // run task only if list is empty and not previously loaded
        else if (directories.isEmpty()) {
            startLoadTask();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.news_agenda_directory_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            Toast.makeText(getActivity(), R.string.directory_fragment_refreshing, Toast.LENGTH_SHORT).show();
            startLoadTask();
            return true;
        }

        return false;
    }

    @Override
    public void tabUnselected() {
        stopLoadTaskIfRunning();
    }

    private void startLoadTask() {
        stopLoadTaskIfRunning();

        getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
        loadTask = new DirectoryLoadTask(this);
        loadTask.execute();
    }

    private void stopLoadTaskIfRunning() {
        if (null == loadTask) {
            return;
        }

        HttpManager.getInstance().closeActivesRequests();
        loadTask.cancel(true);
        loadTask = null;
        getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);
    }

    public void onLoadTaskProgress(List<Directory> loadedDirectorys) {
        if (null == loadedDirectorys || loadedDirectorys.isEmpty()) {
            return;
        }

        directories.clear();
        directories.addAll(loadedDirectorys);
        adapter.notifyDataSetChanged();
    }

    public void onLoadTaskFinished(List<Directory> loadedDirectories) {
        stopLoadTaskIfRunning();

        if (null == loadedDirectories) {
            // loaded elements are null and nothing came from progress
            if (directories.isEmpty()) {
                Toast.makeText(getActivity(), R.string.directory_fragment_loading_internet_failure, Toast.LENGTH_LONG).show();
            }
            return;
        }

        directories.clear();
        directories.addAll(loadedDirectories);
        adapter.notifyDataSetChanged();
    }
}
