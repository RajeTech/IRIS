package com.iris.ramilton.iris;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.EquipePeritoDao;
import com.iris.ramilton.iris.dao.GuiaPericiaProvidenciaDao;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Equipelevantamento;
import com.iris.ramilton.iris.modelo.Equipeperito;
import com.iris.ramilton.iris.modelo.GuiapericiaProvidencia;

import java.util.ArrayList;
import java.util.List;

public class ProvidenciasActivity extends AppCompatActivity {

    private Button BtnGuias, BtnIPProvidencia, BtnCautelaresRepresentadasProvidencia, BtnDestinacoesInvestigacaoProvidencia, BtnCadastroProvidencia, BtnNBO, BtnProvidenciasTomadas;
    private EditText EdtIPProvidencia, EdtNBO, EdtProvidenciasTomadas;
    private Spinner SpDestinacaoInvestigacao;
    private String desticacaoinvestigacao;
    private CheckBox CbBuscaPreensaoProvidencia, CbPrisaoTemporariaProvidencia, CbPrisaoPreventivaProvidencia, CbQuebraSigiloProvidencia, CbMedidasProtetivasUrgencia, CbSemCautelares;
    private ListView LvGuias;
    private RadioGroup RgExpedirGuias;
    private RadioButton RbExpedirGuiaPericial, RbNExpedirGuiaPericial, RbGuiaEscolhida;
    private int ExpedirGuiaPericial,NExpedirGuiaPericial;
    private int bsucapreensao, prisaotemporaria, prisaopreventiva, quebrasigilo, MedidasProtetivasUrgencia, SemCautelares;
    private Cvli cvli, cvliprovidenciasematualizar;
    private CvliDao cvliDao, cvliprovidenciasematualizarDao;
    private GuiaPericiaProvidenciaDao guiaPericiaProvidenciaDao;
    private Cvli cvliprovidencia = null;
    private int atualizar = 0, controleenvio = 0, codigorecebidosem;
    private List<GuiapericiaProvidencia> ListaGuiaPericiaProvidencia;
    private List<GuiapericiaProvidencia> ListaGuiaPericiaProvidenciaFiltrado = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providencias);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        BtnGuias = (Button) findViewById(R.id.btnGuias);
        BtnNBO = (Button) findViewById(R.id.btnBO);
        BtnCadastroProvidencia = (Button) findViewById(R.id.btnCadastroProvidencia);
        BtnIPProvidencia = (Button) findViewById(R.id.btnInqueritoProvidencia);
        BtnCautelaresRepresentadasProvidencia = (Button) findViewById(R.id.btnCautelaresRepresentadasProvidencias);
        BtnDestinacoesInvestigacaoProvidencia = (Button) findViewById(R.id.btnDestinacaoInvestigacaoProvidencia);
        EdtIPProvidencia = (EditText) findViewById(R.id.edtNIP);
        EdtNBO = (EditText) findViewById(R.id.edtNBO);
        SpDestinacaoInvestigacao = (Spinner) findViewById(R.id.spDestinacaoInvestigacao);
        CbBuscaPreensaoProvidencia = (CheckBox) findViewById(R.id.cbBuscaApreensaoProvidencia);
        CbPrisaoTemporariaProvidencia = (CheckBox) findViewById(R.id.cbPrisaoTemporariaProvidencia);
        CbPrisaoPreventivaProvidencia = (CheckBox) findViewById(R.id.cbPrisaoPreventivaProvidencia);
        CbQuebraSigiloProvidencia = (CheckBox) findViewById(R.id.cbQuebraSigiloProvidencia);
        CbMedidasProtetivasUrgencia = (CheckBox) findViewById(R.id.cbMedidasProtetivasUrgenciasProvidencia);
        CbSemCautelares = (CheckBox) findViewById(R.id.cbSemCautelares);
        BtnProvidenciasTomadas = (Button) findViewById(R.id.btnProvidenciasTomadas);
        EdtProvidenciasTomadas = (EditText) findViewById(R.id.edtProvidenciasTomadas);
        LvGuias = (ListView) findViewById(R.id.lvGuias);
        RgExpedirGuias = (RadioGroup) findViewById(R.id.rgGuiapericial);

        cvliprovidenciasematualizar = new Cvli();
        cvliprovidenciasematualizarDao = new CvliDao(this);

        SimpleMaskFormatter mascaraBO = new SimpleMaskFormatter("NN-NNNNN");
        MaskTextWatcher maskBO = new MaskTextWatcher(EdtNBO,mascaraBO);
        EdtNBO.addTextChangedListener(maskBO);

        SimpleMaskFormatter mascaraIP = new SimpleMaskFormatter("NNN/NNNN");
        MaskTextWatcher maskIP = new MaskTextWatcher(EdtIPProvidencia,mascaraIP);
        EdtIPProvidencia.addTextChangedListener(maskIP);

        ArrayAdapter adaptadorDestinacao = ArrayAdapter.createFromResource(this, R.array.DTs23Coorpin, android.R.layout.simple_spinner_item);
        SpDestinacaoInvestigacao.setAdapter(adaptadorDestinacao);

        Intent it = getIntent();
        if (it.hasExtra("providencia")) {
            cvliprovidencia = (Cvli) it.getSerializableExtra("providencia");

            atualizar = 1;
            controleenvio = 4;

            EdtIPProvidencia.setText(cvliprovidencia.getDsNIP());
            EdtNBO.setText(cvliprovidencia.getDsNBO());
            EdtProvidenciasTomadas.setText(cvliprovidencia.getDsProvidencia());

            if (cvliprovidencia.getCbkBuscaApreensao() == 0) {
                CbBuscaPreensaoProvidencia.setChecked(false);
            } else {
                CbBuscaPreensaoProvidencia.setChecked(true);
            }

            if (cvliprovidencia.getCbkPrisaoTemporaria() == 0) {
                CbPrisaoTemporariaProvidencia.setChecked(false);
            } else {
                CbPrisaoTemporariaProvidencia.setChecked(true);
            }

            if (cvliprovidencia.getCbkPrisaoPreventiva() == 0) {
                CbPrisaoPreventivaProvidencia.setChecked(false);
            } else {
                CbPrisaoPreventivaProvidencia.setChecked(true);
            }

            if (cvliprovidencia.getCbkQuebraSigilo() == 0) {
                CbQuebraSigiloProvidencia.setChecked(false);
            } else {
                CbQuebraSigiloProvidencia.setChecked(true);
            }

            if (cvliprovidencia.getCbkMedidasProtetivas() == 0) {
                CbMedidasProtetivasUrgencia.setChecked(false);
            } else {
                CbMedidasProtetivasUrgencia.setChecked(true);
            }

            if (cvliprovidencia.getCbkSemCautelares() == 0) {
                CbSemCautelares.setChecked(false);
            } else {
                CbSemCautelares.setChecked(true);
            }

        }else if(it.hasExtra("providenciasematualizar")){
            codigorecebidosem = (int) it.getSerializableExtra("providenciasematualizar");

            atualizar = 2;
            controleenvio = 4;

            cvliprovidenciasematualizar = cvliprovidenciasematualizarDao.retornaCVLIProvidenciasObj(codigorecebidosem);

            EdtIPProvidencia.setText(cvliprovidenciasematualizar.getDsNIP());
            EdtNBO.setText(cvliprovidenciasematualizar.getDsNBO());
            EdtProvidenciasTomadas.setText(cvliprovidenciasematualizar.getDsProvidencia());

            if (cvliprovidenciasematualizar.getCbkBuscaApreensao() == 0) {
                CbBuscaPreensaoProvidencia.setChecked(false);
            } else {
                CbBuscaPreensaoProvidencia.setChecked(true);
            }

            if (cvliprovidenciasematualizar.getCbkPrisaoTemporaria() == 0) {
                CbPrisaoTemporariaProvidencia.setChecked(false);
            } else {
                CbPrisaoTemporariaProvidencia.setChecked(true);
            }

            if (cvliprovidenciasematualizar.getCbkPrisaoPreventiva() == 0) {
                CbPrisaoPreventivaProvidencia.setChecked(false);
            } else {
                CbPrisaoPreventivaProvidencia.setChecked(true);
            }

            if (cvliprovidenciasematualizar.getCbkQuebraSigilo() == 0) {
                CbQuebraSigiloProvidencia.setChecked(false);
            } else {
                CbQuebraSigiloProvidencia.setChecked(true);
            }

            if (cvliprovidenciasematualizar.getCbkMedidasProtetivas() == 0) {
                CbMedidasProtetivasUrgencia.setChecked(false);
            } else {
                CbMedidasProtetivasUrgencia.setChecked(true);
            }

            if (cvliprovidenciasematualizar.getCbkSemCautelares() == 0) {
                CbSemCautelares.setChecked(false);
            } else {
                CbSemCautelares.setChecked(true);
            }

        }

        cvli = new Cvli();
        cvliDao = new CvliDao(this);

        guiaPericiaProvidenciaDao = new GuiaPericiaProvidenciaDao(this);
        ListaGuiaPericiaProvidencia = guiaPericiaProvidenciaDao.retornarGuiaPericiaProvidencia();
        ListaGuiaPericiaProvidenciaFiltrado.addAll(ListaGuiaPericiaProvidencia);
        ArrayAdapter<GuiapericiaProvidencia> adaptadorGuiaPericiaProvidencia = new ArrayAdapter<GuiapericiaProvidencia>(this, android.R.layout.simple_list_item_1, ListaGuiaPericiaProvidenciaFiltrado);
        LvGuias.setAdapter(adaptadorGuiaPericiaProvidencia);

        BtnNBO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtNBO.setVisibility(View.VISIBLE);
            }
        });

        EdtNBO.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (atualizar == 1) {
                    BtnGuias.setVisibility(View.VISIBLE);
                    EdtNBO.setVisibility(View.VISIBLE);
                } else if(atualizar == 2){
                    BtnGuias.setVisibility(View.VISIBLE);
                    EdtNBO.setVisibility(View.VISIBLE);
                }else {
                    EdtNBO.setVisibility(View.VISIBLE);
                    BtnGuias.setVisibility(View.VISIBLE);
                }
            }
        });

        BtnGuias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(atualizar == 1){
                    RgExpedirGuias.setVisibility(View.VISIBLE);
                    LvGuias.setVisibility(View.VISIBLE);
                    BtnIPProvidencia.setVisibility(View.VISIBLE);
                }else if(atualizar == 2){
                    RgExpedirGuias.setVisibility(View.VISIBLE);
                    LvGuias.setVisibility(View.VISIBLE);
                    BtnIPProvidencia.setVisibility(View.VISIBLE);
                }else {
                    RgExpedirGuias.setVisibility(View.VISIBLE);
                }

            }
        });

        SpDestinacaoInvestigacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                desticacaoinvestigacao = SpDestinacaoInvestigacao.getItemAtPosition(position).toString();

                if(desticacaoinvestigacao.equals("PLANTÃO DE EUNÁPOLIS")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("DT DE EUNÁPOLIS")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("DRFR/ EUNÁPOLIS")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("1ª DT DE PORTO SEGURO")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("2ª DT PORTO SEGURO/ ARRAIAL D’AJUDA")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("DELTUR/ PORTO SEGURO")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("DEAM/ PORTO SEGURO")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("DT DE SANTA CRUZ CABRÁLIA")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("DT DE BELMONTE")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("DT DE ITAGIMIRIM")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("DT DE ITAPEBI")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("DT DE ITABELA")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }
                if(desticacaoinvestigacao.equals("DT DE GUARATINGA")){
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RgExpedirGuias.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbGuiaEscolhida = (RadioButton) RgExpedirGuias.findViewById(checkedId);
                int indice = RgExpedirGuias.indexOfChild(RbGuiaEscolhida);

                if(indice == 0){
                    if (atualizar == 1) {
                        RgExpedirGuias.setVisibility(View.VISIBLE);
                        LvGuias.setVisibility(View.VISIBLE);
                        BtnIPProvidencia.setVisibility(View.VISIBLE);
                        final int co = cvliprovidencia.getId();
                        Intent it = new Intent(ProvidenciasActivity.this, GuiasProvidenciasActivity.class);
                        it.putExtra("codigoGuiaProvidencias", co);
                        startActivity(it);

                    }else if(atualizar == 2){
                        RgExpedirGuias.setVisibility(View.VISIBLE);
                        LvGuias.setVisibility(View.VISIBLE);
                        BtnIPProvidencia.setVisibility(View.VISIBLE);
                        final int co = cvliprovidenciasematualizar.getId();
                        Intent it = new Intent(ProvidenciasActivity.this, GuiasProvidenciasActivity.class);
                        it.putExtra("codigoGuiaProvidencias", co);
                        startActivity(it);
                    }else {
                        ExpedirGuiaPericial = 1;
                        NExpedirGuiaPericial = 0;
                        LvGuias.setVisibility(View.VISIBLE);
                        BtnIPProvidencia.setVisibility(View.VISIBLE);
                        startActivity(new Intent(ProvidenciasActivity.this, GuiasProvidenciasActivity.class));
                    }
                }else{
                    ExpedirGuiaPericial = 0;
                    NExpedirGuiaPericial = 1;
                    BtnIPProvidencia.setVisibility(View.VISIBLE);
                }

            }
        });

        BtnIPProvidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atualizar == 1) {
                    esconderTeclado(ProvidenciasActivity.this);
                    EdtIPProvidencia.setVisibility(View.VISIBLE);
                    BtnCautelaresRepresentadasProvidencia.setVisibility(View.VISIBLE);
                } else if(atualizar == 2){
                    esconderTeclado(ProvidenciasActivity.this);
                    EdtIPProvidencia.setVisibility(View.VISIBLE);
                    BtnCautelaresRepresentadasProvidencia.setVisibility(View.VISIBLE);
                }else {
                    esconderTeclado(ProvidenciasActivity.this);
                    EdtIPProvidencia.setVisibility(View.VISIBLE);
                }
            }
        });

        EdtIPProvidencia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                BtnCautelaresRepresentadasProvidencia.setVisibility(View.VISIBLE);
            }
        });

        BtnCautelaresRepresentadasProvidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (atualizar == 1) {
                    esconderTeclado(ProvidenciasActivity.this);
                    BtnDestinacoesInvestigacaoProvidencia.setVisibility(View.VISIBLE);
                    CbBuscaPreensaoProvidencia.setVisibility(View.VISIBLE);
                    CbPrisaoTemporariaProvidencia.setVisibility(View.VISIBLE);
                    CbPrisaoPreventivaProvidencia.setVisibility(View.VISIBLE);
                    CbQuebraSigiloProvidencia.setVisibility(View.VISIBLE);
                    CbMedidasProtetivasUrgencia.setVisibility(View.VISIBLE);
                    CbSemCautelares.setVisibility(View.VISIBLE);
                }else if(atualizar == 2){
                    esconderTeclado(ProvidenciasActivity.this);
                    BtnDestinacoesInvestigacaoProvidencia.setVisibility(View.VISIBLE);
                    CbBuscaPreensaoProvidencia.setVisibility(View.VISIBLE);
                    CbPrisaoTemporariaProvidencia.setVisibility(View.VISIBLE);
                    CbPrisaoPreventivaProvidencia.setVisibility(View.VISIBLE);
                    CbQuebraSigiloProvidencia.setVisibility(View.VISIBLE);
                    CbMedidasProtetivasUrgencia.setVisibility(View.VISIBLE);
                    CbSemCautelares.setVisibility(View.VISIBLE);
                } else {
                    CbBuscaPreensaoProvidencia.setVisibility(View.VISIBLE);
                    CbPrisaoTemporariaProvidencia.setVisibility(View.VISIBLE);
                    CbPrisaoPreventivaProvidencia.setVisibility(View.VISIBLE);
                    CbQuebraSigiloProvidencia.setVisibility(View.VISIBLE);
                    CbMedidasProtetivasUrgencia.setVisibility(View.VISIBLE);
                    CbSemCautelares.setVisibility(View.VISIBLE);
                    esconderTeclado(ProvidenciasActivity.this);
                }

            }
        });

        CbBuscaPreensaoProvidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CbBuscaPreensaoProvidencia.isChecked()) {
                    bsucapreensao = 1;
                } else {
                    bsucapreensao = 0;
                }
                BtnDestinacoesInvestigacaoProvidencia.setVisibility(View.VISIBLE);
            }
        });

        CbPrisaoTemporariaProvidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CbPrisaoTemporariaProvidencia.isChecked()) {
                    prisaotemporaria = 1;
                } else {
                    prisaotemporaria = 0;
                }
                BtnDestinacoesInvestigacaoProvidencia.setVisibility(View.VISIBLE);
            }
        });

        CbPrisaoPreventivaProvidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CbPrisaoPreventivaProvidencia.isChecked()) {
                    prisaopreventiva = 1;
                } else {
                    prisaopreventiva = 0;
                }
                BtnDestinacoesInvestigacaoProvidencia.setVisibility(View.VISIBLE);
            }
        });

        CbQuebraSigiloProvidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CbQuebraSigiloProvidencia.isChecked()) {
                    quebrasigilo = 1;
                } else {
                    quebrasigilo = 0;
                }
                BtnDestinacoesInvestigacaoProvidencia.setVisibility(View.VISIBLE);
            }
        });

        CbMedidasProtetivasUrgencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CbMedidasProtetivasUrgencia.isChecked()) {
                    MedidasProtetivasUrgencia = 1;
                } else {
                    MedidasProtetivasUrgencia = 0;
                }
                BtnDestinacoesInvestigacaoProvidencia.setVisibility(View.VISIBLE);
            }
        });

        CbSemCautelares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CbSemCautelares.isChecked()) {
                    SemCautelares = 1;
                } else {
                    SemCautelares = 0;
                }
                BtnDestinacoesInvestigacaoProvidencia.setVisibility(View.VISIBLE);
            }
        });

        BtnDestinacoesInvestigacaoProvidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EdtNBO.setFocusable(false);
                SpDestinacaoInvestigacao.setVisibility(View.VISIBLE);

                if(atualizar ==1){
                    EdtNBO.setFocusable(false);
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                    SpDestinacaoInvestigacao.setVisibility(View.VISIBLE);

                    String Dsdestinacaoinvestigacao = cvliprovidencia.getDsDestinacaoInvestigacao();

                    if (Dsdestinacaoinvestigacao == null) {
                        SpDestinacaoInvestigacao.setSelection(0);
                        Dsdestinacaoinvestigacao = SpDestinacaoInvestigacao.getItemAtPosition(0).toString();
                    }

                    if (Dsdestinacaoinvestigacao.equals("Selecione...") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(0);
                    }
                    if (Dsdestinacaoinvestigacao.equals("PLANTÃO DE EUNÁPOLIS") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(1);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE EUNÁPOLIS") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(2);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DRFR/ EUNÁPOLIS") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(3);
                    }
                    if (Dsdestinacaoinvestigacao.equals("1ª DT DE PORTO SEGURO") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(4);
                    }
                    if (Dsdestinacaoinvestigacao.equals("2ª DT PORTO SEGURO/ ARRAIAL D’AJUDA") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(5);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DELTUR/ PORTO SEGURO") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(6);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DEAM/ PORTO SEGURO") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(7);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE SANTA CRUZ CABRÁLIA") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(8);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE BELMONTE") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(9);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE ITAGIMIRIM") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(10);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE ITAPEBI") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(11);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE ITABELA") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(12);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE GUARATINGA") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(13);
                    }
                }else if(atualizar == 2){
                    EdtNBO.setFocusable(false);
                    BtnProvidenciasTomadas.setVisibility(View.VISIBLE);
                    SpDestinacaoInvestigacao.setVisibility(View.VISIBLE);

                    String Dsdestinacaoinvestigacao = cvliprovidenciasematualizar.getDsDestinacaoInvestigacao();

                    if (Dsdestinacaoinvestigacao == null) {
                        SpDestinacaoInvestigacao.setSelection(0);
                        Dsdestinacaoinvestigacao = SpDestinacaoInvestigacao.getItemAtPosition(0).toString();
                    }

                    if (Dsdestinacaoinvestigacao.equals("Selecione...") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(0);
                    }
                    if (Dsdestinacaoinvestigacao.equals("PLANTÃO DE EUNÁPOLIS") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(1);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE EUNÁPOLIS") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(2);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DRFR/ EUNÁPOLIS") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(3);
                    }
                    if (Dsdestinacaoinvestigacao.equals("1ª DT DE PORTO SEGURO") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(4);
                    }
                    if (Dsdestinacaoinvestigacao.equals("2ª DT PORTO SEGURO/ ARRAIAL D’AJUDA") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(5);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DELTUR/ PORTO SEGURO") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(6);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DEAM/ PORTO SEGURO") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(7);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE SANTA CRUZ CABRÁLIA") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(8);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE BELMONTE") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(9);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE ITAGIMIRIM") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(10);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE ITAPEBI") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(11);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE ITABELA") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(12);
                    }
                    if (Dsdestinacaoinvestigacao.equals("DT DE GUARATINGA") && Dsdestinacaoinvestigacao != null) {
                        SpDestinacaoInvestigacao.setSelection(13);
                    }
                }
                }
        });


        BtnProvidenciasTomadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtNBO.setFocusable(false);
                EdtIPProvidencia.setFocusable(false);
                EdtProvidenciasTomadas.setVisibility(View.VISIBLE);

            }
        });

        BtnCadastroProvidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvli.setDsNBO(EdtNBO.getText().toString());
                cvli.setDsNIP(EdtIPProvidencia.getText().toString());
                cvli.setCbkBuscaApreensao(bsucapreensao);
                cvli.setCbkPrisaoTemporaria(prisaotemporaria);
                cvli.setCbkPrisaoPreventiva(prisaopreventiva);
                cvli.setCbkQuebraSigilo(quebrasigilo);
                cvli.setCbkMedidasProtetivas(MedidasProtetivasUrgencia);
                cvli.setCbkSemCautelares(SemCautelares);
                cvli.setDsDestinacaoInvestigacao(desticacaoinvestigacao);
                cvli.setDsProvidencia(EdtProvidenciasTomadas.getText().toString());
                cvli.setCbkExpedidoGuiaPericial(ExpedirGuiaPericial);
                cvli.setCbkNExpedidoGuiaPericial(NExpedirGuiaPericial);

                int codigocvli = cvliDao.retornarCodigoCvliSemParametro();

                if (atualizar == 1) {
                    long certo = cvliDao.AtualizarCVLIProvidencias(cvli, cvliprovidencia.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(ProvidenciasActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProvidenciasActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else if(atualizar == 2){
                    long certo = cvliprovidenciasematualizarDao.AtualizarCVLIProvidencias(cvli, cvliprovidenciasematualizar.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(ProvidenciasActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProvidenciasActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    long certo = cvliDao.CadastrarCVLIProvidencias(cvli, codigocvli);
                    if (certo > 0) {
                        Toast.makeText(ProvidenciasActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProvidenciasActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_providencias, menu);

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

    @Override
    public void onResume() {
        super.onResume();

        if (atualizar == 1) {
            ListaGuiaPericiaProvidencia = guiaPericiaProvidenciaDao.retornarGuiaPericiaProvidenciaAtualizar(cvliprovidencia.getId());
            ListaGuiaPericiaProvidenciaFiltrado.clear();
            ListaGuiaPericiaProvidenciaFiltrado.addAll(ListaGuiaPericiaProvidencia);
            LvGuias.invalidateViews();

        } else {
            ListaGuiaPericiaProvidencia = guiaPericiaProvidenciaDao.retornarGuiaPericiaProvidencia();
            ListaGuiaPericiaProvidenciaFiltrado.clear();
            ListaGuiaPericiaProvidenciaFiltrado.addAll(ListaGuiaPericiaProvidencia);
            LvGuias.invalidateViews();

        }
    }

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}