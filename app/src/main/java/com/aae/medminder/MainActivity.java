package com.aae.medminder;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aae.medminder.components.calendar.CustomHorizontalCalendar;
import com.aae.medminder.components.calendar.OnHorizontalDateSelectListener;
import com.aae.medminder.components.calendar.model.DateModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnHorizontalDateSelectListener {
    // Initialize variable
    DrawerLayout drawerLayout;
    private CustomHorizontalCalendar mCalendar;


    @ Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);

        mCalendar = findViewById(R.id.calendar);
        mCalendar.setOnDateSelectListener(this);
        mCalendar.setLocale(Locale.getDefault());
        Calendar calendar = Calendar.getInstance(); // this would default to now
        calendar.add(Calendar.DAY_OF_MONTH, -10);
        mCalendar.setStartDate(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, 45);

        mCalendar.selectDate(Calendar.getInstance().getTime());
    }

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        // Open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        // Close drawer layout
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // When drawer is open
            // Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickAddMedicine(View view) {

        startActivity(new Intent(this, AddMedicineActivity.class));
    }

    public void ClickAddMeasurement(View view) {

        startActivity(new Intent(this, AddMeasurementActivity.class));
    }

    public void ClickTreatments(View view) {

        startActivity(new Intent(this, TreatmentActivity.class));
    }

    public void ClickDoctors(View view) {

        startActivity(new Intent(this, DoctorsActivity.class));
    }

    public void ClickAppointments(View view) {

        startActivity(new Intent(this, AppointmentsActivity.class));
    }

    public void ClickPharmacies(View view) {

        startActivity(new Intent(this, PermissionsActivity.class));
    }

    public void ClickSettings(View view) {

        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    public void onDateClick(DateModel dateModel) {

        Log.d("date", dateModel != null ? dateModel.month + dateModel.day + dateModel.dayOfWeek + dateModel.year : "");
    }
}