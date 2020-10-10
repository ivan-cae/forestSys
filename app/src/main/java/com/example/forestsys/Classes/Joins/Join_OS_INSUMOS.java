package com.example.forestsys.Classes.Joins;

import java.io.Serializable;

public class Join_OS_INSUMOS implements Serializable {
    int ID_PROGRAMACAO_ATIVIDADE;

    int ID_INSUMO;

    private String DESCRICAO;
    private double NUTRIENTE_N;
    private double NUTRIENTE_P2O5;
    private double NUTRIENTE_K2O;
    private double NUTRIENTE_CAO;
    private double NUTRIENTE_MGO;
    private double NUTRIENTE_B;
    private double NUTRIENTE_ZN;
    private double NUTRIENTE_S;
    private double NUTRIENTE_CU;
    private double NUTRIENTE_AF;
    private double NUTRIENTE_MN;
    private String UND_MEDIDA;

    private int RECOMENDACAO;
    private double QTD_HA_RECOMENDADO;
    private  double QTD_APLICADO;
    private String DATA;

    private String ACAO_INATIVO;
    private char REGISTRO_DESCARREGADO;
    private String OBSERVACAO;
    private String ID_INSUMO_RM;

    public Join_OS_INSUMOS() {
    }

    public int getID_PROGRAMACAO_ATIVIDADE() {
        return ID_PROGRAMACAO_ATIVIDADE;
    }

    public void setID_PROGRAMACAO_ATIVIDADE(int ID_PROGRAMACAO_ATIVIDADE) {
        this.ID_PROGRAMACAO_ATIVIDADE = ID_PROGRAMACAO_ATIVIDADE;
    }

    public int getID_INSUMO() {
        return ID_INSUMO;
    }

    public void setID_INSUMO(int ID_INSUMO) {
        this.ID_INSUMO = ID_INSUMO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public double getNUTRIENTE_N() {
        return NUTRIENTE_N;
    }

    public void setNUTRIENTE_N(double NUTRIENTE_N) {
        this.NUTRIENTE_N = NUTRIENTE_N;
    }

    public double getNUTRIENTE_P2O5() {
        return NUTRIENTE_P2O5;
    }

    public void setNUTRIENTE_P2O5(double NUTRIENTE_P2O5) {
        this.NUTRIENTE_P2O5 = NUTRIENTE_P2O5;
    }

    public double getNUTRIENTE_K2O() {
        return NUTRIENTE_K2O;
    }

    public void setNUTRIENTE_K2O(double NUTRIENTE_K2O) {
        this.NUTRIENTE_K2O = NUTRIENTE_K2O;
    }

    public double getNUTRIENTE_CAO() {
        return NUTRIENTE_CAO;
    }

    public void setNUTRIENTE_CAO(double NUTRIENTE_CAO) {
        this.NUTRIENTE_CAO = NUTRIENTE_CAO;
    }

    public double getNUTRIENTE_MGO() {
        return NUTRIENTE_MGO;
    }

    public void setNUTRIENTE_MGO(double NUTRIENTE_MGO) {
        this.NUTRIENTE_MGO = NUTRIENTE_MGO;
    }

    public double getNUTRIENTE_B() {
        return NUTRIENTE_B;
    }

    public void setNUTRIENTE_B(double NUTRIENTE_B) {
        this.NUTRIENTE_B = NUTRIENTE_B;
    }

    public double getNUTRIENTE_ZN() {
        return NUTRIENTE_ZN;
    }

    public void setNUTRIENTE_ZN(double NUTRIENTE_ZN) {
        this.NUTRIENTE_ZN = NUTRIENTE_ZN;
    }

    public double getNUTRIENTE_S() {
        return NUTRIENTE_S;
    }

    public void setNUTRIENTE_S(double NUTRIENTE_S) {
        this.NUTRIENTE_S = NUTRIENTE_S;
    }

    public double getNUTRIENTE_CU() {
        return NUTRIENTE_CU;
    }

    public void setNUTRIENTE_CU(double NUTRIENTE_CU) {
        this.NUTRIENTE_CU = NUTRIENTE_CU;
    }

    public double getNUTRIENTE_AF() {
        return NUTRIENTE_AF;
    }

    public void setNUTRIENTE_AF(double NUTRIENTE_AF) {
        this.NUTRIENTE_AF = NUTRIENTE_AF;
    }

    public double getNUTRIENTE_MN() {
        return NUTRIENTE_MN;
    }

    public void setNUTRIENTE_MN(double NUTRIENTE_MN) {
        this.NUTRIENTE_MN = NUTRIENTE_MN;
    }

    public String getUND_MEDIDA() {
        return UND_MEDIDA;
    }

    public void setUND_MEDIDA(String UND_MEDIDA) {
        this.UND_MEDIDA = UND_MEDIDA;
    }

    public int getRECOMENDACAO() {
        return RECOMENDACAO;
    }

    public void setRECOMENDACAO(int RECOMENDACAO) {
        this.RECOMENDACAO = RECOMENDACAO;
    }

    public double getQTD_HA_RECOMENDADO() {
        return QTD_HA_RECOMENDADO;
    }

    public void setQTD_HA_RECOMENDADO(double QTD_HA_RECOMENDADO) {
        this.QTD_HA_RECOMENDADO = QTD_HA_RECOMENDADO;
    }

    public double getQTD_APLICADO() {
        return QTD_APLICADO;
    }

    public void setQTD_APLICADO(double QTD_APLICADO) {
        this.QTD_APLICADO = QTD_APLICADO;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getACAO_INATIVO() {
        return ACAO_INATIVO;
    }

    public void setACAO_INATIVO(String ACAO_INATIVO) {
        this.ACAO_INATIVO = ACAO_INATIVO;
    }

    public char getREGISTRO_DESCARREGADO() {
        return REGISTRO_DESCARREGADO;
    }

    public void setREGISTRO_DESCARREGADO(char REGISTRO_DESCARREGADO) {
        this.REGISTRO_DESCARREGADO = REGISTRO_DESCARREGADO;
    }

    public String getOBSERVACAO() {
        return OBSERVACAO;
    }

    public void setOBSERVACAO(String OBSERVACAO) {
        this.OBSERVACAO = OBSERVACAO;
    }

    public String getID_INSUMO_RM() {
        return ID_INSUMO_RM;
    }

    public void setID_INSUMO_RM(String ID_INSUMO_RM) {
        this.ID_INSUMO_RM = ID_INSUMO_RM;
    }

    @Override
    public String toString(){return DESCRICAO;}
}
