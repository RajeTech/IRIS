package com.iris.ramilton.iris.modelo;

public class Objapreendidoveiculoacaopolicial {

    private int id;
    private int fkAcaoPolicial;
    private String dsTipoVeiculoAcaoPolicial;
    private int Controle;
    private String idUnico;
    private String dsMarcaVeiculoAcaoPolicial;
    private String dsModeloVeiculoAcaoPolicial;
    private String dsPlacaOstentadaAcaoPolicial;
    private String dsCorVeiculoAcaoPolicial;

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

    public String getDsTipoVeiculoAcaoPolicial() {
        return dsTipoVeiculoAcaoPolicial;
    }

    public void setDsTipoVeiculoAcaoPolicial(String dsTipoVeiculoAcaoPolicial) {
        this.dsTipoVeiculoAcaoPolicial = dsTipoVeiculoAcaoPolicial;
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

    public String getDsMarcaVeiculoAcaoPolicial() {
        return dsMarcaVeiculoAcaoPolicial;
    }

    public void setDsMarcaVeiculoAcaoPolicial(String dsMarcaVeiculoAcaoPolicial) {
        this.dsMarcaVeiculoAcaoPolicial = dsMarcaVeiculoAcaoPolicial;
    }

    public String getDsModeloVeiculoAcaoPolicial() {
        return dsModeloVeiculoAcaoPolicial;
    }

    public void setDsModeloVeiculoAcaoPolicial(String dsModeloVeiculoAcaoPolicial) {
        this.dsModeloVeiculoAcaoPolicial = dsModeloVeiculoAcaoPolicial;
    }

    public String getDsPlacaOstentadaAcaoPolicial() {
        return dsPlacaOstentadaAcaoPolicial;
    }

    public void setDsPlacaOstentadaAcaoPolicial(String dsPlacaOstentadaAcaoPolicial) {
        this.dsPlacaOstentadaAcaoPolicial = dsPlacaOstentadaAcaoPolicial;
    }

    public String getDsCorVeiculoAcaoPolicial() {
        return dsCorVeiculoAcaoPolicial;
    }

    public void setDsCorVeiculoAcaoPolicial(String dsCorVeiculoAcaoPolicial) {
        this.dsCorVeiculoAcaoPolicial = dsCorVeiculoAcaoPolicial;
    }

    @Override
    public String toString(){
        return ""+getDsTipoVeiculoAcaoPolicial();
    }
}
