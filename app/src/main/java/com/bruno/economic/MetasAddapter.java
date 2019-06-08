package com.bruno.economic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bruno.economic.dominio.entidades.Meta;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MetasAddapter extends RecyclerView.Adapter<MetasAddapter.ViewHolderMetas> {

    private ArrayList<Meta> dados;

    public MetasAddapter(ArrayList<Meta> dados){
        this.dados = dados;
    }

    @NonNull
    @Override
    public MetasAddapter.ViewHolderMetas onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View view = layoutInflater.inflate(R.layout.linhas, viewGroup, false);

        ViewHolderMetas holderMetas = new ViewHolderMetas(view, viewGroup.getContext());

        return holderMetas;
    }

    @Override
    public void onBindViewHolder(@NonNull MetasAddapter.ViewHolderMetas holder, int i) {

        if((dados != null) && (dados.size() > 0)) {
            Meta meta = dados.get(i);

            DecimalFormat nf = new DecimalFormat("###,##0.00");
            holder.TV_Nome.setText(meta.getNome());
            holder.TV_Valor.setText("Valor R$ " + nf.format(meta.getValorMeta()).replaceAll(",", "."));
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderMetas extends RecyclerView.ViewHolder{

        public TextView TV_Nome;
        public TextView TV_Valor;

        public ViewHolderMetas(@NonNull View itemView, final Context context) {
            super(itemView);

            TV_Nome  = (TextView) itemView.findViewById(R.id.TV_NOME);
            TV_Valor = (TextView) itemView.findViewById(R.id.TXT_Valor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(dados.size() > 0) {
                        Meta meta = dados.get(getLayoutPosition());


                        Intent intent = new Intent(context, ActvVisualizaMeta.class);
                        intent.putExtra("META", meta);
                        ((AppCompatActivity) context).startActivity(intent);
                    }

                }
            });
        }
    }
}
