package com.iris.ramilton.iris;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.iris.ramilton.iris.dao.AcaoPolicialDao;
import com.iris.ramilton.iris.modelo.Acaopolicial;

public class DoProcedimentoAcaoPolicial extends AppCompatActivity {

    private Button BtnNIPAcaoPolicial, BtnNomeDelegadoAcaoPolicial, BtnConcluirDoProcedimentoAcaoPolicial;
    private EditText EdtNIPAcaoPolicial, EdtNomeDelegadoAcaoPolicial;
    private Acaopolicial acaopolicial, acaopolicialdoprocedimentosematualizar;
    private AcaoPolicialDao acaopolicialDao, acaoPolicialDaodoprocedimentosematualizar;
    private Acaopolicial acaopolicialdoprocedimento = null;
    private int atualizar = 0, controleenvio = 0, codigorecebidosem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_procedimento_acao_policial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        BtnNIPAcaoPolicial = (Button) findViewById(R.id.btnNIPAcaoPolicial);
        BtnNomeDelegadoAcaoPolicial = (Button) findViewById(R.id.btnNomeDelegadoAcaoPolicial);
        BtnConcluirDoProcedimentoAcaoPolicial = (Button) findViewById(R.id.btnConcluirDoProcedimentoAcaoPolicial);
        EdtNIPAcaoPolicial = (EditText) findViewById(R.id.edtNIAcaoPolicial);
        EdtNomeDelegadoAcaoPolicial = (EditText) findViewById(R.id.edtNomeDelegadoAcaoPolicial);

        acaopolicialdoprocedimentosematualizar = new Acaopolicial();
        acaoPolicialDaodoprocedimentosematualizar = new AcaoPolicialDao(this);

        SimpleMaskFormatter mascaraIP = new SimpleMaskFormatter("NNN/NNNN");
        MaskTextWatcher maskIP = new MaskTextWatcher(EdtNIPAcaoPolicial,mascaraIP);
        EdtNIPAcaoPolicial.addTextChangedListener(maskIP);

        Intent it = getIntent();
        if (it.hasExtra("acaopolicialprocedimento")) {
            acaopolicialdoprocedimento = (Acaopolicial) it.getSerializableExtra("acaopolicialprocedimento");
            atualizar = 1;
            controleenvio = 4;
            EdtNIPAcaoPolicial.setText(acaopolicialdoprocedimento.getDsNIPAcaoPolicial());
            EdtNomeDelegadoAcaoPolicial.setText(acaopolicialdoprocedimento.getDsNomeDelegadoAcaoPolicial());

        }else if(it.hasExtra("procedimentosematualizar")){

            codigorecebidosem = (int) it.getSerializableExtra("procedimentosematualizar");
            atualizar = 2;
            controleenvio = 4;
            acaopolicialdoprocedimentosematualizar = acaoPolicialDaodoprocedimentosematualizar.retornaAcaopolicialdoProcedimentoObj(codigorecebidosem);

            EdtNIPAcaoPolicial.setText(acaopolicialdoprocedimentosematualizar.getDsNIPAcaoPolicial());
            EdtNomeDelegadoAcaoPolicial.setText(acaopolicialdoprocedimentosematualizar.getDsNomeDelegadoAcaoPolicial());

        }

        acaopolicial = new Acaopolicial();
        acaopolicialDao = new AcaoPolicialDao(this);

        EdtNIPAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtNomeDelegadoAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        BtnNIPAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtNIPAcaoPolicial.setVisibility(View.VISIBLE);

            }
        });

        EdtNIPAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnNomeDelegadoAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        BtnNomeDelegadoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtNomeDelegadoAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        BtnConcluirDoProcedimentoAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acaopolicial.setDsNIPAcaoPolicial(EdtNIPAcaoPolicial.getText().toString());
                acaopolicial.setDsNomeDelegadoAcaoPolicial(EdtNomeDelegadoAcaoPolicial.getText().toString());

                int codigocvli = acaopolicialDao.retornarCodigoAcaoPolicialSemParametro();

                if (atualizar == 1) {
                    long certo = acaopolicialDao.AtualizarAcaoPolicialDoProcedimento(acaopolicial, acaopolicialdoprocedimento.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(DoProcedimentoAcaoPolicial.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DoProcedimentoAcaoPolicial.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else if(atualizar == 2) {

                    long certo = acaopolicialDao.AtualizarAcaoPolicialDoProcedimento(acaopolicial, acaopolicialdoprocedimentosematualizar.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(DoProcedimentoAcaoPolicial.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DoProcedimentoAcaoPolicial.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else{
                    long certo = acaopolicialDao.cadastrarAcaoPolicialDoProcedimento(acaopolicial, codigocvli);
                    if (certo > 0) {
                        Toast.makeText(DoProcedimentoAcaoPolicial.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DoProcedimentoAcaoPolicial.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doprocedimentoacaopolicial, menu);

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
