package com.anew.devl.prova_si700_156233.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anew.devl.prova_si700_156233.R;

/**
 * Created by devl on 6/28/17.
 */

public class UpdateBibliografiaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_bibliografia, container, false);




        Button btn = (Button) view.findViewById(R.id.btnNovoBibliografia);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
           //     callNewBibliografia();
            }
        });


        return view;
    }
}
