package com.iris.ramilton.iris.modelo;

import org.json.JSONException;
import org.json.JSONObject;

public class GuiapericiaProvidencia {

    private int id;
    private int fkCvli;
    private String dsGuiaPericial;
    private String dsNGuiaPericial;
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

    public String getDsGuiaPericial() {
        return dsGuiaPericial;
    }

    public void setDsGuiaPericial(String dsGuiaPericial) {
        this.dsGuiaPericial = dsGuiaPericial;
    }

    public String getDsNGuiaPericial() {
        return dsNGuiaPericial;
    }

    public void setDsNGuiaPericial(String dsNGuiaPericial) {
        this.dsNGuiaPericial = dsNGuiaPericial;
    }

    public int getControle() {
        return Controle;
    }

    public void setControle(int controle) {
        Controle = controle;
    }

    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    @Override
    public String toString() {

        return "Guia pericial de " + dsGuiaPericial + " Nº " + dsNGuiaPericial;
    }

    public void PreencherCamposCVLIGuias(JSONObject guia) {

        try {
            this.setDsGuiaPericial(guia.getString("dsGuiaPericial"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNGuiaPericial(guia.getString("dsNGuiaPericial"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setControle(guia.getInt("Controle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setIdUnico(guia.getString("idUnico"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
