package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Carro;
import com.iris.ramilton.iris.modelo.Equipelevantamento;

import java.util.ArrayList;
import java.util.List;

public class EquipeLevantamentoDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;
    private  Equipelevantamento equiLevantamento;

    public EquipeLevantamentoDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarCVLIEquipeLevantamentoWEB(Equipelevantamento equipelevantamento){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

            cv.put("fkCvli", equipelevantamento.getFkCvli());
            cv.put("dsCargoEquipeLevantamento", equipelevantamento.getDsCargoEquipeLevantamento());
            cv.put("dsNomeEquipeLevantamento", equipelevantamento.getDsNomeEquipeLevantamento());
            cv.put("Controle", equipelevantamento.getControle());
            cv.put("idUnico", equipelevantamento.getIdUnico());


            return db.insert("cvliequipelevantamentos", null, cv);
    }

    public long atualizaCVLIEquipeLevantamentoWEB(Equipelevantamento equipelevantamento, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkCvli", equipelevantamento.getFkCvli());
        cv.put("dsCargoEquipeLevantamento", equipelevantamento.getDsCargoEquipeLevantamento());
        cv.put("dsNomeEquipeLevantamento", equipelevantamento.getDsNomeEquipeLevantamento());
        cv.put("Controle", equipelevantamento.getControle());
        cv.put("idUnico", equipelevantamento.getIdUnico());


        return db.update("cvliequipelevantamentos",cv,"idUnico='"+codigo+"'", null);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvliequipelevantamentos", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public long cadastrarCVLIEquipeLevantamento(Equipelevantamento equipelevantamento, String valor){

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {
                int codigocvli = retornarCodigoCvli(0);
                equipelevantamento.setFkCvli(codigocvli);
                equipelevantamento.setIdUnico(valor);

                cv.put("fkCvli", equipelevantamento.getFkCvli());
                cv.put("dsCargoEquipeLevantamento", equipelevantamento.getDsCargoEquipeLevantamento());
                cv.put("dsNomeEquipeLevantamento", equipelevantamento.getDsNomeEquipeLevantamento());
                cv.put("Controle", equipelevantamento.getControle());
                cv.put("idUnico", equipelevantamento.getIdUnico());


                return db.insert("cvliequipelevantamentos", null, cv);
            } else {
                int codigocvli = retornarCodigoCvli(1);
                equipelevantamento.setFkCvli(codigocvli);
                equipelevantamento.setIdUnico(valor);

                cv.put("fkCvli", equipelevantamento.getFkCvli());
                cv.put("dsCargoEquipeLevantamento", equipelevantamento.getDsCargoEquipeLevantamento());
                cv.put("dsNomeEquipeLevantamento", equipelevantamento.getDsNomeEquipeLevantamento());
                cv.put("Controle", equipelevantamento.getControle());
                cv.put("idUnico", equipelevantamento.getIdUnico());

                return db.insert("cvliequipelevantamentos", null, cv);
            }
    }

    public long excluirEquipeLevantamento(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("cvliequipelevantamentos", "fkCvli='"+fk+"'",null);
    }

    public long cadastrarCVLIEquipeLevantamentoAtualizar(Equipelevantamento equipelevantamento, int codigo, String valor){


            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            equipelevantamento.setFkCvli(codigo);
            equipelevantamento.setIdUnico(valor);

            cv.put("fkCvli", equipelevantamento.getFkCvli());
            cv.put("dsCargoEquipeLevantamento", equipelevantamento.getDsCargoEquipeLevantamento());
            cv.put("dsNomeEquipeLevantamento", equipelevantamento.getDsNomeEquipeLevantamento());
            cv.put("Controle", equipelevantamento.getControle());
            cv.put("idUnico", equipelevantamento.getIdUnico());


            return db.insert("cvliequipelevantamentos", null, cv);
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

    public List<Equipelevantamento> retornarEquipeLevantamento(){

            db = conexao.getReadableDatabase();

            List<Equipelevantamento> equipelevantamento = new ArrayList<>();

            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {

                int codigocvli = retornarCodigoCvli(0);

                Cursor cursor = db.query("cvliequipelevantamentos", new String[]{"id", "fkCvli", "dsCargoEquipeLevantamento",
                        "dsNomeEquipeLevantamento", "Controle"}, "fkCvli='" + codigocvli + "'", null, null, null, null);

                while (cursor.moveToNext()) {
                    Equipelevantamento equiLevantamento = new Equipelevantamento();
                    equiLevantamento.setId(cursor.getInt(0));
                    equiLevantamento.setFkCvli(cursor.getInt(1));
                    equiLevantamento.setDsCargoEquipeLevantamento(cursor.getString(2));
                    equiLevantamento.setDsNomeEquipeLevantamento(cursor.getString(3));
                    equiLevantamento.setControle(cursor.getInt(4));

                    equipelevantamento.add(equiLevantamento);
                }
                return equipelevantamento;
            } else {
                int codigocvli = retornarCodigoCvli(1);

                Cursor cursor = db.query("cvliequipelevantamentos", new String[]{"id", "fkCvli", "dsCargoEquipeLevantamento",
                        "dsNomeEquipeLevantamento", "Controle"}, "fkCvli='" + codigocvli + "'", null, null, null, null);

                while (cursor.moveToNext()) {
                    Equipelevantamento equiLevantamento = new Equipelevantamento();
                    equiLevantamento.setId(cursor.getInt(0));
                    equiLevantamento.setFkCvli(cursor.getInt(1));
                    equiLevantamento.setDsCargoEquipeLevantamento(cursor.getString(2));
                    equiLevantamento.setDsNomeEquipeLevantamento(cursor.getString(3));
                    equiLevantamento.setControle(cursor.getInt(4));

                    equipelevantamento.add(equiLevantamento);
                }
                return equipelevantamento;
            }
    }

    public Equipelevantamento retornarEquipeLevantamentoRelatorio(int id){


        db = conexao.getReadableDatabase();


            Cursor cursor = db.query("cvliequipelevantamentos", new String[]{"id", "fkCvli", "dsCargoEquipeLevantamento",
                    "dsNomeEquipeLevantamento", "Controle"}, "fkCvli='" + id + "'", null, null, null, null);

            while (cursor.moveToNext()) {

                equiLevantamento = new Equipelevantamento();

                equiLevantamento.setId(cursor.getInt(0));
                equiLevantamento.setFkCvli(cursor.getInt(1));
                equiLevantamento.setDsCargoEquipeLevantamento(cursor.getString(2));
                equiLevantamento.setDsNomeEquipeLevantamento(cursor.getString(3));
                equiLevantamento.setControle(cursor.getInt(4));

            }

            return equiLevantamento;
    }

    public List<Equipelevantamento> retornarEquipeLevantamentoAtualizar(int fk) {

        db = conexao.getReadableDatabase();

        List<Equipelevantamento> equipelevantamentos = new ArrayList<>();

        Cursor cursor = db.query("cvliequipelevantamentos", new String[]{"id", "fkCvli", "dsCargoEquipeLevantamento",
                "dsNomeEquipeLevantamento", "Controle"}, "fkCvli='" + fk + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Equipelevantamento equipelevantamento = new Equipelevantamento();
            equipelevantamento.setId(cursor.getInt(0));
            equipelevantamento.setFkCvli(cursor.getInt(1));
            equipelevantamento.setDsCargoEquipeLevantamento(cursor.getString(2));
            equipelevantamento.setDsNomeEquipeLevantamento(cursor.getString(3));
            equipelevantamento.setControle(cursor.getInt(4));

            equipelevantamentos.add(equipelevantamento);
        }
        return equipelevantamentos;
    }
}
