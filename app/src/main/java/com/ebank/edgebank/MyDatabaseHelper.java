package com.emreguven.burmetembank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME="Data.db";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="data";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_ACCNO="acc_no";
    public static final String COLUMN_BALANCE="balance";
    public static final String COLUMN_FIRSTNAME="firstname";
    public static final String COLUMN_LASTNAME="lastname";
    public static final String COLUMN_DEBT="debt";
    public static final String COLUMN_PLACE="place";
    public static final String COLUMN_PHONE="phone";
    public static final String COLUMN_PASSWORD="password";
    public static final String COLUMN_GENDER="gender";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+ TABLE_NAME +
                "(" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACCNO + " INTEGER, " +
                COLUMN_BALANCE + " DECIMAL, " +
                COLUMN_FIRSTNAME + " TEXT, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_DEBT + " DECIMAL, " +
                COLUMN_PLACE + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_GENDER + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
