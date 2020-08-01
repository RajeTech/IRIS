package com.iris.ramilton.iris.modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Vitima implements Serializable {

    private int id;
    private int fkCvli;
    private int ckbIdentificadaVitima;
    private int cbkNaoIdentificadaVitima;
    private String dsNomeVitima;
    private String dsRGVitima;
    private String dsOrgaoExpRGVitima;
    private String dsSexoVitima;
    private String dsCPFVitima;
    private String dsNomeMaeVitima;
    private String dsNomePaiVitima;
    private String dsNascionalidadeVitima;
    private String dsNaturalidadeVitima;
    private String dsOrientacaoSexualVitima;
    private String dsProfissaoVitima;
    private String dsCondicaoSaudeVitima;
    private int Controle;
    private String idUnico;
    private String dsEstadoVitima;
    private String dsCondicaoVitima;
    private String dsDtNascimentoVitima;

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

    public void setCkbIdentificadaVitima(int ckbIdentificadaVitima){
        this.ckbIdentificadaVitima = ckbIdentificadaVitima;
    }

    public int getCkbIdentificadaVitima(){
        return ckbIdentificadaVitima;
    }

    public void setCbkNaoIdentificadaVitima(int cbkNaoIdentificadaVitima){
        this.cbkNaoIdentificadaVitima = cbkNaoIdentificadaVitima;
    }

    public int getCbkNaoIdentificadaVitima(){
        return cbkNaoIdentificadaVitima;
    }

    public String getDsNomeVitima() {
        return dsNomeVitima;
    }

    public void setDsNomeVitima(String dsNomeVitima) {
        this.dsNomeVitima = dsNomeVitima;
    }

    public String getDsRGVitima() {
        return dsRGVitima;
    }

    public void setDsRGVitima(String dsRGVitima) {
        this.dsRGVitima = dsRGVitima;
    }

    public String getDsOrgaoExpRGVitima() {
        return dsOrgaoExpRGVitima;
    }

    public void setDsOrgaoExpRGVitima(String dsOrgaoExpRGVitima) {
        this.dsOrgaoExpRGVitima = dsOrgaoExpRGVitima;
    }

    public String getDsSexoVitima() {
        return dsSexoVitima;
    }

    public void setDsSexoVitima(String dsSexoVitima) {
        this.dsSexoVitima = dsSexoVitima;
    }

    public String getDsCPFVitima() {
        return dsCPFVitima;
    }

    public void setDsCPFVitima(String dsCPFVitima) {
        this.dsCPFVitima = dsCPFVitima;
    }

    public String getDsNomeMaeVitima() {
        return dsNomeMaeVitima;
    }

    public void setDsNomeMaeVitima(String dsNomeMaeVitima) {
        this.dsNomeMaeVitima = dsNomeMaeVitima;
    }

    public String getDsNomePaiVitima() {
        return dsNomePaiVitima;
    }

    public void setDsNomePaiVitima(String dsNomePaiVitima) {
        this.dsNomePaiVitima = dsNomePaiVitima;
    }

    public String getDsNascionalidadeVitima() {
        return dsNascionalidadeVitima;
    }

    public void setDsNascionalidadeVitima(String dsNascionalidadeVitima) {
        this.dsNascionalidadeVitima = dsNascionalidadeVitima;
    }

    public String getDsNaturalidadeVitima() {
        return dsNaturalidadeVitima;
    }

    public void setDsNaturalidadeVitima(String dsNaturalidadeVitima) {
        this.dsNaturalidadeVitima = dsNaturalidadeVitima;
    }

    public String getDsOrientacaoSexualVitima() {
        return dsOrientacaoSexualVitima;
    }

    public void setDsOrientacaoSexualVitima(String dsOrientacaoSexualVitima) {
        this.dsOrientacaoSexualVitima = dsOrientacaoSexualVitima;
    }

    public String getDsProfissaoVitima() {
        return dsProfissaoVitima;
    }

    public void setDsProfissaoVitima(String dsProfissaoVitima) {
        this.dsProfissaoVitima = dsProfissaoVitima;
    }

    public String getDsCondicaoSaudeVitima() {
        return dsCondicaoSaudeVitima;
    }

    public void setDsCondicaoSaudeVitima(String dsCondicaoSaudeVitima) {
        this.dsCondicaoSaudeVitima = dsCondicaoSaudeVitima;
    }

    public int getControle() {
        return Controle;
    }

    public void setControle(int controle) {
        Controle = controle;
    }

    @Override
    public String toString(){
        if(getCbkNaoIdentificadaVitima() == 1){
            return "Não identificado";
        }else {
            return dsNomeVitima;
        }

    }

    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public String getDsEstadoVitima() {
        return dsEstadoVitima;
    }

    public void setDsEstadoVitima(String dsEstadoVitima) {
        this.dsEstadoVitima = dsEstadoVitima;
    }

    public String getDsCondicaoVitima() {
        return dsCondicaoVitima;
    }

    public void setDsCondicaoVitima(String dsCondicaoVitima) {
        this.dsCondicaoVitima = dsCondicaoVitima;
    }
    public String getDsDtNascimentoVitima() {
        return dsDtNascimentoVitima;
    }

    public void setDsDtNascimentoVitima(String dsDtNascimentoVitima) {
        this.dsDtNascimentoVitima = dsDtNascimentoVitima;
    }

    public void PreencherCamposCVLIVitimas(JSONObject vit) {


        try {
            if(vit.get("ckbIdentificadaVitima") == null){
                ckbIdentificadaVitima = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(vit.get("cbkNaoIdentificadaVitima") == null){
                cbkNaoIdentificadaVitima = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            Log.i("dados","nomevitima "+ vit.getString("dsNomeVitima"));
            this.setDsNomeVitima(vit.getString("dsNomeVitima"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsRGVitima(vit.getString("dsRGVitima"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsOrgaoExpRGVitima(vit.getString("dsOrgaoExpRGVitima"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(vit.getString("dsSexoVitima") == "M"){
                this.setDsSexoVitima("Masculino");
            }else if(vit.getString("dsSexoVitima") == "F"){
                this.setDsSexoVitima("Feminino");
            }else{
                this.setDsSexoVitima("Desconhecida");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsCPFVitima(vit.getString("dsCPFVitima"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNomeMaeVitima(vit.getString("dsNomeMaeVitima"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNomePaiVitima(vit.getString("dsNomePaiVitima"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if((vit.getInt("dsNascionalidadeVitima") == 10)){
                this.setDsNascionalidadeVitima("Brasileiro");
            }else{
                this.setDsNascionalidadeVitima("Estrangeiro");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNaturalidadeVitima(vit.getString("dsNaturalidadeVitima"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(vit.getString("dsOrientacaoSexualVitima") == "HE"){
                this.setDsOrientacaoSexualVitima("Heterossexual");
            }
            if(vit.getString("dsOrientacaoSexualVitima") == "HO"){
                this.setDsOrientacaoSexualVitima("Homossexual");
            }
            if(vit.getString("dsOrientacaoSexualVitima") == "BI"){
                this.setDsOrientacaoSexualVitima("Bissexual ");
            }
            if(vit.getString("dsOrientacaoSexualVitima") == "TR"){
                this.setDsOrientacaoSexualVitima("Transsexual");
            }
            if(vit.getString("dsOrientacaoSexualVitima") == "TRA"){
                this.setDsOrientacaoSexualVitima("Travesti");
            }
            if(vit.getString("dsOrientacaoSexualVitima") == "OU"){
                this.setDsOrientacaoSexualVitima("Outros");
            }
            if(vit.getString("dsOrientacaoSexualVitima") == "NA"){
                this.setDsOrientacaoSexualVitima("Não Informado");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsProfissaoVitima(vit.getString("dsProfissaoVitima"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsCondicaoSaudeVitima(vit.getString("dsCondicaoSaudeVitima"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setControle(vit.getInt("Controle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setIdUnico(vit.getString("idUnico"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsEstadoVitima(vit.getString("dsEstadoVitima"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsCondicaoVitima(vit.getString("dsCondicaoVitima"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            String data = vit.getString("dsDtNascimentoVitima");
            //passar do formato texto para tipo data
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = inputFormat.parse(data);

            //depois transformar para o padrão brasileiro
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
            final String formattedDate = sdf.format(inputDate);
            this.setDsDtNascimentoVitima(formattedDate);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
