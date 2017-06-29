package com.anew.devl.prova_si700_156233.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.anew.devl.prova_si700_156233.R;
import com.anew.devl.prova_si700_156233.adapter.BibliografiaAdapter;
import com.anew.devl.prova_si700_156233.database.DBHelperBibliografia;
import com.anew.devl.prova_si700_156233.database.SincronizeDatabaseLocalServer;
import com.anew.devl.prova_si700_156233.model.Bibliografia;

import java.util.ArrayList;
import java.util.List;

public class Busca extends Fragment {


    public final static String BIBLIOGRAFIA_BUSCA = "com.anew.devl.prova_si700_156233.fragment.BibliografiaSearchSelected";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_busca, container, false);

        List<Bibliografia> bibliografias = selectBibliografiasLocalDB(getContext());
        final BibliografiaAdapter adapter = new BibliografiaAdapter(view.getContext(), bibliografias);

        ListView listView = (ListView) view.findViewById(R.id.listviewBuscaBibliografia);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callUpdateBibliografia(adapter.getItem(position));
            }
        });

        return view;
    }

    public List<Bibliografia> selectBibliografiasLocalDB(Context context) {

        SincronizeDatabaseLocalServer s = new SincronizeDatabaseLocalServer();
        return s.selectBibliografiasLocalDB(context);
    }

    private void callUpdateBibliografia(Bibliografia bibliografia) {

        ShowBibliografiaFragment fragment = new ShowBibliografiaFragment();
        Bundle args = new Bundle();
        args.putSerializable(BIBLIOGRAFIA_BUSCA, bibliografia);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}