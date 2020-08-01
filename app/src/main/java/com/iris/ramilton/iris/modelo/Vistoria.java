package com.iris.ramilton.iris.modelo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

public class Vistoria implements Serializable {

    private String descricao;
    private int id;
    private int vistoriador_id;
    private int estepe;
    private int chaveRodas;
    private int triangulo;
    private int macaco;
    private int tapetes;
    private int chaveVeiculo;
    private int CRV;
    private int CRLV;
    private String rodaAro;
    private String rodaTipo;
    private String nivelCombustivel;
    private String quilometragem;
    private int estadoVeiculo;
    private String avarias;
    private byte[] FotoFrente;
    private byte[] FotoFundo;
    private byte[] FotoLateralDireita;
    private byte[] FotoLateralEsquerda;
    private byte[] FotoTeto;
    private byte[] FotoChassi;
    private byte[] FotoMotor;
    private byte[] FotoVidro;
    private byte[] FotoEtiquetaSeguranca;
    private int Controle;
    private String idUnico;
    private int validarcarropericiado;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVistoriador_id() {
        return vistoriador_id;
    }

    public void setVistoriador_id(int vistoriador_id) {
        this.vistoriador_id = vistoriador_id;
    }

    public int getEstepe() {
        return estepe;
    }

    public void setEstepe(int estepe) {
        this.estepe = estepe;
    }

    public int getChaveRodas() {
        return chaveRodas;
    }

    public void setChaveRodas(int chaveRodas) {
        this.chaveRodas = chaveRodas;
    }

    public int getTriangulo() {
        return triangulo;
    }

    public void setTriangulo(int triangulo) {
        this.triangulo = triangulo;
    }

    public int getMacaco() {
        return macaco;
    }

    public void setMacaco(int macaco) {
        this.macaco = macaco;
    }

    public int getTapetes() {
        return tapetes;
    }

    public void setTapetes(int tapetes) {
        this.tapetes = tapetes;
    }

    public int getChaveVeiculo() {
        return chaveVeiculo;
    }

    public void setChaveVeiculo(int chaveVeiculo) {
        this.chaveVeiculo = chaveVeiculo;
    }

    public int getCRV() {
        return CRV;
    }

    public void setCRV(int CRV) {
        this.CRV = CRV;
    }

    public int getCRLV() {
        return CRLV;
    }

    public void setCRLV(int CRLV) {
        this.CRLV = CRLV;
    }

    public String getRodaAro() {
        return rodaAro;
    }

    public void setRodaAro(String rodaAro) {
        this.rodaAro = rodaAro;
    }

    public String getRodaTipo() {
        return rodaTipo;
    }

    public void setRodaTipo(String rodaTipo) {
        this.rodaTipo = rodaTipo;
    }

    public String getNivelCombustivel() {
        return nivelCombustivel;
    }

    public void setNivelCombustivel(String nivelCombustivel) {
        this.nivelCombustivel = nivelCombustivel;
    }

    public String getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(String quilometragem) {
        this.quilometragem = quilometragem;
    }

    public int getEstadoVeiculo() {
        return estadoVeiculo;
    }

    public void setEstadoVeiculo(int estadoVeiculo) {
        this.estadoVeiculo = estadoVeiculo;
    }

    public String getAvarias() {
        return avarias;
    }

    public void setAvarias(String avarias) {
        this.avarias = avarias;
    }

    public byte[] getFotoFrente() {
        return FotoFrente;
    }

    public void setFotoFrente(byte[] fotoFrente) {
        FotoFrente = fotoFrente;
    }

    public byte[] getFotoFundo() {
        return FotoFundo;
    }

    public void setFotoFundo(byte[] fotoFundo) {
        FotoFundo = fotoFundo;
    }

    public byte[] getFotoLateralDireita() {
        return FotoLateralDireita;
    }

    public void setFotoLateralDireita(byte[] fotoLateralDireita) {
        FotoLateralDireita = fotoLateralDireita;
    }

    public byte[] getFotoLateralEsquerda() {
        return FotoLateralEsquerda;
    }

    public void setFotoLateralEsquerda(byte[] fotoLateralEsquerda) {
        FotoLateralEsquerda = fotoLateralEsquerda;
    }

    public byte[] getFotoTeto() {
        return FotoTeto;
    }

    public void setFotoTeto(byte[] fotoTeto) {
        FotoTeto = fotoTeto;
    }

    public byte[] getFotoChassi() {
        return FotoChassi;
    }

    public void setFotoChassi(byte[] fotoChassi) {
        FotoChassi = fotoChassi;
    }

    public byte[] getFotoMotor() {
        return FotoMotor;
    }

    public void setFotoMotor(byte[] fotoMotor) {
        FotoMotor = fotoMotor;
    }

    public byte[] getFotoVidro() {
        return FotoVidro;
    }

    public void setFotoVidro(byte[] fotoVidro) {
        FotoVidro = fotoVidro;
    }

    public byte[] getFotoEtiquetaSeguranca() {
        return FotoEtiquetaSeguranca;
    }

    public void setFotoEtiquetaSeguranca(byte[] fotoEtiquetaSeguranca) {
        FotoEtiquetaSeguranca = fotoEtiquetaSeguranca;
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

    public int getValidarcarropericiado() {
        return validarcarropericiado;
    }

    public void setValidarcarropericiado(int validarcarropericiado) {
        this.validarcarropericiado = validarcarropericiado;
    }

    public void PreencherCamposVistoria(JSONObject vist) {
        try {
            this.setId(vist.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDescricao(vist.getString("descricao"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
