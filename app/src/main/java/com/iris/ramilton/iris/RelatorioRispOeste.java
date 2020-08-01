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

public class RelatorioRispOeste extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLIOeste, TvTipoRelatorioCVLIOeste, TvEscolhaPeriodoCVLIOeste, TvAteCVLIOeste;
    private ListView LvResfatoOeste;
    private Button BtnGerarRelatorioCVLIOeste;
    private RadioGroup RgRelatorioCVLIOeste;
    private RadioButton RbEscolhidoRelatorioCVLIOeste;
    private ConstraintLayout ClRelatorioSemanalOeste, ClRelatorioEstatisticoOeste, ClRelatorioCustomizadoOeste;
    private EditText EdtDtInicialCVLIOeste, EdtDtFinalCVLIOeste, EdtTituloRelatorioOeste, EdtDtInicialCustOeste, EdtDtFinalCustOeste;
    private Spinner SpTipoRelatorioSemanalOeste;
    private CheckBox CkbNaturezaCusOeste, CkbMotivacaoCusOeste, CkbDistribuicaoCidadeCusOeste, CkbDistribuicaoBairroCusOeste, CkbDetalhamentoCusOeste, CkbComparativoAnualCusOeste, CkbMeioEmpregadoCusOeste, CkbElucidacaoCusOeste, CkbZoneamentoCusOeste, CkbComparativoPeriodoCusOeste, CkbComparativoMensalCusOeste, CkbIncidenciaSemanaCusOeste, CkbIncidenciaHorarioCusOeste, CkbRelacaoVitimasCusOeste;
    private int NaturezacusOeste = 0, MotivacaocusOeste = 0, DistribuicaocidadecusOeste = 0, DistribuicaobairrocusOeste = 0, DetalhamentocusOeste = 0, ComparativoanualcusOeste = 0, MeioempregadocusOeste = 0, ElucidacaocusOeste = 0, ZoneamentocusOeste = 0, ComparativoPeriodocusOeste = 0, ComparativomensalcusOeste = 0, incidenciaSemanacusOeste = 0, incidenciaHorariocusOeste = 0, relacaovitimascusOeste = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalOeste, tiporelatorioOeste;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialOeste, dataFinalOeste, dateInicialCusOeste, dateFinalCusOeste;
    private ProgressBar carregando;
    private List<Cvli> ListaResfatoOeste;
    private List<Cvli> ListaResfatoFiltradosOeste = new ArrayList<>();
    String dataincialOeste, datafinalOeste, datainicialcusOeste, datafinalcusOeste;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_risp_oeste);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoOeste = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLIOeste);
        ClRelatorioEstatisticoOeste = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoOeste);
        ClRelatorioSemanalOeste = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstadoOeste);
        RgRelatorioCVLIOeste = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstadoOeste);
        TvTipoRelatorioCVLIOeste = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstadoOeste);
        TvEscolhaPeriodoCVLIOeste = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstadoOeste);
        TvAteCVLIOeste = (TextView) findViewById(R.id.tvAteCVLIEstadoOeste);
        BtnGerarRelatorioCVLIOeste = (Button) findViewById(R.id.btnGerarRelatorioCVLIOeste);
        EdtDtInicialCVLIOeste = (EditText) findViewById(R.id.edtDtInicialCVLIEstadoOeste);
        EdtDtFinalCVLIOeste = (EditText) findViewById(R.id.edtDtFinalCVLIOeste);
        EdtDtInicialCustOeste = (EditText) findViewById(R.id.edtDataInicioCusCvliOeste);
        EdtDtFinalCustOeste = (EditText) findViewById(R.id.edtDataFinalCusCVLIOeste);
        EdtTituloRelatorioOeste = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstadoOeste);
        SpTipoRelatorioSemanalOeste = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstadoOeste);
        LvResfatoOeste = (ListView) findViewById(R.id.lvResFatoOeste);
        CkbNaturezaCusOeste = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLIOeste);
        CkbMotivacaoCusOeste = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLIOeste);
        CkbDistribuicaoCidadeCusOeste = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLIOeste);
        CkbDistribuicaoBairroCusOeste = (CheckBox) findViewById(R.id.ckbDistBairCusCVLIOeste);
        CkbDetalhamentoCusOeste = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLIOeste);
        CkbComparativoAnualCusOeste = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLIOeste);
        CkbMeioEmpregadoCusOeste = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLIOeste);
        CkbElucidacaoCusOeste = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLIOeste);
        CkbZoneamentoCusOeste = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLIOeste);
        CkbComparativoPeriodoCusOeste = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLIOeste);
        CkbComparativoMensalCusOeste = (CheckBox) findViewById(R.id.ckbCompMesCusCVLIOeste);
        CkbIncidenciaSemanaCusOeste = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLIOeste);
        CkbIncidenciaHorarioCusOeste = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLIOeste);
        CkbRelacaoVitimasCusOeste = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPOeste);


        ArrayAdapter adaptadorTipoRelatorioSemanalOeste = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalOeste.setAdapter(adaptadorTipoRelatorioSemanalOeste);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincialOeste="01/01/2019";
        datafinalOeste="06/01/2019";
        ListaResfatoOeste = cvliDao.retornaResFato(dataincialOeste,datafinalOeste);
        ListaResfatoFiltradosOeste.addAll(ListaResfatoOeste);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltradosOeste);
        LvResfatoOeste.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfatoOeste);

        RgRelatorioCVLIOeste.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLIOeste = (RadioButton) RgRelatorioCVLIOeste.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLIOeste.indexOfChild(RbEscolhidoRelatorioCVLIOeste);

                if(indiceescolha == 0){
                    ClRelatorioSemanalOeste.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoOeste.setVisibility(View.GONE);
                    ClRelatorioCustomizadoOeste.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIOeste.setText("GERAR RELATÓRIO SEMANAL - RISP OESTE");
                    tiporelatorioOeste = "semanalrisp";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoOeste.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalOeste.setVisibility(View.GONE);
                    ClRelatorioCustomizadoOeste.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIOeste.setText("GERAR RELATÓRIO ESTATÍSTICO - RISP OESTE");
                    tiporelatorioOeste = "acumuladorisp";
                }else{
                    ClRelatorioEstatisticoOeste.setVisibility(View.GONE);
                    ClRelatorioSemanalOeste.setVisibility(View.GONE);
                    ClRelatorioCustomizadoOeste.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLIOeste.setText("GERAR RELATÓRIO CUSTOMIZADO - RISP OESTE");
                    tiporelatorioOeste = "customizadorisp";
                }
            }
        });

        CkbNaturezaCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusOeste.isChecked()){
                    NaturezacusOeste = 1;
                }else{
                    NaturezacusOeste = 0;
                }
            }
        });

        CkbRelacaoVitimasCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusOeste.isChecked()){
                    relacaovitimascusOeste = 1;
                }else{
                    relacaovitimascusOeste = 0;
                }
            }
        });

        CkbMotivacaoCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusOeste.isChecked()){
                    MotivacaocusOeste = 1;
                }else{
                    MotivacaocusOeste = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusOeste.isChecked()){
                    DistribuicaocidadecusOeste = 1;
                }else{
                    DistribuicaocidadecusOeste = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusOeste.isChecked()){
                    DistribuicaobairrocusOeste = 1;
                }else{
                    DistribuicaobairrocusOeste = 0;
                }

            }
        });

        CkbDetalhamentoCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusOeste.isChecked()){
                    DetalhamentocusOeste = 1;
                }else{
                    DetalhamentocusOeste = 0;
                }

            }
        });

        CkbComparativoAnualCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusOeste.isChecked()){
                    ComparativoanualcusOeste = 1;
                }else{
                    ComparativoanualcusOeste = 0;
                }

            }
        });

        CkbMeioEmpregadoCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusOeste.isChecked()){
                    MeioempregadocusOeste = 1;
                }else{
                    MeioempregadocusOeste = 0;
                }

            }
        });

        CkbElucidacaoCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusOeste.isChecked()){
                    ElucidacaocusOeste = 1;
                }else{
                    ElucidacaocusOeste = 0;
                }

            }
        });

        CkbZoneamentoCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusOeste.isChecked()){
                    ZoneamentocusOeste = 1;
                }else{
                    ZoneamentocusOeste = 0;
                }

            }
        });

        CkbComparativoPeriodoCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusOeste.isChecked()){
                    ComparativoPeriodocusOeste = 1;
                }else{
                    ComparativoPeriodocusOeste = 0;
                }

            }
        });

        CkbComparativoMensalCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusOeste.isChecked()){
                    ComparativomensalcusOeste = 1;
                }else{
                    ComparativomensalcusOeste = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusOeste.isChecked()){
                    incidenciaSemanacusOeste = 1;
                }else{
                    incidenciaSemanacusOeste = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusOeste.isChecked()){
                    incidenciaHorariocusOeste = 1;
                }else{
                    incidenciaHorariocusOeste = 0;
                }
            }
        });


        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialOeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialOeste();
            }
        };

        dataFinalOeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalOeste();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusOeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusOeste();
            }
        };

        dateFinalCusOeste = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusOeste();
            }
        };


        EdtDtInicialCustOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispOeste.this, dateInicialCusOeste, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioOeste = "customizadorisp";
            }
        });

        EdtDtFinalCustOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispOeste.this, dateFinalCusOeste, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioOeste = "customizadorisp";
            }
        });




        EdtDtInicialCVLIOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispOeste.this, dateInicialOeste, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioOeste = "acumuladorisp";
            }
        });

        EdtDtFinalCVLIOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispOeste.this, dataFinalOeste, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioOeste = "acumuladorisp";
            }
        });


        SpTipoRelatorioSemanalOeste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalOeste = SpTipoRelatorioSemanalOeste.getItemAtPosition(position).toString();
                tiporelatorioOeste = "semanalrisp";

                datafinalOeste = tiporelatoriosemanalOeste.substring(13,23);
                dataincialOeste = tiporelatoriosemanalOeste.substring(0,10);

                ListaResfatoOeste = cvliDao.retornaResFato(dataincialOeste,datafinalOeste);

                ListaResfatoFiltradosOeste.clear();
                ListaResfatoFiltradosOeste.addAll(ListaResfatoOeste);
                LvResfatoOeste.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnGerarRelatorioCVLIOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConected(RelatorioRispOeste.this) == false) {

                    AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispOeste.this);
                    LayoutInflater factory = LayoutInflater.from(RelatorioRispOeste.this);
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
                    if (tiporelatorioOeste == "customizadorisp") {
                        if ((EdtDtInicialCustOeste.getText().toString().equals("")) || (EdtDtFinalCustOeste.getText().toString().equals(""))) {

                            Log.i("Valores ent", "entrou aqui no em branco");
                            AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispOeste.this);
                            LayoutInflater factory = LayoutInflater.from(RelatorioRispOeste.this);
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
                        if (tiporelatorioOeste == "acumuladorisp") {
                            if ((EdtDtInicialCVLIOeste.getText().toString().equals("")) || (EdtDtFinalCVLIOeste.getText().toString().equals(""))) {

                                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispOeste.this);
                                LayoutInflater factory = LayoutInflater.from(RelatorioRispOeste.this);
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

    private void updateLabelDataInicialOeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLIOeste.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinalOeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLIOeste.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusOeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustOeste.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusOeste(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustOeste.setText(sdf.format(c1.getTime()));
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
        getMenuInflater().inflate(R.menu.menu_relatorio_rispoeste, menu);

        return true;
    }

    private void EnvioDadosRelatorioCVLIRISP() {

        HttpClient httpClient = new DefaultHttpClient();

        String dataIncialCvli = EdtDtInicialCVLIOeste.getText().toString();
        String dataFinalCvli = EdtDtFinalCVLIOeste.getText().toString();
        String titulorelatorio = EdtTituloRelatorioOeste.getText().toString();
        String dataIncialCvliCus = EdtDtInicialCustOeste.getText().toString();
        String dataFinalCvliCus = EdtDtFinalCustOeste.getText().toString();
        int identificacaorisp = 4;

        try {
            int idservidor = usuarioDao.user.getId();
            String matriculaservidor = usuarioDao.user.getDsMatricula();

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", tiporelatorioOeste));
            itens.add(new BasicNameValuePair("tiporelatorio", tiporelatoriosemanalOeste));
            itens.add(new BasicNameValuePair("tituloRelatorio", titulorelatorio));
            itens.add(new BasicNameValuePair("comecoPeriodo", dataIncialCvli));
            itens.add(new BasicNameValuePair("fimPeriodo", dataFinalCvli));
            itens.add(new BasicNameValuePair("comecoPeriodoCus", dataIncialCvliCus));
            itens.add(new BasicNameValuePair("fimPeriodoCus", dataFinalCvliCus));
            itens.add(new BasicNameValuePair("identrisp", String.valueOf(identificacaorisp)));
            itens.add(new BasicNameValuePair("naturezacus", String.valueOf(NaturezacusOeste)));
            itens.add(new BasicNameValuePair("motivacaocus", String.valueOf(MotivacaocusOeste)));
            itens.add(new BasicNameValuePair("distribuicaocidcus", String.valueOf(DistribuicaocidadecusOeste)));
            itens.add(new BasicNameValuePair("districaobairrocus", String.valueOf(DistribuicaobairrocusOeste)));
            itens.add(new BasicNameValuePair("detalhamentocus", String.valueOf(DetalhamentocusOeste)));
            itens.add(new BasicNameValuePair("companualcus", String.valueOf(ComparativoanualcusOeste)));
            itens.add(new BasicNameValuePair("meioempregadocus", String.valueOf(MeioempregadocusOeste)));
            itens.add(new BasicNameValuePair("elucidacaocus", String.valueOf(ElucidacaocusOeste)));
            itens.add(new BasicNameValuePair("zoneamentocus", String.valueOf(ZoneamentocusOeste)));
            itens.add(new BasicNameValuePair("compperiodicocus", String.valueOf(ComparativoPeriodocusOeste)));
            itens.add(new BasicNameValuePair("compmensalcus", String.valueOf(ComparativomensalcusOeste)));
            itens.add(new BasicNameValuePair("incidenciadiasemanacus", String.valueOf(incidenciaSemanacusOeste)));
            itens.add(new BasicNameValuePair("incidenciahorariocus", String.valueOf(incidenciaHorariocusOeste)));
            itens.add(new BasicNameValuePair("relacaovitimacus", String.valueOf(relacaovitimascusOeste)));


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

                                    Intent it = new Intent(RelatorioRispOeste.this, RelatorioGeradoCVLIActivity.class);
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
