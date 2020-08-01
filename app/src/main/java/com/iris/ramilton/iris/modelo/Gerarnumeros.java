package com.iris.ramilton.iris.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.CvliActivity;
import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.dao.UsuarioDao;

import java.util.Calendar;
import java.util.Random;

public class Gerarnumeros {

    private Usuario usuario;
    private UsuarioDao usuarioDao;

    public String RetornarNumeroTabelaCVLI(Context context){

        usuarioDao = new UsuarioDao(context);
        int id = usuarioDao.user.getId();
        String valor = String.valueOf(id);
        Random gerador = new Random();
        Calendar cal = Calendar.getInstance();

        valor = gerador.nextInt() +valor + cal.getTimeInMillis();
        return valor;
    }
}
