package com.example.forestsys.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.forestsys.BaseDeDados;
import com.example.forestsys.DAO;
import com.example.forestsys.R;
import com.example.forestsys.viewModels.ViewModelUsers;
import com.example.forestsys.classes.GGF_USUARIOS;

import java.util.List;

public class ActivityLogin extends AppCompatActivity{ //implements PopupMenu.OnMenuItemClickListener{

    private final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static String nomeEmpresaPref;
    public static String preferenceLogo;
    private ImageButton botaoVoltar;
    public static GGF_USUARIOS usuarioLogado = null;


    private ImageView imageViewLogo;
    private ImageView imageViewRodape;
    private String nomeUsuario;
    private String senhaUsuario;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ImageButton configButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        if(it.hasExtra("fechar")){
            finishAffinity();
            moveTaskToBack(true);
        }

        if(it.hasExtra("deslogar")){
            usuarioLogado=null;
        }

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.botao_login);
        configButton = findViewById(R.id.botao_config);
       // botaoVoltar = findViewById(R.id.botao_login_voltar);
        checarPermissaodeLocalizacao();
        imageViewLogo = findViewById(R.id.imagem_login);

        nomeEmpresaPref = getSharedPreferences("nomeEmpresa", MODE_PRIVATE)
                .getString("nomeEmpresaPref", "GELF");

        preferenceLogo = getSharedPreferences("imagemLogo", MODE_PRIVATE)
                .getString("preferenceLogo", null);

        if(preferenceLogo == null) imageViewLogo.setImageResource(R.mipmap.logo_gelf_completo);
        else imageViewLogo.setImageURI(Uri.parse(preferenceLogo));

        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*PopupMenu popup = new PopupMenu(ActivityLogin.this, v);
                popup.setOnMenuItemClickListener(ActivityLogin.this);
                popup.inflate(R.menu.menu_login_lateral);
                popup.show();*/
                Intent it2 = new Intent(ActivityLogin.this, ActivityConfiguracoes.class);
                startActivity(it2);
            }
        });

        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();
        List<GGF_USUARIOS> listaUsers = dao.todosUsers();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tem = false;
                nomeUsuario = usernameEditText.getText().toString();
                senhaUsuario = passwordEditText.getText().toString();
                usuarioLogado = dao.valida(nomeUsuario, senhaUsuario);
                if(usuarioLogado!=null) tem=true;
                if(tem==false){
                    Toast.makeText(ActivityLogin.this, "Credenciais Inválidas", Toast.LENGTH_SHORT).show();
                }else{
                    Intent it = new Intent(ActivityLogin.this, ActivityMain.class);
                    startActivity(it);
                }
            }});

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


    //checa as permissões de localização
    //retorna true se a permissão for concedida e false se não for
    public boolean checarPermissaodeLocalizacao(){
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
                .setNegativeButton("NÃO",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                })
                .create()
                .show();
        }

/*
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cadastrar_conta:
                Intent it1 = new Intent(this, ActivityValidarAdmin.class);
                startActivity(it1);
                return true;
            case R.id.config_login:
                Intent it2 = new Intent(this, ActivityConfiguracoes.class);
                startActivity(it2);
            default:
                return false;
        }
    }*/
}