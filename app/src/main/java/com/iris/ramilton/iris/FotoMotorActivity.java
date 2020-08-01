package com.iris.ramilton.iris;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.VistoriaDao;
import com.iris.ramilton.iris.modelo.Vistoria;

import java.io.ByteArrayOutputStream;

public class FotoMotorActivity extends AppCompatActivity {

    private ImageView IVFotoMotor;
    private Button BtnTirarFotoMotor, BtnProximoPassoMotor;
    private int codigorecebido;
    private Vistoria vistoria;
    private VistoriaDao vistoriaDao;
    private Bitmap imagemBitmap;
    private int clique = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_motor);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        IVFotoMotor = (ImageView) findViewById(R.id.ivFotoMotor);
        BtnTirarFotoMotor = (Button) findViewById(R.id.btnFotoMotor);
        BtnProximoPassoMotor = (Button) findViewById(R.id.btnProximoPassoVidro);

        vistoria = new Vistoria();
        vistoriaDao = new VistoriaDao(this);

        Intent it = getIntent();

        if(it.hasExtra("codvistoria")){
            codigorecebido = (int) it.getSerializableExtra("codvistoria");
            clique = 1;
            if(vistoriaDao.verificarFotoMotor(codigorecebido)){
                IVFotoMotor.setImageBitmap(vistoriaDao.retornaFotomotor(codigorecebido));
            }
        }

        BtnTirarFotoMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clique =0;
                tirarFoto();
            }
        });

        BtnProximoPassoMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(clique == 0){
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imagemBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                    byte imagemBytes[] = stream.toByteArray();

                    vistoria.setFotoFundo(imagemBytes);
                    vistoria.setControle(2);

                    long certo = vistoriaDao.AtualizarVistoriaFotoMotor(vistoria,codigorecebido);
                    if(certo > 0){
                        Toast.makeText(FotoMotorActivity.this, "Vistoria Atualizada!!!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(FotoMotorActivity.this, "Erro ao Vistoriar!!!", Toast.LENGTH_SHORT).show();
                    }
                    Intent it = new Intent(FotoMotorActivity.this, FotoVidroActivity.class);
                    it.putExtra("codvistoria", codigorecebido);
                    startActivity(it);
                    finish();
                }else{
                    clique = 1;
                    Intent it = new Intent(FotoMotorActivity.this, FotoVidroActivity.class);
                    it.putExtra("codvistoria", codigorecebido);
                    startActivity(it);
                    finish();

                }

            }
        });
    }

    public void tirarFoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== 1 && resultCode== RESULT_OK) {
            Bundle extras = data.getExtras();
            imagemBitmap =(Bitmap) extras.get("data");
            IVFotoMotor.setImageBitmap(imagemBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foto_motor, menu);

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
