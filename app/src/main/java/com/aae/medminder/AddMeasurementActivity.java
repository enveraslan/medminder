package com.aae.medminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.aae.medminder.models.Treatment;
import com.aae.medminder.models.TreatmentDao;
import com.aae.medminder.models.TreatmentType;
import com.aae.medminder.notification.NotificationScheduler;

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
    private Long treatmentId;
    private Calendar calendar = null;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);
        treatmentId = getIntent().getLongExtra("treatmentId", 0);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Add Measurement");
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 30);

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
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
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

        if(treatmentId > 0){
            buttonDeleteMeasurement.setVisibility(View.VISIBLE);
            mToolbar.setTitle("Edit Measurement");
            setMeasurement(treatmentId);
        }
        buttonSaveMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMeasurement();
            }
        });

        buttonDeleteMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteMeasurement();
            }
        });

    }

    public class measurementItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinnerMeasurement.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinnerMeasurement.getSelectedItem()))) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#808080"));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }


    private void saveMeasurement() {
        Treatment treatment = new Treatment();
        if(treatmentId > 0){
            UpdateMeasurement();
        }else{
            treatment.setTreatmentID(null);
            treatment.setTreatmentType(new TreatmentType("MEA", "Measurement"));
            Calendar createdDate = Calendar.getInstance();
            treatment.setCreatedDate(createdDate.getTime().toString());

            MedminderApp.getDaoSession().getTreatmentDao().insert(treatment);

            List<Treatment> treatmentQuery = MedminderApp.getDaoSession().getTreatmentDao().queryBuilder()
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
        }

        Notification notification = NotificationScheduler.createNotification(getApplicationContext(),
                "Medminder",
                "Don't forget to measure "+ spinnerMeasurement.getSelectedItem().toString(),
                R.drawable.ac_glucose_meter,
                R.drawable.ac_measurement_notificiation_big,
                MainActivity.class);

        calendar.add(Calendar.MINUTE,-5);
        NotificationScheduler.scheduleRepeatingNotification(getApplicationContext(),
                notification,
                Integer.parseInt(treatment.getTreatmentID().toString()),
                calendar,
                AlarmManager.INTERVAL_DAY);


        finish();
        startActivity(new Intent(this, MainActivity.class));

    }

    private void UpdateMeasurement() {

        MeasurementType measurementType = MedminderApp.getDaoSession().getMeasurementTypeDao().queryBuilder()
                .where(MeasurementTypeDao.Properties.Title.eq(spinnerMeasurement.getSelectedItem().toString()))
                .list().get(0);

        String updateMeasurementTreatmentQuery = String.format("update "+ MeasurementTreatmentDao.TABLENAME +" set "
                + MeasurementTreatmentDao.Properties.Time.columnName + " = '%s', "
                + MeasurementTreatmentDao.Properties.MeasurementTypeID.columnName + " = '%s'"
                + " where " + MeasurementTreatmentDao.Properties.TreatmentID.columnName + " = '%d'", editTextMeasurementTime.getText().toString(), measurementType.getMeasurementTypeID(),  treatmentId);

        MedminderApp.getDaoSession().getDatabase().execSQL(updateMeasurementTreatmentQuery);
        MedminderApp.updateSession();

        Notification notification = NotificationScheduler.createNotification(getApplicationContext(),
                "Medminder",
                "Don't forget to measure "+ spinnerMeasurement.getSelectedItem().toString(),
                R.drawable.ac_glucose_meter,
                R.drawable.ac_measurement_notificiation_big,
                MainActivity.class);

        calendar.add(Calendar.MINUTE,-5);
        NotificationScheduler.scheduleRepeatingNotification(getApplicationContext(),
                notification,
                Integer.parseInt(treatmentId.toString()),
                calendar,
                AlarmManager.INTERVAL_DAY);
    }

    private void DeleteMeasurement(){
        String deleteTreatmentQuery = String.format("delete from " + TreatmentDao.TABLENAME +
                " where " + TreatmentDao.Properties.TreatmentID.columnName + " = %d", treatmentId);

        String deleteMeasurementTreatmentQuery = String.format("delete from "+ MeasurementTreatmentDao.TABLENAME
                + " where " + MeasurementTreatmentDao.Properties.TreatmentID.columnName + " = '%d'", treatmentId);

        MedminderApp.getDaoSession().getDatabase().execSQL(deleteTreatmentQuery);
        MedminderApp.getDaoSession().getDatabase().execSQL(deleteMeasurementTreatmentQuery);
        MedminderApp.updateSession();

        NotificationScheduler.cancelNotification(getApplicationContext(), Integer.parseInt(treatmentId.toString()));

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void setMeasurement(long treatmentId){
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
            spinnerMeasurement.setSelection(getIndex(spinnerMeasurement, measurementTreatment.getMeasurementType().getTitle()));
            editTextMeasurementTime.setText(measurementTreatment.getTime());

            String timeText = measurementTreatment.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeText.split(":")[0].trim()));
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeText.split(":")[1].trim()));
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