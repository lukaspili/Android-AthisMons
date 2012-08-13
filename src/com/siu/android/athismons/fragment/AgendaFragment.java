package com.siu.android.athismons.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.siu.android.andutils.http.HttpManager;
import com.siu.android.athismons.R;
import com.siu.android.athismons.actionbar.TabSherlockFragment;
import com.siu.android.athismons.activity.AgendaDetailActivity;
import com.siu.android.athismons.adapter.AgendaAdapter;
import com.siu.android.athismons.dao.model.Agenda;
import com.siu.android.athismons.task.AgendaLoadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AgendaFragment extends TabSherlockFragment {

    private ListView listView;
    private AgendaAdapter adapter;

    private AgendaLoadTask loadTask;

    private List<Agenda> agendas = new ArrayList<Agenda>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agenda_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new AgendaAdapter(getActivity(), agendas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), AgendaDetailActivity.class);
                intent.putExtra(AgendaDetailActivity.EXTRA, adapter.getItem(i));
                startActivity(intent);
            }
        });

        // task is already running
        if (null != loadTask) {
            getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
        }
        // run task only if list is empty and not previously loaded
        else if (agendas.isEmpty()) {
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
            Toast.makeText(getActivity(), R.string.agenda_fragment_refreshing, Toast.LENGTH_SHORT).show();
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

        HttpManager.getInstance().closeActivesRequests();
        loadTask.cancel(true);
        loadTask = null;
        getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);
    }

    public void onLoadTaskFinished(List<Agenda> loadedAgendas) {
        stopLoadTaskIfRunning();

        if (null == loadedAgendas) {
            // loaded elements are null and nothing came from progress
            if (agendas.isEmpty()) {
                Toast.makeText(getActivity(), R.string.agenda_fragment_loading_internet_failure, Toast.LENGTH_LONG).show();
            }
            return;
        }

        agendas.clear();
        agendas.addAll(loadedAgendas);
        adapter.notifyDataSetChanged();
    }
}
