package com.bruno.economic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bruno.economic.DBHElper.MetasDb;
import com.bruno.economic.dominio.entidades.Meta;
import com.bruno.economic.dominio.repositorio.MetaRepositorio;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActvVisualizaMeta extends AppCompatActivity {

    private SQLiteDatabase conexao;
    private MetasDb metasDb;
    private MetaRepositorio metaRepositorio;


    private TextView TXT_Nome;
    private TextView TXT_Valor;
    private TextView TXT_Economia;
    private TextView TXT_ValorAlcancado;
    private TextView TXT_Termino;
    private TextView TXT_Previsao;

    private ProgressBar PB_Progresso;

    private Button BTN_Excluir;

    private Meta dado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_visualiza_meta);

        criarConexao();

        TXT_Nome = (TextView) findViewById(R.id.TXT_Nome);
        TXT_Valor = (TextView) findViewById(R.id.TXT_Valor);
        TXT_Economia = (TextView) findViewById(R.id.TXT_Economia);
        TXT_ValorAlcancado = (TextView) findViewById(R.id.TXT_ValorAlcancado);
        TXT_Termino = (TextView) findViewById(R.id.TXT_Termino);
        TXT_Previsao = (TextView) findViewById(R.id.TXT_Previsao);

        PB_Progresso = (ProgressBar) findViewById(R.id.PB_Progresso);


        dado = recebeDados();

        BTN_Excluir = (Button) findViewById(R.id.BTN_Excluir);


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

        final Intent intentMenuPrincipal = new Intent(getApplicationContext(), ActvMain.class);

        if(meta != null){
            int progresso = 0;
            int previsao = 0;
            double porcentagem = 0;

            DecimalFormat nf = new DecimalFormat("###,##0.00");

            TXT_Nome.setText(meta.getNome());
            TXT_Valor.setText(nf.format(meta.getValorMeta()).replaceAll(",", "."));
            TXT_Economia.setText(nf.format(meta.getValorEconomia()).replaceAll(",", "."));
            TXT_ValorAlcancado.setText(nf.format(meta.getValorAlcancado()).replaceAll(",", "."));

            //Converte dia mes e ano para o formato data
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            String dataRecebida = Integer.toString(meta.getDiaFim()) + "/" + Integer.toString(meta.getMesFim()) + "/" + Integer.toString(meta.getAnoFim());

            Date data = new Date();

            try{
                data = formato.parse(dataRecebida);
            } catch(Exception e){

            }

            String dataFormatada = formato.format(data).toString();

            TXT_Termino.setText(dataFormatada);

            //Porcentagem da barra de progresso
            porcentagem =  (meta.getValorAlcancado() / meta.getValorMeta()) * 100;
            progresso = (int) porcentagem;

            PB_Progresso.setProgress(progresso);

            double x = (int)(meta.getValorMeta() - meta.getValorAlcancado()) / meta.getValorEconomia();
            previsao = (int) x;

            TXT_Previsao.setText(Integer.toString(previsao) + " Meses");

        } else {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);

            dlg.setTitle("ERRO");
            dlg.setMessage("Ocorreu Algum Problema Inesperado!");
            dlg.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface arg, int argInt){
                    startActivity(intentMenuPrincipal);
                }
            });
            dlg.show();
        }

        return meta;
    }

    public void excluiMeta(View view){
        final Intent intentMenuPrincipal = new Intent(getApplicationContext(), ActvMain.class);
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);

        if(dado != null) {
            dlg.setTitle("Excluir");
            dlg.setMessage("Você Tem Certeza que Quer Excluir Esta Meta?");

            dlg.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface arg, int argInt){
                    metaRepositorio.excluir(dado.getNome());
                    startActivity(intentMenuPrincipal);
                }
            });

            dlg.setNeutralButton("Cancelar", null);

            dlg.show();

        } else {
            dlg.setTitle("ERRO");
            dlg.setMessage("Ocorreu Algum Problema Inesperado!");
            dlg.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface arg, int argInt){
                    startActivity(intentMenuPrincipal);
                }
            });
            dlg.show();
        }
    }

    public void retornaMenuInicial(View view){
        Intent intentMenuPrincipal = new Intent(getApplicationContext(), ActvMain.class);
        startActivity(intentMenuPrincipal);
    }

    public void registraAtividade(View view){
        AlertDialog.Builder dl = new AlertDialog.Builder(this);

        dl.setTitle("Quanto Você Está Disposto a Adicionar?");

        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editText);

        dl.setView(layout);

        dl.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {
                Meta meta = new Meta();
                meta = dado;
                meta.setValorAlcancado(Double.parseDouble(editText.getText().toString()));
                metaRepositorio.alterar(meta);

                Intent intent = new Intent(getApplicationContext(), ActvVisualizaMeta.class);
                intent.putExtra("META", meta);
                startActivity(intent);
            }
        });

        dl.setNeutralButton("Cancelar", null);
        dl.show();
    }
}
