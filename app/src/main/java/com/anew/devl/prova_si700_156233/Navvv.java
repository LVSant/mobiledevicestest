package com.anew.devl.prova_si700_156233;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.anew.devl.prova_si700_156233.database.SyncronizeLocalServer;
import com.anew.devl.prova_si700_156233.fragment.Bibliografia;
import com.anew.devl.prova_si700_156233.fragment.Busca;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Navvv extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navvvv);

        init();
    }

    private void init() {
        initDatabase();
        initFragments();
    }

    private void initDatabase() {
        SyncronizeLocalServer sync = new SyncronizeLocalServer();
        sync.init(getBaseContext());
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        fragment = new Bibliografia();

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_frame, fragment).commit();


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_bibliografia:
                        fragment = new Bibliografia();
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
        throw new UnsupportedOperationException("Implement on return");
    }

}
