package com.iris.ramilton.iris;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.VitimaDao;
import com.iris.ramilton.iris.modelo.Carro;
import com.iris.ramilton.iris.modelo.Cvli;
import com.iris.ramilton.iris.modelo.Vitima;

import java.util.ArrayList;
import java.util.List;

public class AtualizarVitimaActivity extends AppCompatActivity {

    private TextView TvTituloAtualizarVitima;
    private Button BtnConcluirEditarVitima;
    private ListView LvListaVitimas;
    private List<Vitima> ListaVitimaGeral;
    private List<Vitima> ListaVitimaGeralFiltrados = new ArrayList<>();
    private Vitima vitima = null;
    private Vitima vitimapesquisa;
    private VitimaDao vitimaDao;
    private int fkvitima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_vitima);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        TvTituloAtualizarVitima = (TextView) findViewById(R.id.tvListaVitimas);
        LvListaVitimas = (ListView) findViewById(R.id.lvListaVitimas);
        BtnConcluirEditarVitima = (Button) findViewById(R.id.btnConcluirEditarVitima);

        Intent it = getIntent();

        if(it.hasExtra("codigovitima")){
            fkvitima = (int) it.getSerializableExtra("codigovitima");
        }


        vitimapesquisa = new Vitima();
        vitimaDao = new VitimaDao(this);

        ListaVitimaGeral = vitimaDao.retornarVitimas(fkvitima);
        ListaVitimaGeralFiltrados.addAll(ListaVitimaGeral);
        ArrayAdapter<Vitima> adaptadorvitimapesquisa = new ArrayAdapter<Vitima>(this,android.R.layout.simple_list_item_1,ListaVitimaGeralFiltrados);
        LvListaVitimas.setAdapter(adaptadorvitimapesquisa);

        registerForContextMenu(LvListaVitimas);

        BtnConcluirEditarVitima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vitima, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        switch (id) {
            case android.R.id.home:
                finish(); // Finaliza a Activity atual e assim volta para a tela anterior
                break;
            case R.id.IncluirVitima:
                Intent it = new Intent(AtualizarVitimaActivity.this, VitimaActivity.class);
                it.putExtra("fkvitimaAtualizar", fkvitima);
                startActivity(it);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto_vitima, menu);
    }

    public void AtualizarVitima(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Vitima vitimaAtualizar = ListaVitimaGeralFiltrados.get(menuInfo.position);
        Intent it = new Intent(AtualizarVitimaActivity.this, VitimaActivity.class);
        it.putExtra("vitimaAtualizar", vitimaAtualizar);
        startActivity(it);
    }

    public void DeletarVitima(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Vitima vitimaAtualizar = ListaVitimaGeralFiltrados.get(menuInfo.position);
        AlertDialog.Builder b = new AlertDialog.Builder(AtualizarVitimaActivity.this);
        LayoutInflater factory = LayoutInflater.from(AtualizarVitimaActivity.this);
        final View view = factory.inflate(R.layout.logotipo, null);
        b.setTitle("IRIS - Atenção!!!")
                .setView(view)
                .setMessage("Deseja Realmente Excluir Esta Vítima?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vitimaDao.excluirVitima(vitimaAtualizar.getId());
                        ListaVitimaGeralFiltrados.remove(vitimaAtualizar);
                        ListaVitimaGeral.remove(vitimaAtualizar);
                        LvListaVitimas.invalidateViews();

                    }
                }).create();
        b.show();

    }

    @Override
    public void onResume(){
        super.onResume();

            ListaVitimaGeral = vitimaDao.retornarVitimas(fkvitima);
            ListaVitimaGeralFiltrados.clear();
            ListaVitimaGeralFiltrados.addAll(ListaVitimaGeral);
            LvListaVitimas.invalidateViews();

    }
}
