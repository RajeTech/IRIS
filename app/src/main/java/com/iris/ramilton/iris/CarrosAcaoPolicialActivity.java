package com.iris.ramilton.iris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.ObjapreendidodrogaacaopolicialDao;
import com.iris.ramilton.iris.dao.ObjapreendidoveiculoacaopolicialDao;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.Objapreendidodrogaacaopolicial;
import com.iris.ramilton.iris.modelo.Objapreendidoveiculoacaopolicial;

public class CarrosAcaoPolicialActivity extends AppCompatActivity {

    private EditText EdtTipoCarroAcaoPolicial, EdtMarcaCarroAcaoPolicial, EdtModeloCarroAcaoPolicial, EdtPlacaOstentadaCarroAcaoPolicial, EdtCorCarroAcaoPolicial;
    private Button BtnNovoCarroAcaoPolicial, BtnFinalizarCarroCarroAcaoPolicial;
    private int cod, atualiza = 0;
    private Gerarnumeros gerarnumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carros_acao_policial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        EdtTipoCarroAcaoPolicial = (EditText) findViewById(R.id.edtTipoCarroAcaoPolcial);
        EdtMarcaCarroAcaoPolicial = (EditText) findViewById(R.id.edtMarcaCarroAcaoPolcial);
        EdtModeloCarroAcaoPolicial = (EditText) findViewById(R.id.edtModeloCarroAcaoPolicial);
        EdtPlacaOstentadaCarroAcaoPolicial = (EditText) findViewById(R.id.edtPlacaOstentadaCarroAcaoPolcial);
        EdtCorCarroAcaoPolicial = (EditText) findViewById(R.id.edtCorCarroAcaoPolcial);
        BtnNovoCarroAcaoPolicial = (Button) findViewById(R.id.btnIncluirNovoCarroAcaoPolicial);
        BtnFinalizarCarroCarroAcaoPolicial = (Button) findViewById(R.id.btnFinalizarCarrosApreendidosAcaoPolicial);

        gerarnumeros = new Gerarnumeros();
        Intent it = getIntent();
        if (it.hasExtra("codigoVeiculosAtualizar")) {
            cod = (int) it.getSerializableExtra("codigoVeiculosatualizar");

            atualiza = 1;

        }

        BtnNovoCarroAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objapreendidoveiculoacaopolicial objapreendidoveiculoacaopolicial = new Objapreendidoveiculoacaopolicial();
                ObjapreendidoveiculoacaopolicialDao objapreendidoveiculoacaopolicialDao = new ObjapreendidoveiculoacaopolicialDao(getApplicationContext());

                objapreendidoveiculoacaopolicial.setDsTipoVeiculoAcaoPolicial(EdtTipoCarroAcaoPolicial.getText().toString());
                objapreendidoveiculoacaopolicial.setDsMarcaVeiculoAcaoPolicial(EdtMarcaCarroAcaoPolicial.getText().toString());
                objapreendidoveiculoacaopolicial.setDsModeloVeiculoAcaoPolicial(EdtModeloCarroAcaoPolicial.getText().toString());
                objapreendidoveiculoacaopolicial.setDsPlacaOstentadaAcaoPolicial(EdtPlacaOstentadaCarroAcaoPolicial.getText().toString());
                objapreendidoveiculoacaopolicial.setDsCorVeiculoAcaoPolicial(EdtCorCarroAcaoPolicial.getText().toString());

                if (atualiza == 1) {

                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = objapreendidoveiculoacaopolicialDao.cadastrarVeiculosAcaoPolicialAtualiza(objapreendidoveiculoacaopolicial, cod, qualquer);
                    if (codigoequipeperito > 0) {
                        Toast.makeText(CarrosAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CarrosAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    EdtTipoCarroAcaoPolicial.setText("");
                    EdtMarcaCarroAcaoPolicial.setText("");
                    EdtModeloCarroAcaoPolicial.setText("");
                    EdtPlacaOstentadaCarroAcaoPolicial.setText("");
                    EdtCorCarroAcaoPolicial.setText("");
                }else{
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = objapreendidoveiculoacaopolicialDao.cadastrarVeiculoAcaoPolicial(objapreendidoveiculoacaopolicial, qualquer);
                    if (codigoequipeperito > 0) {
                        Toast.makeText(CarrosAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CarrosAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    EdtTipoCarroAcaoPolicial.setText("");
                    EdtMarcaCarroAcaoPolicial.setText("");
                    EdtModeloCarroAcaoPolicial.setText("");
                    EdtPlacaOstentadaCarroAcaoPolicial.setText("");
                    EdtCorCarroAcaoPolicial.setText("");
                }
            }
        });

        BtnFinalizarCarroCarroAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objapreendidoveiculoacaopolicial objapreendidoveiculoacaopolicial = new Objapreendidoveiculoacaopolicial();
                ObjapreendidoveiculoacaopolicialDao objapreendidoveiculoacaopolicialDao = new ObjapreendidoveiculoacaopolicialDao(getApplicationContext());

                objapreendidoveiculoacaopolicial.setDsTipoVeiculoAcaoPolicial(EdtTipoCarroAcaoPolicial.getText().toString());
                objapreendidoveiculoacaopolicial.setDsMarcaVeiculoAcaoPolicial(EdtMarcaCarroAcaoPolicial.getText().toString());
                objapreendidoveiculoacaopolicial.setDsModeloVeiculoAcaoPolicial(EdtModeloCarroAcaoPolicial.getText().toString());
                objapreendidoveiculoacaopolicial.setDsPlacaOstentadaAcaoPolicial(EdtPlacaOstentadaCarroAcaoPolicial.getText().toString());
                objapreendidoveiculoacaopolicial.setDsCorVeiculoAcaoPolicial(EdtCorCarroAcaoPolicial.getText().toString());

                if (atualiza == 1) {

                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = objapreendidoveiculoacaopolicialDao.cadastrarVeiculosAcaoPolicialAtualiza(objapreendidoveiculoacaopolicial, cod, qualquer);
                    if (codigoequipeperito > 0) {
                        Toast.makeText(CarrosAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CarrosAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else{
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoequipeperito = objapreendidoveiculoacaopolicialDao.cadastrarVeiculoAcaoPolicial(objapreendidoveiculoacaopolicial, qualquer);
                    if (codigoequipeperito > 0) {
                        Toast.makeText(CarrosAcaoPolicialActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CarrosAcaoPolicialActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carroacaopolicial, menu);

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
