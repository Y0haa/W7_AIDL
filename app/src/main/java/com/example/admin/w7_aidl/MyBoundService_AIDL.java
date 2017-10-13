package com.example.admin.w7_aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import java.util.StringTokenizer;

/**
 * Created by Admin on 10/13/2017.
 */

public class MyBoundService_AIDL extends Service {

    private static final  String TAG = "MyBoundServiceTag";
   // IBinder iBinder = new MyBinder();
    List<String> randData = new ArrayList<>();
    public MyBoundService_AIDL() {

    }

    public class  MyBinder extends Binder {
        public MyBoundService_AIDL getService (){
            return MyBoundService_AIDL.this;
        }
    }


    private final IMyAidlInterface.Stub iBinder = new IMyAidlInterface.Stub() {
        @Override
        public List<String> listFiles() throws RemoteException {
            initData(10);
            List<String> toSend = getRandData();
            return toSend;
        }

        @Override
        public void exit() throws RemoteException {
        stopSelf();
        }
    };





    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG, "onBind: "+ intent.getStringExtra("data"));
//
        return iBinder;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    public void  initData(int initializeStr){
        for(int i=0; i<initializeStr; i++){
            randData.add(String.valueOf(new Random().nextInt(30)));

        }
    }
    public List<String> getRandData(){
        return randData;
    }
}
