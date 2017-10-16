package com.workfinder.workfinder.Data;

import org.json.JSONObject;

/**
 * Created by Kim-Christian on 2017-08-27.
 */

public class Villkor implements JSONPopulator {
    private String varaktighet;
    private String arbetstid;
    private String arbetstidvaraktighet;
    private String tilltrade;
    private String lonetyp;
    private String loneform;

    public String getVaraktighet() { return varaktighet; }
    public String getArbetstid() { return arbetstid; }
    public String getArbetstidvaraktighet() { return arbetstidvaraktighet; }
    public String getTilltrade() { return tilltrade; }
    public String getLonetyp() { return lonetyp; }
    public String getLoneform() { return loneform; }

    @Override
    public void populate(JSONObject data) {
        varaktighet = data.optString("varaktighet");
        arbetstid = data.optString("arbetstid");
        arbetstidvaraktighet = data.optString("arbetstidvaraktighet");
        tilltrade = data.optString("tilltrade");
        lonetyp = data.optString("lonetyp");
        loneform = data.optString("loneform");
    }
}
