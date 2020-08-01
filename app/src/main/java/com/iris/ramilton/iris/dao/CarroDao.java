package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Carro;
import com.iris.ramilton.iris.modelo.Cvli;

import java.util.ArrayList;
import java.util.List;

public class CarroDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public CarroDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);

    }

    public long cadastrarCVLICarroWEB(Carro carro){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

            cv.put("fkCvli", carro.getFkCvli());
            cv.put("dsTipoCarro", carro.getDsTipoCarro());
            cv.put("dsMarcaCarro", carro.getDsMarcaCarro());
            cv.put("dsModeloCarro", carro.getDsMarcaCarro());
            cv.put("dsDescricaoCarro", carro.getDsDescricaoCarro());
            cv.put("dsCorCarro", carro.getDsCorCarro());
            cv.put("dsPlacaCarro", carro.getDsPlacaCarro());
            cv.put("ckbNIdentificadoMarcaCarroCrime", carro.getCkbNIdentificadoMarcaCarroCrime());
            cv.put("ckbNIdentificadoModeloCarroCrime", carro.getCkbNIdentificadoModeloCarroCrime());
            cv.put("ckbNIdentificadoCorCarroCrime", carro.getCkbNIdentificadoCorCarroCrime());
            cv.put("ckbNidentificadoPlacaCarroCrime", carro.getCkbNidentificadoPlacaCarroCrime());
            cv.put("dsDescricaoBiscicleta", carro.getDsDescricaoBiscicleta());
            cv.put("Controle", carro.getControle());
            cv.put("idUnico", carro.getIdUnico());

            return db.insert("cvlicarros", null, cv);
    }

    public long atualizarCVLICarroWEB(Carro carro, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkCvli", carro.getFkCvli());
        cv.put("dsTipoCarro", carro.getDsTipoCarro());
        cv.put("dsMarcaCarro", carro.getDsMarcaCarro());
        cv.put("dsModeloCarro", carro.getDsMarcaCarro());
        cv.put("dsDescricaoCarro", carro.getDsDescricaoCarro());
        cv.put("dsCorCarro", carro.getDsCorCarro());
        cv.put("dsPlacaCarro", carro.getDsPlacaCarro());
        cv.put("ckbNIdentificadoMarcaCarroCrime", carro.getCkbNIdentificadoMarcaCarroCrime());
        cv.put("ckbNIdentificadoModeloCarroCrime", carro.getCkbNIdentificadoModeloCarroCrime());
        cv.put("ckbNIdentificadoCorCarroCrime", carro.getCkbNIdentificadoCorCarroCrime());
        cv.put("ckbNidentificadoPlacaCarroCrime", carro.getCkbNidentificadoPlacaCarroCrime());
        cv.put("dsDescricaoBiscicleta", carro.getDsDescricaoBiscicleta());
        cv.put("Controle", carro.getControle());
        cv.put("idUnico", carro.getIdUnico());

        return db.update("cvlicarros",cv, "idUnico='"+codigo+"'", null);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvlicarros", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }
        public long cadastrarCVLICarro(Carro carro, String valor){

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {


                int codigocvli = retornarCodigoCvli(0);
                carro.setFkCvli(codigocvli);
                carro.setIdUnico(valor);

                cv.put("fkCvli", carro.getFkCvli());
                cv.put("dsTipoCarro", carro.getDsTipoCarro());
                cv.put("dsMarcaCarro", carro.getDsMarcaCarro());
                cv.put("dsModeloCarro", carro.getDsMarcaCarro());
                cv.put("dsDescricaoCarro", carro.getDsDescricaoCarro());
                cv.put("dsCorCarro", carro.getDsCorCarro());
                cv.put("dsPlacaCarro", carro.getDsPlacaCarro());
                cv.put("ckbNIdentificadoMarcaCarroCrime", carro.getCkbNIdentificadoMarcaCarroCrime());
                cv.put("ckbNIdentificadoModeloCarroCrime", carro.getCkbNIdentificadoModeloCarroCrime());
                cv.put("ckbNIdentificadoCorCarroCrime", carro.getCkbNIdentificadoCorCarroCrime());
                cv.put("ckbNidentificadoPlacaCarroCrime", carro.getCkbNidentificadoPlacaCarroCrime());
                cv.put("dsDescricaoBiscicleta", carro.getDsDescricaoBiscicleta());
                cv.put("Controle", carro.getControle());
                cv.put("idUnico", carro.getIdUnico());

                return db.insert("cvlicarros", null, cv);
            } else {

                int codigocvli = retornarCodigoCvli(1);
                carro.setFkCvli(codigocvli);
                carro.setIdUnico(valor);

                cv.put("fkCvli", carro.getFkCvli());
                cv.put("dsTipoCarro", carro.getDsTipoCarro());
                cv.put("dsMarcaCarro", carro.getDsMarcaCarro());
                cv.put("dsModeloCarro", carro.getDsMarcaCarro());
                cv.put("dsDescricaoCarro", carro.getDsDescricaoCarro());
                cv.put("dsCorCarro", carro.getDsCorCarro());
                cv.put("dsPlacaCarro", carro.getDsPlacaCarro());
                cv.put("ckbNIdentificadoMarcaCarroCrime", carro.getCkbNIdentificadoMarcaCarroCrime());
                cv.put("ckbNIdentificadoModeloCarroCrime", carro.getCkbNIdentificadoModeloCarroCrime());
                cv.put("ckbNIdentificadoCorCarroCrime", carro.getCkbNIdentificadoCorCarroCrime());
                cv.put("ckbNidentificadoPlacaCarroCrime", carro.getCkbNidentificadoPlacaCarroCrime());
                cv.put("dsDescricaoBiscicleta", carro.getDsDescricaoBiscicleta());
                cv.put("Controle", carro.getControle());
                cv.put("idUnico", carro.getIdUnico());

                return db.insert("cvlicarros", null, cv);

            }
   }

   public long excluirCarro(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("cvlicarros","fkCvli='"+fk+"'",null);
   }


    public long cadastrarCVLICarroAtualizar(Carro carro, int codigo, String valor) {

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            carro.setFkCvli(codigo);
            carro.setIdUnico(valor);

            cv.put("fkCvli", carro.getFkCvli());
            cv.put("dsTipoCarro", carro.getDsTipoCarro());
            cv.put("dsMarcaCarro", carro.getDsMarcaCarro());
            cv.put("dsModeloCarro", carro.getDsMarcaCarro());
            cv.put("dsDescricaoCarro", carro.getDsDescricaoCarro());
            cv.put("dsCorCarro", carro.getDsCorCarro());
            cv.put("dsPlacaCarro", carro.getDsPlacaCarro());
            cv.put("ckbNIdentificadoMarcaCarroCrime", carro.getCkbNIdentificadoMarcaCarroCrime());
            cv.put("ckbNIdentificadoModeloCarroCrime", carro.getCkbNIdentificadoModeloCarroCrime());
            cv.put("ckbNIdentificadoCorCarroCrime", carro.getCkbNIdentificadoCorCarroCrime());
            cv.put("ckbNidentificadoPlacaCarroCrime", carro.getCkbNidentificadoPlacaCarroCrime());
            cv.put("dsDescricaoBiscicleta", carro.getDsDescricaoBiscicleta());
            cv.put("Controle", carro.getControle());
            cv.put("idUnico", carro.getIdUnico());

            return db.insert("cvlicarros", null, cv);

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
            Cursor c = db.query("cvlis",new String[]{"EstatusCVLI"},"id='"+id+"'",null, null, null,null);
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


    public List<Carro> retornarCarros(){

            db = conexao.getReadableDatabase();
            List<Carro> carros = new ArrayList<>();

            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {

                int codigocvli = retornarCodigoCvli(0);

                Cursor cursor = db.query("cvlicarros", new String[]{"id", "fkCvli", "dsTipoCarro", "dsMarcaCarro",
                                "dsModeloCarro", "dsDescricaoCarro", "dsCorCarro", "dsPlacaCarro", "ckbNIdentificadoMarcaCarroCrime", "ckbNIdentificadoModeloCarroCrime",
                                "ckbNIdentificadoCorCarroCrime", "ckbNidentificadoPlacaCarroCrime", "dsDescricaoBiscicleta", "Controle"},
                        "fkCvli='" + codigocvli + "'", null, null, null, null);

                while (cursor.moveToNext()) {
                    Carro carro = new Carro();
                    carro.setId(cursor.getInt(0));
                    carro.setFkCvli(cursor.getInt(1));
                    carro.setDsTipoCarro(cursor.getString(2));
                    carro.setDsMarcaCarro(cursor.getString(3));
                    carro.setDsModeloCarro(cursor.getString(4));
                    carro.setDsDescricaoCarro(cursor.getString(5));
                    carro.setDsCorCarro(cursor.getString(6));
                    carro.setDsPlacaCarro(cursor.getString(7));
                    carro.setCkbNIdentificadoMarcaCarroCrime(cursor.getInt(8));
                    carro.setCkbNIdentificadoModeloCarroCrime(cursor.getInt(9));
                    carro.setCkbNIdentificadoCorCarroCrime(cursor.getInt(10));
                    carro.setCkbNidentificadoPlacaCarroCrime(cursor.getInt(11));
                    carro.setDsDescricaoBiscicleta(cursor.getString(12));
                    carro.setControle(cursor.getInt(13));

                    carros.add(carro);
                }
                return carros;
            } else {
                int codigocvli = retornarCodigoCvli(1);


                Cursor cursor = db.query("cvlicarros", new String[]{"id", "fkCvli", "dsTipoCarro", "dsMarcaCarro",
                                "dsModeloCarro", "dsDescricaoCarro", "dsCorCarro", "dsPlacaCarro", "ckbNIdentificadoMarcaCarroCrime", "ckbNIdentificadoModeloCarroCrime",
                                "ckbNIdentificadoCorCarroCrime", "ckbNidentificadoPlacaCarroCrime", "dsDescricaoBiscicleta", "Controle"},
                        "fkCvli='" + codigocvli + "'", null, null, null, null);

                while (cursor.moveToNext()) {
                    Carro carro = new Carro();
                    carro.setId(cursor.getInt(0));
                    carro.setFkCvli(cursor.getInt(1));
                    carro.setDsTipoCarro(cursor.getString(2));
                    carro.setDsMarcaCarro(cursor.getString(3));
                    carro.setDsModeloCarro(cursor.getString(4));
                    carro.setDsDescricaoCarro(cursor.getString(5));
                    carro.setDsCorCarro(cursor.getString(6));
                    carro.setDsPlacaCarro(cursor.getString(7));
                    carro.setCkbNIdentificadoMarcaCarroCrime(cursor.getInt(8));
                    carro.setCkbNIdentificadoModeloCarroCrime(cursor.getInt(9));
                    carro.setCkbNIdentificadoCorCarroCrime(cursor.getInt(10));
                    carro.setCkbNidentificadoPlacaCarroCrime(cursor.getInt(11));
                    carro.setDsDescricaoBiscicleta(cursor.getString(12));
                    carro.setControle(cursor.getInt(13));

                    carros.add(carro);
                }
                return carros;
            }
    }

    public List<String> retornarCarrosLv(){

        db = conexao.getReadableDatabase();
        List<String> carros = new ArrayList<>();

        int codigoretorno = retornarCodigoCvliSemParametro();

        int codigostatus = retornarCodigoStatusCvli(codigoretorno);

        if (codigostatus == 0) {

            int codigocvli = retornarCodigoCvli(0);

            Cursor cursor = db.query("cvlicarros", new String[]{"dsTipoCarro"},
                    "fkCvli='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                carros.add(cursor.getString(0));

            }
            return carros;
        } else {
            int codigocvli = retornarCodigoCvli(1);


            Cursor cursor = db.query("cvlicarros", new String[]{"dsTipoCarro"},
                    "fkCvli='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                carros.add(cursor.getString(0));
            }
            return carros;
        }
    }

    public List<Carro> retornarCarrosAtualizar(int fk) {

        List<Carro> carros = new ArrayList<>();

            db = conexao.getReadableDatabase();
            Cursor cursor = db.query("cvlicarros", new String[]{"id", "fkCvli", "dsTipoCarro", "dsMarcaCarro",
                    "dsModeloCarro", "dsDescricaoCarro", "dsCorCarro", "dsPlacaCarro", "ckbNIdentificadoMarcaCarroCrime", "ckbNIdentificadoModeloCarroCrime",
                    "ckbNIdentificadoCorCarroCrime", "ckbNidentificadoPlacaCarroCrime", "dsDescricaoBiscicleta", "Controle"}, "fkCvli='" + fk + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Carro carro = new Carro();
                carro.setId(cursor.getInt(0));
                carro.setFkCvli(cursor.getInt(1));
                carro.setDsTipoCarro(cursor.getString(2));
                carro.setDsMarcaCarro(cursor.getString(3));
                carro.setDsModeloCarro(cursor.getString(4));
                carro.setDsDescricaoCarro(cursor.getString(5));
                carro.setDsCorCarro(cursor.getString(6));
                carro.setDsPlacaCarro(cursor.getString(7));
                carro.setCkbNIdentificadoMarcaCarroCrime(cursor.getInt(8));
                carro.setCkbNIdentificadoModeloCarroCrime(cursor.getInt(9));
                carro.setCkbNIdentificadoCorCarroCrime(cursor.getInt(10));
                carro.setCkbNidentificadoPlacaCarroCrime(cursor.getInt(11));
                carro.setDsDescricaoBiscicleta(cursor.getString(12));
                carro.setControle(cursor.getInt(13));

                carros.add(carro);
            }
            return carros;
        }

    public String retornarCarros(int id){

        db = conexao.getReadableDatabase();

        String listacarros = "";

        Cursor cursor = db.query("cvlicarros", new String[]{"dsTipoCarro"}, "fkCvli='" + id+ "'", null, null, null, null);

        while (cursor.moveToNext()) {
            listacarros += cursor.getString(0)+"; ";
        }
        return listacarros;

    }
}
