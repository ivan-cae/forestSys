package com.example.forestsys.assets;

import android.text.format.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

public class DataHoraAtual {

    //Retorna a data atual do dispositivo
    public static String horaAtual() {
        return DateFormat.format("HH:mm", new Date()).toString().trim();
    }

    public static String dataAtual() {
        return DateFormat.format("dd-MM-yyyy", new Date()).toString().trim();
    }

    public static Date horaToDate(String hora) {
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

    public static Date dataToDate(String hora) {
        String pattern = "dd-MM-yyyy";
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

    public static String formataDataDb(String data) {
        char[] caracteres = data.toCharArray();
        String dia = caracteres[0] + "" + caracteres[1];
        String mes = caracteres[3] + "" + caracteres[4];
        String ano = caracteres[6] + "" + caracteres[7] + "" + caracteres[8] + "" + caracteres[9];
        return ano + "-" + mes + "-" + dia;
    }

    public static String formataDataTextView(String data) {
        char[] caracteres = data.toCharArray();
        String dia = caracteres[8] + "" + caracteres[9];
        String mes = caracteres[5] + "" + caracteres[6];
        String ano = caracteres[0] + "" + caracteres[1] + "" + caracteres[2] + "" + caracteres[3];
        return dia+ "-" +mes+ "-" +ano;
    }

}