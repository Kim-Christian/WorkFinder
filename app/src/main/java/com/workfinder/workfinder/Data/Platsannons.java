package com.workfinder.workfinder.Data;

import org.json.JSONObject;

/**
 * Created by Kim-Christian on 2017-08-27.
 */

public class Platsannons implements JSONPopulator {
    private Annons annons;
    private Villkor villkor;
    private Ansokan ansokan;
    private Arbetsplats arbetsplats;
    private Krav krav;

    public Annons getAnnons() { return annons; }
    public Villkor getVillkor() { return villkor; }
    public Ansokan getAnsokan() { return ansokan; }
    public Arbetsplats getArbetsplats() { return arbetsplats; }
    public Krav getKrav() { return krav; }

    @Override
    public void populate(JSONObject data) {
        annons = new Annons();
        annons.populate(data.optJSONObject("annons"));
        villkor = new Villkor();
        villkor.populate(data.optJSONObject("villkor"));
        ansokan = new Ansokan();
        ansokan.populate(data.optJSONObject("ansokan"));
        arbetsplats = new Arbetsplats();
        arbetsplats.populate(data.optJSONObject("arbetsplats"));
        krav = new Krav();
        krav.populate(data.optJSONObject("krav"));
    }
}
