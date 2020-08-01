package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iris.ramilton.iris.GuiasProvidenciasActivity;
import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Equipepreservacaolocal;
import com.iris.ramilton.iris.modelo.GuiapericiaProvidencia;

import java.util.ArrayList;
import java.util.List;

public class GuiaPericiaProvidenciaDao {

    private DatabaseHelper conexao;
    private SQLiteDatabase db;

    public GuiaPericiaProvidenciaDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarGuiaPericialProvidenciaWEB(GuiapericiaProvidencia guiapericiaProvidencia){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

            cv.put("fkCvli", guiapericiaProvidencia.getFkCvli());
            cv.put("dsGuiaPericial", guiapericiaProvidencia.getDsGuiaPericial());
            cv.put("dsNGuiaPericial", guiapericiaProvidencia.getDsNGuiaPericial());
            cv.put("Controle", guiapericiaProvidencia.getControle());
            cv.put("idUnico", guiapericiaProvidencia.getIdUnico());


            return db.insert("cvliguiaspericiais", null, cv);
    }

    public long atualizaGuiaPericialProvidenciaWEB(GuiapericiaProvidencia guiapericiaProvidencia, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkCvli", guiapericiaProvidencia.getFkCvli());
        cv.put("dsGuiaPericial", guiapericiaProvidencia.getDsGuiaPericial());
        cv.put("dsNGuiaPericial", guiapericiaProvidencia.getDsNGuiaPericial());
        cv.put("Controle", guiapericiaProvidencia.getControle());
        cv.put("idUnico", guiapericiaProvidencia.getIdUnico());


        return db.update("cvliguiaspericiais",cv, "idUnico='"+codigo+"'", null);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvliguiaspericiais", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public long cadastrarGuiaPericialProvidencia(GuiapericiaProvidencia guiapericiaProvidencia, String valor){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigoretorno = retornarCodigoCvliSemParametro();

        int codigostatus = retornarCodigoStatusCvli(codigoretorno);

        if (codigostatus == 0) {

            int codigocvli = retornarCodigoCvli(0);
            guiapericiaProvidencia.setFkCvli(codigocvli);
            guiapericiaProvidencia.setIdUnico(valor);

            cv.put("fkCvli", guiapericiaProvidencia.getFkCvli());
            cv.put("dsGuiaPericial", guiapericiaProvidencia.getDsGuiaPericial());
            cv.put("dsNGuiaPericial", guiapericiaProvidencia.getDsNGuiaPericial());
            cv.put("Controle", guiapericiaProvidencia.getControle());
            cv.put("idUnico", guiapericiaProvidencia.getIdUnico());


            return db.insert("cvliguiaspericiais", null, cv);
        } else {
            int codigocvli = retornarCodigoCvli(1);
            guiapericiaProvidencia.setFkCvli(codigocvli);
            guiapericiaProvidencia.setIdUnico(valor);
            cv.put("fkCvli", guiapericiaProvidencia.getFkCvli());
            cv.put("dsGuiaPericial", guiapericiaProvidencia.getDsGuiaPericial());
            cv.put("dsNGuiaPericial", guiapericiaProvidencia.getDsNGuiaPericial());
            cv.put("Controle", guiapericiaProvidencia.getControle());
            cv.put("idUnico", guiapericiaProvidencia.getIdUnico());

            return db.insert("cvliguiaspericiais", null, cv);
        }

    }

    public long excluirGuiasPericiais(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("cvliguiaspericiais","fkCvli",null);
    }

    public long cadastrarGuiaPericialProvidenciaAtualiza(GuiapericiaProvidencia guiapericiaProvidencia, int codigo, String valor){


        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        guiapericiaProvidencia.setFkCvli(codigo);
        guiapericiaProvidencia.setIdUnico(valor);

        cv.put("fkCvli", guiapericiaProvidencia.getFkCvli());
        cv.put("dsGuiaPericial", guiapericiaProvidencia.getDsGuiaPericial());
        cv.put("dsNGuiaPericial", guiapericiaProvidencia.getDsNGuiaPericial());
        cv.put("Controle", guiapericiaProvidencia.getControle());
        cv.put("idUnico", guiapericiaProvidencia.getIdUnico());

        return db.insert("cvliguiaspericiais", null, cv);

    }

