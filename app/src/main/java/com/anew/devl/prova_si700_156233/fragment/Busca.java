package com.anew.devl.prova_si700_156233.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anew.devl.prova_si700_156233.R;
import com.anew.devl.prova_si700_156233.adapter.BibliografiaAdapter;
import com.anew.devl.prova_si700_156233.database.DBHelperBibliografia;
import com.anew.devl.prova_si700_156233.model.Bibliografia;

import java.util.ArrayList;
import java.util.List;

public class Busca extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_busca, container, false);


        BibliografiaAdapter adapter = new BibliografiaAdapter(view.getContext(), selectBibliografiasLocalDB(getContext()));

        ListView listView = (ListView) view.findViewById(R.id.listviewBuscaBibliografia);
        listView.setAdapter(adapter);

        return view;
    }

    private List<Bibliografia> selectBibliografiasLocalDB(Context context) {

        List<Bibliografia> bibliografias = new ArrayList<>();
        DBHelperBibliografia helper = new DBHelperBibliografia(context);
        SQLiteDatabase db = helper.getReadableDatabase();


        String[] projection = {

                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_ID_LIVRO,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_ID_DISCIPLINA,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_ID_DISCIPLINA,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_TITULO_LIVRO,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_AUTOR,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_CURSO,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_DISCIPLINA
        };


        String sortByAdd =
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_CURSO + " ASC ";

        Cursor c = db.query(
                DBHelperBibliografia.DBHelperBibliografiaColumns.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortByAdd                                 // The sort order
        );

        if (c.moveToFirst()) {
            do {


                long idLivro = c.getLong(c.getColumnIndexOrThrow("idLivro"));
                long idDiscplina = c.getLong(c.getColumnIndexOrThrow("idDisciplina"));

                String disciplina = c.getString(c.getColumnIndexOrThrow(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_DISCIPLINA));
                String curso = c.getString(c.getColumnIndexOrThrow(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_CURSO));
                String autor = c.getString(c.getColumnIndexOrThrow(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_AUTOR));
                String livro = c.getString(c.getColumnIndexOrThrow(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_TITULO_LIVRO));

                bibliografias.add(new Bibliografia(idLivro, idDiscplina, disciplina, curso, autor, livro));

            } while (c.moveToNext());
        }
        return bibliografias;
    }
}