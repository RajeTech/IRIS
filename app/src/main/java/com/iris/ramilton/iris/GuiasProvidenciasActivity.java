package com.iris.ramilton.iris;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.iris.ramilton.iris.dao.GuiaPericiaProvidenciaDao;
import com.iris.ramilton.iris.modelo.Gerarnumeros;
import com.iris.ramilton.iris.modelo.GuiapericiaProvidencia;

public class GuiasProvidenciasActivity extends AppCompatActivity {

    private Spinner SpGuiaPericia;
    private EditText EdtNGuiaPericia;
    private Button BtnIncluirNovoGuiaPericia, BtnConcluirGuiaPericia;
    private String guiapericia;
    private int cod, atualiza = 0;
    private Gerarnumeros gerarnumeros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guias_providencias);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        SpGuiaPericia = (Spinner) findViewById(R.id.spGuiaparaPericia);
        EdtNGuiaPericia = (EditText) findViewById(R.id.edtNGuiasPericia);
        BtnIncluirNovoGuiaPericia = (Button) findViewById(R.id.btnIncluirNovoGuiaPericia);
        BtnConcluirGuiaPericia = (Button) findViewById(R.id.btnConcluirGuiaPericia);

        gerarnumeros = new Gerarnumeros();
        Intent it = getIntent();

        if (it.hasExtra("codigoGuiaProvidencias")) {

            cod = (int) it.getSerializableExtra("codigoGuiaProvidencias");

            atualiza = 1;
        }

        SimpleMaskFormatter mascaraguiaspericiais = new SimpleMaskFormatter("NNN/NNNN");
        MaskTextWatcher maskguiaspericiais = new MaskTextWatcher(EdtNGuiaPericia,mascaraguiaspericiais);
        EdtNGuiaPericia.addTextChangedListener(maskguiaspericiais);

        ArrayAdapter adaptadorSpGuiaPericia = ArrayAdapter.createFromResource(this, R.array.Guiaparapericia, android.R.layout.simple_spinner_item);
        SpGuiaPericia.setAdapter(adaptadorSpGuiaPericia);

        SpGuiaPericia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                guiapericia = SpGuiaPericia.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnIncluirNovoGuiaPericia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GuiapericiaProvidencia guiapericiaProvidencia = new GuiapericiaProvidencia();
                GuiaPericiaProvidenciaDao guiaPericiaProvidenciaDao = new GuiaPericiaProvidenciaDao(getApplicationContext());


                guiapericiaProvidencia.setDsGuiaPericial(guiapericia);
                guiapericiaProvidencia.setDsNGuiaPericial(EdtNGuiaPericia.getText().toString());


                if (atualiza == 1) {

                    if (EdtNGuiaPericia.getText().toString().equals("")) {
                        AlertDialog.Builder b = new AlertDialog.Builder(GuiasProvidenciasActivity.this);
                        b.setTitle("IRIS - Atenção!!!")
                                .setMessage("O campo Nº da Guia não pode ficar em branco")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EdtNGuiaPericia.setFocusable(true);
                                    }
                                }).create();
                        b.show();
                    } else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoguiapericiaprovidencia = guiaPericiaProvidenciaDao.cadastrarGuiaPericialProvidenciaAtualiza(guiapericiaProvidencia, cod, qualquer);
                        if (codigoguiapericiaprovidencia > 0) {
                            Toast.makeText(GuiasProvidenciasActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GuiasProvidenciasActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        SpGuiaPericia.setSelection(0);
                        EdtNGuiaPericia.setText("");
                        EdtNGuiaPericia.setFocusable(true);
                    }
                } else if (EdtNGuiaPericia.getText().toString().equals("")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(GuiasProvidenciasActivity.this);
                    b.setTitle("IRIS - Atenção!!!")
                            .setMessage("O campo Nº da Guia não pode ficar em branco")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtNGuiaPericia.setFocusable(true);
                                }
                            }).create();
                    b.show();
                } else {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoguiapericialpreservacao = guiaPericiaProvidenciaDao.cadastrarGuiaPericialProvidencia(guiapericiaProvidencia, qualquer);
                    if (codigoguiapericialpreservacao > 0) {
                        Toast.makeText(GuiasProvidenciasActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GuiasProvidenciasActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    SpGuiaPericia.setSelection(0);
                    EdtNGuiaPericia.setText("");
                    EdtNGuiaPericia.setFocusable(true);
                }

            }
        });

        BtnConcluirGuiaPericia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GuiapericiaProvidencia guiapericiaProvidencia = new GuiapericiaProvidencia();
                GuiaPericiaProvidenciaDao guiaPericiaProvidenciaDao = new GuiaPericiaProvidenciaDao(getApplicationContext());

                guiapericiaProvidencia.setDsGuiaPericial(guiapericia);
                guiapericiaProvidencia.setDsNGuiaPericial(EdtNGuiaPericia.getText().toString());

                if (atualiza == 1) {

                    if (EdtNGuiaPericia.getText().toString().equals("")) {
                        AlertDialog.Builder b = new AlertDialog.Builder(GuiasProvidenciasActivity.this);
                        b.setTitle("IRIS - Atenção!!!")
                                .setMessage("O campo Nº da Guia não pode ficar em branco")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EdtNGuiaPericia.setFocusable(true);
                                    }
                                }).create();
                        b.show();
                    } else {
                        String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                        long codigoguiapericiaprovidencia = guiaPericiaProvidenciaDao.cadastrarGuiaPericialProvidenciaAtualiza(guiapericiaProvidencia, cod, qualquer);
                        if (codigoguiapericiaprovidencia > 0) {
                            Toast.makeText(GuiasProvidenciasActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GuiasProvidenciasActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                } else if (EdtNGuiaPericia.getText().toString().equals("")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(GuiasProvidenciasActivity.this);
                    b.setTitle("IRIS - Atenção!!!")
                            .setMessage("O campo Nº da Guia não pode ficar em branco")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EdtNGuiaPericia.setFocusable(true);
                                }
                            }).create();
                    b.show();
                } else {
                    String qualquer = gerarnumeros.RetornarNumeroTabelaCVLI(getBaseContext());
                    long codigoguiapericialpreservacao = guiaPericiaProvidenciaDao.cadastrarGuiaPericialProvidencia(guiapericiaProvidencia, qualquer);
                    if (codigoguiapericialpreservacao > 0) {
                        Toast.makeText(GuiasProvidenciasActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GuiasProvidenciasActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guiaspericiais, menu);

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
