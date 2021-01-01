package com.aae.medminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aae.medminder.components.calendar.CustomHorizontalCalendar;
import com.aae.medminder.components.calendar.OnHorizontalDateSelectListener;
import com.aae.medminder.components.calendar.model.DateModel;
import com.aae.medminder.models.MeasurementTreatment;
import com.aae.medminder.models.MeasurementTreatmentDao;
import com.aae.medminder.models.MeasurementType;
import com.aae.medminder.models.MeasurementTypeDao;
import com.aae.medminder.models.Medicine;
import com.aae.medminder.models.MedicineDao;
import com.aae.medminder.models.MedicineTreatment;
import com.aae.medminder.models.MedicineTreatmentDao;
import com.aae.medminder.models.Treatment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements OnHorizontalDateSelectListener, TreatmentRecyclerViewAdapter.ItemClickListener {
    // Initialize variable
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    private ArrayList<TreatmentDetail> treatmentList = new ArrayList<>();
    DrawerLayout drawerLayout;
    private CustomHorizontalCalendar mCalendar;
    RecyclerView medicineRecyclerView;

    TreatmentRecyclerViewAdapter adapter;
    Context context = this;

    @ Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        medicineRecyclerView = findViewById(R.id.medicine_recycler_view);

        mCalendar = findViewById(R.id.calendar);
        mCalendar.setOnDateSelectListener(this);
        mCalendar.setLocale(Locale.getDefault());
        Calendar calendar = Calendar.getInstance(); // this would default to now
        calendar.add(Calendar.DAY_OF_MONTH, -10);
        mCalendar.setStartDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 45);
        mCalendar.selectDate(Calendar.getInstance().getTime());
        getTreatments(sdf.format(mCalendar.getCurrentDate()));
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
        getTreatments(sdf.format(dateModel.date));
        Log.d("date", dateModel != null ? sdf.format(dateModel.date) : "");
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void getTreatments(String date) {
        treatmentList = new ArrayList<TreatmentDetail>();

        List<Treatment> treatments =((MedminderApp)getApplication()).getDaoSession().getTreatmentDao().loadAll();

        try {
            for (int i = 0; i < treatments.size(); i++) {
                Treatment treatment = treatments.get(i);
                if(treatment.getTreatmentTypeID().equals("MED")) {

                    List<MedicineTreatment> medicineTreatments = MedminderApp
                            .getDaoSession().getMedicineTreatmentDao().queryBuilder()
                            .where(MedicineTreatmentDao.Properties.TreatmentID.eq(treatment.getTreatmentID()))
                            .where(MedicineTreatmentDao.Properties.Date.eq(date))
                            .where(MedicineTreatmentDao.Properties.CosumeType.eq("N"))
                            .orderAsc(MedicineTreatmentDao.Properties.Time)
                            .list();

                    for(int j = 0; j < medicineTreatments.size(); j++) {
                        MedicineTreatment medicineTreatment = medicineTreatments.get(j);
                        List<Medicine> medicines = ((MedminderApp)getApplication())
                                .getDaoSession().getMedicineDao().queryBuilder()
                                .where(MedicineDao.Properties.MedicineID.eq(medicineTreatment.getMedicineID()))
                                .list();
                        treatmentList.add(new TreatmentDetail(medicineTreatment.getMedicineTreatmentID(),
                                medicines.get(0).getName(),
                                medicineTreatment.getDosage().toString(),  medicineTreatment.getTime(), "MED"));
                    }


                }
                else if (treatment.getTreatmentTypeID().equals("MEA")) {
                    List<MeasurementTreatment> measurementTreatments = MedminderApp.getDaoSession()
                            .getMeasurementTreatmentDao().queryBuilder()
                            .where(MeasurementTreatmentDao.Properties.TreatmentID.eq(treatment.getTreatmentID()))
                            .where(MeasurementTreatmentDao.Properties.Date.eq(date))
                            .where(MeasurementTreatmentDao.Properties.Measured.eq("N"))
                            .orderAsc(MeasurementTreatmentDao.Properties.Time)
                            .list();

                    for(int j = 0; j < measurementTreatments.size(); j++) {
                        MeasurementTreatment measurementTreatment = measurementTreatments.get(j);
                        List<MeasurementType> measurementTypes = ((MedminderApp)getApplication())
                                .getDaoSession().getMeasurementTypeDao().queryBuilder()
                                .where(MeasurementTypeDao.Properties.MeasurementTypeID.eq(measurementTreatment.getMeasurementTypeID()))
                                .list();
                        treatmentList.add(new TreatmentDetail(measurementTreatment.getMeasurementTreatmentID(),
                                measurementTypes.get(0).getTitle(),
                                "0",  measurementTreatment.getTime(), "MEA"));
                    }
                }


            }
        } catch (IndexOutOfBoundsException ex) {

        }

        medicineRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TreatmentRecyclerViewAdapter(context, treatmentList);
        adapter.setClickListener(this);
        medicineRecyclerView.setAdapter(adapter);
    }
}