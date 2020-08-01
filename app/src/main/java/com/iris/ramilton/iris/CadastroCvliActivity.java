package com.iris.ramilton.iris;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.AutoriaDao;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.VitimaDao;
import com.iris.ramilton.iris.modelo.Cvli;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CadastroCvliActivity extends AppCompatActivity {

    private Button doFato, doSilc, daVitima, daAutoria, dasProvidencias, daFaseJudicial, ResumoFatos, ValidarCVLI;
    private Cvli cvliatualizar = null;
    private int codigopassado;
    private List<Cvli> ListaCvliFato;
    private List<Cvli> ListaCvliFatoFiltrados = new ArrayList<>();
    private List<Cvli> ListaCvliSilc;
    private List<Cvli> ListaCvliSilcFiltrados = new ArrayList<>();
    private List<Cvli> ListaCvliProvidencia;
    private List<Cvli> ListaCvliProvidenciaFiltrados = new ArrayList<>();
    private List<Cvli> ListaCvliResumo;
    private List<Cvli> ListaCvliResumoFiltrados = new ArrayList<>();
    private Cvli cvli;
    private CvliDao cvliDao;
    private int atualizacadastra = 0, clique = 0;
    private VitimaDao vitimaDao;
    private AutoriaDao autoriaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cvli);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        //Adicionando os objetos na tela
        doFato = (Button) findViewById(R.id.btnFato);
        doSilc = (Button) findViewById(R.id.btnSilc);
        daVitima = (Button) findViewById(R.id.btnVitima);
        daAutoria = (Button) findViewById(R.id.btnAutoria);
        dasProvidencias = (Button) findViewById(R.id.btnProvidencias);
        daFaseJudicial = (Button) findViewById(R.id.btnFaseJudicial);
        ResumoFatos = (Button) findViewById(R.id.btnResumoFato);
        ValidarCVLI = (Button) findViewById(R.id.btnValidarCVLI);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);
        vitimaDao = new VitimaDao(this);
        autoriaDao = new AutoriaDao(this);

        Intent it = getIntent();

        if(it.hasExtra("cvli")){
            cvliatualizar = (Cvli) it.getSerializableExtra("cvli");
            codigopassado = cvliatualizar.getId();

            ListaCvliFato = cvliDao.retornaCVLIFato(codigopassado);
            ListaCvliFatoFiltrados.addAll(ListaCvliFato);

            ListaCvliSilc = cvliDao.retornaCVLISilc(codigopassado);
            ListaCvliSilcFiltrados.addAll(ListaCvliSilc);

            ListaCvliProvidencia = cvliDao.retornaCVLIProvidencias(codigopassado);
            ListaCvliProvidenciaFiltrados.addAll(ListaCvliProvidencia);

            ListaCvliResumo = cvliDao.retornaCVLIResumo(codigopassado);
            ListaCvliResumoFiltrados.addAll(ListaCvliResumo);

            atualizacadastra = 1;

        }

        //Criando os eventos dos objetos na tela
        doFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(atualizacadastra == 1) {
                    doFato.setBackgroundResource(R.color.colorBotaoClicado);
                    final Cvli cvliAtualizarFato = ListaCvliFatoFiltrados.get(0);
                    Intent it = new Intent(CadastroCvliActivity.this, FatoActivity.class);
                    it.putExtra("fato", cvliAtualizarFato);
                    startActivity(it);
                }else{
                    if(clique == 0) {
                        doFato.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroCvliActivity.this, FatoActivity.class));
                    }else{
                        doFato.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = cvliDao.retornarCodigoCvliSemParametro();
                        Intent it = new Intent(CadastroCvliActivity.this, FatoActivity.class);
                        it.putExtra("fatosematualizar", codigo);
                        startActivity(it);
                    }
                }

            }
        });

        doSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSilc.setBackgroundResource(R.color.colorBotaoClicado);
                if(atualizacadastra == 1) {
                    final Cvli cvliAtualizarSilc = ListaCvliSilcFiltrados.get(0);
                    Intent it = new Intent(CadastroCvliActivity.this, SilcActivity.class);
                    it.putExtra("silc", cvliAtualizarSilc);
                    startActivity(it);
                }else {
                    if(clique == 0) {
                        doSilc.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroCvliActivity.this, SilcActivity.class));
                    }else{
                        doSilc.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = cvliDao.retornarCodigoCvliSemParametro();
                        Intent it = new Intent(CadastroCvliActivity.this, SilcActivity.class);
                        it.putExtra("silcsematualizar", codigo);
                        startActivity(it);
                    }
                }
            }
        });

        daVitima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clique = 0;
                daVitima.setBackgroundResource(R.color.colorBotaoClicado);
                if(atualizacadastra == 1){
                    Intent it = new Intent(CadastroCvliActivity.this, AtualizarVitimaActivity.class);
                    it.putExtra("codigovitima", codigopassado);
                    startActivity(it);
                }else {
                    if(clique == 0){
                        daVitima.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroCvliActivity.this, VitimaActivity.class));
                    }else{
                        daVitima.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = cvliDao.retornarCodigoCvliSemParametro();
                        Intent it = new Intent(CadastroCvliActivity.this, VitimaActivity.class);
                        it.putExtra("vitimasematualizar", codigo);
                        startActivity(it);
                    }

                }
            }
        });

        daAutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clique = 0;
                daAutoria.setBackgroundResource(R.color.colorBotaoClicado);
                if(atualizacadastra == 1){
                    Intent it = new Intent(CadastroCvliActivity.this, AtualizarAutoriaActivity.class);
                    it.putExtra("codigoautoria", codigopassado);
                    startActivity(it);
                }else{
                    if(clique == 0){
                        daAutoria.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroCvliActivity.this, AutoriaActivity.class));
                    }else{
                        daAutoria.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = cvliDao.retornarCodigoCvliSemParametro();
                        Intent it = new Intent(CadastroCvliActivity.this, AutoriaActivity.class);
                        it.putExtra("autoriaematualizar", codigo);
                        startActivity(it);
                    }

                }
            }
        });

        dasProvidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dasProvidencias.setBackgroundResource(R.color.colorBotaoClicado);
                if(atualizacadastra == 1){
                    final Cvli cvliAtualizarProvidencia = ListaCvliProvidenciaFiltrados.get(0);
                    Intent it = new Intent(CadastroCvliActivity.this, ProvidenciasActivity.class);
                    it.putExtra("providencia", cvliAtualizarProvidencia);
                    startActivity(it);
                }else {
                    if(clique == 0){
                        dasProvidencias.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroCvliActivity.this, ProvidenciasActivity.class));
                    }else{
                        dasProvidencias.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = cvliDao.retornarCodigoCvliSemParametro();
                        Intent it = new Intent(CadastroCvliActivity.this, ProvidenciasActivity.class);
                        it.putExtra("providenciasematualizar", codigo);
                        startActivity(it);
                    }

                }

            }
        });

        daFaseJudicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // daFaseJudicial.setBackgroundResource(R.color.colorBotaoClicado);
               // startActivity(new Intent(CadastroCvliActivity.this,FaseJudicialActivity.class));
            }
        });

        ResumoFatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResumoFatos.setBackgroundResource(R.color.colorBotaoClicado);
                if(atualizacadastra == 1){
                    final Cvli cvliAtualizarResumo = ListaCvliResumoFiltrados.get(0);
                    Intent it = new Intent(CadastroCvliActivity.this, ResumoFatosActivity.class);
                    it.putExtra("resumo", cvliAtualizarResumo);
                    startActivity(it);
                }else {
                    if(clique == 0){
                        ResumoFatos.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroCvliActivity.this, ResumoFatosActivity.class));
                    }else{
                        ResumoFatos.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = cvliDao.retornarCodigoCvliSemParametro();
                        Intent it = new Intent(CadastroCvliActivity.this, ResumoFatosActivity.class);
                        it.putExtra("resumoematualizar", codigo);
                        startActivity(it);
                    }

                }

            }
        });

        ValidarCVLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarCVLI.setBackgroundResource(R.color.colorBotaoClicado);

                if(atualizacadastra == 1){

                    if (cvliDao.VerificarEmBrancoCVLI(codigopassado) != null) {

                        AlertDialog.Builder b = new AlertDialog.Builder(CadastroCvliActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CadastroCvliActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("Os campos '" + cvliDao.VerificarEmBrancoCVLI(codigopassado) + "' não podem ficar em branco!!!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        b.show();

                    } else if (vitimaDao.VerificarBracoVitimas(codigopassado) != null) {

                        AlertDialog.Builder b = new AlertDialog.Builder(CadastroCvliActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CadastroCvliActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("Os campos '" + vitimaDao.VerificarBracoVitimas(codigopassado) + "' não podem ficar em branco!!!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        b.show();
                    } else if (autoriaDao.VerificarBrancoAutoria(codigopassado) != null) {
                        AlertDialog.Builder b = new AlertDialog.Builder(CadastroCvliActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CadastroCvliActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("Os campos '" + autoriaDao.VerificarBrancoAutoria(codigopassado) + "' não podem ficar em branco!!!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        b.show();
                    } else {

                        cvli.setValidarcvli(1);
                        long retornocodigovalidado = cvliDao.validarCVLI(cvli,codigopassado);
                        finish();
                        if(retornocodigovalidado > 0){
                            Toast.makeText(CadastroCvliActivity.this, "CVLI Validado com sucesso!!!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(CadastroCvliActivity.this, "Erro ao Validar CVLI!!!", Toast.LENGTH_LONG).show();
                        }
                    }

                }else{

                    ValidarCVLI.setBackgroundResource(R.color.colorBotaoClicado);

                    if (cvliDao.VerificarEmBrancoCVLI(cvliDao.retornarCodigoCvliSemParametro()) != null) {

                        AlertDialog.Builder b = new AlertDialog.Builder(CadastroCvliActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CadastroCvliActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("Os campos '" + cvliDao.VerificarEmBrancoCVLI(cvliDao.retornarCodigoCvliSemParametro()) + "' não podem ficar vazios!!!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        b.show();

                    } else if (vitimaDao.VerificarBracoVitimas(cvliDao.retornarCodigoCvliSemParametro()) != null) {

                        AlertDialog.Builder b = new AlertDialog.Builder(CadastroCvliActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CadastroCvliActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("Os campos '" + vitimaDao.VerificarBracoVitimas(cvliDao.retornarCodigoCvliSemParametro()) + "' não podem ficar em brancos!!!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        b.show();
                    } else if (autoriaDao.VerificarBrancoAutoria(cvliDao.retornarCodigoCvliSemParametro()) != null) {
                        AlertDialog.Builder b = new AlertDialog.Builder(CadastroCvliActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CadastroCvliActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("Os campos '" + autoriaDao.VerificarBrancoAutoria(cvliDao.retornarCodigoCvliSemParametro()) + "' não podem ficar em brancos!!!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        b.show();
                    } else {

                        cvli.setValidarcvli(1);
                        long retornocodigovalidado = cvliDao.validarCVLI(cvli,cvliDao.retornarCodigoCvliSemParametro());
                        finish();
                        if(retornocodigovalidado > 0){
                            Toast.makeText(CadastroCvliActivity.this, "CVLI Validado com sucesso!!!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(CadastroCvliActivity.this, "Erro ao Validar CVLI!!!", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastrocvli, menu);

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
