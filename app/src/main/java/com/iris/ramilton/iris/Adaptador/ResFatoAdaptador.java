package com.iris.ramilton.iris.Adaptador;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iris.ramilton.iris.R;
import com.iris.ramilton.iris.modelo.Cvli;

import java.util.List;

public class ResFatoAdaptador extends BaseAdapter {

    private List<Cvli> cvliList;
    private Activity activity;

    public ResFatoAdaptador(Activity activity, List<Cvli> cvliList) {
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

        View v = activity.getLayoutInflater().inflate(R.layout.resfato, parent, false);
        TextView Municipiorel = v.findViewById(R.id.tvMunicipioRel);
        TextView Dtfatorel = v.findViewById(R.id.tvDtFatoRel);
        TextView Vitimarel = v.findViewById(R.id.tvVitimaRel);

        Cvli a = cvliList.get(position);


        if(a.retornarNomesVitimas(activity) == "" || a.retornarNomesVitimas(activity) == null || a.retornarNomesVitimas(activity) == "; "){
            Vitimarel.setText("Indefinido");
        }else{
            Vitimarel.setText(a.retornarNomesVitimas(activity));
        }
        Dtfatorel.setText(a.getDtFato());
        Municipiorel.setText(a.getDsMunicipioFato());

        return v;
    }


}
