package com.iris.ramilton.iris;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import java.text.SimpleDateFormat;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.iris.ramilton.iris.dao.AutoriaDao;
import com.iris.ramilton.iris.modelo.Autoria;
import com.iris.ramilton.iris.modelo.Gerarnumeros;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

public class AutoriaActivity extends AppCompatActivity {

    private RadioGroup radioGroup, RgInstrumentoCrimeAutoria;
    private RadioButton radiobuttonescolhido, radiobuttonescolhidoInstrumento, RbAutoriaIdentificado, RbAutoriaNIdentificado, RbAutoriaSuspeita;
    private Spinner SpStatusAutoria, SpSexoAutoria, SpNacionalidadeAutoria, SpCurtisAutoria, SpResponsavelPrisaoAutoria;
    private EditText EdtidadeAutoria, EdtcpfAutoria, EdtNomeAutoria, EdtRGAutoria, EdtOrgExpAutoria, EdtNomeMaeAutoria, EdtNomePaiAutoria, EdtNaturalidadeAutoria, EdtVulgoAutoria;
    private Button BtnQualificacaoAutoria,BtnStatusAutoria, BtnIncluirAutoria, BtnFinalizarAutoria, BtnIncluirPresoAutoria, BtnFinalizarPresoAutoria, BtnAutoria;
    private TextView TvNomeAutoria, TvRGAutoria, TvOrgaoExpAutoria, TvSexoAutoria, TvCPFAutoria, TvIdadeAutoria, TvCurtisAutoria, TvNomeMaeAutoria, TvNomePaiAutoria, TvNascionalidade, TvNaturalidade, TvVulgoAutoria;
    private ConstraintLayout ClAutoriaPreso;
    private Spinner naturezaPrisao;
    private EditText dataPrisao, horarioPrisao, EdtLocalPrisao;
    private String StatusAutoria, SexoAutoria, NacionalidadeAutoria, CurtisAutoria, ResponsavelPrisaoAutoria, naturezaprisaoAutoria;
    private int AutoriaDefinida, AutoriaNDefinida, AutoriaSuspeita;
    private AutoriaDao autoriaDao, autoriasematualizarDao;
    private Autoria autoria, autoriasematualizar;
    private Autoria autoriaatualiza = null;
    private int atualiza = 0, fkatualizar, jogada = 0, codigocvli, codigosematualizar;
    private Gerarnumeros gerarnumeros;

    Calendar cAutoriaPresa;
    DatePickerDialog dpdAutoriaPresa;
    DatePickerDialog.OnDateSetListener date;

