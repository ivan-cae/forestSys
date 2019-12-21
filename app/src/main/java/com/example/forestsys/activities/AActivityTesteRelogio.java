package com.example.forestsys.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.R;

import java.util.Date;


public class AActivityTesteRelogio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_relogio);
        DataHoraAtual dataHoraAtual = new DataHoraAtual();

        TextView text = findViewById(R.id.data);

        String date = DateFormat.format("dd-MM-yyyy 'Ás' hh:mm", new Date()).toString();

            //Date date1=new SimpleDateFormat("dd/MM/yyyy hh:mm");


        text.setText(date);
    }
}