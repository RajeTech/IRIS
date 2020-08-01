package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.UnidadeBO;

import java.util.ArrayList;

public class UnidadeBODao {


    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public UnidadeBODao(Context context){

        conexao = DatabaseHelper.getInstancia(context);

    }

    public long cadastrarUnidadeBOWEB(UnidadeBO unidadeBO){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("dsUnidadeBO", unidadeBO.getDsUnidadeBO());

        return db.insert("unidadebo", null, cv);
    }

    public long atualizarUnidadeBOWEB(UnidadeBO unidadeBO, String mun){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("dsUnidadeBO", unidadeBO.getDsUnidadeBO());

        return db.update("unidadebo",cv, "dsUnidadeBO='"+mun+"'", null);
    }

    public boolean verificarUnidadeBO(String mun){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("unidadebo", new String[]{"dsUnidadeBO"}, "dsUnidadeBO='"+mun+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<String> retornarUnidadeBO(){

        db = conexao.getReadableDatabase();

        Log.i("dados", "Entrou aqui");
        ArrayList<String> listagem = new ArrayList<String>();

        Cursor c = db.query("unidadebo", new String[]{"dsUnidadeBO"}, null, null, null, null, null);

        UnidadeBO uni = new UnidadeBO();

        while (c.moveToNext()){

            if(!listagem.contains(c.getString(0)) && c.getString(0) != null){
                listagem.add(c.getString(0));
                Log.i("dados","Entrou aqui dao");
                Log.i("dados","unidades "+c.getString(0));
            }
        }

        return listagem;
    }
}
