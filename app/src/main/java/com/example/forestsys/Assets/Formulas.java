package com.example.forestsys.Assets;

import java.text.DecimalFormat;

/*
 * Classe contendo as formulas definidas pela GELF para calculo da conformidade dos indicadores da subsolagem
 */
public class Formulas {

    //A
    public double subsolagem_profundidade(int qtdNc, int qtdPontos){
        DecimalFormat df = new DecimalFormat(".##");
        double resultado = Double.valueOf(qtdNc)/Double.valueOf(qtdPontos);
        return Double.valueOf(df.format(resultado*100).replace(',', '.'));
    }

    //B
    public double subsolagem_largura_estrondamento(int qtdNc, int qtdPontos){
        DecimalFormat df = new DecimalFormat(".##");
        double resultado = Double.valueOf(qtdNc)/Double.valueOf(qtdPontos);
        return Double.valueOf(df.format(resultado*100).replace(',', '.'));
    }

    //C
    public double subsolagem_faixa_solo(int qtdNc, int qtdPontos){
        DecimalFormat df = new DecimalFormat(".##");
        double resultado = Double.valueOf(qtdNc)/Double.valueOf(qtdPontos);
        return Double.valueOf(df.format(resultado*100).replace(',', '.'));
    }

    //D
    public double subsolagem_profundidade_adubo(int qtdNc, int qtdPontos){
        DecimalFormat df = new DecimalFormat(".##");
        double resultado = Double.valueOf(qtdNc)/Double.valueOf(qtdPontos);
        return Double.valueOf(df.format(resultado*100).replace(',', '.'));
    }

    //E
    public double subsolagem_presenca_adubo(int qtdNc, int qtdPontos){
        DecimalFormat df = new DecimalFormat(".##");
        double resultado = Double.valueOf(qtdNc)/Double.valueOf(qtdPontos);
        return Double.valueOf(df.format(resultado*100).replace(',', '.'));
    }

    //F
    public double subsolagem_espacamento(int qtdNc, int qtdPontos){
        DecimalFormat df = new DecimalFormat(".##");
        double resultado = Double.valueOf(qtdNc)/Double.valueOf(qtdPontos);
        return Double.valueOf(df.format(resultado*100).replace(',', '.'));
    }

    //G
    public double subsolagem_torroes(int qtdNc, int qtdPontos){
        DecimalFormat df = new DecimalFormat(".##");
        double resultado = Double.valueOf(qtdNc)/Double.valueOf(qtdPontos);
        return Double.valueOf(df.format(resultado*100).replace(',', '.'));
    }

    //H
    public double subsolagem_espelhamento_solo(int qtdNc, int qtdPontos){
        DecimalFormat df = new DecimalFormat(".##");
        double resultado = Double.valueOf(qtdNc)/Double.valueOf(qtdPontos);
        return Double.valueOf(df.format(resultado*100).replace(',', '.'));
    }

    //I
    public double subsolagem_bolsoes_ar(int qtdNc, int qtdPontos){
        DecimalFormat df = new DecimalFormat(".##");
        double resultado = Double.valueOf(qtdNc)/Double.valueOf(qtdPontos);
        return Double.valueOf(df.format(resultado*100).replace(',', '.'));
    }

    //J
    public double subsolagem_distancia_insumo(int qtdNc, int qtdPontos){
        DecimalFormat df = new DecimalFormat(".##");
        double resultado = Double.valueOf(qtdNc)/Double.valueOf(qtdPontos);
        return Double.valueOf(df.format(resultado*100).replace(',', '.'));
    }
}