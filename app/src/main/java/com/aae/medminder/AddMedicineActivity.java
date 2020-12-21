package com.aae.medminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddMedicineActivity extends AppCompatActivity {

    private Spinner medicineTypeSpinner;
    private Spinner intervalSpinner;
    private Spinner instructionsSpinner;

    private EditText timeInput;
    private Button setTimeButton;
    private Button setDateButton;
    private EditText startsFromInput;
    Context context = this;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        medicineTypeSpinner = findViewById(R.id.medicine_type_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.medicine_type_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        medicineTypeSpinner.setAdapter(adapter);
        medicineTypeSpinner.setOnItemSelectedListener(new medicineItemSelectedListener());

        intervalSpinner = findViewById(R.id.interval_spinner);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.interval_array, R.layout.spinner_item);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
        intervalSpinner.setAdapter(adapter1);
        intervalSpinner.setOnItemSelectedListener(new intervalItemSelectedListener());

        instructionsSpinner = findViewById(R.id.instructions_spinner);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.instruction_array, R.layout.spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        instructionsSpinner.setAdapter(adapter2);
        instructionsSpinner.setOnItemSelectedListener(new instructionsItemSelectedListener());

        timeInput = findViewById(R.id.time_input);
        setTimeButton = findViewById(R.id.setTimeButton);
        setTimeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timeInput.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, true);
                tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Choose", tpd);
                tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel", tpd);
                tpd.show();
            }
        });

        setDateButton= findViewById(R.id.setDateButton);
        startsFromInput = findViewById(R.id.starts_from_input);

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

                                startsFromInput.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
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


    }

    public class medicineItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(medicineTypeSpinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if (firstItem.equals(String.valueOf(medicineTypeSpinner.getSelectedItem()))) {
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

    public class intervalItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(intervalSpinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if (firstItem.equals(String.valueOf(intervalSpinner.getSelectedItem()))) {
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

    public class instructionsItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(instructionsSpinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if (firstItem.equals(String.valueOf(instructionsSpinner.getSelectedItem()))) {
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
}