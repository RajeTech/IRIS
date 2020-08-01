package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Acaopolicial;
import com.iris.ramilton.iris.modelo.Conduzidoacaopolicial;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.ObjetosApreendidos;
import com.iris.ramilton.iris.modelo.Vitima;

import java.util.ArrayList;
import java.util.List;

public class ConduzidoacaopolicialDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    public ConduzidoacaopolicialDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarConduzidoAcaoPolicialWEB(Conduzidoacaopolicial conduzidoacaopolicial){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkAcaoPolicial", conduzidoacaopolicial.getFkAcaoPolicial());
        cv.put("dsNomeConduzidoAcaoPolicial", conduzidoacaopolicial.getDsNomeConduzidoAcaoPolicial());
        cv.put("IdadeConduzidoAcaoPolicial", conduzidoacaopolicial.getIdadeConduzidoAcaoPolicial());
        cv.put("dsTipoProcedimentoAcaoPolcial", conduzidoacaopolicial.getDsTipoProcedimentoAcaoPolcial());
        cv.put("dsAtoInfracionalAcaoPolicial", conduzidoacaopolicial.getDsAtoInfracionalAcaoPolicial());
        cv.put("dsTipoConducaoAcaoPolicial", conduzidoacaopolicial.getDsTipoConducaoAcaoPolicial());
        cv.put("dsNomeJuizAcaoPolicial", conduzidoacaopolicial.getDsNomeJuizAcaoPolicial());
        cv.put("dsComarcaAcaoPolicial", conduzidoacaopolicial.getDsComarcaAcaoPolicial());
        cv.put("Controle", conduzidoacaopolicial.getControle());
        cv.put("idUnico", conduzidoacaopolicial.getIdUnico());

