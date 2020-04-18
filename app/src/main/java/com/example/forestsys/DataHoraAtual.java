package com.example.forestsys;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

public class DataHoraAtual {

    //Retorna a data atual do dispositivo
    public static String horaAtual(){
            return DateFormat.format("HH:mm", new Date()).toString().trim();
    }

    public static String dataAtual(){
        return DateFormat.format("dd/MM/yyyy", new Date()).toString().trim();
    }

    public static Date horaToDate(String hora){
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date parseHora = null;
        try {
            parseHora = sdf.parse(hora);
            return parseHora;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseHora;
    }

    public static Date dataToDate(String hora){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date parseHora = null;
        try {
            parseHora = sdf.parse(hora);
            return parseHora;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseHora;
    }
}