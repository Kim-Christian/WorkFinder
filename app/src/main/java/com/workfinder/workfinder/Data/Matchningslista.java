package com.workfinder.workfinder.Data;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Kim-Christian on 2017-08-26.
 */

public class Matchningslista implements JSONPopulator {
    private int antal_platsannonser;
    private int antal_sidor;
    private Matchningdata[] matchningdata;

    public int getAntal_platsannonser() { return antal_platsannonser; }
    public int getAntal_sidor() { return antal_sidor; }
    public Matchningdata[] getMatchningdata() { return matchningdata; }

    @Override
    public void populate(JSONObject data) {
        antal_platsannonser = data.optInt("antal_platsannonser");
        antal_sidor = data.optInt("antal_sidor");
        JSONArray m = data.optJSONArray("matchningdata");
        if (m != null) {
            matchningdata = new Matchningdata[m.length()];
            for (int i = 0; i < m.length(); i++) {
                matchningdata[i] = new Matchningdata();
                matchningdata[i].populate(m.optJSONObject(i));
            }
        } else {
            matchningdata = new Matchningdata[0];
        }
    }
}
