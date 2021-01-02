package com.aae.medminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aae.medminder.models.MeasurementTreatment;
import com.aae.medminder.models.MeasurementTreatmentDao;
import com.aae.medminder.models.MeasurementType;
import com.aae.medminder.models.MeasurementTypeDao;
import com.aae.medminder.models.Medicine;
import com.aae.medminder.models.MedicineDao;
import com.aae.medminder.models.MedicineTreatment;
import com.aae.medminder.models.MedicineTreatmentDao;
import com.aae.medminder.models.Treatment;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TreatmentActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTreatments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        recyclerViewTreatments = findViewById(R.id.recyclerViewTreatments);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Treatments");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTreatments();
    }

    public void init(){
        recyclerViewTreatments = findViewById(R.id.recyclerViewTreatments);
        ArrayList<Medicine> medicines = new ArrayList<Medicine>(MedminderApp.getDaoSession().getMedicineDao().loadAll());
        ArrayList<MeasurementTreatment> measurements = new ArrayList<MeasurementTreatment>(MedminderApp.getDaoSession().getMeasurementTreatmentDao().loadAll());
    }

    public void getTreatments() {
        ArrayList<TreatmentDetail> treatmentList = new ArrayList<TreatmentDetail>();

        List<Treatment> treatments =((MedminderApp)getApplication()).getDaoSession().getTreatmentDao().loadAll();

        try {
            for (int i = 0; i < treatments.size(); i++) {
                Treatment treatment = treatments.get(i);
                if(treatment.getTreatmentTypeID().equals("MED")) {
                    MedicineTreatment medicineTreatment = MedminderApp
                            .getDaoSession().getMedicineTreatmentDao().queryBuilder()
                            .where(MedicineTreatmentDao.Properties.TreatmentID.eq(treatment.getTreatmentID()))
                            .where(MedicineTreatmentDao.Properties.CosumeType.eq("N"))
                            .orderAsc(MedicineTreatmentDao.Properties.Time)
                            .list().get(0);

                        Medicine medicines =  MedminderApp
                                .getDaoSession().getMedicineDao().queryBuilder()
                                .where(MedicineDao.Properties.MedicineID.eq(medicineTreatment.getMedicineID()))
                                .list().get(0);

                        treatmentList.add(new TreatmentDetail(treatment.getTreatmentID(),
                                medicines.getName(),
                                medicineTreatment.getDosage().toString(),  medicineTreatment.getTime(), "MED"));
                }
                else if (treatment.getTreatmentTypeID().equals("MEA")) {
                    MeasurementTreatment measurementTreatment = MedminderApp.getDaoSession()
                            .getMeasurementTreatmentDao().queryBuilder()
                            .where(MeasurementTreatmentDao.Properties.TreatmentID.eq(treatment.getTreatmentID()))
                            .where(MeasurementTreatmentDao.Properties.Measured.eq("N"))
                            .orderAsc(MeasurementTreatmentDao.Properties.Time)
                            .list().get(0);

                    MeasurementType measurementTypes = MedminderApp
                            .getDaoSession().getMeasurementTypeDao().queryBuilder()
                            .where(MeasurementTypeDao.Properties.MeasurementTypeID.eq(measurementTreatment.getMeasurementTypeID()))
                            .list().get(0);
                    treatmentList.add(new TreatmentDetail(treatment.getTreatmentID(),
                            measurementTypes.getTitle(),
                            "0", measurementTreatment.getTime(), "MEA"));

                }
            }
        } catch (IndexOutOfBoundsException ex) {}

        recyclerViewTreatments.setLayoutManager(new LinearLayoutManager(this));
        TreatmentsRecyclerViewAdapter adapter = new TreatmentsRecyclerViewAdapter(this, treatmentList);
        //adapter.setClickListener(this);
        recyclerViewTreatments.setAdapter(adapter);
    }
}