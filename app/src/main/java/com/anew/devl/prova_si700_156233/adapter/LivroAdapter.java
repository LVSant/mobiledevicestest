package com.anew.devl.prova_si700_156233.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anew.devl.prova_si700_156233.R;
import com.anew.devl.prova_si700_156233.model.Livro;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 18/05/17.
 */
public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.MyViewHolder> implements Serializable {

    private Context mContext;
    private List<Livro> livroList;
    List<Long> idsLivrosSelecionados;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }


    }


    public LivroAdapter(Context mContext, List<Livro> livroList) {
        this.mContext = mContext;
        this.livroList = livroList;
        this.idsLivrosSelecionados = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.livro_card, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LivroAdapter.MyViewHolder viewHolder, final int i) {

        String[] livros = {"http://statics.livrariacultura.net.br/products/capas_lg/926/11005926.jpg",
                "http://www.livroandroid.com.br/site/imgs/livro_android.png",
                "http://statics.livrariacultura.net.br/products/capas_lg/014/3014.jpg",
                "http://www.training.com.br/aso/capa4.jpg",
                "http://statics.livrariacultura.net.br/products/capas_lg/926/11005926.jpg",
                "http://www.livroandroid.com.br/site/imgs/livro_android.png",
                "http://statics.livrariacultura.net.br/products/capas_lg/014/3014.jpg",
                "http://www.training.com.br/aso/capa4.jpg",
                "http://statics.livrariacultura.net.br/products/capas_lg/926/11005926.jpg",
                "http://www.livroandroid.com.br/site/imgs/livro_android.png",
                "http://statics.livrariacultura.net.br/products/capas_lg/014/3014.jpg",
                "http://www.training.com.br/aso/capa4.jpg",
                "http://statics.livrariacultura.net.br/products/capas_lg/926/11005926.jpg",
                "http://www.livroandroid.com.br/site/imgs/livro_android.png",
                "http://statics.livrariacultura.net.br/products/capas_lg/014/3014.jpg",
                "http://www.training.com.br/aso/capa4.jpg",
                "http://statics.livrariacultura.net.br/products/capas_lg/926/11005926.jpg",
                "http://www.livroandroid.com.br/site/imgs/livro_android.png",
                "http://statics.livrariacultura.net.br/products/capas_lg/014/3014.jpg",
                "http://www.training.com.br/aso/capa4.jpg"};

        viewHolder.title.setText(livroList.get(i).getTituloLivro());
        viewHolder.count.setText(livroList.get(i).getAutor());

        //load album cover using picasso
        Picasso.with(mContext)
                .load(livros[i])
                .placeholder(R.drawable.ic_loading)
                .into(viewHolder.thumbnail);


        viewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                long livroId = livroList.get(i).get_id();

                if (!idsLivrosSelecionados.contains(livroId)) {
                    idsLivrosSelecionados.add(livroId);
                    viewHolder.title.setBackgroundColor(v.getResources().getColor(R.color.colorLightList));
                } else {
                    idsLivrosSelecionados.remove(livroId);
                    viewHolder.title.setBackgroundColor(v.getResources().getColor(R.color.cardview_light_background));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return livroList.size();
    }

    public List<Long> getIdsLivrosSelecionados() {
        return idsLivrosSelecionados;
    }
}
