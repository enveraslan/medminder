package com.aae.medminder;

import android.content.Context;
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
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity implements AppointmentRecyclerViewAdapter.ItemClickListener {
    private RecyclerView recyclerViewAppointment;
    private List<Appointment> appointments;
    private AppointmentRecyclerViewAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        context = this;

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

        init();
    }

    private void init() {
        // set up the RecyclerView
        appointments = MedminderApp.getDaoSession().getAppointmentDao().loadAll();
        recyclerViewAppointment = findViewById(R.id.appointments_recycler_view);
        recyclerViewAppointment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppointmentRecyclerViewAdapter(this, appointments);
        adapter.setClickListener(this);
        recyclerViewAppointment.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(context, AddAppointmentActivity.class);
        intent.putExtra("appointmentID", adapter.getItem(position).getAppointmentID());
        finish();
        startActivity(intent);
    }

    public void ClickAddAppointment(View view) {
        finish();
        startActivity(new Intent(this, AddAppointmentActivity.class));
    }
}