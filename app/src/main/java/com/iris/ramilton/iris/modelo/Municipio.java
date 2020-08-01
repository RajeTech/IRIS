package com.iris.ramilton.iris.modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Municipio {

    private int id;
    private String dsMunicipio;
    private int uf_id;
    private int coordenacao_id;
    private int fkAISP;

    public int getUf_id() {
        return uf_id;
    }

    public void setUf_id(int uf_id) {
        this.uf_id = uf_id;
    }

    public int getCoordenacao_id() {
        return coordenacao_id;
    }

    public void setCoordenacao_id(int coordenacao_id) {
        this.coordenacao_id = coordenacao_id;
    }

    public int getFkAISP() {
        return fkAISP;
    }

    public void setFkAISP(int fkAISP) {
        this.fkAISP = fkAISP;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDsMunicipio() {
        return dsMunicipio;
    }

    public void setDsMunicipio(String dsMunicipio) {
        this.dsMunicipio = dsMunicipio;
    }

    public void PreencherCamposMunicipio(JSONObject muni) {
        try {
            this.setDsMunicipio(muni.getString("descricao"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
