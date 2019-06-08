package com.bruno.economic.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bruno.economic.dominio.entidades.Meta;

import java.util.ArrayList;

public class MetaRepositorio {

    private SQLiteDatabase conexao;

    public MetaRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserir(Meta meta){
        ContentValues contentValues = new ContentValues();

        contentValues.put("nome", meta.getNome());
        contentValues.put("valorMeta", meta.getValorMeta());
        contentValues.put("valorEconomia", meta.getValorEconomia());
        contentValues.put("valorAlcancado", meta.getValorAlcancado());
        contentValues.put("diaFim", meta.getDiaFim());
        contentValues.put("mesFim", meta.getMesFim());
        contentValues.put("anoFim", meta.getAnoFim());

        conexao.insertOrThrow("META", null, contentValues);
    }


    public void excluir(String nome){
        String[] parametros = new String[1];
        parametros[0] = nome;


        conexao.delete("META", "nome = ? ", parametros);
    }

    public  void alterar(Meta meta){
        ContentValues contentValues = new ContentValues();

        contentValues.put("nome", meta.getNome());
        contentValues.put("valorMeta", meta.getValorMeta());
        contentValues.put("valorEconomia", meta.getValorEconomia());
        contentValues.put("valorAlcancado", meta.getValorAlcancado());
        contentValues.put("diaFim", meta.getDiaFim());
        contentValues.put("mesFim", meta.getMesFim());
        contentValues.put("anoFim", meta.getAnoFim());

        String[] parametros = new String[1];
        parametros[0] = meta.getNome();


        conexao.update("META", contentValues, "nome = ? ", parametros);
    }

    public ArrayList<Meta> buscarTodos(){
        ArrayList<Meta> metas = new ArrayList<Meta>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT nome, valorMeta, valorEconomia, valorAlcancado, diaFim, mesFim, anoFim ");
        sql.append("   From META");

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if(resultado.getCount() > 0) {
            resultado.moveToFirst();

            do{
                Meta met = new Meta();

                met.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));
                met.setValorMeta(resultado.getDouble(resultado.getColumnIndexOrThrow("valorMeta")));
                met.setValorEconomia(resultado.getDouble(resultado.getColumnIndexOrThrow("valorEconomia")));
                met.setValorAlcancado(resultado.getDouble(resultado.getColumnIndexOrThrow("valorAlcancado")));
                met.setDiaFim(resultado.getInt(resultado.getColumnIndexOrThrow("diaFim")));
                met.setMesFim(resultado.getInt(resultado.getColumnIndexOrThrow("mesFim")));
                met.setAnoFim(resultado.getInt(resultado.getColumnIndexOrThrow("anoFim")));

                metas.add(met);

            }while(resultado.moveToNext());
        }

        return metas;
    }

    public Meta buscarMeta(String nome){
        Meta meta = new Meta();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT nome, valorMeta, valorEconomia, valorAlcancado, diaFim, mesFim, anoFim ");
        sql.append("   From META");
        sql.append(" WHERE nome = ? ");

        String[] parametros = new String[1];
        parametros[0] = meta.getNome();

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if(resultado.getCount() > 0) {
            resultado.moveToFirst();


            meta.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));
            meta.setValorMeta(resultado.getDouble(resultado.getColumnIndexOrThrow("valorMeta")));
            meta.setValorEconomia(resultado.getDouble(resultado.getColumnIndexOrThrow("valorEconomia")));
            meta.setValorAlcancado(resultado.getDouble(resultado.getColumnIndexOrThrow("valorAlcancado")));
            meta.setDiaFim(resultado.getInt(resultado.getColumnIndexOrThrow("diaFim")));
            meta.setMesFim(resultado.getInt(resultado.getColumnIndexOrThrow("mesFim")));
            meta.setAnoFim(resultado.getInt(resultado.getColumnIndexOrThrow("anoFim")));

            return meta;
        }

        return null;
    }

}
