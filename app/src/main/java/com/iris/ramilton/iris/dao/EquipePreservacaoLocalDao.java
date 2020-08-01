package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Equipeperito;
import com.iris.ramilton.iris.modelo.Equipepreservacaolocal;

import java.util.ArrayList;
import java.util.List;

public class EquipePreservacaoLocalDao {

    private DatabaseHelper conexao;
    private SQLiteDatabase db;

    public EquipePreservacaoLocalDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarCVLIEquipePreservacaoLocalWEB(Equipepreservacaolocal equipepreservacaolocal){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

            cv.put("fkCvli", equipepreservacaolocal.getFkCvli());
            cv.put("dsCargoEquipepreservacaolocal", equipepreservacaolocal.getDsCargoEquipepreservacaolocal());
            cv.put("dsNomeEquipepreservacaolocal", equipepreservacaolocal.getDsNomeEquipepreservacaolocal());
            cv.put("Controle", equipepreservacaolocal.getControle());
            cv.put("idUnico", equipepreservacaolocal.getIdUnico());


            return db.insert("cvliequipepreservacaolocals", null, cv);
    }

    public long atualizarCVLIEquipePreservacaoLocalWEB(Equipepreservacaolocal equipepreservacaolocal, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkCvli", equipepreservacaolocal.getFkCvli());
        cv.put("dsCargoEquipepreservacaolocal", equipepreservacaolocal.getDsCargoEquipepreservacaolocal());
        cv.put("dsNomeEquipepreservacaolocal", equipepreservacaolocal.getDsNomeEquipepreservacaolocal());
        cv.put("Controle", equipepreservacaolocal.getControle());
        cv.put("idUnico", equipepreservacaolocal.getIdUnico());


        return db.update("cvliequipepreservacaolocals", cv,"idUnico='"+codigo+"'", null);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvliequipepreservacaolocals", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

        public long cadastrarCVLIEquipePreservacaoLocal(Equipepreservacaolocal equipepreservacaolocal, String valor){

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {

                int codigocvli = retornarCodigoCvli(0);
                equipepreservacaolocal.setFkCvli(codigocvli);
                equipepreservacaolocal.setIdUnico(valor);

                cv.put("fkCvli", equipepreservacaolocal.getFkCvli());
                cv.put("dsCargoEquipepreservacaolocal", equipepreservacaolocal.getDsCargoEquipepreservacaolocal());
                cv.put("dsNomeEquipepreservacaolocal", equipepreservacaolocal.getDsNomeEquipepreservacaolocal());
                cv.put("Controle", equipepreservacaolocal.getControle());
                cv.put("idUnico", equipepreservacaolocal.getIdUnico());


                return db.insert("cvliequipepreservacaolocals", null, cv);
            } else {
                int codigocvli = retornarCodigoCvli(1);
                equipepreservacaolocal.setFkCvli(codigocvli);
                equipepreservacaolocal.setIdUnico(valor);
                cv.put("fkCvli", equipepreservacaolocal.getFkCvli());
                cv.put("dsCargoEquipepreservacaolocal", equipepreservacaolocal.getDsCargoEquipepreservacaolocal());
                cv.put("dsNomeEquipepreservacaolocal", equipepreservacaolocal.getDsNomeEquipepreservacaolocal());
                cv.put("Controle", equipepreservacaolocal.getControle());
                cv.put("idUnico", equipepreservacaolocal.getIdUnico());

                return db.insert("cvliequipepreservacaolocals", null, cv);
            }

    }

    public long excluirEquipepreservacaoLocal(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("cvliequipepreservacaolocals","fkCvli",null);
    }

    public long cadastrarCVLIEquipePreservacaoLocalAtualiza(Equipepreservacaolocal equipepreservacaolocal, int codigo, String valor){


            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            equipepreservacaolocal.setFkCvli(codigo);
            equipepreservacaolocal.setIdUnico(valor);

            cv.put("fkCvli", equipepreservacaolocal.getFkCvli());
            cv.put("dsCargoEquipepreservacaolocal", equipepreservacaolocal.getDsCargoEquipepreservacaolocal());
            cv.put("dsNomeEquipepreservacaolocal", equipepreservacaolocal.getDsNomeEquipepreservacaolocal());
            cv.put("Controle", equipepreservacaolocal.getControle());
            cv.put("idUnico", equipepreservacaolocal.getIdUnico());

            return db.insert("cvliequipepreservacaolocals", null, cv);

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

    public List<Equipepreservacaolocal> retornarEquipepreservacaolocal(){


            db = conexao.getReadableDatabase();

            List<Equipepreservacaolocal> equipepreservacaolocal = new ArrayList<>();

            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {

                int codigocvli = retornarCodigoCvli(0);

                Cursor cursor = db.query("cvliequipepreservacaolocals", new String[]{"id", "fkCvli", "dsCargoEquipepreservacaolocal",
                        "dsNomeEquipepreservacaolocal", "Controle"}, "fkCvli='" + codigocvli + "'", null, null, null, null);

                while (cursor.moveToNext()) {
                    Equipepreservacaolocal equiPreservacaolocal = new Equipepreservacaolocal();
                    equiPreservacaolocal.setId(cursor.getInt(0));
                    equiPreservacaolocal.setFkCvli(cursor.getInt(1));
                    equiPreservacaolocal.setDsCargoEquipepreservacaolocal(cursor.getString(2));
                    equiPreservacaolocal.setDsNomeEquipepreservacaolocal(cursor.getString(3));
                    equiPreservacaolocal.setControle(cursor.getInt(4));

                    equipepreservacaolocal.add(equiPreservacaolocal);
                }
                return equipepreservacaolocal;
            } else {
                int codigocvli = retornarCodigoCvli(1);

                Cursor cursor = db.query("cvliequipepreservacaolocals", new String[]{"id", "fkCvli", "dsCargoEquipepreservacaolocal",
                        "dsNomeEquipepreservacaolocal", "Controle"}, "fkCvli='" + codigocvli + "'", null, null, null, null);

                while (cursor.moveToNext()) {
                    Equipepreservacaolocal equiPreservacaolocal = new Equipepreservacaolocal();
                    equiPreservacaolocal.setId(cursor.getInt(0));
                    equiPreservacaolocal.setFkCvli(cursor.getInt(1));
                    equiPreservacaolocal.setDsCargoEquipepreservacaolocal(cursor.getString(2));
                    equiPreservacaolocal.setDsNomeEquipepreservacaolocal(cursor.getString(3));
                    equiPreservacaolocal.setControle(cursor.getInt(4));

                    equipepreservacaolocal.add(equiPreservacaolocal);
                }
                return equipepreservacaolocal;
            }

    }

    public List<Equipepreservacaolocal> retornarEquipepreservacaolocalAtualizar(int fk) {

            db = conexao.getReadableDatabase();

            List<Equipepreservacaolocal> equipepreservacaolocalatualizar = new ArrayList<>();

            Cursor cursor = db.query("cvliequipepreservacaolocals", new String[]{"id", "fkCvli", "dsCargoEquipepreservacaolocal",
                    "dsNomeEquipepreservacaolocal", "Controle"}, "fkCvli='" + fk + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Equipepreservacaolocal equiPreservacaolocal = new Equipepreservacaolocal();
                equiPreservacaolocal.setId(cursor.getInt(0));
                equiPreservacaolocal.setFkCvli(cursor.getInt(1));
                equiPreservacaolocal.setDsCargoEquipepreservacaolocal(cursor.getString(2));
                equiPreservacaolocal.setDsNomeEquipepreservacaolocal(cursor.getString(3));
                equiPreservacaolocal.setControle(cursor.getInt(4));

                equipepreservacaolocalatualizar.add(equiPreservacaolocal);
            }
            return equipepreservacaolocalatualizar;

    }
}
