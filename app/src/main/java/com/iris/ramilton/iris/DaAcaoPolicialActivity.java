package com.iris.ramilton.iris;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.AcaoPolicialDao;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.modelo.Acaopolicial;
import com.iris.ramilton.iris.modelo.Cvli;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

public class DaAcaoPolicialActivity extends AppCompatActivity {

    private Button BtnClassificacaoAcaoPolicial, BtnRegistrarAcaoPolicial, BtnNomeOperacaoAcaoPolicial, BtnDtHrLocalAcaoPolicial, BtnNBuscasJudiciaisAcaoPolicial, BtnCrimeObjetoAcaoPolicial, BtnOrigemAcaoPolicial, BtnResumoAcaoPolicial, BtnTituloResumoAcaoPolicial, BtnMunicipioReferenciaAcaoPolicial, BtnSubtituloAcaoPolicial, BtnNovoSutituloAcaoPolicial;
    private TextView TvDtAcaoPolicial, TvHrAcaoPolicial, TvLocalAcaoPolicial, TvClassificacaoAcaoPolicial, TvSubtitulo1, TvSubtitulo2, TvSubtitulo3, TvSubtitulo4, TvTexto1, TvTexto2, TvTexto3, TvTexto4;
    private EditText EdtNomeOperacaoAcaoPolicial, EdtDataAcaoPolicial, EdtHorarioAcaoPolicial, EdtLocalAcaoPolicial, EdtNBuscaJudiciaisAcaoPolicial, EdtCrimeObjetoAcaoPolicial, EdtResumoAcaoPolicial, EdtTitutloResumoAcaoPolicial, EdtMunicipioReferenciaAcaoPolicial, EdtSubtitulo1, EdtSubtitulo2, EdtSubtitulo3, EdtSubtitulo4, EdtTexto1, EdtTexto2, EdtTexto3, EdtTexto4;
    private CheckBox CbMedidasCautelaresAcaoPolicial, CbDesdobramentoInvestigacaoAcaoPolicial, CbDesdobramentoRegistroAcaoPolicial, CbAbordagemRotinaAcaoPolicial, CbColaboradorAcaoPolicial, CbDenunciaAcaoPolicial, CbOutrosAcaoPolicial, CbkPresoAcaoPolicial, CbkBensApreendidosAcaoPolicial, CbkOperacaoInominada;
    private Acaopolicial acaopolicial, acaopolicialdaacaosematualizar ;
    private AcaoPolicialDao acaopolicialDao, acaoPolicialDaodaacaosematualizar;
    private Acaopolicial acaopolicialdaacao = null;
    private int atualizar = 0, controleenvio = 0, codigorecebidosem;
    private int MedidasCautelaresAcaoPolicial, DesdobramentoInvestigacaoAcaoPolicial, DesdobramentoRegistroAcaoPolicial, AbordagemRotinaAcaoPolicial, ColaboradorAcaoPolicial, DenunciaAcaoPolicial, OutrosAcaoPolicial, Presoacaopolicial, Bensapreendidosacaopolicial, OperacaoInominada;
    DatePickerDialog.OnDateSetListener date;
    private LinearLayout LSubtituloAcaoPolicial;
    int subtexto = 1;
    Calendar c;
    DatePickerDialog dpd;

