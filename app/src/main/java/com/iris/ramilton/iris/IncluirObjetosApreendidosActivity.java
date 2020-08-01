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
import android.widget.TextView;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.ObjetosApreendidosDao;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.ObjetosApreendidos;

import org.w3c.dom.Text;

public class IncluirObjetosApreendidosActivity extends AppCompatActivity {

    private Spinner SpTipoObjetosApreendidos;
    private TextView TvObjetosArrecadados, TvTipodeObjetosApreendidos, TvDescricaoObjetosApreendidos;
    private EditText EdtDescricaoObjetosApreendidos;
    private Button BtnCadastarObjetosApreendidos, BtnFinalizarCadastrarObjetosApreendidos;
    private String TipoObjetosApreendidos;
    private int cod, atualiza = 0;
    private Gerarnumeros gerarnumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_objetos_apreendidos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        SpTipoObjetosApreendidos = (Spinner) findViewById(R.id.spTipoObjetosApreendidos);
        TvObjetosArrecadados = (TextView) findViewById(R.id.tvTituloObjetosArrecadados);
        TvTipodeObjetosApreendidos = (TextView) findViewById(R.id.tvTipoObjetosArrecadados);
        TvDescricaoObjetosApreendidos = (TextView) findViewById(R.id.tvDescricaoObjetosApreendidos);
        EdtDescricaoObjetosApreendidos = (EditText) findViewById(R.id.edtDescricaoObjetosApreendidos);
        BtnCadastarObjetosApreendidos = (Button) findViewById(R.id.btnCadastrarObjetosApreendidos);
        BtnFinalizarCadastrarObjetosApreendidos = (Button) findViewById(R.id.btnFinalizarIncluirObjetosApreendidos);

        gerarnumeros = new Gerarnumeros();

        Intent it = getIntent();

        if (it.hasExtra("codigoobjetosatualizar")) {
            cod = (int) it.getSerializableExtra("codigoobjetosatualizar");

            atualiza = 1;
        }

        ArrayAdapter adaptadorSpTipoObjetosApreendidos = ArrayAdapter.createFromResource(this, R.array.ObjetosApreendidoSILC, android.R.layout.simple_spinner_item);
        SpTipoObjetosApreendidos.setAdapter(adaptadorSpTipoObjetosApreendidos);

        SpTipoObjetosApreendidos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoObjetosApreendidos = SpTipoObjetosApreendidos.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnCadastarObjetosApreendidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjetosApreendidos objetosApreendidos = new ObjetosApreendidos();
                ObjetosApreendidosDao objetosApreendidosDao = new ObjetosApreendidosDao(getApplicationContext());

                objetosApreendidos.setDsTipoObjetoApreendido(TipoObjetosApreendidos);
                objetosApreendidos.setDsDescricaoObjetoApreendido(EdtDescricaoObjetosApreendidos.getText().toString());

                if (atualiza == 1) {

                    if (EdtDescricaoObjetosApreendidos.getText().toString().equals("")) {
                        AlertDialog.Builder b = new AlertDialog.Builder(IncluirObjetosApreendidosActivity.this);
                        LayoutInflater factory = LayoutInflater.from(IncluirObjetosApreendidosActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("O campo Descrição não pode ficar em branco")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EdtDescricaoObjetosApreendidos.setFocusable(true);
                                    }
                                }).create();
                        b.show();

                    } else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoobjetosapreendidos = objetosApreendidosDao.cadastrarCVLIObjetosApreendidosAtualizar(objetosApreendidos, cod, qualquer);
                        if (codigoobjetosapreendidos > 0) {
                            Toast.makeText(IncluirObjetosApreendidosActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(IncluirObjetosApreendidosActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }

                        SpTipoObjetosApreendidos.setSelection(0);
                        EdtDescricaoObjetosApreendidos.setText("");

                    }

                } else if (EdtDescricaoObjetosApreendidos.getText().toString().equals("")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(IncluirObjetosApreendidosActivity.this);
                    LayoutInflater factory = LayoutInflater.from(IncluirObjetosApreendidosActivity.this);
                    final View view = factory.inflate(R.layout.logotipo, null);
                    b.setTitle("IRIS - Atenção!!!")
                            .setView(view)
                            .setMessage("O campo Descrição não pode ficar em branco")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtDescricaoObjetosApreendidos.setFocusable(true);
                                }
                            }).create();
                    b.show();

                } else {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoobjetosapreendidos = objetosApreendidosDao.cadastrarCVLIObjetosApreendidos(objetosApreendidos, qualquer);
                    if (codigoobjetosapreendidos > 0) {
                        Toast.makeText(IncluirObjetosApreendidosActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IncluirObjetosApreendidosActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }

                    SpTipoObjetosApreendidos.setSelection(0);
                    EdtDescricaoObjetosApreendidos.setText("");

                }
            }
        });

        BtnFinalizarCadastrarObjetosApreendidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjetosApreendidos objetosApreendidos = new ObjetosApreendidos();
                ObjetosApreendidosDao objetosApreendidosDao = new ObjetosApreendidosDao(getApplicationContext());

                objetosApreendidos.setDsTipoObjetoApreendido(TipoObjetosApreendidos);
                objetosApreendidos.setDsDescricaoObjetoApreendido(EdtDescricaoObjetosApreendidos.getText().toString());

                if (atualiza == 1) {

                    if (EdtDescricaoObjetosApreendidos.getText().toString().equals("")) {
                        AlertDialog.Builder b = new AlertDialog.Builder(IncluirObjetosApreendidosActivity.this);
                        LayoutInflater factory = LayoutInflater.from(IncluirObjetosApreendidosActivity.this);
                        final View view = factory.inflate(R.layout.logotipo, null);
                        b.setTitle("IRIS - Atenção!!!")
                                .setView(view)
                                .setMessage("O campo Descrição não pode ficar em branco")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EdtDescricaoObjetosApreendidos.setFocusable(true);
                                    }
                                }).create();
                        b.show();

                    } else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoobjetosapreendidos = objetosApreendidosDao.cadastrarCVLIObjetosApreendidosAtualizar(objetosApreendidos, cod, qualquer);
                        if (codigoobjetosapreendidos > 0) {
                            Toast.makeText(IncluirObjetosApreendidosActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(IncluirObjetosApreendidosActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }

                        finish();
                    }

                } else if (EdtDescricaoObjetosApreendidos.getText().toString().equals("")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(IncluirObjetosApreendidosActivity.this);
                    LayoutInflater factory = LayoutInflater.from(IncluirObjetosApreendidosActivity.this);
                    final View view = factory.inflate(R.layout.logotipo, null);
                    b.setTitle("IRIS - Atenção!!!")
                            .setView(view)
                            .setMessage("O campo Descrição não pode ficar em branco")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtDescricaoObjetosApreendidos.setFocusable(true);
                                }
                            }).create();
                    b.show();

                } else {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoobjetosapreendidos = objetosApreendidosDao.cadastrarCVLIObjetosApreendidos(objetosApreendidos, qualquer);
                    if (codigoobjetosapreendidos > 0) {
                        Toast.makeText(IncluirObjetosApreendidosActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IncluirObjetosApreendidosActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_objetosapreendidos, menu);

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