        return db.insert("ConduzidoAcaoPolicial", null, cv);
    }

    public long atualizaConduzidoAcaoPolicialWEB(Conduzidoacaopolicial conduzidoacaopolicial, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fkAcaoPolicial", conduzidoacaopolicial.getFkAcaoPolicial());
        cv.put("dsNomeConduzidoAcaoPolicial", conduzidoacaopolicial.getDsNomeConduzidoAcaoPolicial());
        cv.put("IdadeConduzidoAcaoPolicial", conduzidoacaopolicial.getIdadeConduzidoAcaoPolicial());
        cv.put("dsTipoProcedimentoAcaoPolcial", conduzidoacaopolicial.getDsTipoProcedimentoAcaoPolcial());
        cv.put("dsAtoInfracionalAcaoPolicial", conduzidoacaopolicial.getDsAtoInfracionalAcaoPolicial());
        cv.put("dsTipoConducaoAcaoPolicial", conduzidoacaopolicial.getDsTipoConducaoAcaoPolicial());
        cv.put("dsNomeJuizAcaoPolicial", conduzidoacaopolicial.getDsNomeJuizAcaoPolicial());
        cv.put("dsComarcaAcaoPolicial", conduzidoacaopolicial.getDsComarcaAcaoPolicial());
        cv.put("Controle", conduzidoacaopolicial.getControle());
        cv.put("idUnico", conduzidoacaopolicial.getIdUnico());

        return db.update("ConduzidoAcaoPolicial", cv, "idUnico='"+codigo+"'" ,null);
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("ConduzidoAcaoPolicial", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

    public long cadastrarConduzidoAcaoPolicial(Conduzidoacaopolicial conduzidoacaopolicial, String valor, int codigo){


        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        int codigoretorno = retornarCodigoAcaoPolicialSemParametro();

        int codigostatus = retornarCodigoStatusAcaoPolicial(codigoretorno);

        if (codigostatus == 0) {

            conduzidoacaopolicial.setFkAcaoPolicial(codigo);
            conduzidoacaopolicial.setIdUnico(valor);

            cv.put("fkAcaoPolicial", conduzidoacaopolicial.getFkAcaoPolicial());
            cv.put("dsNomeConduzidoAcaoPolicial", conduzidoacaopolicial.getDsNomeConduzidoAcaoPolicial());
            cv.put("IdadeConduzidoAcaoPolicial", conduzidoacaopolicial.getIdadeConduzidoAcaoPolicial());
            cv.put("dsTipoProcedimentoAcaoPolcial", conduzidoacaopolicial.getDsTipoProcedimentoAcaoPolcial());
            cv.put("dsAtoInfracionalAcaoPolicial", conduzidoacaopolicial.getDsAtoInfracionalAcaoPolicial());
            cv.put("dsTipoConducaoAcaoPolicial", conduzidoacaopolicial.getDsTipoConducaoAcaoPolicial());
            cv.put("dsNomeJuizAcaoPolicial", conduzidoacaopolicial.getDsNomeJuizAcaoPolicial());
            cv.put("dsComarcaAcaoPolicial", conduzidoacaopolicial.getDsComarcaAcaoPolicial());
            cv.put("Controle", conduzidoacaopolicial.getControle());
            cv.put("idUnico", conduzidoacaopolicial.getIdUnico());

            return db.insert("ConduzidoAcaoPolicial", null, cv);
        } else {


            conduzidoacaopolicial.setFkAcaoPolicial(codigo);
            conduzidoacaopolicial.setIdUnico(valor);

            cv.put("fkAcaoPolicial", conduzidoacaopolicial.getFkAcaoPolicial());
            cv.put("dsNomeConduzidoAcaoPolicial", conduzidoacaopolicial.getDsNomeConduzidoAcaoPolicial());
            cv.put("IdadeConduzidoAcaoPolicial", conduzidoacaopolicial.getIdadeConduzidoAcaoPolicial());
            cv.put("dsTipoProcedimentoAcaoPolcial", conduzidoacaopolicial.getDsTipoProcedimentoAcaoPolcial());
            cv.put("dsAtoInfracionalAcaoPolicial", conduzidoacaopolicial.getDsAtoInfracionalAcaoPolicial());
            cv.put("dsTipoConducaoAcaoPolicial", conduzidoacaopolicial.getDsTipoConducaoAcaoPolicial());
            cv.put("dsNomeJuizAcaoPolicial", conduzidoacaopolicial.getDsNomeJuizAcaoPolicial());
            cv.put("dsComarcaAcaoPolicial", conduzidoacaopolicial.getDsComarcaAcaoPolicial());
            cv.put("Controle", conduzidoacaopolicial.getControle());
            cv.put("idUnico", conduzidoacaopolicial.getIdUnico());

            return db.insert("ConduzidoAcaoPolicial", null, cv);

        }
    }

    public long AtualizarConduzidoAcaoPolicial(Conduzidoacaopolicial conduzidoacaopolicial, int fk, int id) {

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("dsNomeConduzidoAcaoPolicial", conduzidoacaopolicial.getDsNomeConduzidoAcaoPolicial());
        cv.put("IdadeConduzidoAcaoPolicial", conduzidoacaopolicial.getIdadeConduzidoAcaoPolicial());
        cv.put("dsTipoProcedimentoAcaoPolcial", conduzidoacaopolicial.getDsTipoProcedimentoAcaoPolcial());
        cv.put("dsAtoInfracionalAcaoPolicial", conduzidoacaopolicial.getDsAtoInfracionalAcaoPolicial());
        cv.put("dsTipoConducaoAcaoPolicial", conduzidoacaopolicial.getDsTipoConducaoAcaoPolicial());
        cv.put("Controle", conduzidoacaopolicial.getControle());
        cv.put("dsNomeJuizAcaoPolicial", conduzidoacaopolicial.getDsNomeJuizAcaoPolicial());
        cv.put("dsComarcaAcaoPolicial", conduzidoacaopolicial.getDsComarcaAcaoPolicial());


        return db.update("ConduzidoAcaoPolicial", cv, "fkAcaoPolicial= '" + fk + "' and id= '" + id + "'", null);
    }

    public long excluirConduzidoAcaoPolicial(int fk){
        db = conexao.getWritableDatabase();

        return db.delete("ConduzidoAcaoPolicial","fkAcaoPolicial='"+fk+"'",null);
    }

    public long cadastrarConduzidoAcaoPolicialAtualizar(Conduzidoacaopolicial conduzidoacaopolicial, int codigo, String valor){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        conduzidoacaopolicial.setFkAcaoPolicial(codigo);
        conduzidoacaopolicial.setIdUnico(valor);

        cv.put("fkAcaoPolicial", conduzidoacaopolicial.getFkAcaoPolicial());
        cv.put("dsNomeConduzidoAcaoPolicial", conduzidoacaopolicial.getDsNomeConduzidoAcaoPolicial());
        cv.put("IdadeConduzidoAcaoPolicial", conduzidoacaopolicial.getIdadeConduzidoAcaoPolicial());
        cv.put("dsTipoProcedimentoAcaoPolcial", conduzidoacaopolicial.getDsTipoProcedimentoAcaoPolcial());
        cv.put("dsAtoInfracionalAcaoPolicial", conduzidoacaopolicial.getDsAtoInfracionalAcaoPolicial());
        cv.put("dsTipoConducaoAcaoPolicial", conduzidoacaopolicial.getDsTipoConducaoAcaoPolicial());
        cv.put("dsNomeJuizAcaoPolicial", conduzidoacaopolicial.getDsNomeJuizAcaoPolicial());
        cv.put("dsComarcaAcaoPolicial", conduzidoacaopolicial.getDsComarcaAcaoPolicial());
        cv.put("Controle", conduzidoacaopolicial.getControle());
        cv.put("idUnico", conduzidoacaopolicial.getIdUnico());

        return db.insert("ConduzidoAcaoPolicial", null, cv);

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

    public List<Conduzidoacaopolicial> retornarConduzidosAcaoPolicial(int id){

        db = conexao.getReadableDatabase();

        List<Conduzidoacaopolicial> listaconduzidos = new ArrayList<>();

        Cursor cursor = db.query("ConduzidoAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsNomeConduzidoAcaoPolicial", "IdadeConduzidoAcaoPolicial",
                "dsTipoProcedimentoAcaoPolcial", "dsAtoInfracionalAcaoPolicial", "dsTipoConducaoAcaoPolicial", "dsNomeJuizAcaoPolicial", "dsComarcaAcaoPolicial"}, "fkAcaoPolicial='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Conduzidoacaopolicial atualizarconduzido = new Conduzidoacaopolicial();

            atualizarconduzido.setId(cursor.getInt(0));
            atualizarconduzido.setFkAcaoPolicial(cursor.getInt(1));
            atualizarconduzido.setDsNomeConduzidoAcaoPolicial(cursor.getString(2));
            atualizarconduzido.setIdadeConduzidoAcaoPolicial(cursor.getInt(3));
            atualizarconduzido.setDsTipoProcedimentoAcaoPolcial(cursor.getString(4));
            atualizarconduzido.setDsAtoInfracionalAcaoPolicial(cursor.getString(5));
            atualizarconduzido.setDsTipoConducaoAcaoPolicial(cursor.getString(6));
            atualizarconduzido.setDsNomeJuizAcaoPolicial(cursor.getString(7));
            atualizarconduzido.setDsComarcaAcaoPolicial(cursor.getString(8));

            listaconduzidos.add(atualizarconduzido);
        }
        return listaconduzidos;

    }

    public String VerificarEmBrancoConduzido(int id){

        db = conexao.getReadableDatabase();

        String nomebrancos = null;
        Cursor cursor = db.query("ConduzidoAcaoPolicial", new String[]{"dsNomeConduzidoAcaoPolicial", "IdadeConduzidoAcaoPolicial"},
                "fkAcaoPolicial='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            Conduzidoacaopolicial conduzidoacaopolicial = new Conduzidoacaopolicial();

            conduzidoacaopolicial.setDsNomeConduzidoAcaoPolicial(cursor.getString(0));
            if(conduzidoacaopolicial.getDsNomeConduzidoAcaoPolicial() == null || conduzidoacaopolicial.getDsNomeConduzidoAcaoPolicial().equals("")){
                nomebrancos = nomebrancos + "Nome do Conduzido, ";
            }
            conduzidoacaopolicial.setIdadeConduzidoAcaoPolicial(cursor.getInt(1));
            if(String.valueOf(conduzidoacaopolicial.getIdadeConduzidoAcaoPolicial()) == null){
                nomebrancos = nomebrancos + "Idade da Autoria, ";
            }

        }

        return nomebrancos;
    }

    public List<Conduzidoacaopolicial> retornarConduzidoAcaoPolicial() {


        db = conexao.getReadableDatabase();

        List<Conduzidoacaopolicial> conduzidoacaopolicials = new ArrayList<>();

        int codigoretorno = retornarCodigoAcaoPolicialSemParametro();

        int codigostatus = retornarCodigoStatusAcaoPolicial(codigoretorno);

        if (codigostatus == 0) {

            int codigocvli = retornarCodigoAcaoPolicial(0);

            Cursor cursor = db.query("ConduzidoAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsNomeConduzidoAcaoPolicial", "IdadeConduzidoAcaoPolicial",
                    "dsTipoProcedimentoAcaoPolcial", "dsAtoInfracionalAcaoPolicial", "dsTipoConducaoAcaoPolicial", "Controle", "idUnico", "dsNomeJuizAcaoPolicial", "dsComarcaAcaoPolicial"}, "fkAcaoPolicial='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Conduzidoacaopolicial conduzidoacaopolicial = new Conduzidoacaopolicial();

                conduzidoacaopolicial.setId(cursor.getInt(0));
                conduzidoacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
                conduzidoacaopolicial.setDsNomeConduzidoAcaoPolicial(cursor.getString(2));
                conduzidoacaopolicial.setIdadeConduzidoAcaoPolicial(cursor.getInt(3));
                conduzidoacaopolicial.setDsTipoProcedimentoAcaoPolcial(cursor.getString(4));
                conduzidoacaopolicial.setDsAtoInfracionalAcaoPolicial(cursor.getString(5));
                conduzidoacaopolicial.setDsTipoConducaoAcaoPolicial(cursor.getString(6));
                conduzidoacaopolicial.setControle(cursor.getInt(7));
                conduzidoacaopolicial.setIdUnico(cursor.getString(8));
                conduzidoacaopolicial.setDsNomeJuizAcaoPolicial(cursor.getString(9));
                conduzidoacaopolicial.setDsComarcaAcaoPolicial(cursor.getString(10));

                conduzidoacaopolicials.add(conduzidoacaopolicial);
            }
            return conduzidoacaopolicials;

        } else {
            int codigocvli = retornarCodigoAcaoPolicial(1);
            Cursor cursor = db.query("ConduzidoAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsNomeConduzidoAcaoPolicial", "IdadeConduzidoAcaoPolicial",
                    "dsTipoProcedimentoAcaoPolcial", "dsAtoInfracionalAcaoPolicial", "dsTipoConducaoAcaoPolicial", "Controle", "idUnico", "dsNomeJuizAcaoPolicial", "dsComarcaAcaoPolicial"}, "fkAcaoPolicial='" + codigocvli + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Conduzidoacaopolicial conduzidoacaopolicial = new Conduzidoacaopolicial();

                conduzidoacaopolicial.setId(cursor.getInt(0));
                conduzidoacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
                conduzidoacaopolicial.setDsNomeConduzidoAcaoPolicial(cursor.getString(2));
                conduzidoacaopolicial.setIdadeConduzidoAcaoPolicial(cursor.getInt(3));
                conduzidoacaopolicial.setDsTipoProcedimentoAcaoPolcial(cursor.getString(4));
                conduzidoacaopolicial.setDsAtoInfracionalAcaoPolicial(cursor.getString(5));
                conduzidoacaopolicial.setDsTipoConducaoAcaoPolicial(cursor.getString(6));
                conduzidoacaopolicial.setControle(cursor.getInt(7));
                conduzidoacaopolicial.setIdUnico(cursor.getString(8));
                conduzidoacaopolicial.setDsNomeJuizAcaoPolicial(cursor.getString(9));
                conduzidoacaopolicial.setDsComarcaAcaoPolicial(cursor.getString(10));

                conduzidoacaopolicials.add(conduzidoacaopolicial);
            }
            return conduzidoacaopolicials;

        }
    }

        public List<Conduzidoacaopolicial> retornarConduzidoAcaoPolicialAtualizar(int fk){

            db = conexao.getReadableDatabase();

            List<Conduzidoacaopolicial> Conduzidoacaopolicialsatualizar = new ArrayList<>();

            Cursor cursor = db.query("ConduzidoAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsNomeConduzidoAcaoPolicial", "IdadeConduzidoAcaoPolicial",
                    "dsTipoProcedimentoAcaoPolcial", "dsAtoInfracionalAcaoPolicial", "dsTipoConducaoAcaoPolicial", "Controle", "idUnico", "dsNomeJuizAcaoPolicial", "dsComarcaAcaoPolicial"}, "fkAcaoPolicial='" + fk + "'", null, null, null, null);

            while (cursor.moveToNext()) {
                Conduzidoacaopolicial conduzidoacaopolicial = new Conduzidoacaopolicial();

                conduzidoacaopolicial.setId(cursor.getInt(0));
                conduzidoacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
                conduzidoacaopolicial.setDsNomeConduzidoAcaoPolicial(cursor.getString(2));
                conduzidoacaopolicial.setIdadeConduzidoAcaoPolicial(cursor.getInt(3));
                conduzidoacaopolicial.setDsTipoProcedimentoAcaoPolcial(cursor.getString(4));
                conduzidoacaopolicial.setDsAtoInfracionalAcaoPolicial(cursor.getString(5));
                conduzidoacaopolicial.setDsTipoConducaoAcaoPolicial(cursor.getString(6));
                conduzidoacaopolicial.setControle(cursor.getInt(7));
                conduzidoacaopolicial.setIdUnico(cursor.getString(8));
                conduzidoacaopolicial.setDsNomeJuizAcaoPolicial(cursor.getString(9));
                conduzidoacaopolicial.setDsComarcaAcaoPolicial(cursor.getString(10));

                Conduzidoacaopolicialsatualizar.add(conduzidoacaopolicial);
            }
            return Conduzidoacaopolicialsatualizar;
        }

    public Conduzidoacaopolicial retornaConduzidoAcaoPolicialObj(int id){

        Conduzidoacaopolicial conduzidoacaopolicial = new Conduzidoacaopolicial();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("ConduzidoAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsNomeConduzidoAcaoPolicial", "IdadeConduzidoAcaoPolicial", "dsTipoProcedimentoAcaoPolcial",
                "dsAtoInfracionalAcaoPolicial", "dsTipoConducaoAcaoPolicial", "Controle", "idUnico", "dsNomeJuizAcaoPolicial", "dsComarcaAcaoPolicial"}, "id='" + id + "'", null, null, null, null);

        while (cursor.moveToNext()) {

            conduzidoacaopolicial.setId(cursor.getInt(0));
            conduzidoacaopolicial.setFkAcaoPolicial(cursor.getInt(1));
            conduzidoacaopolicial.setDsNomeConduzidoAcaoPolicial(cursor.getString(2));
            conduzidoacaopolicial.setIdadeConduzidoAcaoPolicial(cursor.getInt(3));
            conduzidoacaopolicial.setDsTipoProcedimentoAcaoPolcial(cursor.getString(4));
            conduzidoacaopolicial.setDsAtoInfracionalAcaoPolicial(cursor.getString(5));
            conduzidoacaopolicial.setDsTipoConducaoAcaoPolicial(cursor.getString(6));
            conduzidoacaopolicial.setControle(cursor.getInt(7));
            conduzidoacaopolicial.setIdUnico(cursor.getString(8));
            conduzidoacaopolicial.setDsNomeJuizAcaoPolicial(cursor.getString(9));
            conduzidoacaopolicial.setDsComarcaAcaoPolicial(cursor.getString(10));

        }
        return conduzidoacaopolicial;
    }
}
