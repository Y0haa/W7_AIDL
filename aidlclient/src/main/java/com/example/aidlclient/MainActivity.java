package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IMyAidlInterface iService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent().setComponent(new ComponentName(
                "com.example.admin.w7_aidl",
                "com.example.admin.w7_aidl.MyBoundService_AIDL"));
        startService(serviceIntent);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iService = IMyAidlInterface.Stub.asInterface(iBinder);
            performListing();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iService = null;
        }
    };

    private void performListing() {
        try {
            List<String> randData = iService.listFiles();
            for (String number: randData) {
                Log.d("New: ", "performListing: " + number);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            iService.exit();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }




}
