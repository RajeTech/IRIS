package com.iris.ramilton.iris;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.dao.VitimaDao;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.Vitima;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;

public class VitimaActivity extends AppCompatActivity {

    private Spinner SpStatusVitima, SpSexoVitima, SpNacionalidadeVitima, SpHorientacaoSexual;
    private Spinner SpEstadoVitima, SpCondicaoVitima;
    private TextView TvRGVitima, TvOrgaoExpedicaoRGVitima, TvCPFVitima, TvSexoVitima, TvNascionalidadeVitima, TvNaturalidadeVitima, TvProfissionalVitima, TvNomePaiVitima, TvNomeMaeVitima, TvHorientacaoSexualVitima, TvNomeVitima, TvDtNascimentoVítima;
    private EditText EdtCPFVitima, EdtNomeVitima, EdtRGVitima, EdtOrgaoExpVitima, EdtProfissionalVitima, EdtNomeMaeVitima, EdtNomePaiVitima;
    private EditText EdtDtNascimentoVitima, EdtNaturalidadeVitima;
    private Button BtnEstatusVitima, BtnQualificacaoVitima, BtnFinalizarCadastroVitima, BtnNovoCadastroVitima;
    private String StatusVitima, SexoVitima, NascionalidadeVitima, HorientacaoSexualVitima;
    private String EstadoVitima, CondicaoVitima;
    private RadioButton RbIdentificada, RbNIdentificada, RbescolhidoVitima;
    private RadioGroup RgIndefinidodefinido;
    private int IdentificadaVitima, NaoIdentificadaVitima,codigorecebidosem;
    private VitimaDao vitimaDao, vitimasematualizarDao;
    private Vitima vitima, vitimasematualizar;
    private Vitima vitimaatualiza = null;
    private int atualiza = 0, jogada = 0, codigocvli, fkatualizar;
    private Gerarnumeros gerarnumeros;
    private UsuarioDao usuarioDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitima);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        TvDtNascimentoVítima = (TextView) findViewById(R.id.tvDtNascimentoVítima);
        RgIndefinidodefinido = (RadioGroup) findViewById(R.id.rgIndefinidaDefinidaVitima);
        EdtCPFVitima = (EditText) findViewById(R.id.edtCPFVitima);
        SpSexoVitima = (Spinner) findViewById(R.id.spinnerSexoVitima);
        SpNacionalidadeVitima = (Spinner) findViewById(R.id.spinnerNacionalidadeVitima);
        SpHorientacaoSexual = (Spinner) findViewById(R.id.spinnerHorientacaoSexualVitima);
        TvRGVitima = (TextView) findViewById(R.id.tvRGVitima);
        TvOrgaoExpedicaoRGVitima = (TextView) findViewById(R.id.tvOrgaoExpVitima);
        TvCPFVitima = (TextView) findViewById(R.id.tvCPFVitima);
        TvSexoVitima = (TextView) findViewById(R.id.tvSexoVitima);
        TvNascionalidadeVitima = (TextView) findViewById(R.id.tvNascionalidadeVitima);
        TvNaturalidadeVitima = (TextView) findViewById(R.id.tvNaturalidadeVitima);
        TvProfissionalVitima = (TextView) findViewById(R.id.tvProfissaoVitima);
        TvNomePaiVitima = (TextView) findViewById(R.id.tvNomePaiVitima);
        TvNomeMaeVitima = (TextView) findViewById(R.id.tvNomeMaeVitima);
        TvHorientacaoSexualVitima = (TextView) findViewById(R.id.tvHorientacaoSexualVitima);
        EdtNomeVitima = (EditText) findViewById(R.id.edtNomeVitima);
        EdtRGVitima = (EditText) findViewById(R.id.edtRGVitima);
        EdtOrgaoExpVitima = (EditText) findViewById(R.id.edtOrgaoExpVitima);
        EdtProfissionalVitima = (EditText) findViewById(R.id.edtProfissaoVitima);
        EdtNomeMaeVitima = (EditText) findViewById(R.id.edtNomeMaeVitima);
        EdtNomePaiVitima = (EditText) findViewById(R.id.edtNomePaiVitima);
        BtnEstatusVitima = (Button) findViewById(R.id.btnEstatusVitima);
        BtnQualificacaoVitima = (Button) findViewById(R.id.btnQualificacaoVitima);
        RbIdentificada = (RadioButton) findViewById(R.id.rbDefinidaVitima);
        RbNIdentificada = (RadioButton) findViewById(R.id.rbIndefinidaVitima);
        TvNomeVitima = (TextView) findViewById(R.id.tvNomeVitima);
        BtnNovoCadastroVitima = (Button) findViewById(R.id.btnIncluirVitima);
        BtnFinalizarCadastroVitima = (Button) findViewById(R.id.btnFinalizarVitima);
        SpCondicaoVitima = (Spinner) findViewById(R.id.spCondicaoVitima);
        SpEstadoVitima = (Spinner) findViewById(R.id.spEstadoVitima);
        EdtDtNascimentoVitima = (EditText) findViewById(R.id.edtDtNacimentoVitima);
        EdtNaturalidadeVitima = (EditText) findViewById(R.id.edtNaturalidadeVitima);

        vitimasematualizar = new Vitima();
        vitimasematualizarDao = new VitimaDao(this);

        Intent it = getIntent();

        if(it.hasExtra("vitimaAtualizar")){
            vitimaatualiza = (Vitima) it.getSerializableExtra("vitimaAtualizar");

            atualiza = 1;

            EdtCPFVitima.setText(vitimaatualiza.getDsCPFVitima());
            EdtNomeVitima.setText(vitimaatualiza.getDsNomeVitima());
            EdtRGVitima.setText(vitimaatualiza.getDsRGVitima());
            EdtOrgaoExpVitima.setText(vitimaatualiza.getDsOrgaoExpRGVitima());
            EdtProfissionalVitima.setText(vitimaatualiza.getDsProfissaoVitima());
            EdtNomeMaeVitima.setText(vitimaatualiza.getDsNomeMaeVitima());
            EdtNomePaiVitima.setText(vitimaatualiza.getDsNomePaiVitima());
            EdtDtNascimentoVitima.setText(vitimaatualiza.getDsDtNascimentoVitima());
            EdtNaturalidadeVitima.setText(vitimaatualiza.getDsNaturalidadeVitima());

            if(vitimaatualiza.getCkbIdentificadaVitima() == 0){
                RbIdentificada.setChecked(true);
            }else{
                RbIdentificada.setChecked(false);
            }

            if(vitimaatualiza.getCbkNaoIdentificadaVitima() == 1){
                RbNIdentificada.setChecked(true);
            }else{
                RbNIdentificada.setChecked(false);
            }
        }else if(it.hasExtra("fkvitimaAtualizar")){
            fkatualizar = (int) it.getSerializableExtra("fkvitimaAtualizar");
            jogada = 1;
        }else if(it.hasExtra("vitimasematualizar")){
            codigorecebidosem = (int) it.getSerializableExtra("vitimasematualizar");

            vitimasematualizar = vitimasematualizarDao.retornarVitimasObj(codigorecebidosem);

            atualiza = 2;

            EdtCPFVitima.setText(vitimasematualizar.getDsCPFVitima());
            EdtNomeVitima.setText(vitimasematualizar.getDsNomeVitima());
            EdtRGVitima.setText(vitimasematualizar.getDsRGVitima());
            EdtOrgaoExpVitima.setText(vitimasematualizar.getDsOrgaoExpRGVitima());
            EdtProfissionalVitima.setText(vitimasematualizar.getDsProfissaoVitima());
            EdtNomeMaeVitima.setText(vitimasematualizar.getDsNomeMaeVitima());
            EdtNomePaiVitima.setText(vitimasematualizar.getDsNomePaiVitima());
            EdtDtNascimentoVitima.setText(vitimasematualizar.getDsDtNascimentoVitima());
            EdtNaturalidadeVitima.setText(vitimasematualizar.getDsNaturalidadeVitima());

            if(vitimasematualizar.getCkbIdentificadaVitima() == 0){
                RbIdentificada.setChecked(true);
            }else{
                RbIdentificada.setChecked(false);
            }

            if(vitimasematualizar.getCbkNaoIdentificadaVitima() == 1){
                RbNIdentificada.setChecked(true);
            }else{
                RbNIdentificada.setChecked(false);
            }
        }

        usuarioDao = new UsuarioDao(this);
        vitimaDao = new VitimaDao(this);
        vitima = new Vitima();

        if(atualiza == 1){
            BtnFinalizarCadastroVitima.setVisibility(View.INVISIBLE);
            BtnNovoCadastroVitima.setText("Concluir");
        }else if(atualiza == 2){
            BtnFinalizarCadastroVitima.setVisibility(View.INVISIBLE);
            BtnNovoCadastroVitima.setText("Concluir");
        }else{
            BtnFinalizarCadastroVitima.setVisibility(View.VISIBLE);
            BtnNovoCadastroVitima.setVisibility(View.VISIBLE);
        }

        ArrayAdapter adapadorSpSexoVitima = ArrayAdapter.createFromResource(this,R.array.Sexo,android.R.layout.simple_spinner_item);
        SpSexoVitima.setAdapter(adapadorSpSexoVitima);

        ArrayAdapter adaptadorSpNacionalidadeVitima = ArrayAdapter.createFromResource(this,R.array.Nacionalidade,android.R.layout.simple_spinner_item);
        SpNacionalidadeVitima.setAdapter(adaptadorSpNacionalidadeVitima);

        ArrayAdapter adaptadorSpHorientacaoSexual = ArrayAdapter.createFromResource(this,R.array.OrientacaoSexual,android.R.layout.simple_spinner_item);
        SpHorientacaoSexual.setAdapter(adaptadorSpHorientacaoSexual);

        SimpleMaskFormatter mascaracpfvitima = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskcpfvitima = new MaskTextWatcher(EdtCPFVitima,mascaracpfvitima);
        EdtCPFVitima.addTextChangedListener(maskcpfvitima);

        SpStatusVitima = (Spinner) findViewById(R.id.spinnerStatusVitima);
        ArrayAdapter adaptadorStatusVitima = ArrayAdapter.createFromResource(this,R.array.StatusVitima,android.R.layout.simple_spinner_item);
        SpStatusVitima.setAdapter(adaptadorStatusVitima);

        ArrayAdapter adaptadorEstadoVitima = ArrayAdapter.createFromResource(this,R.array.CondicaoVitima,android.R.layout.simple_spinner_item);
        SpEstadoVitima.setAdapter(adaptadorEstadoVitima);

        ArrayAdapter adaptadorCondicaoVitima = ArrayAdapter.createFromResource(this,R.array.EstadodaVitima,android.R.layout.simple_spinner_item);
        SpCondicaoVitima.setAdapter(adaptadorCondicaoVitima);

        SimpleMaskFormatter mascaraIdadeVitima = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskIdadeVitima = new MaskTextWatcher(EdtDtNascimentoVitima,mascaraIdadeVitima);
        EdtDtNascimentoVitima.addTextChangedListener(maskIdadeVitima);

        RgIndefinidodefinido.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RbescolhidoVitima = (RadioButton) RgIndefinidodefinido.findViewById(checkedId);
                int indece = RgIndefinidodefinido.indexOfChild(RbescolhidoVitima);

                if (indece == 1) {
                    NaoIdentificadaVitima = 1;
                    IdentificadaVitima = 0;
                    BtnQualificacaoVitima.setVisibility(View.VISIBLE);
                    BtnEstatusVitima.setVisibility(View.VISIBLE);
                    SpSexoVitima.setVisibility(View.VISIBLE);
                    TvSexoVitima.setVisibility(View.VISIBLE);
                    EdtNomeVitima.setVisibility(View.GONE);
                    EdtRGVitima.setVisibility(View.GONE);
                    EdtOrgaoExpVitima.setVisibility(View.GONE);
                    EdtCPFVitima.setVisibility(View.GONE);
                    EdtDtNascimentoVitima.setVisibility(View.GONE);
                    EdtNomeMaeVitima.setVisibility(View.GONE);
                    EdtNomePaiVitima.setVisibility(View.GONE);
                    SpNacionalidadeVitima.setVisibility(View.GONE);
                    EdtNaturalidadeVitima.setVisibility(View.GONE);
                    SpHorientacaoSexual.setVisibility(View.GONE);
                    EdtProfissionalVitima.setVisibility(View.GONE);
                    TvNomeVitima.setVisibility(View.GONE);
                    TvRGVitima.setVisibility(View.GONE);
                    TvOrgaoExpedicaoRGVitima.setVisibility(View.GONE);
                    TvCPFVitima.setVisibility(View.GONE);
                    TvNomeMaeVitima.setVisibility(View.GONE);
                    TvNomePaiVitima.setVisibility(View.GONE);
                    TvNascionalidadeVitima.setVisibility(View.GONE);
                    TvNaturalidadeVitima.setVisibility(View.GONE);
                    TvHorientacaoSexualVitima.setVisibility(View.GONE);
                    TvProfissionalVitima.setVisibility(View.GONE);
                    TvDtNascimentoVítima.setVisibility(View.GONE);
                } else {
                    NaoIdentificadaVitima = 0;
                    IdentificadaVitima = 1;
                    BtnQualificacaoVitima.setVisibility(View.VISIBLE);
                    tornarCamposQualificacoesVisiveis();
                    EdtNomeVitima.setEnabled(true);
                    EdtRGVitima.setEnabled(true);
                    EdtOrgaoExpVitima.setEnabled(true);
                    EdtCPFVitima.setEnabled(true);
                    EdtDtNascimentoVitima.setEnabled(true);
                    EdtNomeMaeVitima.setEnabled(true);
                    EdtNomePaiVitima.setEnabled(true);
                    SpNacionalidadeVitima.setEnabled(true);
                    EdtNaturalidadeVitima.setEnabled(true);
                    SpHorientacaoSexual.setEnabled(true);
                    EdtProfissionalVitima.setEnabled(true);

                }
            }

        });

        RbIdentificada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (atualiza == 1) {

                    String Dssexo = vitimaatualiza.getDsSexoVitima();

                    if (Dssexo == null) {
                        SpSexoVitima.setSelection(0);
                        Dssexo = SpSexoVitima.getItemAtPosition(0).toString();
                    }

                    switch (Dssexo) {
                        case "Selecione...":
                            SpSexoVitima.setSelection(0);
                            break;
                        case "Masculino":
                            SpSexoVitima.setSelection(1);
                            break;
                        case "Feminino":
                            SpSexoVitima.setSelection(2);
                            break;
                        default:
                            SpSexoVitima.setSelection(3);
                            break;
                    }

                    String DsnacionalidadeVitima = vitimaatualiza.getDsNascionalidadeVitima();

                    if (DsnacionalidadeVitima == null) {
                        SpNacionalidadeVitima.setSelection(0);
                        DsnacionalidadeVitima = SpNacionalidadeVitima.getItemAtPosition(0).toString();
                    }

                    switch (DsnacionalidadeVitima) {
                        case "Selecione...":
                            SpNacionalidadeVitima.setSelection(0);
                            break;
                        case "Brasileiro":
                            SpNacionalidadeVitima.setSelection(1);
                            break;
                        default:
                            SpNacionalidadeVitima.setSelection(2);
                            break;
                    }

                    String DsHorientacaoSexual = vitimaatualiza.getDsOrientacaoSexualVitima();

                    if (DsHorientacaoSexual == null) {
                        SpHorientacaoSexual.setSelection(0);
                        DsHorientacaoSexual = SpHorientacaoSexual.getItemAtPosition(0).toString();
                    }

                    switch (DsHorientacaoSexual) {
                        case "Selecione...":
                            SpHorientacaoSexual.setSelection(0);
                            break;
                        case "Heterossexual":
                            SpHorientacaoSexual.setSelection(1);
                            break;
                        case "Homossexual":
                            SpHorientacaoSexual.setSelection(2);
                            break;
                        case "Bissexual":
                            SpHorientacaoSexual.setSelection(3);
                            break;
                        case "transsexual":
                            SpHorientacaoSexual.setSelection(4);
                            break;
                        case "Travesti":
                            SpHorientacaoSexual.setSelection(5);
                            break;
                        case "Outros":
                            SpHorientacaoSexual.setSelection(6);
                            break;
                        default:
                            SpHorientacaoSexual.setSelection(7);
                            break;
                    }
                }else if(atualiza == 2){
                    String Dssexo = vitimasematualizar.getDsSexoVitima();

                    if (Dssexo == null) {
                        SpSexoVitima.setSelection(0);
                        Dssexo = SpSexoVitima.getItemAtPosition(0).toString();
                    }

                    switch (Dssexo) {
                        case "Selecione...":
                            SpSexoVitima.setSelection(0);
                            break;
                        case "Masculino":
                            SpSexoVitima.setSelection(1);
                            break;
                        case "Feminino":
                            SpSexoVitima.setSelection(2);
                            break;
                        default:
                            SpSexoVitima.setSelection(3);
                            break;
                    }

                    String DsnacionalidadeVitima = vitimasematualizar.getDsNascionalidadeVitima();

                    if (DsnacionalidadeVitima == null) {
                        SpNacionalidadeVitima.setSelection(0);
                        DsnacionalidadeVitima = SpNacionalidadeVitima.getItemAtPosition(0).toString();
                    }

                    switch (DsnacionalidadeVitima) {
                        case "Selecione...":
                            SpNacionalidadeVitima.setSelection(0);
                            break;
                        case "Brasileiro":
                            SpNacionalidadeVitima.setSelection(1);
                            break;
                        default:
                            SpNacionalidadeVitima.setSelection(2);
                            break;
                    }

                    String DsHorientacaoSexual = vitimasematualizar.getDsOrientacaoSexualVitima();

                    if (DsHorientacaoSexual == null) {
                        SpHorientacaoSexual.setSelection(0);
                        DsHorientacaoSexual = SpHorientacaoSexual.getItemAtPosition(0).toString();
                    }

                    switch (DsHorientacaoSexual) {
                        case "Selecione...":
                            SpHorientacaoSexual.setSelection(0);
                            break;
                        case "Heterossexual":
                            SpHorientacaoSexual.setSelection(1);
                            break;
                        case "Homossexual":
                            SpHorientacaoSexual.setSelection(2);
                            break;
                        case "Bissexual":
                            SpHorientacaoSexual.setSelection(3);
                            break;
                        case "transsexual":
                            SpHorientacaoSexual.setSelection(4);
                            break;
                        case "Travesti":
                            SpHorientacaoSexual.setSelection(5);
                            break;
                        case "Outros":
                            SpHorientacaoSexual.setSelection(6);
                            break;
                        default:
                            SpHorientacaoSexual.setSelection(7);
                            break;
                    }
                }
            }
        });

        EdtDtNascimentoVitima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnEstatusVitima.setVisibility(View.VISIBLE);
            }
        });

        EdtNomeVitima.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                BtnEstatusVitima.setVisibility(View.VISIBLE);
            }
        });

        BtnEstatusVitima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(atualiza == 1) {

                    String DsStatusVitima = vitimaatualiza.getDsCondicaoSaudeVitima();

                    if (DsStatusVitima == null) {
                        SpStatusVitima.setSelection(0);
                        DsStatusVitima = SpStatusVitima.getItemAtPosition(0).toString();
                    }

                    switch (DsStatusVitima) {
                        case "Selecione...":
                            SpStatusVitima.setSelection(0);
                            break;
                        case "Óbito":
                            SpStatusVitima.setSelection(1);
                            break;
                        default:
                            SpStatusVitima.setSelection(2);
                            break;
                    }

                    SpStatusVitima.setVisibility(View.VISIBLE);
                }else if(atualiza == 2){
                    String DsStatusVitima = vitimasematualizar.getDsCondicaoSaudeVitima();

                    if (DsStatusVitima == null) {
                        SpStatusVitima.setSelection(0);
                        DsStatusVitima = SpStatusVitima.getItemAtPosition(0).toString();
                    }

                    switch (DsStatusVitima) {
                        case "Selecione...":
                            SpStatusVitima.setSelection(0);
                            break;
                        case "Óbito":
                            SpStatusVitima.setSelection(1);
                            break;
                        default:
                            SpStatusVitima.setSelection(2);
                            break;
                    }

                    SpStatusVitima.setVisibility(View.VISIBLE);
                }else{
                    SpStatusVitima.setVisibility(View.VISIBLE);

                }
            }
        });

        BtnQualificacaoVitima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(atualiza == 1) {
                    tornarCamposQualificacoesVisiveis();

                    String Dssexo = vitimaatualiza.getDsSexoVitima();

                    if (Dssexo == null) {
                        SpSexoVitima.setSelection(0);
                        Dssexo = SpSexoVitima.getItemAtPosition(0).toString();
                    }

                    switch (Dssexo) {
                        case "Selecione...":
                            SpSexoVitima.setSelection(0);
                            break;
                        case "Masculino":
                            SpSexoVitima.setSelection(1);
                            break;
                        case "Feminino":
                            SpSexoVitima.setSelection(2);
                            break;
                        default:
                            SpSexoVitima.setSelection(3);
                            break;
                    }

                    String DsnacionalidadeVitima = vitimaatualiza.getDsNascionalidadeVitima();

                    if (DsnacionalidadeVitima == null) {
                        SpNacionalidadeVitima.setSelection(0);
                        DsnacionalidadeVitima = SpNacionalidadeVitima.getItemAtPosition(0).toString();
                    }

                    switch (DsnacionalidadeVitima) {
                        case "Selecione...":
                            SpNacionalidadeVitima.setSelection(0);
                            break;
                        case "Brasileiro":
                            SpNacionalidadeVitima.setSelection(1);
                            break;
                        default:
                            SpNacionalidadeVitima.setSelection(2);
                            break;
                    }

                    String DsHorientacaoSexual = vitimaatualiza.getDsOrientacaoSexualVitima();

                    if (DsHorientacaoSexual == null) {
                        SpHorientacaoSexual.setSelection(0);
                        DsHorientacaoSexual = SpHorientacaoSexual.getItemAtPosition(0).toString();
                    }

                    switch (DsHorientacaoSexual) {
                        case "Selecione...":
                            SpHorientacaoSexual.setSelection(0);
                            break;
                        case "Heterossexual":
                            SpHorientacaoSexual.setSelection(1);
                            break;
                        case "Homossexual":
                            SpHorientacaoSexual.setSelection(2);
                            break;
                        case "Bissexual":
                            SpHorientacaoSexual.setSelection(3);
                            break;
                        case "transsexual":
                            SpHorientacaoSexual.setSelection(4);
                            break;
                        case "Travesti":
                            SpHorientacaoSexual.setSelection(5);
                            break;
                        case "Outros":
                            SpHorientacaoSexual.setSelection(6);
                            break;
                        default:
                            SpHorientacaoSexual.setSelection(7);
                            break;
                    }
                }else if(atualiza == 2){

                    tornarCamposQualificacoesVisiveis();

                    String Dssexo = vitimasematualizar.getDsSexoVitima();

                    if (Dssexo == null) {
                        SpSexoVitima.setSelection(0);
                        Dssexo = SpSexoVitima.getItemAtPosition(0).toString();
                    }

                    switch (Dssexo) {
                        case "Selecione...":
                            SpSexoVitima.setSelection(0);
                            break;
                        case "Masculino":
                            SpSexoVitima.setSelection(1);
                            break;
                        case "Feminino":
                            SpSexoVitima.setSelection(2);
                            break;
                        default:
                            SpSexoVitima.setSelection(3);
                            break;
                    }

                    String DsnacionalidadeVitima = vitimasematualizar.getDsNascionalidadeVitima();

                    if (DsnacionalidadeVitima == null) {
                        SpNacionalidadeVitima.setSelection(0);
                        DsnacionalidadeVitima = SpNacionalidadeVitima.getItemAtPosition(0).toString();
                    }

                    switch (DsnacionalidadeVitima) {
                        case "Selecione...":
                            SpNacionalidadeVitima.setSelection(0);
                            break;
                        case "Brasileiro":
                            SpNacionalidadeVitima.setSelection(1);
                            break;
                        default:
                            SpNacionalidadeVitima.setSelection(2);
                            break;
                    }

                    String DsHorientacaoSexual = vitimasematualizar.getDsOrientacaoSexualVitima();

                    if (DsHorientacaoSexual == null) {
                        SpHorientacaoSexual.setSelection(0);
                        DsHorientacaoSexual = SpHorientacaoSexual.getItemAtPosition(0).toString();
                    }

                    switch (DsHorientacaoSexual) {
                        case "Selecione...":
                            SpHorientacaoSexual.setSelection(0);
                            break;
                        case "Heterossexual":
                            SpHorientacaoSexual.setSelection(1);
                            break;
                        case "Homossexual":
                            SpHorientacaoSexual.setSelection(2);
                            break;
                        case "Bissexual":
                            SpHorientacaoSexual.setSelection(3);
                            break;
                        case "transsexual":
                            SpHorientacaoSexual.setSelection(4);
                            break;
                        case "Travesti":
                            SpHorientacaoSexual.setSelection(5);
                            break;
                        case "Outros":
                            SpHorientacaoSexual.setSelection(6);
                            break;
                        default:
                            SpHorientacaoSexual.setSelection(7);
                            break;
                    }
                }else{
                    tornarCamposQualificacoesVisiveis();
                }

            }
        });


        SpStatusVitima.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                StatusVitima = SpStatusVitima.getItemAtPosition(position).toString();
                if(StatusVitima.equals("Sobrevivente")){
                    SpEstadoVitima.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpEstadoVitima.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                EstadoVitima = SpEstadoVitima.getItemAtPosition(position).toString();
                if(EstadoVitima.equals("Lesionada")){
                    SpCondicaoVitima.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpCondicaoVitima.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CondicaoVitima = SpCondicaoVitima.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpSexoVitima.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SexoVitima = SpSexoVitima.getItemAtPosition(position).toString();

                if(NaoIdentificadaVitima == 1 && SexoVitima.equals("Feminino")){
                    EdtNomeVitima.setText("Mulher não identificada");
                }else if(NaoIdentificadaVitima == 1 && SexoVitima.equals("Masculino")){
                    EdtNomeVitima.setText("Homem não identificado");
                }else if(NaoIdentificadaVitima == 1 && SexoVitima.equals("Desconhecida")){
                    EdtNomeVitima.setText("Vitima não identificada");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpNacionalidadeVitima.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                NascionalidadeVitima = SpNacionalidadeVitima.getItemAtPosition(position).toString();


                if(NascionalidadeVitima.equals("Estrangeiro")){
                    EdtNaturalidadeVitima.setEnabled(false);
                }else{
                    EdtNaturalidadeVitima.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpHorientacaoSexual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HorientacaoSexualVitima = SpHorientacaoSexual.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnNovoCadastroVitima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vitima.setCkbIdentificadaVitima(IdentificadaVitima);
                vitima.setCbkNaoIdentificadaVitima(NaoIdentificadaVitima);
                vitima.setDsNomeVitima(EdtNomeVitima.getText().toString());
                vitima.setDsRGVitima(EdtRGVitima.getText().toString());
                vitima.setDsOrgaoExpRGVitima(EdtOrgaoExpVitima.getText().toString());
                vitima.setDsSexoVitima(SexoVitima);
                vitima.setDsCPFVitima(EdtCPFVitima.getText().toString());
                vitima.setDsNomeMaeVitima(EdtNomeMaeVitima.getText().toString());
                vitima.setDsNomePaiVitima(EdtNomePaiVitima.getText().toString());
                vitima.setDsNascionalidadeVitima(NascionalidadeVitima);
                vitima.setDsNaturalidadeVitima(EdtNaturalidadeVitima.getText().toString());
                vitima.setDsOrientacaoSexualVitima(HorientacaoSexualVitima);
                vitima.setDsProfissaoVitima(EdtProfissionalVitima.getText().toString());
                vitima.setDsCondicaoSaudeVitima(StatusVitima);
                vitima.setDsEstadoVitima(EstadoVitima);
                vitima.setDsCondicaoVitima(CondicaoVitima);
                vitima.setDsDtNascimentoVitima(EdtDtNascimentoVitima.getText().toString());

                if(jogada == 1){
                    codigocvli = fkatualizar;
                }else {
                    codigocvli = vitimaDao.retornarCodigoCvliSemParametro();
                }

                if(atualiza ==1){
                    long certo = vitimaDao.AtualizarVitima(vitima,vitimaatualiza.getFkCvli(),vitimaatualiza.getId());
                    if (certo > 0) {
                        Toast.makeText(VitimaActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(VitimaActivity.this, "Erro ao Atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else if(atualiza == 2){
                    long certo = vitimasematualizarDao.AtualizarVitima(vitima,vitimasematualizar.getFkCvli(),vitimasematualizar.getId());
                    if (certo > 0) {
                        Toast.makeText(VitimaActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(VitimaActivity.this, "Erro ao Atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else {

                    String valorqualquer = RetornarNumeroTabelaCVLI();
                    long certo = vitimaDao.cadastrarCVLIVitima(vitima, valorqualquer, codigocvli);
                    if (certo > 0) {
                        Toast.makeText(VitimaActivity.this, "Cadastrardo com Sucesso!!!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(VitimaActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    limparCampos();
                    tornarCamposQualificacoesVisiveis();
                    EdtNomeVitima.setFocusable(true);
                }

            }
        });

        BtnFinalizarCadastroVitima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vitima.setCkbIdentificadaVitima(IdentificadaVitima);
                vitima.setCbkNaoIdentificadaVitima(NaoIdentificadaVitima);
                vitima.setDsNomeVitima(EdtNomeVitima.getText().toString());
                vitima.setDsRGVitima(EdtRGVitima.getText().toString());
                vitima.setDsOrgaoExpRGVitima(EdtOrgaoExpVitima.getText().toString());
                vitima.setDsSexoVitima(SexoVitima);
                vitima.setDsCPFVitima(EdtCPFVitima.getText().toString());
                vitima.setDsNomeMaeVitima(EdtNomeMaeVitima.getText().toString());
                vitima.setDsNomePaiVitima(EdtNomePaiVitima.getText().toString());
                vitima.setDsNascionalidadeVitima(NascionalidadeVitima);
                vitima.setDsNaturalidadeVitima(EdtNaturalidadeVitima.getText().toString());
                vitima.setDsOrientacaoSexualVitima(HorientacaoSexualVitima);
                vitima.setDsProfissaoVitima(EdtProfissionalVitima.getText().toString());
                vitima.setDsCondicaoSaudeVitima(StatusVitima);
                vitima.setDsEstadoVitima(EstadoVitima);
                vitima.setDsCondicaoVitima(CondicaoVitima);
                vitima.setDsDtNascimentoVitima(EdtDtNascimentoVitima.getText().toString());

                if(jogada == 1){
                    codigocvli = fkatualizar;
                }else {
                    codigocvli = vitimaDao.retornarCodigoCvliSemParametro();
                }

                String valorqualquer = RetornarNumeroTabelaCVLI();

                long certo = vitimaDao.cadastrarCVLIVitima(vitima, valorqualquer, codigocvli);
                if (certo > 0) {
                    Toast.makeText(VitimaActivity.this, "Cadastrardo com Sucesso!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VitimaActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vitima, menu);

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

    public void limparCampos(){

        EdtNomeVitima.setText("");
        EdtRGVitima.setText("");
        EdtOrgaoExpVitima.setText("");
        SpSexoVitima.setSelection(0);
        EdtCPFVitima.setText("");
        EdtNomeMaeVitima.setText("");
        EdtNomePaiVitima.setText("");
        SpNacionalidadeVitima.setSelection(0);
        SpHorientacaoSexual.setSelection(0);
        EdtProfissionalVitima.setText("");
        SpStatusVitima.setSelection(0);
        SpCondicaoVitima.setSelection(0);
        SpEstadoVitima.setSelection(0);
        EdtDtNascimentoVitima.setText("");
        EdtNaturalidadeVitima.setText("");
    }

    public void tornarCamposQualificacoesVisiveis(){

        EdtCPFVitima.setVisibility(View.VISIBLE);
        TvRGVitima.setVisibility(View.VISIBLE);
        TvOrgaoExpedicaoRGVitima.setVisibility(View.VISIBLE);
        TvCPFVitima.setVisibility(View.VISIBLE);
        EdtRGVitima.setVisibility(View.VISIBLE);
        EdtOrgaoExpVitima.setVisibility(View.VISIBLE);
        SpSexoVitima.setVisibility(View.VISIBLE);
        SpNacionalidadeVitima.setVisibility(View.VISIBLE);
        SpHorientacaoSexual.setVisibility(View.VISIBLE);
        TvSexoVitima.setVisibility(View.VISIBLE);
        TvNascionalidadeVitima.setVisibility(View.VISIBLE);
        TvNaturalidadeVitima.setVisibility(View.VISIBLE);
        TvProfissionalVitima.setVisibility(View.VISIBLE);
        TvNomePaiVitima.setVisibility(View.VISIBLE);
        TvNomeMaeVitima.setVisibility(View.VISIBLE);
        TvHorientacaoSexualVitima.setVisibility(View.VISIBLE);
        EdtProfissionalVitima.setVisibility(View.VISIBLE);
        EdtNomeMaeVitima.setVisibility(View.VISIBLE);
        EdtNomePaiVitima.setVisibility(View.VISIBLE);
        EdtNomeVitima.setVisibility(View.VISIBLE);
        TvNomeVitima.setVisibility(View.VISIBLE);
        EdtDtNascimentoVitima.setVisibility(View.VISIBLE);
        TvDtNascimentoVítima.setVisibility(View.VISIBLE);
        EdtNaturalidadeVitima.setVisibility(View.VISIBLE);

    }
}
