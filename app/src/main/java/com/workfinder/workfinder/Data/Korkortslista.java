package com.workfinder.workfinder.Data;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Kim-Christian on 2017-08-27.
 */

public class Korkortslista implements JSONPopulator {
    private String[] korkortstyp;

    public String[] getKorkortstyp() { return korkortstyp; }

    @Override
    public void populate(JSONObject data) {
        /*JSONArray k = data.optJSONArray("korkortstyp");
        korkortstyp = new String[k.length()];
        for (int i = 0; i < k.length(); i++) {
            korkortstyp[i] = k.optString(i);
        }*/
    }
}
