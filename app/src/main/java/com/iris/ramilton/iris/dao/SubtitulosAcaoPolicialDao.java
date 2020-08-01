package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Objapreendidoarmaacaopolicial;
import com.iris.ramilton.iris.modelo.SubtitulosAcaoPolicial;

public class SubtitulosAcaoPolicialDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public SubtitulosAcaoPolicialDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("SubtitulosAcaoPolicial", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public long cadastrarSubtitulosAcaoPolicial(SubtitulosAcaoPolicial subtitulosAcaoPolicial, String valor){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigoretorno = retornarCodigoAcaoPolicialSemParametro();

        int codigostatus = retornarCodigoStatusAcaoPolicial(codigoretorno);

        if (codigostatus == 0) {

            int codigocvli = retornarCodigoAcaoPolicial(0);
            subtitulosAcaoPolicial.setFkAcaoPolicial(codigocvli);
            subtitulosAcaoPolicial.setIdUnico(valor);

            cv.put("fkAcaoPolicial", subtitulosAcaoPolicial.getFkAcaoPolicial());
            cv.put("dsSubtituloAbertoAcaoPolicial", subtitulosAcaoPolicial.getDsSubtituloAbertoAcaoPolicial());
            cv.put("TextoSubtituloAcaoPolicial", subtitulosAcaoPolicial.getTextoSubtituloAcaoPolicial());
            cv.put("Controle", subtitulosAcaoPolicial.getControle());
            cv.put("idUnico", subtitulosAcaoPolicial.getIdUnico());

            return db.insert("SubtitulosAcaoPolicial", null, cv);
        } else {
            int codigocvli = retornarCodigoAcaoPolicial(1);
            subtitulosAcaoPolicial.setFkAcaoPolicial(codigocvli);
            subtitulosAcaoPolicial.setIdUnico(valor);

            cv.put("fkAcaoPolicial", subtitulosAcaoPolicial.getFkAcaoPolicial());
            cv.put("dsSubtituloAbertoAcaoPolicial", subtitulosAcaoPolicial.getDsSubtituloAbertoAcaoPolicial());
            cv.put("TextoSubtituloAcaoPolicial", subtitulosAcaoPolicial.getTextoSubtituloAcaoPolicial());
            cv.put("Controle", subtitulosAcaoPolicial.getControle());
            cv.put("idUnico", subtitulosAcaoPolicial.getIdUnico());


            return db.insert("SubtitulosAcaoPolicial", null, cv);
        }

    }

    public long excluirSubtitulosAcaoPolicial(int fk){
        db = conexao.getWritableDatabase();
        return db.delete("SubtitulosAcaoPolicial", "fkAcaoPolicial='"+fk+"'",null);
    }

    public long cadastrarSubtitulosAcaoPolicialAtualiza(SubtitulosAcaoPolicial subtitulosAcaoPolicial, int codigo, String valor){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        subtitulosAcaoPolicial.setFkAcaoPolicial(codigo);
        subtitulosAcaoPolicial.setIdUnico(valor);

        cv.put("fkAcaoPolicial", subtitulosAcaoPolicial.getFkAcaoPolicial());
        cv.put("dsSubtituloAbertoAcaoPolicial", subtitulosAcaoPolicial.getDsSubtituloAbertoAcaoPolicial());
        cv.put("TextoSubtituloAcaoPolicial", subtitulosAcaoPolicial.getTextoSubtituloAcaoPolicial());
        cv.put("Controle", subtitulosAcaoPolicial.getControle());
        cv.put("idUnico", subtitulosAcaoPolicial.getIdUnico());


        return db.insert("SubtitulosAcaoPolicial", null, cv);

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

}
