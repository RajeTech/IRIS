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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.iris.ramilton.iris.dao.UsuarioDao;
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

public class RelatorioIndividualPDF extends AppCompatActivity {

    private Cvli cvli = null;
    private int codigo;
    private UsuarioDao usuarioDao;
    private String idunico, co;
    private JSONObject relatorio;
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_individual_pdf);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        webView = findViewById(R.id.relatorioindividualPDF);
        progressBar = findViewById(R.id.progressBarRelPdf);

        usuarioDao = new UsuarioDao(this);

        Intent it = getIntent();

        if (it.hasExtra("relatorioindividualcvli")) {
            cvli = (Cvli) it.getSerializableExtra("relatorioindividualcvli");

            codigo = cvli.getId();
            idunico = cvli.getIdUnico();

            if(isConected(this) == false){
                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioIndividualPDF.this);
                LayoutInflater factory = LayoutInflater.from(RelatorioIndividualPDF.this);
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
                EnvioDadosRelatorioCVLIIndividual();
            }
        }

        progressBar.setVisibility(View.VISIBLE);
    }

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

    private void EnvioDadosRelatorioCVLIIndividual() {

        HttpClient httpClient = new DefaultHttpClient();

        String senha = usuarioDao.user.getDsSenha();
        String matriculaservidor = usuarioDao.user.getDsMatricula();
        int idservidor = usuarioDao.user.getId();

        try {

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", "individual"));
            itens.add(new BasicNameValuePair("idUnico", idunico));
            String parametro = URLEncodedUtils.format(itens, "utf-8");

            HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/cvli/relatorio?" + parametro);

            final HttpResponse response = httpClient.execute(httpGet);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String resposta = EntityUtils.toString(response.getEntity());
                        JSONObject resp = null;

                        Log.i("Valores",""+resposta);
                        try {
                            resp = new JSONObject(resposta);

                            if (resp.getString("resposta") == "1" || resp.getInt("resposta") == 1) {
                                relatorio = new JSONObject(resp.getString("informacoes"));
                               // co = relatorio.getString("relatorio");

                                String url="";
                                String finalurl="http://drive.google.com/viewerng/viewer?embedded=true&url="+url;

                                webView.getSettings().setJavaScriptEnabled(true);
                                webView.getSettings().setBuiltInZoomControls(true);

                                webView.setWebChromeClient(new WebChromeClient(){

                                    @Override
                                    public void onProgressChanged(WebView view, int newProgress){
                                        super.onProgressChanged(view,newProgress);

                                        getSupportActionBar().setTitle("Carregando o arquivo...");
                                        if(newProgress == 100){
                                            progressBar.setVisibility(View.GONE);
                                            getSupportActionBar().setTitle(R.string.app_name);
                                        }
                                    }
                                });

                                webView.loadUrl(finalurl);
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
        getMenuInflater().inflate(R.menu.menu_relatorio_individualpdf, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish(); // Finaliza a Activity atual e assim volta para a tela anterior
                break;
            case R.id.menucompartilharrelatorioindividualPDF:
                compartilharPDF();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void compartilharPDF(){
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        //  share.setPackage("com.whatsapp");
        share.putExtra(Intent.EXTRA_TEXT, co);

        startActivity(Intent.createChooser(share, "Compartilhar"));
    }
}
