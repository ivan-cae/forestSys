package com.example.forestsys.Activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.forestsys.Assets.BaseDeDados;
import com.example.forestsys.Assets.DAO;
import com.example.forestsys.Classes.ClassesAuxiliares.Configs;
import com.example.forestsys.R;

public class ActivityInicializacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicializacao);
        BaseDeDados baseDeDados = BaseDeDados.getInstance(getApplicationContext());

        baseDeDados.populaBdComWebService(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent it = new Intent(ActivityInicializacao.this, ActivityLogin.class);

                startActivity(it);
                finish();
            }
        }, 10000);
    }

    @Override
    public void onBackPressed() {
    }
}