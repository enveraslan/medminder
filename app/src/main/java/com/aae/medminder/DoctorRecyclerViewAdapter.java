package com.aae.medminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aae.medminder.models.Doctor;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorRecyclerViewAdapter extends RecyclerView.Adapter<DoctorRecyclerViewAdapter.Holder> {
    private ArrayList<Doctor> doctors;
    private Context context;


    public DoctorRecyclerViewAdapter(Context context, ArrayList<Doctor> doctors) {
        this.context = context;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.doctor_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        final String fullName, phone, email, location;
        fullName = doctors.get(position).getFirstName() + " " + doctors.get(position).getLastName();
        phone = doctors.get(position).getPhoneNumber();
        email = doctors.get(position).getEmail();
        location = doctors.get(position).getLocation();
        holder.textViewDoctorName.setText(fullName);
        holder.textViewDoctorPhone.setText(phone);
        final long doctorID = doctors.get(position).getDoctorID();
        holder.doctorsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context, DoctorDetailsActivity.class);
                intent.putExtra("doctorID", doctorID);
                intent.putExtra("doctorFullName", fullName);
                intent.putExtra("phoneNumber", phone);
                intent.putExtra("email", email);
                intent.putExtra("location", location);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textViewDoctorName;
        TextView textViewDoctorPhone;
        ConstraintLayout doctorsLayout;

        public Holder(View itemView) {
            super(itemView);
            textViewDoctorName = itemView.findViewById(R.id.textViewDoctorRowName);
            textViewDoctorPhone = itemView.findViewById(R.id.textViewDoctorRowPhoneNumber);
            doctorsLayout = itemView.findViewById(R.id.doctorsLayout);
        }
    }
}
