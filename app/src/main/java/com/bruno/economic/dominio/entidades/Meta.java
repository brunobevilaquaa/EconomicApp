package com.bruno.economic.dominio.entidades;

import java.io.Serializable;

public class Meta implements Serializable{
    private String nome;
    private double valorMeta;
    private double valorEconomia;
    private double valorAlcancado;
    private int diaFim;
    private int mesFim;
    private int anoFim;


    //SETERS;
    public void setNome(String n){
        this.nome = n;
    }

    public void setValorMeta(double vm){
        this.valorMeta = vm;
    }

    public void setValorEconomia(double ve){
        this.valorEconomia = ve;
    }

    public void setValorAlcancado(double va){
        this.valorAlcancado = va;
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

    public double getValorMeta(){
        return this.valorMeta;
    }

    public double getValorEconomia(){
        return this.valorEconomia;
    }

    public double getValorAlcancado(){
        return this.valorAlcancado;
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
