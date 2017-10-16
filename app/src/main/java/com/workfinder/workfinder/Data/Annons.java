package com.workfinder.workfinder.Data;

import org.json.JSONObject;

/**
 * Created by Kim-Christian on 2017-08-27.
 */

public class Annons implements JSONPopulator {
    private String platsannonsUrl;
    private String annonsid;
    private String annonsrubrik;
    private String annonstext;
    private String yrkesbenamning;
    private int yrkesid;
    //private DateTime publiceraddatum;
    private int antal_platser;
    private String kommunnamn;
    private String kommunkod;

    public String getPlatsannonsUrl() { return platsannonsUrl; }
    public String getAnnonsid() { return annonsid; }
    public String getAnnonsrubrik() { return annonsrubrik; }
    public String getAnnonstext() { return annonstext; }
    public String getYrkesbenamning() { return yrkesbenamning; }
    public int getYrkesid() { return yrkesid; }
    public int getAntal_platser() { return antal_platser; }
    public String getKommunnamn() { return kommunnamn; }
    public String getKommunkod() { return kommunkod; }

    @Override
    public void populate(JSONObject data) {
        platsannonsUrl = data.optString("platsannonsUrl");
        annonsid = data.optString("annonsid");
        annonsrubrik = data.optString("annonsrubrik");
        annonstext = data.optString("annonstext");
        yrkesbenamning = data.optString("yrkesbenamning");
        yrkesid = data.optInt("yrkesid");
        antal_platser = data.optInt("antal_platser");
        kommunnamn = data.optString("kommunnamn");
        kommunkod = data.optString("kommunkod");
    }
}
