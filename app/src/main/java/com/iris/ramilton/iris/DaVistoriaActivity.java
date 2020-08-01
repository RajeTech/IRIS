package com.iris.ramilton.iris;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.dao.VistoriaDao;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Usuario;
import com.iris.ramilton.iris.modelo.Vistoria;

public class DaVistoriaActivity extends AppCompatActivity {

    private CheckBox CbEstepe, CbChaveRodas, CbTriangulo, CbMacaco, CbTapetes, CbChaveVeiculo, CbCRV, CbCRLV, CbTodos;
    private EditText EdtRodaAro, EdtTipoRoda, EdtQuilometragem, EdtAvaria;
    private TextView TvRodaAro, TvNivelCombustivel, TvQuilometragem, TvEstadoveiculo, TvAvaria;
    private Spinner SpNivelCombustivel;
    private RadioGroup RgEstadoVeiculo;
    private RadioButton RbServivel, RbInservivel, RbescolhidoEstadoVeiculo;
    private Button BtnFotos, BtnCheckList;
    private Vistoria vistoriaatualizar = null;
    private int codigopassado, Estepe = 0, ChaveRodas = 0, Triangulo = 0, Macaco = 0, Tapetes = 0, ChaveVeiculo = 0, CRV = 0, CRLV = 0;
    private int servivel = 0, inservivel = 0, estadov = 0;
    private Vistoria vistoria;
    private VistoriaDao vistoriaDao;
    private int atualizacadastra = 0, clique = 0;
    private String nivelcombustivel;
    private Usuario usuario;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_vistoria);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        CbEstepe = (CheckBox) findViewById(R.id.cbEstepe);
        CbChaveRodas = (CheckBox) findViewById(R.id.cbChavedeRodas);
        CbTriangulo = (CheckBox) findViewById(R.id.cbTriangulo);
        CbMacaco = (CheckBox) findViewById(R.id.cbMacaco);
        CbTapetes = (CheckBox) findViewById(R.id.cbTapetes);
        CbChaveVeiculo = (CheckBox) findViewById(R.id.cbChaveVeiculo);
        CbCRV = (CheckBox) findViewById(R.id.cbCrv);
        CbCRLV = (CheckBox) findViewById(R.id.cbCrlv);
        CbTodos = (CheckBox) findViewById(R.id.cbTodos);
        EdtRodaAro = (EditText) findViewById(R.id.edtRodaAro);
        EdtTipoRoda = (EditText) findViewById(R.id.edtTipoRoda);
        EdtQuilometragem = (EditText) findViewById(R.id.edtQuilometragem);
        EdtAvaria = (EditText) findViewById(R.id.edtAvaria);
        SpNivelCombustivel = (Spinner) findViewById(R.id.spNivelCombustível);
        RgEstadoVeiculo = (RadioGroup) findViewById(R.id.rgEstadoVeiculo);
        RbServivel = (RadioButton) findViewById(R.id.rbServivel);
        RbInservivel = (RadioButton) findViewById(R.id.rbInservivel);
        BtnFotos = (Button) findViewById(R.id.btnFotos);
        BtnCheckList = (Button) findViewById(R.id.btnCheckList);
        TvRodaAro = (TextView) findViewById(R.id.tvRodaAro);
        TvNivelCombustivel = (TextView) findViewById(R.id.tvNivelConbustivel);
        TvQuilometragem = (TextView) findViewById(R.id.tvQuilometragem);
        TvEstadoveiculo = (TextView) findViewById(R.id.tvEstadoVeiculo);
        TvAvaria = (TextView) findViewById(R.id.tvAvaria);

        vistoria = new Vistoria();
        vistoriaDao = new VistoriaDao(this);
        usuario = new Usuario();
        usuarioDao = new UsuarioDao(this);

        Intent it = getIntent();

        if(it.hasExtra("vistoria")){
            vistoriaatualizar = (Vistoria) it.getSerializableExtra("vistoria");
            codigopassado = vistoriaatualizar.getId();

            if(vistoriaatualizar.getEstepe() == 0){
                CbEstepe.setChecked(false);
            }else{
                CbEstepe.setChecked(true);
            }
            if(vistoriaatualizar.getChaveRodas() == 0){
                CbChaveRodas.setChecked(false);
            }else{
                CbChaveRodas.setChecked(true);
            }
            if(vistoriaatualizar.getTriangulo() == 0){
                CbTriangulo.setChecked(false);
            }else{
                CbTriangulo.setChecked(true);
            }
            if(vistoriaatualizar.getMacaco() == 0){
                CbMacaco.setChecked(false);
            }else{
                CbMacaco.setChecked(true);
            }
            if(vistoriaatualizar.getTapetes() == 0){
                CbTapetes.setChecked(false);
            }else{
                CbTapetes.setChecked(true);
            }
            if(vistoriaatualizar.getChaveVeiculo() == 0){
                CbChaveVeiculo.setChecked(false);
            }else{
                CbChaveVeiculo.setChecked(true);
            }
            if(vistoriaatualizar.getCRV() == 0){
                CbCRV.setChecked(false);
            }else{
                CbCRV.setChecked(true);
            }
            if(vistoriaatualizar.getCRLV() == 0){
                CbCRLV.setChecked(false);
            }else{
                CbCRLV.setChecked(true);
            }
            if(vistoriaatualizar.getEstepe() == 1 && vistoriaatualizar.getChaveRodas() == 1 && vistoriaatualizar.getTriangulo() == 1 && vistoriaatualizar.getMacaco() == 1
             && vistoriaatualizar.getTapetes() == 1 && vistoriaatualizar.getChaveVeiculo() == 1 && vistoriaatualizar.getCRV() == 1 && vistoriaatualizar.getCRLV() == 1){
                CbTodos.setChecked(true);
            }

            EdtRodaAro.setText(vistoriaatualizar.getRodaAro());
            EdtTipoRoda.setText(vistoriaatualizar.getRodaTipo());
            EdtQuilometragem.setText(vistoriaatualizar.getQuilometragem());
            EdtAvaria.setText(vistoriaatualizar.getAvarias());

        }

        CbEstepe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbEstepe.isChecked()){
                    Estepe = 1;
                }else{
                    Estepe = 0;
                }
            }
        });

        CbChaveRodas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbChaveRodas.isChecked()){
                    ChaveRodas = 1;
                }else{
                    ChaveRodas = 0;
                }
            }
        });

        CbTriangulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbTriangulo.isChecked()){
                    Triangulo = 1;
                }else{
                    Triangulo = 0;
                }
            }
        });

        CbMacaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbMacaco.isChecked()){
                    Macaco = 1;
                }else{
                    Macaco = 0;
                }
            }
        });

        CbTapetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbTapetes.isChecked()){
                    Tapetes = 1;
                }else{
                    Tapetes = 0;
                }
            }
        });

        CbChaveVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbChaveVeiculo.isChecked()){
                    ChaveVeiculo = 1;
                }else{
                    ChaveVeiculo = 0;
                }
            }
        });

        CbCRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbCRV.isChecked()){
                    CRV = 1;
                }else{
                    CRV = 0;
                }
            }
        });

        CbCRLV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbCRLV.isChecked()){
                    CRLV = 1;
                }else{
                    CRLV = 0;
                }
            }
        });

        CbTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbTodos.isChecked()){
                    CbEstepe.setChecked(true);
                    CbChaveRodas.setChecked(true);
                    CbTriangulo.setChecked(true);
                    CbMacaco.setChecked(true);
                    CbTapetes.setChecked(true);
                    CbChaveVeiculo.setChecked(true);
                    CbCRV.setChecked(true);
                    CbCRLV.setChecked(true);
                    Estepe = 1;
                    ChaveRodas = 1;
                    Triangulo = 1;
                    Macaco = 1;
                    Tapetes = 1;
                    ChaveVeiculo = 1;
                    CRV = 1;
                    CRLV = 1;
                }else{
                    Estepe = 0;
                    ChaveRodas = 0;
                    Triangulo = 0;
                    Macaco = 0;
                    Tapetes = 0;
                    ChaveVeiculo = 0;
                    CRV = 0;
                    CRLV = 0;
                }
            }
        });

        ArrayAdapter adaptadorNivelCombustivel = ArrayAdapter.createFromResource(this, R.array.NivelCombustivel, android.R.layout.simple_spinner_item);
        SpNivelCombustivel.setAdapter(adaptadorNivelCombustivel);

        SpNivelCombustivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivelcombustivel = SpNivelCombustivel.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RgEstadoVeiculo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RbescolhidoEstadoVeiculo = (RadioButton) RgEstadoVeiculo.findViewById(checkedId);
                int indiceEstadoVeiculoEscolhido = RgEstadoVeiculo.indexOfChild(RbescolhidoEstadoVeiculo);

                if(indiceEstadoVeiculoEscolhido == 1){
                    servivel = 1;
                    inservivel = 0;
                }else{
                    inservivel = 1;
                    servivel = 0;
                }
            }
        });

        if(servivel == 1){
            estadov = 1;
        }else{
            estadov = 0;
        }
        BtnCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    TornarCamposVistoriaVisivel();
                    String DsnivelCombustivel = vistoriaatualizar.getNivelCombustivel();

                    if (DsnivelCombustivel == null) {
                        SpNivelCombustivel.setSelection(0);
                        DsnivelCombustivel = SpNivelCombustivel.getItemAtPosition(0).toString();
                    }

                    if (DsnivelCombustivel.equals("Selecione...") && DsnivelCombustivel != null) {
                        SpNivelCombustivel.setSelection(0);
                    }
                    if (DsnivelCombustivel.equals("Vazio") && DsnivelCombustivel != null) {
                        SpNivelCombustivel.setSelection(1);
                    }
                    if (DsnivelCombustivel.equals("Reserva") && DsnivelCombustivel != null) {
                        SpNivelCombustivel.setSelection(2);
                    }
                    if (DsnivelCombustivel.equals("1/4") && DsnivelCombustivel != null) {
                        SpNivelCombustivel.setSelection(3);
                    }
                    if (DsnivelCombustivel.equals("Meio Tanque") && DsnivelCombustivel != null) {
                        SpNivelCombustivel.setSelection(4);
                    }
                    if (DsnivelCombustivel.equals("3/4") && DsnivelCombustivel != null) {
                        SpNivelCombustivel.setSelection(5);
                    }
                    if (DsnivelCombustivel.equals("Cheio") && DsnivelCombustivel != null) {
                        SpNivelCombustivel.setSelection(6);
                    }

                    if(vistoriaatualizar.getEstadoVeiculo() == 1){
                        RbServivel.setChecked(true);
                    }else{
                        RbInservivel.setChecked(true);
                    }
            }
        });



        BtnFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vistoria.setVistoriador_id(usuario.getId());
                vistoria.setEstepe(Estepe);
                vistoria.setChaveRodas(ChaveRodas);
                vistoria.setTriangulo(Triangulo);
                vistoria.setMacaco(Macaco);
                vistoria.setTapetes(Tapetes);
                vistoria.setChaveVeiculo(ChaveVeiculo);
                vistoria.setCRV(CRV);
                vistoria.setCRLV(CRLV);
                vistoria.setRodaAro(EdtRodaAro.getText().toString());
                vistoria.setRodaTipo(EdtTipoRoda.getText().toString());
                vistoria.setNivelCombustivel(nivelcombustivel);
                vistoria.setQuilometragem(EdtQuilometragem.getText().toString());
                vistoria.setEstadoVeiculo(estadov);
                vistoria.setAvarias(EdtAvaria.getText().toString());
                vistoria.setControle(2);

                long certo = vistoriaDao.AtualizarVistoria(vistoria,codigopassado);
                if(certo > 0){
                    Toast.makeText(DaVistoriaActivity.this, "Vistoria Atualizada!!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DaVistoriaActivity.this, "Erro ao Vistoriar!!!", Toast.LENGTH_SHORT).show();
                }

                Intent it = new Intent(DaVistoriaActivity.this, FotoFrenteActivity.class);
                it.putExtra("codvistoria", codigopassado);
                startActivity(it);
            }
        });
    }

    public void TornarCamposVistoriaVisivel(){
        CbEstepe.setVisibility(View.VISIBLE);
        CbChaveRodas.setVisibility(View.VISIBLE);
        CbTriangulo.setVisibility(View.VISIBLE);
        CbMacaco.setVisibility(View.VISIBLE);
        CbTapetes.setVisibility(View.VISIBLE);
        CbChaveVeiculo.setVisibility(View.VISIBLE);
        CbCRV.setVisibility(View.VISIBLE);
        CbCRLV.setVisibility(View.VISIBLE);
        CbTodos.setVisibility(View.VISIBLE);
        EdtRodaAro.setVisibility(View.VISIBLE);
        EdtTipoRoda.setVisibility(View.VISIBLE);
        EdtQuilometragem.setVisibility(View.VISIBLE);
        EdtAvaria.setVisibility(View.VISIBLE);
        SpNivelCombustivel.setVisibility(View.VISIBLE);
        RgEstadoVeiculo.setVisibility(View.VISIBLE);
        RbServivel.setVisibility(View.VISIBLE);
        RbInservivel.setVisibility(View.VISIBLE);
        BtnFotos.setVisibility(View.VISIBLE);
        TvRodaAro.setVisibility(View.VISIBLE);
        TvQuilometragem.setVisibility(View.VISIBLE);
        TvNivelCombustivel.setVisibility(View.VISIBLE);
        TvEstadoveiculo.setVisibility(View.VISIBLE);
        TvAvaria.setVisibility(View.VISIBLE);
        BtnFotos.setVisibility(View.VISIBLE);

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
