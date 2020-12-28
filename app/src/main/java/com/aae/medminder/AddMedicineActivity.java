package com.aae.medminder;

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

import com.aae.medminder.models.Doctor;
import com.aae.medminder.models.DoctorDao;
import com.aae.medminder.models.Medicine;
import com.aae.medminder.models.MedicineDao;
import com.aae.medminder.models.MedicineTreatment;
import com.aae.medminder.models.MedicineUnit;
import com.aae.medminder.models.Treatment;
import com.aae.medminder.models.TreatmentDao;
import com.aae.medminder.models.TreatmentType;

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
    private Long dosage = Long.valueOf(1);

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        editTextMedicineName = findViewById(R.id.editTextMedicineName);
        editTextDosage = findViewById(R.id.editTextDosage);
        editTextRemaining = findViewById(R.id.editTextRemaining);
        spinnerMedicineUnit = findViewById(R.id.spinnerMedicineUnit);
        editTextTime = findViewById(R.id.editTextMedicineTime);
        buttonSetTime = findViewById(R.id.buttonSetTime);
        buttonSaveMedicine = findViewById(R.id.buttonSaveMedicine);
        buttonDecreaseDosage = findViewById(R.id.buttonDecreaseDosage);
        buttonIncreaseDosage = findViewById(R.id.buttonIncreaseDosage);

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
                                editTextTime.setText(hS + ":" + mS);
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


    }

    public class medicineItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinnerMedicineUnit.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if (firstItem.equals(String.valueOf(spinnerMedicineUnit.getSelectedItem()))) {
                // ToDo when first item is selected
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#808080"));

            } else {
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
                // Todo when item is selected by the user
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }

    private void saveMedicine() {

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

        ((MedminderApp)getApplication()).getDaoSession().getTreatmentDao().insert(treatment);
        ((MedminderApp)getApplication()).getDaoSession().getMedicineDao().insert(medicine);



        List<Treatment> treatmentQuery = ((MedminderApp)getApplication()).getDaoSession().getTreatmentDao().queryBuilder()
                .where(TreatmentDao.Properties.TreatmentTypeID.eq("MED"))
                .where(TreatmentDao.Properties.CreatedDate.eq(createdDate.getTime().toString()))
                .list();

        List<Medicine> medicineQuery = ((MedminderApp)getApplication()).getDaoSession().getMedicineDao().queryBuilder()
                .where(MedicineDao.Properties.Name.eq(editTextMedicineName.getText().toString()))
                .list();

        try {
            Calendar date = Calendar.getInstance();

            medicine.setMedicineID(medicineQuery.get(0).getMedicineID());
            treatment.setTreatmentID(treatmentQuery.get(0).getTreatmentID());


            for(int i = 0; i < 30; i++) {
                MedicineTreatment medicineTreatment = new MedicineTreatment();
                medicineTreatment.setMedicineTreatmentID(null);
                medicineTreatment.setConsumedDosage(null);
                medicineTreatment.setTreatment(treatment);
                medicineTreatment.setMedicine(medicine);
                medicineTreatment.setDosage(dosage);
                medicineTreatment.setCosumeType("N");
                medicineTreatment.setTime(editTextTime.getText().toString());
                medicineTreatment.setDate(sdf.format(date.getTime()));
                ((MedminderApp)getApplication()).getDaoSession().getMedicineTreatmentDao().insert(medicineTreatment);
                Log.d("Date", medicineTreatment.getDate());
                date.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (IndexOutOfBoundsException ex) {

        }

        finish();
        startActivity(new Intent(this, MainActivity.class));


    }
}