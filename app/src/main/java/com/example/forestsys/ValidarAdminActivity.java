package com.example.forestsys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ValidarAdminActivity extends AppCompatActivity {

    private String nomeUsuario;
    private String senhaUsuario;
    private UsersViewModel usersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_admin);

        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        final EditText usernameEditText = findViewById(R.id.username_validar);
        final EditText passwordEditText = findViewById(R.id.password_validar);
        final Button loginButton = findViewById(R.id.botao_login_validar);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users teste;
                nomeUsuario = usernameEditText.getText().toString();
                senhaUsuario = passwordEditText.getText().toString();
                teste = usersViewModel.consultaAdmin(nomeUsuario, senhaUsuario);
                if (teste != null) {
                    Toast.makeText(ValidarAdminActivity.this, "Usuário Validado! ", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(ValidarAdminActivity.this, CadastrarUsuarioActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(ValidarAdminActivity.this, "Credenciais Inválidas", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}