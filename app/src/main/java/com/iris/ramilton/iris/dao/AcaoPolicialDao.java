package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Acaopolicial;
import com.iris.ramilton.iris.modelo.Cvli;

import java.util.ArrayList;
import java.util.List;

public class AcaoPolicialDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public AcaoPolicialDao(Context context) {
        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarAcaoPolicial(Acaopolicial acaopolicial){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("EstatusAcaoPolicial", acaopolicial.getEstatusAcaoPolicial());
        cv.put("Controle", acaopolicial.getControle());
        cv.put("idUnico", acaopolicial.getIdUnico());
        cv.put("unidade_id", acaopolicial.getUnidade_id());
        cv.put("servidor_id", acaopolicial.getServidor_id());

        return db.insert("AcaoPolicial", null, cv);
    }

    public int retornarCodigoAcaoPolicial(int status) {

        db = conexao.getReadableDatabase();
        Cursor c = db.query("AcaoPolicial", new String[]{"id"}, "EstatusAcaoPolicial='" + status + "'", null, null, null, null);
        c.moveToLast();
        int valorcodigo = c.getInt(0);
        return valorcodigo;
    }

    public long excluirAcaoPolicial(int cod){
        db = conexao.getWritableDatabase();

        return db.delete("AcaoPolicial","id='"+cod+"'",null);
    }

