package com.iris.ramilton.iris.modelo;

import java.io.Serializable;

public class Conduzidoacaopolicial implements Serializable {

    private int id;
    private int fkAcaoPolicial;
    private String dsNomeConduzidoAcaoPolicial;
    private int IdadeConduzidoAcaoPolicial;
    private String dsTipoProcedimentoAcaoPolcial;
    private String dsAtoInfracionalAcaoPolicial;
    private String dsTipoConducaoAcaoPolicial;
    private int Controle;
    private String idUnico;
    private String dsNomeJuizAcaoPolicial;
    private String dsComarcaAcaoPolicial;

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

    public String getDsNomeConduzidoAcaoPolicial() {
        return dsNomeConduzidoAcaoPolicial;
    }

    public void setDsNomeConduzidoAcaoPolicial(String dsNomeConduzidoAcaoPolicial) {
        this.dsNomeConduzidoAcaoPolicial = dsNomeConduzidoAcaoPolicial;
    }

    public int getIdadeConduzidoAcaoPolicial() {
        return IdadeConduzidoAcaoPolicial;
    }

    public void setIdadeConduzidoAcaoPolicial(int idadeConduzidoAcaoPolicial) {
        IdadeConduzidoAcaoPolicial = idadeConduzidoAcaoPolicial;
    }

    public String getDsTipoProcedimentoAcaoPolcial() {
        return dsTipoProcedimentoAcaoPolcial;
    }

    public void setDsTipoProcedimentoAcaoPolcial(String dsTipoProcedimentoAcaoPolcial) {
        this.dsTipoProcedimentoAcaoPolcial = dsTipoProcedimentoAcaoPolcial;
    }

    public String getDsAtoInfracionalAcaoPolicial() {
        return dsAtoInfracionalAcaoPolicial;
    }

    public void setDsAtoInfracionalAcaoPolicial(String dsAtoInfracionalAcaoPolicial) {
        this.dsAtoInfracionalAcaoPolicial = dsAtoInfracionalAcaoPolicial;
    }

    public String getDsTipoConducaoAcaoPolicial() {
        return dsTipoConducaoAcaoPolicial;
    }

    public void setDsTipoConducaoAcaoPolicial(String dsTipoConducaoAcaoPolicial) {
        this.dsTipoConducaoAcaoPolicial = dsTipoConducaoAcaoPolicial;
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

    public String getDsNomeJuizAcaoPolicial() {
        return dsNomeJuizAcaoPolicial;
    }

    public void setDsNomeJuizAcaoPolicial(String dsNomeJuizAcaoPolicial) {
        this.dsNomeJuizAcaoPolicial = dsNomeJuizAcaoPolicial;
    }

    public String getDsComarcaAcaoPolicial() {
        return dsComarcaAcaoPolicial;
    }

    public void setDsComarcaAcaoPolicial(String dsComarcaAcaoPolicial) {
        this.dsComarcaAcaoPolicial = dsComarcaAcaoPolicial;
    }

    @Override
    public String toString(){
        return dsNomeConduzidoAcaoPolicial;
    }
}