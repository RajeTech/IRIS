package com.iris.ramilton.iris.modelo;

import org.json.JSONException;
import org.json.JSONObject;

public class ObjetosApreendidos {

    private int id;
    private int fkCvli;
    private String dsTipoObjetoApreendido;
    private String dsDescricaoObjetoApreendido;
    private int Controle;
    private String idUnico;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkCvli() {
        return fkCvli;
    }

    public void setFkCvli(int fkCvli) {
        this.fkCvli = fkCvli;
    }

    public String getDsTipoObjetoApreendido() {
        return dsTipoObjetoApreendido;
    }

    public void setDsTipoObjetoApreendido(String dsTipoObjetoApreendido) {
        this.dsTipoObjetoApreendido = dsTipoObjetoApreendido;
    }

    public String getDsDescricaoObjetoApreendido() {
        return dsDescricaoObjetoApreendido;
    }

    public void setDsDescricaoObjetoApreendido(String dsDescricaoObjetoApreendido) {
        this.dsDescricaoObjetoApreendido = dsDescricaoObjetoApreendido;
    }

    public int getControle() {
        return Controle;
    }

    public void setControle(int controle) {
        Controle = controle;
    }

    @Override
    public String toString(){
        return dsTipoObjetoApreendido;
    }


    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public void PreencherCamposCVLIObjetos(JSONObject obj) {

        try {
            this.setDsTipoObjetoApreendido(obj.getString("dsTipoObjetoApreendido"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsDescricaoObjetoApreendido(obj.getString("dsDescricaoObjetoApreendido"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setControle(obj.getInt("Controle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setIdUnico(obj.getString("idUnico"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
