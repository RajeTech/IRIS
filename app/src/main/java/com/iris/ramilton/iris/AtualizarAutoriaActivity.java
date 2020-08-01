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

import com.iris.ramilton.iris.dao.AutoriaDao;
import com.iris.ramilton.iris.dao.VitimaDao;
import com.iris.ramilton.iris.modelo.Autoria;
import com.iris.ramilton.iris.modelo.Vitima;

import java.util.ArrayList;
import java.util.List;

public class AtualizarAutoriaActivity extends AppCompatActivity {

    private ListView LvAutoriaAtualizar;
    private Button BtnConcluirAutoriaEditar;
    private TextView TvTituloAutoriaAtualizar;
    private List<Autoria> ListaAutoriaGeral;
    private List<Autoria> ListaAutoriaGeralFiltrados = new ArrayList<>();
    private Autoria autoria = null;
    private Autoria autoriapesquisa;
    private AutoriaDao autoriaDao;
    private int fkautoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_autoria);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        LvAutoriaAtualizar = (ListView) findViewById(R.id.lvAutoresAtualizar);
        TvTituloAutoriaAtualizar = (TextView) findViewById(R.id.tvTituloAtualizarAutoria);
        BtnConcluirAutoriaEditar = (Button) findViewById(R.id.btnConcluirAutoresEditar);

        Intent it = getIntent();

        if(it.hasExtra("codigoautoria")){
            fkautoria = (int) it.getSerializableExtra("codigoautoria");
        }

        autoriapesquisa = new Autoria();
        autoriaDao = new AutoriaDao(this);

        ListaAutoriaGeral = autoriaDao.retornarAutoria(fkautoria);
        ListaAutoriaGeralFiltrados.addAll(ListaAutoriaGeral);
        ArrayAdapter<Autoria> adaptadorautoriapesquisa = new ArrayAdapter<Autoria>(this,android.R.layout.simple_list_item_1,ListaAutoriaGeralFiltrados);
        LvAutoriaAtualizar.setAdapter(adaptadorautoriapesquisa);

        registerForContextMenu(LvAutoriaAtualizar);

        BtnConcluirAutoriaEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_autoria, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish(); // Finaliza a Activity atual e assim volta para a tela anterior
                break;
            case  R.id.IncluirAutoria:
                Intent it = new Intent(AtualizarAutoriaActivity.this, AutoriaActivity.class);
                it.putExtra("fkautoriaAtualizar", fkautoria);
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
        i.inflate(R.menu.menu_contexto_autoria, menu);
    }

    public void AtualizarAutoria(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Autoria autoriaAtualizar = ListaAutoriaGeralFiltrados.get(menuInfo.position);
        Intent it = new Intent(AtualizarAutoriaActivity.this, AutoriaActivity.class);
        it.putExtra("autoriaAtualizar", autoriaAtualizar);
        startActivity(it);
    }

    public void DeletarAutoria(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Autoria autoriaAtualizar = ListaAutoriaGeralFiltrados.get(menuInfo.position);
        AlertDialog.Builder b = new AlertDialog.Builder(AtualizarAutoriaActivity.this);
        LayoutInflater factory = LayoutInflater.from(AtualizarAutoriaActivity.this);
        final View view = factory.inflate(R.layout.logotipo, null);
        b.setTitle("IRIS - Atenção!!!")
                .setView(view)
                .setMessage("Deseja Realmente Excluir Este Autor?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        autoriaDao.excluirAutoria(autoriaAtualizar.getId());
                        ListaAutoriaGeralFiltrados.remove(autoriaAtualizar);
                        ListaAutoriaGeral.remove(autoriaAtualizar);
                        LvAutoriaAtualizar.invalidateViews();

                    }
                }).create();
        b.show();
    }

    @Override
    public void onResume(){
        super.onResume();

        ListaAutoriaGeral = autoriaDao.retornarAutoria(fkautoria);
        ListaAutoriaGeralFiltrados.clear();
        ListaAutoriaGeralFiltrados.addAll(ListaAutoriaGeral);
        LvAutoriaAtualizar.invalidateViews();

    }
}
