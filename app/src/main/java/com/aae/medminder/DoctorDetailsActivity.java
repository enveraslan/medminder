package com.aae.medminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorDetailsActivity extends AppCompatActivity {
    private TextView textViewPhone;
    private TextView textViewEmail;
    private TextView textViewLocation;
    private Button buttonEditDoctor;

    private Long doctorID;
    private String fullName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        init();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(fullName);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewLocation = findViewById(R.id.textViewLocation);
        buttonEditDoctor = findViewById(R.id.buttonSaveDoctor);



        if(getIntent().hasExtra("doctorID") && getIntent().hasExtra("doctorFullName") &&
                getIntent().hasExtra("phoneNumber") && getIntent().hasExtra("email") &&
                getIntent().hasExtra("location")){

            doctorID = getIntent().getLongExtra("doctorID", 0);
            fullName = getIntent().getStringExtra("doctorFullName");
            textViewPhone.setText(getIntent().getStringExtra("phoneNumber"));
            textViewEmail.setText(getIntent().getStringExtra("email"));
            textViewLocation.setText(getIntent().getStringExtra("location"));

        } else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }

    }


    public void clickEditDoctorButton(View view) {
        Intent intent = new Intent(this, AddDoctorActivity.class);
        intent.putExtra("doctorID", doctorID);

        startActivity(intent);
    }

}