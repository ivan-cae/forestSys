package com.example.forestsys.Assets;

import android.text.InputFilter;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.forestsys.Activities.ActivityMain.osSelecionada;
import static java.lang.String.format;

public class Ferramentas {


    //Retorna a hora e minutos atuais do dispositivo
    public static String horaAtual() {
        return DateFormat.format("HH:mm", new Date()).toString().trim();
    }

    //Retorna a hora, minutos e segundos atuais do dispositivo
    public static String dataHoraMinutosSegundosAtual() {
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString();
    }

    //Retorna a data atual do dispositivo
    public static String dataAtual() {
        return DateFormat.format("dd-MM-yyyy", new Date()).toString().trim();
    }

    //converte uma string contendo o formato "HH:mm" para o tipo Date e a retorna
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

    //converte uma string contendo o formato "dd-MM-yyyy" para o tipo Date e a retorna
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

    //formata uma string para salva-la no DB no campo "DATA" de uma tabela e a retorna convertida
    public static String formataDataDb(String data) {
        char[] caracteres = data.toCharArray();
        String dia = caracteres[0] + "" + caracteres[1];
        String mes = caracteres[3] + "" + caracteres[4];
        String ano = caracteres[6] + "" + caracteres[7] + "" + caracteres[8] + "" + caracteres[9];
        return ano + "-" + mes + "-" + dia;
    }

    //formata uma string salva no DB para o formato de um TextView e a retorna convertida
    public static String formataDataTextView(String data) {
        char[] caracteres = data.toCharArray();
        String dia = caracteres[8] + "" + caracteres[9];
        String mes = caracteres[5] + "" + caracteres[6];
        String ano = caracteres[0] + "" + caracteres[1] + "" + caracteres[2] + "" + caracteres[3];
        return dia+ "-" +mes+ "-" +ano;
    }

    //Poe a virgula automaticamente como separador decimal dos números inseridos nas caixas de texto
    //parâmetros de entrada: Uma instância de uma caixa de texto, um CharSequence contendo o valor a ser formatado,
    //Um inteiro representando o número de casas decimais
    //Uma string com um valor de referência para calcular o número de casas antes da virgula
    //Um inteiro contendo o tamanho do CharSequence antes da formatação
    //Um inteiro contendo o tamanho do CharSequence depois da formatação
    public static void mascaraVirgula(EditText edit, CharSequence s, int casasDecimais, String valorReferencia,
                                      int contAtual, int contAnterior) {

        if(casasDecimais == 0) casasDecimais = 2;

        String input = s.toString();
        String semFormatar = input.replace(",", "").trim();
        int tamanho = semFormatar.length();
        String valorFinal = semFormatar;
        char[] separaSemFormatar = semFormatar.toCharArray();
        //char[] separaFormatado = input.toCharArray();
        int nCasasAntesVirgula;

        valorReferencia = valorReferencia.replace(".", ",");
        String[] antesDaVirgula = valorReferencia.split(",");
        nCasasAntesVirgula = antesDaVirgula[0].length();


        edit.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(nCasasAntesVirgula+1+casasDecimais)});

        if (contAnterior != contAtual) {
                /*if (tamanho == casasDecimais) {
                    if (contAnterior>contAtual && input.contains(",")) {
                        valorFinal = "";
                        valorFinal = (separaSemFormatar[0] + "" + separaSemFormatar[1]).trim();
                    }

                    if(contAnterior<contAtual && !input.contains(",")){
                        valorFinal = separaSemFormatar[0] + "," + separaSemFormatar[1];
                    }
                }
*/

            if (tamanho > casasDecimais) {
                valorFinal = "";
                for (int i = 0; i < tamanho; i++) {
                    valorFinal += separaSemFormatar[i];
                    if (i == (tamanho - (casasDecimais+1))) {
                        valorFinal += ",";
                    }

                }
            }

            /*Log.wtf("Digitado", s.toString());
            Log.wtf("Anterior", String.valueOf(contAnterior));
            Log.wtf("Atual", String.valueOf(contAtual));
*/
            edit.setText(valorFinal.trim());
            edit.setSelection(edit.length());
        }
    }
}