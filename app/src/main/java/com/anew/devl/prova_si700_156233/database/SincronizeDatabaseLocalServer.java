package com.anew.devl.prova_si700_156233.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.anew.devl.prova_si700_156233.database.serverdata.SelectBibliografia;
import com.anew.devl.prova_si700_156233.database.serverdata.SelectDisciplina;
import com.anew.devl.prova_si700_156233.database.serverdata.SelectLivro;
import com.anew.devl.prova_si700_156233.database.serverdata.Server;
import com.anew.devl.prova_si700_156233.model.Bibliografia;
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

public class SincronizeDatabaseLocalServer {
    public static final String COLUMN_NAME_TITULO_LIVRO = "TituloLivro";
    public static final String COLUMN_NAME_AUTOR = "Autor";
    public static final String COLUMN_NAME_NOME_DISCIPLINA = "NomeDisciplina";
    public static final String COLUMN_NAME_CURSO = "Curso";
    private List<Livro> livrosDBLOcal;
    private List<Disciplina> disciplinasDBLocal;


    public void init(Context context) {
        cleanDB(context);

        initLivro(context);
        initDisciplina(context);
        initBibliografia(context);

    }

    /**
     * Responsible for cleaning up the database for the new sync
     */
    private void cleanDB(Context context) {

        DBHelperLivro helperLivro = new DBHelperLivro(context);
        SQLiteDatabase dbLivro = helperLivro.getWritableDatabase();
        helperLivro.onDropAll(dbLivro);


        DBHelperDisciplina helperDisciplina = new DBHelperDisciplina(context);
        SQLiteDatabase dbDisciplina = helperDisciplina.getWritableDatabase();
        helperDisciplina.onDropAll(dbDisciplina);


        DBHelperBibliografia helperBibliografia = new DBHelperBibliografia(context);
        SQLiteDatabase dbBibliografia = helperBibliografia.getWritableDatabase();
        helperBibliografia.onDropAll(dbBibliografia);
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


        /*
         * Approach for sincronizing the local database with the server data, by the "_id" field
         * */
        List<Livro> livrosServidor = livroJson2List(result);
        livrosDBLOcal = selectLivrosLocalDB(context);
        List<Long> idsDBLocal = new ArrayList<>();


        for (Livro livro : livrosDBLOcal) {
            idsDBLocal.add(livro.get_id());
        }

        for (Livro livro : livrosServidor) {

            if (!idsDBLocal.contains(livro.get_id()))
                insertLivroDBLOcal(context, livro);
        }


    }

