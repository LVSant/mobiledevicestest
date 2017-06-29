package com.anew.devl.prova_si700_156233.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anew.devl.prova_si700_156233.R;
import com.anew.devl.prova_si700_156233.model.Bibliografia;
import com.squareup.picasso.Picasso;

import static com.anew.devl.prova_si700_156233.fragment.Busca.BIBLIOGRAFIA_BUSCA;

/**
 * Created by devl on 6/28/17.
 */

public class ShowBibliografiaFragment extends Fragment {
    Bibliografia bibliografia;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_bibliografia, container, false);

        Bundle arguments = getArguments();
        bibliografia = (Bibliografia) arguments.get(BIBLIOGRAFIA_BUSCA);

        TextView autorText = (TextView) view.findViewById(R.id.textAutor);
        autorText.setText(bibliografia.getAutor());

        TextView livroText = (TextView) view.findViewById(R.id.textLivro);
        livroText.setText(bibliografia.getTituloLivro());

        TextView disciplinaText = (TextView) view.findViewById(R.id.textDisciplina);
        disciplinaText.setText(bibliografia.getNomeDisciplina());

        TextView cursoText = (TextView) view.findViewById(R.id.textCurso);
        cursoText.setText(bibliografia.getCurso());

        String bookImage = bibliografia.getImageLivro();

        Log.d("THUMBBAIL LOADED", bookImage);
        //load album cover using Picasso! take a look at https://github.com/square/picasso
        Picasso.with(getContext())
                .load(bookImage)
                .placeholder(R.drawable.ic_loading)
                .into((ImageView) view.findViewById(R.id.livroThumbnail));

        Button btnUpdate = (Button) view.findViewById(R.id.btnUpdateShowBibliografia);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateBibliografiaFragment fragment = new UpdateBibliografiaFragment();
                Bundle args = new Bundle();
                args.putSerializable(BIBLIOGRAFIA_BUSCA, bibliografia);
                fragment.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();
            }
        });


        return view;
    }


}
