package com.iris.ramilton.iris;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.EquipePreservacaoLocalDao;
import com.iris.ramilton.iris.modelo.Equipepreservacaolocal;
import com.iris.ramilton.iris.modelo.Gerarnumeros;

public class IncluirEqPresLocalActivity extends AppCompatActivity {

    private Spinner SpCargoEquipePresLocal;
    private EditText EdtNomeEquipePresLocal;
    private Button BtnIncluirEquipePresLocal, BtnIncluirEquipePresLocalFinalizar;
    private String CargoEquipePreservacao;
    private int cod, atualiza = 0;
    private Gerarnumeros gerarnumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_eq_pres_local);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        SpCargoEquipePresLocal = (Spinner) findViewById(R.id.spinnerCargoEquipPreservacaoLocal);
        EdtNomeEquipePresLocal = (EditText) findViewById(R.id.edtEqPresLocalNome);
        BtnIncluirEquipePresLocal = (Button) findViewById(R.id.btnIncluirEquipePreservacaoLocal);
        BtnIncluirEquipePresLocalFinalizar = (Button) findViewById(R.id.btnIncluirEquipePreservacaoLocalFinalizar);

        gerarnumeros = new Gerarnumeros();
        Intent it = getIntent();

        if (it.hasExtra("codigoEquipePresLocalsatualizar")) {

            cod = (int) it.getSerializableExtra("codigoEquipePresLocalsatualizar");

            atualiza = 1;
        }

        ArrayAdapter adaptadorSpCargoEquipeLocal = ArrayAdapter.createFromResource(this, R.array.CargoEquipePreservacaoLocal, android.R.layout.simple_spinner_item);
        SpCargoEquipePresLocal.setAdapter(adaptadorSpCargoEquipeLocal);

        SpCargoEquipePresLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CargoEquipePreservacao = SpCargoEquipePresLocal.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnIncluirEquipePresLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Equipepreservacaolocal equipepreservacaolocal = new Equipepreservacaolocal();
                EquipePreservacaoLocalDao equipePreservacaoLocalDao = new EquipePreservacaoLocalDao(getApplicationContext());

                equipepreservacaolocal.setDsCargoEquipepreservacaolocal(CargoEquipePreservacao);
                equipepreservacaolocal.setDsNomeEquipepreservacaolocal(EdtNomeEquipePresLocal.getText().toString());

                if (atualiza == 1) {

                    if (EdtNomeEquipePresLocal.getText().toString().equals("")) {
                        AlertDialog.Builder b = new AlertDialog.Builder(IncluirEqPresLocalActivity.this);
                        b.setTitle("IRIS - Atenção!!!")
                                .setMessage("O campo nome do perito não pode ficar em branco")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EdtNomeEquipePresLocal.setFocusable(true);
                                    }
                                }).create();
                        b.show();
                    } else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoequipepreservacaolocal = equipePreservacaoLocalDao.cadastrarCVLIEquipePreservacaoLocalAtualiza(equipepreservacaolocal, cod, qualquer);
                        if (codigoequipepreservacaolocal > 0) {
                            Toast.makeText(IncluirEqPresLocalActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(IncluirEqPresLocalActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        SpCargoEquipePresLocal.setSelection(0);
                        EdtNomeEquipePresLocal.setText("");
                        EdtNomeEquipePresLocal.setFocusable(true);
                    }
                } else if (EdtNomeEquipePresLocal.getText().toString().equals("")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(IncluirEqPresLocalActivity.this);
                    b.setTitle("IRIS - Atenção!!!")
                            .setMessage("O campo nome do perito não pode ficar em branco")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtNomeEquipePresLocal.setFocusable(true);
                                }
                            }).create();
                    b.show();
                } else {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipepreservacaolocal = equipePreservacaoLocalDao.cadastrarCVLIEquipePreservacaoLocal(equipepreservacaolocal, qualquer);
                    if (codigoequipepreservacaolocal > 0) {
                        Toast.makeText(IncluirEqPresLocalActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IncluirEqPresLocalActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    SpCargoEquipePresLocal.setSelection(0);
                    EdtNomeEquipePresLocal.setText("");
                    EdtNomeEquipePresLocal.setFocusable(true);
                }
            }
        });

        BtnIncluirEquipePresLocalFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Equipepreservacaolocal equipepreservacaolocal = new Equipepreservacaolocal();
                EquipePreservacaoLocalDao equipePreservacaoLocalDao = new EquipePreservacaoLocalDao(getApplicationContext());

                equipepreservacaolocal.setDsCargoEquipepreservacaolocal(CargoEquipePreservacao);
                equipepreservacaolocal.setDsNomeEquipepreservacaolocal(EdtNomeEquipePresLocal.getText().toString());

                if (atualiza == 1) {

                    if (EdtNomeEquipePresLocal.getText().toString().equals("")) {
                        AlertDialog.Builder b = new AlertDialog.Builder(IncluirEqPresLocalActivity.this);
                        LayoutInflater factory = LayoutInflater.from(IncluirEqPresLocalActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("O campo nome do perito não pode ficar em branco")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EdtNomeEquipePresLocal.setFocusable(true);

                                    }
                                }).create();
                        b.show();
                    } else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoequipepreservacaolocal = equipePreservacaoLocalDao.cadastrarCVLIEquipePreservacaoLocalAtualiza(equipepreservacaolocal, cod, qualquer);
                        if(codigoequipepreservacaolocal > 0){
                            Toast.makeText(IncluirEqPresLocalActivity.this,"Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(IncluirEqPresLocalActivity.this,"Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                } else if (EdtNomeEquipePresLocal.getText().toString().equals("")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(IncluirEqPresLocalActivity.this);
                    LayoutInflater factory = LayoutInflater.from(IncluirEqPresLocalActivity.this);
                    final View view = factory.inflate(R.layout.logotipo, null);
                    b.setTitle("IRIS - Atenção!!!")
                            .setView(view)
                            .setMessage("O campo nome do perito não pode ficar em branco")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtNomeEquipePresLocal.setFocusable(true);

                                }
                            }).create();
                    b.show();
                } else {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipepreservacaolocal = equipePreservacaoLocalDao.cadastrarCVLIEquipePreservacaoLocal(equipepreservacaolocal, qualquer);
                    if(codigoequipepreservacaolocal > 0){
                        Toast.makeText(IncluirEqPresLocalActivity.this,"Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(IncluirEqPresLocalActivity.this,"Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipepreservacaolocal, menu);

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

