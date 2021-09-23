package com.example.serviceproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("FFFF","YEAH");
        Toast.makeText(context,"Yeah",Toast.LENGTH_SHORT).show();
    }
}
