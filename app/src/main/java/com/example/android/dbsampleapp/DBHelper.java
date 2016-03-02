package com.example.android.dbsampleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by VenkataManohar on 02/03/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    /*Database name and version number*/
    private static final String DBNAME = "mydb.db";
    private static final int VERSION = 1;

    /*Table name in the database*/
    public static final String TABLE_NAME = "employees";

    /* Column Names of the employee table */
    public final String ID = "_id";
    public final String FIRST_NAME = "firstName";
    public final String LAST_NAME = "lastName";
    public final String ADDRESS = "address";
    public final String SALARY = "salary";
    public final String FULL_NAME = "fullName";

    public SQLiteDatabase myDB;


    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQLQuery = "CREATE TABLE " + TABLE_NAME +
                " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIRST_NAME + " TEXT NOT NULL, " +
                LAST_NAME + " TEXT NOT NULL, " +
                ADDRESS + " TEXT NOT NULL, " +
                SALARY + " REAL NOT NULL " +
                ")";
        db.execSQL(SQLQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void OpenDB(){
        myDB = getWritableDatabase();
    }


    public void CloseDB(){
        if (myDB !=null && myDB.isOpen()){
            myDB.close();
        }
    }

    public long insertTableRow(int id, String fName, String lName, String address, Double salary) {
        ContentValues values = new ContentValues();
        if (id != -1)
            values.put(ID, id);

        values.put(FIRST_NAME, fName);
        values.put(LAST_NAME, lName);
        values.put(ADDRESS, address);
        values.put(SALARY, salary);
        return myDB.insert(TABLE_NAME, null, values);
    }

    public int deleteTableRow(int id) {
        String whereClause = ID + "=" + id;
        return myDB.delete(TABLE_NAME, whereClause, null);
    }

    public Cursor getAllRecords() {
        String[] columns = new String[]{
                ID, FIRST_NAME + "|| ' ' ||" + LAST_NAME + " AS " + FULL_NAME, ADDRESS, SALARY
        };
        Cursor cursor = myDB.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Log.v("Table rows", DatabaseUtils.dumpCursorToString(cursor));
        return cursor;
    }


}
