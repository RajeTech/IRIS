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

public class RelatorioRispSul extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLISul, TvTipoRelatorioCVLISul, TvEscolhaPeriodoCVLISul, TvAteCVLISul;
    private ListView LvResfatoSul;
    private Button BtnGerarRelatorioCVLISul;
    private RadioGroup RgRelatorioCVLISul;
    private RadioButton RbEscolhidoRelatorioCVLISul;
    private ConstraintLayout ClRelatorioSemanalSul, ClRelatorioEstatisticoSul, ClRelatorioCustomizadoSul;
    private EditText EdtDtInicialCVLISul, EdtDtFinalCVLISul, EdtTituloRelatorioSul, EdtDtInicialCustSul, EdtDtFinalCustSul;
    private Spinner SpTipoRelatorioSemanalSul;
    private CheckBox CkbNaturezaCusSul, CkbMotivacaoCusSul, CkbDistribuicaoCidadeCusSul, CkbDistribuicaoBairroCusSul, CkbDetalhamentoCusSul, CkbComparativoAnualCusSul, CkbMeioEmpregadoCusSul, CkbElucidacaoCusSul, CkbZoneamentoCusSul, CkbComparativoPeriodoCusSul, CkbComparativoMensalCusSul, CkbIncidenciaSemanaCusSul, CkbIncidenciaHorarioCusSul, CkbRelacaoVitimasCusSul;
    private int NaturezacusSul = 0, MotivacaocusSul = 0, DistribuicaocidadecusSul = 0, DistribuicaobairrocusSul = 0, DetalhamentocusSul = 0, ComparativoanualcusSul = 0, MeioempregadocusSul = 0, ElucidacaocusSul = 0, ZoneamentocusSul = 0, ComparativoPeriodocusSul = 0, ComparativomensalcusSul = 0, incidenciaSemanacusSul = 0, incidenciaHorariocusSul = 0, relacaovitimascusSul = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalSul, tiporelatorioSul;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialSul, dataFinalSul, dateInicialCusSul, dateFinalCusSul;
    private ProgressBar carregando;
    private List<Cvli> ListaResfatoSul;
    private List<Cvli> ListaResfatoFiltradosSul = new ArrayList<>();
    String dataincialSul, datafinalSul, datainicialcusSul, datafinalcusSul;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_risp_sul);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoSul = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLISul);
        ClRelatorioEstatisticoSul = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoSul);
        ClRelatorioSemanalSul = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstadoSul);
        RgRelatorioCVLISul = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstadoSul);
        TvTipoRelatorioCVLISul = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstadoSul);
        TvEscolhaPeriodoCVLISul = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstadoSul);
        TvAteCVLISul = (TextView) findViewById(R.id.tvAteCVLIEstadoSul);
        BtnGerarRelatorioCVLISul = (Button) findViewById(R.id.btnGerarRelatorioCVLISul);
        EdtDtInicialCVLISul = (EditText) findViewById(R.id.edtDtInicialCVLIEstadoSul);
        EdtDtFinalCVLISul = (EditText) findViewById(R.id.edtDtFinalCVLISul);
        EdtDtInicialCustSul = (EditText) findViewById(R.id.edtDataInicioCusCvliSul);
        EdtDtFinalCustSul = (EditText) findViewById(R.id.edtDataFinalCusCVLISul);
        EdtTituloRelatorioSul = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstadoSul);
        SpTipoRelatorioSemanalSul = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstadoSul);
        LvResfatoSul = (ListView) findViewById(R.id.lvResFatoSul);
        CkbNaturezaCusSul = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLISul);
        CkbMotivacaoCusSul = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLISul);
        CkbDistribuicaoCidadeCusSul = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLISul);
        CkbDistribuicaoBairroCusSul = (CheckBox) findViewById(R.id.ckbDistBairCusCVLISul);
        CkbDetalhamentoCusSul = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLISul);
        CkbComparativoAnualCusSul = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLISul);
        CkbMeioEmpregadoCusSul = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLISul);
        CkbElucidacaoCusSul = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLISul);
        CkbZoneamentoCusSul = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLISul);
        CkbComparativoPeriodoCusSul = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLISul);
        CkbComparativoMensalCusSul = (CheckBox) findViewById(R.id.ckbCompMesCusCVLISul);
        CkbIncidenciaSemanaCusSul = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLISul);
        CkbIncidenciaHorarioCusSul = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLISul);
        CkbRelacaoVitimasCusSul = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPSul);


        ArrayAdapter adaptadorTipoRelatorioSemanalSul = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalSul.setAdapter(adaptadorTipoRelatorioSemanalSul);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincialSul="01/01/2019";
        datafinalSul="06/01/2019";
        ListaResfatoSul = cvliDao.retornaResFato(dataincialSul,datafinalSul);
        ListaResfatoFiltradosSul.addAll(ListaResfatoSul);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltradosSul);
        LvResfatoSul.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfatoSul);

        RgRelatorioCVLISul.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLISul = (RadioButton) RgRelatorioCVLISul.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLISul.indexOfChild(RbEscolhidoRelatorioCVLISul);

                if(indiceescolha == 0){
                    ClRelatorioSemanalSul.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoSul.setVisibility(View.GONE);
                    ClRelatorioCustomizadoSul.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLISul.setText("GERAR RELATÓRIO SEMANAL - RISP SUL");
                    tiporelatorioSul = "semanalrisp";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoSul.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalSul.setVisibility(View.GONE);
                    ClRelatorioCustomizadoSul.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLISul.setText("GERAR RELATÓRIO ESTATÍSTICO - RISP SUL");
                    tiporelatorioSul = "acumuladorisp";
                }else{
                    ClRelatorioEstatisticoSul.setVisibility(View.GONE);
                    ClRelatorioSemanalSul.setVisibility(View.GONE);
                    ClRelatorioCustomizadoSul.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLISul.setText("GERAR RELATÓRIO CUSTOMIZADO - RISP SUL");
                    tiporelatorioSul = "customizadorisp";
                }
            }
        });

        CkbNaturezaCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusSul.isChecked()){
                    NaturezacusSul = 1;
                }else{
                    NaturezacusSul = 0;
                }
            }
        });

        CkbRelacaoVitimasCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusSul.isChecked()){
                    relacaovitimascusSul = 1;
                }else{
                    relacaovitimascusSul = 0;
                }
            }
        });

        CkbMotivacaoCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusSul.isChecked()){
                    MotivacaocusSul = 1;
                }else{
                    MotivacaocusSul = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusSul.isChecked()){
                    DistribuicaocidadecusSul = 1;
                }else{
                    DistribuicaocidadecusSul = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusSul.isChecked()){
                    DistribuicaobairrocusSul = 1;
                }else{
                    DistribuicaobairrocusSul = 0;
                }

            }
        });

        CkbDetalhamentoCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusSul.isChecked()){
                    DetalhamentocusSul = 1;
                }else{
                    DetalhamentocusSul = 0;
                }

            }
        });

        CkbComparativoAnualCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusSul.isChecked()){
                    ComparativoanualcusSul = 1;
                }else{
                    ComparativoanualcusSul = 0;
                }

            }
        });

        CkbMeioEmpregadoCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusSul.isChecked()){
                    MeioempregadocusSul = 1;
                }else{
                    MeioempregadocusSul = 0;
                }

            }
        });

        CkbElucidacaoCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusSul.isChecked()){
                    ElucidacaocusSul = 1;
                }else{
                    ElucidacaocusSul = 0;
                }

            }
        });

        CkbZoneamentoCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusSul.isChecked()){
                    ZoneamentocusSul = 1;
                }else{
                    ZoneamentocusSul = 0;
                }

            }
        });

        CkbComparativoPeriodoCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusSul.isChecked()){
                    ComparativoPeriodocusSul = 1;
                }else{
                    ComparativoPeriodocusSul = 0;
                }

            }
        });

        CkbComparativoMensalCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusSul.isChecked()){
                    ComparativomensalcusSul = 1;
                }else{
                    ComparativomensalcusSul = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusSul.isChecked()){
                    incidenciaSemanacusSul = 1;
                }else{
                    incidenciaSemanacusSul = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusSul.isChecked()){
                    incidenciaHorariocusSul = 1;
                }else{
                    incidenciaHorariocusSul = 0;
                }
            }
        });


        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialSul = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialSul();
            }
        };

        dataFinalSul = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalSul();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusSul = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusSul();
            }
        };

        dateFinalCusSul = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusSul();
            }
        };


        EdtDtInicialCustSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispSul.this, dateInicialCusSul, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioSul = "customizadorisp";
            }
        });

        EdtDtFinalCustSul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispSul.this, dateFinalCusSul, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioSul = "customizadorisp";
            }
        });




        EdtDtInicialCVLISul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispSul.this, dateInicialSul, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioSul = "acumuladorisp";
            }
        });

        EdtDtFinalCVLISul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispSul.this, dataFinalSul, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioSul = "acumuladorisp";
            }
        });


        SpTipoRelatorioSemanalSul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalSul = SpTipoRelatorioSemanalSul.getItemAtPosition(position).toString();
                tiporelatorioSul = "semanalrisp";

                datafinalSul = tiporelatoriosemanalSul.substring(13,23);
                dataincialSul = tiporelatoriosemanalSul.substring(0,10);

                ListaResfatoSul = cvliDao.retornaResFato(dataincialSul,datafinalSul);

                ListaResfatoFiltradosSul.clear();
                ListaResfatoFiltradosSul.addAll(ListaResfatoSul);
                LvResfatoSul.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnGerarRelatorioCVLISul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConected(RelatorioRispSul.this) == false) {

                    AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispSul.this);
                    LayoutInflater factory = LayoutInflater.from(RelatorioRispSul.this);
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
                    if (tiporelatorioSul == "customizadorisp") {
                        if ((EdtDtInicialCustSul.getText().toString().equals("")) || (EdtDtFinalCustSul.getText().toString().equals(""))) {

                            AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispSul.this);
                            LayoutInflater factory = LayoutInflater.from(RelatorioRispSul.this);
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
                        if (tiporelatorioSul == "acumuladorisp") {
                            if ((EdtDtInicialCVLISul.getText().toString().equals("")) || (EdtDtFinalCVLISul.getText().toString().equals(""))) {

                                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioRispSul.this);
                                LayoutInflater factory = LayoutInflater.from(RelatorioRispSul.this);
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

    private void updateLabelDataInicialSul(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLISul.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinalSul(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLISul.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusSul(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustSul.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusSul(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustSul.setText(sdf.format(c1.getTime()));
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

        String dataIncialCvli = EdtDtInicialCVLISul.getText().toString();
        String dataFinalCvli = EdtDtFinalCVLISul.getText().toString();
        String titulorelatorio = EdtTituloRelatorioSul.getText().toString();
        String dataIncialCvliCus = EdtDtInicialCustSul.getText().toString();
        String dataFinalCvliCus = EdtDtFinalCustSul.getText().toString();
        int identrisp = 1;

        try {
            int idservidor = usuarioDao.user.getId();
            String matriculaservidor = usuarioDao.user.getDsMatricula();

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", tiporelatorioSul));
            itens.add(new BasicNameValuePair("tiporelatorio", tiporelatoriosemanalSul));
            itens.add(new BasicNameValuePair("tituloRelatorio", titulorelatorio));
            itens.add(new BasicNameValuePair("comecoPeriodo", dataIncialCvli));
            itens.add(new BasicNameValuePair("fimPeriodo", dataFinalCvli));
            itens.add(new BasicNameValuePair("comecoPeriodoCus", dataIncialCvliCus));
            itens.add(new BasicNameValuePair("fimPeriodoCus", dataFinalCvliCus));
            itens.add(new BasicNameValuePair("identrisp", String.valueOf(identrisp)));
            itens.add(new BasicNameValuePair("naturezacus", String.valueOf(NaturezacusSul)));
            itens.add(new BasicNameValuePair("motivacaocus", String.valueOf(MotivacaocusSul)));
            itens.add(new BasicNameValuePair("distribuicaocidcus", String.valueOf(DistribuicaocidadecusSul)));
            itens.add(new BasicNameValuePair("districaobairrocus", String.valueOf(DistribuicaobairrocusSul)));
            itens.add(new BasicNameValuePair("detalhamentocus", String.valueOf(DetalhamentocusSul)));
            itens.add(new BasicNameValuePair("companualcus", String.valueOf(ComparativoanualcusSul)));
            itens.add(new BasicNameValuePair("meioempregadocus", String.valueOf(MeioempregadocusSul)));
            itens.add(new BasicNameValuePair("elucidacaocus", String.valueOf(ElucidacaocusSul)));
            itens.add(new BasicNameValuePair("zoneamentocus", String.valueOf(ZoneamentocusSul)));
            itens.add(new BasicNameValuePair("compperiodicocus", String.valueOf(ComparativoPeriodocusSul)));
            itens.add(new BasicNameValuePair("compmensalcus", String.valueOf(ComparativomensalcusSul)));
            itens.add(new BasicNameValuePair("incidenciadiasemanacus", String.valueOf(incidenciaSemanacusSul)));
            itens.add(new BasicNameValuePair("incidenciahorariocus", String.valueOf(incidenciaHorariocusSul)));
            itens.add(new BasicNameValuePair("relacaovitimacus", String.valueOf(relacaovitimascusSul)));


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

                                    Intent it = new Intent(RelatorioRispSul.this, RelatorioGeradoCVLIActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_relatorio_rispsul, menu);

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
