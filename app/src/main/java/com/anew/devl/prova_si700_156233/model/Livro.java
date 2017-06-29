package com.anew.devl.prova_si700_156233.model;

/**
 * Created by devl on 6/25/17.
 */

public class Livro {

    private long _id;
    private String tituloLivro;
    private String autor;
    private String image;

    public static final String[] imgIndexLivro = {
            // Android
            "http://www.livroandroid.com.br/site/imgs/livro_android.png",
            // Cálculo A
            "http://statics.livrariacultura.net.br/products/capas_lg/926/11005926.jpg",
            // Álgebra Linear
            "http://statics.livrariacultura.net.br/products/capas_lg/014/3014.jpg",
            // Sistemas Operacionais
            "http://www.training.com.br/aso/capa4.jpg",
            // DB
            "http://3.bp.blogspot.com/_aFYkUPKyX3M/Rxf_oEuBkLI/AAAAAAAABfU/4MnFUjbwkEA/s400/Sistemas+de+Banco+de+Dados+-+Elmasri.jpg",
            // PMBOK
            "https://images.livrariasaraiva.com.br/imagemnet/imagem.aspx/?pro_id=7076153&qld=90&l=430&a=-1",
            // OO
            "https://cdn.shopify.com/s/files/1/0155/7645/products/oo-solid-sumario-featured_large.png",
            // Engenharia Ambiental
            "http://loja.grupoa.com.br/uploads/imagensTitulo/20160718112501_DAVIS_Principios_Engenharia_Ambiental_3ed_G.jpg",
            // Engenharia de Telecomunicações
            "http://www.casasbahia-imagens.com.br/livros/EngenhariaTecnologia/EngenhariadaTelecomunicacao/141316/243392149/Telecomunicacoes-Sistemas-de-Modulacao-Vicente-Soares-Neto-141316.jpg",
            // Genetic Algorithms
            "https://images-na.ssl-images-amazon.com/images/I/51hfb6UahWL._SX310_BO1,204,203,200_.jpg",
            // Python Data Science
            "http://blog.paralleldots.com/wp-content/uploads/2017/03/python-ds.jpg"
    };

    public Livro() {
    }

    public Livro(long _id, String tituloLivro, String autor) {
        this._id = _id;
        this.tituloLivro = tituloLivro;
        this.autor = autor;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
