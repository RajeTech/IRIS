package com.iris.ramilton.iris.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.Usuario;

import java.util.HashMap;

public class UsuarioDao {

    private final DatabaseHelper conexao;
    private SQLiteDatabase db;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public Usuario user;

    public UsuarioDao(Context context) {

        conexao = DatabaseHelper.getInstancia(context);
        pref = context.getSharedPreferences("sessaoUsuarioIris", 0); // 0 - for private mode
        editor = pref.edit();
        user = new Usuario();
        povoarDadosUsuario();
    }

    public void cadastroSenha(int senhaquatro){
        editor.putInt("senhaquatrodigitos", senhaquatro);
        editor.commit();
    }

    public int retornarSenhaQuatro() {
        if (verificarSessao()) {
            return pref.getInt("senhaquatrodigitos", 0);
        }
        return 0;
    }



    public void inserirInformacaoUsuario(int id, String senha, String matricula, int unidadeid, String unidadedescricao, String nome) {

        editor.putInt("id", id);
        editor.putString("senha", senha);
        editor.putString("matricula", matricula);
        editor.putInt("unidade-id", unidadeid);
        editor.putString("unidadeDescricao", unidadedescricao);
        editor.putString("nome", nome);
        editor.commit();
    }

    public boolean verificarSessao() {
        if (pref.getInt("id", 0) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void povoarDadosUsuario() {
        if (verificarSessao()) {
            user.setId(pref.getInt("id", 0));
            user.setDsSenha(pref.getString("senha", null));
            user.setDsMatricula(pref.getString("matricula", null));
            user.setDsIdUnidade(pref.getInt("unidade-id", 0));
            user.setDsUnidade(pref.getString("unidadeDescricao", null));
            user.setDsNome(pref.getString("nome", null));
        }
    }

}
