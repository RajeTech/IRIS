package com.iris.ramilton.iris;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.AcaoPolicialDao;
import com.iris.ramilton.iris.dao.AutoriaDao;
import com.iris.ramilton.iris.dao.ConduzidoacaopolicialDao;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.ObjapreendidoarmaacaopolicialDao;
import com.iris.ramilton.iris.dao.ObjapreendidodrogaacaopolicialDao;
import com.iris.ramilton.iris.dao.ObjapreendidoveiculoacaopolicialDao;
import com.iris.ramilton.iris.dao.VitimaDao;
import com.iris.ramilton.iris.modelo.Acaopolicial;
import com.iris.ramilton.iris.modelo.Cvli;

import java.util.ArrayList;
import java.util.List;

public class CadastroAcaoPolicialActivity extends AppCompatActivity {

    private Button BtnAcaoPolicial, BtnEquipesEnvolvidas, BtnConduzidos, BtnOjetosApreendidos, BtnValidarAcaoPolicial, BtnDoProcedimentoAcaoPolicial;
    private Acaopolicial acaopolicialatualizar = null;
    private int codigopassado;
    private List<Acaopolicial> ListaAcaoPolicialDacao;
    private List<Acaopolicial> ListaAcaoPolicialDacaoFiltrados = new ArrayList<>();
    private List<Acaopolicial> ListaAcaoPolicialDaEquipeEnvolvidas;
    private List<Acaopolicial> ListaAcaoPolicialDaEquipeEnvolvidasFiltrados = new ArrayList<>();
    private List<Acaopolicial> ListaAcaoPolicialDoProcedimento;
    private List<Acaopolicial> ListaAcaoPolicialDoProcedimentoFiltrados = new ArrayList<>();
    private Acaopolicial acaopolicial;
    private AcaoPolicialDao acaoPolicialDao;
    private int atualizacadastra = 0, clique = 0;
    private ConduzidoacaopolicialDao conduzidoacaopolicialDao;
    private ObjapreendidoarmaacaopolicialDao objapreendidoarmaacaopolicialDao;
    private ObjapreendidodrogaacaopolicialDao objapreendidodrogaacaopolicialDao;
    private ObjapreendidoveiculoacaopolicialDao objapreendidoveiculoacaopolicialDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_acao_policial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        BtnAcaoPolicial = (Button) findViewById(R.id.btnDaAcaoPolicial);
        BtnEquipesEnvolvidas = (Button) findViewById(R.id.btnDasEquipesEnvolvidas);
        BtnConduzidos = (Button) findViewById(R.id.btnDosConduzidos);
        BtnOjetosApreendidos = (Button) findViewById(R.id.btnDosObjetosApreendidos);
        BtnDoProcedimentoAcaoPolicial = (Button) findViewById(R.id.btnDoProcedimentoAcaoPolicial);
        BtnValidarAcaoPolicial = (Button) findViewById(R.id.btnValidarAcaoPolicial);

        conduzidoacaopolicialDao = new ConduzidoacaopolicialDao(this);
        acaoPolicialDao = new AcaoPolicialDao(this);
        acaopolicial = new Acaopolicial();

        Intent it = getIntent();

        if(it.hasExtra("acaopolicial")){

            acaopolicialatualizar = (Acaopolicial) it.getSerializableExtra("acaopolicial");
            codigopassado = acaopolicialatualizar.getId();

            ListaAcaoPolicialDacao = acaoPolicialDao.retornaAcaoPolicialDaAcao(codigopassado);
            ListaAcaoPolicialDacaoFiltrados.addAll(ListaAcaoPolicialDacao);

            ListaAcaoPolicialDaEquipeEnvolvidas = acaoPolicialDao.retornaAcaoPolicialDaEquipeEnvolvida(codigopassado);
            ListaAcaoPolicialDaEquipeEnvolvidasFiltrados.addAll(ListaAcaoPolicialDaEquipeEnvolvidas);

            ListaAcaoPolicialDoProcedimento = acaoPolicialDao.retornaAcaoPolicialDoProcedimento(codigopassado);
            ListaAcaoPolicialDoProcedimentoFiltrados.addAll(ListaAcaoPolicialDoProcedimento);

            atualizacadastra = 1;

        }

        BtnAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(atualizacadastra == 1) {
                    BtnAcaoPolicial.setBackgroundResource(R.color.colorBotaoClicado);
                    final Acaopolicial acaopolicialAtualizardaAcao = ListaAcaoPolicialDacaoFiltrados.get(0);
                    Intent it = new Intent(CadastroAcaoPolicialActivity.this, DaAcaoPolicialActivity.class);
                    it.putExtra("dacao", acaopolicialAtualizardaAcao);
                    startActivity(it);
                }else{
                    if(clique == 0) {
                        BtnAcaoPolicial.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroAcaoPolicialActivity.this, DaAcaoPolicialActivity.class));
                    }else{
                        BtnAcaoPolicial.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = acaoPolicialDao.retornarCodigoAcaoPolicialSemParametro();
                        Intent it = new Intent(CadastroAcaoPolicialActivity.this, DaAcaoPolicialActivity.class);
                        it.putExtra("dacaosematualizar", codigo);
                        startActivity(it);
                    }
                }

            }
        });

        BtnEquipesEnvolvidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnEquipesEnvolvidas.setBackgroundResource(R.color.colorBotaoClicado);
                if(atualizacadastra == 1) {
                    final Acaopolicial AcaopolicialAtualizarEquipe = ListaAcaoPolicialDaEquipeEnvolvidasFiltrados.get(0);
                    Intent it = new Intent(CadastroAcaoPolicialActivity.this, DaEquipeEnvolvidaActivity.class);
                    it.putExtra("acaopolicialequipe", AcaopolicialAtualizarEquipe);
                    startActivity(it);
                }else {
                    if(clique == 0) {
                        BtnEquipesEnvolvidas.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroAcaoPolicialActivity.this, DaEquipeEnvolvidaActivity.class));
                    }else{
                        BtnEquipesEnvolvidas.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = acaoPolicialDao.retornarCodigoAcaoPolicialSemParametro();
                        Intent it = new Intent(CadastroAcaoPolicialActivity.this, DaEquipeEnvolvidaActivity.class);
                        it.putExtra("equipesematualizar", codigo);
                        startActivity(it);
                    }
                }
            }
        });

        BtnOjetosApreendidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnOjetosApreendidos.setBackgroundResource(R.color.colorBotaoClicado);
                if(atualizacadastra == 1){
                    Intent it = new Intent(CadastroAcaoPolicialActivity.this, ObjetosApeendidosAcaoPolicialActivity.class);
                    it.putExtra("codigoobjetos", codigopassado);
                    startActivity(it);
                }else {
                    if(clique == 0){
                        BtnOjetosApreendidos.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroAcaoPolicialActivity.this, ObjetosApeendidosAcaoPolicialActivity.class));
                    }else{
                        BtnOjetosApreendidos.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = acaoPolicialDao.retornarCodigoAcaoPolicialSemParametro();
                        Intent it = new Intent(CadastroAcaoPolicialActivity.this, ObjetosApeendidosAcaoPolicialActivity.class);
                        it.putExtra("objetossematualizar", codigo);
                        startActivity(it);
                    }

                }
            }
        });

        BtnConduzidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clique = 0;
                BtnConduzidos.setBackgroundResource(R.color.colorBotaoClicado);
                if(atualizacadastra == 1){
                    Intent it = new Intent(CadastroAcaoPolicialActivity.this, AtualizarConduzidoAcaoPolicial.class);
                    it.putExtra("codigoconduzidos", codigopassado);
                    startActivity(it);
                }else {
                    if(clique == 0){
                        BtnConduzidos.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroAcaoPolicialActivity.this, ConduzidosAcaoPolicialActivity.class));
                    }else{
                        BtnConduzidos.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = acaoPolicialDao.retornarCodigoAcaoPolicialSemParametro();
                        Intent it = new Intent(CadastroAcaoPolicialActivity.this, ConduzidosAcaoPolicialActivity.class);
                        it.putExtra("conduzidosematualizar", codigo);
                        startActivity(it);
                    }

                }
            }
        });

        BtnDoProcedimentoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnDoProcedimentoAcaoPolicial.setBackgroundResource(R.color.colorBotaoClicado);
                if(atualizacadastra == 1) {
                    Log.i("Valores do bo","Entrou aqui");
                    final Acaopolicial AcaopolicialAtualizarDoprocedimento = ListaAcaoPolicialDoProcedimentoFiltrados.get(0);
                    Log.i("Valores do atu acaop",""+AcaopolicialAtualizarDoprocedimento);
                    Intent it = new Intent(CadastroAcaoPolicialActivity.this, DoProcedimentoAcaoPolicial.class);
                    it.putExtra("acaopolicialprocedimento", AcaopolicialAtualizarDoprocedimento);
                    startActivity(it);
                }else {
                    if(clique == 0) {
                        BtnDoProcedimentoAcaoPolicial.setBackgroundResource(R.color.colorBotaoClicado);
                        clique = 1;
                        startActivity(new Intent(CadastroAcaoPolicialActivity.this, DoProcedimentoAcaoPolicial.class));
                    }else{
                        BtnDoProcedimentoAcaoPolicial.setBackgroundResource(R.color.colorBotaoClicado);
                        int codigo = acaoPolicialDao.retornarCodigoAcaoPolicialSemParametro();
                        Log.i("Valores cod se at",""+codigo);
                        Intent it = new Intent(CadastroAcaoPolicialActivity.this, DoProcedimentoAcaoPolicial.class);
                        it.putExtra("procedimentosematualizar", codigo);
                        startActivity(it);
                    }
                }
            }
        });

        BtnValidarAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnValidarAcaoPolicial.setBackgroundResource(R.color.colorBotaoClicado);

                if(atualizacadastra == 1){

                    if (acaoPolicialDao.VerificarEmBrancoAcaoPolicial(codigopassado) != null) {

                        AlertDialog.Builder b = new AlertDialog.Builder(CadastroAcaoPolicialActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CadastroAcaoPolicialActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("Os campos '" + acaoPolicialDao.VerificarEmBrancoAcaoPolicial(codigopassado) + "' não podem ficar em branco!!!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        b.show();

                    } else if (conduzidoacaopolicialDao.VerificarEmBrancoConduzido(codigopassado) != null) {

                        AlertDialog.Builder b = new AlertDialog.Builder(CadastroAcaoPolicialActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CadastroAcaoPolicialActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("Os campos '" + conduzidoacaopolicialDao.VerificarEmBrancoConduzido(codigopassado) + "' não podem ficar em branco!!!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        b.show();
                    }else {
                        acaopolicial.setValidaracaopolicial(1);
                        long retornocodigovalidado = acaoPolicialDao.validarAcaoPolicial(acaopolicial,codigopassado);
                        finish();
                        if(retornocodigovalidado > 0){
                            Toast.makeText(CadastroAcaoPolicialActivity.this, "Ação Policial Validado com sucesso!!!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(CadastroAcaoPolicialActivity.this, "Erro ao Validar Ação Policial!!!", Toast.LENGTH_LONG).show();
                        }
                    }

                }else{

                    BtnValidarAcaoPolicial.setBackgroundResource(R.color.colorBotaoClicado);

                    if (acaoPolicialDao.VerificarEmBrancoAcaoPolicial(acaoPolicialDao.retornarCodigoAcaoPolicialSemParametro()) != null) {

                        AlertDialog.Builder b = new AlertDialog.Builder(CadastroAcaoPolicialActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CadastroAcaoPolicialActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("Os campos '" + acaoPolicialDao.VerificarEmBrancoAcaoPolicial(acaoPolicialDao.retornarCodigoAcaoPolicialSemParametro()) + "' não podem ficar vazios!!!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        b.show();

                    } else if (conduzidoacaopolicialDao.VerificarEmBrancoConduzido(acaoPolicialDao.retornarCodigoAcaoPolicialSemParametro()) != null) {

                        AlertDialog.Builder b = new AlertDialog.Builder(CadastroAcaoPolicialActivity.this);
                        LayoutInflater factory = LayoutInflater.from(CadastroAcaoPolicialActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("Os campos '" + conduzidoacaopolicialDao.VerificarEmBrancoConduzido(acaoPolicialDao.retornarCodigoAcaoPolicialSemParametro()) + "' não podem ficar em brancos!!!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        b.show();
                    }else {
                        acaopolicial.setValidaracaopolicial(1);
                        long retornocodigovalidado = acaoPolicialDao.validarAcaoPolicial(acaopolicial,acaoPolicialDao.retornarCodigoAcaoPolicialSemParametro());
                        finish();
                        if(retornocodigovalidado > 0){
                            Toast.makeText(CadastroAcaoPolicialActivity.this, "Ação Policial Validado com sucesso!!!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(CadastroAcaoPolicialActivity.this, "Erro ao Validar Ação Policial!!!", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastroacaopolicial, menu);

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
