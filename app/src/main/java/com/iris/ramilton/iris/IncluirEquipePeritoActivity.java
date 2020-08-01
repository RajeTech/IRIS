package com.iris.ramilton.iris;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
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

import com.iris.ramilton.iris.dao.EquipePeritoDao;
import com.iris.ramilton.iris.modelo.Equipeperito;
import com.iris.ramilton.iris.modelo.Gerarnumeros;

public class IncluirEquipePeritoActivity extends AppCompatActivity {

    private Spinner SpCargoEquipePerito;
    private EditText EdtNomeEquipePerito;
    private Button BtnIncluirEquipePerito, BtnIncluirEquipePeritoFinalizar;
    private String CargoEquipePerito;
    private int cod, atualiza = 0;
    private Gerarnumeros gerarnumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_equipe_perito);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        SpCargoEquipePerito = (Spinner) findViewById(R.id.spinnerCargoEquipePerito);
        EdtNomeEquipePerito = (EditText) findViewById(R.id.edtEqPeritoNome);
        BtnIncluirEquipePerito = (Button) findViewById(R.id.btnIncluirEquipePerito);
        BtnIncluirEquipePeritoFinalizar = (Button) findViewById(R.id.btnIncluirEquipePeritoFinalizar);

        gerarnumeros = new Gerarnumeros();
        Intent it = getIntent();
        if (it.hasExtra("codigoEquiperitoatualizar")) {
            cod = (int) it.getSerializableExtra("codigoEquiperitoatualizar");

            atualiza = 1;

        }

        ArrayAdapter adaptadorApCargoEquipePerito = ArrayAdapter.createFromResource(this, R.array.CargoEquipePerito, android.R.layout.simple_spinner_item);
        SpCargoEquipePerito.setAdapter(adaptadorApCargoEquipePerito);

        SpCargoEquipePerito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CargoEquipePerito = SpCargoEquipePerito.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnIncluirEquipePerito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Equipeperito equipeperito = new Equipeperito();
                EquipePeritoDao equipePeritoDao = new EquipePeritoDao(getApplicationContext());

                equipeperito.setDsCargoEquipePerito(CargoEquipePerito);
                equipeperito.setDsNomeEquipePerito(EdtNomeEquipePerito.getText().toString());

                if (atualiza == 1) {

                    if (EdtNomeEquipePerito.getText().toString().equals("")) {
                        AlertDialog.Builder a = new AlertDialog.Builder(IncluirEquipePeritoActivity.this);
                        LayoutInflater factory = LayoutInflater.from(IncluirEquipePeritoActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        a.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("O campo nome do perito não pode ficar em branco")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EdtNomeEquipePerito.setFocusable(true);
                                    }
                                }).create();
                        a.show();
                    } else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoequipeperito = equipePeritoDao.cadastrarCVLIEquipePeritoAtualiza(equipeperito, cod, qualquer);
                        if (codigoequipeperito > 0) {
                            Toast.makeText(IncluirEquipePeritoActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(IncluirEquipePeritoActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        SpCargoEquipePerito.setSelection(0);
                        EdtNomeEquipePerito.setText("");
                        EdtNomeEquipePerito.setFocusable(true);
                    }
                } else if (EdtNomeEquipePerito.getText().toString().equals("")) {
                    AlertDialog.Builder a = new AlertDialog.Builder(IncluirEquipePeritoActivity.this);
                    LayoutInflater factory = LayoutInflater.from(IncluirEquipePeritoActivity.this);
                    final View view = factory.inflate(R.layout.logotipo, null);
                    a.setTitle("IRIS - Atenção!!!")
                            .setView(view)
                            .setMessage("O campo nome do perito não pode ficar em branco")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtNomeEquipePerito.setFocusable(true);
                                }
                            }).create();
                    a.show();
                } else {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = equipePeritoDao.cadastrarCVLIEquipePerito(equipeperito, qualquer);
                    if (codigoequipeperito > 0) {
                        Toast.makeText(IncluirEquipePeritoActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IncluirEquipePeritoActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    SpCargoEquipePerito.setSelection(0);
                    EdtNomeEquipePerito.setText("");
                    EdtNomeEquipePerito.setFocusable(true);
                }
            }
        });

        BtnIncluirEquipePeritoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Equipeperito equipeperito = new Equipeperito();
                EquipePeritoDao equipePeritoDao = new EquipePeritoDao(getApplicationContext());

                equipeperito.setDsCargoEquipePerito(CargoEquipePerito);
                equipeperito.setDsNomeEquipePerito(EdtNomeEquipePerito.getText().toString());

                if (atualiza == 1) {

                    if (EdtNomeEquipePerito.getText().toString().equals("")) {
                        AlertDialog.Builder b = new AlertDialog.Builder(IncluirEquipePeritoActivity.this);
                        b.setTitle("IRIS - Atenção!!!")
                                .setMessage("O campo nome do perito não pode ficar em branco")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EdtNomeEquipePerito.setFocusable(true);
                                    }
                                }).create();
                        b.show();
                    } else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoequipeperito = equipePeritoDao.cadastrarCVLIEquipePeritoAtualiza(equipeperito, cod, qualquer);
                        if (codigoequipeperito > 0) {
                            Toast.makeText(IncluirEquipePeritoActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(IncluirEquipePeritoActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                } else if (EdtNomeEquipePerito.getText().toString().equals("")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(IncluirEquipePeritoActivity.this);
                    LayoutInflater factory = LayoutInflater.from(IncluirEquipePeritoActivity.this);
                    final View view = factory.inflate(R.layout.logotipo, null);
                    b.setTitle("IRIS - Atenção!!!")
                            .setView(view)
                            .setMessage("O campo nome do perito não pode ficar em branco")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtNomeEquipePerito.setFocusable(true);
                                }
                            }).create();
                    b.show();
                } else {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = equipePeritoDao.cadastrarCVLIEquipePerito(equipeperito, qualquer);
                    if (codigoequipeperito > 0) {
                        Toast.makeText(IncluirEquipePeritoActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IncluirEquipePeritoActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipeperito, menu);

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
