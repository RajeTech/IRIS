package com.iris.ramilton.iris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.ObjapreendidoarmaacaopolicialDao;
import com.iris.ramilton.iris.dao.ObjapreendidodrogaacaopolicialDao;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.Objapreendidoarmaacaopolicial;
import com.iris.ramilton.iris.modelo.Objapreendidodrogaacaopolicial;

public class DrogasAcaoPolicialActivity extends AppCompatActivity {

    private EditText EdtTipoDrogaAcaoPolicial, EdtQtdDrogaAcaoPolicial, EdtPesoDrogaAcaoPolicial;
    private Spinner SpModoApresentacaoDrogaAcaoPolicial, SpTipoDrogasAcaoPolicial;
    private Button BtnIncluirNovaDrogaAcaoPolicial, BtnFinalizarDrogaAcaoPolicial;
    private String modoapresentacaodroga, tipodrogasacaopolicial;
    private int cod, atualiza = 0;
    private Gerarnumeros gerarnumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drogas_acao_policial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        EdtTipoDrogaAcaoPolicial = (EditText) findViewById(R.id.edtTipoDrogasAcaoPolicial);
        EdtQtdDrogaAcaoPolicial = (EditText) findViewById(R.id.edtQtdDrogasAcaoPolicial);
        EdtPesoDrogaAcaoPolicial = (EditText) findViewById(R.id.edtPesoDrogaAcaoPolicial);
        SpModoApresentacaoDrogaAcaoPolicial = (Spinner) findViewById(R.id.spModoApresentacaoDrogaAcaoPolicial);
        SpTipoDrogasAcaoPolicial = (Spinner) findViewById(R.id.spTipoDrogaAcaoPolicial);
        BtnIncluirNovaDrogaAcaoPolicial = (Button) findViewById(R.id.btnIncluirNovaDrogaAcaoPolicial);
        BtnFinalizarDrogaAcaoPolicial = (Button) findViewById(R.id.btnFinalizarDrogaAcaoPolicial);

        gerarnumeros = new Gerarnumeros();
        Intent it = getIntent();
        if (it.hasExtra("codigoDrogaAtualizar")) {
            cod = (int) it.getSerializableExtra("codigoDrogaatualizar");

            atualiza = 1;

        }

        ArrayAdapter adaptadorTipoDroga = ArrayAdapter.createFromResource(this, R.array.TipoDrogasAcaoPolicial, android.R.layout.simple_spinner_item);
        SpTipoDrogasAcaoPolicial.setAdapter(adaptadorTipoDroga);

        ArrayAdapter adaptadorModoApresesntacaoDroga = ArrayAdapter.createFromResource(this, R.array.TipoApresentacaoDrogas, android.R.layout.simple_spinner_item);
        SpModoApresentacaoDrogaAcaoPolicial.setAdapter(adaptadorModoApresesntacaoDroga);

        SpModoApresentacaoDrogaAcaoPolicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                modoapresentacaodroga = SpModoApresentacaoDrogaAcaoPolicial.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpTipoDrogasAcaoPolicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tipodrogasacaopolicial = SpTipoDrogasAcaoPolicial.getItemAtPosition(position).toString();

                if(tipodrogasacaopolicial.equals("Outras")){
                    EdtTipoDrogaAcaoPolicial.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnIncluirNovaDrogaAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objapreendidodrogaacaopolicial objapreendidodrogaacaopolicial = new Objapreendidodrogaacaopolicial();
                ObjapreendidodrogaacaopolicialDao objapreendidodrogaacaopolicialDao = new ObjapreendidodrogaacaopolicialDao(getApplicationContext());

                objapreendidodrogaacaopolicial.setDsTipoDrogaAcaoPolicial(tipodrogasacaopolicial);
                objapreendidodrogaacaopolicial.setDsModoApresentacaoDrogaAcaoPolicial(modoapresentacaodroga);
                objapreendidodrogaacaopolicial.setQtdDrogaAcaoPolicial(Integer.parseInt(EdtQtdDrogaAcaoPolicial.getText().toString()));
                objapreendidodrogaacaopolicial.setDsPesoGramaAcaoPolicial(EdtPesoDrogaAcaoPolicial.getText().toString());

                if (atualiza == 1) {

                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = objapreendidodrogaacaopolicialDao.cadastrarDrogaAcaoPolicialAtualiza(objapreendidodrogaacaopolicial, cod, qualquer);
                    if (codigoequipeperito > 0) {
                        Toast.makeText(DrogasAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DrogasAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    SpTipoDrogasAcaoPolicial.setSelection(0);
                    SpModoApresentacaoDrogaAcaoPolicial.setSelection(0);
                    EdtQtdDrogaAcaoPolicial.setText("");
                    EdtPesoDrogaAcaoPolicial.setText("");
                }else{
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = objapreendidodrogaacaopolicialDao.cadastrarDrogaAcaoPolicial(objapreendidodrogaacaopolicial, qualquer);
                    if (codigoequipeperito > 0) {
                        Toast.makeText(DrogasAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DrogasAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    SpTipoDrogasAcaoPolicial.setSelection(0);
                    SpModoApresentacaoDrogaAcaoPolicial.setSelection(0);
                    EdtQtdDrogaAcaoPolicial.setText("");
                    EdtPesoDrogaAcaoPolicial.setText("");
                }
            }
        });

        BtnFinalizarDrogaAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objapreendidodrogaacaopolicial objapreendidodrogaacaopolicial = new Objapreendidodrogaacaopolicial();
                ObjapreendidodrogaacaopolicialDao objapreendidodrogaacaopolicialDao = new ObjapreendidodrogaacaopolicialDao(getApplicationContext());

                objapreendidodrogaacaopolicial.setDsTipoDrogaAcaoPolicial(tipodrogasacaopolicial);
                objapreendidodrogaacaopolicial.setDsModoApresentacaoDrogaAcaoPolicial(modoapresentacaodroga);
                objapreendidodrogaacaopolicial.setQtdDrogaAcaoPolicial(Integer.parseInt(EdtQtdDrogaAcaoPolicial.getText().toString()));
                objapreendidodrogaacaopolicial.setDsPesoGramaAcaoPolicial(EdtPesoDrogaAcaoPolicial.getText().toString());

                if (atualiza == 1) {

                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = objapreendidodrogaacaopolicialDao.cadastrarDrogaAcaoPolicialAtualiza(objapreendidodrogaacaopolicial, cod, qualquer);
                    if (codigoequipeperito > 0) {
                        Toast.makeText(DrogasAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DrogasAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else{
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = objapreendidodrogaacaopolicialDao.cadastrarDrogaAcaoPolicial(objapreendidodrogaacaopolicial, qualquer);
                    if (codigoequipeperito > 0) {
                        Toast.makeText(DrogasAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DrogasAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drogasacaopolicial, menu);

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
