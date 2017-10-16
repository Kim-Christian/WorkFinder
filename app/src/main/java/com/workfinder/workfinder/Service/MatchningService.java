package com.workfinder.workfinder.Service;

import android.os.AsyncTask;

import com.workfinder.workfinder.Data.MatchningParams;
import com.workfinder.workfinder.Data.Matchningslista;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Kim-Christian on 2017-08-28.
 */

public class MatchningService {
    private MatchningServiceCallback matchningServiceCallback;
    private Exception error;
    private String apiURL = "http://api.arbetsformedlingen.se/af/v0/platsannonser/matchning?";

    public MatchningService(
            MatchningServiceCallback matchningServiceCallback) {
        this.matchningServiceCallback = matchningServiceCallback;
    }

    public void refreshMatchning(final MatchningParams matchningParams) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String apiParams = "";
                if (matchningParams.hasLan()) {
                    apiParams += "lanid=" + matchningParams.getLanKod();
                }
                if (matchningParams.hasKommun()) {
                    if (!apiParams.equals("")) {
                        apiParams += "&";
                    }
                    apiParams += "kommunid=" + matchningParams.getKommunKod();
                }
                if (matchningParams.hasAnstallningstyp()) {
                    if (!apiParams.equals("")) {
                        apiParams += "&";
                    }
                    apiParams += "anstallningstyp=" + matchningParams.getAnstallningstypKod();
                }
                if (matchningParams.hasNyckelord()) {
                    if (!apiParams.equals("")) {
                        apiParams += "&";
                    }
                    apiParams += "nyckelord=" + matchningParams.getNyckelord();
                }
                String endpoint = apiURL + apiParams;
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
                    matchningServiceCallback.serviceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);
                    JSONObject response = data.getJSONObject("matchningslista");
                    Matchningslista matchningslista = new Matchningslista();
                    matchningslista.populate(response);
                    matchningServiceCallback.serviceSuccess(matchningslista);
                } catch (JSONException e) {
                    matchningServiceCallback.serviceFailure(e);
                }
            }
        }.execute();
    }
}
