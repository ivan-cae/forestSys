package com.example.forestsys.Classes.ClassesAuxiliares;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Configs implements Serializable {
    @PrimaryKey
    private int idConfig;
    private String nomeEmpresa;
    private String endereçoHost;
    private String portaDeComunicacao;

    public Configs(int idConfig, String nomeEmpresa, String endereçoHost, String portaDeComunicacao) {
        this.idConfig = idConfig;
        this.nomeEmpresa = nomeEmpresa;
        this.endereçoHost = endereçoHost;
        this.portaDeComunicacao = portaDeComunicacao;
    }

    public int getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(int idConfig) {
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
}
