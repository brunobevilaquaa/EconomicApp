package com.bruno.economic;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bruno.economic.DBHElper.MetasDb;
import com.bruno.economic.dominio.entidades.Meta;
import com.bruno.economic.dominio.repositorio.MetaRepositorio;

import java.util.ArrayList;
import java.util.List;

public class ActvMain extends AppCompatActivity {
    private ConstraintLayout actv_main;

    private SQLiteDatabase conexao;
    private MetasDb metasDb;

    private MetaRepositorio metaRepositorio;

    private RecyclerView lstDados;

    private MetasAddapter metasAddapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_main);

        criarConexao();

        lstDados = (RecyclerView) findViewById(R.id.RV_Dados);

        Button BTN_Meta = (Button) findViewById(R.id.BNT_CriaMeta);
        BTN_Meta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentA = new Intent(getApplicationContext(), ActvNovaMeta.class);
                startActivity(intentA);
            }
        });

        actv_main = (ConstraintLayout)findViewById(R.id.actv_main);

        lstDados.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        lstDados.setLayoutManager(linearLayoutManager);

        ArrayList<Meta> dados = metaRepositorio.buscarTodos();

        metasAddapter = new MetasAddapter(metaRepositorio.buscarTodos());

        lstDados.setAdapter(metasAddapter);

    }


    private void criarConexao(){
        try{
            metasDb = new MetasDb(this);

            conexao = metasDb.getWritableDatabase();

            metaRepositorio = new MetaRepositorio(conexao);

        } catch(SQLException ex) {

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("ERRO");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    public ArrayList<String> getDados(ArrayList<Meta> m){
        ArrayList<String> dados = new ArrayList<String >();

        for(int i = 0; i < m.size(); i++){
            dados.add(m.get(i).getNome());
        }

        return dados;
    }


}
