package com.anew.devl.prova_si700_156233;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<String> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        FirebaseMessaging.getInstance().subscribeToTopic("bibliografia");

        IntentFilter myFilter = new IntentFilter("MyServiceBroadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastReceiver, myFilter);


    }

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {


            messages.add(intent.getStringExtra("message"));


            //ListView list = (ListView) findViewById(R.id.listviewmain);
            //list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, messages));

            String stringMessage = intent.getStringExtra("message");

            Log.d("VAI CARAI", stringMessage);
        }
    };

}
