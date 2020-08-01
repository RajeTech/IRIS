package com.iris.ramilton.iris;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.iris.ramilton.iris.dao.ConduzidoacaopolicialDao;
import com.iris.ramilton.iris.dao.VitimaDao;
import com.iris.ramilton.iris.modelo.Conduzidoacaopolicial;
import com.iris.ramilton.iris.modelo.Vitima;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AtualizarConduzidoAcaoPolicial extends AppCompatActivity {

    private TextView TvTituloAtualizarConduzido;
    private Button BtnConcluirEditarConduzido;
    private ListView LvListaConduzidos;
    private List<Conduzidoacaopolicial> ListaConduzidosGeral;
    private List<Conduzidoacaopolicial> ListaConduzidosFiltrados = new ArrayList<>();
    private Conduzidoacaopolicial conduzido = null;
    private Conduzidoacaopolicial conduzidoacaopolicialpesquisar;
    private ConduzidoacaopolicialDao conduzidoacaopolicialDao;
    private int fkConduzido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_conduzido_acao_policial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        TvTituloAtualizarConduzido = (TextView) findViewById(R.id.tvTituloAtualizarAutoria);
        LvListaConduzidos = (ListView) findViewById(R.id.lvConduzidosAcaoPolicial);
        BtnConcluirEditarConduzido = (Button) findViewById(R.id.btnConcluirConduzidoAcaoPolicial);

        Intent it = getIntent();

        if(it.hasExtra("codigoconduzidos")){
            fkConduzido = (int) it.getSerializableExtra("codigoconduzidos");
        }

        conduzidoacaopolicialpesquisar = new Conduzidoacaopolicial();
        conduzidoacaopolicialDao = new ConduzidoacaopolicialDao(this);

        ListaConduzidosGeral = conduzidoacaopolicialDao.retornarConduzidosAcaoPolicial(fkConduzido);
        ListaConduzidosFiltrados.addAll(ListaConduzidosGeral);
        ArrayAdapter<Conduzidoacaopolicial> adaptadorconduzidopesquisa = new ArrayAdapter<Conduzidoacaopolicial>(this,android.R.layout.simple_list_item_1,ListaConduzidosFiltrados);
        LvListaConduzidos.setAdapter(adaptadorconduzidopesquisa);

        registerForContextMenu(LvListaConduzidos);

        BtnConcluirEditarConduzido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conduzido, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish(); // Finaliza a Activity atual e assim volta para a tela anterior
                break;
            case R.id.IncluirConduzidos:
                Intent it = new Intent(AtualizarConduzidoAcaoPolicial.this, ConduzidosAcaoPolicialActivity.class);
                it.putExtra("fkConduzidoAtualizar", fkConduzido);
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
        i.inflate(R.menu.menu_contexto_conduzidoacaopolicial, menu);
    }

    public void AtualizarConduzidoAcaoPolicial(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Conduzidoacaopolicial ConduzidoAtualizar = ListaConduzidosFiltrados.get(menuInfo.position);
        Intent it = new Intent(AtualizarConduzidoAcaoPolicial.this, ConduzidosAcaoPolicialActivity.class);
        it.putExtra("conduzidoAtualizar", ConduzidoAtualizar);
        startActivity(it);
    }

    public void DeletarConduzidoAcaoPolicial(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Conduzidoacaopolicial ConduzidoAtualizar = ListaConduzidosFiltrados.get(menuInfo.position);
        AlertDialog.Builder b = new AlertDialog.Builder(AtualizarConduzidoAcaoPolicial.this);
        LayoutInflater factory = LayoutInflater.from(AtualizarConduzidoAcaoPolicial.this);
        final View view = factory.inflate(R.layout.logotipo, null);
        b.setTitle("IRIS - Atenção!!!")
                .setView(view)
                .setMessage("Deseja Realmente Excluir Este Conduzido?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        conduzidoacaopolicialDao.excluirConduzidoAcaoPolicial(ConduzidoAtualizar.getId());
                        ListaConduzidosFiltrados.remove(ConduzidoAtualizar);
                        ListaConduzidosGeral.remove(ConduzidoAtualizar);
                        LvListaConduzidos.invalidateViews();

                    }
                }).create();
        b.show();

    }

    @Override
    public void onResume(){
        super.onResume();

        ListaConduzidosGeral = conduzidoacaopolicialDao.retornarConduzidosAcaoPolicial(fkConduzido);
        ListaConduzidosFiltrados.clear();
        ListaConduzidosFiltrados.addAll(ListaConduzidosGeral);
        LvListaConduzidos.invalidateViews();

    }
}
