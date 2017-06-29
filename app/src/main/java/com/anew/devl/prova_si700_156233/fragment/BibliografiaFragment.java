package com.anew.devl.prova_si700_156233.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.anew.devl.prova_si700_156233.R;

public class BibliografiaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bibliografia, container, false);


        Button btnNovaBibliografia = (Button) view.findViewById(R.id.btnNovoBibliografia);
        btnNovaBibliografia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                callNewBibliografia();
            }
        });


        Button btnUpdateBibliogragia = (Button) view.findViewById(R.id.btnAtualizarBibliografia);
        btnUpdateBibliogragia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                callUpdate() ;
            }
        });


        Button btnRemoverBibliografia = (Button) view.findViewById(R.id.btnRemoverBibliografia);
        btnRemoverBibliografia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                callRemover();
            }
        });


        return view;
    }

    private void callNewBibliografia() {

        NovaBibliografiaFragmet fragment = new NovaBibliografiaFragmet();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    private void callUpdate() {
        Busca fragment = new Busca();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();

    }

    private void callRemover() {
        Busca fragment = new Busca();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();


    }

}