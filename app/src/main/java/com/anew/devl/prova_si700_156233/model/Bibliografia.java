package com.anew.devl.prova_si700_156233.model;

import java.io.Serializable;

/**
 * Created by devl on 6/25/17.
 */

public class Bibliografia implements Serializable {
    private long idLivro;
    private long idDisciplina;
    private long _id;
    private String nomeDisciplina;
    private String tituloLivro;
    private String curso;
    private String autor;
    private String imageLivro;


    public Bibliografia(long idLivro, long idDisciplina, String nomeDisciplina,
                        String tituloLivro, String curso, String autor, String imageLivro) {
        this.idLivro = idLivro;
        this.idDisciplina = idDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.tituloLivro = tituloLivro;
        this.curso = curso;
        this.autor = autor;
        this.imageLivro = imageLivro;
    }

    public Bibliografia(long idLivro, long idDisciplina) {
        this.idLivro = idLivro;
        this.idDisciplina = idDisciplina;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(long idLivro) {
        this.idLivro = idLivro;
    }

    public long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getImageLivro() {
        return imageLivro;
    }

    public void setImageLivro(String imageLivro) {
        this.imageLivro = imageLivro;
    }
}
