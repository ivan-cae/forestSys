package com.example.forestsys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import static com.example.forestsys.LoginActivity.nomeEmpresaPref;

public class CalibragemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibragem);
        setTitle(nomeEmpresaPref);
    }
}
