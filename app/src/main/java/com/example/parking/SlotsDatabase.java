package com.example.parking;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SlotsDatabase extends SQLiteOpenHelper {
    public static final  String DataBase_Name= "Test.db";
    public static final  String Table_Name1="Slot";
    public static final  String Col_1="ID";
    public static final  String Col_2="Status";
    public static final  String Col_3="Id_floor";



    public SlotsDatabase(Context context) {
        super(context, DataBase_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table_Name1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Status INTEGER,Id_floor INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name1);
        onCreate(db);

    }
}
