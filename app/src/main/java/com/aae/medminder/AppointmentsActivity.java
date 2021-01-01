package com.aae.medminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aae.medminder.models.Appointment;

import java.util.ArrayList;

public class AppointmentsActivity extends AppCompatActivity implements AppointmentRecyclerViewAdapter.ItemClickListener {

    AppointmentRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Appointments");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ArrayList<AppointmentDetail> appointments = new ArrayList<>();
        appointments.add(new AppointmentDetail("Dr. Enver Altay", "Sunnetci" ,"Goztepe", "01.02.2021", "00:30"));
        appointments.add(new AppointmentDetail("Dr. Abdullah Aslan", "Urologist" ,"Findikli", "28.01.2021", "10:45"));
        appointments.add(new AppointmentDetail("Dr. Anil Gulcur", "Cardiologist", "Maltepe", "21.04.2021", "15:00"));
        appointments.add(new AppointmentDetail("Dr. Enver Altay", "Sunnetci" ,"Goztepe", "01.02.2021", "00:30"));
        appointments.add(new AppointmentDetail("Dr. Abdullah Aslan", "Urologist" ,"Findikli", "28.01.2021", "10:45"));
        appointments.add(new AppointmentDetail("Dr. Anil Gulcur", "Cardiologist", "Maltepe", "21.04.2021", "15:00"));

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.appointments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppointmentRecyclerViewAdapter(this, appointments);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, AddAppointmentActivity.class));
    }

    public void ClickAddAppointment(View view) {

        startActivity(new Intent(this, AddAppointmentActivity.class));
    }
}