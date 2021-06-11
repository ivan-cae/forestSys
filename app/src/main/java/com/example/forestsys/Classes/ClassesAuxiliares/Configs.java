package com.example.forestsys.Classes.ClassesAuxiliares;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Configs implements Serializable {
    @PrimaryKey
    private Integer idConfig;
    private String nomeEmpresa;
    private String endereçoHost;
    private String portaDeComunicacao;
    private Integer permanenciaDosDados;
    private Integer posicaoNoSpinner;
    private String dataParaApagarDados;
    private String ultimaDataQueApagou;

    public Configs(Integer idConfig, String nomeEmpresa, String endereçoHost, String portaDeComunicacao, Integer permanenciaDosDados,
                   Integer posicaoNoSpinner) {
        this.idConfig = idConfig;
        this.nomeEmpresa = nomeEmpresa;
        this.endereçoHost = endereçoHost;
        this.portaDeComunicacao = portaDeComunicacao;
        this.permanenciaDosDados = permanenciaDosDados;
        this.posicaoNoSpinner = posicaoNoSpinner;
    }

    public Integer getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(Integer idConfig) {
        this.idConfig = idConfig;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEndereçoHost() {
        return endereçoHost;
    }

    public void setEndereçoHost(String endereçoHost) {
        this.endereçoHost = endereçoHost;
    }

    public String getPortaDeComunicacao() {
        return portaDeComunicacao;
    }

    public void setPortaDeComunicacao(String portaDeComunicacao) {
        this.portaDeComunicacao = portaDeComunicacao;
    }

    public Integer getPermanenciaDosDados() {
        return permanenciaDosDados;
    }

    public void setPermanenciaDosDados(Integer permanenciaDosDados) {
        this.permanenciaDosDados = permanenciaDosDados;
    }

    public Integer getPosicaoNoSpinner() {
        return posicaoNoSpinner;
    }

    public void setPosicaoNoSpinner(Integer posicaoNoSpinner) {
        this.posicaoNoSpinner = posicaoNoSpinner;
    }

    public String getDataParaApagarDados() {
        return dataParaApagarDados;
    }

    public void setDataParaApagarDados(String dataParaApagarDados) {
        this.dataParaApagarDados = dataParaApagarDados;
    }

    public String getUltimaDataQueApagou() {
        return ultimaDataQueApagou;
    }

    public void setUltimaDataQueApagou(String ultimaDataQueApagou) {
        this.ultimaDataQueApagou = ultimaDataQueApagou;
    }
}
