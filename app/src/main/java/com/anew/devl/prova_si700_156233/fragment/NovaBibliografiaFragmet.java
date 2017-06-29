package com.anew.devl.prova_si700_156233.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.anew.devl.prova_si700_156233.R;
import com.anew.devl.prova_si700_156233.adapter.DisciplinaAdapter;
import com.anew.devl.prova_si700_156233.adapter.LivroAdapter;
import com.anew.devl.prova_si700_156233.database.DBHelperDisciplina;
import com.anew.devl.prova_si700_156233.database.SincronizeDatabaseLocalServer;
import com.anew.devl.prova_si700_156233.model.Bibliografia;
import com.anew.devl.prova_si700_156233.model.Disciplina;
import com.anew.devl.prova_si700_156233.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class NovaBibliografiaFragmet extends Fragment {

    RecyclerView recyclerView;
    LivroAdapter livroAdapter;
    DisciplinaAdapter disciplinaAdapter;
    List<Long> idsDisciplinasSelecionadas;
    List<Long> idsLivrosSelecionados;
    List<Livro> livros;
    List<Disciplina> disciplinas;



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.novabibliografia, container, false);


        initListDisciplina(view);
        initCardViewLivros(view);

        Button btnNewBibliografia = (Button) view.findViewById(R.id.buttonCriarBibliografia);
        btnNewBibliografia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaBib();
            }
        });

        return view;
    }

    private void initCardViewLivros(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        livros = selectLivros(getContext());
        livroAdapter = new LivroAdapter(this.getContext(), livros);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(livroAdapter);

    }

    private void initListDisciplina(View view) {


        idsDisciplinasSelecionadas = new ArrayList<>();
        disciplinas = selectDisciplinas(getContext());
        this.disciplinaAdapter = new DisciplinaAdapter(this.getContext(), disciplinas);
        ListView listView = (ListView) view.findViewById(R.id.listviewDisciplinas);
        listView.setAdapter(disciplinaAdapter);

        /**
         * The ID that came from the listener, in this case long wrongIdSomehow is wrong!
         * The approach to get the ID from the Item via the Adapter, above as
         *
         * long _id = disciplinaAdapter.getItem(position).get_id();
         *
         * seems to work!
         * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long wrongIdSomehow) {

                long _id = disciplinaAdapter.getItem(position).get_id();

                if (!idsDisciplinasSelecionadas.contains(_id)) {
                    idsDisciplinasSelecionadas.add(_id);
                    view.setBackgroundColor(getResources().getColor(R.color.colorLightList));
                } else {
                    idsDisciplinasSelecionadas.remove(_id);
                    view.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                }
            }
        });
    }

    private List<Livro> selectLivros(Context context) {
        SincronizeDatabaseLocalServer s = new SincronizeDatabaseLocalServer();
        return s.selectLivrosLocalDB(context);
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

    private boolean validateBibliografia(List<Long> idsLivrosSelecionados, List<Long> idsDisciplinasSelecionadas) {
        boolean isValid = true;
        if (idsDisciplinasSelecionadas.size() != 1) {
            toastValidateDisciplinas();
            return false;
        }

        if (idsLivrosSelecionados.size() != 1) {
            toastValidadeLivros();
            return false;
        }
        return isValid;
    }

    private void criaBib() {
        LivroAdapter adapter = (LivroAdapter) recyclerView.getAdapter();
        idsLivrosSelecionados = adapter.getIdsLivrosSelecionados();

        if (validateBibliografia(idsLivrosSelecionados, idsDisciplinasSelecionadas)) {

            Bibliografia bib = new Bibliografia(idsLivrosSelecionados.get(0), idsDisciplinasSelecionadas.get(0));

            for (Livro livro : livros) {
                if (livro.get_id() == bib.getIdLivro()) {
                    bib.setTituloLivro(livro.getTituloLivro());
                    bib.setAutor(livro.getAutor());
                    bib.setImageLivro(livro.getImage());
                }
            }

            for (Disciplina disciplina : disciplinas) {
                if (disciplina.get_id() == bib.getIdDisciplina()) {
                    bib.setNomeDisciplina(disciplina.getNomeDisciplina());
                    bib.setCurso(disciplina.getCurso());
                }
            }

            SincronizeDatabaseLocalServer s = new SincronizeDatabaseLocalServer();
            s.insertBibliografiaDBLocal(getContext(), bib);

            toastSucessoInsertBibliografia();
            callBibliografia();
        }


    }


    private void toastValidateDisciplinas() {
        Toast.makeText(getContext(), "Selecione UMA Disciplina!", Toast.LENGTH_SHORT).show();
    }

    private void toastValidadeLivros() {
        Toast.makeText(getContext(), "Selecione UM Livro!", Toast.LENGTH_SHORT).show();
    }

    private void toastSucessoInsertBibliografia() {
        Toast.makeText(getContext(), "Bibliografia salva com sucesso", Toast.LENGTH_LONG).show();
    }

    private void callBibliografia() {

        BibliografiaFragment fragment = new BibliografiaFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }


}
