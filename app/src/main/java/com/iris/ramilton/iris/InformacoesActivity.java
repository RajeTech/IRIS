package com.iris.ramilton.iris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.iris.ramilton.iris.dao.UsuarioDao;

public class InformacoesActivity extends AppCompatActivity {

    private TextView TvNomeUsuarioInformacao, TvMatriculaCPFInformacao, TvUnidadeInformacao;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        TvNomeUsuarioInformacao = (TextView) findViewById(R.id.tvNomeUsuarioInformacao);
        TvMatriculaCPFInformacao = (TextView) findViewById(R.id.tvMatriculaCPFInformacao);
        TvUnidadeInformacao = (TextView) findViewById(R.id.tvUnidadeInformacao);

        usuarioDao = new UsuarioDao(this);

        TvNomeUsuarioInformacao.setText(usuarioDao.user.getDsNome());
        TvMatriculaCPFInformacao.setText(usuarioDao.user.getDsMatricula());
        TvUnidadeInformacao.setText(String.valueOf(usuarioDao.user.getDsIdUnidade()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_informacoes, menu);

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
