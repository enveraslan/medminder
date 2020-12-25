package com.aae.medminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import com.aae.medminder.models.Doctor;
import com.aae.medminder.models.Profile;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "medminder2";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Profile");
        db.execSQL("DROP TABLE IF EXISTS Doctor");

        updateDatabase(db, 0, DB_VERSION);

    }


    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(createProfile());
        db.execSQL(createDoctor());
        Profile profile = new Profile();
        insertProfile(db, profile);
    }



    // Profile CRUD
    public String createProfile() {
        return "CREATE TABLE Profile(" +
                "firstName TEXT, " +
                "lastName TEXT, " +
                "birthDate TEXT, " +
                "address TEXT, " +
                "zipCode TEXT)";
    }

    public static void insertProfile(SQLiteDatabase db, Profile profile) {
        ContentValues profileValues = new ContentValues();
        profileValues.put("firstName", profile.getFirstName());
        profileValues.put("lastName", profile.getLastName());
        profileValues.put("birthDate", profile.getBirthDateString());
        profileValues.put("address", profile.getAddress());
        profileValues.put("zipCode", profile.getZipCode());
        db.insert("Profile", null, profileValues);
    }

    public static Cursor selectProfile(SQLiteDatabase db) {
        String table = "Profile";
        String[] columns = {"firstName", "lastName", "birthDate", "address", "zipCode"};
        return db.query(table, columns, null, null, null, null, null, null);

    }

    public static void updateProfile(SQLiteDatabase db, Profile profile) {
        ContentValues profileValues = new ContentValues();
        profileValues.put("firstName", profile.getFirstName());
        profileValues.put("lastName", profile.getLastName());
        profileValues.put("birthDate", profile.getBirthDateString());
        profileValues.put("address", profile.getAddress());
        profileValues.put("zipCode", profile.getZipCode());
        db.update("Profile", profileValues, null, null);
    }
    // End Profile CRUD

    // Doctor CRUD
    public String createDoctor() {
        return "CREATE TABLE Doctor(" +
                "doctorID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstName TEXT, " +
                "lastName TEXT, " +
                "phoneNumber TEXT, " +
                "email TEXT, " +
                "location TEXT)";
    }

    public static void insertDoctor(SQLiteDatabase db, Doctor doctor) {
        ContentValues doctorValues = new ContentValues();
        doctorValues.put("firstName", doctor.getFirstName());
        doctorValues.put("lastName", doctor.getLastName());
        doctorValues.put("phoneNumber", doctor.getPhoneNumber());
        doctorValues.put("email", doctor.getEmail());
        doctorValues.put("location", doctor.getLocation());
        db.insert("Doctor", null, doctorValues);
    }

    public static Cursor selectDoctor(SQLiteDatabase db) {
        String table = "Doctor";
        String[] columns = {"doctorID", "firstName", "lastName", "phoneNumber", "email", "location"};
        return db.query(table, columns, null, null, null, null, null, null);

    }
    // End Doctor CRUD

    public String createPerson() {
        return "CREATE TABLE Person(" +
                "personID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName TEXT," +
                "lastName TEXT)";
    }

    public String createPatient() {
        return "Create TABLE Patient(" +
                "personID INTEGER PRIMARY KEY, " +
                "birthDate TEXT, " +
                "address TEXT, " +
                "zipCode TEXT," +
                " FOREIGN KEY(personID) REFERENCES Person(personID))";
    }

    public static void insertPerson(SQLiteDatabase db, String firstName, String lastName) {
        ContentValues personValues = new ContentValues();
        personValues.put("firstName", firstName);
        personValues.put("lastName", lastName);
        db.insert("Person", null, personValues);
    }

    public static void insertPatient(SQLiteDatabase db, int personID, String birthdate, String address, String zipCode) {
        ContentValues patientValues = new ContentValues();
        Cursor cursor = selectPerson(db);
        cursor.moveToFirst();
        patientValues.put("personID", cursor.getString(0));
        patientValues.put("birthDate", birthdate);
        patientValues.put("address", address);
        patientValues.put("zipCode", zipCode);
        db.insert("Patient", null, patientValues);
    }

    public static void updatePatient(SQLiteDatabase db, int personID, String firstName, String lastName,
                                     String birthDate, String address, String zipCode) {
        updatePerson(db, personID, firstName, lastName);
        ContentValues patientValues = new ContentValues();
        patientValues.put("birthDate", birthDate);
        patientValues.put("address", address);
        patientValues.put("zipCode", zipCode);
        db.update("Patient", patientValues, "personID=?",
                new String[]{String.valueOf(personID)});


    }

    public static void updatePerson(SQLiteDatabase db, int personID, String firstName, String lastName) {
        ContentValues personValues = new ContentValues();
        personValues.put("firstName", firstName);
        personValues.put("lastName", lastName);
        db.update("Person", personValues, "personID=?",
                new String[]{String.valueOf(personID)});
    }

    public static Cursor selectPerson(SQLiteDatabase db, String firstName, String lastName) {
        String table = "Person";
        String[] columns = {"personID", "firstName", "lastName"};
        String selection = "firstName=? AND lastName=?";
        String[] selectionArgs = {firstName, lastName};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public static Cursor selectPerson(SQLiteDatabase db) {
        String table = "Person";
        String[] columns = {"personID", "firstName", "lastName"};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public static Cursor selectPatient(SQLiteDatabase db, int personID) {
        return db.rawQuery(
                "SELECT p.personID, p.firstName, p.lastName, pa.birthDate, pa.address, pa.zipcode " +
                "FROM Person p, Patient as pa " +
                "WHERE pa.personID = p.personID and p.personID=?", new String[]{String.valueOf(personID)});
    }
}
