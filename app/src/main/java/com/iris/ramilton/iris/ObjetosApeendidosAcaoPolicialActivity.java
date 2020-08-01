package com.iris.ramilton.iris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.iris.ramilton.iris.dao.EquipeLevantamentoDao;
import com.iris.ramilton.iris.dao.EquipePeritoDao;
import com.iris.ramilton.iris.dao.EquipePreservacaoLocalDao;
import com.iris.ramilton.iris.dao.ObjapreendidoarmaacaopolicialDao;
import com.iris.ramilton.iris.dao.ObjapreendidodrogaacaopolicialDao;
import com.iris.ramilton.iris.dao.ObjapreendidoveiculoacaopolicialDao;
import com.iris.ramilton.iris.dao.ObjetosApreendidosDao;
import com.iris.ramilton.iris.modelo.Equipelevantamento;
import com.iris.ramilton.iris.modelo.Equipeperito;
import com.iris.ramilton.iris.modelo.Equipepreservacaolocal;
import com.iris.ramilton.iris.modelo.Objapreendidoarmaacaopolicial;
import com.iris.ramilton.iris.modelo.Objapreendidodrogaacaopolicial;
import com.iris.ramilton.iris.modelo.Objapreendidoveiculoacaopolicial;
import com.iris.ramilton.iris.modelo.ObjetosApreendidos;

import java.util.ArrayList;
import java.util.List;

public class ObjetosApeendidosAcaoPolicialActivity extends AppCompatActivity {

    private Button BtnArmasAcaoPolicial, BtnDrogasAcaoPolicial, BtnVeiculosAcaoPolicial, BtnValidarObjetosAcaoPolicial;
    private ListView LvArmasAcaoPolicial, LvDrogasAcaoPolicial, LvVeiculosAcaoPolicial;
    private ObjapreendidoarmaacaopolicialDao objapreendidoarmaacaopolicialDao;
    private ObjapreendidodrogaacaopolicialDao objapreendidodrogaacaopolicialDao;
    private ObjapreendidoveiculoacaopolicialDao objapreendidoveiculoacaopolicialDao;
    private List<Objapreendidoarmaacaopolicial> ListaObjApreendidosArmasAcaoPolicial;
    private List<Objapreendidoarmaacaopolicial> ListaObjApreendidosArmasAcaoPolicialFiltrado = new ArrayList<>();
    private List<Objapreendidodrogaacaopolicial> ListaObjApreendidosDrogasAcaoPolicial;
    private List<Objapreendidodrogaacaopolicial> ListaObjApreendidosDrogasAcaoPolicialFiltrado = new ArrayList<>();
    private List<Objapreendidoveiculoacaopolicial> ListaObjApreendidosVeiculosAcaoPolicial;
    private List<Objapreendidoveiculoacaopolicial> ListaObjApreendidosVeiculosAcaoPolicialFiltrado = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetos_apeendidos_acao_policial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        BtnArmasAcaoPolicial = (Button) findViewById(R.id.btnArmasAcaoPolicial);
        BtnDrogasAcaoPolicial = (Button) findViewById(R.id.btnDrogasAcaoPolicial);
        BtnVeiculosAcaoPolicial = (Button) findViewById(R.id.btnVeiculosAcaoPolicial);
        BtnValidarObjetosAcaoPolicial = (Button) findViewById(R.id.btnValidarObjetosAcaoPolicial);
        LvArmasAcaoPolicial = (ListView) findViewById(R.id.lvArmarAcaoPolicial);
        LvDrogasAcaoPolicial = (ListView) findViewById(R.id.lvDrogasAcaoPolicial);
        LvVeiculosAcaoPolicial = (ListView) findViewById(R.id.lvCarrosAcaoPolicial);

