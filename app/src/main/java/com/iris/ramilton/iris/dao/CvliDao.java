package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.iris.ramilton.iris.CvliActivity;
import com.iris.ramilton.iris.R;
import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Vitima;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class CvliDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public CvliDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarCVLI(Cvli cvli){

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put("EstatusCVLI", cvli.getEstatusCVLI());
            cv.put("Controle", cvli.getControle());
            cv.put("idUnico", cvli.getIdUnico());
            cv.put("unidade_id", cvli.getUnidade_id());
            cv.put("servidor_id", cvli.getServidor_id());

            return db.insert("cvlis", null, cv);
    }

    public int retornarCodigoCvli(int status) {

        db = conexao.getReadableDatabase();
        Cursor c = db.query("cvlis", new String[]{"id"}, "EstatusCVLI='" + status + "'", null, null, null, null);
        c.moveToLast();
        int valorcodigo = c.getInt(0);
        return valorcodigo;
    }

    public long excluirCVLI(int cod){
        db = conexao.getWritableDatabase();

        return db.delete("cvlis","id='"+cod+"'",null);
    }

    public int retornarCodigoCvliSemParametro(){

            db = conexao.getReadableDatabase();

            Cursor c = db.query("cvlis", new String[]{"id"}, null, null, null, null, null);
            c.moveToLast();
            int codigosemparametro = c.getInt(0);
            return codigosemparametro;
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvlis", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public boolean verificarID(String id){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvlimunicipios", new String[]{"id"}, "id='"+id+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public int retornarCodId(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvlis", new String[]{"id"}, "idUnico= '"+idunico+"'", null, null, null, null);
        c.moveToLast();
        int codigocontrole = c.getInt(0);
        return codigocontrole;
    }

    public int retornarCodigoControle(int id){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvlis", new String[]{"Controle"}, "id= '"+id+"'", null, null, null, null);
        c.moveToLast();
        int codigocontrole = c.getInt(0);
        return codigocontrole;
    }

    public int retornarCodigoStatusCvli(int id){

            db = conexao.getReadableDatabase();

            Cursor c = db.query("cvlis", new String[]{"EstatusCVLI"}, "id='" + id + "'", null, null, null, null);
            c.moveToFirst();
            int valorcvlistatus = c.getInt(0);
            return valorcvlistatus;

    }

    public void AtualizarControleCVLI(Cvli cvli, int id, int controle){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cvli.setId(id);
        cvli.setControle(controle);

        cv.put("Controle", cvli.getControle());

        db.update("cvlis", cv, "id = ?", new String[]{String.valueOf(cvli.getId())});

    }

    public long validarCVLI(Cvli cvli, int codigo){
        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

            cv.put("validarcvli", cvli.getValidarcvli());

            return db.update("cvlis", cv, "id = '"+codigo+"'", null);
    }

    public long cadastrarCVLISilc(Cvli cvli1, int codigo){
        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigocvlisempsramentro = retornarCodigoCvliSemParametro();

        int codigoStatus = retornarCodigoStatusCvli(codigocvlisempsramentro);

        if (codigoStatus == 0) {

            cvli1.setEstatusCVLI(1);
            cvli1.setControle(2);
            cvli1.setValidarcvli(0);

            cvli1.setId(codigo);

            cv.put("responsavelCVLI", cvli1.getResponsavelCVLI());
            cv.put("dtDataSilc", cvli1.getDtDataSilc());
            cv.put("hsHorarioSilc", cvli1.getHsHorarioSilc());
            cv.put("dsLogradouroSilc", cvli1.getDsLogradouroSilc());
            cv.put("dsRuaSilc", cvli1.getDsRuaSilc());
            cv.put("dsNRuaSilc", cvli1.getDsNRuaSilc());
            cv.put("dsReferenciaLocalSilc", cvli1.getDsReferenciaLocalSilc());
            cv.put("dsBairroSilc", cvli1.getDsBairroSilc());
            cv.put("dsDistVilPovoSilc", cvli1.getDsDistVilPovoSilc());
            cv.put("dsDescrDistVilPovoSilc", cvli1.getDsDescrDistVilPovoSilc());
            cv.put("dsMunicipioSilc", cvli1.getDsMunicipioSilc());
            cv.put("dsEstadoSilc", cvli1.getDsEstadoSilc());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("rbEquipePreservacaoLocalDefinido", cvli1.getCbkEquipePreservacaoLocalDefinido());
            cv.put("rbEquipePreservacaoLocalSilcNDefinido", cvli1.getCbkEquipePreservacaoLocalSilcNDefinido());
            cv.put("rbEquipeLevantamentoSilcDefinido", cvli1.getCbkEquipeLevantamentoSilcDefinido());
            cv.put("rbEquipeLevantamentoSilcNDefinido", cvli1.getCbkEquipeLevantamentoSilcNDefinido());
            cv.put("rbEquipePeritosSilcDefinido", cvli1.getCbkEquipePeritosSilcDefinido());
            cv.put("rbEquipePeritosSilcNDefinido", cvli1.getCbkEquipePeritosSilcNDefinido());
            cv.put("rbObjetosArrecadadosSilcDefinido", cvli1.getCbkObjetosArrecadadosSilcDefinido());
            cv.put("rbObjetosArrecadadosSilcNDefinido", cvli1.getCbkObjetosArrecadadosSilcNDefinido());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = ?", new String[]{String.valueOf(cvli1.getId())});
        } else {

            cvli1.setId(codigocvlisempsramentro);
            cvli1.setEstatusCVLI(1);
            cvli1.setControle(2);
            cvli1.setValidarcvli(0);

            cv.put("responsavelCVLI", cvli1.getResponsavelCVLI());
            cv.put("dtDataSilc", cvli1.getDtDataSilc());
            cv.put("hsHorarioSilc", cvli1.getHsHorarioSilc());
            cv.put("dsLogradouroSilc", cvli1.getDsLogradouroSilc());
            cv.put("dsRuaSilc", cvli1.getDsRuaSilc());
            cv.put("dsNRuaSilc", cvli1.getDsNRuaSilc());
            cv.put("dsReferenciaLocalSilc", cvli1.getDsReferenciaLocalSilc());
            cv.put("dsBairroSilc", cvli1.getDsBairroSilc());
            cv.put("dsDistVilPovoSilc", cvli1.getDsDistVilPovoSilc());
            cv.put("dsDescrDistVilPovoSilc", cvli1.getDsDescrDistVilPovoSilc());
            cv.put("dsMunicipioSilc", cvli1.getDsMunicipioSilc());
            cv.put("dsEstadoSilc", cvli1.getDsEstadoSilc());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("rbEquipePreservacaoLocalDefinido", cvli1.getCbkEquipePreservacaoLocalDefinido());
            cv.put("rbEquipePreservacaoLocalSilcNDefinido", cvli1.getCbkEquipePreservacaoLocalSilcNDefinido());
            cv.put("rbEquipeLevantamentoSilcDefinido", cvli1.getCbkEquipeLevantamentoSilcDefinido());
            cv.put("rbEquipeLevantamentoSilcNDefinido", cvli1.getCbkEquipeLevantamentoSilcNDefinido());
            cv.put("rbEquipePeritosSilcDefinido", cvli1.getCbkEquipePeritosSilcDefinido());
            cv.put("rbEquipePeritosSilcNDefinido", cvli1.getCbkEquipePeritosSilcNDefinido());
            cv.put("rbObjetosArrecadadosSilcDefinido", cvli1.getCbkObjetosArrecadadosSilcDefinido());
            cv.put("rbObjetosArrecadadosSilcNDefinido", cvli1.getCbkObjetosArrecadadosSilcNDefinido());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = ?", new String[]{String.valueOf(cvli1.getId())});
        }
    }

    public long CadastrarCVLIProvidencias(Cvli cvli1, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigocvlisempsramentro = retornarCodigoCvliSemParametro();

        int codigoStatus = retornarCodigoStatusCvli(codigocvlisempsramentro);

        if (codigoStatus == 0) {

            cvli1.setEstatusCVLI(1);
            cvli1.setControle(2);
            cvli1.setValidarcvli(0);

            cvli1.setId(codigo);

            cv.put("dsNBO", cvli1.getDsNBO());
            cv.put("dsNIP", cvli1.getDsNIP());
            cv.put("cbkBuscaApreensao", cvli1.getCbkBuscaApreensao());
            cv.put("cbkPrisaoTemporaria", cvli1.getCbkPrisaoTemporaria());
            cv.put("cbkPrisaoPreventiva", cvli1.getCbkPrisaoPreventiva());
            cv.put("cbkQuebraSigilo", cvli1.getCbkQuebraSigilo());
            cv.put("cbMedidaProtetivadeUrgencia", cvli1.getCbkMedidasProtetivas());
            cv.put("cbSemCautelares", cvli1.getCbkSemCautelares());
            cv.put("dsDestinacaoInvestigacao", cvli1.getDsDestinacaoInvestigacao());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("dsProvidencia", cvli1.getDsProvidencia());
            cv.put("rbExpedidoGuiaPericial", cvli1.getCbkExpedidoGuiaPericial());
            cv.put("rbNExpedidoGuiaPericial", cvli1.getCbkNExpedidoGuiaPericial());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = ?", new String[]{String.valueOf(cvli1.getId())});
        } else {

            cvli1.setId(codigocvlisempsramentro);
            cvli1.setEstatusCVLI(1);
            cvli1.setControle(2);
            cvli1.setValidarcvli(0);

            cv.put("dsNBO", cvli1.getDsNBO());
            cv.put("dsNIP", cvli1.getDsNIP());
            cv.put("cbkBuscaApreensao", cvli1.getCbkBuscaApreensao());
            cv.put("cbkPrisaoTemporaria", cvli1.getCbkPrisaoTemporaria());
            cv.put("cbkPrisaoPreventiva", cvli1.getCbkPrisaoPreventiva());
            cv.put("cbkQuebraSigilo", cvli1.getCbkQuebraSigilo());
            cv.put("cbMedidaProtetivadeUrgencia", cvli1.getCbkMedidasProtetivas());
            cv.put("cbSemCautelares", cvli1.getCbkSemCautelares());
            cv.put("dsDestinacaoInvestigacao", cvli1.getDsDestinacaoInvestigacao());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("dsProvidencia", cvli1.getDsProvidencia());
            cv.put("rbExpedidoGuiaPericial", cvli1.getCbkExpedidoGuiaPericial());
            cv.put("rbNExpedidoGuiaPericial", cvli1.getCbkNExpedidoGuiaPericial());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = ?", new String[]{String.valueOf(cvli1.getId())});
        }
    }

    public long CadastrarCVLIResumoFatos(Cvli cvli1, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigocvlisempsramentro = retornarCodigoCvliSemParametro();

        int codigoStatus = retornarCodigoStatusCvli(codigocvlisempsramentro);

        if (codigoStatus == 0) {

            cvli1.setEstatusCVLI(1);
            cvli1.setControle(2);
            cvli1.setValidarcvli(0);

            cvli1.setId(codigo);

            cv.put("dsResumoFato", cvli1.getDsResumoFato());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("dsTituloResumo", cvli1.getDsTituloFato());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = ?", new String[]{String.valueOf(cvli1.getId())});
        } else {

            cvli1.setId(codigocvlisempsramentro);
            cvli1.setEstatusCVLI(1);
            cvli1.setControle(2);
            cvli1.setValidarcvli(0);

            cv.put("dsResumoFato", cvli1.getDsResumoFato());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("dsTituloResumo", cvli1.getDsTituloFato());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = ?", new String[]{String.valueOf(cvli1.getId())});
        }
    }

    public long CadastrarCVLIFato(Cvli cvli1, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigocvlisempsramentro = retornarCodigoCvliSemParametro();

        int codigoStatus = retornarCodigoStatusCvli(codigocvlisempsramentro);

        if (codigoStatus == 0) {

            cvli1.setEstatusCVLI(1);
            cvli1.setControle(2);
            cvli1.setValidarcvli(0);

            cvli1.setId(codigo);
            cv.put("dsNaturezaFato", cvli1.getDsNaturezaFato());
            cv.put("dtFato", cvli1.getDtFato());
            cv.put("ckbDataFatoIndefinido", cvli1.getCkbDataFatoIndefinido());
            cv.put("hsFato", cvli1.getHsFato());
            cv.put("ckbHorarioFatoIndefinido", cvli1.getCkbHorarioFatoIndefinido());
            cv.put("rbEnderecoFatoDefinido", cvli1.getRbEnderecoFatoDefinido());
            cv.put("rbEnderecoFatoNIndefinido", cvli1.getRbEnderecoFatoNIndefinido());
            cv.put("dsLogradouroFato", cvli1.getDsLogradouroFato());
            cv.put("dsRuaFato", cvli1.getDsRuaFato());
            cv.put("dsNRuaFato", cvli1.getDsNRuaFato());
            cv.put("dsReferenciaLocalFato", cvli1.getDsReferenciaLocalFato());
            cv.put("dsBairroFato", cvli1.getDsBairroFato());
            cv.put("dsDistVilPovoFato", cvli1.getDsDistVilPovoFato());
            cv.put("dsDescrDistVilPovoFato", cvli1.getDsDescrDistVilPovoFato());
            cv.put("dsMunicipioFato", cvli1.getDsMunicipioFato());
            cv.put("dsEstadoFato", cvli1.getDsEstadoFato());
            cv.put("dsZonaFato", cvli1.getDsZonaFato());
            cv.put("dsDetalhamentoLocal", cvli1.getDsDetalhamentoLocal());
            cv.put("dsOutroDetalhamento", cvli1.getDsOutroDetalhamento());
            cv.put("cbkTortura", cvli1.getCbkTortura());
            cv.put("cbkAfogamentoFato", cvli1.getCbkAfogamentoFato());
            cv.put("cbkAsfixiaFato", cvli1.getCbkAsfixiaFato());
            cv.put("cbkTropelamentoFato", cvli1.getCbkTropelamentoFato());
            cv.put("cbkDisparoArmaFato", cvli1.getCbkDisparoArmaFato());
            cv.put("cbkEmpurraoAlturaFato", cvli1.getCbkEmpurraoAlturaFato());
            cv.put("cbkEvenenadoFato", cvli1.getCbkEvenenadoFato());
            cv.put("cbkEstanaduraFato", cvli1.getCbkEstanaduraFato());
            cv.put("cbkEstrangulamentoFato", cvli1.getCbkEstrangulamentoFato());
            cv.put("cbkEmpurraoSobVeiculoFato", cvli1.getCbkEmpurraoSobVeiculoFato());
            cv.put("cbkGolpeFacaoFato", cvli1.getCbkGolpeFacaoFato());
            cv.put("cbkGolpeFacaFato", cvli1.getCbkGolpeFacaFato());
            cv.put("cbkPauladaFato", cvli1.getCbkPauladaFato());
            cv.put("cbkOmissaoSocorroFato", cvli1.getCbkOmissaoSocorroFato());
            cv.put("cbkQueimaduraFato", cvli1.getCbkQueimaduraFato());
            cv.put("cbkPedradaFato", cvli1.getCbkPedradaFato());
            cv.put("cbkPerfuracoesFato", cvli1.getCbkPerfuracoesFato());
            cv.put("cbkIncendioFato", cvli1.getCbkIncendioFato());
            cv.put("cbkSocosPontapesFato", cvli1.getCbkSocosPontapesFato());
            cv.put("cbkQueimarudasAcidoFato", cvli1.getCbkQueimarudasAcidoFato());
            cv.put("cbkNaoIdentificadoFato", cvli1.getCbkNaoIdentificadoFato());
            cv.put("cbkOutrosNaoRelacionadoFato", cvli1.getCbkOutrosNaoRelacionadoFato());
            cv.put("dsMotivacaoFato", cvli1.getDsMotivacaoFato());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = ?", new String[]{String.valueOf(cvli1.getId())});
        } else {

            cvli1.setId(codigocvlisempsramentro);
            cvli1.setEstatusCVLI(1);
            cvli1.setControle(2);
            cvli1.setValidarcvli(0);

            cv.put("dsNaturezaFato", cvli1.getDsNaturezaFato());
            cv.put("dtFato", cvli1.getDtFato());
            cv.put("ckbDataFatoIndefinido", cvli1.getCkbDataFatoIndefinido());
            cv.put("hsFato", cvli1.getHsFato());
            cv.put("ckbHorarioFatoIndefinido", cvli1.getCkbHorarioFatoIndefinido());
            cv.put("rbEnderecoFatoDefinido", cvli1.getRbEnderecoFatoDefinido());
            cv.put("rbEnderecoFatoNIndefinido", cvli1.getRbEnderecoFatoNIndefinido());
            cv.put("dsLogradouroFato", cvli1.getDsLogradouroFato());
            cv.put("dsRuaFato", cvli1.getDsRuaFato());
            cv.put("dsNRuaFato", cvli1.getDsNRuaFato());
            cv.put("dsReferenciaLocalFato", cvli1.getDsReferenciaLocalFato());
            cv.put("dsBairroFato", cvli1.getDsBairroFato());
            cv.put("dsDistVilPovoFato", cvli1.getDsDistVilPovoFato());
            cv.put("dsDescrDistVilPovoFato", cvli1.getDsDescrDistVilPovoFato());
            cv.put("dsMunicipioFato", cvli1.getDsMunicipioFato());
            cv.put("dsEstadoFato", cvli1.getDsEstadoFato());
            cv.put("dsZonaFato", cvli1.getDsZonaFato());
            cv.put("dsDetalhamentoLocal", cvli1.getDsDetalhamentoLocal());
            cv.put("dsOutroDetalhamento", cvli1.getDsOutroDetalhamento());
            cv.put("cbkTortura", cvli1.getCbkTortura());
            cv.put("cbkAfogamentoFato", cvli1.getCbkAfogamentoFato());
            cv.put("cbkAsfixiaFato", cvli1.getCbkAsfixiaFato());
            cv.put("cbkTropelamentoFato", cvli1.getCbkTropelamentoFato());
            cv.put("cbkDisparoArmaFato", cvli1.getCbkDisparoArmaFato());
            cv.put("cbkEmpurraoAlturaFato", cvli1.getCbkEmpurraoAlturaFato());
            cv.put("cbkEvenenadoFato", cvli1.getCbkEvenenadoFato());
            cv.put("cbkEstanaduraFato", cvli1.getCbkEstanaduraFato());
            cv.put("cbkEstrangulamentoFato", cvli1.getCbkEstrangulamentoFato());
            cv.put("cbkEmpurraoSobVeiculoFato", cvli1.getCbkEmpurraoSobVeiculoFato());
            cv.put("cbkGolpeFacaoFato", cvli1.getCbkGolpeFacaoFato());
            cv.put("cbkGolpeFacaFato", cvli1.getCbkGolpeFacaFato());
            cv.put("cbkPauladaFato", cvli1.getCbkPauladaFato());
            cv.put("cbkOmissaoSocorroFato", cvli1.getCbkOmissaoSocorroFato());
            cv.put("cbkQueimaduraFato", cvli1.getCbkQueimaduraFato());
            cv.put("cbkPedradaFato", cvli1.getCbkPedradaFato());
            cv.put("cbkPerfuracoesFato", cvli1.getCbkPerfuracoesFato());
            cv.put("cbkIncendioFato", cvli1.getCbkIncendioFato());
            cv.put("cbkSocosPontapesFato", cvli1.getCbkSocosPontapesFato());
            cv.put("cbkQueimarudasAcidoFato", cvli1.getCbkQueimarudasAcidoFato());
            cv.put("cbkNaoIdentificadoFato", cvli1.getCbkNaoIdentificadoFato());
            cv.put("cbkOutrosNaoRelacionadoFato", cvli1.getCbkOutrosNaoRelacionadoFato());
            cv.put("dsMotivacaoFato", cvli1.getDsMotivacaoFato());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = ?", new String[]{String.valueOf(cvli1.getId())});
        }
    }

    public long CadastrarCVLIWEB(Cvli cvli1){

        Log.i("Valores dsna",""+ cvli1.getIdUnico());
        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

            cv.put("dsNaturezaFato", cvli1.getDsNaturezaFato());
            cv.put("dtFato", cvli1.getDtFato());
            cv.put("ckbDataFatoIndefinido", cvli1.getCkbDataFatoIndefinido());
            cv.put("hsFato", cvli1.getHsFato());
            cv.put("ckbHorarioFatoIndefinido", cvli1.getCkbHorarioFatoIndefinido());
            cv.put("rbEnderecoFatoDefinido", cvli1.getRbEnderecoFatoDefinido());
            cv.put("rbEnderecoFatoNIndefinido", cvli1.getRbEnderecoFatoNIndefinido());
            cv.put("dsLogradouroFato", cvli1.getDsLogradouroFato());
            cv.put("dsRuaFato", cvli1.getDsRuaFato());
            cv.put("dsNRuaFato", cvli1.getDsNRuaFato());
            cv.put("dsReferenciaLocalFato", cvli1.getDsReferenciaLocalFato());
            cv.put("dsBairroFato", cvli1.getDsBairroFato());
            cv.put("dsDistVilPovoFato", cvli1.getDsDistVilPovoFato());
            cv.put("dsDescrDistVilPovoFato", cvli1.getDsDescrDistVilPovoFato());
            cv.put("dsMunicipioFato", cvli1.getDsMunicipioFato());
            cv.put("dsEstadoFato", cvli1.getDsEstadoFato());
            cv.put("dsZonaFato", cvli1.getDsZonaFato());
            cv.put("dsDetalhamentoLocal", cvli1.getDsDetalhamentoLocal());
            cv.put("dsOutroDetalhamento", cvli1.getDsOutroDetalhamento());
            cv.put("cbkTortura", cvli1.getCbkTortura());
            cv.put("cbkAfogamentoFato", cvli1.getCbkAfogamentoFato());
            cv.put("cbkAsfixiaFato", cvli1.getCbkAsfixiaFato());
            cv.put("cbkTropelamentoFato", cvli1.getCbkTropelamentoFato());
            cv.put("cbkDisparoArmaFato", cvli1.getCbkDisparoArmaFato());
            cv.put("cbkEmpurraoAlturaFato", cvli1.getCbkEmpurraoAlturaFato());
            cv.put("cbkEvenenadoFato", cvli1.getCbkEvenenadoFato());
            cv.put("cbkEstanaduraFato", cvli1.getCbkEstanaduraFato());
            cv.put("cbkEstrangulamentoFato", cvli1.getCbkEstrangulamentoFato());
            cv.put("cbkEmpurraoSobVeiculoFato", cvli1.getCbkEmpurraoSobVeiculoFato());
            cv.put("cbkGolpeFacaoFato", cvli1.getCbkGolpeFacaoFato());
            cv.put("cbkGolpeFacaFato", cvli1.getCbkGolpeFacaFato());
            cv.put("cbkPauladaFato", cvli1.getCbkPauladaFato());
            cv.put("cbkOmissaoSocorroFato", cvli1.getCbkOmissaoSocorroFato());
            cv.put("cbkQueimaduraFato", cvli1.getCbkQueimaduraFato());
            cv.put("cbkPedradaFato", cvli1.getCbkPedradaFato());
            cv.put("cbkPerfuracoesFato", cvli1.getCbkPerfuracoesFato());
            cv.put("cbkIncendioFato", cvli1.getCbkIncendioFato());
            cv.put("cbkSocosPontapesFato", cvli1.getCbkSocosPontapesFato());
            cv.put("cbkQueimarudasAcidoFato", cvli1.getCbkQueimarudasAcidoFato());
            cv.put("cbkNaoIdentificadoFato", cvli1.getCbkNaoIdentificadoFato());
            cv.put("cbkOutrosNaoRelacionadoFato", cvli1.getCbkOutrosNaoRelacionadoFato());
            cv.put("dsMotivacaoFato", cvli1.getDsMotivacaoFato());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("idUnico", cvli1.getIdUnico());
            cv.put("validarcvli", cvli1.getValidarcvli());
            cv.put("responsavelCVLI", cvli1.getResponsavelCVLI());
            cv.put("dtDataSilc", cvli1.getDtDataSilc());
            cv.put("hsHorarioSilc", cvli1.getHsHorarioSilc());
            cv.put("dsLogradouroSilc", cvli1.getDsLogradouroSilc());
            cv.put("dsRuaSilc", cvli1.getDsRuaSilc());
            cv.put("dsNRuaSilc", cvli1.getDsNRuaSilc());
            cv.put("dsReferenciaLocalSilc", cvli1.getDsReferenciaLocalSilc());
            cv.put("dsBairroSilc", cvli1.getDsBairroSilc());
            cv.put("dsDistVilPovoSilc", cvli1.getDsDistVilPovoSilc());
            cv.put("dsDescrDistVilPovoSilc", cvli1.getDsDescrDistVilPovoSilc());
            cv.put("dsMunicipioSilc", cvli1.getDsMunicipioSilc());
            cv.put("dsEstadoSilc", cvli1.getDsEstadoSilc());
            cv.put("rbEquipePreservacaoLocalDefinido", cvli1.getCbkEquipePreservacaoLocalDefinido());
            cv.put("rbEquipePreservacaoLocalSilcNDefinido", cvli1.getCbkEquipePreservacaoLocalSilcNDefinido());
            cv.put("rbEquipeLevantamentoSilcDefinido", cvli1.getCbkEquipeLevantamentoSilcDefinido());
            cv.put("rbEquipeLevantamentoSilcNDefinido", cvli1.getCbkEquipeLevantamentoSilcNDefinido());
            cv.put("rbEquipePeritosSilcDefinido", cvli1.getCbkEquipePeritosSilcDefinido());
            cv.put("rbEquipePeritosSilcNDefinido", cvli1.getCbkEquipePeritosSilcNDefinido());
            cv.put("rbObjetosArrecadadosSilcDefinido", cvli1.getCbkObjetosArrecadadosSilcDefinido());
            cv.put("rbObjetosArrecadadosSilcNDefinido", cvli1.getCbkObjetosArrecadadosSilcNDefinido());
            cv.put("dsResumoFato", cvli1.getDsResumoFato());
            cv.put("dsTituloResumo", cvli1.getDsTituloFato());
            cv.put("dsNBO", cvli1.getDsNBO());
            cv.put("dsNIP", cvli1.getDsNIP());
            cv.put("cbkBuscaApreensao", cvli1.getCbkBuscaApreensao());
            cv.put("cbkPrisaoTemporaria", cvli1.getCbkPrisaoTemporaria());
            cv.put("cbkPrisaoPreventiva", cvli1.getCbkPrisaoPreventiva());
            cv.put("cbkQuebraSigilo", cvli1.getCbkQuebraSigilo());
            cv.put("cbMedidaProtetivadeUrgencia", cvli1.getCbkMedidasProtetivas());
            cv.put("cbSemCautelares", cvli1.getCbkSemCautelares());
            cv.put("dsDestinacaoInvestigacao", cvli1.getDsDestinacaoInvestigacao());
            cv.put("dsProvidencia", cvli1.getDsProvidencia());
            cv.put("rbExpedidoGuiaPericial", cvli1.getCbkExpedidoGuiaPericial());
            cv.put("rbNExpedidoGuiaPericial", cvli1.getCbkNExpedidoGuiaPericial());
            cv.put("dsResFato", cvli1.getDsResFato());

            return db.insert("cvlis", null, cv);
    }

    public long AtualizarCVLIWEB(Cvli cvli1, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("dsNaturezaFato", cvli1.getDsNaturezaFato());
        cv.put("dtFato", cvli1.getDtFato());
        cv.put("ckbDataFatoIndefinido", cvli1.getCkbDataFatoIndefinido());
        cv.put("hsFato", cvli1.getHsFato());
        cv.put("ckbHorarioFatoIndefinido", cvli1.getCkbHorarioFatoIndefinido());
        cv.put("rbEnderecoFatoDefinido", cvli1.getRbEnderecoFatoDefinido());
        cv.put("rbEnderecoFatoNIndefinido", cvli1.getRbEnderecoFatoNIndefinido());
        cv.put("dsLogradouroFato", cvli1.getDsLogradouroFato());
        cv.put("dsRuaFato", cvli1.getDsRuaFato());
        cv.put("dsNRuaFato", cvli1.getDsNRuaFato());
        cv.put("dsReferenciaLocalFato", cvli1.getDsReferenciaLocalFato());
        cv.put("dsBairroFato", cvli1.getDsBairroFato());
        cv.put("dsDistVilPovoFato", cvli1.getDsDistVilPovoFato());
        cv.put("dsDescrDistVilPovoFato", cvli1.getDsDescrDistVilPovoFato());
        cv.put("dsMunicipioFato", cvli1.getDsMunicipioFato());
        cv.put("dsEstadoFato", cvli1.getDsEstadoFato());
        cv.put("dsZonaFato", cvli1.getDsZonaFato());
        cv.put("dsDetalhamentoLocal", cvli1.getDsDetalhamentoLocal());
        cv.put("dsOutroDetalhamento", cvli1.getDsOutroDetalhamento());
        cv.put("cbkTortura", cvli1.getCbkTortura());
        cv.put("cbkAfogamentoFato", cvli1.getCbkAfogamentoFato());
        cv.put("cbkAsfixiaFato", cvli1.getCbkAsfixiaFato());
        cv.put("cbkTropelamentoFato", cvli1.getCbkTropelamentoFato());
        cv.put("cbkDisparoArmaFato", cvli1.getCbkDisparoArmaFato());
        cv.put("cbkEmpurraoAlturaFato", cvli1.getCbkEmpurraoAlturaFato());
        cv.put("cbkEvenenadoFato", cvli1.getCbkEvenenadoFato());
        cv.put("cbkEstanaduraFato", cvli1.getCbkEstanaduraFato());
        cv.put("cbkEstrangulamentoFato", cvli1.getCbkEstrangulamentoFato());
        cv.put("cbkEmpurraoSobVeiculoFato", cvli1.getCbkEmpurraoSobVeiculoFato());
        cv.put("cbkGolpeFacaoFato", cvli1.getCbkGolpeFacaoFato());
        cv.put("cbkGolpeFacaFato", cvli1.getCbkGolpeFacaFato());
        cv.put("cbkPauladaFato", cvli1.getCbkPauladaFato());
        cv.put("cbkOmissaoSocorroFato", cvli1.getCbkOmissaoSocorroFato());
        cv.put("cbkQueimaduraFato", cvli1.getCbkQueimaduraFato());
        cv.put("cbkPedradaFato", cvli1.getCbkPedradaFato());
        cv.put("cbkPerfuracoesFato", cvli1.getCbkPerfuracoesFato());
        cv.put("cbkIncendioFato", cvli1.getCbkIncendioFato());
        cv.put("cbkSocosPontapesFato", cvli1.getCbkSocosPontapesFato());
        cv.put("cbkQueimarudasAcidoFato", cvli1.getCbkQueimarudasAcidoFato());
        cv.put("cbkNaoIdentificadoFato", cvli1.getCbkNaoIdentificadoFato());
        cv.put("cbkOutrosNaoRelacionadoFato", cvli1.getCbkOutrosNaoRelacionadoFato());
        cv.put("dsMotivacaoFato", cvli1.getDsMotivacaoFato());
        cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
        cv.put("Controle", cvli1.getControle());
        cv.put("validarcvli", cvli1.getValidarcvli());
        cv.put("responsavelCVLI", cvli1.getResponsavelCVLI());
        cv.put("dtDataSilc", cvli1.getDtDataSilc());
        cv.put("hsHorarioSilc", cvli1.getHsHorarioSilc());
        cv.put("dsLogradouroSilc", cvli1.getDsLogradouroSilc());
        cv.put("dsRuaSilc", cvli1.getDsRuaSilc());
        cv.put("dsNRuaSilc", cvli1.getDsNRuaSilc());
        cv.put("dsReferenciaLocalSilc", cvli1.getDsReferenciaLocalSilc());
        cv.put("dsBairroSilc", cvli1.getDsBairroSilc());
        cv.put("dsDistVilPovoSilc", cvli1.getDsDistVilPovoSilc());
        cv.put("dsDescrDistVilPovoSilc", cvli1.getDsDescrDistVilPovoSilc());
        cv.put("dsMunicipioSilc", cvli1.getDsMunicipioSilc());
        cv.put("dsEstadoSilc", cvli1.getDsEstadoSilc());
        cv.put("rbEquipePreservacaoLocalDefinido", cvli1.getCbkEquipePreservacaoLocalDefinido());
        cv.put("rbEquipePreservacaoLocalSilcNDefinido", cvli1.getCbkEquipePreservacaoLocalSilcNDefinido());
        cv.put("rbEquipeLevantamentoSilcDefinido", cvli1.getCbkEquipeLevantamentoSilcDefinido());
        cv.put("rbEquipeLevantamentoSilcNDefinido", cvli1.getCbkEquipeLevantamentoSilcNDefinido());
        cv.put("rbEquipePeritosSilcDefinido", cvli1.getCbkEquipePeritosSilcDefinido());
        cv.put("rbEquipePeritosSilcNDefinido", cvli1.getCbkEquipePeritosSilcNDefinido());
        cv.put("rbObjetosArrecadadosSilcDefinido", cvli1.getCbkObjetosArrecadadosSilcDefinido());
        cv.put("rbObjetosArrecadadosSilcNDefinido", cvli1.getCbkObjetosArrecadadosSilcNDefinido());
        cv.put("dsResumoFato", cvli1.getDsResumoFato());
        cv.put("dsTituloResumo", cvli1.getDsTituloFato());
        cv.put("dsNBO", cvli1.getDsNBO());
        cv.put("dsNIP", cvli1.getDsNIP());
        cv.put("cbkBuscaApreensao", cvli1.getCbkBuscaApreensao());
        cv.put("cbkPrisaoTemporaria", cvli1.getCbkPrisaoTemporaria());
        cv.put("cbkPrisaoPreventiva", cvli1.getCbkPrisaoPreventiva());
        cv.put("cbkQuebraSigilo", cvli1.getCbkQuebraSigilo());
        cv.put("cbMedidaProtetivadeUrgencia", cvli1.getCbkMedidasProtetivas());
        cv.put("cbSemCautelares", cvli1.getCbkSemCautelares());
        cv.put("dsDestinacaoInvestigacao", cvli1.getDsDestinacaoInvestigacao());
        cv.put("dsProvidencia", cvli1.getDsProvidencia());
        cv.put("rbExpedidoGuiaPericial", cvli1.getCbkExpedidoGuiaPericial());
        cv.put("rbNExpedidoGuiaPericial", cvli1.getCbkNExpedidoGuiaPericial());
        cv.put("dsResFato", cvli1.getDsResFato());

        return db.update("cvlis", cv, "idUnico = '" + codigo + "'", null);
    }

    public long AtualizarCVLIProvidencias(Cvli cvli1, int id, int controle) {

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cvli1.setEstatusCVLI(1);
            cvli1.setControle(controle);
            cvli1.setValidarcvli(0);

            cv.put("dsNBO", cvli1.getDsNBO());
            cv.put("dsNIP", cvli1.getDsNIP());
            cv.put("cbkBuscaApreensao", cvli1.getCbkBuscaApreensao());
            cv.put("cbkPrisaoTemporaria", cvli1.getCbkPrisaoTemporaria());
            cv.put("cbkPrisaoPreventiva", cvli1.getCbkPrisaoPreventiva());
            cv.put("cbkQuebraSigilo", cvli1.getCbkQuebraSigilo());
            cv.put("cbMedidaProtetivadeUrgencia", cvli1.getCbkMedidasProtetivas());
            cv.put("cbSemCautelares", cvli1.getCbkSemCautelares());
            cv.put("dsDestinacaoInvestigacao", cvli1.getDsDestinacaoInvestigacao());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("dsProvidencia", cvli1.getDsProvidencia());
            cv.put("rbExpedidoGuiaPericial", cvli1.getCbkExpedidoGuiaPericial());
            cv.put("rbNExpedidoGuiaPericial", cvli1.getCbkNExpedidoGuiaPericial());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = '" + id + "'", null);

    }

    public long AtualizarCVLIFatos(Cvli cvli1, int id, int controle) {

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cvli1.setEstatusCVLI(1);
            cvli1.setControle(controle);
            cvli1.setValidarcvli(0);

            cv.put("dsNaturezaFato", cvli1.getDsNaturezaFato());
            cv.put("dtFato", cvli1.getDtFato());
            cv.put("ckbDataFatoIndefinido", cvli1.getCkbDataFatoIndefinido());
            cv.put("hsFato", cvli1.getHsFato());
            cv.put("ckbHorarioFatoIndefinido", cvli1.getCkbHorarioFatoIndefinido());
            cv.put("rbEnderecoFatoDefinido", cvli1.getRbEnderecoFatoDefinido());
            cv.put("rbEnderecoFatoNIndefinido", cvli1.getRbEnderecoFatoNIndefinido());
            cv.put("dsLogradouroFato", cvli1.getDsLogradouroFato());
            cv.put("dsRuaFato", cvli1.getDsRuaFato());
            cv.put("dsNRuaFato", cvli1.getDsNRuaFato());
            cv.put("dsReferenciaLocalFato", cvli1.getDsReferenciaLocalFato());
            cv.put("dsBairroFato", cvli1.getDsBairroFato());
            cv.put("dsDistVilPovoFato", cvli1.getDsDistVilPovoFato());
            cv.put("dsDescrDistVilPovoFato", cvli1.getDsDescrDistVilPovoFato());
            cv.put("dsMunicipioFato", cvli1.getDsMunicipioFato());
            cv.put("dsEstadoFato", cvli1.getDsEstadoFato());
            cv.put("dsZonaFato", cvli1.getDsZonaFato());
            cv.put("dsDetalhamentoLocal", cvli1.getDsDetalhamentoLocal());
            cv.put("dsOutroDetalhamento", cvli1.getDsOutroDetalhamento());
            cv.put("cbkTortura", cvli1.getCbkTortura());
            cv.put("cbkAfogamentoFato", cvli1.getCbkAfogamentoFato());
            cv.put("cbkAsfixiaFato", cvli1.getCbkAsfixiaFato());
            cv.put("cbkTropelamentoFato", cvli1.getCbkTropelamentoFato());
            cv.put("cbkDisparoArmaFato", cvli1.getCbkDisparoArmaFato());
            cv.put("cbkEmpurraoAlturaFato", cvli1.getCbkEmpurraoAlturaFato());
            cv.put("cbkEvenenadoFato", cvli1.getCbkEvenenadoFato());
            cv.put("cbkEstanaduraFato", cvli1.getCbkEstanaduraFato());
            cv.put("cbkEstrangulamentoFato", cvli1.getCbkEstrangulamentoFato());
            cv.put("cbkEmpurraoSobVeiculoFato", cvli1.getCbkEmpurraoSobVeiculoFato());
            cv.put("cbkGolpeFacaoFato", cvli1.getCbkGolpeFacaoFato());
            cv.put("cbkGolpeFacaFato", cvli1.getCbkGolpeFacaFato());
            cv.put("cbkPauladaFato", cvli1.getCbkPauladaFato());
            cv.put("cbkOmissaoSocorroFato", cvli1.getCbkOmissaoSocorroFato());
            cv.put("cbkQueimaduraFato", cvli1.getCbkQueimaduraFato());
            cv.put("cbkPedradaFato", cvli1.getCbkPedradaFato());
            cv.put("cbkPerfuracoesFato", cvli1.getCbkPerfuracoesFato());
            cv.put("cbkIncendioFato", cvli1.getCbkIncendioFato());
            cv.put("cbkSocosPontapesFato", cvli1.getCbkSocosPontapesFato());
            cv.put("cbkQueimarudasAcidoFato", cvli1.getCbkQueimarudasAcidoFato());
            cv.put("cbkNaoIdentificadoFato", cvli1.getCbkNaoIdentificadoFato());
            cv.put("cbkOutrosNaoRelacionadoFato", cvli1.getCbkOutrosNaoRelacionadoFato());
            cv.put("dsMotivacaoFato", cvli1.getDsMotivacaoFato());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = '" + id + "'", null);

    }

    public long AtualizarCVLISilc(Cvli cvli1, int id, int controle) {

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cvli1.setEstatusCVLI(1);
            cvli1.setControle(controle);
            cvli1.setValidarcvli(0);

            cv.put("responsavelCVLI", cvli1.getResponsavelCVLI());
            cv.put("dtDataSilc", cvli1.getDtDataSilc());
            cv.put("hsHorarioSilc", cvli1.getHsHorarioSilc());
            cv.put("dsLogradouroSilc", cvli1.getDsLogradouroSilc());
            cv.put("dsRuaSilc", cvli1.getDsRuaSilc());
            cv.put("dsNRuaSilc", cvli1.getDsNRuaSilc());
            cv.put("dsReferenciaLocalSilc", cvli1.getDsReferenciaLocalSilc());
            cv.put("dsBairroSilc", cvli1.getDsBairroSilc());
            cv.put("dsDistVilPovoSilc", cvli1.getDsDistVilPovoSilc());
            cv.put("dsDescrDistVilPovoSilc", cvli1.getDsDescrDistVilPovoSilc());
            cv.put("dsMunicipioSilc", cvli1.getDsMunicipioSilc());
            cv.put("dsEstadoSilc", cvli1.getDsEstadoSilc());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("rbEquipePreservacaoLocalDefinido", cvli1.getCbkEquipePreservacaoLocalDefinido());
            cv.put("rbEquipePreservacaoLocalSilcNDefinido", cvli1.getCbkEquipePreservacaoLocalSilcNDefinido());
            cv.put("rbEquipeLevantamentoSilcDefinido", cvli1.getCbkEquipeLevantamentoSilcDefinido());
            cv.put("rbEquipeLevantamentoSilcNDefinido", cvli1.getCbkEquipeLevantamentoSilcNDefinido());
            cv.put("rbEquipePeritosSilcDefinido", cvli1.getCbkEquipePeritosSilcDefinido());
            cv.put("rbEquipePeritosSilcNDefinido", cvli1.getCbkEquipePeritosSilcNDefinido());
            cv.put("rbObjetosArrecadadosSilcDefinido", cvli1.getCbkObjetosArrecadadosSilcDefinido());
            cv.put("rbObjetosArrecadadosSilcNDefinido", cvli1.getCbkObjetosArrecadadosSilcNDefinido());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = '" + id + "'", null);

    }

    public long AtualizarCVLIResumoFatos(Cvli cvli1, int id, int controle) {

        db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cvli1.setEstatusCVLI(1);
            cvli1.setControle(controle);
            cvli1.setValidarcvli(0);

            cv.put("dsResumoFato", cvli1.getDsResumoFato());
            cv.put("EstatusCVLI", cvli1.getEstatusCVLI());
            cv.put("Controle", cvli1.getControle());
            cv.put("dsTituloResumo", cvli1.getDsTituloFato());
            cv.put("validarcvli", cvli1.getValidarcvli());

            return db.update("cvlis", cv, "id = '" + id + "'", null);

    }

    public int ContarCvli(){
        int contador = 0;

        Cursor cursor = db.query("cvlis", new String[]{"COUNT(*)"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            contador = cursor.getInt(0);
        }

        return contador;
    }

    public String VerificarEmBrancoCVLI(int id){

        db = conexao.getReadableDatabase();

        String nomesembrancos = null;

        Cursor cursor = db.query("cvlis", new String[]{"dsNaturezaFato", "dtFato", "dsMunicipioFato","dsMotivacaoFato",
                 "dsResumoFato", "dsNBO","dsDestinacaoInvestigacao", "dsTituloResumo","cbkAfogamentoFato",
                 "cbkAsfixiaFato","cbkTropelamentoFato","cbkDisparoArmaFato","cbkEmpurraoAlturaFato","cbkEvenenadoFato","cbkEstanaduraFato",
                 "cbkEstrangulamentoFato","cbkEmpurraoSobVeiculoFato","cbkGolpeFacaoFato","cbkGolpeFacaFato","cbkPauladaFato","cbkOmissaoSocorroFato",
                 "cbkQueimaduraFato","cbkPedradaFato","cbkPerfuracoesFato","cbkIncendioFato","cbkSocosPontapesFato","cbkQueimarudasAcidoFato","cbkNaoIdentificadoFato",
                 "cbkOutrosNaoRelacionadoFato","cbkTortura"}, "id='"+id+"'", null, null, null, null);

        while (cursor.moveToNext()) {
            Cvli cvli = new Cvli();

            cvli.setDsNaturezaFato(cursor.getString(0));
            if(cvli.getDsNaturezaFato() == null || cvli.getDsNaturezaFato().equals("")){
                nomesembrancos = nomesembrancos + "Natureza do Fato, ";
            }
            cvli.setDtFato(cursor.getString(1));
            if(cvli.getDtFato() == null || cvli.getDtFato().equals("")){
                nomesembrancos = nomesembrancos + "Data do Fato, ";
            }
            cvli.setDsMunicipioFato(cursor.getString(2));
            if(cvli.getDsMunicipioFato() == null || cvli.getDsMunicipioFato().equals("")){
                nomesembrancos = nomesembrancos + "Município do Fato, ";
            }
            cvli.setDsMotivacaoFato(cursor.getString(3));
            if(cvli.getDsMotivacaoFato() == null || cvli.getDsMotivacaoFato().equals("") || cvli.getDsMotivacaoFato().equals("Selecione...")){
                nomesembrancos = nomesembrancos + "Motivação do Fato, ";
            }
            cvli.setDsResumoFato(cursor.getString(4));
            if(cvli.getDsResumoFato() == null || cvli.getDsResumoFato().equals("")){
                nomesembrancos = nomesembrancos + "Descrição do Fato, ";
            }
            cvli.setDsNBO(cursor.getString(5));
            if(cvli.getDsNBO() == null || cvli.getDsNBO().equals("")){
                nomesembrancos = nomesembrancos + "Número do BO, ";
            }
            cvli.setDsDestinacaoInvestigacao(cursor.getString(6));
            if(cvli.getDsDestinacaoInvestigacao() == null || cvli.getDsDestinacaoInvestigacao().equals("")){
                nomesembrancos = nomesembrancos + "Destinação da Investigação, ";
            }
            cvli.setDsTituloFato(cursor.getString(7));
            if(cvli.getDsTituloFato() == null || cvli.getDsTituloFato().equals("")){
                nomesembrancos = nomesembrancos + "Título do Fato, ";
            }
            cvli.setCbkAfogamentoFato(cursor.getInt(8));
            cvli.setCbkAsfixiaFato(cursor.getInt(9));
            cvli.setCbkTropelamentoFato(cursor.getInt(10));
            cvli.setCbkDisparoArmaFato(cursor.getInt(11));
            cvli.setCbkEmpurraoAlturaFato(cursor.getInt(12));
            cvli.setCbkEvenenadoFato(cursor.getInt(13));
            cvli.setCbkEstanaduraFato(cursor.getInt(14));
            cvli.setCbkEstrangulamentoFato(cursor.getInt(15));
            cvli.setCbkEmpurraoSobVeiculoFato(cursor.getInt(16));
            cvli.setCbkGolpeFacaoFato(cursor.getInt(17));
            cvli.setCbkGolpeFacaFato(cursor.getInt(18));
            cvli.setCbkPauladaFato(cursor.getInt(19));
            cvli.setCbkOmissaoSocorroFato(cursor.getInt(20));
            cvli.setCbkQueimaduraFato(cursor.getInt(21));
            cvli.setCbkPedradaFato(cursor.getInt(22));
            cvli.setCbkPerfuracoesFato(cursor.getInt(23));
            cvli.setCbkIncendioFato(cursor.getInt(24));
            cvli.setCbkSocosPontapesFato(cursor.getInt(25));
            cvli.setCbkQueimarudasAcidoFato(cursor.getInt(25));
            cvli.setCbkNaoIdentificadoFato(cursor.getInt(26));
            cvli.setCbkOutrosNaoRelacionadoFato(cursor.getInt(27));
            cvli.setCbkTortura(cursor.getInt(28));
            if(cvli.getCbkAfogamentoFato() == 0 && cvli.getCbkAsfixiaFato() == 0 && cvli.getCbkTropelamentoFato() == 0 && cvli.getCbkDisparoArmaFato() == 0
                    && cvli.getCbkEmpurraoAlturaFato() == 0 && cvli.getCbkEstanaduraFato() == 0 && cvli.getCbkEstrangulamentoFato() == 0 && cvli.getCbkEmpurraoSobVeiculoFato() == 0
                    && cvli.getCbkGolpeFacaoFato() == 0 && cvli.getCbkGolpeFacaFato() == 0 && cvli.getCbkPauladaFato() == 0 && cvli.getCbkOmissaoSocorroFato() == 0
                    && cvli.getCbkQueimaduraFato() == 0 && cvli.getCbkPedradaFato() == 0 && cvli.getCbkPerfuracoesFato() == 0 && cvli.getCbkIncendioFato() == 0
                    && cvli.getCbkSocosPontapesFato() == 0 && cvli.getCbkQueimarudasAcidoFato() == 0 && cvli.getCbkNaoIdentificadoFato() == 0 && cvli.getCbkOutrosNaoRelacionadoFato() == 0 && cvli.getCbkTortura() == 0){
                nomesembrancos = nomesembrancos + "Meio Empregado, ";
            }

        }
        return nomesembrancos;

    }

    public List<Cvli> retornaCVLI(){

        List<Cvli> cvlis = new ArrayList<>();

            db = conexao.getReadableDatabase();
            Cursor cursor = db.query("cvlis", new String[]{"id", "dsNaturezaFato", "dtFato", "ckbDataFatoIndefinido",
                    "hsFato", "ckbHorarioFatoIndefinido","rbEnderecoFatoDefinido", "rbEnderecoFatoNIndefinido", "dsLogradouroFato", "dsRuaFato", "dsNRuaFato",
                    "dsReferenciaLocalFato", "dsBairroFato", "dsDistVilPovoFato", "dsDescrDistVilPovoFato", "dsMunicipioFato", "dsEstadoFato",
                    "dsZonaFato", "dsDetalhamentoLocal", "dsOutroDetalhamento", "cbkAfogamentoFato", "cbkAsfixiaFato", "cbkTropelamentoFato",
                    "cbkDisparoArmaFato", "cbkEmpurraoAlturaFato", "cbkEvenenadoFato", "cbkEstanaduraFato",
                    "cbkEstrangulamentoFato", "cbkEmpurraoSobVeiculoFato", "cbkGolpeFacaoFato", "cbkGolpeFacaFato", "cbkPauladaFato",
                    "cbkOmissaoSocorroFato", "cbkQueimaduraFato", "cbkPedradaFato", "cbkPerfuracoesFato", "cbkIncendioFato", "cbkSocosPontapesFato",
                    "cbkQueimarudasAcidoFato", "cbkNaoIdentificadoFato", "cbkOutrosNaoRelacionadoFato", "dsMotivacaoFato","responsavelCVLI",
                    "dtDataSilc", "hsHorarioSilc", "dsLogradouroSilc", "dsRuaSilc", "dsNRuaSilc", "dsReferenciaLocalSilc", "dsBairroSilc",
                    "dsDistVilPovoSilc", "dsDescrDistVilPovoSilc", "dsMunicipioSilc", "dsEstadoSilc", "dsResumoFato", "dsNBO",
                    "rbExpedidoGuiaPericial","rbNExpedidoGuiaPericial", "dsNIP", "cbkBuscaApreensao", "cbkPrisaoTemporaria", "cbkPrisaoPreventiva",
                    "cbkQuebraSigilo", "cbMedidaProtetivadeUrgencia", "dsDestinacaoInvestigacao", "EstatusCVLI", "unidade_id", "Controle", "servidor_id",
                    "dsTituloResumo", "idUnico", "dsProvidencia", "cbSemCautelares", "validarcvli", "dsResFato", "cbkTortura"}, null, null, null, null, null);

            while (cursor.moveToNext()) {
                Cvli cvli = new Cvli();

                cvli.setId(cursor.getInt(0));
                cvli.setDsNaturezaFato(cursor.getString(1));
                cvli.setDtFato(cursor.getString(2));
                cvli.setCkbDataFatoIndefinido(cursor.getInt(3));
                cvli.setHsFato(cursor.getString(4));
                cvli.setCkbHorarioFatoIndefinido(cursor.getInt(5));
                cvli.setRbEnderecoFatoDefinido((cursor.getInt(6)));
                cvli.setRbEnderecoFatoNIndefinido((cursor.getInt(7)));
                cvli.setDsLogradouroFato(cursor.getString(8));
                cvli.setDsRuaFato(cursor.getString(9));
                cvli.setDsNRuaFato(cursor.getString(10));
                cvli.setDsReferenciaLocalFato(cursor.getString(11));
                cvli.setDsBairroFato(cursor.getString(12));
                cvli.setDsDistVilPovoFato(cursor.getString(13));
                cvli.setDsDescrDistVilPovoFato(cursor.getString(14));
                cvli.setDsMunicipioFato(cursor.getString(15));
                cvli.setDsEstadoFato(cursor.getString(16));
                cvli.setDsZonaFato(cursor.getString(17));
                cvli.setDsDetalhamentoLocal(cursor.getString(18));
                cvli.setDsOutroDetalhamento(cursor.getString(19));
                cvli.setCbkAfogamentoFato(cursor.getInt(20));
                cvli.setCbkAsfixiaFato(cursor.getInt(21));
                cvli.setCbkTropelamentoFato(cursor.getInt(22));
                cvli.setCbkDisparoArmaFato(cursor.getInt(23));
                cvli.setCbkEmpurraoAlturaFato(cursor.getInt(24));
                cvli.setCbkEvenenadoFato(cursor.getInt(25));
                cvli.setCbkEstanaduraFato(cursor.getInt(26));
                cvli.setCbkEstanaduraFato(cursor.getInt(27));
                cvli.setCbkEmpurraoSobVeiculoFato(cursor.getInt(28));
                cvli.setCbkGolpeFacaoFato(cursor.getInt(29));
                cvli.setCbkGolpeFacaFato(cursor.getInt(30));
                cvli.setCbkPauladaFato(cursor.getInt(31));
                cvli.setCbkOmissaoSocorroFato(cursor.getInt(32));
                cvli.setCbkQueimaduraFato(cursor.getInt(33));
                cvli.setCbkPedradaFato(cursor.getInt(34));
                cvli.setCbkPerfuracoesFato(cursor.getInt(35));
                cvli.setCbkIncendioFato(cursor.getInt(36));
                cvli.setCbkSocosPontapesFato(cursor.getInt(37));
                cvli.setCbkQueimarudasAcidoFato(cursor.getInt(38));
                cvli.setCbkNaoIdentificadoFato(cursor.getInt(39));
                cvli.setCbkOutrosNaoRelacionadoFato(cursor.getInt(40));
                cvli.setDsMotivacaoFato(cursor.getString(41));
                cvli.setResponsavelCVLI(cursor.getString(42));
                cvli.setDtDataSilc(cursor.getString(43));
                cvli.setHsHorarioSilc(cursor.getString(44));
                cvli.setDsLogradouroSilc(cursor.getString(45));
                cvli.setDsRuaSilc(cursor.getString(46));
                cvli.setDsNRuaSilc(cursor.getString(47));
                cvli.setDsReferenciaLocalSilc(cursor.getString(48));
                cvli.setDsBairroSilc(cursor.getString(49));
                cvli.setDsDistVilPovoSilc(cursor.getString(50));
                cvli.setDsDescrDistVilPovoSilc(cursor.getString(51));
                cvli.setDsMunicipioSilc(cursor.getString(52));
                cvli.setDsEstadoSilc(cursor.getString(53));
                cvli.setDsResumoFato(cursor.getString(54));
                cvli.setDsNBO(cursor.getString(55));
                cvli.setCbkExpedidoGuiaPericial(cursor.getInt(56));
                cvli.setCbkNExpedidoGuiaPericial(cursor.getInt(57));
                cvli.setDsNIP(cursor.getString(58));
                cvli.setCbkBuscaApreensao(cursor.getInt(59));
                cvli.setCbkPrisaoTemporaria(cursor.getInt(60));
                cvli.setCbkPrisaoPreventiva(cursor.getInt(61));
                cvli.setCbkQuebraSigilo(cursor.getInt(62));
                cvli.setCbkMedidasProtetivas(cursor.getInt(63));
                cvli.setDsDestinacaoInvestigacao(cursor.getString(64));
                cvli.setEstatusCVLI(cursor.getInt(65));
                cvli.setUnidade_id(cursor.getInt(66));
                cvli.setControle(cursor.getInt(67));
                cvli.setServidor_id(cursor.getInt(68));
                cvli.setDsTituloFato(cursor.getString(69));
                cvli.setIdUnico(cursor.getString(70));
                cvli.setDsProvidencia(cursor.getString(71));
                cvli.setCbkSemCautelares(cursor.getInt(72));
                cvli.setValidarcvli(cursor.getInt(73));
                cvli.setDsResFato(cursor.getString(74));
                cvli.setCbkTortura(cursor.getInt(75));

                cvlis.add(cvli);
            }
            return cvlis;
    }


    public List<Cvli> retornaEnderecoFato(int id){

        List<Cvli> cvlis = new ArrayList<>();

        db = conexao.getReadableDatabase();
        Cursor cursor = db.query("cvlis", new String[]{"id", "dsLogradouroFato", "dsRuaFato", "dsNRuaFato",
                "dsReferenciaLocalFato", "dsBairroFato", "dsDistVilPovoFato", "dsDescrDistVilPovoFato", "dsMunicipioFato", "dsEstadoFato",
                "dsZonaFato", "dsDetalhamentoLocal", "dsOutroDetalhamento"}, "id='"+id+"'", null, null, null, null);

        while (cursor.moveToNext()) {
            Cvli cvli = new Cvli();

            cvli.setId(cursor.getInt(0));
            cvli.setDsLogradouroFato(cursor.getString(1));
            cvli.setDsRuaFato(cursor.getString(2));
            cvli.setDsNRuaFato(cursor.getString(3));
            cvli.setDsReferenciaLocalFato(cursor.getString(4));
            cvli.setDsBairroFato(cursor.getString(5));
            cvli.setDsDistVilPovoFato(cursor.getString(6));
            cvli.setDsDescrDistVilPovoFato(cursor.getString(7));
            cvli.setDsMunicipioFato(cursor.getString(8));
            cvli.setDsEstadoFato(cursor.getString(9));
            cvli.setDsZonaFato(cursor.getString(10));
            cvli.setDsDetalhamentoLocal(cursor.getString(11));
            cvli.setDsOutroDetalhamento(cursor.getString(12));

            cvlis.add(cvli);
        }
        return cvlis;
    }

    public ArrayList<String> retornarCVLIGeral(int codigo){

        db = conexao.getReadableDatabase();

        ArrayList<String> listagem = new ArrayList<String>();

        String SqlJoin = "select c.dsMunicipioFato, c.dsMotivacaoFato, c.dsRuaFato, c.dsBairroFato," +
                "c.dtFato, v.dsNomeVitima, a.dsNomeAutoria, " +
                "o.dsTipoObjetoApreendido, o.dsDescricaoObjetoApreendido, ca.dsTipoCarro, ca.dsDescricaoCarro," +
                "ca.dsDescricaoBiscicleta, ep.dsCargoEquipePerito, ep.dsNomeEquipePerito, el.dsCargoEquipeLevantamento," +
                "el.dsNomeEquipeLevantamento, epl.dsCargoEquipepreservacaolocal, epl.dsNomeEquipepreservacaolocal" +
                " from cvlis c inner join " +
                "cvliequipepreservacaolocals epl on c.id=epl.fkCvli inner join cvliequipelevantamentos el on c.id=el.fkCvli" +
                " inner join cvliequipeperitos ep on c.id=epl.fkCvli inner join cvliautorias a on c.id=a.fkCvli " +
                "inner join cvlicarros ca on c.id=ca.fkCvli inner join cvliobjetosapreendidos o on c.id=o.fkCvli inner join " +
                "cvlivitimas v on c.id=v.fkCvli where c.id="+codigo;

        Cursor cur = db.rawQuery(SqlJoin,null);


       while (cur.moveToNext()){

           if(!listagem.contains(cur.getString(0))){
               listagem.add(cur.getString(0));
           }
           if(!listagem.contains(cur.getString(1))){
               listagem.add(cur.getString(1));
           }
           if(!listagem.contains(cur.getString(2))){
               listagem.add(cur.getString(2));
           }
           if(!listagem.contains(cur.getString(3))){
               listagem.add(cur.getString(3));
           }
           if(!listagem.contains(cur.getString(4))){
               listagem.add(cur.getString(4));
           }
           if(!listagem.contains(cur.getString(5))){
               listagem.add(cur.getString(5));
           }
           if(!listagem.contains(cur.getString(6))){
               listagem.add(cur.getString(6));
           }
           if(!listagem.contains(cur.getString(7))){
               listagem.add(cur.getString(7));
           }
           if(!listagem.contains(cur.getString(8))){
               listagem.add(cur.getString(8));
           }
           if(!listagem.contains(cur.getString(9))){
               listagem.add(cur.getString(9));
           }
           if(!listagem.contains(cur.getString(10))){
               listagem.add(cur.getString(10));
           }
           if(!listagem.contains(cur.getString(11))){
               listagem.add(cur.getString(11));
           }
           if(!listagem.contains(cur.getString(12))){
               listagem.add(cur.getString(12));
           }
           if(!listagem.contains(cur.getString(13))){
               listagem.add(cur.getString(13));
           }
           if(!listagem.contains(cur.getString(14))){
               listagem.add(cur.getString(14));
           }
           if(!listagem.contains(cur.getString(15))){
               listagem.add(cur.getString(15));
           }
           if(!listagem.contains(cur.getString(16))){
               listagem.add(cur.getString(16));
           }
           if(!listagem.contains(cur.getString(17))){
               listagem.add(cur.getString(17));
           }

            }

        return listagem;
    }

    public List<Cvli> retornaCVLIFato(int id){

        List<Cvli> cvlisfato = new ArrayList<>();

            db = conexao.getReadableDatabase();

            Cursor cursor = db.query("cvlis", new String[]{"id", "dsNaturezaFato", "dtFato", "ckbDataFatoIndefinido",
                    "hsFato", "ckbHorarioFatoIndefinido", "rbEnderecoFatoDefinido", "rbEnderecoFatoNIndefinido", "dsLogradouroFato", "dsRuaFato", "dsNRuaFato",
                    "dsReferenciaLocalFato", "dsBairroFato", "dsDistVilPovoFato", "dsDescrDistVilPovoFato", "dsMunicipioFato", "dsEstadoFato",
                    "dsZonaFato", "dsDetalhamentoLocal", "dsOutroDetalhamento", "cbkAfogamentoFato", "cbkAsfixiaFato", "cbkTropelamentoFato",
                    "cbkDisparoArmaFato", "cbkEmpurraoAlturaFato", "cbkEvenenadoFato", "cbkEstanaduraFato",
                    "cbkEstrangulamentoFato", "cbkEmpurraoSobVeiculoFato", "cbkGolpeFacaoFato", "cbkGolpeFacaFato", "cbkPauladaFato",
                    "cbkOmissaoSocorroFato", "cbkQueimaduraFato", "cbkPedradaFato", "cbkPerfuracoesFato", "cbkIncendioFato", "cbkSocosPontapesFato",
                    "cbkQueimarudasAcidoFato", "cbkNaoIdentificadoFato", "cbkOutrosNaoRelacionadoFato", "dsMotivacaoFato", "cbkTortura"}, "id='" + id + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Cvli cvliFato = new Cvli();

                cvliFato.setId(cursor.getInt(0));
                cvliFato.setDsNaturezaFato(cursor.getString(1));
                cvliFato.setDtFato(cursor.getString(2));
                cvliFato.setCkbDataFatoIndefinido(cursor.getInt(3));
                cvliFato.setHsFato(cursor.getString(4));
                cvliFato.setCkbHorarioFatoIndefinido(cursor.getInt(5));
                cvliFato.setRbEnderecoFatoDefinido((cursor.getInt(6)));
                cvliFato.setRbEnderecoFatoNIndefinido((cursor.getInt(7)));
                cvliFato.setDsLogradouroFato(cursor.getString(8));
                cvliFato.setDsRuaFato(cursor.getString(9));
                cvliFato.setDsNRuaFato(cursor.getString(10));
                cvliFato.setDsReferenciaLocalFato(cursor.getString(11));
                cvliFato.setDsBairroFato(cursor.getString(12));
                cvliFato.setDsDistVilPovoFato(cursor.getString(13));
                cvliFato.setDsDescrDistVilPovoFato(cursor.getString(14));
                cvliFato.setDsMunicipioFato(cursor.getString(15));
                cvliFato.setDsEstadoFato(cursor.getString(16));
                cvliFato.setDsZonaFato(cursor.getString(17));
                cvliFato.setDsDetalhamentoLocal(cursor.getString(18));
                cvliFato.setDsOutroDetalhamento(cursor.getString(19));
                cvliFato.setCbkAfogamentoFato(cursor.getInt(20));
                cvliFato.setCbkAsfixiaFato(cursor.getInt(21));
                cvliFato.setCbkTropelamentoFato(cursor.getInt(22));
                cvliFato.setCbkDisparoArmaFato(cursor.getInt(23));
                cvliFato.setCbkEmpurraoAlturaFato(cursor.getInt(24));
                cvliFato.setCbkEvenenadoFato(cursor.getInt(25));
                cvliFato.setCbkEstanaduraFato(cursor.getInt(26));
                cvliFato.setCbkEstanaduraFato(cursor.getInt(27));
                cvliFato.setCbkEmpurraoSobVeiculoFato(cursor.getInt(28));
                cvliFato.setCbkGolpeFacaoFato(cursor.getInt(29));
                cvliFato.setCbkGolpeFacaFato(cursor.getInt(30));
                cvliFato.setCbkPauladaFato(cursor.getInt(31));
                cvliFato.setCbkOmissaoSocorroFato(cursor.getInt(32));
                cvliFato.setCbkQueimaduraFato(cursor.getInt(33));
                cvliFato.setCbkPedradaFato(cursor.getInt(34));
                cvliFato.setCbkPerfuracoesFato(cursor.getInt(35));
                cvliFato.setCbkIncendioFato(cursor.getInt(36));
                cvliFato.setCbkSocosPontapesFato(cursor.getInt(37));
                cvliFato.setCbkQueimarudasAcidoFato(cursor.getInt(38));
                cvliFato.setCbkNaoIdentificadoFato(cursor.getInt(39));
                cvliFato.setCbkOutrosNaoRelacionadoFato(cursor.getInt(40));
                cvliFato.setDsMotivacaoFato(cursor.getString(41));
                cvliFato.setCbkTortura(cursor.getInt(42));

                cvlisfato.add(cvliFato);

            }
            return cvlisfato;
    }

    public Cvli retornaCVLIFatoObj(int id){

       Cvli cvliFato = new Cvli();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("cvlis", new String[]{"id", "dsNaturezaFato", "dtFato", "ckbDataFatoIndefinido",
                "hsFato", "ckbHorarioFatoIndefinido", "rbEnderecoFatoDefinido", "rbEnderecoFatoNIndefinido", "dsLogradouroFato", "dsRuaFato", "dsNRuaFato",
                "dsReferenciaLocalFato", "dsBairroFato", "dsDistVilPovoFato", "dsDescrDistVilPovoFato", "dsMunicipioFato", "dsEstadoFato",
                "dsZonaFato", "dsDetalhamentoLocal", "dsOutroDetalhamento", "cbkAfogamentoFato", "cbkAsfixiaFato", "cbkTropelamentoFato",
                "cbkDisparoArmaFato", "cbkEmpurraoAlturaFato", "cbkEvenenadoFato", "cbkEstanaduraFato",
                "cbkEstrangulamentoFato", "cbkEmpurraoSobVeiculoFato", "cbkGolpeFacaoFato", "cbkGolpeFacaFato", "cbkPauladaFato",
                "cbkOmissaoSocorroFato", "cbkQueimaduraFato", "cbkPedradaFato", "cbkPerfuracoesFato", "cbkIncendioFato", "cbkSocosPontapesFato",
                "cbkQueimarudasAcidoFato", "cbkNaoIdentificadoFato", "cbkOutrosNaoRelacionadoFato", "dsMotivacaoFato", "cbkTortura"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            cvliFato.setId(cursor.getInt(0));
            cvliFato.setDsNaturezaFato(cursor.getString(1));
            cvliFato.setDtFato(cursor.getString(2));
            cvliFato.setCkbDataFatoIndefinido(cursor.getInt(3));
            cvliFato.setHsFato(cursor.getString(4));
            cvliFato.setCkbHorarioFatoIndefinido(cursor.getInt(5));
            cvliFato.setRbEnderecoFatoDefinido((cursor.getInt(6)));
            cvliFato.setRbEnderecoFatoNIndefinido((cursor.getInt(7)));
            cvliFato.setDsLogradouroFato(cursor.getString(8));
            cvliFato.setDsRuaFato(cursor.getString(9));
            cvliFato.setDsNRuaFato(cursor.getString(10));
            cvliFato.setDsReferenciaLocalFato(cursor.getString(11));
            cvliFato.setDsBairroFato(cursor.getString(12));
            cvliFato.setDsDistVilPovoFato(cursor.getString(13));
            cvliFato.setDsDescrDistVilPovoFato(cursor.getString(14));
            cvliFato.setDsMunicipioFato(cursor.getString(15));
            cvliFato.setDsEstadoFato(cursor.getString(16));
            cvliFato.setDsZonaFato(cursor.getString(17));
            cvliFato.setDsDetalhamentoLocal(cursor.getString(18));
            cvliFato.setDsOutroDetalhamento(cursor.getString(19));
            cvliFato.setCbkAfogamentoFato(cursor.getInt(20));
            cvliFato.setCbkAsfixiaFato(cursor.getInt(21));
            cvliFato.setCbkTropelamentoFato(cursor.getInt(22));
            cvliFato.setCbkDisparoArmaFato(cursor.getInt(23));
            cvliFato.setCbkEmpurraoAlturaFato(cursor.getInt(24));
            cvliFato.setCbkEvenenadoFato(cursor.getInt(25));
            cvliFato.setCbkEstanaduraFato(cursor.getInt(26));
            cvliFato.setCbkEstanaduraFato(cursor.getInt(27));
            cvliFato.setCbkEmpurraoSobVeiculoFato(cursor.getInt(28));
            cvliFato.setCbkGolpeFacaoFato(cursor.getInt(29));
            cvliFato.setCbkGolpeFacaFato(cursor.getInt(30));
            cvliFato.setCbkPauladaFato(cursor.getInt(31));
            cvliFato.setCbkOmissaoSocorroFato(cursor.getInt(32));
            cvliFato.setCbkQueimaduraFato(cursor.getInt(33));
            cvliFato.setCbkPedradaFato(cursor.getInt(34));
            cvliFato.setCbkPerfuracoesFato(cursor.getInt(35));
            cvliFato.setCbkIncendioFato(cursor.getInt(36));
            cvliFato.setCbkSocosPontapesFato(cursor.getInt(37));
            cvliFato.setCbkQueimarudasAcidoFato(cursor.getInt(38));
            cvliFato.setCbkNaoIdentificadoFato(cursor.getInt(39));
            cvliFato.setCbkOutrosNaoRelacionadoFato(cursor.getInt(40));
            cvliFato.setDsMotivacaoFato(cursor.getString(41));
            cvliFato.setCbkTortura(cursor.getInt(42));


        }
        return cvliFato;
    }

    public List<Cvli> retornaCVLISilc(int id){

        List<Cvli> cvlisSilc = new ArrayList<>();

            db = conexao.getReadableDatabase();

            Cursor cursor = db.query("cvlis", new String[]{"id", "dtDataSilc", "hsHorarioSilc", "dsLogradouroSilc", "dsRuaSilc", "dsNRuaSilc", "dsReferenciaLocalSilc", "dsBairroSilc",
                    "dsDistVilPovoSilc", "dsDescrDistVilPovoSilc", "dsMunicipioSilc", "dsEstadoSilc", "rbEquipePreservacaoLocalDefinido", "rbEquipePreservacaoLocalSilcNDefinido",
                    "rbEquipeLevantamentoSilcDefinido", "rbEquipeLevantamentoSilcNDefinido", "rbEquipePeritosSilcDefinido", "rbEquipePeritosSilcNDefinido",
                    "rbObjetosArrecadadosSilcDefinido", "rbObjetosArrecadadosSilcNDefinido"}, "id='" + id + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Cvli cvlisilc = new Cvli();

                cvlisilc.setId(cursor.getInt(0));
                cvlisilc.setDtDataSilc(cursor.getString(1));
                cvlisilc.setHsHorarioSilc(cursor.getString(2));
                cvlisilc.setDsLogradouroSilc(cursor.getString(3));
                cvlisilc.setDsRuaSilc(cursor.getString(4));
                cvlisilc.setDsNRuaSilc(cursor.getString(5));
                cvlisilc.setDsReferenciaLocalSilc(cursor.getString(6));
                cvlisilc.setDsBairroSilc(cursor.getString(7));
                cvlisilc.setDsDistVilPovoSilc(cursor.getString(8));
                cvlisilc.setDsDescrDistVilPovoSilc(cursor.getString(9));
                cvlisilc.setDsMunicipioSilc(cursor.getString(10));
                cvlisilc.setDsEstadoSilc(cursor.getString(11));
                cvlisilc.setCbkEquipePreservacaoLocalDefinido(cursor.getInt(12));
                cvlisilc.setCbkEquipePreservacaoLocalSilcNDefinido(cursor.getInt(13));
                cvlisilc.setCbkEquipeLevantamentoSilcDefinido(cursor.getInt(14));
                cvlisilc.setCbkEquipeLevantamentoSilcNDefinido(cursor.getInt(15));
                cvlisilc.setCbkEquipePeritosSilcDefinido(cursor.getInt(16));
                cvlisilc.setCbkEquipePeritosSilcNDefinido(cursor.getInt(17));
                cvlisilc.setCbkObjetosArrecadadosSilcDefinido(cursor.getInt(18));
                cvlisilc.setCbkObjetosArrecadadosSilcNDefinido(cursor.getInt(19));

                cvlisSilc.add(cvlisilc);
            }
            return cvlisSilc;
    }

    public Cvli retornaCVLISilcObj(int id){

        Cvli cvlisilc = new Cvli();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("cvlis", new String[]{"id", "dtDataSilc", "hsHorarioSilc", "dsLogradouroSilc", "dsRuaSilc", "dsNRuaSilc", "dsReferenciaLocalSilc", "dsBairroSilc",
                "dsDistVilPovoSilc", "dsDescrDistVilPovoSilc", "dsMunicipioSilc", "dsEstadoSilc", "rbEquipePreservacaoLocalDefinido", "rbEquipePreservacaoLocalSilcNDefinido",
                "rbEquipeLevantamentoSilcDefinido", "rbEquipeLevantamentoSilcNDefinido", "rbEquipePeritosSilcDefinido", "rbEquipePeritosSilcNDefinido",
                "rbObjetosArrecadadosSilcDefinido", "rbObjetosArrecadadosSilcNDefinido"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            cvlisilc.setId(cursor.getInt(0));
            cvlisilc.setDtDataSilc(cursor.getString(1));
            cvlisilc.setHsHorarioSilc(cursor.getString(2));
            cvlisilc.setDsLogradouroSilc(cursor.getString(3));
            cvlisilc.setDsRuaSilc(cursor.getString(4));
            cvlisilc.setDsNRuaSilc(cursor.getString(5));
            cvlisilc.setDsReferenciaLocalSilc(cursor.getString(6));
            cvlisilc.setDsBairroSilc(cursor.getString(7));
            cvlisilc.setDsDistVilPovoSilc(cursor.getString(8));
            cvlisilc.setDsDescrDistVilPovoSilc(cursor.getString(9));
            cvlisilc.setDsMunicipioSilc(cursor.getString(10));
            cvlisilc.setDsEstadoSilc(cursor.getString(11));
            cvlisilc.setCbkEquipePreservacaoLocalDefinido(cursor.getInt(12));
            cvlisilc.setCbkEquipePreservacaoLocalSilcNDefinido(cursor.getInt(13));
            cvlisilc.setCbkEquipeLevantamentoSilcDefinido(cursor.getInt(14));
            cvlisilc.setCbkEquipeLevantamentoSilcNDefinido(cursor.getInt(15));
            cvlisilc.setCbkEquipePeritosSilcDefinido(cursor.getInt(16));
            cvlisilc.setCbkEquipePeritosSilcNDefinido(cursor.getInt(17));
            cvlisilc.setCbkObjetosArrecadadosSilcDefinido(cursor.getInt(18));
            cvlisilc.setCbkObjetosArrecadadosSilcNDefinido(cursor.getInt(19));

        }
        return cvlisilc;
    }

    public List<Cvli> retornaCVLIProvidencias(int id){


        List<Cvli> cvlisprovidencias = new ArrayList<>();

            db = conexao.getReadableDatabase();

            Cursor cursor = db.query("cvlis", new String[]{"id", "dsNBO", "rbExpedidoGuiaPericial", "rbNExpedidoGuiaPericial", "dsNIP",
                    "cbkBuscaApreensao", "cbkPrisaoTemporaria", "cbkPrisaoPreventiva", "cbkQuebraSigilo", "cbMedidaProtetivadeUrgencia",
                    "dsDestinacaoInvestigacao","cbSemCautelares","dsProvidencia"}, "id='" + id + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Cvli cvliprovidencias = new Cvli();

                cvliprovidencias.setId(cursor.getInt(0));
                cvliprovidencias.setDsNBO(cursor.getString(1));
                cvliprovidencias.setCbkExpedidoGuiaPericial(cursor.getInt(2));
                cvliprovidencias.setCbkNExpedidoGuiaPericial(cursor.getInt(3));
                cvliprovidencias.setDsNIP(cursor.getString(4));
                cvliprovidencias.setCbkBuscaApreensao(cursor.getInt(5));
                cvliprovidencias.setCbkPrisaoTemporaria(cursor.getInt(6));
                cvliprovidencias.setCbkPrisaoPreventiva(cursor.getInt(7));
                cvliprovidencias.setCbkQuebraSigilo(cursor.getInt(8));
                cvliprovidencias.setCbkMedidasProtetivas(cursor.getInt(9));
                cvliprovidencias.setDsDestinacaoInvestigacao(cursor.getString(10));
                cvliprovidencias.setCbkSemCautelares(cursor.getInt(11));
                cvliprovidencias.setDsProvidencia(cursor.getString(12));

                cvlisprovidencias.add(cvliprovidencias);
            }
            return cvlisprovidencias;

    }

    public Cvli retornaCVLIProvidenciasObj(int id){

        Cvli cvliprovidencias = new Cvli();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("cvlis", new String[]{"id", "dsNBO", "rbExpedidoGuiaPericial", "rbNExpedidoGuiaPericial", "dsNIP",
                "cbkBuscaApreensao", "cbkPrisaoTemporaria", "cbkPrisaoPreventiva", "cbkQuebraSigilo", "cbMedidaProtetivadeUrgencia",
                "dsDestinacaoInvestigacao","cbSemCautelares","dsProvidencia"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            cvliprovidencias.setId(cursor.getInt(0));
            cvliprovidencias.setDsNBO(cursor.getString(1));
            cvliprovidencias.setCbkExpedidoGuiaPericial(cursor.getInt(2));
            cvliprovidencias.setCbkNExpedidoGuiaPericial(cursor.getInt(3));
            cvliprovidencias.setDsNIP(cursor.getString(4));
            cvliprovidencias.setCbkBuscaApreensao(cursor.getInt(5));
            cvliprovidencias.setCbkPrisaoTemporaria(cursor.getInt(6));
            cvliprovidencias.setCbkPrisaoPreventiva(cursor.getInt(7));
            cvliprovidencias.setCbkQuebraSigilo(cursor.getInt(8));
            cvliprovidencias.setCbkMedidasProtetivas(cursor.getInt(9));
            cvliprovidencias.setDsDestinacaoInvestigacao(cursor.getString(10));
            cvliprovidencias.setCbkSemCautelares(cursor.getInt(11));
            cvliprovidencias.setDsProvidencia(cursor.getString(12));

        }
        return cvliprovidencias;

    }

    public List<Cvli> retornaCVLIResumo(int id){


        List<Cvli> cvlisresumo = new ArrayList<>();

            db = conexao.getReadableDatabase();

            Cursor cursor = db.query("cvlis", new String[]{"id", "dsResumoFato", "dsTituloResumo","dsResFato"}, "id='" + id + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Cvli cvliresumo = new Cvli();

                cvliresumo.setId(cursor.getInt(0));
                cvliresumo.setDsResumoFato(cursor.getString(1));
                cvliresumo.setDsTituloFato(cursor.getString(2));
                cvliresumo.setDsResFato(cursor.getString(3));

                cvlisresumo.add(cvliresumo);
            }
            return cvlisresumo;
    }

    public Cvli retornaCVLIResumoObj(int id){

        Cvli cvliresumo = new Cvli();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("cvlis", new String[]{"id", "dsResumoFato", "dsTituloResumo","dsResFato"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            cvliresumo.setId(cursor.getInt(0));
            cvliresumo.setDsResumoFato(cursor.getString(1));
            cvliresumo.setDsTituloFato(cursor.getString(2));
            cvliresumo.setDsResFato(cursor.getString(3));

        }
        return cvliresumo;
    }

    public String retornarNomesVitimas(int id){

        db = conexao.getReadableDatabase();

        String listavitimas = "";

        Cursor cursor = db.query("cvlivitimas", new String[]{"cbkNaoIdentificadaVitima","dsNomeVitima"}, "fkCvli='" + id+ "'", null, null, null, null);

        while (cursor.moveToNext()) {
            int valor = cursor.getInt(0);
            if(valor == 1) {
                listavitimas += "Não identificada" + "; ";
            }else{
                listavitimas += cursor.getString(1) + "; ";
            }
        }
        return listavitimas;

    }

    public List<Cvli> retornaResFato(String datain, String datafin){

        List<Cvli> cvlis = new ArrayList<>();

        db = conexao.getReadableDatabase();
        //Cursor cursor = db.query("cvlis", new String[]{"id", "idUnico", "validarcvli", "dsResFato", "dtFato", "dsMunicipioFato"}, "dtFato>='"+datain+"' and dtFato<='"+datafin+"'" , null, null, null, null);

        String selectQuery = "SELECT id, idUnico, validarcvli, dsResFato, dtFato, dsMunicipioFato FROM cvlis where dtFato BETWEEN "+datain+" and "+datafin;
        Cursor cursor = db.rawQuery(selectQuery,null);

        while (cursor.moveToNext()) {
            Cvli cvli = new Cvli();

            cvli.setId(cursor.getInt(0));
            cvli.setIdUnico(cursor.getString(1));
            cvli.setValidarcvli(cursor.getInt(2));
            cvli.setDsResFato(cursor.getString(3));
            cvli.setDtFato(cursor.getString(4));
            cvli.setDsMunicipioFato(cursor.getString(5));

            cvlis.add(cvli);
        }
        return cvlis;
    }

    public long AtualizarResfato(Cvli cvli, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cvli.setValidarcvli(1);

        cv.put("validarcvli", cvli.getValidarcvli());
        cv.put("dsResFato", cvli.getDsResFato());

        return db.update("cvlis", cv, "id = '" + codigo + "'", null);
    }

    public String retornarDataFato(int id){

        db = conexao.getReadableDatabase();

        String datafato = "";

        Cursor cursor = db.query("cvlis", new String[]{"dtFato"}, "id='" + id+ "'", null, null, null, null);

        while (cursor.moveToNext()) {
            datafato = cursor.getString(0);
        }
        return datafato;
    }
}