    Timer t;
    TimePickerDialog tpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_acao_policial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        BtnClassificacaoAcaoPolicial = (Button) findViewById(R.id.btnClassificacaoDasAcoesPoliciais);
        BtnNomeOperacaoAcaoPolicial = (Button) findViewById(R.id.btnNomeOperacaoAcaoPolicial);
        BtnDtHrLocalAcaoPolicial = (Button) findViewById(R.id.btnDataHorarioLocalAcaoPolicial);
        BtnNBuscasJudiciaisAcaoPolicial = (Button) findViewById(R.id.btnNBuscaJudiciaisAcaoPolicial);
        BtnCrimeObjetoAcaoPolicial = (Button) findViewById(R.id.btnCrimePrincipalAcaoPolicial);
        BtnOrigemAcaoPolicial = (Button) findViewById(R.id.btnOrigemdaAcaoPolicial);
        BtnResumoAcaoPolicial = (Button) findViewById(R.id.btnResumoAcaoPolicial);
        BtnRegistrarAcaoPolicial = (Button) findViewById(R.id.btnValidarAcaoPolicial);
        BtnTituloResumoAcaoPolicial = (Button) findViewById(R.id.btnTituloResumo);
        BtnMunicipioReferenciaAcaoPolicial = (Button) findViewById(R.id.btnMunicipioReferenciaAcaoPolicial);
        BtnSubtituloAcaoPolicial = (Button) findViewById(R.id.btnSubtituloAcaoPolicial);
        BtnNovoSutituloAcaoPolicial = (Button) findViewById(R.id.btnNovoSutituloAcaoPolicial);
        TvDtAcaoPolicial = (TextView) findViewById(R.id.tvDataAcaoPolicial);
        TvHrAcaoPolicial = (TextView) findViewById(R.id.tvHorarioAcaoPolicial);
        TvLocalAcaoPolicial = (TextView) findViewById(R.id.tvLocalAcaoPolicial);
        TvSubtitulo1 = (TextView) findViewById(R.id.tvSubtitulo1);
        TvSubtitulo2 = (TextView) findViewById(R.id.tvSubtitulo2);
        TvSubtitulo3 = (TextView) findViewById(R.id.tvSubtitulo3);
        TvSubtitulo4 = (TextView) findViewById(R.id.tvSubtitulo4);
        TvTexto1 = (TextView) findViewById(R.id.tvTexto1);
        TvTexto2 = (TextView) findViewById(R.id.tvTexto2);
        TvTexto3 = (TextView) findViewById(R.id.tvTexto3);
        TvTexto4 = (TextView) findViewById(R.id.tvTitulo4);
        EdtSubtitulo1 = (EditText) findViewById(R.id.edtSubTitulo1);
        EdtSubtitulo2 = (EditText) findViewById(R.id.edtSubtitulo2);
        EdtSubtitulo3 = (EditText) findViewById(R.id.edtSubtitulo3);
        EdtSubtitulo4 = (EditText) findViewById(R.id.edtSubtitulo4);
        EdtTexto1 = (EditText) findViewById(R.id.edtTexto1);
        EdtTexto2 = (EditText) findViewById(R.id.edtTexto2);
        EdtTexto3 = (EditText) findViewById(R.id.edtTexto3);
        EdtTexto4 = (EditText) findViewById(R.id.edtTexto4);
        EdtNomeOperacaoAcaoPolicial = (EditText) findViewById(R.id.edtNomeOperacaoAcaoPolicial);
        EdtDataAcaoPolicial = (EditText) findViewById(R.id.edtDataAcaoPolicial);
        EdtHorarioAcaoPolicial = (EditText) findViewById(R.id.edtHorarioAcaoPolicial);
        EdtLocalAcaoPolicial = (EditText) findViewById(R.id.edtLocalAcaoPolicial);
        EdtNBuscaJudiciaisAcaoPolicial = (EditText) findViewById(R.id.edtNBuscaJudiciaisAcaoPolicial);
        EdtCrimeObjetoAcaoPolicial = (EditText) findViewById(R.id.edtCrimePrincipalAcaoPolicial);
        EdtResumoAcaoPolicial = (EditText) findViewById(R.id.edtResumoAcaoPolicial);
        EdtTitutloResumoAcaoPolicial = (EditText) findViewById(R.id.edtTituloResumo);
        EdtMunicipioReferenciaAcaoPolicial = (EditText) findViewById(R.id.edtMunicipioRefereciaAcaoPolicial);
        TvClassificacaoAcaoPolicial = (TextView) findViewById(R.id.tvClassificaoAcaoPolicial);
        CbMedidasCautelaresAcaoPolicial = (CheckBox) findViewById(R.id.cbMedidasCautelaresAcaoPolicial);
        CbDesdobramentoInvestigacaoAcaoPolicial = (CheckBox) findViewById(R.id.cbDesdobramentoInvestigacoes);
        CbDesdobramentoRegistroAcaoPolicial = (CheckBox) findViewById(R.id.cbDesdobramentoRegistro);
        CbAbordagemRotinaAcaoPolicial = (CheckBox) findViewById(R.id.cbAbordagemRotina);
        CbColaboradorAcaoPolicial = (CheckBox) findViewById(R.id.cbcolaboradorInformante);
        CbDenunciaAcaoPolicial = (CheckBox) findViewById(R.id.cbDenunciaAnonima);
        CbOutrosAcaoPolicial = (CheckBox) findViewById(R.id.cbOutros);
        CbkPresoAcaoPolicial = (CheckBox) findViewById(R.id.cbkPresoAcaoPolicial);
        CbkOperacaoInominada = (CheckBox) findViewById(R.id.cbOperacaoInominada);
        CbkBensApreendidosAcaoPolicial = (CheckBox) findViewById(R.id.cbkBensApreendidoAcaoPolicial);
        LSubtituloAcaoPolicial = (LinearLayout) findViewById(R.id.linearLayoutAcaoPolicial);


