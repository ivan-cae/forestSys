package com.example.forestsys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;



public class ActivityLogin extends AppCompatActivity{ //implements PopupMenu.OnMenuItemClickListener{

    private final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static String nomeEmpresaPref;
    public static String preferenceLogo;

    public static ClasseUsers usuarioLogado = null;


    private ImageView imageView;
    private String nomeUsuario;
    private String senhaUsuario;
    private ViewModelUsers viewModelUsers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.botao_login);
        final ImageButton configButton = findViewById(R.id.botao_config);
        checarPermissaodeLocalizacao();
        imageView = findViewById(R.id.imagem_login);

        viewModelUsers = ViewModelProviders.of(this).get(ViewModelUsers.class);


        nomeEmpresaPref = getSharedPreferences("nomeEmpresa", MODE_PRIVATE)
                .getString("nomeEmpresaPref", "Plantar Siderurgica S/A");

        preferenceLogo = getSharedPreferences("imagemLogo", MODE_PRIVATE)
                .getString("preferenceLogo", null);

        if(preferenceLogo == null) imageView.setImageResource(R.mipmap.ic_login_round);
        else imageView.setImageURI(Uri.parse(preferenceLogo));





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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeUsuario = usernameEditText.getText().toString();
                senhaUsuario = passwordEditText.getText().toString();
                usuarioLogado = viewModelUsers.consulta(nomeUsuario, senhaUsuario);
                if(usuarioLogado!= null) {
                    Toast.makeText(ActivityLogin.this, "Bem vindo(a) " +
                            usuarioLogado.getNome(), Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(ActivityLogin.this, ActivityMain.class);
                    startActivity(it);
                }else if(usuarioLogado == null){
                    Toast.makeText(ActivityLogin.this, "Credenciais Inválidas", Toast.LENGTH_SHORT).show();
                    return;

                }
            }});
    }

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