package com.workfinder.workfinder.Data;

import org.json.JSONObject;

/**
 * Created by Kim-Christian on 2017-08-27.
 */

public class Krav implements JSONPopulator {
    private Korkortslista korkortslista;
    private boolean egenbil;

    public Korkortslista getKorkortslista() { return korkortslista; }
    public boolean isEgenbil() { return egenbil; }

    @Override
    public void populate(JSONObject data) {
        korkortslista = new Korkortslista();
        korkortslista.populate(data.optJSONObject("korkortslista"));
        egenbil = data.optBoolean("egenbil");
    }
}
