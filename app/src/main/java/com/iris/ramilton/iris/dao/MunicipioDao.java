package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Carro;
import com.iris.ramilton.iris.modelo.Municipio;

import java.util.ArrayList;
import java.util.List;

public class MunicipioDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public MunicipioDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);

    }

    public long cadastrarMunicipioWEB(Municipio municipio){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("dsMunicipio", municipio.getDsMunicipio());

        return db.insert("cvlimunicipios", null, cv);
    }

    public long atualizarMunicipioWEB(Municipio municipio, String mun){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("dsMunicipio", municipio.getDsMunicipio());

        return db.update("cvlimunicipios",cv, "dsMunicipio='"+mun+"'", null);
    }

    public boolean verificarMunicipio(String mun){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("cvlimunicipios", new String[]{"dsMunicipio"}, "dsMunicipio='"+mun+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

    public int retornarCodigoCvliSemParametro(){

        db = conexao.getReadableDatabase();
        Cursor c = db.query("cvlimunicipios", new String[]{"id"}, null, null, null, null, null);
        c.moveToLast();
        int codigosemparametro = c.getInt(0);
        return codigosemparametro;
    }

    public ArrayList<String> retornarMunicipioFato(){

        db = conexao.getReadableDatabase();

        ArrayList<String> listagem = new ArrayList<String>();

        Cursor c = db.query("cvlimunicipios", new String[]{"dsMunicipio"}, null, null, null, null, null);

        Municipio munic = new Municipio();

        while (c.moveToNext()){

            if(!listagem.contains(c.getString(0)) && c.getString(0) != null){
                listagem.add(c.getString(0));
            }
        }

        return listagem;
    }

}
