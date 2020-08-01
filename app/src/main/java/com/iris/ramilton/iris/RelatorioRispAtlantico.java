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

public class RelatorioRispAtlantico extends AppCompatActivity {

    private TextView TvtituloRelatorioCVLIAtlantico, TvTipoRelatorioCVLIAtlantico, TvEscolhaPeriodoCVLIAtlantico, TvAteCVLIAtlantico;
    private ListView LvResfatoAtlantico;
    private Button BtnGerarRelatorioCVLIAtlantico;
    private RadioGroup RgRelatorioCVLIAtlantico;
    private RadioButton RbEscolhidoRelatorioCVLIAtlantico;
    private ConstraintLayout ClRelatorioSemanalAtlantico, ClRelatorioEstatisticoAtlantico, ClRelatorioCustomizadoAtlantico;
    private EditText EdtDtInicialCVLIAtlantico, EdtDtFinalCVLIAtlantico, EdtTituloRelatorioAtlantico, EdtDtInicialCustAtlantico, EdtDtFinalCustAtlantico;
    private Spinner SpTipoRelatorioSemanalAtlantico;
    private CheckBox CkbNaturezaCusAtlantico, CkbMotivacaoCusAtlantico, CkbDistribuicaoCidadeCusAtlantico, CkbDistribuicaoBairroCusAtlantico, CkbDetalhamentoCusAtlantico, CkbComparativoAnualCusAtlantico, CkbMeioEmpregadoCusAtlantico, CkbElucidacaoCusAtlantico, CkbZoneamentoCusAtlantico, CkbComparativoPeriodoCusAtlantico, CkbComparativoMensalCusAtlantico, CkbIncidenciaSemanaCusAtlantico, CkbIncidenciaHorarioCusAtlantico, CkbRelacaoVitimasCusAtlantico;
    private int NaturezacusAtlantico = 0, MotivacaocusAtlantico = 0, DistribuicaocidadecusAtlantico = 0, DistribuicaobairrocusAtlantico = 0, DetalhamentocusAtlantico = 0, ComparativoanualcusAtlantico = 0, MeioempregadocusAtlantico = 0, ElucidacaocusAtlantico = 0, ZoneamentocusAtlantico = 0, ComparativoPeriodocusAtlantico = 0, ComparativomensalcusAtlantico = 0, incidenciaSemanacusAtlantico = 0, incidenciaHorariocusAtlantico = 0, relacaovitimascusAtlantico = 0;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private String tiporelatoriosemanalAtlantico, tiporelatorioAtlantico;
    private Cvli cvli;
    private CvliDao cvliDao;
    DatePickerDialog.OnDateSetListener dateInicialAtlantico, dataFinalAtlantico, dateInicialCusAtlantico, dateFinalCusAtlantico;
    private ProgressBar carregando;
    private List<Cvli> ListaResfatoAtlantico;
    private List<Cvli> ListaResfatoFiltradosAtlantico = new ArrayList<>();
    String dataincialAtlantico, datafinalAtlantico, datainicialcusAtlantico, datafinalcusAtlantico;
    Calendar c, c1;
    DatePickerDialog dpd, dpd1;

