package com.aae.medminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.aae.medminder.MedminderApp;
import com.aae.medminder.models.DaoMaster;
import com.aae.medminder.models.Profile;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.CountQuery;

public class DbOpenHelper extends DaoMaster.OpenHelper {

    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        db.execSQL("INSERT INTO Profile VALUES('', '', '', '', '')");
        db.execSQL("INSERT INTO Doctor(firstName, lastName) VALUES('Please select a doctor,', 'if any.')");
        insertMedicineUnit(db);
        insertTreatmentType(db);
        insertMeasuretmentType(db);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
            case 2:
        }
    }

    private void insertMedicineUnit(SQLiteDatabase db) {
        db.execSQL("INSERT INTO MedicineUnit VALUES('CAP', 'capsule(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('AMP', 'ampule(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('APP', 'applications(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('DRO', 'drop(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('GRA', 'grams(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('INJ', 'injection(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('MLG', 'milligram(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('MLL', 'milliliters(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('PCK', 'packet(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('PLL', 'pill(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('PFF', 'puff(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('SPP', 'suppository(ies)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('TBS', 'tablespoon(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('TES', 'teaspoon(s)', 'default')");
        db.execSQL("INSERT INTO MedicineUnit VALUES('UNT', 'unit(s)', 'default')");
    }

    private void insertTreatmentType(SQLiteDatabase db) {
        db.execSQL("INSERT INTO TreatmentType VALUES('APP', 'Appointment')");
        db.execSQL("INSERT INTO TreatmentType VALUES('REM', 'Remainder')");
        db.execSQL("INSERT INTO TreatmentType VALUES('MED', 'Medicine')");
    }

    private void insertMeasuretmentType(SQLiteDatabase db) {
        db.execSQL("INSERT INTO MeasurementType VALUES('BLG', 'Blood Glucose', 'mg/dl')");
        db.execSQL("INSERT INTO MeasurementType VALUES('HDL', 'HDL Colesterol', 'mg/dl')");
        db.execSQL("INSERT INTO MeasurementType VALUES('LDL', 'LDL Colesterol', 'mg/dl')");
        db.execSQL("INSERT INTO MeasurementType VALUES('BLP', 'Blood Pressure', 'mmHg')");
    }
}
