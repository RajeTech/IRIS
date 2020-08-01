package com.iris.ramilton.iris;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.iris.ramilton.iris.dao.EquipeLevantamentoDao;
import com.iris.ramilton.iris.modelo.Equipelevantamento;
import com.iris.ramilton.iris.modelo.Gerarnumeros;

import java.util.ArrayList;

public class IncluirEquipeLevantamentoActivity extends AppCompatActivity {

    private Spinner SpCargoLevantamento;
    private EditText EdtNomeEquipeLevantamento;
    private Button BtnIncluirEquipeLevantamento, BtnIncluirEquipeLevantamentoFinalizar;
    private String CargoLevantamento;
    private int cod, atualiza = 0;
    private Gerarnumeros gerarnumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipe_levantamento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        SpCargoLevantamento = (Spinner) findViewById(R.id.spinnerCargoLevantamento);
        EdtNomeEquipeLevantamento = (EditText) findViewById(R.id.edtEqLevantamentoNome);
        BtnIncluirEquipeLevantamento = (Button) findViewById(R.id.btnIncluirEquipeLevantamento);
        BtnIncluirEquipeLevantamentoFinalizar = (Button) findViewById(R.id.btnIncluirEquipeLevantamentoFinalizar);

        gerarnumeros = new Gerarnumeros();

        Intent it = getIntent();
        if (it.hasExtra("codigoEquipeLevantamentosatualizar")) {
            cod = (int) it.getSerializableExtra("codigoEquipeLevantamentosatualizar");

            atualiza = 1;
        }

        ArrayAdapter adaptadorSpCargoLevantamento = ArrayAdapter.createFromResource(this, R.array.CargoEquipeLevantamento, android.R.layout.simple_spinner_item);
        SpCargoLevantamento.setAdapter(adaptadorSpCargoLevantamento);

        SpCargoLevantamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CargoLevantamento = SpCargoLevantamento.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnIncluirEquipeLevantamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Equipelevantamento equipelevantamento = new Equipelevantamento();
                EquipeLevantamentoDao equipeLevantamentoDao = new EquipeLevantamentoDao(getApplicationContext());

                equipelevantamento.setDsCargoEquipeLevantamento(CargoLevantamento);
                equipelevantamento.setDsNomeEquipeLevantamento(EdtNomeEquipeLevantamento.getText().toString());

                if (atualiza == 1) {

                    if (EdtNomeEquipeLevantamento.getText().toString().equals("")) {
                        AlertDialog.Builder b = new AlertDialog.Builder(IncluirEquipeLevantamentoActivity.this);
                        LayoutInflater factory = LayoutInflater.from(IncluirEquipeLevantamentoActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("O campo nome do perito não pode ficar em branco")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EdtNomeEquipeLevantamento.setFocusable(true);
                                    }
                                }).create();
                        b.show();
                    } else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoequipelevantamento = equipeLevantamentoDao.cadastrarCVLIEquipeLevantamentoAtualizar(equipelevantamento, cod, qualquer);
                        if (codigoequipelevantamento > 0) {
                            Toast.makeText(IncluirEquipeLevantamentoActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(IncluirEquipeLevantamentoActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        SpCargoLevantamento.setSelection(0);
                        EdtNomeEquipeLevantamento.setText("");
                        EdtNomeEquipeLevantamento.setFocusable(true);
                    }
                } else if (EdtNomeEquipeLevantamento.getText().toString().equals("")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(IncluirEquipeLevantamentoActivity.this);
                    LayoutInflater factory = LayoutInflater.from(IncluirEquipeLevantamentoActivity.this);
                    final View view = factory.inflate(R.layout.logotipo, null);
                    b.setTitle("IRIS - Atenção!!!")
                            .setView(view)
                            .setMessage("O campo nome do perito não pode ficar em branco")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtNomeEquipeLevantamento.setFocusable(true);
                                }
                            }).create();
                    b.show();
                } else {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipelevantamento = equipeLevantamentoDao.cadastrarCVLIEquipeLevantamento(equipelevantamento, qualquer);
                    if (codigoequipelevantamento > 0) {
                        Toast.makeText(IncluirEquipeLevantamentoActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IncluirEquipeLevantamentoActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    SpCargoLevantamento.setSelection(0);
                    EdtNomeEquipeLevantamento.setText("");
                    EdtNomeEquipeLevantamento.setFocusable(true);
                }
            }
        });

        BtnIncluirEquipeLevantamentoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Equipelevantamento equipelevantamento = new Equipelevantamento();
                EquipeLevantamentoDao equipeLevantamentoDao = new EquipeLevantamentoDao(getApplicationContext());

                equipelevantamento.setDsCargoEquipeLevantamento(CargoLevantamento);
                equipelevantamento.setDsNomeEquipeLevantamento(EdtNomeEquipeLevantamento.getText().toString());

                if (atualiza == 1) {

                    if (EdtNomeEquipeLevantamento.getText().toString().equals("")) {
                        AlertDialog.Builder b = new AlertDialog.Builder(IncluirEquipeLevantamentoActivity.this);
                        LayoutInflater factory = LayoutInflater.from(IncluirEquipeLevantamentoActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("O campo nome do perito não pode ficar em branco")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EdtNomeEquipeLevantamento.setFocusable(true);
                                    }
                                }).create();
                        b.show();
                    } else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoequipelevantamento = equipeLevantamentoDao.cadastrarCVLIEquipeLevantamentoAtualizar(equipelevantamento, cod, qualquer);
                        if (codigoequipelevantamento > 0) {
                            Toast.makeText(IncluirEquipeLevantamentoActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(IncluirEquipeLevantamentoActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                } else if (EdtNomeEquipeLevantamento.getText().toString().equals("")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(IncluirEquipeLevantamentoActivity.this);
                    LayoutInflater factory = LayoutInflater.from(IncluirEquipeLevantamentoActivity.this);
                    final View view = factory.inflate(R.layout.logotipo, null);
                    b.setTitle("IRIS - Atenção!!!")
                            .setView(view)
                            .setMessage("O campo nome do perito não pode ficar em branco")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtNomeEquipeLevantamento.setFocusable(true);
                                }
                            }).create();
                    b.show();
                } else {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipelevantamento = equipeLevantamentoDao.cadastrarCVLIEquipeLevantamento(equipelevantamento, qualquer);
                    if (codigoequipelevantamento > 0) {
                        Toast.makeText(IncluirEquipeLevantamentoActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IncluirEquipeLevantamentoActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipelevantamento, menu);

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
