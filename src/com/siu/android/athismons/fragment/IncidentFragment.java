package com.siu.android.athismons.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.siu.android.andutils.util.FragmentUtils;
import com.siu.android.athismons.AppConstants;
import com.siu.android.athismons.R;
import com.siu.android.athismons.actionbar.TabSherlockFragment;
import com.siu.android.athismons.dialog.IncidentLocationProgressDialog;
import com.siu.android.athismons.dialog.IncidentSubmitProgressDialog;
import com.siu.android.athismons.model.Incident;
import com.siu.android.athismons.task.GetLastLocationAddressTask;
import com.siu.android.athismons.task.IncidentSubmitTask;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class IncidentFragment extends TabSherlockFragment {

    private ImageButton locateButton, addPictureButton, sendButton;
    private TextView address, description, name, email;
    private ImageView pictureImageView;

    private Spinner incidentTypesSpinner;
    private ArrayAdapter incidentTypesAdapter;

    private Incident incident;

    private GetLastLocationAddressTask getLastLocationAddressTask;
    private IncidentLocationProgressDialog incidentLocationProgressDialog;

    private TextWatcher addressTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            incident.setLatitude(null);
            incident.setLongitude(null);

            address.removeTextChangedListener(this);
        }
    };

    private IncidentSubmitTask incidentSubmitTask;
    private IncidentSubmitProgressDialog incidentSubmitProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Log.d(getClass().getName(), "Incident = " + incident);

//        if (null != savedInstanceState) {
//            incident = (Incident) savedInstanceState.get("incident");
//        } else {
        incident = new Incident();
