package com.workfinder.workfinder.Data;

import org.json.JSONObject;

/**
 * Created by Kim-Christian on 2017-08-27.
 */

public class Matchningdata implements JSONPopulator {
    private String annonsid;
    private String annonsrubrik;
    private String annonsurl;
    private String yrkesbenamning;
    private String arbetsplatsnamn;
    private String kommunnamn;
    private int kommunkod;
    //private Datetime publiceraddatum;
    private int relevans;
    private int antalplatser;

    public String getAnnonsid() { return annonsid; }
    public String getAnnonsrubrik() { return annonsrubrik; }
    public String getAnnonsurl() { return annonsurl; }
    public String getYrkesbenamning() { return yrkesbenamning; }
    public String getArbetsplatsnamn() { return arbetsplatsnamn; }
    public String getKommunnamn() { return kommunnamn; }
    public int getKommunkod() { return kommunkod; }
    public int getRelevans() { return relevans; }
    public int getAntalplatser() { return antalplatser; }

    @Override
    public void populate(JSONObject data) {
        annonsid = data.optString("annonsid");
        annonsrubrik = data.optString("annonsrubrik");
        annonsurl = data.optString("annonsurl");
        yrkesbenamning = data.optString("yrkesbenamning");
        arbetsplatsnamn = data.optString("arbetsplatsnamn");
        kommunnamn = data.optString("kommunnamn");
        kommunkod = data.optInt("kommunkod");
        relevans = data.optInt("relevans");
        antalplatser = data.optInt("antalplatser");
    }
}
