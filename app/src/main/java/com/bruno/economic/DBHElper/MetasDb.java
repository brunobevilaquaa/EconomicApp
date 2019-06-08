package com.bruno.economic.DBHElper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MetasDb extends SQLiteOpenHelper {


    public MetasDb(Context context){
        super(context, "metasdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDLL.getCreateTableMeta());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String meta = "DROP TABLE IF EXISTS meta";
        db.execSQL(meta);
    }

}
