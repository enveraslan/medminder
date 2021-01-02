package com.aae.medminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aae.medminder.models.Appointment;
import com.aae.medminder.models.AppointmentDao;
import com.aae.medminder.models.Doctor;
import com.aae.medminder.models.DoctorDao;

import org.greenrobot.greendao.annotation.Index;

public class AddAppointmentActivity extends AppCompatActivity {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    private EditText editTextAppointmentTitle;
    private Spinner spinnerDoctor;
    private EditText dateInput;
    private EditText editTextTime;
    private Button setDateButton;
    private EditText editTextLocation;
    private EditText editTextNote;
    private CheckBox checkBoxIsReminder;
    private Button buttonSaveAppointment;
    private Button buttonSetTime;
    private Button deleteButton;
    private int mYear, mMonth, mDay;
    Context context = this;

    private Long appointmentID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        appointmentID = getIntent().getLongExtra("appointmentID", 0);

        editTextAppointmentTitle = findViewById(R.id.editTextAppointmentTitle);
        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextNote = findViewById(R.id.editTextNote);
        checkBoxIsReminder = findViewById(R.id.checkBoxIsReminder);
        buttonSaveAppointment = findViewById(R.id.buttonSaveAppointment);
        editTextTime = findViewById(R.id.editTextAppointmentTime);
        editTextTime.setKeyListener(null);
        buttonSetTime = findViewById(R.id.buttonSetTime);
        setDateButton = findViewById(R.id.setDateButton);
        dateInput = findViewById(R.id.editTextAppointmentDate);
        dateInput.setKeyListener(null);
        deleteButton = findViewById(R.id.buttonDeleteAppointment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(appointmentID > 0) {
            toolbar.setTitle("Edit Doctor");
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            toolbar.setTitle("Add Doctor");
            deleteButton.setVisibility(View.INVISIBLE);
        }
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);



        final List<Doctor> doctorList = MedminderApp.getDaoSession().getDoctorDao().loadAll();
        ArrayAdapter<Doctor> adapterDoctor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctorList);
        adapterDoctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapterDoctor);
        spinnerDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editTextLocation.setText(doctorList.get(position).getLocation());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                c.set(Calendar.MONTH, monthOfYear);
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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDoctor();
            }
        });

        init();
    }


    private void init() {
        try {
            if(appointmentID > 0) {
                Appointment appointment = MedminderApp.getDaoSession().getAppointmentDao().queryBuilder()
                        .where(AppointmentDao.Properties.AppointmentID.eq(appointmentID))
                        .list().get(0);
                Doctor doctor = MedminderApp.getDaoSession().getDoctorDao().queryBuilder()
                        .where(DoctorDao.Properties.DoctorID.eq(appointment.getDoctorID()))
                        .list().get(0);

                editTextAppointmentTitle.setText(appointment.getTitle());
                spinnerDoctor.setSelection(getIndex(spinnerDoctor, doctor));
                dateInput.setText(appointment.getDate());
                editTextTime.setText(appointment.getTime());
                editTextLocation.setText(appointment.getLocation());
                editTextNote.setText(appointment.getNotes());

                if(appointment.getReminder() == 1L) {
                    checkBoxIsReminder.setChecked(true);
                } else {
                    checkBoxIsReminder.setChecked(false);
                }

            }

        } catch (IndexOutOfBoundsException ex) {

        }
    }

    private void save() {
        Appointment appointment = new Appointment();
        appointment.setTitle(editTextAppointmentTitle.getText().toString());
        appointment.setDoctor((Doctor)spinnerDoctor.getSelectedItem());
        appointment.setDate(dateInput.getText().toString());
        appointment.setTime(editTextTime.getText().toString());
        appointment.setLocation(editTextLocation.getText().toString());
        appointment.setNotes(editTextNote.getText().toString());
        if (checkBoxIsReminder.isChecked()) {
            appointment.setReminder(1L);
        } else {
            appointment.setReminder(0L);
        }
        finish();
        if(appointmentID > 0) {
            appointment.setAppointmentID(appointmentID);
            MedminderApp.getDaoSession().getAppointmentDao().update(appointment);
        } else {
            appointment.setAppointmentID(null);
            MedminderApp.getDaoSession().getAppointmentDao().insert(appointment);
        }

        finish();
        startActivity(new Intent(this, AppointmentsActivity.class));

    }

    private int getIndex(Spinner spinner, Doctor doctor){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(doctor.toString())){
                return i;
            }
        }
        return 0;
    }

    private void deleteDoctor() {
        MedminderApp.getDaoSession().getAppointmentDao().deleteByKey(appointmentID);
        finish();
        startActivity(new Intent(this, AppointmentsActivity.class));
    }

}