package com.example.parking;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class LocationService extends IntentService {
    public static final float MIN_DISTANCE = 150;

    private static final long INTERVAL = 1000 * 5;
    private static final long FASTEST_INTERVAL = 1000 * 3;
    public static final String EXTRA_WORD = "word";

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Word mWord;

    public LocationService() {
        super("service");
    }

    public static void startListening(Context context, Word word) {
        Intent intent = new Intent(context, LocationService.class);
        intent.putExtra(EXTRA_WORD, word);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                Location location = !locationResult.getLocations().isEmpty() ? locationResult.getLocations().get(0) : null;
                if (location != null) {
                    Location locationA = new Location("A");
                    locationA.setLatitude(mWord.getCor1());
                    locationA.setLongitude(mWord.getCor2());

                    float distance = location.distanceTo(locationA);
                    if (distance <= MIN_DISTANCE) {
                        //arrived
                        Log.d("tag", "distance <= MIN_DISTANCE, " + distance);
                        NotificationUtils.notifyText(getApplicationContext(), "Arrived", mWord);

                        //Stop service when just arrived
                        stopSelf();
                    }
                }
            }
        };

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient = new FusedLocationProviderClient(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Word word = intent.getParcelableExtra(EXTRA_WORD);
        if (word != null)
            startListening(word);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void startListening(Word word) {
        startForeground(2, NotificationUtils.getServiceNotification(getApplicationContext(), "START LISTENING"));
        mWord = word;
        Log.d("tag", "startLocationUpdates");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.d("tag", "start sending location updates");
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
