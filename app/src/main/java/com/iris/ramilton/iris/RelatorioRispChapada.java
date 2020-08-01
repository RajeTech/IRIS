package com.iris.ramilton.iris;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.iris.ramilton.iris.Adaptador.ResFatoAdaptador;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.modelo.Cvli;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class RelatorioRispChapada extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLIChapada, TvTipoRelatorioCVLIChapada, TvEscolhaPeriodoCVLIChapada, TvAteCVLIChapada;
    private ListView LvResfatoChapada;
    private Button BtnGerarRelatorioCVLIChapada;
    private RadioGroup RgRelatorioCVLIChapada;
    private RadioButton RbEscolhidoRelatorioCVLIChapada;
    private ConstraintLayout ClRelatorioSemanalChapada, ClRelatorioEstatisticoChapada, ClRelatorioCustomizadoChapada;
    private EditText EdtDtInicialCVLIChapada, EdtDtFinalCVLIChapada, EdtTituloRelatorioChapada, EdtDtInicialCustChapada, EdtDtFinalCustChapada;
    private Spinner SpTipoRelatorioSemanalChapada;
    private CheckBox CkbNaturezaCusChapada, CkbMotivacaoCusChapada, CkbDistribuicaoCidadeCusChapada, CkbDistribuicaoBairroCusChapada, CkbDetalhamentoCusChapada, CkbComparativoAnualCusChapada, CkbMeioEmpregadoCusChapada, CkbElucidacaoCusChapada, CkbZoneamentoCusChapada, CkbComparativoPeriodoCusChapada, CkbComparativoMensalCusChapada, CkbIncidenciaSemanaCusChapada, CkbIncidenciaHorarioCusChapada, CkbRelacaoVitimasCusChapada;
    private int NaturezacusChapada = 0, MotivacaocusChapada = 0, DistribuicaocidadecusChapada = 0, DistribuicaobairrocusChapada = 0, DetalhamentocusChapada = 0, ComparativoanualcusChapada = 0, MeioempregadocusChapada = 0, ElucidacaocusChapada = 0, ZoneamentocusChapada = 0, ComparativoPeriodocusChapada = 0, ComparativomensalcusChapada = 0, incidenciaSemanacusChapada = 0, incidenciaHorariocusChapada = 0, relacaovitimascusChapada = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalChapada, tiporelatorioChapada;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialChapada, dataFinalChapada, dateInicialCusChapada, dateFinalCusChapada;
    private ProgressBar carregando;
    private List<Cvli> ListaResfatoChapada;
    private List<Cvli> ListaResfatoFiltradosChapada = new ArrayList<>();
    String dataincialChapada, datafinalChapada, datainicialcusChapada, datafinalcusChapada;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_risp_chapada);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoChapada = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLIChapada);
        ClRelatorioEstatisticoChapada = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoChapada);
        ClRelatorioSemanalChapada = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstadoChapada);
        RgRelatorioCVLIChapada = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstadoChapada);
        TvTipoRelatorioCVLIChapada = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstadoChapada);
        TvEscolhaPeriodoCVLIChapada = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstadoChapada);
        TvAteCVLIChapada = (TextView) findViewById(R.id.tvAteCVLIEstadoChapada);
        BtnGerarRelatorioCVLIChapada = (Button) findViewById(R.id.btnGerarRelatorioCVLIChapada);
        EdtDtInicialCVLIChapada = (EditText) findViewById(R.id.edtDtInicialCVLIEstadoChapada);
        EdtDtFinalCVLIChapada = (EditText) findViewById(R.id.edtDtFinalCVLIChapada);
        EdtDtInicialCustChapada = (EditText) findViewById(R.id.edtDataInicioCusCvliChapada);
        EdtDtFinalCustChapada = (EditText) findViewById(R.id.edtDataFinalCusCVLIChapada);
        EdtTituloRelatorioChapada = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstadoChapada);
        SpTipoRelatorioSemanalChapada = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstadoChapada);
        LvResfatoChapada = (ListView) findViewById(R.id.lvResFatoChapada);
        CkbNaturezaCusChapada = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLIChapada);
        CkbMotivacaoCusChapada = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLIChapada);
        CkbDistribuicaoCidadeCusChapada = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLIChapada);
        CkbDistribuicaoBairroCusChapada = (CheckBox) findViewById(R.id.ckbDistBairCusCVLIChapada);
        CkbDetalhamentoCusChapada = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLIChapada);
        CkbComparativoAnualCusChapada = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLIChapada);
        CkbMeioEmpregadoCusChapada = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLIChapada);
        CkbElucidacaoCusChapada = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLIChapada);
        CkbZoneamentoCusChapada = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLIChapada);
        CkbComparativoPeriodoCusChapada = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLIChapada);
        CkbComparativoMensalCusChapada = (CheckBox) findViewById(R.id.ckbCompMesCusCVLIChapada);
        CkbIncidenciaSemanaCusChapada = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLIChapada);
        CkbIncidenciaHorarioCusChapada = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLIChapada);
        CkbRelacaoVitimasCusChapada = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPChapada);


        ArrayAdapter adaptadorTipoRelatorioSemanalChapada = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalChapada.setAdapter(adaptadorTipoRelatorioSemanalChapada);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincialChapada="01/01/2019";
        datafinalChapada="06/01/2019";
        ListaResfatoChapada = cvliDao.retornaResFato(dataincialChapada,datafinalChapada);
        ListaResfatoFiltradosChapada.addAll(ListaResfatoChapada);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltradosChapada);
        LvResfatoChapada.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfatoChapada);

        RgRelatorioCVLIChapada.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLIChapada = (RadioButton) RgRelatorioCVLIChapada.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLIChapada.indexOfChild(RbEscolhidoRelatorioCVLIChapada);

                if(indiceescolha == 0){
                    ClRelatorioSemanalChapada.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoChapada.setVisibility(View.GONE);
                    ClRelatorioCustomizadoChapada.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIChapada.setText("GERAR RELATÓRIO SEMANAL - RISP CHAPADA");
                    tiporelatorioChapada = "semanalrisp";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoChapada.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalChapada.setVisibility(View.GONE);
                    ClRelatorioCustomizadoChapada.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIChapada.setText("GERAR RELATÓRIO ESTATÍSTICO - RISP CHAPADA");
                    tiporelatorioChapada = "acumuladorisp";
                }else{
                    ClRelatorioEstatisticoChapada.setVisibility(View.GONE);
                    ClRelatorioSemanalChapada.setVisibility(View.GONE);
                    ClRelatorioCustomizadoChapada.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLIChapada.setText("GERAR RELATÓRIO CUSTOMIZADO - RISP CHAPADA");
                    tiporelatorioChapada = "customizadorisp";
                }
            }
        });

        CkbNaturezaCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusChapada.isChecked()){
                    NaturezacusChapada = 1;
                }else{
                    NaturezacusChapada = 0;
                }
            }
        });

        CkbRelacaoVitimasCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusChapada.isChecked()){
                    relacaovitimascusChapada = 1;
                }else{
                    relacaovitimascusChapada = 0;
                }
            }
        });

        CkbMotivacaoCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusChapada.isChecked()){
                    MotivacaocusChapada = 1;
                }else{
                    MotivacaocusChapada = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusChapada.isChecked()){
                    DistribuicaocidadecusChapada = 1;
                }else{
                    DistribuicaocidadecusChapada = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusChapada.isChecked()){
                    DistribuicaobairrocusChapada = 1;
                }else{
                    DistribuicaobairrocusChapada = 0;
                }

            }
        });

        CkbDetalhamentoCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusChapada.isChecked()){
                    DetalhamentocusChapada = 1;
                }else{
                    DetalhamentocusChapada = 0;
                }

            }
        });

        CkbComparativoAnualCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusChapada.isChecked()){
                    ComparativoanualcusChapada = 1;
                }else{
                    ComparativoanualcusChapada = 0;
                }

            }
        });

        CkbMeioEmpregadoCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusChapada.isChecked()){
                    MeioempregadocusChapada = 1;
                }else{
                    MeioempregadocusChapada = 0;
                }

            }
        });

        CkbElucidacaoCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusChapada.isChecked()){
                    ElucidacaocusChapada = 1;
                }else{
                    ElucidacaocusChapada = 0;
                }

            }
        });

        CkbZoneamentoCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusChapada.isChecked()){
                    ZoneamentocusChapada = 1;
                }else{
                    ZoneamentocusChapada = 0;
                }

            }
        });

        CkbComparativoPeriodoCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusChapada.isChecked()){
                    ComparativoPeriodocusChapada = 1;
                }else{
                    ComparativoPeriodocusChapada = 0;
                }

            }
        });

        CkbComparativoMensalCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusChapada.isChecked()){
                    ComparativomensalcusChapada = 1;
                }else{
                    ComparativomensalcusChapada = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusChapada.isChecked()){
                    incidenciaSemanacusChapada = 1;
                }else{
                    incidenciaSemanacusChapada = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusChapada.isChecked()){
                    incidenciaHorariocusChapada = 1;
                }else{
                    incidenciaHorariocusChapada = 0;
                }
            }
        });


        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialChapada = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialChapada();
            }
        };

        dataFinalChapada = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalChapada();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusChapada = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusChapada();
            }
        };

        dateFinalCusChapada = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusChapada();
            }
        };


        EdtDtInicialCustChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispChapada.this, dateInicialCusChapada, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioChapada = "customizadorisp";
            }
        });

        EdtDtFinalCustChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispChapada.this, dateFinalCusChapada, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioChapada = "customizadorisp";
            }
        });




        EdtDtInicialCVLIChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispChapada.this, dateInicialChapada, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioChapada = "acumuladorisp";
            }
        });

        EdtDtFinalCVLIChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispChapada.this, dataFinalChapada, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioChapada = "acumuladorisp";
            }
        });


        SpTipoRelatorioSemanalChapada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalChapada = SpTipoRelatorioSemanalChapada.getItemAtPosition(position).toString();
                tiporelatorioChapada = "semanalrisp";

                datafinalChapada = tiporelatoriosemanalChapada.substring(13,23);
                dataincialChapada = tiporelatoriosemanalChapada.substring(0,10);

                ListaResfatoChapada = cvliDao.retornaResFato(dataincialChapada,datafinalChapada);

                ListaResfatoFiltradosChapada.clear();
                ListaResfatoFiltradosChapada.addAll(ListaResfatoChapada);
                LvResfatoChapada.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnGerarRelatorioCVLIChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConected(RelatorioRispChapada.this) == false) {

                    AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispChapada.this);
                    LayoutInflater factory = LayoutInflater.from(RelatorioRispChapada.this);
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
                    if (tiporelatorioChapada == "customizadorisp") {
                        if ((EdtDtInicialCustChapada.getText().toString().equals("")) || (EdtDtFinalCustChapada.getText().toString().equals(""))) {

                            AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispChapada.this);
                            LayoutInflater factory = LayoutInflater.from(RelatorioRispChapada.this);
                            final View view = factory.inflate(R.layout.logotipo, null);
                            b.setTitle("IRIS - Atenção!!!")
                                    .setView(view)
                                    .setMessage("Os campos datas não podem ficar em branco!!!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //  carregando.setVisibility(ProgressBar.GONE);
                                        }
                                    }).create();
                            b.show();
                        } else {
                            carregando.setVisibility(ProgressBar.VISIBLE);
                            EnvioDadosRelatorioCVLIRISP();
                        }
                    } else {
                        if (tiporelatorioChapada == "acumuladorisp") {
                            if ((EdtDtInicialCVLIChapada.getText().toString().equals("")) || (EdtDtFinalCVLIChapada.getText().toString().equals(""))) {

                                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispChapada.this);
                                LayoutInflater factory = LayoutInflater.from(RelatorioRispChapada.this);
                                final View view = factory.inflate(R.layout.logotipo, null);
                                b.setTitle("IRIS - Atenção!!!")
                                        .setView(view)
                                        .setMessage("Os campos datas não podem ficar em branco!!!")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //  carregando.setVisibility(ProgressBar.GONE);
                                            }
                                        }).create();
                                b.show();
                            } else {
                                carregando.setVisibility(ProgressBar.VISIBLE);
                                EnvioDadosRelatorioCVLIRISP();
                            }
                        } else {
                            carregando.setVisibility(ProgressBar.VISIBLE);
                            EnvioDadosRelatorioCVLIRISP();
                        }
                    }
                }
            }
        });

    }

    private void EnvioDadosRelatorioCVLIRISP() {

        HttpClient httpClient = new DefaultHttpClient();

        String dataIncialCvli = EdtDtInicialCVLIChapada.getText().toString();
        String dataFinalCvli = EdtDtFinalCVLIChapada.getText().toString();
        String titulorelatorio = EdtTituloRelatorioChapada.getText().toString();
        String dataIncialCvliCus = EdtDtInicialCustChapada.getText().toString();
        String dataFinalCvliCus = EdtDtFinalCustChapada.getText().toString();
        int identificacaorisp = 6;

        try {
            int idservidor = usuarioDao.user.getId();
            String matriculaservidor = usuarioDao.user.getDsMatricula();

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", tiporelatorioChapada));
            itens.add(new BasicNameValuePair("tiporelatorio", tiporelatoriosemanalChapada));
            itens.add(new BasicNameValuePair("tituloRelatorio", titulorelatorio));
            itens.add(new BasicNameValuePair("comecoPeriodo", dataIncialCvli));
            itens.add(new BasicNameValuePair("fimPeriodo", dataFinalCvli));
            itens.add(new BasicNameValuePair("comecoPeriodoCus", dataIncialCvliCus));
            itens.add(new BasicNameValuePair("fimPeriodoCus", dataFinalCvliCus));
            itens.add(new BasicNameValuePair("identrisp", String.valueOf(identificacaorisp)));
            itens.add(new BasicNameValuePair("naturezacus", String.valueOf(NaturezacusChapada)));
            itens.add(new BasicNameValuePair("motivacaocus", String.valueOf(MotivacaocusChapada)));
            itens.add(new BasicNameValuePair("distribuicaocidcus", String.valueOf(DistribuicaocidadecusChapada)));
            itens.add(new BasicNameValuePair("districaobairrocus", String.valueOf(DistribuicaobairrocusChapada)));
            itens.add(new BasicNameValuePair("detalhamentocus", String.valueOf(DetalhamentocusChapada)));
            itens.add(new BasicNameValuePair("companualcus", String.valueOf(ComparativoanualcusChapada)));
            itens.add(new BasicNameValuePair("meioempregadocus", String.valueOf(MeioempregadocusChapada)));
            itens.add(new BasicNameValuePair("elucidacaocus", String.valueOf(ElucidacaocusChapada)));
            itens.add(new BasicNameValuePair("zoneamentocus", String.valueOf(ZoneamentocusChapada)));
            itens.add(new BasicNameValuePair("compperiodicocus", String.valueOf(ComparativoPeriodocusChapada)));
            itens.add(new BasicNameValuePair("compmensalcus", String.valueOf(ComparativomensalcusChapada)));
            itens.add(new BasicNameValuePair("incidenciadiasemanacus", String.valueOf(incidenciaSemanacusChapada)));
            itens.add(new BasicNameValuePair("incidenciahorariocus", String.valueOf(incidenciaHorariocusChapada)));
            itens.add(new BasicNameValuePair("relacaovitimacus", String.valueOf(relacaovitimascusChapada)));


            String parametro = URLEncodedUtils.format(itens, "utf-8");
            HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/cvli/relatorio?" + parametro);

            final HttpResponse response = httpClient.execute(httpGet);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String resposta = EntityUtils.toString(response.getEntity());

                        JSONObject resp = null;
                        try {
                            resp = new JSONObject(resposta);

                            if(resp.getString("resposta")=="1" || resp.getInt("resposta")== 1) {


                                relatorio = new JSONObject(resp.getString("informacoes"));
                                try {
                                    String co = relatorio.getString("relatorio");

                                    Intent it = new Intent(RelatorioRispChapada.this, RelatorioGeradoCVLIActivity.class);
                                    it.putExtra("relatorioCvlis", co);
                                    startActivity(it);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        carregando.setVisibility(ProgressBar.GONE);
                    }
                }
            });

        }catch(ClientProtocolException e){}
        catch(Exception e){}
    }

    private void updateLabelDataInicialChapada(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLIChapada.setText(sdf.format(c.getTime())); 
    }

    private void updateLabelDataFinalChapada(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLIChapada.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusChapada(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustChapada.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusChapada(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustChapada.setText(sdf.format(c1.getTime()));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_relatorio_rispchapada, menu);

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
