package com.aae.medminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.content.Context;
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
import com.aae.medminder.notification.NotificationScheduler;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

public class MainTreatmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TreatmentDetail> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    private static int TYPE_MEDICINE = 1;
    private static int TYPE_MEASUREMENT = 2;

    // data is passed into the constructor
    MainTreatmentRecyclerViewAdapter(Context context, List<TreatmentDetail> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_MEDICINE){
            view = mInflater.inflate(R.layout.medicine_row_card, parent, false);
            return new ViewHolderMedicine(view);
        }else {
            view = mInflater.inflate(R.layout.measurement_row_card, parent, false);
            return new ViewHolderMeasurement(view);
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

        if(getItemViewType(position) == TYPE_MEDICINE) {
            TreatmentDetail detail = mData.get(position);
            MedicineTreatment medicineTreatment;
            if (detail.getTreatmentType().equals("MED")) {
                try {

                    medicineTreatment = MedminderApp.getDaoSession().getMedicineTreatmentDao().queryBuilder()
                            .where(MedicineTreatmentDao.Properties.MedicineTreatmentID.eq(detail.getTreatmentID()))
                            .list().get(0);
                    Medicine medicine = MedminderApp.getDaoSession().getMedicineDao().queryBuilder()
                            .where(MedicineDao.Properties.MedicineID.eq(medicineTreatment.getMedicineID()))
                            .list().get(0);
                    MedicineUnit medicineUnit = MedminderApp.getDaoSession().getMedicineUnitDao().queryBuilder()
                            .where(MedicineUnitDao.Properties.MedicineUnitID.eq(medicine.getMedicineUnitID()))
                            .list().get(0);
                    ((ViewHolderMedicine) viewHolder).medicineUnit.setText(medicineUnit.getTitle());

                }catch (IndexOutOfBoundsException ex) {}

                ((ViewHolderMedicine) viewHolder).medicineName.setText(detail.getTreatmentName());
                ((ViewHolderMedicine) viewHolder).amount.setText(detail.getAmount());
                ((ViewHolderMedicine) viewHolder).time.setText(detail.getTime());


                boolean isExpandable = detail.isExpandable();
                ((ViewHolderMedicine) viewHolder).buttonLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
                ((ViewHolderMedicine) viewHolder).doseLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
                ((ViewHolderMedicine) viewHolder).timeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
            }
        }

        if(getItemViewType(position) == TYPE_MEASUREMENT) {
            TreatmentDetail detail = mData.get(position);
            MeasurementTreatment measurementTreatment;
            if (detail.getTreatmentType().equals("MEA")) {
                try {
                    measurementTreatment = MedminderApp.getDaoSession().getMeasurementTreatmentDao().queryBuilder()
                            .where(MeasurementTreatmentDao.Properties.MeasurementTreatmentID.eq(detail.getTreatmentID()))
                            .list().get(0);
                    MeasurementType measurementType = MedminderApp.getDaoSession().getMeasurementTypeDao().queryBuilder()
                            .where(MeasurementTypeDao.Properties.MeasurementTypeID.eq(measurementTreatment.getMeasurementTypeID()))
                            .list().get(0);
                    ((ViewHolderMeasurement) viewHolder).measurementUnit.setText(measurementType.getUnit());
                } catch (IndexOutOfBoundsException ex) {

                }

                ((ViewHolderMeasurement) viewHolder).measurementName.setText(detail.getTreatmentName());
                ((ViewHolderMeasurement) viewHolder).editTextMeasurementValue.setText(detail.getAmount());
                ((ViewHolderMeasurement) viewHolder).time.setText(detail.getTime());

                boolean isExpandable = detail.isExpandable();
                ((ViewHolderMeasurement) viewHolder).buttonLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
                ((ViewHolderMeasurement) viewHolder).valueLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
                ((ViewHolderMeasurement) viewHolder).timeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
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

        TextView medicineName, amount, time, medicineUnit;
        LinearLayout medicineNameLayout, buttonLayout;
        RelativeLayout doseLayout, timeLayout;
        ImageView incButton, decButton;
        Button snoozeButton, confirmButton;

        ViewHolderMedicine(final View itemView) {
            super(itemView);

            medicineName = itemView.findViewById(R.id.MedicineName);
            amount = itemView.findViewById(R.id.amount);
            time = itemView.findViewById(R.id.time);
            medicineUnit = itemView.findViewById(R.id.textViewMedicineUnit);

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
                                .where(MedicineTreatmentDao.Properties.MedicineTreatmentID.eq(info.getTreatmentID()))
                                .list().get(0);
                        medicineTreatment.setCosumeType("S");
                        MedminderApp.getDaoSession().update(medicineTreatment);
                    } catch (IndexOutOfBoundsException ex) {

                    }
                    mData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mData.size());
                }
            });

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TreatmentDetail info = mData.get(getAdapterPosition());
                    try {
                        MedicineTreatment medicineTreatment = MedminderApp.getDaoSession()
                                .getMedicineTreatmentDao().queryBuilder()
                                .where(MedicineTreatmentDao.Properties.MedicineTreatmentID.eq(info.getTreatmentID()))
                                .list().get(0);
                        medicineTreatment.setCosumeType("C");
                        medicineTreatment.setConsumedDosage(Long.valueOf(amount.getText().toString()));
                        MedminderApp.getDaoSession().update(medicineTreatment);


                        Medicine medicine = MedminderApp.getDaoSession().getMedicineDao().queryBuilder()
                                .where(MedicineDao.Properties.MedicineID.eq(medicineTreatment.getMedicineID()))
                                .list().get(0);
                        medicine.setCount(medicine.getCount() - Long.valueOf(amount.getText().toString()));

                        if(medicine.getCount() < 5){
                            Notification notification = NotificationScheduler.createNotification(itemView.getContext().getApplicationContext(),
                                    "Medminder",
                                    "You're running low on "+medicine.getName(),
                                    R.drawable.ac_count_warning_small,
                                    R.drawable.ac_count_warning_big,
                                    MainActivity.class);

                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.HOUR, 2);
                            NotificationScheduler.scheduleRepeatingNotification(itemView.getContext().getApplicationContext(),
                                    notification,
                                    2000 + medicineTreatment.getTreatmentID().intValue(),
                                    calendar,
                                    AlarmManager.INTERVAL_DAY);
                        }

                        MedminderApp.getDaoSession().update(medicine);
                    } catch (IndexOutOfBoundsException ex) {

                    }
                    mData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mData.size());
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public class ViewHolderMeasurement extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView measurementName, time, measurementUnit;
        EditText editTextMeasurementValue;
        LinearLayout measurementLayout, buttonLayout, valueLayout;
        RelativeLayout timeLayout;
        Button snoozeButton, confirmButton;

        ViewHolderMeasurement(final View itemView) {
            super(itemView);

            measurementLayout = itemView.findViewById(R.id.measurementLayout);
            measurementName = itemView.findViewById(R.id.measurementName);
            measurementUnit = itemView.findViewById(R.id.textViewMeasurementUnit);
            editTextMeasurementValue = itemView.findViewById(R.id.editTextMeasurementValue);
            time = itemView.findViewById(R.id.textViewMeasurementTime);
            buttonLayout = itemView.findViewById(R.id.buttonLayout);
            valueLayout = itemView.findViewById(R.id.valueLayout);
            timeLayout = itemView.findViewById(R.id.measurementTimeLayout);
            snoozeButton = itemView.findViewById(R.id.buttonMeasurementSnooze);
            confirmButton = itemView.findViewById(R.id.buttonMeasurementConfirm);


            measurementLayout.setOnClickListener(new View.OnClickListener(){

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
                        MeasurementTreatment measurementTreatment = MedminderApp.getDaoSession()
                                .getMeasurementTreatmentDao().queryBuilder()
                                .where(MeasurementTreatmentDao.Properties.MeasurementTreatmentID.eq(info.getTreatmentID()))
                                .list().get(0);
                        measurementTreatment.setMeasured("S");
                        MedminderApp.getDaoSession().update(measurementTreatment);
                    } catch (IndexOutOfBoundsException ex) {

                    }
                    mData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mData.size());
                }
            });

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TreatmentDetail info = mData.get(getAdapterPosition());
                    try {
                        MeasurementTreatment measurementTreatment = MedminderApp.getDaoSession()
                                .getMeasurementTreatmentDao().queryBuilder()
                                .where(MeasurementTreatmentDao.Properties.MeasurementTreatmentID.eq(info.getTreatmentID()))
                                .list().get(0);
                        measurementTreatment.setMeasured("M");
                        measurementTreatment.setValue(Long.valueOf(editTextMeasurementValue.getText().toString()));
                        MedminderApp.getDaoSession().update(measurementTreatment);

                    } catch (IndexOutOfBoundsException ex) {

                    }
                    mData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mData.size());
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