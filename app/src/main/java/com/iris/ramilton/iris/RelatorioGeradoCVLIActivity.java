package com.iris.ramilton.iris;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.modelo.Cvli;

import org.w3c.dom.Text;

public class RelatorioGeradoCVLIActivity extends AppCompatActivity {

    private TextView TvMostrarRelatorioCVLI;
    private String receber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_gerado_cvli);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        TvMostrarRelatorioCVLI = (TextView) findViewById(R.id.tvMostarRelatorioCVLI);

        Intent it = getIntent();
        if(it.hasExtra("relatorioCvlis")){

            receber = (String) it.getSerializableExtra("relatorioCvlis");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //TvMostrarRelatorioCVLI.setText(Html.fromHtml(receber, Html.FROM_HTML_MODE_COMPACT));
            } else {
               // TvMostrarRelatorioCVLI.setText(Html.fromHtml(receber));
            }

            TvMostrarRelatorioCVLI.setText(receber);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_relatorio_cvli, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish(); // Finaliza a Activity atual e assim volta para a tela anterior
                break;
            case R.id.menucompartilhar:
                compartilhar();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void compartilhar(){
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
      //  share.setPackage("com.whatsapp");
        share.putExtra(Intent.EXTRA_TEXT, receber);
        startActivity(Intent.createChooser(share, "Compartilhar"));
    }
}
