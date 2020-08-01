package com.iris.ramilton.iris;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.dao.UsuarioDao;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private Button botaoEntrar;
    private EditText EdtLogin, EdtSenha;
    private Spinner SpUnidades;
    private SQLiteDatabase db;
    private DatabaseHelper conexao = null;
    private UsuarioDao usuarioDao;
    private String senha, matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuarioDao =  new UsuarioDao(this);

        if (usuarioDao.verificarSessao()){

            startActivity(new Intent(LoginActivity.this, SenhaActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        botaoEntrar = (Button) findViewById(R.id.buttonEntrarID);
        EdtLogin = (EditText) findViewById(R.id.editTextMatriculaID);
        EdtSenha = (EditText) findViewById(R.id.editTextSenhaID);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                senha = EdtSenha.getText().toString();
                matricula = EdtLogin.getText().toString();

                if(senha.equals("")){

                    AlertDialog.Builder b = new AlertDialog.Builder(LoginActivity.this);
                    LayoutInflater factory = LayoutInflater.from(LoginActivity.this);
                    final View view = factory.inflate(R.layout.logotipo, null);
                    b.setTitle("IRIS - Atenção!!!")
                            .setView(view)
                            .setMessage("O campo SENHA não pode ficar em branco!!!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtSenha.setFocusable(true);

                                }
                            }).create();
                    b.show();
                }else if(matricula.equals("")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(LoginActivity.this);
                    LayoutInflater factory = LayoutInflater.from(LoginActivity.this);
                    final View view = factory.inflate(R.layout.logotipo, null);
                    b.setTitle("IRIS - Atenção!!!")
                            .setView(view)
                            .setMessage("O campo Matricula não pode ficar em branco!!!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtLogin.setFocusable(true);

                                }
                            }).create();
                    b.show();
                    }else {
                    ReceberDados();
                }
            }
        });
    }
    private void ReceberDados() {

        HttpClient httpClient = new DefaultHttpClient();

        try {
            conexao = DatabaseHelper.getInstancia(LoginActivity.this);
            db = conexao.getReadableDatabase();

                HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/login/logar?senha="+senha.trim()+"&matricula="+matricula+"");

                final HttpResponse response = httpClient.execute(httpGet);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String resposta = EntityUtils.toString(response.getEntity());

                            try {

                                JSONObject login = new JSONObject(resposta);

                                if(login.getString("resposta")=="1" || login.getInt("resposta")== 1){

                                JSONObject usuario = new JSONObject(login.getString("informacoes"));

                                usuarioDao.inserirInformacaoUsuario(usuario.getInt("id"),usuario.getString("senha"), usuario.getString("matricula"),
                                        usuario.getInt("unidade-id"), usuario.getString("unidadeDescricao"), usuario.getString("nome"));
                                    Toast.makeText(getBaseContext(), "Bem vindo "+usuario.getString("nome")+"!!!", Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                                    finish();
                               }else
                                {
                                    Toast.makeText(getBaseContext(), "Matricula ou Senha inválida!!!", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
