package com.example.forestsys.Activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.ClienteWeb;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.R;

import org.json.JSONException;

import java.io.IOException;

import static com.example.forestsys.Assets.ClienteWeb.finalizouSinc;

public class ActivityInicializacao extends AppCompatActivity {
    public static String HOST_PORTA;
    public static String nomeEmpresaPref;
    public static boolean conectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicializacao);
        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();

        if (dao.selecionaConfigs() == null) {
            dao.insert(new Configs(1, "GELF", "http://sateliteinfo.ddns.net", "3333"));
        }
        Configs configs = dao.selecionaConfigs();
        HOST_PORTA = configs.getEndereçoHost() + ":" + configs.getPortaDeComunicacao() + "/";
        nomeEmpresaPref = configs.getNomeEmpresa();

        ClienteWeb clienteWeb = new ClienteWeb(getApplicationContext());

        if(temRede()==true){
        try {
            clienteWeb.sincronizaWebService();
        } catch (JSONException | IOException e) {
            finalizouSinc = true;
            conectado = false;
            e.printStackTrace();
        }}else{
            finalizouSinc = true;
            conectado = false;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent it = new Intent(ActivityInicializacao.this, ActivityLogin.class);

                startActivity(it);
                finish();
            }
        }, 10000);
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

    @Override
    public void onBackPressed() {
    }
}