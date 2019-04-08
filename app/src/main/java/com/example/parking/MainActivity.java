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

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            while(cursor.moveToNext()){
                Toast.makeText(getApplicationContext(), "Id"+cursor.getString(0), Toast.LENGTH_LONG).show();

            }//while


        }




       /* txt=(TextView)findViewById(R.id.txt);
        DatabaseHelper db=new DatabaseHelper(this);
        List<Slot>slots=db.getAllSlots();*/

      /*  checkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listdata(showit);
            }
        });*/
     //   for(Slot slot:slots){

          //  String log="ID:"+slot.getId()+"status"+slot.getStatus();
        //     res+=log;
       // }
       // txt.setText(res);
//        DataAccess dataAccess=DataAccess.getInstance(getApplicationContext());
//        dataAccess.open();
//
//        String name=dataAccess.getStatus(0);
//        txt.setText(name);
//        Log.v("monyaaaa","areeeen");
//        dataAccess.close();
////        mDBHelper = new DatabaseHelper(this);
//
//        try {
//            mDBHelper.updateDataBase();
//        } catch (IOException mIOException) {
//            throw new Error("UnableToUpdateDatabase");
//        }
//
//        try {
//            mDb = mDBHelper.getWritableDatabase();
//        } catch (SQLException mSQLException) {
//            throw mSQLException;
//        }
//
////        mDb = mDBHelper.getWritableDatabase();
////        Cursor resultSet = mDb.rawQuery("Select * from User",null);
////        resultSet.moveToFirst();
////        String username = resultSet.getString(1);

////       // mDb.query("User",new String[]{"id" , "username"},null,null,null,null,null);

    }


}
