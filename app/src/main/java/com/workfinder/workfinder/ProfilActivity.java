package com.workfinder.workfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Profil";
    private EditText editText;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private ArrayAdapter<CharSequence> adapter1;
    private ArrayAdapter<CharSequence> adapter2;
    private ArrayAdapter<CharSequence> adapter3;
    private Button signOutButton;
    private DatabaseReference fritext;
    private DatabaseReference lan;
    private DatabaseReference lanKod;
    private DatabaseReference kommun;
    private DatabaseReference kommunKod;
    private DatabaseReference anstallningstyp;
    private DatabaseReference anstallningstypKod;
    private String kommunDatabase;
    private int init = 0;
    private boolean spinner2Set = false;
    private InputMethodManager inputManager;
    private String userUid;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        userUid = getIntent().getExtras().getString("userUid");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(ProfilActivity.this, InloggningActivity.class));
                }
            }
        };

        final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference(userUid);
        fritext = userDatabase.child("fritext");
        lan = userDatabase.child("lan");
        lanKod = userDatabase.child("lanKod");
        kommun = userDatabase.child("kommun");
        kommunKod = userDatabase.child("kommunKod");
        anstallningstyp = userDatabase.child("anstallningstyp");
        anstallningstypKod = userDatabase.child("anstallningstypKod");

        editText = (EditText) findViewById(R.id.edit_text);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        adapter1 = ArrayAdapter.createFromResource(this,
                R.array.lan_array, android.R.layout.simple_spinner_item);
        adapter3 = ArrayAdapter.createFromResource(this,
                R.array.anstallningstyp_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner3.setAdapter(adapter3);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
        signOutButton = (Button) findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value != null) {
                    String reference = dataSnapshot.getRef().toString();
                    if (reference.equals(fritext.toString())) {
                        editText.setHint(value);
                    } else if (reference.equals(lan.toString())) {
                        spinner1.setSelection(adapter1.getPosition(value));
                        lanKod.setValue(getKod(spinner1));
                        setSpinner2(value);
                    } else if (reference.equals(kommun.toString())) {
                        if (spinner2Set) {
                            spinner2.setSelection(adapter2.getPosition(value));
                            kommunKod.setValue(getKod(spinner2));
                        } else {
                            kommunDatabase = value;
                        }
                    } else if (reference.equals(anstallningstyp.toString())) {
                        spinner3.setSelection(adapter3.getPosition(value));
                        anstallningstypKod.setValue(getKod(spinner3));
                    }
                } else {
                    Log.d(TAG, "Value doesn't exist!!!");
                }
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };
        fritext.addValueEventListener(valueEventListener);
        lan.addValueEventListener(valueEventListener);
        kommun.addValueEventListener(valueEventListener);
        anstallningstyp.addValueEventListener(valueEventListener);

        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String input = editText.getText().toString();
                    editText.setText(null);
                    editText.clearFocus();
                    fritext.setValue(input);
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });
        inputManager =
                (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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

    private boolean hideKeyboard() {
        try {
            inputManager.hideSoftInputFromWindow(
                    this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setSpinner2(String value) {
        int kommun_array = 0;
        switch (value) {
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
        adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),
                kommun_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        if (!spinner2Set) {
            spinner2.setSelection(adapter2.getPosition(kommunDatabase));
            spinner2Set = true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (init >= 3) {
            String selectedItem = parent.getSelectedItem().toString();
            String parentObject = parent.toString();
            int length = parentObject.length();
            String parentName = parentObject.substring(length - 9, length - 1);
            switch (parentName) {
                case "spinner1":
                    lan.setValue(selectedItem);
                    setSpinner2(selectedItem);
                    break;
                case "spinner2":
                    kommun.setValue(selectedItem);
                    break;
                case "spinner3":
                    anstallningstyp.setValue(selectedItem);
                    break;
            }
        }
        init++;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void dialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ProfilActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(ProfilActivity.this);
        }
        builder.setTitle("Logga ut")
                .setMessage("Vill du logga ut?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
