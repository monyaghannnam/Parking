package com.example.parking;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class LocationService extends Service {
    public static final float MIN_DISTANCE = 150;
    public static void startListening(final Context context, final Word word) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Location locA = new Location("A");
                locA.setLatitude(word.getCor1());
                locA.setLongitude(word.getCor2());
                float distance = location.distanceTo(locA);
//                if (distance < MIN_DISTANCE)
                NotificationUtils.notifyText(context, "YOU ARRIVED",word);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(2, NotificationUtils.getServiceNotification(getApplicationContext(), "START LISTENING"));
        Word word = intent.getParcelableExtra("word");
        startListening(getApplicationContext(), word);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
