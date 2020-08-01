package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Marca;

import java.util.ArrayList;

public class MarcaDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public MarcaDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);

    }

    public long cadastrarMarcaWEB(Marca marca){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("dsMarcaCarro", marca.getDsMarcaCarro());

        return db.insert("marcacarros", null, cv);
    }

    public long atualizarMarcaWEB(Marca marca, String mun){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("dsMarcaCarro", marca.getDsMarcaCarro());

        return db.update("marcacarros",cv, "dsMarcaCarro='"+mun+"'", null);
    }

    public boolean verificarMarca(String mun){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("marcacarros", new String[]{"dsMarcaCarro"}, "dsMarcaCarro='"+mun+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<String> retornarMarca(){

        db = conexao.getReadableDatabase();

        ArrayList<String> listagem = new ArrayList<String>();

        Cursor c = db.query("marcacarros", new String[]{"dsMarcaCarro"}, null, null, null, null, null);

        Marca marca = new Marca();

        while (c.moveToNext()){

            if(!listagem.contains(c.getString(0)) && c.getString(0) != null){
                listagem.add(c.getString(0));
            }
        }

        return listagem;
    }
}
