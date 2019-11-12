package com.example.forestsys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static com.example.forestsys.ActivityLogin.nomeEmpresaPref;

public class ActivityCalibragem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibragem);
        setTitle(nomeEmpresaPref);
    }
}
