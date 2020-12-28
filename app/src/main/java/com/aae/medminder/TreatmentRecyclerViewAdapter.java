package com.aae.medminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aae.medminder.models.MedicineTreatment;
import com.aae.medminder.models.MedicineTreatmentDao;

import java.util.List;

public class TreatmentRecyclerViewAdapter extends RecyclerView.Adapter<TreatmentRecyclerViewAdapter.ViewHolder> {
    private List<TreatmentDetail> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    TreatmentRecyclerViewAdapter(Context context, List<TreatmentDetail> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.medicine_row_card, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TreatmentDetail detail = mData.get(position);

        holder.medicineName.setText(detail.getMedicineName());
        holder.amount.setText(detail.getAmount());
        holder.time.setText(detail.getTime());

        boolean isExpandable = detail.isExpandable();
        holder.buttonLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        holder.doseLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        holder.timeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView medicineName, amount, time;
        LinearLayout medicineNameLayout, buttonLayout;
        RelativeLayout doseLayout, timeLayout;
        ImageView incButton, decButton;
        Button snoozeButton, confirmButton;




        ViewHolder(final View itemView) {
            super(itemView);


            medicineName = itemView.findViewById(R.id.MedicineName);
            amount = itemView.findViewById(R.id.amount);
            time = itemView.findViewById(R.id.time);

            medicineNameLayout = itemView.findViewById(R.id.medicineNameLayout);
            buttonLayout = itemView.findViewById(R.id.buttonLayout);
            doseLayout = itemView.findViewById(R.id.doseLayout);
            timeLayout = itemView.findViewById(R.id.timeLayout);

            incButton = itemView.findViewById(R.id.incImage);
            decButton = itemView.findViewById(R.id.decImage);

            snoozeButton = itemView.findViewById(R.id.buttonMedicineSnooze);
            confirmButton = itemView.findViewById(R.id.buttonMedicineConfirm);

            incButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    TreatmentDetail info = mData.get(getAdapterPosition());
                    info.increaseAmount();
                    amount.setText(info.getAmount());
                }
            });

            decButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    TreatmentDetail info = mData.get(getAdapterPosition());
                    info.decreaseAmount();
                    amount.setText(info.getAmount());
                }
            });

            medicineNameLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    TreatmentDetail info = mData.get(getAdapterPosition());
                    info.setExpandable(!info.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            snoozeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TreatmentDetail info = mData.get(getAdapterPosition());
                    try {
                        MedicineTreatment medicineTreatment = MedminderApp.getDaoSession()
                                .getMedicineTreatmentDao().queryBuilder()
                                .where(MedicineTreatmentDao.Properties.MedicineTreatmentID.eq(info.getMedicineTreatmentID()))
                                .list().get(0);
                        medicineTreatment.setCosumeType("S");
                        MedminderApp.getDaoSession().update(medicineTreatment);
                    } catch (IndexOutOfBoundsException ex) {

                    }
                    mData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mData.size());
                    //itemView.setVisibility(View.INVISIBLE);
                }
            });

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TreatmentDetail info = mData.get(getAdapterPosition());
                    try {
                        MedicineTreatment medicineTreatment = MedminderApp.getDaoSession()
                                .getMedicineTreatmentDao().queryBuilder()
                                .where(MedicineTreatmentDao.Properties.MedicineTreatmentID.eq(info.getMedicineTreatmentID()))
                                .list().get(0);
                        medicineTreatment.setCosumeType("S");
                        MedminderApp.getDaoSession().update(medicineTreatment);
                    } catch (IndexOutOfBoundsException ex) {

                    }
                    mData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mData.size());
                    //itemView.setVisibility(View.INVISIBLE);
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    TreatmentDetail getItem(int id) {
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