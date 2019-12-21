package com.example.forestsys.classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TPRODUTO {
    @PrimaryKey
     private double CODCOLPRD;
	 private double IDPRD;
	 private String CODIGOPRD;
	 private String NOMEFANTASIA;
	 private String CODIGOREDUZIDO;
	 private int ULTIMONIVEL;
	 private String TIPO;
	 private String DESCRICAO;
	 private String CODUNDCONTROLE;
	 private String CODUNDCOMPRA;
	 private String CODUNDVENDA;

    public TPRODUTO(double CODCOLPRD, double IDPRD, String CODIGOPRD, String NOMEFANTASIA, String CODIGOREDUZIDO, int ULTIMONIVEL, String TIPO,
                    String DESCRICAO, String CODUNDCONTROLE, String CODUNDCOMPRA, String CODUNDVENDA) {
        this.CODCOLPRD = CODCOLPRD;
        this.IDPRD = IDPRD;
        this.CODIGOPRD = CODIGOPRD;
        this.NOMEFANTASIA = NOMEFANTASIA;
        this.CODIGOREDUZIDO = CODIGOREDUZIDO;
        this.ULTIMONIVEL = ULTIMONIVEL;
        this.TIPO = TIPO;
        this.DESCRICAO = DESCRICAO;
        this.CODUNDCONTROLE = CODUNDCONTROLE;
        this.CODUNDCOMPRA = CODUNDCOMPRA;
        this.CODUNDVENDA = CODUNDVENDA;
    }

    public double getCODCOLPRD() {
        return CODCOLPRD;
    }

    public void setCODCOLPRD(double CODCOLPRD) {
        this.CODCOLPRD = CODCOLPRD;
    }

    public double getIDPRD() {
        return IDPRD;
    }

    public void setIDPRD(double IDPRD) {
        this.IDPRD = IDPRD;
    }

    public String getCODIGOPRD() {
        return CODIGOPRD;
    }

    public void setCODIGOPRD(String CODIGOPRD) {
        this.CODIGOPRD = CODIGOPRD;
    }

    public String getNOMEFANTASIA() {
        return NOMEFANTASIA;
    }

    public void setNOMEFANTASIA(String NOMEFANTASIA) {
        this.NOMEFANTASIA = NOMEFANTASIA;
    }

    public String getCODIGOREDUZIDO() {
        return CODIGOREDUZIDO;
    }

    public void setCODIGOREDUZIDO(String CODIGOREDUZIDO) {
        this.CODIGOREDUZIDO = CODIGOREDUZIDO;
    }

    public int getULTIMONIVEL() {
        return ULTIMONIVEL;
    }

    public void setULTIMONIVEL(int ULTIMONIVEL) {
        this.ULTIMONIVEL = ULTIMONIVEL;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public String getCODUNDCONTROLE() {
        return CODUNDCONTROLE;
    }

    public void setCODUNDCONTROLE(String CODUNDCONTROLE) {
        this.CODUNDCONTROLE = CODUNDCONTROLE;
    }

    public String getCODUNDCOMPRA() {
        return CODUNDCOMPRA;
    }

    public void setCODUNDCOMPRA(String CODUNDCOMPRA) {
        this.CODUNDCOMPRA = CODUNDCOMPRA;
    }

    public String getCODUNDVENDA() {
        return CODUNDVENDA;
    }

    public void setCODUNDVENDA(String CODUNDVENDA) {
        this.CODUNDVENDA = CODUNDVENDA;
    }
}
