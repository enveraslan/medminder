package com.aae.medminder;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aae.medminder.models.MeasurementTreatment;
import com.aae.medminder.models.MeasurementTreatmentDao;
import com.aae.medminder.models.MeasurementType;
import com.aae.medminder.models.MeasurementTypeDao;
import com.aae.medminder.models.Medicine;
import com.aae.medminder.models.MedicineDao;
import com.aae.medminder.models.MedicineTreatment;
import com.aae.medminder.models.MedicineTreatmentDao;
import com.aae.medminder.models.MedicineUnit;
import com.aae.medminder.models.MedicineUnitDao;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

public class TreatmentsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TreatmentDetail> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    private static final int TYPE_MEDICINE = 1;
    private static final int TYPE_MEASUREMENT = 2;

    // data is passed into the constructor
    TreatmentsRecyclerViewAdapter(Context context, List<TreatmentDetail> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_MEDICINE : {
                view = mInflater.inflate(R.layout.treatment_row, parent, false);
                return new ViewHolderMedicine(view);
            }
            case TYPE_MEASUREMENT : {
                view = mInflater.inflate(R.layout.treatment_row, parent, false);
                return new ViewHolderMeasurement(view);
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getTreatmentType().equals("MED")) {
            return TYPE_MEDICINE;
        } else {
            return TYPE_MEASUREMENT;
        }
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (getItemViewType(position)){
            case TYPE_MEDICINE : {
                TreatmentDetail detail = mData.get(position);
                MedicineTreatment medicineTreatment = null;
                Medicine medicine = null;
                MedicineUnit medicineUnit = null;
                if (detail.getTreatmentType().equals("MED")) {
                    try {
                        medicineTreatment = MedminderApp.getDaoSession().getMedicineTreatmentDao().queryBuilder()
                                .where(MedicineTreatmentDao.Properties.TreatmentID.eq(detail.getTreatmentID()))
                                .list().get(0);
                        medicine = MedminderApp.getDaoSession().getMedicineDao().queryBuilder()
                                .where(MedicineDao.Properties.MedicineID.eq(medicineTreatment.getMedicineID()))
                                .list().get(0);
                        medicineUnit = MedminderApp.getDaoSession().getMedicineUnitDao().queryBuilder()
                                .where(MedicineUnitDao.Properties.MedicineUnitID.eq(medicine.getMedicineUnitID()))
                               .list().get(0);
                    }catch (IndexOutOfBoundsException ex) {}

                    if(medicineTreatment != null){
                        ((ViewHolderMedicine) viewHolder).imageViewTreatment.setImageResource(R.drawable.ac_pill2);
                        ((ViewHolderMedicine) viewHolder).medicineName.setText(detail.getTreatmentName());
                        ((ViewHolderMedicine) viewHolder).medicineDetail.setText(medicine.getCount().toString() + " "+ medicineUnit.getTitle());
                    }
                }
                break;
            }
            case TYPE_MEASUREMENT : {
                TreatmentDetail detail = mData.get(position);
                MeasurementTreatment measurementTreatment = null;
                MeasurementType measurementType = null;
                if (detail.getTreatmentType().equals("MEA")) {
                    try {
                        measurementTreatment = MedminderApp.getDaoSession().getMeasurementTreatmentDao().queryBuilder()
                                .where(MeasurementTreatmentDao.Properties.TreatmentID.eq(detail.getTreatmentID()))
                                .list().get(0);
                        measurementType = MedminderApp.getDaoSession().getMeasurementTypeDao().queryBuilder()
                                .where(MeasurementTypeDao.Properties.MeasurementTypeID.eq(measurementTreatment.getMeasurementTypeID()))
                                .list().get(0);

                    } catch (IndexOutOfBoundsException ex) {

                    }
                    if(measurementTreatment != null && measurementType != null) {
                        ((ViewHolderMeasurement) viewHolder).imageViewTreatment.setImageResource(R.drawable.ac_glucose_meter);
                        ((ViewHolderMeasurement) viewHolder).measurementName.setText(measurementType.getTitle());
                        ((ViewHolderMeasurement) viewHolder).measurementDetail.setText(measurementTreatment.getTime());
                    }

                }
            }
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolderMedicine extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView medicineName, medicineDetail;
        ImageView imageViewTreatment;
        ConstraintLayout treatmentLayout;

        ViewHolderMedicine(final View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.textViewTreatmentName);
            medicineDetail = itemView.findViewById(R.id.textViewTreatmentSubdetail);
            imageViewTreatment = itemView.findViewById(R.id.imageViewTreatment);
            treatmentLayout = itemView.findViewById(R.id.treatmentLayout);

            treatmentLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    TreatmentDetail info = mData.get(getAdapterPosition());
                    Intent intent = new Intent(context, AddMedicineActivity.class);
                    intent.putExtra("treatmentId",info.getTreatmentID());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public class ViewHolderMeasurement extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView measurementName, measurementDetail;
        ImageView imageViewTreatment;
        ConstraintLayout treatmentLayout;

        ViewHolderMeasurement(final View itemView) {
            super(itemView);
            measurementName = itemView.findViewById(R.id.textViewTreatmentName);
            measurementDetail = itemView.findViewById(R.id.textViewTreatmentSubdetail);
            imageViewTreatment = itemView.findViewById(R.id.imageViewTreatment);
            treatmentLayout = itemView.findViewById(R.id.treatmentLayout);

            treatmentLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    TreatmentDetail info = mData.get(getAdapterPosition());
                    Intent intent = new Intent(context, AddMeasurementActivity.class);
                    intent.putExtra("treatmentId",info.getTreatmentID());
                    context.startActivity(intent);
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