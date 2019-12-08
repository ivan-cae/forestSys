package com.example.forestsys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataHoraAtual {

    //Retorna a data atual do dispositivo
    public static Date dataHora(){
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String s = formato.format( new java.util.Date());

        //String s = formato.format(new Date());
        Date data = null;
        try {
            data = formato.parse (s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (data);
    }
}