package com.iris.ramilton.iris;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.CarroDao;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.EquipeLevantamentoDao;
import com.iris.ramilton.iris.dao.EquipePeritoDao;
import com.iris.ramilton.iris.dao.EquipePreservacaoLocalDao;
import com.iris.ramilton.iris.dao.ObjetosApreendidosDao;
import com.iris.ramilton.iris.modelo.Carro;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Equipelevantamento;
import com.iris.ramilton.iris.modelo.Equipeperito;
import com.iris.ramilton.iris.modelo.Equipepreservacaolocal;
import com.iris.ramilton.iris.modelo.ObjetosApreendidos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class SilcActivity extends AppCompatActivity {


    private Button BtnDataSilc, BtnHorarioSilc, BtnEnderecoSilc, BtnEquipePreservacaoLocalSilc, BtnEquipeLevantamentoSilc, BtnEquipePeritosSilc, BtnObjetosapreendidosSilc, BtnSalvarSilc, BtnImportarEnderecoFatoSilc;
    private EditText EdtDataSilc, EdtHorarioSilc, EdtRuaSilc, EdtNRuaSilc, EdtReferenciaLocalSilc, EdtBairroSilc, EdtDisVilPovoSilc, EdtMunicipioSilc, EdtDetalhamentoOutroSilc, EdtDelegadoResponsavel;
    private Spinner SpLogradouroSilc, SpDistVilPovoSilc, SpEstadoSilc, SpZonaSilc, SpDetalhamentoLocalSilc;
    private TextView TvLogradouroSilc, TvNumeroSilc, TvReferenciaLocalSilc, TvBairroSilc, TvDistVilPovoSilc, TvMunicipioSilc, TvZonaSilc, TvDetalhamentoLocalSilc, TvDetalhementoOutroSilc;
    private ListView LvEquipePreservacaoLocalSilc, LvEquipeLevantamentoSilc, LvEquipePeritoSilc, LvObjetosApreendidos;
    private String LogradouroSilc, DistVilPovoSilc, EstadoSilc;
    private RadioGroup RgObjetosApreendidos, RgEquipePeritosSilc, RgEquipeLevantamentoSilc, RgEquipePreservacaolocalSilc, RgSilc;
    private RadioButton RbObjetosApreendidosIdentificado, RbObjetosApreendidosNIdentificado, RbObjetosApreendidosEscolhido, RbEquipePeritosSilcIdentificado, RbEquipePeritosSilcNIdentificado, RbEquipePeritosSilcEscolhido, RbEquipeLevantamentoSilcIdenficado, RbEquipeLevantamentoSilcNIdentificado, RbEquipeLevantamentoSilcEscolhido, RbEquipePreservacaolocalSilcIdenficado, RbEquipePreservacaolocalSilcNIdentificado, RbEquipePreservacaolocalSilcEscolhido, RbSilcRealizado, RbSilcNRealizado, RbSilcIdentificado;
    private int ObjetosApreendidosIdentificado, ObjetosApreendidosNIdentificado, EquipePeritosSilcIdentificado, EquipePeritosSilcNIdentificado, EquipeLevantamentoSilcIdenficado, EquipeLevantamentoSilcNIdentificado, EquipePreservacaolocalSilcIdenficado, EquipePreservacaolocalSilcNIdentificado, Escolhasilc = 0;
    private Cvli cvli, cvliSilcsematualizar;
    private List<Cvli> cvliEndereco;
    private CvliDao cvliDao, cvliSilcsematualizarDao;
    private EquipeLevantamentoDao equipeLevantamentoDao;
    private EquipePeritoDao equipePeritoDao;
    private EquipePreservacaoLocalDao equipePreservacaoLocalDao;
    private ObjetosApreendidosDao objetosApreendidosDao;
    private List<Equipelevantamento> ListaEquipeLevantamento;
    private List<Equipelevantamento> ListaEquipeLevantamentoFiltrado = new ArrayList<>();
    private List<Equipepreservacaolocal> ListaEquipePreservacaoLocal;
    private List<Equipepreservacaolocal> ListaEquipePreservacaoLocalFiltrado = new ArrayList<>();
    private List<Equipeperito> ListaEquipePerito;
    private List<Equipeperito> ListaEquipePeritoFiltrado = new ArrayList<>();
    private List<ObjetosApreendidos> ListaObjetosApreendidos;
    private List<ObjetosApreendidos> ListaObjetosApreendidosFiltrados = new ArrayList<>();
    private List<Equipelevantamento> ListaEquipeLevantamentoAtualizar;
    private List<Equipelevantamento> ListaEquipeLevantamentoAtualizarFiltrado = new ArrayList<>();
    private List<Equipepreservacaolocal> ListaEquipePreservacaoLocalAtualizar;
    private List<Equipepreservacaolocal> ListaEquipePreservacaoLocalAtualizarFiltrado = new ArrayList<>();
    private List<Equipeperito> ListaEquipePeritoAtualizar;
    private List<Equipeperito> ListaEquipePeritoAtualizarFiltrado = new ArrayList<>();
    private List<ObjetosApreendidos> ListaObjetosApreendidosAtualizar;
    private List<ObjetosApreendidos> ListaObjetosApreendidosAtualizarFiltrados = new ArrayList<>();
    private Cvli cvlisilc = null;
    private int atualizar = 0, controleenvio = 0, codigorecebidosem;

    DatePickerDialog.OnDateSetListener dateSilc;

    Calendar c;
    DatePickerDialog dpd;

    Timer t;
    TimePickerDialog tpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silc);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        BtnSalvarSilc = (Button) findViewById(R.id.btnSalvarSIlc);
        BtnObjetosapreendidosSilc = (Button) findViewById(R.id.btnObjetosApreendidosSIlc);
        BtnDataSilc = (Button) findViewById(R.id.btnDataSilc);
        BtnHorarioSilc = (Button) findViewById(R.id.btnHorarioSilc);
        BtnEnderecoSilc = (Button) findViewById(R.id.btnEnderecoSilc);
        BtnEquipeLevantamentoSilc = (Button) findViewById(R.id.btnEquipeLevantamentoSilc);
        BtnEquipePreservacaoLocalSilc = (Button) findViewById(R.id.btnEquipePreservacaolocalSilc);
        BtnEquipePeritosSilc = (Button) findViewById(R.id.btnEquipePeritosSilc);
        EdtDataSilc = (EditText) findViewById(R.id.edtDataSilc);
        EdtHorarioSilc = (EditText) findViewById(R.id.edthorarioSilc);
        EdtRuaSilc = (EditText) findViewById(R.id.edtRuaSilc);
        EdtNRuaSilc = (EditText) findViewById(R.id.edtNumeroRuaSilc);
        EdtReferenciaLocalSilc = (EditText) findViewById(R.id.edtReferenciaLocalSilc);
        EdtBairroSilc = (EditText) findViewById(R.id.edtBairroSilc);
        EdtDisVilPovoSilc = (EditText) findViewById(R.id.edtDisVilPovoSilc);
        EdtMunicipioSilc = (EditText) findViewById(R.id.edtMunicipioSilc);
        SpLogradouroSilc = (Spinner) findViewById(R.id.spinnerLogradouroSilc);
        SpDistVilPovoSilc = (Spinner) findViewById(R.id.spinnerDistVilPovoSilc);
        SpEstadoSilc = (Spinner) findViewById(R.id.spinnerEstadoSilc);
        TvLogradouroSilc = (TextView) findViewById(R.id.tvLogradouroSilc);
        TvNumeroSilc = (TextView) findViewById(R.id.tvNumeroSilc);
        TvReferenciaLocalSilc = (TextView) findViewById(R.id.tvReferenciaLocalSilc);
        TvBairroSilc = (TextView) findViewById(R.id.tvBairroSilc);
        TvDistVilPovoSilc = (TextView) findViewById(R.id.tvDisVilPovoSilc);
        TvMunicipioSilc = (TextView) findViewById(R.id.tvMunicipioSilc);
        LvEquipePreservacaoLocalSilc = (ListView) findViewById(R.id.lvEquipePreservacaoLocalSilc);
        LvEquipeLevantamentoSilc = (ListView) findViewById(R.id.lvEquipeLevantamentoLocalSilc);
        LvEquipePeritoSilc = (ListView) findViewById(R.id.lvEquipePeritoSilc);
        LvObjetosApreendidos = (ListView) findViewById(R.id.lvObjetosApreendidos);
        BtnImportarEnderecoFatoSilc = (Button) findViewById(R.id.btnImportarEnderecoFatoSilc);
        RgObjetosApreendidos = (RadioGroup) findViewById(R.id.rgObjetosApreendidos);
        RgEquipePeritosSilc = (RadioGroup) findViewById(R.id.rgEquipePeritoSilc);
        RgEquipeLevantamentoSilc = (RadioGroup) findViewById(R.id.rgEquipeLevantamentoSilc);
        RgEquipePreservacaolocalSilc = (RadioGroup) findViewById(R.id.rgEquipePreservacaolocalSilc);
        RbObjetosApreendidosIdentificado = (RadioButton) findViewById(R.id.rbObjetosApreendidosIdentificado);
        RbObjetosApreendidosNIdentificado = (RadioButton) findViewById(R.id.rbObjetosApreendidosNidentificado);
        RbEquipePeritosSilcIdentificado = (RadioButton) findViewById(R.id.rbEquipePeritoIdentificadoSilc);
        RbEquipePeritosSilcNIdentificado = (RadioButton) findViewById(R.id.rbEquipePeritoNIdentificadoSilc);
        RbEquipeLevantamentoSilcIdenficado = (RadioButton) findViewById(R.id.rbEquipeLevantamentoIdentificadoSilc);
        RbEquipeLevantamentoSilcNIdentificado = (RadioButton) findViewById(R.id.rbEquipeLevantamentoNIdentificadoSilc);
        RbEquipePreservacaolocalSilcIdenficado = (RadioButton) findViewById(R.id.rbEquipePreservacaolocalIdentificadaSilc);
        RbEquipePreservacaolocalSilcNIdentificado = (RadioButton) findViewById(R.id.rbEquipePreservacaolocalNIdenficadaSilc);
        RgSilc = (RadioGroup) findViewById(R.id.rgSilc);
        RbSilcRealizado = (RadioButton) findViewById(R.id.rbRealizadoSilc);
        RbSilcNRealizado = (RadioButton) findViewById(R.id.rbNRealizadoSilc);
        EdtDelegadoResponsavel = (EditText) findViewById(R.id.edtDelegadoResponsavel);

        cvliSilcsematualizar = new Cvli();
        cvliSilcsematualizarDao = new CvliDao(this);

        Intent it = getIntent();
        if (it.hasExtra("silc")) {

            cvlisilc = (Cvli) it.getSerializableExtra("silc");

            atualizar = 1;
            controleenvio = 4;

            EdtDelegadoResponsavel.setText(cvlisilc.getResponsavelCVLI());
            EdtDataSilc.setText(cvlisilc.getDtDataSilc());
            EdtHorarioSilc.setText(cvlisilc.getHsHorarioSilc());
            EdtRuaSilc.setText(cvlisilc.getDsRuaSilc());
            EdtNRuaSilc.setText(cvlisilc.getDsNRuaSilc());
            EdtReferenciaLocalSilc.setText(cvlisilc.getDsReferenciaLocalSilc());
            EdtBairroSilc.setText(cvlisilc.getDsBairroSilc());
            EdtDisVilPovoSilc.setText(cvlisilc.getDsDistVilPovoSilc());
            EdtMunicipioSilc.setText(cvlisilc.getDsMunicipioSilc());

            if(cvlisilc.getCbkEquipePreservacaoLocalDefinido() == 0){
                RbEquipePreservacaolocalSilcIdenficado.setChecked(true);
            }else{
                RbEquipePreservacaolocalSilcIdenficado.setChecked(false);
            }

            if(cvlisilc.getCbkEquipePreservacaoLocalSilcNDefinido() == 1){
                RbEquipePreservacaolocalSilcNIdentificado.setChecked(true);
            }else{
                RbEquipePreservacaolocalSilcNIdentificado.setChecked(false);
            }

            if(cvlisilc.getCbkEquipeLevantamentoSilcDefinido() == 0){
                RbEquipeLevantamentoSilcIdenficado.setChecked(true);
            }else{
                RbEquipeLevantamentoSilcIdenficado.setChecked(false);
            }

            if(cvlisilc.getCbkEquipeLevantamentoSilcNDefinido() == 1){
                RbEquipeLevantamentoSilcNIdentificado.setChecked(true);
            }else{
                RbEquipeLevantamentoSilcNIdentificado.setChecked(false);
            }

            if(cvlisilc.getCbkEquipePeritosSilcDefinido() == 0){
                RbEquipePeritosSilcIdentificado.setChecked(true);
            }else{
                RbEquipePeritosSilcIdentificado.setChecked(false);
            }

            if(cvlisilc.getCbkEquipePeritosSilcNDefinido() == 1){
                RbEquipePeritosSilcNIdentificado.setChecked(true);
            }else{
                RbEquipePeritosSilcNIdentificado.setChecked(false);
            }

            if(cvlisilc.getCbkObjetosArrecadadosSilcDefinido() == 0){
                RbObjetosApreendidosIdentificado.setChecked(true);
            }else{
                RbObjetosApreendidosIdentificado.setChecked(false);
            }

            if(cvlisilc.getCbkObjetosArrecadadosSilcNDefinido() == 1){
                RbObjetosApreendidosNIdentificado.setChecked(true);
            }else{
                RbObjetosApreendidosNIdentificado.setChecked(false);
            }

        }else if(it.hasExtra("silcsematualizar")){
            codigorecebidosem = (int) it.getSerializableExtra("silcsematualizar");

            cvliSilcsematualizar = cvliSilcsematualizarDao.retornaCVLISilcObj(codigorecebidosem);

            atualizar = 2;
            controleenvio = 4;

            EdtDelegadoResponsavel.setText(cvliSilcsematualizar.getResponsavelCVLI());
            EdtDataSilc.setText(cvliSilcsematualizar.getDtDataSilc());
            EdtHorarioSilc.setText(cvliSilcsematualizar.getHsHorarioSilc());
            EdtRuaSilc.setText(cvliSilcsematualizar.getDsRuaSilc());
            EdtNRuaSilc.setText(cvliSilcsematualizar.getDsNRuaSilc());
            EdtReferenciaLocalSilc.setText(cvliSilcsematualizar.getDsReferenciaLocalSilc());
            EdtBairroSilc.setText(cvliSilcsematualizar.getDsBairroSilc());
            EdtDisVilPovoSilc.setText(cvliSilcsematualizar.getDsDistVilPovoSilc());
            EdtMunicipioSilc.setText(cvliSilcsematualizar.getDsMunicipioSilc());

            if(cvliSilcsematualizar.getCbkEquipePreservacaoLocalDefinido() == 0){
                RbEquipePreservacaolocalSilcIdenficado.setChecked(true);
            }else{
                RbEquipePreservacaolocalSilcIdenficado.setChecked(false);
            }

            if(cvliSilcsematualizar.getCbkEquipePreservacaoLocalSilcNDefinido() == 1){
                RbEquipePreservacaolocalSilcNIdentificado.setChecked(true);
            }else{
                RbEquipePreservacaolocalSilcNIdentificado.setChecked(false);
            }

            if(cvliSilcsematualizar.getCbkEquipeLevantamentoSilcDefinido() == 0){
                RbEquipeLevantamentoSilcIdenficado.setChecked(true);
            }else{
                RbEquipeLevantamentoSilcIdenficado.setChecked(false);
            }

            if(cvliSilcsematualizar.getCbkEquipeLevantamentoSilcNDefinido() == 1){
                RbEquipeLevantamentoSilcNIdentificado.setChecked(true);
            }else{
                RbEquipeLevantamentoSilcNIdentificado.setChecked(false);
            }

            if(cvliSilcsematualizar.getCbkEquipePeritosSilcDefinido() == 0){
                RbEquipePeritosSilcIdentificado.setChecked(true);
            }else{
                RbEquipePeritosSilcIdentificado.setChecked(false);
            }

            if(cvliSilcsematualizar.getCbkEquipePeritosSilcNDefinido() == 1){
                RbEquipePeritosSilcNIdentificado.setChecked(true);
            }else{
                RbEquipePeritosSilcNIdentificado.setChecked(false);
            }

            if(cvliSilcsematualizar.getCbkObjetosArrecadadosSilcDefinido() == 0){
                RbObjetosApreendidosIdentificado.setChecked(true);
            }else{
                RbObjetosApreendidosIdentificado.setChecked(false);
            }

            if(cvliSilcsematualizar.getCbkObjetosArrecadadosSilcNDefinido() == 1){
                RbObjetosApreendidosNIdentificado.setChecked(true);
            }else{
                RbObjetosApreendidosNIdentificado.setChecked(false);
            }

        }


        cvli = new Cvli();
        cvliDao = new CvliDao(this);

        objetosApreendidosDao = new ObjetosApreendidosDao(this);
        ListaObjetosApreendidos = objetosApreendidosDao.retornarObjetosApreendidos();
        ListaObjetosApreendidosFiltrados.addAll(ListaObjetosApreendidos);
        ArrayAdapter<ObjetosApreendidos> adaptadorObjetosApreendidos = new ArrayAdapter<ObjetosApreendidos>(this, android.R.layout.simple_list_item_1, ListaObjetosApreendidosFiltrados);
        LvObjetosApreendidos.setAdapter(adaptadorObjetosApreendidos);

        equipeLevantamentoDao = new EquipeLevantamentoDao(this);
        ListaEquipeLevantamento = equipeLevantamentoDao.retornarEquipeLevantamento();
        ListaEquipeLevantamentoFiltrado.addAll(ListaEquipeLevantamento);
        ArrayAdapter<Equipelevantamento> adaptadorEquipeLevantamento = new ArrayAdapter<Equipelevantamento>(this, android.R.layout.simple_list_item_1, ListaEquipeLevantamentoFiltrado);
        LvEquipeLevantamentoSilc.setAdapter(adaptadorEquipeLevantamento);

        equipePreservacaoLocalDao = new EquipePreservacaoLocalDao(this);
        ListaEquipePreservacaoLocal = equipePreservacaoLocalDao.retornarEquipepreservacaolocal();
        ListaEquipePreservacaoLocalFiltrado.addAll(ListaEquipePreservacaoLocal);
        ArrayAdapter<Equipepreservacaolocal> adaptadorEquipePreservacaoLocal = new ArrayAdapter<Equipepreservacaolocal>(this, android.R.layout.simple_list_item_1, ListaEquipePreservacaoLocalFiltrado);
        LvEquipePreservacaoLocalSilc.setAdapter(adaptadorEquipePreservacaoLocal);

        equipePeritoDao = new EquipePeritoDao(this);
        ListaEquipePerito = equipePeritoDao.retornarEquipePerito();
        ListaEquipePeritoFiltrado.addAll(ListaEquipePerito);
        ArrayAdapter<Equipeperito> adaptadorEquipePerito = new ArrayAdapter<Equipeperito>(this, android.R.layout.simple_list_item_1, ListaEquipePeritoFiltrado);
        LvEquipePeritoSilc.setAdapter(adaptadorEquipePerito);

        ArrayAdapter adaptadorSpLogradouroSilc = ArrayAdapter.createFromResource(this, R.array.Logradouro, android.R.layout.simple_spinner_item);
        SpLogradouroSilc.setAdapter(adaptadorSpLogradouroSilc);

        ArrayAdapter adaptadorSpDistVilPovoSilc = ArrayAdapter.createFromResource(this, R.array.Distrito, android.R.layout.simple_spinner_item);
        SpDistVilPovoSilc.setAdapter(adaptadorSpDistVilPovoSilc);

        ArrayAdapter adaptadorSpEstadoSilc = ArrayAdapter.createFromResource(this, R.array.Estado, android.R.layout.simple_spinner_item);
        SpEstadoSilc.setAdapter(adaptadorSpEstadoSilc);

        BtnDataSilc.setVisibility(View.GONE);

        RgSilc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbSilcIdentificado = (RadioButton) RgSilc.findViewById(checkedId);

                int indice = RgSilc.indexOfChild(RbSilcIdentificado);
                if(indice == 0){
                    BtnDataSilc.setVisibility(View.VISIBLE);
                    BtnEquipeLevantamentoSilc.setText("Equipe do Levantamento");
                    BtnEquipeLevantamentoSilc.setVisibility(View.GONE);
                    RgEquipeLevantamentoSilc.setVisibility(View.GONE);
                    LvEquipeLevantamentoSilc.setVisibility(View.GONE);

                }else{
                    Escolhasilc = 1;
                    BtnDataSilc.setVisibility(View.GONE);
                    EdtDataSilc.setVisibility(View.GONE);
                    BtnHorarioSilc.setVisibility(View.GONE);
                    EdtHorarioSilc.setVisibility(View.GONE);
                    BtnEnderecoSilc.setVisibility(View.GONE);
                    TvLogradouroSilc.setVisibility(View.GONE);
                    SpLogradouroSilc.setVisibility(View.GONE);
                    EdtRuaSilc.setVisibility(View.GONE);
                    BtnImportarEnderecoFatoSilc.setVisibility(View.GONE);
                    TvNumeroSilc.setVisibility(View.GONE);
                    TvReferenciaLocalSilc.setVisibility(View.GONE);
                    EdtNRuaSilc.setVisibility(View.GONE);
                    EdtReferenciaLocalSilc.setVisibility(View.GONE);
                    TvBairroSilc.setVisibility(View.GONE);
                    EdtBairroSilc.setVisibility(View.GONE);
                    TvDistVilPovoSilc.setVisibility(View.GONE);
                    SpDistVilPovoSilc.setVisibility(View.GONE);
                    EdtDisVilPovoSilc.setVisibility(View.GONE);
                    TvMunicipioSilc.setVisibility(View.GONE);
                    EdtMunicipioSilc.setVisibility(View.GONE);
                    SpEstadoSilc.setVisibility(View.GONE);
                    BtnEquipePreservacaoLocalSilc.setVisibility(View.GONE);
                    RgEquipePreservacaolocalSilc.setVisibility(View.GONE);
                    LvEquipePreservacaoLocalSilc.setVisibility(View.GONE);
                    BtnEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    BtnEquipeLevantamentoSilc.setText("Responsável pelo Release");
                    RgEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    LvEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    BtnEquipePeritosSilc.setVisibility(View.GONE);
                    RgEquipePeritosSilc.setVisibility(View.GONE);
                    LvEquipePeritoSilc.setVisibility(View.GONE);
                    BtnObjetosapreendidosSilc.setVisibility(View.GONE);
                    RgObjetosApreendidos.setVisibility(View.GONE);
                    LvObjetosApreendidos.setVisibility(View.GONE);
                }

            }
        });

        c = Calendar.getInstance();

        dateSilc = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        BtnObjetosapreendidosSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atualizar == 1) {
                    LvObjetosApreendidos.setVisibility(View.VISIBLE);
                    RgObjetosApreendidos.setVisibility(View.VISIBLE);
                    BtnDataSilc.setVisibility(View.VISIBLE);
                    final int co = cvlisilc.getId();
                    Log.i("Valores", "" + co);
                    Intent it = new Intent(SilcActivity.this, IncluirObjetosApreendidosActivity.class);
                    it.putExtra("codigoobjetosatualizar", co);
                    startActivity(it);

                } else if(atualizar == 2) {
                    LvObjetosApreendidos.setVisibility(View.VISIBLE);
                    RgObjetosApreendidos.setVisibility(View.VISIBLE);
                    BtnDataSilc.setVisibility(View.VISIBLE);
                    final int co = cvliSilcsematualizar.getId();
                    Log.i("Valores", "" + co);
                    Intent it = new Intent(SilcActivity.this, IncluirObjetosApreendidosActivity.class);
                    it.putExtra("codigoobjetosatualizar", co);
                    startActivity(it);
                    }else{

                        RgObjetosApreendidos.setVisibility(View.VISIBLE);
                        BtnDataSilc.setVisibility(View.VISIBLE);
                    }

            }
        });

        RgObjetosApreendidos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RbObjetosApreendidosEscolhido = (RadioButton) RgObjetosApreendidos.findViewById(checkedId);
                int indice = RgObjetosApreendidos.indexOfChild(RbObjetosApreendidosEscolhido);
                if(indice == 0){
                    esconderTeclado(SilcActivity.this);
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    RbObjetosApreendidosIdentificado.setFocusableInTouchMode(true);
                    RbObjetosApreendidosIdentificado.requestFocus();
                    ObjetosApreendidosIdentificado = 1;
                    ObjetosApreendidosNIdentificado = 0;
                    LvObjetosApreendidos.setVisibility(View.VISIBLE);
                    startActivity(new Intent(SilcActivity.this, IncluirObjetosApreendidosActivity.class));
                }else{
                    esconderTeclado(SilcActivity.this);
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    RbObjetosApreendidosNIdentificado.setFocusableInTouchMode(true);
                    RbObjetosApreendidosNIdentificado.requestFocus();
                    ObjetosApreendidosIdentificado = 0;
                    ObjetosApreendidosNIdentificado = 1;
                    LvObjetosApreendidos.setVisibility(View.GONE);
                }
            }
        });

        BtnDataSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atualizar == 1) {
                    EdtDataSilc.setVisibility(View.VISIBLE);
                    BtnHorarioSilc.setVisibility(View.VISIBLE);
                } else if(atualizar == 2) {
                    EdtDataSilc.setVisibility(View.VISIBLE);
                    BtnHorarioSilc.setVisibility(View.VISIBLE);
                }else{
                    EdtDataSilc.setVisibility(View.VISIBLE);
                }
            }
        });

        EdtDataSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SilcActivity.this, dateSilc, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

                BtnHorarioSilc.setVisibility(View.VISIBLE);
            }
        });

        BtnHorarioSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(SilcActivity.this);
                if (atualizar == 1) {
                    EdtHorarioSilc.setVisibility(View.VISIBLE);
                    BtnEnderecoSilc.setVisibility(View.VISIBLE);
                } else if(atualizar == 2) {
                    EdtHorarioSilc.setVisibility(View.VISIBLE);
                    BtnEnderecoSilc.setVisibility(View.VISIBLE);
                }else{
                    EdtHorarioSilc.setVisibility(View.VISIBLE);
                }
            }
        });

        EdtHorarioSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int minutos = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(SilcActivity.this, R.style.Theme_AppCompat_DayNight_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if(minute <10){
                            EdtHorarioSilc.setText(hourOfDay + ":0" + minute);
                        }else {
                            EdtHorarioSilc.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, hora, minutos, android.text.format.DateFormat.is24HourFormat(getApplicationContext()));
                tpd.show();

                BtnEnderecoSilc.setVisibility(View.VISIBLE);
            }
        });

        BtnEnderecoSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(SilcActivity.this);
                BtnEquipePreservacaoLocalSilc.setVisibility(View.VISIBLE);
                BtnImportarEnderecoFatoSilc.setVisibility(View.VISIBLE);
                EdtRuaSilc.setVisibility(View.VISIBLE);
                EdtNRuaSilc.setVisibility(View.VISIBLE);
                EdtReferenciaLocalSilc.setVisibility(View.VISIBLE);
                EdtBairroSilc.setVisibility(View.VISIBLE);
                EdtDisVilPovoSilc.setVisibility(View.VISIBLE);
                EdtMunicipioSilc.setVisibility(View.VISIBLE);
                SpLogradouroSilc.setVisibility(View.VISIBLE);
                SpDistVilPovoSilc.setVisibility(View.VISIBLE);
                SpEstadoSilc.setVisibility(View.VISIBLE);
                TvLogradouroSilc.setVisibility(View.VISIBLE);
                TvNumeroSilc.setVisibility(View.VISIBLE);
                TvReferenciaLocalSilc.setVisibility(View.VISIBLE);
                TvBairroSilc.setVisibility(View.VISIBLE);
                TvDistVilPovoSilc.setVisibility(View.VISIBLE);
                TvMunicipioSilc.setVisibility(View.VISIBLE);

                if (atualizar == 1) {
                    BtnEquipePreservacaoLocalSilc.setVisibility(View.VISIBLE);
                    LvEquipePreservacaoLocalSilc.setVisibility(View.VISIBLE);
                    BtnEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    LvEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    BtnEquipePeritosSilc.setVisibility(View.VISIBLE);
                    LvEquipePeritoSilc.setVisibility(View.VISIBLE);
                    BtnObjetosapreendidosSilc.setVisibility(View.VISIBLE);
                    LvObjetosApreendidos.setVisibility(View.VISIBLE);
                    RgObjetosApreendidos.setVisibility(View.VISIBLE);
                    RgEquipePeritosSilc.setVisibility(View.VISIBLE);
                    RgEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    RgEquipePreservacaolocalSilc.setVisibility(View.VISIBLE);

                    String Dslogradouro = cvlisilc.getDsLogradouroSilc();

                    if (Dslogradouro == null) {
                        SpLogradouroSilc.setSelection(0);
                        Dslogradouro = SpLogradouroSilc.getItemAtPosition(0).toString();
                    }

                    switch (Dslogradouro) {

                        case "Selecione...":
                            SpLogradouroSilc.setSelection(0);
                            break;
                        case "Rua":
                            SpLogradouroSilc.setSelection(1);
                            break;
                        case "Avenida":
                            SpLogradouroSilc.setSelection(2);
                            break;
                        case "Estrada":
                            SpLogradouroSilc.setSelection(3);
                            break;
                        case "Alameda":
                            SpLogradouroSilc.setSelection(4);
                            break;
                        case "Caminho":
                            SpLogradouroSilc.setSelection(5);
                            break;
                        case "Cemiterio":
                            SpLogradouroSilc.setSelection(6);
                            break;
                        case "Feira":
                            SpLogradouroSilc.setSelection(7);
                            break;
                        case "Rodovia":
                            SpLogradouroSilc.setSelection(8);
                            break;
                        case "Parque":
                            SpLogradouroSilc.setSelection(9);
                            break;
                        case "Travessa":
                            SpLogradouroSilc.setSelection(10);
                            break;
                        case "Largo":
                            SpLogradouroSilc.setSelection(11);
                            break;
                        case "Jardim":
                            SpLogradouroSilc.setSelection(12);
                            break;
                        case "Praça":
                            SpLogradouroSilc.setSelection(13);
                            break;
                        case "Quadra":
                            SpLogradouroSilc.setSelection(14);
                            break;
                        case "Fazenda":
                            SpLogradouroSilc.setSelection(15);
                            break;
                        case "Mata":
                            SpLogradouroSilc.setSelection(16);
                            break;
                        case "Plantio Agricula":
                            SpLogradouroSilc.setSelection(17);
                            break;
                        case "Pasto":
                            SpLogradouroSilc.setSelection(18);
                            break;
                        case "Praia":
                            SpLogradouroSilc.setSelection(19);
                            break;
                        case "Estabelecimento Prisional":
                            SpLogradouroSilc.setSelection(20);
                            break;
                        case "Rio":
                            SpLogradouroSilc.setSelection(21);
                            break;
                        case "Aldeia Indigena":
                            SpLogradouroSilc.setSelection(22);
                            break;
                        case "Assentamento":
                            SpLogradouroSilc.setSelection(23);
                            break;
                        case "Anel Viário":
                            SpLogradouroSilc.setSelection(24);
                            break;
                        default:
                            SpLogradouroSilc.setSelection(25);
                            break;
                    }

                    String Dsdistvilpovo = cvlisilc.getDsDistVilPovoSilc();

                    if (Dsdistvilpovo == null) {
                        SpDistVilPovoSilc.setSelection(0);
                        Dsdistvilpovo = SpDistVilPovoSilc.getItemAtPosition(0).toString();
                    }

                    switch (Dsdistvilpovo) {

                        case "Selecione...":
                            SpDistVilPovoSilc.setSelection(0);
                            break;
                        case "Sede":
                            SpDistVilPovoSilc.setSelection(1);
                            break;
                        case "Distrito":
                            SpDistVilPovoSilc.setSelection(2);
                            break;
                        case "Vilarejo":
                            SpDistVilPovoSilc.setSelection(3);
                            break;
                        default:
                            SpDistVilPovoSilc.setSelection(13);
                            break;
                    }

                    String Dsestadofato = cvlisilc.getDsEstadoSilc();

                    if (Dsestadofato == null) {
                        SpEstadoSilc.setSelection(0);
                        Dsestadofato = SpEstadoSilc.getItemAtPosition(0).toString();
                    }

                    switch (Dsestadofato) {

                        case "BA":
                            SpEstadoSilc.setSelection(0);
                            break;
                        case "AC":
                            SpEstadoSilc.setSelection(1);
                            break;
                        case "AL":
                            SpEstadoSilc.setSelection(2);
                            break;
                        case "AP":
                            SpEstadoSilc.setSelection(3);
                            break;
                        case "AM":
                            SpEstadoSilc.setSelection(4);
                            break;
                        case "CE":
                            SpEstadoSilc.setSelection(5);
                            break;
                        case "DF":
                            SpEstadoSilc.setSelection(6);
                            break;
                        case "ES":
                            SpEstadoSilc.setSelection(7);
                            break;
                        case "GO":
                            SpEstadoSilc.setSelection(8);
                            break;
                        case "MA":
                            SpEstadoSilc.setSelection(9);
                            break;
                        case "MT":
                            SpEstadoSilc.setSelection(10);
                            break;
                        case "MS":
                            SpEstadoSilc.setSelection(11);
                            break;
                        case "MG":
                            SpEstadoSilc.setSelection(12);
                            break;
                        case "PA":
                            SpEstadoSilc.setSelection(13);
                            break;
                        case "PB":
                            SpEstadoSilc.setSelection(14);
                            break;
                        case "PR":
                            SpEstadoSilc.setSelection(15);
                            break;
                        case "PE":
                            SpEstadoSilc.setSelection(16);
                            break;
                        case "PI":
                            SpEstadoSilc.setSelection(17);
                            break;
                        case "RJ":
                            SpEstadoSilc.setSelection(18);
                            break;
                        case "RN":
                            SpEstadoSilc.setSelection(19);
                            break;
                        case "RS":
                            SpEstadoSilc.setSelection(20);
                            break;
                        case "RO":
                            SpEstadoSilc.setSelection(21);
                            break;
                        case "RR":
                            SpEstadoSilc.setSelection(22);
                            break;
                        case "SC":
                            SpEstadoSilc.setSelection(23);
                            break;
                        case "SP":
                            SpEstadoSilc.setSelection(24);
                            break;
                        case "SE":
                            SpEstadoSilc.setSelection(25);
                            break;
                        default:
                            SpEstadoSilc.setSelection(26);
                            break;
                    }
                }else if(atualizar == 2){
                    BtnEquipePreservacaoLocalSilc.setVisibility(View.VISIBLE);
                    LvEquipePreservacaoLocalSilc.setVisibility(View.VISIBLE);
                    BtnEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    LvEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    BtnEquipePeritosSilc.setVisibility(View.VISIBLE);
                    LvEquipePeritoSilc.setVisibility(View.VISIBLE);
                    BtnObjetosapreendidosSilc.setVisibility(View.VISIBLE);
                    LvObjetosApreendidos.setVisibility(View.VISIBLE);
                    RgObjetosApreendidos.setVisibility(View.VISIBLE);
                    RgEquipePeritosSilc.setVisibility(View.VISIBLE);
                    RgEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    RgEquipePreservacaolocalSilc.setVisibility(View.VISIBLE);

                    String Dslogradouro = cvliSilcsematualizar.getDsLogradouroSilc();

                    if (Dslogradouro == null) {
                        SpLogradouroSilc.setSelection(0);
                        Dslogradouro = SpLogradouroSilc.getItemAtPosition(0).toString();
                    }

                    switch (Dslogradouro) {

                        case "Selecione...":
                            SpLogradouroSilc.setSelection(0);
                            break;
                        case "Rua":
                            SpLogradouroSilc.setSelection(1);
                            break;
                        case "Avenida":
                            SpLogradouroSilc.setSelection(2);
                            break;
                        case "Estrada":
                            SpLogradouroSilc.setSelection(3);
                            break;
                        case "Alameda":
                            SpLogradouroSilc.setSelection(4);
                            break;
                        case "Caminho":
                            SpLogradouroSilc.setSelection(5);
                            break;
                        case "Cemiterio":
                            SpLogradouroSilc.setSelection(6);
                            break;
                        case "Feira":
                            SpLogradouroSilc.setSelection(7);
                            break;
                        case "Rodovia":
                            SpLogradouroSilc.setSelection(8);
                            break;
                        case "Parque":
                            SpLogradouroSilc.setSelection(9);
                            break;
                        case "Travessa":
                            SpLogradouroSilc.setSelection(10);
                            break;
                        case "Largo":
                            SpLogradouroSilc.setSelection(11);
                            break;
                        case "Jardim":
                            SpLogradouroSilc.setSelection(12);
                            break;
                        case "Praça":
                            SpLogradouroSilc.setSelection(13);
                            break;
                        case "Quadra":
                            SpLogradouroSilc.setSelection(14);
                            break;
                        case "Fazenda":
                            SpLogradouroSilc.setSelection(15);
                            break;
                        case "Mata":
                            SpLogradouroSilc.setSelection(16);
                            break;
                        case "Plantio Agricula":
                            SpLogradouroSilc.setSelection(17);
                            break;
                        case "Pasto":
                            SpLogradouroSilc.setSelection(18);
                            break;
                        case "Praia":
                            SpLogradouroSilc.setSelection(19);
                            break;
                        case "Estabelecimento Prisional":
                            SpLogradouroSilc.setSelection(20);
                            break;
                        case "Rio":
                            SpLogradouroSilc.setSelection(21);
                            break;
                        case "Aldeia Indigena":
                            SpLogradouroSilc.setSelection(22);
                            break;
                        case "Assentamento":
                            SpLogradouroSilc.setSelection(23);
                            break;
                        case "Anel Viário":
                            SpLogradouroSilc.setSelection(24);
                            break;
                        default:
                            SpLogradouroSilc.setSelection(25);
                            break;
                    }

                    String Dsdistvilpovo = cvliSilcsematualizar.getDsDistVilPovoSilc();

                    if (Dsdistvilpovo == null) {
                        SpDistVilPovoSilc.setSelection(0);
                        Dsdistvilpovo = SpDistVilPovoSilc.getItemAtPosition(0).toString();
                    }

                    switch (Dsdistvilpovo) {

                        case "Selecione...":
                            SpDistVilPovoSilc.setSelection(0);
                            break;
                        case "Sede":
                            SpDistVilPovoSilc.setSelection(1);
                            break;
                        case "Distrito":
                            SpDistVilPovoSilc.setSelection(2);
                            break;
                        case "Vilarejo":
                            SpDistVilPovoSilc.setSelection(3);
                            break;
                        default:
                            SpDistVilPovoSilc.setSelection(13);
                            break;
                    }

                    String Dsestadofato = cvliSilcsematualizar.getDsEstadoSilc();

                    if (Dsestadofato == null) {
                        SpEstadoSilc.setSelection(0);
                        Dsestadofato = SpEstadoSilc.getItemAtPosition(0).toString();
                    }

                    switch (Dsestadofato) {

                        case "BA":
                            SpEstadoSilc.setSelection(0);
                            break;
                        case "AC":
                            SpEstadoSilc.setSelection(1);
                            break;
                        case "AL":
                            SpEstadoSilc.setSelection(2);
                            break;
                        case "AP":
                            SpEstadoSilc.setSelection(3);
                            break;
                        case "AM":
                            SpEstadoSilc.setSelection(4);
                            break;
                        case "CE":
                            SpEstadoSilc.setSelection(5);
                            break;
                        case "DF":
                            SpEstadoSilc.setSelection(6);
                            break;
                        case "ES":
                            SpEstadoSilc.setSelection(7);
                            break;
                        case "GO":
                            SpEstadoSilc.setSelection(8);
                            break;
                        case "MA":
                            SpEstadoSilc.setSelection(9);
                            break;
                        case "MT":
                            SpEstadoSilc.setSelection(10);
                            break;
                        case "MS":
                            SpEstadoSilc.setSelection(11);
                            break;
                        case "MG":
                            SpEstadoSilc.setSelection(12);
                            break;
                        case "PA":
                            SpEstadoSilc.setSelection(13);
                            break;
                        case "PB":
                            SpEstadoSilc.setSelection(14);
                            break;
                        case "PR":
                            SpEstadoSilc.setSelection(15);
                            break;
                        case "PE":
                            SpEstadoSilc.setSelection(16);
                            break;
                        case "PI":
                            SpEstadoSilc.setSelection(17);
                            break;
                        case "RJ":
                            SpEstadoSilc.setSelection(18);
                            break;
                        case "RN":
                            SpEstadoSilc.setSelection(19);
                            break;
                        case "RS":
                            SpEstadoSilc.setSelection(20);
                            break;
                        case "RO":
                            SpEstadoSilc.setSelection(21);
                            break;
                        case "RR":
                            SpEstadoSilc.setSelection(22);
                            break;
                        case "SC":
                            SpEstadoSilc.setSelection(23);
                            break;
                        case "SP":
                            SpEstadoSilc.setSelection(24);
                            break;
                        case "SE":
                            SpEstadoSilc.setSelection(25);
                            break;
                        default:
                            SpEstadoSilc.setSelection(26);
                            break;
                    }
                }
            }
        });

        BtnImportarEnderecoFatoSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(atualizar == 1) {

                    cvliEndereco = cvliDao.retornaEnderecoFato(cvlisilc.getId());

                    String Dslogradouro = cvliEndereco.get(0).getDsLogradouroFato();

                    switch (Dslogradouro) {

                        case "Selecione...":
                            SpLogradouroSilc.setSelection(0);
                            break;
                        case "Rua":
                            SpLogradouroSilc.setSelection(1);
                            break;
                        case "Avenida":
                            SpLogradouroSilc.setSelection(2);
                            break;
                        case "Estrada":
                            SpLogradouroSilc.setSelection(3);
                            break;
                        case "Alameda":
                            SpLogradouroSilc.setSelection(4);
                            break;
                        case "Caminho":
                            SpLogradouroSilc.setSelection(5);
                            break;
                        case "Cemiterio":
                            SpLogradouroSilc.setSelection(6);
                            break;
                        case "Feira":
                            SpLogradouroSilc.setSelection(7);
                            break;
                        case "Rodovia":
                            SpLogradouroSilc.setSelection(8);
                            break;
                        case "Parque":
                            SpLogradouroSilc.setSelection(9);
                            break;
                        case "Travessa":
                            SpLogradouroSilc.setSelection(10);
                            break;
                        case "Largo":
                            SpLogradouroSilc.setSelection(11);
                            break;
                        case "Jardim":
                            SpLogradouroSilc.setSelection(12);
                            break;
                        case "Praça":
                            SpLogradouroSilc.setSelection(13);
                            break;
                        case "Quadra":
                            SpLogradouroSilc.setSelection(14);
                            break;
                        case "Fazenda":
                            SpLogradouroSilc.setSelection(15);
                            break;
                        case "Mata":
                            SpLogradouroSilc.setSelection(16);
                            break;
                        case "Plantio Agricula":
                            SpLogradouroSilc.setSelection(17);
                            break;
                        case "Pasto":
                            SpLogradouroSilc.setSelection(18);
                            break;
                        case "Praia":
                            SpLogradouroSilc.setSelection(19);
                            break;
                        case "Estabelecimento Prisional":
                            SpLogradouroSilc.setSelection(20);
                            break;
                        case "Rio":
                            SpLogradouroSilc.setSelection(21);
                            break;
                        case "Aldeia Indigena":
                            SpLogradouroSilc.setSelection(22);
                            break;
                        case "Assentamento":
                            SpLogradouroSilc.setSelection(23);
                            break;
                        case "Anel Viário":
                            SpLogradouroSilc.setSelection(24);
                            break;
                        default:
                            SpLogradouroSilc.setSelection(25);
                            break;
                    }

                    String Dsdistvilpovo = cvliEndereco.get(0).getDsDistVilPovoFato();

                    switch (Dsdistvilpovo) {

                        case "Selecione...":
                            SpDistVilPovoSilc.setSelection(0);
                            break;
                        case "Sede":
                            SpDistVilPovoSilc.setSelection(1);
                            break;
                        case "Distrito":
                            SpDistVilPovoSilc.setSelection(2);
                            break;
                        case "Vilarejo":
                            SpDistVilPovoSilc.setSelection(3);
                            break;
                        default:
                            SpDistVilPovoSilc.setSelection(4);
                            break;
                    }

                    String Dsestadofato = cvliEndereco.get(0).getDsEstadoFato();

                    switch (Dsestadofato) {

                        case "BA":
                            SpEstadoSilc.setSelection(0);
                            break;
                        case "AC":
                            SpEstadoSilc.setSelection(1);
                            break;
                        case "AL":
                            SpEstadoSilc.setSelection(2);
                            break;
                        case "AP":
                            SpEstadoSilc.setSelection(3);
                            break;
                        case "AM":
                            SpEstadoSilc.setSelection(4);
                            break;
                        case "CE":
                            SpEstadoSilc.setSelection(5);
                            break;
                        case "DF":
                            SpEstadoSilc.setSelection(6);
                            break;
                        case "ES":
                            SpEstadoSilc.setSelection(7);
                            break;
                        case "GO":
                            SpEstadoSilc.setSelection(8);
                            break;
                        case "MA":
                            SpEstadoSilc.setSelection(9);
                            break;
                        case "MT":
                            SpEstadoSilc.setSelection(10);
                            break;
                        case "MS":
                            SpEstadoSilc.setSelection(11);
                            break;
                        case "MG":
                            SpEstadoSilc.setSelection(12);
                            break;
                        case "PA":
                            SpEstadoSilc.setSelection(13);
                            break;
                        case "PB":
                            SpEstadoSilc.setSelection(14);
                            break;
                        case "PR":
                            SpEstadoSilc.setSelection(15);
                            break;
                        case "PE":
                            SpEstadoSilc.setSelection(16);
                            break;
                        case "PI":
                            SpEstadoSilc.setSelection(17);
                            break;
                        case "RJ":
                            SpEstadoSilc.setSelection(18);
                            break;
                        case "RN":
                            SpEstadoSilc.setSelection(19);
                            break;
                        case "RS":
                            SpEstadoSilc.setSelection(20);
                            break;
                        case "RO":
                            SpEstadoSilc.setSelection(21);
                            break;
                        case "RR":
                            SpEstadoSilc.setSelection(22);
                            break;
                        case "SC":
                            SpEstadoSilc.setSelection(23);
                            break;
                        case "SP":
                            SpEstadoSilc.setSelection(24);
                            break;
                        case "SE":
                            SpEstadoSilc.setSelection(25);
                            break;
                        default:
                            SpEstadoSilc.setSelection(26);
                            break;
                    }

                    EdtRuaSilc.setText(cvliEndereco.get(0).getDsRuaFato());
                    EdtNRuaSilc.setText(cvliEndereco.get(0).getDsNRuaFato());
                    EdtReferenciaLocalSilc.setText(cvliEndereco.get(0).getDsReferenciaLocalFato());
                    EdtBairroSilc.setText(cvliEndereco.get(0).getDsBairroFato());
                    EdtDisVilPovoSilc.setText(cvliEndereco.get(0).getDsDescrDistVilPovoFato());
                    EdtMunicipioSilc.setText(cvliEndereco.get(0).getDsMunicipioFato());

                }else if(atualizar == 2){
                    cvliEndereco = cvliSilcsematualizarDao.retornaEnderecoFato(cvliSilcsematualizar.getId());

                    String Dslogradouro = cvliEndereco.get(0).getDsLogradouroFato();

                    switch (Dslogradouro) {

                        case "Selecione...":
                            SpLogradouroSilc.setSelection(0);
                            break;
                        case "Rua":
                            SpLogradouroSilc.setSelection(1);
                            break;
                        case "Avenida":
                            SpLogradouroSilc.setSelection(2);
                            break;
                        case "Estrada":
                            SpLogradouroSilc.setSelection(3);
                            break;
                        case "Alameda":
                            SpLogradouroSilc.setSelection(4);
                            break;
                        case "Caminho":
                            SpLogradouroSilc.setSelection(5);
                            break;
                        case "Cemiterio":
                            SpLogradouroSilc.setSelection(6);
                            break;
                        case "Feira":
                            SpLogradouroSilc.setSelection(7);
                            break;
                        case "Rodovia":
                            SpLogradouroSilc.setSelection(8);
                            break;
                        case "Parque":
                            SpLogradouroSilc.setSelection(9);
                            break;
                        case "Travessa":
                            SpLogradouroSilc.setSelection(10);
                            break;
                        case "Largo":
                            SpLogradouroSilc.setSelection(11);
                            break;
                        case "Jardim":
                            SpLogradouroSilc.setSelection(12);
                            break;
                        case "Praça":
                            SpLogradouroSilc.setSelection(13);
                            break;
                        case "Quadra":
                            SpLogradouroSilc.setSelection(14);
                            break;
                        case "Fazenda":
                            SpLogradouroSilc.setSelection(15);
                            break;
                        case "Mata":
                            SpLogradouroSilc.setSelection(16);
                            break;
                        case "Plantio Agricula":
                            SpLogradouroSilc.setSelection(17);
                            break;
                        case "Pasto":
                            SpLogradouroSilc.setSelection(18);
                            break;
                        case "Praia":
                            SpLogradouroSilc.setSelection(19);
                            break;
                        case "Estabelecimento Prisional":
                            SpLogradouroSilc.setSelection(20);
                            break;
                        case "Rio":
                            SpLogradouroSilc.setSelection(21);
                            break;
                        case "Aldeia Indigena":
                            SpLogradouroSilc.setSelection(22);
                            break;
                        case "Assentamento":
                            SpLogradouroSilc.setSelection(23);
                            break;
                        case "Anel Viário":
                            SpLogradouroSilc.setSelection(24);
                            break;
                        default:
                            SpLogradouroSilc.setSelection(25);
                            break;
                    }

                    String Dsdistvilpovo = cvliEndereco.get(0).getDsDistVilPovoFato();

                    switch (Dsdistvilpovo) {

                        case "Selecione...":
                            SpDistVilPovoSilc.setSelection(0);
                            break;
                        case "Sede":
                            SpDistVilPovoSilc.setSelection(1);
                            break;
                        case "Distrito":
                            SpDistVilPovoSilc.setSelection(2);
                            break;
                        case "Vilarejo":
                            SpDistVilPovoSilc.setSelection(3);
                            break;
                        default:
                            SpDistVilPovoSilc.setSelection(4);
                            break;
                    }

                    String Dsestadofato = cvliEndereco.get(0).getDsEstadoFato();

                    switch (Dsestadofato) {

                        case "BA":
                            SpEstadoSilc.setSelection(0);
                            break;
                        case "AC":
                            SpEstadoSilc.setSelection(1);
                            break;
                        case "AL":
                            SpEstadoSilc.setSelection(2);
                            break;
                        case "AP":
                            SpEstadoSilc.setSelection(3);
                            break;
                        case "AM":
                            SpEstadoSilc.setSelection(4);
                            break;
                        case "CE":
                            SpEstadoSilc.setSelection(5);
                            break;
                        case "DF":
                            SpEstadoSilc.setSelection(6);
                            break;
                        case "ES":
                            SpEstadoSilc.setSelection(7);
                            break;
                        case "GO":
                            SpEstadoSilc.setSelection(8);
                            break;
                        case "MA":
                            SpEstadoSilc.setSelection(9);
                            break;
                        case "MT":
                            SpEstadoSilc.setSelection(10);
                            break;
                        case "MS":
                            SpEstadoSilc.setSelection(11);
                            break;
                        case "MG":
                            SpEstadoSilc.setSelection(12);
                            break;
                        case "PA":
                            SpEstadoSilc.setSelection(13);
                            break;
                        case "PB":
                            SpEstadoSilc.setSelection(14);
                            break;
                        case "PR":
                            SpEstadoSilc.setSelection(15);
                            break;
                        case "PE":
                            SpEstadoSilc.setSelection(16);
                            break;
                        case "PI":
                            SpEstadoSilc.setSelection(17);
                            break;
                        case "RJ":
                            SpEstadoSilc.setSelection(18);
                            break;
                        case "RN":
                            SpEstadoSilc.setSelection(19);
                            break;
                        case "RS":
                            SpEstadoSilc.setSelection(20);
                            break;
                        case "RO":
                            SpEstadoSilc.setSelection(21);
                            break;
                        case "RR":
                            SpEstadoSilc.setSelection(22);
                            break;
                        case "SC":
                            SpEstadoSilc.setSelection(23);
                            break;
                        case "SP":
                            SpEstadoSilc.setSelection(24);
                            break;
                        case "SE":
                            SpEstadoSilc.setSelection(25);
                            break;
                        default:
                            SpEstadoSilc.setSelection(26);
                            break;
                    }

                    EdtRuaSilc.setText(cvliEndereco.get(0).getDsRuaFato());
                    EdtNRuaSilc.setText(cvliEndereco.get(0).getDsNRuaFato());
                    EdtReferenciaLocalSilc.setText(cvliEndereco.get(0).getDsReferenciaLocalFato());
                    EdtBairroSilc.setText(cvliEndereco.get(0).getDsBairroFato());
                    EdtDisVilPovoSilc.setText(cvliEndereco.get(0).getDsDescrDistVilPovoFato());
                    EdtMunicipioSilc.setText(cvliEndereco.get(0).getDsMunicipioFato());
                }else{
                    cvliEndereco = cvliDao.retornaEnderecoFato(cvliDao.retornarCodigoCvliSemParametro());

                    String Dslogradouro = cvliEndereco.get(0).getDsLogradouroFato();

                    switch (Dslogradouro) {

                        case "Selecione...":
                            SpLogradouroSilc.setSelection(0);
                            break;
                        case "Rua":
                            SpLogradouroSilc.setSelection(1);
                            break;
                        case "Avenida":
                            SpLogradouroSilc.setSelection(2);
                            break;
                        case "Estrada":
                            SpLogradouroSilc.setSelection(3);
                            break;
                        case "Alameda":
                            SpLogradouroSilc.setSelection(4);
                            break;
                        case "Caminho":
                            SpLogradouroSilc.setSelection(5);
                            break;
                        case "Cemiterio":
                            SpLogradouroSilc.setSelection(6);
                            break;
                        case "Feira":
                            SpLogradouroSilc.setSelection(7);
                            break;
                        case "Rodovia":
                            SpLogradouroSilc.setSelection(8);
                            break;
                        case "Parque":
                            SpLogradouroSilc.setSelection(9);
                            break;
                        case "Travessa":
                            SpLogradouroSilc.setSelection(10);
                            break;
                        case "Largo":
                            SpLogradouroSilc.setSelection(11);
                            break;
                        case "Jardim":
                            SpLogradouroSilc.setSelection(12);
                            break;
                        case "Praça":
                            SpLogradouroSilc.setSelection(13);
                            break;
                        case "Quadra":
                            SpLogradouroSilc.setSelection(14);
                            break;
                        case "Fazenda":
                            SpLogradouroSilc.setSelection(15);
                            break;
                        case "Mata":
                            SpLogradouroSilc.setSelection(16);
                            break;
                        case "Plantio Agricula":
                            SpLogradouroSilc.setSelection(17);
                            break;
                        case "Pasto":
                            SpLogradouroSilc.setSelection(18);
                            break;
                        case "Praia":
                            SpLogradouroSilc.setSelection(19);
                            break;
                        case "Estabelecimento Prisional":
                            SpLogradouroSilc.setSelection(20);
                            break;
                        case "Rio":
                            SpLogradouroSilc.setSelection(21);
                            break;
                        case "Aldeia Indigena":
                            SpLogradouroSilc.setSelection(22);
                            break;
                        case "Assentamento":
                            SpLogradouroSilc.setSelection(23);
                            break;
                        case "Anel Viário":
                            SpLogradouroSilc.setSelection(24);
                            break;
                        default:
                            SpLogradouroSilc.setSelection(25);
                            break;
                    }

                    String Dsdistvilpovo = cvliEndereco.get(0).getDsDistVilPovoFato();

                    switch (Dsdistvilpovo) {

                        case "Selecione...":
                            SpDistVilPovoSilc.setSelection(0);
                            break;
                        case "Sede":
                            SpDistVilPovoSilc.setSelection(1);
                            break;
                        case "Distrito":
                            SpDistVilPovoSilc.setSelection(2);
                            break;
                        case "Vilarejo":
                            SpDistVilPovoSilc.setSelection(3);
                            break;
                        default:
                            SpDistVilPovoSilc.setSelection(4);
                            break;
                    }

                    String Dsestadofato = cvliEndereco.get(0).getDsEstadoFato();

                    switch (Dsestadofato) {

                        case "BA":
                            SpEstadoSilc.setSelection(0);
                            break;
                        case "AC":
                            SpEstadoSilc.setSelection(1);
                            break;
                        case "AL":
                            SpEstadoSilc.setSelection(2);
                            break;
                        case "AP":
                            SpEstadoSilc.setSelection(3);
                            break;
                        case "AM":
                            SpEstadoSilc.setSelection(4);
                            break;
                        case "CE":
                            SpEstadoSilc.setSelection(5);
                            break;
                        case "DF":
                            SpEstadoSilc.setSelection(6);
                            break;
                        case "ES":
                            SpEstadoSilc.setSelection(7);
                            break;
                        case "GO":
                            SpEstadoSilc.setSelection(8);
                            break;
                        case "MA":
                            SpEstadoSilc.setSelection(9);
                            break;
                        case "MT":
                            SpEstadoSilc.setSelection(10);
                            break;
                        case "MS":
                            SpEstadoSilc.setSelection(11);
                            break;
                        case "MG":
                            SpEstadoSilc.setSelection(12);
                            break;
                        case "PA":
                            SpEstadoSilc.setSelection(13);
                            break;
                        case "PB":
                            SpEstadoSilc.setSelection(14);
                            break;
                        case "PR":
                            SpEstadoSilc.setSelection(15);
                            break;
                        case "PE":
                            SpEstadoSilc.setSelection(16);
                            break;
                        case "PI":
                            SpEstadoSilc.setSelection(17);
                            break;
                        case "RJ":
                            SpEstadoSilc.setSelection(18);
                            break;
                        case "RN":
                            SpEstadoSilc.setSelection(19);
                            break;
                        case "RS":
                            SpEstadoSilc.setSelection(20);
                            break;
                        case "RO":
                            SpEstadoSilc.setSelection(21);
                            break;
                        case "RR":
                            SpEstadoSilc.setSelection(22);
                            break;
                        case "SC":
                            SpEstadoSilc.setSelection(23);
                            break;
                        case "SP":
                            SpEstadoSilc.setSelection(24);
                            break;
                        case "SE":
                            SpEstadoSilc.setSelection(25);
                            break;
                        default:
                            SpEstadoSilc.setSelection(26);
                            break;
                    }

                    EdtRuaSilc.setText(cvliEndereco.get(0).getDsRuaFato());
                    EdtNRuaSilc.setText(cvliEndereco.get(0).getDsNRuaFato());
                    EdtReferenciaLocalSilc.setText(cvliEndereco.get(0).getDsReferenciaLocalFato());
                    EdtBairroSilc.setText(cvliEndereco.get(0).getDsBairroFato());
                    EdtDisVilPovoSilc.setText(cvliEndereco.get(0).getDsDescrDistVilPovoFato());
                    EdtMunicipioSilc.setText(cvliEndereco.get(0).getDsMunicipioFato());

                }
            }
        });

        SpLogradouroSilc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                LogradouroSilc = SpLogradouroSilc.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpDistVilPovoSilc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DistVilPovoSilc = SpDistVilPovoSilc.getItemAtPosition(position).toString();
                if(DistVilPovoSilc.equals("Sede")){
                    EdtDisVilPovoSilc.setEnabled(false);
                }else{
                    EdtDisVilPovoSilc.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpEstadoSilc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                EstadoSilc = SpEstadoSilc.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EdtRuaSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BtnEquipePreservacaoLocalSilc.setVisibility(View.VISIBLE);
            }
        });

        BtnEquipePreservacaoLocalSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atualizar == 1) {
                    EdtHorarioSilc.setFocusable(false);
                    RgEquipePreservacaolocalSilc.setVisibility(View.VISIBLE);
                    final int co = cvlisilc.getId();
                    Intent it = new Intent(SilcActivity.this, IncluirEqPresLocalActivity.class);
                    it.putExtra("codigoEquipePresLocalsatualizar", co);
                    startActivity(it);

                } else if(atualizar == 2) {
                    EdtHorarioSilc.setFocusable(false);
                    RgEquipePreservacaolocalSilc.setVisibility(View.VISIBLE);
                    final int co = cvliSilcsematualizar.getId();
                    Intent it = new Intent(SilcActivity.this, IncluirEqPresLocalActivity.class);
                    it.putExtra("codigoEquipePresLocalsatualizar", co);
                    startActivity(it);

                }else {
                    EdtHorarioSilc.setFocusable(false);
                    RgEquipePreservacaolocalSilc.setVisibility(View.VISIBLE);
                    }

                }
        });

        RgEquipePreservacaolocalSilc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RbEquipePreservacaolocalSilcEscolhido = (RadioButton) RgEquipePreservacaolocalSilc.findViewById(checkedId);
                int indice = RgEquipePreservacaolocalSilc.indexOfChild(RbEquipePreservacaolocalSilcEscolhido);

                if(indice == 0){
                    esconderTeclado(SilcActivity.this);
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    EdtNRuaSilc.setFocusable(false);
                    RbEquipePreservacaolocalSilcIdenficado.setFocusableInTouchMode(true);
                    RbEquipePreservacaolocalSilcIdenficado.requestFocus();
                    EquipePreservacaolocalSilcIdenficado = 1;
                    EquipePreservacaolocalSilcNIdentificado= 0;
                    BtnEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    LvEquipePreservacaoLocalSilc.setVisibility(View.VISIBLE);
                    startActivity(new Intent(SilcActivity.this, IncluirEqPresLocalActivity.class));
                }else{
                    esconderTeclado(SilcActivity.this);
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    EdtNRuaSilc.setFocusable(false);
                    RbEquipePreservacaolocalSilcNIdentificado.setFocusableInTouchMode(true);
                    RbEquipePreservacaolocalSilcNIdentificado.requestFocus();
                    EquipePreservacaolocalSilcIdenficado = 0;
                    EquipePreservacaolocalSilcNIdentificado= 1;
                    LvEquipePreservacaoLocalSilc.setVisibility(View.GONE);
                    BtnEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    BtnEquipeLevantamentoSilc.setFocusable(true);
                }
            }
        });

        BtnEquipeLevantamentoSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atualizar == 1) {
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    RgEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    LvEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    final int co = cvlisilc.getId();
                    Intent it = new Intent(SilcActivity.this, IncluirEquipeLevantamentoActivity.class);
                    it.putExtra("codigoEquipeLevantamentosatualizar", co);
                    startActivity(it);

                } else if(atualizar == 2){
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    RgEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    LvEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    final int co = cvliSilcsematualizar.getId();
                    Intent it = new Intent(SilcActivity.this, IncluirEquipeLevantamentoActivity.class);
                    it.putExtra("codigoEquipeLevantamentosatualizar", co);
                    startActivity(it);
                }else {
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    RgEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                }
            }
        });

        RgEquipeLevantamentoSilc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RbEquipeLevantamentoSilcEscolhido = (RadioButton) RgEquipeLevantamentoSilc.findViewById(checkedId);

                int indice = RgEquipeLevantamentoSilc.indexOfChild(RbEquipeLevantamentoSilcEscolhido);
                if(indice == 0){
                    esconderTeclado(SilcActivity.this);
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    RbEquipeLevantamentoSilcIdenficado.setFocusableInTouchMode(true);
                    RbEquipeLevantamentoSilcIdenficado.requestFocus();
                    EquipeLevantamentoSilcIdenficado = 1;
                    EquipeLevantamentoSilcNIdentificado = 0;
                    if(Escolhasilc == 0){
                        BtnEquipePeritosSilc.setVisibility(View.GONE);
                    }else{
                        BtnEquipePeritosSilc.setVisibility(View.VISIBLE);
                    }
                    LvEquipeLevantamentoSilc.setVisibility(View.VISIBLE);
                    startActivity(new Intent(SilcActivity.this, IncluirEquipeLevantamentoActivity.class));

                }else{
                    esconderTeclado(SilcActivity.this);
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    RbEquipeLevantamentoSilcNIdentificado.setFocusableInTouchMode(true);
                    RbEquipeLevantamentoSilcNIdentificado.requestFocus();
                    EquipeLevantamentoSilcIdenficado = 0;
                    EquipeLevantamentoSilcNIdentificado = 1;
                    BtnEquipePeritosSilc.setVisibility(View.VISIBLE);
                    LvEquipeLevantamentoSilc.setVisibility(View.GONE);
                    BtnEquipePeritosSilc.setFocusable(true);
                }
            }
        });

        BtnEquipePeritosSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atualizar == 1) {
                    LvEquipePeritoSilc.setVisibility(View.VISIBLE);
                    RgEquipePeritosSilc.setVisibility(View.VISIBLE);
                    final int co = cvlisilc.getId();
                    Intent it = new Intent(SilcActivity.this, IncluirEquipePeritoActivity.class);
                    it.putExtra("codigoEquiperitoatualizar", co);
                    startActivity(it);
                } else if(atualizar == 2){
                    LvEquipePeritoSilc.setVisibility(View.VISIBLE);
                    RgEquipePeritosSilc.setVisibility(View.VISIBLE);
                    final int co = cvliSilcsematualizar.getId();
                    Intent it = new Intent(SilcActivity.this, IncluirEquipePeritoActivity.class);
                    it.putExtra("codigoEquiperitoatualizar", co);
                    startActivity(it);
                }else {
                    BtnObjetosapreendidosSilc.setFocusable(true);
                    RgEquipePeritosSilc.setVisibility(View.VISIBLE);

                }

            }
        });

        RgEquipePeritosSilc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RbEquipePeritosSilcEscolhido = (RadioButton) RgEquipePeritosSilc.findViewById(checkedId);
                int indice = RgEquipePeritosSilc.indexOfChild(RbEquipePeritosSilcEscolhido);

                if(indice == 0){
                    esconderTeclado(SilcActivity.this);
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    RbEquipePeritosSilcIdentificado.setFocusableInTouchMode(true);
                    RbEquipePeritosSilcIdentificado.requestFocus();
                    EquipePeritosSilcIdentificado = 1;
                    EquipePeritosSilcNIdentificado = 0;
                    BtnObjetosapreendidosSilc.setVisibility(View.VISIBLE);
                    LvEquipePeritoSilc.setVisibility(View.VISIBLE);
                    startActivity(new Intent(SilcActivity.this, IncluirEquipePeritoActivity.class));
                }else{
                    esconderTeclado(SilcActivity.this);
                    EdtDataSilc.setFocusable(false);
                    EdtHorarioSilc.setFocusable(false);
                    RbEquipePeritosSilcNIdentificado.setFocusableInTouchMode(true);
                    RbEquipePeritosSilcNIdentificado.requestFocus();
                    EquipePeritosSilcIdentificado = 0;
                    EquipePeritosSilcNIdentificado = 1;
                    BtnObjetosapreendidosSilc.setVisibility(View.VISIBLE);
                    BtnObjetosapreendidosSilc.setFocusable(true);
                    LvEquipePeritoSilc.setVisibility(View.GONE);
                }
            }
        });

        EdtRuaSilc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtNRuaSilc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtDisVilPovoSilc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtMunicipioSilc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtBairroSilc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtReferenciaLocalSilc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        BtnSalvarSilc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cvli.setResponsavelCVLI(EdtDelegadoResponsavel.getText().toString());
                cvli.setDtDataSilc(EdtDataSilc.getText().toString());
                cvli.setHsHorarioSilc(EdtHorarioSilc.getText().toString());
                cvli.setDsLogradouroSilc(LogradouroSilc);
                cvli.setDsRuaSilc(EdtRuaSilc.getText().toString());
                cvli.setDsNRuaSilc(EdtNRuaSilc.getText().toString());
                cvli.setDsReferenciaLocalSilc(EdtReferenciaLocalSilc.getText().toString());
                cvli.setDsBairroSilc(EdtBairroSilc.getText().toString());
                cvli.setDsMunicipioSilc(EdtMunicipioSilc.getText().toString());
                cvli.setDsDistVilPovoSilc(DistVilPovoSilc);
                cvli.setDsDescrDistVilPovoSilc(EdtDisVilPovoSilc.getText().toString());
                cvli.setDsEstadoSilc(EstadoSilc);
                cvli.setCbkEquipePreservacaoLocalDefinido(EquipePreservacaolocalSilcIdenficado);
                cvli.setCbkEquipePreservacaoLocalSilcNDefinido(EquipePreservacaolocalSilcNIdentificado);
                cvli.setCbkEquipeLevantamentoSilcDefinido(EquipeLevantamentoSilcIdenficado);
                cvli.setCbkEquipeLevantamentoSilcNDefinido(EquipeLevantamentoSilcNIdentificado);
                cvli.setCbkEquipePeritosSilcDefinido(EquipePeritosSilcIdentificado);
                cvli.setCbkEquipePeritosSilcNDefinido(EquipePeritosSilcNIdentificado);
                cvli.setCbkObjetosArrecadadosSilcDefinido(ObjetosApreendidosIdentificado);
                cvli.setCbkObjetosArrecadadosSilcNDefinido(ObjetosApreendidosNIdentificado);

                int codigocvli = cvliDao.retornarCodigoCvliSemParametro();


                if (atualizar == 1) {

                    String data = cvliDao.retornarDataFato(cvlisilc.getId());
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                    try {
                        Date date1 = format.parse(EdtDataSilc.getText().toString());
                        Date date2 = format.parse(data);

                        if (date1.compareTo(date2) > 0) {
                            AlertDialog.Builder b = new AlertDialog.Builder(SilcActivity.this);
                            LayoutInflater factory = LayoutInflater.from(SilcActivity.this);
                            final View view = factory.inflate(R.layout.logotipo, null);
                            b.setTitle("IRIS - Atenção!!!")
                                    .setView(view)
                                    .setMessage("A data não pode ser anterior a data do fato.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            EdtDataSilc.setFocusable(true);
                                        }
                                    }).create();
                            b.show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long certo = cvliDao.AtualizarCVLISilc(cvli, cvlisilc.getId(), controleenvio);

                    if (certo > 0) {
                        Toast.makeText(SilcActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SilcActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else if(atualizar == 2){
                    String data = cvliSilcsematualizarDao.retornarDataFato(cvliSilcsematualizar.getId());
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                    try {
                        Date date1 = format.parse(EdtDataSilc.getText().toString());
                        Date date2 = format.parse(data);

                        if (date1.compareTo(date2) > 0) {
                            AlertDialog.Builder b = new AlertDialog.Builder(SilcActivity.this);
                            LayoutInflater factory = LayoutInflater.from(SilcActivity.this);
                            final View view = factory.inflate(R.layout.logotipo, null);
                            b.setTitle("IRIS - Atenção!!!")
                                    .setView(view)
                                    .setMessage("A data não pode ser anterior a data do fato.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            EdtDataSilc.setFocusable(true);
                                        }
                                    }).create();
                            b.show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long certo = cvliSilcsematualizarDao.AtualizarCVLISilc(cvli, cvliSilcsematualizar.getId(), controleenvio);

                    if (certo > 0) {
                        Toast.makeText(SilcActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SilcActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    String data = cvliDao.retornarDataFato(codigocvli);

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                    try {
                        Date date1 = format.parse(EdtDataSilc.getText().toString());
                        Date date2 = format.parse(data);

                        if (date1.compareTo(date2) <= 0) {
                            AlertDialog.Builder b = new AlertDialog.Builder(SilcActivity.this);
                            LayoutInflater factory = LayoutInflater.from(SilcActivity.this);
                            final View view = factory.inflate(R.layout.logotipo, null);
                            b.setTitle("IRIS - Atenção!!!")
                                    .setView(view)
                                    .setMessage("A data não pode ser anterior a data do fato.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            EdtDataSilc.setFocusable(true);
                                        }
                                    }).create();
                            b.show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long certo = cvliDao.cadastrarCVLISilc(cvli,codigocvli);
                    if (certo > 0) {
                        Toast.makeText(SilcActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SilcActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_silc, menu);

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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        EdtDataSilc.setText(sdf.format(c.getTime()));
    }

    @Override
    public void onResume() {
        super.onResume();

        if (atualizar == 1) {

            ListaObjetosApreendidos = objetosApreendidosDao.retornarObjetosApreendidosAtualizar(cvlisilc.getId());
            ListaObjetosApreendidosFiltrados.clear();
            ListaObjetosApreendidosFiltrados.addAll(ListaObjetosApreendidos);
            LvObjetosApreendidos.invalidateViews();

            ListaEquipeLevantamento = equipeLevantamentoDao.retornarEquipeLevantamentoAtualizar(cvlisilc.getId());
            ListaEquipeLevantamentoFiltrado.clear();
            ListaEquipeLevantamentoFiltrado.addAll(ListaEquipeLevantamento);
            LvEquipeLevantamentoSilc.invalidateViews();

            ListaEquipePreservacaoLocal = equipePreservacaoLocalDao.retornarEquipepreservacaolocalAtualizar(cvlisilc.getId());
            ListaEquipePreservacaoLocalFiltrado.clear();
            ListaEquipePreservacaoLocalFiltrado.addAll(ListaEquipePreservacaoLocal);
            LvEquipePreservacaoLocalSilc.invalidateViews();

            ListaEquipePerito = equipePeritoDao.retornarEquipePeritoAtualizar(cvlisilc.getId());
            ListaEquipePeritoFiltrado.clear();
            ListaEquipePeritoFiltrado.addAll(ListaEquipePerito);
            LvEquipePeritoSilc.invalidateViews();


        } else {

            ListaObjetosApreendidos = objetosApreendidosDao.retornarObjetosApreendidos();
            ListaObjetosApreendidosFiltrados.clear();
            ListaObjetosApreendidosFiltrados.addAll(ListaObjetosApreendidos);
            LvObjetosApreendidos.invalidateViews();

            ListaEquipeLevantamento = equipeLevantamentoDao.retornarEquipeLevantamento();
            ListaEquipeLevantamentoFiltrado.clear();
            ListaEquipeLevantamentoFiltrado.addAll(ListaEquipeLevantamento);
            LvEquipeLevantamentoSilc.invalidateViews();

            ListaEquipePreservacaoLocal = equipePreservacaoLocalDao.retornarEquipepreservacaolocal();
            ListaEquipePreservacaoLocalFiltrado.clear();
            ListaEquipePreservacaoLocalFiltrado.addAll(ListaEquipePreservacaoLocal);
            LvEquipePreservacaoLocalSilc.invalidateViews();

            ListaEquipePerito = equipePeritoDao.retornarEquipePerito();
            ListaEquipePeritoFiltrado.clear();
            ListaEquipePeritoFiltrado.addAll(ListaEquipePerito);
            LvEquipePeritoSilc.invalidateViews();
        }
        }

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
