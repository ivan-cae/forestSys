package com.example.forestsys;

import android.content.Intent;
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


public class ActivityLogin extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{


    public static String nomeEmpresaPref;
    public static ClasseUsers usuarioLogado = null;



    private String nomeUsuario;
    private String senhaUsuario;
    private ViewModelUsers viewModelUsers;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Boolean primeiraVez = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("primeiraVez", true);

        if (primeiraVez) {
            startActivity(new Intent(ActivityLogin.this, ActivityConfiguracoes.class));
            Toast.makeText(ActivityLogin.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("primeiraVez", false).commit();
*/

        nomeEmpresaPref = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("nomeEmpresaPref", "Plantar Siderurgica S/A");

        viewModelUsers = ViewModelProviders.of(this).get(ViewModelUsers.class);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.botao_login);
        final ImageButton configButton = findViewById(R.id.botao_config);

        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ActivityLogin.this, v);
                popup.setOnMenuItemClickListener(ActivityLogin.this);
                popup.inflate(R.menu.menu_login_lateral);
                popup.show();
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
            }
        });



    }

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
    }
}