    Timer t, t1;
    TimePickerDialog tpd, tpd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_risp_atlantico);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        carregando = findViewById(R.id.carregando);
        ClRelatorioCustomizadoAtlantico = (ConstraintLayout) findViewById(R.id.clCustomizadoCVLIAtlantico);
        ClRelatorioEstatisticoAtlantico = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoAtlantico);
        ClRelatorioSemanalAtlantico = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstadoAtlantico);
        RgRelatorioCVLIAtlantico = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstadoAtlantico);
        TvTipoRelatorioCVLIAtlantico = (TextView) findViewById(R.id.tvEscolhaTipoRelatorioEstadoAtlantico);
        TvEscolhaPeriodoCVLIAtlantico = (TextView) findViewById(R.id.tvPeriodoRelatorioCVLIEstadoAtlantico);
        TvAteCVLIAtlantico = (TextView) findViewById(R.id.tvAteCVLIEstadoAtlantico);
        BtnGerarRelatorioCVLIAtlantico = (Button) findViewById(R.id.btnGerarRelatorioCVLIAtlantico);
        EdtDtInicialCVLIAtlantico = (EditText) findViewById(R.id.edtDtInicialCVLIEstadoAtlantico);
        EdtDtFinalCVLIAtlantico = (EditText) findViewById(R.id.edtDtFinalCVLIAtlantico);
        EdtDtInicialCustAtlantico = (EditText) findViewById(R.id.edtDataInicioCusCvliAtlantico);
        EdtDtFinalCustAtlantico = (EditText) findViewById(R.id.edtDataFinalCusCVLIAtlantico);
        EdtTituloRelatorioAtlantico = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstadoAtlantico);
        SpTipoRelatorioSemanalAtlantico = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstadoAtlantico);
        LvResfatoAtlantico = (ListView) findViewById(R.id.lvResFatoAtlantico);
        CkbNaturezaCusAtlantico = (CheckBox) findViewById(R.id.ckbNaturezaCusCVLIAtlantico);
        CkbMotivacaoCusAtlantico = (CheckBox) findViewById(R.id.ckbMotivacaoCusCVLIAtlantico);
        CkbDistribuicaoCidadeCusAtlantico = (CheckBox) findViewById(R.id.ckbDistriCidCusCVLIAtlantico);
        CkbDistribuicaoBairroCusAtlantico = (CheckBox) findViewById(R.id.ckbDistBairCusCVLIAtlantico);
        CkbDetalhamentoCusAtlantico = (CheckBox) findViewById(R.id.ckbDetalhamentoCustCVLIAtlantico);
        CkbComparativoAnualCusAtlantico = (CheckBox) findViewById(R.id.ckbCompAnualCustCVLIAtlantico);
        CkbMeioEmpregadoCusAtlantico = (CheckBox) findViewById(R.id.ckbMeioEmpregadoCusCVLIAtlantico);
        CkbElucidacaoCusAtlantico = (CheckBox) findViewById(R.id.ckbElucidacaoCusCVLIAtlantico);
        CkbZoneamentoCusAtlantico = (CheckBox) findViewById(R.id.ckbZoneamentoCusCVLIAtlantico);
        CkbComparativoPeriodoCusAtlantico = (CheckBox) findViewById(R.id.ckbCompPeriCusCVLIAtlantico);
        CkbComparativoMensalCusAtlantico = (CheckBox) findViewById(R.id.ckbCompMesCusCVLIAtlantico);
        CkbIncidenciaSemanaCusAtlantico = (CheckBox) findViewById(R.id.ckbIncSemaCusCVLIAtlantico);
        CkbIncidenciaHorarioCusAtlantico = (CheckBox) findViewById(R.id.ckbIncidenciaHorarioCVLIAtlantico);
        CkbRelacaoVitimasCusAtlantico = (CheckBox) findViewById(R.id.cbkRelacaoVitimaAISPAtlantico);

        ArrayAdapter adaptadorTipoRelatorioSemanalAtlantico = ArrayAdapter.createFromResource(this, R.array.TipoRelatorioSemanal, android.R.layout.simple_spinner_item);
        SpTipoRelatorioSemanalAtlantico.setAdapter(adaptadorTipoRelatorioSemanalAtlantico);

        cvli = new Cvli();
        cvliDao = new CvliDao(this);


        dataincialAtlantico="01/01/2019";
        datafinalAtlantico="06/01/2019";
        ListaResfatoAtlantico = cvliDao.retornaResFato(dataincialAtlantico,datafinalAtlantico);
        ListaResfatoFiltradosAtlantico.addAll(ListaResfatoAtlantico);
        ResFatoAdaptador adaptadorResfato = new ResFatoAdaptador(this, ListaResfatoFiltradosAtlantico);
        LvResfatoAtlantico.setAdapter(adaptadorResfato);
        registerForContextMenu(LvResfatoAtlantico);

        RgRelatorioCVLIAtlantico.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLIAtlantico = (RadioButton) RgRelatorioCVLIAtlantico.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLIAtlantico.indexOfChild(RbEscolhidoRelatorioCVLIAtlantico);

                if(indiceescolha == 0){
                    ClRelatorioSemanalAtlantico.setVisibility(View.VISIBLE);
                    ClRelatorioEstatisticoAtlantico.setVisibility(View.GONE);
                    ClRelatorioCustomizadoAtlantico.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIAtlantico.setText("GERAR RELATÓRIO SEMANAL - RISP ATLÂNTICO");
                    tiporelatorioAtlantico = "semanal";
                }else if(indiceescolha == 1){
                    ClRelatorioEstatisticoAtlantico.setVisibility(View.VISIBLE);
                    ClRelatorioSemanalAtlantico.setVisibility(View.GONE);
                    ClRelatorioCustomizadoAtlantico.setVisibility(View.GONE);
                    BtnGerarRelatorioCVLIAtlantico.setText("GERAR RELATÓRIO ESTATÍSTICO - RISP ATLÂNTICO");
                    tiporelatorioAtlantico = "acumulado";
                }else{
                    ClRelatorioEstatisticoAtlantico.setVisibility(View.GONE);
                    ClRelatorioSemanalAtlantico.setVisibility(View.GONE);
                    ClRelatorioCustomizadoAtlantico.setVisibility(View.VISIBLE);
                    BtnGerarRelatorioCVLIAtlantico.setText("GERAR RELATÓRIO CUSTOMIZADO - RISP ATLÂNTICO");
                    tiporelatorioAtlantico = "customizado";
                }
            }
        });

        CkbNaturezaCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbNaturezaCusAtlantico.isChecked()){
                    NaturezacusAtlantico = 1;
                }else{
                    NaturezacusAtlantico = 0;
                }
            }
        });

        CkbRelacaoVitimasCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbRelacaoVitimasCusAtlantico.isChecked()){
                    relacaovitimascusAtlantico = 1;
                }else{
                    relacaovitimascusAtlantico = 0;
                }
            }
        });

        CkbMotivacaoCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMotivacaoCusAtlantico.isChecked()){
                    MotivacaocusAtlantico = 1;
                }else{
                    MotivacaocusAtlantico = 0;
                }

            }
        });

        CkbDistribuicaoCidadeCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoCidadeCusAtlantico.isChecked()){
                    DistribuicaocidadecusAtlantico = 1;
                }else{
                    DistribuicaocidadecusAtlantico = 0;
                }

            }
        });

        CkbDistribuicaoBairroCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDistribuicaoBairroCusAtlantico.isChecked()){
                    DistribuicaobairrocusAtlantico = 1;
                }else{
                    DistribuicaobairrocusAtlantico = 0;
                }

            }
        });

        CkbDetalhamentoCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbDetalhamentoCusAtlantico.isChecked()){
                    DetalhamentocusAtlantico = 1;
                }else{
                    DetalhamentocusAtlantico = 0;
                }

            }
        });

        CkbComparativoAnualCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoAnualCusAtlantico.isChecked()){
                    ComparativoanualcusAtlantico = 1;
                }else{
                    ComparativoanualcusAtlantico = 0;
                }

            }
        });

        CkbMeioEmpregadoCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbMeioEmpregadoCusAtlantico.isChecked()){
                    MeioempregadocusAtlantico = 1;
                }else{
                    MeioempregadocusAtlantico = 0;
                }

            }
        });

        CkbElucidacaoCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbElucidacaoCusAtlantico.isChecked()){
                    ElucidacaocusAtlantico = 1;
                }else{
                    ElucidacaocusAtlantico = 0;
                }

            }
        });

        CkbZoneamentoCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbZoneamentoCusAtlantico.isChecked()){
                    ZoneamentocusAtlantico = 1;
                }else{
                    ZoneamentocusAtlantico = 0;
                }

            }
        });

        CkbComparativoPeriodoCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoPeriodoCusAtlantico.isChecked()){
                    ComparativoPeriodocusAtlantico = 1;
                }else{
                    ComparativoPeriodocusAtlantico = 0;
                }

            }
        });

        CkbComparativoMensalCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbComparativoMensalCusAtlantico.isChecked()){
                    ComparativomensalcusAtlantico = 1;
                }else{
                    ComparativomensalcusAtlantico = 0;
                }

            }
        });

        CkbIncidenciaSemanaCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaSemanaCusAtlantico.isChecked()){
                    incidenciaSemanacusAtlantico = 1;
                }else{
                    incidenciaSemanacusAtlantico = 0;
                }

            }
        });

        CkbIncidenciaHorarioCusAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CkbIncidenciaHorarioCusAtlantico.isChecked()){
                    incidenciaHorariocusAtlantico = 1;
                }else{
                    incidenciaHorariocusAtlantico = 0;
                }
            }
        });

        usuarioDao = new UsuarioDao(this);
        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        dateInicialAtlantico = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialAtlantico();
            }
        };

        dataFinalAtlantico = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalAtlantico();
            }
        };

        c1 = Calendar.getInstance();

        dateInicialCusAtlantico = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataInicialCusAtlantico();
            }
        };

        dateFinalCusAtlantico = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinalCusAtlantico();
            }
        };


        EdtDtInicialCustAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispAtlantico.this, dateInicialCusAtlantico, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioAtlantico = "customizado";
            }
        });

        EdtDtFinalCustAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispAtlantico.this, dateFinalCusAtlantico, c1.get(Calendar.YEAR),
                        c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioAtlantico = "customizado";
            }
        });




        EdtDtInicialCVLIAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispAtlantico.this, dateInicialAtlantico, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioAtlantico = "acumulado";
            }
        });

        EdtDtFinalCVLIAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RelatorioRispAtlantico.this, dataFinalAtlantico, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                tiporelatorioAtlantico = "acumulado";
            }
        });

        SpTipoRelatorioSemanalAtlantico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tiporelatoriosemanalAtlantico = SpTipoRelatorioSemanalAtlantico.getItemAtPosition(position).toString();
                tiporelatorioAtlantico = "semanal";

                datafinalAtlantico = tiporelatoriosemanalAtlantico.substring(13,23);
                dataincialAtlantico = tiporelatoriosemanalAtlantico.substring(0,10);

                ListaResfatoAtlantico = cvliDao.retornaResFato(dataincialAtlantico,datafinalAtlantico);

                ListaResfatoFiltradosAtlantico.clear();
                ListaResfatoFiltradosAtlantico.addAll(ListaResfatoAtlantico);
                LvResfatoAtlantico.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void updateLabelDataInicialAtlantico(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCVLIAtlantico.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataFinalAtlantico(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCVLIAtlantico.setText(sdf.format(c.getTime()));
    }

    private void updateLabelDataInicialCusAtlantico(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtInicialCustAtlantico.setText(sdf.format(c1.getTime()));
    }

    private void updateLabelDataFinalCusAtlantico(){

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        EdtDtFinalCustAtlantico.setText(sdf.format(c1.getTime()));
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
        getMenuInflater().inflate(R.menu.menu_relatorio_rispatlantico, menu);

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
