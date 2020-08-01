package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Autoria;

import java.util.ArrayList;
import java.util.List;

public class AutoriaDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;
    private Autoria AtualizarAutoria;
    public String autorias[];

    public AutoriaDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarCVLIAutoriaWEB(Autoria autoria){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

            cv.put("fkCvli", autoria.getFkCvli());
            cv.put("cbkAutoriaDefinida", autoria.getCbkAutoriaDefinida());
            cv.put("cbkAutoriaNDefinida", autoria.getCbkAutoriaNDefinida());
            cv.put("cbkAutoriaSuspeita", autoria.getCbkAutoriaSuspeita());
            cv.put("dsNomeAutoria", autoria.getDsNomeAutoria());
            cv.put("dsRGAutoria", autoria.getDsRGAutoria());
            cv.put("dsOrgaoExpRGAutoria", autoria.getDsOrgaoExpRGAutoria());
            cv.put("dsSexoAutoria", autoria.getDsSexoAutoria());
            cv.put("dsCPFAutoria", autoria.getDsCPFAutoria());
            cv.put("dsIdadeAutoria", autoria.getDsIdadeAutoria());
            cv.put("dsCurtisAutoria", autoria.getDsCurtisAutoria());
            cv.put("dsNomeMaeAutoria", autoria.getDsNomeMaeAutoria());
            cv.put("dsNomePaiAutoria", autoria.getDsNomePaiAutoria());
            cv.put("dsNascionalidadeAutoria", autoria.getDsNascionalidadeAutoria());
            cv.put("dsNaturalidadeAutoria", autoria.getDsNaturalidadeAutoria());
            cv.put("dsCondicaoAutoria", autoria.getDsCondicaoAutoria());
            cv.put("dtPrisaoAutoria", autoria.getDtPrisaoAutoria());
            cv.put("dsLocalPrisaoAutoria", autoria.getDsLocalPrisaoAutoria());
            cv.put("hsHorarioPrisaoAutoria", autoria.getHsHorarioPrisaoAutoria());
            cv.put("dsNaturezaPrisaoAutoria", autoria.getDsNaturezaPrisaoAutoria());
            cv.put("dsResponsavelPrisaoAutoria", autoria.getDsResponsavelPrisaoAutoria());
            cv.put("Controle", autoria.getControle());
            cv.put("idUnico", autoria.getIdUnico());
            cv.put("dsVulgoAutoria", autoria.getDsVulgoAutoria());

            return db.insert("cvliautorias", null, cv);
    }

    public long atualizarCVLIAutoriaWEB(Autoria autoria, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkCvli", autoria.getFkCvli());
        cv.put("cbkAutoriaDefinida", autoria.getCbkAutoriaDefinida());
        cv.put("cbkAutoriaNDefinida", autoria.getCbkAutoriaNDefinida());
        cv.put("cbkAutoriaSuspeita", autoria.getCbkAutoriaSuspeita());
        cv.put("dsNomeAutoria", autoria.getDsNomeAutoria());
        cv.put("dsRGAutoria", autoria.getDsRGAutoria());
        cv.put("dsOrgaoExpRGAutoria", autoria.getDsOrgaoExpRGAutoria());
        cv.put("dsSexoAutoria", autoria.getDsSexoAutoria());
        cv.put("dsCPFAutoria", autoria.getDsCPFAutoria());
        cv.put("dsIdadeAutoria", autoria.getDsIdadeAutoria());
        cv.put("dsCurtisAutoria", autoria.getDsCurtisAutoria());
        cv.put("dsNomeMaeAutoria", autoria.getDsNomeMaeAutoria());
        cv.put("dsNomePaiAutoria", autoria.getDsNomePaiAutoria());
        cv.put("dsNascionalidadeAutoria", autoria.getDsNascionalidadeAutoria());
        cv.put("dsNaturalidadeAutoria", autoria.getDsNaturalidadeAutoria());
        cv.put("dsCondicaoAutoria", autoria.getDsCondicaoAutoria());
        cv.put("dtPrisaoAutoria", autoria.getDtPrisaoAutoria());
        cv.put("dsLocalPrisaoAutoria", autoria.getDsLocalPrisaoAutoria());
        cv.put("hsHorarioPrisaoAutoria", autoria.getHsHorarioPrisaoAutoria());
        cv.put("dsNaturezaPrisaoAutoria", autoria.getDsNaturezaPrisaoAutoria());
        cv.put("dsResponsavelPrisaoAutoria", autoria.getDsResponsavelPrisaoAutoria());
        cv.put("Controle", autoria.getControle());
        cv.put("idUnico", autoria.getIdUnico());
        cv.put("dsVulgoAutoria", autoria.getDsVulgoAutoria());

        return db.update("cvliautorias", cv, "idUnico= '" + codigo + "'", null);
    }

    public long cadastrarCVLIAutoria(Autoria autoria, String valor, int codigo){

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            int codigostatus = retornarCodigoStatusCvli(retornarCodigoCvliSemParametro());
            if (codigostatus == 0) {
                autoria.setFkCvli(codigo);
                autoria.setIdUnico(valor);

                cv.put("fkCvli", autoria.getFkCvli());
                cv.put("cbkAutoriaDefinida", autoria.getCbkAutoriaDefinida());
                cv.put("cbkAutoriaNDefinida", autoria.getCbkAutoriaNDefinida());
                cv.put("cbkAutoriaSuspeita", autoria.getCbkAutoriaSuspeita());
                cv.put("dsNomeAutoria", autoria.getDsNomeAutoria());
                cv.put("dsRGAutoria", autoria.getDsRGAutoria());
                cv.put("dsOrgaoExpRGAutoria", autoria.getDsOrgaoExpRGAutoria());
                cv.put("dsSexoAutoria", autoria.getDsSexoAutoria());
                cv.put("dsCPFAutoria", autoria.getDsCPFAutoria());
                cv.put("dsIdadeAutoria", autoria.getDsIdadeAutoria());
                cv.put("dsCurtisAutoria", autoria.getDsCurtisAutoria());
                cv.put("dsNomeMaeAutoria", autoria.getDsNomeMaeAutoria());
                cv.put("dsNomePaiAutoria", autoria.getDsNomePaiAutoria());
                cv.put("dsNascionalidadeAutoria", autoria.getDsNascionalidadeAutoria());
                cv.put("dsNaturalidadeAutoria", autoria.getDsNaturalidadeAutoria());
                cv.put("dsCondicaoAutoria", autoria.getDsCondicaoAutoria());
                cv.put("dtPrisaoAutoria", autoria.getDtPrisaoAutoria());
                cv.put("dsLocalPrisaoAutoria", autoria.getDsLocalPrisaoAutoria());
                cv.put("hsHorarioPrisaoAutoria", autoria.getHsHorarioPrisaoAutoria());
                cv.put("dsNaturezaPrisaoAutoria", autoria.getDsNaturezaPrisaoAutoria());
                cv.put("dsResponsavelPrisaoAutoria", autoria.getDsResponsavelPrisaoAutoria());
                cv.put("Controle", autoria.getControle());
                cv.put("idUnico", autoria.getIdUnico());
                cv.put("dsVulgoAutoria", autoria.getDsVulgoAutoria());


                return db.insert("cvliautorias", null, cv);
            } else {
                autoria.setFkCvli(codigo);
                autoria.setIdUnico(valor);

                Log.i("valores aut1",""+autoria.getFkCvli());
                cv.put("fkCvli", autoria.getFkCvli());
                cv.put("cbkAutoriaDefinida", autoria.getCbkAutoriaDefinida());
                cv.put("cbkAutoriaNDefinida", autoria.getCbkAutoriaNDefinida());
                cv.put("cbkAutoriaSuspeita", autoria.getCbkAutoriaSuspeita());
                cv.put("dsNomeAutoria", autoria.getDsNomeAutoria());
                cv.put("dsRGAutoria", autoria.getDsRGAutoria());
                cv.put("dsOrgaoExpRGAutoria", autoria.getDsOrgaoExpRGAutoria());
                cv.put("dsSexoAutoria", autoria.getDsSexoAutoria());
                cv.put("dsCPFAutoria", autoria.getDsCPFAutoria());
                cv.put("dsIdadeAutoria", autoria.getDsIdadeAutoria());
                cv.put("dsCurtisAutoria", autoria.getDsCurtisAutoria());
                cv.put("dsNomeMaeAutoria", autoria.getDsNomeMaeAutoria());
                cv.put("dsNomePaiAutoria", autoria.getDsNomePaiAutoria());
                cv.put("dsNascionalidadeAutoria", autoria.getDsNascionalidadeAutoria());
                cv.put("dsNaturalidadeAutoria", autoria.getDsNaturalidadeAutoria());
                cv.put("dsCondicaoAutoria", autoria.getDsCondicaoAutoria());
                cv.put("dtPrisaoAutoria", autoria.getDtPrisaoAutoria());
                cv.put("dsLocalPrisaoAutoria", autoria.getDsLocalPrisaoAutoria());
                cv.put("hsHorarioPrisaoAutoria", autoria.getHsHorarioPrisaoAutoria());
                cv.put("dsNaturezaPrisaoAutoria", autoria.getDsNaturezaPrisaoAutoria());
                cv.put("dsResponsavelPrisaoAutoria", autoria.getDsResponsavelPrisaoAutoria());
                cv.put("Controle", autoria.getControle());
                cv.put("idUnico", autoria.getIdUnico());
                cv.put("dsVulgoAutoria", autoria.getDsVulgoAutoria());


                return db.insert("cvliautorias", null, cv);
            }

    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvliautorias", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public long excluirAutoria(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("cvliautorias","fkCvli= '"+fk+"'",null);
    }

    public long atualizarAutoria(Autoria autoria, int fk, int id){

            db = conexao.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put("cbkAutoriaDefinida", autoria.getCbkAutoriaDefinida());
            cv.put("cbkAutoriaNDefinida", autoria.getCbkAutoriaNDefinida());
            cv.put("cbkAutoriaSuspeita", autoria.getCbkAutoriaSuspeita());
            cv.put("dsNomeAutoria", autoria.getDsNomeAutoria());
            cv.put("dsRGAutoria", autoria.getDsRGAutoria());
            cv.put("dsOrgaoExpRGAutoria", autoria.getDsOrgaoExpRGAutoria());
            cv.put("dsSexoAutoria", autoria.getDsSexoAutoria());
            cv.put("dsCPFAutoria", autoria.getDsCPFAutoria());
            cv.put("dsIdadeAutoria", autoria.getDsIdadeAutoria());
            cv.put("dsCurtisAutoria", autoria.getDsCurtisAutoria());
            cv.put("dsNomeMaeAutoria", autoria.getDsNomeMaeAutoria());
            cv.put("dsNomePaiAutoria", autoria.getDsNomePaiAutoria());
            cv.put("dsNascionalidadeAutoria", autoria.getDsNascionalidadeAutoria());
            cv.put("dsNaturalidadeAutoria", autoria.getDsNaturalidadeAutoria());
            cv.put("dsCondicaoAutoria", autoria.getDsCondicaoAutoria());
            cv.put("dtPrisaoAutoria", autoria.getDtPrisaoAutoria());
            cv.put("dsLocalPrisaoAutoria", autoria.getDsLocalPrisaoAutoria());
            cv.put("hsHorarioPrisaoAutoria", autoria.getHsHorarioPrisaoAutoria());
            cv.put("dsNaturezaPrisaoAutoria", autoria.getDsNaturezaPrisaoAutoria());
            cv.put("dsResponsavelPrisaoAutoria", autoria.getDsResponsavelPrisaoAutoria());
            cv.put("Controle", autoria.getControle());
            cv.put("dsVulgoAutoria", autoria.getDsVulgoAutoria());

            return db.update("cvliautorias", cv, "fkCvli= '" + fk + "' and id= '" + id + "'", null);

    }

    public int retornarCodigoCvli(int status){

            db = conexao.getReadableDatabase();

            Cursor c = db.query("cvlis", new String[]{"id"}, "EstatusCVLI='" + status + "'", null, null, null, null);
            c.moveToFirst();
            int valorcodigo = c.getInt(0);
            return valorcodigo;

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

    public String VerificarBrancoAutoria(int id){

        db = conexao.getReadableDatabase();

        String nomeembranco = null;

        Cursor cursor = db.query("cvliautorias", new String[]{"dsSexoAutoria", "dsIdadeAutoria"},
                "fkCvli='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Autoria AtualizarAutoria = new Autoria();

            AtualizarAutoria.setDsSexoAutoria(cursor.getString(0));
            if(AtualizarAutoria.getDsSexoAutoria() == null || AtualizarAutoria.getDsSexoAutoria().equals("")){
                nomeembranco = nomeembranco + "Sexo da Autoria, ";
            }
            AtualizarAutoria.setDsIdadeAutoria(cursor.getInt(1));
            if(String.valueOf(AtualizarAutoria.getDsIdadeAutoria()) == null){
                nomeembranco = nomeembranco + "Idade da Autoria, ";
            }

        }

        return nomeembranco;
    }

    public List<Autoria> retornarAutoria(int id) {

            db = conexao.getReadableDatabase();

            List<Autoria> listaautores = new ArrayList<>();

            Cursor cursor = db.query("cvliautorias", new String[]{"id", "fkCvli", "cbkAutoriaDefinida", "cbkAutoriaNDefinida",
                    "cbkAutoriaSuspeita", "dsNomeAutoria", "dsRGAutoria", "dsOrgaoExpRGAutoria", "dsSexoAutoria",
                    "dsCPFAutoria", "dsIdadeAutoria", "dsCurtisAutoria", "dsNomeMaeAutoria", "dsNomePaiAutoria",
                    "dsNascionalidadeAutoria", "dsNaturalidadeAutoria", "dsCondicaoAutoria", "hsHorarioPrisaoAutoria",
                    "dtPrisaoAutoria", "dsLocalPrisaoAutoria", "dsNaturezaPrisaoAutoria", "dsResponsavelPrisaoAutoria", "Controle", "dsVulgoAutoria"}, "fkCvli='" + id + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Autoria AtualizarAutoria = new Autoria();

                AtualizarAutoria.setId(cursor.getInt(0));
                AtualizarAutoria.setFkCvli(cursor.getInt(1));
                AtualizarAutoria.setCbkAutoriaDefinida(cursor.getInt(2));
                AtualizarAutoria.setCbkAutoriaNDefinida(cursor.getInt(3));
                AtualizarAutoria.setCbkAutoriaSuspeita(cursor.getInt(4));
                AtualizarAutoria.setDsNomeAutoria(cursor.getString(5));
                AtualizarAutoria.setDsRGAutoria(cursor.getString(6));
                AtualizarAutoria.setDsOrgaoExpRGAutoria(cursor.getString(7));
                AtualizarAutoria.setDsSexoAutoria(cursor.getString(8));
                AtualizarAutoria.setDsCPFAutoria(cursor.getString(9));
                AtualizarAutoria.setDsIdadeAutoria(cursor.getInt(10));
                AtualizarAutoria.setDsCurtisAutoria(cursor.getString(11));
                AtualizarAutoria.setDsNomeMaeAutoria(cursor.getString(12));
                AtualizarAutoria.setDsNomePaiAutoria(cursor.getString(13));
                AtualizarAutoria.setDsNascionalidadeAutoria(cursor.getString(14));
                AtualizarAutoria.setDsNaturalidadeAutoria(cursor.getString(15));
                AtualizarAutoria.setDsCondicaoAutoria(cursor.getString(16));
                AtualizarAutoria.setHsHorarioPrisaoAutoria(cursor.getString(17));
                AtualizarAutoria.setDtPrisaoAutoria(cursor.getString(18));
                AtualizarAutoria.setDsLocalPrisaoAutoria(cursor.getString(19));
                AtualizarAutoria.setDsNaturezaPrisaoAutoria(cursor.getString(20));
                AtualizarAutoria.setDsResponsavelPrisaoAutoria(cursor.getString(21));
                AtualizarAutoria.setControle(cursor.getInt(22));
                AtualizarAutoria.setDsVulgoAutoria(cursor.getString(23));

                listaautores.add(AtualizarAutoria);
            }
            return listaautores;

    }

    public Autoria retornarAutoriaObj(int id) {

        Autoria AtualizarAutoria = new Autoria();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("cvliautorias", new String[]{"id", "fkCvli", "cbkAutoriaDefinida", "cbkAutoriaNDefinida",
                "cbkAutoriaSuspeita", "dsNomeAutoria", "dsRGAutoria", "dsOrgaoExpRGAutoria", "dsSexoAutoria",
                "dsCPFAutoria", "dsIdadeAutoria", "dsCurtisAutoria", "dsNomeMaeAutoria", "dsNomePaiAutoria",
                "dsNascionalidadeAutoria", "dsNaturalidadeAutoria", "dsCondicaoAutoria", "hsHorarioPrisaoAutoria",
                "dtPrisaoAutoria", "dsLocalPrisaoAutoria", "dsNaturezaPrisaoAutoria", "dsResponsavelPrisaoAutoria", "Controle", "dsVulgoAutoria"}, "fkCvli='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            AtualizarAutoria.setId(cursor.getInt(0));
            AtualizarAutoria.setFkCvli(cursor.getInt(1));
            AtualizarAutoria.setCbkAutoriaDefinida(cursor.getInt(2));
            AtualizarAutoria.setCbkAutoriaNDefinida(cursor.getInt(3));
            AtualizarAutoria.setCbkAutoriaSuspeita(cursor.getInt(4));
            AtualizarAutoria.setDsNomeAutoria(cursor.getString(5));
            AtualizarAutoria.setDsRGAutoria(cursor.getString(6));
            AtualizarAutoria.setDsOrgaoExpRGAutoria(cursor.getString(7));
            AtualizarAutoria.setDsSexoAutoria(cursor.getString(8));
            AtualizarAutoria.setDsCPFAutoria(cursor.getString(9));
            AtualizarAutoria.setDsIdadeAutoria(cursor.getInt(10));
            AtualizarAutoria.setDsCurtisAutoria(cursor.getString(11));
            AtualizarAutoria.setDsNomeMaeAutoria(cursor.getString(12));
            AtualizarAutoria.setDsNomePaiAutoria(cursor.getString(13));
            AtualizarAutoria.setDsNascionalidadeAutoria(cursor.getString(14));
            AtualizarAutoria.setDsNaturalidadeAutoria(cursor.getString(15));
            AtualizarAutoria.setDsCondicaoAutoria(cursor.getString(16));
            AtualizarAutoria.setHsHorarioPrisaoAutoria(cursor.getString(17));
            AtualizarAutoria.setDtPrisaoAutoria(cursor.getString(18));
            AtualizarAutoria.setDsLocalPrisaoAutoria(cursor.getString(19));
            AtualizarAutoria.setDsNaturezaPrisaoAutoria(cursor.getString(20));
            AtualizarAutoria.setDsResponsavelPrisaoAutoria(cursor.getString(21));
            AtualizarAutoria.setControle(cursor.getInt(22));
            AtualizarAutoria.setDsVulgoAutoria(cursor.getString(23));

        }
        return AtualizarAutoria;

    }

}
