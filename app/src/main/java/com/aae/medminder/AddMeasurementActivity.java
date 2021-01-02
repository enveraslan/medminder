package com.aae.medminder;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaMetadata;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aae.medminder.models.MeasurementTreatment;
import com.aae.medminder.models.MeasurementTreatmentDao;
import com.aae.medminder.models.MeasurementType;
import com.aae.medminder.models.MeasurementTypeDao;
import com.aae.medminder.models.MedicineUnit;
import com.aae.medminder.models.Treatment;
import com.aae.medminder.models.TreatmentDao;
import com.aae.medminder.models.TreatmentType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMeasurementActivity extends AppCompatActivity {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    private Toolbar mToolbar;
    private Spinner spinnerMeasurement;
    private EditText editTextMeasurementTime;
    private Button buttonSetMeasurementTime;
    private Button buttonSaveMeasurement;
    private Button buttonDeleteMeasurement;
    private long measurementId;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);
        measurementId = getIntent().getLongExtra("treatmentId", 0);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Add Measurement");
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        spinnerMeasurement = findViewById(R.id.spinnerMeasurement);
        editTextMeasurementTime = findViewById(R.id.editTextMeasurementTime);
        buttonSetMeasurementTime = findViewById(R.id.buttonSetMeasurementTime);
        buttonSaveMeasurement = findViewById(R.id.buttonSaveMeasurement);
        buttonDeleteMeasurement = findViewById(R.id.buttonDeleteMeasurement);

        buttonSetMeasurementTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Calendar calendar = Calendar.getInstance();
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(context,
                        android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String hS = (hourOfDay < 10 || hourOfDay == 24) ? "0" + hourOfDay : Integer.toString(hourOfDay);
                                String mS = (minute < 10 || minute == 60) ? "0" + minute : Integer.toString(minute);
                                editTextMeasurementTime.setText(hS + ":" + mS);
                            }
                        }, hour, minute, true);
                tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Choose", tpd);
                tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", tpd);

                tpd.show();
            }
        });


        List<MeasurementType> measurementTypes = new ArrayList<MeasurementType>(((MedminderApp)getApplication()).getDaoSession().getMeasurementTypeDao().loadAll());
        ArrayAdapter<MeasurementType> adapterMeasurementType = new ArrayAdapter<MeasurementType>(this, android.R.layout.simple_spinner_item, measurementTypes);
        adapterMeasurementType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeasurement.setAdapter(adapterMeasurementType);
        spinnerMeasurement.setOnItemSelectedListener(new measurementItemSelectedListener());

        if(measurementId > 0){
            buttonDeleteMeasurement.setVisibility(View.VISIBLE);
            mToolbar.setTitle("Edit Measurement");
            setMeasurement(measurementId);
        }

        buttonSaveMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMeasurement();
            }
        });

    }

    public class measurementItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinnerMeasurement.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if (firstItem.equals(String.valueOf(spinnerMeasurement.getSelectedItem()))) {
                // ToDo when first item is selected
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#808080"));

            } else {
                //Toast.makeText(parent.getContext(),
                        //"You have selected : " + parent.getItemAtPosition(pos).toString(),
                        //Toast.LENGTH_LONG).show();
                // Todo when item is selected by the user
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }


    private void saveMeasurement() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentID(null);
        treatment.setTreatmentType(new TreatmentType("MEA", "Measurement"));
        Calendar createdDate = Calendar.getInstance();
        treatment.setCreatedDate(createdDate.getTime().toString());

        ((MedminderApp)getApplication()).getDaoSession().getTreatmentDao().insert(treatment);

        List<Treatment> treatmentQuery = ((MedminderApp)getApplication()).getDaoSession().getTreatmentDao().queryBuilder()
                .where(TreatmentDao.Properties.TreatmentTypeID.eq("MEA"))
                .where(TreatmentDao.Properties.CreatedDate.eq(createdDate.getTime().toString()))
                .list();
        MeasurementType measurementType = MedminderApp.getDaoSession().getMeasurementTypeDao().queryBuilder()
                .where(MeasurementTypeDao.Properties.Title.eq(spinnerMeasurement.getSelectedItem().toString()))
                .list().get(0);

        try {
            Calendar date = Calendar.getInstance();
            treatment.setTreatmentID(treatmentQuery.get(0).getTreatmentID());

            for(int i = 0; i < 30; i++) {
                MeasurementTreatment measurementTreatment = new MeasurementTreatment();
                measurementTreatment.setMeasurementTreatmentID(null);
                measurementTreatment.setMeasurementType(measurementType);
                measurementTreatment.setTreatment(treatment);
                measurementTreatment.setMeasured("N");
                measurementTreatment.setTime(editTextMeasurementTime.getText().toString());
                measurementTreatment.setDate(sdf.format(date.getTime()));
                MedminderApp.getDaoSession().getMeasurementTreatmentDao().insert(measurementTreatment);
                date.add(Calendar.DAY_OF_MONTH, 1);
            }


        }catch (IndexOutOfBoundsException ex) {}

        finish();
        startActivity(new Intent(this, MainActivity.class));

    }

    private void setMeasurement(long treatmentId){
        Toast.makeText(this, "Treatment ID: " +treatmentId, Toast.LENGTH_LONG).show();

        List<MeasurementTreatment> measurementTreatments = MedminderApp.getDaoSession()
                .getMeasurementTreatmentDao().queryBuilder()
                .where(MeasurementTreatmentDao.Properties.TreatmentID.eq(treatmentId))
                .where(MeasurementTreatmentDao.Properties.Measured.eq("N"))
                .orderAsc(MeasurementTreatmentDao.Properties.Time)
                .list();

        if(measurementTreatments.size() > 0){
            MeasurementTreatment measurementTreatment = measurementTreatments.get(0);
            MeasurementType measurementType = MedminderApp
                    .getDaoSession().getMeasurementTypeDao().queryBuilder()
                    .where(MeasurementTypeDao.Properties.MeasurementTypeID.eq(measurementTreatment.getMeasurementTypeID()))
                    .list().get(0);
            Toast.makeText(context, measurementTreatment.getMeasurementType().getTitle().toString(), Toast.LENGTH_LONG).show();
            spinnerMeasurement.setSelection(getIndex(spinnerMeasurement, measurementTreatment.getMeasurementType().getTitle()));
            editTextMeasurementTime.setText(measurementTreatment.getTime());
        }

    }

    private int getIndex(Spinner spinner, String myString){

        for (int i=0;i<spinner.getCount();i++){
            if (myString.equals(spinner.getItemAtPosition(i).toString())){
                return i;
            }
        }
        return 0;
    }
}