    public List<Livro> selectLivrosLocalDB(Context context) {

        List<Livro> livros = new ArrayList<>();
        DBHelperLivro helper = new DBHelperLivro(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        int livroImgIndex = 0;

        String[] projection = {
                DBHelperLivro.DBHelperLivroColumns._ID,
                DBHelperLivro.DBHelperLivroColumns.COLUMN_NAME_TITULO_LIVRO,
                DBHelperLivro.DBHelperLivroColumns.COLUMN_NAME_AUTOR
        };


        String sortByAdd =
                DBHelperLivro.DBHelperLivroColumns._ID + " ASC ";

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

                Livro livro = new Livro(id, tituloLivro, autor);

                livro.setImage(Livro.imgIndexLivro[livroImgIndex]);
                livroImgIndex++;

                livros.add(livro);

            } while (c.moveToNext());
        }
        return livros;
    }

    private List<Livro> livroJson2List(String result) {
        ArrayList<Livro> livros = new ArrayList<>();

        if (result != null && !result.isEmpty()) {
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
        }
        return livros;
    }


    private boolean insertLivroDBLOcal(Context context, Livro livro) {

        /*Insert na table Livro*/
        DBHelperLivro helper = new DBHelperLivro(context);
        SQLiteDatabase db = helper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(DBHelperLivro.DBHelperLivroColumns._ID, livro.get_id());
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
        disciplinasDBLocal = selectDisciplinasLocalDB(context);
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
        if (result != null && !result.isEmpty()) {
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


        DBHelperDisciplina helper = new DBHelperDisciplina(context);
        SQLiteDatabase db = helper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(DBHelperDisciplina.DBHelperDisciplinaColumns._ID, disciplina.get_id());
        values.put(COLUMN_NAME_NOME_DISCIPLINA, disciplina.getNomeDisciplina());
        values.put(COLUMN_NAME_CURSO, disciplina.getCurso());


        long newRowId = db.insert(Server.TABLE_DISCIPLINA, null, values);

        return (newRowId >= 1);

    }

    private void initBibliografia(Context context) {

        String[] fields = {""};
        String[] values = {""};


        AsyncTask<Void, Void, String> retornoSelectBibliografia = null;
        String result = null;
        SelectBibliografia selectBibliografia = new SelectBibliografia(fields, values);
        try {
            retornoSelectBibliografia = selectBibliografia.execute();
            result = retornoSelectBibliografia.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<Bibliografia> bibliografiasServidor = bibliografiaJson2List(result);
       /* List<Bibliografia> bibliografiasDBLocal = selectBibliografiasLocalDB(context);
        List<Long> idsDBLocal = new ArrayList<>();
*/

        /*for (Bibliografia bib : bibliografiasDBLocal) {
            idsDBLocal.add(bib.get_id());
        }
*/
        for (Bibliografia bibliografiaServidor : bibliografiasServidor) {


            for (Livro livro : selectLivrosLocalDB(context)) {
                if (bibliografiaServidor.getIdLivro() == livro.get_id()) {
                    bibliografiaServidor.setTituloLivro(livro.getTituloLivro());
                    bibliografiaServidor.setAutor(livro.getAutor());

                }
            }

            for (Disciplina disciplina : selectDisciplinasLocalDB(context)) {
                if (bibliografiaServidor.getIdDisciplina() == disciplina.get_id()) {
                    bibliografiaServidor.setNomeDisciplina(disciplina.getNomeDisciplina());
                    bibliografiaServidor.setCurso(disciplina.getCurso());
                }

            }


            insertBibliografiaDBLocal(context, bibliografiaServidor);

        }
    }

    private List<Bibliografia> bibliografiaJson2List(String result) {
        ArrayList<Bibliografia> bibliografias = new ArrayList<>();
        if (result != null && !result.isEmpty()) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    long idLivro = jsonObject.getLong("idLivro");
                    long idDisciplina = jsonObject.getLong("idDisciplina");


                    Bibliografia Bibliografia = new Bibliografia(idLivro, idDisciplina);
                    bibliografias.add(Bibliografia);
                }
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }

        return bibliografias;
    }

    public boolean insertBibliografiaDBLocal(Context context, Bibliografia bibliografia) {


        DBHelperBibliografia helper = new DBHelperBibliografia(context);
        SQLiteDatabase db = helper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_ID_DISCIPLINA, bibliografia.getIdDisciplina());
        values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_ID_LIVRO, bibliografia.getIdLivro());
        values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_AUTOR, bibliografia.getAutor());
        values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_TITULO_LIVRO, bibliografia.getTituloLivro());
        values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_DISCIPLINA, bibliografia.getNomeDisciplina());
        values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_CURSO, bibliografia.getCurso());
        values.put(DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_IMAGE_LIVRO, bibliografia.getImageLivro());


        long newRowId = db.insert(Server.TABLE_BIBLIOGRAFIA, null, values);

        return (newRowId >= 1);

    }

    public  List<Bibliografia> selectBibliografiasLocalDB(Context context) {

        List<Bibliografia> bibliografias = new ArrayList<>();
        DBHelperBibliografia helper = new DBHelperBibliografia(context);
        SQLiteDatabase db = helper.getReadableDatabase();


        String[] projection = {

                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_ID_LIVRO,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_ID_DISCIPLINA,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_TITULO_LIVRO,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_AUTOR,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_CURSO,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_DISCIPLINA,
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_IMAGE_LIVRO,
        };


        String sortByAdd =
                DBHelperBibliografia.DBHelperBibliografiaColumns.COLUMN_NAME_CURSO + " ASC ";

        Cursor c = db.query(
                DBHelperBibliografia.DBHelperBibliografiaColumns.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortByAdd                                 // The sort order
        );

        if (c.moveToFirst()) {
            do {


                long idLivro = c.getLong(c.getColumnIndexOrThrow(DBHelperBibliografia.
                        DBHelperBibliografiaColumns.COLUMN_NAME_ID_LIVRO));
                long idDiscplina = c.getLong(c.getColumnIndexOrThrow(DBHelperBibliografia.
                        DBHelperBibliografiaColumns.COLUMN_NAME_ID_DISCIPLINA));
                String disciplina = c.getString(c.getColumnIndexOrThrow(DBHelperBibliografia.
                        DBHelperBibliografiaColumns.COLUMN_NAME_DISCIPLINA));
                String curso = c.getString(c.getColumnIndexOrThrow(DBHelperBibliografia.
                        DBHelperBibliografiaColumns.COLUMN_NAME_CURSO));
                String autor = c.getString(c.getColumnIndexOrThrow(DBHelperBibliografia.
                        DBHelperBibliografiaColumns.COLUMN_NAME_AUTOR));
                String livro = c.getString(c.getColumnIndexOrThrow(DBHelperBibliografia.
                        DBHelperBibliografiaColumns.COLUMN_NAME_TITULO_LIVRO));
                String imgLivro = c.getString(c.getColumnIndexOrThrow(DBHelperBibliografia.
                        DBHelperBibliografiaColumns.COLUMN_NAME_IMAGE_LIVRO));


                bibliografias.add(new Bibliografia(idLivro, idDiscplina, disciplina, livro, curso, autor, imgLivro));

            } while (c.moveToNext());
        }
        return bibliografias;
    }


}
