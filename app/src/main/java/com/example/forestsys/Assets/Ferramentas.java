package com.example.forestsys.Assets;


import static android.content.Context.CONNECTIVITY_SERVICE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.InputFilter;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.forestsys.Activities.ActivityMain.osSelecionada;
import static java.lang.String.format;

import androidx.core.content.ContextCompat;

import com.example.forestsys.Classes.O_S_ATIVIDADES;
import com.example.forestsys.Classes.O_S_ATIVIDADES_DIA;

/*
 * Classe auxiliar responsável por armazenar os métodos mais utilizados por outras classes ou em Activities
 */
public class Ferramentas {


    /*
     * Retorna: A hora e minutos atuais do dispositivo
     */
    public static String horaAtual() {
        return DateFormat.format("HH:mm", new Date()).toString().trim();
    }

    /*
     * Retorna: A data, hora, minutos e segundos atuais do dispositivo
     */
    public static String dataHoraMinutosSegundosAtual() {
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString();
    }

    /*
     * Retorna a data atual do dispositivo
     */
    public static String dataAtual() {
        return DateFormat.format("dd-MM-yyyy", new Date()).toString().trim();
    }

    /*
     * Método responsável por converter uma string contendo o formato "HH:mm" para o tipo Date
     * Parâmetro de entrada: String a ser convertida]
     * Retorna: Null se não for posível converter a String ou a String convertida corretamente para o tipo Date
     */
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

    /*
     * Método responsável por converter uma string contendo o formato "dd-MM-yyyy" para o tipo Date
     * Parâmetro de entrada: String a ser convertida]
     * Retorna: Null se não for posível converter a String ou a String convertida corretamente para o tipo Date
     */
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

    /*
     * Método responsável por formatar uma string para salva-la no DB em alguma tabela que tenha um atributo tipo
     DATE com o padrão YYYY-MM-DD
     * Parâmetro de entrada: String a ser formatada
     * Retorna: String formatada
     */
    public static String formataDataDb(String data) {
        char[] caracteres = data.toCharArray();
        String dia = caracteres[0] + "" + caracteres[1];
        String mes = caracteres[3] + "" + caracteres[4];
        String ano = caracteres[6] + "" + caracteres[7] + "" + caracteres[8] + "" + caracteres[9];
        return ano + "-" + mes + "-" + dia;
    }

    /*
     * Método responsável por formatar uma data salva em alguma tabela que tenha um atributo tipo
     DATE com o padrão YYYY-MM-DD para o padrão DD-MM-YYYY
     * Parâmetro de entrada: String a ser formatada
     * Retorna: String formatada
     */
    public static String formataDataTextView(String data) {
        char[] caracteres = data.toCharArray();
        String dia = caracteres[8] + "" + caracteres[9];
        String mes = caracteres[5] + "" + caracteres[6];
        String ano = caracteres[0] + "" + caracteres[1] + "" + caracteres[2] + "" + caracteres[3];
        return dia+ "-" +mes+ "-" +ano;
    }

    /*
     * mètodo responsável por adicionar uma virgula automaticamente como separador decimal dos números inseridos
     nas caixas de texto
     * Parâmetros de entrada: Uma instância de uma caixa de texto, um CharSequence contendo o valor a ser formatado,
     um inteiro representando o número de casas decimais,uma string com um valor de referência para calcular o
     número de casas antes da virgula, um inteiro contendo o tamanho do CharSequence antes da formatação, um inteiro
     contendo o tamanho do CharSequence depois da formatação
     */
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
            edit.setText(valorFinal.trim());
            edit.setSelection(edit.length());
        }
    }

    /*
     * Método utilizado para calcular a área realizada de uma atividade e fazer o update desse campo
     * O calculo é feito usando uma lista dos apontamentos cadastrados para uma atividade e somando suas áreas realizadas
     * Parâmetro de entrada: id de uma atividade
     * */
    public static void calculaAreaRealizada(int id, Context context){
        BaseDeDados baseDeDados = BaseDeDados.getInstance(context);
        DAO dao = baseDeDados.dao();
        double calcula = 0;
        List<O_S_ATIVIDADES_DIA> listaReg = dao.listaAtividadesDia(id);
        for(int i=0; i <listaReg.size(); i++){
            String s = listaReg.get(i).getAREA_REALIZADA().replace(',', '.');
            double d = 0;
            try {
                d = Double.valueOf(s);
            }catch(Exception e){
                e.printStackTrace();
                d = 0;
            }
            calcula+=d;
        }

        O_S_ATIVIDADES atividade = dao.selecionaOs(id);

        BigDecimal bd = BigDecimal.valueOf(calcula);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        atividade.setAREA_REALIZADA(bd.doubleValue());
        if(osSelecionada != null) {
            osSelecionada.setAREA_REALIZADA(bd.doubleValue());
        }
        dao.update(atividade);
    }

    /*
     * Método responsável por verificar se há conexão com a rede
     * Retorna: True se há rede e false se não há
     */
    public static boolean temRede(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * formula= (1-(amostra atual/amostra anterior))/100
     * Método responsável por calcular a diferença percentual entre dois números do tipo Double
     * Parâmetro de entrada: dois Doubles a serem comparados
     * Retorna: A diferença percentual entre os dois valores
     */
    public static Double diferencaPercentual(Double anterior, Double atual) {
        Double calculo = (1 - (atual / anterior)) * 100;//((anterior - atual) / anterior) * 100.0
        DecimalFormat df = new DecimalFormat("###.##");
        if (Double.valueOf(df.format(calculo).replace(',', '.')) == 0.0) return -0.0;
        return Double.valueOf(df.format(calculo).replace(',', '.'));
    }

    /*
     * Método responsável por contar quantas indicências de um caractere há em uma String
     * Parâmetros de entrada: Uma String a ser verificada, Um Char contendo o carctere a ser contado na String
     * Retorna: Quantidade de indicências do caractere
     */
    public int contaVirgula(String s, char c) {
        return s.length() == 0 ? 0 : (s.charAt(0) == c ? 1 : 0) + contaVirgula(s.substring(1), c);
    }
}