package com.iris.ramilton.iris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.modelo.Cvli;

public class ResumoFatosActivity extends AppCompatActivity {

    private TextView TvTituloFato, TvResumoFato, TvProvidenciaResumoFato;
    private MultiAutoCompleteTextView EdtResumoFato;
    private Button BtnCadastroResumoFato;
    private EditText EdtTituloFato, EdtProvidenciaResumoFato;
    private Cvli cvli, cvliresumosematualizar;
    private CvliDao cvliDao, cvliresumosematualizarDao;
    private Cvli cvliresumo = null;
    private int atualizar = 0, controleenvio = 0, codigorecebidosem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_fatos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        TvResumoFato = (TextView) findViewById(R.id.tvTituloResumoFatoRelatorio);
        TvTituloFato = (TextView) findViewById(R.id.tvTituloFatos);
        EdtResumoFato = (MultiAutoCompleteTextView) findViewById(R.id.edtResumoFatos);
        EdtTituloFato = (EditText) findViewById(R.id.edtTituloFato);
        BtnCadastroResumoFato = (Button) findViewById(R.id.btnCadastroResumoFato);

        cvliresumosematualizar = new Cvli();
        cvliresumosematualizarDao = new CvliDao(this);

        EdtTituloFato.setFocusable(true);

        Intent it = getIntent();
        cvli = new Cvli();
        cvliDao = new CvliDao(this);
        if (it.hasExtra("resumo")) {
            cvliresumo = (Cvli) it.getSerializableExtra("resumo");

            ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cvliDao.retornarCVLIGeral(cvliresumo.getId()));
            EdtResumoFato.setAdapter(adaptador);
            EdtResumoFato.setThreshold(1);

            EdtResumoFato.setTokenizer(new SpaceTokenizer());

            atualizar = 1;
            controleenvio = 4;

            EdtResumoFato.setText(cvliresumo.getDsResumoFato());
            EdtTituloFato.setText(cvliresumo.getDsTituloFato());
        }else if(it.hasExtra("resumoematualizar")){
            codigorecebidosem = (int) it.getSerializableExtra("resumoematualizar");

            cvliresumosematualizar = cvliresumosematualizarDao.retornaCVLIResumoObj(codigorecebidosem);

            ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cvliresumosematualizarDao.retornarCVLIGeral(cvliresumosematualizar.getId()));
            EdtResumoFato.setAdapter(adaptador);
            EdtResumoFato.setThreshold(1);

            EdtResumoFato.setTokenizer(new SpaceTokenizer());

            atualizar = 2;
            controleenvio = 4;

            EdtResumoFato.setText(cvliresumosematualizar.getDsResumoFato());
            EdtTituloFato.setText(cvliresumosematualizar.getDsTituloFato());
        }


        BtnCadastroResumoFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvli.setDsResumoFato(EdtResumoFato.getText().toString());
                cvli.setDsTituloFato(EdtTituloFato.getText().toString());

                int codigocvli = cvliDao.retornarCodigoCvliSemParametro();

                if (atualizar == 1) {
                    long certo = cvliDao.AtualizarCVLIResumoFatos(cvli, cvliresumo.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(ResumoFatosActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ResumoFatosActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else if(atualizar == 2){
                    long certo = cvliresumosematualizarDao.AtualizarCVLIResumoFatos(cvli, cvliresumosematualizar.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(ResumoFatosActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ResumoFatosActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    long certo = cvliDao.CadastrarCVLIResumoFatos(cvli,codigocvli);
                    if (certo > 0) {
                        Toast.makeText(ResumoFatosActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ResumoFatosActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resumo, menu);

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
