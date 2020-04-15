package com.example.android.bbtracker;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class StickyServices extends Service {
    private SharedPreferences mPrefs;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        FirebaseAuth.getInstance().signOut();
        Log.d("StickyService", "onTaskRemoved: Signout");
    }
}
