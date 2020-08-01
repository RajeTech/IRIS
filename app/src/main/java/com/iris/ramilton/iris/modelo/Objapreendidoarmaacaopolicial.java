package com.iris.ramilton.iris.modelo;

public class Objapreendidoarmaacaopolicial {

    private int id;
    private int fkAcaoPolicial;
    private String dsTipoArmaAcaoPolicial;
    private String dsModeloArmaAcaoPolicial;
    private int Controle;
    private String idUnico;
    private String dsCalibreArmaAcaoPolicial;
    private int QtdArmaAcaoPolicial;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkAcaoPolicial() {
        return fkAcaoPolicial;
    }

    public void setFkAcaoPolicial(int fkAcaoPolicial) {
        this.fkAcaoPolicial = fkAcaoPolicial;
    }

    public String getDsTipoArmaAcaoPolicial() {
        return dsTipoArmaAcaoPolicial;
    }

    public void setDsTipoArmaAcaoPolicial(String dsTipoArmaAcaoPolicial) {
        this.dsTipoArmaAcaoPolicial = dsTipoArmaAcaoPolicial;
    }

    public String getDsModeloArmaAcaoPolicial() {
        return dsModeloArmaAcaoPolicial;
    }

    public void setDsModeloArmaAcaoPolicial(String dsModeloArmaAcaoPolicial) {
        this.dsModeloArmaAcaoPolicial = dsModeloArmaAcaoPolicial;
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

    public String getDsCalibreArmaAcaoPolicial() {
        return dsCalibreArmaAcaoPolicial;
    }

    public void setDsCalibreArmaAcaoPolicial(String dsCalibreArmaAcaoPolicial) {
        this.dsCalibreArmaAcaoPolicial = dsCalibreArmaAcaoPolicial;
    }

    public int getQtdArmaAcaoPolicial() {
        return QtdArmaAcaoPolicial;
    }

    public void setQtdArmaAcaoPolicial(int qtdArmaAcaoPolicial) {
        QtdArmaAcaoPolicial = qtdArmaAcaoPolicial;
    }

    @Override
    public String toString(){
        return ""+getDsTipoArmaAcaoPolicial();
    }

}