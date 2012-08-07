package com.siu.android.athismons.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.view.MenuItem;
import com.siu.android.andutils.activity.tracker.TrackedSherlockActivity;
import com.siu.android.athismons.R;
import com.siu.android.athismons.adapter.CardAdapter;
import com.siu.android.athismons.dao.model.Card;
import com.siu.android.athismons.dao.model.Directory;
import com.siu.android.athismons.task.CardLoadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DirectoryDetailActivity extends TrackedSherlockActivity {

    public static final String EXTRA = "extra";

    private ListView listView;
    private TextView title;

    private Directory directory;

    private CardLoadTask cardLoadTask;

    private CardAdapter adapter;
    private List<Card> cards = new ArrayList<Card>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.directory_detail_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list);
        title = (TextView) findViewById(R.id.directory_detail_title);

        if (null != savedInstanceState) {
            directory = (Directory) savedInstanceState.get("directory");
            cards = (List<Card>) savedInstanceState.get("cards");
        } else {
            directory = (Directory) getIntent().getExtras().get(EXTRA);
            cards = new ArrayList<Card>();
        }

        adapter = new CardAdapter(this, cards);
        listView.setAdapter(adapter);
        title.setText(directory.getTitle());

        cardLoadTask = (CardLoadTask) getLastNonConfigurationInstance();
        if (null != cardLoadTask) {
            cardLoadTask.setActivity(this);
            setSupportProgressBarIndeterminateVisibility(true);
        } else {
            startCardLoadTask();
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        super.onRetainNonConfigurationInstance();

        if (null != cardLoadTask) {
            cardLoadTask.setActivity(null);
            return cardLoadTask;
        }

        return null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("directory", directory);
        outState.putSerializable("cards", (ArrayList<Card>) cards);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startCardLoadTask() {
        if (null != cardLoadTask) {
            cardLoadTask.cancel(true);
            cardLoadTask = null;
        }

        setSupportProgressBarIndeterminateVisibility(true);
        cardLoadTask = new CardLoadTask(this, directory);
        cardLoadTask.execute();
    }

    public void onCardLoadTaskFinished(List<Card> loadedCards) {
        cardLoadTask = null;
        setSupportProgressBarIndeterminateVisibility(false);

        if (null == loadedCards || loadedCards.isEmpty()) {
            Toast.makeText(this, R.string.directory_detail_activity_cards_empty, Toast.LENGTH_LONG).show();
            return;
        }

        cards.clear();
        cards.addAll(loadedCards);
        adapter.notifyDataSetChanged();
    }
}
