package com.example.forestsys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataHoraAtual {
    public static Date getCurrentDateTime(){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        String s = formato.format(new Date());
        Date data = null;
        try {
            data = formato.parse (s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (data);
    }
}
