package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.ObjetosApreendidos;

import java.util.ArrayList;
import java.util.List;

public class ObjetosApreendidosDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public ObjetosApreendidosDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarCVLIObjetosApreendidosWEB(ObjetosApreendidos objetosApreendidos){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

            cv.put("fkCvli", objetosApreendidos.getFkCvli());
            cv.put("dsTipoObjetoApreendido", objetosApreendidos.getDsTipoObjetoApreendido());
            cv.put("dsDescricaoObjetoApreendido", objetosApreendidos.getDsDescricaoObjetoApreendido());
            cv.put("Controle", objetosApreendidos.getControle());
            cv.put("idUnico", objetosApreendidos.getIdUnico());

            return db.insert("cvliobjetosapreendidos", null, cv);
    }

    public long atualizaCVLIObjetosApreendidosWEB(ObjetosApreendidos objetosApreendidos, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkCvli", objetosApreendidos.getFkCvli());
        cv.put("dsTipoObjetoApreendido", objetosApreendidos.getDsTipoObjetoApreendido());
        cv.put("dsDescricaoObjetoApreendido", objetosApreendidos.getDsDescricaoObjetoApreendido());
        cv.put("Controle", objetosApreendidos.getControle());
        cv.put("idUnico", objetosApreendidos.getIdUnico());

        return db.update("cvliobjetosapreendidos", cv, "idUnico='"+codigo+"'" ,null);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvliobjetosapreendidos", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

        public long cadastrarCVLIObjetosApreendidos(ObjetosApreendidos objetosApreendidos, String valor){

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {


                int codigocvli = retornarCodigoCvli(0);
                objetosApreendidos.setFkCvli(codigocvli);
                objetosApreendidos.setIdUnico(valor);

                cv.put("fkCvli", objetosApreendidos.getFkCvli());
                cv.put("dsTipoObjetoApreendido", objetosApreendidos.getDsTipoObjetoApreendido());
                cv.put("dsDescricaoObjetoApreendido", objetosApreendidos.getDsDescricaoObjetoApreendido());
                cv.put("Controle", objetosApreendidos.getControle());
                cv.put("idUnico", objetosApreendidos.getIdUnico());

                return db.insert("cvliobjetosapreendidos", null, cv);
            } else {

                int codigocvli = retornarCodigoCvli(1);
                objetosApreendidos.setFkCvli(codigocvli);
                objetosApreendidos.setIdUnico(valor);
                cv.put("fkCvli", objetosApreendidos.getFkCvli());
                cv.put("dsTipoObjetoApreendido", objetosApreendidos.getDsTipoObjetoApreendido());
                cv.put("dsDescricaoObjetoApreendido", objetosApreendidos.getDsDescricaoObjetoApreendido());
                cv.put("Controle", objetosApreendidos.getControle());
                cv.put("idUnico", objetosApreendidos.getIdUnico());

                return db.insert("cvliobjetosapreendidos", null, cv);

            }

    }

    public long excluirObjetosApreendidos(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("cvliobjetosapreendidos","fkCvli='"+fk+"'",null);
    }

    public long cadastrarCVLIObjetosApreendidosAtualizar(ObjetosApreendidos objetosApreendidos, int codigo, String valor){

            db = conexao.getWritableDatabase();

            ContentValues cv = new ContentValues();

            objetosApreendidos.setFkCvli(codigo);
            objetosApreendidos.setIdUnico(valor);

            cv.put("fkCvli", objetosApreendidos.getFkCvli());
            cv.put("dsTipoObjetoApreendido", objetosApreendidos.getDsTipoObjetoApreendido());
            cv.put("dsDescricaoObjetoApreendido", objetosApreendidos.getDsDescricaoObjetoApreendido());
            cv.put("Controle", objetosApreendidos.getControle());
            cv.put("idUnico", objetosApreendidos.getIdUnico());

            return db.insert("cvliobjetosapreendidos", null, cv);

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

    public List<ObjetosApreendidos> retornarObjetosApreendidos(){


            db = conexao.getReadableDatabase();

            List<ObjetosApreendidos> objetosApreendidos = new ArrayList<>();

            int codigoretorno = retornarCodigoCvliSemParametro();

            int codigostatus = retornarCodigoStatusCvli(codigoretorno);

            if (codigostatus == 0) {

                int codigocvli = retornarCodigoCvli(0);

                Cursor cursor = db.query("cvliobjetosapreendidos", new String[]{"id", "fkCvli", "dsTipoObjetoApreendido",
                        "dsDescricaoObjetoApreendido", "Controle"}, "fkCvli='" + codigocvli + "'", null, null, null, null);

                while (cursor.moveToNext()) {
                    ObjetosApreendidos objetosApreendido = new ObjetosApreendidos();

                    objetosApreendido.setId(cursor.getInt(0));
                    objetosApreendido.setFkCvli(cursor.getInt(1));
                    objetosApreendido.setDsTipoObjetoApreendido(cursor.getString(2));
                    objetosApreendido.setDsDescricaoObjetoApreendido(cursor.getString(3));
                    objetosApreendido.setControle(cursor.getInt(4));

                    objetosApreendidos.add(objetosApreendido);
                }
                return objetosApreendidos;

            } else {
                int codigocvli = retornarCodigoCvli(1);
                Cursor cursor = db.query("cvliobjetosapreendidos", new String[]{"id", "fkCvli", "dsTipoObjetoApreendido",
                        "dsDescricaoObjetoApreendido", "Controle"}, "fkCvli='" + codigocvli + "'", null, null, null, null);

                while (cursor.moveToNext()) {
                    ObjetosApreendidos objetosApreendido = new ObjetosApreendidos();

                    objetosApreendido.setId(cursor.getInt(0));
                    objetosApreendido.setFkCvli(cursor.getInt(1));
                    objetosApreendido.setDsTipoObjetoApreendido(cursor.getString(2));
                    objetosApreendido.setDsDescricaoObjetoApreendido(cursor.getString(3));
                    objetosApreendido.setControle(cursor.getInt(4));

                    objetosApreendidos.add(objetosApreendido);
                }
                return objetosApreendidos;

            }

    }

    public List<ObjetosApreendidos> retornarObjetosApreendidosAtualizar(int fk){

            db = conexao.getReadableDatabase();

            List<ObjetosApreendidos> objetosApreendidosatualizar = new ArrayList<>();

            Cursor cursor = db.query("cvliobjetosapreendidos", new String[]{"id", "fkCvli", "dsTipoObjetoApreendido",
                    "dsDescricaoObjetoApreendido", "Controle"}, "fkCvli='" + fk + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                ObjetosApreendidos objetosApreendidoatualizar = new ObjetosApreendidos();

                objetosApreendidoatualizar.setId(cursor.getInt(0));
                objetosApreendidoatualizar.setFkCvli(cursor.getInt(1));
                objetosApreendidoatualizar.setDsTipoObjetoApreendido(cursor.getString(2));
                objetosApreendidoatualizar.setDsDescricaoObjetoApreendido(cursor.getString(3));
                objetosApreendidoatualizar.setControle(cursor.getInt(4));

                objetosApreendidosatualizar.add(objetosApreendidoatualizar);
            }
            return objetosApreendidosatualizar;

        }
}
