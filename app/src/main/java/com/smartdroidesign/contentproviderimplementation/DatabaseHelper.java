package com.smartdroidesign.contentproviderimplementation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    // DB name
    private static final String DATABASE = "MyDatabase";
    // DB version
    private static final int DATABASE_VERSION = 1;

    // DB constants for columns and table-name
    static final String TABLE_VICS = "vics";
    static final String COLUMN_ID = "_ID";
    static final String COLUMN_NAME = "name";

    // Reference for the DB object
    private SQLiteDatabase database;

    // Constructor calling super, and populating the DB
    DatabaseHelper(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating the 'vics' table using the constants COLUMN_ID and COLUMN_NAME
        String SQL = " CREATE TABLE " + TABLE_VICS + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL);";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    // Takes a 'vics' name as parameter (i.e. the name of the table)
    // Inserts values into it
// OLD CODE PREVIOUS CUSTOM CONTENT PROVIDER
//    void addContact(String name) {
//        String SQL = "INSERT INTO " + TABLE_VICS + "(" + COLUMN_NAME + ") VALUES (\"" + name + "\")";
//        database.execSQL(SQL, new String[]{});
//    }
//
//    // Queries the 'vics' table for all the content and returns an array of rows[]
//    // Row[] is a simple class made to store only one row from the 'vics' table
    /*
     * OLD CODE
     *
//    Row[] getContacts() {
//        // We create the select statement
//        String SQL = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + " FROM " + TABLE_VICS;
//        // We execute the statement and get the results to a Cursor
//        Cursor cursor = database.rawQuery(SQL, new String[]{});
//
//        // Retrieve the Cursor position
//        int ct = cursor.getCount();
//        Log.d(TAG, "getContacts: cursor is " + ct);
//        // We create an array of rows
//        Row[] rows = new Row[ct];
//        // We loop through the cursor and populate the array
//        /* ct variable gets the cursor position and updates it at each loop
//         * getContacts: cursor is 0
//         * getContacts: cursor is 1
//         * getContacts: cursor is 2
//         */
//        if (ct > 0) {
//            cursor.moveToFirst();
//            for (int i = 0; i < ct; i++) {
//                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
//                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
//                rows[i] = new Row(id, name);
//                cursor.moveToNext();
//            }
//        }
//        // Close the cursor when done, and close it
//        cursor.close();
//        return rows;
//    }
}
