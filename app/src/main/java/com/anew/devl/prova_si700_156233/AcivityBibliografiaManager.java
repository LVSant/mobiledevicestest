package com.anew.devl.prova_si700_156233;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.anew.devl.prova_si700_156233.database.SincronizeDatabaseLocalServer;
import com.anew.devl.prova_si700_156233.fragment.BibliografiaFragment;
import com.anew.devl.prova_si700_156233.fragment.Busca;
import com.google.firebase.messaging.FirebaseMessaging;

public class AcivityBibliografiaManager extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliografia_manager);
        setTitle("Bibliografia Manager");

        init();
    }

    private void init() {
        //initDatabase();
        initFirebase();
        initFragments();
    }

    private void initDatabase() {
        SincronizeDatabaseLocalServer sync = new SincronizeDatabaseLocalServer();
        sync.init(getBaseContext());
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        fragment = new BibliografiaFragment();

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_frame, fragment).commit();


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_bibliografia:
                        fragment = new BibliografiaFragment();
                        break;
                    case R.id.nav_search:
                        fragment = new Busca();
                        break;

                }

                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment).commit();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        View viewById = findViewById(R.id.nav_bibliografia);
        viewById.performClick();
        super.onBackPressed();
    }

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("FB MESSAGE", intent.getStringExtra("message"));
            initDatabase();
        }
    };

    private void initFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("bibliografia");
        IntentFilter myFilter = new IntentFilter("MyServiceBroadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastReceiver, myFilter);

    }

}
