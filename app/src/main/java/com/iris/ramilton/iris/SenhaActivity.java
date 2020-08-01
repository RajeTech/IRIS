package com.iris.ramilton.iris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.UsuarioDao;

public class SenhaActivity extends AppCompatActivity {

    private TextInputEditText TIETEntrar, TIETCadastro;
    private Button BtnCadastroSenha;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);

        TIETEntrar = (TextInputEditText) findViewById(R.id.tietEntrar);
        TIETCadastro = (TextInputEditText) findViewById(R.id.tietCadastro);
        BtnCadastroSenha = (Button) findViewById(R.id.btnCadastroSenha4);

        usuarioDao = new UsuarioDao(this);


        if (usuarioDao.retornarSenhaQuatro() == 0) {
            TIETCadastro.setVisibility(View.VISIBLE);
            BtnCadastroSenha.setVisibility(View.VISIBLE);
            ClicarCadastro();
        } else {
            TIETEntrar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 4)
                    {
                        conferirSenha();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private void ClicarCadastro() {
        BtnCadastroSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    private void cadastrar() {

        if (TIETEntrar.getText().toString().equals(TIETCadastro.getText().toString())) {
            usuarioDao.cadastroSenha(Integer.parseInt(TIETCadastro.getText().toString()));
            Toast.makeText(SenhaActivity.this, "Senha Cadastrada com Sucesso!!!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SenhaActivity.this, PrincipalActivity.class));
            finish();
        } else {
            Toast.makeText(SenhaActivity.this, "Senha não confere!!!", Toast.LENGTH_LONG).show();
        }

    }

    private void conferirSenha() {
        if (TIETEntrar.getText().toString().trim().equals(String.valueOf(usuarioDao.retornarSenhaQuatro()))) {
            startActivity(new Intent(SenhaActivity.this, PrincipalActivity.class));
            finish();
        }else{
            Toast.makeText(SenhaActivity.this, "Senha não confere!!!", Toast.LENGTH_LONG).show();
        }
    }
}
