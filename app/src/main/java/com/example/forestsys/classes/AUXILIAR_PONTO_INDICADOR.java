package com.example.forestsys.classes;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        foreignKeys = {@ForeignKey(entity = O_S_ATIVIDADES.class,
                parentColumns = "ID_PROGRAMACAO_ATIVIDADE",
                childColumns  = "ID_PROGRAMACAO_ATIVIDADE",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = ATIVIDADE_INDICADORES.class,
                        parentColumns = "ID_INDICADOR",
                        childColumns  = "ID_INDICADOR",
                        onDelete = ForeignKey.NO_ACTION,
                        onUpdate = ForeignKey.NO_ACTION)},
        primaryKeys = {"ID_PROGRAMACAO_ATIVIDADE", "DATA", "PONTO", "ID_ATIVIDADE", "ID_INDICADOR"})

public class AUXILIAR_PONTO_INDICADOR {

    private int ID_PROGRAMACAO_ATIVIDADE;
    private int PONTO;

    private double  VALOR_ITEM_1;
    private double  VALOR_ITEM_2;
    private double  VALOR_ITEM_3;
    private double  VALOR_ITEM_4;
    private double  VALOR_ITEM_5;
    private double  VALOR_ITEM_6;
    private double  VALOR_ITEM_7;
    private double  VALOR_ITEM_8;
    private double  VALOR_ITEM_9;
    private double  VALOR_ITEM_10;

    private String NOME_ITEM_1;
    private String NOME_ITEM_2;
    private String NOME_ITEM_3;
    private String NOME_ITEM_4;
    private String NOME_ITEM_5;
    private String NOME_ITEM_6;
    private String NOME_ITEM_7;
    private String NOME_ITEM_8;
    private String NOME_ITEM_9;
    private String NOME_ITEM_10;

    private double COORDENADA_X_ITEM_1;
    private double COORDENADA_X_ITEM_2;
    private double COORDENADA_X_ITEM_3;
    private double COORDENADA_X_ITEM_4;
    private double COORDENADA_X_ITEM_5;
    private double COORDENADA_X_ITEM_6;
    private double COORDENADA_X_ITEM_7;
    private double COORDENADA_X_ITEM_8;
    private double COORDENADA_X_ITEM_9;
    private double COORDENADA_X_ITEM_10;

    private double COORDENADA_Y_ITEM_1;
    private double COORDENADA_Y_ITEM_2;
    private double COORDENADA_Y_ITEM_3;
    private double COORDENADA_Y_ITEM_4;
    private double COORDENADA_Y_ITEM_5;
    private double COORDENADA_Y_ITEM_6;
    private double COORDENADA_Y_ITEM_7;
    private double COORDENADA_Y_ITEM_8;
    private double COORDENADA_Y_ITEM_9;
    private double COORDENADA_Y_ITEM_10;

    private int CORRIGIVEL_ITEM_1;
    private int CORRIGIVEL_ITEM_2;
    private int CORRIGIVEL_ITEM_3;
    private int CORRIGIVEL_ITEM_4;
    private int CORRIGIVEL_ITEM_5;
    private int CORRIGIVEL_ITEM_6;
    private int CORRIGIVEL_ITEM_7;
    private int CORRIGIVEL_ITEM_8;
    private int CORRIGIVEL_ITEM_9;
    private int CORRIGIVEL_ITEM_10;

}
