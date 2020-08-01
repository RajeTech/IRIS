package com.iris.ramilton.iris.modelo;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Carro implements Serializable {

    private int id;
    private int fkCvli;
    private String dsTipoCarro;
    private String dsMarcaCarro;
    private String dsModeloCarro;
    private String dsCorCarro;
    private String dsPlacaCarro;
    private String dsDescricaoCarro;
    private String dsDescricaoBiscicleta;
    private int ckbNIdentificadoMarcaCarroCrime;
    private int ckbNIdentificadoModeloCarroCrime;
    private int ckbNIdentificadoCorCarroCrime;
    private int ckbNidentificadoPlacaCarroCrime;
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

    public String getDsTipoCarro() {
        return dsTipoCarro;
    }

    public void setDsTipoCarro(String dsTipoCarro) {
        this.dsTipoCarro = dsTipoCarro;
    }

    public String getDsMarcaCarro() {
        return dsMarcaCarro;
    }

    public void setDsMarcaCarro(String dsMarcaCarro) {
        this.dsMarcaCarro = dsMarcaCarro;
    }

    public String getDsModeloCarro() {
        return dsModeloCarro;
    }

    public void setDsModeloCarro(String dsModeloCarro) {
        this.dsModeloCarro = dsModeloCarro;
    }

    public String getDsCorCarro() {
        return dsCorCarro;
    }

    public void setDsCorCarro(String dsCorCarro) {
        this.dsCorCarro = dsCorCarro;
    }

    public String getDsPlacaCarro() {
        return dsPlacaCarro;
    }

    public void setDsPlacaCarro(String dsPlacaCarro) {
        this.dsPlacaCarro = dsPlacaCarro;
    }

    public String getDsDescricaoBiscicleta() {
        return dsDescricaoBiscicleta;
    }

    public void setDsDescricaoBiscicleta(String dsDescricaoBiscicleta) {
        this.dsDescricaoBiscicleta = dsDescricaoBiscicleta;
    }

    @Override
    public String toString(){
        return dsTipoCarro;
    }


    public int getCkbNIdentificadoMarcaCarroCrime() {
        return ckbNIdentificadoMarcaCarroCrime;
    }

    public void setCkbNIdentificadoMarcaCarroCrime(int ckbNIdentificadoMarcaCarroCrime) {
        this.ckbNIdentificadoMarcaCarroCrime = ckbNIdentificadoMarcaCarroCrime;
    }

    public int getCkbNIdentificadoModeloCarroCrime() {
        return ckbNIdentificadoModeloCarroCrime;
    }

    public void setCkbNIdentificadoModeloCarroCrime(int ckbNIdentificadoModeloCarroCrime) {
        this.ckbNIdentificadoModeloCarroCrime = ckbNIdentificadoModeloCarroCrime;
    }

    public int getCkbNIdentificadoCorCarroCrime() {
        return ckbNIdentificadoCorCarroCrime;
    }

    public void setCkbNIdentificadoCorCarroCrime(int ckbNIdentificadoCorCarroCrime) {
        this.ckbNIdentificadoCorCarroCrime = ckbNIdentificadoCorCarroCrime;
    }

    public int getCkbNidentificadoPlacaCarroCrime() {
        return ckbNidentificadoPlacaCarroCrime;
    }

    public void setCkbNidentificadoPlacaCarroCrime(int ckbNidentificadoPlacaCarroCrime) {
        this.ckbNidentificadoPlacaCarroCrime = ckbNidentificadoPlacaCarroCrime;
    }

    public String getDsDescricaoCarro() {
        return dsDescricaoCarro;
    }

    public void setDsDescricaoCarro(String dsDescricaoCarro) {
        this.dsDescricaoCarro = dsDescricaoCarro;
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

    public void PreencherCamposCVLICarro(JSONObject car) {

        try {
            this.setDsTipoCarro(car.getString("dsTipoCarro"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsMarcaCarro(car.getString("dsMarcaCarro"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsModeloCarro(car.getString("dsModeloCarro"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsDescricaoCarro(car.getString("dsDescricaoCarro"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsCorCarro(car.getString("dsCorCarro"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsPlacaCarro(car.getString("dsPlacaCarro"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            ckbNIdentificadoMarcaCarroCrime = car.getInt("ckbNIdentificadoMarcaCarroCrime");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             ckbNIdentificadoModeloCarroCrime = car.getInt("ckbNIdentificadoModeloCarroCrime");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             ckbNIdentificadoCorCarroCrime = car.getInt("ckbNIdentificadoCorCarroCrime");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             ckbNidentificadoPlacaCarroCrime = car.getInt("ckbNidentificadoPlacaCarroCrime");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setControle(car.getInt("Controle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setIdUnico(car.getString("idUnico"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
