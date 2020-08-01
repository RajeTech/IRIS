package com.iris.ramilton.iris;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.iris.ramilton.iris.Adaptador.AcaoPolicialAdaptador;
import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.dao.AcaoPolicialDao;
import com.iris.ramilton.iris.dao.ConduzidoacaopolicialDao;
import com.iris.ramilton.iris.dao.ObjapreendidoarmaacaopolicialDao;
import com.iris.ramilton.iris.dao.ObjapreendidodrogaacaopolicialDao;
import com.iris.ramilton.iris.dao.ObjapreendidoveiculoacaopolicialDao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.modelo.Acaopolicial;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Gerarnumeros;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AcaoPolicialActivity extends AppCompatActivity {

    private ListView LvAcaoPolicial;
    private Button BtnRegistrarAcaoPolicial;
    private AcaoPolicialDao acaoPolicialDao;
    private Gerarnumeros geranumeros;
    private UsuarioDao usuarioDao;
    private int controleenvio = 0;
    private List<Acaopolicial> ListaAcaoPolicialGeral;
    private List<Acaopolicial> ListaAcaoPolicialGeralFiltrados = new ArrayList<>();
    private Acaopolicial acaopolicial;
    private Acaopolicial codigoaserenviado = null;
    private ConduzidoacaopolicialDao conduzidoacaopolicialDao;
    private ObjapreendidoarmaacaopolicialDao objapreendidoarmaacaopolicialDao;
    private ObjapreendidodrogaacaopolicialDao objapreendidodrogaacaopolicialDao;
    private ObjapreendidoveiculoacaopolicialDao objapreendidoveiculoacaopolicialDao;
    private SQLiteDatabase db;
    private DatabaseHelper conexao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acao_policial);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        LvAcaoPolicial = (ListView) findViewById(R.id.lvAcaoPolicial);
        BtnRegistrarAcaoPolicial = (Button) findViewById(R.id.btnRegistrarAcaoPolicial);

        usuarioDao = new UsuarioDao(this);
        geranumeros = new Gerarnumeros();
        acaopolicial = new Acaopolicial();
        acaoPolicialDao = new AcaoPolicialDao(this);
        conduzidoacaopolicialDao = new ConduzidoacaopolicialDao(this);
        objapreendidoarmaacaopolicialDao = new ObjapreendidoarmaacaopolicialDao(this);
        objapreendidodrogaacaopolicialDao = new ObjapreendidodrogaacaopolicialDao(this);
        objapreendidoveiculoacaopolicialDao = new ObjapreendidoveiculoacaopolicialDao(this);
        ListaAcaoPolicialGeral = acaoPolicialDao.retornaAcaoPolicial();
        ListaAcaoPolicialGeralFiltrados.addAll(ListaAcaoPolicialGeral);

        AcaoPolicialAdaptador acaoPolicialAdaptador = new AcaoPolicialAdaptador(this,ListaAcaoPolicialGeralFiltrados);
        LvAcaoPolicial.setAdapter(acaoPolicialAdaptador);
        registerForContextMenu(LvAcaoPolicial);

        BtnRegistrarAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acaoPolicialDao = new AcaoPolicialDao(getApplicationContext());
                Acaopolicial acaopolicial = new Acaopolicial();
                acaopolicial.setEstatusAcaoPolicial(0);
                acaopolicial.setControle(2);

                String coisa = geranumeros.RetornarNumeroTabelaCVLI(getBaseContext());

                int unidadeid = usuarioDao.user.getDsIdUnidade();
                int servidorid = usuarioDao.user.getId();

                acaopolicial.setIdUnico(coisa);
                acaopolicial.setUnidade_id(unidadeid);
                acaopolicial.setServidor_id(servidorid);

                if (!usuarioDao.verificarSessao()) {

                    startActivity(new Intent(AcaoPolicialActivity.this, LoginActivity.class));
                    finish();
                }

                long codigo = acaoPolicialDao.cadastrarAcaoPolicial(acaopolicial);
                if (codigo > 0) {
                    Toast.makeText(AcaoPolicialActivity.this, "Registro realizado com sucesso", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AcaoPolicialActivity.this, "Erro ao realizar o Registro", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(AcaoPolicialActivity.this, CadastroAcaoPolicialActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acao_policial, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                procuraAcaoPolicial(newText);
                return false;
            }
        });

        return true;
    }

    public void procuraAcaoPolicial(String NomeOperacao) {
        ListaAcaoPolicialGeralFiltrados.clear();
        for (Acaopolicial a : ListaAcaoPolicialGeral) {

            if (a.getDsNomeOperacaoPolicial().toLowerCase().toString().contains(NomeOperacao.toLowerCase().toString())) {
                ListaAcaoPolicialGeralFiltrados.add(a);
            }
        }
        LvAcaoPolicial.invalidateViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish(); // Finaliza a Activity atual e assim volta para a tela anterior
                break;
            case R.id.action_legenda:
                AlertDialog.Builder b = new AlertDialog.Builder(AcaoPolicialActivity.this);
                LayoutInflater factory = LayoutInflater.from(AcaoPolicialActivity.this);
                final View view = factory.inflate(R.layout.legendaacaopolicial, null);
                b.setTitle("IRIS - Atenção!!!")
                        .setView(view)
                        .setMessage("Legendas das Cores do Envio da Ação Policial!!!")
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
        i.inflate(R.menu.menu_contexto_acaopolicial, menu);

    }

    public void AtualizarAcaoPolicial(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Acaopolicial acaopolicialAtualizar = ListaAcaoPolicialGeralFiltrados.get(menuInfo.position);

        Intent it = new Intent(this, CadastroAcaoPolicialActivity.class);
        it.putExtra("acaopolicial",acaopolicialAtualizar);
        startActivity(it);

    }

    public void RelatorioIndividualAcaoPolicialPDF(MenuItem item){

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Acaopolicial relatoriopdf = ListaAcaoPolicialGeralFiltrados.get(menuInfo.position);

        if (acaoPolicialDao.VerificarEmBrancoAcaoPolicial(relatoriopdf.getId()) != null) {

            AlertDialog.Builder b = new AlertDialog.Builder(AcaoPolicialActivity.this);
            LayoutInflater factory = LayoutInflater.from(AcaoPolicialActivity.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Os campos '" + acaoPolicialDao.VerificarEmBrancoAcaoPolicial(relatoriopdf.getId()) + "' não podem ficar vazios!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create();
            b.show();

        }else {

            Intent it = new Intent(this, RelatorioAcaoPolicialPDF.class);
            it.putExtra("relatorioindividualacaopolicialpdf", relatoriopdf);
            startActivity(it);
        }

    }

    public void ExcluirAcaoPolicial(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Acaopolicial acaopolicialExcluir = ListaAcaoPolicialGeralFiltrados.get(menuInfo.position);

        AlertDialog.Builder b = new AlertDialog.Builder(AcaoPolicialActivity.this);
        LayoutInflater factory = LayoutInflater.from(AcaoPolicialActivity.this);
        final View view = factory.inflate(R.layout.logotipo, null);
        b.setTitle("IRIS - Atenção!!!")
                .setView(view)
                .setMessage("Deseja Realmente Excluir a Ação Policial?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        objapreendidoarmaacaopolicialDao.excluirArmaAcaoPolicial(acaopolicialExcluir.getId());
                        objapreendidodrogaacaopolicialDao.excluirDrogaAcaoPolicial(acaopolicialExcluir.getId());
                        objapreendidoveiculoacaopolicialDao.excluirVeiculosAcaoPolicial(acaopolicialExcluir.getId());
                        conduzidoacaopolicialDao.excluirConduzidoAcaoPolicial(acaopolicialExcluir.getId());
                        ListaAcaoPolicialGeralFiltrados.remove(acaopolicialExcluir);
                        ListaAcaoPolicialGeral.remove(acaopolicialExcluir);
                        acaoPolicialDao.excluirAcaoPolicial(acaopolicialExcluir.getId());
                        LvAcaoPolicial.invalidateViews();

                    }
                }).create();
        b.show();
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

    public void RelatorioIndividualAcaoPolicial(MenuItem item){

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Acaopolicial relatorioIndividualAcaoPolicial = ListaAcaoPolicialGeralFiltrados.get(menuInfo.position);

        if (acaoPolicialDao.VerificarEmBrancoAcaoPolicial(relatorioIndividualAcaoPolicial.getId()) != null) {

            AlertDialog.Builder b = new AlertDialog.Builder(AcaoPolicialActivity.this);
            LayoutInflater factory = LayoutInflater.from(AcaoPolicialActivity.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Os campos '" + acaoPolicialDao.VerificarEmBrancoAcaoPolicial(relatorioIndividualAcaoPolicial.getId()) + "' não podem ficar vazios!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
            b.show();

        } else if (conduzidoacaopolicialDao.VerificarEmBrancoConduzido(relatorioIndividualAcaoPolicial.getId()) != null) {

            AlertDialog.Builder b = new AlertDialog.Builder(AcaoPolicialActivity.this);
            LayoutInflater factory = LayoutInflater.from(AcaoPolicialActivity.this);
            final View view = factory.inflate(R.layout.logotipo, null);
            b.setTitle("IRIS - Atenção!!!")
                    .setView(view)
                    .setMessage("Os campos '" + conduzidoacaopolicialDao.VerificarEmBrancoConduzido(relatorioIndividualAcaoPolicial.getId()) + "' não podem ficar vazios!!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
            b.show();
        }else {

            Intent it = new Intent(this, RelatorioIndividualAcaoPolicialactivity.class);
            it.putExtra("relatorioindividualacaopolicial", relatorioIndividualAcaoPolicial);
            startActivity(it);
        }
    }

    public void SincronizarAcaoPolicial(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Acaopolicial acaopolicialSincronizar = ListaAcaoPolicialGeralFiltrados.get(menuInfo.position);

        if (isConected(this) == false) {
            AlertDialog.Builder b = new AlertDialog.Builder(AcaoPolicialActivity.this);
            LayoutInflater factory = LayoutInflater.from(AcaoPolicialActivity.this);
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
            int numeracao = acaoPolicialDao.retornarCodigoControle(acaopolicialSincronizar.getId());

            if (numeracao != 3) {
                new Thread() {
                    public void run() {

                        EnvioDadosAcaoPolicial(acaopolicialSincronizar.getId());

                    }
                }.start();
                controleenvio = 3;
                acaopolicial.setControle(controleenvio);
                acaoPolicialDao.AtualizarControleAcaoPolicial(acaopolicial, acaopolicialSincronizar.getId(), controleenvio);
            } else {
                Toast.makeText(AcaoPolicialActivity.this, "Ação Policial já foi enviado! Escolha outro para enviar. ", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String EnvioDadosConduzidos(int id) {

        Cursor cursorConduzidos = db.query("ConduzidoAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsNomeConduzidoAcaoPolicial", "IdadeConduzidoAcaoPolicial",
                "dsTipoProcedimentoAcaoPolcial", "dsAtoInfracionalAcaoPolicial", "dsTipoConducaoAcaoPolicial", "Controle", "idUnico", "dsNomeJuizAcaoPolicial", "dsComarcaAcaoPolicial"}, "fkAcaoPolicial= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorConduzidos.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

        try {
                jsonlocation.put("id", "" +cursorConduzidos.getInt(0));
                jsonlocation.put("fkAcaoPolicial", "" +cursorConduzidos.getInt(1));
                jsonlocation.put("dsNomeConduzidoAcaoPolicial", cursorConduzidos.getString(2));
                jsonlocation.put("IdadeConduzidoAcaoPolicial", "" +cursorConduzidos.getInt(3));
                jsonlocation.put("dsTipoProcedimentoAcaoPolcial", cursorConduzidos.getString(4));
                jsonlocation.put("dsAtoInfracionalAcaoPolicial", cursorConduzidos.getString(5));
                jsonlocation.put("dsTipoConducaoAcaoPolicial", cursorConduzidos.getString(6));
                jsonlocation.put("Controle", "" +cursorConduzidos.getInt(7));
                jsonlocation.put("idUnico", cursorConduzidos.getString(8));
                jsonlocation.put("dsNomeJuizAcaoPolicial", cursorConduzidos.getString(9));
                jsonlocation.put("dsComarcaAcaoPolicial", cursorConduzidos.getString(10));
                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();

    }

    public String EnvioDadosArmas(int id) {

        Cursor cursorArmas = db.query("ObjApreendidoArmaAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoArmaAcaoPolicial", "dsModeloArmaAcaoPolicial",
                "Controle", "idUnico", "dsCalibreArmaAcaoPolicial", "QtdArmaAcaoPolicial"}, "fkAcaoPolicial= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorArmas.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

            try {
                jsonlocation.put("id", "" +cursorArmas.getInt(0));
                jsonlocation.put("fkAcaoPolicial", "" +cursorArmas.getInt(1));
                jsonlocation.put("dsTipoArmaAcaoPolicial", cursorArmas.getString(2));
                jsonlocation.put("dsModeloArmaAcaoPolicial", "" +cursorArmas.getInt(3));
                jsonlocation.put("Controle", "" + cursorArmas.getInt(4));
                jsonlocation.put("idUnico", cursorArmas.getString(5));
                jsonlocation.put("dsCalibreArmaAcaoPolicial", cursorArmas.getString(6));
                jsonlocation.put("QtdArmaAcaoPolicial", "" +cursorArmas.getInt(7));
                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();
    }

    public String EnvioDadosDrogas(int id) {

        Cursor cursorDrogas = db.query("ObjApreendidoDrogaAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoDrogaAcaoPolicial", "dsModoApresentacaoDrogaAcaoPolicial",
                "Controle", "idUnico", "QtdDrogaAcaoPolicial", "dsPesoGramaAcaoPolicial", "dsOutrasDrogasAcaoPolicial"}, "fkAcaoPolicial= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorDrogas.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

            try {
                jsonlocation.put("id", "" +cursorDrogas.getInt(0));
                jsonlocation.put("fkAcaoPolicial", "" +cursorDrogas.getInt(1));
                jsonlocation.put("dsTipoDrogaAcaoPolicial", cursorDrogas.getString(2));
                jsonlocation.put("dsModoApresentacaoDrogaAcaoPolicial", cursorDrogas.getString(3));
                jsonlocation.put("Controle", "" + cursorDrogas.getInt(4));
                jsonlocation.put("idUnico", cursorDrogas.getString(5));
                jsonlocation.put("QtdDrogaAcaoPolicial", "" +cursorDrogas.getInt(6));
                jsonlocation.put("dsPesoGramaAcaoPolicial", cursorDrogas.getString(7));
                jsonlocation.put("dsOutrasDrogasAcaoPolicial", cursorDrogas.getString(7));
                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();
    }

    public String EnvioDadosVeiculos(int id) {

        Cursor cursorVeiculos = db.query("ObjApreendidoCarroAcaoPolicial", new String[]{"id", "fkAcaoPolicial", "dsTipoVeiculoAcaoPolicial",
                "Controle", "idUnico", "dsMarcaVeiculoAcaoPolicial", "dsModeloVeiculoAcaoPolicial", "dsPlacaOstentadaAcaoPolicial","dsCorVeiculoAcaoPolicial"}, "fkAcaoPolicial= '" + id + "'", null, null, null, null);

        JSONArray jsonArray = new JSONArray();

        while (cursorVeiculos.moveToNext()) {

            JSONObject jsonlocation = new JSONObject();

            try {
                jsonlocation.put("id", "" +cursorVeiculos.getInt(0));
                jsonlocation.put("fkAcaoPolicial", "" +cursorVeiculos.getInt(1));
                jsonlocation.put("dsTipoVeiculoAcaoPolicial", cursorVeiculos.getString(2));
                jsonlocation.put("Controle", "" + cursorVeiculos.getInt(3));
                jsonlocation.put("idUnico", cursorVeiculos.getString(4));
                jsonlocation.put("dsMarcaVeiculoAcaoPolicial", "" +cursorVeiculos.getInt(5));
                jsonlocation.put("dsModeloVeiculoAcaoPolicial", cursorVeiculos.getString(6));
                jsonlocation.put("dsPlacaOstentadaAcaoPolicial", cursorVeiculos.getString(7));
                jsonlocation.put("dsCorVeiculoAcaoPolicial", cursorVeiculos.getString(8));
                jsonArray.put(jsonlocation);
            } catch (Exception e) {
            }
        }

        return jsonArray.toString();
    }

    private void EnvioDadosAcaoPolicial(int id) {

        HttpClient httpClient = new DefaultHttpClient();

        try {
            conexao = DatabaseHelper.getInstancia(AcaoPolicialActivity.this);
            db = conexao.getReadableDatabase();

            Cursor cursor = db.query("AcaoPolicial", new String[]{"id", "dsClassificacaoAcaoPolicial", "dsNomeOperacaoPolicial", "cbkOperacaoInominada", "dtAcaoPolicial",
                    "hsAcaoPolicial", "LocalAcaoPolicial", "NBuscaJudiciaisAcaoPolicial", "dsCrimePrincipalAcaoPolicial", "cbkMedidasCautelares", "cbkInvestigacoesOrdinarias", "cbkRegistroPolicial",
                    "cbkAbordagemRotina", "cbkColaborador", "cbkDenuncia", "cbkOutros", "dsUnidadeResponsavelAcaoPolicial", "NEquipeLocaisAcaoPolicial",
                    "NEquipeUnidadeCoorpinAcaoPolicial", "NEquipeDetCoorCoorpinAcaoPolicial", "NOutraInstituicaoAcaoPolicial", "dsOutraInstituicaoAcaoPolicial", "dsNomeUnidadeEspAcaoPolicial", "NEquipeEspAcaoPolicial",
                    "dsNomeOutraInstituicaoAcaoPolicial", "NEnvolvidosOutrasInstituicaoAcaoPolicial", "EstatusAcaoPolicial", "Controle",
                    "servidor_id", "unidade_id", "validaracaopolicial", "idUnico","dstituloResumo","dsResumo", "dsNIPAcaoPolicial", "dsNomeDelegadoAcaoPolicial", "dsMunicipioReferenciaAcaoPolicial, " +
                    "cbkPresoAcaoPolicial", "cbkBensAprendidos", "dsSubitulo1", "dsTexto1", "dsSubitulo2", "dsTexto2", "dsSubitulo3", "dsTexto3", "dsSubitulo4", "dsTexto4"}, "id= '" + id + "'", null, null, null, null);

            while (cursor.moveToNext()) {

                ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();

                itens.add(new BasicNameValuePair("id", "" + cursor.getInt(0)));
                itens.add(new BasicNameValuePair("dsClassificacaoAcaoPolicial", cursor.getString(1)));
                itens.add(new BasicNameValuePair("dsNomeOperacaoPolicial", cursor.getString(2)));
                itens.add(new BasicNameValuePair("cbkOperacaoInominada", ""+cursor.getInt(3)));
                itens.add(new BasicNameValuePair("dtAcaoPolicial", cursor.getString(4)));
                itens.add(new BasicNameValuePair("hsAcaoPolicial", cursor.getString(5)));
                itens.add(new BasicNameValuePair("LocalAcaoPolicial", cursor.getString(6)));
                itens.add(new BasicNameValuePair("NBuscaJudiciaisAcaoPolicial", "" + cursor.getInt(7)));
                itens.add(new BasicNameValuePair("dsCrimePrincipalAcaoPolicial",  cursor.getString(8)));
                itens.add(new BasicNameValuePair("cbkMedidasCautelares", "" + cursor.getInt(9)));
                itens.add(new BasicNameValuePair("cbkInvestigacoesOrdinarias", "" + cursor.getInt(10)));
                itens.add(new BasicNameValuePair("cbkRegistroPolicial", "" + cursor.getInt(11)));
                itens.add(new BasicNameValuePair("cbkAbordagemRotina", "" + cursor.getInt(12)));
                itens.add(new BasicNameValuePair("cbkColaborador", "" + cursor.getInt(13)));
                itens.add(new BasicNameValuePair("cbkDenuncia", "" + cursor.getInt(14)));
                itens.add(new BasicNameValuePair("cbkOutros", "" + cursor.getInt(15)));
                itens.add(new BasicNameValuePair("dsUnidadeResponsavelAcaoPolicial", cursor.getString(16)));
                itens.add(new BasicNameValuePair("NEquipeLocaisAcaoPolicial", "" + cursor.getInt(17)));
                itens.add(new BasicNameValuePair("NEquipeUnidadeCoorpinAcaoPolicial", "" + cursor.getInt(18)));
                itens.add(new BasicNameValuePair("NEquipeDetCoorCoorpinAcaoPolicial", "" + cursor.getInt(19)));
                itens.add(new BasicNameValuePair("NOutraInstituicaoAcaoPolicial", "" + cursor.getInt(20)));
                itens.add(new BasicNameValuePair("dsOutraInstituicaoAcaoPolicial", cursor.getString(21)));
                itens.add(new BasicNameValuePair("dsNomeUnidadeEspAcaoPolicial", cursor.getString(22)));
                itens.add(new BasicNameValuePair("NEquipeEspAcaoPolicial", "" + cursor.getInt(23)));
                itens.add(new BasicNameValuePair("dsNomeOutraInstituicaoAcaoPolicial", cursor.getString(24)));
                itens.add(new BasicNameValuePair("NEnvolvidosOutrasInstituicaoAcaoPolicial", "" + cursor.getInt(25)));
                itens.add(new BasicNameValuePair("EstatusAcaoPolicial", "" + cursor.getInt(26)));
                itens.add(new BasicNameValuePair("Controle", "" + cursor.getInt(27)));
                itens.add(new BasicNameValuePair("servidor_id", "" + cursor.getInt(28)));
                itens.add(new BasicNameValuePair("unidade_id", "" + cursor.getInt(29)));
                itens.add(new BasicNameValuePair("validaracaopolicial", "" + cursor.getInt(30)));
                itens.add(new BasicNameValuePair("idUnico", cursor.getString(31)));
                itens.add(new BasicNameValuePair("dstituloResumo", cursor.getString(32)));
                itens.add(new BasicNameValuePair("dsResumo", cursor.getString(33)));
                itens.add(new BasicNameValuePair("dsNIPAcaoPolicial", cursor.getString(34)));
                itens.add(new BasicNameValuePair("dsNomeDelegadoAcaoPolicial", cursor.getString(35)));
                itens.add(new BasicNameValuePair("dsMunicipioReferenciaAcaoPolicial", cursor.getString(36)));
                itens.add(new BasicNameValuePair("cbkPresoAcaoPolicial", cursor.getString(37)));
                itens.add(new BasicNameValuePair("cbkBensAprendidos", cursor.getString(38)));
                itens.add(new BasicNameValuePair("dsSubitulo1", cursor.getString(39)));
                itens.add(new BasicNameValuePair("dsTexto1", cursor.getString(40)));
                itens.add(new BasicNameValuePair("dsSubitulo2", cursor.getString(41)));
                itens.add(new BasicNameValuePair("dsTexto2", cursor.getString(42)));
                itens.add(new BasicNameValuePair("dsSubitulo3", cursor.getString(43)));
                itens.add(new BasicNameValuePair("dsTexto3", cursor.getString(44)));
                itens.add(new BasicNameValuePair("dsSubitulo4", cursor.getString(45)));
                itens.add(new BasicNameValuePair("dsTexto4", cursor.getString(46)));
                itens.add(new BasicNameValuePair("conduzido", EnvioDadosConduzidos(id)));
                itens.add(new BasicNameValuePair("apreendidoarma", EnvioDadosArmas(id)));
                itens.add(new BasicNameValuePair("apreendidodroga", EnvioDadosDrogas(id)));
                itens.add(new BasicNameValuePair("apreendidocarro", EnvioDadosVeiculos(id)));

                String idservidor = String.valueOf(usuarioDao.user.getId());
                String matricula = usuarioDao.user.getDsMatricula();

                UrlEncodedFormEntity parametro = new UrlEncodedFormEntity(itens, "utf-8");
                HttpPost httpPost = new HttpPost("https://irispoliciacivilba.com.br/app/modulo/acaopolicial/cadastrar?id=" + idservidor + "&matricula=" + matricula + "?");

                httpPost.setEntity(parametro);
                final HttpResponse response = httpClient.execute(httpPost);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            String resposta = EntityUtils.toString(response.getEntity());

                            JSONObject login = new JSONObject(resposta);

                            if (login.getString("resposta") == "1" || login.getInt("resposta") == 1) {

                                Toast.makeText(AcaoPolicialActivity.this, "Sincronizado com Sucesso!!", Toast.LENGTH_LONG).show();
                            } else {

                                Toast.makeText(AcaoPolicialActivity.this, "Erro ao Sincronizar!!", Toast.LENGTH_LONG).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        } finally {

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
        ListaAcaoPolicialGeral = acaoPolicialDao.retornaAcaoPolicial();
        ListaAcaoPolicialGeralFiltrados.clear();
        ListaAcaoPolicialGeralFiltrados.addAll(ListaAcaoPolicialGeral);
        LvAcaoPolicial.invalidateViews();
    }
}