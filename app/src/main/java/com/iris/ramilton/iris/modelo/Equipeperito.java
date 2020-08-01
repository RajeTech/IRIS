package com.iris.ramilton.iris.modelo;

import org.json.JSONException;
import org.json.JSONObject;

public class Equipeperito {

    private int id;
    private int fkCvli;
    private String dsCargoEquipePerito;
    private String dsNomeEquipePerito;
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

    public String getDsCargoEquipePerito() {
        return dsCargoEquipePerito;
    }

    public void setDsCargoEquipePerito(String dsCargoEquipePerito) {
        this.dsCargoEquipePerito = dsCargoEquipePerito;
    }

    public String getDsNomeEquipePerito() {
        return dsNomeEquipePerito;
    }

    public void setDsNomeEquipePerito(String dsNomeEquipePerito) {
        this.dsNomeEquipePerito = dsNomeEquipePerito;
    }

    public int getControle() {
        return Controle;
    }

    public void setControle(int controle) {
        Controle = controle;
    }

    @Override
    public String toString(){
        return dsCargoEquipePerito +" "+ dsNomeEquipePerito;
    }

    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public void PreencherCamposCVLIEqPerito(JSONObject eqperito) {

        try {
            this.setDsCargoEquipePerito(eqperito.getString("dsCargoEquipePerito"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNomeEquipePerito(eqperito.getString("dsNomeEquipePerito"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setControle(eqperito.getInt("Controle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setIdUnico(eqperito.getString("idUnico"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
