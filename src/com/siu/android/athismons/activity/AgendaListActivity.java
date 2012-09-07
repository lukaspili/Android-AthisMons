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
import com.siu.android.athismons.adapter.AgendaAdapter;
import com.siu.android.athismons.dao.model.Agenda;
import com.siu.android.athismons.task.AgendaLoadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AgendaListActivity extends TrackedSherlockActivity {

    private ListView listView;
    private AgendaAdapter adapter;

    private AgendaLoadTask loadTask;

    private List<Agenda> agendas = new ArrayList<Agenda>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.agenda_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list);

        adapter = new AgendaAdapter(this, agendas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AgendaListActivity.this, AgendaDetailActivity.class);
                intent.putExtra(AgendaDetailActivity.EXTRA, adapter.getItem(i));
                startActivity(intent);
            }
        });

        // task is already running
        if (null != loadTask) {
            setSupportProgressBarIndeterminateVisibility(true);
        }
        // run task only if list is empty and not previously loaded
        else if (agendas.isEmpty()) {
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
            Toast.makeText(this, R.string.agenda_fragment_refreshing, Toast.LENGTH_SHORT).show();
            startLoadTask();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private void startLoadTask() {
        stopLoadTaskIfRunning();

        setSupportProgressBarIndeterminateVisibility(true);
        loadTask = new AgendaLoadTask(this);
        loadTask.execute();
    }

    public void onLoadTaskProgress(List<Agenda> loadedAgendas) {
        if (null == loadedAgendas || loadedAgendas.isEmpty()) {
            return;
        }

        agendas.clear();
        agendas.addAll(loadedAgendas);
        adapter.notifyDataSetChanged();
    }

    protected void stopLoadTaskIfRunning() {
        if (null == loadTask) {
            return;
        }

        loadTask.cancel(true);
        loadTask = null;
        setSupportProgressBarIndeterminateVisibility(false);
    }

    public void onLoadTaskFinished(List<Agenda> loadedAgendas) {
        stopLoadTaskIfRunning();

        if (null == loadedAgendas) {
            // loaded elements are null and nothing came from progress
            if (agendas.isEmpty()) {
                Toast.makeText(this, R.string.agenda_fragment_loading_internet_failure, Toast.LENGTH_LONG).show();
            }
            return;
        }

        agendas.clear();
        agendas.addAll(loadedAgendas);
        adapter.notifyDataSetChanged();
    }
}
