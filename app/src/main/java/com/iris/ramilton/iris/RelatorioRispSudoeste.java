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

public class RelatorioRispSudoeste extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLISudoeste, TvTipoRelatorioCVLISudoeste, TvEscolhaPeriodoCVLISudoeste, TvAteCVLISudoeste;
    private ListView LvResfatoSudoeste;
    private Button BtnGerarRelatorioCVLISudoeste;
    private RadioGroup RgRelatorioCVLISudoeste;
    private RadioButton RbEscolhidoRelatorioCVLISudoeste;
    private ConstraintLayout ClRelatorioSemanalSudoeste, ClRelatorioEstatisticoSudoeste, ClRelatorioCustomizadoSudoeste;
    private EditText EdtDtInicialCVLISudoeste, EdtDtFinalCVLISudoeste, EdtTituloRelatorioSudoeste, EdtDtInicialCustSudoeste, EdtDtFinalCustSudoeste;
    private Spinner SpTipoRelatorioSemanalSudoeste;
    private CheckBox CkbNaturezaCusSudoeste, CkbMotivacaoCusSudoeste, CkbDistribuicaoCidadeCusSudoeste, CkbDistribuicaoBairroCusSudoeste, CkbDetalhamentoCusSudoeste, CkbComparativoAnualCusSudoeste, CkbMeioEmpregadoCusSudoeste, CkbElucidacaoCusSudoeste, CkbZoneamentoCusSudoeste, CkbComparativoPeriodoCusSudoeste, CkbComparativoMensalCusSudoeste, CkbIncidenciaSemanaCusSudoeste, CkbIncidenciaHorarioCusSudoeste, CkbRelacaoVitimasCusSudoeste;
    private int NaturezacusSudoeste = 0, MotivacaocusSudoeste = 0, DistribuicaocidadecusSudoeste = 0, DistribuicaobairrocusSudoeste = 0, DetalhamentocusSudoeste = 0, ComparativoanualcusSudoeste = 0, MeioempregadocusSudoeste = 0, ElucidacaocusSudoeste = 0, ZoneamentocusSudoeste = 0, ComparativoPeriodocusSudoeste = 0, ComparativomensalcusSudoeste = 0, incidenciaSemanacusSudoeste = 0, incidenciaHorariocusSudoeste = 0, relacaovitimascusSudoeste = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalSudoeste, tiporelatorioSudoeste;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialSudoeste, dataFinalSudoeste, dateInicialCusSudoeste, dateFinalCusSudoeste;
    private ProgressBar carregando;
    private List<Cvli> ListaResfatoSudoeste;
    private List<Cvli> ListaResfatoFiltradosSudoeste = new ArrayList<>();
    String dataincialSudoeste, datafinalSudoeste, datainicialcusSudoeste, datafinalcusSudoeste;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_risp_sudoeste);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoSudoeste = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLISudoeste);
        ClRelatorioEstatisticoSudoeste = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoSudoeste);
        ClRelatorioSemanalSudoeste = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstadoSudoeste);
        RgRelatorioCVLISudoeste = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstadoSudoeste);
        TvTipoRelatorioCVLISudoeste = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstadoSudoeste);
        TvEscolhaPeriodoCVLISudoeste = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstadoSudoeste);
        TvAteCVLISudoeste = (TextView) findViewById(R.id.tvAteCVLIEstadoSudoeste);
        BtnGerarRelatorioCVLISudoeste = (Button) findViewById(R.id.btnGerarRelatorioCVLISudoeste);
        EdtDtInicialCVLISudoeste = (EditText) findViewById(R.id.edtDtInicialCVLIEstadoSudoeste);
        EdtDtFinalCVLISudoeste = (EditText) findViewById(R.id.edtDtFinalCVLISudoeste);
        EdtDtInicialCustSudoeste = (EditText) findViewById(R.id.edtDataInicioCusCvliSudoeste);
        EdtDtFinalCustSudoeste = (EditText) findViewById(R.id.edtDataFinalCusCVLISudoeste);
        EdtTituloRelatorioSudoeste = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstadoSudoeste);
        SpTipoRelatorioSemanalSudoeste = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstadoSudoeste);
        LvResfatoSudoeste = (ListView) findViewById(R.id.lvResFatoSudoeste);
        CkbNaturezaCusSudoeste = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLISudoeste);
        CkbMotivacaoCusSudoeste = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLISudoeste);
        CkbDistribuicaoCidadeCusSudoeste = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLISudoeste);
        CkbDistribuicaoBairroCusSudoeste = (CheckBox) findViewById(R.id.ckbDistBairCusCVLISudoeste);
        CkbDetalhamentoCusSudoeste = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLISudoeste);
        CkbComparativoAnualCusSudoeste = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLISudoeste);
        CkbMeioEmpregadoCusSudoeste = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLISudoeste);
        CkbElucidacaoCusSudoeste = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLISudoeste);
        CkbZoneamentoCusSudoeste = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLISudoeste);
        CkbComparativoPeriodoCusSudoeste = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLISudoeste);
        CkbComparativoMensalCusSudoeste = (CheckBox) findViewById(R.id.ckbCompMesCusCVLISudoeste);
        CkbIncidenciaSemanaCusSudoeste = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLISudoeste);
        CkbIncidenciaHorarioCusSudoeste = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLISudoeste);
        CkbRelacaoVitimasCusSudoeste = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPSudoeste);


        ArrayAdapter adaptadorTipoRelatorioSemanalSudoeste = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalSudoeste.setAdapter(adaptadorTipoRelatorioSemanalSudoeste);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincialSudoeste="01/01/2019";
        datafinalSudoeste="06/01/2019";
        ListaResfatoSudoeste = cvliDao.retornaResFato(dataincialSudoeste,datafinalSudoeste);
        ListaResfatoFiltradosSudoeste.addAll(ListaResfatoSudoeste);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltradosSudoeste);
        LvResfatoSudoeste.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfatoSudoeste);

        RgRelatorioCVLISudoeste.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLISudoeste = (RadioButton) RgRelatorioCVLISudoeste.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLISudoeste.indexOfChild(RbEscolhidoRelatorioCVLISudoeste);

                if(indiceescolha == 0){
                    ClRelatorioSemanalSudoeste.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoSudoeste.setVisibility(View.GONE);
                    ClRelatorioCustomizadoSudoeste.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLISudoeste.setText("GERAR RELATÓRIO SEMANAL - RISP SUDOESTE");
                    tiporelatorioSudoeste = "semanalrisp";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoSudoeste.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalSudoeste.setVisibility(View.GONE);
                    ClRelatorioCustomizadoSudoeste.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLISudoeste.setText("GERAR RELATÓRIO ESTATÍSTICO - RISP SUDOESTE");
                    tiporelatorioSudoeste = "acumuladorisp";
                }else{
                    ClRelatorioEstatisticoSudoeste.setVisibility(View.GONE);
                    ClRelatorioSemanalSudoeste.setVisibility(View.GONE);
                    ClRelatorioCustomizadoSudoeste.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLISudoeste.setText("GERAR RELATÓRIO CUSTOMIZADO - RISP SUDOESTE");
                    tiporelatorioSudoeste = "customizadorisp";
                }
            }
        });

        CkbNaturezaCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusSudoeste.isChecked()){
                    NaturezacusSudoeste = 1;
                }else{
                    NaturezacusSudoeste = 0;
                }
            }
        });

        CkbRelacaoVitimasCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusSudoeste.isChecked()){
                    relacaovitimascusSudoeste = 1;
                }else{
                    relacaovitimascusSudoeste = 0;
                }
            }
        });

        CkbMotivacaoCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusSudoeste.isChecked()){
                    MotivacaocusSudoeste = 1;
                }else{
                    MotivacaocusSudoeste = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusSudoeste.isChecked()){
                    DistribuicaocidadecusSudoeste = 1;
                }else{
                    DistribuicaocidadecusSudoeste = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusSudoeste.isChecked()){
                    DistribuicaobairrocusSudoeste = 1;
                }else{
                    DistribuicaobairrocusSudoeste = 0;
                }

            }
        });

        CkbDetalhamentoCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusSudoeste.isChecked()){
                    DetalhamentocusSudoeste = 1;
                }else{
                    DetalhamentocusSudoeste = 0;
                }

            }
        });

        CkbComparativoAnualCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusSudoeste.isChecked()){
                    ComparativoanualcusSudoeste = 1;
                }else{
                    ComparativoanualcusSudoeste = 0;
                }

            }
        });

        CkbMeioEmpregadoCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusSudoeste.isChecked()){
                    MeioempregadocusSudoeste = 1;
                }else{
                    MeioempregadocusSudoeste = 0;
                }

            }
        });

        CkbElucidacaoCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusSudoeste.isChecked()){
                    ElucidacaocusSudoeste = 1;
                }else{
                    ElucidacaocusSudoeste = 0;
                }

            }
        });

        CkbZoneamentoCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusSudoeste.isChecked()){
                    ZoneamentocusSudoeste = 1;
                }else{
                    ZoneamentocusSudoeste = 0;
                }

            }
        });

        CkbComparativoPeriodoCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusSudoeste.isChecked()){
                    ComparativoPeriodocusSudoeste = 1;
                }else{
                    ComparativoPeriodocusSudoeste = 0;
                }

            }
        });

        CkbComparativoMensalCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusSudoeste.isChecked()){
                    ComparativomensalcusSudoeste = 1;
                }else{
                    ComparativomensalcusSudoeste = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusSudoeste.isChecked()){
                    incidenciaSemanacusSudoeste = 1;
                }else{
                    incidenciaSemanacusSudoeste = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusSudoeste.isChecked()){
                    incidenciaHorariocusSudoeste = 1;
                }else{
                    incidenciaHorariocusSudoeste = 0;
                }
            }
        });


        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialSudoeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialSudoeste();
            }
        };

        dataFinalSudoeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalSudoeste();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusSudoeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusSudoeste();
            }
        };

        dateFinalCusSudoeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusSudoeste();
            }
        };


        EdtDtInicialCustSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispSudoeste.this, dateInicialCusSudoeste, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioSudoeste = "customizadorisp";
            }
        });

        EdtDtFinalCustSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispSudoeste.this, dateFinalCusSudoeste, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioSudoeste = "customizadorisp";
            }
        });




        EdtDtInicialCVLISudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispSudoeste.this, dateInicialSudoeste, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioSudoeste = "acumuladorisp";
            }
        });

        EdtDtFinalCVLISudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispSudoeste.this, dataFinalSudoeste, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioSudoeste = "acumuladorisp";
            }
        });


        SpTipoRelatorioSemanalSudoeste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalSudoeste = SpTipoRelatorioSemanalSudoeste.getItemAtPosition(position).toString();
                tiporelatorioSudoeste = "semanalrisp";

                datafinalSudoeste = tiporelatoriosemanalSudoeste.substring(13,23);
                dataincialSudoeste = tiporelatoriosemanalSudoeste.substring(0,10);

                ListaResfatoSudoeste = cvliDao.retornaResFato(dataincialSudoeste,datafinalSudoeste);

                ListaResfatoFiltradosSudoeste.clear();
                ListaResfatoFiltradosSudoeste.addAll(ListaResfatoSudoeste);
                LvResfatoSudoeste.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnGerarRelatorioCVLISudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConected(RelatorioRispSudoeste.this) == false) {

                    AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispSudoeste.this);
                    LayoutInflater factory = LayoutInflater.from(RelatorioRispSudoeste.this);
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
                    if (tiporelatorioSudoeste == "customizadorisp") {
                        if ((EdtDtInicialCustSudoeste.getText().toString().equals("")) || (EdtDtFinalCustSudoeste.getText().toString().equals(""))) {

                            AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispSudoeste.this);
                            LayoutInflater factory = LayoutInflater.from(RelatorioRispSudoeste.this);
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
                        if (tiporelatorioSudoeste == "acumuladorisp") {
                            if ((EdtDtInicialCVLISudoeste.getText().toString().equals("")) || (EdtDtFinalCVLISudoeste.getText().toString().equals(""))) {

                                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispSudoeste.this);
                                LayoutInflater factory = LayoutInflater.from(RelatorioRispSudoeste.this);
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

    private void updateLabelDataInicialSudoeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLISudoeste.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinalSudoeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLISudoeste.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusSudoeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustSudoeste.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusSudoeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustSudoeste.setText(sdf.format(c1.getTime()));
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

    private void EnvioDadosRelatorioCVLIRISP() {

        HttpClient httpClient = new DefaultHttpClient();

        String dataIncialCvli = EdtDtInicialCVLISudoeste.getText().toString();
        String dataFinalCvli = EdtDtFinalCVLISudoeste.getText().toString();
        String titulorelatorio = EdtTituloRelatorioSudoeste.getText().toString();
        String dataIncialCvliCus = EdtDtInicialCustSudoeste.getText().toString();
        String dataFinalCvliCus = EdtDtFinalCustSudoeste.getText().toString();
        int indentificacaorisp = 7;

        try {
            int idservidor = usuarioDao.user.getId();
            String matriculaservidor = usuarioDao.user.getDsMatricula();

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", tiporelatorioSudoeste));
            itens.add(new BasicNameValuePair("tiporelatorio", tiporelatoriosemanalSudoeste));
            itens.add(new BasicNameValuePair("tituloRelatorio", titulorelatorio));
            itens.add(new BasicNameValuePair("comecoPeriodo", dataIncialCvli));
            itens.add(new BasicNameValuePair("fimPeriodo", dataFinalCvli));
            itens.add(new BasicNameValuePair("comecoPeriodoCus", dataIncialCvliCus));
            itens.add(new BasicNameValuePair("fimPeriodoCus", dataFinalCvliCus));
            itens.add(new BasicNameValuePair("identrisp", String.valueOf(indentificacaorisp)));
            itens.add(new BasicNameValuePair("naturezacus", String.valueOf(NaturezacusSudoeste)));
            itens.add(new BasicNameValuePair("motivacaocus", String.valueOf(MotivacaocusSudoeste)));
            itens.add(new BasicNameValuePair("distribuicaocidcus", String.valueOf(DistribuicaocidadecusSudoeste)));
            itens.add(new BasicNameValuePair("districaobairrocus", String.valueOf(DistribuicaobairrocusSudoeste)));
            itens.add(new BasicNameValuePair("detalhamentocus", String.valueOf(DetalhamentocusSudoeste)));
            itens.add(new BasicNameValuePair("companualcus", String.valueOf(ComparativoanualcusSudoeste)));
            itens.add(new BasicNameValuePair("meioempregadocus", String.valueOf(MeioempregadocusSudoeste)));
            itens.add(new BasicNameValuePair("elucidacaocus", String.valueOf(ElucidacaocusSudoeste)));
            itens.add(new BasicNameValuePair("zoneamentocus", String.valueOf(ZoneamentocusSudoeste)));
            itens.add(new BasicNameValuePair("compperiodicocus", String.valueOf(ComparativoPeriodocusSudoeste)));
            itens.add(new BasicNameValuePair("compmensalcus", String.valueOf(ComparativomensalcusSudoeste)));
            itens.add(new BasicNameValuePair("incidenciadiasemanacus", String.valueOf(incidenciaSemanacusSudoeste)));
            itens.add(new BasicNameValuePair("incidenciahorariocus", String.valueOf(incidenciaHorariocusSudoeste)));
            itens.add(new BasicNameValuePair("relacaovitimacus", String.valueOf(relacaovitimascusSudoeste)));


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

                                    Intent it = new Intent(RelatorioRispSudoeste.this, RelatorioGeradoCVLIActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_relatorio_rispsudoeste, menu);

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