    Timer tAutoriaPresa;
    TimePickerDialog tpdAutoriaPresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoria);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        ClAutoriaPreso = (ConstraintLayout) findViewById(R.id.clAutoriaPresa);
        radioGroup = (RadioGroup) findViewById(R.id.rgIndefinidaDefinidaAutoria);
        EdtidadeAutoria = (EditText) findViewById(R.id.edtIdadeAutoria);
        EdtcpfAutoria = (EditText) findViewById(R.id.edtCPFAutoria);
        EdtNomeAutoria = (EditText) findViewById(R.id.edtNomeAutoria);
        EdtRGAutoria = (EditText) findViewById(R.id.edtRGAutoria);
        EdtOrgExpAutoria = (EditText) findViewById(R.id.edtOrgaoExpAutoria);
        EdtNomeMaeAutoria = (EditText) findViewById(R.id.edtNomeMaeAutoria);
        EdtNomePaiAutoria = (EditText) findViewById(R.id.edtNomePaiAutoria);
        SpSexoAutoria = (Spinner) findViewById(R.id.spinnerSexoAutoria);
        SpNacionalidadeAutoria = (Spinner) findViewById(R.id.spinnerNacionalidadeAutoria);
        SpStatusAutoria = (Spinner) findViewById(R.id.spinnerStatusAutoria);
        SpCurtisAutoria = (Spinner) findViewById(R.id.spinnerCutisAutoria);
        BtnQualificacaoAutoria = (Button) findViewById(R.id.btnQualificacaoAutoria);
        BtnStatusAutoria = (Button) findViewById(R.id.btnEstatusAutoria);
        BtnIncluirAutoria = (Button) findViewById(R.id.btnIncluirAutoria);
        BtnFinalizarAutoria = (Button) findViewById(R.id.btnIncluirFinalizarAutoria);
        BtnIncluirPresoAutoria = (Button) findViewById(R.id.btnCadastroAutoriaPrisao);
        BtnFinalizarPresoAutoria = (Button) findViewById(R.id.btnFinalizarPreso);
        BtnAutoria = (Button) findViewById(R.id.btnAutoria);
        TvNomeAutoria = (TextView) findViewById(R.id.tvNomeAutoria);
        TvRGAutoria = (TextView) findViewById(R.id.tvRGAutoria);
        TvOrgaoExpAutoria = (TextView) findViewById(R.id.tvOrgaoExpAutoria);
        TvSexoAutoria = (TextView) findViewById(R.id.tvSexoAutoria);
        TvCPFAutoria = (TextView) findViewById(R.id.tvCPFAutoria);
        TvIdadeAutoria = (TextView) findViewById(R.id.tvIdadeAutoria);
        TvCurtisAutoria = (TextView) findViewById(R.id.tvCurtisAutoria);
        TvNomeMaeAutoria = (TextView) findViewById(R.id.tvNomeMaeAutoria);
        TvNomePaiAutoria = (TextView) findViewById(R.id.tvNomePaiAutoria);
        TvNascionalidade = (TextView) findViewById(R.id.tvNascionalidadeAutoria);
        TvNaturalidade = (TextView) findViewById(R.id.tvNaturalidadeAutoria);
        EdtLocalPrisao = (EditText) findViewById(R.id.edtLocalPrisao);
        RbAutoriaIdentificado = (RadioButton) findViewById(R.id.rbDefinidaAutoria);
        RbAutoriaNIdentificado = (RadioButton) findViewById(R.id.rbIndefinidaAutoria);
        RbAutoriaSuspeita = (RadioButton) findViewById(R.id.rbApenasSuspeito);
        dataPrisao = (EditText) findViewById(R.id.edtDataPrisao);
        horarioPrisao = (EditText) findViewById(R.id.edtHorarioPrisao);
        naturezaPrisao = (Spinner) findViewById(R.id.spinnerNaturezaPrisao);
        SpResponsavelPrisaoAutoria = (Spinner) findViewById(R.id.spinnerResponsavelPrisaoAutoria);
        EdtNaturalidadeAutoria = (EditText) findViewById(R.id.edtNaturalidadeAutoria);
        EdtVulgoAutoria = (EditText) findViewById(R.id.edtVulgoAutoria);
        TvVulgoAutoria = (TextView) findViewById(R.id.tvVulgoAutoria);

        autoriasematualizar = new Autoria();
        autoriasematualizarDao = new AutoriaDao(this);

        EdtidadeAutoria.setText("0");


        gerarnumeros = new Gerarnumeros();
        Intent it = getIntent();
        if(it.hasExtra("autoriaAtualizar")){
            autoriaatualiza = (Autoria) it.getSerializableExtra("autoriaAtualizar");

            atualiza = 1;

            EdtidadeAutoria.setText(Integer.toString(autoriaatualiza.getDsIdadeAutoria()));
            EdtcpfAutoria.setText(autoriaatualiza.getDsCPFAutoria());
            EdtNomeAutoria.setText(autoriaatualiza.getDsNomeAutoria());
            EdtRGAutoria.setText(autoriaatualiza.getDsRGAutoria());
            EdtOrgExpAutoria.setText(autoriaatualiza.getDsOrgaoExpRGAutoria());
            EdtNomeMaeAutoria.setText(autoriaatualiza.getDsNomeMaeAutoria());
            EdtNomePaiAutoria.setText(autoriaatualiza.getDsNomePaiAutoria());
            EdtLocalPrisao.setText(autoriaatualiza.getDsLocalPrisaoAutoria());
            horarioPrisao.setText(autoriaatualiza.getHsHorarioPrisaoAutoria());
            dataPrisao.setText(autoriaatualiza.getDtPrisaoAutoria());
            EdtNaturalidadeAutoria.setText(autoriaatualiza.getDsNaturalidadeAutoria());
            EdtVulgoAutoria.setText(autoriaatualiza.getDsVulgoAutoria());

            if(autoriaatualiza.getCbkAutoriaDefinida() == 1){
                RbAutoriaIdentificado.setChecked(true);
                RbAutoriaNIdentificado.setChecked(false);
                RbAutoriaSuspeita.setChecked(false);
            }else{
                RbAutoriaIdentificado.setChecked(false);
            }

            if(autoriaatualiza.getCbkAutoriaNDefinida() == 1){
                RbAutoriaNIdentificado.setChecked(true);
                RbAutoriaIdentificado.setChecked(false);
                RbAutoriaSuspeita.setChecked(false);
            }else{
                RbAutoriaNIdentificado.setChecked(false);
            }

            if(autoriaatualiza.getCbkAutoriaSuspeita() == 1){
                RbAutoriaSuspeita.setChecked(true);
                RbAutoriaNIdentificado.setChecked(false);
                RbAutoriaIdentificado.setChecked(false);
            }else{
                RbAutoriaSuspeita.setChecked(false);
            }

        }else if(it.hasExtra("fkautoriaAtualizar")){
            fkatualizar = (int) it.getSerializableExtra("fkautoriaAtualizar");
            jogada = 1;
        }else if(it.hasExtra("autoriaematualizar")){
            codigosematualizar = (int) it.getSerializableExtra("autoriaematualizar");

            autoriasematualizar = autoriasematualizarDao.retornarAutoriaObj(codigosematualizar);

            atualiza = 2;

            EdtidadeAutoria.setText(Integer.toString(autoriasematualizar.getDsIdadeAutoria()));
            EdtcpfAutoria.setText(autoriasematualizar.getDsCPFAutoria());
            EdtNomeAutoria.setText(autoriasematualizar.getDsNomeAutoria());
            EdtRGAutoria.setText(autoriasematualizar.getDsRGAutoria());
            EdtOrgExpAutoria.setText(autoriasematualizar.getDsOrgaoExpRGAutoria());
            EdtNomeMaeAutoria.setText(autoriasematualizar.getDsNomeMaeAutoria());
            EdtNomePaiAutoria.setText(autoriasematualizar.getDsNomePaiAutoria());
            EdtLocalPrisao.setText(autoriasematualizar.getDsLocalPrisaoAutoria());
            horarioPrisao.setText(autoriasematualizar.getHsHorarioPrisaoAutoria());
            dataPrisao.setText(autoriasematualizar.getDtPrisaoAutoria());
            EdtNaturalidadeAutoria.setText(autoriasematualizar.getDsNaturalidadeAutoria());
            EdtVulgoAutoria.setText(autoriasematualizar.getDsVulgoAutoria());

            if(autoriasematualizar.getCbkAutoriaDefinida() == 1){
                RbAutoriaIdentificado.setChecked(true);
                RbAutoriaNIdentificado.setChecked(false);
                RbAutoriaSuspeita.setChecked(false);
            }else{
                RbAutoriaIdentificado.setChecked(false);
            }

            if(autoriasematualizar.getCbkAutoriaNDefinida() == 1){
                RbAutoriaNIdentificado.setChecked(true);
                RbAutoriaIdentificado.setChecked(false);
                RbAutoriaSuspeita.setChecked(false);
            }else{
                RbAutoriaNIdentificado.setChecked(false);
            }

            if(autoriasematualizar.getCbkAutoriaSuspeita() == 1){
                RbAutoriaSuspeita.setChecked(true);
                RbAutoriaNIdentificado.setChecked(false);
                RbAutoriaIdentificado.setChecked(false);
            }else{
                RbAutoriaSuspeita.setChecked(false);
            }
        }

        autoria = new Autoria();
        autoriaDao = new AutoriaDao(this);

        if(atualiza == 1){
            BtnFinalizarPresoAutoria.setVisibility(View.INVISIBLE);
            BtnFinalizarAutoria.setVisibility(View.INVISIBLE);
            BtnIncluirPresoAutoria.setText("Concluir");
            BtnIncluirAutoria.setText("Concluir");
        }else if(atualiza == 2){
            BtnFinalizarPresoAutoria.setVisibility(View.INVISIBLE);
            BtnFinalizarAutoria.setVisibility(View.INVISIBLE);
            BtnIncluirPresoAutoria.setText("Concluir");
            BtnIncluirAutoria.setText("Concluir");
        }else{
            BtnFinalizarPresoAutoria.setVisibility(View.VISIBLE);
            BtnIncluirPresoAutoria.setVisibility(View.VISIBLE);
            BtnFinalizarAutoria.setVisibility(View.VISIBLE);
            BtnIncluirAutoria.setVisibility(View.VISIBLE);
        }

        SimpleMaskFormatter mascaraCPFAutoria = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCPFAutoria = new MaskTextWatcher(EdtcpfAutoria,mascaraCPFAutoria);
        EdtcpfAutoria.addTextChangedListener(maskCPFAutoria);

        ArrayAdapter adaptadorSpNacionalidade = ArrayAdapter.createFromResource(this,R.array.Nacionalidade,android.R.layout.simple_spinner_item);
        SpNacionalidadeAutoria.setAdapter(adaptadorSpNacionalidade);

        ArrayAdapter adaptadorStatusAutoria = ArrayAdapter.createFromResource(this,R.array.StatusAutoria,android.R.layout.simple_spinner_item);
        SpStatusAutoria.setAdapter(adaptadorStatusAutoria);

        ArrayAdapter adaptadorSexoAutoria = ArrayAdapter.createFromResource(this,R.array.Sexo,android.R.layout.simple_spinner_item);
        SpSexoAutoria.setAdapter(adaptadorSexoAutoria);

        ArrayAdapter adaptadorCurtisAutoria = ArrayAdapter.createFromResource(this, R.array.Curtis, android.R.layout.simple_spinner_item);
        SpCurtisAutoria.setAdapter(adaptadorCurtisAutoria);

        ArrayAdapter adaptadorResponsavelPrisaoAutoria = ArrayAdapter.createFromResource(this,R.array.QuemPrendeu,android.R.layout.simple_spinner_item);
        SpResponsavelPrisaoAutoria.setAdapter(adaptadorResponsavelPrisaoAutoria);

        cAutoriaPresa = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cAutoriaPresa.set(Calendar.YEAR, year);
                cAutoriaPresa.set(Calendar.MONTH, month);
                cAutoriaPresa.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        BtnAutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(atualiza == 1){
                    radioGroup.setVisibility(View.VISIBLE);
                    BtnQualificacaoAutoria.setVisibility(View.VISIBLE);
                }else if(atualiza == 2){
                    radioGroup.setVisibility(View.VISIBLE);
                    BtnQualificacaoAutoria.setVisibility(View.VISIBLE);
                }else{
                    radioGroup.setVisibility(View.VISIBLE);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                radiobuttonescolhido = (RadioButton) radioGroup.findViewById(checkedId);
                int indece = radioGroup.indexOfChild(radiobuttonescolhido);

                if (indece == 1) {
                    tornarCamposInvisiveis();
                    AutoriaNDefinida = 1;
                    AutoriaDefinida = 0;
                    AutoriaSuspeita = 0;

                }else if(indece == 0){
                    AutoriaNDefinida = 0;
                    AutoriaDefinida = 1;
                    AutoriaSuspeita = 0;
                    BtnQualificacaoAutoria.setVisibility(View.VISIBLE);
                }else{
                    AutoriaNDefinida = 0;
                    AutoriaDefinida = 0;
                    AutoriaSuspeita = 1;
                    BtnQualificacaoAutoria.setVisibility(View.VISIBLE);
                }

            }
        });

        RbAutoriaIdentificado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(atualiza == 1){
                    tornarCamposVisiveis();
                    EdtNomeAutoria.setFocusable(true);
                    BtnIncluirAutoria.setVisibility(View.INVISIBLE);
                    BtnFinalizarAutoria.setVisibility(View.INVISIBLE);
                    BtnQualificacaoAutoria.setVisibility(View.VISIBLE);
                    BtnStatusAutoria.setVisibility(View.VISIBLE);
                    ClAutoriaPreso.setVisibility(View.VISIBLE);
                    String Dssexoautoria = autoriaatualiza.getDsSexoAutoria();

                    if (Dssexoautoria == null) {
                        SpSexoAutoria.setSelection(0);
                        Dssexoautoria = SpSexoAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dssexoautoria) {
                        case "Selecione...":
                            SpSexoAutoria.setSelection(0);
                            break;
                        case "Masculino":
                            SpSexoAutoria.setSelection(1);
                            break;
                        default:
                            SpSexoAutoria.setSelection(2);
                            break;
                    }

                    String DsNacionalidadeautoria = autoriaatualiza.getDsNascionalidadeAutoria();

                    if (DsNacionalidadeautoria == null) {
                        SpNacionalidadeAutoria.setSelection(0);
                        DsNacionalidadeautoria = SpNacionalidadeAutoria.getItemAtPosition(0).toString();
                    }

                    switch (DsNacionalidadeautoria) {
                        case "Selecione...":
                            SpNacionalidadeAutoria.setSelection(0);
                            break;
                        case "Brasileiro":
                            SpNacionalidadeAutoria.setSelection(1);
                            break;
                        default:
                            SpNacionalidadeAutoria.setSelection(2);
                            break;
                    }

                    String Dscurtisautoria = autoriaatualiza.getDsCurtisAutoria();

                    if (Dscurtisautoria == null) {
                        SpCurtisAutoria.setSelection(0);
                        Dscurtisautoria = SpCurtisAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dscurtisautoria) {
                        case "Selecione...":
                            SpCurtisAutoria.setSelection(0);
                            break;
                        case "Branco":
                            SpCurtisAutoria.setSelection(1);
                            break;
                        default:
                            SpCurtisAutoria.setSelection(2);
                            break;
                    }

                    String Dsstatusautoria = autoriaatualiza.getDsCondicaoAutoria();

                    SpStatusAutoria.setVisibility(View.VISIBLE);

                    if(Dsstatusautoria == null){
                        SpStatusAutoria.setSelection(0);
                        Dsstatusautoria = SpStatusAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dsstatusautoria){
                        case "Selecione...":
                            SpStatusAutoria.setSelection(0);
                            break;
                        case "Autor em Liberdade":
                            SpStatusAutoria.setSelection(1);
                            break;
                        case "Autor Foragido":
                            SpStatusAutoria.setSelection(2);
                            break;
                        case "Autor Preso":
                            SpStatusAutoria.setSelection(3);
                            break;
                        default:
                            SpStatusAutoria.setSelection(4);
                            break;
                    }

                    String Dsnaturezaprisao = autoriaatualiza.getDsNaturezaPrisaoAutoria();

                    if(Dsnaturezaprisao == null){
                        naturezaPrisao.setSelection(0);
                        Dsnaturezaprisao = naturezaPrisao.getItemAtPosition(0).toString();
                    }

                    switch (Dsnaturezaprisao){
                        case "Selecione...":
                            naturezaPrisao.setSelection(0);
                            break;
                        case "Preventiva":
                            naturezaPrisao.setSelection(1);
                            break;
                        case "Temporária":
                            naturezaPrisao.setSelection(2);
                            break;
                        default:
                            naturezaPrisao.setSelection(3);
                            break;
                    }

                    String Dsresponsavelprisaoautoria = autoriaatualiza.getDsResponsavelPrisaoAutoria();

                    if(Dsresponsavelprisaoautoria == null){
                        SpResponsavelPrisaoAutoria.setSelection(0);
                        Dsresponsavelprisaoautoria = SpResponsavelPrisaoAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dsresponsavelprisaoautoria){
                        case "Selecione...":
                            SpResponsavelPrisaoAutoria.setSelection(0);
                            break;
                        case "Polícia Militar":
                            SpResponsavelPrisaoAutoria.setSelection(1);
                            break;
                        case "Polícia Civil":
                            SpResponsavelPrisaoAutoria.setSelection(2);
                            break;
                        case "Polícia Federal":
                            SpResponsavelPrisaoAutoria.setSelection(3);
                            break;
                        case "Polícia Rodoviária Federal":
                            SpResponsavelPrisaoAutoria.setSelection(4);
                            break;
                        case "Guarda Municipal":
                            SpResponsavelPrisaoAutoria.setSelection(5);
                            break;
                        case "Pessoa do Povo":
                            SpResponsavelPrisaoAutoria.setSelection(6);
                            break;
                        default:
                            SpResponsavelPrisaoAutoria.setSelection(7);
                            break;
                    }
                }else if(atualiza == 2){
                    tornarCamposVisiveis();
                    EdtNomeAutoria.setFocusable(true);
                    BtnIncluirAutoria.setVisibility(View.INVISIBLE);
                    BtnFinalizarAutoria.setVisibility(View.INVISIBLE);
                    BtnQualificacaoAutoria.setVisibility(View.VISIBLE);
                    BtnStatusAutoria.setVisibility(View.VISIBLE);
                    ClAutoriaPreso.setVisibility(View.VISIBLE);

                    String Dssexoautoria = autoriasematualizar.getDsSexoAutoria();

                    if (Dssexoautoria == null) {
                        SpSexoAutoria.setSelection(0);
                        Dssexoautoria = SpSexoAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dssexoautoria) {
                        case "Selecione...":
                            SpSexoAutoria.setSelection(0);
                            break;
                        case "Masculino":
                            SpSexoAutoria.setSelection(1);
                            break;
                        default:
                            SpSexoAutoria.setSelection(2);
                            break;
                    }

                    String DsNacionalidadeautoria = autoriasematualizar.getDsNascionalidadeAutoria();

                    if (DsNacionalidadeautoria == null) {
                        SpNacionalidadeAutoria.setSelection(0);
                        DsNacionalidadeautoria = SpNacionalidadeAutoria.getItemAtPosition(0).toString();
                    }

                    switch (DsNacionalidadeautoria) {
                        case "Selecione...":
                            SpNacionalidadeAutoria.setSelection(0);
                            break;
                        case "Brasileiro":
                            SpNacionalidadeAutoria.setSelection(1);
                            break;
                        default:
                            SpNacionalidadeAutoria.setSelection(2);
                            break;
                    }

                    String Dscurtisautoria = autoriasematualizar.getDsCurtisAutoria();

                    if (Dscurtisautoria == null) {
                        SpCurtisAutoria.setSelection(0);
                        Dscurtisautoria = SpCurtisAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dscurtisautoria) {
                        case "Selecione...":
                            SpCurtisAutoria.setSelection(0);
                            break;
                        case "Branco":
                            SpCurtisAutoria.setSelection(1);
                            break;
                        default:
                            SpCurtisAutoria.setSelection(2);
                            break;
                    }

                    String Dsstatusautoria = autoriasematualizar.getDsCondicaoAutoria();

                    SpStatusAutoria.setVisibility(View.VISIBLE);

                    if(Dsstatusautoria == null){
                        SpStatusAutoria.setSelection(0);
                        Dsstatusautoria = SpStatusAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dsstatusautoria){
                        case "Selecione...":
                            SpStatusAutoria.setSelection(0);
                            break;
                        case "Autor em Liberdade":
                            SpStatusAutoria.setSelection(1);
                            break;
                        case "Autor Foragido":
                            SpStatusAutoria.setSelection(2);
                            break;
                        case "Autor Preso":
                            SpStatusAutoria.setSelection(3);
                            break;
                        default:
                            SpStatusAutoria.setSelection(4);
                            break;
                    }

                    String Dsnaturezaprisao = autoriasematualizar.getDsNaturezaPrisaoAutoria();

                    if(Dsnaturezaprisao == null){
                        naturezaPrisao.setSelection(0);
                        Dsnaturezaprisao = naturezaPrisao.getItemAtPosition(0).toString();
                    }

                    switch (Dsnaturezaprisao){
                        case "Selecione...":
                            naturezaPrisao.setSelection(0);
                            break;
                        case "Preventiva":
                            naturezaPrisao.setSelection(1);
                            break;
                        case "Temporária":
                            naturezaPrisao.setSelection(2);
                            break;
                        default:
                            naturezaPrisao.setSelection(3);
                            break;
                    }

                    String Dsresponsavelprisaoautoria = autoriasematualizar.getDsResponsavelPrisaoAutoria();

                    if(Dsresponsavelprisaoautoria == null){
                        SpResponsavelPrisaoAutoria.setSelection(0);
                        Dsresponsavelprisaoautoria = SpResponsavelPrisaoAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dsresponsavelprisaoautoria){
                        case "Selecione...":
                            SpResponsavelPrisaoAutoria.setSelection(0);
                            break;
                        case "Polícia Militar":
                            SpResponsavelPrisaoAutoria.setSelection(1);
                            break;
                        case "Polícia Civil":
                            SpResponsavelPrisaoAutoria.setSelection(2);
                            break;
                        case "Polícia Federal":
                            SpResponsavelPrisaoAutoria.setSelection(3);
                            break;
                        case "Polícia Rodoviária Federal":
                            SpResponsavelPrisaoAutoria.setSelection(4);
                            break;
                        case "Guarda Municipal":
                            SpResponsavelPrisaoAutoria.setSelection(5);
                            break;
                        case "Pessoa do Povo":
                            SpResponsavelPrisaoAutoria.setSelection(6);
                            break;
                        default:
                            SpResponsavelPrisaoAutoria.setSelection(7);
                            break;
                    }
                }
            }
        });

        BtnQualificacaoAutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(atualiza == 1) {
                    tornarCamposVisiveis();
                    BtnStatusAutoria.setVisibility(View.VISIBLE);
                    String Dssexoautoria = autoriaatualiza.getDsSexoAutoria();

                    if (Dssexoautoria == null) {
                        SpSexoAutoria.setSelection(0);
                        Dssexoautoria = SpSexoAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dssexoautoria) {
                        case "Selecione...":
                            SpSexoAutoria.setSelection(0);
                            break;
                        case "Masculino":
                            SpSexoAutoria.setSelection(1);
                            break;
                        default:
                            SpSexoAutoria.setSelection(2);
                            break;
                    }

                    String DsNacionalidadeautoria = autoriaatualiza.getDsNascionalidadeAutoria();

                    if (DsNacionalidadeautoria == null) {
                        SpNacionalidadeAutoria.setSelection(0);
                        DsNacionalidadeautoria = SpNacionalidadeAutoria.getItemAtPosition(0).toString();
                    }

                    switch (DsNacionalidadeautoria) {
                        case "Selecione...":
                            SpNacionalidadeAutoria.setSelection(0);
                            break;
                        case "Brasileiro":
                            SpNacionalidadeAutoria.setSelection(1);
                            break;
                        default:
                            SpNacionalidadeAutoria.setSelection(2);
                            break;
                    }

                    String Dscurtisautoria = autoriaatualiza.getDsCurtisAutoria();

                    if (Dscurtisautoria == null) {
                        SpCurtisAutoria.setSelection(0);
                        Dscurtisautoria = SpCurtisAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dscurtisautoria) {
                        case "Selecione...":
                            SpCurtisAutoria.setSelection(0);
                            break;
                        case "Branco":
                            SpCurtisAutoria.setSelection(1);
                            break;
                        default:
                            SpCurtisAutoria.setSelection(2);
                            break;
                    }
                }else if(atualiza == 2){
                    tornarCamposVisiveis();
                    BtnStatusAutoria.setVisibility(View.VISIBLE);
                    String Dssexoautoria = autoriasematualizar.getDsSexoAutoria();

                    if (Dssexoautoria == null) {
                        SpSexoAutoria.setSelection(0);
                        Dssexoautoria = SpSexoAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dssexoautoria) {
                        case "Selecione...":
                            SpSexoAutoria.setSelection(0);
                            break;
                        case "Masculino":
                            SpSexoAutoria.setSelection(1);
                            break;
                        default:
                            SpSexoAutoria.setSelection(2);
                            break;
                    }

                    String DsNacionalidadeautoria = autoriasematualizar.getDsNascionalidadeAutoria();

                    if (DsNacionalidadeautoria == null) {
                        SpNacionalidadeAutoria.setSelection(0);
                        DsNacionalidadeautoria = SpNacionalidadeAutoria.getItemAtPosition(0).toString();
                    }

                    switch (DsNacionalidadeautoria) {
                        case "Selecione...":
                            SpNacionalidadeAutoria.setSelection(0);
                            break;
                        case "Brasileiro":
                            SpNacionalidadeAutoria.setSelection(1);
                            break;
                        default:
                            SpNacionalidadeAutoria.setSelection(2);
                            break;
                    }

                    String Dscurtisautoria = autoriasematualizar.getDsCurtisAutoria();

                    if (Dscurtisautoria == null) {
                        SpCurtisAutoria.setSelection(0);
                        Dscurtisautoria = SpCurtisAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dscurtisautoria) {
                        case "Selecione...":
                            SpCurtisAutoria.setSelection(0);
                            break;
                        case "Branco":
                            SpCurtisAutoria.setSelection(1);
                            break;
                        default:
                            SpCurtisAutoria.setSelection(2);
                            break;
                    }
                }else{
                    EdtNomeAutoria.setFocusable(true);
                    BtnStatusAutoria.setVisibility(View.VISIBLE);
                    tornarCamposVisiveis();
                }
            }
        });

        EdtNomeAutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnStatusAutoria.setVisibility(View.VISIBLE);
            }
        });

        BtnStatusAutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(atualiza == 1){
                    String Dsstatusautoria = autoriaatualiza.getDsCondicaoAutoria();

                    SpStatusAutoria.setVisibility(View.VISIBLE);

                    if(Dsstatusautoria == null){
                        SpStatusAutoria.setSelection(0);
                        Dsstatusautoria = SpStatusAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dsstatusautoria){
                        case "Selecione...":
                            SpStatusAutoria.setSelection(0);
                            break;
                        case "Autor em Liberdade":
                            SpStatusAutoria.setSelection(1);
                            break;
                        case "Autor Foragido":
                            SpStatusAutoria.setSelection(2);
                            break;
                        case "Autor Preso":
                            SpStatusAutoria.setSelection(3);
                            break;
                        default:
                            SpStatusAutoria.setSelection(4);
                            break;
                    }

                    String Dsnaturezaprisao = autoriaatualiza.getDsNaturezaPrisaoAutoria();

                    if(Dsnaturezaprisao == null){
                        naturezaPrisao.setSelection(0);
                        Dsnaturezaprisao = naturezaPrisao.getItemAtPosition(0).toString();
                    }

                    switch (Dsnaturezaprisao){
                        case "Selecione...":
                            naturezaPrisao.setSelection(0);
                            break;
                        case "Preventiva":
                            naturezaPrisao.setSelection(1);
                            break;
                        case "Temporária":
                            naturezaPrisao.setSelection(2);
                            break;
                        default:
                            naturezaPrisao.setSelection(3);
                            break;
                    }

                    String Dsresponsavelprisaoautoria = autoriaatualiza.getDsResponsavelPrisaoAutoria();

                    if(Dsresponsavelprisaoautoria == null){
                        SpResponsavelPrisaoAutoria.setSelection(0);
                        Dsresponsavelprisaoautoria = SpResponsavelPrisaoAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dsresponsavelprisaoautoria){
                        case "Selecione...":
                            SpResponsavelPrisaoAutoria.setSelection(0);
                            break;
                        case "Polícia Militar":
                            SpResponsavelPrisaoAutoria.setSelection(1);
                            break;
                        case "Polícia Civil":
                            SpResponsavelPrisaoAutoria.setSelection(2);
                            break;
                        case "Polícia Federal":
                            SpResponsavelPrisaoAutoria.setSelection(3);
                            break;
                        case "Polícia Rodoviária Federal":
                            SpResponsavelPrisaoAutoria.setSelection(4);
                            break;
                        case "Guarda Municipal":
                            SpResponsavelPrisaoAutoria.setSelection(5);
                            break;
                        case "Pessoa do Povo":
                            SpResponsavelPrisaoAutoria.setSelection(6);
                            break;
                        default:
                            SpResponsavelPrisaoAutoria.setSelection(7);
                            break;
                    }

                }else if(atualiza == 2){
                    String Dsstatusautoria = autoriasematualizar.getDsCondicaoAutoria();

                    SpStatusAutoria.setVisibility(View.VISIBLE);

                    if(Dsstatusautoria == null){
                        SpStatusAutoria.setSelection(0);
                        Dsstatusautoria = SpStatusAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dsstatusautoria){
                        case "Selecione...":
                            SpStatusAutoria.setSelection(0);
                            break;
                        case "Autor em Liberdade":
                            SpStatusAutoria.setSelection(1);
                            break;
                        case "Autor Foragido":
                            SpStatusAutoria.setSelection(2);
                            break;
                        case "Autor Preso":
                            SpStatusAutoria.setSelection(3);
                            break;
                        default:
                            SpStatusAutoria.setSelection(4);
                            break;
                    }

                    String Dsnaturezaprisao = autoriasematualizar.getDsNaturezaPrisaoAutoria();

                    if(Dsnaturezaprisao == null){
                        naturezaPrisao.setSelection(0);
                        Dsnaturezaprisao = naturezaPrisao.getItemAtPosition(0).toString();
                    }

                    switch (Dsnaturezaprisao){
                        case "Selecione...":
                            naturezaPrisao.setSelection(0);
                            break;
                        case "Preventiva":
                            naturezaPrisao.setSelection(1);
                            break;
                        case "Temporária":
                            naturezaPrisao.setSelection(2);
                            break;
                        default:
                            naturezaPrisao.setSelection(3);
                            break;
                    }

                    String Dsresponsavelprisaoautoria = autoriasematualizar.getDsResponsavelPrisaoAutoria();

                    if(Dsresponsavelprisaoautoria == null){
                        SpResponsavelPrisaoAutoria.setSelection(0);
                        Dsresponsavelprisaoautoria = SpResponsavelPrisaoAutoria.getItemAtPosition(0).toString();
                    }

                    switch (Dsresponsavelprisaoautoria){
                        case "Selecione...":
                            SpResponsavelPrisaoAutoria.setSelection(0);
                            break;
                        case "Polícia Militar":
                            SpResponsavelPrisaoAutoria.setSelection(1);
                            break;
                        case "Polícia Civil":
                            SpResponsavelPrisaoAutoria.setSelection(2);
                            break;
                        case "Polícia Federal":
                            SpResponsavelPrisaoAutoria.setSelection(3);
                            break;
                        case "Polícia Rodoviária Federal":
                            SpResponsavelPrisaoAutoria.setSelection(4);
                            break;
                        case "Guarda Municipal":
                            SpResponsavelPrisaoAutoria.setSelection(5);
                            break;
                        case "Pessoa do Povo":
                            SpResponsavelPrisaoAutoria.setSelection(6);
                            break;
                        default:
                            SpResponsavelPrisaoAutoria.setSelection(7);
                            break;
                    }
                }else{
                    SpStatusAutoria.setVisibility(View.VISIBLE);
                }
            }
        });

        SpStatusAutoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                StatusAutoria = SpStatusAutoria.getItemAtPosition(position).toString();
                if(StatusAutoria.equals("Autor Preso")){
                    ClAutoriaPreso.setVisibility(View.VISIBLE);
                    BtnIncluirAutoria.setVisibility(View.INVISIBLE);
                    BtnFinalizarAutoria.setVisibility(View.INVISIBLE);

                }else{
                    ClAutoriaPreso.setVisibility(View.INVISIBLE);
                    BtnIncluirAutoria.setVisibility(View.VISIBLE);
                    BtnFinalizarAutoria.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        naturezaPrisao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                naturezaprisaoAutoria = naturezaPrisao.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpSexoAutoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SexoAutoria = SpSexoAutoria.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpNacionalidadeAutoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                NacionalidadeAutoria = SpNacionalidadeAutoria.getItemAtPosition(position).toString();

                if(SpNacionalidadeAutoria.equals("Estrangeiro")){
                    EdtNaturalidadeAutoria.setEnabled(false);
                }
                BtnStatusAutoria.setFocusable(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpCurtisAutoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CurtisAutoria = SpCurtisAutoria.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpResponsavelPrisaoAutoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ResponsavelPrisaoAutoria = SpResponsavelPrisaoAutoria.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter adaptadorNaturezaPrisao = ArrayAdapter.createFromResource(this,R.array.NaturezaPrisao,android.R.layout.simple_spinner_item);
        naturezaPrisao.setAdapter(adaptadorNaturezaPrisao);


        dataPrisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AutoriaActivity.this, date, cAutoriaPresa
                        .get(Calendar.YEAR), cAutoriaPresa.get(Calendar.MONTH),
                        cAutoriaPresa.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        horarioPrisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cHorarioAutoriaPresa = Calendar.getInstance();
                int horaAutoriaPresa = cHorarioAutoriaPresa.get(Calendar.HOUR_OF_DAY);
                int minutosAutoriaPresa = cHorarioAutoriaPresa.get(Calendar.MINUTE);

                tpdAutoriaPresa = new TimePickerDialog(AutoriaActivity.this,R.style.Theme_AppCompat_DayNight_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (minute < 10) {
                            horarioPrisao.setText(hourOfDay + ":0" + minute);
                        } else {
                            horarioPrisao.setText(hourOfDay + ":" + minute);
                        }

                    }
                }, horaAutoriaPresa,minutosAutoriaPresa, android.text.format.DateFormat.is24HourFormat(getApplicationContext()));
                tpdAutoriaPresa.show();
            }
        });


        BtnIncluirAutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autoria.setCbkAutoriaDefinida(AutoriaDefinida);
                autoria.setCbkAutoriaNDefinida(AutoriaNDefinida);
                autoria.setCbkAutoriaSuspeita(AutoriaSuspeita);
                autoria.setDsNomeAutoria(EdtNomeAutoria.getText().toString());
                autoria.setDsRGAutoria(EdtRGAutoria.getText().toString());
                autoria.setDsOrgaoExpRGAutoria(EdtOrgExpAutoria.getText().toString());
                autoria.setDsSexoAutoria(SexoAutoria);
                autoria.setDsCPFAutoria(EdtcpfAutoria.getText().toString());
                autoria.setDsIdadeAutoria(Integer.parseInt(EdtidadeAutoria.getText().toString()));
                autoria.setDsCurtisAutoria(CurtisAutoria);
                autoria.setDsNomeMaeAutoria(EdtNomeMaeAutoria.getText().toString());
                autoria.setDsNomePaiAutoria(EdtNomePaiAutoria.getText().toString());
                autoria.setDsNascionalidadeAutoria(NacionalidadeAutoria);
                autoria.setDsNaturalidadeAutoria(EdtNaturalidadeAutoria.getText().toString());
                autoria.setDsCondicaoAutoria(StatusAutoria);
                autoria.setDtPrisaoAutoria(dataPrisao.getText().toString());
                autoria.setDsLocalPrisaoAutoria(EdtLocalPrisao.getText().toString());
                autoria.setHsHorarioPrisaoAutoria(horarioPrisao.getText().toString());
                autoria.setDsNaturezaPrisaoAutoria(naturezaprisaoAutoria);
                autoria.setDsResponsavelPrisaoAutoria(ResponsavelPrisaoAutoria);
                autoria.setDsVulgoAutoria(EdtVulgoAutoria.getText().toString());

                if(jogada == 1){
                    codigocvli = fkatualizar;
                }else {
                    codigocvli = autoriaDao.retornarCodigoCvliSemParametro();
                }

                if(atualiza == 1){
                    autoriaDao.atualizarAutoria(autoria, autoriaatualiza.getFkCvli(), autoriaatualiza.getId());

                    finish();
                }else if(atualiza == 2){
                    autoriasematualizarDao.atualizarAutoria(autoria, autoriasematualizar.getFkCvli(), autoriasematualizar.getId());

                    finish();
                }else {
                    String valorqualquer  = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    autoriaDao.cadastrarCVLIAutoria(autoria,valorqualquer,codigocvli);

                    limparCampos();
                    tornarCamposInvisiveis();
                    BtnQualificacaoAutoria.setVisibility(View.VISIBLE);
                    EdtNomeAutoria.setFocusable(true);
                    ClAutoriaPreso.setVisibility(View.GONE);

                }

            }
        });

        BtnFinalizarAutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autoria.setCbkAutoriaDefinida(AutoriaDefinida);
                autoria.setCbkAutoriaNDefinida(AutoriaNDefinida);
                autoria.setCbkAutoriaSuspeita(AutoriaSuspeita);
                autoria.setDsNomeAutoria(EdtNomeAutoria.getText().toString());
                autoria.setDsRGAutoria(EdtRGAutoria.getText().toString());
                autoria.setDsOrgaoExpRGAutoria(EdtOrgExpAutoria.getText().toString());
                autoria.setDsSexoAutoria(SexoAutoria);
                autoria.setDsCPFAutoria(EdtcpfAutoria.getText().toString());
                autoria.setDsIdadeAutoria(Integer.parseInt(EdtidadeAutoria.getText().toString()));
                autoria.setDsCurtisAutoria(CurtisAutoria);
                autoria.setDsNomeMaeAutoria(EdtNomeMaeAutoria.getText().toString());
                autoria.setDsNomePaiAutoria(EdtNomePaiAutoria.getText().toString());
                autoria.setDsNascionalidadeAutoria(NacionalidadeAutoria);
                autoria.setDsNaturalidadeAutoria(EdtNaturalidadeAutoria.getText().toString());
                autoria.setDsCondicaoAutoria(StatusAutoria);
                autoria.setDtPrisaoAutoria(dataPrisao.getText().toString());
                autoria.setDsLocalPrisaoAutoria(EdtLocalPrisao.getText().toString());
                autoria.setHsHorarioPrisaoAutoria(horarioPrisao.getText().toString());
                autoria.setDsNaturezaPrisaoAutoria(naturezaprisaoAutoria);
                autoria.setDsResponsavelPrisaoAutoria(ResponsavelPrisaoAutoria);
                autoria.setDsVulgoAutoria(EdtVulgoAutoria.getText().toString());

                if(jogada == 1){
                    codigocvli = fkatualizar;
                }else {
                    codigocvli = autoriaDao.retornarCodigoCvliSemParametro();
                }

                String valorqualquer  = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                autoriaDao.cadastrarCVLIAutoria(autoria,valorqualquer,codigocvli);
                finish();
            }
        });

        BtnIncluirPresoAutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autoria.setCbkAutoriaDefinida(AutoriaDefinida);
                autoria.setCbkAutoriaNDefinida(AutoriaNDefinida);
                autoria.setCbkAutoriaSuspeita(AutoriaSuspeita);
                autoria.setDsNomeAutoria(EdtNomeAutoria.getText().toString());
                autoria.setDsRGAutoria(EdtRGAutoria.getText().toString());
                autoria.setDsOrgaoExpRGAutoria(EdtOrgExpAutoria.getText().toString());
                autoria.setDsSexoAutoria(SexoAutoria);
                autoria.setDsCPFAutoria(EdtcpfAutoria.getText().toString());
                autoria.setDsIdadeAutoria(Integer.parseInt(EdtidadeAutoria.getText().toString()));
                autoria.setDsCurtisAutoria(CurtisAutoria);
                autoria.setDsNomeMaeAutoria(EdtNomeMaeAutoria.getText().toString());
                autoria.setDsNomePaiAutoria(EdtNomePaiAutoria.getText().toString());
                autoria.setDsNascionalidadeAutoria(NacionalidadeAutoria);
                autoria.setDsNaturalidadeAutoria(EdtNaturalidadeAutoria.getText().toString());
                autoria.setDsCondicaoAutoria(StatusAutoria);
                autoria.setDtPrisaoAutoria(dataPrisao.getText().toString());
                autoria.setDsLocalPrisaoAutoria(EdtLocalPrisao.getText().toString());
                autoria.setHsHorarioPrisaoAutoria(horarioPrisao.getText().toString());
                autoria.setDsNaturezaPrisaoAutoria(naturezaprisaoAutoria);
                autoria.setDsResponsavelPrisaoAutoria(ResponsavelPrisaoAutoria);
                autoria.setDsVulgoAutoria(EdtVulgoAutoria.getText().toString());

                if(jogada == 1){
                    codigocvli = fkatualizar;
                }else {
                    codigocvli = autoriaDao.retornarCodigoCvliSemParametro();
                }

                if(atualiza == 1){
                    autoriaDao.atualizarAutoria(autoria, autoriaatualiza.getFkCvli(), autoriaatualiza.getId());
                    finish();
                }else if(atualiza == 2){
                    autoriasematualizarDao.atualizarAutoria(autoria, autoriasematualizar.getFkCvli(), autoriasematualizar.getId());
                    finish();
                }else {
                    String valorqualquer  = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    autoriaDao.cadastrarCVLIAutoria(autoria, valorqualquer,codigocvli);

                    limparCampos();
                    tornarCamposInvisiveis();
                    EdtNomeAutoria.setFocusable(true);
                    ClAutoriaPreso.setVisibility(View.GONE);
                }

            }
        });

        BtnFinalizarPresoAutoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autoria.setCbkAutoriaDefinida(AutoriaDefinida);
                autoria.setCbkAutoriaNDefinida(AutoriaNDefinida);
                autoria.setCbkAutoriaSuspeita(AutoriaSuspeita);
                autoria.setDsNomeAutoria(EdtNomeAutoria.getText().toString());
                autoria.setDsRGAutoria(EdtRGAutoria.getText().toString());
                autoria.setDsOrgaoExpRGAutoria(EdtOrgExpAutoria.getText().toString());
                autoria.setDsSexoAutoria(SexoAutoria);
                autoria.setDsCPFAutoria(EdtcpfAutoria.getText().toString());
                autoria.setDsIdadeAutoria(Integer.parseInt(EdtidadeAutoria.getText().toString()));
                autoria.setDsCurtisAutoria(CurtisAutoria);
                autoria.setDsNomeMaeAutoria(EdtNomeMaeAutoria.getText().toString());
                autoria.setDsNomePaiAutoria(EdtNomePaiAutoria.getText().toString());
                autoria.setDsNascionalidadeAutoria(NacionalidadeAutoria);
                autoria.setDsNaturalidadeAutoria(EdtNaturalidadeAutoria.getText().toString());
                autoria.setDsCondicaoAutoria(StatusAutoria);
                autoria.setDtPrisaoAutoria(dataPrisao.getText().toString());
                autoria.setDsLocalPrisaoAutoria(EdtLocalPrisao.getText().toString());
                autoria.setHsHorarioPrisaoAutoria(horarioPrisao.getText().toString());
                autoria.setDsNaturezaPrisaoAutoria(naturezaprisaoAutoria);
                autoria.setDsResponsavelPrisaoAutoria(ResponsavelPrisaoAutoria);
                autoria.setDsVulgoAutoria(EdtVulgoAutoria.getText().toString());

                if(jogada == 1){
                    codigocvli = fkatualizar;
                }else {
                    codigocvli = autoriaDao.retornarCodigoCvliSemParametro();
                }

                String valorqualquer  = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                autoriaDao.cadastrarCVLIAutoria(autoria, valorqualquer, codigocvli);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_autoria, menu);

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

    public void limparCampos(){

        EdtidadeAutoria.setText("0");
        EdtcpfAutoria.setText("");
        EdtNomeAutoria.setText("");
        EdtRGAutoria.setText("");
        EdtOrgExpAutoria.setText("");
        EdtNomeMaeAutoria.setText("");
        EdtNomePaiAutoria.setText("");
        SpSexoAutoria.setSelection(0);
        SpNacionalidadeAutoria.setSelection(0);
        SpStatusAutoria.setSelection(0);
        SpCurtisAutoria.setSelection(0);
        EdtLocalPrisao.setText("");
        dataPrisao.setText("");
        horarioPrisao.setText("");
        naturezaPrisao.setSelection(0);
        SpResponsavelPrisaoAutoria.setSelection(0);
        EdtNaturalidadeAutoria.setText("");
        EdtVulgoAutoria.setText("");

    }

    public void tornarCamposInvisiveis(){

        BtnQualificacaoAutoria.setVisibility(View.GONE);
        BtnStatusAutoria.setVisibility(View.GONE);
        EdtidadeAutoria.setVisibility(View.GONE);
        EdtcpfAutoria.setVisibility(View.GONE);
        EdtNomeAutoria.setVisibility(View.GONE);
        EdtRGAutoria.setVisibility(View.GONE);
        EdtOrgExpAutoria.setVisibility(View.GONE);
        EdtNomeMaeAutoria.setVisibility(View.GONE);
        EdtNomePaiAutoria.setVisibility(View.GONE);
        SpSexoAutoria.setVisibility(View.GONE);
        SpNacionalidadeAutoria.setVisibility(View.GONE);
        SpCurtisAutoria.setVisibility(View.GONE);
        SpStatusAutoria.setVisibility(View.GONE);
        BtnStatusAutoria.setVisibility(View.GONE);
        TvNomeAutoria.setVisibility(View.GONE);
        TvRGAutoria.setVisibility(View.GONE);
        TvOrgaoExpAutoria.setVisibility(View.GONE);
        TvSexoAutoria.setVisibility(View.GONE);
        TvCPFAutoria.setVisibility(View.GONE);
        TvIdadeAutoria.setVisibility(View.GONE);
        TvCurtisAutoria.setVisibility(View.GONE);
        TvNomeMaeAutoria.setVisibility(View.GONE);
        TvNomePaiAutoria.setVisibility(View.GONE);
        TvNascionalidade.setVisibility(View.GONE);
        TvNaturalidade.setVisibility(View.GONE);
        EdtNaturalidadeAutoria.setVisibility(View.GONE);
        EdtVulgoAutoria.setVisibility(View.GONE);
        TvVulgoAutoria.setVisibility(View.GONE);
    }

    public void tornarCamposVisiveis(){

        EdtidadeAutoria.setVisibility(View.VISIBLE);
        EdtcpfAutoria.setVisibility(View.VISIBLE);
        EdtNomeAutoria.setVisibility(View.VISIBLE);
        EdtRGAutoria.setVisibility(View.VISIBLE);
        EdtOrgExpAutoria.setVisibility(View.VISIBLE);
        EdtNomeMaeAutoria.setVisibility(View.VISIBLE);
        EdtNomePaiAutoria.setVisibility(View.VISIBLE);
        SpSexoAutoria.setVisibility(View.VISIBLE);
        SpNacionalidadeAutoria.setVisibility(View.VISIBLE);
        SpCurtisAutoria.setVisibility(View.VISIBLE);
        TvNomeAutoria.setVisibility(View.VISIBLE);
        TvRGAutoria.setVisibility(View.VISIBLE);
        TvOrgaoExpAutoria.setVisibility(View.VISIBLE);
        TvSexoAutoria.setVisibility(View.VISIBLE);
        TvCPFAutoria.setVisibility(View.VISIBLE);
        TvIdadeAutoria.setVisibility(View.VISIBLE);
        TvCurtisAutoria.setVisibility(View.VISIBLE);
        TvNomeMaeAutoria.setVisibility(View.VISIBLE);
        TvNomePaiAutoria.setVisibility(View.VISIBLE);
        TvNascionalidade.setVisibility(View.VISIBLE);
        TvNaturalidade.setVisibility(View.VISIBLE);
        EdtNaturalidadeAutoria.setVisibility(View.VISIBLE);
        EdtVulgoAutoria.setVisibility(View.VISIBLE);
        TvVulgoAutoria.setVisibility(View.VISIBLE);
    }

    private void updateLabel(){

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        dataPrisao.setText(sdf.format(cAutoriaPresa.getTime()));
    }

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
