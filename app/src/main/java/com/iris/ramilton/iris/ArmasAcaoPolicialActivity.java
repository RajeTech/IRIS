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

import com.iris.ramilton.iris.dao.EquipePeritoDao;
import com.iris.ramilton.iris.dao.ObjapreendidoarmaacaopolicialDao;
import com.iris.ramilton.iris.modelo.Equipeperito;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.Objapreendidoarmaacaopolicial;

public class ArmasAcaoPolicialActivity extends AppCompatActivity {

    private Spinner SpTipoArmaAcaoPolicial;
    private EditText EdtModeloArmaAcaoPolicial, EdtCalibreArmaAcaoPolicial, EdtQtdArmaAcaoPolicial;
    private Button BtnCadastrarNovoArmaAcaoPolicial, BtnFinalizarArmaAcaoPolicial;
    private String tipoarmaacaopolicial;
    private int cod, atualiza = 0;
    private Gerarnumeros gerarnumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armas_acao_policial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        SpTipoArmaAcaoPolicial = (Spinner) findViewById(R.id.spTipoArmaAcaoPolicial);
        EdtModeloArmaAcaoPolicial = (EditText) findViewById(R.id.edtModeloArmaAcaoPolicial);
        EdtCalibreArmaAcaoPolicial = (EditText) findViewById(R.id.edtCalibreArmaAcaoPolicial);
        EdtQtdArmaAcaoPolicial = (EditText) findViewById(R.id.edtQtdArmasAcaoPolicial);
        BtnCadastrarNovoArmaAcaoPolicial = (Button) findViewById(R.id.btnCadastrarNovoArmasAcaoPolicial);
        BtnFinalizarArmaAcaoPolicial = (Button) findViewById(R.id.btnFinalizarCadastroArmasAcaoPolicial);

        gerarnumeros = new Gerarnumeros();
        Intent it = getIntent();
        if (it.hasExtra("codigoArmaAtualizar")) {
            cod = (int) it.getSerializableExtra("codigoArmaatualizar");

            atualiza = 1;

        }

        ArrayAdapter adaptadorTipoArma = ArrayAdapter.createFromResource(this, R.array.ArmasAcaoPolicial, android.R.layout.simple_spinner_item);
        SpTipoArmaAcaoPolicial.setAdapter(adaptadorTipoArma);

        SpTipoArmaAcaoPolicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tipoarmaacaopolicial = SpTipoArmaAcaoPolicial.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnCadastrarNovoArmaAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objapreendidoarmaacaopolicial objapreendidoarmaacaopolicial = new Objapreendidoarmaacaopolicial();
                ObjapreendidoarmaacaopolicialDao objapreendidoarmaacaopolicialDao = new ObjapreendidoarmaacaopolicialDao(getApplicationContext());

                objapreendidoarmaacaopolicial.setDsTipoArmaAcaoPolicial(tipoarmaacaopolicial);
                objapreendidoarmaacaopolicial.setDsModeloArmaAcaoPolicial(EdtModeloArmaAcaoPolicial.getText().toString());
                objapreendidoarmaacaopolicial.setDsCalibreArmaAcaoPolicial(EdtCalibreArmaAcaoPolicial.getText().toString());
                objapreendidoarmaacaopolicial.setQtdArmaAcaoPolicial(Integer.parseInt(EdtQtdArmaAcaoPolicial.getText().toString()));

                if (atualiza == 1) {

                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = objapreendidoarmaacaopolicialDao.cadastrarArmaAcaoPolicialAtualiza(objapreendidoarmaacaopolicial, cod, qualquer);
                    if (codigoequipeperito > 0) {
                          Toast.makeText(ArmasAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ArmasAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        SpTipoArmaAcaoPolicial.setSelection(0);
                        EdtModeloArmaAcaoPolicial.setText("");
                        EdtCalibreArmaAcaoPolicial.setText("");
                        EdtQtdArmaAcaoPolicial.setText("");
                    }else{
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoequipeperito = objapreendidoarmaacaopolicialDao.cadastrarArmaAcaoPolicial(objapreendidoarmaacaopolicial, qualquer);
                        if (codigoequipeperito > 0) {
                            Toast.makeText(ArmasAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ArmasAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        SpTipoArmaAcaoPolicial.setSelection(0);
                        EdtModeloArmaAcaoPolicial.setText("");
                        EdtCalibreArmaAcaoPolicial.setText("");
                        EdtQtdArmaAcaoPolicial.setText("");
                }

            }
        });

        BtnFinalizarArmaAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objapreendidoarmaacaopolicial objapreendidoarmaacaopolicial = new Objapreendidoarmaacaopolicial();
                ObjapreendidoarmaacaopolicialDao objapreendidoarmaacaopolicialDao = new ObjapreendidoarmaacaopolicialDao(getApplicationContext());

                objapreendidoarmaacaopolicial.setDsTipoArmaAcaoPolicial(tipoarmaacaopolicial);
                objapreendidoarmaacaopolicial.setDsModeloArmaAcaoPolicial(EdtModeloArmaAcaoPolicial.getText().toString());
                objapreendidoarmaacaopolicial.setDsCalibreArmaAcaoPolicial(EdtCalibreArmaAcaoPolicial.getText().toString());
                objapreendidoarmaacaopolicial.setQtdArmaAcaoPolicial(Integer.parseInt(EdtQtdArmaAcaoPolicial.getText().toString()));

                if (atualiza == 1) {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = objapreendidoarmaacaopolicialDao.cadastrarArmaAcaoPolicialAtualiza(objapreendidoarmaacaopolicial, cod, qualquer);
                    if (codigoequipeperito > 0) {
                         Toast.makeText(ArmasAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                         Toast.makeText(ArmasAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    }else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoequipeperito = objapreendidoarmaacaopolicialDao.cadastrarArmaAcaoPolicial(objapreendidoarmaacaopolicial, qualquer);
                        if (codigoequipeperito > 0) {
                            Toast.makeText(ArmasAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ArmasAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_armasacaopolicial, menu);

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
