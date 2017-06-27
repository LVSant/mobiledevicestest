package com.anew.devl.prova_si700_156233.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
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
import android.widget.ListView;

import com.anew.devl.prova_si700_156233.R;
import com.anew.devl.prova_si700_156233.adapter.DisciplinaAdapter;
import com.anew.devl.prova_si700_156233.adapter.LivroAdapter;
import com.anew.devl.prova_si700_156233.model.Disciplina;
import com.anew.devl.prova_si700_156233.model.Livro;

import org.json.JSONArray;

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


            initListDisciplina(null, view);


        initCardViewLivros(view);


        return view;
    }

    private void initCardViewLivros(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        /*TODO: Remover esse teste */
        List<Livro> livrosList = new ArrayList<>();


        livrosList.add(new Livro(1, "Calculo A", "Maria Flemming", "img"));
        livrosList.add(new Livro(1, "PROG 1", "Alexandre Saudate", "img2"));
        livrosList.add(new Livro(1, "Calculo A", "Maria Flemming", "img"));
        livrosList.add(new Livro(1, "PROG 1", "Alexandre Saudate", "img2"));
        livrosList.add(new Livro(1, "Calculo A", "Maria Flemming", "img"));
        livrosList.add(new Livro(1, "PROG 1", "Alexandre Saudate", "img2"));
        livrosList.add(new Livro(1, "Calculo A", "Maria Flemming", "img"));
        livrosList.add(new Livro(1, "PROG 1", "Alexandre Saudate", "img2"));
        livrosList.add(new Livro(1, "Calculo A", "Maria Flemming", "img"));
        livrosList.add(new Livro(1, "PROG 1", "Alexandre Saudate", "img2"));
        livrosList.add(new Livro(1, "Calculo A", "Maria Flemming", "img"));
        livrosList.add(new Livro(1, "PROG 1", "Alexandre Saudate", "img2"));


        livroAdapter = new LivroAdapter(this.getContext(), livrosList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setAdapter(livroAdapter);

    }

    private void callNewBibliografia() {
        Busca fragment2 = new Busca();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment2);
        fragmentTransaction.commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

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

    /**
     * Adding few albums for testing
     */
  /*  private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        livroAdapter.notifyDataSetChanged();
    }
*/
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private void initListDisciplina(JSONArray modelosItems, View view) {

        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        disciplinas.add(new Disciplina(0, "Algoritmos e Prog", "TADS"));
        disciplinas.add(new Disciplina(0, "Calculo A", "TADS"));
        disciplinas.add(new Disciplina(0, "Saneamento", "Engenharia Ambiental"));
        disciplinas.add(new Disciplina(0, "Redes complexas", "Engenharia Telecom"));
        disciplinas.add(new Disciplina(0, "Engenharia de Software", "TADS"));
        disciplinas.add(new Disciplina(0, "Algoritmos e Prog", "TADS"));
        disciplinas.add(new Disciplina(0, "Calculo A", "TADS"));
        disciplinas.add(new Disciplina(0, "Saneamento", "Engenharia Ambiental"));
        disciplinas.add(new Disciplina(0, "Redes complexas", "Engenharia Telecom"));
        disciplinas.add(new Disciplina(0, "Engenharia de Software", "TADS"));
        disciplinas.add(new Disciplina(0, "Algoritmos e Prog", "TADS"));
        disciplinas.add(new Disciplina(0, "Calculo A", "TADS"));
        disciplinas.add(new Disciplina(0, "Saneamento", "Engenharia Ambiental"));
        disciplinas.add(new Disciplina(0, "Redes complexas", "Engenharia Telecom"));
        disciplinas.add(new Disciplina(0, "Engenharia de Software", "TADS"));
//
//        try {
//            for (int i = 0; i < modelosItems.length(); i++) {
//                /*JSONObject jsonModelo = modelosItems.optJSONObject(i);
//
//                String name = (String) jsonModelo.get("fipe_name");
//
//                Object idjson = jsonModelo.get("id");
//                String id;
//                if (idjson instanceof String) {
//                    id = idjson.toString();
//                } else {
//                    id = Integer.parseInt(idjson.toString()) + "";
//
//                }
//                Modelo modelo = new Modelo(name, id);
//
//
//                disciplinas.add(modelo);
//*/            }
//        } catch (JSONException jsonex) {
//            Log.d("populateListModelo", jsonex.getLocalizedMessage());
//        }


        this.disciplinaAdapter = new DisciplinaAdapter(this.getContext(), disciplinas);

        ListView listView = (ListView) view.findViewById(R.id.listviewDisciplinas);
        listView.setAdapter(disciplinaAdapter);

    }

}