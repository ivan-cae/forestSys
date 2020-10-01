package com.example.forestsys.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.R;


public class ActivityConfiguracoes extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 0;

    private EditText editNomeEmpresa;
    private Button botaoSalvar;
    private Button botaoVoltar;
    private EditText editHost;
    private EditText editPorta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        editNomeEmpresa = findViewById(R.id.configs_edit_nome_empresa);
        editHost = findViewById(R.id.configs_edit_host);
        editPorta = findViewById(R.id.configs_edit_porta);
        botaoSalvar = findViewById(R.id.botao_salvar_configs);
        botaoVoltar = findViewById(R.id.botao_configs_voltar);

        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();
        Configs configs = dao.selecionaConfigs();

        if(configs != null) {
            if(configs.getNomeEmpresa()!=null) editNomeEmpresa.setText(configs.getNomeEmpresa());
            else editNomeEmpresa.setText("GELF");
            if(configs.getEndereçoHost() != null) editHost.setText(configs.getEndereçoHost());
            if(configs.getPortaDeComunicacao() != null) editPorta.setText(configs.getPortaDeComunicacao());
        }

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empresa = null;
                String host = null;
                String porta = null;
                if(editNomeEmpresa.length()>0) empresa = editNomeEmpresa.getText().toString();
                if(editHost.length()>0) host = editHost.getText().toString();
                if(editPorta.length()>0) porta = editPorta.getText().toString();

                dao.insert(new Configs(1, empresa, host, porta));
                Toast.makeText(getApplicationContext(), "Configurações Salvas com Sucesso!", Toast.LENGTH_LONG).show();
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ActivityConfiguracoes.this)
                        .setTitle("Voltar")
                        .setMessage("Deseja voltar a tela de login?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent it = new Intent(ActivityConfiguracoes.this, ActivityLogin.class);
                                startActivity(it);
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }

    //SObrescrita do método onBackPressed nativo do Android para que não execute nenhuma função
    @Override
    public void onBackPressed() {
    }
}