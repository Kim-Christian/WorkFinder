package com.workfinder.workfinder.Data;

import org.json.JSONObject;

/**
 * Created by Kim-Christian on 2017-08-27.
 */

public class Ansokan implements JSONPopulator {
    private String referens;
    private String webbadress;
    private String epostadress;
    //private DateTime sista_ansokningsdag;
    private String ovrigt_om_ansokan;

    public String getReferens() { return referens; }
    public String getWebbadress() { return webbadress; }
    public String getEpostadress() { return epostadress; }
    public String getOvrigt_om_ansokan() { return ovrigt_om_ansokan; }

    @Override
    public void populate(JSONObject data) {
        referens = data.optString("referens");
        webbadress = data.optString("webbadress");
        epostadress = data.optString("epostadress");
        ovrigt_om_ansokan = data.optString("ovrigt_om_ansokan");
    }
}
