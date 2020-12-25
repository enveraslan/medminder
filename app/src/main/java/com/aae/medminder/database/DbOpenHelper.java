package com.aae.medminder.database;

import android.content.ContentValues;
import android.content.Context;

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
    public void onOpen(Database db) {
        super.onOpen(db);


    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        db.execSQL("INSERT INTO Profile VALUES('', '', '', '', '')");
        db.endTransaction();
        insertMedicineUnit(db);

        switch (oldVersion) {
            case 1:
            case 2:
        }
    }

    private void insertMedicineUnit(Database db) {
        db.execSQL("INSERT INTO MedicineUnit VALUE('AMP', 'ampule(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('APP', 'applications(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('CAP', 'capsule(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('DRO', 'drop(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('GRA', 'grams(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('INJ', 'injection(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('MLG', 'milligram(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('MLL', 'milliliters(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('PCK', 'packet(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('PLL', 'pill(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('PFF', 'puff(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('SPP', 'suppository(ies)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('TBS', 'tablespoon(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('TES', 'teaspoon(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('UNT', 'unit(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('VCP', 'vaginal capsule(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('VGI', 'vaginal insert(s)', '')");
        db.endTransaction();
        db.execSQL("INSERT INTO MedicineUnit VALUE('VGT', 'vaginal tablet(s)', '')");
        db.endTransaction();
    }

    private void insertMedicine(Database db) {

    }
}
