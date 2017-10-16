package com.workfinder.workfinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workfinder.workfinder.Data.MatchningParams;
import com.workfinder.workfinder.Data.Matchningdata;
import com.workfinder.workfinder.Data.Matchningslista;
import com.workfinder.workfinder.Service.MatchningService;
import com.workfinder.workfinder.Service.MatchningServiceCallback;

public class MatchningActivity extends AppCompatActivity implements MatchningServiceCallback, AdapterView.OnItemSelectedListener {

    private static final String TAG = "Matchning";
    private MatchningService matchningService;
    private MatchningParams matchningParams;
    private LayoutInflater layoutInflater;
    private LinearLayout list;
    private BottomNavigationView bottomNavigationView;
    private MenuItem itemSelected;
    private Card[] cards;
    private SearchView searchView;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private ArrayAdapter<CharSequence> adapter1;
    private ArrayAdapter<CharSequence> adapter2;
    private ArrayAdapter<CharSequence> adapter3;
    private Button searchButton;
    private String userUid;
    private String fritextDatabase;
    private String lanDatabase;
    private String lanKodDatabase;
    private String kommunDatabase;
    private String kommunKodDatabase;
    private String anstallningstypDatabase;
    private String anstallningstypKodDatabase;
    private InputMethodManager inputManager;
    private ProgressDialog progressDialog;
    private AppBarLayout appBarLayout;
    private DatabaseReference fritext;
    private DatabaseReference lan;
    private DatabaseReference lanKod;
    private DatabaseReference kommun;
    private DatabaseReference kommunKod;
    private DatabaseReference anstallningstyp;
    private DatabaseReference anstallningstypKod;
    private boolean gotUserUid = false;
    private Matchningdata[] matchningdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchning);
        if (!gotUserUid) {
            userUid = getIntent().getExtras().getString("userUid");
            gotUserUid = true;
        }
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = (LinearLayout) findViewById(R.id.list);

        final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference(userUid);
        fritext = userDatabase.child("fritext");
        lan = userDatabase.child("lan");
        lanKod = userDatabase.child("lanKod");
        kommun = userDatabase.child("kommun");
        kommunKod = userDatabase.child("kommunKod");
        anstallningstyp = userDatabase.child("anstallningstyp");
        anstallningstypKod = userDatabase.child("anstallningstypKod");

        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                String reference = dataSnapshot.getRef().toString();
                if (value == null) {
                    if (reference.equals(fritext.toString())) {
                        fritext.setValue("");
                    } else if (reference.equals(lan.toString())) {
                        lan.setValue("Alla");
                    } else if (reference.equals(lanKod.toString())) {
                        lanKod.setValue("0");
                    } else if (reference.equals(kommun.toString())) {
                        kommun.setValue("Alla");
                    } else if (reference.equals(kommunKod.toString())) {
                        kommunKod.setValue("0");
                    } else if (reference.equals(anstallningstyp.toString())) {
                        anstallningstyp.setValue("Alla");
                    } else if (reference.equals(anstallningstypKod.toString())) {
                        anstallningstypKod.setValue("0");
                    }
                } else {
                    if (reference.equals(fritext.toString())) {
                        fritextDatabase = value;
                    } else if (reference.equals(lan.toString())) {
                        lanDatabase = value;
                    } else if (reference.equals(lanKod.toString())) {
                        lanKodDatabase = value;
                    } else if (reference.equals(kommun.toString())) {
                        kommunDatabase = value;
                    } else if (reference.equals(kommunKod.toString())) {
                        kommunKodDatabase = value;
                    } else if (reference.equals(anstallningstyp.toString())) {
                        anstallningstypDatabase = value;
                    } else if (reference.equals(anstallningstypKod.toString())) {
                        anstallningstypKodDatabase = value;
                    }
                    Log.d(TAG, "Value is: " + value);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };
        fritext.addValueEventListener(valueEventListener);
        lan.addValueEventListener(valueEventListener);
        lanKod.addValueEventListener(valueEventListener);
        kommun.addValueEventListener(valueEventListener);
        kommunKod.addValueEventListener(valueEventListener);
        anstallningstyp.addValueEventListener(valueEventListener);
        anstallningstypKod.addValueEventListener(valueEventListener);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        itemSelected = bottomNavigationView.getMenu().getItem(0);//.findItem(R.id.preset);
        itemSelected.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.preset:
                        coordinatorLayout.removeView(appBarLayout);
                        loadPreset();
                        break;
                    case R.id.search:
                        if (item != itemSelected) {
                            list.removeAllViews();
                            coordinatorLayout.addView(appBarLayout);
                        }
                        appBarLayout.setExpanded(true);
                        break;
                    case R.id.profile:
                        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                        intent.putExtra("userUid", userUid);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                if (item.getItemId() != R.id.profile) {
                    itemSelected.setChecked(false);
                    itemSelected = item;
                    itemSelected.setChecked(true);
                }
                return false;
            }
        });

        searchView = (SearchView) findViewById(R.id.search_view);
        searchButton = (Button) findViewById(R.id.search_btn);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        adapter1 = ArrayAdapter.createFromResource(this,
                R.array.lan_array, android.R.layout.simple_spinner_item);
        adapter2 = ArrayAdapter.createFromResource(this,
                R.array.alla_array, android.R.layout.simple_spinner_item);
        adapter3 = ArrayAdapter.createFromResource(this,
                R.array.anstallningstyp_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner1.setOnItemSelectedListener(this);

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getString(R.string.search_view_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        matchningParams = new MatchningParams();
        matchningService = new MatchningService(this);

        inputManager =
                (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Hämtar...");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                userUid = data.getStringExtra("userUid");
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private boolean hideKeyboard() {
        try {
            inputManager.hideSoftInputFromWindow(
                    searchView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getKod(Spinner spinner) {
        String spinnerName = spinner.toString();
        int length = spinnerName.length();
        spinnerName = spinnerName.substring(length - 9, length - 1);
        int kodArray = 0;

        switch (spinnerName) {
            case "spinner1":
                kodArray = R.array.lan_kod_array;
                break;
            case "spinner2":
                switch (spinner1.getSelectedItemPosition()) {
                    case 0:
                        kodArray = R.array.alla_kod_array;
                        break;
                    case 1:
                        kodArray = R.array.stockholm_kod_array;
                        break;
                    case 2:
                        kodArray = R.array.uppsala_kod_array;
                        break;
                    case 3:
                        kodArray = R.array.sodermanland_kod_array;
                        break;
                    case 4:
                        kodArray = R.array.ostergotland_kod_array;
                        break;
                    default:
                        break;
                }
                break;
            case "spinner3":
                kodArray = R.array.anstallningstyp_kod_array;
                break;
            default:
                break;
        }
        return getResources().getStringArray(kodArray)[spinner.getSelectedItemPosition()];
    }

    private void search() {
        hideKeyboard();
        progressDialog.show();
        appBarLayout.setExpanded(false);
        matchningParams.setFritext(searchView.getQuery().toString());
        matchningParams.setLan(spinner1.getSelectedItem().toString());
        matchningParams.setLanKod(getKod(spinner1));
        matchningParams.setKommun(spinner2.getSelectedItem().toString());
        matchningParams.setKommunKod(getKod(spinner2));
        matchningParams.setAnstallningstyp(spinner3.getSelectedItem().toString());
        matchningParams.setAnstallningstypKod(getKod(spinner3));
        matchningService.refreshMatchning(matchningParams);
    }

    private void addView(View view, int insertPointId, int insertPosition) {
        ViewGroup insertPoint = (ViewGroup) findViewById(insertPointId);
        insertPoint.addView(view, insertPosition, view.getLayoutParams());
    }

    private void addCards(Matchningdata[] matchningdata) {
        cards = new Card[matchningdata.length];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(layoutInflater);
            cards[i].setThumbnailText(matchningdata[i].getArbetsplatsnamn());
            cards[i].setTitle(matchningdata[i].getAnnonsrubrik());
            cards[i].setSubtitle(matchningdata[i].getYrkesbenamning());
            addView(cards[i].getCardView(), list.getId(), i);
            final String annonsid = matchningdata[i].getAnnonsid();
            cards[i].getCardView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PlatsannonsActivity.class);
                    intent.putExtra("annonsid", annonsid);
                    intent.putExtra("userUid", userUid);
                    startActivityForResult(intent, 1);
                }
            });
        }
        if (cards.length == 0) {
            Toast.makeText(this, "Inga annonser hittades", Toast.LENGTH_LONG).show();
        }
    }

    private void loadPreset() {
        progressDialog.show();
        matchningParams.setFritext(fritextDatabase);
        matchningParams.setLan(lanDatabase);
        matchningParams.setLanKod(lanKodDatabase);
        matchningParams.setKommun(kommunDatabase);
        matchningParams.setKommunKod(kommunKodDatabase);
        matchningParams.setAnstallningstyp(anstallningstypDatabase);
        matchningParams.setAnstallningstypKod(anstallningstypKodDatabase);
        matchningService.refreshMatchning(matchningParams);
    }

    @Override
    public void serviceSuccess(Matchningslista matchningslista) {
        matchningdata = matchningslista.getMatchningdata();
        list.removeAllViews();
        addCards(matchningdata);
        progressDialog.hide();
    }

    @Override
    public void serviceFailure(Exception exception) {
        progressDialog.hide();
        Toast.makeText(this, "Inga annonser hittades", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getSelectedItem().toString();
        int kommun_array = 0;
        switch (selectedItem) {
            case "Alla":
                kommun_array = R.array.alla_array;
                break;
            case "Stockholm":
                kommun_array = R.array.stockholm_array;
                break;
            case "Uppsala":
                kommun_array = R.array.uppsala_array;
                break;
            case "Södermanland":
                kommun_array = R.array.sodermanland_array;
                break;
            case "Östergötland":
                kommun_array = R.array.ostergotland_array;
                break;
            default:
                break;
        }
        adapter2 = ArrayAdapter.createFromResource(this,
                kommun_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}