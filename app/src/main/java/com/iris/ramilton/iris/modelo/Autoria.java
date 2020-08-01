package com.iris.ramilton.iris.modelo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Autoria implements Serializable {

    private int id;
    private int fkCvli;
    private int cbkAutoriaDefinida;
    private int cbkAutoriaNDefinida;
    private int cbkAutoriaSuspeita;
    private String dsNomeAutoria;
    private String dsRGAutoria;
    private String dsOrgaoExpRGAutoria;
    private String dsSexoAutoria;
    private String dsCPFAutoria;
    private int dsIdadeAutoria;
    private String dsCurtisAutoria;
    private String dsNomeMaeAutoria;
    private String dsNomePaiAutoria;
    private String dsNascionalidadeAutoria;
    private String dsNaturalidadeAutoria;
    private String dsCondicaoAutoria;
    private String dtPrisaoAutoria;
    private String dsLocalPrisaoAutoria;
    private String hsHorarioPrisaoAutoria;
    private String dsNaturezaPrisaoAutoria;
    private String dsResponsavelPrisaoAutoria;
    private int Controle;
    private String idUnico;

    private String dsVulgoAutoria;

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

    public void setCbkAutoriaDefinida(int cbkAutoriaDefinida){
        this.cbkAutoriaDefinida = cbkAutoriaDefinida;
    }

    public int getCbkAutoriaDefinida(){
        return cbkAutoriaDefinida;
    }

    public void setCbkAutoriaNDefinida(int cbkAutoriaNDefinida){
        this.cbkAutoriaNDefinida = cbkAutoriaNDefinida;
    }

    public int getCbkAutoriaNDefinida(){
        return cbkAutoriaNDefinida;
    }

    public void setCbkAutoriaSuspeita(int cbkAutoriaSuspeita){
        this.cbkAutoriaSuspeita = cbkAutoriaSuspeita;
    }

    public int getCbkAutoriaSuspeita(){
        return cbkAutoriaSuspeita;
    }

    public String getDsNomeAutoria() {
        return dsNomeAutoria;
    }

    public void setDsNomeAutoria(String dsNomeAutoria) {
        this.dsNomeAutoria = dsNomeAutoria;
    }

    public String getDsRGAutoria() {
        return dsRGAutoria;
    }

    public void setDsRGAutoria(String dsRGAutoria) {
        this.dsRGAutoria = dsRGAutoria;
    }

    public String getDsOrgaoExpRGAutoria() {
        return dsOrgaoExpRGAutoria;
    }

    public void setDsOrgaoExpRGAutoria(String dsOrgaoExpRGAutoria) {
        this.dsOrgaoExpRGAutoria = dsOrgaoExpRGAutoria;
    }

    public String getDsSexoAutoria() {
        return dsSexoAutoria;
    }

    public void setDsSexoAutoria(String dsSexoAutoria) {
        this.dsSexoAutoria = dsSexoAutoria;
    }

    public String getDsCPFAutoria() {
        return dsCPFAutoria;
    }

    public void setDsCPFAutoria(String dsCPFAutoria) {
        this.dsCPFAutoria = dsCPFAutoria;
    }

    public int getDsIdadeAutoria() {
        return dsIdadeAutoria;
    }

    public void setDsIdadeAutoria(int dsIdadeAutoria) {
        this.dsIdadeAutoria = dsIdadeAutoria;
    }

    public String getDsCurtisAutoria() {
        return dsCurtisAutoria;
    }

    public void setDsCurtisAutoria(String dsCurtisAutoria) {
        this.dsCurtisAutoria = dsCurtisAutoria;
    }

    public String getDsNomeMaeAutoria() {
        return dsNomeMaeAutoria;
    }

    public void setDsNomeMaeAutoria(String dsNomeMaeAutoria) {
        this.dsNomeMaeAutoria = dsNomeMaeAutoria;
    }

    public String getDsNomePaiAutoria() {
        return dsNomePaiAutoria;
    }

    public void setDsNomePaiAutoria(String dsNomePaiAutoria) {
        this.dsNomePaiAutoria = dsNomePaiAutoria;
    }

    public String getDsNascionalidadeAutoria() {
        return dsNascionalidadeAutoria;
    }

    public void setDsNascionalidadeAutoria(String dsNascionalidadeAutoria) {
        this.dsNascionalidadeAutoria = dsNascionalidadeAutoria;
    }

    public String getDsNaturalidadeAutoria() {
        return dsNaturalidadeAutoria;
    }

    public void setDsNaturalidadeAutoria(String dsNaturalidadeAutoria) {
        this.dsNaturalidadeAutoria = dsNaturalidadeAutoria;
    }

    public String getDsCondicaoAutoria() {
        return dsCondicaoAutoria;
    }

    public void setDsCondicaoAutoria(String dsCondicaoAutoria) {
        this.dsCondicaoAutoria = dsCondicaoAutoria;
    }

    public String getDtPrisaoAutoria() {
        return dtPrisaoAutoria;
    }

    public void setDtPrisaoAutoria(String dtPrisaoAutoria) {
        this.dtPrisaoAutoria = dtPrisaoAutoria;
    }

    public String getDsLocalPrisaoAutoria() {
        return dsLocalPrisaoAutoria;
    }

    public void setDsLocalPrisaoAutoria(String dsLocalPrisaoAutoria) {
        this.dsLocalPrisaoAutoria = dsLocalPrisaoAutoria;
    }

    public String getHsHorarioPrisaoAutoria() {
        return hsHorarioPrisaoAutoria;
    }

    public void setHsHorarioPrisaoAutoria(String hsHorarioPrisaoAutoria) {
        this.hsHorarioPrisaoAutoria = hsHorarioPrisaoAutoria;
    }

    public String getDsNaturezaPrisaoAutoria() {
        return dsNaturezaPrisaoAutoria;
    }

    public void setDsNaturezaPrisaoAutoria(String dsNaturezaPrisaoAutoria) {
        this.dsNaturezaPrisaoAutoria = dsNaturezaPrisaoAutoria;
    }

    public String getDsResponsavelPrisaoAutoria() {
        return dsResponsavelPrisaoAutoria;
    }

    public void setDsResponsavelPrisaoAutoria(String dsResponsavelPrisaoAutoria) {
        this.dsResponsavelPrisaoAutoria = dsResponsavelPrisaoAutoria;
    }

    public void setControle(int Controle){
        this.Controle = Controle;
    }

    public int getControle(){
        return Controle;
    }
    @Override
    public String toString(){
        if(getCbkAutoriaNDefinida() == 1){
            return "Não Identificado";
        }else {
            return dsNomeAutoria;
        }
    }

    public String getIdUnico() {
        return idUnico;
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public void PreencherCamposCVLIautoria(JSONObject aut) {

        try {
            cbkAutoriaDefinida = aut.getInt("cbkAutoriaDefinida");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             cbkAutoriaNDefinida = aut.getInt("cbkAutoriaNDefinida");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             cbkAutoriaSuspeita = aut.getInt("cbkAutoriaSuspeita");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsVulgoAutoria(aut.getString("dsVulgoAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNomeAutoria(aut.getString("dsNomeAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsRGAutoria(aut.getString("dsRGAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsOrgaoExpRGAutoria(aut.getString("dsOrgaoExpRGAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(aut.getString("dsSexoAutoria") == "M"){
                this.setDsSexoAutoria("Masculino");
            }else if(aut.getString("dsSexoAutoria") == "F"){
                this.setDsSexoAutoria("Feminino");
            }else{
                this.setDsSexoAutoria("Desconhecida");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsCPFAutoria(aut.getString("dsCPFAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsIdadeAutoria(aut.getInt("dsIdadeAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsCurtisAutoria(aut.getString("dsCurtisAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNomeMaeAutoria(aut.getString("dsNomeMaeAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNomePaiAutoria(aut.getString("dsNomePaiAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if((aut.getInt("dsNascionalidadeAutoria") == 10)){
                this.setDsNascionalidadeAutoria("Brasileiro");
            }else{
                this.setDsNascionalidadeAutoria("Estrangeiro");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNaturalidadeAutoria(aut.getString("dsNaturalidadeAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsCondicaoAutoria(aut.getString("dsCondicaoAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String data = aut.getString("dtPrisaoAutoria");
            //passar do formato texto para tipo data
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = inputFormat.parse(data);

            //depois transformar para o padrão brasileiro
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
            final String formattedDate = sdf.format(inputDate);
            this.setDtPrisaoAutoria(formattedDate);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            this.setDsLocalPrisaoAutoria(aut.getString("dsLocalPrisaoAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setHsHorarioPrisaoAutoria(aut.getString("hsHorarioPrisaoAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNaturezaPrisaoAutoria(aut.getString("dsNaturezaPrisaoAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsResponsavelPrisaoAutoria(aut.getString("dsResponsavelPrisaoAutoria"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setControle(aut.getInt("Controle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setIdUnico(aut.getString("idUnico"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDsVulgoAutoria() {
        return dsVulgoAutoria;
    }

    public void setDsVulgoAutoria(String dsVulgoAutoria) {
        this.dsVulgoAutoria = dsVulgoAutoria;
    }

}
