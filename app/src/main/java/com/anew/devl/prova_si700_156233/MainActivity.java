package com.anew.devl.prova_si700_156233;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<String> messages = new ArrayList<>();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        initFirebase();

    }


    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            messages.add(intent.getStringExtra("message"));
        }
    };

    private void initFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("bibliografia");
        IntentFilter myFilter = new IntentFilter("MyServiceBroadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastReceiver, myFilter);

    }

}
