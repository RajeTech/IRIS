package com.iris.ramilton.iris;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iris.ramilton.iris.Adaptador.CvliAdaptador;
import com.iris.ramilton.iris.Adaptador.VistoriaAdaptador;
import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.dao.VistoriaDao;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.Vistoria;
import com.iris.ramilton.iris.modelo.Vitima;

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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VistoriaActivity extends AppCompatActivity {

    private ListView LVVistoriaVeiculo;
    private List<Vistoria> ListaVistoriaGeral;
    private List<Vistoria> ListaVistoriaGeralFiltrados = new ArrayList<>();
    private Vistoria vistoria;
    private VistoriaDao vistoriaDao;
    private SQLiteDatabase db;
    private DatabaseHelper conexao = null;
    private int controleenvio = 0;
    private Gerarnumeros geranumeros;
    private UsuarioDao usuarioDao;
    private Vistoria codigoaserenviado = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistoria);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        LVVistoriaVeiculo = (ListView) findViewById(R.id.ListViewVistoria);

        usuarioDao = new UsuarioDao(this);
        geranumeros = new Gerarnumeros();
        vistoria = new Vistoria();
        vistoriaDao = new VistoriaDao(this);
        ListaVistoriaGeral = vistoriaDao.retornaCarrosVistorias();
        ListaVistoriaGeralFiltrados.addAll(ListaVistoriaGeral);

        VistoriaAdaptador adaptadorVistoria = new VistoriaAdaptador(this, ListaVistoriaGeralFiltrados);
        LVVistoriaVeiculo.setAdapter(adaptadorVistoria);
        registerForContextMenu(LVVistoriaVeiculo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vistoria, menu);

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

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto_veiculo_periciar, menu);

    }

    public void AtualizarVistoria(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Vistoria vistoriaAtualizar = ListaVistoriaGeralFiltrados.get(menuInfo.position);
        Intent it = new Intent(VistoriaActivity.this, DaVistoriaActivity.class);
        it.putExtra("vistoria", vistoriaAtualizar);
        startActivity(it);
    }

    public void SincronizarVistoria(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Vistoria vistoriaSincronizar = ListaVistoriaGeralFiltrados.get(menuInfo.position);

        if (isConected(this) == false) {
            AlertDialog.Builder b = new AlertDialog.Builder(VistoriaActivity.this);
            LayoutInflater factory = LayoutInflater.from(VistoriaActivity.this);
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

        } else {
            int numeracao = vistoriaDao.retornarCodigoControle(vistoriaSincronizar.getId());

            if (numeracao != 3) {
                new Thread() {
                    public void run() {

                        EnvioDadosVistoria(vistoriaSincronizar.getId());

                    }
                }.start();
                controleenvio = 3;
                vistoria.setControle(controleenvio);
                vistoriaDao.AtualizarControleVistoria(vistoria, vistoriaSincronizar.getId(), controleenvio);
            } else {
                Toast.makeText(VistoriaActivity.this, "Vistoria já foi enviado! Escolha outro para enviar. ", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void ExcluirVistoria(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Vistoria vistoriaExcluir = ListaVistoriaGeralFiltrados.get(menuInfo.position);

        AlertDialog.Builder b = new AlertDialog.Builder(VistoriaActivity.this);
        LayoutInflater factory = LayoutInflater.from(VistoriaActivity.this);
        final View view = factory.inflate(R.layout.logotipo, null);
        b.setTitle("IRIS - Atenção!!!")
                .setView(view)
                .setMessage("Deseja Realmente Excluir Esta Vistoria?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListaVistoriaGeralFiltrados.remove(vistoriaExcluir);
                        ListaVistoriaGeral.remove(vistoriaExcluir);
                        vistoriaDao.excluirVistoria(vistoriaExcluir.getId());
                        LVVistoriaVeiculo.invalidateViews();

                    }
                }).create();
        b.show();

    }

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void EnvioDadosVistoria(int id) {

        String idservidor = String.valueOf(usuarioDao.user.getId());
        String matricula = usuarioDao.user.getDsMatricula();

        vistoria.setVistoriador_id(Integer.parseInt(idservidor));

        HttpClient httpClient = new DefaultHttpClient();

        try {
            conexao = DatabaseHelper.getInstancia(VistoriaActivity.this);
            db = conexao.getReadableDatabase();

            Cursor cursor = db.query("vistoria", new String[]{"id", "descricao", "vistoriador_id", "estepe",
                    "chaveRodas", "triangulo","macaco", "tapetes", "chaveVeiculo", "CRV", "CRLV",
                    "rodaAro", "rodaTipo", "nivelCombustivel", "quilometragem", "estadoVeiculo", "avarias","FotoFrente", "FotoFundo", "FotoLateralDireita",
                    "FotoLateralEsquerda", "FotoTeto", "FotoChassi", "FotoMotor", "FotoVidro", "FotoEtiquetaSeguranca"}, null, null, null, null, null);

            while (cursor.moveToNext()) {

                ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
                itens.add(new BasicNameValuePair("id", "" + cursor.getInt(0)));
                itens.add(new BasicNameValuePair("descricao", cursor.getString(1)));
                itens.add(new BasicNameValuePair("vistoriador_id", ""+ cursor.getInt(2)));
                itens.add(new BasicNameValuePair("estepe", "" + cursor.getInt(3)));
                itens.add(new BasicNameValuePair("chaveRodas", ""+ cursor.getInt(4)));
                itens.add(new BasicNameValuePair("triangulo", ""+ cursor.getInt(5)));
                itens.add(new BasicNameValuePair("macaco", ""+ cursor.getInt(6)));
                itens.add(new BasicNameValuePair("tapetes", ""+ cursor.getInt(7)));
                itens.add(new BasicNameValuePair("chaveVeiculo", ""+ cursor.getInt(8)));
                itens.add(new BasicNameValuePair("CRV", ""+ cursor.getInt(9)));
                itens.add(new BasicNameValuePair("CRLV", ""+ cursor.getInt(10)));
                itens.add(new BasicNameValuePair("rodaAro", cursor.getString(11)));
                itens.add(new BasicNameValuePair("rodaTipo", cursor.getString(12)));
                itens.add(new BasicNameValuePair("nivelCombustivel", cursor.getString(13)));
                itens.add(new BasicNameValuePair("quilometragem", cursor.getString(14)));
                itens.add(new BasicNameValuePair("estadoVeiculo", ""+ cursor.getInt(15)));
                itens.add(new BasicNameValuePair("avarias", cursor.getString(16)));
                itens.add(new BasicNameValuePair("FotoFrente", ""+ cursor.getBlob(17)));
                itens.add(new BasicNameValuePair("FotoFundo", ""+ cursor.getBlob(18)));
                itens.add(new BasicNameValuePair("FotoLateralDireita", ""+cursor.getBlob(19)));
                itens.add(new BasicNameValuePair("FotoLateralEsquerda", ""+cursor.getBlob(20)));
                itens.add(new BasicNameValuePair("FotoTeto", ""+cursor.getBlob(21)));
                itens.add(new BasicNameValuePair("FotoChassi", ""+cursor.getBlob(22)));
                itens.add(new BasicNameValuePair("FotoMotor", ""+cursor.getBlob(23)));
                itens.add(new BasicNameValuePair("FotoVidro", ""+cursor.getBlob(24)));
                itens.add(new BasicNameValuePair("FotoEtiquetaSeguranca", ""+cursor.getBlob(25)));


                UrlEncodedFormEntity parametro = new UrlEncodedFormEntity(itens, "utf-8");
                HttpPost httpPost = new HttpPost("https://irispoliciacivilba.com.br/app/modulo/vistoria/cadastrar?id=" + idservidor + "&matricula=" + matricula + "?");

                httpPost.setEntity(parametro);
                final HttpResponse response = httpClient.execute(httpPost);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            String resposta = EntityUtils.toString(response.getEntity());

                            JSONObject login = new JSONObject(resposta);

                            if (login.getString("resposta") == "1" || login.getInt("resposta") == 1) {

                                Toast.makeText(VistoriaActivity.this, "Sincronizado com Sucesso!!", Toast.LENGTH_LONG).show();
                            } else {

                                Toast.makeText(VistoriaActivity.this, "Erro ao Sincronizar!!", Toast.LENGTH_LONG).show();
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

    public static boolean isConected(Context cont) {
        ConnectivityManager conmag = (ConnectivityManager) cont.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conmag != null) {
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

    @Override
    public void onResume() {
        super.onResume();
        ListaVistoriaGeral = vistoriaDao.retornaCarrosVistorias();
        ListaVistoriaGeralFiltrados.clear();
        ListaVistoriaGeralFiltrados.addAll(ListaVistoriaGeral);
        LVVistoriaVeiculo.invalidateViews();
    }


}
