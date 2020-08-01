package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Objapreendidoveiculoacaopolicial;

import java.util.ArrayList;
import java.util.List;

public class ObjapreendidoveiculoacaopolicialDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public ObjapreendidoveiculoacaopolicialDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarVeiculosPolicialWEB(Objapreendidoveiculoacaopolicial objapreendidoveiculoacaopolicial){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkAcaoPolicial", objapreendidoveiculoacaopolicial.getFkAcaoPolicial());
        cv.put("dsTipoVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsTipoVeiculoAcaoPolicial());
        cv.put("Controle", objapreendidoveiculoacaopolicial.getControle());
        cv.put("idUnico", objapreendidoveiculoacaopolicial.getIdUnico());
        cv.put("dsMarcaVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsMarcaVeiculoAcaoPolicial());
        cv.put("dsModeloVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsModeloVeiculoAcaoPolicial());
        cv.put("dsPlacaOstentadaAcaoPolicial", objapreendidoveiculoacaopolicial.getDsPlacaOstentadaAcaoPolicial());
        cv.put("dsCorVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsCorVeiculoAcaoPolicial());

        return db.insert("ObjApreendidoCarroAcaoPolicial", null, cv);
    }

    public long atualizarVeiculoAcaoPolicialWEB(Objapreendidoveiculoacaopolicial objapreendidoveiculoacaopolicial, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkAcaoPolicial", objapreendidoveiculoacaopolicial.getFkAcaoPolicial());
        cv.put("dsTipoVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsTipoVeiculoAcaoPolicial());
        cv.put("Controle", objapreendidoveiculoacaopolicial.getControle());
        cv.put("idUnico", objapreendidoveiculoacaopolicial.getIdUnico());
        cv.put("dsMarcaVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsMarcaVeiculoAcaoPolicial());
        cv.put("dsModeloVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsModeloVeiculoAcaoPolicial());
        cv.put("dsPlacaOstentadaAcaoPolicial", objapreendidoveiculoacaopolicial.getDsPlacaOstentadaAcaoPolicial());
        cv.put("dsCorVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsCorVeiculoAcaoPolicial());


        return db.update("ObjApreendidoCarroAcaoPolicial",cv, "idUnico='"+codigo+"'" ,null);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("ObjApreendidoCarroAcaoPolicial", new String[]{"fkAcaoPolicial"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public long cadastrarVeiculoAcaoPolicial(Objapreendidoveiculoacaopolicial objapreendidoveiculoacaopolicial, String valor){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigoretorno = retornarCodigoAcaoPolicialSemParametro();

        int codigostatus = retornarCodigoStatusAcaoPolicial(codigoretorno);

        if (codigostatus == 0) {

            int codigocvli = retornarCodigoAcaoPolicial(0);
            objapreendidoveiculoacaopolicial.setFkAcaoPolicial(codigocvli);
            objapreendidoveiculoacaopolicial.setIdUnico(valor);

            cv.put("fkAcaoPolicial", objapreendidoveiculoacaopolicial.getFkAcaoPolicial());
            cv.put("dsTipoVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsTipoVeiculoAcaoPolicial());
            cv.put("Controle", objapreendidoveiculoacaopolicial.getControle());
            cv.put("idUnico", objapreendidoveiculoacaopolicial.getIdUnico());
            cv.put("dsMarcaVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsMarcaVeiculoAcaoPolicial());
            cv.put("dsModeloVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsModeloVeiculoAcaoPolicial());
            cv.put("dsPlacaOstentadaAcaoPolicial", objapreendidoveiculoacaopolicial.getDsPlacaOstentadaAcaoPolicial());
            cv.put("dsCorVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsCorVeiculoAcaoPolicial());


            return db.insert("ObjApreendidoCarroAcaoPolicial", null, cv);
        } else {
            int codigocvli = retornarCodigoAcaoPolicial(1);
            objapreendidoveiculoacaopolicial.setFkAcaoPolicial(codigocvli);
            objapreendidoveiculoacaopolicial.setIdUnico(valor);

            cv.put("fkAcaoPolicial", objapreendidoveiculoacaopolicial.getFkAcaoPolicial());
            cv.put("dsTipoVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsTipoVeiculoAcaoPolicial());
            cv.put("Controle", objapreendidoveiculoacaopolicial.getControle());
            cv.put("idUnico", objapreendidoveiculoacaopolicial.getIdUnico());
            cv.put("dsMarcaVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsMarcaVeiculoAcaoPolicial());
            cv.put("dsModeloVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsModeloVeiculoAcaoPolicial());
            cv.put("dsPlacaOstentadaAcaoPolicial", objapreendidoveiculoacaopolicial.getDsPlacaOstentadaAcaoPolicial());
            cv.put("dsCorVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsCorVeiculoAcaoPolicial());

            return db.insert("ObjApreendidoCarroAcaoPolicial", null, cv);
        }

    }

    public long excluirVeiculosAcaoPolicial(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("ObjApreendidoCarroAcaoPolicial", "fkAcaoPolicial='"+fk+"'",null);
    }

    public long cadastrarVeiculosAcaoPolicialAtualiza(Objapreendidoveiculoacaopolicial objapreendidoveiculoacaopolicial, int codigo, String valor){


        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        objapreendidoveiculoacaopolicial.setFkAcaoPolicial(codigo);
        objapreendidoveiculoacaopolicial.setIdUnico(valor);

        cv.put("fkAcaoPolicial", objapreendidoveiculoacaopolicial.getFkAcaoPolicial());
        cv.put("dsTipoVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsTipoVeiculoAcaoPolicial());
        cv.put("Controle", objapreendidoveiculoacaopolicial.getControle());
        cv.put("idUnico", objapreendidoveiculoacaopolicial.getIdUnico());
        cv.put("dsMarcaVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsMarcaVeiculoAcaoPolicial());
        cv.put("dsModeloVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsModeloVeiculoAcaoPolicial());
        cv.put("dsPlacaOstentadaAcaoPolicial", objapreendidoveiculoacaopolicial.getDsPlacaOstentadaAcaoPolicial());
        cv.put("dsCorVeiculoAcaoPolicial", objapreendidoveiculoacaopolicial.getDsCorVeiculoAcaoPolicial());


        return db.insert("ObjApreendidoCarroAcaoPolicial", null, cv);

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

    public List<Objapreendidoveiculoacaopolicial> retornarObjApreendidoVeiculosAcaoPolicial(){

        db = conexao.getReadableDatabase();

        List<Objapreendidoveiculoacaopolicial> objapreendidoveiculoacaopolicials = new ArrayList<>();

        int codigoretorno = retornarCodigoAcaoPolicialSemParametro();

        int codigostatus = retornarCodigoStatusAcaoPolicial(codigoretorno);

        if (codigostatus == 0) {
            int codigocvli = retornarCodigoAcaoPolicial(0);

            Cursor cursor = db.query("ObjApreendidoCarroAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoVeiculoAcaoPolicial",
                    "Controle", "idUnico", "dsMarcaVeiculoAcaoPolicial","dsModeloVeiculoAcaoPolicial","dsPlacaOstentadaAcaoPolicial","dsCorVeiculoAcaoPolicial"}, "fkAcaoPolicial='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Objapreendidoveiculoacaopolicial objapreendidoveiculoacaopolicial = new Objapreendidoveiculoacaopolicial();
                objapreendidoveiculoacaopolicial.setId(cursor.getInt(0));
                objapreendidoveiculoacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
                objapreendidoveiculoacaopolicial.setDsTipoVeiculoAcaoPolicial(cursor.getString(2));
                objapreendidoveiculoacaopolicial.setControle(cursor.getInt(3));
                objapreendidoveiculoacaopolicial.setIdUnico(cursor.getString(4));
                objapreendidoveiculoacaopolicial.setDsMarcaVeiculoAcaoPolicial(cursor.getString(5));
                objapreendidoveiculoacaopolicial.setDsModeloVeiculoAcaoPolicial(cursor.getString(6));
                objapreendidoveiculoacaopolicial.setDsPlacaOstentadaAcaoPolicial(cursor.getString(7));
                objapreendidoveiculoacaopolicial.setDsCorVeiculoAcaoPolicial(cursor.getString(8));

                objapreendidoveiculoacaopolicials.add(objapreendidoveiculoacaopolicial);
            }
            return objapreendidoveiculoacaopolicials;

        } else {
            int codigocvli = retornarCodigoAcaoPolicial(1);

            Cursor cursor = db.query("ObjApreendidoCarroAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoVeiculoAcaoPolicial",
                    "Controle", "idUnico", "dsMarcaVeiculoAcaoPolicial","dsModeloVeiculoAcaoPolicial","dsPlacaOstentadaAcaoPolicial","dsCorVeiculoAcaoPolicial"}, "fkAcaoPolicial='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Objapreendidoveiculoacaopolicial objapreendidoveiculoacaopolicial = new Objapreendidoveiculoacaopolicial();
                objapreendidoveiculoacaopolicial.setId(cursor.getInt(0));
                objapreendidoveiculoacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
                objapreendidoveiculoacaopolicial.setDsTipoVeiculoAcaoPolicial(cursor.getString(2));
                objapreendidoveiculoacaopolicial.setControle(cursor.getInt(3));
                objapreendidoveiculoacaopolicial.setIdUnico(cursor.getString(4));
                objapreendidoveiculoacaopolicial.setDsMarcaVeiculoAcaoPolicial(cursor.getString(5));
                objapreendidoveiculoacaopolicial.setDsModeloVeiculoAcaoPolicial(cursor.getString(6));
                objapreendidoveiculoacaopolicial.setDsPlacaOstentadaAcaoPolicial(cursor.getString(7));
                objapreendidoveiculoacaopolicial.setDsCorVeiculoAcaoPolicial(cursor.getString(8));

                objapreendidoveiculoacaopolicials.add(objapreendidoveiculoacaopolicial);
            }
            return objapreendidoveiculoacaopolicials;
        }

    }

    public List<Objapreendidoveiculoacaopolicial> retornarVeiculosAcaoPolicialAtualizar(int fk) {

        db = conexao.getReadableDatabase();

        List<Objapreendidoveiculoacaopolicial> objapreendidoveiculoacaopolicials = new ArrayList<>();

        Cursor cursor = db.query("ObjApreendidoCarroAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoVeiculoAcaoPolicial",
                "Controle", "idUnico", "dsMarcaVeiculoAcaoPolicial","dsModeloVeiculoAcaoPolicial","dsPlacaOstentadaAcaoPolicial","dsCorVeiculoAcaoPolicial"}, "fkAcaoPolicial='" + fk + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Objapreendidoveiculoacaopolicial objapreendidoveiculoacaopolicial = new Objapreendidoveiculoacaopolicial();
            objapreendidoveiculoacaopolicial.setId(cursor.getInt(0));
            objapreendidoveiculoacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
            objapreendidoveiculoacaopolicial.setDsTipoVeiculoAcaoPolicial(cursor.getString(2));
            objapreendidoveiculoacaopolicial.setControle(cursor.getInt(3));
            objapreendidoveiculoacaopolicial.setIdUnico(cursor.getString(4));
            objapreendidoveiculoacaopolicial.setDsMarcaVeiculoAcaoPolicial(cursor.getString(5));
            objapreendidoveiculoacaopolicial.setDsModeloVeiculoAcaoPolicial(cursor.getString(6));
            objapreendidoveiculoacaopolicial.setDsPlacaOstentadaAcaoPolicial(cursor.getString(7));
            objapreendidoveiculoacaopolicial.setDsCorVeiculoAcaoPolicial(cursor.getString(8));

            objapreendidoveiculoacaopolicials.add(objapreendidoveiculoacaopolicial);
        }
        return objapreendidoveiculoacaopolicials;
    }
}
