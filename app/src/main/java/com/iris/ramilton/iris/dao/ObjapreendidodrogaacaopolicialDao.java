package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Objapreendidoarmaacaopolicial;
import com.iris.ramilton.iris.modelo.Objapreendidodrogaacaopolicial;

import java.util.ArrayList;
import java.util.List;

public class ObjapreendidodrogaacaopolicialDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public ObjapreendidodrogaacaopolicialDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarDrogasPolicialWEB(Objapreendidodrogaacaopolicial objapreendidodrogaacaopolicial){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkAcaoPolicial", objapreendidodrogaacaopolicial.getFkAcaoPolicial());
        cv.put("dsTipoDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getDsTipoDrogaAcaoPolicial());
        cv.put("dsModoApresentacaoDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getDsModoApresentacaoDrogaAcaoPolicial());
        cv.put("Controle", objapreendidodrogaacaopolicial.getControle());
        cv.put("idUnico", objapreendidodrogaacaopolicial.getIdUnico());
        cv.put("QtdDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getQtdDrogaAcaoPolicial());
        cv.put("dsPesoGramaAcaoPolicial", objapreendidodrogaacaopolicial.getDsPesoGramaAcaoPolicial());

        return db.insert("ObjApreendidoDrogaAcaoPolicial", null, cv);
    }

    public long atualizarDrogaAcaoPolicialWEB(Objapreendidodrogaacaopolicial objapreendidodrogaacaopolicial, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkAcaoPolicial", objapreendidodrogaacaopolicial.getFkAcaoPolicial());
        cv.put("dsTipoDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getDsTipoDrogaAcaoPolicial());
        cv.put("dsModoApresentacaoDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getDsModoApresentacaoDrogaAcaoPolicial());
        cv.put("Controle", objapreendidodrogaacaopolicial.getControle());
        cv.put("idUnico", objapreendidodrogaacaopolicial.getIdUnico());
        cv.put("QtdDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getQtdDrogaAcaoPolicial());
        cv.put("dsPesoGramaAcaoPolicial", objapreendidodrogaacaopolicial.getDsPesoGramaAcaoPolicial());

        return db.update("ObjApreendidoDrogaAcaoPolicial",cv, "idUnico='"+codigo+"'" ,null);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("ObjApreendidoDrogaAcaoPolicial", new String[]{"fkAcaoPolicial"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public long cadastrarDrogaAcaoPolicial(Objapreendidodrogaacaopolicial objapreendidodrogaacaopolicial, String valor){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigoretorno = retornarCodigoAcaoPolicialSemParametro();

        int codigostatus = retornarCodigoStatusAcaoPolicial(codigoretorno);

        if (codigostatus == 0) {

            int codigocvli = retornarCodigoAcaoPolicial(0);
            objapreendidodrogaacaopolicial.setFkAcaoPolicial(codigocvli);
            objapreendidodrogaacaopolicial.setIdUnico(valor);

            cv.put("fkAcaoPolicial", objapreendidodrogaacaopolicial.getFkAcaoPolicial());
            cv.put("dsTipoDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getDsTipoDrogaAcaoPolicial());
            cv.put("dsModoApresentacaoDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getDsModoApresentacaoDrogaAcaoPolicial());
            cv.put("Controle", objapreendidodrogaacaopolicial.getControle());
            cv.put("idUnico", objapreendidodrogaacaopolicial.getIdUnico());
            cv.put("QtdDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getQtdDrogaAcaoPolicial());
            cv.put("dsPesoGramaAcaoPolicial", objapreendidodrogaacaopolicial.getDsPesoGramaAcaoPolicial());


            return db.insert("ObjApreendidoDrogaAcaoPolicial", null, cv);
        } else {
            int codigocvli = retornarCodigoAcaoPolicial(1);
            objapreendidodrogaacaopolicial.setFkAcaoPolicial(codigocvli);
            objapreendidodrogaacaopolicial.setIdUnico(valor);

            cv.put("fkAcaoPolicial", objapreendidodrogaacaopolicial.getFkAcaoPolicial());
            cv.put("dsTipoDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getDsTipoDrogaAcaoPolicial());
            cv.put("dsModoApresentacaoDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getDsModoApresentacaoDrogaAcaoPolicial());
            cv.put("Controle", objapreendidodrogaacaopolicial.getControle());
            cv.put("idUnico", objapreendidodrogaacaopolicial.getIdUnico());
            cv.put("QtdDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getQtdDrogaAcaoPolicial());
            cv.put("dsPesoGramaAcaoPolicial", objapreendidodrogaacaopolicial.getDsPesoGramaAcaoPolicial());

            return db.insert("ObjApreendidoDrogaAcaoPolicial", null, cv);
        }

    }

    public long excluirDrogaAcaoPolicial(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("ObjApreendidoDrogaAcaoPolicial", "fkAcaoPolicial='"+fk+"'",null);
    }

    public long cadastrarDrogaAcaoPolicialAtualiza(Objapreendidodrogaacaopolicial objapreendidodrogaacaopolicial, int codigo, String valor){


        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        objapreendidodrogaacaopolicial.setFkAcaoPolicial(codigo);
        objapreendidodrogaacaopolicial.setIdUnico(valor);

        cv.put("fkAcaoPolicial", objapreendidodrogaacaopolicial.getFkAcaoPolicial());
        cv.put("dsTipoDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getDsTipoDrogaAcaoPolicial());
        cv.put("dsModoApresentacaoDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getDsModoApresentacaoDrogaAcaoPolicial());
        cv.put("Controle", objapreendidodrogaacaopolicial.getControle());
        cv.put("idUnico", objapreendidodrogaacaopolicial.getIdUnico());
        cv.put("QtdDrogaAcaoPolicial", objapreendidodrogaacaopolicial.getQtdDrogaAcaoPolicial());
        cv.put("dsPesoGramaAcaoPolicial", objapreendidodrogaacaopolicial.getDsPesoGramaAcaoPolicial());


        return db.insert("ObjApreendidoDrogaAcaoPolicial", null, cv);

    }

    public int retornarCodigoAcaoPolicial(int status){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("AcaoPolicial", new String[]{"id"}, "EstatusAcaoPolicial='" + status + "'", null, null, null, null);
        c.moveToLast();
        int valorcodigo = c.getInt(0);
        return valorcodigo;

    }

    public int retornarCodigoStatusAcaoPolicial(int id){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("AcaoPolicial", new String[]{"EstatusAcaoPolicial"}, "id='" + id + "'", null, null, null, null);
        c.moveToFirst();
        int valorcvlistatus = c.getInt(0);
        return valorcvlistatus;

    }

    public int retornarCodigoAcaoPolicialSemParametro(){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("AcaoPolicial", new String[]{"id"}, null, null, null, null, null);
        c.moveToLast();
        int codigosemparametro = c.getInt(0);
        return codigosemparametro;

    }

    public List<Objapreendidodrogaacaopolicial> retornarObjApreendidoDrogaAcaoPolicial(){

        db = conexao.getReadableDatabase();

        List<Objapreendidodrogaacaopolicial> objapreendidodrogaacaopolicials = new ArrayList<>();

        int codigoretorno = retornarCodigoAcaoPolicialSemParametro();

        int codigostatus = retornarCodigoStatusAcaoPolicial(codigoretorno);

        if (codigostatus == 0) {
            int codigocvli = retornarCodigoAcaoPolicial(0);

            Cursor cursor = db.query("ObjApreendidoDrogaAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoDrogaAcaoPolicial",
                    "dsModoApresentacaoDrogaAcaoPolicial", "Controle", "idUnico", "QtdDrogaAcaoPolicial","dsPesoGramaAcaoPolicial"}, "fkAcaoPolicial='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Objapreendidodrogaacaopolicial objapreendidodrogaacaopolicial = new Objapreendidodrogaacaopolicial();
                objapreendidodrogaacaopolicial.setId(cursor.getInt(0));
                objapreendidodrogaacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
                objapreendidodrogaacaopolicial.setDsTipoDrogaAcaoPolicial(cursor.getString(2));
                objapreendidodrogaacaopolicial.setDsModoApresentacaoDrogaAcaoPolicial(cursor.getString(3));
                objapreendidodrogaacaopolicial.setControle(cursor.getInt(4));
                objapreendidodrogaacaopolicial.setIdUnico(cursor.getString(5));
                objapreendidodrogaacaopolicial.setQtdDrogaAcaoPolicial(cursor.getInt(6));
                objapreendidodrogaacaopolicial.setDsPesoGramaAcaoPolicial(cursor.getString(7));

                objapreendidodrogaacaopolicials.add(objapreendidodrogaacaopolicial);
            }
            return objapreendidodrogaacaopolicials;

        } else {
            int codigocvli = retornarCodigoAcaoPolicial(1);

            Cursor cursor = db.query("ObjApreendidoDrogaAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoDrogaAcaoPolicial",
                    "dsModoApresentacaoDrogaAcaoPolicial", "Controle", "idUnico", "QtdDrogaAcaoPolicial","dsPesoGramaAcaoPolicial"}, "fkAcaoPolicial='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Objapreendidodrogaacaopolicial objapreendidodrogaacaopolicial = new Objapreendidodrogaacaopolicial();
                objapreendidodrogaacaopolicial.setId(cursor.getInt(0));
                objapreendidodrogaacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
                objapreendidodrogaacaopolicial.setDsTipoDrogaAcaoPolicial(cursor.getString(2));
                objapreendidodrogaacaopolicial.setDsModoApresentacaoDrogaAcaoPolicial(cursor.getString(3));
                objapreendidodrogaacaopolicial.setControle(cursor.getInt(4));
                objapreendidodrogaacaopolicial.setIdUnico(cursor.getString(5));
                objapreendidodrogaacaopolicial.setQtdDrogaAcaoPolicial(cursor.getInt(6));
                objapreendidodrogaacaopolicial.setDsPesoGramaAcaoPolicial(cursor.getString(7));

                objapreendidodrogaacaopolicials.add(objapreendidodrogaacaopolicial);
            }
            return objapreendidodrogaacaopolicials;
        }

    }

    public List<Objapreendidodrogaacaopolicial> retornarDrogaAcaoPolicialAtualizar(int fk) {

        db = conexao.getReadableDatabase();

        List<Objapreendidodrogaacaopolicial> objapreendidodrogaacaopolicials = new ArrayList<>();

        Cursor cursor = db.query("ObjApreendidoDrogaAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoDrogaAcaoPolicial",
                "dsModoApresentacaoDrogaAcaoPolicial", "Controle", "idUnico", "QtdDrogaAcaoPolicial","dsPesoGramaAcaoPolicial"}, "fkAcaoPolicial='" + fk + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Objapreendidodrogaacaopolicial objapreendidodrogaacaopolicial = new Objapreendidodrogaacaopolicial();
            objapreendidodrogaacaopolicial.setId(cursor.getInt(0));
            objapreendidodrogaacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
            objapreendidodrogaacaopolicial.setDsTipoDrogaAcaoPolicial(cursor.getString(2));
            objapreendidodrogaacaopolicial.setDsModoApresentacaoDrogaAcaoPolicial(cursor.getString(3));
            objapreendidodrogaacaopolicial.setControle(cursor.getInt(4));
            objapreendidodrogaacaopolicial.setIdUnico(cursor.getString(5));
            objapreendidodrogaacaopolicial.setQtdDrogaAcaoPolicial(cursor.getInt(6));
            objapreendidodrogaacaopolicial.setDsPesoGramaAcaoPolicial(cursor.getString(7));

            objapreendidodrogaacaopolicials.add(objapreendidodrogaacaopolicial);
        }
        return objapreendidodrogaacaopolicials;
    }
}
