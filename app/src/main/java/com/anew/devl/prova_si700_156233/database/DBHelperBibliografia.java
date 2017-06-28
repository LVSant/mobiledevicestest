/**
 * Created by devl on 4/1/17.
 */
package com.anew.devl.prova_si700_156233.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.List;

public class DBHelperBibliografia extends SQLiteOpenHelper {
    public static class DBHelperBibliografiaColumns implements BaseColumns {
        public static final String TABLE_NAME = "Bibliografia";
        public static final String COLUMN_NAME_ID_LIVRO = "idLivro";
        public static final String COLUMN_NAME_ID_DISCIPLINA = "idDisciplina";
        public static final String COLUMN_NAME_TITULO_LIVRO = "Livro";
        public static final String COLUMN_NAME_AUTOR = "Autpr";
        public static final String COLUMN_NAME_DISCIPLINA = "Disciplina";
        public static final String COLUMN_NAME_CURSO = "Curso";

    }


    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBHelperBibliografiaColumns.TABLE_NAME + " (" +
                    DBHelperBibliografiaColumns.COLUMN_NAME_ID_DISCIPLINA + " INTEGER ," +
                    DBHelperBibliografiaColumns.COLUMN_NAME_ID_LIVRO + " INTEGER ," +
                    DBHelperBibliografiaColumns.COLUMN_NAME_TITULO_LIVRO + " TEXT," +
                    DBHelperBibliografiaColumns.COLUMN_NAME_AUTOR+ " TEXT," +
                    DBHelperBibliografiaColumns.COLUMN_NAME_DISCIPLINA+ " TEXT," +
                    DBHelperBibliografiaColumns.COLUMN_NAME_CURSO+ " TEXT )"
                    ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBHelperBibliografiaColumns.TABLE_NAME;
    private static final String SQL_DELETE_IDS =
            "DELETE FROM " + DBHelperBibliografiaColumns.TABLE_NAME + " WHERE _id IN (";
    private static final String SQL_DELETE_ID =
            "DELETE FROM " + DBHelperBibliografiaColumns.TABLE_NAME + " WHERE _id = ";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Bibliografia.db";

    public DBHelperBibliografia(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_DELETE_ENTRIESCREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDropAll(SQLiteDatabase db) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void dropIds(SQLiteDatabase db, List<Long> ids) {
        String idsCommaSeparated = TextUtils.join(",", ids);
        db.execSQL(SQL_DELETE_IDS + idsCommaSeparated + ")");
        //onCreate(db);
    }

    public void dropId(SQLiteDatabase db, long idLivro, long idDisciplina) {

        //db.execSQL(SQL_DELETE_ID + id);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

