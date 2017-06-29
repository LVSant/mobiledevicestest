package com.anew.devl.prova_si700_156233.adapter;

import android.content.Context;
import android.util.Log;
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

    class DisciplinaAdapterHolder {
        TextView textCurso, textDisciplina;

        public DisciplinaAdapterHolder(View v) {
            textCurso = (TextView) v.findViewById(R.id.curso);
            textDisciplina = (TextView) v.findViewById(R.id.nomeDisciplina);
        }
    }

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

        View vi = convertView;
        DisciplinaAdapterHolder holder;
        if (vi == null) {
            vi = inflater.inflate(R.layout.disciplina_adapter, null);
            holder = new DisciplinaAdapterHolder(vi);
            vi.setTag(holder);
        } else {
            holder = (DisciplinaAdapterHolder) vi.getTag();
        }


        Disciplina disciplina = data.get(position);
        holder.textCurso.setText(disciplina.getCurso());
        holder.textDisciplina.setText(disciplina.getNomeDisciplina());

        return vi;
    }



}