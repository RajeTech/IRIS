package com.iris.ramilton.iris.Adaptador;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iris.ramilton.iris.R;
import com.iris.ramilton.iris.modelo.Acaopolicial;

import java.util.List;

public class AcaoPolicialAdaptador extends BaseAdapter {

    private List<Acaopolicial> acaopolicialList;
    private Activity activity;
    private int codigoestatus;

    public AcaoPolicialAdaptador(Activity activity, List<Acaopolicial> acaopolicialList) {
        this.activity = activity;
        this.acaopolicialList = acaopolicialList;

    }

    @Override
    public int getCount() {
        return acaopolicialList.size();
    }

    @Override
    public Object getItem(int position) {
        return acaopolicialList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return acaopolicialList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = activity.getLayoutInflater().inflate(R.layout.itemacaopolicial, parent, false);
        TextView statusoperacaopolicial = v.findViewById(R.id.tvStatusOperacao);
        TextView dataoperacaopolicial = v.findViewById(R.id.tvDtOperacao);
        TextView datavisiveloperacaopolicial = v.findViewById(R.id.tvDtVisOperacao);
        TextView nomeoperacaopolicial = v.findViewById(R.id.tvNomeOperacao);
        TextView nomeoperacaovisivelpolicial = v.findViewById(R.id.tvNomeVisOperacao);

        Acaopolicial a = acaopolicialList.get(position);


        codigoestatus = a.getControle();

        if(codigoestatus == 2){
            statusoperacaopolicial.setTextColor(Color.RED);
            dataoperacaopolicial.setTextColor(Color.RED);
            datavisiveloperacaopolicial.setTextColor(Color.RED);
            nomeoperacaopolicial.setTextColor(Color.RED);
            nomeoperacaovisivelpolicial.setTextColor(Color.RED);
        }else{
            if(codigoestatus == 4){
                statusoperacaopolicial.setTextColor(Color.BLUE);
                dataoperacaopolicial.setTextColor(Color.BLUE);
                datavisiveloperacaopolicial.setTextColor(Color.BLUE);
                nomeoperacaopolicial.setTextColor(Color.BLUE);
                nomeoperacaovisivelpolicial.setTextColor(Color.BLUE);
            }else{
                statusoperacaopolicial.setTextColor(Color.BLACK);
                dataoperacaopolicial.setTextColor(Color.BLACK);
                datavisiveloperacaopolicial.setTextColor(Color.BLACK);
                nomeoperacaopolicial.setTextColor(Color.BLACK);
                nomeoperacaovisivelpolicial.setTextColor(Color.BLACK);
            }
        }

        if(a.getValidaracaopolicial() == 1){
            statusoperacaopolicial.setText("VALIDADO");
        }else{
            statusoperacaopolicial.setText("PENDENTE");
        }

        datavisiveloperacaopolicial.setText(a.getDtAcaoPolicial());
        nomeoperacaovisivelpolicial.setText(a.getDsNomeOperacaoPolicial());

        return v;
    }

}
