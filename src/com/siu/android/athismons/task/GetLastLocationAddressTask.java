package com.siu.android.athismons.task;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.siu.android.andutils.location.GetLastLocation;
import com.siu.android.andutils.location.GetLastLocationFactory;
import com.siu.android.athismons.AppConstants;
import com.siu.android.athismons.Application;
import com.siu.android.athismons.fragment.IncidentFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class GetLastLocationAddressTask extends AsyncTask<Void, Void, Map<String, String>> {

    private IncidentFragment fragment;
    private Location location;

    public GetLastLocationAddressTask(IncidentFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected Map<String, String> doInBackground(Void... voids) {
        Log.d(getClass().getName(), "GetLastLocationAddressTask");

        GetLastLocation getLastLocation = GetLastLocationFactory.getLastLocationFinder(Application.getContext());
        getLastLocation.setChangedLocationListener(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                GetLastLocationAddressTask.this.location = location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        });

        try {
            location = getLastLocation.getLastBestLocation(AppConstants.MAX_DISTANCE, AppConstants.MAX_TIME);
        } catch (Exception e) {
            return null;
        }

//        MyLocation location1 = new MyLocation();
//        boolean res = location1.getLocation(fragment.getActivity(), new MyLocation.LocationResult() {
//            @Override
//            public void gotLocation(Location location) {
//                GetLastLocationAddressTask.this.location = location;
//            }
//        });

        if (isCancelled()) {
            return null;
        }

        long initialTimeMs = System.currentTimeMillis();
        while (null == location && System.currentTimeMillis() <= initialTimeMs + 10000 && !isCancelled()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return null;
            }
        }

        if (null == location || isCancelled()) {
            return null;
        }

        Geocoder geocoder = new Geocoder(Application.getContext(), Locale.FRANCE);

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty() || isCancelled()) {
                return null;
            }

            Address address = addresses.get(0);

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                builder.append(address.getAddressLine(i));
                if (i < address.getMaxAddressLineIndex()) {
                    builder.append(" ");
                }
            }

            Map<String, String> res = new HashMap<String, String>();
            res.put("latitude", String.valueOf(location.getLatitude()));
            res.put("longitude", String.valueOf(location.getLongitude()));
            res.put("address", builder.toString());

            return res;

        } catch (Exception e) {
            Log.e(getClass().getName(), "Cannot get address from location with geocoder", e);
            return null;
        }
    }


    @Override
    protected void onPostExecute(Map<String, String> res) {
        if (null == fragment.getActivity()) {
            return;
        }

        fragment.onGetLastLocationAddressTaskFinished(res);
    }


}
