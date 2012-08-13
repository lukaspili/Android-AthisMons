package com.siu.android.athismons.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.entity.mime.HttpMultipartMode;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntity;
import ch.boye.httpclientandroidlib.entity.mime.content.ByteArrayBody;
import ch.boye.httpclientandroidlib.entity.mime.content.StringBody;
import com.siu.android.andutils.http.SimpleHttpRequest;
import com.siu.android.athismons.AppConstants;
import com.siu.android.athismons.fragment.IncidentFragment;
import com.siu.android.athismons.model.Incident;

import java.io.ByteArrayOutputStream;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class IncidentSubmitTask extends AsyncTask<Void, Void, Boolean> {

    private IncidentFragment fragment;
    private Incident incident;

    public IncidentSubmitTask(IncidentFragment fragment, Incident incident) {
        this.fragment = fragment;
        this.incident = incident;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Bitmap bitmap = incident.getBitmap();
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        if (null != bitmap) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
            byte[] data = bos.toByteArray();

            entity.addPart("picture", new ByteArrayBody(data, "incident.jpg"));
        }

        try {
            if (null != incident.getType()) {
                entity.addPart("type", new StringBody(incident.getType()));
            }

            if (null != incident.getDescription()) {
                entity.addPart("description", new StringBody(incident.getDescription()));
            }

            if (null != incident.getAddress()) {
                entity.addPart("address", new StringBody(incident.getAddress()));
            }

            if (null != incident.getEmail()) {
                entity.addPart("email", new StringBody(incident.getEmail()));
            }

            if (null != incident.getFirstname()) {
                entity.addPart("firstname", new StringBody(incident.getFirstname()));
            }

            if (null != incident.getLastname()) {
                entity.addPart("lastname", new StringBody(incident.getLastname()));
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), "Entity construction failure", e);
        }

        HttpResponse response = new SimpleHttpRequest.Builder()
                .url(AppConstants.INCIDENT_SUBMIT_URL)
                .requestEntity(entity)
                .build().request();

        if (null == response) {
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (null == fragment.getActivity()) {
            return;
        }

        fragment.onIncidentSubmitTaskFinished(aBoolean);
    }
}
