package com.workfinder.workfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.workfinder.workfinder.Data.Annons;
import com.workfinder.workfinder.Data.Ansokan;
import com.workfinder.workfinder.Data.Arbetsplats;
import com.workfinder.workfinder.Data.Kontaktpersondata;
import com.workfinder.workfinder.Data.Krav;
import com.workfinder.workfinder.Data.Platsannons;
import com.workfinder.workfinder.Data.Villkor;
import com.workfinder.workfinder.Service.PlatsannonsService;
import com.workfinder.workfinder.Service.PlatsannonsServiceCallback;

import java.util.List;

public class PlatsannonsActivity extends AppCompatActivity implements PlatsannonsServiceCallback {
    private TextView yrkesbenamning;
    private TextView foretag;
    private TextView kommun;
    private TextView annonstext;
    private TextView varaktighet;
    private TextView arbetstid;
    private TextView arbetstidvaraktighet;
    private TextView tilltrade;
    private TextView lonetyp;
    private TextView loneform;
    private TextView referens;
    private TextView webbadress;
    private TextView epostadressAnsokan;
    private TextView ovrigtOmAnsokan;
    private TextView arbetsplatsnamn;
    private TextView postnummer;
    private TextView postadress;
    private TextView postort;
    private TextView besoksadress;
    private TextView besoksort;
    private TextView telefonnummerArbetsplats;
    private TextView faxnummer;
    private TextView epostadressArbetsplats;
    private TextView hemsida;
    private TextView namnKontaktperson;
    private TextView titelKontaktperson;
    private TextView mobilnummerKontaktperson;
    private TextView telefonnummerKontaktperson;
    private TextView korkortstyp;
    private TextView egenbil;
    private android.support.v7.app.ActionBar actionBar;
    private String userUid;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platsannons);
        Intent intent = getIntent();
        String annonsid = intent.getExtras().getString("annonsid");
        userUid = intent.getExtras().getString("userUid");

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        yrkesbenamning = (TextView) findViewById(R.id.yrkesbenamning);

        foretag = (TextView) findViewById(R.id.foretag);
        kommun = (TextView) findViewById(R.id.kommun);
        annonstext = (TextView) findViewById(R.id.annonstext);
        varaktighet = (TextView) findViewById(R.id.varaktighet);
        arbetstid = (TextView) findViewById(R.id.arbetstid);
        arbetstidvaraktighet = (TextView) findViewById(R.id.arbetstidvaraktighet);
        tilltrade = (TextView) findViewById(R.id.tilltrade);
        lonetyp = (TextView) findViewById(R.id.lonetyp);
        loneform = (TextView) findViewById(R.id.loneform);
        referens = (TextView) findViewById(R.id.referens);
        webbadress = (TextView) findViewById(R.id.webbadress);
        epostadressAnsokan = (TextView) findViewById(R.id.epostadress_ansokan);
        ovrigtOmAnsokan = (TextView) findViewById(R.id.ovrigt_om_ansokan);
        arbetsplatsnamn = (TextView) findViewById(R.id.arbetsplatsnamn);
        postnummer = (TextView) findViewById(R.id.postnummer);
        postadress = (TextView) findViewById(R.id.postadress);
        postort = (TextView) findViewById(R.id.postort);
        besoksadress = (TextView) findViewById(R.id.besoksadress);
        besoksort = (TextView) findViewById(R.id.besoksort);
        telefonnummerArbetsplats = (TextView) findViewById(R.id.telefonnummer_arbetsplats);
        faxnummer = (TextView) findViewById(R.id.faxnummer);
        epostadressArbetsplats = (TextView) findViewById(R.id.epostadress_arbetsplats);
        hemsida = (TextView) findViewById(R.id.hemsida);
        namnKontaktperson = (TextView) findViewById(R.id.namn_kontaktperson);
        titelKontaktperson = (TextView) findViewById(R.id.titel_kontaktperson);
        mobilnummerKontaktperson = (TextView) findViewById(R.id.mobilnummer_kontaktperson);
        telefonnummerKontaktperson = (TextView) findViewById(R.id.telefonnummer_kontaktperson);
        korkortstyp = (TextView) findViewById(R.id.korkortstyp);
        egenbil = (TextView) findViewById(R.id.egenbil);

        PlatsannonsService platsannonsService = new PlatsannonsService(this);
        platsannonsService.refreshPlatsannons(annonsid);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Hämtar...");
        progressDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = getIntent();
            intent.putExtra("userUid", userUid);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void serviceSuccess(Platsannons platsannons) {
        Annons annons = platsannons.getAnnons();
        Villkor villkor = platsannons.getVillkor();
        Ansokan ansokan = platsannons.getAnsokan();
        Arbetsplats arbetsplats = platsannons.getArbetsplats();
        Krav krav = platsannons.getKrav();

        yrkesbenamning.setText(annons.getYrkesbenamning());
        foretag.setText(arbetsplats.getArbetsplatsnamn());
        kommun.setText(annons.getKommunnamn());
        annonstext.setText(annons.getAnnonstext());
        varaktighet.setText(villkor.getVaraktighet());
        arbetstid.setText(villkor.getArbetstid());
        arbetstidvaraktighet.setText(villkor.getArbetstidvaraktighet());
        tilltrade.setText(villkor.getTilltrade());
        lonetyp.setText(villkor.getLonetyp());
        loneform.setText(villkor.getLoneform());
        referens.setText(ansokan.getReferens());
        webbadress.setText(ansokan.getWebbadress());
        epostadressAnsokan.setText(ansokan.getEpostadress());
        ovrigtOmAnsokan.setText(ansokan.getOvrigt_om_ansokan());
        arbetsplatsnamn.setText(arbetsplats.getArbetsplatsnamn());
        postnummer.setText(arbetsplats.getPostnummer());
        postadress.setText(arbetsplats.getPostadress());
        postort.setText(arbetsplats.getPostort());
        besoksadress.setText(arbetsplats.getBesoksadress());
        besoksort.setText(arbetsplats.getBesoksort());
        telefonnummerArbetsplats.setText(arbetsplats.getTelefonnummer());
        faxnummer.setText(arbetsplats.getFaxnummer());
        epostadressArbetsplats.setText(arbetsplats.getEpostadress());
        hemsida.setText(arbetsplats.getHemsida());
        List<Kontaktpersondata> kontaktpersondata = arbetsplats.getKontaktpersonlista().getKontaktpersondata();
        if (kontaktpersondata != null) {
            Kontaktpersondata data = kontaktpersondata.get(0);
            namnKontaktperson.setText(data.getNamn());
            titelKontaktperson.setText(data.getTitel());
            mobilnummerKontaktperson.setText(data.getMobilnummer());
            telefonnummerKontaktperson.setText(data.getTelefonnummer());
        }
        if (krav.isEgenbil()) {
            egenbil.setText("Egen bil");
        }

        actionBar.setTitle(annons.getAnnonsrubrik());
        progressDialog.hide();
    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