    public int retornarCodigoCvli(int status){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvlis", new String[]{"id"}, "EstatusCVLI='" + status + "'", null, null, null, null);
        c.moveToLast();
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

    public List<GuiapericiaProvidencia> retornarGuiaPericiaProvidencia(){


        db = conexao.getReadableDatabase();

        List<GuiapericiaProvidencia> guiapericiaProvidenciaslista = new ArrayList<>();

        int codigoretorno = retornarCodigoCvliSemParametro();

        int codigostatus = retornarCodigoStatusCvli(codigoretorno);

        if (codigostatus == 0) {

            int codigocvli = retornarCodigoCvli(0);

            Cursor cursor = db.query("cvliguiaspericiais", new String[]{"id", "fkCvli", "dsGuiaPericial",
                    "dsNGuiaPericial", "Controle"}, "fkCvli='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                GuiapericiaProvidencia guiapericiaProvidencia = new GuiapericiaProvidencia();
                guiapericiaProvidencia.setId(cursor.getInt(0));
                guiapericiaProvidencia.setFkCvli(cursor.getInt(1));
                guiapericiaProvidencia.setDsGuiaPericial(cursor.getString(2));
                guiapericiaProvidencia.setDsNGuiaPericial(cursor.getString(3));
                guiapericiaProvidencia.setControle(cursor.getInt(4));

                guiapericiaProvidenciaslista.add(guiapericiaProvidencia);
            }
            return guiapericiaProvidenciaslista;
        } else {
            int codigocvli = retornarCodigoCvli(1);

            Cursor cursor = db.query("cvliguiaspericiais", new String[]{"id", "fkCvli", "dsGuiaPericial",
                    "dsNGuiaPericial", "Controle"}, "fkCvli='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                GuiapericiaProvidencia guiapericiaProvidencia = new GuiapericiaProvidencia();
                guiapericiaProvidencia.setId(cursor.getInt(0));
                guiapericiaProvidencia.setFkCvli(cursor.getInt(1));
                guiapericiaProvidencia.setDsGuiaPericial(cursor.getString(2));
                guiapericiaProvidencia.setDsNGuiaPericial(cursor.getString(3));
                guiapericiaProvidencia.setControle(cursor.getInt(4));

                guiapericiaProvidenciaslista.add(guiapericiaProvidencia);
            }
            return guiapericiaProvidenciaslista;
        }

    }

    public List<GuiapericiaProvidencia> retornarGuiaPericiaProvidenciaAtualizar(int fk) {

        db = conexao.getReadableDatabase();

        List<GuiapericiaProvidencia> guiapericiaProvidenciasatualizar = new ArrayList<>();

        Cursor cursor = db.query("cvliguiaspericiais", new String[]{"id", "fkCvli", "dsGuiaPericial",
                "dsNGuiaPericial", "Controle"}, "fkCvli='" + fk + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            GuiapericiaProvidencia guiapericiaProvidencia = new GuiapericiaProvidencia();
            guiapericiaProvidencia.setId(cursor.getInt(0));
            guiapericiaProvidencia.setFkCvli(cursor.getInt(1));
            guiapericiaProvidencia.setDsGuiaPericial(cursor.getString(2));
            guiapericiaProvidencia.setDsNGuiaPericial(cursor.getString(3));
            guiapericiaProvidencia.setControle(cursor.getInt(4));

            guiapericiaProvidenciasatualizar.add(guiapericiaProvidencia);
        }
        return guiapericiaProvidenciasatualizar;

    }

}
