package com.aae.medminder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aae.medminder.database.DatabaseHelper;
import com.aae.medminder.database.DbOpenHelper;
import com.aae.medminder.models.DaoMaster;
import com.aae.medminder.models.DaoSession;
import com.aae.medminder.models.Doctor;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDoctors;

    private ArrayList<Doctor> doctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Doctors");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        init();

    }

    private void init() {
        recyclerViewDoctors = findViewById(R.id.recyclerViewDoctors);
        doctors = new ArrayList<>();
        doctors = new ArrayList<Doctor>(((MedminderApp)getApplication()).getDaoSession().getDoctorDao().loadAll());
        DoctorRecyclerViewAdapter dcRcvAdapter = new DoctorRecyclerViewAdapter(this, doctors);
        recyclerViewDoctors.setAdapter(dcRcvAdapter);
        recyclerViewDoctors.setLayoutManager(new LinearLayoutManager(this));
    }

    public void ClickAddDoctor(View view) {
        startActivity(new Intent(this, AddDoctorActivity.class));
    }
}