package com.siu.android.athismons.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.actionbarsherlock.app.SherlockFragment;
import com.siu.android.andutils.util.FragmentUtils;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dialog.IncidentTypesDialog;
import com.siu.android.athismons.model.Incident;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class IncidentFragment extends SherlockFragment {

    private Button incidentTypesButton, addPictureButton, locateButton, sendButton;

    private Incident incident = new Incident();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incident_fragment, container, false);

        incidentTypesButton = (Button) view.findViewById(R.id.incident_type_button);
        addPictureButton = (Button) view.findViewById(R.id.incident_add_picture_button);
        locateButton = (Button) view.findViewById(R.id.incident_locate_button);
        sendButton = (Button) view.findViewById(R.id.incident_send_button);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initButtons();
        updateIncident();
    }

    private void initButtons() {
        incidentTypesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtils.showDialog(getFragmentManager(), new IncidentTypesDialog(IncidentFragment.this));
            }
        });
    }

    private void updateIncident() {
        if (null != incident.getType()) {
            incidentTypesButton.setText(incident.getType());
        }

//        if (null != incident.getDescription()) {
//            de.setText(incident.getType());
//        }
    }

    public void updateIncidentType(String type) {
        incident.setType(type);
        incidentTypesButton.setText(type);
    }
}
