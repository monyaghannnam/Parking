package com.example.parking.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.app.ActionBar;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.parking.DatabaseHelper;
import com.example.parking.LocationService;
import com.example.parking.R;
import com.example.parking.Sensor;
import com.example.parking.Word;
import com.example.parking.WordAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    WordAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title);

        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                show_nearest_parking();
            }
        });
        show_nearest_parking();
    }


    public void show_nearest_parking() {
        mSwipeRefreshLayout.setRefreshing(true);
        final List<Word> words = new ArrayList<>();
        mAdapter = new WordAdapter(MainActivity.this, words);

        DatabaseReference parkingRef = FirebaseDatabase.getInstance().getReference().child("parking");
        parkingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSwipeRefreshLayout.setRefreshing(false);
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot ds = iterator.next();
                    Word word = ds.getValue(Word.class);
                    word.setId(ds.getKey());
                    getSlots(word);
                    words.add(word);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(mAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                try {
                    startLocationService(words.get(position));
                    openMap(words.get(position).getCor1(), words.get(position).getCor2());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Check the internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startLocationService(Word word) {
//        Word word1 = new Word();
//        word1.setCor1(33);
//        word1.setCor2(33);
        LocationService.startListening(this, word);
    }

    private void openMap(double latitude, double longitude) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f", latitude, longitude, latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    private void getSlots(final Word word) {
        FirebaseDatabase.getInstance().getReference().child("sensors")
                .orderByChild("pid").equalTo(word.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                        int count = 0;
                        while (iterator.hasNext()) {

                            Sensor sensor = iterator.next().getValue(Sensor.class);
                            if (sensor.getStatus() == 0) {
                                count++;
                            }
                        }
                        word.setFree_slot(count);

                        mAdapter.notifyDataSetChanged();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}