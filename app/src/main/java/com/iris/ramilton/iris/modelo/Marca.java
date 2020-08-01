package com.iris.ramilton.iris.modelo;

import org.json.JSONException;
import org.json.JSONObject;

public class Marca {

    private int id;
    private String dsMarcaCarro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDsMarcaCarro() {
        return dsMarcaCarro;
    }

    public void setDsMarcaCarro(String dsMarcaCarro) {
        this.dsMarcaCarro = dsMarcaCarro;
    }

    public void PreencherCamposMarca(JSONObject mar) {
        try {
            this.setDsMarcaCarro(mar.getString("descricao"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
