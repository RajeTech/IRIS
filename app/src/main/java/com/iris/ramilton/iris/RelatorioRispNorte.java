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

public class RelatorioRispNorte extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLINorte, TvTipoRelatorioCVLINorte, TvEscolhaPeriodoCVLINorte, TvAteCVLINorte;
    private ListView LvResfatoNorte;
    private Button BtnGerarRelatorioCVLINorte;
    private RadioGroup RgRelatorioCVLINorte;
    private RadioButton RbEscolhidoRelatorioCVLINorte;
    private ConstraintLayout ClRelatorioSemanalNorte, ClRelatorioEstatisticoNorte, ClRelatorioCustomizadoNorte;
    private EditText EdtDtInicialCVLINorte, EdtDtFinalCVLINorte, EdtTituloRelatorioNorte, EdtDtInicialCustNorte, EdtDtFinalCustNorte;
    private Spinner SpTipoRelatorioSemanalNorte;
    private CheckBox CkbNaturezaCusNorte, CkbMotivacaoCusNorte, CkbDistribuicaoCidadeCusNorte, CkbDistribuicaoBairroCusNorte, CkbDetalhamentoCusNorte, CkbComparativoAnualCusNorte, CkbMeioEmpregadoCusNorte, CkbElucidacaoCusNorte, CkbZoneamentoCusNorte, CkbComparativoPeriodoCusNorte, CkbComparativoMensalCusNorte, CkbIncidenciaSemanaCusNorte, CkbIncidenciaHorarioCusNorte, CkbRelacaoVitimasCusNorte;
    private int NaturezacusNorte = 0, MotivacaocusNorte = 0, DistribuicaocidadecusNorte = 0, DistribuicaobairrocusNorte = 0, DetalhamentocusNorte = 0, ComparativoanualcusNorte = 0, MeioempregadocusNorte = 0, ElucidacaocusNorte = 0, ZoneamentocusNorte = 0, ComparativoPeriodocusNorte = 0, ComparativomensalcusNorte = 0, incidenciaSemanacusNorte = 0, incidenciaHorariocusNorte = 0, relacaovitimascusNorte = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalNorte, tiporelatorioNorte;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialNorte, dataFinalNorte, dateInicialCusNorte, dateFinalCusNorte;
    private ProgressBar carregando;
    private List<Cvli> ListaResfatoNorte;
    private List<Cvli> ListaResfatoFiltradosNorte = new ArrayList<>();
    String dataincialNorte, datafinalNorte, datainicialcusNorte, datafinalcusNorte;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_risp_norte);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoNorte = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLINorte);
        ClRelatorioEstatisticoNorte = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoNorte);
        ClRelatorioSemanalNorte = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstadoNorte);
        RgRelatorioCVLINorte = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstadoNorte);
        TvTipoRelatorioCVLINorte = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstadoNorte);
        TvEscolhaPeriodoCVLINorte = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstadoNorte);
        TvAteCVLINorte = (TextView) findViewById(R.id.tvAteCVLIEstadoNorte);
        BtnGerarRelatorioCVLINorte = (Button) findViewById(R.id.btnGerarRelatorioCVLINorte);
        EdtDtInicialCVLINorte = (EditText) findViewById(R.id.edtDtInicialCVLIEstadoNorte);
        EdtDtFinalCVLINorte = (EditText) findViewById(R.id.edtDtFinalCVLINorte);
        EdtDtInicialCustNorte = (EditText) findViewById(R.id.edtDataInicioCusCvliNorte);
        EdtDtFinalCustNorte = (EditText) findViewById(R.id.edtDataFinalCusCVLINorte);
        EdtTituloRelatorioNorte = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstadoNorte);
        SpTipoRelatorioSemanalNorte = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstadoNorte);
        LvResfatoNorte = (ListView) findViewById(R.id.lvResFatoNorte);
        CkbNaturezaCusNorte = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLINorte);
        CkbMotivacaoCusNorte = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLINorte);
        CkbDistribuicaoCidadeCusNorte = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLINorte);
        CkbDistribuicaoBairroCusNorte = (CheckBox) findViewById(R.id.ckbDistBairCusCVLINorte);
        CkbDetalhamentoCusNorte = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLINorte);
        CkbComparativoAnualCusNorte = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLINorte);
        CkbMeioEmpregadoCusNorte = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLINorte);
        CkbElucidacaoCusNorte = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLINorte);
        CkbZoneamentoCusNorte = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLINorte);
        CkbComparativoPeriodoCusNorte = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLINorte);
        CkbComparativoMensalCusNorte = (CheckBox) findViewById(R.id.ckbCompMesCusCVLINorte);
        CkbIncidenciaSemanaCusNorte = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLINorte);
        CkbIncidenciaHorarioCusNorte = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLINorte);
        CkbRelacaoVitimasCusNorte = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPNorte);


        ArrayAdapter adaptadorTipoRelatorioSemanalNorte = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalNorte.setAdapter(adaptadorTipoRelatorioSemanalNorte);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincialNorte="01/01/2019";
        datafinalNorte="06/01/2019";
        ListaResfatoNorte = cvliDao.retornaResFato(dataincialNorte,datafinalNorte);
        ListaResfatoFiltradosNorte.addAll(ListaResfatoNorte);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltradosNorte);
        LvResfatoNorte.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfatoNorte);

        RgRelatorioCVLINorte.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLINorte = (RadioButton) RgRelatorioCVLINorte.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLINorte.indexOfChild(RbEscolhidoRelatorioCVLINorte);

                if(indiceescolha == 0){
                    ClRelatorioSemanalNorte.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoNorte.setVisibility(View.GONE);
                    ClRelatorioCustomizadoNorte.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLINorte.setText("GERAR RELATÓRIO SEMANAL - RISP NORTE");
                    tiporelatorioNorte = "semanalrisp";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoNorte.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalNorte.setVisibility(View.GONE);
                    ClRelatorioCustomizadoNorte.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLINorte.setText("GERAR RELATÓRIO ESTATÍSTICO - RISP NORTE");
                    tiporelatorioNorte = "acumuladorisp";
                }else{
                    ClRelatorioEstatisticoNorte.setVisibility(View.GONE);
                    ClRelatorioSemanalNorte.setVisibility(View.GONE);
                    ClRelatorioCustomizadoNorte.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLINorte.setText("GERAR RELATÓRIO CUSTOMIZADO - RISP NORTE");
                    tiporelatorioNorte = "customizadorisp";
                }
            }
        });

        CkbNaturezaCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusNorte.isChecked()){
                    NaturezacusNorte = 1;
                }else{
                    NaturezacusNorte = 0;
                }
            }
        });

        CkbRelacaoVitimasCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusNorte.isChecked()){
                    relacaovitimascusNorte = 1;
                }else{
                    relacaovitimascusNorte = 0;
                }
            }
        });

        CkbMotivacaoCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusNorte.isChecked()){
                    MotivacaocusNorte = 1;
                }else{
                    MotivacaocusNorte = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusNorte.isChecked()){
                    DistribuicaocidadecusNorte = 1;
                }else{
                    DistribuicaocidadecusNorte = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusNorte.isChecked()){
                    DistribuicaobairrocusNorte = 1;
                }else{
                    DistribuicaobairrocusNorte = 0;
                }

            }
        });

        CkbDetalhamentoCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusNorte.isChecked()){
                    DetalhamentocusNorte = 1;
                }else{
                    DetalhamentocusNorte = 0;
                }

            }
        });

        CkbComparativoAnualCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusNorte.isChecked()){
                    ComparativoanualcusNorte = 1;
                }else{
                    ComparativoanualcusNorte = 0;
                }

            }
        });

        CkbMeioEmpregadoCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusNorte.isChecked()){
                    MeioempregadocusNorte = 1;
                }else{
                    MeioempregadocusNorte = 0;
                }

            }
        });

        CkbElucidacaoCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusNorte.isChecked()){
                    ElucidacaocusNorte = 1;
                }else{
                    ElucidacaocusNorte = 0;
                }

            }
        });

        CkbZoneamentoCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusNorte.isChecked()){
                    ZoneamentocusNorte = 1;
                }else{
                    ZoneamentocusNorte = 0;
                }

            }
        });

        CkbComparativoPeriodoCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusNorte.isChecked()){
                    ComparativoPeriodocusNorte = 1;
                }else{
                    ComparativoPeriodocusNorte = 0;
                }

            }
        });

        CkbComparativoMensalCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusNorte.isChecked()){
                    ComparativomensalcusNorte = 1;
                }else{
                    ComparativomensalcusNorte = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusNorte.isChecked()){
                    incidenciaSemanacusNorte = 1;
                }else{
                    incidenciaSemanacusNorte = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusNorte.isChecked()){
                    incidenciaHorariocusNorte = 1;
                }else{
                    incidenciaHorariocusNorte = 0;
                }
            }
        });


        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialNorte = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialNorte();
            }
        };

        dataFinalNorte = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalNorte();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusNorte = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusNorte();
            }
        };

        dateFinalCusNorte = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusNorte();
            }
        };


        EdtDtInicialCustNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispNorte.this, dateInicialCusNorte, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioNorte = "customizadorisp";
            }
        });

        EdtDtFinalCustNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispNorte.this, dateFinalCusNorte, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioNorte = "customizadorisp";
            }
        });




        EdtDtInicialCVLINorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispNorte.this, dateInicialNorte, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioNorte = "acumuladorisp";
            }
        });

        EdtDtFinalCVLINorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispNorte.this, dataFinalNorte, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioNorte = "acumuladorisp";
            }
        });


        SpTipoRelatorioSemanalNorte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalNorte = SpTipoRelatorioSemanalNorte.getItemAtPosition(position).toString();
                tiporelatorioNorte = "semanalrisp";

                datafinalNorte = tiporelatoriosemanalNorte.substring(13,23);
                dataincialNorte = tiporelatoriosemanalNorte.substring(0,10);

                ListaResfatoNorte = cvliDao.retornaResFato(dataincialNorte,datafinalNorte);

                ListaResfatoFiltradosNorte.clear();
                ListaResfatoFiltradosNorte.addAll(ListaResfatoNorte);
                LvResfatoNorte.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnGerarRelatorioCVLINorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConected(RelatorioRispNorte.this) == false) {

                    AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispNorte.this);
                    LayoutInflater factory = LayoutInflater.from(RelatorioRispNorte.this);
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
                    if (tiporelatorioNorte == "customizadorisp") {
                        if ((EdtDtInicialCustNorte.getText().toString().equals("")) || (EdtDtFinalCustNorte.getText().toString().equals(""))) {

                            Log.i("Valores ent", "entrou aqui no em branco");
                            AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispNorte.this);
                            LayoutInflater factory = LayoutInflater.from(RelatorioRispNorte.this);
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
                        if (tiporelatorioNorte == "acumuladorisp") {
                            if ((EdtDtInicialCVLINorte.getText().toString().equals("")) || (EdtDtFinalCVLINorte.getText().toString().equals(""))) {

                                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispNorte.this);
                                LayoutInflater factory = LayoutInflater.from(RelatorioRispNorte.this);
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

    private void updateLabelDataInicialNorte(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLINorte.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinalNorte(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLINorte.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusNorte(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustNorte.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusNorte(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustNorte.setText(sdf.format(c1.getTime()));
    }


    private void EnvioDadosRelatorioCVLIRISP() {

        HttpClient httpClient = new DefaultHttpClient();

        String dataIncialCvli = EdtDtInicialCVLINorte.getText().toString();
        String dataFinalCvli = EdtDtFinalCVLINorte.getText().toString();
        String titulorelatorio = EdtTituloRelatorioNorte.getText().toString();
        String dataIncialCvliCus = EdtDtInicialCustNorte.getText().toString();
        String dataFinalCvliCus = EdtDtFinalCustNorte.getText().toString();
        int identificacaorisp = 2;

        try {
            int idservidor = usuarioDao.user.getId();
            String matriculaservidor = usuarioDao.user.getDsMatricula();

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", tiporelatorioNorte));
            itens.add(new BasicNameValuePair("tiporelatorio", tiporelatorioNorte));
            itens.add(new BasicNameValuePair("tituloRelatorio", titulorelatorio));
            itens.add(new BasicNameValuePair("comecoPeriodo", dataIncialCvli));
            itens.add(new BasicNameValuePair("fimPeriodo", dataFinalCvli));
            itens.add(new BasicNameValuePair("comecoPeriodoCus", dataIncialCvliCus));
            itens.add(new BasicNameValuePair("fimPeriodoCus", dataFinalCvliCus));
            itens.add(new BasicNameValuePair("identrisp", String.valueOf(identificacaorisp)));
            itens.add(new BasicNameValuePair("naturezacus", String.valueOf(NaturezacusNorte)));
            itens.add(new BasicNameValuePair("motivacaocus", String.valueOf(MotivacaocusNorte)));
            itens.add(new BasicNameValuePair("distribuicaocidcus", String.valueOf(DistribuicaocidadecusNorte)));
            itens.add(new BasicNameValuePair("districaobairrocus", String.valueOf(DistribuicaobairrocusNorte)));
            itens.add(new BasicNameValuePair("detalhamentocus", String.valueOf(DetalhamentocusNorte)));
            itens.add(new BasicNameValuePair("companualcus", String.valueOf(ComparativoanualcusNorte)));
            itens.add(new BasicNameValuePair("meioempregadocus", String.valueOf(MeioempregadocusNorte)));
            itens.add(new BasicNameValuePair("elucidacaocus", String.valueOf(ElucidacaocusNorte)));
            itens.add(new BasicNameValuePair("zoneamentocus", String.valueOf(ZoneamentocusNorte)));
            itens.add(new BasicNameValuePair("compperiodicocus", String.valueOf(ComparativoPeriodocusNorte)));
            itens.add(new BasicNameValuePair("compmensalcus", String.valueOf(ComparativomensalcusNorte)));
            itens.add(new BasicNameValuePair("incidenciadiasemanacus", String.valueOf(incidenciaSemanacusNorte)));
            itens.add(new BasicNameValuePair("incidenciahorariocus", String.valueOf(incidenciaHorariocusNorte)));
            itens.add(new BasicNameValuePair("relacaovitimacus", String.valueOf(relacaovitimascusNorte)));


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

                                    Intent it = new Intent(RelatorioRispNorte.this, RelatorioGeradoCVLIActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_relatorio_rispnorte, menu);

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
