package com.iris.ramilton.iris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

public class ConsultasActivity extends AppCompatActivity {

    private TextView TvTituloRelatorios;
    private Button BtnRelatoriosCVLI, BtnRelatorioGerencial, BtnRelatorioDepin, BtnRelatorioDepom, BtnRelatorioCapital, BtnRelatorioEstado, BtnRelatorioRispMetropolitana;
    private Button BtnRelatorioRispsul, BtnRelatorioRispAtlantico, BtnRelatorioRispBPS, BtnRelatorioRispCentral, BtnRelatorioRispChapada, BtnRelatorioRispLeste, BtnRelatorioRispNorte, BtnRelatorioRispOeste, BtnRelatorioRispSudoeste;
    private int clique = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        TvTituloRelatorios = (TextView) findViewById(R.id.tvTituloRelatorios);
        BtnRelatoriosCVLI = (Button) findViewById(R.id.btnRelatorioCVLI);
        BtnRelatorioDepin = (Button) findViewById(R.id.btnRelatorioDepin);
        BtnRelatorioDepom = (Button) findViewById(R.id.btnRelatórioDEPOM);
        BtnRelatorioEstado = (Button) findViewById(R.id.btnRelatorioEstado);
        BtnRelatorioCapital = (Button) findViewById(R.id.btnRelatorioCapital);
        BtnRelatorioGerencial = (Button) findViewById(R.id.btnRelatorioGerencial);
        BtnRelatorioRispsul = (Button) findViewById(R.id.btnRISPSUL);
        BtnRelatorioRispAtlantico = (Button) findViewById(R.id.btnRispAtlantico);
        BtnRelatorioRispBPS = (Button) findViewById(R.id.btnRispBPS);
        BtnRelatorioRispCentral = (Button) findViewById(R.id.btnRispCentral);
        BtnRelatorioRispChapada = (Button) findViewById(R.id.btnRispchapada);
        BtnRelatorioRispLeste = (Button) findViewById(R.id.btnRispleste);
        BtnRelatorioRispNorte = (Button) findViewById(R.id.btnRispnorte);
        BtnRelatorioRispOeste = (Button) findViewById(R.id.btnRispOeste);
        BtnRelatorioRispSudoeste = (Button) findViewById(R.id.btnRispSudoeste);
        BtnRelatorioRispMetropolitana = (Button) findViewById(R.id.btnRelatorioRispMetropolitana);


        BtnRelatoriosCVLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioCVLIActivity.class));

            }
        });

        BtnRelatorioGerencial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(clique == 0) {
                    BtnRelatorioRispsul.setVisibility(View.VISIBLE);
                    //BtnRelatorioRispAtlantico.setVisibility(View.VISIBLE);
                    //BtnRelatorioRispBPS.setVisibility(View.VISIBLE);
                    //BtnRelatorioRispCentral.setVisibility(View.VISIBLE);
                    BtnRelatorioRispChapada.setVisibility(View.VISIBLE);
                    BtnRelatorioRispLeste.setVisibility(View.VISIBLE);
                    BtnRelatorioRispNorte.setVisibility(View.VISIBLE);
                    BtnRelatorioRispOeste.setVisibility(View.VISIBLE);
                    BtnRelatorioRispSudoeste.setVisibility(View.VISIBLE);
                    BtnRelatorioRispMetropolitana.setVisibility(View.VISIBLE);
                    clique = 1;
                }else{
                    BtnRelatorioRispsul.setVisibility(View.GONE);
                    //BtnRelatorioRispAtlantico.setVisibility(View.GONE);
                    //BtnRelatorioRispBPS.setVisibility(View.GONE);
                    //BtnRelatorioRispCentral.setVisibility(View.GONE);
                    BtnRelatorioRispChapada.setVisibility(View.GONE);
                    BtnRelatorioRispLeste.setVisibility(View.GONE);
                    BtnRelatorioRispNorte.setVisibility(View.GONE);
                    BtnRelatorioRispOeste.setVisibility(View.GONE);
                    BtnRelatorioRispSudoeste.setVisibility(View.GONE);
                    BtnRelatorioRispMetropolitana.setVisibility(View.GONE);
                    clique = 0;
                }


            }
        });

        BtnRelatorioRispAtlantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioRispAtlantico.class));
            }
        });

        BtnRelatorioRispBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioRispBPS.class));
            }
        });

        BtnRelatorioRispCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioRispCentral.class));
            }
        });

        BtnRelatorioRispChapada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioRispChapada.class));
            }
        });

        BtnRelatorioRispLeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioRispLeste.class));
            }
        });

        BtnRelatorioRispNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioRispNorte.class));
            }
        });

        BtnRelatorioRispOeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioRispOeste.class));
            }
        });

        BtnRelatorioRispSudoeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioRispSudoeste.class));
            }
        });

        BtnRelatorioRispsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioRispSul.class));
            }
        });

        BtnRelatorioDepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultasActivity.this, RelatorioDepin.class));
            }
        });

        BtnRelatorioDepom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(ConsultasActivity.this, RelatorioDepom.class));
            }
        });

        BtnRelatorioCapital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ConsultasActivity.this, RelatorioCapital.class));
            }
        });

        BtnRelatorioEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(ConsultasActivity.this, RelatorioEstado.class));
            }
        });

        BtnRelatorioRispMetropolitana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(ConsultasActivity.this, RelatorioRispMetropolitana.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_consultas, menu);

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
