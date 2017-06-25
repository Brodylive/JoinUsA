package com.example.jbd.ju2;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

/**
 * Created by jenniferbrody on 2/03/17.
 */

public class LocalSQLiteOpenHelper extends SQLiteOpenHelper {
    static String DB_NAME = "joinus.db";
    static int DB_VERSION = 3;

    private static final String LOGCAT = null;

    public LocalSQLiteOpenHelper(Context context){

        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOGCAT,"Created ICI");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOGCAT,"ICI");
        String sqlFilTable =    "CREATE TABLE CATEGORIE(id INTEGER PRIMARY KEY, name TEXT);"+
                                "CREATE TABLE EVENT(id INTEGER PRIMARY KEY, title TEXT, description TEXT, address TEXT, urlFacebook TEXT, date INTEGER, time INTEGER, creator TEXT);" +
                                "CREATE TABLE TAG(id INTEGER PRIMARY KEY, name TEXT);" +
                                "CREATE TABLE USER(id INTEGER PRIMARY KEY, username TEXT, firstname TEXT, lastname TEXT, birthdate INTEGER, lastLoc STRING);" +
                                "INSERT INTO CATEGORIE (id, name) VALUES(1, 'truc');";
        sqLiteDatabase.execSQL(sqlFilTable);
        Log.d(LOGCAT,"Students Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(LOGCAT,"ICI UPGRADE");
        String query;
        query = "DROP TABLE IF EXISTS CATEGORIE; DROP TABLE IF EXISTS EVENT; DROP TABLE IF EXISTS TAG; DROP TABLE IF EXISTS USER;";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
}
