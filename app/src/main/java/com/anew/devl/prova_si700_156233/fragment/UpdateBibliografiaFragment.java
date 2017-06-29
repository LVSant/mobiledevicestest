package com.anew.devl.prova_si700_156233.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.anew.devl.prova_si700_156233.database.DBHelperBibliografia;
import com.anew.devl.prova_si700_156233.database.DBHelperDisciplina;
import com.anew.devl.prova_si700_156233.database.SincronizeDatabaseLocalServer;
import com.anew.devl.prova_si700_156233.model.Bibliografia;
import com.anew.devl.prova_si700_156233.model.Disciplina;
import com.anew.devl.prova_si700_156233.model.Livro;

import java.util.ArrayList;
import java.util.List;

import static com.anew.devl.prova_si700_156233.fragment.Busca.BIBLIOGRAFIA_BUSCA;

public class UpdateBibliografiaFragment extends Fragment {

    RecyclerView recyclerView;
    LivroAdapter livroAdapter;
    DisciplinaAdapter disciplinaAdapter;
    List<Long> idsDisciplinasSelecionadas;
    List<Long> idsLivrosSelecionados;
    List<Livro> livrosDBLocal;
    List<Disciplina> disciplinasDBLocal;
    Bibliografia bibliografiaUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.novabibliografia, container, false);

        initListDisciplina(view);
        initCardViewLivros(view);

        Button btnUpdateBibliografia = (Button) view.findViewById(R.id.buttonCriarBibliografia);
        btnUpdateBibliografia.setText("ATUALIZAR");

        Bundle arguments = getArguments();
        bibliografiaUpdate = (Bibliografia) arguments.get(BIBLIOGRAFIA_BUSCA);
        Log.d("ON UPDATE", "BIB LIVRO: "+bibliografiaUpdate.getImageLivro());

        btnUpdateBibliografia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBib();
            }
        });

        return view;
    }

    private void initCardViewLivros(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        livrosDBLocal = selectLivros(getContext());
        livroAdapter = new LivroAdapter(this.getContext(), livrosDBLocal);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(livroAdapter);

    }

    private void initListDisciplina(View view) {


        idsDisciplinasSelecionadas = new ArrayList<>();
        disciplinasDBLocal = selectDisciplinas(getContext());
        this.disciplinaAdapter = new DisciplinaAdapter(this.getContext(), disciplinasDBLocal);
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
                    view.setBackgroundColor(getResources().getColor(R.color.colorList));
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

    private void updateBib() {

        LivroAdapter adapter = (LivroAdapter) recyclerView.getAdapter();
        idsLivrosSelecionados = adapter.getIdsLivrosSelecionados();

        if (validateBibliografia(idsLivrosSelecionados, idsDisciplinasSelecionadas)) {


            List<Bibliografia> bibliografias = selectBibliografiasLocalDB(getContext());
            for (Bibliografia bibliografia : bibliografias) {
                Log.d("AntesUpdate", "curso " + bibliografia.getCurso() + " livro " + bibliografia.getTituloLivro());
            }

            Log.d("bibliografiaUpdate", "curso: " + bibliografiaUpdate.getCurso() + " livro " + bibliografiaUpdate.getTituloLivro());

            int i = updateBibliografiaLocalDB(getContext(), bibliografiaUpdate);
            Log.d("AATUALIZOU", "Total: " + i);
            toastSucessoInsertBibliografia();


            bibliografias = selectBibliografiasLocalDB(getContext());
            for (Bibliografia bibliografia : bibliografias) {
                Log.d("DepoisUpdate", "curso " + bibliografia.getCurso() + " livro " + bibliografia.getTituloLivro());
            }

        }
    }

    private int updateBibliografiaLocalDB(Context context, Bibliografia bibliografiaUpdate) {


        DBHelperBibliografia helper = new DBHelperBibliografia(context);
        SQLiteDatabase db = helper.getReadableDatabase();


        ContentValues values = new ContentValues();
        values.put("idLivro", idsLivrosSelecionados.get(0));
        values.put("idDisciplina", idsDisciplinasSelecionadas.get(0));

        for (Livro livro : livrosDBLocal) {
            if (livro.get_id() == idsLivrosSelecionados.get(0)) {
                values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_TITULO_LIVRO,
                        livro.getTituloLivro());
                values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_AUTOR,
                        livro.getAutor());
                values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_IMAGE_LIVRO,
                        livro.getImage());

            }
        }

        for (Disciplina disciplina : disciplinasDBLocal) {
            if (disciplina.get_id() == idsDisciplinasSelecionadas.get(0)) {
                values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_DISCIPLINA,
                        disciplina.getNomeDisciplina());
                values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_CURSO,
                        disciplina.getCurso());
            }
        }


        String[] args = {
                bibliografiaUpdate.getIdLivro() + "",
                bibliografiaUpdate.getIdDisciplina() + ""
        };
        String whereClause = "idLivro=? AND idDisciplina=?";


        return db.update(
                DBHelperBibliografia.DBHelperBibliografiaColumns.TABLE_NAME,                     // The table to query
                values,                                  // The columns to return
                whereClause,         // The columns for the WHERE clause
                args                                     // The values for the WHERE clause
        );


    }


    private List<Bibliografia> selectBibliografiasLocalDB(Context context) {

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

    private void toastValidateDisciplinas() {
        Toast.makeText(getContext(), "Selecione UMA Disciplina!", Toast.LENGTH_SHORT).show();
    }

    private void toastValidadeLivros() {
        Toast.makeText(getContext(), "Selecione UM Livro!", Toast.LENGTH_SHORT).show();
    }

    private void toastSucessoInsertBibliografia() {
        Toast.makeText(getContext(), "Bibliografia atualizada com sucesso", Toast.LENGTH_LONG).show();
    }


}
