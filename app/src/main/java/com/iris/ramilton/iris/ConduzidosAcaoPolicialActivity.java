package com.iris.ramilton.iris;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.AcaoPolicialDao;
import com.iris.ramilton.iris.dao.ConduzidoacaopolicialDao;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.EquipeLevantamentoDao;
import com.iris.ramilton.iris.dao.EquipePeritoDao;
import com.iris.ramilton.iris.dao.EquipePreservacaoLocalDao;
import com.iris.ramilton.iris.dao.ObjetosApreendidosDao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.dao.VitimaDao;
import com.iris.ramilton.iris.modelo.Acaopolicial;
import com.iris.ramilton.iris.modelo.Conduzidoacaopolicial;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Equipelevantamento;
import com.iris.ramilton.iris.modelo.Equipeperito;
import com.iris.ramilton.iris.modelo.Equipepreservacaolocal;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.ObjetosApreendidos;
import com.iris.ramilton.iris.modelo.Vitima;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ConduzidosAcaoPolicialActivity extends AppCompatActivity {

    private Button BtnNomeConduzidoAcaoPolicial, BtnIdadeConduzidoAcaoPolicial, BtnTipoConducaoConduzidoAcaoPolicial, BtnNovoConduzidoAcaoPolicial, BtnFinalizarConduzidoAcaoPolicial;
    private TextView TvIdadeConduzidoAcaoPolicial, TvTipoProcedimentoRelaConduzidoAcaoPolicial, TvAtoInfracionalConduzidoAcaoPolicial, TvNomeJuizConduzidoAcaoPolicial, TvComarcaConduzidoAcaoPolicial;
    private Spinner SpTipoProcedimentoRealAcaoPolicial, SpTipoConducaoAcaoPolicial;
    private EditText EdtNomeConduzidoAcaoPolicial, EdtIdadeConduzidoAcaoPolicial, EdtAtoInfracionalConduzidoAcaoPolicial, EdtNomeJuizConduzidoAcaoPolicial, EdtComarcaConduzidoAcaoPolicial;
    private String tipoconducaoacaopolicial;
    private Conduzidoacaopolicial conduzidoacaopolicial, conduzidosacaopolicialsematualizar;
    private ConduzidoacaopolicialDao conduzidoacaopolicialDao, conduzidosacaopolicialsematualizarDao;
    private Conduzidoacaopolicial conduzidoatualiza = null;
    private int atualizar = 0, jogada = 0, codigoacaopolicial, fkatualizar, codigorecebidosem;
    private Gerarnumeros gerarnumeros;
    private String Dstipoprocedimentoacaopolicial, Dstipoconducaopolicial;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conduzidos_acao_policial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        BtnNomeConduzidoAcaoPolicial = (Button) findViewById(R.id.btnNomeConduzidoAcaoPolcial);
        BtnIdadeConduzidoAcaoPolicial = (Button) findViewById(R.id.btnIdadeConduzidoAcaoPolicial);
        BtnTipoConducaoConduzidoAcaoPolicial = (Button) findViewById(R.id.btnTipoConducaoConduzidoAcaoPolicial);
        BtnNovoConduzidoAcaoPolicial = (Button) findViewById(R.id.btnNovoConduzidoAcaoPolicial);
        BtnFinalizarConduzidoAcaoPolicial = (Button) findViewById(R.id.btnFinalizarConduzidoAcaoPolicial);
        TvIdadeConduzidoAcaoPolicial = (TextView) findViewById(R.id.tvIdadeConduzidoAcaoPolicial);
        TvTipoProcedimentoRelaConduzidoAcaoPolicial = (TextView) findViewById(R.id.tvTipoProcedimentoRelAcaoPolicial);
        TvAtoInfracionalConduzidoAcaoPolicial = (TextView) findViewById(R.id.tvAtoInfracionalAcaoPolicial);
        TvNomeJuizConduzidoAcaoPolicial = (TextView) findViewById(R.id.tvNomeJuizConduzidoAcaoPolicial);
        TvComarcaConduzidoAcaoPolicial = (TextView) findViewById(R.id.tvComarcaConduzidoAcaoPolicial);
        SpTipoProcedimentoRealAcaoPolicial = (Spinner) findViewById(R.id.spTipoProcedimentoRelAcaoPolicial);
        SpTipoConducaoAcaoPolicial = (Spinner) findViewById(R.id.spTipoConducaoConduzidoAcaoPolicial);
        EdtNomeConduzidoAcaoPolicial = (EditText) findViewById(R.id.edtNomeConduzidoAcaoPolicial);
        EdtIdadeConduzidoAcaoPolicial = (EditText) findViewById(R.id.edtIdadeConduzidoAcaoPolicial);
        EdtAtoInfracionalConduzidoAcaoPolicial = (EditText) findViewById(R.id.edtAtoInfracionalAcaoPolicial);
        EdtNomeJuizConduzidoAcaoPolicial = (EditText) findViewById(R.id.edtNomeJuizConducaoAcaoPolicial);
        EdtComarcaConduzidoAcaoPolicial = (EditText) findViewById(R.id.edtComarcaConduzidoAcaoPolicial);

        conduzidosacaopolicialsematualizar = new Conduzidoacaopolicial();
        conduzidosacaopolicialsematualizarDao = new ConduzidoacaopolicialDao(this);

        gerarnumeros = new Gerarnumeros();

        Intent it = getIntent();

        if (it.hasExtra("codigoconduzidos")) {

            conduzidoatualiza = (Conduzidoacaopolicial) it.getSerializableExtra("codigoconduzidos");

            atualizar = 1;

            EdtNomeConduzidoAcaoPolicial.setText(conduzidoatualiza.getDsNomeConduzidoAcaoPolicial());
            EdtIdadeConduzidoAcaoPolicial.setText(conduzidoatualiza.getIdadeConduzidoAcaoPolicial());
            EdtAtoInfracionalConduzidoAcaoPolicial.setText(conduzidoatualiza.getDsAtoInfracionalAcaoPolicial());
            EdtNomeJuizConduzidoAcaoPolicial.setText(conduzidoatualiza.getDsNomeJuizAcaoPolicial());
            EdtComarcaConduzidoAcaoPolicial.setText(conduzidoatualiza.getDsComarcaAcaoPolicial());

        }else if(it.hasExtra("fkconduzidoAtualizar")){
            fkatualizar = (int) it.getSerializableExtra("fkconduzidoAtualizar");
            jogada = 1;
        }else if(it.hasExtra("conduzidosematualizar")){
            codigorecebidosem = (int) it.getSerializableExtra("conduzidosematualizar");

            conduzidosacaopolicialsematualizar = conduzidosacaopolicialsematualizarDao.retornaConduzidoAcaoPolicialObj(codigorecebidosem);

            atualizar = 2;

            EdtNomeConduzidoAcaoPolicial.setText(conduzidoatualiza.getDsNomeConduzidoAcaoPolicial());
            EdtIdadeConduzidoAcaoPolicial.setText(conduzidoatualiza.getIdadeConduzidoAcaoPolicial());
            EdtAtoInfracionalConduzidoAcaoPolicial.setText(conduzidoatualiza.getDsAtoInfracionalAcaoPolicial());
            EdtNomeJuizConduzidoAcaoPolicial.setText(conduzidoatualiza.getDsNomeJuizAcaoPolicial());
            EdtComarcaConduzidoAcaoPolicial.setText(conduzidoatualiza.getDsComarcaAcaoPolicial());

        }

        usuarioDao = new UsuarioDao(this);
        conduzidoacaopolicialDao = new ConduzidoacaopolicialDao(this);
        conduzidoacaopolicial = new Conduzidoacaopolicial();

        if(atualizar == 1){
            BtnFinalizarConduzidoAcaoPolicial.setVisibility(View.INVISIBLE);
            BtnNovoConduzidoAcaoPolicial.setText("Concluir");
        }else if(atualizar == 2){
            BtnFinalizarConduzidoAcaoPolicial.setVisibility(View.INVISIBLE);
            BtnNovoConduzidoAcaoPolicial.setText("Concluir");
        }else{
            BtnFinalizarConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
            BtnNovoConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
        }

        ArrayAdapter adaptadorTipoProcedimento = ArrayAdapter.createFromResource(this, R.array.TipoProcedimentoRelacionadoAcaoPolicial, android.R.layout.simple_spinner_item);
        SpTipoProcedimentoRealAcaoPolicial.setAdapter(adaptadorTipoProcedimento);

        ArrayAdapter adaptadorTipoConducao = ArrayAdapter.createFromResource(this, R.array.TipoConducaoAcaoPolicial, android.R.layout.simple_spinner_item);
        SpTipoConducaoAcaoPolicial.setAdapter(adaptadorTipoConducao);

        SpTipoProcedimentoRealAcaoPolicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Dstipoprocedimentoacaopolicial = SpTipoProcedimentoRealAcaoPolicial.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpTipoConducaoAcaoPolicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoconducaoacaopolicial = SpTipoConducaoAcaoPolicial.getItemAtPosition(position).toString();

                if(tipoconducaoacaopolicial.equals("Prisão Temporária")){
                    TvNomeJuizConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtNomeJuizConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    TvComarcaConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtComarcaConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                }

                if(tipoconducaoacaopolicial.equals("Prisão Preventiva")){
                    TvNomeJuizConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtNomeJuizConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    TvComarcaConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtComarcaConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                }

                if(tipoconducaoacaopolicial.equals("Prisão por condenação")){
                    TvNomeJuizConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtNomeJuizConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    TvComarcaConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtComarcaConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                }
                if(tipoconducaoacaopolicial.equals("Prisão em Flagrante")){
                    TvNomeJuizConduzidoAcaoPolicial.setVisibility(View.GONE);
                    EdtNomeJuizConduzidoAcaoPolicial.setVisibility(View.GONE);
                    TvComarcaConduzidoAcaoPolicial.setVisibility(View.GONE);
                    EdtComarcaConduzidoAcaoPolicial.setVisibility(View.GONE);
                }
                if(tipoconducaoacaopolicial.equals("Condução para qualificação e oitiva")){
                    TvNomeJuizConduzidoAcaoPolicial.setVisibility(View.GONE);
                    EdtNomeJuizConduzidoAcaoPolicial.setVisibility(View.GONE);
                    TvComarcaConduzidoAcaoPolicial.setVisibility(View.GONE);
                    EdtComarcaConduzidoAcaoPolicial.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnNomeConduzidoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtNomeConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        EdtNomeConduzidoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnIdadeConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        BtnIdadeConduzidoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TvIdadeConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                EdtIdadeConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(ConduzidosAcaoPolicialActivity.this);

                if (atualizar == 1) {
                    TvIdadeConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtIdadeConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    TvTipoProcedimentoRelaConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    SpTipoProcedimentoRealAcaoPolicial.setVisibility(View.VISIBLE);
                    TvAtoInfracionalConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtAtoInfracionalConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    BtnTipoConducaoConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    esconderTeclado(ConduzidosAcaoPolicialActivity.this);

                    Dstipoprocedimentoacaopolicial = conduzidoatualiza.getDsTipoProcedimentoAcaoPolcial();

                    if (Dstipoprocedimentoacaopolicial == null) {
                        SpTipoProcedimentoRealAcaoPolicial.setSelection(0);
                        Dstipoprocedimentoacaopolicial = SpTipoProcedimentoRealAcaoPolicial.getItemAtPosition(0).toString();
                    }

                    switch (Dstipoprocedimentoacaopolicial) {

                        case "Selecione...":
                            SpTipoProcedimentoRealAcaoPolicial.setSelection(0);
                            break;
                        case "Auto de Apreensão":
                            SpTipoProcedimentoRealAcaoPolicial.setSelection(1);
                            break;
                        case "BOC":
                            SpTipoProcedimentoRealAcaoPolicial.setSelection(2);
                            break;
                        default:
                            SpTipoProcedimentoRealAcaoPolicial.setSelection(3);
                            break;
                    }
                } else if (atualizar == 2) {
                    TvIdadeConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtIdadeConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    TvTipoProcedimentoRelaConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    SpTipoProcedimentoRealAcaoPolicial.setVisibility(View.VISIBLE);
                    TvAtoInfracionalConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtAtoInfracionalConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    esconderTeclado(ConduzidosAcaoPolicialActivity.this);

                    Dstipoprocedimentoacaopolicial = conduzidosacaopolicialsematualizar.getDsTipoProcedimentoAcaoPolcial();

                    if (Dstipoprocedimentoacaopolicial == null) {
                        SpTipoProcedimentoRealAcaoPolicial.setSelection(0);
                        Dstipoprocedimentoacaopolicial = SpTipoProcedimentoRealAcaoPolicial.getItemAtPosition(0).toString();
                    }

                    switch (Dstipoprocedimentoacaopolicial) {

                        case "Selecione...":
                            SpTipoProcedimentoRealAcaoPolicial.setSelection(0);
                            break;
                        case "Auto de Apreensão":
                            SpTipoProcedimentoRealAcaoPolicial.setSelection(1);
                            break;
                        case "BOC":
                            SpTipoProcedimentoRealAcaoPolicial.setSelection(2);
                            break;
                        default:
                            SpTipoProcedimentoRealAcaoPolicial.setSelection(3);
                            break;
                    }
                }
            }
        });

        BtnTipoConducaoConduzidoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpTipoConducaoAcaoPolicial.setVisibility(View.VISIBLE);

                if (atualizar == 1) {
                    SpTipoConducaoAcaoPolicial.setVisibility(View.VISIBLE);

                    Dstipoconducaopolicial = conduzidoatualiza.getDsTipoConducaoAcaoPolicial();

                    if (Dstipoconducaopolicial == null) {
                        SpTipoConducaoAcaoPolicial.setSelection(0);
                        Dstipoconducaopolicial = SpTipoConducaoAcaoPolicial.getItemAtPosition(0).toString();
                    }

                    switch (Dstipoconducaopolicial) {

                        case "Selecione...":
                            SpTipoConducaoAcaoPolicial.setSelection(0);
                            break;
                        case "Condução para qualificação e oitiva":
                            SpTipoConducaoAcaoPolicial.setSelection(1);
                            break;
                        case "Prisão em Flagrante":
                            SpTipoConducaoAcaoPolicial.setSelection(2);
                            break;
                        case "Prisão em Temporária":
                            SpTipoConducaoAcaoPolicial.setSelection(3);
                            break;
                        case "Prisão em Preventiva":
                            SpTipoConducaoAcaoPolicial.setSelection(4);
                            break;
                        default:
                            SpTipoConducaoAcaoPolicial.setSelection(5);
                            break;
                    }
                } else if (atualizar == 2) {
                    SpTipoConducaoAcaoPolicial.setVisibility(View.VISIBLE);

                    Dstipoconducaopolicial = conduzidosacaopolicialsematualizar.getDsTipoConducaoAcaoPolicial();

                    if (Dstipoconducaopolicial == null) {
                        SpTipoConducaoAcaoPolicial.setSelection(0);
                        Dstipoconducaopolicial = SpTipoConducaoAcaoPolicial.getItemAtPosition(0).toString();
                    }

                    switch (Dstipoconducaopolicial) {

                        case "Selecione...":
                            SpTipoConducaoAcaoPolicial.setSelection(0);
                            break;
                        case "Condução para qualificação e oitiva":
                            SpTipoConducaoAcaoPolicial.setSelection(1);
                            break;
                        case "Prisão em Flagrante":
                            SpTipoConducaoAcaoPolicial.setSelection(2);
                            break;
                        case "Prisão em Temporária":
                            SpTipoConducaoAcaoPolicial.setSelection(3);
                            break;
                        case "Prisão em Preventiva":
                            SpTipoConducaoAcaoPolicial.setSelection(4);
                            break;
                        default:
                            SpTipoConducaoAcaoPolicial.setSelection(5);
                            break;
                    }
                }
            }
        });

        EdtIdadeConduzidoAcaoPolicial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int idade = 0;
                String recebido = "0" +s;

                idade = Integer.parseInt(recebido);

                if(idade < 18){
                    SpTipoProcedimentoRealAcaoPolicial.setVisibility(View.VISIBLE);
                    EdtAtoInfracionalConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    SpTipoConducaoAcaoPolicial.setVisibility(View.GONE);
                    TvTipoProcedimentoRelaConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    TvAtoInfracionalConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                    BtnTipoConducaoConduzidoAcaoPolicial.setVisibility(View.GONE);


                }else{
                    SpTipoProcedimentoRealAcaoPolicial.setVisibility(View.GONE);
                    EdtAtoInfracionalConduzidoAcaoPolicial.setVisibility(View.GONE);
                    TvTipoProcedimentoRelaConduzidoAcaoPolicial.setVisibility(View.GONE);
                    TvAtoInfracionalConduzidoAcaoPolicial.setVisibility(View.GONE);
                    SpTipoConducaoAcaoPolicial.setVisibility(View.VISIBLE);
                    BtnTipoConducaoConduzidoAcaoPolicial.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        BtnNovoConduzidoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conduzidoacaopolicial.setDsNomeConduzidoAcaoPolicial(EdtNomeConduzidoAcaoPolicial.getText().toString());
                conduzidoacaopolicial.setIdadeConduzidoAcaoPolicial(Integer.parseInt(EdtIdadeConduzidoAcaoPolicial.getText().toString()));
                conduzidoacaopolicial.setDsTipoProcedimentoAcaoPolcial(Dstipoprocedimentoacaopolicial);
                conduzidoacaopolicial.setDsAtoInfracionalAcaoPolicial(EdtAtoInfracionalConduzidoAcaoPolicial.getText().toString());
                conduzidoacaopolicial.setDsTipoConducaoAcaoPolicial(Dstipoconducaopolicial);
                conduzidoacaopolicial.setDsNomeJuizAcaoPolicial(EdtNomeJuizConduzidoAcaoPolicial.getText().toString());
                conduzidoacaopolicial.setDsComarcaAcaoPolicial(EdtComarcaConduzidoAcaoPolicial.getText().toString());

                if(jogada == 1){
                    codigoacaopolicial = fkatualizar;
                }else {
                    codigoacaopolicial = conduzidoacaopolicialDao.retornarCodigoAcaoPolicialSemParametro();
                }

                if (atualizar == 1) {
                    long certo = conduzidoacaopolicialDao.AtualizarConduzidoAcaoPolicial(conduzidoacaopolicial,conduzidoatualiza.getFkAcaoPolicial(),conduzidoatualiza.getId());
                    if (certo > 0) {
                        Toast.makeText(ConduzidosAcaoPolicialActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(ConduzidosAcaoPolicialActivity.this, "Erro ao Atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                       finish();
                }else  if(atualizar == 2) {
                    long certo = conduzidoacaopolicialDao.AtualizarConduzidoAcaoPolicial(conduzidoacaopolicial, conduzidosacaopolicialsematualizar.getFkAcaoPolicial(), conduzidosacaopolicialsematualizar.getId());
                    if (certo > 0) {
                        Toast.makeText(ConduzidosAcaoPolicialActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(ConduzidosAcaoPolicialActivity.this, "Erro ao Atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else {
                    String qualquer = RetornarNumeroTabelaCVLI();
                    long codigoconduzidoacaopolicial = conduzidoacaopolicialDao.cadastrarConduzidoAcaoPolicial(conduzidoacaopolicial, qualquer, codigoacaopolicial);
                    if (codigoconduzidoacaopolicial > 0) {
                        Toast.makeText(ConduzidosAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ConduzidosAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    EdtNomeConduzidoAcaoPolicial.setText("");
                    EdtIdadeConduzidoAcaoPolicial.setText("");
                    SpTipoProcedimentoRealAcaoPolicial.setSelection(0);
                    EdtAtoInfracionalConduzidoAcaoPolicial.setText("");
                    SpTipoConducaoAcaoPolicial.setSelection(0);
                    EdtNomeJuizConduzidoAcaoPolicial.setText("");
                    EdtComarcaConduzidoAcaoPolicial.setText("");
                    EdtNomeConduzidoAcaoPolicial.setFocusable(true);
                }

            }
        });

        BtnFinalizarConduzidoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conduzidoacaopolicial.setDsNomeConduzidoAcaoPolicial(EdtNomeConduzidoAcaoPolicial.getText().toString());
                conduzidoacaopolicial.setIdadeConduzidoAcaoPolicial(Integer.parseInt(EdtIdadeConduzidoAcaoPolicial.getText().toString()));
                conduzidoacaopolicial.setDsTipoProcedimentoAcaoPolcial(Dstipoprocedimentoacaopolicial);
                conduzidoacaopolicial.setDsAtoInfracionalAcaoPolicial(EdtAtoInfracionalConduzidoAcaoPolicial.getText().toString());
                conduzidoacaopolicial.setDsTipoConducaoAcaoPolicial(Dstipoconducaopolicial);
                conduzidoacaopolicial.setDsNomeJuizAcaoPolicial(EdtNomeJuizConduzidoAcaoPolicial.getText().toString());
                conduzidoacaopolicial.setDsComarcaAcaoPolicial(EdtComarcaConduzidoAcaoPolicial.getText().toString());

                if(jogada == 1){
                    codigoacaopolicial = fkatualizar;
                }else {
                    codigoacaopolicial = conduzidoacaopolicialDao.retornarCodigoAcaoPolicialSemParametro();
                }

                String valorqualquer = RetornarNumeroTabelaCVLI();

                long certo = conduzidoacaopolicialDao.cadastrarConduzidoAcaoPolicial(conduzidoacaopolicial, valorqualquer, codigoacaopolicial);
                if (certo > 0) {
                    Toast.makeText(ConduzidosAcaoPolicialActivity.this, "Cadastrardo com Sucesso!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConduzidosAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doconduzidoacaopolicial, menu);

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

    public String RetornarNumeroTabelaCVLI(){

        int id = usuarioDao.user.getId();
        String valor = String.valueOf(id);
        Random gerador = new Random();
        Calendar cal = Calendar.getInstance();
        valor = gerador.nextInt() +valor + cal.getTimeInMillis();
        return valor;
    }

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
