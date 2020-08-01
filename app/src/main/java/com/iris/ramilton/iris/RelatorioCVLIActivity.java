package com.iris.ramilton.iris;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import java.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class RelatorioCVLIActivity extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLI, TvTipoRelatorioCVLI, TvEscolhaPeriodoCVLI, TvAteCVLI;
    private ListView LvResfato;
    private Button BtnGerarRelatorioCVLI;
    private RadioGroup RgRelatorioCVLI;
    private RadioButton RbEscolhidoRelatorioCVLI;
    private ConstraintLayout ClRelatorioSemanal, ClRelatorioEstatistico, ClRelatorioCustomizado;
    private EditText EdtDtInicialCVLI, EdtDtFinalCVLI, EdtTituloRelatorio, EdtDtInicialCust, EdtDtFinalCust;
    private Spinner SpTipoRelatorioSemanal;
    private CheckBox CkbNaturezaCus, CkbMotivacaoCus, CkbDistribuicaoCidadeCus, CkbDistribuicaoBairroCus, CkbDetalhamentoCus, CkbComparativoAnualCus, CkbMeioEmpregadoCus, CkbElucidacaoCus, CkbZoneamentoCus, CkbComparativoPeriodoCus, CkbComparativoMensalCus, CkbIncidenciaSemanaCus, CkbIncidenciaHorarioCus, CkbRelacaoVitimasCus;
    private int Naturezacus = 0, Motivacaocus = 0, Distribuicaocidadecus = 0, Distribuicaobairrocus = 0, Detalhamentocus = 0, Comparativoanualcus = 0, Meioempregadocus = 0, Elucidacaocus = 0, Zoneamentocus = 0, ComparativoPeriodocus = 0, Comparativomensalcus = 0, incidenciaSemanacus = 0, incidenciaHorariocus = 0, relacaovitimascus = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanal, tiporelatorio;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicial, dataFinal, dateInicialCus, dateFinalCus;
    private ProgressBar carregando;
    private List<Cvli> ListaResfato;
    private List<Cvli> ListaResfatoFiltrados = new ArrayList<>();
    String dataincial, datafinal, datainicialcus, datafinalcus;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_cvli);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizado = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLI);
        ClRelatorioEstatistico = (ConstraintLayout) findViewById(R.id.clRelatorioEstatistico);
        ClRelatorioSemanal = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstado);
        RgRelatorioCVLI = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstado);
        TvtituloRelatorioCVLI = (TextView) findViewById(R.id.tvRelatoriosCVLIEstado);
        TvTipoRelatorioCVLI = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstado);
        TvEscolhaPeriodoCVLI = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstado);
        TvAteCVLI = (TextView) findViewById(R.id.tvAteCVLIEstado);
        BtnGerarRelatorioCVLI = (Button) findViewById(R.id.btnGerarRelatorioCVLI);
        EdtDtInicialCVLI = (EditText) findViewById(R.id.edtDtInicialCVLIEstado);
        EdtDtFinalCVLI = (EditText) findViewById(R.id.edtDtFinalCVLI);
        EdtDtInicialCust = (EditText) findViewById(R.id.edtDataInicioCusCvli);
        EdtDtFinalCust = (EditText) findViewById(R.id.edtDataFinalCusCVLI);
        EdtTituloRelatorio = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstado);
        SpTipoRelatorioSemanal = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstado);
        LvResfato = (ListView) findViewById(R.id.lvResFato);
        CkbNaturezaCus = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLI);
        CkbMotivacaoCus = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLI);
        CkbDistribuicaoCidadeCus = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLI);
        CkbDistribuicaoBairroCus = (CheckBox) findViewById(R.id.ckbDistBairCusCVLI);
        CkbDetalhamentoCus = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLI);
        CkbComparativoAnualCus = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLI);
        CkbMeioEmpregadoCus = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLI);
        CkbElucidacaoCus = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLI);
        CkbZoneamentoCus = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLI);
        CkbComparativoPeriodoCus = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLI);
        CkbComparativoMensalCus = (CheckBox) findViewById(R.id.ckbCompMesCusCVLI);
        CkbIncidenciaSemanaCus = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLI);
        CkbIncidenciaHorarioCus = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLI);
        CkbRelacaoVitimasCus = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISP);


        ArrayAdapter adaptadorTipoRelatorioSemanal = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanal.setAdapter(adaptadorTipoRelatorioSemanal);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincial="01/01/2020";
        datafinal="05/01/2020";
        ListaResfato = cvliDao.retornaResFato(dataincial,datafinal);
        ListaResfatoFiltrados.addAll(ListaResfato);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltrados);
        LvResfato.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfato);

        RgRelatorioCVLI.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLI = (RadioButton) RgRelatorioCVLI.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLI.indexOfChild(RbEscolhidoRelatorioCVLI);

                if(indiceescolha == 0){
                    ClRelatorioSemanal.setVisibility(View.VISIBLE);
                    ClRelatorioEstatistico.setVisibility(View.GONE);
                    ClRelatorioCustomizado.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLI.setText("GERAR RELATÓRIO SEMANAL - AISP");
                    tiporelatorio = "semanal";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatistico.setVisibility(View.VISIBLE);
                    ClRelatorioSemanal.setVisibility(View.GONE);
                    ClRelatorioCustomizado.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLI.setText("GERAR RELATÓRIO ESTATÍSTICO - AISP");
                    tiporelatorio = "acumulado";
                }else{
                    ClRelatorioEstatistico.setVisibility(View.GONE);
                    ClRelatorioSemanal.setVisibility(View.GONE);
                    ClRelatorioCustomizado.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLI.setText("GERAR RELATÓRIO CUSTOMIZADO - AISP");
                    tiporelatorio = "customizado";
                }
            }
        });

        CkbNaturezaCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCus.isChecked()){
                    Naturezacus = 1;
                }else{
                    Naturezacus = 0;
                }
            }
        });

        CkbRelacaoVitimasCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCus.isChecked()){
                    relacaovitimascus = 1;
                }else{
                    relacaovitimascus = 0;
                }
            }
        });

        CkbMotivacaoCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCus.isChecked()){
                    Motivacaocus = 1;
                }else{
                    Motivacaocus = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCus.isChecked()){
                    Distribuicaocidadecus = 1;
                }else{
                    Distribuicaocidadecus = 0;
                }

            }
        });

        CkbDistribuicaoBairroCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCus.isChecked()){
                    Distribuicaobairrocus = 1;
                }else{
                    Distribuicaobairrocus = 0;
                }

            }
        });

        CkbDetalhamentoCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCus.isChecked()){
                    Detalhamentocus = 1;
                }else{
                    Detalhamentocus = 0;
                }

            }
        });

        CkbComparativoAnualCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCus.isChecked()){
                    Comparativoanualcus = 1;
                }else{
                    Comparativoanualcus = 0;
                }

            }
        });

        CkbMeioEmpregadoCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCus.isChecked()){
                    Meioempregadocus = 1;
                }else{
                    Meioempregadocus = 0;
                }

            }
        });

        CkbElucidacaoCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCus.isChecked()){
                    Elucidacaocus = 1;
                }else{
                    Elucidacaocus = 0;
                }

            }
        });

        CkbZoneamentoCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCus.isChecked()){
                    Zoneamentocus = 1;
                }else{
                    Zoneamentocus = 0;
                }

            }
        });

        CkbComparativoPeriodoCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCus.isChecked()){
                    ComparativoPeriodocus = 1;
                }else{
                    ComparativoPeriodocus = 0;
                }

            }
        });

        CkbComparativoMensalCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCus.isChecked()){
                    Comparativomensalcus = 1;
                }else{
                    Comparativomensalcus = 0;
                }

            }
        });

        CkbIncidenciaSemanaCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCus.isChecked()){
                    incidenciaSemanacus = 1;
                }else{
                    incidenciaSemanacus = 0;
                }

            }
        });

        CkbIncidenciaHorarioCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCus.isChecked()){
                    incidenciaHorariocus = 1;
                }else{
                    incidenciaHorariocus = 0;
                }
            }
        });


        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicial = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicial();
            }
        };

        dataFinal = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinal();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCus = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCus();
            }
        };

        dateFinalCus = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCus();
            }
        };


        EdtDtInicialCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioCVLIActivity.this, dateInicialCus, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorio = "customizado";
            }
        });

        EdtDtFinalCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioCVLIActivity.this, dateFinalCus, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorio = "customizado";
            }
        });




        EdtDtInicialCVLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioCVLIActivity.this, dateInicial, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorio = "acumulado";
            }
        });

        EdtDtFinalCVLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioCVLIActivity.this, dataFinal, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorio = "acumulado";
            }
        });


        SpTipoRelatorioSemanal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanal = SpTipoRelatorioSemanal.getItemAtPosition(position).toString();
                tiporelatorio = "semanal";

                datafinal = tiporelatoriosemanal.substring(13,23);
                dataincial = tiporelatoriosemanal.substring(0,10);

                ListaResfato = cvliDao.retornaResFato(dataincial,datafinal);

                ListaResfatoFiltrados.clear();
                ListaResfatoFiltrados.addAll(ListaResfato);
                LvResfato.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        BtnGerarRelatorioCVLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConected(RelatorioCVLIActivity.this) == false) {

                    AlertDialog.Builder b = new AlertDialog.Builder(RelatorioCVLIActivity.this);
                    LayoutInflater factory = LayoutInflater.from(RelatorioCVLIActivity.this);
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
                    if (tiporelatorio == "customizado") {
                        if ((EdtDtInicialCust.getText().toString().equals("")) || (EdtDtFinalCust.getText().toString().equals(""))) {

                            Log.i("Valores ent", "entrou aqui no em branco");
                            AlertDialog.Builder b = new AlertDialog.Builder(RelatorioCVLIActivity.this);
                            LayoutInflater factory = LayoutInflater.from(RelatorioCVLIActivity.this);
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
                            EnvioDadosRelatorioCVLI();
                        }
                    } else {
                        if (tiporelatorio == "acumulado") {
                            if ((EdtDtInicialCVLI.getText().toString().equals("")) || (EdtDtFinalCVLI.getText().toString().equals(""))) {

                                AlertDialog.Builder b = new AlertDialog.Builder(RelatorioCVLIActivity.this);
                                LayoutInflater factory = LayoutInflater.from(RelatorioCVLIActivity.this);
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
                                EnvioDadosRelatorioCVLI();
                            }
                        } else {
                            carregando.setVisibility(ProgressBar.VISIBLE);
                            EnvioDadosRelatorioCVLI();
                        }
                    }
                }
            }
        });
    }

    private void updateLabelDataInicial(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLI.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinal(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLI.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCus(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCust.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCus(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCust.setText(sdf.format(c1.getTime()));
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

    private void EnvioDadosRelatorioCVLI() {

        HttpClient httpClient = new DefaultHttpClient();

        String dataIncialCvli = EdtDtInicialCVLI.getText().toString();
        String dataFinalCvli = EdtDtFinalCVLI.getText().toString();
        String titulorelatorio = EdtTituloRelatorio.getText().toString();
        String dataIncialCvliCus = EdtDtInicialCust.getText().toString();
        String dataFinalCvliCus = EdtDtFinalCust.getText().toString();

        try {
                int idservidor = usuarioDao.user.getId();
                String matriculaservidor = usuarioDao.user.getDsMatricula();

                ArrayList<NameValuePair> itens = new ArrayList<NameValuePair>();
                itens.add(new BasicNameValuePair("id", String.valueOf(idservidor)));
                itens.add(new BasicNameValuePair("matricula", matriculaservidor));
                itens.add(new BasicNameValuePair("tipo", tiporelatorio));
                itens.add(new BasicNameValuePair("tiporelatorio", tiporelatoriosemanal));
                itens.add(new BasicNameValuePair("tituloRelatorio", titulorelatorio));
                itens.add(new BasicNameValuePair("comecoPeriodo", dataIncialCvli));
                itens.add(new BasicNameValuePair("fimPeriodo", dataFinalCvli));
                itens.add(new BasicNameValuePair("comecoPeriodoCus", dataIncialCvliCus));
                itens.add(new BasicNameValuePair("fimPeriodoCus", dataFinalCvliCus));
                itens.add(new BasicNameValuePair("naturezacus", String.valueOf(Naturezacus)));
                itens.add(new BasicNameValuePair("motivacaocus", String.valueOf(Motivacaocus)));
                itens.add(new BasicNameValuePair("distribuicaocidcus", String.valueOf(Distribuicaocidadecus)));
                itens.add(new BasicNameValuePair("districaobairrocus", String.valueOf(Distribuicaobairrocus)));
                itens.add(new BasicNameValuePair("detalhamentocus", String.valueOf(Detalhamentocus)));
                itens.add(new BasicNameValuePair("companualcus", String.valueOf(Comparativoanualcus)));
                itens.add(new BasicNameValuePair("meioempregadocus", String.valueOf(Meioempregadocus)));
                itens.add(new BasicNameValuePair("elucidacaocus", String.valueOf(Elucidacaocus)));
                itens.add(new BasicNameValuePair("zoneamentocus", String.valueOf(Zoneamentocus)));
                itens.add(new BasicNameValuePair("compperiodicocus", String.valueOf(ComparativoPeriodocus)));
                itens.add(new BasicNameValuePair("compmensalcus", String.valueOf(Comparativomensalcus)));
                itens.add(new BasicNameValuePair("incidenciadiasemanacus", String.valueOf(incidenciaSemanacus)));
                itens.add(new BasicNameValuePair("incidenciahorariocus", String.valueOf(incidenciaHorariocus)));
                itens.add(new BasicNameValuePair("relacaovitimacus", String.valueOf(relacaovitimascus)));


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

                                    Intent it = new Intent(RelatorioCVLIActivity.this, RelatorioGeradoCVLIActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_relatorio_cvligeral, menu);

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

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto_relatoriocvli, menu);

    }

    public void AtualizarResFato(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cvli AtualizarResFato = ListaResfatoFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, AturalizaResFatoActivity.class);
        it.putExtra("codigoresfatoatualizar", AtualizarResFato);
        startActivity(it);
    }

    @Override
    public void onResume() {
        super.onResume();
            ListaResfato = cvliDao.retornaResFato(dataincial,datafinal);
            ListaResfatoFiltrados.clear();
            ListaResfatoFiltrados.addAll(ListaResfato);
            LvResfato.invalidateViews();
        }
}
