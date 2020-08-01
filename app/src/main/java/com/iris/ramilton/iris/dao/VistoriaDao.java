package com.iris.ramilton.iris.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Vistoria;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


public class VistoriaDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;
    private Vistoria vistoria;

    public VistoriaDao(Context context){

        conexao = DatabaseHelper.getInstancia(context);
    }

    public long cadastrarVistoriaWeb(Vistoria vistoria){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("id", vistoria.getId());
        cv.put("descricao", vistoria.getDescricao());


        return db.insert("vistoria", null, cv);
    }

    public long atualizarVistoriaWeb(Vistoria vistoria, String codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("id", vistoria.getId());
        cv.put("descricao", vistoria.getDescricao());

        return db.update("vistoria", cv, "descricao = '" + codigo + "'", null);
    }

    public long cadastrarVistoria(Vistoria vistoria){

        db = conexao.getWritableDatabase();

        vistoria.setControle(2);

        ContentValues cv = new ContentValues();

        cv.put("id", vistoria.getId());
        cv.put("descricao", vistoria.getDescricao());
        cv.put("estepe", vistoria.getEstepe());
        cv.put("chaveRodas", vistoria.getChaveRodas());
        cv.put("triangulo", vistoria.getTriangulo());
        cv.put("macaco", vistoria.getMacaco());
        cv.put("tapetes", vistoria.getTapetes());
        cv.put("chaveVeiculo", vistoria.getChaveVeiculo());
        cv.put("CRV", vistoria.getCRV());
        cv.put("CRLV", vistoria.getCRLV());
        cv.put("rodaAro", vistoria.getRodaAro());
        cv.put("rodaTipo", vistoria.getRodaTipo());
        cv.put("nivelCombustivel", vistoria.getNivelCombustivel());
        cv.put("quilometragem", vistoria.getQuilometragem());
        cv.put("estadoVeiculo", vistoria.getEstadoVeiculo());
        cv.put("avarias", vistoria.getAvarias());
        cv.put("FotoFrente", vistoria.getFotoFrente());
        cv.put("FotoFundo", vistoria.getFotoFundo());
        cv.put("FotoLateralDireita", vistoria.getFotoLateralDireita());
        cv.put("FotoLateralEsquerda", vistoria.getFotoLateralEsquerda());
        cv.put("FotoTeto", vistoria.getFotoTeto());
        cv.put("FotoChassi", vistoria.getFotoChassi());
        cv.put("FotoMotor", vistoria.getFotoMotor());
        cv.put("FotoVidro", vistoria.getFotoVidro());
        cv.put("FotoEtiquetaSeguranca", vistoria.getFotoEtiquetaSeguranca());
        cv.put("Controle", vistoria.getControle());

        return db.insert("vistoria", null, cv);
    }

    public long AtualizarVistoria(Vistoria vistoria, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("vistoriador_id", vistoria.getVistoriador_id());
        cv.put("estepe", vistoria.getEstepe());
        cv.put("chaveRodas", vistoria.getChaveRodas());
        cv.put("triangulo", vistoria.getTriangulo());
        cv.put("macaco", vistoria.getMacaco());
        cv.put("tapetes", vistoria.getTapetes());
        cv.put("chaveVeiculo", vistoria.getChaveVeiculo());
        cv.put("CRV", vistoria.getCRV());
        cv.put("CRLV", vistoria.getCRLV());
        cv.put("rodaAro", vistoria.getRodaAro());
        cv.put("rodaTipo", vistoria.getRodaTipo());
        cv.put("nivelCombustivel", vistoria.getNivelCombustivel());
        cv.put("quilometragem", vistoria.getQuilometragem());
        cv.put("estadoVeiculo", vistoria.getEstadoVeiculo());
        cv.put("avarias", vistoria.getAvarias());
        cv.put("Controle", vistoria.getControle());

        return db.update("vistoria", cv, "id = '" + codigo + "'", null);
    }

    public long AtualizarVistoriaFotoFrente(Vistoria vistoria, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("FotoFrente", vistoria.getFotoFrente());
        cv.put("Controle", vistoria.getControle());

        return db.update("vistoria", cv, "id = '" + codigo + "'", null);
    }

    public long AtualizarVistoriaFotoFundo(Vistoria vistoria, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("FotoFundo", vistoria.getFotoFrente());
        cv.put("Controle", vistoria.getControle());

        return db.update("vistoria", cv, "id = '" + codigo + "'", null);
    }

    public long AtualizarVistoriaFotoLateralDireita(Vistoria vistoria, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("FotoLateralDireita", vistoria.getFotoFrente());
        cv.put("Controle", vistoria.getControle());

        return db.update("vistoria", cv, "id = '" + codigo + "'", null);
    }

    public long AtualizarVistoriaFotoLateralEsquerda(Vistoria vistoria, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("FotoLateralEsquerda", vistoria.getFotoFrente());
        cv.put("Controle", vistoria.getControle());

        return db.update("vistoria", cv, "id = '" + codigo + "'", null);
    }

    public long AtualizarVistoriaFotoTeto(Vistoria vistoria, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("FotoTeto", vistoria.getFotoFrente());
        cv.put("Controle", vistoria.getControle());

        return db.update("vistoria", cv, "id = '" + codigo + "'", null);
    }

    public long AtualizarVistoriaFotoChassi(Vistoria vistoria, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("FotoChassi", vistoria.getFotoFrente());
        cv.put("Controle", vistoria.getControle());

        return db.update("vistoria", cv, "id = '" + codigo + "'", null);
    }

    public long AtualizarVistoriaFotoMotor(Vistoria vistoria, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("FotoMotor", vistoria.getFotoFrente());
        cv.put("Controle", vistoria.getControle());

        return db.update("vistoria", cv, "id = '" + codigo + "'", null);
    }

    public long AtualizarVistoriaFotoVidro(Vistoria vistoria, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("FotoVidro", vistoria.getFotoFrente());
        cv.put("Controle", vistoria.getControle());

        return db.update("vistoria", cv, "id = '" + codigo + "'", null);
    }

    public long AtualizarVistoriaFotoEtiquetaSeguranca(Vistoria vistoria, int codigo){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("FotoEtiquetaSeguranca", vistoria.getFotoFrente());
        cv.put("Controle", vistoria.getControle());

        return db.update("vistoria", cv, "id = '" + codigo + "'", null);
    }

    public long excluirVistoria(int cod){
        db = conexao.getWritableDatabase();

        return db.delete("vistoria","id='"+cod+"'",null);
    }


    public int retornarCodigoVistoriaSemParametro(){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("vistoria", new String[]{"id"}, null, null, null, null, null);
        c.moveToLast();
        int codigosemparametro = c.getInt(0);
        return codigosemparametro;
    }

    public boolean verificarIDUnico(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("vistoria", new String[]{"idUnico"}, "idUnico='"+idunico+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }

    }

    public int retornarCodId(String idunico){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("vistoria", new String[]{"id"}, "idUnico= '"+idunico+"'", null, null, null, null);
        c.moveToLast();
        int codigocontrole = c.getInt(0);
        return codigocontrole;
    }

    public int retornarCodigoControle(int id){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("vistoria", new String[]{"Controle"}, "id= '"+id+"'", null, null, null, null);
        c.moveToLast();
        int codigocontrole = c.getInt(0);
        return codigocontrole;
    }

    public boolean verificarVistoria(String mun){

        db = conexao.getReadableDatabase();

        Cursor c = db.query("vistoria", new String[]{"descricao"}, "descricao='"+mun+"'", null, null, null, null);
        c.moveToLast();

        if(c.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

    public List<Vistoria> retornaCarrosVistorias(){

        List<Vistoria> vistoriass = new ArrayList<>();

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"id", "descricao", "vistoriador_id", "estepe",
                "chaveRodas", "triangulo","macaco", "tapetes", "chaveVeiculo", "CRV", "CRLV",
                "rodaAro", "rodaTipo", "nivelCombustivel", "quilometragem", "estadoVeiculo", "avarias",
                "Controle"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Vistoria vistoria = new Vistoria();

            vistoria.setId(cursor.getInt(0));
            vistoria.setDescricao(cursor.getString(1));
            vistoria.setVistoriador_id(cursor.getInt(2));
            vistoria.setEstepe(cursor.getInt(3));
            vistoria.setChaveRodas(cursor.getInt(4));
            vistoria.setTriangulo(cursor.getInt(5));
            vistoria.setMacaco((cursor.getInt(6)));
            vistoria.setTapetes((cursor.getInt(7)));
            vistoria.setChaveVeiculo(cursor.getInt(8));
            vistoria.setCRV(cursor.getInt(9));
            vistoria.setCRLV(cursor.getInt(10));
            vistoria.setRodaAro(cursor.getString(11));
            vistoria.setRodaTipo(cursor.getString(12));
            vistoria.setNivelCombustivel(cursor.getString(13));
            vistoria.setQuilometragem(cursor.getString(14));
            vistoria.setEstadoVeiculo(cursor.getInt(15));
            vistoria.setAvarias(cursor.getString(16));
            vistoria.setControle(cursor.getInt(17));

            vistoriass.add(vistoria);
        }
        return vistoriass;
    }

    public Bitmap retornaFotofrente(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoFrente"}, "id='"+id+"'", null, null, null, null);

        vistoria = new Vistoria();

        while (cursor.moveToNext()) {
            vistoria.setFotoFrente(cursor.getBlob(0));
        }

        byte[] outImage = vistoria.getFotoFrente();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
        return imageBitmap;
    }

    public Bitmap retornaFotofundo(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoFundo"}, "id='"+id+"'", null, null, null, null);

        vistoria = new Vistoria();

        while (cursor.moveToNext()) {
            vistoria.setFotoFundo(cursor.getBlob(0));
        }

        byte[] outImage = vistoria.getFotoFundo();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
        return imageBitmap;
    }

    public boolean verificarFotoFrente(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoFrente"}, "id='"+id+"'", null, null, null, null);

        cursor.moveToLast();

        if(cursor.getBlob(0) != null){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificarFotoFundo(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoFundo"}, "id='"+id+"'", null, null, null, null);

        cursor.moveToLast();

        if(cursor.getBlob(0) != null){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificarFotoLateralDireita(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoLateralDireita"}, "id='"+id+"'", null, null, null, null);

        cursor.moveToLast();

        if(cursor.getBlob(0) != null){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificarFotoLateralEsquerda(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoLateralEsquerda"}, "id='"+id+"'", null, null, null, null);

        cursor.moveToLast();

        if(cursor.getBlob(0) != null){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificarFotoTeto(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoTeto"}, "id='"+id+"'", null, null, null, null);

        cursor.moveToLast();

        if(cursor.getBlob(0) != null){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificarFotoChassi(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoChassi"}, "id='"+id+"'", null, null, null, null);

        cursor.moveToLast();

        if(cursor.getBlob(0) != null){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificarFotoMotor(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoMotor"}, "id='"+id+"'", null, null, null, null);

        cursor.moveToLast();

        if(cursor.getBlob(0) != null){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificarFotoVidro(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoVidro"}, "id='"+id+"'", null, null, null, null);

        cursor.moveToLast();

        if(cursor.getBlob(0) != null){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificarFotoEtiquetaSeguranca(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoEtiquetaSeguranca"}, "id='"+id+"'", null, null, null, null);

        cursor.moveToLast();

        if(cursor.getBlob(0) != null){
            return true;
        }else{
            return false;
        }
    }

    public Bitmap retornaFotolateraldireita(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoLateralDireita"}, "id='"+id+"'", null, null, null, null);

        vistoria = new Vistoria();

        while (cursor.moveToNext()) {
            vistoria.setFotoLateralDireita(cursor.getBlob(0));
        }

        byte[] outImage = vistoria.getFotoLateralDireita();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
        return imageBitmap;
    }

    public Bitmap retornaFotolateralesquerda(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoLateralEsquerda"}, "id='"+id+"'", null, null, null, null);

        vistoria = new Vistoria();

        while (cursor.moveToNext()) {
            vistoria.setFotoLateralEsquerda(cursor.getBlob(0));
        }

        byte[] outImage = vistoria.getFotoLateralEsquerda();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
        return imageBitmap;
    }

    public Bitmap retornaFototeto(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoTeto"}, "id='"+id+"'", null, null, null, null);

        vistoria = new Vistoria();

        while (cursor.moveToNext()) {
            vistoria.setFotoTeto(cursor.getBlob(0));
        }

        byte[] outImage = vistoria.getFotoTeto();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
        return imageBitmap;
    }

    public Bitmap retornaFotochassi(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoChassi"}, "id='"+id+"'", null, null, null, null);

        vistoria = new Vistoria();

        while (cursor.moveToNext()) {
            vistoria.setFotoChassi(cursor.getBlob(0));
        }

        byte[] outImage = vistoria.getFotoChassi();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
        return imageBitmap;
    }

    public Bitmap retornaFotomotor(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoMotor"}, "id='"+id+"'", null, null, null, null);

        vistoria = new Vistoria();

        while (cursor.moveToNext()) {
            vistoria.setFotoMotor(cursor.getBlob(0));
        }

        byte[] outImage = vistoria.getFotoMotor();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
        return imageBitmap;
    }

    public Bitmap retornaFotovidro(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoVidro"}, "id='"+id+"'", null, null, null, null);

        vistoria = new Vistoria();

        while (cursor.moveToNext()) {
            vistoria.setFotoVidro(cursor.getBlob(0));
        }

        byte[] outImage = vistoria.getFotoVidro();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
        return imageBitmap;
    }

    public Bitmap retornaFotoetiquetaseguranca(int id){

        db = conexao.getReadableDatabase();

        Cursor cursor = db.query("vistoria", new String[]{"FotoEtiquetaSeguranca"}, "id='"+id+"'", null, null, null, null);

        vistoria = new Vistoria();

        while (cursor.moveToNext()) {
            vistoria.setFotoEtiquetaSeguranca(cursor.getBlob(0));
        }

        byte[] outImage = vistoria.getFotoEtiquetaSeguranca();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
        return imageBitmap;
    }

    public void AtualizarControleVistoria(Vistoria vistoria, int id, int controle){

        db = conexao.getWritableDatabase();

        ContentValues cv = new ContentValues();

        vistoria.setId(id);
        vistoria.setControle(controle);

        cv.put("Controle", vistoria.getControle());

        db.update("vistoria", cv, "id = ?", new String[]{String.valueOf(vistoria.getId())});

    }

}
