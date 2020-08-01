package com.iris.ramilton.iris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class PrincipalActivity extends AppCompatActivity {

    private Button Btninformacao, Btncvli, BtnRelatoriosGerais, BtnAcoesPoliciais, BtnSincronizar, BtnVistoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Btncvli = (Button) findViewById(R.id.buttonCVLIID);
        BtnRelatoriosGerais = (Button) findViewById(R.id.btnRelatoriosGerais);
        BtnAcoesPoliciais = (Button) findViewById(R.id.btnAcoesPoliciais);
        Btninformacao = (Button) findViewById(R.id.buttonInformacaoID);
        BtnSincronizar = (Button) findViewById(R.id.btnSincronizacao);
        BtnVistoria = (Button) findViewById(R.id.btnVistoria);


        Btncvli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrincipalActivity.this, CvliActivity.class));
            }
        });

        BtnVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrincipalActivity.this, VistoriaActivity.class));
            }
        });

        BtnRelatoriosGerais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrincipalActivity.this, ConsultasActivity.class));
            }
        });

        BtnAcoesPoliciais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrincipalActivity.this, AcaoPolicialActivity.class));
            }
        });

        Btninformacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrincipalActivity.this, InformacoesActivity.class));
            }
        });

        BtnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PrincipalActivity.this, sincronizacao.class));


            }
        });

    }

}
