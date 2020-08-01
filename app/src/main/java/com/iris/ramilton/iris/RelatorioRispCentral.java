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

public class RelatorioRispCentral extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLICentral, TvTipoRelatorioCVLICentral, TvEscolhaPeriodoCVLICentral, TvAteCVLICentral;
    private ListView LvResfatoCentral;
    private Button BtnGerarRelatorioCVLICentral;
    private RadioGroup RgRelatorioCVLICentral;
    private RadioButton RbEscolhidoRelatorioCVLICentral;
    private ConstraintLayout ClRelatorioSemanalCentral, ClRelatorioEstatisticoCentral, ClRelatorioCustomizadoCentral;
    private EditText EdtDtInicialCVLICentral, EdtDtFinalCVLICentral, EdtTituloRelatorioCentral, EdtDtInicialCustCentral, EdtDtFinalCustCentral;
    private Spinner SpTipoRelatorioSemanalCentral;
    private CheckBox CkbNaturezaCusCentral, CkbMotivacaoCusCentral, CkbDistribuicaoCidadeCusCentral, CkbDistribuicaoBairroCusCentral, CkbDetalhamentoCusCentral, CkbComparativoAnualCusCentral, CkbMeioEmpregadoCusCentral, CkbElucidacaoCusCentral, CkbZoneamentoCusCentral, CkbComparativoPeriodoCusCentral, CkbComparativoMensalCusCentral, CkbIncidenciaSemanaCusCentral, CkbIncidenciaHorarioCusCentral, CkbRelacaoVitimasCusCentral;
    private int NaturezacusCentral = 0, MotivacaocusCentral = 0, DistribuicaocidadecusCentral = 0, DistribuicaobairrocusCentral = 0, DetalhamentocusCentral = 0, ComparativoanualcusCentral = 0, MeioempregadocusCentral = 0, ElucidacaocusCentral = 0, ZoneamentocusCentral = 0, ComparativoPeriodocusCentral = 0, ComparativomensalcusCentral = 0, incidenciaSemanacusCentral = 0, incidenciaHorariocusCentral = 0, relacaovitimascusCentral = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalCentral, tiporelatorioCentral;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialCentral, dataFinalCentral, dateInicialCusCentral, dateFinalCusCentral;
    private ProgressBar carregando;
    private List<Cvli> ListaResfatoCentral;
    private List<Cvli> ListaResfatoFiltradosCentral = new ArrayList<>();
    String dataincialCentral, datafinalCentral, datainicialcusCentral, datafinalcusCentral;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_risp_central);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoCentral = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLICentral);
        ClRelatorioEstatisticoCentral = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoCentral);
        ClRelatorioSemanalCentral = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstadoCentral);
        RgRelatorioCVLICentral = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstadoCentral);
        TvTipoRelatorioCVLICentral = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstadoCentral);
        TvEscolhaPeriodoCVLICentral = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstadoCentral);
        TvAteCVLICentral = (TextView) findViewById(R.id.tvAteCVLIEstadoCentral);
        BtnGerarRelatorioCVLICentral = (Button) findViewById(R.id.btnGerarRelatorioCVLICentral);
        EdtDtInicialCVLICentral = (EditText) findViewById(R.id.edtDtInicialCVLIEstadoCentral);
        EdtDtFinalCVLICentral = (EditText) findViewById(R.id.edtDtFinalCVLICentral);
        EdtDtInicialCustCentral = (EditText) findViewById(R.id.edtDataInicioCusCvliCentral);
        EdtDtFinalCustCentral = (EditText) findViewById(R.id.edtDataFinalCusCVLICentral);
        EdtTituloRelatorioCentral = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstadoCentral);
        SpTipoRelatorioSemanalCentral = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstadoCentral);
        LvResfatoCentral = (ListView) findViewById(R.id.lvResFatoCentral);
        CkbNaturezaCusCentral = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLICentral);
        CkbMotivacaoCusCentral = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLICentral);
        CkbDistribuicaoCidadeCusCentral = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLIBCentral);
        CkbDistribuicaoBairroCusCentral = (CheckBox) findViewById(R.id.ckbDistBairCusCVLICentral);
        CkbDetalhamentoCusCentral = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLICentral);
        CkbComparativoAnualCusCentral = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLICentral);
        CkbMeioEmpregadoCusCentral = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLICentral);
        CkbElucidacaoCusCentral = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLICentral);
        CkbZoneamentoCusCentral = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLICentral);
        CkbComparativoPeriodoCusCentral = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLICentral);
        CkbComparativoMensalCusCentral = (CheckBox) findViewById(R.id.ckbCompMesCusCVLICentral);
        CkbIncidenciaSemanaCusCentral = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLICentral);
        CkbIncidenciaHorarioCusCentral = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLICentral);
        CkbRelacaoVitimasCusCentral = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPCentral);


        ArrayAdapter adaptadorTipoRelatorioSemanalCentral = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalCentral.setAdapter(adaptadorTipoRelatorioSemanalCentral);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincialCentral="01/01/2019";
        datafinalCentral="06/01/2019";
        ListaResfatoCentral = cvliDao.retornaResFato(dataincialCentral,datafinalCentral);
        ListaResfatoFiltradosCentral.addAll(ListaResfatoCentral);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltradosCentral);
        LvResfatoCentral.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfatoCentral);

        RgRelatorioCVLICentral.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLICentral = (RadioButton) RgRelatorioCVLICentral.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLICentral.indexOfChild(RbEscolhidoRelatorioCVLICentral);

                if(indiceescolha == 0){
                    ClRelatorioSemanalCentral.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoCentral.setVisibility(View.GONE);
                    ClRelatorioCustomizadoCentral.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLICentral.setText("GERAR RELATÓRIO SEMANAL - RISP CENTRAL");
                    tiporelatorioCentral = "semanalrisp";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoCentral.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalCentral.setVisibility(View.GONE);
                    ClRelatorioCustomizadoCentral.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLICentral.setText("GERAR RELATÓRIO ESTATÍSTICO - RISP CENTRAL");
                    tiporelatorioCentral = "acumuladorisp";
                }else{
                    ClRelatorioEstatisticoCentral.setVisibility(View.GONE);
                    ClRelatorioSemanalCentral.setVisibility(View.GONE);
                    ClRelatorioCustomizadoCentral.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLICentral.setText("GERAR RELATÓRIO CUSTOMIZADO - RISP CENTRAL");
                    tiporelatorioCentral = "customizadorisp";
                }
            }
        });

        CkbNaturezaCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusCentral.isChecked()){
                    NaturezacusCentral = 1;
                }else{
                    NaturezacusCentral = 0;
                }
            }
        });

        CkbRelacaoVitimasCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusCentral.isChecked()){
                    relacaovitimascusCentral = 1;
                }else{
                    relacaovitimascusCentral = 0;
                }
            }
        });

        CkbMotivacaoCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusCentral.isChecked()){
                    MotivacaocusCentral = 1;
                }else{
                    MotivacaocusCentral = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusCentral.isChecked()){
                    DistribuicaocidadecusCentral = 1;
                }else{
                    DistribuicaocidadecusCentral = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusCentral.isChecked()){
                    DistribuicaobairrocusCentral = 1;
                }else{
                    DistribuicaobairrocusCentral = 0;
                }

            }
        });

        CkbDetalhamentoCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusCentral.isChecked()){
                    DetalhamentocusCentral = 1;
                }else{
                    DetalhamentocusCentral = 0;
                }

            }
        });

        CkbComparativoAnualCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusCentral.isChecked()){
                    ComparativoanualcusCentral = 1;
                }else{
                    ComparativoanualcusCentral = 0;
                }

            }
        });

        CkbMeioEmpregadoCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusCentral.isChecked()){
                    MeioempregadocusCentral = 1;
                }else{
                    MeioempregadocusCentral = 0;
                }

            }
        });

        CkbElucidacaoCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusCentral.isChecked()){
                    ElucidacaocusCentral = 1;
                }else{
                    ElucidacaocusCentral = 0;
                }

            }
        });

        CkbZoneamentoCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusCentral.isChecked()){
                    ZoneamentocusCentral = 1;
                }else{
                    ZoneamentocusCentral = 0;
                }

            }
        });

        CkbComparativoPeriodoCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusCentral.isChecked()){
                    ComparativoPeriodocusCentral = 1;
                }else{
                    ComparativoPeriodocusCentral = 0;
                }

            }
        });

        CkbComparativoMensalCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusCentral.isChecked()){
                    ComparativomensalcusCentral = 1;
                }else{
                    ComparativomensalcusCentral = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusCentral.isChecked()){
                    incidenciaSemanacusCentral = 1;
                }else{
                    incidenciaSemanacusCentral = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusCentral.isChecked()){
                    incidenciaHorariocusCentral = 1;
                }else{
                    incidenciaHorariocusCentral = 0;
                }
            }
        });


        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialCentral = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCentral();
            }
        };

        dataFinalCentral = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCentral();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusCentral = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusCentral();
            }
        };

        dateFinalCusCentral = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusCentral();
            }
        };


        EdtDtInicialCustCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispCentral.this, dateInicialCusCentral, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioCentral = "customizadorisp";
            }
        });

        EdtDtFinalCustCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispCentral.this, dateFinalCusCentral, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioCentral = "customizadorisp";
            }
        });




        EdtDtInicialCVLICentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispCentral.this, dateInicialCentral, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioCentral = "acumuladorisp";
            }
        });

        EdtDtFinalCVLICentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispCentral.this, dataFinalCentral, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioCentral = "acumuladorisp";
            }
        });


        SpTipoRelatorioSemanalCentral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalCentral = SpTipoRelatorioSemanalCentral.getItemAtPosition(position).toString();
                tiporelatorioCentral = "semanalrisp";

                datafinalCentral = tiporelatoriosemanalCentral.substring(13,23);
                dataincialCentral = tiporelatoriosemanalCentral.substring(0,10);

                ListaResfatoCentral = cvliDao.retornaResFato(dataincialCentral,datafinalCentral);

                ListaResfatoFiltradosCentral.clear();
                ListaResfatoFiltradosCentral.addAll(ListaResfatoCentral);
                LvResfatoCentral.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnGerarRelatorioCVLICentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConected(RelatorioRispCentral.this) == false) {

                    AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispCentral.this);
                    LayoutInflater factory = LayoutInflater.from(RelatorioRispCentral.this);
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
                    if (tiporelatorioCentral == "customizadorisp") {
                        if ((EdtDtInicialCustCentral.getText().toString().equals("")) || (EdtDtFinalCustCentral.getText().toString().equals(""))) {

                            Log.i("Valores ent", "entrou aqui no em branco");
                            AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispCentral.this);
                            LayoutInflater factory = LayoutInflater.from(RelatorioRispCentral.this);
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
                        if (tiporelatorioCentral == "acumuladorisp") {
                            if ((EdtDtInicialCVLICentral.getText().toString().equals("")) || (EdtDtFinalCVLICentral.getText().toString().equals(""))) {

                                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispCentral.this);
                                LayoutInflater factory = LayoutInflater.from(RelatorioRispCentral.this);
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

        String dataIncialCvli = EdtDtInicialCVLICentral.getText().toString();
        String dataFinalCvli = EdtDtFinalCVLICentral.getText().toString();
        String titulorelatorio = EdtTituloRelatorioCentral.getText().toString();
        String dataIncialCvliCus = EdtDtInicialCustCentral.getText().toString();
        String dataFinalCvliCus = EdtDtFinalCustCentral.getText().toString();
        int identificacaorisp = 5;

        try {
            int idservidor = usuarioDao.user.getId();
            String matriculaservidor = usuarioDao.user.getDsMatricula();

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", tiporelatorioCentral));
            itens.add(new BasicNameValuePair("tiporelatorio", tiporelatoriosemanalCentral));
            itens.add(new BasicNameValuePair("tituloRelatorio", titulorelatorio));
            itens.add(new BasicNameValuePair("comecoPeriodo", dataIncialCvli));
            itens.add(new BasicNameValuePair("fimPeriodo", dataFinalCvli));
            itens.add(new BasicNameValuePair("comecoPeriodoCus", dataIncialCvliCus));
            itens.add(new BasicNameValuePair("fimPeriodoCus", dataFinalCvliCus));
            itens.add(new BasicNameValuePair("identrisp", String.valueOf(identificacaorisp)));
            itens.add(new BasicNameValuePair("naturezacus", String.valueOf(NaturezacusCentral)));
            itens.add(new BasicNameValuePair("motivacaocus", String.valueOf(MotivacaocusCentral)));
            itens.add(new BasicNameValuePair("distribuicaocidcus", String.valueOf(DistribuicaocidadecusCentral)));
            itens.add(new BasicNameValuePair("districaobairrocus", String.valueOf(DistribuicaobairrocusCentral)));
            itens.add(new BasicNameValuePair("detalhamentocus", String.valueOf(DetalhamentocusCentral)));
            itens.add(new BasicNameValuePair("companualcus", String.valueOf(ComparativoanualcusCentral)));
            itens.add(new BasicNameValuePair("meioempregadocus", String.valueOf(MeioempregadocusCentral)));
            itens.add(new BasicNameValuePair("elucidacaocus", String.valueOf(ElucidacaocusCentral)));
            itens.add(new BasicNameValuePair("zoneamentocus", String.valueOf(ZoneamentocusCentral)));
            itens.add(new BasicNameValuePair("compperiodicocus", String.valueOf(ComparativoPeriodocusCentral)));
            itens.add(new BasicNameValuePair("compmensalcus", String.valueOf(ComparativomensalcusCentral)));
            itens.add(new BasicNameValuePair("incidenciadiasemanacus", String.valueOf(incidenciaSemanacusCentral)));
            itens.add(new BasicNameValuePair("incidenciahorariocus", String.valueOf(incidenciaHorariocusCentral)));
            itens.add(new BasicNameValuePair("relacaovitimacus", String.valueOf(relacaovitimascusCentral)));


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

                                    Intent it = new Intent(RelatorioRispCentral.this, RelatorioGeradoCVLIActivity.class);
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

    private void updateLabelDataInicialCentral(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLICentral.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinalCentral(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLICentral.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusCentral(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustCentral.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusCentral(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustCentral.setText(sdf.format(c1.getTime()));
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
        getMenuInflater().inflate(R.menu.menu_relatorio_rispcentral, menu);

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
