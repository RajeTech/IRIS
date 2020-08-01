package com.iris.ramilton.iris.modelo;

import org.json.JSONException;
import org.json.JSONObject;

public class Equipepreservacaolocal {

    private int id;
    private int fkCvli;
    private String dsCargoEquipepreservacaolocal;
    private String dsNomeEquipepreservacaolocal;
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

    public String getDsCargoEquipepreservacaolocal() {
        return dsCargoEquipepreservacaolocal;
    }

    public void setDsCargoEquipepreservacaolocal(String dsCargoEquipepreservacaolocal) {
        this.dsCargoEquipepreservacaolocal = dsCargoEquipepreservacaolocal;
    }

    public String getDsNomeEquipepreservacaolocal() {
        return dsNomeEquipepreservacaolocal;
    }

    public void setDsNomeEquipepreservacaolocal(String dsNomeEquipepreservacaolocal) {
        this.dsNomeEquipepreservacaolocal = dsNomeEquipepreservacaolocal;
    }

    public int getControle() {
        return Controle;
    }

    public void setControle(int controle) {
        Controle = controle;
    }

    @Override
    public String toString(){
        return dsCargoEquipepreservacaolocal +" "+ dsNomeEquipepreservacaolocal;
    }

    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public void PreencherCamposCVLIEqPresLocal(JSONObject eqprelocal) {

        try {
            this.setDsCargoEquipepreservacaolocal(eqprelocal.getString("dsCargoEquipepreservacaolocal"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNomeEquipepreservacaolocal(eqprelocal.getString("dsNomeEquipepreservacaolocal"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setControle(eqprelocal.getInt("Controle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setIdUnico(eqprelocal.getString("idUnico"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
