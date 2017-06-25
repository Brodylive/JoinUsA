package com.example.jbd.ju2.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jbd.ju2.LocalSQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by JBD on 1/12/16.
 */

public class Categorie {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categorie(int id, String name){
        this.id = id;
        this.name = name;
    }


    public static ArrayList<Categorie> getCategorieList(Context context){
        ArrayList<Categorie> listCategorie = new ArrayList<>();

        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(true, "Categorie", new String[]{"id", "name"}, null, null, null, null, null, null);

        while(cursor.moveToNext()){
            listCategorie.add(new Categorie(cursor));
        }
        cursor.close();
        db.close();

        return listCategorie;
    }

    public static Categorie getCategorie(Context context, int id){
        Categorie categorie = null;

        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String where = "id = " + String.valueOf(id);
        Cursor cursor = db.query(true, "Categorie", new String[]{"id", "name"}, where, null, null, null, null, null);

        if(cursor.moveToFirst())
            categorie = new Categorie(cursor);

        cursor.close();
        db.close();

        return categorie;

    }

    private Categorie(Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex("id"));
        name = cursor.getString(cursor.getColumnIndex("name"));
    }
}
