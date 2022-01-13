package com.example.forestsys.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.ClienteWeb;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Assets.Ferramentas;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.Classes.ClassesAuxiliares.FOREST_LOG;
import com.example.forestsys.R;
import com.example.forestsys.Classes.GGF_USUARIOS;

import org.json.JSONException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

import static com.example.forestsys.Activities.ActivityInicializacao.conectado;
import static com.example.forestsys.Activities.ActivityInicializacao.informacaoDispositivo;
import static com.example.forestsys.Activities.ActivityInicializacao.pulaSinc;
import static com.example.forestsys.Assets.ClienteWeb.contadorDeErros;
import static com.example.forestsys.Assets.ClienteWeb.erroNoOracle;
import static com.example.forestsys.Assets.ClienteWeb.finalizouSinc;
import static com.example.forestsys.Activities.ActivityInicializacao.HOST_PORTA;
import static com.example.forestsys.Activities.ActivityInicializacao.nomeEmpresaPref;

public class ActivityLogin extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static GGF_USUARIOS usuarioLogado = null;

    private String nomeUsuario;
    private String senhaUsuario;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ImageButton configButton;
    private DAO dao;
    private BaseDeDados baseDeDados;
    private Intent it;
    private Configs configs;

    private ProgressDialog dialogoProgresso;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        it = getIntent();
        if (it.hasExtra("fechar")) {
            finishAffinity();
            moveTaskToBack(true);
        }
        dialogoProgresso = new ProgressDialog(ActivityLogin.this);
        dialogoProgresso.setTitle("Sincronizando com o servidor");
        dialogoProgresso.setMessage("Aguarde um momento...");
        //dialogoProgresso.setIndeterminate(true);
        //dialogoProgresso.setIndeterminateDrawable(getResources().getDrawable(R.mipmap.icone_progresso));

        dialogoProgresso.setCancelable(false);
        dialogoProgresso.setCanceledOnTouchOutside(false);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.botao_login);
        configButton = findViewById(R.id.botao_config);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        if (dao.selecionaConfigs() != null) {
            configs = dao.selecionaConfigs();
            HOST_PORTA = "http://" + configs.getEndereçoHost() + ":" + configs.getPortaDeComunicacao() + "/";
            nomeEmpresaPref = configs.getNomeEmpresa();
        }
        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                it = new Intent(ActivityLogin.this, ActivityConfiguracoes.class);
                startActivity(it);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
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
                    Ferramentas ferramentas = new Ferramentas();

                    FOREST_LOG registroLog = new FOREST_LOG(ferramentas.dataHoraMinutosSegundosAtual(), informacaoDispositivo,
                            usuarioLogado.getEMAIL(), "Atividades", "Acessou", null);

                    dao.insert(registroLog);
                    it = new Intent(ActivityLogin.this, ActivityMain.class);

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
                                it = new Intent(ActivityLogin.this, ActivityLogin.class);
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
        if (it.hasExtra("deslogar")) {
            usuarioLogado = null;
        }

        if (pulaSinc == false) {
            checaFimDaSinc();
        } else {
            AlertDialog dialog = new AlertDialog.Builder(ActivityLogin.this)
                    .setTitle("Não Foram Encontradas Configurações Salvas")
                    .setMessage("A tela de configurações será aberta")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent it = new Intent(ActivityLogin.this, ActivityConfiguracoes.class);
                            startActivity(it);
                        }
                    }).create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private void checaFimDaSinc() {
        if (temRede() == true) {

            dialogoProgresso.show();

            new AsyncTask<Void, Void, Void>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    while (finalizouSinc == false) {
                    }
                    return null;
                }

                @SuppressLint("StaticFieldLeak")
                @Override
                protected void onPostExecute(Void aVoid) {
                    it = getIntent();

                    if (conectado == true && erroNoOracle == false && !it.hasExtra("fechar")) {
                        dialogoProgresso.dismiss();
                        String s = "Sincronizado com " + HOST_PORTA;
                        Toast.makeText(ActivityLogin.this, s, Toast.LENGTH_LONG).show();
                    }
                    if (conectado == false) {
                        dialogoProgresso.dismiss();
                        @SuppressLint("StaticFieldLeak") AlertDialog dialog = new AlertDialog.Builder(ActivityLogin.this)
                                .setTitle("Erro de conexão com o host.")
                                .setMessage("Possíveis causas:\n" +
                                        "Dispositivo offline\n" +
                                        "Servidor offline\n" +
                                        "Host Não encontrado\n" +
                                        "Porta não encontrada")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    }
                    if (conectado == true && erroNoOracle == true) {
                        dialogoProgresso.dismiss();
                        @SuppressLint("StaticFieldLeak") AlertDialog dialog = new AlertDialog.Builder(ActivityLogin.this)
                                .setTitle("Erro!")
                                .setMessage("Falha de conexão com o banco de dados, favor comunicar ao responsável.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    }
                    super.onPostExecute(aVoid);
                }
            }.execute();

        } else {
            AlertDialog dialog = new AlertDialog.Builder(ActivityLogin.this)
                    .setTitle("Erro de rede.")
                    .setMessage("Não há conexão com a rede.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog.show();
        }

        checarPermissaodeLocalizacao();
    }

    private boolean temRede() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
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
                        .setTitle("Fornecer permissão.")
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
                        it = new Intent(ActivityLogin.this, ActivityLogin.class);
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

    @Override
    protected void onDestroy() {
        dialogoProgresso.dismiss();
        super.onDestroy();
    }
}