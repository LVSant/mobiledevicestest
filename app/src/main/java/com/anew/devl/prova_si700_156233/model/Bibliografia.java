package com.anew.devl.prova_si700_156233.model;

/**
 * Created by devl on 6/25/17.
 */

public class Bibliografia {
    private long idLivro;
    private long idDisciplina;

    public Bibliografia(long idLivro, long idDisciplina) {
        this.idLivro = idLivro;
        this.idDisciplina = idDisciplina;
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
}