        objapreendidoarmaacaopolicialDao = new ObjapreendidoarmaacaopolicialDao(this);
        ListaObjApreendidosArmasAcaoPolicial = objapreendidoarmaacaopolicialDao.retornarObjApreendidoAcaoPolicial();
        ListaObjApreendidosArmasAcaoPolicialFiltrado.addAll(ListaObjApreendidosArmasAcaoPolicial);
        ArrayAdapter<Objapreendidoarmaacaopolicial> adaptadorArma = new ArrayAdapter<Objapreendidoarmaacaopolicial>(this, android.R.layout.simple_list_item_1, ListaObjApreendidosArmasAcaoPolicialFiltrado);
        LvArmasAcaoPolicial.setAdapter(adaptadorArma);

        objapreendidodrogaacaopolicialDao = new ObjapreendidodrogaacaopolicialDao(this);
        ListaObjApreendidosDrogasAcaoPolicial = objapreendidodrogaacaopolicialDao.retornarObjApreendidoDrogaAcaoPolicial();
        ListaObjApreendidosDrogasAcaoPolicialFiltrado.addAll(ListaObjApreendidosDrogasAcaoPolicial);
        ArrayAdapter<Objapreendidodrogaacaopolicial> adaptadorDroga = new ArrayAdapter<Objapreendidodrogaacaopolicial>(this, android.R.layout.simple_list_item_1, ListaObjApreendidosDrogasAcaoPolicialFiltrado);
        LvDrogasAcaoPolicial.setAdapter(adaptadorDroga);

        objapreendidoveiculoacaopolicialDao = new ObjapreendidoveiculoacaopolicialDao(this);
        ListaObjApreendidosVeiculosAcaoPolicial = objapreendidoveiculoacaopolicialDao.retornarObjApreendidoVeiculosAcaoPolicial();
        ListaObjApreendidosVeiculosAcaoPolicialFiltrado.addAll(ListaObjApreendidosVeiculosAcaoPolicial);
        ArrayAdapter<Objapreendidoveiculoacaopolicial> adaptadorVeiculo = new ArrayAdapter<Objapreendidoveiculoacaopolicial>(this, android.R.layout.simple_list_item_1, ListaObjApreendidosVeiculosAcaoPolicialFiltrado);
        LvVeiculosAcaoPolicial.setAdapter(adaptadorVeiculo);

        BtnArmasAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ObjetosApeendidosAcaoPolicialActivity.this, ArmasAcaoPolicialActivity.class));
            }
        });

        BtnDrogasAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ObjetosApeendidosAcaoPolicialActivity.this, DrogasAcaoPolicialActivity.class));
            }
        });

        BtnVeiculosAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ObjetosApeendidosAcaoPolicialActivity.this, CarrosAcaoPolicialActivity.class));
            }
        });
        BtnValidarObjetosAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_objetosapreendidosacaopolicial, menu);

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

    @Override
    public void onResume() {
        super.onResume();

        ListaObjApreendidosArmasAcaoPolicial = objapreendidoarmaacaopolicialDao.retornarObjApreendidoAcaoPolicial();
        ListaObjApreendidosArmasAcaoPolicialFiltrado.clear();
        ListaObjApreendidosArmasAcaoPolicialFiltrado.addAll(ListaObjApreendidosArmasAcaoPolicial);
        LvArmasAcaoPolicial.invalidateViews();

        ListaObjApreendidosDrogasAcaoPolicial = objapreendidodrogaacaopolicialDao.retornarObjApreendidoDrogaAcaoPolicial();
        ListaObjApreendidosDrogasAcaoPolicialFiltrado.clear();
        ListaObjApreendidosDrogasAcaoPolicialFiltrado.addAll(ListaObjApreendidosDrogasAcaoPolicial);
        LvDrogasAcaoPolicial.invalidateViews();

        ListaObjApreendidosVeiculosAcaoPolicial = objapreendidoveiculoacaopolicialDao.retornarObjApreendidoVeiculosAcaoPolicial();
        ListaObjApreendidosVeiculosAcaoPolicialFiltrado.clear();
        ListaObjApreendidosVeiculosAcaoPolicialFiltrado.addAll(ListaObjApreendidosVeiculosAcaoPolicial);
        LvVeiculosAcaoPolicial.invalidateViews();

    }

}
