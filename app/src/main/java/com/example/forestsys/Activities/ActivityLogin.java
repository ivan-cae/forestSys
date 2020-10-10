package com.example.forestsys.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.R;
import com.example.forestsys.Classes.GGF_USUARIOS;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ActivityLogin extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static String nomeEmpresaPref;
    public static GGF_USUARIOS usuarioLogado = null;

    private String nomeUsuario;
    private String senhaUsuario;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ImageButton configButton;
private Configs configs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        if (it.hasExtra("fechar")) {
            finishAffinity();
            moveTaskToBack(true);
        }

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.botao_login);
        configButton = findViewById(R.id.botao_config);
        checarPermissaodeLocalizacao();


        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it2 = new Intent(ActivityLogin.this, ActivityConfiguracoes.class);
                startActivity(it2);
            }
        });

        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();

        nomeEmpresaPref = null;

        configs = dao.selecionaConfigs();

        if (configs.getNomeEmpresa() != null) nomeEmpresaPref = configs.getNomeEmpresa();

        if (nomeEmpresaPref == null) nomeEmpresaPref = "GELF";

        if (it.hasExtra("deslogar")) {
            usuarioLogado = null;
        }
        else{
        if(conectadoAoServidor()==true) {
            try {
                baseDeDados.populaBdComJson(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
        List<GGF_USUARIOS> listaUsers = dao.todosUsers();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tem = false;
                nomeUsuario = usernameEditText.getText().toString();
                senhaUsuario = passwordEditText.getText().toString();
                usuarioLogado = dao.valida(nomeUsuario, senhaUsuario);
                if (usuarioLogado != null) tem = true;
                if (tem == false) {
                    Toast.makeText(ActivityLogin.this, "Credenciais Inválidas", Toast.LENGTH_SHORT).show();
                } else {
                    Intent it = new Intent(ActivityLogin.this, ActivityMain.class);
                    startActivity(it);
                }
            }
        });

        /*botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ActivityLogin.this)
                        .setTitle("SAIR")
                        .setMessage("Deseja fechar o aplicativo ?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent it = new Intent(ActivityLogin.this, ActivityLogin.class);
                                boolean fechou = true;
                                it.putExtra("fechar", fechou);
                                startActivity(it);
                            }
                        })
                        .setNegativeButton("NÃO",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
                        })
                        .create()
                        .show();
            }
        });*/
    }


    private boolean conectadoAoServidor() {
        try{
            URL myUrl = new URL(configs.getEndereçoHost()+":"+configs.getPortaDeComunicacao()+"/");
            URLConnection connection = myUrl.openConnection();
            connection.connect();
            return true;
        } catch (Exception e) {
            AlertDialog dialog = new AlertDialog.Builder(ActivityLogin.this)
                    .setTitle("Erro de conexão com o servidor:")
                    .setMessage(String.valueOf(e))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog.show();
            return false;
        }
    }

    //checa as permissões de localização
    //retorna true se a permissão for concedida e false se não for
    public boolean checarPermissaodeLocalizacao() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Fornecer permissão")
                        .setMessage("Fornecer permissão para localizar dispositivo")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(ActivityLogin.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(ActivityLogin.this)
                .setTitle("SAIR")
                .setMessage("Deseja fechar o aplicativo ?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(ActivityLogin.this, ActivityLogin.class);
                        boolean fechou = true;
                        it.putExtra("fechar", fechou);
                        startActivity(it);
                    }
                })
                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create()
                .show();
    }
}