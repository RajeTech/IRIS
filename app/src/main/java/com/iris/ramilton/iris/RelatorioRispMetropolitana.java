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
import android.view.LayoutInflater;
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

public class RelatorioRispMetropolitana extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLIMetropolitana, TvTipoRelatorioCVLIMetropolitana, TvEscolhaPeriodoCVLIMetropolitana, TvAteCVLIMetropolitana;
    private ListView LvResfatoMetropolitana;
    private Button BtnGerarRelatorioCVLIMetropolitana;
    private RadioGroup RgRelatorioCVLIMetropolitana;
    private RadioButton RbEscolhidoRelatorioCVLIMetropolitana;
    private ConstraintLayout ClRelatorioSemanalMetropolitana, ClRelatorioEstatisticoMetropolitana, ClRelatorioCustomizadoMetropolitana;
    private EditText EdtDtInicialCVLIMetropolitana, EdtDtFinalCVLIMetropolitana, EdtTituloRelatorioMetropolitana, EdtDtInicialCustMetropolitana, EdtDtFinalCustMetropolitana;
    private Spinner SpTipoRelatorioSemanalMetropolitana;
    private CheckBox CkbNaturezaCusMetropolitana, CkbMotivacaoCusMetropolitana, CkbDistribuicaoCidadeCusMetropolitana, CkbDistribuicaoBairroCusMetropolitana, CkbDetalhamentoCusMetropolitana, CkbComparativoAnualCusMetropolitana, CkbMeioEmpregadoCusMetropolitana, CkbElucidacaoCusMetropolitana, CkbZoneamentoCusMetropolitana, CkbComparativoPeriodoCusMetropolitana, CkbComparativoMensalCusMetropolitana, CkbIncidenciaSemanaCusMetropolitana, CkbIncidenciaHorarioCusMetropolitana, CkbRelacaoVitimasCusMetropolitana;
    private int NaturezacusMetropolitana = 0, MotivacaocusMetropolitana = 0, DistribuicaocidadecusMetropolitana = 0, DistribuicaobairrocusMetropolitana = 0, DetalhamentocusMetropolitana = 0, ComparativoanualcusMetropolitana = 0, MeioempregadocusMetropolitana = 0, ElucidacaocusMetropolitana = 0, ZoneamentocusMetropolitana = 0, ComparativoPeriodocusMetropolitana = 0, ComparativomensalcusMetropolitana = 0, incidenciaSemanacusMetropolitana = 0, incidenciaHorariocusMetropolitana = 0, relacaovitimascusMetropolitana = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalMetropolitana, tiporelatorioMetropolitana;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialMetropolitana, dataFinalMetropolitana, dateInicialCusMetropolitana, dateFinalCusMetropolitana;
    private ProgressBar carregando;
    private List<Cvli> ListaResfatoMetropolitana;
    private List<Cvli> ListaResfatoFiltradosMetropolitana = new ArrayList<>();
    String dataincialMetropolitana, datafinalMetropolitana, datainicialcusMetropolitana, datafinalcusMetropolitana;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_risp_metropolitana);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoMetropolitana = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLIMetropolitana);
        ClRelatorioEstatisticoMetropolitana = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoMetropolitana);
        ClRelatorioSemanalMetropolitana = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalMetropolitana);
        RgRelatorioCVLIMetropolitana = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIMetropolitana);
        TvTipoRelatorioCVLIMetropolitana = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioMetropolitana);
        TvEscolhaPeriodoCVLIMetropolitana = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIMetropolitana);
        TvAteCVLIMetropolitana = (TextView) findViewById(R.id.tvAteCVLIMetropolitana);
        BtnGerarRelatorioCVLIMetropolitana = (Button) findViewById(R.id.btnGerarRelatorioCVLIMetropolitana);
        EdtDtInicialCVLIMetropolitana = (EditText) findViewById(R.id.edtDtInicialCVLIMetropolitana);
        EdtDtFinalCVLIMetropolitana = (EditText) findViewById(R.id.edtDtFinalCVLIMetropolitana);
        EdtDtInicialCustMetropolitana = (EditText) findViewById(R.id.edtDataInicioCusCvliMetropolitana);
        EdtDtFinalCustMetropolitana = (EditText) findViewById(R.id.edtDataFinalCusCVLIMetropolitana);
        EdtTituloRelatorioMetropolitana = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoMetropolitana);
        SpTipoRelatorioSemanalMetropolitana = (Spinner) findViewById(R.id.spTipoRelatorioSemanalMetropolitana);
        LvResfatoMetropolitana = (ListView) findViewById(R.id.lvResFatoMetropolitana);
        CkbNaturezaCusMetropolitana = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLIMetropolitana);
        CkbMotivacaoCusMetropolitana = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLIMetropolitana);
        CkbDistribuicaoCidadeCusMetropolitana = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLIMetropolitana);
        CkbDistribuicaoBairroCusMetropolitana = (CheckBox) findViewById(R.id.ckbDistBairCusCVLIMetropolitana);
        CkbDetalhamentoCusMetropolitana = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLIMetropolitana);
        CkbComparativoAnualCusMetropolitana = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLIMetropolitana);
        CkbMeioEmpregadoCusMetropolitana = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLIMetropolitana);
        CkbElucidacaoCusMetropolitana = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLIMetropolitana);
        CkbZoneamentoCusMetropolitana = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLIMetropolitana);
        CkbComparativoPeriodoCusMetropolitana = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLIMetropolitana);
        CkbComparativoMensalCusMetropolitana = (CheckBox) findViewById(R.id.ckbCompMesCusCVLIMetropolitana);
        CkbIncidenciaSemanaCusMetropolitana = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLIMetropolitana);
        CkbIncidenciaHorarioCusMetropolitana = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLIMetropolitana);
        CkbRelacaoVitimasCusMetropolitana = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPMetropolitana);

        ArrayAdapter adaptadorTipoRelatorioSemanalMetropolitana = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalMetropolitana.setAdapter(adaptadorTipoRelatorioSemanalMetropolitana);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincialMetropolitana="01/01/2019";
        datafinalMetropolitana="06/01/2019";
        ListaResfatoMetropolitana = cvliDao.retornaResFato(dataincialMetropolitana,datafinalMetropolitana);
        ListaResfatoFiltradosMetropolitana.addAll(ListaResfatoMetropolitana);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltradosMetropolitana);
        LvResfatoMetropolitana.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfatoMetropolitana);

        RgRelatorioCVLIMetropolitana.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLIMetropolitana = (RadioButton) RgRelatorioCVLIMetropolitana.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLIMetropolitana.indexOfChild(RbEscolhidoRelatorioCVLIMetropolitana);

                if(indiceescolha == 0){
                    ClRelatorioSemanalMetropolitana.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoMetropolitana.setVisibility(View.GONE);
                    ClRelatorioCustomizadoMetropolitana.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIMetropolitana.setText("GERAR RELATÓRIO SEMANAL - RISP REGIÃO METROPOLITANA");
                    tiporelatorioMetropolitana = "semanalrisp";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoMetropolitana.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalMetropolitana.setVisibility(View.GONE);
                    ClRelatorioCustomizadoMetropolitana.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIMetropolitana.setText("GERAR RELATÓRIO ESTATÍSTICO - RISP REGIÃO METROPOLITANA");
                    tiporelatorioMetropolitana = "acumuladorisp";
                }else{
                    ClRelatorioEstatisticoMetropolitana.setVisibility(View.GONE);
                    ClRelatorioSemanalMetropolitana.setVisibility(View.GONE);
                    ClRelatorioCustomizadoMetropolitana.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLIMetropolitana.setText("GERAR RELATÓRIO CUSTOMIZADO - RISP REIGÃO METROPOLITANA");
                    tiporelatorioMetropolitana = "customizadorisp";
                }
            }
        });

        CkbNaturezaCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusMetropolitana.isChecked()){
                    NaturezacusMetropolitana = 1;
                }else{
                    NaturezacusMetropolitana = 0;
                }
            }
        });

        CkbRelacaoVitimasCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusMetropolitana.isChecked()){
                    relacaovitimascusMetropolitana = 1;
                }else{
                    relacaovitimascusMetropolitana = 0;
                }
            }
        });

        CkbMotivacaoCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusMetropolitana.isChecked()){
                    MotivacaocusMetropolitana = 1;
                }else{
                    MotivacaocusMetropolitana = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusMetropolitana.isChecked()){
                    DistribuicaocidadecusMetropolitana = 1;
                }else{
                    DistribuicaocidadecusMetropolitana = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusMetropolitana.isChecked()){
                    DistribuicaobairrocusMetropolitana = 1;
                }else{
                    DistribuicaobairrocusMetropolitana = 0;
                }

            }
        });

        CkbDetalhamentoCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusMetropolitana.isChecked()){
                    DetalhamentocusMetropolitana = 1;
                }else{
                    DetalhamentocusMetropolitana = 0;
                }

            }
        });

        CkbComparativoAnualCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusMetropolitana.isChecked()){
                    ComparativoanualcusMetropolitana = 1;
                }else{
                    ComparativoanualcusMetropolitana = 0;
                }

            }
        });

        CkbMeioEmpregadoCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusMetropolitana.isChecked()){
                    MeioempregadocusMetropolitana = 1;
                }else{
                    MeioempregadocusMetropolitana = 0;
                }

            }
        });

        CkbElucidacaoCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusMetropolitana.isChecked()){
                    ElucidacaocusMetropolitana = 1;
                }else{
                    ElucidacaocusMetropolitana = 0;
                }

            }
        });

        CkbZoneamentoCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusMetropolitana.isChecked()){
                    ZoneamentocusMetropolitana = 1;
                }else{
                    ZoneamentocusMetropolitana = 0;
                }

            }
        });

        CkbComparativoPeriodoCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusMetropolitana.isChecked()){
                    ComparativoPeriodocusMetropolitana = 1;
                }else{
                    ComparativoPeriodocusMetropolitana = 0;
                }

            }
        });

        CkbComparativoMensalCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusMetropolitana.isChecked()){
                    ComparativomensalcusMetropolitana = 1;
                }else{
                    ComparativomensalcusMetropolitana = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusMetropolitana.isChecked()){
                    incidenciaSemanacusMetropolitana = 1;
                }else{
                    incidenciaSemanacusMetropolitana = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusMetropolitana.isChecked()){
                    incidenciaHorariocusMetropolitana = 1;
                }else{
                    incidenciaHorariocusMetropolitana = 0;
                }
            }
        });

        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialMetropolitana = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialMetropolitana();
            }
        };

        dataFinalMetropolitana = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalMetropolitana();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusMetropolitana = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusMetropolitana();
            }
        };

        dateFinalCusMetropolitana = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusMetropolitana();
            }
        };


        EdtDtInicialCustMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispMetropolitana.this, dateInicialCusMetropolitana, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioMetropolitana = "customizadorisp";
            }
        });

        EdtDtFinalCustMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispMetropolitana.this, dateFinalCusMetropolitana, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioMetropolitana = "customizadorisp";
            }
        });




        EdtDtInicialCVLIMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispMetropolitana.this, dateInicialMetropolitana, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioMetropolitana = "acumuladorisp";
            }
        });

        EdtDtFinalCVLIMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispMetropolitana.this, dataFinalMetropolitana, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioMetropolitana = "acumuladorisp";
            }
        });

        SpTipoRelatorioSemanalMetropolitana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalMetropolitana = SpTipoRelatorioSemanalMetropolitana.getItemAtPosition(position).toString();
                tiporelatorioMetropolitana = "semanalrisp";

                datafinalMetropolitana = tiporelatoriosemanalMetropolitana.substring(13,23);
                dataincialMetropolitana = tiporelatoriosemanalMetropolitana.substring(0,10);

                ListaResfatoMetropolitana = cvliDao.retornaResFato(dataincialMetropolitana,datafinalMetropolitana);

                ListaResfatoFiltradosMetropolitana.clear();
                ListaResfatoFiltradosMetropolitana.addAll(ListaResfatoMetropolitana);
                LvResfatoMetropolitana.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnGerarRelatorioCVLIMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConected(RelatorioRispMetropolitana.this) == false) {

                    AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispMetropolitana.this);
                    LayoutInflater factory = LayoutInflater.from(RelatorioRispMetropolitana.this);
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
                    if (tiporelatorioMetropolitana == "customizadorisp") {
                        if ((EdtDtInicialCustMetropolitana.getText().toString().equals("")) || (EdtDtFinalCustMetropolitana.getText().toString().equals(""))) {

                            AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispMetropolitana.this);
                            LayoutInflater factory = LayoutInflater.from(RelatorioRispMetropolitana.this);
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
                        if (tiporelatorioMetropolitana == "acumuladorisp") {
                            if ((EdtDtInicialCVLIMetropolitana.getText().toString().equals("")) || (EdtDtFinalCVLIMetropolitana.getText().toString().equals(""))) {

                                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispMetropolitana.this);
                                LayoutInflater factory = LayoutInflater.from(RelatorioRispMetropolitana.this);
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

    private void updateLabelDataInicialMetropolitana(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLIMetropolitana.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinalMetropolitana(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLIMetropolitana.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusMetropolitana(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustMetropolitana.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusMetropolitana(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustMetropolitana.setText(sdf.format(c1.getTime()));
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

        String dataIncialCvli = EdtDtInicialCVLIMetropolitana.getText().toString();
        String dataFinalCvli = EdtDtFinalCVLIMetropolitana.getText().toString();
        String titulorelatorio = EdtTituloRelatorioMetropolitana.getText().toString();
        String dataIncialCvliCus = EdtDtInicialCustMetropolitana.getText().toString();
        String dataFinalCvliCus = EdtDtFinalCustMetropolitana.getText().toString();
        int identrisp = 8;

        try {
            int idservidor = usuarioDao.user.getId();
            String matriculaservidor = usuarioDao.user.getDsMatricula();

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", tiporelatorioMetropolitana));
            itens.add(new BasicNameValuePair("tiporelatorio", tiporelatoriosemanalMetropolitana));
            itens.add(new BasicNameValuePair("tituloRelatorio", titulorelatorio));
            itens.add(new BasicNameValuePair("comecoPeriodo", dataIncialCvli));
            itens.add(new BasicNameValuePair("fimPeriodo", dataFinalCvli));
            itens.add(new BasicNameValuePair("comecoPeriodoCus", dataIncialCvliCus));
            itens.add(new BasicNameValuePair("fimPeriodoCus", dataFinalCvliCus));
            itens.add(new BasicNameValuePair("identrisp", String.valueOf(identrisp)));
            itens.add(new BasicNameValuePair("naturezacus", String.valueOf(NaturezacusMetropolitana)));
            itens.add(new BasicNameValuePair("motivacaocus", String.valueOf(MotivacaocusMetropolitana)));
            itens.add(new BasicNameValuePair("distribuicaocidcus", String.valueOf(DistribuicaocidadecusMetropolitana)));
            itens.add(new BasicNameValuePair("districaobairrocus", String.valueOf(DistribuicaobairrocusMetropolitana)));
            itens.add(new BasicNameValuePair("detalhamentocus", String.valueOf(DetalhamentocusMetropolitana)));
            itens.add(new BasicNameValuePair("companualcus", String.valueOf(ComparativoanualcusMetropolitana)));
            itens.add(new BasicNameValuePair("meioempregadocus", String.valueOf(MeioempregadocusMetropolitana)));
            itens.add(new BasicNameValuePair("elucidacaocus", String.valueOf(ElucidacaocusMetropolitana)));
            itens.add(new BasicNameValuePair("zoneamentocus", String.valueOf(ZoneamentocusMetropolitana)));
            itens.add(new BasicNameValuePair("compperiodicocus", String.valueOf(ComparativoPeriodocusMetropolitana)));
            itens.add(new BasicNameValuePair("compmensalcus", String.valueOf(ComparativomensalcusMetropolitana)));
            itens.add(new BasicNameValuePair("incidenciadiasemanacus", String.valueOf(incidenciaSemanacusMetropolitana)));
            itens.add(new BasicNameValuePair("incidenciahorariocus", String.valueOf(incidenciaHorariocusMetropolitana)));
            itens.add(new BasicNameValuePair("relacaovitimacus", String.valueOf(relacaovitimascusMetropolitana)));


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

                                    Intent it = new Intent(RelatorioRispMetropolitana.this, RelatorioGeradoCVLIActivity.class);
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
}
