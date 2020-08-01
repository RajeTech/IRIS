package com.iris.ramilton.iris;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.iris.ramilton.iris.Adaptador.CvliAdaptador;
import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.dao.AutoriaDao;
import com.iris.ramilton.iris.dao.CarroDao;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.EquipeLevantamentoDao;
import com.iris.ramilton.iris.dao.EquipePeritoDao;
import com.iris.ramilton.iris.dao.EquipePreservacaoLocalDao;
import com.iris.ramilton.iris.dao.ObjetosApreendidosDao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.dao.VitimaDao;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.Usuario;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class CvliActivity extends AppCompatActivity {

    private ListView ListaItemCvli;
    private Button BtnRegistrarCvli;
    private CvliDao cvliDao;
    private AutoriaDao autoriaDao;
    private CarroDao carroDao;
    private EquipeLevantamentoDao equipeLevantamentoDao;
    private EquipePeritoDao equipePeritoDao;
    private EquipePreservacaoLocalDao equipePreservacaoLocalDao;
    private ObjetosApreendidosDao objetosApreendidosDao;
    private VitimaDao vitimaDao;
    private Cvli cvli;
    private List<Cvli> ListaCvliGeral;
    private List<Cvli> ListaCvliGeralFiltrados = new ArrayList<>();
    private SQLiteDatabase db;
    private DatabaseHelper conexao = null;
    private int controleenvio = 0;
    private Gerarnumeros geranumeros;
    private UsuarioDao usuarioDao;
    private ProgressBar carregando;
    private Cvli codigoaserenviado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvli);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        carregando = findViewById(R.id.carregando);
        ListaItemCvli = (ListView) findViewById(R.id.ListViewCvli);
        BtnRegistrarCvli = (Button) findViewById(R.id.btnRegistrarCVLI);

        usuarioDao = new UsuarioDao(this);
        geranumeros = new Gerarnumeros();
        cvli = new Cvli();
        cvliDao = new CvliDao(this);
        ListaCvliGeral = cvliDao.retornaCVLI();
        ListaCvliGeralFiltrados.addAll(ListaCvliGeral);

        BtnRegistrarCvli.setText("Total de CVLIs "+cvliDao.ContarCvli()+"\n Registrar novo CVLI ");
        CvliAdaptador adaptadorCvli = new CvliAdaptador(this, ListaCvliGeralFiltrados);
        ListaItemCvli.setAdapter(adaptadorCvli);
        registerForContextMenu(ListaItemCvli);

        autoriaDao = new AutoriaDao(this);
        carroDao = new CarroDao(this);
        equipeLevantamentoDao = new EquipeLevantamentoDao(this);
        equipePeritoDao = new EquipePeritoDao(this);
        equipePreservacaoLocalDao = new EquipePreservacaoLocalDao(this);
        objetosApreendidosDao = new ObjetosApreendidosDao(this);
        vitimaDao = new VitimaDao(this);


        BtnRegistrarCvli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvliDao = new CvliDao(getApplicationContext());
                Cvli cvli = new Cvli();
                cvli.setEstatusCVLI(0);
                cvli.setControle(2);

                String coisa = geranumeros.RetornarNumeroTabelaCVLI(getBaseContext());

                int unidadeid = usuarioDao.user.getDsIdUnidade();
                int servidorid = usuarioDao.user.getId();

                cvli.setIdUnico(coisa);
                cvli.setUnidade_id(unidadeid);
                cvli.setServidor_id(servidorid);

                if (!usuarioDao.verificarSessao()) {

                    startActivity(new Intent(CvliActivity.this, LoginActivity.class));
                    finish();
                }

                long codigo = cvliDao.cadastrarCVLI(cvli);
                if (codigo > 0) {
                    Toast.makeText(CvliActivity.this, "Registro realizado com sucesso", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CvliActivity.this, "Erro ao realizar o Registro", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(CvliActivity.this, CadastroCvliActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cvli, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                procuraCVLI(newText);
                return false;
            }
        });

        return true;
    }

    public void procuraCVLI(String municipio) {
        ListaCvliGeralFiltrados.clear();
        for (Cvli a : ListaCvliGeral) {

            if (a.getDsMunicipioFato().toLowerCase().toString().contains(municipio.toLowerCase().toString())) {
                ListaCvliGeralFiltrados.add(a);
            }
        }
        ListaItemCvli.invalidateViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish(); // Finaliza a Activity atual e assim volta para a tela anterior
                break;
            case R.id.action_relatorio:
                startActivity(new Intent(CvliActivity.this, RelatorioCVLIActivity.class));
                break;
            case R.id.action_legenda:
                AlertDialog.Builder b = new AlertDialog.Builder(CvliActivity.this);
                LayoutInflater factory = LayoutInflater.from(CvliActivity.this);
                final View view = factory.inflate(R.layout.legendacvli, null);
                b.setTitle("IRIS - Atenção!!!")
                        .setView(view)
                        .setMessage("Legendas das Cores do Envio do CVLI!!!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                b.show();

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);

    }

    public void Atualizar(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cvli cvliAtualizar = ListaCvliGeralFiltrados.get(menuInfo.position);

        Intent it = new Intent(this, CadastroCvliActivity.class);
        it.putExtra("cvli", cvliAtualizar);
        startActivity(it);

    }

    public void GerarReleasePDF(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cvli releasepdf = ListaCvliGeralFiltrados.get(menuInfo.position);

        if (cvliDao.VerificarEmBrancoCVLI(releasepdf.getId()) != null) {

            AlertDialog.Builder b = new AlertDialog.Builder(CvliActivity.this);
            LayoutInflater factory = LayoutInflater.from(CvliActivity.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Os campos '" + cvliDao.VerificarEmBrancoCVLI(releasepdf.getId()) + "' não podem ficar vazios!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carregando.setVisibility(ProgressBar.GONE);
                        }
                    }).create();
            b.show();

        } else if (vitimaDao.VerificarBracoVitimas(releasepdf.getId()) != null) {

            AlertDialog.Builder b = new AlertDialog.Builder(CvliActivity.this);
            LayoutInflater factory = LayoutInflater.from(CvliActivity.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Os campos '" + vitimaDao.VerificarBracoVitimas(releasepdf.getId()) + "' não podem ficar vazios!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carregando.setVisibility(ProgressBar.GONE);
                        }
                    }).create();
            b.show();
        } else if (autoriaDao.VerificarBrancoAutoria(releasepdf.getId()) != null) {
            AlertDialog.Builder b = new AlertDialog.Builder(CvliActivity.this);
            LayoutInflater factory = LayoutInflater.from(CvliActivity.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Os campos '" + autoriaDao.VerificarBrancoAutoria(releasepdf.getId()) + "' não podem ficar vazios!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carregando.setVisibility(ProgressBar.GONE);
                        }
                    }).create();
            b.show();
        } else {

            Intent it = new Intent(this, RelatorioIndividualCVLIActivity.class);
            it.putExtra("relatorioreliasepdf", releasepdf);
            startActivity(it);
        }

    }

    public void Excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cvli cvliExcluir = ListaCvliGeralFiltrados.get(menuInfo.position);

        AlertDialog.Builder b = new AlertDialog.Builder(CvliActivity.this);
        LayoutInflater factory = LayoutInflater.from(CvliActivity.this);
        final View view = factory.inflate(R.layout.logotipo, null);
        b.setTitle("IRIS - Atenção!!!")
                .setView(view)
                .setMessage("Deseja Realmente Excluir o CVLI?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        autoriaDao.excluirAutoria(cvliExcluir.getId());
                        carroDao.excluirCarro(cvliExcluir.getId());
                        equipeLevantamentoDao.excluirEquipeLevantamento(cvliExcluir.getId());
                        equipePeritoDao.excluirEquipePerito(cvliExcluir.getId());
                        equipePreservacaoLocalDao.excluirEquipepreservacaoLocal(cvliExcluir.getId());
                        objetosApreendidosDao.excluirObjetosApreendidos(cvliExcluir.getId());
                        vitimaDao.excluirVitima(cvliExcluir.getId());
                        ListaCvliGeralFiltrados.remove(cvliExcluir);
                        ListaCvliGeral.remove(cvliExcluir);
                        cvliDao.excluirCVLI(cvliExcluir.getId());
                        ListaItemCvli.invalidateViews();

                    }
                }).create();
        b.show();
    }

    public void InfoRelatorioSemanal(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cvli cvliInfoRelatorioSemanal = ListaCvliGeralFiltrados.get(menuInfo.position);

        Intent it = new Intent(this, AturalizaResFatoActivity.class);
        it.putExtra("codigoresfatoatualizar", cvliInfoRelatorioSemanal);
        startActivity(it);
    }


    public void RelatorioIndividualCVLI(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cvli relatorioIndividualCVLI = ListaCvliGeralFiltrados.get(menuInfo.position);

        if (cvliDao.VerificarEmBrancoCVLI(relatorioIndividualCVLI.getId()) != null) {

            AlertDialog.Builder b = new AlertDialog.Builder(CvliActivity.this);
            LayoutInflater factory = LayoutInflater.from(CvliActivity.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Os campos '" + cvliDao.VerificarEmBrancoCVLI(relatorioIndividualCVLI.getId()) + "' não podem ficar vazios!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carregando.setVisibility(ProgressBar.GONE);
                        }
                    }).create();
            b.show();

        } else if (vitimaDao.VerificarBracoVitimas(relatorioIndividualCVLI.getId()) != null) {

            AlertDialog.Builder b = new AlertDialog.Builder(CvliActivity.this);
            LayoutInflater factory = LayoutInflater.from(CvliActivity.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Os campos '" + vitimaDao.VerificarBracoVitimas(relatorioIndividualCVLI.getId()) + "' não podem ficar vazios!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carregando.setVisibility(ProgressBar.GONE);
                        }
                    }).create();
            b.show();
        } else if (autoriaDao.VerificarBrancoAutoria(relatorioIndividualCVLI.getId()) != null) {
            AlertDialog.Builder b = new AlertDialog.Builder(CvliActivity.this);
            LayoutInflater factory = LayoutInflater.from(CvliActivity.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Os campos '" + autoriaDao.VerificarBrancoAutoria(relatorioIndividualCVLI.getId()) + "' não podem ficar vazios!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carregando.setVisibility(ProgressBar.GONE);
                        }
                    }).create();
            b.show();
        } else {

            Intent it = new Intent(this, RelatorioIndividualCVLIActivity.class);
            it.putExtra("relatorioindividualcvli", relatorioIndividualCVLI);
            startActivity(it);
        }
    }

    //método para verificar acesso a internet
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

    public void Sincronizar(MenuItem item) {
        carregando.setVisibility(ProgressBar.VISIBLE);
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cvli cvliSincronizar = ListaCvliGeralFiltrados.get(menuInfo.position);

        if (isConected(this) == false) {
            AlertDialog.Builder b = new AlertDialog.Builder(CvliActivity.this);
            LayoutInflater factory = LayoutInflater.from(CvliActivity.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Verifique a sua conecxão com a internet!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carregando.setVisibility(ProgressBar.GONE);
                        }
                    }).create();
            b.show();

        } else {
            int numeracao = cvliDao.retornarCodigoControle(cvliSincronizar.getId());

            if (numeracao != 3) {
                new Thread() {
                    public void run() {

                        EnvioDados(cvliSincronizar.getId());

                    }
                }.start();
                controleenvio = 3;
                cvli.setControle(controleenvio);
                cvliDao.AtualizarControleCVLI(cvli, cvliSincronizar.getId(), controleenvio);
            } else {
                Toast.makeText(CvliActivity.this, "Cvli já foi enviado! Escolha outro para enviar. ", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String EnvioDadosAutorias(int id) {
        Cursor cursorAutorias = db.query("cvliautorias", new String[]{"id", "fkCvli", "cbkAutoriaDefinida", "cbkAutoriaNDefinida", "cbkAutoriaSuspeita",
                "dsNomeAutoria", "dsRGAutoria", "dsOrgaoExpRGAutoria", "dsSexoAutoria", "dsCPFAutoria", "dsIdadeAutoria", "dsCurtisAutoria", "dsNomeMaeAutoria",
                "dsNomePaiAutoria", "dsNascionalidadeAutoria", "dsNaturalidadeAutoria", "dsCondicaoAutoria", "dtPrisaoAutoria", "dsLocalPrisaoAutoria",
                "hsHorarioPrisaoAutoria", "dsNaturezaPrisaoAutoria", "dsResponsavelPrisaoAutoria", "Controle", "idUnico", "dsVulgoAutoria"}, "fkCvli= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorAutorias.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

            try {
                jsonlocation.put("id", cursorAutorias.getInt(0));
                jsonlocation.put("fkCvli", cursorAutorias.getInt(1));
                jsonlocation.put("cbkAutoriaDefinida", cursorAutorias.getInt(2));
                jsonlocation.put("cbkAutoriaNDefinida", cursorAutorias.getInt(3));
                jsonlocation.put("cbkAutoriaSuspeita", cursorAutorias.getString(4));
                jsonlocation.put("dsNomeAutoria", cursorAutorias.getString(5));
                jsonlocation.put("dsRGAutoria", cursorAutorias.getString(6));
                jsonlocation.put("dsOrgaoExpRGAutoria", cursorAutorias.getString(7));

                if(cursorAutorias.getString(8) == "Masculino"){
                    jsonlocation.put("dsSexoAutoria", "M");
                }else if(cursorAutorias.getString(8) == "Feminino"){
                    jsonlocation.put("dsSexoAutoria", "F");
                }
                //jsonlocation.put("dsSexoAutoria", cursorAutorias.getString(8));
                jsonlocation.put("dsCPFAutoria", cursorAutorias.getInt(9));
                jsonlocation.put("dsIdadeAutoria", cursorAutorias.getString(10));
                jsonlocation.put("dsCurtisAutoria", cursorAutorias.getString(11));
                jsonlocation.put("dsNomeMaeAutoria", cursorAutorias.getString(12));
                jsonlocation.put("dsNomePaiAutoria", cursorAutorias.getString(13));
                jsonlocation.put("dsNascionalidadeAutoria", cursorAutorias.getString(14));
                jsonlocation.put("dsNaturalidadeAutoria", cursorAutorias.getString(15));
                jsonlocation.put("dsCondicaoAutoria", cursorAutorias.getString(16));
                jsonlocation.put("dtPrisaoAutoria", cursorAutorias.getString(17));
                jsonlocation.put("dsLocalPrisaoAutoria", cursorAutorias.getString(18));
                jsonlocation.put("hsHorarioPrisaoAutoria", cursorAutorias.getString(19));
                jsonlocation.put("dsNaturezaPrisaoAutoria", cursorAutorias.getInt(20));
                jsonlocation.put("dsResponsavelPrisaoAutoria", cursorAutorias.getInt(21));
                jsonlocation.put("Controle", cursorAutorias.getInt(22));
                jsonlocation.put("idUnico", cursorAutorias.getString(23));
                jsonlocation.put("dsVulgoAutoria", cursorAutorias.getString(24));

                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();

    }

    public String EnvioDadosVitimas(int id) {
        Cursor cursorVitimas = db.query("cvlivitimas", new String[]{"id", "fkCvli", "ckbIdentificadaVitima", "cbkNaoIdentificadaVitima",
                "dsNomeVitima", "dsRGVitima", "dsOrgaoExpRGVitima", "dsSexoVitima", "dsCPFVitima", "dsNomeMaeVitima",
                "dsNomePaiVitima", "dsNascionalidadeVitima", "dsNaturalidadeVitima", "dsOrientacaoSexualVitima", "dsProfissaoVitima",
                "dsCondicaoSaudeVitima", "Controle", "idUnico", "dsEstadoVitima", "dsCondicaoVitima", "dsDtNascimentoVitima"}, "fkCvli= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorVitimas.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

            try {
                jsonlocation.put("id", cursorVitimas.getInt(0));
                jsonlocation.put("fkCvli", cursorVitimas.getInt(1));
                jsonlocation.put("ckbIdentificadaVitima", cursorVitimas.getInt(2));
                jsonlocation.put("cbkNaoIdentificadaVitima", cursorVitimas.getInt(3));
                jsonlocation.put("dsNomeVitima", cursorVitimas.getString(4));
                jsonlocation.put("dsRGVitima", cursorVitimas.getString(5));
                jsonlocation.put("dsOrgaoExpRGVitima", cursorVitimas.getString(6));

                if(cursorVitimas.getString(7) == "Masculino"){
                    jsonlocation.put("dsSexoVitima", "M");
                }else if(cursorVitimas.getString(7) == "Feminino"){
                    jsonlocation.put("dsSexoVitima", "F");
                }
                jsonlocation.put("dsCPFVitima", cursorVitimas.getString(8));
                jsonlocation.put("dsNomeMaeVitima", cursorVitimas.getString(9));
                jsonlocation.put("dsNomePaiVitima", cursorVitimas.getString(10));
                jsonlocation.put("dsNascionalidadeVitima", cursorVitimas.getString(11));
                jsonlocation.put("dsNaturalidadeVitima", cursorVitimas.getString(12));

                if(cursorVitimas.getString(13) == "Heterossexual") {
                    jsonlocation.put("dsOrientacaoSexualVitima", "HE");
                }
                if(cursorVitimas.getString(13) == "Homossexual"){
                    jsonlocation.put("dsOrientacaoSexualVitima", "HO");
                }
                if(cursorVitimas.getString(13) == "Bissexual"){
                    jsonlocation.put("dsOrientacaoSexualVitima", "BI");
                }
                if(cursorVitimas.getString(13) == "Transsexual"){
                    jsonlocation.put("dsOrientacaoSexualVitima", "TR");
                }
                if(cursorVitimas.getString(13) == "Travesti"){
                    jsonlocation.put("dsOrientacaoSexualVitima", "TRA");
                }
                if(cursorVitimas.getString(13) == "Outros"){
                    jsonlocation.put("dsOrientacaoSexualVitima", "OU");
                }
                if(cursorVitimas.getString(13) == "Não Informado"){
                    jsonlocation.put("dsOrientacaoSexualVitima", "NA");
                }

                jsonlocation.put("dsProfissaoVitima", cursorVitimas.getString(14));
                jsonlocation.put("dsCondicaoSaudeVitima", cursorVitimas.getString(15));
                jsonlocation.put("Controle", cursorVitimas.getInt(16));
                jsonlocation.put("idUnico", cursorVitimas.getString(17));
                jsonlocation.put("dsEstadoVitima", cursorVitimas.getString(18));
                jsonlocation.put("dsCondicaoVitima", cursorVitimas.getString(19));
                jsonlocation.put("dsDtNascimentoVitima", cursorVitimas.getString(20));
                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();

    }

    public String EnvioDadosGuiasPericiais(int id){
        Cursor cursorGuiasPericiais = db.query("cvliguiaspericiais", new String[]{"id", "fkCvli", "dsGuiaPericial", "dsNGuiaPericial",
                "Controle", "idUnico"}, "fkCvli= '"+id+"'", null, null, null, null);

        JSONArray josonArray = new JSONArray();

        while(cursorGuiasPericiais.moveToNext()){

            JSONObject jsonlocation = new JSONObject();

            try{
                jsonlocation.put("id", cursorGuiasPericiais.getInt(0));
                jsonlocation.put("fkCvli", cursorGuiasPericiais.getInt(1));
                jsonlocation.put("dsGuiaPericial", cursorGuiasPericiais.getString(2));
                jsonlocation.put("dsNGuiaPericial", cursorGuiasPericiais.getString(3));
                jsonlocation.put("Controle", cursorGuiasPericiais.getInt(4));
                jsonlocation.put("idUnico", cursorGuiasPericiais.getString(5));
                josonArray.put(jsonlocation);
            }catch(Exception e){
            }
        }
        return josonArray.toString();
    }

    public String EnvioDadosEquiPeritos(int id) {
        Cursor cursorEquiPeritos = db.query("cvliequipeperitos", new String[]{"id", "fkCvli", "dsCargoEquipePerito", "dsNomeEquipePerito",
                "Controle", "idUnico"}, "fkCvli= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorEquiPeritos.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

            try {
                jsonlocation.put("id", cursorEquiPeritos.getInt(0));
                jsonlocation.put("fkCvli", cursorEquiPeritos.getInt(1));
                jsonlocation.put("dsCargoEquipePerito", cursorEquiPeritos.getString(2));
                jsonlocation.put("dsNomeEquipePerito", cursorEquiPeritos.getString(3));
                jsonlocation.put("Controle", cursorEquiPeritos.getInt(4));
                jsonlocation.put("idUnico", cursorEquiPeritos.getString(5));
                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();

    }

    public String EnvioDadosEquiLevantamento(int id) {
        Cursor cursorEquiLevantamento = db.query("cvliequipelevantamentos", new String[]{"id", "fkCvli", "dsCargoEquipeLevantamento", "dsNomeEquipeLevantamento",
                "Controle", "idUnico"}, "fkCvli= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorEquiLevantamento.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

            try {
                jsonlocation.put("id", cursorEquiLevantamento.getInt(0));
                jsonlocation.put("fkCvli", cursorEquiLevantamento.getInt(1));
                jsonlocation.put("dsCargoEquipeLevantamento", cursorEquiLevantamento.getString(2));
                jsonlocation.put("dsNomeEquipeLevantamento", cursorEquiLevantamento.getString(3));
                jsonlocation.put("Controle", cursorEquiLevantamento.getInt(4));
                jsonlocation.put("idUnico", cursorEquiLevantamento.getString(5));
                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();

    }

    public String EnvioDadosEquiPresLocal(int id) {
        Cursor cursorEquiPresLocal = db.query("cvliequipepreservacaolocals", new String[]{"id", "fkCvli", "dsCargoEquipepreservacaolocal", "dsNomeEquipepreservacaolocal",
                "Controle", "idUnico"}, "fkCvli= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorEquiPresLocal.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

            try {
                jsonlocation.put("id", cursorEquiPresLocal.getInt(0));
                jsonlocation.put("fkCvli", cursorEquiPresLocal.getInt(1));
                jsonlocation.put("dsCargoEquipepreservacaolocal", cursorEquiPresLocal.getString(2));
                jsonlocation.put("dsNomeEquipepreservacaolocal", cursorEquiPresLocal.getString(3));
                jsonlocation.put("Controle", cursorEquiPresLocal.getInt(4));
                jsonlocation.put("idUnico", cursorEquiPresLocal.getString(5));
                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();

    }

    public String EnvioDadosCarros(int id) {
        Cursor cursorCarros = db.query("cvlicarros", new String[]{"id", "fkCvli", "dsTipoCarro", "dsMarcaCarro",
                "dsModeloCarro", "dsDescricaoCarro", "dsCorCarro", "dsPlacaCarro", "ckbNIdentificadoMarcaCarroCrime", "ckbNIdentificadoModeloCarroCrime",
                "ckbNIdentificadoCorCarroCrime", "ckbNidentificadoPlacaCarroCrime", "dsDescricaoBiscicleta", "Controle", "idUnico"}, "fkCvli= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorCarros.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

            try {
                jsonlocation.put("id", cursorCarros.getInt(0));
                jsonlocation.put("fkCvli", cursorCarros.getInt(1));
                jsonlocation.put("dsTipoCarro", cursorCarros.getString(2));
                jsonlocation.put("dsMarcaCarro", cursorCarros.getString(3));
                jsonlocation.put("dsModeloCarro", cursorCarros.getString(4));
                jsonlocation.put("dsDescricaoCarro", cursorCarros.getString(5));
                jsonlocation.put("dsCorCarro", cursorCarros.getString(6));
                jsonlocation.put("dsPlacaCarro", cursorCarros.getString(7));
                jsonlocation.put("ckbNIdentificadoMarcaCarroCrime", cursorCarros.getInt(8));
                jsonlocation.put("ckbNIdentificadoModeloCarroCrime", cursorCarros.getInt(9));
                jsonlocation.put("ckbNIdentificadoCorCarroCrime", cursorCarros.getInt(10));
                jsonlocation.put("ckbNidentificadoPlacaCarroCrime", cursorCarros.getInt(11));
                jsonlocation.put("dsDescricaoBiscicleta", cursorCarros.getString(12));
                jsonlocation.put("Controle", cursorCarros.getInt(13));
                jsonlocation.put("idUnico", cursorCarros.getString(14));
                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();

    }

    public String EnvioDadosObjetos(int id) {
        Cursor cursorObjetosApreendidos = db.query("cvliobjetosapreendidos", new String[]{"id", "fkCvli", "dsTipoObjetoApreendido", "dsDescricaoObjetoApreendido",
                "Controle", "idUnico"}, "fkCvli= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorObjetosApreendidos.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

            try {
                jsonlocation.put("id", cursorObjetosApreendidos.getInt(0));
                jsonlocation.put("fkCvli", cursorObjetosApreendidos.getInt(1));
                jsonlocation.put("dsTipoObjetoApreendido", cursorObjetosApreendidos.getString(2));
                jsonlocation.put("dsDescricaoObjetoApreendido", cursorObjetosApreendidos.getString(3));
                jsonlocation.put("Controle", cursorObjetosApreendidos.getInt(4));
                jsonlocation.put("idUnico", cursorObjetosApreendidos.getString(5));
                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();

    }

    private void EnvioDados(int id) {

        HttpClient httpClient = new DefaultHttpClient();

        try {
            conexao = DatabaseHelper.getInstancia(CvliActivity.this);
            db = conexao.getReadableDatabase();

            Cursor cursor = db.query("cvlis", new String[]{"id", "dsNaturezaFato", "dtFato", "ckbDataFatoIndefinido",
                    "hsFato", "ckbHorarioFatoIndefinido", "rbEnderecoFatoDefinido", "rbEnderecoFatoNIndefinido", "dsLogradouroFato", "dsRuaFato", "dsNRuaFato",
                    "dsReferenciaLocalFato", "dsBairroFato", "dsDistVilPovoFato", "dsDescrDistVilPovoFato", "dsMunicipioFato", "dsEstadoFato",
                    "dsZonaFato", "dsDetalhamentoLocal", "dsOutroDetalhamento", "cbkAfogamentoFato", "cbkAsfixiaFato", "cbkTropelamentoFato",
                    "cbkDisparoArmaFato", "cbkEmpurraoAlturaFato", "cbkEvenenadoFato", "cbkEstanaduraFato",
                    "cbkEstrangulamentoFato", "cbkEmpurraoSobVeiculoFato", "cbkGolpeFacaoFato", "cbkGolpeFacaFato", "cbkPauladaFato",
                    "cbkOmissaoSocorroFato", "cbkQueimaduraFato", "cbkPedradaFato", "cbkPerfuracoesFato", "cbkIncendioFato", "cbkSocosPontapesFato",
                    "cbkQueimarudasAcidoFato", "cbkNaoIdentificadoFato", "cbkOutrosNaoRelacionadoFato", "dsMotivacaoFato",
                    "dtDataSilc", "hsHorarioSilc", "dsLogradouroSilc", "dsRuaSilc", "dsNRuaSilc", "dsReferenciaLocalSilc", "dsBairroSilc",
                    "dsDistVilPovoSilc", "dsDescrDistVilPovoSilc", "dsMunicipioSilc", "dsEstadoSilc", "dsResumoFato", "dsNBO",
                    "rbExpedidoGuiaPericial", "rbNExpedidoGuiaPericial", "dsNIP", "cbkBuscaApreensao", "cbkPrisaoTemporaria", "cbkPrisaoPreventiva",
                    "cbkQuebraSigilo", "cbMedidaProtetivadeUrgencia", "cbSemCautelares", "dsDestinacaoInvestigacao", "EstatusCVLI", "idUnico", "unidade_id", "servidor_id",
                    "dsTituloResumo", "dsProvidencia", "rbEquipePreservacaoLocalDefinido", "rbEquipePreservacaoLocalSilcNDefinido", "rbEquipeLevantamentoSilcDefinido",
                    "rbEquipeLevantamentoSilcNDefinido", "rbEquipePeritosSilcDefinido", "rbEquipePeritosSilcNDefinido", "rbObjetosArrecadadosSilcDefinido", "rbObjetosArrecadadosSilcNDefinido",
                    "validarcvli", "dsResFato", "cbkTortura"}, "id= '" + id + "'", null, null, null, null);


            while (cursor.moveToNext()) {

                ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
                itens.add(new BasicNameValuePair("id", "" + cursor.getInt(0)));
                itens.add(new BasicNameValuePair("dsNaturezaFato", cursor.getString(1)));
                itens.add(new BasicNameValuePair("dtFato", cursor.getString(2)));
                itens.add(new BasicNameValuePair("ckbDataFatoIndefinido", "" + cursor.getInt(3)));
                itens.add(new BasicNameValuePair("hsFato", cursor.getString(4)));
                itens.add(new BasicNameValuePair("ckbHorarioFatoIndefinido", "" + cursor.getInt(5)));
                itens.add(new BasicNameValuePair("rbEnderecoFatoDefinido", "" + cursor.getInt(6)));
                itens.add(new BasicNameValuePair("rbEnderecoFatoNIndefinido", "" + cursor.getInt(7)));
                itens.add(new BasicNameValuePair("dsLogradouroFato", cursor.getString(8)));
                itens.add(new BasicNameValuePair("dsRuaFato", cursor.getString(9)));
                itens.add(new BasicNameValuePair("dsNRuaFato", cursor.getString(10)));
                itens.add(new BasicNameValuePair("dsReferenciaLocalFato", cursor.getString(11)));
                itens.add(new BasicNameValuePair("dsBairroFato", cursor.getString(12)));
                itens.add(new BasicNameValuePair("dsDistVilPovoFato", cursor.getString(13)));
                itens.add(new BasicNameValuePair("dsDescrDistVilPovoFato", cursor.getString(14)));
                itens.add(new BasicNameValuePair("dsMunicipioFato", cursor.getString(15)));
                itens.add(new BasicNameValuePair("dsEstadoFato", cursor.getString(16)));
                itens.add(new BasicNameValuePair("dsZonaFato", cursor.getString(17)));
                itens.add(new BasicNameValuePair("dsDetalhamentoLocal", cursor.getString(18)));
                itens.add(new BasicNameValuePair("dsOutroDetalhamento", cursor.getString(19)));
                itens.add(new BasicNameValuePair("cbkAfogamentoFato", "" + cursor.getInt(20)));
                itens.add(new BasicNameValuePair("cbkAsfixiaFato", "" + cursor.getInt(21)));
                itens.add(new BasicNameValuePair("cbkTropelamentoFato", "" + cursor.getInt(22)));
                itens.add(new BasicNameValuePair("cbkDisparoArmaFato", "" + cursor.getInt(23)));
                itens.add(new BasicNameValuePair("cbkEmpurraoAlturaFato", "" + cursor.getInt(24)));
                itens.add(new BasicNameValuePair("cbkEvenenadoFato", "" + cursor.getInt(25)));
                itens.add(new BasicNameValuePair("cbkEstanaduraFato", "" + cursor.getInt(26)));
                itens.add(new BasicNameValuePair("cbkEstrangulamentoFato", "" + cursor.getInt(27)));
                itens.add(new BasicNameValuePair("cbkEmpurraoSobVeiculoFato", "" + cursor.getInt(28)));
                itens.add(new BasicNameValuePair("cbkGolpeFacaoFato", "" + cursor.getInt(29)));
                itens.add(new BasicNameValuePair("cbkGolpeFacaFato", "" + cursor.getInt(30)));
                itens.add(new BasicNameValuePair("cbkPauladaFato", "" + cursor.getInt(31)));
                itens.add(new BasicNameValuePair("cbkOmissaoSocorroFato", "" + cursor.getInt(32)));
                itens.add(new BasicNameValuePair("cbkQueimaduraFato", "" + cursor.getInt(33)));
                itens.add(new BasicNameValuePair("cbkPedradaFato", "" + cursor.getInt(34)));
                itens.add(new BasicNameValuePair("cbkPerfuracoesFato", "" + cursor.getInt(35)));
                itens.add(new BasicNameValuePair("cbkIncendioFato", "" + cursor.getInt(36)));
                itens.add(new BasicNameValuePair("cbkSocosPontapesFato", "" + cursor.getInt(37)));
                itens.add(new BasicNameValuePair("cbkQueimarudasAcidoFato", "" + cursor.getInt(38)));
                itens.add(new BasicNameValuePair("cbkNaoIdentificadoFato", "" + cursor.getInt(39)));
                itens.add(new BasicNameValuePair("cbkOutrosNaoRelacionadoFato", "" + cursor.getInt(40)));
                itens.add(new BasicNameValuePair("dsMotivacaoFato", cursor.getString(41)));
                itens.add(new BasicNameValuePair("dtDataSilc", cursor.getString(42)));
                itens.add(new BasicNameValuePair("hsHorarioSilc", cursor.getString(43)));
                itens.add(new BasicNameValuePair("dsLogradouroSilc", cursor.getString(44)));
                itens.add(new BasicNameValuePair("dsRuaSilc", cursor.getString(45)));
                itens.add(new BasicNameValuePair("dsNRuaSilc", cursor.getString(46)));
                itens.add(new BasicNameValuePair("dsReferenciaLocalSilc", cursor.getString(47)));
                itens.add(new BasicNameValuePair("dsBairroSilc", cursor.getString(48)));
                itens.add(new BasicNameValuePair("dsDistVilPovoSilc", cursor.getString(49)));
                itens.add(new BasicNameValuePair("dsDescrDistVilPovoSilc", cursor.getString(50)));
                itens.add(new BasicNameValuePair("dsMunicipioSilc", cursor.getString(51)));
                itens.add(new BasicNameValuePair("dsEstadoSilc", cursor.getString(52)));
                itens.add(new BasicNameValuePair("dsResumoFato", cursor.getString(53)));
                itens.add(new BasicNameValuePair("dsNBO", cursor.getString(54)));
                itens.add(new BasicNameValuePair("rbExpedidoGuiaPericial", ""+ cursor.getInt(55)));
                itens.add(new BasicNameValuePair("rbNExpedidoGuiaPericial", ""+ cursor.getInt(56)));
                itens.add(new BasicNameValuePair("dsNIP", cursor.getString(57)));
                itens.add(new BasicNameValuePair("cbkBuscaApreensao", "" + cursor.getInt(58)));
                itens.add(new BasicNameValuePair("cbkPrisaoTemporaria", "" + cursor.getInt(59)));
                itens.add(new BasicNameValuePair("cbkPrisaoPreventiva", "" + cursor.getInt(60)));
                itens.add(new BasicNameValuePair("cbkQuebraSigilo", "" + cursor.getInt(61)));
                itens.add(new BasicNameValuePair("cbMedidaProtetivadeUrgencia", "" + cursor.getInt(62)));
                itens.add(new BasicNameValuePair("cbSemCautelares",""+ cursor.getInt(63)));
                itens.add(new BasicNameValuePair("dsDestinacaoInvestigacao", cursor.getString(64)));
                itens.add(new BasicNameValuePair("EstatusCVLI", "" + cursor.getInt(65)));
                itens.add(new BasicNameValuePair("idUnico", cursor.getString(66)));
                itens.add(new BasicNameValuePair("unidade_id", cursor.getString(67)));
                itens.add(new BasicNameValuePair("servidor_id", cursor.getString(68)));
                itens.add(new BasicNameValuePair("dsTituloResumo", cursor.getString(69)));
                itens.add(new BasicNameValuePair("dsProvidencia", cursor.getString(70)));
                itens.add(new BasicNameValuePair("rbEquipePreservacaoLocalDefinido", "" + cursor.getInt(71)));
                itens.add(new BasicNameValuePair("rbEquipePreservacaoLocalSilcNDefinido", "" + cursor.getInt(72)));
                itens.add(new BasicNameValuePair("rbEquipeLevantamentoSilcDefinido", "" + cursor.getInt(73)));
                itens.add(new BasicNameValuePair("rbEquipeLevantamentoSilcNDefinido", "" + cursor.getInt(74)));
                itens.add(new BasicNameValuePair("rbEquipePeritosSilcDefinido", "" + cursor.getInt(75)));
                itens.add(new BasicNameValuePair("rbEquipePeritosSilcNDefinido", "" + cursor.getInt(76)));
                itens.add(new BasicNameValuePair("rbObjetosArrecadadosSilcDefinido", "" + cursor.getInt(77)));
                itens.add(new BasicNameValuePair("rbObjetosArrecadadosSilcNDefinido", "" + cursor.getInt(78)));
                itens.add(new BasicNameValuePair("validarcvli", "" + cursor.getInt(79)));
                itens.add(new BasicNameValuePair("dsResFato", "" + cursor.getString(80)));
                itens.add(new BasicNameValuePair("cbkTortura", ""+cursor.getInt(81)));
                itens.add(new BasicNameValuePair("CVLIObjetosApreendidos", EnvioDadosObjetos(id)));
                itens.add(new BasicNameValuePair("carros", EnvioDadosCarros(id)));
                itens.add(new BasicNameValuePair("equipePreLocais", EnvioDadosEquiPresLocal(id)));
                itens.add(new BasicNameValuePair("equipePreLevant", EnvioDadosEquiLevantamento(id)));
                itens.add(new BasicNameValuePair("equipeEquipPeri", EnvioDadosEquiPeritos(id)));
                itens.add(new BasicNameValuePair("vitimas", EnvioDadosVitimas(id)));
                itens.add(new BasicNameValuePair("autorias", EnvioDadosAutorias(id)));
                itens.add(new BasicNameValuePair("guias", EnvioDadosGuiasPericiais(id)));


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

                                Toast.makeText(CvliActivity.this, "Sincronizado com Sucesso!!", Toast.LENGTH_LONG).show();
                            } else {

                                Toast.makeText(CvliActivity.this, "Erro ao Sincronizar!!", Toast.LENGTH_LONG).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        } finally {

                            carregando.setVisibility(ProgressBar.GONE);
                        }
                    }
                });

            }

        } catch (ClientProtocolException e) {
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ListaCvliGeral = cvliDao.retornaCVLI();
        ListaCvliGeralFiltrados.clear();
        ListaCvliGeralFiltrados.addAll(ListaCvliGeral);
        ListaItemCvli.invalidateViews();
    }

}


