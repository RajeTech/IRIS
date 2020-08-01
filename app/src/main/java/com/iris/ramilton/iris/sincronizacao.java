package com.iris.ramilton.iris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.dao.AutoriaDao;
import com.iris.ramilton.iris.dao.CarroDao;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.EquipeLevantamentoDao;
import com.iris.ramilton.iris.dao.EquipePeritoDao;
import com.iris.ramilton.iris.dao.EquipePreservacaoLocalDao;
import com.iris.ramilton.iris.dao.GuiaPericiaProvidenciaDao;
import com.iris.ramilton.iris.dao.MarcaDao;
import com.iris.ramilton.iris.dao.MunicipioDao;
import com.iris.ramilton.iris.dao.ObjetosApreendidosDao;
import com.iris.ramilton.iris.dao.UnidadeBODao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.dao.VistoriaDao;
import com.iris.ramilton.iris.dao.VitimaDao;
import com.iris.ramilton.iris.modelo.Autoria;
import com.iris.ramilton.iris.modelo.Carro;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Equipelevantamento;
import com.iris.ramilton.iris.modelo.Equipeperito;
import com.iris.ramilton.iris.modelo.Equipepreservacaolocal;
import com.iris.ramilton.iris.modelo.GuiapericiaProvidencia;
import com.iris.ramilton.iris.modelo.Marca;
import com.iris.ramilton.iris.modelo.Municipio;
import com.iris.ramilton.iris.modelo.ObjetosApreendidos;
import com.iris.ramilton.iris.modelo.UnidadeBO;
import com.iris.ramilton.iris.modelo.Vistoria;
import com.iris.ramilton.iris.modelo.Vitima;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class sincronizacao extends AppCompatActivity {


    private SQLiteDatabase db;
    private DatabaseHelper conexao = null;
    private UsuarioDao usuarioDao;
    private Cvli cvli;
    private CvliDao cvliDao;
    private Vitima vitima;
    private UnidadeBO unidadeBO;
    private UnidadeBODao unidadeBODao;
    private Vistoria vistoria;
    private VistoriaDao vistoriaDao;
    private Marca marca;
    private MarcaDao marcaDao;
    private Municipio municipio;
    private MunicipioDao municipioDao;
    private VitimaDao vitimaDao;
    private Autoria autoria;
    private AutoriaDao autoriaDao;
    private Equipepreservacaolocal equipepreservacaolocal;
    private EquipePreservacaoLocalDao equipePreservacaoLocalDao;
    private Equipelevantamento equipelevantamento;
    private EquipeLevantamentoDao equipeLevantamentoDao;
    private Equipeperito equipeperito;
    private EquipePeritoDao equipePeritoDao;
    private Carro carro;
    private CarroDao carroDao;
    private ObjetosApreendidos objetosApreendidos;
    private ObjetosApreendidosDao objetosApreendidosDao;
    private GuiapericiaProvidencia guiapericiaProvidencia;
    private GuiaPericiaProvidenciaDao guiaPericiaProvidenciaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizacao);

        usuarioDao = new UsuarioDao(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isConected(sincronizacao.this) == false) {

            AlertDialog.Builder b = new AlertDialog.Builder(sincronizacao.this);
            LayoutInflater factory = LayoutInflater.from(sincronizacao.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Verifique a sua conecxão com a internet!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  carregando.setVisibility(ProgressBar.GONE);
                        }
                    }).create();
            b.show();
        } else {

            new sincronizacaoApp().execute();
        }
    }

    public void ReceberdadosCVLI(String idservidor, String matricula) {


    }

    public void ReceberdadosMunicipio(String idservidor, String matricula) {




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

    private class sincronizacaoApp extends AsyncTask<Void, Void, Void>{

        sincronizacao sin;
        private ProgressBar carregando;
        private TextView TvInicioconexao, TvRecebendoCvlis, TvRecebendoMunicipio, TvFinalizandoconexao, TvRecebendoMarca, TvRecebendoUnidade, TvRecebendoVeiculos;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            carregando = (ProgressBar) findViewById(R.id.carregando);
            TvInicioconexao = (TextView) findViewById(R.id.tvInicioconexao);
            TvRecebendoCvlis = (TextView) findViewById(R.id.tvRecebendoDadosCvli);
            TvRecebendoMunicipio = (TextView) findViewById(R.id.tvRecebendodadosMunicipio);
            TvFinalizandoconexao = (TextView) findViewById(R.id.tvFinalizandoConexao);
            TvRecebendoMarca = (TextView) findViewById(R.id.tvRecebendoMarca);
            TvRecebendoUnidade = (TextView) findViewById(R.id.tvUnidadesBO);
            TvRecebendoVeiculos = (TextView) findViewById(R.id.tvListarVeiculos);
            carregando.setVisibility(ProgressBar.VISIBLE);
            Toast.makeText(sincronizacao.this,"Dispositivo Sincronizando!!!!",Toast.LENGTH_LONG).show();
            sin = new sincronizacao();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {

            TvInicioconexao.setVisibility(View.VISIBLE);
            String idservidor = String.valueOf(usuarioDao.user.getId());
            String matricula = usuarioDao.user.getDsMatricula();

            HttpClient httpClient = new DefaultHttpClient();

            try {

                conexao = DatabaseHelper.getInstancia(sincronizacao.this);
                db = conexao.getReadableDatabase();


                HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/cvli/listar?id=" + idservidor + "&matricula=" + matricula + "?");

                final HttpResponse response = httpClient.execute(httpGet);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            TvRecebendoCvlis.setVisibility(View.VISIBLE);

                            String resposta = EntityUtils.toString(response.getEntity());


                            JSONObject dados = new JSONObject(resposta);

                            if (dados.getString("resposta") == "1" || dados.getInt("resposta") == 1) {

                                JSONArray informacao = new JSONArray(dados.getString("informacoes"));

                                int i = 0;

                                for (int j = 0; j < informacao.length(); j++) {

                                    cvli = new Cvli();
                                    cvliDao = new CvliDao(getApplicationContext());
                                    //dados // todos os campos do CVLI
                                    JSONObject dado = informacao.getJSONObject(j);

                                    String idun = dado.getString("idUnico");


                                    if (cvliDao.verificarIDUnico(dado.getString("idUnico")) == true) {

                                        cvli.PreencherCamposCVLI(dado);

                                        cvliDao.AtualizarCVLIWEB(cvli, dado.getString("idUnico"));

                                    } else {
                                        cvli.PreencherCamposCVLI(dado);

                                        long resp = cvliDao.CadastrarCVLIWEB(cvli);

                                    }

                                    int codirecebido = cvliDao.retornarCodId(idun);

                                    JSONArray vitimas = new JSONArray(dado.getString("vitimas"));

                                    if (vitimas.length() > 0)
                                        for (int h = 0; h < vitimas.length(); h++) {
                                            vitima = new Vitima();
                                            vitimaDao = new VitimaDao(getApplicationContext());

                                            vitima.setFkCvli(codirecebido);

                                            JSONObject vit = vitimas.getJSONObject(h);

                                            if (vit.has("idUnico")) {

                                                if (vitimaDao.verificarIDUnico(vit.getString("idUnico")) == true) {
                                                    vitima.PreencherCamposCVLIVitimas(vit);

                                                    vitimaDao.atualizarCVLIVitimaWEB(vitima, vit.getString("idUnico"));
                                                } else {
                                                    vitima.PreencherCamposCVLIVitimas(vit);

                                                    vitimaDao.cadastrarCVLIVitimaWEB(vitima);
                                                }
                                            }

                                        }

                                    JSONArray autorias = new JSONArray(dado.getString("autorias"));

                                    if (autorias.length() > 0)
                                        for (i = 0; i < autorias.length(); i++) {

                                            autoria = new Autoria();
                                            autoriaDao = new AutoriaDao(getApplicationContext());

                                            autoria.setFkCvli(codirecebido);

                                            JSONObject aut = autorias.getJSONObject(i);

                                            if (aut.has("idUnico")) {

                                                if (autoriaDao.verificarIDUnico(aut.getString("idUnico")) == true) {
                                                    autoria.PreencherCamposCVLIautoria(aut);
                                                    autoriaDao.atualizarCVLIAutoriaWEB(autoria, aut.getString("idUnico"));
                                                } else {
                                                    autoria.PreencherCamposCVLIautoria(aut);
                                                    autoriaDao.cadastrarCVLIAutoriaWEB(autoria);
                                                }
                                            }

                                        }

                                    JSONArray equipreserlocal = new JSONArray(dado.getString("equipePreLocais"));

                                    if (equipreserlocal.length() > 0)
                                        for (i = 0; i < equipreserlocal.length(); i++) {

                                            equipepreservacaolocal = new Equipepreservacaolocal();
                                            equipePreservacaoLocalDao = new EquipePreservacaoLocalDao(getApplicationContext());

                                            equipepreservacaolocal.setFkCvli(codirecebido);

                                            JSONObject eqprelocal = equipreserlocal.getJSONObject(i);

                                            if (eqprelocal.has("idUnico")) {

                                                if (equipePreservacaoLocalDao.verificarIDUnico(eqprelocal.getString("idUnico")) == true) {
                                                    equipepreservacaolocal.PreencherCamposCVLIEqPresLocal(eqprelocal);

                                                    equipePreservacaoLocalDao.atualizarCVLIEquipePreservacaoLocalWEB(equipepreservacaolocal, eqprelocal.getString("idUnico"));
                                                } else {
                                                    equipepreservacaolocal.PreencherCamposCVLIEqPresLocal(eqprelocal);
                                                    equipePreservacaoLocalDao.cadastrarCVLIEquipePreservacaoLocalWEB(equipepreservacaolocal);
                                                }
                                            }

                                        }

                                    JSONArray equiplevantamento = new JSONArray(dado.getString("equipePreLevant"));

                                    if (equiplevantamento.length() > 0)
                                        for (i = 0; i < equiplevantamento.length(); i++) {
                                            equipelevantamento = new Equipelevantamento();
                                            equipeLevantamentoDao = new EquipeLevantamentoDao(getApplicationContext());
                                            equipelevantamento.setFkCvli(codirecebido);
                                            JSONObject eqlevan = equiplevantamento.getJSONObject(i);
                                            if (eqlevan.has("idUnico")) {
                                                if (equipeLevantamentoDao.verificarIDUnico("idUnico") == true) {
                                                    equipelevantamento.PreencherCamposCVLIEqLevantamento(eqlevan);
                                                    equipeLevantamentoDao.atualizaCVLIEquipeLevantamentoWEB(equipelevantamento, eqlevan.getString("idUnico"));
                                                } else {
                                                    equipelevantamento.PreencherCamposCVLIEqLevantamento(eqlevan);
                                                    equipeLevantamentoDao.cadastrarCVLIEquipeLevantamentoWEB(equipelevantamento);
                                                }
                                            }
                                        }

                                    JSONArray equipperito = new JSONArray(dado.getString("equipeEquipPeri"));

                                    if (equipperito.length() > 0)
                                        for (i = 0; i < equipperito.length(); i++) {

                                            equipeperito = new Equipeperito();
                                            equipePeritoDao = new EquipePeritoDao(getApplicationContext());
                                            equipeperito.setFkCvli(codirecebido);
                                            JSONObject eqperito = equipperito.getJSONObject(i);
                                            if (eqperito.has("idUnico")) {
                                                if (equipePeritoDao.verificarIDUnico("idUnico") == true) {
                                                    equipeperito.PreencherCamposCVLIEqPerito(eqperito);
                                                    equipePeritoDao.atualizarCVLIEquipePeritoWEB(equipeperito, eqperito.getString("idUnico"));
                                                } else {
                                                    equipeperito.PreencherCamposCVLIEqPerito(eqperito);
                                                    equipePeritoDao.cadastrarCVLIEquipePeritoWEB(equipeperito);
                                                }
                                            }

                                        }

                                    JSONArray guias = new JSONArray(dado.getString("guiaspericiais"));
                                    if (guias.length() > 0)
                                        for (i = 0; i < guias.length(); i++) {

                                            guiapericiaProvidencia = new GuiapericiaProvidencia();
                                            guiaPericiaProvidenciaDao = new GuiaPericiaProvidenciaDao(getApplicationContext());
                                            guiapericiaProvidencia.setFkCvli(codirecebido);
                                            JSONObject guia = guias.getJSONObject(i);
                                            if (guia.has("idUnico")) {
                                                if (guiaPericiaProvidenciaDao.verificarIDUnico("idUnico") == true) {
                                                    guiapericiaProvidencia.PreencherCamposCVLIGuias(guia);
                                                    guiaPericiaProvidenciaDao.atualizaGuiaPericialProvidenciaWEB(guiapericiaProvidencia, guia.getString("idUnico"));
                                                } else {
                                                    guiapericiaProvidencia.PreencherCamposCVLIGuias(guia);
                                                    guiaPericiaProvidenciaDao.cadastrarGuiaPericialProvidenciaWEB(guiapericiaProvidencia);
                                                }
                                            }

                                        }

                                    JSONArray objetos = new JSONArray(dado.getString("objetosApreendido"));

                                    if (objetos.length() > 0)
                                        for (i = 0; i < objetos.length(); i++) {

                                            objetosApreendidos = new ObjetosApreendidos();
                                            objetosApreendidosDao = new ObjetosApreendidosDao(getApplicationContext());

                                            objetosApreendidos.setFkCvli(codirecebido);

                                            JSONObject obj = objetos.getJSONObject(i);

                                            if (obj.has("idUnico")) {
                                                if (objetosApreendidosDao.verificarIDUnico("idUnico") == true) {
                                                    objetosApreendidos.PreencherCamposCVLIObjetos(obj);
                                                    objetosApreendidosDao.atualizaCVLIObjetosApreendidosWEB(objetosApreendidos, obj.getString("idUnico"));
                                                } else {
                                                    objetosApreendidos.PreencherCamposCVLIObjetos(obj);
                                                    objetosApreendidosDao.cadastrarCVLIObjetosApreendidosWEB(objetosApreendidos);
                                                }
                                            }

                                        }

                                    JSONArray carros = new JSONArray(dado.getString("carros"));

                                    if (carros.length() > 0)
                                        for (i = 0; i < carros.length(); i++) {

                                            carro = new Carro();
                                            carroDao = new CarroDao(getApplicationContext());
                                            carro.setFkCvli(codirecebido);
                                            JSONObject car = carros.getJSONObject(i);
                                            if (car.has("idUnico")) {
                                                if (carroDao.verificarIDUnico("idUnico") == true) {
                                                    carro.PreencherCamposCVLICarro(car);
                                                    carroDao.atualizarCVLICarroWEB(carro, car.getString("idUnico"));
                                                } else {
                                                    carro.PreencherCamposCVLICarro(car);
                                                    carroDao.cadastrarCVLICarroWEB(carro);
                                                }
                                            }

                                        }

                                }

                            } else {

                                Toast.makeText(sincronizacao.this, "Erro ao Sincronizar!!", Toast.LENGTH_LONG).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.i("Valores Pars", "" + e.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("Valores IO", "" + e.getMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("Valores JOs", "" + e.getMessage());
                        }
                    }
                });
            } catch (ClientProtocolException e) {
            } catch (Exception e) {

                Log.i("Valores Excep", "" + e.getMessage());
            } finally {
            }

            HttpClient httpClient1 = new DefaultHttpClient();

            try {
                conexao = DatabaseHelper.getInstancia(sincronizacao.this);
                db = conexao.getReadableDatabase();

                HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/cvli/listarmunicipio?id=" + idservidor + "&matricula=" + matricula + "?");

                final HttpResponse response = httpClient1.execute(httpGet);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            TvRecebendoMunicipio.setVisibility(View.VISIBLE);

                            String resposta = EntityUtils.toString(response.getEntity());

                            JSONObject dados = new JSONObject(resposta);

                            if (dados.getString("resposta") == "1" || dados.getInt("resposta") == 1) {

                                JSONArray informacao = new JSONArray(dados.getString("informacoes"));

                                int i = 0;

                                for (int j = 0; j < informacao.length(); j++) {

                                    municipio = new Municipio();
                                    municipioDao = new MunicipioDao(getApplicationContext());
                                    //dados // todos os campos do CVLI
                                    JSONObject dado = informacao.getJSONObject(j);

                                    if (municipioDao.verificarMunicipio(dado.getString("descricao")) == true) {

                                        municipio.PreencherCamposMunicipio(dado);

                                        municipioDao.atualizarMunicipioWEB(municipio, dado.getString("descricao"));

                                    } else {
                                        municipio.PreencherCamposMunicipio(dado);

                                        long resp = municipioDao.cadastrarMunicipioWEB(municipio);

                                    }
                                }

                            } else {

                                Toast.makeText(sincronizacao.this, "Erro ao Sincronizar!!", Toast.LENGTH_LONG).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.i("Valores Pars", "" + e.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("Valores IO", "" + e.getMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("Valores JOssss", "" + e.getMessage());
                        }
                    }
                });
            } catch (ClientProtocolException e) {
            } catch (Exception e) {

                Log.i("Valores Excep", "" + e.getMessage());
            } finally {

            }

          //codigo de listar as marcas
            /**HttpClient httpClient2 = new DefaultHttpClient();

            try {
                conexao = DatabaseHelper.getInstancia(sincronizacao.this);
                db = conexao.getReadableDatabase();

                HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/vistoria/listarmarca?id=" + idservidor + "&matricula=" + matricula + "?");

                final HttpResponse response = httpClient2.execute(httpGet);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            TvRecebendoMarca.setVisibility(View.VISIBLE);

                            String resposta = EntityUtils.toString(response.getEntity());

                            JSONObject dados = new JSONObject(resposta);

                            if (dados.getString("resposta") == "1" || dados.getInt("resposta") == 1) {

                                JSONArray informacao = new JSONArray(dados.getString("informacoes"));

                                int i = 0;

                                for (int j = 0; j < informacao.length(); j++) {

                                    marca = new Marca();
                                    marcaDao = new MarcaDao(getApplicationContext());

                                    JSONObject dado = informacao.getJSONObject(j);

                                    if (marcaDao.verificarMarca(dado.getString("descricao")) == true) {

                                        marca.PreencherCamposMarca(dado);

                                        marcaDao.atualizarMarcaWEB(marca, dado.getString("descricao"));

                                    } else {
                                        marca.PreencherCamposMarca(dado);

                                        long resp = marcaDao.cadastrarMarcaWEB(marca);

                                    }
                                }

                            } else {

                                Toast.makeText(sincronizacao.this, "Erro ao Sincronizar!!", Toast.LENGTH_LONG).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.i("Valores Pars", "" + e.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("Valores IO", "" + e.getMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("Valores JOssss", "" + e.getMessage());
                        }
                    }
                });
            } catch (ClientProtocolException e) {
            } catch (Exception e) {

                Log.i("Valores Excep", "" + e.getMessage());
            } finally {

            }
*/

            //início do código para listar a unidadeBO
            /* HttpClient httpClient3 = new DefaultHttpClient();

            try {
                conexao = DatabaseHelper.getInstancia(sincronizacao.this);
                db = conexao.getReadableDatabase();

                HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/vistoria/listarunidadebo?id=" + idservidor + "&matricula=" + matricula + "?");


                final HttpResponse response = httpClient3.execute(httpGet);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            TvRecebendoUnidade.setVisibility(View.VISIBLE);

                            String resposta = EntityUtils.toString(response.getEntity());

                            JSONObject dados = new JSONObject(resposta);

                            if (dados.getString("resposta") == "1" || dados.getInt("resposta") == 1) {

                                JSONArray informacao = new JSONArray(dados.getString("informacoes"));

                                int i = 0;

                                for (int j = 0; j < informacao.length(); j++) {

                                    unidadeBO = new UnidadeBO();
                                    unidadeBODao = new UnidadeBODao(getApplicationContext());

                                    JSONObject dado = informacao.getJSONObject(j);

                                    if (unidadeBODao.verificarUnidadeBO(dado.getString("descricao")) == true) {
                                        unidadeBO.PreencherCamposUnidadeBO(dado);

                                        unidadeBODao.atualizarUnidadeBOWEB(unidadeBO, dado.getString("descricao"));

                                    } else {
                                        unidadeBO.PreencherCamposUnidadeBO(dado);
                                        long resp = unidadeBODao.cadastrarUnidadeBOWEB(unidadeBO);

                                    }
                                }

                            } else {

                                Toast.makeText(sincronizacao.this, "Erro ao Sincronizar!!", Toast.LENGTH_LONG).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.i("Valores Pars", "" + e.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("Valores IO", "" + e.getMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("Valores JOssss", "" + e.getMessage());
                        }
                    }
                });
            } catch (ClientProtocolException e) {
            } catch (Exception e) {

                Log.i("Valores Excep", "" + e.getMessage());
            } finally {

            }

             */

            //inicio do código para trazer os veículos a ser vistoriado
            HttpClient httpClient4 = new DefaultHttpClient();

            try {
                conexao = DatabaseHelper.getInstancia(sincronizacao.this);
                db = conexao.getReadableDatabase();
                HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/vistoria/listarveiculosvistoriar?id=" + idservidor + "&matricula=" + matricula + "?");


                final HttpResponse response = httpClient4.execute(httpGet);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            TvRecebendoVeiculos.setVisibility(View.VISIBLE);

                            String resposta = EntityUtils.toString(response.getEntity());

                            JSONObject dados = new JSONObject(resposta);

                            if (dados.getString("resposta") == "1" || dados.getInt("resposta") == 1) {
                                JSONArray informacao = new JSONArray(dados.getString("informacoes"));

                                int i = 0;

                                for (int j = 0; j < informacao.length(); j++) {

                                    vistoria = new Vistoria();
                                    vistoriaDao = new VistoriaDao(getApplicationContext());

                                    JSONObject dado = informacao.getJSONObject(j);


                                    if (vistoriaDao.verificarVistoria(dado.getString("descricao")) == true) {
                                        vistoria.PreencherCamposVistoria(dado);

                                        vistoriaDao.atualizarVistoriaWeb(vistoria, dado.getString("descricao"));

                                    } else {
                                        vistoria.PreencherCamposVistoria(dado);
                                        long resp = vistoriaDao.cadastrarVistoriaWeb(vistoria);

                                    }

                                }

                            } else {

                                Toast.makeText(sincronizacao.this, "Erro ao Sincronizar!!", Toast.LENGTH_LONG).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.i("Valores Pars", "" + e.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("Valores IO", "" + e.getMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("Valores JOssss", "" + e.getMessage());
                        }
                    }
                });
            } catch (ClientProtocolException e) {
            } catch (Exception e) {

                Log.i("Valores Excep", "" + e.getMessage());
            } finally {

            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TvFinalizandoconexao.setVisibility(View.VISIBLE);
            carregando.setVisibility(ProgressBar.GONE);
            Toast.makeText(sincronizacao.this,"Dispositivo Sincronizado com Sucesso!!!!",Toast.LENGTH_LONG).show();
            finish();

        }
    }
}
