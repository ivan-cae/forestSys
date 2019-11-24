package com.example.forestsys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ActivityInicializacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicializacao);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(ActivityInicializacao.this, ActivityLogin.class);


                //Intent it = new Intent(ActivityInicializacao.this, AActivityTesteRelogio.class);

                //Intent it = new Intent(ActivityInicializacao.this, ActivityMaps.class);


                startActivity(it);
                finish();
            }
        }, 5000);
    }
}
