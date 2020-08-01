package com.iris.ramilton.iris.modelo;

public class SubtitulosAcaoPolicial {

    private int id;
    private int fkAcaoPolicial;
    private String dsSubtituloAbertoAcaoPolicial;
    private String TextoSubtituloAcaoPolicial;
    private int Controle;
    private String idUnico;

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

    public String getDsSubtituloAbertoAcaoPolicial() {
        return dsSubtituloAbertoAcaoPolicial;
    }

    public void setDsSubtituloAbertoAcaoPolicial(String dsSubtituloAbertoAcaoPolicial) {
        this.dsSubtituloAbertoAcaoPolicial = dsSubtituloAbertoAcaoPolicial;
    }

    public String getTextoSubtituloAcaoPolicial() {
        return TextoSubtituloAcaoPolicial;
    }

    public void setTextoSubtituloAcaoPolicial(String textoSubtituloAcaoPolicial) {
        TextoSubtituloAcaoPolicial = textoSubtituloAcaoPolicial;
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
}
