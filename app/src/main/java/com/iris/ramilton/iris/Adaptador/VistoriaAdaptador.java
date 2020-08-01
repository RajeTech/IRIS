package com.iris.ramilton.iris.Adaptador;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iris.ramilton.iris.R;
import com.iris.ramilton.iris.modelo.Vistoria;

import java.util.List;

public class VistoriaAdaptador extends BaseAdapter {

    private List<Vistoria> vistoriaList;
    private Activity activity;
    private int codigoestatus;

    public VistoriaAdaptador(Activity activity, List<Vistoria> vistoriaList) {
        this.activity = activity;
        this.vistoriaList = vistoriaList;
    }

    @Override
    public int getCount() {
        return vistoriaList.size();
    }

    @Override
    public Object getItem(int position) {
        return vistoriaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vistoriaList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = activity.getLayoutInflater().inflate(R.layout.itemveiculospericiar, parent, false);
        TextView codigo = v.findViewById(R.id.tvIDVeiculoPericiar);
        TextView descricaoveiculop = v.findViewById(R.id.tvDescricaoVeiculo);


        Vistoria a = vistoriaList.get(position);


        codigoestatus = a.getControle();

        if(codigoestatus == 2){
            codigo.setTextColor(Color.RED);
            descricaoveiculop.setTextColor(Color.RED);
        }else{
            if(codigoestatus == 4){
                codigo.setTextColor(Color.BLUE);
                descricaoveiculop.setTextColor(Color.BLUE);
            }else{
                codigo.setTextColor(Color.BLACK);
                descricaoveiculop.setTextColor(Color.BLACK);
            }
        }

        if(a.getValidarcarropericiado() == 1){
            codigo.setText("VISTORIADO");
        }else{
            codigo.setText("NÃO VISTORIADO");
        }

        descricaoveiculop.setText(a.getDescricao());

        return v;
    }
}
