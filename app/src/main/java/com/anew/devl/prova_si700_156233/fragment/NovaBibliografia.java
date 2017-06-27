package com.anew.devl.prova_si700_156233.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anew.devl.prova_si700_156233.R;
import com.anew.devl.prova_si700_156233.adapter.DisciplinaAdapter;
import com.anew.devl.prova_si700_156233.adapter.LivroAdapter;
import com.anew.devl.prova_si700_156233.database.DBHelperDisciplina;
import com.anew.devl.prova_si700_156233.database.DBHelperLivro;
import com.anew.devl.prova_si700_156233.model.Disciplina;
import com.anew.devl.prova_si700_156233.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class NovaBibliografia extends Fragment {

    RecyclerView recyclerView;
    LivroAdapter livroAdapter;
    DisciplinaAdapter disciplinaAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.novabibliografia, container, false);

        initListDisciplina(view);
        initCardViewLivros(view);

        return view;
    }

    private void initCardViewLivros(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        List<Livro> livrosList = selectLivros(getContext());

        livroAdapter = new LivroAdapter(this.getContext(), livrosList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setAdapter(livroAdapter);

    }


    private void initListDisciplina(View view) {

        List<Disciplina> disciplinas = selectDisciplinas(getContext());

        this.disciplinaAdapter = new DisciplinaAdapter(this.getContext(), disciplinas);

        ListView listView = (ListView) view.findViewById(R.id.listviewDisciplinas);
        listView.setAdapter(disciplinaAdapter);

    }

    private List<Livro> selectLivros(Context context) {

        List<Livro> livros = new ArrayList<>();
        DBHelperLivro helper = new DBHelperLivro(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                DBHelperLivro.DBHelperLivroColumns._ID,
                DBHelperLivro.DBHelperLivroColumns.COLUMN_NAME_TITULO_LIVRO,
                DBHelperLivro.DBHelperLivroColumns.COLUMN_NAME_AUTOR
        };


        //String selection =  DBHelper.VeiculoDBHelper;
        String sortByAdd =
                DBHelperLivro.DBHelperLivroColumns.COLUMN_NAME_TITULO_LIVRO + " DESC ";

        Cursor c = db.query(
                DBHelperLivro.DBHelperLivroColumns.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortByAdd                                 // The sort order
        );

        if (c.moveToFirst()) {
            do {
                //id, name, marca, preco, adicionado
                long id = c.getLong(c.getColumnIndexOrThrow("_id"));
                String tituloLivro = c.getString(c.getColumnIndexOrThrow("TituloLivro"));
                String autor = c.getString(c.getColumnIndexOrThrow("Autor"));


                livros.add(new Livro(id, tituloLivro, autor));

            } while (c.moveToNext());
        }
        return livros;
    }

    private List<Disciplina> selectDisciplinas(Context context) {


        List<Disciplina> disciplinas = new ArrayList<>();
        DBHelperDisciplina helper = new DBHelperDisciplina(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                DBHelperDisciplina.DBHelperDisciplinaColumns._ID,
                DBHelperDisciplina.DBHelperDisciplinaColumns.COLUMN_NAME_TITULO_DISCIPLINA,
                DBHelperDisciplina.DBHelperDisciplinaColumns.COLUMN_NAME_CURSO
        };


        String sortByAdd =
                DBHelperDisciplina.DBHelperDisciplinaColumns.COLUMN_NAME_TITULO_DISCIPLINA + " DESC ";

        Cursor c = db.query(
                DBHelperDisciplina.DBHelperDisciplinaColumns.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortByAdd                                 // The sort order
        );

        if (c.moveToFirst()) {
            do {

                long id = c.getLong(c.getColumnIndexOrThrow("_id"));
                String tituloDisciplina = c.getString(c.getColumnIndexOrThrow("NomeDisciplina"));
                String curso = c.getString(c.getColumnIndexOrThrow("Curso"));


                disciplinas.add(new Disciplina(id, tituloDisciplina, curso));

            } while (c.moveToNext());
        }
        return disciplinas;
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}