package com.iris.ramilton.iris;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.dao.CarroDao;
import com.iris.ramilton.iris.modelo.Carro;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Gerarnumeros;

public class AutomovelCrimeFatoActivity extends AppCompatActivity {

    private TextView TvTitulo, TvTipoVeiculoCrimeFato, TvMarcaVeiculoCrimeFato, TvModeloVeiculoCrimeFato, TvCorVeiculoCrimeFato, TvPlacaVeiculoCrimeFato;
    private TextView TvDescricaoBiscicletaCrimeFato, TvDescricaoCarroCrimeFato;
    private Spinner SpTipoCarroCrimeFato, SpMarcaCarroCrimeFato;
    private EditText EdtModeloCarroCrimeFato, EdtCorCarroCrimeFato, EdtPlacaCarroCrimeFato, EdtDescricaoBiscicletaCrime, EdtDescricaoCarroCrimeFato;
    private ConstraintLayout ClCarroCrime, ClBiscicletaCrime;
    private Button BtnCadastroAutomovelCrimeFato;
    private CheckBox CbNIdentificadoMarcaCarroCrime, CbNIdentificadoModeloCarroCrime, CbNIdentificadoCorCarroCrime, CbNidentificadoPlacaCarroCrime;
    private int NIdentificadoMarcaCarroCrime, NIdentificadoModeloCarroCrime, NIdentificadoCorCarroCrime, NidentificadoPlacaCarroCrime;
    private Carro carro;
    private CarroDao carroDao;
    private String tipocarro, marcacarro;
    private int cvlicodigo = 0;
    private int atualiza = 0;
    private Gerarnumeros gerarnumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automovel_crime_fato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        TvTitulo = (TextView) findViewById(R.id.tvTituloCadastroAutomovelCrimeFato);
        TvDescricaoCarroCrimeFato = (TextView) findViewById(R.id.tvDescricaoCarroCrime);
        TvTipoVeiculoCrimeFato = (TextView) findViewById(R.id.tvTipoVeiculoCrimeFato);
        TvMarcaVeiculoCrimeFato = (TextView) findViewById(R.id.tvMarcaVeiculoCrimeFato);
        TvModeloVeiculoCrimeFato = (TextView) findViewById(R.id.tvModeloAutomovelCrimeFato);
        TvCorVeiculoCrimeFato = (TextView) findViewById(R.id.tvCorAutomovelCrimeFato);
        TvPlacaVeiculoCrimeFato = (TextView) findViewById(R.id.tvPlacaAutomovelCrimeFato);
        TvDescricaoBiscicletaCrimeFato = (TextView) findViewById(R.id.tvDescricaoBiscicletaCrimeFato);
        SpTipoCarroCrimeFato = (Spinner) findViewById(R.id.spinnerTipoVeiculoCrimeFato);
        SpMarcaCarroCrimeFato = (Spinner) findViewById(R.id.spinnerMarcaCarroCrimeFato);
        EdtModeloCarroCrimeFato = (EditText) findViewById(R.id.edtModeloCarroCrimeFato);
        EdtCorCarroCrimeFato = (EditText) findViewById(R.id.edtCorCarroCrimeFato);
        EdtPlacaCarroCrimeFato = (EditText) findViewById(R.id.edtPlacaCarroCrimeFato);
        EdtDescricaoBiscicletaCrime = (EditText) findViewById(R.id.edtDescricaoBiscicletaCrimeFato);
        EdtDescricaoCarroCrimeFato = (EditText) findViewById(R.id.edtDescricaoCarroCrime);
        ClCarroCrime = (ConstraintLayout) findViewById(R.id.clAutomovelCrime);
        ClBiscicletaCrime = (ConstraintLayout) findViewById(R.id.clBiscicletaCrime);
        BtnCadastroAutomovelCrimeFato = (Button) findViewById(R.id.btnCadastrarCarroCrimeFato);
        CbNIdentificadoMarcaCarroCrime = (CheckBox) findViewById(R.id.cbNidentificadoMarcaCarro);
        CbNIdentificadoModeloCarroCrime = (CheckBox) findViewById(R.id.cbNIdentificadoModeloCarro);
        CbNIdentificadoCorCarroCrime = (CheckBox) findViewById(R.id.cbNIdentificadoCorCarro);
        CbNidentificadoPlacaCarroCrime = (CheckBox) findViewById(R.id.cbNIdentificadoPlacaCarro);

        gerarnumeros = new Gerarnumeros();
        Intent it = getIntent();

        if(it.hasExtra("codigocarroatualizar")){
            cvlicodigo = (int) it.getSerializableExtra("codigocarroatualizar");

            atualiza = 1;

        }


        ArrayAdapter adaptadorSpTipoCarroCrimeFato = ArrayAdapter.createFromResource(this,R.array.TipoAutomovelCrime,android.R.layout.simple_spinner_item);
        SpTipoCarroCrimeFato.setAdapter(adaptadorSpTipoCarroCrimeFato);

        ArrayAdapter adaptadorSpMarcaCarroCrimeFato = ArrayAdapter.createFromResource(this,R.array.MarcaCarroCrime,android.R.layout.simple_spinner_item);
        SpMarcaCarroCrimeFato.setAdapter(adaptadorSpMarcaCarroCrimeFato);

        CbNIdentificadoMarcaCarroCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbNIdentificadoMarcaCarroCrime.isChecked()){
                    NIdentificadoMarcaCarroCrime = 1;
                    SpMarcaCarroCrimeFato.setEnabled(false);
                }else{
                    NIdentificadoMarcaCarroCrime = 0;
                    SpMarcaCarroCrimeFato.setEnabled(true);
                }
            }
        });

        CbNIdentificadoModeloCarroCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbNIdentificadoModeloCarroCrime.isChecked()){
                    NIdentificadoModeloCarroCrime = 1;
                    EdtModeloCarroCrimeFato.setEnabled(false);
                }else{
                    NIdentificadoModeloCarroCrime = 0;
                    EdtModeloCarroCrimeFato.setEnabled(true);
                }
            }
        });

        CbNIdentificadoCorCarroCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbNIdentificadoCorCarroCrime.isChecked()){
                    NIdentificadoCorCarroCrime = 1;
                    EdtCorCarroCrimeFato.setEnabled(false);
                }else{
                    NIdentificadoCorCarroCrime = 0;
                    EdtCorCarroCrimeFato.setEnabled(true);
                }
            }
        });

        CbNidentificadoPlacaCarroCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CbNidentificadoPlacaCarroCrime.isChecked()){
                    NidentificadoPlacaCarroCrime = 1;
                    EdtPlacaCarroCrimeFato.setEnabled(false);
                }else{
                    NidentificadoPlacaCarroCrime = 0;
                    EdtPlacaCarroCrimeFato.setEnabled(true);
                }
            }
        });


        SpMarcaCarroCrimeFato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                marcacarro = SpMarcaCarroCrimeFato.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpTipoCarroCrimeFato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tipocarro = SpTipoCarroCrimeFato.getItemAtPosition(position).toString();

                if(tipocarro.equals("Biscicleta")){
                    ClCarroCrime.setVisibility(View.INVISIBLE);
                    ClBiscicletaCrime.setVisibility(View.VISIBLE);
                    BtnCadastroAutomovelCrimeFato.setVisibility(View.VISIBLE);
                }
                if(tipocarro.equals("Automóvel Sedan")){
                    ClBiscicletaCrime.setVisibility(View.INVISIBLE);
                    ClCarroCrime.setVisibility(View.VISIBLE);
                    BtnCadastroAutomovelCrimeFato.setVisibility(View.VISIBLE);
                }
                if(tipocarro.equals("Automóvel hatch")){
                    ClBiscicletaCrime.setVisibility(View.INVISIBLE);
                    ClCarroCrime.setVisibility(View.VISIBLE);
                    BtnCadastroAutomovelCrimeFato.setVisibility(View.VISIBLE);
                }
                if(tipocarro.equals("Automóvel SUV")){
                    ClBiscicletaCrime.setVisibility(View.INVISIBLE);
                    ClCarroCrime.setVisibility(View.VISIBLE);
                    BtnCadastroAutomovelCrimeFato.setVisibility(View.VISIBLE);
                }
                if(tipocarro.equals("Caminhão")){
                    ClBiscicletaCrime.setVisibility(View.INVISIBLE);
                    ClCarroCrime.setVisibility(View.VISIBLE);
                    BtnCadastroAutomovelCrimeFato.setVisibility(View.VISIBLE);

                }
                if(tipocarro.equals("Caminhonete")){
                    ClBiscicletaCrime.setVisibility(View.INVISIBLE);
                    ClCarroCrime.setVisibility(View.VISIBLE);
                    BtnCadastroAutomovelCrimeFato.setVisibility(View.VISIBLE);

                }
                if(tipocarro.equals("Motocicleta")){
                    ClBiscicletaCrime.setVisibility(View.INVISIBLE);
                    ClCarroCrime.setVisibility(View.VISIBLE);
                    BtnCadastroAutomovelCrimeFato.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnCadastroAutomovelCrimeFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                carro = new Carro();
                carroDao = new CarroDao(getApplicationContext());

                carro.setDsTipoCarro(tipocarro);
                carro.setDsMarcaCarro(marcacarro);
                carro.setDsModeloCarro(EdtModeloCarroCrimeFato.getText().toString());
                carro.setDsDescricaoCarro(EdtDescricaoCarroCrimeFato.getText().toString());
                carro.setDsCorCarro(EdtCorCarroCrimeFato.getText().toString());
                carro.setDsPlacaCarro(EdtPlacaCarroCrimeFato.getText().toString());
                carro.setCkbNIdentificadoMarcaCarroCrime(NIdentificadoMarcaCarroCrime);
                carro.setCkbNIdentificadoModeloCarroCrime(NIdentificadoModeloCarroCrime);
                carro.setCkbNIdentificadoCorCarroCrime(NIdentificadoCorCarroCrime);
                carro.setCkbNidentificadoPlacaCarroCrime(NidentificadoPlacaCarroCrime);
                carro.setDsDescricaoBiscicleta(EdtDescricaoBiscicletaCrime.getText().toString());

                if(atualiza == 1){
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codicarro = carroDao.cadastrarCVLICarroAtualizar(carro,cvlicodigo,qualquer);
                    finish();
                }else{
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigocarro = carroDao.cadastrarCVLICarro(carro, qualquer);

                    finish();
                }


            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_automovelcrime, menu);

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