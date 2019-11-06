package com.example.forestsys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editMatricula;
    private EditText editCargo;
    private Spinner spinnerNivel;
    private EditText editLogin;
    private EditText editSenha;
    private Button botaoCadastrar;
    private UsersViewModel usersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        editNome = findViewById(R.id.edit_nome);
        editMatricula = findViewById(R.id.edit_matricula);
        editCargo = findViewById(R.id.edit_cargo);
        spinnerNivel = findViewById(R.id.spinner_nivel_acesso);
        editLogin = findViewById(R.id.edit_login);
        editSenha = findViewById(R.id.edit_senha);
        botaoCadastrar = findViewById(R.id.botao_cadastrar_conta);

        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);


        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editNome.getText().toString().isEmpty() || editMatricula.getText().toString().isEmpty() || editCargo.getText().toString().isEmpty() ||
                        editLogin.getText().toString().isEmpty() || editSenha.getText().toString().isEmpty()) {
                    AlertDialog dialog = new AlertDialog.Builder(CadastrarUsuarioActivity.this)
                            .setTitle("ERRO")
                            .setMessage("Favor Preencher todos os campos marcados")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                    editNome.setError("");
                    editMatricula.setError("");
                    editCargo.setError("");
                    editLogin.setError("");
                    editSenha.setError("");
                    return;
                }
                int j = 0;
                if(spinnerNivel.getSelectedItem().toString() == "ADMIN") j=1;
                else j = 2;
                Enumeraveis.nivelAcesso i = Enumeraveis.nivelAcesso.getNivelAcesso(j);
                Users novo = new Users(editNome.getText().toString(), editMatricula.getText().toString(),
                        editCargo.getText().toString(), i,
                            editLogin.getText().toString(), editSenha.getText().toString());

                if (usersViewModel.consultaLogin(novo.getLogin()) != null ||
                        usersViewModel.consultaMatricula(novo.getMatricula()) != null) {
                    AlertDialog dialog = new AlertDialog.Builder(CadastrarUsuarioActivity.this)
                            .setTitle("ERRO")
                            .setMessage("Login ou Matricula já cadastrados, favor alterar os valores.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    dialog.show();
                    return;
                }
                usersViewModel.insert(novo);
                Toast.makeText(CadastrarUsuarioActivity.this, "Usuário "+novo.getLogin()+"Cadastrado!", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(CadastrarUsuarioActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });
    }
}