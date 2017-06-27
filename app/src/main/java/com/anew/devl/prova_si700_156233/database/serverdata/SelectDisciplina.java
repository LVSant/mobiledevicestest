package com.anew.devl.prova_si700_156233.database.serverdata;

import android.os.AsyncTask;

import com.anew.devl.prova_si700_156233.model.Disciplina;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Leonardo
 */

public class SelectDisciplina extends AsyncTask<Void, Void, String> {


    private String[] fields;
    private String[] values;

    public SelectDisciplina(String[] fields, String[] values) {

        this.fields = fields;
        this.values = values;
    }

    @Override
    protected String doInBackground(Void... objects) {
        HttpURLConnection httpURLConnection = null;
        try {
            /*
               Preparando os dados para envio via post
             */
            String data =
                    URLEncoder.encode("database", "UTF-8") + "=" +
                            URLEncoder.encode(Server.DATABASE_NAME, "UTF-8") + "&" +
                            URLEncoder.encode("table", "UTF-8") + "=" +
                            URLEncoder.encode(Server.TABLE_DISCIPLINA, "UTF-8");




            /*
               Abrindo uma conexão com o servidor
             */
            URL url = new URL(Server.SERVER_URL_SELECT);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            /*
               Enviando os dados via post
             */
            OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
            wr.write(data);
            wr.flush();

            /*
                Lendo a resposta do servidor
             */
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(httpURLConnection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();

        } catch (IOException exception) {
            exception.printStackTrace();
            return "Exception: " + exception.getMessage();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }


}
