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

public class RelatorioRispLeste extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLILeste, TvTipoRelatorioCVLILeste, TvEscolhaPeriodoCVLILeste, TvAteCVLILeste;
    private ListView LvResfatoLeste;
    private Button BtnGerarRelatorioCVLILeste;
    private RadioGroup RgRelatorioCVLILeste;
    private RadioButton RbEscolhidoRelatorioCVLILeste;
    private ConstraintLayout ClRelatorioSemanalLeste, ClRelatorioEstatisticoLeste, ClRelatorioCustomizadoLeste;
    private EditText EdtDtInicialCVLILeste, EdtDtFinalCVLILeste, EdtTituloRelatorioLeste, EdtDtInicialCustLeste, EdtDtFinalCustLeste;
    private Spinner SpTipoRelatorioSemanalLeste;
    private CheckBox CkbNaturezaCusLeste, CkbMotivacaoCusLeste, CkbDistribuicaoCidadeCusLeste, CkbDistribuicaoBairroCusLeste, CkbDetalhamentoCusLeste, CkbComparativoAnualCusLeste, CkbMeioEmpregadoCusLeste, CkbElucidacaoCusLeste, CkbZoneamentoCusLeste, CkbComparativoPeriodoCusLeste, CkbComparativoMensalCusLeste, CkbIncidenciaSemanaCusLeste, CkbIncidenciaHorarioCusLeste, CkbRelacaoVitimasCusLeste;
    private int NaturezacusLeste = 0, MotivacaocusLeste = 0, DistribuicaocidadecusLeste = 0, DistribuicaobairrocusLeste = 0, DetalhamentocusLeste = 0, ComparativoanualcusLeste = 0, MeioempregadocusLeste = 0, ElucidacaocusLeste = 0, ZoneamentocusLeste = 0, ComparativoPeriodocusLeste = 0, ComparativomensalcusLeste = 0, incidenciaSemanacusLeste = 0, incidenciaHorariocusLeste = 0, relacaovitimascusLeste = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalLeste, tiporelatorioLeste;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialLeste, dataFinalLeste, dateInicialCusLeste, dateFinalCusLeste;
    private ProgressBar carregando;
    private List<Cvli> ListaResfatoLeste;
    private List<Cvli> ListaResfatoFiltradosLeste = new ArrayList<>();
    String dataincialLeste, datafinalLeste, datainicialcusLeste, datafinalcusLeste;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_risp_leste);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoLeste = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLILeste);
        ClRelatorioEstatisticoLeste = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoLeste);
        ClRelatorioSemanalLeste = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstadoLeste);
        RgRelatorioCVLILeste = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstadoLeste);
        TvTipoRelatorioCVLILeste = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstadoLeste);
        TvEscolhaPeriodoCVLILeste = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstadoLeste);
        TvAteCVLILeste = (TextView) findViewById(R.id.tvAteCVLIEstadoLeste);
        BtnGerarRelatorioCVLILeste = (Button) findViewById(R.id.btnGerarRelatorioCVLILeste);
        EdtDtInicialCVLILeste = (EditText) findViewById(R.id.edtDtInicialCVLIEstadoLeste);
        EdtDtFinalCVLILeste = (EditText) findViewById(R.id.edtDtFinalCVLILeste);
        EdtDtInicialCustLeste = (EditText) findViewById(R.id.edtDataInicioCusCvliLeste);
        EdtDtFinalCustLeste = (EditText) findViewById(R.id.edtDataFinalCusCVLILeste);
        EdtTituloRelatorioLeste = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstadoLeste);
        SpTipoRelatorioSemanalLeste = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstadoLeste);
        LvResfatoLeste = (ListView) findViewById(R.id.lvResFatoLeste);
        CkbNaturezaCusLeste = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLILeste);
        CkbMotivacaoCusLeste = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLILeste);
        CkbDistribuicaoCidadeCusLeste = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLILeste);
        CkbDistribuicaoBairroCusLeste = (CheckBox) findViewById(R.id.ckbDistBairCusCVLILeste);
        CkbDetalhamentoCusLeste = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLILeste);
        CkbComparativoAnualCusLeste = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLILeste);
        CkbMeioEmpregadoCusLeste = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLILeste);
        CkbElucidacaoCusLeste = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLILeste);
        CkbZoneamentoCusLeste = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLILeste);
        CkbComparativoPeriodoCusLeste = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLILeste);
        CkbComparativoMensalCusLeste = (CheckBox) findViewById(R.id.ckbCompMesCusCVLILeste);
        CkbIncidenciaSemanaCusLeste = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLILeste);
        CkbIncidenciaHorarioCusLeste = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLILeste);
        CkbRelacaoVitimasCusLeste = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPLeste);


        ArrayAdapter adaptadorTipoRelatorioSemanalLeste = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalLeste.setAdapter(adaptadorTipoRelatorioSemanalLeste);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincialLeste="01/01/2019";
        datafinalLeste="06/01/2019";
        ListaResfatoLeste = cvliDao.retornaResFato(dataincialLeste,datafinalLeste);
        ListaResfatoFiltradosLeste.addAll(ListaResfatoLeste);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltradosLeste);
        LvResfatoLeste.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfatoLeste);

        RgRelatorioCVLILeste.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLILeste = (RadioButton) RgRelatorioCVLILeste.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLILeste.indexOfChild(RbEscolhidoRelatorioCVLILeste);

                if(indiceescolha == 0){
                    ClRelatorioSemanalLeste.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoLeste.setVisibility(View.GONE);
                    ClRelatorioCustomizadoLeste.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLILeste.setText("GERAR RELATÓRIO SEMANAL - RISP LESTE");
                    tiporelatorioLeste = "semanalrisp";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoLeste.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalLeste.setVisibility(View.GONE);
                    ClRelatorioCustomizadoLeste.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLILeste.setText("GERAR RELATÓRIO ESTATÍSTICO - RISP LESTE");
                    tiporelatorioLeste = "acumuladorisp";
                }else{
                    ClRelatorioEstatisticoLeste.setVisibility(View.GONE);
                    ClRelatorioSemanalLeste.setVisibility(View.GONE);
                    ClRelatorioCustomizadoLeste.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLILeste.setText("GERAR RELATÓRIO CUSTOMIZADO - RISP LESTE");
                    tiporelatorioLeste = "customizadorisp";
                }
            }
        });

        CkbNaturezaCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusLeste.isChecked()){
                    NaturezacusLeste = 1;
                }else{
                    NaturezacusLeste = 0;
                }
            }
        });

        CkbRelacaoVitimasCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusLeste.isChecked()){
                    relacaovitimascusLeste = 1;
                }else{
                    relacaovitimascusLeste = 0;
                }
            }
        });

        CkbMotivacaoCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusLeste.isChecked()){
                    MotivacaocusLeste = 1;
                }else{
                    MotivacaocusLeste = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusLeste.isChecked()){
                    DistribuicaocidadecusLeste = 1;
                }else{
                    DistribuicaocidadecusLeste = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusLeste.isChecked()){
                    DistribuicaobairrocusLeste = 1;
                }else{
                    DistribuicaobairrocusLeste = 0;
                }

            }
        });

        CkbDetalhamentoCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusLeste.isChecked()){
                    DetalhamentocusLeste = 1;
                }else{
                    DetalhamentocusLeste = 0;
                }

            }
        });

        CkbComparativoAnualCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusLeste.isChecked()){
                    ComparativoanualcusLeste = 1;
                }else{
                    ComparativoanualcusLeste = 0;
                }

            }
        });

        CkbMeioEmpregadoCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusLeste.isChecked()){
                    MeioempregadocusLeste = 1;
                }else{
                    MeioempregadocusLeste = 0;
                }

            }
        });

        CkbElucidacaoCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusLeste.isChecked()){
                    ElucidacaocusLeste = 1;
                }else{
                    ElucidacaocusLeste = 0;
                }

            }
        });

        CkbZoneamentoCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusLeste.isChecked()){
                    ZoneamentocusLeste = 1;
                }else{
                    ZoneamentocusLeste = 0;
                }

            }
        });

        CkbComparativoPeriodoCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusLeste.isChecked()){
                    ComparativoPeriodocusLeste = 1;
                }else{
                    ComparativoPeriodocusLeste = 0;
                }

            }
        });

        CkbComparativoMensalCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusLeste.isChecked()){
                    ComparativomensalcusLeste = 1;
                }else{
                    ComparativomensalcusLeste = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusLeste.isChecked()){
                    incidenciaSemanacusLeste = 1;
                }else{
                    incidenciaSemanacusLeste = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusLeste.isChecked()){
                    incidenciaHorariocusLeste = 1;
                }else{
                    incidenciaHorariocusLeste = 0;
                }
            }
        });


        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialLeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialLeste();
            }
        };

        dataFinalLeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalLeste();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusLeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusLeste();
            }
        };

        dateFinalCusLeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusLeste();
            }
        };


        EdtDtInicialCustLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispLeste.this, dateInicialCusLeste, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioLeste = "customizadorisp";
            }
        });

        EdtDtFinalCustLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispLeste.this, dateFinalCusLeste, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioLeste = "customizadorisp";
            }
        });




        EdtDtInicialCVLILeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispLeste.this, dateInicialLeste, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioLeste = "acumuladorisp";
            }
        });

        EdtDtFinalCVLILeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispLeste.this, dataFinalLeste, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioLeste = "acumuladorisp";
            }
        });


        SpTipoRelatorioSemanalLeste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalLeste = SpTipoRelatorioSemanalLeste.getItemAtPosition(position).toString();
                tiporelatorioLeste = "semanalrisp";

                datafinalLeste = tiporelatoriosemanalLeste.substring(13,23);
                dataincialLeste = tiporelatoriosemanalLeste.substring(0,10);

                ListaResfatoLeste = cvliDao.retornaResFato(dataincialLeste,datafinalLeste);

                ListaResfatoFiltradosLeste.clear();
                ListaResfatoFiltradosLeste.addAll(ListaResfatoLeste);
                LvResfatoLeste.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnGerarRelatorioCVLILeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConected(RelatorioRispLeste.this) == false) {

                    AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispLeste.this);
                    LayoutInflater factory = LayoutInflater.from(RelatorioRispLeste.this);
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
                    if (tiporelatorioLeste == "customizadorisp") {
                        if ((EdtDtInicialCustLeste.getText().toString().equals("")) || (EdtDtFinalCustLeste.getText().toString().equals(""))) {

                            Log.i("Valores ent", "entrou aqui no em branco");
                            AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispLeste.this);
                            LayoutInflater factory = LayoutInflater.from(RelatorioRispLeste.this);
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
                        if (tiporelatorioLeste == "acumuladorisp") {
                            if ((EdtDtInicialCVLILeste.getText().toString().equals("")) || (EdtDtFinalCVLILeste.getText().toString().equals(""))) {

                                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispLeste.this);
                                LayoutInflater factory = LayoutInflater.from(RelatorioRispLeste.this);
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

    private void updateLabelDataInicialLeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLILeste.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinalLeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLILeste.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusLeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustLeste.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusLeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustLeste.setText(sdf.format(c1.getTime()));
    }

    private void EnvioDadosRelatorioCVLIRISP() {

        HttpClient httpClient = new DefaultHttpClient();

        String dataIncialCvli = EdtDtInicialCVLILeste.getText().toString();
        String dataFinalCvli = EdtDtFinalCVLILeste.getText().toString();
        String titulorelatorio = EdtTituloRelatorioLeste.getText().toString();
        String dataIncialCvliCus = EdtDtInicialCustLeste.getText().toString();
        String dataFinalCvliCus = EdtDtFinalCustLeste.getText().toString();
        int identificacaorisp = 3;

        try {
            int idservidor = usuarioDao.user.getId();
            String matriculaservidor = usuarioDao.user.getDsMatricula();

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", tiporelatorioLeste));
            itens.add(new BasicNameValuePair("tiporelatorio", tiporelatoriosemanalLeste));
            itens.add(new BasicNameValuePair("tituloRelatorio", titulorelatorio));
            itens.add(new BasicNameValuePair("comecoPeriodo", dataIncialCvli));
            itens.add(new BasicNameValuePair("fimPeriodo", dataFinalCvli));
            itens.add(new BasicNameValuePair("comecoPeriodoCus", dataIncialCvliCus));
            itens.add(new BasicNameValuePair("fimPeriodoCus", dataFinalCvliCus));
            itens.add(new BasicNameValuePair("identrisp", String.valueOf(identificacaorisp)));
            itens.add(new BasicNameValuePair("naturezacus", String.valueOf(NaturezacusLeste)));
            itens.add(new BasicNameValuePair("motivacaocus", String.valueOf(MotivacaocusLeste)));
            itens.add(new BasicNameValuePair("distribuicaocidcus", String.valueOf(DistribuicaocidadecusLeste)));
            itens.add(new BasicNameValuePair("districaobairrocus", String.valueOf(DistribuicaobairrocusLeste)));
            itens.add(new BasicNameValuePair("detalhamentocus", String.valueOf(DetalhamentocusLeste)));
            itens.add(new BasicNameValuePair("companualcus", String.valueOf(ComparativoanualcusLeste)));
            itens.add(new BasicNameValuePair("meioempregadocus", String.valueOf(MeioempregadocusLeste)));
            itens.add(new BasicNameValuePair("elucidacaocus", String.valueOf(ElucidacaocusLeste)));
            itens.add(new BasicNameValuePair("zoneamentocus", String.valueOf(ZoneamentocusLeste)));
            itens.add(new BasicNameValuePair("compperiodicocus", String.valueOf(ComparativoPeriodocusLeste)));
            itens.add(new BasicNameValuePair("compmensalcus", String.valueOf(ComparativomensalcusLeste)));
            itens.add(new BasicNameValuePair("incidenciadiasemanacus", String.valueOf(incidenciaSemanacusLeste)));
            itens.add(new BasicNameValuePair("incidenciahorariocus", String.valueOf(incidenciaHorariocusLeste)));
            itens.add(new BasicNameValuePair("relacaovitimacus", String.valueOf(relacaovitimascusLeste)));


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

                                    Intent it = new Intent(RelatorioRispLeste.this, RelatorioGeradoCVLIActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_relatorio_rispleste, menu);

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
