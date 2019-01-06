package com.example.ashirafzal.databaseexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Institute {

    private Context c;
    private DBHelper dbHelper;
    private SQLiteDatabase ourDB;

    public DB_Institute(Context c){
        this.c = c;
    }

    /****************************
     * Database Name and Version
     ****************************/
    private static final String DATABASE_NAME = "institute";
    private static final int DATABASE_VERSION = 1;

    /****************************
     * Table Students and Fields
     ****************************/
    private static final String TABLE_STUDENT = "student";
    private static final String STUDENT_FNAME = "fname";
    private static final String STUDENT_LNAME = "lname";

    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_STUDENT +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STUDENT_FNAME + " TEXT, " +
            STUDENT_LNAME + " TEXT)";

    /***** ***** ***** Student Table CRUD ***** ***** *****/
    public long newStudent(String FName, String LName){
        ContentValues cv = new ContentValues();

        cv.put(STUDENT_FNAME, FName);
        cv.put(STUDENT_LNAME, LName);

        return ourDB.insert(TABLE_STUDENT, null, cv);
    }

    public String getAllRecords(){
        String result = "";

        String[] columns = {"id", STUDENT_FNAME, STUDENT_LNAME};
        Cursor c = ourDB.rawQuery("SELECT * FROM " + TABLE_STUDENT + " order by id", null);
        int iID = c.getColumnIndex("id");
        int iFName = c.getColumnIndex(STUDENT_FNAME);
        int iLName = c.getColumnIndex(STUDENT_LNAME);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result += "ID: " + c.getString(iID) + " " + c.getString(iFName);
            result += " " + c.getString(iLName) + " \n";
        }

        return result;
    }

    public void updateRecords(int ID, String FName, String LName){
        // update student set fname = 'ABC', lname = 'XYZ' where id = 1
        ourDB.execSQL("UPDATE " + TABLE_STUDENT + " SET " +
                STUDENT_FNAME + " = '" + FName + "', " +
                STUDENT_LNAME + " = '" + LName + "'" +
                "WHERE ID = " + ID);
    }

    public void deleteRecord(int ID){
        // delete from student where id = 1
        ourDB.execSQL("DELETE FROM " + TABLE_STUDENT + " " +
                "WHERE ID = " + ID);
    }

    /****************************
     * Create Database and Tables
     ****************************/
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_STUDENT_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
            onCreate(db);
        }
    }

    /****************************
     * Database opening and closing
     ****************************/
    public DB_Institute open() throws SQLException {
        dbHelper = new DBHelper(c);
        ourDB = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

}