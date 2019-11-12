package com.example.forestsys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityValidarAdmin extends AppCompatActivity {

    private String nomeUsuario;
    private String senhaUsuario;
    private ViewModelUsers viewModelUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_admin);

        viewModelUsers = ViewModelProviders.of(this).get(ViewModelUsers.class);
        final EditText usernameEditText = findViewById(R.id.username_validar);
        final EditText passwordEditText = findViewById(R.id.password_validar);
        final Button loginButton = findViewById(R.id.botao_login_validar);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClasseUsers teste;
                nomeUsuario = usernameEditText.getText().toString();
                senhaUsuario = passwordEditText.getText().toString();
                teste = viewModelUsers.consultaAdmin(nomeUsuario, senhaUsuario);
                if (teste != null) {
                    Toast.makeText(ActivityValidarAdmin.this, "Usuário Validado! ", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(ActivityValidarAdmin.this, ActivityCadastrarUsuario.class);
                    startActivity(it);
                } else {
                    Toast.makeText(ActivityValidarAdmin.this, "Credenciais Inválidas", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}