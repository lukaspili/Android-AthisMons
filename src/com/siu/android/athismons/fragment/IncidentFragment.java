package com.siu.android.athismons.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.siu.android.andutils.util.FragmentUtils;
import com.siu.android.athismons.R;
import com.siu.android.athismons.actionbar.TabSherlockFragment;
import com.siu.android.athismons.dialog.IncidentLocationProgressDialog;
import com.siu.android.athismons.dialog.IncidentSubmitProgressDialog;
import com.siu.android.athismons.model.Incident;
import com.siu.android.athismons.task.GetLastLocationAddressTask;
import com.siu.android.athismons.task.IncidentSubmitTask;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class IncidentFragment extends TabSherlockFragment {

    private ImageButton locateButton, addPictureButton, sendButton;
    private TextView address, description, firstname, lastname, email;

    private Spinner incidentTypesSpinner;
    private ArrayAdapter incidentTypesAdapter;

    private Incident incident = new Incident();

    private GetLastLocationAddressTask getLastLocationAddressTask;
    private IncidentLocationProgressDialog incidentLocationProgressDialog;

    private IncidentSubmitTask incidentSubmitTask;
    private IncidentSubmitProgressDialog incidentSubmitProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incident_fragment, container, false);

        incidentTypesSpinner = (Spinner) view.findViewById(R.id.incident_types_spinner);
        addPictureButton = (ImageButton) view.findViewById(R.id.incident_add_picture_button);
        locateButton = (ImageButton) view.findViewById(R.id.incident_locate_button);
        sendButton = (ImageButton) view.findViewById(R.id.incident_send_button);
        description = (TextView) view.findViewById(R.id.incident_description);
        address = (TextView) view.findViewById(R.id.incident_address);
        lastname = (TextView) view.findViewById(R.id.incident_lastname);
        firstname = (TextView) view.findViewById(R.id.incident_firstname);
        email = (TextView) view.findViewById(R.id.incident_email);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSpinner();
        initButtons();

        if (null != incident.getBitmap()) {
//            addPictureButton.setText(R.string.incident_change_picture);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (null != getLastLocationAddressTask && getLastLocationAddressTask.getStatus() != AsyncTask.Status.FINISHED) {
            FragmentUtils.showDialog(getFragmentManager(), incidentLocationProgressDialog);
        } else if (null != incidentSubmitTask && incidentSubmitTask.getStatus() != AsyncTask.Status.FINISHED) {
            FragmentUtils.showDialog(getFragmentManager(), incidentSubmitProgressDialog);
        }
    }

    @Override
    public void tabUnselected() {

    }

    private void initSpinner() {
        incidentTypesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.incident_types, android.R.layout.simple_spinner_item);
        incidentTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incidentTypesSpinner.setAdapter(incidentTypesAdapter);
        incidentTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                incident.setTypeId(i);
                if (i == 0) {
                    incident.setType(null);
                } else {
                    incident.setType((String) incidentTypesAdapter.getItem(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initButtons() {
        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGetLastLocationAddress();
            }
        });

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), 1);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incident.setDescription(description.getText().toString());
                incident.setAddress(address.getText().toString());
                incident.setEmail(email.getText().toString());
                incident.setFirstname(firstname.getText().toString());
                incident.setLastname(lastname.getText().toString());

                startIncidentSubmitTask();
            }
        });
    }

    private void startGetLastLocationAddress() {
        if (null == incidentLocationProgressDialog) {
            incidentLocationProgressDialog = new IncidentLocationProgressDialog(IncidentFragment.this);
        }

        FragmentUtils.showDialog(getFragmentManager(), incidentLocationProgressDialog);

        getLastLocationAddressTask = new GetLastLocationAddressTask(IncidentFragment.this);
        getLastLocationAddressTask.execute();
    }

    public void stopGetLastLocationAddressTaskIfRunning() {
        if (null == getLastLocationAddressTask) {
            return;
        }

        getLastLocationAddressTask.cancel(true);
        getLastLocationAddressTask = null;
    }

    public void onGetLastLocationAddressTaskFinished(String receivedAddress) {
        try {
            incidentLocationProgressDialog.dismiss();
        } catch (Exception e) {
            Log.e(getClass().getName(), "Cannot dismiss IncidentLocationProgressDialog", e);
        }

        getLastLocationAddressTask = null;

        if (null == receivedAddress) {
            Toast.makeText(getActivity(), R.string.incident_address_location_failed, Toast.LENGTH_LONG).show();
        } else {
            address.setText(receivedAddress);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentData) {
        if (null == intentData.getExtras()) {
            return;
        }

        incident.setBitmap((Bitmap) intentData.getExtras().get("data"));
//        addPictureButton.setText(R.string.incident_change_picture);
    }

    public void startIncidentSubmitTask() {
        // validation
        if (incident.getTypeId() == 0) {
            Toast.makeText(getActivity(), "Veuillez renseigner un type d'incident", Toast.LENGTH_SHORT).show();
            return;
        } else if(incident.getAddress() == null) {
            Toast.makeText(getActivity(), "Veuillez renseigner l'adresse de l'incident", Toast.LENGTH_SHORT).show();
            return;
        }

        if (null == incidentSubmitProgressDialog) {
            incidentSubmitProgressDialog = new IncidentSubmitProgressDialog(this);
        }

        FragmentUtils.showDialog(getFragmentManager(), incidentSubmitProgressDialog);


        incidentSubmitTask = new IncidentSubmitTask(this, incident);
        incidentSubmitTask.execute();
    }

    public void onIncidentSubmitTaskFinished(Boolean result) {
        // ugly fix for strange exception happening with rotation
        try {
            incidentSubmitProgressDialog.dismiss();
        } catch (Exception e) {
            Log.e(getClass().getName(), "Cannot dismiss IncidentSubmitProgressDialog", e);
        }

        incidentSubmitTask = null;

        if (result) {
            Toast.makeText(getActivity(), R.string.incident_submit_success, Toast.LENGTH_LONG).show();
            incidentTypesSpinner.setPromptId(0);
            address.setText("");
            description.setText("");
            firstname.setText("");
            lastname.setText("");
            email.setText("");
        } else {
            Toast.makeText(getActivity(), R.string.incident_submit_failed, Toast.LENGTH_LONG).show();
        }
    }

    public void stopIncidentSubmitTaskIfRunning() {
        if (null == incidentSubmitTask) {
            return;
        }

        incidentSubmitTask.cancel(true);
        incidentSubmitTask = null;
    }
}
