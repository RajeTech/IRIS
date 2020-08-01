package com.iris.ramilton.iris;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.modelo.Acaopolicial;
import com.iris.ramilton.iris.modelo.Cvli;

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

public class RelatorioIndividualAcaoPolicialactivity extends AppCompatActivity {

    private TextView TvMostrarRelatorioIndividualAcaoPolicial;
    private Acaopolicial acaopolicial = null;
    private int codigo;
    private UsuarioDao usuarioDao;
    private String idunico, co;
    private JSONObject relatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_individual_acao_policialactivity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        TvMostrarRelatorioIndividualAcaoPolicial = (TextView) findViewById(R.id.tvMostrarRelatorioIndividualAcaoPolicial);

        usuarioDao = new UsuarioDao(this);

        Intent it = getIntent();

        if (it.hasExtra("relatorioindividualacaopolicial")) {
            acaopolicial = (Acaopolicial) it.getSerializableExtra("relatorioindividualacaopolicial");

            codigo = acaopolicial.getId();
            idunico = acaopolicial.getIdUnico();

            if(isConected(this) == false){
                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioIndividualAcaoPolicialactivity.this);
                LayoutInflater factory = LayoutInflater.from(RelatorioIndividualAcaoPolicialactivity.this);
                final View view = factory.inflate(R.layout.logotipo, null);
                b.setTitle("IRIS - Atenção!!!")
                        .setView(view)
                        .setMessage("Verifique a sua conecxão com a internet!!!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                b.show();
            }else {
                Log.i("Valores","Entrou aqui");
                EnvioDadosRelatorioAcaoPolicialIndividual();
            }
        }

    }

    //método para verificar acesso a internet
    public static boolean isConected(Context cont){
        ConnectivityManager conmag = (ConnectivityManager)cont.getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conmag != null ) {
            conmag.getActiveNetworkInfo();

            //Verifica internet pela WIFI
            if (conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                return true;
            }

            //Verifica se tem internet móvel
            if (conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                return true;
            }
        }

        return false;
    }

    private void EnvioDadosRelatorioAcaoPolicialIndividual() {

        Log.i("Valores","Entrou aqui");
        HttpClient httpClient = new DefaultHttpClient();

        String senha = usuarioDao.user.getDsSenha();
        String matriculaservidor = usuarioDao.user.getDsMatricula();
        int idservidor = usuarioDao.user.getId();

        try {

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", "releaseImprensa"));
            itens.add(new BasicNameValuePair("idUnico", idunico));
            String parametro = URLEncodedUtils.format(itens, "utf-8");

            HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/acaopolicial/relatorio?" + parametro);

            final HttpResponse response = httpClient.execute(httpGet);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String resposta = EntityUtils.toString(response.getEntity());
                        JSONObject resp = null;

                        try {
                            Log.i("Valores respo",""+resposta);
                            resp = new JSONObject(resposta);

                            if (resp.getString("resposta") == "1" || resp.getInt("resposta") == 1) {
                                relatorio = new JSONObject(resp.getString("informacoes"));
                                co = relatorio.getString("relatorio");
                                TvMostrarRelatorioIndividualAcaoPolicial.setText(co);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }catch(ClientProtocolException e){}
        catch(Exception e){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_relatorio_individual_acaopolicial, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish(); // Finaliza a Activity atual e assim volta para a tela anterior
                break;
            case R.id.menucompartilharrelatorioindividualAcaoPolicial:
                compartilhar();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void compartilhar(){
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        //  share.setPackage("com.whatsapp");
        share.putExtra(Intent.EXTRA_TEXT, co);

        startActivity(Intent.createChooser(share, "Compartilhar"));
    }

}
