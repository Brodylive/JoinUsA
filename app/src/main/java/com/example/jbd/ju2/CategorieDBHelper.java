package com.example.jbd.ju2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JBD on 13/02/17.
 */

public class CategorieDBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES = "";
    private static final String SQL_DELETE_ENTRIES = "drop table if exists categories";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BookDB.db";

    public CategorieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
