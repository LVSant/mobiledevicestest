package com.anew.devl.prova_si700_156233.model;

/**
 * Created by devl on 6/25/17.
 */

public class Disciplina {
    private long _id;
    private String nomeDisciplina;
    private String curso;

    public Disciplina() {
    }

    public Disciplina(long _id, String nomeDisciplina, String curso) {
        this._id = _id;
        this.nomeDisciplina = nomeDisciplina;
        this.curso = curso;
    }

    public Disciplina( String nomeDisciplina, String curso) {
        this._id = _id;
        this.nomeDisciplina = nomeDisciplina;
        this.curso = curso;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
