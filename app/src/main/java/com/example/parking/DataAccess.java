package com.example.parking;

import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.*;
import android.util.Log;

public class DataAccess {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase database;
    private static DataAccess instance;

    Cursor c = null;

    private DataAccess(Context context) {
        this.sqLiteOpenHelper = new DatabaseHelper(context);

    }


    public static DataAccess getInstance(Context context) {

        if (instance == null) {
            instance = new DataAccess(context);
        }
        return instance;

    }

    public void open() {

        this.database = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() {

        if (database != null) {
            this.database.close();
        }
    }

    public String getStatus(int id) {


        c = database.rawQuery("select Name from Floor where Id = '" + id + "'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        if (c != null)
            c.moveToFirst(); // this is part of cursor initialization
            while (c.moveToNext()) {

                String name = c.getString(1);
                buffer.append("" + name);
            }

            return buffer.toString();
        }





}