        acaopolicialdaacaosematualizar = new Acaopolicial();
        acaoPolicialDaodaacaosematualizar = new AcaoPolicialDao(this);

        Intent it = getIntent();
        if (it.hasExtra("dacao")) {
            acaopolicialdaacao = (Acaopolicial) it.getSerializableExtra("dacao");

            atualizar = 1;
            controleenvio = 4;

            TvClassificacaoAcaoPolicial.setText(acaopolicialdaacao.getDsClassificacaoAcaoPolicial());
            EdtNomeOperacaoAcaoPolicial.setText(acaopolicialdaacao.getDsNomeOperacaoPolicial());
            EdtDataAcaoPolicial.setText(acaopolicialdaacao.getDtAcaoPolicial());
            EdtHorarioAcaoPolicial.setText(acaopolicialdaacao.getHsAcaoPolicial());
            EdtLocalAcaoPolicial.setText(acaopolicialdaacao.getLocalAcaoPolicial());
            EdtNBuscaJudiciaisAcaoPolicial.setText(String.valueOf(acaopolicialdaacao.getNBuscaJudiciaisAcaoPolicial()));
            EdtCrimeObjetoAcaoPolicial.setText(acaopolicialdaacao.getDsCrimePrincipalAcaoPolicial());

            if(acaopolicialdaacao.getCbkOperacaoInominada() == 0){
                CbkOperacaoInominada.setChecked(false);
            }else{
                CbkOperacaoInominada.setChecked(true);
            }

            if (acaopolicialdaacao.getCbkMedidasCautelares() == 0) {
                CbMedidasCautelaresAcaoPolicial.setChecked(false);
            } else {
                CbMedidasCautelaresAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacao.getCbkInvestigacoesOrdinarias() == 0) {
                CbDesdobramentoInvestigacaoAcaoPolicial.setChecked(false);
            } else {
                CbDesdobramentoInvestigacaoAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacao.getCbkRegistroPolicial() == 0) {
                CbDesdobramentoRegistroAcaoPolicial.setChecked(false);
            } else {
                CbDesdobramentoRegistroAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacao.getCbkAbordagemRotina() == 0) {
                CbAbordagemRotinaAcaoPolicial.setChecked(false);
            } else {
                CbAbordagemRotinaAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacao.getCbkColaborador() == 0) {
                CbColaboradorAcaoPolicial.setChecked(false);
            } else {
                CbColaboradorAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacao.getCbkDenuncia() == 0) {
                CbDenunciaAcaoPolicial.setChecked(false);
            } else {
                CbDenunciaAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacao.getCbkOutros() == 0) {
                CbOutrosAcaoPolicial.setChecked(false);
            } else {
                CbOutrosAcaoPolicial.setChecked(true);
            }

            if(acaopolicialdaacao.getCbkBensAprendidos() == 0){
                CbkBensApreendidosAcaoPolicial.setChecked(false);
            }else {
                CbkBensApreendidosAcaoPolicial.setChecked(true);
            }

            if(acaopolicialdaacao.getCbkPresoAcaoPolicial() == 0){
                CbkPresoAcaoPolicial.setChecked(false);
            }else {
                CbkPresoAcaoPolicial.setChecked(true);
            }

            EdtTitutloResumoAcaoPolicial.setText(acaopolicialdaacao.getDstituloResumo());
            EdtResumoAcaoPolicial.setText(acaopolicialdaacao.getDsResumo());
            EdtMunicipioReferenciaAcaoPolicial.setText(acaopolicialdaacao.getDsMunicipioReferenciaAcaoPolicial());
            EdtSubtitulo1.setText(acaopolicialdaacao.getDsSubtitulo1());
            EdtTexto1.setText(acaopolicialdaacao.getDsTexto1());
            EdtSubtitulo2.setText(acaopolicialdaacao.getDsSubtitulo2());
            EdtTexto2.setText(acaopolicialdaacao.getDsTexto2());
            EdtSubtitulo3.setText(acaopolicialdaacao.getDsSubtitulo3());
            EdtTexto3.setText(acaopolicialdaacao.getDsTexto3());
            EdtSubtitulo4.setText(acaopolicialdaacao.getDsSubtitulo4());
            EdtTexto4.setText(acaopolicialdaacao.getDsTexto4());

        }else if(it.hasExtra("dacaosematualizar")){

            codigorecebidosem = (int) it.getSerializableExtra("dacaosematualizar");

            atualizar = 2;
            controleenvio = 4;

            acaopolicialdaacaosematualizar = acaoPolicialDaodaacaosematualizar.retornaAcaopolicialdaAcaoObj(codigorecebidosem);

            TvClassificacaoAcaoPolicial.setText(acaopolicialdaacaosematualizar.getDsClassificacaoAcaoPolicial());
            EdtNomeOperacaoAcaoPolicial.setText(acaopolicialdaacaosematualizar.getDsNomeOperacaoPolicial());
            EdtDataAcaoPolicial.setText(acaopolicialdaacaosematualizar.getDtAcaoPolicial());
            EdtHorarioAcaoPolicial.setText(acaopolicialdaacaosematualizar.getHsAcaoPolicial());
            EdtLocalAcaoPolicial.setText(acaopolicialdaacaosematualizar.getLocalAcaoPolicial());
            EdtNBuscaJudiciaisAcaoPolicial.setText(String.valueOf(acaopolicialdaacaosematualizar.getNBuscaJudiciaisAcaoPolicial()));
            EdtCrimeObjetoAcaoPolicial.setText(acaopolicialdaacaosematualizar.getDsCrimePrincipalAcaoPolicial());

            if(acaopolicialdaacaosematualizar.getCbkOperacaoInominada() == 0){
                CbkOperacaoInominada.setChecked(false);
            }else{
                CbkOperacaoInominada.setChecked(true);
            }

            if (acaopolicialdaacaosematualizar.getCbkMedidasCautelares() == 0) {
                CbMedidasCautelaresAcaoPolicial.setChecked(false);
            } else {
                CbMedidasCautelaresAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacaosematualizar.getCbkInvestigacoesOrdinarias() == 0) {
                CbDesdobramentoInvestigacaoAcaoPolicial.setChecked(false);
            } else {
                CbDesdobramentoInvestigacaoAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacaosematualizar.getCbkRegistroPolicial() == 0) {
                CbDesdobramentoRegistroAcaoPolicial.setChecked(false);
            } else {
                CbDesdobramentoRegistroAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacaosematualizar.getCbkAbordagemRotina() == 0) {
                CbAbordagemRotinaAcaoPolicial.setChecked(false);
            } else {
                CbAbordagemRotinaAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacaosematualizar.getCbkColaborador() == 0) {
                CbColaboradorAcaoPolicial.setChecked(false);
            } else {
                CbColaboradorAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacaosematualizar.getCbkDenuncia() == 0) {
                CbDenunciaAcaoPolicial.setChecked(false);
            } else {
                CbDenunciaAcaoPolicial.setChecked(true);
            }

            if (acaopolicialdaacaosematualizar.getCbkOutros() == 0) {
                CbOutrosAcaoPolicial.setChecked(false);
            } else {
                CbOutrosAcaoPolicial.setChecked(true);
            }

            if(acaopolicialdaacaosematualizar.getCbkBensAprendidos() == 0){
                CbkBensApreendidosAcaoPolicial.setChecked(false);
            }else {
                CbkBensApreendidosAcaoPolicial.setChecked(true);
            }

            if(acaopolicialdaacaosematualizar.getCbkPresoAcaoPolicial() == 0){
                CbkPresoAcaoPolicial.setChecked(false);
            }else {
                CbkPresoAcaoPolicial.setChecked(true);
            }

            EdtTitutloResumoAcaoPolicial.setText(acaopolicialdaacaosematualizar.getDstituloResumo());
            EdtResumoAcaoPolicial.setText(acaopolicialdaacaosematualizar.getDsResumo());
            EdtMunicipioReferenciaAcaoPolicial.setText(acaopolicialdaacaosematualizar.getDsMunicipioReferenciaAcaoPolicial());
            EdtSubtitulo1.setText(acaopolicialdaacaosematualizar.getDsSubtitulo1());
            EdtTexto1.setText(acaopolicialdaacaosematualizar.getDsTexto1());
            EdtSubtitulo2.setText(acaopolicialdaacaosematualizar.getDsSubtitulo2());
            EdtTexto2.setText(acaopolicialdaacaosematualizar.getDsTexto2());
            EdtSubtitulo3.setText(acaopolicialdaacaosematualizar.getDsSubtitulo3());
            EdtTexto3.setText(acaopolicialdaacaosematualizar.getDsTexto3());
            EdtSubtitulo4.setText(acaopolicialdaacaosematualizar.getDsSubtitulo4());
            EdtTexto4.setText(acaopolicialdaacaosematualizar.getDsTexto4());
        }

