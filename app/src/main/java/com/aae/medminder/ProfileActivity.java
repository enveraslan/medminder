package com.aae.medminder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aae.medminder.database.DatabaseHelper;
import com.aae.medminder.models.Patient;
import com.aae.medminder.models.Profile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    private DatabaseHelper helper = new DatabaseHelper(this);
    private SQLiteDatabase db;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextBirthDate;
    private EditText editTextAddress;
    private EditText editTextZipCode;
    private Button buttonSaveProfile;
    private Cursor cursor;
    private Profile profile;

    private String firstName;
    private String lastName;
    private String birthDate;
    private String address;
    private String zipCode;

    private SimpleDateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();

    }

    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        dateFormat = new SimpleDateFormat("dd/M/yyyy");

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextBirthDate = findViewById(R.id.editTextBirthDate);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextZipCode = findViewById(R.id.editTextZipCode);
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile);

        editTextBirthDate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editTextBirthDate.setText(current);
                    editTextBirthDate.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });



        loadProfile();

        buttonSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private void fillAllTextField() {
        editTextFirstName.setText(firstName);
        editTextLastName.setText(lastName);
        editTextBirthDate.setText(birthDate);
        editTextAddress.setText(address);
        editTextZipCode.setText(zipCode);
    }

    private void saveProfile()   {
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        birthDate = editTextBirthDate.getText().toString();
        address = editTextAddress.getText().toString();
        zipCode = editTextZipCode.getText().toString();
        profile = new Profile(firstName, lastName, birthDate, address, zipCode);

        try {
            db = helper.getWritableDatabase();
            DatabaseHelper.updateProfile(db, profile);
            Toast.makeText(getApplicationContext(), "Profile is updated.", Toast.LENGTH_SHORT).show();
        } catch (SQLiteException ex) {

        }

    }




    public void loadProfile() {
        try {
            db = helper.getWritableDatabase();
            cursor = DatabaseHelper.selectProfile(db);
            if (cursor.moveToFirst()) {
                firstName = cursor.getString(0);
                lastName = cursor.getString(1);
                birthDate = cursor.getString(2);
                address = cursor.getString(3);
                zipCode = cursor.getString(4);
                fillAllTextField();
            }
        } catch (SQLiteException ex) {

        }
    }
/*
    public void saveProfile() {
        try {
            db = helper.getWritableDatabase();
            DatabaseHelper.updatePatient(db, patientID,
                    editTextFirstName.getText().toString(),
                    editTextLastName.getText().toString(),
                    editTextBirthDate.getText().toString(),
                    editTextAddress.getText().toString(),
                    editTextZipCode.getText().toString());
            Toast.makeText(getApplicationContext(), "Profile is updated.", Toast.LENGTH_SHORT).show();

//            if (cursor != null && cursor.getCount() > 0) {
//                Toast.makeText(getApplicationContext(), "Kayıt Var", Toast.LENGTH_SHORT).show();
//            } else {
//
//            }

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }*/
}