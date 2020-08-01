package com.iris.ramilton.iris;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.modelo.Cvli;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Timer;

public class RelatorioCapital extends AppCompatActivity {

    private RadioGroup RgRelatorioCVLI;
    private RadioButton RbEscolhidoRelatorioCVLI;
    private Button BtnGerarRelatorioCVLI;
    private ConstraintLayout ClRelatorioSemanal, ClRelatorioEstatistico, ClRelatorioCustomizado;
    private Spinner SpTipoRelatorioSemanal;
    private EditText EdtDtInicialCVLI, EdtDtFinalCVLI, EdtDtInicialCVLICus, EdtDtFinalCVLICus, EdtTituloRelatorio;
    private CheckBox CbNaturezaCapital, CbMeioEmpregadoCapital, CbMotivacaoCapital, CbElucidacaoCapital, CbDistribuicaoCapital, CbZoneamentoCapital, CbdetalhamentoCapital, CbComparativoperiodoCapital, CbComparativoAnualCapital, CbComparativoMensalCapital, CbHistoricoDiarioCapital, CbIncidenciaDiaSemanaCapital;
    DatePickerDialog.OnDateSetListener dateInicial, dataFinal;
    private UsuarioDao usuarioDao;
    private JSONObject relatorio;
    private Cvli cvli;
    private CvliDao cvliDao;
    private ProgressBar carregando;
    String dataincial, datafinal;
    Calendar c;
    DatePickerDialog dpd;

    Timer t;
    TimePickerDialog tpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_capital);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ClRelatorioEstatistico = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoCapital);
        ClRelatorioSemanal = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstado);
        ClRelatorioCustomizado = (ConstraintLayout) findViewById(R.id.clRelatorioCustomizadoCapital);
        RgRelatorioCVLI = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstado);
        BtnGerarRelatorioCVLI = (Button) findViewById(R.id.btnGerarRelatorioCVLICapital);
        EdtDtInicialCVLI = (EditText) findViewById(R.id.edtDtInicialCVLIEstado);
        EdtDtFinalCVLI = (EditText) findViewById(R.id.edtDtFinalCVLCapital);
        EdtDtInicialCVLICus = (EditText) findViewById(R.id.edtDtInicialCustomizadoEstado);
        EdtDtFinalCVLICus = (EditText) findViewById(R.id.edtDtFinalCustomizadoCapital);
        EdtTituloRelatorio = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstado);
        SpTipoRelatorioSemanal = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstado);
        CbNaturezaCapital = (CheckBox) findViewById(R.id.cbNaturezaEstado);
        CbMeioEmpregadoCapital = (CheckBox) findViewById(R.id.cbMeioEmpregadoEstado);
        CbMotivacaoCapital = (CheckBox) findViewById(R.id.cbMotivacaoEstado);
        CbElucidacaoCapital = (CheckBox) findViewById(R.id.cbElucidacaoEstado);
        CbDistribuicaoCapital = (CheckBox) findViewById(R.id.cbDistriuiçãoEstado);
        CbZoneamentoCapital = (CheckBox) findViewById(R.id.cbZoneamentoEstado);
        CbdetalhamentoCapital = (CheckBox) findViewById(R.id.cbDetalhamentoEstado);
        CbComparativoperiodoCapital = (CheckBox) findViewById(R.id.cbComparativoPeriodoEstado);
        CbComparativoAnualCapital = (CheckBox) findViewById(R.id.cbComparativoAnualEstado);
        CbComparativoMensalCapital = (CheckBox) findViewById(R.id.cbComparativomensalEstado);
        CbHistoricoDiarioCapital = (CheckBox) findViewById(R.id.cbHistoricoDiarioEstado);
        CbIncidenciaDiaSemanaCapital = (CheckBox) findViewById(R.id.cbIncidenciaDiaSemanaCapital);

        RgRelatorioCVLI.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbEscolhidoRelatorioCVLI = (RadioButton) RgRelatorioCVLI.findViewById(checkedId);
                int indiceescolha = RgRelatorioCVLI.indexOfChild(RbEscolhidoRelatorioCVLI);

                if(indiceescolha == 0){
                    ClRelatorioSemanal.setVisibility(View.VISIBLE);
                    ClRelatorioEstatistico.setVisibility(View.GONE);
                    ClRelatorioCustomizado.setVisibility(View.GONE);
                }else if(indiceescolha == 1){
                    ClRelatorioEstatistico.setVisibility(View.VISIBLE);
                    ClRelatorioSemanal.setVisibility(View.GONE);
                    ClRelatorioCustomizado.setVisibility(View.GONE);
                }else{
                    ClRelatorioEstatistico.setVisibility(View.GONE);
                    ClRelatorioSemanal.setVisibility(View.GONE);
                    ClRelatorioCustomizado.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
