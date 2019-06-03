package com.example.parking;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    WordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title);

        btn = (Button) findViewById(R.id.btn);
        mDBHelper = new DatabaseHelper(this);


        /////////////////////////DataBase///////////////////////////////////
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


/*
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

            Cursor cursor3 = mDBHelper.parking_location();
            if (cursor3.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();

            } else {

                while (cursor3.moveToNext()) {
                    float key=cursor3.getFloat(2);
                    Toast.makeText(this, key+"", Toast.LENGTH_LONG).show();

                }



            }//while



        }//else

*/
    }


    public void show_nearest_parking(View view) {
        String name = "";
        float cor1 = 0;
        float cor2 = 0;
        ///////////parking database////////////////////
        Cursor cursor3 = mDBHelper.parking_location();
        if (cursor3.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();

        } else {

            while (cursor3.moveToNext()) {
                name = cursor3.getString(1);
                cor1 = cursor3.getFloat(2);
                cor2 = cursor3.getFloat(3);
            }
        }//while


        final List<Word> words = new ArrayList<>();
        mAdapter = new WordAdapter(MainActivity.this, words);

        DatabaseReference parkingRef = FirebaseDatabase.getInstance().getReference().child("parking");
        parkingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot ds = iterator.next();
                    Word word = ds.getValue(Word.class);
                    word.id = ds.getKey();
                    getSlots(word);
                    words.add(word);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter);


        /////////////////////////ListViewClicklistener////////////////////////////////
        final String url = "https://www.google.com/maps/place/%D9%85%D9%88%D9%82%D9%81+%D8%A7%D9%84%D8%A7%D8%AF%D9%87%D9%85%D9%8A+%D9%84%D9%84%D8%B3%D9%8A%D8%A7%D8%B1%D8%A7%D8%AA+Adhami+Parking%E2%80%AD/@31.9039614,35.2062568,17z/data=!4m12!1m6!3m5!1s0x151d2aae1ce7e7e9:0x81af726d0de5160f!2z2YXZiNmC2YEg2KfZhNin2K_Zh9mF2Yog2YTZhNiz2YrYp9ix2KfYqiBBZGhhbWkgUGFya2luZw!8m2!3d31.9039569!4d35.2040681!3m4!1s0x151d2aae1ce7e7e9:0x81af726d0de5160f!8m2!3d31.9039569!4d35.2040681";
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                /////////////////////////Map///////////////////////////////////

                try {
//NotificationUtils.notifyText(MainActivity.this, "sdfsdf", words.get(position));
                    Intent intent = new Intent(MainActivity.this, LocationService.class);
                    intent.putExtra("word",words.get(position));
                    startForegroundService(intent);
                    openMap(words.get(position).getCor1(), words.get(position).getCor2());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Check the internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void openMap(double latitude, double longitude) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f", latitude, longitude, latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    private void getSlots(final Word word) {
        FirebaseDatabase.getInstance().getReference().child("sensors")
                .orderByChild("pid").equalTo(word.id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        int count = 0;
                        while (iterator.hasNext()) {

                            Sensor sensor = iterator.next().getValue(Sensor.class);
                            if (sensor.status == 0) {
                                count++;
                            }
                        }
                        word.free_slot = count;

                        mAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}