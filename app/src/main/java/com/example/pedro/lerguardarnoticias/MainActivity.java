package com.example.pedro.lerguardarnoticias; /** Pedro Moreira A030425 **/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    EditText etNumnoticias;
    Button btnCarregar, btnLerBaseDados;
    TextView tvNumnoticias;
    public static final int INTERNET = 0;
    public static final int OFFLINE = 1;
    protected void mudarDeEcra(Class<?> subAtividade, Integer opcao, Integer tamanhoLista) {
        Intent x = new Intent(this, subAtividade);
        x.putExtra("opcao", opcao);
        x.putExtra("tamanhoLista", tamanhoLista);
        startActivity(x);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvNumnoticias = (TextView) findViewById(R.id.tvNumnoticias);
        etNumnoticias = (EditText) findViewById(R.id.etNumnoticias);
        btnCarregar = (Button) findViewById(R.id.btnCarregar);
        btnLerBaseDados = (Button) findViewById(R.id.btnLerBaseDados);
    }
    public void onClick(View v) {
        final int id = v.getId();
        int opcao = 0;
        switch (id) {
            case R.id.btnCarregar:
                opcao = INTERNET;
                break;
            case R.id.btnLerBaseDados:
                opcao = OFFLINE;
                break;
        }
        mudarDeEcra(ListarActivity.class, opcao, Integer.parseInt(etNumnoticias.getText().toString()));
    }
}