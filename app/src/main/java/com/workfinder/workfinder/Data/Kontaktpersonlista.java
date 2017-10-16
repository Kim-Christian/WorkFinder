package com.workfinder.workfinder.Data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kim-Christian on 2017-08-27.
 */

public class Kontaktpersonlista implements JSONPopulator {
    private ArrayList<Kontaktpersondata> kontaktpersondata;

    public ArrayList<Kontaktpersondata> getKontaktpersondata() { return kontaktpersondata; }

    @Override
    public void populate(JSONObject data) {
        JSONArray k = data.optJSONArray("kontaktpersondata");
        if (k != null) {
            kontaktpersondata = new ArrayList<>();
            for (int i = 0; i < k.length(); i++) {
                kontaktpersondata.add(new Kontaktpersondata());
                kontaktpersondata.get(i).populate(k.optJSONObject(i));
            }
        }
    }
}
