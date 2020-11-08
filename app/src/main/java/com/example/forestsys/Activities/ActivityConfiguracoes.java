package com.example.forestsys.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.NDSpinner;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.R;


public class ActivityConfiguracoes extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 0;

    private EditText editNomeEmpresa;
    private Button botaoSalvar;
    private Button botaoVoltar;
    private EditText editHost;
    private EditText editPorta;

    private String[] opçõesPermanencia = new String[]{"15", "30", "45",
            "60", "75", "90"};
    private NDSpinner spinnerPermanencia;
    private Integer posicaoSpinnerPermanencia;
    private Integer diasPermanencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        editNomeEmpresa = findViewById(R.id.configs_edit_nome_empresa);
        editHost = findViewById(R.id.configs_edit_host);
        editPorta = findViewById(R.id.configs_edit_porta);
        botaoSalvar = findViewById(R.id.botao_salvar_configs);
        botaoVoltar = findViewById(R.id.botao_configs_voltar);
        spinnerPermanencia = findViewById(R.id.configs_spinner_permanecia_dos_dados);

        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();
        Configs configs = dao.selecionaConfigs();

        if (configs.getNomeEmpresa() != null) editNomeEmpresa.setText(configs.getNomeEmpresa());
        else editNomeEmpresa.setText("GELF");
        if (configs.getEndereçoHost() != null) editHost.setText(configs.getEndereçoHost());
        if (configs.getPortaDeComunicacao() != null)
            editPorta.setText(configs.getPortaDeComunicacao());

        ArrayAdapter adapterPioridade = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, opçõesPermanencia);
        adapterPioridade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPermanencia.setAdapter(adapterPioridade);

        spinnerPermanencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicaoSpinnerPermanencia=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerPermanencia.setSelection(configs.getPosicaoNoSpinner());


        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ActivityConfiguracoes.this)
                        .setTitle("Salvar essas informações irá reiniciar o aplicativo")
                        .setMessage("Deseja continuar?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String empresa = null;
                                String host = null;
                                String porta = null;
                                if (editNomeEmpresa.length() > 0)
                                    empresa = editNomeEmpresa.getText().toString();
                                if (editHost.length() > 0) host = editHost.getText().toString();
                                if (editPorta.length() > 0) porta = editPorta.getText().toString();
                                try {
                                    dao.insert(new Configs(1, empresa, host, porta, Integer.valueOf(opçõesPermanencia[posicaoSpinnerPermanencia]), posicaoSpinnerPermanencia));
                                    Intent it = new Intent(ActivityConfiguracoes.this, ActivityInicializacao.class);
                                    startActivity(it);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    AlertDialog dialogoErro = new AlertDialog.Builder(ActivityConfiguracoes.this)
                                            .setTitle("Erro")
                                            .setMessage("Houve um problema ao salvar as configurações.")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                }
                                            }).create();
                                    dialogoErro.show();
                                }
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();
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
                                it.putExtra("deslogar", true);
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