//        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("incident", incident);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incident_fragment, container, false);

        incidentTypesSpinner = (Spinner) view.findViewById(R.id.incident_types_spinner);
        addPictureButton = (ImageButton) view.findViewById(R.id.incident_add_picture_button);
        locateButton = (ImageButton) view.findViewById(R.id.incident_locate_button);
        sendButton = (ImageButton) view.findViewById(R.id.incident_send_button);
        description = (TextView) view.findViewById(R.id.incident_description);
        address = (TextView) view.findViewById(R.id.incident_address);
        name = (TextView) view.findViewById(R.id.incident_name);
        email = (TextView) view.findViewById(R.id.incident_email);
        pictureImageView = (ImageView) view.findViewById(R.id.incident_picture_preview);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSpinner();
        initButtons();

        if (null != incident.getPictureUri()) {
            pictureImageView.setImageURI(Uri.parse(incident.getPictureUri()));
            pictureImageView.setVisibility(View.VISIBLE);
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
                startCamera();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportIncident();
            }
        });
    }

    private void startCamera() {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "Athis-Mons incident " + new Date().getTime());
            values.put(MediaStore.Images.Media.DESCRIPTION, "Athis-Mons incident");
            Uri imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            incident.setPictureUri(imageUri.toString()); // store temp futur picture uri

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Impossible de lancer l'appareil photo", Toast.LENGTH_SHORT).show();
            Log.e(getClass().getName(), "Cannot start camera", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentData) {
        if (requestCode != 1) {
            return;
        }

        if (resultCode != Activity.RESULT_OK) {
            incident.setPictureUri(null);
            pictureImageView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Photo annulée", Toast.LENGTH_SHORT).show();
            return;
        }

        // get full size picture real path
        try {
            Uri uri = Uri.parse(incident.getPictureUri());

            // show pic
            pictureImageView.setImageURI(uri);
            pictureImageView.setVisibility(View.VISIBLE);

//            String[] proj = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getActivity().managedQuery(uri, proj, null, null, null);
//            cursor.moveToFirst();
//            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            incident.setPictureUri(path);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Impossible d'enregister la photo.", Toast.LENGTH_SHORT).show();
            Log.e(getClass().getName(), "Cannot save the picture in the media store", e);
            return;
        }

        Toast.makeText(getActivity(), "Photo enregistrée !", Toast.LENGTH_SHORT).show();
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

    public void onGetLastLocationAddressTaskFinished(Map<String, String> res) {
        try {
            incidentLocationProgressDialog.dismiss();
        } catch (Exception e) {
            Log.e(getClass().getName(), "Cannot dismiss IncidentLocationProgressDialog", e);
        }

        getLastLocationAddressTask = null;

        if (null == res) {
            Toast.makeText(getActivity(), R.string.incident_address_location_failed, Toast.LENGTH_LONG).show();
        } else {
            address.setText(res.get("address"));
            incident.setLatitude(res.get("latitude"));
            incident.setLongitude(res.get("longitude"));

            // listen manual changes on address edittext
            address.addTextChangedListener(addressTextWatcher);
        }
    }

    public void reportIncident() {
        incident.setDescription(description.getText().toString());
        incident.setAddress(address.getText().toString());
        incident.setEmail(email.getText().toString());
        incident.setName(name.getText().toString());

        // validation
        boolean valid = true;
        StringBuilder builder = new StringBuilder("Veuillez renseigner les champs suivants : ");

        if (incident.getTypeId() == 0) {
            builder.append("type de l'incident, ");
            valid = false;
        }

        if (StringUtils.isBlank(incident.getAddress())) {
            builder.append("adresse, ");
            valid = false;
        }

        if (StringUtils.isBlank(incident.getDescription())) {
            builder.append("description, ");
            valid = false;
        }

        if (StringUtils.isBlank(incident.getName())) {
            builder.append("nom, ");
            valid = false;
        }

        if (StringUtils.isBlank(incident.getEmail())) {
            builder.append("email, ");
            valid = false;
        }

        if (!valid) {
            builder.delete(builder.length() - 2, builder.length()); // remove the last ", "
            builder.append(".");
            Toast.makeText(getActivity(), builder.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        startIncidentSubmitTask();
    }

    public void startIncidentSubmitTask() {
        stopIncidentSubmitTaskIfRunning();

        if (null == incidentSubmitProgressDialog) {
            incidentSubmitProgressDialog = new IncidentSubmitProgressDialog(this);
        }

        FragmentUtils.showDialog(getFragmentManager(), incidentSubmitProgressDialog);

        incidentSubmitTask = new IncidentSubmitTask(incident, this);
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

        // reported with success, reset all fields and incident
        if (result) {
            Toast.makeText(getActivity(), R.string.incident_submit_success, Toast.LENGTH_LONG).show();
            incidentTypesSpinner.setSelection(0);
            address.setText("");
            description.setText("");
            name.setText("");
            email.setText("");
            pictureImageView.setVisibility(View.GONE);
            incident = new Incident();
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

    private void prepareAndSendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        // headers
        String[] to = null;
        switch (incident.getTypeId()) {
            case 1:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT1);
                break;
            case 2:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT2);
                break;
            case 3:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT3);
                break;
            case 4:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT4);
                break;
            case 5:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT5);
                break;
            case 6:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT6);
                break;
            case 7:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT7);
                break;
            case 8:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT8);
                break;
            case 9:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT9);
                break;
            case 10:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT10);
                break;
            case 11:
                to = ArrayUtils.addAll(AppConstants.INCIDENT_EMAIL_GENERAL, AppConstants.INCIDENT_EMAIL_CAT11);
                break;
        }

        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Athis-Mons déclaration d'incident");

        // body
        StringBuilder builder = new StringBuilder("Déclaration d'un incident sur Athis-Mons\n\n");
        builder.append("Type d'incident : ").append(incident.getType()).append("\n");
        builder.append("Addresse : ").append(incident.getAddress()).append("\n");
        builder.append("Description : ").append(incident.getDescription()).append("\n");
//        builder.append("Nom : ").append(incident.getLastname()).append("\n");
//        builder.append("Prénom : ").append(incident.getFirstname()).append("\n");
        builder.append("Email : ").append(incident.getEmail()).append("\n\n");

        // lat/long exists from geocoder
        if (null != incident.getLatitude()) {
            String mapUrl = String.format(AppConstants.INCIDENT_MAP_URL, incident.getLatitude(), incident.getLongitude());
            builder.append("Voir l'incident sur la carte : ").append(mapUrl).append("\n\n");
        }

        builder.append("Envoyé depuis l'application Athis-Mons Android");
        intent.putExtra(Intent.EXTRA_TEXT, builder.toString());

        // picture attachment
        if (null != incident.getPictureUri()) {
            try {
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + incident.getPictureUri()));
            } catch (Exception e) {
                Log.e(getClass().getName(), "Cannot parse picture path as uri", e);
            }
        }

        try {
            startActivity(Intent.createChooser(intent, "Envoyer la déclaration d'incident par email"));
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Impossible de préparer l'envoi de l'incident par email", Toast.LENGTH_SHORT).show();
            Log.e(getClass().getName(), "Cannot start email intent", e);
        }
    }
}
