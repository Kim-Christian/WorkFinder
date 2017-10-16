package com.workfinder.workfinder.Data;

import org.json.JSONObject;

/**
 * Created by Kim-Christian on 2017-08-27.
 */

public class Arbetsplats implements JSONPopulator {
    private String arbetsplatsnamn;
    private String postnummer;
    private String postadress;
    private String postort;
    private String postland;
    private String besoksadress;
    private String besoksort;
    private String telefonnummer;
    private String faxnummer;
    private String epostadress;
    private String hemsida;
    private String logotypurl;
    private Kontaktpersonlista kontaktpersonlista;

    public String getArbetsplatsnamn() { return arbetsplatsnamn; }
    public String getPostnummer() { return postnummer; }
    public String getPostadress() { return postadress; }
    public String getPostort() { return postort; }
    public String getPostland() { return postland; }
    public String getBesoksadress() { return besoksadress; }
    public String getBesoksort() { return besoksort; }
    public String getTelefonnummer() { return telefonnummer; }
    public String getFaxnummer() { return faxnummer; }
    public String getEpostadress() { return epostadress; }
    public String getHemsida() { return hemsida; }
    public String getLogotypurl()  { return logotypurl; }
    public Kontaktpersonlista getKontaktpersonlista() { return kontaktpersonlista; }

    @Override
    public void populate(JSONObject data) {
        arbetsplatsnamn = data.optString("arbetsplatsnamn");
        postnummer = data.optString("postnummer");
        postadress = data.optString("postadress");
        postort = data.optString("postort");
        postland = data.optString("postland");
        besoksadress = data.optString("besoksadress");
        besoksort = data.optString("besoksort");
        telefonnummer = data.optString("telefonnummer");
        faxnummer = data.optString("faxnummer");
        epostadress = data.optString("epostadress");
        hemsida = data.optString("hemsida");
        logotypurl = data.optString("logotypurl");
        kontaktpersonlista = new Kontaktpersonlista();
        kontaktpersonlista.populate(data.optJSONObject("kontaktpersonlista"));
    }
}
