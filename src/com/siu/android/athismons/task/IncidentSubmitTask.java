package com.siu.android.athismons.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.entity.mime.HttpMultipartMode;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntity;
import ch.boye.httpclientandroidlib.entity.mime.content.ByteArrayBody;
import ch.boye.httpclientandroidlib.entity.mime.content.StringBody;
import com.siu.android.andutils.http.SimpleHttpRequest;
import com.siu.android.athismons.AppConstants;
import com.siu.android.athismons.Mail;
import com.siu.android.athismons.fragment.IncidentFragment;
import com.siu.android.athismons.model.Incident;
import org.apache.commons.lang3.ArrayUtils;

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
        Log.d(getClass().getName(), "IncidentSubmitTask");

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        Bitmap bitmap = incident.getBitmap();
//        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//
//        if (null != bitmap) {
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
//            byte[] data = bos.toByteArray();
//
//            entity.addPart("picture", new ByteArrayBody(data, "incident.jpg"));
//        }
//
//        try {
//            if (null != incident.getType()) {
//                entity.addPart("type", new StringBody(incident.getType()));
//            }
//
//            if (null != incident.getDescription()) {
//                entity.addPart("description", new StringBody(incident.getDescription()));
//            }
//
//            if (null != incident.getAddress()) {
//                entity.addPart("address", new StringBody(incident.getAddress()));
//            }
//
//            if (null != incident.getEmail()) {
//                entity.addPart("email", new StringBody(incident.getEmail()));
//            }
//
//            if (null != incident.getFirstname()) {
//                entity.addPart("firstname", new StringBody(incident.getFirstname()));
//            }
//
//            if (null != incident.getLastname()) {
//                entity.addPart("lastname", new StringBody(incident.getLastname()));
//            }
//        } catch (Exception e) {
//            Log.e(getClass().getName(), "Entity construction failure", e);
//        }
//
//        HttpResponse response = new SimpleHttpRequest.Builder()
//                .url(AppConstants.INCIDENT_SUBMIT_URL)
//                .requestEntity(entity)
//                .build().request();
//
//        if (null == response) {
//            return false;
//        }

        Mail mail = new Mail("siudemo@gmail.com", "siudemo99");
        String[] to = null;

        switch (incident.getTypeId()) {
            case 0:
                return false;
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

        // build and send mail
        if (AppConstants.DEBUG) {
            mail.set_to(new String[]{"lukasz.pili@gmail.com"});
        } else {
            mail.set_to(to);
        }

        mail.set_from("siudemo@gmail.com");
        mail.set_subject("Incident report");

        StringBuilder builder = new StringBuilder("Incident report\n\n");
        builder.append("Type d'incident : ").append(incident.getType()).append("\n");
        builder.append("Addresse : ").append(incident.getAddress()).append("\n");
        builder.append("Description : ").append(incident.getDescription()).append("\n");
        builder.append("Nom : ").append(incident.getLastname()).append("\n");
        builder.append("Prénom : ").append(incident.getFirstname()).append("\n");
        builder.append("Email : ").append(incident.getEmail()).append("\n");
        builder.append("Photo jointe : ").append("Pas de photo jointe").append("\n\n");
        builder.append("Envoyé depuis l'application android");

        mail.setBody(builder.toString());

        try {
            return mail.send();
        } catch (Exception e) {
            Log.e(getClass().getName(), "Sending mail failure", e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (null == fragment.getActivity()) {
            return;
        }

        fragment.onIncidentSubmitTaskFinished(aBoolean);
    }
}
