package com.aae.medminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aae.medminder.models.Profile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextBirthDate;
    private EditText editTextAddress;
    private EditText editTextZipCode;
    private Button buttonSaveProfile;
    private Profile profile;


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

        profile = null;

        try {
            profile = ((MedminderApp) getApplication()).getDaoSession().getProfileDao().loadAll().get(0);
            editTextFirstName.setText(profile.getFirstName());
            editTextLastName.setText(profile.getLastName());
            editTextBirthDate.setText(profile.getBirthDate());
            editTextAddress.setText(profile.getBirthDate());
            editTextZipCode.setText(profile.getZipCode());
        } catch (IndexOutOfBoundsException ex) {
            // TODO:
        }



        buttonSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profile == null)
                    saveProfile();
                else
                    updateProfile();
            }
        });
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
    }


    private void saveProfile() {
        profile = new Profile();
        profile.setFirstName(editTextFirstName.getText().toString());
        profile.setLastName(editTextLastName.getText().toString());
        profile.setBirthDate(editTextBirthDate.getText().toString());
        profile.setAddress(editTextAddress.getText().toString());
        profile.setZipCode(editTextZipCode.getText().toString());

        ((MedminderApp)getApplication()).getDaoSession().getProfileDao().insert(profile);

    }

    private void updateProfile() {
        profile.setFirstName(editTextFirstName.getText().toString());
        profile.setLastName(editTextLastName.getText().toString());
        profile.setBirthDate(editTextBirthDate.getText().toString());
        profile.setAddress(editTextAddress.getText().toString());
        profile.setZipCode(editTextZipCode.getText().toString());

        ((MedminderApp)getApplication()).getDaoSession().getProfileDao().update(profile);

    }



}