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

public class RelatorioRisp extends AppCompatActivity {

    private RadioGroup RgRelatorioCVLI;
    private RadioButton RbEscolhidoRelatorioCVLI;
    private Button BtnGerarRelatorioCVLI;
    private ConstraintLayout ClRelatorioSemanal, ClRelatorioEstatistico, ClRelatorioCustomizado;
    private Spinner SpTipoRelatorioSemanal;
    private EditText EdtDtInicialCVLI, EdtDtFinalCVLI, EdtDtInicialCVLICus, EdtDtFinalCVLICus, EdtTituloRelatorio;
    private CheckBox CbNaturezaRisp, CbMeioEmpregadoRisp, CbMotivacaoRisp, CbElucidacaoRisp, CbDistribuicaoRISP, CbZoneamentoRISP, CbdetalhamentoRISP, CbComparativoperiodoRISP, CbComparativoAnualRISP, CbComparativoMensalRISP, CbHistoricoDiarioRISP, CbIncidenciaDiaSemanaRISP;
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
        setContentView(R.layout.activity_relatorio_risp);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ClRelatorioEstatistico = (ConstraintLayout) findViewById(R.id.clRelatorioEstatisticoRisp);
        ClRelatorioSemanal = (ConstraintLayout) findViewById(R.id.clRelatorioSemanalEstado);
        ClRelatorioCustomizado = (ConstraintLayout) findViewById(R.id.clRelatorioCustomizadoRISP);
        RgRelatorioCVLI = (RadioGroup) findViewById(R.id.rgTipoRelatorioCVLIEstado);
        BtnGerarRelatorioCVLI = (Button) findViewById(R.id.btnGerarRelatorioCVLIRISP);
        EdtDtInicialCVLI = (EditText) findViewById(R.id.edtDtInicialCVLIEstado);
        EdtDtFinalCVLI = (EditText) findViewById(R.id.edtDtFinalCVLIRISP);
        EdtDtInicialCVLICus = (EditText) findViewById(R.id.edtDtInicialCustomizadoEstado);
        EdtDtFinalCVLICus = (EditText) findViewById(R.id.edtDtFinalCustomizadoRISP);
        EdtTituloRelatorio = (EditText) findViewById(R.id.edtNomeRelatorioEstatisticoEstado);
        SpTipoRelatorioSemanal = (Spinner) findViewById(R.id.spTipoRelatorioSemanalEstado);
        CbNaturezaRisp = (CheckBox) findViewById(R.id.cbNaturezaEstado);
        CbMeioEmpregadoRisp = (CheckBox) findViewById(R.id.cbMeioEmpregadoEstado);
        CbMotivacaoRisp = (CheckBox) findViewById(R.id.cbMotivacaoEstado);
        CbElucidacaoRisp = (CheckBox) findViewById(R.id.cbElucidacaoEstado);
        CbDistribuicaoRISP = (CheckBox) findViewById(R.id.cbDistriuiçãoEstado);
        CbZoneamentoRISP = (CheckBox) findViewById(R.id.cbZoneamentoEstado);
        CbdetalhamentoRISP = (CheckBox) findViewById(R.id.cbDetalhamentoEstado);
        CbComparativoperiodoRISP = (CheckBox) findViewById(R.id.cbComparativoPeriodoEstado);
        CbComparativoAnualRISP = (CheckBox) findViewById(R.id.cbComparativoAnualEstado);
        CbComparativoMensalRISP = (CheckBox) findViewById(R.id.cbComparativomensalEstado);
        CbHistoricoDiarioRISP = (CheckBox) findViewById(R.id.cbHistoricoDiarioEstado);
        CbIncidenciaDiaSemanaRISP = (CheckBox) findViewById(R.id.cbIncidenciaDiaSemanaRISP);

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
