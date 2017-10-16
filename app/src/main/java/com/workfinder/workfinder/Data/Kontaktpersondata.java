package com.workfinder.workfinder.Data;

import org.json.JSONObject;

/**
 * Created by Kim-Christian on 2017-08-27.
 */

public class Kontaktpersondata implements JSONPopulator {
    private String namn;
    private String titel;
    private String mobilnummer;
    private String telefonnummer;

    public String getNamn() { return namn; }
    public String getTitel() { return titel; }
    public String getMobilnummer() { return mobilnummer; }
    public String getTelefonnummer() { return telefonnummer; }

    @Override
    public void populate(JSONObject data) {
        namn = data.optString("namn");
        titel = data.optString("titel");
        mobilnummer = data.optString("mobilnummer");
        telefonnummer = data.optString("telefonnummer");
    }
}
