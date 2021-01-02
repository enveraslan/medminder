package com.aae.medminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aae.medminder.models.Appointment;
import com.aae.medminder.models.Doctor;
import com.aae.medminder.models.DoctorDao;

import java.util.List;

public class AppointmentRecyclerViewAdapter extends RecyclerView.Adapter<AppointmentRecyclerViewAdapter.ViewHolder> {

    private List<Appointment> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;



    // data is passed into the constructor
    AppointmentRecyclerViewAdapter(Context context, List<Appointment> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.appointment_row_card, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Appointment appointment = mData.get(position);
        try {
            Doctor doctor = MedminderApp.getDaoSession().getDoctorDao().queryBuilder()
                    .where(DoctorDao.Properties.DoctorID.eq(appointment.getDoctorID()))
                    .list().get(0);
            if(appointment.getDoctorID() == 1L) {
                holder.titleTxt.setText("-");
            } else {
                holder.titleTxt.setText("Dr." + doctor.toString());
            }
        } catch (IndexOutOfBoundsException ex) {

        }

        holder.specialtyTxt.setText(appointment.getTitle());
        holder.dateTxt.setText(appointment.getDate());
        holder.timeTxt.setText(appointment.getTime());
        holder.hospitalTxt.setText(appointment.getLocation());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //TextView myTextView;

        TextView titleTxt;
        TextView hospitalTxt;
        TextView dateTxt;
        TextView specialtyTxt;
        TextView timeTxt;
        LinearLayout appointmentCard;

        ViewHolder(View itemView) {

            super(itemView);
            titleTxt = itemView.findViewById(R.id.appointmentTitle);
            hospitalTxt = itemView.findViewById(R.id.hospitalName);
            dateTxt = itemView.findViewById(R.id.date);
            specialtyTxt = itemView.findViewById(R.id.doctorSpecialty);
            timeTxt = itemView.findViewById(R.id.time);
            appointmentCard = itemView.findViewById(R.id.doctorCard);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    // convenience method for getting data at click position
    Appointment getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}