        acaopolicial = new Acaopolicial();
        acaopolicialDao = new AcaoPolicialDao(this);

        c = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        EdtDataAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DaAcaoPolicialActivity.this, date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();            }
        });

        EdtHorarioAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int minutos = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(DaAcaoPolicialActivity.this, R.style.Theme_AppCompat_DayNight_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (minute < 10) {
                            EdtHorarioAcaoPolicial.setText(hourOfDay + ":0" + minute);
                        } else {
                            EdtHorarioAcaoPolicial.setText(hourOfDay + ":" + minute);
                        }

                    }
                }, hora, minutos, android.text.format.DateFormat.is24HourFormat(getApplicationContext()));
                tpd.show();
            }
        });

        CbkOperacaoInominada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbkOperacaoInominada.isChecked()){
                    OperacaoInominada = 1;
                    EdtNomeOperacaoAcaoPolicial.setVisibility(View.GONE);
                }else{
                    OperacaoInominada = 0;
                    EdtNomeOperacaoAcaoPolicial.setVisibility(View.VISIBLE);
                }
            }
        });

        CbMedidasCautelaresAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CbMedidasCautelaresAcaoPolicial.isChecked()) {
                    MedidasCautelaresAcaoPolicial = 1;
                } else {
                    MedidasCautelaresAcaoPolicial = 0;
                }
            }
        });

        CbDesdobramentoInvestigacaoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbDesdobramentoInvestigacaoAcaoPolicial.isChecked()){
                    DesdobramentoInvestigacaoAcaoPolicial = 1;
                }else{
                    DesdobramentoInvestigacaoAcaoPolicial = 0;
                }
            }
        });

        CbDesdobramentoRegistroAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbDesdobramentoRegistroAcaoPolicial.isChecked()){
                    DesdobramentoRegistroAcaoPolicial = 1;
                }else{
                    DesdobramentoRegistroAcaoPolicial = 0;
                }
            }
        });

        CbAbordagemRotinaAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbAbordagemRotinaAcaoPolicial.isChecked()){
                    AbordagemRotinaAcaoPolicial = 1;
                }else{
                    AbordagemRotinaAcaoPolicial = 0;
                }
            }
        });

        CbColaboradorAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbColaboradorAcaoPolicial.isChecked()){
                    ColaboradorAcaoPolicial = 1;
                }else{
                    ColaboradorAcaoPolicial = 0;
                }
            }
        });

        CbDenunciaAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbDenunciaAcaoPolicial.isChecked()){
                    DenunciaAcaoPolicial = 1;
                }else{
                    DenunciaAcaoPolicial = 0;
                }
            }
        });

        CbOutrosAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbOutrosAcaoPolicial.isChecked()){
                    OutrosAcaoPolicial = 1;
                }else{
                    OutrosAcaoPolicial = 0;
                }
            }
        });

        CbkPresoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbkPresoAcaoPolicial.isChecked()){
                    Presoacaopolicial = 1;
                }else{
                    Presoacaopolicial = 0;
                }
            }
        });

        CbkBensApreendidosAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbkBensApreendidosAcaoPolicial.isChecked()){
                    Bensapreendidosacaopolicial = 1;
                }else{
                    Bensapreendidosacaopolicial = 0;
                }
            }
        });

        EdtNomeOperacaoAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtDataAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtHorarioAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtLocalAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtNBuscaJudiciaisAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtCrimeObjetoAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtTitutloResumoAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtResumoAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtMunicipioReferenciaAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtSubtitulo1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtTexto1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtSubtitulo2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtTexto2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtSubtitulo3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtTexto3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtSubtitulo4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtTexto4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        BtnClassificacaoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TvClassificacaoAcaoPolicial.setVisibility(View.VISIBLE);
                BtnNomeOperacaoAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        BtnNomeOperacaoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtNomeOperacaoAcaoPolicial.setVisibility(View.VISIBLE);
                CbkOperacaoInominada.setVisibility(View.VISIBLE);
                BtnDtHrLocalAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });
        BtnDtHrLocalAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtDataAcaoPolicial.setVisibility(View.VISIBLE);
                EdtHorarioAcaoPolicial.setVisibility(View.VISIBLE);
                EdtLocalAcaoPolicial.setVisibility(View.VISIBLE);
                TvDtAcaoPolicial.setVisibility(View.VISIBLE);
                TvHrAcaoPolicial.setVisibility(View.VISIBLE);
                TvLocalAcaoPolicial.setVisibility(View.VISIBLE);
                BtnNBuscasJudiciaisAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(DaAcaoPolicialActivity.this);
            }
        });

        BtnNBuscasJudiciaisAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtNBuscaJudiciaisAcaoPolicial.setVisibility(View.VISIBLE);
                BtnCrimeObjetoAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(DaAcaoPolicialActivity.this);
            }
        });

        BtnCrimeObjetoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtCrimeObjetoAcaoPolicial.setVisibility(View.VISIBLE);
                BtnOrigemAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(DaAcaoPolicialActivity.this);
            }
        });

        BtnOrigemAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CbMedidasCautelaresAcaoPolicial.setVisibility(View.VISIBLE);
                CbDesdobramentoInvestigacaoAcaoPolicial.setVisibility(View.VISIBLE);
                CbDesdobramentoRegistroAcaoPolicial.setVisibility(View.VISIBLE);
                CbAbordagemRotinaAcaoPolicial.setVisibility(View.VISIBLE);
                CbColaboradorAcaoPolicial.setVisibility(View.VISIBLE);
                CbDenunciaAcaoPolicial.setVisibility(View.VISIBLE);
                CbOutrosAcaoPolicial.setVisibility(View.VISIBLE);
                CbOutrosAcaoPolicial.setFocusable(true);
                BtnMunicipioReferenciaAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(DaAcaoPolicialActivity.this);
            }
        });

        BtnMunicipioReferenciaAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtMunicipioReferenciaAcaoPolicial.setVisibility(View.VISIBLE);
                EdtMunicipioReferenciaAcaoPolicial.setFocusable(true);
                BtnTituloResumoAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(DaAcaoPolicialActivity.this);
            }
        });

        BtnTituloResumoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtTitutloResumoAcaoPolicial.setVisibility(View.VISIBLE);
                EdtTitutloResumoAcaoPolicial.setFocusable(true);
                BtnResumoAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(DaAcaoPolicialActivity.this);
            }
        });

        BtnResumoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtResumoAcaoPolicial.setVisibility(View.VISIBLE);
                EdtResumoAcaoPolicial.setFocusable(true);
                CbkBensApreendidosAcaoPolicial.setVisibility(View.VISIBLE);
                CbkPresoAcaoPolicial.setVisibility(View.VISIBLE);
                BtnNovoSutituloAcaoPolicial.setVisibility(View.VISIBLE);
                BtnRegistrarAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(DaAcaoPolicialActivity.this);
            }
        });

        BtnNovoSutituloAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subtexto == 1){
                    TvSubtitulo1.setVisibility(View.VISIBLE);
                    EdtSubtitulo1.setVisibility(View.VISIBLE);
                    EdtSubtitulo1.setFocusable(true);
                    TvTexto1.setVisibility(View.VISIBLE);
                    EdtTexto1.setVisibility(View.VISIBLE);

                }
                if(subtexto == 2){
                    TvSubtitulo2.setVisibility(View.VISIBLE);
                    EdtSubtitulo2.setVisibility(View.VISIBLE);
                    EdtSubtitulo2.setFocusable(true);
                    TvTexto2.setVisibility(View.VISIBLE);
                    EdtTexto2.setVisibility(View.VISIBLE);
                }
                if(subtexto == 3){
                    TvSubtitulo3.setVisibility(View.VISIBLE);
                    EdtSubtitulo3.setVisibility(View.VISIBLE);
                    EdtSubtitulo3.setFocusable(true);
                    TvTexto3.setVisibility(View.VISIBLE);
                    EdtTexto3.setVisibility(View.VISIBLE);
                }
                if(subtexto == 4){
                    TvSubtitulo4.setVisibility(View.VISIBLE);
                    EdtSubtitulo4.setVisibility(View.VISIBLE);
                    EdtSubtitulo4.setFocusable(true);
                    TvTexto4.setVisibility(View.VISIBLE);
                    EdtTexto4.setVisibility(View.VISIBLE);
                }

                subtexto = subtexto+1;

                BtnRegistrarAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        BtnRegistrarAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acaopolicial.setDsClassificacaoAcaoPolicial(TvClassificacaoAcaoPolicial.getText().toString());
                acaopolicial.setDsNomeOperacaoPolicial(EdtNomeOperacaoAcaoPolicial.getText().toString());
                acaopolicial.setCbkOperacaoInominada(OperacaoInominada);
                acaopolicial.setDtAcaoPolicial(EdtDataAcaoPolicial.getText().toString());
                acaopolicial.setHsAcaoPolicial(EdtHorarioAcaoPolicial.getText().toString());
                acaopolicial.setLocalAcaoPolicial(EdtLocalAcaoPolicial.getText().toString());
                acaopolicial.setNBuscaJudiciaisAcaoPolicial(Integer.parseInt(EdtNBuscaJudiciaisAcaoPolicial.getText().toString()));
                acaopolicial.setDsCrimePrincipalAcaoPolicial(EdtCrimeObjetoAcaoPolicial.getText().toString());
                acaopolicial.setCbkMedidasCautelares(MedidasCautelaresAcaoPolicial);
                acaopolicial.setCbkInvestigacoesOrdinarias(DesdobramentoInvestigacaoAcaoPolicial);
                acaopolicial.setCbkRegistroPolicial(DesdobramentoRegistroAcaoPolicial);
                acaopolicial.setCbkAbordagemRotina(AbordagemRotinaAcaoPolicial);
                acaopolicial.setCbkColaborador(ColaboradorAcaoPolicial);
                acaopolicial.setCbkDenuncia(DenunciaAcaoPolicial);
                acaopolicial.setCbkOutros(OutrosAcaoPolicial);
                acaopolicial.setDstituloResumo(EdtTitutloResumoAcaoPolicial.getText().toString());
                acaopolicial.setDsResumo(EdtResumoAcaoPolicial.getText().toString());
                acaopolicial.setDsMunicipioReferenciaAcaoPolicial(EdtMunicipioReferenciaAcaoPolicial.getText().toString());
                acaopolicial.setCbkBensAprendidos(Bensapreendidosacaopolicial);
                acaopolicial.setCbkPresoAcaoPolicial(Presoacaopolicial);
                acaopolicial.setDsSubtitulo1(EdtSubtitulo1.getText().toString());
                acaopolicial.setDsTexto1(EdtTexto1.getText().toString());
                acaopolicial.setDsSubtitulo2(EdtSubtitulo2.getText().toString());
                acaopolicial.setDsTexto2(EdtTexto2.getText().toString());
                acaopolicial.setDsSubtitulo3(EdtSubtitulo3.getText().toString());
                acaopolicial.setDsTexto3(EdtTexto3.getText().toString());
                acaopolicial.setDsSubtitulo4(EdtSubtitulo4.getText().toString());
                acaopolicial.setDsTexto4(EdtTexto4.getText().toString());

                int codigoacaopolicial = acaopolicialDao.retornarCodigoAcaoPolicialSemParametro();

                if (atualizar == 1) {
                    long certo = acaopolicialDao.AtualizarAcaoPolicialDaAcao(acaopolicial, acaopolicialdaacao.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(DaAcaoPolicialActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DaAcaoPolicialActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else if (atualizar == 2) {
                    long certo = acaopolicialDao.AtualizarAcaoPolicialDaAcao(acaopolicial, acaopolicialdaacaosematualizar.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(DaAcaoPolicialActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DaAcaoPolicialActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    long certo = acaopolicialDao.cadastrarDaAcaoPolicial(acaopolicial, codigoacaopolicial);
                    if (certo > 0) {
                        Toast.makeText(DaAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DaAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daacaopolicial, menu);

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

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        EdtDataAcaoPolicial.setText(sdf.format(c.getTime()));
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
