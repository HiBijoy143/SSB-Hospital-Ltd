package dia.appmonsters.ssb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import dia.appmonsters.ssb.model.AppointmentInfo;
import dia.appmonsters.ssb.model.DoctorInfo;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ssb.db";
    private static final String TABLE_NAME_DOCTOR = "table_doctor";

    private static final String TABLE_NAME_APPOINTMENT = "table_appointment";

    private static final String TABLE_NAM = "";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DEGREES = "degrees";
    private static final String COLUMN_DEPARTMENT = "department";
    private static final String COLUMN_DESIGNATION = "designation";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_OFFDAY = "offday";
    private static final String COLUMN_IMAGE = "image";

    private static final String COLUMN_PATIENT_NAME = "pat_name";
    private static final String COLUMN_PATIENT_EMAIL = "pat_email";
    private static final String COLUMN_PATIENT_PHONE = "pat_phone";
    private static final String COLUMN_PATIENT_DOCTOR_NAME = "pat_doc_name";
    private static final String COLUMN_PATIENT_DOCTOR_ID = "pat_doc_id";
    private static final String COLUMN_APPO_DATE = "appo_date";
    private static final String COLUMN_APPO_TIME = "appo_time";
    private static final String COLUMN_APPO_MESSAGE = "message";
    private static final String COLUMN_APPO_CONFIRMATION = "confirmation";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    private static final int VERSION = 1;

    private static final String QUERY_CREATE_DOCTOR_TABLE = "CREATE TABLE "
            + TABLE_NAME_DOCTOR
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_DEGREES + " TEXT, "
            + COLUMN_DEPARTMENT + " TEXT, "
            + COLUMN_DESIGNATION + " TEXT, "
            + COLUMN_PHONE + " TEXT, "
            + COLUMN_EMAIL + " TEXT, "
            + COLUMN_OFFDAY + " TEXT, "
            + COLUMN_IMAGE + " BLOB"
            + ");";

    private static final String QUERY_CREATE_APPOINTMENT_TABLE = "CREATE TABLE "
            + TABLE_NAME_APPOINTMENT
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PATIENT_NAME + " TEXT, "
            + COLUMN_PATIENT_EMAIL + " TEXT, "
            + COLUMN_PATIENT_PHONE + " TEXT, "
            + COLUMN_PATIENT_DOCTOR_NAME + " TEXT, "
            + COLUMN_PATIENT_DOCTOR_ID + " INTEGER, "
            + COLUMN_APPO_DATE + " TEXT, "
            + COLUMN_APPO_TIME + " TEXT, "
            + COLUMN_APPO_MESSAGE + " TEXT, "
            + COLUMN_APPO_CONFIRMATION + " TEXT, "
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ");";


    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QUERY_CREATE_DOCTOR_TABLE);
        sqLiteDatabase.execSQL(QUERY_CREATE_APPOINTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DOCTOR);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_APPOINTMENT);
        onCreate(sqLiteDatabase);
    }

    public long insertDoctorInfo(DoctorInfo docModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, docModel.getName());
        contentValues.put(COLUMN_DEGREES, docModel.getDegress());
        contentValues.put(COLUMN_DEPARTMENT, docModel.getDepartment());
        contentValues.put(COLUMN_DESIGNATION, docModel.getDesignation());
        contentValues.put(COLUMN_PHONE, docModel.getPhone());
        contentValues.put(COLUMN_EMAIL, docModel.getEmail());
        contentValues.put(COLUMN_OFFDAY, docModel.getOffday());
        contentValues.put(COLUMN_IMAGE, docModel.getImage());

        long value = db.insert(TABLE_NAME_DOCTOR, null, contentValues);

        db.close();

        return value;
    }

    public List<DoctorInfo> getAllDoctorsList() {
        List<DoctorInfo> allDoctorList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_DOCTOR + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DoctorInfo doctorModel = new DoctorInfo();
                doctorModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                doctorModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                doctorModel.setDegress(cursor.getString(cursor.getColumnIndex(COLUMN_DEGREES)));
                doctorModel.setDepartment(cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTMENT)));
                doctorModel.setDesignation(cursor.getString(cursor.getColumnIndex(COLUMN_DESIGNATION)));
                doctorModel.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                doctorModel.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                doctorModel.setOffday(cursor.getString(cursor.getColumnIndex(COLUMN_OFFDAY)));
                doctorModel.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)));

                allDoctorList.add(doctorModel);
            } while (cursor.moveToNext());
        }

        db.close();

        return allDoctorList;
    }

    public List<DoctorInfo> getDoctorListByDepartment(String dept) {
        List<DoctorInfo> doctorList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_DOCTOR + " WHERE " + COLUMN_DEPARTMENT + " LIKE %" + dept + "%;";

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(query, null);
        Cursor cursor = db.query(TABLE_NAME_DOCTOR, new String[] {},COLUMN_DEPARTMENT+"=?", new String[]{dept}, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DoctorInfo doctorModel = new DoctorInfo();
                doctorModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                doctorModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                doctorModel.setDegress(cursor.getString(cursor.getColumnIndex(COLUMN_DEGREES)));
                doctorModel.setDepartment(cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTMENT)));
                doctorModel.setDesignation(cursor.getString(cursor.getColumnIndex(COLUMN_DESIGNATION)));
                doctorModel.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                doctorModel.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                doctorModel.setOffday(cursor.getString(cursor.getColumnIndex(COLUMN_OFFDAY)));
                doctorModel.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)));

                doctorList.add(doctorModel);
            } while (cursor.moveToNext());
        }

        db.close();

        return doctorList;

    }


    public long insertAppointmentInfo(AppointmentInfo appoModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PATIENT_NAME, appoModel.getPatientName());
        contentValues.put(COLUMN_PATIENT_EMAIL, appoModel.getPatientEmail());
        contentValues.put(COLUMN_PATIENT_PHONE, appoModel.getPatientPhone());
        contentValues.put(COLUMN_PATIENT_DOCTOR_NAME, appoModel.getDoctorName());
        contentValues.put(COLUMN_PATIENT_DOCTOR_ID, appoModel.getDoctorId());
        contentValues.put(COLUMN_APPO_DATE, appoModel.getAppDate());
        contentValues.put(COLUMN_APPO_MESSAGE, appoModel.getMessage());

        long value = db.insert(TABLE_NAME_APPOINTMENT, null, contentValues);
        db.close();

        return value;
    }

    public List<AppointmentInfo> getAllAppointmentInfo() {
        List<AppointmentInfo> appoLists = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME_APPOINTMENT + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AppointmentInfo appoModel = new AppointmentInfo();
                appoModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                appoModel.setPatientName(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_NAME)));
                appoModel.setPatientEmail(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_EMAIL)));
                appoModel.setPatientPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_PHONE)));
                appoModel.setDoctorName(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_DOCTOR_NAME)));
                appoModel.setDoctorId(cursor.getInt(cursor.getColumnIndex(COLUMN_PATIENT_DOCTOR_ID)));
                appoModel.setAppDate(cursor.getString(cursor.getColumnIndex(COLUMN_APPO_DATE)));
                appoModel.setAppTime(cursor.getString(cursor.getColumnIndex(COLUMN_APPO_TIME)));
                appoModel.setMessage(cursor.getString(cursor.getColumnIndex(COLUMN_APPO_MESSAGE)));
                appoModel.setConfirmation(cursor.getString(cursor.getColumnIndex(COLUMN_APPO_CONFIRMATION)));
                appoModel.setTimestamp(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));

                appoLists.add(appoModel);
            } while (cursor.moveToNext());


        }
        db.close();

        return appoLists;
    }


}
