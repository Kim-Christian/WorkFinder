package com.workfinder.workfinder.Service;

import android.os.AsyncTask;

import com.workfinder.workfinder.Data.Platsannons;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Kim-Christian on 2017-08-31.
 */

public class PlatsannonsService {
    private PlatsannonsServiceCallback platsannonsServiceCallback;
    private Exception error;
    private String endpointURL = "http://api.arbetsformedlingen.se/af/v0/";

    public PlatsannonsService(
            PlatsannonsServiceCallback platsannonsServiceCallback) {
        this.platsannonsServiceCallback = platsannonsServiceCallback;
    }

    public void refreshPlatsannons(final String annonsid) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String endpoint = endpointURL + "platsannonser/" + annonsid;
                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Accept-Language", "sv");
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception e) {
                    error = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null && error != null) {
                    platsannonsServiceCallback.serviceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);
                    JSONObject response = data.getJSONObject("platsannons");
                    Platsannons platsannons = new Platsannons();
                    platsannons.populate(response);
                    platsannonsServiceCallback.serviceSuccess(platsannons);
                } catch (JSONException e) {
                    platsannonsServiceCallback.serviceFailure(e);
                }
            }
        }.execute(annonsid);
    }
}