package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Vitima;

import java.util.ArrayList;
import java.util.List;

public class VitimaDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;
    public Vitima vitima;
    public String vitimas[];

    public VitimaDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
        vitima = new Vitima();
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvlivitimas", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public long cadastrarCVLIVitimaWEB(Vitima vitima){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

            cv.put("fkCvli", vitima.getFkCvli());
            cv.put("ckbIdentificadaVitima", vitima.getCkbIdentificadaVitima());
            cv.put("cbkNaoIdentificadaVitima", vitima.getCbkNaoIdentificadaVitima());
            cv.put("dsNomeVitima", vitima.getDsNomeVitima());
            cv.put("dsRGVitima", vitima.getDsRGVitima());
            cv.put("dsOrgaoExpRGVitima", vitima.getDsOrgaoExpRGVitima());
            cv.put("dsSexoVitima", vitima.getDsSexoVitima());
            cv.put("dsCPFVitima", vitima.getDsCPFVitima());
            cv.put("dsNomeMaeVitima", vitima.getDsNomeMaeVitima());
            cv.put("dsNomePaiVitima", vitima.getDsNomePaiVitima());
            cv.put("dsNascionalidadeVitima", vitima.getDsNascionalidadeVitima());
            cv.put("dsNaturalidadeVitima", vitima.getDsNaturalidadeVitima());
            cv.put("dsOrientacaoSexualVitima", vitima.getDsOrientacaoSexualVitima());
            cv.put("dsProfissaoVitima", vitima.getDsProfissaoVitima());
            cv.put("dsCondicaoSaudeVitima", vitima.getDsCondicaoSaudeVitima());
            cv.put("Controle", vitima.getControle());
            cv.put("idUnico", vitima.getIdUnico());
            cv.put("dsEstadoVitima", vitima.getDsEstadoVitima());
            cv.put("dsCondicaoVitima", vitima.getDsCondicaoVitima());
            cv.put("dsDtNascimentoVitima", vitima.getDsDtNascimentoVitima());

            return db.insert("cvlivitimas", null, cv);
    }

    public long atualizarCVLIVitimaWEB(Vitima vitima, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkCvli", vitima.getFkCvli());
        cv.put("ckbIdentificadaVitima", vitima.getCkbIdentificadaVitima());
        cv.put("cbkNaoIdentificadaVitima", vitima.getCbkNaoIdentificadaVitima());
        cv.put("dsNomeVitima", vitima.getDsNomeVitima());
        cv.put("dsRGVitima", vitima.getDsRGVitima());
        cv.put("dsOrgaoExpRGVitima", vitima.getDsOrgaoExpRGVitima());
        cv.put("dsSexoVitima", vitima.getDsSexoVitima());
        cv.put("dsCPFVitima", vitima.getDsCPFVitima());
        cv.put("dsNomeMaeVitima", vitima.getDsNomeMaeVitima());
        cv.put("dsNomePaiVitima", vitima.getDsNomePaiVitima());
        cv.put("dsNascionalidadeVitima", vitima.getDsNascionalidadeVitima());
        cv.put("dsNaturalidadeVitima", vitima.getDsNaturalidadeVitima());
        cv.put("dsOrientacaoSexualVitima", vitima.getDsOrientacaoSexualVitima());
        cv.put("dsProfissaoVitima", vitima.getDsProfissaoVitima());
        cv.put("dsCondicaoSaudeVitima", vitima.getDsCondicaoSaudeVitima());
        cv.put("Controle", vitima.getControle());
        cv.put("idUnico", vitima.getIdUnico());
        cv.put("dsEstadoVitima", vitima.getDsEstadoVitima());
        cv.put("dsCondicaoVitima", vitima.getDsCondicaoVitima());
        cv.put("dsDtNascimentoVitima", vitima.getDsDtNascimentoVitima());

        return db.update("cvlivitimas", cv, "idUnico= '" + codigo + "'", null);
    }

    public long cadastrarCVLIVitima(Vitima vitima, String valor, int codigo){

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();


            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {

                vitima.setFkCvli(codigo);
                vitima.setIdUnico(valor);

                cv.put("fkCvli", vitima.getFkCvli());
                cv.put("ckbIdentificadaVitima", vitima.getCkbIdentificadaVitima());
                cv.put("cbkNaoIdentificadaVitima", vitima.getCbkNaoIdentificadaVitima());
                cv.put("dsNomeVitima", vitima.getDsNomeVitima());
                cv.put("dsRGVitima", vitima.getDsRGVitima());
                cv.put("dsOrgaoExpRGVitima", vitima.getDsOrgaoExpRGVitima());
                cv.put("dsSexoVitima", vitima.getDsSexoVitima());
                cv.put("dsCPFVitima", vitima.getDsCPFVitima());
                cv.put("dsNomeMaeVitima", vitima.getDsNomeMaeVitima());
                cv.put("dsNomePaiVitima", vitima.getDsNomePaiVitima());
                cv.put("dsNascionalidadeVitima", vitima.getDsNascionalidadeVitima());
                cv.put("dsNaturalidadeVitima", vitima.getDsNaturalidadeVitima());
                cv.put("dsOrientacaoSexualVitima", vitima.getDsOrientacaoSexualVitima());
                cv.put("dsProfissaoVitima", vitima.getDsProfissaoVitima());
                cv.put("dsCondicaoSaudeVitima", vitima.getDsCondicaoSaudeVitima());
                cv.put("Controle", vitima.getControle());
                cv.put("idUnico", vitima.getIdUnico());
                cv.put("dsEstadoVitima", vitima.getDsEstadoVitima());
                cv.put("dsCondicaoVitima", vitima.getDsCondicaoVitima());
                cv.put("dsDtNascimentoVitima", vitima.getDsDtNascimentoVitima());

                return db.insert("cvlivitimas", null, cv);
            } else {

                vitima.setFkCvli(codigo);
                vitima.setIdUnico(valor);
                cv.put("fkCvli", vitima.getFkCvli());
                cv.put("ckbIdentificadaVitima", vitima.getCkbIdentificadaVitima());
                cv.put("cbkNaoIdentificadaVitima", vitima.getCbkNaoIdentificadaVitima());
                cv.put("dsNomeVitima", vitima.getDsNomeVitima());
                cv.put("dsRGVitima", vitima.getDsRGVitima());
                cv.put("dsOrgaoExpRGVitima", vitima.getDsOrgaoExpRGVitima());
                cv.put("dsSexoVitima", vitima.getDsSexoVitima());
                cv.put("dsCPFVitima", vitima.getDsCPFVitima());
                cv.put("dsNomeMaeVitima", vitima.getDsNomeMaeVitima());
                cv.put("dsNomePaiVitima", vitima.getDsNomePaiVitima());
                cv.put("dsNascionalidadeVitima", vitima.getDsNascionalidadeVitima());
                cv.put("dsNaturalidadeVitima", vitima.getDsNaturalidadeVitima());
                cv.put("dsOrientacaoSexualVitima", vitima.getDsOrientacaoSexualVitima());
                cv.put("dsProfissaoVitima", vitima.getDsProfissaoVitima());
                cv.put("dsCondicaoSaudeVitima", vitima.getDsCondicaoSaudeVitima());
                cv.put("Controle", vitima.getControle());
                cv.put("idUnico", vitima.getIdUnico());
                cv.put("dsEstadoVitima", vitima.getDsEstadoVitima());
                cv.put("dsCondicaoVitima", vitima.getDsCondicaoVitima());
                cv.put("dsDtNascimentoVitima", vitima.getDsDtNascimentoVitima());

                return db.insert("cvlivitimas", null, cv);
            }

    }

    public long AtualizarVitima(Vitima vitima, int fk, int id) {

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put("ckbIdentificadaVitima", vitima.getCkbIdentificadaVitima());
            cv.put("cbkNaoIdentificadaVitima", vitima.getCbkNaoIdentificadaVitima());
            cv.put("dsNomeVitima", vitima.getDsNomeVitima());
            cv.put("dsRGVitima", vitima.getDsRGVitima());
            cv.put("dsOrgaoExpRGVitima", vitima.getDsOrgaoExpRGVitima());
            cv.put("dsSexoVitima", vitima.getDsSexoVitima());
            cv.put("dsCPFVitima", vitima.getDsCPFVitima());
            cv.put("dsNomeMaeVitima", vitima.getDsNomeMaeVitima());
            cv.put("dsNomePaiVitima", vitima.getDsNomePaiVitima());
            cv.put("dsNascionalidadeVitima", vitima.getDsNascionalidadeVitima());
            cv.put("dsNaturalidadeVitima", vitima.getDsNaturalidadeVitima());
            cv.put("dsOrientacaoSexualVitima", vitima.getDsOrientacaoSexualVitima());
            cv.put("dsProfissaoVitima", vitima.getDsProfissaoVitima());
            cv.put("dsCondicaoSaudeVitima", vitima.getDsCondicaoSaudeVitima());
            cv.put("Controle", vitima.getControle());
            cv.put("dsEstadoVitima", vitima.getDsEstadoVitima());
            cv.put("dsCondicaoVitima", vitima.getDsCondicaoVitima());
            cv.put("dsDtNascimentoVitima", vitima.getDsDtNascimentoVitima());

            return db.update("cvlivitimas", cv, "fkCvli= '" + fk + "' and id= '" + id + "'", null);
    }

    public int retornarCodigoCvli(int status){


            db = conexao.getReadableDatabase();

            Cursor c = db.query("cvlis", new String[]{"id"}, "EstatusCVLI='" + status + "'", null, null, null, null);
            c.moveToLast();
            int valorcodigo = c.getInt(0);
            return valorcodigo;

    }

    public long excluirVitima(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("cvlivitimas","fkCvli='"+fk+"'",null);
    }

    public int retornarCodigoStatusCvli(int id){


            db = conexao.getReadableDatabase();

            Cursor c = db.query("cvlis", new String[]{"EstatusCVLI"}, "id='" + id + "'", null, null, null, null);
            c.moveToFirst();
            int valorcvlistatus = c.getInt(0);
            return valorcvlistatus;

    }

    public int retornarCodigoCvliSemParametro(){

            db = conexao.getReadableDatabase();

            Cursor c = db.query("cvlis", new String[]{"id"}, null, null, null, null, null);
            c.moveToLast();
            int codigosemparametro = c.getInt(0);
            return codigosemparametro;

    }

    public String VerificarBracoVitimas(int id){

        db = conexao.getReadableDatabase();

        String nomebrancos = null;
        Cursor cursor = db.query("cvlivitimas", new String[]{"dsNomeVitima", "dsCondicaoSaudeVitima","dsDtNascimentoVitima"},
                "fkCvli='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Vitima atualizarvitima = new Vitima();

            atualizarvitima.setDsNomeVitima(cursor.getString(0));
            atualizarvitima.setDsCondicaoSaudeVitima(cursor.getString(1));
            atualizarvitima.setDsDtNascimentoVitima(cursor.getString(2));

        }

        return nomebrancos;
    }


    public List<Vitima> retornarVitimas(int id){

            db = conexao.getReadableDatabase();

            List<Vitima> listavitimas = new ArrayList<>();

            Cursor cursor = db.query("cvlivitimas", new String[]{"id", "fkCvli", "ckbIdentificadaVitima",
                    "cbkNaoIdentificadaVitima", "dsNomeVitima", "dsRGVitima", "dsOrgaoExpRGVitima",
                    "dsSexoVitima", "dsCPFVitima","dsNomeMaeVitima", "dsNomePaiVitima", "dsNascionalidadeVitima", "dsNaturalidadeVitima",
                    "dsOrientacaoSexualVitima", "dsProfissaoVitima", "dsCondicaoSaudeVitima",
                    "Controle", "dsEstadoVitima", "dsCondicaoVitima", "dsDtNascimentoVitima"}, "fkCvli='" + id + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Vitima atualizarvitima = new Vitima();

                atualizarvitima.setId(cursor.getInt(0));
                atualizarvitima.setFkCvli(cursor.getInt(1));
                atualizarvitima.setCkbIdentificadaVitima(cursor.getInt(2));
                atualizarvitima.setCbkNaoIdentificadaVitima(cursor.getInt(3));
                atualizarvitima.setDsNomeVitima(cursor.getString(4));
                atualizarvitima.setDsRGVitima(cursor.getString(5));
                atualizarvitima.setDsOrgaoExpRGVitima(cursor.getString(6));
                atualizarvitima.setDsSexoVitima(cursor.getString(7));
                atualizarvitima.setDsCPFVitima(cursor.getString(8));
                atualizarvitima.setDsNomeMaeVitima(cursor.getString(9));
                atualizarvitima.setDsNomePaiVitima(cursor.getString(10));
                atualizarvitima.setDsNascionalidadeVitima(cursor.getString(11));
                atualizarvitima.setDsNaturalidadeVitima(cursor.getString(12));
                atualizarvitima.setDsOrientacaoSexualVitima(cursor.getString(13));
                atualizarvitima.setDsProfissaoVitima(cursor.getString(14));
                atualizarvitima.setDsCondicaoSaudeVitima(cursor.getString(15));
                atualizarvitima.setControle(cursor.getInt(16));
                atualizarvitima.setDsEstadoVitima(cursor.getString(17));
                atualizarvitima.setDsCondicaoVitima(cursor.getString(18));
                atualizarvitima.setDsDtNascimentoVitima(cursor.getString(19));

                listavitimas.add(atualizarvitima);
            }
            return listavitimas;

    }

    public Vitima retornarVitimasObj(int id){

        Vitima atualizarvitima = new Vitima();

        db = conexao.getReadableDatabase();


        Cursor cursor = db.query("cvlivitimas", new String[]{"id", "fkCvli", "ckbIdentificadaVitima",
                "cbkNaoIdentificadaVitima", "dsNomeVitima", "dsRGVitima", "dsOrgaoExpRGVitima",
                "dsSexoVitima", "dsCPFVitima","dsNomeMaeVitima", "dsNomePaiVitima", "dsNascionalidadeVitima", "dsNaturalidadeVitima",
                "dsOrientacaoSexualVitima", "dsProfissaoVitima", "dsCondicaoSaudeVitima",
                "Controle", "dsEstadoVitima", "dsCondicaoVitima", "dsDtNascimentoVitima"}, "fkCvli='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            atualizarvitima.setId(cursor.getInt(0));
            atualizarvitima.setFkCvli(cursor.getInt(1));
            atualizarvitima.setCkbIdentificadaVitima(cursor.getInt(2));
            atualizarvitima.setCbkNaoIdentificadaVitima(cursor.getInt(3));
            atualizarvitima.setDsNomeVitima(cursor.getString(4));
            atualizarvitima.setDsRGVitima(cursor.getString(5));
            atualizarvitima.setDsOrgaoExpRGVitima(cursor.getString(6));
            atualizarvitima.setDsSexoVitima(cursor.getString(7));
            atualizarvitima.setDsCPFVitima(cursor.getString(8));
            atualizarvitima.setDsNomeMaeVitima(cursor.getString(9));
            atualizarvitima.setDsNomePaiVitima(cursor.getString(10));
            atualizarvitima.setDsNascionalidadeVitima(cursor.getString(11));
            atualizarvitima.setDsNaturalidadeVitima(cursor.getString(12));
            atualizarvitima.setDsOrientacaoSexualVitima(cursor.getString(13));
            atualizarvitima.setDsProfissaoVitima(cursor.getString(14));
            atualizarvitima.setDsCondicaoSaudeVitima(cursor.getString(15));
            atualizarvitima.setControle(cursor.getInt(16));
            atualizarvitima.setDsEstadoVitima(cursor.getString(17));
            atualizarvitima.setDsCondicaoVitima(cursor.getString(18));
            atualizarvitima.setDsDtNascimentoVitima(cursor.getString(19));

        }
        return atualizarvitima;

    }

}
