package com.aae.medminder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aae.medminder.models.Appointment;
import com.aae.medminder.models.Doctor;

public class AddAppointmentActivity extends AppCompatActivity {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    private EditText editTextAppointmentTitle;
    private Spinner spinnerDoctor;
    private EditText dateInput;
    private Button setDateButton;
    private EditText editTextLocation;
    private EditText editTextNote;
    private CheckBox checkBoxIsReminder;
    private Button buttonSaveAppointment;
    private int mYear, mMonth, mDay;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Appointment");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        editTextAppointmentTitle = findViewById(R.id.editTextAppointmentTitle);
        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextNote = findViewById(R.id.editTextNote);
        checkBoxIsReminder = findViewById(R.id.checkBoxIsReminder);
        buttonSaveAppointment = findViewById(R.id.buttonSaveAppointment);


        setDateButton = findViewById(R.id.setDateButton);
        dateInput = findViewById(R.id.editTextAppointmentDate);

        List<Doctor> doctorList = MedminderApp.getDaoSession().getDoctorDao().loadAll();
        doctorList.add(0, new Doctor(null, "Please, select doctor ", "if any.", null, null, null));
        ArrayAdapter<Doctor> adapterDoctor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctorList);
        adapterDoctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapterDoctor);

        setDateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, monthOfYear,dayOfMonth);
                                dateInput.setText(sdf.format(c.getTime()));

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        buttonSaveAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void save() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentID(null);
        appointment.setTitle(editTextAppointmentTitle.getText().toString());
        appointment.setDoctor((Doctor)spinnerDoctor.getSelectedItem());
        appointment.setDate(dateInput.getText().toString());
        appointment.setLocation(editTextLocation.getText().toString());
        appointment.setNotes(editTextNote.getText().toString());
        if (checkBoxIsReminder.isChecked()) {
            appointment.setReminder(1L);
        } else {
            appointment.setReminder(0L);
        }

        MedminderApp.getDaoSession().getAppointmentDao().insert(appointment);
    }

}