package com.example.forestsys.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        indices = {@Index(value = "ID_INSUMO_RM", unique = true)}
)
public class INSUMOS implements Serializable {

    @PrimaryKey
    private Integer ID_INSUMO;

    @ColumnInfo (name = "ID_INSUMO_RM")
	private String ID_INSUMO_RM;

	private String CLASSE;
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
	private Integer ATIVO;
	private String UND_MEDIDA;

    public INSUMOS(Integer ID_INSUMO, String ID_INSUMO_RM, String CLASSE, String DESCRICAO, double NUTRIENTE_N,
                   double NUTRIENTE_P2O5, double NUTRIENTE_K2O, double NUTRIENTE_CAO, double NUTRIENTE_MGO,
                   double NUTRIENTE_B, double NUTRIENTE_ZN, double NUTRIENTE_S, double NUTRIENTE_CU,
                   double NUTRIENTE_AF, double NUTRIENTE_MN, Integer ATIVO, String UND_MEDIDA) {
        this.ID_INSUMO = ID_INSUMO;
        this.ID_INSUMO_RM = ID_INSUMO_RM;
        this.CLASSE = CLASSE;
        this.DESCRICAO = DESCRICAO;
        this.NUTRIENTE_N = NUTRIENTE_N;
        this.NUTRIENTE_P2O5 = NUTRIENTE_P2O5;
        this.NUTRIENTE_K2O = NUTRIENTE_K2O;
        this.NUTRIENTE_CAO = NUTRIENTE_CAO;
        this.NUTRIENTE_MGO = NUTRIENTE_MGO;
        this.NUTRIENTE_B = NUTRIENTE_B;
        this.NUTRIENTE_ZN = NUTRIENTE_ZN;
        this.NUTRIENTE_S = NUTRIENTE_S;
        this.NUTRIENTE_CU = NUTRIENTE_CU;
        this.NUTRIENTE_AF = NUTRIENTE_AF;
        this.NUTRIENTE_MN = NUTRIENTE_MN;
        this.ATIVO = ATIVO;
        this.UND_MEDIDA = UND_MEDIDA;
    }

    public Integer getID_INSUMO() {
        return ID_INSUMO;
    }

    public void setID_INSUMO(Integer ID_INSUMO) {
        this.ID_INSUMO = ID_INSUMO;
    }

    public String getID_INSUMO_RM() {
        return ID_INSUMO_RM;
    }

    public void setID_INSUMO_RM(String ID_INSUMO_RM) {
        this.ID_INSUMO_RM = ID_INSUMO_RM;
    }

    public String getCLASSE() {
        return CLASSE;
    }

    public void setCLASSE(String CLASSE) {
        this.CLASSE = CLASSE;
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

    public void setNUTRIENTE_N(Integer NUTRIENTE_N) {
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

    public Integer getATIVO() {
        return ATIVO;
    }

    public void setATIVO(Integer ATIVO) {
        this.ATIVO = ATIVO;
    }

    public String getUND_MEDIDA() {
        return UND_MEDIDA;
    }

    public void setUND_MEDIDA(String UND_MEDIDA) {
        this.UND_MEDIDA = UND_MEDIDA;
    }
}
