package com.siu.android.athismons.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.siu.android.andutils.activity.tracker.TrackedSherlockActivity;
import com.siu.android.athismons.R;
import com.siu.android.athismons.adapter.DirectoryAdapter;
import com.siu.android.athismons.dao.model.Directory;
import com.siu.android.athismons.task.DirectoryLoadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DirectoryActivity extends TrackedSherlockActivity {

    private ListView listView;
    private DirectoryAdapter adapter;

    private DirectoryLoadTask loadTask;

    private List<Directory> directories = new ArrayList<Directory>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.directory_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list);

        adapter = new DirectoryAdapter(this, directories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DirectoryActivity.this, DirectoryDetailActivity.class);
                intent.putExtra(DirectoryDetailActivity.EXTRA, adapter.getItem(i));
                startActivity(intent);
            }
        });

        // task is already running
        if (null != loadTask) {
            setSupportProgressBarIndeterminateVisibility(true);
        }
        // run task only if list is empty and not previously loaded
        else if (directories.isEmpty()) {
            startLoadTask();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.news_agenda_directory_fragment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            Toast.makeText(this, R.string.directory_fragment_refreshing, Toast.LENGTH_SHORT).show();
            startLoadTask();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private void startLoadTask() {
        if (null != loadTask) {
            loadTask.cancel(true);
            loadTask = null;
        }

        setSupportProgressBarIndeterminateVisibility(true);
        loadTask = new DirectoryLoadTask(this);
        loadTask.execute();
    }

    public void onLoadTaskFinished(List<Directory> loadedDirectories) {
        loadTask = null;
        setSupportProgressBarIndeterminateVisibility(false);

        if (null == loadedDirectories || loadedDirectories.isEmpty()) {
            Toast.makeText(this, R.string.directory_fragment_loading_internet_failure, Toast.LENGTH_LONG).show();
            return;
        }

        directories.clear();
        directories.addAll(loadedDirectories);
        adapter.notifyDataSetChanged();
    }
}
