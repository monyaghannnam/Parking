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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txt;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=(TextView)findViewById(R.id.txt);
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
        Cursor cursor=mDBHelper.allData();

        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();

        }
        else{
            int c=0;
            String s="";
            while(cursor.moveToNext()){
                c++;
                 s+="Id"+cursor.getString(0)+" ";
                Toast.makeText(getApplicationContext(), "Id"+cursor.getString(0), Toast.LENGTH_LONG).show();

            }//while
            txt.setText(Integer.toString(c));

        }






    }


}
