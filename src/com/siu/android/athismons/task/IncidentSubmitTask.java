package com.siu.android.athismons.task;

import android.os.AsyncTask;
import android.util.Log;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.entity.mime.content.FileBody;
import com.siu.android.andutils.http.SimpleHttpRequest;
import com.siu.android.andutils.util.NetworkUtils;
import com.siu.android.athismons.AppConstants;
import com.siu.android.athismons.fragment.IncidentFragment;
import com.siu.android.athismons.model.Incident;

import java.io.File;
import java.net.URI;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class IncidentSubmitTask extends AsyncTask<Void, Void, Boolean> {

    private Incident incident;
    private IncidentFragment fragment;

    public IncidentSubmitTask(Incident incident, IncidentFragment fragment) {
        this.incident = incident;
        this.fragment = fragment;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        if (!NetworkUtils.isOnline()) {
            return false;
        }

        SimpleHttpRequest.Builder requestBuilder = new SimpleHttpRequest.Builder()
                .url(AppConstants.INCIDENT_URL)
                .requestMethod(SimpleHttpRequest.Method.POST)
                .requestParam("email", incident.getEmail())
                .requestParam("nom", incident.getName());

        if (null != incident.getPictureUri()) {
            try {
                FileBody fileBody = new FileBody(new File(new URI(incident.getPictureUri().toString())), "photo.jpg", "image/jpg", null);
                requestBuilder.requestFileBody("img", fileBody);
            } catch (Exception e) {
                Log.e(getClass().getName(), "Cannot get picture to upload", e);
            }
        }

        SimpleHttpRequest request = requestBuilder.build();

        try {
            HttpResponse response = request.request();
            return response.getStatusLine().getStatusCode() == 200;
        } catch (Exception e) {
            Log.e(getClass().getName(), "Cannot execute request", e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean res) {
        if (null == fragment.getActivity()) {
            return;
        }

        fragment.onIncidentSubmitTaskFinished(res);
    }
}
