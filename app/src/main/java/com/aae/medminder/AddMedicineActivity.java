package com.aae.medminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.aae.medminder.models.Medicine;
import com.aae.medminder.models.MedicineDao;
import com.aae.medminder.models.MedicineTreatment;
import com.aae.medminder.models.MedicineTreatmentDao;
import com.aae.medminder.models.MedicineUnit;
import com.aae.medminder.models.Treatment;
import com.aae.medminder.models.TreatmentDao;
import com.aae.medminder.models.TreatmentType;
import com.aae.medminder.notification.NotificationScheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddMedicineActivity extends AppCompatActivity {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    private EditText editTextMedicineName;
    private TextView editTextDosage;
    private EditText editTextRemaining;
    private Spinner spinnerMedicineUnit;
    private EditText editTextTime;
    private Button buttonSetTime;
    private Button buttonSaveMedicine;
    private Button buttonDecreaseDosage;
    private Button buttonIncreaseDosage;
    private Button buttonDeleteMedicine;
    private Long dosage = Long.valueOf(1);
    private Long treatmentId;
    private Long medicineId;
    private Calendar calendar = null;

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        treatmentId = getIntent().getLongExtra("treatmentId", 0);

        editTextMedicineName = findViewById(R.id.editTextMedicineName);
        editTextDosage = findViewById(R.id.editTextDosage);
        editTextRemaining = findViewById(R.id.editTextRemaining);
        spinnerMedicineUnit = findViewById(R.id.spinnerMedicineUnit);
        editTextTime = findViewById(R.id.editTextMedicineTime);
        buttonSetTime = findViewById(R.id.buttonSetTime);
        buttonSaveMedicine = findViewById(R.id.buttonSaveMedicine);
        buttonDeleteMedicine = findViewById(R.id.buttonDeleteMedicine);
        buttonDecreaseDosage = findViewById(R.id.buttonDecreaseDosage);
        buttonIncreaseDosage = findViewById(R.id.buttonIncreaseDosage);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 30);

        List<MedicineUnit> medicineUnits =  new ArrayList<MedicineUnit>(((MedminderApp)getApplication()).getDaoSession().getMedicineUnitDao().loadAll());
        ArrayAdapter<MedicineUnit> adapterMedicineUnit = new ArrayAdapter<MedicineUnit>(this, android.R.layout.simple_spinner_item, medicineUnits);
        adapterMedicineUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMedicineUnit.setAdapter(adapterMedicineUnit);
        spinnerMedicineUnit.setOnItemSelectedListener(new medicineItemSelectedListener());

        buttonSetTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                calendar = Calendar.getInstance();
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(context,
                        android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String hS = (hourOfDay < 10 || hourOfDay == 24) ? "0" + hourOfDay : Integer.toString(hourOfDay);
                                String mS = (minute < 10 || minute == 60) ? "0" + minute : Integer.toString(minute);
                                editTextTime.setText(hS + ":" + mS);
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                            }
                        }, hour, minute, true);
                tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Choose", tpd);
                tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", tpd);

                tpd.show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Medicine");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(treatmentId > 0){
            buttonDeleteMedicine.setVisibility(View.VISIBLE);
            toolbar.setTitle("Edit Medicine");
            setMedicine(treatmentId);
        }

        buttonSaveMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedicine();
            }
        });

        buttonIncreaseDosage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dosage++;
                editTextDosage.setText(dosage.toString());
            }
        });

        buttonDecreaseDosage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dosage > 1) {
                    dosage--;
                }
                editTextDosage.setText(dosage.toString());
            }
        });

        buttonDeleteMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMedicine();
            }
        });
    }

    public class medicineItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinnerMedicineUnit.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if (firstItem.equals(String.valueOf(spinnerMedicineUnit.getSelectedItem()))) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#808080"));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }

    private void saveMedicine() {

        if(calendar == null){
            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 30);
        }
        Treatment treatment = new Treatment();
        treatment.setTreatmentID(null);
        treatment.setTreatmentType(new TreatmentType("MED", "Medicine"));
        Calendar createdDate = Calendar.getInstance();
        treatment.setCreatedDate(createdDate.getTime().toString());

        Medicine medicine = new Medicine();
        medicine.setMedicineID(null);
        medicine.setBarcode(null);
        medicine.setName(editTextMedicineName.getText().toString());
        Long remaining =  TextUtils.isEmpty(editTextRemaining.getText().toString()) ? Long.valueOf(0)  : Long.valueOf(editTextRemaining.getText().toString());
        medicine.setCount(remaining);
        medicine.setMedicineUnit(((MedicineUnit)spinnerMedicineUnit.getSelectedItem()));

        //Update
            try {
                //Update
                if(treatmentId > 0){
                    UpdateMedicine();
                }
                //Insert
                else
                {
                    MedminderApp.getDaoSession().getTreatmentDao().insert(treatment);
                    MedminderApp.getDaoSession().getMedicineDao().insert(medicine);

                    List<Treatment> treatmentQuery = ((MedminderApp)getApplication()).getDaoSession().getTreatmentDao().queryBuilder()
                            .where(TreatmentDao.Properties.TreatmentTypeID.eq("MED"))
                            .where(TreatmentDao.Properties.CreatedDate.eq(createdDate.getTime().toString()))
                            .list();

                    List<Medicine> medicineQuery = ((MedminderApp)getApplication()).getDaoSession().getMedicineDao().queryBuilder()
                            .where(MedicineDao.Properties.Name.eq(editTextMedicineName.getText().toString()))
                            .list();

                    Calendar date = Calendar.getInstance();
                    medicine.setMedicineID(medicineQuery.get(0).getMedicineID());
                    treatment.setTreatmentID(treatmentQuery.get(0).getTreatmentID());

                    for(int i = 0; i < 30; i++) {
                        InsertMedicineTreatment(treatment, medicine, date);
                    }
                }
            } catch (IndexOutOfBoundsException ex) {
                Log.e("MedicineOperation", ex.getMessage());
            }
        Notification notification = NotificationScheduler.createNotification(getApplicationContext(),
                "Medminder",
                "Don't forget to consume "+medicine.getName(),
                R.drawable.ac_pill2,
                R.drawable.ac_pill,
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

    private void InsertMedicineTreatment(Treatment treatment, Medicine medicine, Calendar date) {

        MedicineTreatment medicineTreatment = new MedicineTreatment();
        medicineTreatment.setMedicineTreatmentID(null);
        medicineTreatment.setConsumedDosage(null);
        medicineTreatment.setTreatment(treatment);
        medicineTreatment.setMedicine(medicine);
        medicineTreatment.setDosage(dosage);
        medicineTreatment.setCosumeType("N");
        medicineTreatment.setTime(editTextTime.getText().toString());
        medicineTreatment.setDate(sdf.format(date.getTime()));
        MedminderApp.getDaoSession().getMedicineTreatmentDao().insert(medicineTreatment);
        date.add(Calendar.DAY_OF_MONTH, 1);
    }
    private void UpdateMedicine() {
        String updateQueryMedicineTreatment = String.format("update " + MedicineTreatmentDao.TABLENAME + " set "
                + MedicineTreatmentDao.Properties.Time.columnName + " = '%s',"
                + MedicineTreatmentDao.Properties.Dosage.columnName + " = %d"
                + " where " + MedicineTreatmentDao.Properties.TreatmentID.columnName + " = %d", editTextTime.getText().toString(), dosage, treatmentId);

        String updateQueryTreatment = String.format("update " + MedicineDao.TABLENAME + " set "
                        + MedicineDao.Properties.MedicineUnitID.columnName + " = '%s', "
                        + MedicineDao.Properties.Name.columnName + " = '%s', "
                        + MedicineDao.Properties.Count.columnName + " = %d"
                        + " where " + MedicineDao.Properties.MedicineID.columnName + " = %d",
                ((MedicineUnit) spinnerMedicineUnit.getSelectedItem()).getMedicineUnitID(),
                editTextMedicineName.getText().toString(),
                TextUtils.isEmpty(editTextRemaining.getText().toString()) ? Long.valueOf(0) : Long.valueOf(editTextRemaining.getText().toString()),
                medicineId);

        MedminderApp.getDaoSession().getDatabase().execSQL(updateQueryMedicineTreatment);
        MedminderApp.getDaoSession().getDatabase().execSQL(updateQueryTreatment);
        MedminderApp.updateSession();

        Notification notification = NotificationScheduler.createNotification(getApplicationContext(),
                "Medminder",
                "Don't forget to consume "+editTextMedicineName.getText().toString(),
                R.drawable.ac_pill2,
                R.drawable.ac_pill,
                MainActivity.class);
        calendar.add(Calendar.MINUTE, -5);
        NotificationScheduler.scheduleRepeatingNotification(getApplicationContext(),
                notification,
                Integer.parseInt(treatmentId.toString()),
                calendar,
                AlarmManager.INTERVAL_DAY);

    }

    private void deleteMedicine(){
        String deleteTreatment = String.format("delete from " + TreatmentDao.TABLENAME +
                " where " + TreatmentDao.Properties.TreatmentID.columnName + " = %d", treatmentId);

        String deleteMedicineTreatment = String.format("delete from " + MedicineTreatmentDao.TABLENAME
                + " where " + MedicineTreatmentDao.Properties.TreatmentID.columnName + " = %d", treatmentId);

        String deleteMedicine = String.format("delete from " + MedicineDao.TABLENAME
                + " where " + MedicineDao.Properties.MedicineID.columnName + " = %d", medicineId);

        MedminderApp.getDaoSession().getDatabase().execSQL(deleteMedicineTreatment);
        MedminderApp.getDaoSession().getDatabase().execSQL(deleteTreatment);
        MedminderApp.getDaoSession().getDatabase().execSQL(deleteMedicine);

        MedminderApp.updateSession();

        NotificationScheduler.cancelNotification(getApplicationContext(), Integer.parseInt(treatmentId.toString()));

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void setMedicine(long treatmentId){

        List<Treatment> treatments = MedminderApp
                .getDaoSession().getTreatmentDao().queryBuilder()
                .where(TreatmentDao.Properties.TreatmentID.eq(treatmentId))
                .list();

        List<MedicineTreatment> medicineTreatments = MedminderApp
                .getDaoSession().getMedicineTreatmentDao().queryBuilder()
                .where(MedicineTreatmentDao.Properties.TreatmentID.eq(treatmentId))
                .list();

        if(medicineTreatments.size() > 0){
            MedicineTreatment medicineTreatment = medicineTreatments.get(0);
            medicineId = medicineTreatment.getMedicineID();
            Medicine medicine =  MedminderApp
                    .getDaoSession().getMedicineDao().queryBuilder()
                    .where(MedicineDao.Properties.MedicineID.eq(medicineTreatment.getMedicineID()))
                    .list().get(0);

            editTextMedicineName.setText(medicine.getName());
            editTextDosage.setText(medicineTreatment.getDosage().toString());
            dosage = medicineTreatment.getDosage();
            spinnerMedicineUnit.setSelection(getIndex(spinnerMedicineUnit,medicine.getMedicineUnit().getTitle()));
            editTextTime.setText(medicineTreatment.getTime());
            editTextRemaining.setText(medicine.getCount().toString());

            String timeText = medicineTreatment.getTime();
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