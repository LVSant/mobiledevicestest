package com.anew.devl.prova_si700_156233.model;

/**
 * Created by devl on 6/25/17.
 */

public class Livro {

    private long _id;
    private String tituloLivro;
    private String autor;
    private String image;

    public Livro() {
    }

    public Livro(long _id, String tituloLivro, String autor, String image) {
        this._id = _id;
        this.tituloLivro = tituloLivro;
        this.autor = autor;
        this.image = image;
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