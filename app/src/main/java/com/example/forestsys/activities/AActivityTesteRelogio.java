package com.example.forestsys.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.example.forestsys.DataHoraAtual;
import com.example.forestsys.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AActivityTesteRelogio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_relogio);
        TextView text = findViewById(R.id.data);

//        String date = DateFormat.format("dd/MMM/yyyy", new Date()).toString();
        String pattern = "dd/MMM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String horaAtual = DateFormat.format("dd/MMM/yyyy", new Date()).toString();
        Date d1;
        try {
            d1 = sdf.parse(horaAtual);
            text.setText(d1.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}