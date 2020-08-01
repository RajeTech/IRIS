package com.iris.ramilton.iris;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class RelatorioRispBPS extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLIBPS, TvTipoRelatorioCVLIBPS, TvEscolhaPeriodoCVLIBPS, TvAteCVLIBPS;
    private ListView LvResfatoBPS;
    private Button BtnGerarRelatorioCVLIBPS;
    private RadioGroup RgRelatorioCVLIBPS;
    private RadioButton RbEscolhidoRelatorioCVLIBPS;
    private ConstraintLayout ClRelatorioSemanalBPS, ClRelatorioEstatisticoBPS, ClRelatorioCustomizadoBPS;
    private EditText EdtDtInicialCVLIBPS, EdtDtFinalCVLIBPS, EdtTituloRelatorioBPS, EdtDtInicialCustBPS, EdtDtFinalCustBPS;
    private Spinner SpTipoRelatorioSemanalBPS;
    private CheckBox CkbNaturezaCusBPS, CkbMotivacaoCusBPS, CkbDistribuicaoCidadeCusBPS, CkbDistribuicaoBairroCusBPS, CkbDetalhamentoCusBPS, CkbComparativoAnualCusBPS, CkbMeioEmpregadoCusBPS, CkbElucidacaoCusBPS, CkbZoneamentoCusBPS, CkbComparativoPeriodoCusBPS, CkbComparativoMensalCusBPS, CkbIncidenciaSemanaCusBPS, CkbIncidenciaHorarioCusBPS, CkbRelacaoVitimasCusBPS;
    private int NaturezacusBPS = 0, MotivacaocusBPS = 0, DistribuicaocidadecusBPS = 0, DistribuicaobairrocusBPS = 0, DetalhamentocusBPS = 0, ComparativoanualcusBPS = 0, MeioempregadocusBPS = 0, ElucidacaocusBPS = 0, ZoneamentocusBPS = 0, ComparativoPeriodocusBPS = 0, ComparativomensalcusBPS = 0, incidenciaSemanacusBPS = 0, incidenciaHorariocusBPS = 0, relacaovitimascusBPS = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalBPS, tiporelatorioBPS;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialBPS, dataFinalBPS, dateInicialCusBPS, dateFinalCusBPS;
    private ProgressBar carregando;
    private List<Cvli> ListaResfatoBPS;
    private List<Cvli> ListaResfatoFiltradosBPS = new ArrayList<>();
    String dataincialBPS, datafinalBPS, datainicialcusBPS, datafinalcusBPS;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_risp_bps);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoBPS = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLIBPS);
        ClRelatorioEstatisticoBPS = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoBPS);
        ClRelatorioSemanalBPS = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstadoBPS);
        RgRelatorioCVLIBPS = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstadoBPS);
        TvTipoRelatorioCVLIBPS = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstadoBPS);
        TvEscolhaPeriodoCVLIBPS = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstadoBPS);
        TvAteCVLIBPS = (TextView) findViewById(R.id.tvAteCVLIEstadoBPS);
        BtnGerarRelatorioCVLIBPS = (Button) findViewById(R.id.btnGerarRelatorioCVLIBPS);
        EdtDtInicialCVLIBPS = (EditText) findViewById(R.id.edtDtInicialCVLIEstadoBPS);
        EdtDtFinalCVLIBPS = (EditText) findViewById(R.id.edtDtFinalCVLIBPS);
        EdtDtInicialCustBPS = (EditText) findViewById(R.id.edtDataInicioCusCvliBPS);
        EdtDtFinalCustBPS = (EditText) findViewById(R.id.edtDataFinalCusCVLIBPS);
        EdtTituloRelatorioBPS = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstadoBPS);
        SpTipoRelatorioSemanalBPS = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstadoBPS);
        LvResfatoBPS = (ListView) findViewById(R.id.lvResFatoBPS);
        CkbNaturezaCusBPS = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLIBPS);
        CkbMotivacaoCusBPS = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLINorte);
        CkbDistribuicaoCidadeCusBPS = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLIBPS);
        CkbDistribuicaoBairroCusBPS = (CheckBox) findViewById(R.id.ckbDistBairCusCVLIBPS);
        CkbDetalhamentoCusBPS = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLIBPS);
        CkbComparativoAnualCusBPS = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLIBPS);
        CkbMeioEmpregadoCusBPS = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLIBPS);
        CkbElucidacaoCusBPS = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLIBPS);
        CkbZoneamentoCusBPS = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLIBPS);
        CkbComparativoPeriodoCusBPS = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLIBPS);
        CkbComparativoMensalCusBPS = (CheckBox) findViewById(R.id.ckbCompMesCusCVLIBPS);
        CkbIncidenciaSemanaCusBPS = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLISul);
        CkbIncidenciaHorarioCusBPS = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLIBPS);
        CkbRelacaoVitimasCusBPS = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPBPS);


        ArrayAdapter adaptadorTipoRelatorioSemanalBPS = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalBPS.setAdapter(adaptadorTipoRelatorioSemanalBPS);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincialBPS="01/01/2019";
        datafinalBPS="06/01/2019";
        ListaResfatoBPS = cvliDao.retornaResFato(dataincialBPS,datafinalBPS);
        ListaResfatoFiltradosBPS.addAll(ListaResfatoBPS);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltradosBPS);
        LvResfatoBPS.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfatoBPS);

        RgRelatorioCVLIBPS.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLIBPS = (RadioButton) RgRelatorioCVLIBPS.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLIBPS.indexOfChild(RbEscolhidoRelatorioCVLIBPS);

                if(indiceescolha == 0){
                    ClRelatorioSemanalBPS.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoBPS.setVisibility(View.GONE);
                    ClRelatorioCustomizadoBPS.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIBPS.setText("GERAR RELATÓRIO SEMANAL - RISP BPS");
                    tiporelatorioBPS = "semanal";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoBPS.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalBPS.setVisibility(View.GONE);
                    ClRelatorioCustomizadoBPS.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIBPS.setText("GERAR RELATÓRIO ESTATÍSTICO - RISP BPS");
                    tiporelatorioBPS = "acumulado";
                }else{
                    ClRelatorioEstatisticoBPS.setVisibility(View.GONE);
                    ClRelatorioSemanalBPS.setVisibility(View.GONE);
                    ClRelatorioCustomizadoBPS.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLIBPS.setText("GERAR RELATÓRIO CUSTOMIZADO - RISP BPS");
                    tiporelatorioBPS = "customizado";
                }
            }
        });

        CkbNaturezaCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusBPS.isChecked()){
                    NaturezacusBPS = 1;
                }else{
                    NaturezacusBPS = 0;
                }
            }
        });

        CkbRelacaoVitimasCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusBPS.isChecked()){
                    relacaovitimascusBPS = 1;
                }else{
                    relacaovitimascusBPS = 0;
                }
            }
        });

        CkbMotivacaoCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusBPS.isChecked()){
                    MotivacaocusBPS = 1;
                }else{
                    MotivacaocusBPS = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusBPS.isChecked()){
                    DistribuicaocidadecusBPS = 1;
                }else{
                    DistribuicaocidadecusBPS = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusBPS.isChecked()){
                    DistribuicaobairrocusBPS = 1;
                }else{
                    DistribuicaobairrocusBPS = 0;
                }

            }
        });

        CkbDetalhamentoCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusBPS.isChecked()){
                    DetalhamentocusBPS = 1;
                }else{
                    DetalhamentocusBPS = 0;
                }

            }
        });

        CkbComparativoAnualCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusBPS.isChecked()){
                    ComparativoanualcusBPS = 1;
                }else{
                    ComparativoanualcusBPS = 0;
                }

            }
        });

        CkbMeioEmpregadoCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusBPS.isChecked()){
                    MeioempregadocusBPS = 1;
                }else{
                    MeioempregadocusBPS = 0;
                }

            }
        });

        CkbElucidacaoCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusBPS.isChecked()){
                    ElucidacaocusBPS = 1;
                }else{
                    ElucidacaocusBPS = 0;
                }

            }
        });

        CkbZoneamentoCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusBPS.isChecked()){
                    ZoneamentocusBPS = 1;
                }else{
                    ZoneamentocusBPS = 0;
                }

            }
        });

        CkbComparativoPeriodoCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusBPS.isChecked()){
                    ComparativoPeriodocusBPS = 1;
                }else{
                    ComparativoPeriodocusBPS = 0;
                }

            }
        });

        CkbComparativoMensalCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusBPS.isChecked()){
                    ComparativomensalcusBPS = 1;
                }else{
                    ComparativomensalcusBPS = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusBPS.isChecked()){
                    incidenciaSemanacusBPS = 1;
                }else{
                    incidenciaSemanacusBPS = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusBPS.isChecked()){
                    incidenciaHorariocusBPS = 1;
                }else{
                    incidenciaHorariocusBPS = 0;
                }
            }
        });


        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialBPS = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialBPS();
            }
        };

        dataFinalBPS = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalBPS();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusBPS = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusBPS();
            }
        };

        dateFinalCusBPS = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusBPS();
            }
        };


        EdtDtInicialCustBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispBPS.this, dateInicialCusBPS, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioBPS = "customizado";
            }
        });

        EdtDtFinalCustBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispBPS.this, dateFinalCusBPS, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioBPS = "customizado";
            }
        });




        EdtDtInicialCVLIBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispBPS.this, dateInicialBPS, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioBPS = "acumulado";
            }
        });

        EdtDtFinalCVLIBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispBPS.this, dataFinalBPS, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioBPS = "acumulado";
            }
        });


        SpTipoRelatorioSemanalBPS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalBPS = SpTipoRelatorioSemanalBPS.getItemAtPosition(position).toString();
                tiporelatorioBPS = "semanal";

                datafinalBPS = tiporelatoriosemanalBPS.substring(13,23);
                dataincialBPS = tiporelatoriosemanalBPS.substring(0,10);

                ListaResfatoBPS = cvliDao.retornaResFato(dataincialBPS,datafinalBPS);

                ListaResfatoFiltradosBPS.clear();
                ListaResfatoFiltradosBPS.addAll(ListaResfatoBPS);
                LvResfatoBPS.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void updateLabelDataInicialBPS(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLIBPS.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinalBPS(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLIBPS.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusBPS(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustBPS.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusBPS(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustBPS.setText(sdf.format(c1.getTime()));
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
        getMenuInflater().inflate(R.menu.menu_relatorio_rispbps, menu);

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
