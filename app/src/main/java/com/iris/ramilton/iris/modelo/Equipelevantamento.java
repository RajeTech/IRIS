package com.iris.ramilton.iris.modelo;

import org.json.JSONException;
import org.json.JSONObject;

public class Equipelevantamento {

    private int id;
    private int fkCvli;
    private String dsCargoEquipeLevantamento;
    private String dsNomeEquipeLevantamento;
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

    public String getDsCargoEquipeLevantamento() {
        return dsCargoEquipeLevantamento;
    }

    public void setDsCargoEquipeLevantamento(String dsCargoEquipeLevantamento) {
        this.dsCargoEquipeLevantamento = dsCargoEquipeLevantamento;
    }

    public String getDsNomeEquipeLevantamento() {
        return dsNomeEquipeLevantamento;
    }

    public void setDsNomeEquipeLevantamento(String dsNomeEquipeLevantamento) {
        this.dsNomeEquipeLevantamento = dsNomeEquipeLevantamento;
    }

    public int getControle() {
        return Controle;
    }

    public void setControle(int controle) {
        Controle = controle;
    }

    @Override
    public String toString(){
        return dsCargoEquipeLevantamento +" "+ dsNomeEquipeLevantamento;
    }

    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public void PreencherCamposCVLIEqLevantamento(JSONObject eqlevan) {

        try {
            this.setDsCargoEquipeLevantamento(eqlevan.getString("dsCargoEquipeLevantamento"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNomeEquipeLevantamento(eqlevan.getString("dsNomeEquipeLevantamento"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setControle(eqlevan.getInt("Controle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setIdUnico(eqlevan.getString("idUnico"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
