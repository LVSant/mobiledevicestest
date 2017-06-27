package com.anew.devl.prova_si700_156233.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anew.devl.prova_si700_156233.R;
import com.anew.devl.prova_si700_156233.model.Disciplina;

import java.io.Serializable;
import java.util.List;

public class DisciplinaAdapter extends BaseAdapter implements Serializable {

    Context context;
    List<Disciplina> data;
    private static LayoutInflater inflater = null;
    private boolean zebraBackground = true;

    public DisciplinaAdapter(Context context, List<Disciplina> Disciplinas) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = Disciplinas;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Disciplina getItem(int position) {


        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.disciplina_adapter, null);

            zebraBackground = !zebraBackground;
            View backgroundAdapter = vi.findViewById(R.id.disciplinaAdapterLayout);
            if (zebraBackground) {
                backgroundAdapter.setBackgroundColor(vi.getResources().getColor(R.color.colorList));
            } else {
                backgroundAdapter.setBackgroundColor(vi.getResources().getColor(R.color.colorLightList));
            }


            TextView textCurso = (TextView) vi.findViewById(R.id.curso);
            textCurso.setText(data.get(position).getCurso());

            TextView textDisciplina = (TextView) vi.findViewById(R.id.nomeDisciplina);
            textDisciplina.setText(data.get(position).getNomeDisciplina());

        }
        return vi;
    }


}