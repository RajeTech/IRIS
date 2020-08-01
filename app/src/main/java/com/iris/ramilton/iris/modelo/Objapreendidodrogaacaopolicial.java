package com.iris.ramilton.iris.modelo;

public class Objapreendidodrogaacaopolicial {

    private int id;
    private int fkAcaoPolicial;
    private String dsTipoDrogaAcaoPolicial;
    private String dsModoApresentacaoDrogaAcaoPolicial;
    private int Controle;
    private String idUnico;
    private int QtdDrogaAcaoPolicial;
    private String dsPesoGramaAcaoPolicial;
    private String dsOutrasDrogasAcaoPolicial;

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

    public String getDsTipoDrogaAcaoPolicial() {
        return dsTipoDrogaAcaoPolicial;
    }

    public void setDsTipoDrogaAcaoPolicial(String dsTipoDrogaAcaoPolicial) {
        this.dsTipoDrogaAcaoPolicial = dsTipoDrogaAcaoPolicial;
    }

    public String getDsModoApresentacaoDrogaAcaoPolicial() {
        return dsModoApresentacaoDrogaAcaoPolicial;
    }

    public void setDsModoApresentacaoDrogaAcaoPolicial(String dsModoApresentacaoDrogaAcaoPolicial) {
        this.dsModoApresentacaoDrogaAcaoPolicial = dsModoApresentacaoDrogaAcaoPolicial;
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

    public int getQtdDrogaAcaoPolicial() {
        return QtdDrogaAcaoPolicial;
    }

    public void setQtdDrogaAcaoPolicial(int qtdDrogaAcaoPolicial) {
        QtdDrogaAcaoPolicial = qtdDrogaAcaoPolicial;
    }

    public String getDsPesoGramaAcaoPolicial() {
        return dsPesoGramaAcaoPolicial;
    }

    public void setDsPesoGramaAcaoPolicial(String dsPesoGramaAcaoPolicial) {
        this.dsPesoGramaAcaoPolicial = dsPesoGramaAcaoPolicial;
    }

    public String getDsOutrasDrogasAcaoPolicial() {
        return dsOutrasDrogasAcaoPolicial;
    }

    public void setDsOutrasDrogasAcaoPolicial(String dsOutrasDrogasAcaoPolicial) {
        this.dsOutrasDrogasAcaoPolicial = dsOutrasDrogasAcaoPolicial;
    }

    @Override
    public String toString(){
        return ""+getDsTipoDrogaAcaoPolicial();
    }
}