    public int retornarCodigoAcaoPolicialSemParametro(){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("AcaoPolicial", new String[]{"id"}, null, null, null, null, null);
        c.moveToLast();
        int codigosemparametro = c.getInt(0);
        return codigosemparametro;
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("AcaoPolicial", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public String VerificarEmBrancoAcaoPolicial(int id){

        db = conexao.getReadableDatabase();

        String nomesembrancos = null;

        Cursor cursor = db.query("AcaoPolicial", new String[]{"dtAcaoPolicial", "hsAcaoPolicial","LocalAcaoPolicial",
                "NBuscaJudiciaisAcaoPolicial", "dsCrimePrincipalAcaoPolicial","dstituloResumo","dsResumo","dsUnidadeResponsavelAcaoPolicial"}, "id='"+id+"'", null, null, null, null);

        while (cursor.moveToNext()) {
            Acaopolicial acaopolicial = new Acaopolicial();

            acaopolicial.setDtAcaoPolicial(cursor.getString(0));
            if(acaopolicial.getDtAcaoPolicial() == null || acaopolicial.getDtAcaoPolicial().equals("")){
                nomesembrancos = nomesembrancos + "Data da Ação Policial, ";
            }
            acaopolicial.setHsAcaoPolicial(cursor.getString(1));
            if(acaopolicial.getHsAcaoPolicial() == null || acaopolicial.getHsAcaoPolicial().equals("")){
                nomesembrancos = nomesembrancos + "Hora da Ação Policial, ";
            }
            acaopolicial.setLocalAcaoPolicial(cursor.getString(2));
            if(acaopolicial.getLocalAcaoPolicial() == null || acaopolicial.getLocalAcaoPolicial().equals("")){
                nomesembrancos = nomesembrancos + "Local da Ação Policial, ";
            }
            acaopolicial.setNBuscaJudiciaisAcaoPolicial(cursor.getInt(3));
            if(acaopolicial.getNBuscaJudiciaisAcaoPolicial() == 0 ){
                nomesembrancos = nomesembrancos + "Número de Busca Judiciais Cumpridos, ";
            }
            acaopolicial.setDsCrimePrincipalAcaoPolicial(cursor.getString(4));
            if(acaopolicial.getDsCrimePrincipalAcaoPolicial() == null || acaopolicial.getDsCrimePrincipalAcaoPolicial().equals("")){
                nomesembrancos = nomesembrancos + "Descriçao Crime Objeto da Ação, ";
            }

            acaopolicial.setDstituloResumo(cursor.getString(5));
            if(acaopolicial.getDstituloResumo() == null || acaopolicial.getDstituloResumo().equals("")){
                nomesembrancos = nomesembrancos + "Titulo do Resumo da Ação Policial, ";
            }

            acaopolicial.setDsResumo(cursor.getString(6));
            if(acaopolicial.getDsResumo() == null || acaopolicial.getDsResumo().equals("")){
                nomesembrancos = nomesembrancos + "Resumo da Ação Policial, ";
            }
            acaopolicial.setDsUnidadeResponsavelAcaoPolicial(cursor.getString(7));
            if(acaopolicial.getDsUnidadeResponsavelAcaoPolicial() == null || acaopolicial.getDsUnidadeResponsavelAcaoPolicial().equals("")){
                nomesembrancos = nomesembrancos + "Unidade Responsável, ";
            }

        }
        return nomesembrancos;

    }

    public int retornarCodId(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("AcaoPolicial", new String[]{"id"}, "idUnico= '"+idunico+"'", null, null, null, null);
        c.moveToLast();
        int codigocontrole = c.getInt(0);
        return codigocontrole;
    }

    public int retornarCodigoControle(int id){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("AcaoPolicial", new String[]{"Controle"}, "id= '"+id+"'", null, null, null, null);
        c.moveToLast();
        int codigocontrole = c.getInt(0);
        return codigocontrole;
    }

    public int retornarCodigoStatusAcaoPolicial(int id){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("AcaoPolicial", new String[]{"EstatusAcaoPolicial"}, "id='" + id + "'", null, null, null, null);
        c.moveToFirst();
        int valorcvlistatus = c.getInt(0);
        return valorcvlistatus;

    }

    public void AtualizarControleAcaoPolicial(Acaopolicial acaopolicial, int id, int controle){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        acaopolicial.setId(id);
        acaopolicial.setControle(controle);

        cv.put("Controle", acaopolicial.getControle());

        db.update("AcaoPolicial", cv, "id = ?", new String[]{String.valueOf(acaopolicial.getId())});

    }

    public long validarAcaoPolicial(Acaopolicial acaopolicial, int codigo){
        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        String tipo =RetornaClassificacaoAcaoPolicial(codigo);

        cv.put("validaracaopolicial", acaopolicial.getValidaracaopolicial());
        cv.put("dsClassificacaoAcaoPolicial", tipo);

        return db.update("AcaoPolicial", cv, "id = '"+codigo+"'", null);
    }

    public long cadastrarDaAcaoPolicial(Acaopolicial acaopolicial1, int codigo){
        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigocvlisempsramentro = retornarCodigoAcaoPolicialSemParametro();

        int codigoStatus = retornarCodigoStatusAcaoPolicial(codigocvlisempsramentro);

        if (codigoStatus == 0) {

            acaopolicial1.setEstatusAcaoPolicial(1);
            acaopolicial1.setControle(2);
            acaopolicial1.setValidaracaopolicial(0);

            acaopolicial1.setId(codigo);

            cv.put("dsClassificacaoAcaoPolicial", acaopolicial1.getDsClassificacaoAcaoPolicial());
            cv.put("dsNomeOperacaoPolicial", acaopolicial1.getDsNomeOperacaoPolicial());
            cv.put("cbkOperacaoInominada", acaopolicial1.getCbkOperacaoInominada());
            cv.put("dtAcaoPolicial", acaopolicial1.getDtAcaoPolicial());
            cv.put("hsAcaoPolicial", acaopolicial1.getHsAcaoPolicial());
            cv.put("LocalAcaoPolicial", acaopolicial1.getLocalAcaoPolicial());
            cv.put("NBuscaJudiciaisAcaoPolicial", acaopolicial1.getNBuscaJudiciaisAcaoPolicial());
            cv.put("dsCrimePrincipalAcaoPolicial", acaopolicial1.getDsCrimePrincipalAcaoPolicial());
            cv.put("cbkMedidasCautelares", acaopolicial1.getCbkMedidasCautelares());
            cv.put("cbkInvestigacoesOrdinarias", acaopolicial1.getCbkInvestigacoesOrdinarias());
            cv.put("cbkRegistroPolicial", acaopolicial1.getCbkRegistroPolicial());
            cv.put("cbkAbordagemRotina", acaopolicial1.getCbkAbordagemRotina());
            cv.put("cbkColaborador", acaopolicial1.getCbkColaborador());
            cv.put("cbkDenuncia", acaopolicial1.getCbkDenuncia());
            cv.put("cbkOutros", acaopolicial1.getCbkOutros());
            cv.put("Controle", acaopolicial1.getControle());
            cv.put("dstituloResumo", acaopolicial1.getDstituloResumo());
            cv.put("dsResumo", acaopolicial1.getDsResumo());
            cv.put("dsNIPAcaoPolicial", acaopolicial1.getDsNIPAcaoPolicial());
            cv.put("dsNomeDelegadoAcaoPolicial", acaopolicial1.getDsNomeDelegadoAcaoPolicial());
            cv.put("dsMunicipioReferenciaAcaoPolicial", acaopolicial1.getDsMunicipioReferenciaAcaoPolicial());
            cv.put("cbkPresoAcaoPolicial", acaopolicial1.getCbkPresoAcaoPolicial());
            cv.put("cbkBensAprendidos", acaopolicial1.getCbkBensAprendidos());
            cv.put("dsSubitulo1", acaopolicial1.getDsSubtitulo1());
            cv.put("dsTexto1", acaopolicial1.getDsTexto1());
            cv.put("dsSubitulo2", acaopolicial1.getDsSubtitulo2());
            cv.put("dsTexto2", acaopolicial1.getDsTexto2());
            cv.put("dsSubitulo3", acaopolicial1.getDsSubtitulo3());
            cv.put("dsTexto3", acaopolicial1.getDsTexto3());
            cv.put("dsSubitulo4", acaopolicial1.getDsSubtitulo4());
            cv.put("dsTexto4", acaopolicial1.getDsTexto4());
            cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
            cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

            return db.update("AcaoPolicial", cv, "id = ?", new String[]{String.valueOf(acaopolicial1.getId())});
        } else {

            acaopolicial1.setId(codigocvlisempsramentro);
            acaopolicial1.setEstatusAcaoPolicial(1);
            acaopolicial1.setControle(2);
            acaopolicial1.setValidaracaopolicial(0);

            cv.put("dsClassificacaoAcaoPolicial", acaopolicial1.getDsClassificacaoAcaoPolicial());
            cv.put("dsNomeOperacaoPolicial", acaopolicial1.getDsNomeOperacaoPolicial());
            cv.put("cbkOperacaoInominada", acaopolicial1.getCbkOperacaoInominada());
            cv.put("dtAcaoPolicial", acaopolicial1.getDtAcaoPolicial());
            cv.put("hsAcaoPolicial", acaopolicial1.getHsAcaoPolicial());
            cv.put("LocalAcaoPolicial", acaopolicial1.getLocalAcaoPolicial());
            cv.put("NBuscaJudiciaisAcaoPolicial", acaopolicial1.getNBuscaJudiciaisAcaoPolicial());
            cv.put("dsCrimePrincipalAcaoPolicial", acaopolicial1.getDsCrimePrincipalAcaoPolicial());
            cv.put("cbkMedidasCautelares", acaopolicial1.getCbkMedidasCautelares());
            cv.put("cbkInvestigacoesOrdinarias", acaopolicial1.getCbkInvestigacoesOrdinarias());
            cv.put("cbkRegistroPolicial", acaopolicial1.getCbkRegistroPolicial());
            cv.put("cbkAbordagemRotina", acaopolicial1.getCbkAbordagemRotina());
            cv.put("cbkColaborador", acaopolicial1.getCbkColaborador());
            cv.put("cbkDenuncia", acaopolicial1.getCbkDenuncia());
            cv.put("cbkOutros", acaopolicial1.getCbkOutros());
            cv.put("Controle", acaopolicial1.getControle());
            cv.put("dstituloResumo", acaopolicial1.getDstituloResumo());
            cv.put("dsResumo", acaopolicial1.getDsResumo());
            cv.put("dsNIPAcaoPolicial", acaopolicial1.getDsNIPAcaoPolicial());
            cv.put("dsNomeDelegadoAcaoPolicial", acaopolicial1.getDsNomeDelegadoAcaoPolicial());
            cv.put("dsMunicipioReferenciaAcaoPolicial", acaopolicial1.getDsMunicipioReferenciaAcaoPolicial());
            cv.put("cbkPresoAcaoPolicial", acaopolicial1.getCbkPresoAcaoPolicial());
            cv.put("cbkBensAprendidos", acaopolicial1.getCbkBensAprendidos());
            cv.put("dsSubitulo1", acaopolicial1.getDsSubtitulo1());
            cv.put("dsTexto1", acaopolicial1.getDsTexto1());
            cv.put("dsSubitulo2", acaopolicial1.getDsSubtitulo2());
            cv.put("dsTexto2", acaopolicial1.getDsTexto2());
            cv.put("dsSubitulo3", acaopolicial1.getDsSubtitulo3());
            cv.put("dsTexto3", acaopolicial1.getDsTexto3());
            cv.put("dsSubitulo4", acaopolicial1.getDsSubtitulo4());
            cv.put("dsTexto4", acaopolicial1.getDsTexto4());
            cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
            cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

            return db.update("AcaoPolicial", cv, "id = ?", new String[]{String.valueOf(acaopolicial1.getId())});
        }
    }

    public String RetornaClassificacaoAcaoPolicial(int codigo){

        db = conexao.getWritableDatabase();

        String tipo = null;


        Cursor cursor = db.query("AcaoPolicial", new String[]{"NBuscaJudiciaisAcaoPolicial", "cbkMedidasCautelares", "cbkInvestigacoesOrdinarias","cbkRegistroPolicial",
                "cbkAbordagemRotina","cbkColaborador","cbkDenuncia","cbkOutros","NEquipeLocaisAcaoPolicial","NEquipeUnidadeCoorpinAcaoPolicial","NEquipeDetCoorCoorpinAcaoPolicial"}, "id='"+codigo+"'", null, null, null, null);

        while (cursor.moveToNext()) {
            Acaopolicial acaopolicial = new Acaopolicial();

            acaopolicial.setNBuscaJudiciaisAcaoPolicial(cursor.getInt(0));
            acaopolicial.setCbkMedidasCautelares(cursor.getInt(1));
            acaopolicial.setCbkInvestigacoesOrdinarias(cursor.getInt(2));
            acaopolicial.setCbkRegistroPolicial(cursor.getInt(3));
            acaopolicial.setCbkAbordagemRotina(cursor.getInt(4));
            acaopolicial.setCbkColaborador(cursor.getInt(5));
            acaopolicial.setCbkDenuncia(cursor.getInt(6));
            acaopolicial.setCbkOutros(cursor.getInt(7));
            acaopolicial.setNEquipeLocaisAcaoPolicial(cursor.getInt(8));
            acaopolicial.setNEquipeUnidadeCoorpinAcaoPolicial(cursor.getInt(9));
            acaopolicial.setNEquipeDetCoorCoorpinAcaoPolicial(cursor.getInt(10));

            if(acaopolicial.getNBuscaJudiciaisAcaoPolicial() >= 5 && acaopolicial.getNEquipeDetCoorCoorpinAcaoPolicial() >= 1 && acaopolicial.getCbkMedidasCautelares() == 1){
                tipo = "N1";
            }
            if(acaopolicial.getNBuscaJudiciaisAcaoPolicial() < 5 && acaopolicial.getNEquipeDetCoorCoorpinAcaoPolicial() >= 1 && acaopolicial.getCbkMedidasCautelares() == 1){
                tipo = "N2";
            }
            if(acaopolicial.getNEquipeDetCoorCoorpinAcaoPolicial() == 0 && acaopolicial.getCbkMedidasCautelares() == 1 && acaopolicial.getNEquipeLocaisAcaoPolicial() == 1){
                tipo = "N3";
            }
            if(acaopolicial.getNEquipeDetCoorCoorpinAcaoPolicial() == 0 && acaopolicial.getCbkMedidasCautelares() == 0 && acaopolicial.getNEquipeLocaisAcaoPolicial() == 1){
                tipo = "AP";
            }

        }

        return tipo;
    }

    public long cadastrarAcaoPolicialDaEquipe(Acaopolicial acaopolicial1, int codigo){
        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigocvlisempsramentro = retornarCodigoAcaoPolicialSemParametro();

        int codigoStatus = retornarCodigoStatusAcaoPolicial(codigocvlisempsramentro);

        if (codigoStatus == 0) {

            acaopolicial1.setEstatusAcaoPolicial(1);
            acaopolicial1.setControle(2);
            acaopolicial1.setValidaracaopolicial(0);

            acaopolicial1.setId(codigo);

            cv.put("dsUnidadeResponsavelAcaoPolicial", acaopolicial1.getDsUnidadeResponsavelAcaoPolicial());
            cv.put("NEquipeLocaisAcaoPolicial", acaopolicial1.getNEquipeLocaisAcaoPolicial());
            cv.put("NEquipeUnidadeCoorpinAcaoPolicial", acaopolicial1.getNEquipeUnidadeCoorpinAcaoPolicial());
            cv.put("NEquipeDetCoorCoorpinAcaoPolicial", acaopolicial1.getNEquipeDetCoorCoorpinAcaoPolicial());
            cv.put("dsOutraInstituicaoAcaoPolicial", acaopolicial1.getDsOutraInstituicaoAcaoPolicial());
            cv.put("NOutraInstituicaoAcaoPolicial", acaopolicial1.getEdtNEquipesGeraisAcaoPolicial());
            cv.put("dsNomeUnidadeEspAcaoPolicial", acaopolicial1.getDsNomeUnidadeEspAcaoPolicial());
            cv.put("NEquipeEspAcaoPolicial", acaopolicial1.getNEquipeEspAcaoPolicial());
            cv.put("dsNomeOutraInstituicaoAcaoPolicial", acaopolicial1.getDsNomeOutraInstituicaoAcaoPolicial());
            cv.put("NEnvolvidosOutrasInstituicaoAcaoPolicial", acaopolicial1.getNEnvolvidosOutrasInstituicaoAcaoPolicial());
            cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
            cv.put("Controle", acaopolicial1.getControle());
            cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

            return db.update("AcaoPolicial", cv, "id = ?", new String[]{String.valueOf(acaopolicial1.getId())});
        } else {

            acaopolicial1.setId(codigocvlisempsramentro);
            acaopolicial1.setEstatusAcaoPolicial(1);
            acaopolicial1.setControle(2);
            acaopolicial1.setValidaracaopolicial(0);

            cv.put("dsUnidadeResponsavelAcaoPolicial", acaopolicial1.getDsUnidadeResponsavelAcaoPolicial());
            cv.put("NEquipeLocaisAcaoPolicial", acaopolicial1.getNEquipeLocaisAcaoPolicial());
            cv.put("NEquipeUnidadeCoorpinAcaoPolicial", acaopolicial1.getNEquipeUnidadeCoorpinAcaoPolicial());
            cv.put("NEquipeDetCoorCoorpinAcaoPolicial", acaopolicial1.getNEquipeDetCoorCoorpinAcaoPolicial());
            cv.put("dsOutraInstituicaoAcaoPolicial", acaopolicial1.getDsOutraInstituicaoAcaoPolicial());
            cv.put("NOutraInstituicaoAcaoPolicial", acaopolicial1.getEdtNEquipesGeraisAcaoPolicial());
            cv.put("dsNomeUnidadeEspAcaoPolicial", acaopolicial1.getDsNomeUnidadeEspAcaoPolicial());
            cv.put("NEquipeEspAcaoPolicial", acaopolicial1.getNEquipeEspAcaoPolicial());
            cv.put("dsNomeOutraInstituicaoAcaoPolicial", acaopolicial1.getDsNomeOutraInstituicaoAcaoPolicial());
            cv.put("NEnvolvidosOutrasInstituicaoAcaoPolicial", acaopolicial1.getNEnvolvidosOutrasInstituicaoAcaoPolicial());
            cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
            cv.put("Controle", acaopolicial1.getControle());
            cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

            return db.update("AcaoPolicial", cv, "id = ?", new String[]{String.valueOf(acaopolicial1.getId())});
        }
    }

    public long cadastrarAcaoPolicialDoProcedimento(Acaopolicial acaopolicial1, int codigo){
        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigocvlisempsramentro = retornarCodigoAcaoPolicialSemParametro();

        int codigoStatus = retornarCodigoStatusAcaoPolicial(codigocvlisempsramentro);

        if (codigoStatus == 0) {

            acaopolicial1.setEstatusAcaoPolicial(1);
            acaopolicial1.setControle(2);
            acaopolicial1.setValidaracaopolicial(0);

            acaopolicial1.setId(codigo);

            cv.put("dsNIPAcaoPolicial", acaopolicial1.getDsNIPAcaoPolicial());
            cv.put("dsNomeDelegadoAcaoPolicial", acaopolicial1.getDsNomeDelegadoAcaoPolicial());
            cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
            cv.put("Controle", acaopolicial1.getControle());
            cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

            return db.update("AcaoPolicial", cv, "id = ?", new String[]{String.valueOf(acaopolicial1.getId())});
        } else {

            acaopolicial1.setId(codigocvlisempsramentro);
            acaopolicial1.setEstatusAcaoPolicial(1);
            acaopolicial1.setControle(2);
            acaopolicial1.setValidaracaopolicial(0);

            cv.put("dsNIPAcaoPolicial", acaopolicial1.getDsNIPAcaoPolicial());
            cv.put("dsNomeDelegadoAcaoPolicial", acaopolicial1.getDsNomeDelegadoAcaoPolicial());
            cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
            cv.put("Controle", acaopolicial1.getControle());
            cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

            return db.update("AcaoPolicial", cv, "id = ?", new String[]{String.valueOf(acaopolicial1.getId())});
        }
    }

    public long CadastrarAcaoPolicialWEB(Acaopolicial acaopolicial1){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("dsClassificacaoAcaoPolicial", acaopolicial1.getDsClassificacaoAcaoPolicial());
        cv.put("dsNomeOperacaoPolicial", acaopolicial1.getDsNomeOperacaoPolicial());
        cv.put("cbkOperacaoInominada", acaopolicial1.getCbkOperacaoInominada());
        cv.put("dtAcaoPolicial", acaopolicial1.getDtAcaoPolicial());
        cv.put("hsAcaoPolicial", acaopolicial1.getHsAcaoPolicial());
        cv.put("LocalAcaoPolicial", acaopolicial1.getLocalAcaoPolicial());
        cv.put("NBuscaJudiciaisAcaoPolicial", acaopolicial1.getNBuscaJudiciaisAcaoPolicial());
        cv.put("dsCrimePrincipalAcaoPolicial", acaopolicial1.getDsCrimePrincipalAcaoPolicial());
        cv.put("cbkMedidasCautelares", acaopolicial1.getCbkMedidasCautelares());
        cv.put("cbkInvestigacoesOrdinarias", acaopolicial1.getCbkInvestigacoesOrdinarias());
        cv.put("cbkRegistroPolicial", acaopolicial1.getCbkRegistroPolicial());
        cv.put("cbkAbordagemRotina", acaopolicial1.getCbkAbordagemRotina());
        cv.put("cbkColaborador", acaopolicial1.getCbkColaborador());
        cv.put("cbkDenuncia", acaopolicial1.getCbkDenuncia());
        cv.put("cbkOutros", acaopolicial1.getCbkOutros());
        cv.put("Controle", acaopolicial1.getControle());
        cv.put("idUnico", acaopolicial1.getIdUnico());
        cv.put("dstituloResumo", acaopolicial1.getDstituloResumo());
        cv.put("dsResumo", acaopolicial1.getDsResumo());
        cv.put("dsNIPAcaoPolicial", acaopolicial1.getDsNIPAcaoPolicial());
        cv.put("dsNomeDelegadoAcaoPolicial", acaopolicial1.getDsNomeDelegadoAcaoPolicial());
        cv.put("dsMunicipioReferenciaAcaoPolicial", acaopolicial1.getDsMunicipioReferenciaAcaoPolicial());
        cv.put("cbkPresoAcaoPolicial", acaopolicial1.getCbkPresoAcaoPolicial());
        cv.put("cbkBensAprendidos", acaopolicial1.getCbkBensAprendidos());
        cv.put("dsSubitulo1", acaopolicial1.getDsSubtitulo1());
        cv.put("dsTexto1", acaopolicial1.getDsTexto1());
        cv.put("dsSubitulo2", acaopolicial1.getDsSubtitulo2());
        cv.put("dsTexto2", acaopolicial1.getDsTexto2());
        cv.put("dsSubitulo3", acaopolicial1.getDsSubtitulo3());
        cv.put("dsTexto3", acaopolicial1.getDsTexto3());
        cv.put("dsSubitulo4", acaopolicial1.getDsSubtitulo4());
        cv.put("dsTexto4", acaopolicial1.getDsTexto4());
        cv.put("servidor_id", acaopolicial1.getServidor_id());
        cv.put("unidade_id", acaopolicial1.getUnidade_id());
        cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
        cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

        return db.insert("AcaoPolicial", null, cv);
    }

    public long AtualizarAcaoPolicialWEB(Acaopolicial acaopolicial1, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("dsClassificacaoAcaoPolicial", acaopolicial1.getDsClassificacaoAcaoPolicial());
        cv.put("dsNomeOperacaoPolicial", acaopolicial1.getDsNomeOperacaoPolicial());
        cv.put("cbkOperacaoInominada", acaopolicial1.getCbkOperacaoInominada());
        cv.put("dtAcaoPolicial", acaopolicial1.getDtAcaoPolicial());
        cv.put("hsAcaoPolicial", acaopolicial1.getHsAcaoPolicial());
        cv.put("LocalAcaoPolicial", acaopolicial1.getLocalAcaoPolicial());
        cv.put("NBuscaJudiciaisAcaoPolicial", acaopolicial1.getNBuscaJudiciaisAcaoPolicial());
        cv.put("dsCrimePrincipalAcaoPolicial", acaopolicial1.getDsCrimePrincipalAcaoPolicial());
        cv.put("cbkMedidasCautelares", acaopolicial1.getCbkMedidasCautelares());
        cv.put("cbkInvestigacoesOrdinarias", acaopolicial1.getCbkInvestigacoesOrdinarias());
        cv.put("cbkRegistroPolicial", acaopolicial1.getCbkRegistroPolicial());
        cv.put("cbkAbordagemRotina", acaopolicial1.getCbkAbordagemRotina());
        cv.put("cbkColaborador", acaopolicial1.getCbkColaborador());
        cv.put("cbkDenuncia", acaopolicial1.getCbkDenuncia());
        cv.put("cbkOutros", acaopolicial1.getCbkOutros());
        cv.put("Controle", acaopolicial1.getControle());
        cv.put("idUnico", acaopolicial1.getIdUnico());
        cv.put("dstituloResumo", acaopolicial1.getDstituloResumo());
        cv.put("dsResumo", acaopolicial1.getDsResumo());
        cv.put("dsNIPAcaoPolicial", acaopolicial1.getDsNIPAcaoPolicial());
        cv.put("dsNomeDelegadoAcaoPolicial", acaopolicial1.getDsNomeDelegadoAcaoPolicial());
        cv.put("dsMunicipioReferenciaAcaoPolicial", acaopolicial1.getDsMunicipioReferenciaAcaoPolicial());
        cv.put("cbkPresoAcaoPolicial", acaopolicial1.getCbkPresoAcaoPolicial());
        cv.put("cbkBensAprendidos", acaopolicial1.getCbkBensAprendidos());
        cv.put("dsSubitulo1", acaopolicial1.getDsSubtitulo1());
        cv.put("dsTexto1", acaopolicial1.getDsTexto1());
        cv.put("dsSubitulo2", acaopolicial1.getDsSubtitulo2());
        cv.put("dsTexto2", acaopolicial1.getDsTexto2());
        cv.put("dsSubitulo3", acaopolicial1.getDsSubtitulo3());
        cv.put("dsTexto3", acaopolicial1.getDsTexto3());
        cv.put("dsSubitulo4", acaopolicial1.getDsSubtitulo4());
        cv.put("dsTexto4", acaopolicial1.getDsTexto4());
        cv.put("servidor_id", acaopolicial1.getServidor_id());
        cv.put("unidade_id", acaopolicial1.getUnidade_id());
        cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
        cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

        return db.update("AcaoPolicial", cv, "idUnico = '" + codigo + "'", null);
    }

    public long AtualizarAcaoPolicialDaAcao(Acaopolicial acaopolicial1, int id, int controle) {

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        acaopolicial1.setEstatusAcaoPolicial(1);
        acaopolicial1.setControle(controle);
        acaopolicial1.setValidaracaopolicial(0);

        cv.put("dsClassificacaoAcaoPolicial", acaopolicial1.getDsClassificacaoAcaoPolicial());
        cv.put("dsNomeOperacaoPolicial", acaopolicial1.getDsNomeOperacaoPolicial());
        cv.put("cbkOperacaoInominada", acaopolicial1.getCbkOperacaoInominada());
        cv.put("dtAcaoPolicial", acaopolicial1.getDtAcaoPolicial());
        cv.put("hsAcaoPolicial", acaopolicial1.getHsAcaoPolicial());
        cv.put("LocalAcaoPolicial", acaopolicial1.getLocalAcaoPolicial());
        cv.put("NBuscaJudiciaisAcaoPolicial", acaopolicial1.getNBuscaJudiciaisAcaoPolicial());
        cv.put("dsCrimePrincipalAcaoPolicial", acaopolicial1.getDsCrimePrincipalAcaoPolicial());
        cv.put("cbkMedidasCautelares", acaopolicial1.getCbkMedidasCautelares());
        cv.put("cbkInvestigacoesOrdinarias", acaopolicial1.getCbkInvestigacoesOrdinarias());
        cv.put("cbkRegistroPolicial", acaopolicial1.getCbkRegistroPolicial());
        cv.put("cbkAbordagemRotina", acaopolicial1.getCbkAbordagemRotina());
        cv.put("cbkColaborador", acaopolicial1.getCbkColaborador());
        cv.put("cbkDenuncia", acaopolicial1.getCbkDenuncia());
        cv.put("cbkOutros", acaopolicial1.getCbkOutros());
        cv.put("dstituloResumo", acaopolicial1.getDstituloResumo());
        cv.put("dsResumo", acaopolicial1.getDsResumo());
        cv.put("dsNIPAcaoPolicial", acaopolicial1.getDsNIPAcaoPolicial());
        cv.put("dsNomeDelegadoAcaoPolicial", acaopolicial1.getDsNomeDelegadoAcaoPolicial());
        cv.put("dsMunicipioReferenciaAcaoPolicial", acaopolicial1.getDsMunicipioReferenciaAcaoPolicial());
        cv.put("cbkPresoAcaoPolicial", acaopolicial1.getCbkPresoAcaoPolicial());
        cv.put("cbkBensAprendidos", acaopolicial1.getCbkBensAprendidos());
        cv.put("dsSubitulo1", acaopolicial1.getDsSubtitulo1());
        cv.put("dsTexto1", acaopolicial1.getDsTexto1());
        cv.put("dsSubitulo2", acaopolicial1.getDsSubtitulo2());
        cv.put("dsTexto2", acaopolicial1.getDsTexto2());
        cv.put("dsSubitulo3", acaopolicial1.getDsSubtitulo3());
        cv.put("dsTexto3", acaopolicial1.getDsTexto3());
        cv.put("dsSubitulo4", acaopolicial1.getDsSubtitulo4());
        cv.put("dsTexto4", acaopolicial1.getDsTexto4());
        cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
        cv.put("Controle", acaopolicial1.getControle());
        cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

        return db.update("AcaoPolicial", cv, "id = '" + id + "'", null);
    }

    public List<Acaopolicial> retornaAcaoPolicial(){

        List<Acaopolicial> acaopolicials = new ArrayList<>();

        db = conexao.getReadableDatabase();
        Cursor cursor = db.query("AcaoPolicial", new String[]{"id", "dsClassificacaoAcaoPolicial", "dsNomeOperacaoPolicial", "dtAcaoPolicial",
                "hsAcaoPolicial", "LocalAcaoPolicial","NBuscaJudiciaisAcaoPolicial", "dsCrimePrincipalAcaoPolicial", "cbkRegistroPolicial", "cbkAbordagemRotina", "cbkColaborador",
                "cbkDenuncia", "cbkOutros", "EstatusAcaoPolicial", "Controle", "servidor_id", "unidade_id",
                "validaracaopolicial", "idUnico", "dstituloResumo", "dsResumo", "dsNIPAcaoPolicial", "dsNomeDelegadoAcaoPolicial", "dsMunicipioReferenciaAcaoPolicial, " +
                "cbkPresoAcaoPolicial", "cbkBensAprendidos", "dsSubitulo1", "dsTexto1", "dsSubitulo2", "dsTexto2", "dsSubitulo3", "dsTexto3", "dsSubitulo4", "dsTexto4", "cbkOperacaoInominada"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Acaopolicial acaopolicial = new Acaopolicial();

            acaopolicial.setId(cursor.getInt(0));
            acaopolicial.setDsClassificacaoAcaoPolicial(cursor.getString(1));
            acaopolicial.setDsNomeOperacaoPolicial(cursor.getString(2));
            acaopolicial.setDtAcaoPolicial(cursor.getString(3));
            acaopolicial.setHsAcaoPolicial(cursor.getString(4));
            acaopolicial.setLocalAcaoPolicial(cursor.getString(5));
            acaopolicial.setNBuscaJudiciaisAcaoPolicial((cursor.getInt(6)));
            acaopolicial.setDsCrimePrincipalAcaoPolicial((cursor.getString(7)));
            acaopolicial.setCbkRegistroPolicial(cursor.getInt(8));
            acaopolicial.setCbkAbordagemRotina(cursor.getInt(9));
            acaopolicial.setCbkColaborador(cursor.getInt(10));
            acaopolicial.setCbkDenuncia(cursor.getInt(11));
            acaopolicial.setCbkOutros(cursor.getInt(12));
            acaopolicial.setEstatusAcaoPolicial(cursor.getInt(13));
            acaopolicial.setControle(cursor.getInt(14));
            acaopolicial.setServidor_id(cursor.getInt(15));
            acaopolicial.setUnidade_id(cursor.getInt(16));
            acaopolicial.setValidaracaopolicial(cursor.getInt(17));
            acaopolicial.setIdUnico(cursor.getString(18));
            acaopolicial.setDstituloResumo(cursor.getString(19));
            acaopolicial.setDsResumo(cursor.getString(20));
            acaopolicial.setDsNIPAcaoPolicial(cursor.getString(21));
            acaopolicial.setDsNomeDelegadoAcaoPolicial(cursor.getString(22));
            acaopolicial.setDsMunicipioReferenciaAcaoPolicial(cursor.getString(23));
            acaopolicial.setCbkPresoAcaoPolicial(cursor.getInt(24));
            acaopolicial.setCbkBensAprendidos(cursor.getInt(25));
            acaopolicial.setDsSubtitulo1(cursor.getString(26));
            acaopolicial.setDsTexto1(cursor.getString(27));
            acaopolicial.setDsSubtitulo2(cursor.getString(28));
            acaopolicial.setDsTexto2(cursor.getString(29));
            acaopolicial.setDsSubtitulo3(cursor.getString(30));
            acaopolicial.setDsTexto3(cursor.getString(31));
            acaopolicial.setDsSubtitulo4(cursor.getString(32));
            acaopolicial.setDsTexto4(cursor.getString(33));
            acaopolicial.setCbkOperacaoInominada(cursor.getInt(34));
            acaopolicials.add(acaopolicial);
        }
        return acaopolicials;
    }

    public List<Acaopolicial> retornaAcaoPolicialDaAcao(int id){

        List<Acaopolicial> acaopolicialdacao = new ArrayList<>();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("AcaoPolicial", new String[]{"id", "dsClassificacaoAcaoPolicial", "dsNomeOperacaoPolicial", "dtAcaoPolicial", "hsAcaoPolicial", "LocalAcaoPolicial", "NBuscaJudiciaisAcaoPolicial",
                "dsCrimePrincipalAcaoPolicial", "cbkMedidasCautelares", "cbkInvestigacoesOrdinarias", "cbkRegistroPolicial", "cbkAbordagemRotina", "cbkColaborador",
                "cbkDenuncia", "cbkOutros", "dstituloResumo", "dsResumo", "dsNIPAcaoPolicial", "dsNomeDelegadoAcaoPolicial", "dsMunicipioReferenciaAcaoPolicial, " +
                "cbkPresoAcaoPolicial", "cbkBensAprendidos", "dsSubitulo1", "dsTexto1", "dsSubitulo2", "dsTexto2", "dsSubitulo3", "dsTexto3", "dsSubitulo4", "dsTexto4", "cbkOperacaoInominada"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Acaopolicial acaopolicialdaacaopolicial = new Acaopolicial();

            acaopolicialdaacaopolicial.setId(cursor.getInt(0));
            acaopolicialdaacaopolicial.setDsClassificacaoAcaoPolicial(cursor.getString(1));
            acaopolicialdaacaopolicial.setDsNomeOperacaoPolicial(cursor.getString(2));
            acaopolicialdaacaopolicial.setDtAcaoPolicial(cursor.getString(3));
            acaopolicialdaacaopolicial.setHsAcaoPolicial(cursor.getString(4));
            acaopolicialdaacaopolicial.setLocalAcaoPolicial(cursor.getString(5));
            acaopolicialdaacaopolicial.setNBuscaJudiciaisAcaoPolicial(cursor.getInt(6));
            acaopolicialdaacaopolicial.setDsCrimePrincipalAcaoPolicial(cursor.getString(7));
            acaopolicialdaacaopolicial.setCbkMedidasCautelares(cursor.getInt(8));
            acaopolicialdaacaopolicial.setCbkInvestigacoesOrdinarias(cursor.getInt(9));
            acaopolicialdaacaopolicial.setCbkRegistroPolicial(cursor.getInt(10));
            acaopolicialdaacaopolicial.setCbkAbordagemRotina(cursor.getInt(11));
            acaopolicialdaacaopolicial.setCbkColaborador(cursor.getInt(12));
            acaopolicialdaacaopolicial.setCbkDenuncia(cursor.getInt(13));
            acaopolicialdaacaopolicial.setCbkOutros(cursor.getInt(14));
            acaopolicialdaacaopolicial.setDstituloResumo(cursor.getString(15));
            acaopolicialdaacaopolicial.setDsResumo(cursor.getString(16));
            acaopolicialdaacaopolicial.setDsNIPAcaoPolicial(cursor.getString(17));
            acaopolicialdaacaopolicial.setDsNomeDelegadoAcaoPolicial(cursor.getString(18));
            acaopolicialdaacaopolicial.setDsMunicipioReferenciaAcaoPolicial(cursor.getString(19));
            acaopolicialdaacaopolicial.setCbkPresoAcaoPolicial(cursor.getInt(20));
            acaopolicialdaacaopolicial.setCbkBensAprendidos(cursor.getInt(21));
            acaopolicialdaacaopolicial.setDsSubtitulo1(cursor.getString(22));
            acaopolicialdaacaopolicial.setDsTexto1(cursor.getString(23));
            acaopolicialdaacaopolicial.setDsSubtitulo2(cursor.getString(24));
            acaopolicialdaacaopolicial.setDsTexto2(cursor.getString(25));
            acaopolicialdaacaopolicial.setDsSubtitulo3(cursor.getString(26));
            acaopolicialdaacaopolicial.setDsTexto3(cursor.getString(27));
            acaopolicialdaacaopolicial.setDsSubtitulo4(cursor.getString(28));
            acaopolicialdaacaopolicial.setDsTexto4(cursor.getString(29));
            acaopolicialdaacaopolicial.setCbkOperacaoInominada(cursor.getInt(30));
            acaopolicialdacao.add(acaopolicialdaacaopolicial);
        }
        return acaopolicialdacao;
    }

    public List<Acaopolicial> retornaAcaoPolicialDaEquipeEnvolvida(int id){

        List<Acaopolicial> acaopolicialdaequipeenvolvida = new ArrayList<>();

        db = conexao.getReadableDatabase();


        Cursor cursor = db.query("AcaoPolicial", new String[]{"id", "dsUnidadeResponsavelAcaoPolicial", "NEquipeLocaisAcaoPolicial", "NEquipeUnidadeCoorpinAcaoPolicial", "NEquipeDetCoorCoorpinAcaoPolicial", "NOutraInstituicaoAcaoPolicial", "dsNomeUnidadeEspAcaoPolicial",
                "NEquipeEspAcaoPolicial", "dsNomeOutraInstituicaoAcaoPolicial", "NEnvolvidosOutrasInstituicaoAcaoPolicial"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Acaopolicial acaopolicialdaequipe = new Acaopolicial();

            acaopolicialdaequipe.setId(cursor.getInt(0));
            acaopolicialdaequipe.setDsUnidadeResponsavelAcaoPolicial(cursor.getString(1));
            acaopolicialdaequipe.setNEquipeLocaisAcaoPolicial(cursor.getInt(2));
            acaopolicialdaequipe.setNEquipeUnidadeCoorpinAcaoPolicial(cursor.getInt(3));
            acaopolicialdaequipe.setNEquipeDetCoorCoorpinAcaoPolicial(cursor.getInt(4));
            acaopolicialdaequipe.setEdtNEquipesGeraisAcaoPolicial(cursor.getInt(5));
            acaopolicialdaequipe.setDsNomeUnidadeEspAcaoPolicial(cursor.getString(6));
            acaopolicialdaequipe.setNEquipeEspAcaoPolicial(cursor.getInt(7));
            acaopolicialdaequipe.setDsNomeOutraInstituicaoAcaoPolicial(cursor.getString(8));
            acaopolicialdaequipe.setNEnvolvidosOutrasInstituicaoAcaoPolicial(cursor.getInt(9));

            acaopolicialdaequipeenvolvida.add(acaopolicialdaequipe);
        }
        return acaopolicialdaequipeenvolvida;
    }

    public List<Acaopolicial> retornaAcaoPolicialDoProcedimento(int id){

        List<Acaopolicial> acaopolicialdoprocedimento = new ArrayList<>();

        db = conexao.getReadableDatabase();


        Cursor cursor = db.query("AcaoPolicial", new String[]{"id", "dsNIPAcaoPolicial", "dsNomeDelegadoAcaoPolicial"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Acaopolicial acaopolicialdoprocedimentos = new Acaopolicial();

            acaopolicialdoprocedimentos.setId(cursor.getInt(0));
            acaopolicialdoprocedimentos.setDsNIPAcaoPolicial(cursor.getString(1));
            acaopolicialdoprocedimentos.setDsNomeDelegadoAcaoPolicial(cursor.getString(2));

            acaopolicialdoprocedimento.add(acaopolicialdoprocedimentos);
        }
        return acaopolicialdoprocedimento;
    }

    public Acaopolicial retornaAcaopolicialdaAcaoObj(int id){

        Acaopolicial acaopolicialdaacao = new Acaopolicial();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("AcaoPolicial", new String[]{"id", "dsNomeOperacaoPolicial", "dtAcaoPolicial", "hsAcaoPolicial",
                "LocalAcaoPolicial", "NBuscaJudiciaisAcaoPolicial", "dsCrimePrincipalAcaoPolicial", "cbkMedidasCautelares", "cbkInvestigacoesOrdinarias", "cbkRegistroPolicial", "cbkAbordagemRotina",
                "cbkColaborador", "cbkDenuncia", "cbkOutros", "dstituloResumo", "dsResumo", "dsNIPAcaoPolicial", "dsNomeDelegadoAcaoPolicial","dsMunicipioReferenciaAcaoPolicial",
                "cbkPresoAcaoPolicial", "cbkBensAprendidos", "dsSubitulo1", "dsTexto1", "dsSubitulo2", "dsTexto2", "dsSubitulo3", "dsTexto3", "dsSubitulo4", "dsTexto4"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            acaopolicialdaacao.setId(cursor.getInt(0));
            acaopolicialdaacao.setDsNomeOperacaoPolicial(cursor.getString(1));
            acaopolicialdaacao.setDtAcaoPolicial(cursor.getString(2));
            acaopolicialdaacao.setHsAcaoPolicial(cursor.getString(3));
            acaopolicialdaacao.setLocalAcaoPolicial(cursor.getString(4));
            acaopolicialdaacao.setNBuscaJudiciaisAcaoPolicial(cursor.getInt(5));
            acaopolicialdaacao.setDsCrimePrincipalAcaoPolicial((cursor.getString(6)));
            acaopolicialdaacao.setCbkMedidasCautelares((cursor.getInt(7)));
            acaopolicialdaacao.setCbkInvestigacoesOrdinarias(cursor.getInt(8));
            acaopolicialdaacao.setCbkRegistroPolicial(cursor.getInt(9));
            acaopolicialdaacao.setCbkAbordagemRotina(cursor.getInt(10));
            acaopolicialdaacao.setCbkColaborador(cursor.getInt(11));
            acaopolicialdaacao.setCbkDenuncia(cursor.getInt(12));
            acaopolicialdaacao.setCbkOutros(cursor.getInt(13));
            acaopolicialdaacao.setDstituloResumo(cursor.getString(14));
            acaopolicialdaacao.setDsResumo(cursor.getString(15));
            acaopolicialdaacao.setDsNIPAcaoPolicial(cursor.getString(16));
            acaopolicialdaacao.setDsNomeDelegadoAcaoPolicial(cursor.getString(17));
            acaopolicialdaacao.setDsMunicipioReferenciaAcaoPolicial(cursor.getString(18));
            acaopolicialdaacao.setCbkPresoAcaoPolicial(cursor.getInt(19));
            acaopolicialdaacao.setCbkBensAprendidos(cursor.getInt(20));
            acaopolicialdaacao.setDsSubtitulo1(cursor.getString(21));
            acaopolicialdaacao.setDsTexto1(cursor.getString(22));
            acaopolicialdaacao.setDsSubtitulo2(cursor.getString(23));
            acaopolicialdaacao.setDsTexto2(cursor.getString(24));
            acaopolicialdaacao.setDsSubtitulo3(cursor.getString(25));
            acaopolicialdaacao.setDsTexto3(cursor.getString(26));
            acaopolicialdaacao.setDsSubtitulo4(cursor.getString(27));
            acaopolicialdaacao.setDsTexto4(cursor.getString(28));

        }
        return acaopolicialdaacao;
    }

    public Acaopolicial retornaAcaopolicialdaEquipeObj(int id){

        Acaopolicial acaopolicialdaequipe = new Acaopolicial();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("AcaoPolicial", new String[]{"id", "dsUnidadeResponsavelAcaoPolicial", "NEquipeLocaisAcaoPolicial", "NEquipeUnidadeCoorpinAcaoPolicial",
                "NEquipeDetCoorCoorpinAcaoPolicial", "NOutraInstituicaoAcaoPolicial", "dsOutraInstituicaoAcaoPolicial", "dsNomeUnidadeEspAcaoPolicial", "NEquipeEspAcaoPolicial",
                "dsNomeOutraInstituicaoAcaoPolicial", "NEnvolvidosOutrasInstituicaoAcaoPolicial"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            acaopolicialdaequipe.setId(cursor.getInt(0));
            acaopolicialdaequipe.setDsUnidadeResponsavelAcaoPolicial(cursor.getString(1));
            acaopolicialdaequipe.setNEquipeLocaisAcaoPolicial(cursor.getInt(2));
            acaopolicialdaequipe.setNEquipeUnidadeCoorpinAcaoPolicial(cursor.getInt(3));
            acaopolicialdaequipe.setNEquipeDetCoorCoorpinAcaoPolicial(cursor.getInt(4));
            acaopolicialdaequipe.setEdtNEquipesGeraisAcaoPolicial(cursor.getInt(5));
            acaopolicialdaequipe.setDsOutraInstituicaoAcaoPolicial((cursor.getString(6)));
            acaopolicialdaequipe.setDsNomeUnidadeEspAcaoPolicial((cursor.getString(7)));
            acaopolicialdaequipe.setNEquipeEspAcaoPolicial(cursor.getInt(8));
            acaopolicialdaequipe.setDsNomeOutraInstituicaoAcaoPolicial(cursor.getString(9));
            acaopolicialdaequipe.setNEnvolvidosOutrasInstituicaoAcaoPolicial(cursor.getInt(10));

        }
        return acaopolicialdaequipe;
    }

    public long AtualizarAcaoPolicialDaEquipe(Acaopolicial acaopolicial1, int id, int controle) {

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        acaopolicial1.setEstatusAcaoPolicial(1);
        acaopolicial1.setControle(controle);
        acaopolicial1.setValidaracaopolicial(0);

        cv.put("dsUnidadeResponsavelAcaoPolicial", acaopolicial1.getDsUnidadeResponsavelAcaoPolicial());
        cv.put("NEquipeLocaisAcaoPolicial", acaopolicial1.getNEquipeLocaisAcaoPolicial());
        cv.put("NEquipeUnidadeCoorpinAcaoPolicial", acaopolicial1.getNEquipeUnidadeCoorpinAcaoPolicial());
        cv.put("NEquipeDetCoorCoorpinAcaoPolicial", acaopolicial1.getNEquipeDetCoorCoorpinAcaoPolicial());
        cv.put("NOutraInstituicaoAcaoPolicial", acaopolicial1.getEdtNEquipesGeraisAcaoPolicial());
        cv.put("dsOutraInstituicaoAcaoPolicial", acaopolicial1.getDsNomeOutraInstituicaoAcaoPolicial());
        cv.put("dsNomeUnidadeEspAcaoPolicial", acaopolicial1.getDsNomeUnidadeEspAcaoPolicial());
        cv.put("NEquipeEspAcaoPolicial", acaopolicial1.getNEquipeEspAcaoPolicial());
        cv.put("dsNomeOutraInstituicaoAcaoPolicial", acaopolicial1.getDsNomeOutraInstituicaoAcaoPolicial());
        cv.put("NEnvolvidosOutrasInstituicaoAcaoPolicial", acaopolicial1.getNEnvolvidosOutrasInstituicaoAcaoPolicial());
        cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
        cv.put("Controle", acaopolicial1.getControle());
        cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

        return db.update("AcaoPolicial", cv, "id = '" + id + "'", null);

    }


    public Acaopolicial retornaAcaopolicialdoProcedimentoObj(int id){

        Acaopolicial acaopolicialdoprocedimento = new Acaopolicial();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("AcaoPolicial", new String[]{"id", "dsNIPAcaoPolicial", "dsNomeDelegadoAcaoPolicial"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            acaopolicialdoprocedimento.setId(cursor.getInt(0));
            acaopolicialdoprocedimento.setDsNIPAcaoPolicial(cursor.getString(1));
            acaopolicialdoprocedimento.setDsNomeDelegadoAcaoPolicial(cursor.getString(2));
        }
        return acaopolicialdoprocedimento;
    }

    public long AtualizarAcaoPolicialDoProcedimento(Acaopolicial acaopolicial1, int id, int controle) {

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        acaopolicial1.setEstatusAcaoPolicial(1);
        acaopolicial1.setControle(controle);
        acaopolicial1.setValidaracaopolicial(0);

        cv.put("dsNIPAcaoPolicial", acaopolicial1.getDsNIPAcaoPolicial());
        cv.put("dsNomeDelegadoAcaoPolicial", acaopolicial1.getDsNomeDelegadoAcaoPolicial());
        cv.put("EstatusAcaoPolicial", acaopolicial1.getEstatusAcaoPolicial());
        cv.put("Controle", acaopolicial1.getControle());
        cv.put("validaracaopolicial", acaopolicial1.getValidaracaopolicial());

        return db.update("AcaoPolicial", cv, "id = '" + id + "'", null);

    }

}
