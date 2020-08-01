package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Equipelevantamento;
import com.iris.ramilton.iris.modelo.Equipeperito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EquipePeritoDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public EquipePeritoDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarCVLIEquipePeritoWEB(Equipeperito equipeperito){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

            cv.put("fkCvli", equipeperito.getFkCvli());
            cv.put("dsCargoEquipePerito", equipeperito.getDsCargoEquipePerito());
            cv.put("dsNomeEquipePerito", equipeperito.getDsNomeEquipePerito());
            cv.put("Controle", equipeperito.getControle());
            cv.put("idUnico", equipeperito.getIdUnico());

            return db.insert("cvliequipeperitos", null, cv);
    }

    public long atualizarCVLIEquipePeritoWEB(Equipeperito equipeperito, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkCvli", equipeperito.getFkCvli());
        cv.put("dsCargoEquipePerito", equipeperito.getDsCargoEquipePerito());
        cv.put("dsNomeEquipePerito", equipeperito.getDsNomeEquipePerito());
        cv.put("Controle", equipeperito.getControle());
        cv.put("idUnico", equipeperito.getIdUnico());

        return db.update("cvliequipeperitos",cv, "idUnico='"+codigo+"'" ,null);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvliequipeperitos", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public long cadastrarCVLIEquipePerito(Equipeperito equipeperito, String valor){

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {

                int codigocvli = retornarCodigoCvli(0);
                equipeperito.setFkCvli(codigocvli);
                equipeperito.setIdUnico(valor);

                cv.put("fkCvli", equipeperito.getFkCvli());
                cv.put("dsCargoEquipePerito", equipeperito.getDsCargoEquipePerito());
                cv.put("dsNomeEquipePerito", equipeperito.getDsNomeEquipePerito());
                cv.put("Controle", equipeperito.getControle());
                cv.put("idUnico", equipeperito.getIdUnico());


                return db.insert("cvliequipeperitos", null, cv);
            } else {
                int codigocvli = retornarCodigoCvli(1);
                equipeperito.setFkCvli(codigocvli);
                equipeperito.setIdUnico(valor);

                cv.put("fkCvli", equipeperito.getFkCvli());
                cv.put("dsCargoEquipePerito", equipeperito.getDsCargoEquipePerito());
                cv.put("dsNomeEquipePerito", equipeperito.getDsNomeEquipePerito());
                cv.put("Controle", equipeperito.getControle());
                cv.put("idUnico", equipeperito.getIdUnico());


                return db.insert("cvliequipeperitos", null, cv);
            }

    }

    public long excluirEquipePerito(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("cvliequipeperitos", "fkCvli='"+fk+"'",null);
    }

    public long cadastrarCVLIEquipePeritoAtualiza(Equipeperito equipeperito, int codigo, String valor){


            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            equipeperito.setFkCvli(codigo);
            equipeperito.setIdUnico(valor);

            cv.put("fkCvli", equipeperito.getFkCvli());
            cv.put("dsCargoEquipePerito", equipeperito.getDsCargoEquipePerito());
            cv.put("dsNomeEquipePerito", equipeperito.getDsNomeEquipePerito());
            cv.put("Controle", equipeperito.getControle());
            cv.put("idUnico", equipeperito.getIdUnico());


            return db.insert("cvliequipeperitos", null, cv);

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

    public List<Equipeperito> retornarEquipePerito(){

            db = conexao.getReadableDatabase();


            List<Equipeperito> equipeperito = new ArrayList<>();

            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {
                int codigocvli = retornarCodigoCvli(0);

                Cursor cursor = db.query("cvliequipeperitos", new String[]{"id", "fkCvli", "dsCargoEquipePerito",
                        "dsNomeEquipePerito", "Controle"}, "fkCvli='" + codigocvli + "'", null, null, null, null);

                while (cursor.moveToNext()) {
                    Equipeperito equiPerito = new Equipeperito();
                    equiPerito.setId(cursor.getInt(0));
                    equiPerito.setFkCvli(cursor.getInt(1));
                    equiPerito.setDsCargoEquipePerito(cursor.getString(2));
                    equiPerito.setDsNomeEquipePerito(cursor.getString(3));
                    equiPerito.setControle(cursor.getInt(4));

                    equipeperito.add(equiPerito);
                }
                return equipeperito;
            } else {
                int codigocvli = retornarCodigoCvli(1);

                Cursor cursor = db.query("cvliequipeperitos", new String[]{"id", "fkCvli", "dsCargoEquipePerito",
                        "dsNomeEquipePerito", "Controle"}, "fkCvli='" + codigocvli + "'", null, null, null, null);

                while (cursor.moveToNext()) {
                    Equipeperito equiPerito = new Equipeperito();
                    equiPerito.setId(cursor.getInt(0));
                    equiPerito.setFkCvli(cursor.getInt(1));
                    equiPerito.setDsCargoEquipePerito(cursor.getString(2));
                    equiPerito.setDsNomeEquipePerito(cursor.getString(3));
                    equiPerito.setControle(cursor.getInt(4));

                    equipeperito.add(equiPerito);
                }
                return equipeperito;
            }

    }

    public List<Equipeperito> retornarEquipePeritoAtualizar(int fk) {

            db = conexao.getReadableDatabase();

            List<Equipeperito> equipeperitoatualizar = new ArrayList<>();

            Cursor cursor = db.query("cvliequipeperitos", new String[]{"id", "fkCvli", "dsCargoEquipePerito",
                    "dsNomeEquipePerito", "Controle"}, "fkCvli='" + fk + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Equipeperito equiPeritoatualizar = new Equipeperito();
                equiPeritoatualizar.setId(cursor.getInt(0));
                equiPeritoatualizar.setFkCvli(cursor.getInt(1));
                equiPeritoatualizar.setDsCargoEquipePerito(cursor.getString(2));
                equiPeritoatualizar.setDsNomeEquipePerito(cursor.getString(3));
                equiPeritoatualizar.setControle(cursor.getInt(4));

                equipeperitoatualizar.add(equiPeritoatualizar);
            }
            return equipeperitoatualizar;
    }
}
