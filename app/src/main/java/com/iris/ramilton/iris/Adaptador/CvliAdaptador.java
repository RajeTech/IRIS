package com.iris.ramilton.iris.Adaptador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iris.ramilton.iris.R;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.VitimaDao;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.Vitima;

import java.util.List;

public class CvliAdaptador extends BaseAdapter {

    private List<Cvli> cvliList;
    private Activity activity;
    private int codigoestatus;

    public CvliAdaptador(Activity activity, List<Cvli> cvliList) {
        this.activity = activity;
        this.cvliList = cvliList;

    }

    @Override
    public int getCount() {
        return cvliList.size();
    }

    @Override
    public Object getItem(int position) {
        return cvliList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cvliList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = activity.getLayoutInflater().inflate(R.layout.itemcvli, parent, false);
        TextView codigo = v.findViewById(R.id.tvIDLista);
        TextView municipiofato = v.findViewById(R.id.tvMunicipioFatoCvli);
        TextView datafato = v.findViewById(R.id.tvDtFatoCvli);
        TextView vitimafato = v.findViewById(R.id.tvVitimaCvli);
        TextView municipiolegenda = v.findViewById(R.id.tvMunicipioLegenda);

        TextView datafatolegenda = v.findViewById(R.id.tvDTFatoLegenda);

        TextView vitimalegenda = v.findViewById(R.id.tvVitimaLegenda);

        Cvli a = cvliList.get(position);


        codigoestatus = a.getControle();

        if(codigoestatus == 2){
            codigo.setTextColor(Color.RED);
            municipiolegenda.setTextColor(Color.RED);
            datafatolegenda.setTextColor(Color.RED);
            vitimalegenda.setTextColor(Color.RED);
            codigo.setTextColor(Color.RED);
            datafato.setTextColor(Color.RED);
            municipiofato.setTextColor(Color.RED);
            vitimafato.setTextColor(Color.RED);
        }else{
            if(codigoestatus == 4){
                codigo.setTextColor(Color.BLUE);
                municipiolegenda.setTextColor(Color.BLUE);
                datafatolegenda.setTextColor(Color.BLUE);
                vitimalegenda.setTextColor(Color.BLUE);
                codigo.setTextColor(Color.BLUE);
                datafato.setTextColor(Color.BLUE);
                municipiofato.setTextColor(Color.BLUE);
                vitimafato.setTextColor(Color.BLUE);
            }else{
                codigo.setTextColor(Color.BLACK);
                municipiolegenda.setTextColor(Color.BLACK);
                datafatolegenda.setTextColor(Color.BLACK);
                vitimalegenda.setTextColor(Color.BLACK);
                codigo.setTextColor(Color.BLACK);
                datafato.setTextColor(Color.BLACK);
                municipiofato.setTextColor(Color.BLACK);
                vitimafato.setTextColor(Color.BLACK);
            }
        }

        if(a.getValidarcvli() == 1){
            codigo.setText("VALIDADO");
        }else{
            codigo.setText("PENDENTE");
        }


        if(a.retornarNomesVitimas(activity) == "" || a.retornarNomesVitimas(activity) == null || a.retornarNomesVitimas(activity) == "; "){
            vitimafato.setText("Indefinido");
        }else{
            vitimafato.setText(a.retornarNomesVitimas(activity));
        }
        datafato.setText(a.getDtFato());
        municipiofato.setText(a.getDsMunicipioFato());

        return v;
    }


}
