package com.example.parking;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private TextView txt;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.txt);
        mDBHelper = new DatabaseHelper(this);


        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }



        Cursor cursor = mDBHelper.total_free_slot();

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();

        } else {
            int c = 0;
            String s = "";
            while (cursor.moveToNext()) {
                c++;
                //s+="Id"+cursor.getString(0)+" ";
                //Toast.makeText(getApplicationContext(), "Floor_Id"+cursor.getString(2), Toast.LENGTH_LONG).show();

            }//while
            txt.setText("Free Slot: " + Integer.toString(c));

        }
        /////////// Floor_Free_slot////////////////
        HashMap<Integer, Integer> map = new HashMap<>();

        Cursor cursor1 = mDBHelper.floor_free_slot();

        if (cursor1.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();

        } else {
            int c = 0;
            String s = "";
            while (cursor1.moveToNext()) {
                map.put(cursor1.getInt(0),0);
            }//while


            Cursor cursor2 = mDBHelper.total_free_slot();
            if (cursor2.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();

            } else {

                while (cursor2.moveToNext()) {
                    int key=cursor2.getInt(2);
                    map.put(key, map.get(key) + 1);
                }



                }//while
            Set keys = map.keySet();
            for (Iterator i = keys.iterator(); i.hasNext(); ) {
                int key = (int) i.next();
                int value = (int) map.get(key);
                Toast.makeText(getApplicationContext(), key +" = "+value, Toast.LENGTH_LONG).show();
                txt.setText(key + " = " + value);
            }




        }//else


    }
}
