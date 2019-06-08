package com.bruno.economic.DBHElper;

public class ScriptDLL {

    public static String getCreateTableMeta(){

        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS META (");
        sql.append("    nome           TEXT    PRIMARY KEY NOT NULL, ");
        sql.append("    valorMeta      DOUBLE  NOT NULL DEFAULT (''), ");
        sql.append("    valorEconomia  DOUBLE  NOT NULL DEFAULT (''), ");
        sql.append("    valorAlcancado DOUBLE  NOT NULL DEFAULT (''), ");
        sql.append("    diaFim         INTEGER NOT NULL DEFAULT (''), ");
        sql.append("    mesFim         INTEGER NOT NULL DEFAULT (''), ");
        sql.append("    anoFim         INTEGER NOT NULL DEFAULT ('') ) ");

        return sql.toString();
    }
}


