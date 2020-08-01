package com.iris.ramilton.iris;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.iris.ramilton.iris.dao.MarcaDao;
import com.iris.ramilton.iris.dao.MunicipioDao;
import com.iris.ramilton.iris.dao.UnidadeBODao;
import com.iris.ramilton.iris.dao.VistoriaDao;
import com.iris.ramilton.iris.modelo.Vistoria;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DadosVistoriaActivity extends AppCompatActivity {

    private Button BtnUnidadeBOVistoria, BtnDataApresentacaoVistoria, BtnTipoVistoria, BtnMarcaVistoria, BtnModeloVistoria, BtncorVistoria, BtnAnoModeloVistoria, BtnPlacaVistoria, BtnChassiVistoria, BtnProximaEtapaVistoria;
    private Spinner SpTipoVistoria, SpMarcaVistoria, SpEstadoVitoria, SpUnidadeBOVistoria;
    private CheckBox CbPlacaVistoria, CbChassiVistoria;
    private TextView TvMunicipioEstado;
    private EditText EdtDataApresentacaoVistoria, EdtModeloVistoria, EdtCorVistoria, EdtAnoModeloVistoria, EdtPlacaVistoria, EdtMunicipioVistoria, EdtChassiVistoria;
    private String tipo, marca, unidadeboTxT;
    private int placaausente = 0;
    DatePickerDialog.OnDateSetListener date;
    Calendar c;
    DatePickerDialog dpd;
    ArrayList<String> marcacarros;
    private MarcaDao marcaDao;
    ArrayList<String> unidadebolista;
    private UnidadeBODao unidadeBODao;
    private Vistoria vistoria;
    private VistoriaDao vistoriaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_vistoria);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        BtnUnidadeBOVistoria = (Button) findViewById(R.id.btnUnidadeBOVistoria);
        BtnDataApresentacaoVistoria = (Button) findViewById(R.id.btnDataApresentacaoVistoria);
        BtnTipoVistoria = (Button) findViewById(R.id.btnTipoVeiculoVistoria);
        BtnMarcaVistoria = (Button) findViewById(R.id.btnMarcaVeiculoVistoria);
        BtnModeloVistoria = (Button) findViewById(R.id.btnModeloVeiculoVistoria);
        BtncorVistoria = (Button) findViewById(R.id.btnCorVeiculoVistoria);
        BtnAnoModeloVistoria = (Button) findViewById(R.id.btnAnoModeloVeiculoVistoria);
        BtnPlacaVistoria = (Button) findViewById(R.id.btnPlacaVeiculoVistoriado);
        BtnChassiVistoria = (Button) findViewById(R.id.btnChassiVeiculoVistoria);
        BtnProximaEtapaVistoria = (Button) findViewById(R.id.btnProximaEtapa);
        SpTipoVistoria = (Spinner) findViewById(R.id.spTipoVeiculoVistoria);
        SpMarcaVistoria = (Spinner) findViewById(R.id.spMarcaVeiculoVistoria);
        SpEstadoVitoria = (Spinner) findViewById(R.id.spEstadoVeiculoVistoria);
        SpUnidadeBOVistoria = (Spinner) findViewById(R.id.spUnidadeBOVistoria);
        CbPlacaVistoria = (CheckBox) findViewById(R.id.cbPlacaAusendeVeiculoVistoriado);
        CbChassiVistoria = (CheckBox) findViewById(R.id.cbChassiVeiculoVistoria);
        TvMunicipioEstado = (TextView) findViewById(R.id.tvMunicipioEstadoVistoria);
        EdtDataApresentacaoVistoria = (EditText) findViewById(R.id.edtDataApresentacaoVistoria);
        EdtModeloVistoria = (EditText) findViewById(R.id.edtModeloVeiculoVistoria);
        EdtCorVistoria = (EditText) findViewById(R.id.edtCorVeiculoVistoria);
        EdtAnoModeloVistoria = (EditText) findViewById(R.id.edtAnoModeloVeiculoVistoria);
        EdtPlacaVistoria = (EditText) findViewById(R.id.edtPlacaVeiculoVistoriado);
        EdtMunicipioVistoria = (EditText) findViewById(R.id.edtMunicipioVeiculoVistoria);
        EdtChassiVistoria = (EditText) findViewById(R.id.edtChassiVeiculoVistoria);

        vistoria = new Vistoria();
        vistoriaDao = new VistoriaDao(this);

        ArrayAdapter adaptadorTipo = ArrayAdapter.createFromResource(this, R.array.TipoAutomovelVistoria, android.R.layout.simple_spinner_item);
        SpTipoVistoria.setAdapter(adaptadorTipo);

        ArrayAdapter adaptadorEstado = ArrayAdapter.createFromResource(this, R.array.Estado, android.R.layout.simple_spinner_item);
        SpEstadoVitoria.setAdapter(adaptadorEstado);

        marcaDao = new MarcaDao(this);
        unidadeBODao = new UnidadeBODao(this);

        marcacarros = marcaDao.retornarMarca();
        ArrayAdapter<String> adaptadorMarca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, marcacarros);
        adaptadorMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpMarcaVistoria.setAdapter(adaptadorMarca);

        unidadebolista = unidadeBODao.retornarUnidadeBO();
        ArrayAdapter<String> adaptadorUnidadeBO = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, unidadebolista);
        adaptadorUnidadeBO.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpUnidadeBOVistoria.setAdapter(adaptadorUnidadeBO);

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

        BtnUnidadeBOVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUnidadeBOVistoria.setVisibility(View.VISIBLE);
                BtnDataApresentacaoVistoria.setVisibility(View.VISIBLE);
            }
        });

        SpUnidadeBOVistoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unidadeboTxT = SpUnidadeBOVistoria.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnDataApresentacaoVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtDataApresentacaoVistoria.setVisibility(View.VISIBLE);

            }
        });

        EdtDataApresentacaoVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnTipoVistoria.setVisibility(View.VISIBLE);
                new DatePickerDialog(DadosVistoriaActivity.this, date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EdtDataApresentacaoVistoria.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        BtnTipoVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpTipoVistoria.setVisibility(View.VISIBLE);
                BtnMarcaVistoria.setVisibility(View.VISIBLE);
            }
        });

        SpTipoVistoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo = SpTipoVistoria.getItemAtPosition(position).toString();
                if (SpTipoVistoria.equals("Ônibus") && SpTipoVistoria != null) {
                    BtnMarcaVistoria.setVisibility(View.VISIBLE);
                }
                if (SpTipoVistoria.equals("Reboque") && SpTipoVistoria != null) {
                    BtnMarcaVistoria.setVisibility(View.VISIBLE);
                }
                if (SpTipoVistoria.equals("Automovel") && SpTipoVistoria != null) {
                    BtnMarcaVistoria.setVisibility(View.VISIBLE);
                }
                if (SpTipoVistoria.equals("Motocicleta") && SpTipoVistoria != null) {
                    BtnMarcaVistoria.setVisibility(View.VISIBLE);
                }
                if (SpTipoVistoria.equals("Triciclo") && SpTipoVistoria != null) {
                    BtnMarcaVistoria.setVisibility(View.VISIBLE);
                }
                if (SpTipoVistoria.equals("Caminhonete") && SpTipoVistoria != null) {
                    BtnMarcaVistoria.setVisibility(View.VISIBLE);
                }
                if (SpTipoVistoria.equals("Caminhão") && SpTipoVistoria != null) {
                    BtnMarcaVistoria.setVisibility(View.VISIBLE);
                }
                if (SpTipoVistoria.equals("Carreta") && SpTipoVistoria != null) {
                    BtnMarcaVistoria.setVisibility(View.VISIBLE);
                }
                if (SpTipoVistoria.equals("Cavalinho") && SpTipoVistoria != null) {
                    BtnMarcaVistoria.setVisibility(View.VISIBLE);
                }
                if (SpTipoVistoria.equals("Outros") && SpTipoVistoria != null) {
                    BtnMarcaVistoria.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnMarcaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpMarcaVistoria.setVisibility(View.VISIBLE);
                BtnModeloVistoria.setVisibility(View.VISIBLE);
            }
        });

        SpMarcaVistoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                marca = SpMarcaVistoria.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnModeloVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtModeloVistoria.setVisibility(View.VISIBLE);
            }
        });

        EdtModeloVistoria.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                BtncorVistoria.setVisibility(View.VISIBLE);
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        BtncorVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtCorVistoria.setVisibility(View.VISIBLE);
            }
        });

        EdtCorVistoria.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                BtnAnoModeloVistoria.setVisibility(View.VISIBLE);
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        BtnAnoModeloVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtAnoModeloVistoria.setVisibility(View.VISIBLE);
            }
        });

        EdtAnoModeloVistoria.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                BtnPlacaVistoria.setVisibility(View.VISIBLE);
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        BtnPlacaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CbPlacaVistoria.setVisibility(View.VISIBLE);
                EdtPlacaVistoria.setVisibility(View.VISIBLE);
                TvMunicipioEstado.setVisibility(View.VISIBLE);
                EdtMunicipioVistoria.setVisibility(View.VISIBLE);
                SpEstadoVitoria.setVisibility(View.VISIBLE);
            }
        });

        EdtPlacaVistoria.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                BtnChassiVistoria.setVisibility(View.VISIBLE);
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        CbPlacaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnChassiVistoria.setVisibility(View.VISIBLE);
                if(CbPlacaVistoria.isChecked()){
                    placaausente = 1;
                    EdtPlacaVistoria.setVisibility(View.GONE);
                    EdtMunicipioVistoria.setVisibility(View.GONE);
                    SpEstadoVitoria.setVisibility(View.GONE);
                    TvMunicipioEstado.setVisibility(View.GONE);
                }else{
                    EdtPlacaVistoria.setVisibility(View.VISIBLE);
                    EdtMunicipioVistoria.setVisibility(View.VISIBLE);
                    SpEstadoVitoria.setVisibility(View.VISIBLE);
                    TvMunicipioEstado.setVisibility(View.VISIBLE);
                }
            }
        });

        BtnChassiVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CbChassiVistoria.setVisibility(View.VISIBLE);
                EdtChassiVistoria.setVisibility(View.VISIBLE);
            }
        });

        EdtChassiVistoria.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                BtnProximaEtapaVistoria.setVisibility(View.VISIBLE);
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        CbChassiVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnProximaEtapaVistoria.setVisibility(View.VISIBLE);
                if(CbChassiVistoria.isChecked()){
                    esconderTeclado(DadosVistoriaActivity.this);
                    EdtChassiVistoria.setVisibility(View.GONE);
                }else{
                    EdtChassiVistoria.setVisibility(View.VISIBLE);
                }
            }
        });

        BtnProximaEtapaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DadosVistoriaActivity.this, DaVistoriaActivity.class));
            }
        });

    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        EdtDataApresentacaoVistoria.setText(sdf.format(c.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dados_vistoria, menu);

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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
