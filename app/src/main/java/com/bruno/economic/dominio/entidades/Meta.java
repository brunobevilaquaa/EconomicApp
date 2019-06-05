package com.bruno.economic.dominio.entidades;

import java.io.Serializable;

public class Meta implements Serializable{
    private String nome;
    private int valorMeta;
    private int valorEconomia;
    private int diaFim;
    private int mesFim;
    private int anoFim;


    //SETERS;
    public void setNome(String n){
        this.nome = n;
    }

    public void setValorMeta(int vm){
        this.valorMeta = vm;
    }

    public void setValorEconomia(int ve){
        this.valorEconomia = ve;
    }

    public void setDiaFim(int df){
        this.diaFim = df;
    }

    public void setMesFim(int mf){
        this.mesFim = mf;
    }

    public void setAnoFim(int af){
        this.anoFim = af;
    }

    //GETTERS
    public String getNome(){
        return this.nome;
    }

    public int getValorMeta(){
        return this.valorMeta;
    }

    public int getValorEconomia(){
        return this.valorEconomia;
    }

    public int getDiaFim(){
        return this.diaFim;
    }

    public int getMesFim(){
        return this.mesFim;
    }

    public int getAnoFim(){
        return this.anoFim;
    }
}
