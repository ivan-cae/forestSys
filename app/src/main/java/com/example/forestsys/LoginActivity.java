package com.example.forestsys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import static com.example.forestsys.ConfiguracoesActivity.SHARED_PREFS;
import static com.example.forestsys.ConfiguracoesActivity.TEXT;

public class LoginActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    public static Users usuarioLogado;
    public static String nomeEmpresaPref;

    private String nomeUsuario;
    private String senhaUsuario;
    private UsersViewModel usersViewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.botao_login);
        final ImageButton configButton = findViewById(R.id.botao_config);
        carregarDados();

        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(LoginActivity.this, v);
                popup.setOnMenuItemClickListener(LoginActivity.this);
                popup.inflate(R.menu.menu_login_lateral);
                popup.show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeUsuario = usernameEditText.getText().toString();
                senhaUsuario = passwordEditText.getText().toString();
                usuarioLogado = usersViewModel.consulta(nomeUsuario, senhaUsuario);
                if(usuarioLogado!= null) {
                    Toast.makeText(LoginActivity.this, "Bem vindo(a) " +
                            usuarioLogado.getNome(), Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(it);
                }else{
                    Toast.makeText(LoginActivity.this, "Credenciais Inválidas", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cadastrar_conta:
                Intent it1 = new Intent(this, ValidarAdminActivity.class);
                startActivity(it1);
                return true;
            case R.id.config_login:
                Intent it2 = new Intent(this, ConfiguracoesActivity.class);
                startActivity(it2);
            default:
                return false;
        }
    }

    public void carregarDados() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        nomeEmpresaPref = sharedPreferences.getString(TEXT, "Plantar Siderurgica S/A");
    }
}
