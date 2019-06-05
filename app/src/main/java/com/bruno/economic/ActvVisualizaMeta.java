package com.bruno.economic;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bruno.economic.DBHElper.MetasDb;
import com.bruno.economic.dominio.entidades.Meta;
import com.bruno.economic.dominio.repositorio.MetaRepositorio;

public class ActvVisualizaMeta extends AppCompatActivity {

    private SQLiteDatabase conexao;
    private MetasDb metasDb;
    private MetaRepositorio metaRepositorio;


    private TextView TXT_Nome;
    private TextView TXT_Valor;
    private TextView TXT_Economia;
    private TextView TXT_Termino;

    private Button BTN_Excluir;
    private Button BTN_

    private Meta dado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_visualiza_meta);

        criarConexao();

        TXT_Nome = (TextView)findViewById(R.id.TXT_Nome);
        TXT_Valor = (TextView) findViewById(R.id.TXT_Valor);
        TXT_Economia = (TextView) findViewById(R.id.TXT_Economia);
        TXT_Termino = (TextView) findViewById(R.id.TXT_Termino);

        dado = recebeDados();

        BTN_Excluir = (Button) findViewById(R.id.BTN_Excluir);

        if(dado != null) {
            BTN_Excluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    metaRepositorio.excluir(dado.getNome());
                    Intent intentA = new Intent(getApplicationContext(), ActvMain.class);
                    startActivity(intentA);
                }
            });
        }
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


    private Meta recebeDados(){
        Bundle bundle = getIntent().getExtras();

        Meta meta = new Meta();
        meta = (Meta) bundle.getSerializable("META");


        if((bundle != null) && (meta != null)){
            TXT_Nome.setText(meta.getNome());
            TXT_Valor.setText(Integer.toString(meta.getValorMeta()));
            TXT_Economia.setText(Integer.toString(meta.getValorEconomia()));
            TXT_Termino.setText(Integer.toString(meta.getDiaFim()) + "/" + Integer.toString(meta.getMesFim()) + "/" + Integer.toString(meta.getAnoFim()));
        }

        return meta;
    }
}
