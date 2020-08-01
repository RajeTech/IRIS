package com.iris.ramilton.iris.modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class UnidadeBO {

    private int id;
    private String dsUnidadeBO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDsUnidadeBO() {
        return dsUnidadeBO;
    }

    public void setDsUnidadeBO(String dsUnidadeBO) {
        this.dsUnidadeBO = dsUnidadeBO;
    }

    public void PreencherCamposUnidadeBO(JSONObject unibo) {
        try {
            this.setDsUnidadeBO(unibo.getString("descricao"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
