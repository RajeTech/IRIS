package com.iris.ramilton.iris;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.modelo.Cvli;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class AturalizaResFatoActivity extends AppCompatActivity {

    private Button BtnConcluirAtualizar;
    private MultiAutoCompleteTextView EdtResFatoAtualiza;
    private Cvli idcvli = null;
    private Cvli cvli;
    private CvliDao cvliDao;
    private int codigo;
    private SQLiteDatabase db;
    private DatabaseHelper conexao = null;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aturaliza_res_fato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        BtnConcluirAtualizar = (Button) findViewById(R.id.btnAtualizaResFato);
        EdtResFatoAtualiza = (MultiAutoCompleteTextView) findViewById(R.id.edtResFatoAtualiza);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);
        usuarioDao = new UsuarioDao(this);

        Intent it = getIntent();

        if(it.hasExtra("codigoresfatoatualizar")){
            idcvli = (Cvli) it.getSerializableExtra("codigoresfatoatualizar");
            codigo = idcvli.getId();
            EdtResFatoAtualiza.setText(idcvli.getDsResFato());
        }

        BtnConcluirAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cvli.setDsResFato(EdtResFatoAtualiza.getText().toString());

                long resposta = cvliDao.AtualizarResfato(cvli,codigo);

                EnvioDados(codigo);
                if(resposta > 0){


                    Toast.makeText(AturalizaResFatoActivity.this, "Informação para relatório semanal atualizado com sucesso!!!", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(AturalizaResFatoActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void EnvioDados(int id) {

        HttpClient httpClient = new DefaultHttpClient();

        try {
            conexao = DatabaseHelper.getInstancia(AturalizaResFatoActivity.this);
            db = conexao.getReadableDatabase();

            Cursor cursor = db.query("cvlis", new String[]{"idUnico","validarcvli", "dsResFato"}, "id= '" + id + "'", null, null, null, null);

            while (cursor.moveToNext()) {

                ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
                itens.add(new BasicNameValuePair("idUnico", "" + cursor.getString(0)));
                itens.add(new BasicNameValuePair("validarcvli", "" + cursor.getInt(1)));
                itens.add(new BasicNameValuePair("dsResFato", "" + cursor.getString(2)));

                String idservidor = String.valueOf(usuarioDao.user.getId());
                String matricula = usuarioDao.user.getDsMatricula();

                UrlEncodedFormEntity parametro = new UrlEncodedFormEntity(itens, "utf-8");
                HttpPost httpPost = new HttpPost("https://irispoliciacivilba.com.br/app/modulo/cvli/cadastrar?id=" + idservidor + "&matricula=" + matricula + "?");

                httpPost.setEntity(parametro);
                final HttpResponse response = httpClient.execute(httpPost);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            String resposta = EntityUtils.toString(response.getEntity());

                            JSONObject login = new JSONObject(resposta);

                            if (login.getString("resposta") == "1" || login.getInt("resposta") == 1) {

                                Toast.makeText(AturalizaResFatoActivity.this, "Sincronizado com Sucesso!!", Toast.LENGTH_LONG).show();
                            } else {

                                Toast.makeText(AturalizaResFatoActivity.this, "Erro ao Sincronizar!!", Toast.LENGTH_LONG).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

        } catch (ClientProtocolException e) {
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_atualizaresfato, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish(); // Finaliza a Activity atual e assim volta para a tela anterior
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
