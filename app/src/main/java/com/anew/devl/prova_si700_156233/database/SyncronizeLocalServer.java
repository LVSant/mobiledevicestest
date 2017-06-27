package com.anew.devl.prova_si700_156233.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.anew.devl.prova_si700_156233.database.serverdata.SelectDisciplina;
import com.anew.devl.prova_si700_156233.database.serverdata.SelectLivro;
import com.anew.devl.prova_si700_156233.database.serverdata.Server;
import com.anew.devl.prova_si700_156233.model.Disciplina;
import com.anew.devl.prova_si700_156233.model.Livro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by devl on 6/27/17.
 */

public class SyncronizeLocalServer {
    public static final String COLUMN_NAME_TITULO_LIVRO = "TituloLivro";
    public static final String COLUMN_NAME_AUTOR = "Autor";
    public static final String COLUMN_NAME_NOME_DISCIPLINA = "NomeDisciplina";
    public static final String COLUMN_NAME_CURSO = "Curso";


    public void init(Context context) {
        initLivro(context);
        initDisciplina(context);

        //cleanDB(context);
    }

    private void cleanDB(Context context) {

        DBHelperLivro helperLivro = new DBHelperLivro(context);
        SQLiteDatabase dbLivro = helperLivro.getWritableDatabase();
        helperLivro.onDropAll(dbLivro);


        DBHelperDisciplina helperDisciplina = new DBHelperDisciplina(context);
        SQLiteDatabase dbDisciplina = helperDisciplina.getWritableDatabase();
        helperDisciplina.onDropAll(dbDisciplina);

    }

    private void initLivro(Context context) {

        String[] fields = {""};
        String[] values = {""};


        AsyncTask<Void, Void, String> retornoSelectLivro = null;
        String result = null;
        SelectLivro selectLivro = new SelectLivro(fields, values);
        try {
            retornoSelectLivro = selectLivro.execute();
            result = retornoSelectLivro.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        List<Livro> livrosServidor = livroJson2List(result);
        List<Livro> livrosDBLOcal = selectLivrosLocalDB(context);
        List<Long> idsDBLocal = new ArrayList<>();


        for (Livro livro : livrosDBLOcal) {
            idsDBLocal.add(livro.get_id());
        }

        for (Livro livro : livrosServidor) {

            if (!idsDBLocal.contains(livro.get_id()))
                insertLivroDBLOcal(context, livro);
        }



    }

    private List<Livro> selectLivrosLocalDB(Context context) {

        List<Livro> livros = new ArrayList<>();
        DBHelperLivro helper = new DBHelperLivro(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                DBHelperLivro.DBHelperLivroColumns._ID,
                DBHelperLivro.DBHelperLivroColumns.COLUMN_NAME_TITULO_LIVRO,
                DBHelperLivro.DBHelperLivroColumns.COLUMN_NAME_AUTOR
        };


        String sortByAdd =
                DBHelperLivro.DBHelperLivroColumns._ID + " DESC ";

        Cursor c = db.query(
                DBHelperLivro.DBHelperLivroColumns.TABLE_NAME,                     // The table to query
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
                String tituloLivro = c.getString(c.getColumnIndexOrThrow("TituloLivro"));
                String autor = c.getString(c.getColumnIndexOrThrow("Autor"));


                livros.add(new Livro(id, tituloLivro, autor));

            } while (c.moveToNext());
        }
        return livros;
    }

    private List<Livro> livroJson2List(String result) {
        ArrayList<Livro> livros = new ArrayList<>();


        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long idLivro = jsonObject.getLong("idLivro");
                String tituloLivro = jsonObject.getString("TituloLivro");
                String exemplares = jsonObject.getString("Exemplares");

                if (tituloLivro == null) {
                    tituloLivro = "vazio";
                }
                if (exemplares == null) {
                    exemplares = "vazio";
                }

                Livro livro = new Livro(idLivro, tituloLivro, exemplares);
                livros.add(livro);
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        return livros;
    }


    private boolean insertLivroDBLOcal(Context context, Livro livro) {

        /*Insert na table Livro*/
        DBHelperLivro helper = new DBHelperLivro(context);
        SQLiteDatabase db = helper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AUTOR, livro.getAutor());
        values.put(COLUMN_NAME_TITULO_LIVRO, livro.getTituloLivro());

        if (livro.get_id() != 0) {
            //insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(Server.TABLE_LIVRO, null, values);
            return (newRowId >= 1);
        } else return false;

    }

    private void initDisciplina(Context context) {

        String[] fields = {""};
        String[] values = {""};


        AsyncTask<Void, Void, String> retornoSelectDisciplina = null;
        String result = null;
        SelectDisciplina selectDisciplina = new SelectDisciplina(fields, values);
        try {
            retornoSelectDisciplina = selectDisciplina.execute();
            result = retornoSelectDisciplina.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<Disciplina> disciplinasServidor = disciplinaJson2List(result);
        List<Disciplina> disciplinasDBLocal = selectDisciplinasLocalDB(context);
        List<Long> idsDBLocal = new ArrayList<>();


        for (Disciplina disciplina : disciplinasDBLocal) {
            idsDBLocal.add(disciplina.get_id());
        }

        for (Disciplina disciplina : disciplinasServidor) {

            if (!idsDBLocal.contains(disciplina.get_id()))
                insertDisciplinaDBLocal(context, disciplina);
        }
    }

    private List<Disciplina> disciplinaJson2List(String result) {
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long idDisciplina = jsonObject.getLong("idDisciplina");
                String nomeDisciplina = jsonObject.getString("NomeDisciplina");
                String curso = jsonObject.getString("Curso");

                if (nomeDisciplina == null) {
                    nomeDisciplina = "vazio";
                }
                if (curso == null) {
                    curso = "vazio";
                }

                Disciplina disciplina = new Disciplina(idDisciplina, nomeDisciplina, curso);
                disciplinas.add(disciplina);
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        return disciplinas;
    }

    private List<Disciplina> selectDisciplinasLocalDB(Context context) {

        List<Disciplina> disciplinas = new ArrayList<>();
        DBHelperDisciplina helper = new DBHelperDisciplina(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                DBHelperDisciplina.DBHelperDisciplinaColumns._ID,
                DBHelperDisciplina.DBHelperDisciplinaColumns.COLUMN_NAME_TITULO_DISCIPLINA,
                DBHelperDisciplina.DBHelperDisciplinaColumns.COLUMN_NAME_CURSO
        };


        String sortByAdd =
                DBHelperDisciplina.DBHelperDisciplinaColumns._ID + " DESC ";

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

    private boolean insertDisciplinaDBLocal(Context context, Disciplina disciplina) {

        /*Insert na table Disciplina*/
        DBHelperDisciplina helper = new DBHelperDisciplina(context);
        SQLiteDatabase db = helper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(DBHelperDisciplina.DBHelperDisciplinaColumns._ID, disciplina.get_id());
        values.put(COLUMN_NAME_NOME_DISCIPLINA, disciplina.getNomeDisciplina());
        values.put(COLUMN_NAME_CURSO, disciplina.getCurso());


        //insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Server.TABLE_DISCIPLINA, null, values);

        return (newRowId >= 1);

    }
}
