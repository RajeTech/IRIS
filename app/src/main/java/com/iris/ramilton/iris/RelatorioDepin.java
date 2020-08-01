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

public class RelatorioDepin extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLI, TvTipoRelatorioCVLI, TvEscolhaPeriodoCVLI, TvAteCVLI;
    private ListView LvResfato;
    private Button BtnGerarRelatorioCVLIDepin;
    private RadioGroup RgRelatorioCVLIDepin;
    private RadioButton RbEscolhidoRelatorioCVLIDepin;
    private ConstraintLayout ClRelatorioSemanalDepin, ClRelatorioEstatisticoDepin, ClRelatorioCustomizadoDepin;
    private EditText EdtDtInicialCVLIDepin, EdtDtFinalCVLIDepin, EdtTituloRelatorioDepin, EdtDtInicialCustDepin, EdtDtFinalCustDepin;
    private Spinner SpTipoRelatorioSemanalDepin;
    private CheckBox CkbNaturezaCusDepin, CkbMotivacaoCusDepin, CkbDistribuicaoCidadeCusDepin, CkbDistribuicaoBairroCusDepin, CkbDetalhamentoCusDepin, CkbComparativoAnualCusDepin, CkbMeioEmpregadoCusDepin, CkbElucidacaoCusDepin, CkbZoneamentoCusDepin, CkbComparativoPeriodoCusDepin, CkbComparativoMensalCusDepin, CkbIncidenciaSemanaCusDepin, CkbIncidenciaHorarioCusDepin, CkbRelacaoVitimasCusDepin;
    private int NaturezacusDepin = 0, MotivacaocusDepin = 0, DistribuicaocidadecusDepin = 0, DistribuicaobairrocusDepin = 0, DetalhamentocusDepin = 0, ComparativoanualcusDepin = 0, MeioempregadocusDepin = 0, ElucidacaocusDepin = 0, ZoneamentocusDepin = 0, ComparativoPeriodocusDepin = 0, ComparativomensalcusDepin = 0, incidenciaSemanacusDepin = 0, incidenciaHorariocusDepin = 0, relacaovitimascusDepin = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalDepin, tiporelatorioDepin;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialDepin, dataFinalDepin, dateInicialCusDepin, dateFinalCusDepin;
    private ProgressBar carregando;
    private List<Cvli> ListaResfato;
    private List<Cvli> ListaResfatoFiltrados = new ArrayList<>();
    String dataincialDepin, datafinalDepin, datainicialcusDepin, datafinalcusDepin;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_depin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoDepin = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLIDepin);
        ClRelatorioEstatisticoDepin = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoDepin);
        ClRelatorioSemanalDepin = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstadoDepin);
        RgRelatorioCVLIDepin = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstadoDepin);
        TvTipoRelatorioCVLI = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstadoDepin);
        TvEscolhaPeriodoCVLI = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstadoDepin);
        TvAteCVLI = (TextView) findViewById(R.id.tvAteCVLIEstadoDepin);
        BtnGerarRelatorioCVLIDepin = (Button) findViewById(R.id.btnGerarRelatorioCVLIDepin);
        EdtDtInicialCVLIDepin = (EditText) findViewById(R.id.edtDtInicialCVLIEstadoDepin);
        EdtDtFinalCVLIDepin = (EditText) findViewById(R.id.edtDtFinalCVLIDepin);
        EdtDtInicialCustDepin = (EditText) findViewById(R.id.edtDataInicioCusCvliDepin);
        EdtDtFinalCustDepin = (EditText) findViewById(R.id.edtDataFinalCusCVLIDepin);
        EdtTituloRelatorioDepin = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstadoDepin);
        SpTipoRelatorioSemanalDepin = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstadoDepin);
        LvResfato = (ListView) findViewById(R.id.lvResFatoDepin);
        CkbNaturezaCusDepin = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLIDepin);
        CkbMotivacaoCusDepin = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLIDepin);
        CkbDistribuicaoCidadeCusDepin = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLIDepin);
        CkbDistribuicaoBairroCusDepin = (CheckBox) findViewById(R.id.ckbDistBairCusCVLIDepin);
        CkbDetalhamentoCusDepin = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLIDepin);
        CkbComparativoAnualCusDepin = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLIDepin);
        CkbMeioEmpregadoCusDepin = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLIDepin);
        CkbElucidacaoCusDepin = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLIDepin);
        CkbZoneamentoCusDepin = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLIDepin);
        CkbComparativoPeriodoCusDepin = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLIDepin);
        CkbComparativoMensalCusDepin = (CheckBox) findViewById(R.id.ckbCompMesCusCVLIDepin);
        CkbIncidenciaSemanaCusDepin = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLIDepin);
        CkbIncidenciaHorarioCusDepin = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLIDepin);
        CkbRelacaoVitimasCusDepin = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPDepin);

        ArrayAdapter adaptadorTipoRelatorioSemanalDepin = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalDepin.setAdapter(adaptadorTipoRelatorioSemanalDepin);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);

        dataincialDepin="01/01/2019";
        datafinalDepin="06/01/2019";
        ListaResfato = cvliDao.retornaResFato(dataincialDepin,datafinalDepin);
        ListaResfatoFiltrados.addAll(ListaResfato);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltrados);
        LvResfato.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfato);

        RgRelatorioCVLIDepin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLIDepin = (RadioButton) RgRelatorioCVLIDepin.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLIDepin.indexOfChild(RbEscolhidoRelatorioCVLIDepin);

                if(indiceescolha == 0){
                    ClRelatorioSemanalDepin.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoDepin.setVisibility(View.GONE);
                    ClRelatorioCustomizadoDepin.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIDepin.setText("GERAR RELATÓRIO SEMANAL - DEPIN");
                    tiporelatorioDepin = "semanaldepin";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoDepin.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalDepin.setVisibility(View.GONE);
                    ClRelatorioCustomizadoDepin.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIDepin.setText("GERAR RELATÓRIO ESTATÍSTICO - DEPIN");
                    tiporelatorioDepin = "acumuladodepin";
                }else{
                    ClRelatorioEstatisticoDepin.setVisibility(View.GONE);
                    ClRelatorioSemanalDepin.setVisibility(View.GONE);
                    ClRelatorioCustomizadoDepin.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLIDepin.setText("GERAR RELATÓRIO CUSTOMIZADO - DEPIN");
                    tiporelatorioDepin = "customizadodepin";
                }
            }
        });

        CkbNaturezaCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusDepin.isChecked()){
                    NaturezacusDepin = 1;
                }else{
                    NaturezacusDepin = 0;
                }
            }
        });

        CkbRelacaoVitimasCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusDepin.isChecked()){
                    relacaovitimascusDepin = 1;
                }else{
                    relacaovitimascusDepin = 0;
                }
            }
        });

        CkbMotivacaoCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusDepin.isChecked()){
                    MotivacaocusDepin = 1;
                }else{
                    MotivacaocusDepin = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusDepin.isChecked()){
                    DistribuicaocidadecusDepin = 1;
                }else{
                    DistribuicaocidadecusDepin = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusDepin.isChecked()){
                    DistribuicaobairrocusDepin = 1;
                }else{
                    DistribuicaobairrocusDepin = 0;
                }

            }
        });

        CkbDetalhamentoCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusDepin.isChecked()){
                    DetalhamentocusDepin = 1;
                }else{
                    DetalhamentocusDepin = 0;
                }

            }
        });

        CkbComparativoAnualCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusDepin.isChecked()){
                    ComparativoanualcusDepin = 1;
                }else{
                    ComparativoanualcusDepin = 0;
                }

            }
        });

        CkbMeioEmpregadoCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusDepin.isChecked()){
                    MeioempregadocusDepin = 1;
                }else{
                    MeioempregadocusDepin = 0;
                }

            }
        });

        CkbElucidacaoCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusDepin.isChecked()){
                    ElucidacaocusDepin = 1;
                }else{
                    ElucidacaocusDepin = 0;
                }

            }
        });

        CkbZoneamentoCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusDepin.isChecked()){
                    ZoneamentocusDepin = 1;
                }else{
                    ZoneamentocusDepin = 0;
                }

            }
        });

        CkbComparativoPeriodoCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusDepin.isChecked()){
                    ComparativoPeriodocusDepin = 1;
                }else{
                    ComparativoPeriodocusDepin = 0;
                }

            }
        });

        CkbComparativoMensalCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusDepin.isChecked()){
                    ComparativomensalcusDepin = 1;
                }else{
                    ComparativomensalcusDepin = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusDepin.isChecked()){
                    incidenciaSemanacusDepin = 1;
                }else{
                    incidenciaSemanacusDepin = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusDepin.isChecked()){
                    incidenciaHorariocusDepin = 1;
                }else{
                    incidenciaHorariocusDepin = 0;
                }
            }
        });

        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialDepin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialDEPIN();
            }
        };

        dataFinalDepin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalDEPIN();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusDepin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusDEPIN();
            }
        };

        dateFinalCusDepin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusDEPIN();
            }
        };


        EdtDtInicialCustDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioDepin.this, dateInicialCusDepin, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioDepin = "customizadodepin";
            }
        });

        EdtDtFinalCustDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioDepin.this, dateFinalCusDepin, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioDepin = "customizadodepin";
            }
        });




        EdtDtInicialCVLIDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioDepin.this, dateInicialDepin, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioDepin = "acumuladodepin";
            }
        });

        EdtDtFinalCVLIDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioDepin.this, dataFinalDepin, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioDepin = "acumuladodepin";
            }
        });

        SpTipoRelatorioSemanalDepin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalDepin = SpTipoRelatorioSemanalDepin.getItemAtPosition(position).toString();
                tiporelatorioDepin = "semanaldepin";

                datafinalDepin = tiporelatoriosemanalDepin.substring(13,23);
                dataincialDepin = tiporelatoriosemanalDepin.substring(0,10);

                ListaResfato = cvliDao.retornaResFato(dataincialDepin,datafinalDepin);

                ListaResfatoFiltrados.clear();
                ListaResfatoFiltrados.addAll(ListaResfato);
                LvResfato.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnGerarRelatorioCVLIDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConected(RelatorioDepin.this) == false) {

                    AlertDialog.Builder b = new AlertDialog.Builder(RelatorioDepin.this);
                    LayoutInflater factory = LayoutInflater.from(RelatorioDepin.this);
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
                    if (tiporelatorioDepin == "customizadodepin") {
                        if ((EdtDtInicialCustDepin.getText().toString().equals("")) || (EdtDtFinalCustDepin.getText().toString().equals(""))) {

                            Log.i("Valores ent", "entrou aqui no em branco");
                            AlertDialog.Builder b = new AlertDialog.Builder(RelatorioDepin.this);
                            LayoutInflater factory = LayoutInflater.from(RelatorioDepin.this);
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
                            EnvioDadosRelatorioCVLIDEPIN();
                        }
                    } else {
                        if (tiporelatorioDepin == "acumuladodepin") {
                            if ((EdtDtInicialCVLIDepin.getText().toString().equals("")) || (EdtDtFinalCVLIDepin.getText().toString().equals(""))) {

                                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioDepin.this);
                                LayoutInflater factory = LayoutInflater.from(RelatorioDepin.this);
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
                                EnvioDadosRelatorioCVLIDEPIN();
                            }
                        } else {
                            carregando.setVisibility(ProgressBar.VISIBLE);
                            EnvioDadosRelatorioCVLIDEPIN();
                        }
                    }
                }
            }
        });

    }

    private void updateLabelDataInicialDEPIN(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLIDepin.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinalDEPIN(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLIDepin.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusDEPIN(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustDepin.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusDEPIN(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustDepin.setText(sdf.format(c1.getTime()));
    }

    //método para verificar acesso a internet
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

    private void EnvioDadosRelatorioCVLIDEPIN() {

        HttpClient httpClient = new DefaultHttpClient();

        String dataIncialCvli = EdtDtInicialCVLIDepin.getText().toString();
        String dataFinalCvli = EdtDtFinalCVLIDepin.getText().toString();
        String titulorelatorio = EdtTituloRelatorioDepin.getText().toString();
        String dataIncialCvliCus = EdtDtInicialCustDepin.getText().toString();
        String dataFinalCvliCus = EdtDtFinalCustDepin.getText().toString();

        try {
            int idservidor = usuarioDao.user.getId();
            String matriculaservidor = usuarioDao.user.getDsMatricula();

            ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
            itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
            itens.add(new BasicNameValuePair("matricula", matriculaservidor));
            itens.add(new BasicNameValuePair("tipo", tiporelatorioDepin));
            itens.add(new BasicNameValuePair("tiporelatorio", tiporelatoriosemanalDepin));
            itens.add(new BasicNameValuePair("tituloRelatorio", titulorelatorio));
            itens.add(new BasicNameValuePair("comecoPeriodo", dataIncialCvli));
            itens.add(new BasicNameValuePair("fimPeriodo", dataFinalCvli));
            itens.add(new BasicNameValuePair("comecoPeriodoCus", dataIncialCvliCus));
            itens.add(new BasicNameValuePair("fimPeriodoCus", dataFinalCvliCus));
            itens.add(new BasicNameValuePair("naturezacus", String.valueOf(NaturezacusDepin)));
            itens.add(new BasicNameValuePair("motivacaocus", String.valueOf(MotivacaocusDepin)));
            itens.add(new BasicNameValuePair("distribuicaocidcus", String.valueOf(DistribuicaocidadecusDepin)));
            itens.add(new BasicNameValuePair("districaobairrocus", String.valueOf(DistribuicaobairrocusDepin)));
            itens.add(new BasicNameValuePair("detalhamentocus", String.valueOf(DetalhamentocusDepin)));
            itens.add(new BasicNameValuePair("companualcus", String.valueOf(ComparativoanualcusDepin)));
            itens.add(new BasicNameValuePair("meioempregadocus", String.valueOf(MeioempregadocusDepin)));
            itens.add(new BasicNameValuePair("elucidacaocus", String.valueOf(ElucidacaocusDepin)));
            itens.add(new BasicNameValuePair("zoneamentocus", String.valueOf(ZoneamentocusDepin)));
            itens.add(new BasicNameValuePair("compperiodicocus", String.valueOf(ComparativoPeriodocusDepin)));
            itens.add(new BasicNameValuePair("compmensalcus", String.valueOf(ComparativomensalcusDepin)));
            itens.add(new BasicNameValuePair("incidenciadiasemanacus", String.valueOf(incidenciaSemanacusDepin)));
            itens.add(new BasicNameValuePair("incidenciahorariocus", String.valueOf(incidenciaHorariocusDepin)));
            itens.add(new BasicNameValuePair("relacaovitimacus", String.valueOf(relacaovitimascusDepin)));


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

                                    Intent it = new Intent(RelatorioDepin.this, RelatorioGeradoCVLIActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_relatorio_depin, menu);

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
