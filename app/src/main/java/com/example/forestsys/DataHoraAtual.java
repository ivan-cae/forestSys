package com.example.forestsys;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

public class DataHoraAtual {

    //Retorna a data atual do dispositivo
    public static String hora(){
            return DateFormat.format("HH:mm", new Date()).toString();

    }

    public static String data(){
        return DateFormat.format("dd/MM/yyyy", new Date()).toString();
    }}