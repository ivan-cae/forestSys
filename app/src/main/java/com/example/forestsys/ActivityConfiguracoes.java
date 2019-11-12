package com.example.forestsys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static com.example.forestsys.ActivityLogin.nomeEmpresaPref;


public class ActivityConfiguracoes extends AppCompatActivity {
    public static final int PERMISSON_REQUEST = 0;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "nomeEmpresaPref";


    private EditText editNomeEmpresa;
    private Button botaoSalvar;
    private ImageView imagemLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        editNomeEmpresa = findViewById(R.id.edit_nome_empresa);
        botaoSalvar = (Button) findViewById(R.id.botao_salvar_configs);
        imagemLogo = findViewById(R.id.imagem_logo);

        nomeEmpresaPref = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("nomeEmpresaPref", "Plantar Siderurgica S/A");

        editNomeEmpresa.setText(nomeEmpresaPref);


        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putString("nomeEmpresaPref", editNomeEmpresa.getText().toString()).commit();
                voltarAoinicio();
            }
        });
    }

    public void voltarAoinicio(){
        AlertDialog dialog = new AlertDialog.Builder(ActivityConfiguracoes.this)
                .setTitle("ERRO")
                .setMessage("Você será direcionado para a tela de login")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(ActivityConfiguracoes.this, ActivityLogin.class);
                        startActivity(it);
                    }
                }).create();
        dialog.show();
    }

   /* public void salvarDados() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, editNomeEmpresa.getText().toString());

        editor.commit();
    }

    public void carregarDados() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_APPEND);
        nomeEmpresaPref = sharedPreferences.getString(TEXT, "Plantar Siderurgica S/A");
    }

    public void atualizarViews() {
        editNomeEmpresa.setText(nomeEmpresaPref);
    }*/
}