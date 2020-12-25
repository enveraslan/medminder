package com.aae.medminder;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.aae.medminder.models.Doctor;
import com.aae.medminder.models.DoctorDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class AddDoctorActivity extends AppCompatActivity {
    private EditText editTextDoctorFirstName;
    private EditText editTextDoctorLastName;
    private EditText editTextDoctorPhoneNumber;
    private EditText editTextDoctorEmailAddress;
    private EditText editTextDoctorLocation;
    private Button buttonSaveDoctor;
    private Button buttonDeleteDoctor;
    private Long doctorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        doctorID = getIntent().getLongExtra("doctorID", 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(doctorID > 0) {
            toolbar.setTitle("Edit Doctor");
        } else {
            toolbar.setTitle("Add Doctor");
        }

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        init();
    }

    private void init() {
        editTextDoctorFirstName = findViewById(R.id.editTextDoctorFirstName);
        editTextDoctorLastName = findViewById(R.id.editTextDoctorLastName);
        editTextDoctorPhoneNumber = findViewById(R.id.editTextDoctorPhoneNumber);
        editTextDoctorEmailAddress = findViewById(R.id.editTextDoctorEmail);
        editTextDoctorLocation = findViewById(R.id.editTextDoctorLocation);
        buttonSaveDoctor = findViewById(R.id.buttonSaveDoctor);
        buttonDeleteDoctor = findViewById(R.id.buttonDeleteDoctor);

        if(doctorID > 0 ){
            buttonDeleteDoctor.setVisibility(View.VISIBLE);
        } else {
            buttonDeleteDoctor.setVisibility(View.INVISIBLE);
        }


        if(doctorID > 0) {
            List<Doctor> doctorQuery = ((MedminderApp)getApplication()).getDaoSession().getDoctorDao().queryBuilder()
                    .where(DoctorDao.Properties.DoctorID.eq(doctorID))
                    .list();
            Doctor doctor = doctorQuery.get(0);
            editTextDoctorFirstName.setText(doctor.getFirstName());
            editTextDoctorLastName.setText(doctor.getLastName());
            editTextDoctorPhoneNumber.setText(doctor.getPhoneNumber());
            editTextDoctorEmailAddress.setText(doctor.getEmail());
            editTextDoctorLocation.setText(doctor.getLocation());
        }

        buttonSaveDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDoctor();
            }
        });

        buttonDeleteDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDoctor();
            }
        });
    }

    private void saveDoctor() {
        Doctor doctor = new Doctor();
        doctor.setFirstName(editTextDoctorFirstName.getText().toString());
        doctor.setLastName(editTextDoctorLastName.getText().toString());
        doctor.setPhoneNumber(editTextDoctorPhoneNumber.getText().toString());
        doctor.setEmail(editTextDoctorEmailAddress.getText().toString());
        doctor.setLocation(editTextDoctorLocation.getText().toString());

        if(doctorID > 0) {
            doctor.setDoctorID(doctorID);
            ((MedminderApp)getApplication()).getDaoSession().getDoctorDao().update(doctor);
        } else {
            doctor.setDoctorID(null);
            ((MedminderApp)getApplication()).getDaoSession().getDoctorDao().insert(doctor);
        }

        startActivity(new Intent(this, DoctorsActivity.class));
    }

    private void deleteDoctor() {
        ((MedminderApp)getApplication()).getDaoSession().getDoctorDao().deleteByKey(doctorID);
        startActivity(new Intent(this, DoctorsActivity.class));
    }
}