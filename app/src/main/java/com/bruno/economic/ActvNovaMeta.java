package com.bruno.economic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.bruno.economic.DBHElper.MetasDb;
import com.bruno.economic.dominio.entidades.Meta;
import com.bruno.economic.dominio.repositorio.MetaRepositorio;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

public class ActvNovaMeta extends AppCompatActivity {
    private ConstraintLayout actv_nova_meta;

    private EditText ET_Nome;
    private EditText ET_Total;
    private EditText ET_Economia;
    private EditText ET_Fim;

    private Button btnSalvar;

    private MetaRepositorio metaRepositorio;

    private SQLiteDatabase conexao;
    private MetasDb metasDb;

    private Meta meta;

    private int mes, dia, ano;
    static final int DATE_ID = 0;

    Calendar calendario = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_nova_meta);

        actv_nova_meta = (ConstraintLayout) findViewById(R.id.actv_nova_meta);
        criarConexao();

        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        ano = calendario.get(Calendar.YEAR);

        ET_Nome = (EditText) findViewById(R.id.ET_Nome);
        ET_Total = (EditText) findViewById(R.id.ET_Total);
        ET_Economia = (EditText) findViewById(R.id.ET_Economia);
        ET_Fim = (EditText) findViewById(R.id.ET_Fim);

        ET_Fim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });


        //REALIZA AS AÇOES DO BOTAO;
        btnSalvar = (Button) findViewById(R.id.BTN_Salvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    confirma();

                    Intent intentB = new Intent(getApplicationContext(), ActvMain.class);
                    startActivity(intentB);

                } catch(Exception e){

                }
            }
        });
    }

    private void colocaDataEtFim() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        String dataRecebida = Integer.toString(dia) + "/" + Integer.toString(mes + 1) + "/" + Integer.toString(ano);

        Date data = new Date();

        try{
            data = formato.parse(dataRecebida);
        } catch(Exception e){

        }

        String dataFormatada = formato.format(data).toString();

        ET_Fim.setText(dataFormatada);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes, dia);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int getAno, int getMes, int getDia) {
                    dia = getDia;
                    mes = getMes;
                    ano = getAno;
                    colocaDataEtFim();

                }

            };

    private void confirma() throws Exception{
        meta = new Meta();

        if(camposValidos() == false){
            try {
                metaRepositorio.inserir(meta);
            } catch (SQLException e){
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Aviso");
                dlg.setMessage("Não é Possivel Criar Nova Meta no Momento!");
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }
        } else {
            throw new Exception();
        }


    }


    private boolean camposValidos(){
        boolean resultado = false;

        String nome = ET_Nome.getText().toString();
        String totalMeta = ET_Total.getText().toString();
        String economiaEco = ET_Economia.getText().toString();
        String fim = ET_Fim.getText().toString();

        double total = 0;
        double economia = 0;

        if(resultado = campoVazio(nome)){
            ET_Nome.requestFocus();
        }

        else if(resultado = campoVazio(totalMeta)){
            ET_Total.requestFocus();
        }

        else if(campoVazio(economiaEco) || Double.parseDouble(economiaEco) <= 0){
            resultado = true;
            ET_Economia.requestFocus();
        }

        else if(resultado = !dataValida(fim)){
            ET_Fim.requestFocus();
        }


        try{
            total =  Double.parseDouble(totalMeta);
            economia = Double.parseDouble(economiaEco);

        } catch(Exception e){
            resultado = true;
        }

        if(resultado){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage("Verifique Os Campos!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

            return resultado;

        } else {
            StringBuilder sb = new StringBuilder();
            String data = "";

            meta.setNome(nome);
            meta.setValorMeta(total);
            meta.setValorEconomia(economia);

            sb.append(fim.charAt(0));
            sb.append(fim.charAt(1));
            data = sb.toString();
            meta.setDiaFim(Integer.parseInt(data));
            sb.delete(0, sb.length());



            sb.append(fim.charAt(3));
            sb.append(fim.charAt(4));
            data = sb.toString();
            meta.setMesFim(Integer.parseInt(data));
            sb.delete(0, sb.length());


            sb.append(fim.charAt(6));
            sb.append(fim.charAt(7));
            sb.append(fim.charAt(8));
            sb.append(fim.charAt(9));
            data = sb.toString();
            meta.setAnoFim(Integer.parseInt(data));
            sb.delete(0, sb.length());
        }

        return resultado;
    }


    private boolean campoVazio(String valor){

        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }


    public boolean dataValida(String data){
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate d = LocalDate.parse(data, format);
            return true;

        } catch (DateTimeParseException e){
            return false;
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




}
