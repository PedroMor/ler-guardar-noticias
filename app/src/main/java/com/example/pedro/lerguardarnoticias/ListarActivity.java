package com.example.pedro.lerguardarnoticias; /** Pedro Moreira A030425 **/

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListarActivity extends Activity {
    private ProgressDialog progressDialog;
    private TextView tvTituloListar;
    private ListView lvTitulos;
    public static int tamanhoLista;
    private ArrayList<String> titulos = new ArrayList<>();
    private ArrayList<String> conteudo = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private int opcao;
    HandlerBaseDados handlerBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        handlerBD = new HandlerBaseDados(this, null);
        progressDialog = new ProgressDialog(ListarActivity.this);
        Intent intent = getIntent();
        tamanhoLista = intent.getIntExtra("tamanhoLista", 5);
        opcao = intent.getIntExtra("opcao", 0);
        switch(opcao) {
            case MainActivity.INTERNET:
                progressDialog.setMessage("A carregar notícias");
                progressDialog.show();
                progressDialog.setCancelable(false);
                recarregarAsync();
                break;
            case MainActivity.OFFLINE:
                lerBaseDados();
                break;
        }
    }

    public void lerBaseDados() {
        Cursor c = handlerBD.getCursor();
        int titleIndex = c.getColumnIndex(HandlerBaseDados.COLUMN_TITULOS);
        int contentIndex = c.getColumnIndex(HandlerBaseDados.COLUMN_CONTEUDO);
        if (c.moveToFirst()) {
            do {
                titulos.add(c.getString(titleIndex));
                conteudo.add(c.getString(contentIndex));
            } while (c.moveToNext());
            c.close();
        }
        carregarLista();
    }

    public void recarregarAsync() {
        Carregar task = new Carregar();
        try {
            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Carregamento das notícias falhou", Toast.LENGTH_LONG).show();
        }
    }

    private class Carregar extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            String result = "";
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                JSONArray ja = new JSONArray(result);
                if (ja.length() < tamanhoLista) {
                    tamanhoLista = ja.length();
                }
                handlerBD.clearBD();
                int count = 0;
                for (int i = 0; count < tamanhoLista; i++) {
                    result = "";
                    url = new URL("https://hacker-news.firebaseio.com/v0/item/" +
                            ja.getString(i) + ".json?print=pretty");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                    JSONObject jo = new JSONObject(result);

                    if (!jo.isNull("title") && !jo.isNull("url")) {
                        result = "";
                        url = new URL(jo.getString("url"));
                        urlConnection = (HttpURLConnection) url.openConnection();
                        reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        while ((line = reader.readLine()) != null) {
                            result += (line + "\n");
                        }
                        handlerBD.insertNoticia(jo.getString("title"), result);
                        count++;
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lerBaseDados();
        }
    }

    public void carregarLista() {
        tvTituloListar = (TextView) findViewById(R.id.tvTituloListar);
        lvTitulos = (ListView) findViewById(R.id.lvTitulos);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titulos);
        lvTitulos.setAdapter(arrayAdapter);
        lvTitulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NoticiaActivity.class);
                intent.putExtra("content", conteudo.get(position));
                startActivity(intent);
            }
        });
        progressDialog.hide();
        if (opcao == MainActivity.OFFLINE) {
            tvTituloListar.setText("Notícias guardadas na Cache");
            if (titulos.size() == 0) {
                tvTituloListar.setText("Base de dados vazia!");
            }
        }
    }
}