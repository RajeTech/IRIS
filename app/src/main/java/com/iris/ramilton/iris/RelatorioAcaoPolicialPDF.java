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
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.modelo.Acaopolicial;

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

public class RelatorioAcaoPolicialPDF extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.BASE)
    private Acaopolicial acaopolicial = null;
    private int codigo;
    private UsuarioDao usuarioDao;
    private String idunico, co;
    private JSONObject relatorio;
    private WebView webView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_acao_policial_pdf);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando o PDF...");
        progressDialog.setCancelable(false);
        webView = findViewById(R.id.relatorioacaopolicialpdf);
        webView.requestFocus();
        webView.getSettings().setJavaScriptEnabled(true);

        usuarioDao = new UsuarioDao(this);

        Intent it = getIntent();

        if (it.hasExtra("relatorioindividualacaopolicialpdf")) {
            acaopolicial = (Acaopolicial) it.getSerializableExtra("relatorioindividualacaopolicialpdf");

            codigo = acaopolicial.getId();
            Log.i("Valores cod",""+codigo);
            idunico = acaopolicial.getIdUnico();

            if(isConected(this) == false){
                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioAcaoPolicialPDF.this);
                LayoutInflater factory = LayoutInflater.from(RelatorioAcaoPolicialPDF.this);
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
                EnvioDadosRelatorioAcaoIndividualPDF();
            }
        }


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

    private void EnvioDadosRelatorioAcaoIndividualPDF() {

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
            itens.add(new BasicNameValuePair("codigo",""+codigo));
            String parametro = URLEncodedUtils.format(itens, "utf-8");

            HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/acaopolicial/relatorioindividualpdf?" + parametro);

            final HttpResponse response = httpClient.execute(httpGet);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String resposta = EntityUtils.toString(response.getEntity());
                        JSONObject resp = null;

                        try {

                            resp = new JSONObject(resposta);

                            if (resp.getString("resposta") == "1" || resp.getInt("resposta") == 1) {
                                relatorio = new JSONObject(resp.getString("informacoes"));
                                // co = relatorio.getString("relatorio");

                                //String url= relatorio.getString("url");

                                String url= relatorio.getString("url");
                                webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
                                webView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }
                                });
                                webView.setWebChromeClient(new WebChromeClient() {
                                    public void onProgressChanged(WebView view, int progress) {
                                        if (progress < 100) {
                                            progressDialog.show();
                                        }
                                        if (progress == 100) {
                                            progressDialog.dismiss();
                                        }
                                    }
                                });

                              //  webView.getSettings().setJavaScriptEnabled(true);


                               // webView.getSettings().setBuiltInZoomControls(true);

                               // webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=http://159.203.186.228/MyReports/temp/1569528095_operacaopolicial.pdf?idvariou=1603184426");


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

}
