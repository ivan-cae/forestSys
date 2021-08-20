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
import java.text.ParseException;

import static com.example.forestsys.Assets.ClienteWeb.finalizouSinc;

public class ActivityInicializacao extends AppCompatActivity {
    public static String HOST_PORTA;
    public static String nomeEmpresaPref;
    public static boolean conectado;
    public static boolean pulaSinc;
    private Configs configs;

    public static String informacaoDispositivo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicializacao);
        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        DAO dao = baseDeDados.dao();
        informacaoDispositivo = android.os.Build.MODEL + " " +android.os.Build.SERIAL;


        pulaSinc = false;
        if (dao.selecionaConfigs() == null) {
            pulaSinc = true;
        }else {
            configs = dao.selecionaConfigs();
            HOST_PORTA = "http://" + configs.getEndereçoHost() + ":" + configs.getPortaDeComunicacao() + "/";
            nomeEmpresaPref = configs.getNomeEmpresa();
        }

        ClienteWeb clienteWeb = null;
        try {
            clienteWeb = new ClienteWeb(getApplicationContext());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(temRede()==true && pulaSinc==false){
        try {
            clienteWeb.sincronizaWebService();
        } catch (Exception e) {
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