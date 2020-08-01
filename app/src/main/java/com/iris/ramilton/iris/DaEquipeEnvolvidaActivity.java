package com.iris.ramilton.iris;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iris.ramilton.iris.dao.AcaoPolicialDao;
import com.iris.ramilton.iris.modelo.Acaopolicial;

public class DaEquipeEnvolvidaActivity extends AppCompatActivity {

    private Button BtnUnidadeResponsavelAcaoPolicial, BtnNEqLocaisAcaoPolicial, BtnNEqCoorpinAcaoPolicial, BtnNEqDepartamentosAcaoPolicial, BtnNEqOutrasInstituicoesAcaoPolicial, BtnRegistrarEqEnvolvidasAcaoPolicial;
    private Spinner SpOutrasEquipesInstituicaoAcaoPolicial;
    private EditText EdtUnidadeResponsavelAcaoPolicial, EdtNEqLocaisAcaoPolicial, EdtNEqCoorpinAcaoPolicial, EdtNEqDepartamentosAcaoPolicial, EdtNomeEquipeEspecializadaAcaoPolicial, EdtNEqPoliciaEspecializada, EdtNomeOutraInstituicao, EdtNOutrosEnvolvidos, EdtNEquipesGeraisAcaoPolicial;
    private ConstraintLayout ClInstituicoesGerais, ClPoliciaEspecializada, ClOutrasInstituicoes;
    private String OutrasInstituicoes, DsOutrasEquipesInstituicaoAcaoPolicial;
    private Acaopolicial acaopolicial, acaopolicialdaequipesematualizar ;
    private AcaoPolicialDao acaopolicialDao, acaoPolicialDaodaequipesematualizar;
    private Acaopolicial acaopolicialdaequipe = null;
    private int atualizar = 0, controleenvio = 0, codigorecebidosem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_equipe_envolvida);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        BtnUnidadeResponsavelAcaoPolicial = (Button) findViewById(R.id.btnUnidadeResponsavelEqEnvolvidaAcaoPolicial);
        BtnNEqLocaisAcaoPolicial = (Button) findViewById(R.id.btnNEqLocaisAcaoPolicial);
        BtnNEqCoorpinAcaoPolicial = (Button) findViewById(R.id.btnNEqLocalCoorpinAcaoPolicial);
        BtnNEqDepartamentosAcaoPolicial = (Button) findViewById(R.id.btnNEqOutrosDeptoAcaoPolicial);
        BtnNEqOutrasInstituicoesAcaoPolicial = (Button) findViewById(R.id.btnNEqOutrasInstAcaoPolicial);
        BtnRegistrarEqEnvolvidasAcaoPolicial = (Button) findViewById(R.id.btnRegistrarEquipeEnvolvidaAcaoPolicial);
        SpOutrasEquipesInstituicaoAcaoPolicial = (Spinner) findViewById(R.id.spOutrasInstituicoesAcaoPolicial);
        EdtUnidadeResponsavelAcaoPolicial = (EditText) findViewById(R.id.edtUnidadeResponsavelAcaoPolicial);
        EdtNEqLocaisAcaoPolicial = (EditText) findViewById(R.id.edtNEqLocaisAcaoPolicial);
        EdtNEqCoorpinAcaoPolicial = (EditText) findViewById(R.id.edtNEpOutrasUniCoorpinAcaoPolicial);
        EdtNEqDepartamentosAcaoPolicial = (EditText) findViewById(R.id.edtNEpOutrosDetpoCoorCoorpinAcaoPolicial);
        EdtNomeEquipeEspecializadaAcaoPolicial = (EditText) findViewById(R.id.edtNomeEquipeEspecializadaAcaoPolicial);
        EdtNEqPoliciaEspecializada = (EditText) findViewById(R.id.edtNEqPoliciaEspecializada);
        EdtNomeOutraInstituicao = (EditText) findViewById(R.id.edtNomeOutraInstituicao);
        EdtNOutrosEnvolvidos = (EditText) findViewById(R.id.edtNOutrosEnvolvidos);
        EdtNEquipesGeraisAcaoPolicial = (EditText) findViewById(R.id.edtNEquipesGeraisAcaoPolicial);
        ClInstituicoesGerais = (ConstraintLayout) findViewById(R.id.clInstituicoesGeraisAcaoPolicial);
        ClPoliciaEspecializada = (ConstraintLayout) findViewById(R.id.clPoliciaEspecializada);
        ClOutrasInstituicoes = (ConstraintLayout) findViewById(R.id.clOutrasInstituicoes);

        ArrayAdapter adaptadorSpOutrasInstituicoes = ArrayAdapter.createFromResource(this, R.array.OutraInstituicaoAcaoPolicial, android.R.layout.simple_spinner_item);
        SpOutrasEquipesInstituicaoAcaoPolicial.setAdapter(adaptadorSpOutrasInstituicoes);

        acaopolicialdaequipesematualizar = new Acaopolicial();
        acaoPolicialDaodaequipesematualizar = new AcaoPolicialDao(this);

        Intent it = getIntent();
        if (it.hasExtra("acaopolicialequipe")) {
            acaopolicialdaequipe = (Acaopolicial) it.getSerializableExtra("acaopolicialequipe");

            atualizar = 1;
            controleenvio = 4;

            EdtUnidadeResponsavelAcaoPolicial.setText(acaopolicialdaequipe.getDsUnidadeResponsavelAcaoPolicial());
            EdtNEqLocaisAcaoPolicial.setText(String.valueOf(acaopolicialdaequipe.getNEquipeLocaisAcaoPolicial()));
            EdtNEqCoorpinAcaoPolicial.setText(String.valueOf(acaopolicialdaequipe.getNEquipeUnidadeCoorpinAcaoPolicial()));
            EdtNEqDepartamentosAcaoPolicial.setText(String.valueOf(acaopolicialdaequipe.getNEquipeDetCoorCoorpinAcaoPolicial()));
            EdtNEquipesGeraisAcaoPolicial.setText(String.valueOf(acaopolicialdaequipe.getEdtNEquipesGeraisAcaoPolicial()));
            EdtNomeEquipeEspecializadaAcaoPolicial.setText(acaopolicialdaequipe.getDsNomeUnidadeEspAcaoPolicial());
            EdtNEqPoliciaEspecializada.setText(String.valueOf(acaopolicialdaequipe.getNEquipeEspAcaoPolicial()));
            EdtNomeOutraInstituicao.setText(acaopolicialdaequipe.getDsNomeOutraInstituicaoAcaoPolicial());
            EdtNOutrosEnvolvidos.setText(String.valueOf(acaopolicialdaequipe.getNEnvolvidosOutrasInstituicaoAcaoPolicial()));

        }else if(it.hasExtra("equipesematualizar")){

            codigorecebidosem = (int) it.getSerializableExtra("equipesematualizar");

            atualizar = 2;
            controleenvio = 4;

            acaopolicialdaequipesematualizar = acaoPolicialDaodaequipesematualizar.retornaAcaopolicialdaEquipeObj(codigorecebidosem);

            EdtUnidadeResponsavelAcaoPolicial.setText(acaopolicialdaequipesematualizar.getDsUnidadeResponsavelAcaoPolicial());
            EdtNEqLocaisAcaoPolicial.setText(String.valueOf(acaopolicialdaequipesematualizar.getNEquipeLocaisAcaoPolicial()));
            EdtNEqCoorpinAcaoPolicial.setText(String.valueOf(acaopolicialdaequipesematualizar.getNEquipeUnidadeCoorpinAcaoPolicial()));
            EdtNEqDepartamentosAcaoPolicial.setText(String.valueOf(acaopolicialdaequipesematualizar.getNEquipeDetCoorCoorpinAcaoPolicial()));
            EdtNEquipesGeraisAcaoPolicial.setText(String.valueOf(acaopolicialdaequipesematualizar.getEdtNEquipesGeraisAcaoPolicial()));
            EdtNomeEquipeEspecializadaAcaoPolicial.setText(acaopolicialdaequipesematualizar.getDsNomeUnidadeEspAcaoPolicial());
            EdtNEqPoliciaEspecializada.setText(String.valueOf(acaopolicialdaequipesematualizar.getNEquipeEspAcaoPolicial()));
            EdtNomeOutraInstituicao.setText(acaopolicialdaequipesematualizar.getDsNomeOutraInstituicaoAcaoPolicial());
            EdtNOutrosEnvolvidos.setText(String.valueOf(acaopolicialdaequipesematualizar.getNEnvolvidosOutrasInstituicaoAcaoPolicial()));

        }

        acaopolicial = new Acaopolicial();
        acaopolicialDao = new AcaoPolicialDao(this);

        EdtUnidadeResponsavelAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtNEqLocaisAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtNEqCoorpinAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        EdtNEqDepartamentosAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EdtNEquipesGeraisAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        EdtNomeEquipeEspecializadaAcaoPolicial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        EdtNEqPoliciaEspecializada.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        EdtNomeOutraInstituicao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        EdtNOutrosEnvolvidos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        BtnUnidadeResponsavelAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtUnidadeResponsavelAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        EdtUnidadeResponsavelAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnNEqLocaisAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        BtnNEqLocaisAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtNEqLocaisAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        EdtNEqLocaisAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnNEqCoorpinAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        BtnNEqCoorpinAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtNEqCoorpinAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(DaEquipeEnvolvidaActivity.this);
            }
        });

        EdtNEqCoorpinAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnNEqDepartamentosAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        BtnNEqDepartamentosAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdtNEqDepartamentosAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(DaEquipeEnvolvidaActivity.this);
            }
        });

        EdtNEqDepartamentosAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnNEqOutrasInstituicoesAcaoPolicial.setVisibility(View.VISIBLE);
            }
        });

        BtnNEqOutrasInstituicoesAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpOutrasEquipesInstituicaoAcaoPolicial.setVisibility(View.VISIBLE);
                esconderTeclado(DaEquipeEnvolvidaActivity.this);
                if (atualizar == 1) {
                    DsOutrasEquipesInstituicaoAcaoPolicial = acaopolicialdaequipe.getDsOutraInstituicaoAcaoPolicial();
                    if (DsOutrasEquipesInstituicaoAcaoPolicial == null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(0);
                        DsOutrasEquipesInstituicaoAcaoPolicial = SpOutrasEquipesInstituicaoAcaoPolicial.getItemAtPosition(0).toString();
                    }

                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Selecione...") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(0);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Polícia Militar/ Ordinário") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(1);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Polícia Militar/ Especializada") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(2);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Polícia Rodoviária Federal") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(3);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Polícia Federal") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(4);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Guarda Municipal") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(5);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("IBAMA") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(6);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Ministério Público") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(7);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Outras Instituíções") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(8);
                    }
                }else if(atualizar == 2){
                     DsOutrasEquipesInstituicaoAcaoPolicial = acaopolicialdaequipesematualizar.getDsOutraInstituicaoAcaoPolicial();

                    if (DsOutrasEquipesInstituicaoAcaoPolicial == null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(0);
                        DsOutrasEquipesInstituicaoAcaoPolicial = SpOutrasEquipesInstituicaoAcaoPolicial.getItemAtPosition(0).toString();
                    }

                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Selecione...") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(0);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Polícia Militar/ Ordinário") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(1);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Polícia Militar/ Especializada") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(2);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Polícia Rodoviária Federal") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(3);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Polícia Federal") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(4);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Guarda Municipal") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(5);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("IBAMA") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(6);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Ministério Público") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(7);
                    }
                    if (DsOutrasEquipesInstituicaoAcaoPolicial.equals("Outras Instituíções") && DsOutrasEquipesInstituicaoAcaoPolicial != null) {
                        SpOutrasEquipesInstituicaoAcaoPolicial.setSelection(8);
                    }
                }
            }
        });

        SpOutrasEquipesInstituicaoAcaoPolicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                OutrasInstituicoes = SpOutrasEquipesInstituicaoAcaoPolicial.getItemAtPosition(position).toString();

                switch (OutrasInstituicoes){
                    case "Polícia Militar/ Especializada":
                        ClPoliciaEspecializada.setVisibility(View.VISIBLE);
                        ClOutrasInstituicoes.setVisibility(View.INVISIBLE);
                        ClInstituicoesGerais.setVisibility(View.INVISIBLE);
                        EdtNOutrosEnvolvidos.setText("0");
                        EdtNEquipesGeraisAcaoPolicial.setText("0");
                        break;
                    case "Outras Instituições":
                        ClOutrasInstituicoes.setVisibility(View.VISIBLE);
                        ClPoliciaEspecializada.setVisibility(View.INVISIBLE);
                        ClInstituicoesGerais.setVisibility(View.INVISIBLE);
                        EdtNEqPoliciaEspecializada.setText("0");
                        EdtNEquipesGeraisAcaoPolicial.setText("0");
                        break;
                    case "Polícia Militar/ Ordinário":
                        ClPoliciaEspecializada.setVisibility(View.INVISIBLE);
                        ClOutrasInstituicoes.setVisibility(View.INVISIBLE);
                        ClInstituicoesGerais.setVisibility(View.VISIBLE);
                        EdtNEqPoliciaEspecializada.setText("0");
                        EdtNOutrosEnvolvidos.setText("0");
                    case "Polícia Rodoviária Federal":
                        ClPoliciaEspecializada.setVisibility(View.INVISIBLE);
                        ClOutrasInstituicoes.setVisibility(View.INVISIBLE);
                        ClInstituicoesGerais.setVisibility(View.VISIBLE);
                        EdtNEqPoliciaEspecializada.setText("0");
                        EdtNOutrosEnvolvidos.setText("0");
                    case "Polícia Federal":
                        ClPoliciaEspecializada.setVisibility(View.INVISIBLE);
                        ClOutrasInstituicoes.setVisibility(View.INVISIBLE);
                        ClInstituicoesGerais.setVisibility(View.VISIBLE);
                        EdtNEqPoliciaEspecializada.setText("0");
                        EdtNOutrosEnvolvidos.setText("0");
                    case "Guarda Municipal":
                        ClPoliciaEspecializada.setVisibility(View.INVISIBLE);
                        ClOutrasInstituicoes.setVisibility(View.INVISIBLE);
                        ClInstituicoesGerais.setVisibility(View.VISIBLE);
                        EdtNEqPoliciaEspecializada.setText("0");
                        EdtNOutrosEnvolvidos.setText("0");
                    case "Ministério Público":
                        ClPoliciaEspecializada.setVisibility(View.INVISIBLE);
                        ClOutrasInstituicoes.setVisibility(View.INVISIBLE);
                        ClInstituicoesGerais.setVisibility(View.VISIBLE);
                        EdtNEqPoliciaEspecializada.setText("0");
                        EdtNOutrosEnvolvidos.setText("0");
                    default:
                        ClInstituicoesGerais.setVisibility(View.VISIBLE);
                        ClPoliciaEspecializada.setVisibility(View.INVISIBLE);
                        ClPoliciaEspecializada.setVisibility(View.INVISIBLE);
                        EdtNEqPoliciaEspecializada.setText("0");
                        EdtNOutrosEnvolvidos.setText("0");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnRegistrarEqEnvolvidasAcaoPolicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acaopolicial.setDsUnidadeResponsavelAcaoPolicial(EdtUnidadeResponsavelAcaoPolicial.getText().toString());
                acaopolicial.setNEquipeLocaisAcaoPolicial(Integer.parseInt(EdtNEqLocaisAcaoPolicial.getText().toString()));
                acaopolicial.setNEquipeUnidadeCoorpinAcaoPolicial(Integer.parseInt(EdtNEqCoorpinAcaoPolicial.getText().toString()));
                acaopolicial.setNEquipeDetCoorCoorpinAcaoPolicial(Integer.parseInt(EdtNEqDepartamentosAcaoPolicial.getText().toString()));
                acaopolicial.setDsOutraInstituicaoAcaoPolicial(DsOutrasEquipesInstituicaoAcaoPolicial);
                acaopolicial.setEdtNEquipesGeraisAcaoPolicial(Integer.parseInt(EdtNEquipesGeraisAcaoPolicial.getText().toString()));
                acaopolicial.setDsNomeUnidadeEspAcaoPolicial(EdtNomeEquipeEspecializadaAcaoPolicial.getText().toString());
                acaopolicial.setNEquipeEspAcaoPolicial(Integer.parseInt(EdtNEqPoliciaEspecializada.getText().toString()));
                acaopolicial.setDsNomeOutraInstituicaoAcaoPolicial(EdtNomeOutraInstituicao.getText().toString());
                acaopolicial.setNEnvolvidosOutrasInstituicaoAcaoPolicial(Integer.parseInt(EdtNOutrosEnvolvidos.getText().toString()));

                int codigocvli = acaopolicialDao.retornarCodigoAcaoPolicialSemParametro();

                if (atualizar == 1) {
                    long certo = acaopolicialDao.AtualizarAcaoPolicialDaEquipe(acaopolicial, acaopolicialdaequipe.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(DaEquipeEnvolvidaActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DaEquipeEnvolvidaActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else if(atualizar == 2) {
                    long certo = acaopolicialDao.AtualizarAcaoPolicialDaEquipe(acaopolicial, acaopolicialdaequipesematualizar.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(DaEquipeEnvolvidaActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DaEquipeEnvolvidaActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else{
                    long certo = acaopolicialDao.cadastrarAcaoPolicialDaEquipe(acaopolicial, codigocvli);
                    if (certo > 0) {
                        Toast.makeText(DaEquipeEnvolvidaActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DaEquipeEnvolvidaActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daequipeenvolvidaacaopolicial, menu);

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

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
