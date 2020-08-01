package com.iris.ramilton.iris.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iris.ramilton.iris.AtualizarVitimaActivity;
import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.VitimaDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Cvli implements Serializable{

    private DatabaseHelper conexao;
    private SQLiteDatabase db;
    private int id;
    private String dsNaturezaFato;
    private String dtFato;
    private int ckbDataFatoIndefinido;
    private String hsFato;
    private int ckbHorarioFatoIndefinido;
    private int dsNobitos;
    private int dsNSobrevivente;
    private int rbEnderecoFatoDefinido;
    private int rbEnderecoFatoNIndefinido;
    private String dsLogradouroFato;
    private String dsRuaFato;
    private String dsNRuaFato;
    private String dsReferenciaLocalFato;
    private String dsBairroFato;
    private String dsDistVilPovoFato;
    private String dsDescrDistVilPovoFato;
    private String dsMunicipioFato;
    private String dsEstadoFato;
    private String dsZonaFato;
    private String dsDetalhamentoLocal;
    private String dsOutroDetalhamento;
    private int cbkTortura;
    private int cbkAfogamentoFato;
    private int cbkAsfixiaFato;
    private int cbkTropelamentoFato;
    private int cbkDisparoArmaFato;
    private int cbkEmpurraoAlturaFato;
    private int cbkEvenenadoFato;
    private int cbkEstanaduraFato;
    private int cbkEstrangulamentoFato;
    private int cbkEmpurraoSobVeiculoFato;
    private int cbkGolpeFacaoFato;
    private int cbkGolpeFacaFato;
    private int cbkPauladaFato;
    private int cbkOmissaoSocorroFato;
    private int cbkQueimaduraFato;
    private int cbkPedradaFato;
    private int cbkPerfuracoesFato;
    private int cbkIncendioFato;
    private int cbkSocosPontapesFato;
    private int cbkQueimarudasAcidoFato;
    private int cbkNaoIdentificadoFato;
    private int cbkOutrosNaoRelacionadoFato;
    private String dsMotivacaoFato;
    private String dtDataSilc;
    private String hsHorarioSilc;
    private String dsLogradouroSilc;
    private String dsRuaSilc;
    private String dsNRuaSilc;
    private String dsReferenciaLocalSilc;
    private String dsBairroSilc;
    private String dsDistVilPovoSilc;
    private String dsDescrDistVilPovoSilc;
    private String dsMunicipioSilc;
    private String dsEstadoSilc;
    private String dsResumoFato;
    private String dsNBO;
    private String dsNIP;
    private int cbkBuscaApreensao;
    private int cbkPrisaoTemporaria;
    private int cbkPrisaoPreventiva;
    private int cbkQuebraSigilo;
    private int cbkMedidasProtetivas;
    private int cbkSemCautelares;
    private String dsDestinacaoInvestigacao;
    private int EstatusCVLI;
    private int unidade_id;
    private int Controle;
    private String idUnico;
    private String dsTituloFato;
    private int servidor_id;
    private String dsNomeVitima;
    private String dsProvidencia;
    private int cbkEquipePreservacaoLocalDefinido;
    private int cbkEquipePreservacaoLocalSilcNDefinido;
    private int cbkEquipeLevantamentoSilcDefinido;
    private int cbkEquipeLevantamentoSilcNDefinido;
    private int cbkEquipePeritosSilcDefinido;
    private int cbkEquipePeritosSilcNDefinido;
    private int cbkObjetosArrecadadosSilcDefinido;
    private int cbkObjetosArrecadadosSilcNDefinido;
    private int cbkExpedidoGuiaPericial;
    private int cbkNExpedidoGuiaPericial;
    private int validarcvli;
    private String dsResFato;
    private int unidadece_id;
    private String responsavelCVLI;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDsNaturezaFato() {
        return dsNaturezaFato;
    }

    public void setDsNaturezaFato(String dsNaturezaFato) {
        this.dsNaturezaFato = dsNaturezaFato;
    }

    public String getDtFato() {

        return dtFato;

    }

    public void setDtFato(String dtFato) {
        this.dtFato = dtFato;
    }

    public int getCkbDataFatoIndefinido() {
        return ckbDataFatoIndefinido;
    }

    public void setCkbDataFatoIndefinido(int ckbDataFatoIndefinido) {
        this.ckbDataFatoIndefinido = ckbDataFatoIndefinido;
    }

    public String getHsFato() {
        return hsFato;
    }

    public void setHsFato(String hsFato) {
        this.hsFato = hsFato;
    }

    public int getCkbHorarioFatoIndefinido() {
        return ckbHorarioFatoIndefinido;
    }

    public void setCkbHorarioFatoIndefinido(int ckbHorarioFatoIndefinido) {
        this.ckbHorarioFatoIndefinido = ckbHorarioFatoIndefinido;
    }

    public int getDsNobitos() {
        return dsNobitos;
    }

    public void setDsNobitos(int dsNobitos) {
        this.dsNobitos = dsNobitos;
    }

    public int getDsNSobrevivente() {
        return dsNSobrevivente;
    }

    public void setDsNSobrevivente(int dsNSobrevivente) {
        this.dsNSobrevivente = dsNSobrevivente;
    }

    public String getDsLogradouroFato() {
        return dsLogradouroFato;
    }

    public void setDsLogradouroFato(String dsLogradouroFato) {
        this.dsLogradouroFato = dsLogradouroFato;
    }

    public String getDsRuaFato() {
        return dsRuaFato;
    }

    public void setDsRuaFato(String dsRuaFato) {
        this.dsRuaFato = dsRuaFato;
    }

    public String getDsNRuaFato() {
        return dsNRuaFato;
    }

    public void setDsNRuaFato(String dsNRuaFato) {
        this.dsNRuaFato = dsNRuaFato;
    }

    public String getDsReferenciaLocalFato() {
        return dsReferenciaLocalFato;
    }

    public void setDsReferenciaLocalFato(String dsReferenciaLocalFato) {
        this.dsReferenciaLocalFato = dsReferenciaLocalFato;
    }

    public String getDsBairroFato() {
        return dsBairroFato;
    }

    public void setDsBairroFato(String dsBairroFato) {
        this.dsBairroFato = dsBairroFato;
    }

    public String getDsDistVilPovoFato() {
        return dsDistVilPovoFato;
    }

    public void setDsDistVilPovoFato(String dsDistVilPovoFato) {
        this.dsDistVilPovoFato = dsDistVilPovoFato;
    }

    public String getDsDescrDistVilPovoFato() {
        return dsDescrDistVilPovoFato;
    }

    public void setDsDescrDistVilPovoFato(String dsDescrDistVilPovoFato) {
        this.dsDescrDistVilPovoFato = dsDescrDistVilPovoFato;
    }

    public String getDsMunicipioFato() {
        return dsMunicipioFato;
    }

    public void setDsMunicipioFato(String dsMunicipioFato) {
        this.dsMunicipioFato = dsMunicipioFato;
    }

    public String getDsEstadoFato() {
        return dsEstadoFato;
    }

    public void setDsEstadoFato(String dsEstadoFato) {
        this.dsEstadoFato = dsEstadoFato;
    }

    public String getDsZonaFato() {
        return dsZonaFato;
    }

    public void setDsZonaFato(String dsZonaFato) {
        this.dsZonaFato = dsZonaFato;
    }

    public String getDsDetalhamentoLocal() {
        return dsDetalhamentoLocal;
    }

    public void setDsDetalhamentoLocal(String dsDetalhamentoLocal) {
        this.dsDetalhamentoLocal = dsDetalhamentoLocal;
    }

    public String getDsOutroDetalhamento() {
        return dsOutroDetalhamento;
    }

    public void setDsOutroDetalhamento(String dsOutroDetalhamento) {
        this.dsOutroDetalhamento = dsOutroDetalhamento;
    }

    public int getCbkTortura() {
        return cbkTortura;
    }

    public void setCbkTortura(int cbkTortura) {
        this.cbkTortura = cbkTortura;
    }

    public int getCbkAfogamentoFato() {
        return cbkAfogamentoFato;
    }

    public void setCbkAfogamentoFato(int cbkAfogamentoFato) {
        this.cbkAfogamentoFato = cbkAfogamentoFato;
    }

    public int getCbkAsfixiaFato() {
        return cbkAsfixiaFato;
    }

    public void setCbkAsfixiaFato(int cbkAsfixiaFato) {
        this.cbkAsfixiaFato = cbkAsfixiaFato;
    }

    public int getCbkTropelamentoFato() {
        return cbkTropelamentoFato;
    }

    public void setCbkTropelamentoFato(int cbkTropelamentoFato) {
        this.cbkTropelamentoFato = cbkTropelamentoFato;
    }

    public int getCbkDisparoArmaFato() {
        return cbkDisparoArmaFato;
    }

    public void setCbkDisparoArmaFato(int cbkDisparoArmaFato) {
        this.cbkDisparoArmaFato = cbkDisparoArmaFato;
    }

    public int getCbkEmpurraoAlturaFato() {
        return cbkEmpurraoAlturaFato;
    }

    public void setCbkEmpurraoAlturaFato(int cbkEmpurraoAlturaFato) {
        this.cbkEmpurraoAlturaFato = cbkEmpurraoAlturaFato;
    }


    public int getCbkEvenenadoFato() {
        return cbkEvenenadoFato;
    }

    public void setCbkEvenenadoFato(int cbkEvenenadoFato) {
        this.cbkEvenenadoFato = cbkEvenenadoFato;
    }

    public int getCbkEstanaduraFato() {
        return cbkEstanaduraFato;
    }

    public void setCbkEstanaduraFato(int cbkEstanaduraFato) {
        this.cbkEstanaduraFato = cbkEstanaduraFato;
    }



    public int getCbkEstrangulamentoFato() {
        return cbkEstrangulamentoFato;
    }

    public void setCbkEstrangulamentoFato(int cbkEstrangulamentoFato) {
        this.cbkEstrangulamentoFato = cbkEstrangulamentoFato;
    }

    public int getCbkEmpurraoSobVeiculoFato() {
        return cbkEmpurraoSobVeiculoFato;
    }

    public void setCbkEmpurraoSobVeiculoFato(int cbkEmpurraoSobVeiculoFato) {
        this.cbkEmpurraoSobVeiculoFato = cbkEmpurraoSobVeiculoFato;
    }

    public int getCbkGolpeFacaoFato() {
        return cbkGolpeFacaoFato;
    }

    public void setCbkGolpeFacaoFato(int cbkGolpeFacaoFato) {
        this.cbkGolpeFacaoFato = cbkGolpeFacaoFato;
    }

    public int getCbkGolpeFacaFato() {
        return cbkGolpeFacaFato;
    }

    public void setCbkGolpeFacaFato(int cbkGolpeFacaFato) {
        this.cbkGolpeFacaFato = cbkGolpeFacaFato;
    }

    public int getCbkPauladaFato() {
        return cbkPauladaFato;
    }

    public void setCbkPauladaFato(int cbkPauladaFato) {
        this.cbkPauladaFato = cbkPauladaFato;
    }

    public int getCbkOmissaoSocorroFato() {
        return cbkOmissaoSocorroFato;
    }

    public void setCbkOmissaoSocorroFato(int cbkOmissaoSocorroFato) {
        this.cbkOmissaoSocorroFato = cbkOmissaoSocorroFato;
    }

    public int getCbkQueimaduraFato() {
        return cbkQueimaduraFato;
    }

    public void setCbkQueimaduraFato(int cbkQueimaduraFato) {
        this.cbkQueimaduraFato = cbkQueimaduraFato;
    }

    public int getCbkPedradaFato() {
        return cbkPedradaFato;
    }

    public void setCbkPedradaFato(int cbkPedradaFato) {
        this.cbkPedradaFato = cbkPedradaFato;
    }

    public int getCbkPerfuracoesFato() {
        return cbkPerfuracoesFato;
    }

    public void setCbkPerfuracoesFato(int cbkPerfuracoesFato) {
        this.cbkPerfuracoesFato = cbkPerfuracoesFato;
    }

    public int getCbkIncendioFato() {
        return cbkIncendioFato;
    }

    public void setCbkIncendioFato(int cbkIncendioFato) {
        this.cbkIncendioFato = cbkIncendioFato;
    }

    public int getCbkSocosPontapesFato() {
        return cbkSocosPontapesFato;
    }

    public void setCbkSocosPontapesFato(int cbkSocosPontapesFato) {
        this.cbkSocosPontapesFato = cbkSocosPontapesFato;
    }

    public int getCbkQueimarudasAcidoFato() {
        return cbkQueimarudasAcidoFato;
    }

    public void setCbkQueimarudasAcidoFato(int cbkQueimarudasAcidoFato) {
        this.cbkQueimarudasAcidoFato = cbkQueimarudasAcidoFato;
    }

    public int getCbkNaoIdentificadoFato() {
        return cbkNaoIdentificadoFato;
    }

    public void setCbkNaoIdentificadoFato(int cbkNaoIdentificadoFato) {
        this.cbkNaoIdentificadoFato = cbkNaoIdentificadoFato;
    }

    public int getCbkOutrosNaoRelacionadoFato() {
        return cbkOutrosNaoRelacionadoFato;
    }

    public void setCbkOutrosNaoRelacionadoFato(int cbkOutrosNaoRelacionadoFato) {
        this.cbkOutrosNaoRelacionadoFato = cbkOutrosNaoRelacionadoFato;
    }

    public String getDsMotivacaoFato() {
        return dsMotivacaoFato;
    }

    public void setDsMotivacaoFato(String dsMotivacaoFato) {
        this.dsMotivacaoFato = dsMotivacaoFato;
    }

    public String getDtDataSilc() {

        return dtDataSilc;

    }

    public void setDtDataSilc(String dtDataSilc) {
        this.dtDataSilc = dtDataSilc;
    }

    public String getHsHorarioSilc() {
        return hsHorarioSilc;
    }

    public void setHsHorarioSilc(String hsHorarioSilc) {
        this.hsHorarioSilc = hsHorarioSilc;
    }

    public String getDsLogradouroSilc() {
        return dsLogradouroSilc;
    }

    public void setDsLogradouroSilc(String dsLogradouroSilc) {
        this.dsLogradouroSilc = dsLogradouroSilc;
    }

    public String getDsRuaSilc() {
        return dsRuaSilc;
    }

    public void setDsRuaSilc(String dsRuaSilc) {
        this.dsRuaSilc = dsRuaSilc;
    }

    public String getDsNRuaSilc() {
        return dsNRuaSilc;
    }

    public void setDsNRuaSilc(String dsNRuaSilc) {
        this.dsNRuaSilc = dsNRuaSilc;
    }

    public String getDsReferenciaLocalSilc() {
        return dsReferenciaLocalSilc;
    }

    public void setDsReferenciaLocalSilc(String dsReferenciaLocalSilc) {
        this.dsReferenciaLocalSilc = dsReferenciaLocalSilc;
    }

    public String getDsBairroSilc() {
        return dsBairroSilc;
    }

    public void setDsBairroSilc(String dsBairroSilc) {
        this.dsBairroSilc = dsBairroSilc;
    }

    public String getDsDistVilPovoSilc() {
        return dsDistVilPovoSilc;
    }

    public void setDsDistVilPovoSilc(String dsDistVilPovoSilc) {
        this.dsDistVilPovoSilc = dsDistVilPovoSilc;
    }

    public String getDsDescrDistVilPovoSilc() {
        return dsDescrDistVilPovoSilc;
    }

    public void setDsDescrDistVilPovoSilc(String dsDescrDistVilPovoSilc) {
        this.dsDescrDistVilPovoSilc = dsDescrDistVilPovoSilc;
    }

    public String getDsMunicipioSilc() {
        return dsMunicipioSilc;
    }

    public void setDsMunicipioSilc(String dsMunicipioSilc) {
        this.dsMunicipioSilc = dsMunicipioSilc;
    }

    public String getDsEstadoSilc() {
        return dsEstadoSilc;
    }

    public void setDsEstadoSilc(String dsEstadoSilc) {
        this.dsEstadoSilc = dsEstadoSilc;
    }

    public String getDsResumoFato() {
        return dsResumoFato;
    }

    public void setDsResumoFato(String dsResumoFato) {
        this.dsResumoFato = dsResumoFato;
    }

    public String getDsNBO() {
        return dsNBO;
    }

    public void setDsNBO(String dsNBO) {
        this.dsNBO = dsNBO;
    }

    public String getDsNIP() {
        return dsNIP;
    }

    public void setDsNIP(String dsNIP) {
        this.dsNIP = dsNIP;
    }

    public int getCbkBuscaApreensao() {
        return cbkBuscaApreensao;
    }

    public void setCbkBuscaApreensao(int cbkBuscaApreensao) {
        this.cbkBuscaApreensao = cbkBuscaApreensao;
    }

    public int getCbkPrisaoTemporaria() {
        return cbkPrisaoTemporaria;
    }

    public void setCbkPrisaoTemporaria(int cbkPrisaoTemporaria) {
        this.cbkPrisaoTemporaria = cbkPrisaoTemporaria;
    }

    public int getCbkPrisaoPreventiva() {
        return cbkPrisaoPreventiva;
    }

    public void setCbkPrisaoPreventiva(int cbkPrisaoPreventiva) {
        this.cbkPrisaoPreventiva = cbkPrisaoPreventiva;
    }

    public int getCbkQuebraSigilo() {
        return cbkQuebraSigilo;
    }

    public void setCbkQuebraSigilo(int cbkQuebraSigilo) {
        this.cbkQuebraSigilo = cbkQuebraSigilo;
    }

    public int getCbkSemCautelares() {
        return cbkSemCautelares;
    }

    public void setCbkSemCautelares(int cbkSemCautelares) {
        this.cbkSemCautelares = cbkSemCautelares;
    }

    public String getDsDestinacaoInvestigacao() {
        return dsDestinacaoInvestigacao;
    }

    public void setDsDestinacaoInvestigacao(String dsDestinacaoInvestigacao) {
        this.dsDestinacaoInvestigacao = dsDestinacaoInvestigacao;
    }

    public int getEstatusCVLI() {
        return EstatusCVLI;
    }

    public void setEstatusCVLI(int estatusCVLI) {
        EstatusCVLI = estatusCVLI;
    }

    @Override
    public String toString(){
        return ""+id;
    }

    public int getRbEnderecoFatoDefinido() {
        return rbEnderecoFatoDefinido;
    }

    public void setRbEnderecoFatoDefinido(int rbEnderecoFatoDefinido) {
        this.rbEnderecoFatoDefinido = rbEnderecoFatoDefinido;
    }

    public int getRbEnderecoFatoNIndefinido() {
        return rbEnderecoFatoNIndefinido;
    }

    public void setRbEnderecoFatoNIndefinido(int rbEnderecoFatoNIndefinido) {
        this.rbEnderecoFatoNIndefinido = rbEnderecoFatoNIndefinido;
    }

    public int getCbkMedidasProtetivas() {
        return cbkMedidasProtetivas;
    }

    public void setCbkMedidasProtetivas(int cbkMedidasProtetivas) {
        this.cbkMedidasProtetivas = cbkMedidasProtetivas;
    }

    public int getUnidade_id() {
        return unidade_id;
    }

    public void setUnidade_id(int unidade_id) {
        this.unidade_id = unidade_id;
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

    public String getDsTituloFato() {
        return dsTituloFato;
    }

    public void setDsTituloFato(String dsTituloFato) {
        this.dsTituloFato = dsTituloFato;
    }

    public int getServidor_id() {
        return servidor_id;
    }

    public void setServidor_id(int servidor_id) {
        this.servidor_id = servidor_id;
    }

    public String getDsNomeVitima() {
        return dsNomeVitima;
    }

    public void setDsNomeVitima(String dsNomeVitima) {
        this.dsNomeVitima = dsNomeVitima;
    }

    public String getDsProvidencia() {
        return dsProvidencia;
    }

    public void setDsProvidencia(String dsProvidencia) {
        this.dsProvidencia = dsProvidencia;
    }

    public int getCbkEquipePreservacaoLocalDefinido() {
        return cbkEquipePreservacaoLocalDefinido;
    }

    public void setCbkEquipePreservacaoLocalDefinido(int cbkEquipePreservacaoLocalDefinido) {
        this.cbkEquipePreservacaoLocalDefinido = cbkEquipePreservacaoLocalDefinido;
    }

    public int getCbkEquipePreservacaoLocalSilcNDefinido() {
        return cbkEquipePreservacaoLocalSilcNDefinido;
    }

    public void setCbkEquipePreservacaoLocalSilcNDefinido(int cbkEquipePreservacaoLocalSilcNDefinido) {
        this.cbkEquipePreservacaoLocalSilcNDefinido = cbkEquipePreservacaoLocalSilcNDefinido;
    }

    public int getCbkEquipeLevantamentoSilcDefinido() {
        return cbkEquipeLevantamentoSilcDefinido;
    }

    public void setCbkEquipeLevantamentoSilcDefinido(int cbkEquipeLevantamentoSilcDefinido) {
        this.cbkEquipeLevantamentoSilcDefinido = cbkEquipeLevantamentoSilcDefinido;
    }

    public int getCbkEquipeLevantamentoSilcNDefinido() {
        return cbkEquipeLevantamentoSilcNDefinido;
    }

    public void setCbkEquipeLevantamentoSilcNDefinido(int cbkEquipeLevantamentoSilcNDefinido) {
        this.cbkEquipeLevantamentoSilcNDefinido = cbkEquipeLevantamentoSilcNDefinido;
    }

    public int getCbkEquipePeritosSilcDefinido() {
        return cbkEquipePeritosSilcDefinido;
    }

    public void setCbkEquipePeritosSilcDefinido(int cbkEquipePeritosSilcDefinido) {
        this.cbkEquipePeritosSilcDefinido = cbkEquipePeritosSilcDefinido;
    }

    public int getCbkEquipePeritosSilcNDefinido() {
        return cbkEquipePeritosSilcNDefinido;
    }

    public void setCbkEquipePeritosSilcNDefinido(int cbkEquipePeritosSilcNDefinido) {
        this.cbkEquipePeritosSilcNDefinido = cbkEquipePeritosSilcNDefinido;
    }

    public int getCbkObjetosArrecadadosSilcDefinido() {
        return cbkObjetosArrecadadosSilcDefinido;
    }

    public void setCbkObjetosArrecadadosSilcDefinido(int cbkObjetosArrecadadosSilcDefinido) {
        this.cbkObjetosArrecadadosSilcDefinido = cbkObjetosArrecadadosSilcDefinido;
    }

    public int getCbkObjetosArrecadadosSilcNDefinido() {
        return cbkObjetosArrecadadosSilcNDefinido;
    }

    public void setCbkObjetosArrecadadosSilcNDefinido(int cbkObjetosArrecadadosSilcNDefinido) {
        this.cbkObjetosArrecadadosSilcNDefinido = cbkObjetosArrecadadosSilcNDefinido;
    }

    public int getCbkExpedidoGuiaPericial() {
        return cbkExpedidoGuiaPericial;
    }

    public void setCbkExpedidoGuiaPericial(int cbkExpedidoGuiaPericial) {
        this.cbkExpedidoGuiaPericial = cbkExpedidoGuiaPericial;
    }

    public int getCbkNExpedidoGuiaPericial() {
        return cbkNExpedidoGuiaPericial;
    }

    public void setCbkNExpedidoGuiaPericial(int cbkNExpedidoGuiaPericial) {
        this.cbkNExpedidoGuiaPericial = cbkNExpedidoGuiaPericial;
    }

    public int getValidarcvli() {
        return validarcvli;
    }

    public void setValidarcvli(int validarcvli) {
        this.validarcvli = validarcvli;
    }


    public String retornarNomesVitimas(Context context){
       CvliDao cvli = new CvliDao(context);
        return cvli.retornarNomesVitimas(getId());

    }

    public String getDsResFato() {
        return dsResFato;
    }

    public void setDsResFato(String dsResFato) {
        this.dsResFato = dsResFato;
    }

    public int getUnidadece_id() {
        return unidadece_id;
    }

    public void setUnidadece_id(int unidadece_id) {
        this.unidadece_id = unidadece_id;
    }

    public String getResponsavelCVLI() {
        return responsavelCVLI;
    }

    public void setResponsavelCVLI(String responsavelCVLI) {
        this.responsavelCVLI = responsavelCVLI;
    }

    public void PreencherCamposCVLI(JSONObject dado){

        try {
            this.setDsNaturezaFato(dado.getString("dsNaturezaFato"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String data = dado.getString("dtFato");
            //passar do formato texto para tipo data
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = inputFormat.parse(data);

            //depois transformar para o padrão brasileiro
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
            final String formattedDate = sdf.format(inputDate);
            this.setDtFato(formattedDate);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
             this.ckbDataFatoIndefinido = dado.getInt("ckbDataFatoIndefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("hsFato").equals("null")){
                this.setHsFato(" ");
            }else{
                this.setHsFato(dado.getString("hsFato"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
           this.ckbHorarioFatoIndefinido = dado.getInt("ckbHorarioFatoIndefinido");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.rbEnderecoFatoDefinido = dado.getInt("rbEnderecoFatoDefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.rbEnderecoFatoNIndefinido = dado.getInt("rbEnderecoFatoNIndefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsLogradouroFato(dado.getString("dsLogradouroFato"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("dsRuaFato").equals("null")){
                this.setDsRuaFato(" ");
            }else{
                this.setDsRuaFato(dado.getString("dsRuaFato"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("dsNRuaFato").equals("null")){
                this.setDsNRuaFato(" ");
            }else{
                this.setDsNRuaFato(dado.getString("dsNRuaFato"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("dsReferenciaLocalFato").equals("null")){
                this.setDsReferenciaLocalFato(" ");
            }else{
                this.setDsReferenciaLocalFato(dado.getString("dsReferenciaLocalFato"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("dsBairroFato").equals("null")){
                this.setDsBairroFato(" ");
            }else{
                this.setDsBairroFato(dado.getString("dsBairroFato"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsDistVilPovoFato(dado.getString("dsDistVilPovoFato"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsDescrDistVilPovoFato(dado.getString("dsDescrDistVilPovoFato"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsMunicipioFato(dado.getString("dsMunicipioFato"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsEstadoFato(dado.getString("dsEstadoFato"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsZonaFato(dado.getString("dsZonaFato"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsDetalhamentoLocal(dado.getString("dsDetalhamentoLocal"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsOutroDetalhamento(dado.getString("dsOutroDetalhamento"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.cbkTortura = dado.getInt("cbkTortura");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            this.cbkAfogamentoFato = dado.getInt("cbkAfogamentoFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.cbkAsfixiaFato = dado.getInt("cbkAsfixiaFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.cbkTropelamentoFato = dado.getInt("cbkTropelamentoFato");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.cbkDisparoArmaFato = dado.getInt("cbkDisparoArmaFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.cbkEmpurraoAlturaFato = dado.getInt("cbkEmpurraoAlturaFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkEvenenadoFato = dado.getInt("cbkEvenenadoFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkEstanaduraFato = dado.getInt("cbkEstanaduraFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkEstrangulamentoFato = dado.getInt("cbkEstrangulamentoFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkEmpurraoSobVeiculoFato = dado.getInt("cbkEmpurraoSobVeiculoFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkGolpeFacaoFato = dado.getInt("cbkGolpeFacaoFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkGolpeFacaFato = dado.getInt("cbkGolpeFacaFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkPauladaFato = dado.getInt("cbkPauladaFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.cbkOmissaoSocorroFato = dado.getInt("cbkOmissaoSocorroFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkQueimaduraFato = dado.getInt("cbkQueimaduraFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkPedradaFato = dado.getInt("cbkPedradaFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkPerfuracoesFato = dado.getInt("cbkPerfuracoesFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
                this.cbkIncendioFato = dado.getInt("cbkIncendioFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.cbkSocosPontapesFato = dado.getInt("cbkSocosPontapesFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkQueimarudasAcidoFato = dado.getInt("cbkQueimarudasAcidoFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkNaoIdentificadoFato = dado.getInt("cbkNaoIdentificadoFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkOutrosNaoRelacionadoFato = dado.getInt("cbkOutrosNaoRelacionadoFato");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsMotivacaoFato(dado.getString("dsMotivacaoFato"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setResponsavelCVLI(dado.getString("responsavelCVLI"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String data = dado.getString("dtDataSilc");
            //passar do formato texto para tipo data
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = inputFormat.parse(data);

            //depois transformar para o padrão brasileiro
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
            final String formattedDate = sdf.format(inputDate);
            this.setDtDataSilc(formattedDate);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("hsHorarioSilc").equals("null")){
                this.setHsHorarioSilc(" ");
            }else{
                this.setHsHorarioSilc(dado.getString("hsHorarioSilc"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsLogradouroSilc(dado.getString("dsLogradouroSilc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("dsRuaSilc").equals("null")){
                this.setDsRuaSilc(" ");
            }else{
                this.setDsRuaSilc(dado.getString("dsRuaSilc"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("dsNRuaSilc").equals("null")){
                this.setDsNRuaSilc(" ");
            }else{
                this.setDsNRuaSilc(dado.getString("dsNRuaSilc"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("dsReferenciaLocalSilc").equals("null")){
                this.setDsReferenciaLocalSilc(" ");
            }else{
                this.setDsReferenciaLocalSilc(dado.getString("dsReferenciaLocalSilc"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("dsBairroSilc").equals("null")){
                this.setDsBairroSilc(" ");
            }else{
                this.setDsBairroSilc(dado.getString("dsBairroSilc"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsDistVilPovoSilc(dado.getString("dsDistVilPovoSilc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsDescrDistVilPovoSilc(dado.getString("dsDescrDistVilPovoSilc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsMunicipioSilc(dado.getString("dsMunicipioSilc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsEstadoSilc(dado.getString("dsEstadoSilc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsResumoFato(dado.getString("dsResumoFato"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNBO(dado.getString("dsNBO"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkExpedidoGuiaPericial = dado.getInt("rbExpedidoGuiaPericial");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkNExpedidoGuiaPericial = dado.getInt("rbNExpedidoGuiaPericial");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsNIP(dado.getString("dsNIP"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkBuscaApreensao = dado.getInt("rbNExpedidoGuiaPericial");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkPrisaoTemporaria = dado.getInt("cbkPrisaoTemporaria");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.cbkPrisaoPreventiva = dado.getInt("cbkPrisaoPreventiva");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.cbkQuebraSigilo = dado.getInt("cbkQuebraSigilo");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkMedidasProtetivas = dado.getInt("cbMedidaProtetivadeUrgencia");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.cbkSemCautelares = dado.getInt("cbSemCautelares");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsDestinacaoInvestigacao(dado.getString("dsDestinacaoInvestigacao"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             this.EstatusCVLI = dado.getInt("EstatusCVLI");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setUnidade_id(dado.getInt("unidade_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setControle(dado.getInt("Controle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setIdUnico(dado.getString("idUnico"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsTituloFato(dado.getString("dsTituloResumo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setServidor_id(dado.getInt("servidor_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsProvidencia(dado.getString("dsProvidencia"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             cbkEquipePreservacaoLocalDefinido = dado.getInt("rbEquipePreservacaoLocalDefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             cbkEquipePreservacaoLocalSilcNDefinido = dado.getInt("rbEquipePreservacaoLocalSilcNDefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            cbkEquipeLevantamentoSilcDefinido = dado.getInt("rbEquipeLevantamentoSilcDefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             cbkEquipeLevantamentoSilcNDefinido = dado.getInt("rbEquipeLevantamentoSilcNDefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
             cbkEquipePeritosSilcDefinido = dado.getInt("rbEquipePeritosSilcDefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            cbkEquipePeritosSilcNDefinido = dado.getInt("rbEquipePeritosSilcNDefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("rbObjetosArrecadadosSilcDefinido") == "null"){
                cbkObjetosArrecadadosSilcDefinido = 0;
            }else{
                cbkObjetosArrecadadosSilcDefinido = dado.getInt("rbObjetosArrecadadosSilcDefinido");
            }
             //cbkObjetosArrecadadosSilcDefinido = dado.getInt("rbObjetosArrecadadosSilcDefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(dado.getString("rbObjetosArrecadadosSilcNDefinido") == "null"){
                cbkObjetosArrecadadosSilcNDefinido = 0;
            }else{
                cbkObjetosArrecadadosSilcNDefinido = dado.getInt("rbObjetosArrecadadosSilcNDefinido");
            }
             //cbkObjetosArrecadadosSilcNDefinido = dado.getInt("rbObjetosArrecadadosSilcNDefinido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setValidarcvli(dado.getInt("validarcvli"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setDsResFato(dado.getString("dsResFato"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.setUnidadece_id(dado.getInt("unidadece_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
