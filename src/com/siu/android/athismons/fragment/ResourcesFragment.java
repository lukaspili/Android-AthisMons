package com.siu.android.athismons.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.actionbarsherlock.app.SherlockFragment;
import com.siu.android.athismons.R;
import com.siu.android.athismons.actionbar.TabSherlockFragment;
import com.siu.android.athismons.activity.DirectoryActivity;
import com.siu.android.athismons.activity.ProcessesActivity;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class ResourcesFragment extends TabSherlockFragment {

    private ImageButton processesButton, directoryButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resources_fragment, container, false);
        processesButton = (ImageButton) view.findViewById(R.id.resources_processes);
        directoryButton = (ImageButton) view.findViewById(R.id.resources_directory);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        processesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProcessesActivity.class));
            }
        });

        directoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DirectoryActivity.class));
            }
        });
    }

    @Override
    public void tabUnselected() {
    }
}
