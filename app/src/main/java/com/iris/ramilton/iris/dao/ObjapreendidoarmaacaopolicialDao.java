package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Objapreendidoarmaacaopolicial;

import java.util.ArrayList;
import java.util.List;

public class ObjapreendidoarmaacaopolicialDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public ObjapreendidoarmaacaopolicialDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarArmaAcaoPolicialWEB(Objapreendidoarmaacaopolicial objapreendidoarmaacaopolicial){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkAcaoPolicial", objapreendidoarmaacaopolicial.getFkAcaoPolicial());
        cv.put("dsTipoArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsTipoArmaAcaoPolicial());
        cv.put("dsModeloArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsModeloArmaAcaoPolicial());
        cv.put("Controle", objapreendidoarmaacaopolicial.getControle());
        cv.put("idUnico", objapreendidoarmaacaopolicial.getIdUnico());
        cv.put("dsCalibreArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsCalibreArmaAcaoPolicial());
        cv.put("QtdArmaAcaoPolicial", objapreendidoarmaacaopolicial.getQtdArmaAcaoPolicial());

        return db.insert("ObjApreendidoArmaAcaoPolicial", null, cv);
    }

    public long atualizarArmaAcaoPolicialWEB(Objapreendidoarmaacaopolicial objapreendidoarmaacaopolicial, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkAcaoPolicial", objapreendidoarmaacaopolicial.getFkAcaoPolicial());
        cv.put("dsTipoArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsTipoArmaAcaoPolicial());
        cv.put("dsModeloArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsModeloArmaAcaoPolicial());
        cv.put("Controle", objapreendidoarmaacaopolicial.getControle());
        cv.put("idUnico", objapreendidoarmaacaopolicial.getIdUnico());
        cv.put("dsCalibreArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsCalibreArmaAcaoPolicial());
        cv.put("QtdArmaAcaoPolicial", objapreendidoarmaacaopolicial.getQtdArmaAcaoPolicial());

        return db.update("ObjApreendidoArmaAcaoPolicial",cv, "idUnico='"+codigo+"'" ,null);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("ObjApreendidoArmaAcaoPolicial", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public long cadastrarArmaAcaoPolicial(Objapreendidoarmaacaopolicial objapreendidoarmaacaopolicial, String valor){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigoretorno = retornarCodigoAcaoPolicialSemParametro();

        int codigostatus = retornarCodigoStatusAcaoPolicial(codigoretorno);

        if (codigostatus == 0) {

            int codigocvli = retornarCodigoAcaoPolicial(0);
            objapreendidoarmaacaopolicial.setFkAcaoPolicial(codigocvli);
            objapreendidoarmaacaopolicial.setIdUnico(valor);

            cv.put("fkAcaoPolicial", objapreendidoarmaacaopolicial.getFkAcaoPolicial());
            cv.put("dsTipoArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsTipoArmaAcaoPolicial());
            cv.put("dsModeloArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsModeloArmaAcaoPolicial());
            cv.put("Controle", objapreendidoarmaacaopolicial.getControle());
            cv.put("idUnico", objapreendidoarmaacaopolicial.getIdUnico());
            cv.put("dsCalibreArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsCalibreArmaAcaoPolicial());
            cv.put("QtdArmaAcaoPolicial", objapreendidoarmaacaopolicial.getQtdArmaAcaoPolicial());


            return db.insert("ObjApreendidoArmaAcaoPolicial", null, cv);
        } else {
            int codigocvli = retornarCodigoAcaoPolicial(1);
            objapreendidoarmaacaopolicial.setFkAcaoPolicial(codigocvli);
            objapreendidoarmaacaopolicial.setIdUnico(valor);

            cv.put("fkAcaoPolicial", objapreendidoarmaacaopolicial.getFkAcaoPolicial());
            cv.put("dsTipoArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsTipoArmaAcaoPolicial());
            cv.put("dsModeloArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsModeloArmaAcaoPolicial());
            cv.put("Controle", objapreendidoarmaacaopolicial.getControle());
            cv.put("idUnico", objapreendidoarmaacaopolicial.getIdUnico());
            cv.put("dsCalibreArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsCalibreArmaAcaoPolicial());
            cv.put("QtdArmaAcaoPolicial", objapreendidoarmaacaopolicial.getQtdArmaAcaoPolicial());


            return db.insert("ObjApreendidoArmaAcaoPolicial", null, cv);
        }

    }

    public long excluirArmaAcaoPolicial(int fk){
        db = conexao.getWritableDatabase();
        return db.delete("ObjApreendidoArmaAcaoPolicial", "fkAcaoPolicial='"+fk+"'",null);
    }

    public long cadastrarArmaAcaoPolicialAtualiza(Objapreendidoarmaacaopolicial objapreendidoarmaacaopolicial, int codigo, String valor){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        objapreendidoarmaacaopolicial.setFkAcaoPolicial(codigo);
        objapreendidoarmaacaopolicial.setIdUnico(valor);

        cv.put("fkAcaoPolicial", objapreendidoarmaacaopolicial.getFkAcaoPolicial());
        cv.put("dsTipoArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsTipoArmaAcaoPolicial());
        cv.put("dsModeloArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsModeloArmaAcaoPolicial());
        cv.put("Controle", objapreendidoarmaacaopolicial.getControle());
        cv.put("idUnico", objapreendidoarmaacaopolicial.getIdUnico());
        cv.put("dsCalibreArmaAcaoPolicial", objapreendidoarmaacaopolicial.getDsCalibreArmaAcaoPolicial());
        cv.put("QtdArmaAcaoPolicial", objapreendidoarmaacaopolicial.getQtdArmaAcaoPolicial());


        return db.insert("ObjApreendidoArmaAcaoPolicial", null, cv);

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

    public List<Objapreendidoarmaacaopolicial> retornarObjApreendidoAcaoPolicial(){

        db = conexao.getReadableDatabase();

        List<Objapreendidoarmaacaopolicial> objapreendidoarmaacaopolicials = new ArrayList<>();

        int codigoretorno = retornarCodigoAcaoPolicialSemParametro();

        int codigostatus = retornarCodigoStatusAcaoPolicial(codigoretorno);

        if (codigostatus == 0) {
            int codigocvli = retornarCodigoAcaoPolicial(0);

            Cursor cursor = db.query("ObjApreendidoArmaAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoArmaAcaoPolicial",
                    "dsModeloArmaAcaoPolicial", "Controle", "idUnico", "dsCalibreArmaAcaoPolicial","QtdArmaAcaoPolicial"}, "fkAcaoPolicial='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Objapreendidoarmaacaopolicial objapreendidoarmaacaopolicial = new Objapreendidoarmaacaopolicial();
                objapreendidoarmaacaopolicial.setId(cursor.getInt(0));
                objapreendidoarmaacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
                objapreendidoarmaacaopolicial.setDsTipoArmaAcaoPolicial(cursor.getString(2));
                objapreendidoarmaacaopolicial.setDsModeloArmaAcaoPolicial(cursor.getString(3));
                objapreendidoarmaacaopolicial.setControle(cursor.getInt(4));
                objapreendidoarmaacaopolicial.setIdUnico(cursor.getString(5));
                objapreendidoarmaacaopolicial.setDsCalibreArmaAcaoPolicial(cursor.getString(6));
                objapreendidoarmaacaopolicial.setQtdArmaAcaoPolicial(cursor.getInt(7));

                objapreendidoarmaacaopolicials.add(objapreendidoarmaacaopolicial);
            }
            return objapreendidoarmaacaopolicials;

        } else {
            int codigocvli = retornarCodigoAcaoPolicial(1);

            Cursor cursor = db.query("ObjApreendidoArmaAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoArmaAcaoPolicial",
                    "dsModeloArmaAcaoPolicial", "Controle", "idUnico", "dsCalibreArmaAcaoPolicial","QtdArmaAcaoPolicial"}, "fkAcaoPolicial='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Objapreendidoarmaacaopolicial objapreendidoarmaacaopolicial = new Objapreendidoarmaacaopolicial();
                objapreendidoarmaacaopolicial.setId(cursor.getInt(0));
                objapreendidoarmaacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
                objapreendidoarmaacaopolicial.setDsTipoArmaAcaoPolicial(cursor.getString(2));
                objapreendidoarmaacaopolicial.setDsModeloArmaAcaoPolicial(cursor.getString(3));
                objapreendidoarmaacaopolicial.setControle(cursor.getInt(4));
                objapreendidoarmaacaopolicial.setIdUnico(cursor.getString(5));
                objapreendidoarmaacaopolicial.setDsCalibreArmaAcaoPolicial(cursor.getString(6));
                objapreendidoarmaacaopolicial.setQtdArmaAcaoPolicial(cursor.getInt(7));

                objapreendidoarmaacaopolicials.add(objapreendidoarmaacaopolicial);
            }
            return objapreendidoarmaacaopolicials;
        }

    }

    public List<Objapreendidoarmaacaopolicial> retornarArmaAcaoPolicialAtualizar(int fk) {

        db = conexao.getReadableDatabase();

        List<Objapreendidoarmaacaopolicial> objapreendidoarmaacaopolicials = new ArrayList<>();

        Cursor cursor = db.query("ObjApreendidoArmaAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoArmaAcaoPolicial",
                "dsModeloArmaAcaoPolicial", "Controle", "idUnico", "dsCalibreArmaAcaoPolicial","QtdArmaAcaoPolicial"}, "fkAcaoPolicial='" + fk + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Objapreendidoarmaacaopolicial objapreendidoarmaacaopolicial = new Objapreendidoarmaacaopolicial();
            objapreendidoarmaacaopolicial.setId(cursor.getInt(0));
            objapreendidoarmaacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
            objapreendidoarmaacaopolicial.setDsTipoArmaAcaoPolicial(cursor.getString(2));
            objapreendidoarmaacaopolicial.setDsModeloArmaAcaoPolicial(cursor.getString(3));
            objapreendidoarmaacaopolicial.setControle(cursor.getInt(4));
            objapreendidoarmaacaopolicial.setIdUnico(cursor.getString(5));
            objapreendidoarmaacaopolicial.setDsCalibreArmaAcaoPolicial(cursor.getString(6));
            objapreendidoarmaacaopolicial.setQtdArmaAcaoPolicial(cursor.getInt(7));

            objapreendidoarmaacaopolicials.add(objapreendidoarmaacaopolicial);
        }
        return objapreendidoarmaacaopolicials;
    }

}
