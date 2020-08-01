package com.iris.ramilton.iris;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import java.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.iris.ramilton.iris.Adaptador.ExpandablelistviewAdapter;
import com.iris.ramilton.iris.dao.CarroDao;
import com.iris.ramilton.iris.dao.CvliDao;
import com.iris.ramilton.iris.dao.MunicipioDao;
import com.iris.ramilton.iris.modelo.Carro;
import com.iris.ramilton.iris.modelo.Cvli;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import static java.security.AccessController.getContext;


public class FatoActivity extends AppCompatActivity {

    private ListView LvCarros;
    private List<String> listGroup;
    private HashMap<String, List<String>> listData;
    private Spinner Natureza, Logradouro, DistVilPovo, Estado, Zona, DetalhamentoLocal, MotivacaoCrime, SpMunicipioFato;
    private EditText DataFato, HorarioFato, RuaLogradouroFato, NLogradouroFato, ReferenciaLocalFato, BairroFato, DisVilPovoFato, DetalhamentoOutroFato;
    private Button BtnNaturezaFato, BtnDataFato, BtnHorarioFato, BtnMotivacaoFato, BtnEnderecoFato, BtnMeioEmpregadoFato, BtnCarroFato, BtnSalvarFato;
    private CheckBox CbNaoInformadoDataFato, CbNaoInformadoHorarioFato, CbTortura;
    private TextView TvLogradouroFato, TvNFato, TvReferenciaLocalFato, TvBairroFato, TvDisVilPovoFato, TvMunicipioFato, TvZonaFato, TvDetalhamentolocalFato, TvDetalhamentoOutro;
    private CheckBox CbAfogamento, CbAsfixia, CbAtropelamento, CbDisparoArmaFogo, CbEmpurraoAltura, CbEmpurraoSobVeiculo, CbEvenenamento, CbEsganadura, CbEstrangulamento, CbGolpeFacas, CbGolpeFacao, CbIncendio;
    private CheckBox CbOmissaoSocorro, CbPauladas, CbPedradas, CbPerfuracao, CbQueimadura, CbQueimaduraAcido, CbSocosPontapes, CbOutrosNaoRelacionadosMeioEmpregado, CbNaoIdentificadoMeioEmpregado;
    private RadioGroup RgEnderecoFato;
    private RadioButton RbEscolhidoEnderecoFato;
    String NaturezaFato, MotivacaoCrimeFato, LogradouroFato, DistVilPovoFato, EstadoFato, ZonaFato, DetalhamentoLocalFato, MotivacaoFato, MunicipioFato;
    int NaoInformadoDataFato, NaoInformadoHorarioFato, Afogamento, Asfixia, Atropelamento, DisparoArmaFoto, EmpurraoALtura, Evenenamento, Esganadura, Estrangulamento, GolpeFaca, GolpeFacao, Incendio;
    int EmpurraoSobVeiculo, OmissaoSocorro, Pauladas, Pedradas, Perfuracao, Queimaduras, QueimaduraAcido, SocosPontapes, OutrosNaoRelacionados, Naoidentificado;
    int EnderecoNDefinidoFato = 0, EnderecoDefinidoFato = 0, codigorecebidosem, Tortura;
    private Cvli cvli, cvlifatosematualizar;
    private CarroDao carroDao;
    private CvliDao cvliDao, cvlifatosematualizarDao;
    private List<Carro> ListaCarros;
    private List<Carro> ListaCarrosFiltrados = new ArrayList<>();
    private List<Carro> ListaCarrosAtualizar;
    private List<Carro> ListaCarrosAtualizarFiltrados = new ArrayList<>();
    private MunicipioDao municipioDao;
    ArrayList<String> municipiolista;

    private Cvli cvlifato = null;
    private int atualizar = 0, controleenvio = 0;
    DatePickerDialog.OnDateSetListener date;
    Calendar c;
    DatePickerDialog dpd;

    Timer t;
    TimePickerDialog tpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        SpMunicipioFato = (Spinner) findViewById(R.id.spMunicipioFato);
        RgEnderecoFato = (RadioGroup) findViewById(R.id.rgEnderecoFato);
        LvCarros = (ListView) findViewById(R.id.lvCarro);
        Natureza = (Spinner) findViewById(R.id.spinnerNatureza);
        DataFato = (EditText) findViewById(R.id.edtData);
        HorarioFato = (EditText) findViewById(R.id.edtHorario);
        BtnNaturezaFato = (Button) findViewById(R.id.btnNaturezaFato);
        BtnDataFato = (Button) findViewById(R.id.btnDataFato);
        CbNaoInformadoDataFato = (CheckBox) findViewById(R.id.cbIndefinidoDataFato);
        BtnHorarioFato = (Button) findViewById(R.id.btnHorarioFato);
        CbNaoInformadoHorarioFato = (CheckBox) findViewById(R.id.cbindefinidohorariofato);
        BtnMotivacaoFato = (Button) findViewById(R.id.btnMotivacao);
        BtnEnderecoFato = (Button) findViewById(R.id.BtnEndereco);
        BtnMeioEmpregadoFato = (Button) findViewById(R.id.btnMeioEmpregado);
        BtnCarroFato = (Button) findViewById(R.id.btnCarroFato);
        BtnSalvarFato = (Button) findViewById(R.id.btnRegistarFato);
        CbAfogamento = (CheckBox) findViewById(R.id.cbAfogamento);
        CbAsfixia = (CheckBox) findViewById(R.id.cbAsfixia);
        CbAtropelamento = (CheckBox) findViewById(R.id.cbAtropelamento);
        CbDisparoArmaFogo = (CheckBox) findViewById(R.id.cbDisparoArma);
        CbEmpurraoAltura = (CheckBox) findViewById(R.id.cbEmpurraoAltura);
        CbEmpurraoSobVeiculo = (CheckBox) findViewById(R.id.cbEmpurraoCarro);
        CbEvenenamento = (CheckBox) findViewById(R.id.cbEvenenamento);
        CbEsganadura = (CheckBox) findViewById(R.id.cbEsganadura);
        CbEstrangulamento = (CheckBox) findViewById(R.id.cbEstrangulamento);
        CbGolpeFacas = (CheckBox) findViewById(R.id.cbGolpeFaca);
        CbGolpeFacao = (CheckBox) findViewById(R.id.cbGolpeFacao);
        CbIncendio = (CheckBox) findViewById(R.id.cbIncendio);
        CbOmissaoSocorro = (CheckBox) findViewById(R.id.cbOmissaoSocorro);
        CbPauladas = (CheckBox) findViewById(R.id.cbPauladas);
        CbTortura = (CheckBox) findViewById(R.id.cbTortura);
        CbPedradas = (CheckBox) findViewById(R.id.cbPedradas);
        CbPerfuracao = (CheckBox) findViewById(R.id.cbPerfuracoes);
        CbQueimadura = (CheckBox) findViewById(R.id.cbQueimaduras);
        CbQueimaduraAcido = (CheckBox) findViewById(R.id.cbQueimaduraAcos);
        CbSocosPontapes = (CheckBox) findViewById(R.id.cbSocosPontapes);
        CbOutrosNaoRelacionadosMeioEmpregado = (CheckBox) findViewById(R.id.cbOutrosNaoRelacionadosMeioEmpregado);
        CbNaoIdentificadoMeioEmpregado = (CheckBox) findViewById(R.id.cbNaoIdentificadoMeioEmpregado);
        MotivacaoCrime = (Spinner) findViewById(R.id.spinnerMotivacaoFato);

        Logradouro = (Spinner) findViewById(R.id.spinnerLogradouroFato);
        DistVilPovo = (Spinner) findViewById(R.id.spinnerDisVilPovoFato);
        Estado = (Spinner) findViewById(R.id.spinnerEstadoFato);
        Zona = (Spinner) findViewById(R.id.spinnerZonaFato);
        DetalhamentoLocal = (Spinner) findViewById(R.id.spinnerDetalhamentoLocalFato);

        TvLogradouroFato = (TextView) findViewById(R.id.tvLougradouroFato);
        TvNFato = (TextView) findViewById(R.id.tvNLogradouroFato);
        TvReferenciaLocalFato = (TextView) findViewById(R.id.tvReferenciaLocalFato);
        TvBairroFato = (TextView) findViewById(R.id.tvBairroFato);
        TvDisVilPovoFato = (TextView) findViewById(R.id.tvDisVilPovoFato);
        TvMunicipioFato = (TextView) findViewById(R.id.tvMunicipioFatoCvli);
        TvZonaFato = (TextView) findViewById(R.id.tvZonaFato);
        TvDetalhamentolocalFato = (TextView) findViewById(R.id.tvDetalhamentoLocalFato);
        TvDetalhamentoOutro = (TextView) findViewById(R.id.tvDetalhamentoOutroFato);

        RuaLogradouroFato = (EditText) findViewById(R.id.edtLogradouroFato);
        NLogradouroFato = (EditText) findViewById(R.id.edtNLogradouroFato);
        ReferenciaLocalFato = (EditText) findViewById(R.id.edtReferenciaLocalFato);
        BairroFato = (EditText) findViewById(R.id.edtBairroFato);
        DisVilPovoFato = (EditText) findViewById(R.id.edtDisVilPovoFato);
        DetalhamentoOutroFato = (EditText) findViewById(R.id.edtDetalhamentoOutroFato);


        cvlifatosematualizar = new Cvli();
        cvlifatosematualizarDao = new CvliDao(this);

        municipioDao = new MunicipioDao(this);

        Intent it = getIntent();
        if (it.hasExtra("fato")) {
            cvlifato = (Cvli) it.getSerializableExtra("fato");

            atualizar = 1;
            controleenvio = 4;

            DataFato.setText(cvlifato.getDtFato());
            HorarioFato.setText(cvlifato.getHsFato());

            if (cvlifato.getCkbDataFatoIndefinido() == 0) {
                CbNaoInformadoDataFato.setChecked(false);
            } else {
                CbNaoInformadoDataFato.setChecked(true);
            }

            if (cvlifato.getCkbHorarioFatoIndefinido() == 0) {
                CbNaoInformadoHorarioFato.setChecked(false);
            } else {
                CbNaoInformadoHorarioFato.setChecked(true);
            }

            if (cvlifato.getCbkTortura() == 0) {
                CbTortura.setChecked(false);
            } else {
                CbTortura.setChecked(true);
            }

            if (cvlifato.getCbkAfogamentoFato() == 0) {
                CbAfogamento.setChecked(false);
            } else {
                CbAfogamento.setChecked(true);
            }

            if (cvlifato.getCbkAsfixiaFato() == 0) {
                CbAsfixia.setChecked(false);
            } else {
                CbAsfixia.setChecked(true);
            }

            if (cvlifato.getCbkTropelamentoFato() == 0) {
                CbAtropelamento.setChecked(false);
            } else {
                CbAtropelamento.setChecked(true);
            }

            if (cvlifato.getCbkDisparoArmaFato() == 0) {
                CbDisparoArmaFogo.setChecked(false);
            } else {
                CbDisparoArmaFogo.setChecked(true);
            }

            if (cvlifato.getCbkEmpurraoAlturaFato() == 0) {
                CbEmpurraoAltura.setChecked(false);
            } else {
                CbEmpurraoAltura.setChecked(true);
            }

            if (cvlifato.getCbkEmpurraoSobVeiculoFato() == 0) {
                CbEmpurraoSobVeiculo.setChecked(false);
            } else {
                CbEmpurraoSobVeiculo.setChecked(true);
            }

            if (cvlifato.getCbkEvenenadoFato() == 0) {
                CbEvenenamento.setChecked(false);
            } else {
                CbEvenenamento.setChecked(true);
            }

            if (cvlifato.getCbkEstanaduraFato() == 0) {
                CbEsganadura.setChecked(false);
            } else {
                CbEsganadura.setChecked(true);
            }

            if (cvlifato.getCbkEstrangulamentoFato() == 0) {
                CbEstrangulamento.setChecked(false);
            } else {
                CbEstrangulamento.setChecked(true);
            }

            if (cvlifato.getCbkGolpeFacaFato() == 0) {
                CbGolpeFacas.setChecked(false);
            } else {
                CbGolpeFacas.setChecked(true);
            }

            if (cvlifato.getCbkGolpeFacaoFato() == 0) {
                CbGolpeFacao.setChecked(false);
            } else {
                CbGolpeFacao.setChecked(true);
            }

            if (cvlifato.getCbkIncendioFato() == 0) {
                CbIncendio.setChecked(false);
            } else {
                CbIncendio.setChecked(true);
            }

            if (cvlifato.getCbkOmissaoSocorroFato() == 0) {
                CbOmissaoSocorro.setChecked(false);
            } else {
                CbOmissaoSocorro.setChecked(true);
            }

            if (cvlifato.getCbkPauladaFato() == 0) {
                CbPauladas.setChecked(false);
            } else {
                CbPauladas.setChecked(true);
            }

            if (cvlifato.getCbkPedradaFato() == 0) {
                CbPedradas.setChecked(false);
            } else {
                CbPedradas.setChecked(true);
            }

            if (cvlifato.getCbkPerfuracoesFato() == 0) {
                CbPerfuracao.setChecked(false);
            } else {
                CbPerfuracao.setChecked(true);
            }

            if (cvlifato.getCbkQueimaduraFato() == 0) {
                CbQueimadura.setChecked(false);
            } else {
                CbQueimadura.setChecked(true);
            }

            if (cvlifato.getCbkQueimarudasAcidoFato() == 0) {
                CbQueimaduraAcido.setChecked(false);
            } else {
                CbQueimaduraAcido.setChecked(true);
            }

            if (cvlifato.getCbkSocosPontapesFato() == 0) {
                CbSocosPontapes.setChecked(false);
            } else {
                CbSocosPontapes.setChecked(true);
            }

            if (cvlifato.getCbkOutrosNaoRelacionadoFato() == 0) {
                CbOutrosNaoRelacionadosMeioEmpregado.setChecked(false);
            } else {
                CbOutrosNaoRelacionadosMeioEmpregado.setChecked(true);
            }

            if (cvlifato.getCbkNaoIdentificadoFato() == 0) {
                CbNaoIdentificadoMeioEmpregado.setChecked(false);
            } else {
                CbNaoIdentificadoMeioEmpregado.setChecked(true);
            }

            RuaLogradouroFato.setText(cvlifato.getDsRuaFato());
            NLogradouroFato.setText(cvlifato.getDsNRuaFato());
            ReferenciaLocalFato.setText(cvlifato.getDsReferenciaLocalFato());
            BairroFato.setText(cvlifato.getDsBairroFato());
            DisVilPovoFato.setText(cvlifato.getDsDistVilPovoFato());
            DetalhamentoOutroFato.setText(cvlifato.getDsOutroDetalhamento());

        }else if(it.hasExtra("fatosematualizar")){

            codigorecebidosem = (int) it.getSerializableExtra("fatosematualizar");

            atualizar = 2;
            controleenvio = 4;

            cvlifatosematualizar = cvlifatosematualizarDao.retornaCVLIFatoObj(codigorecebidosem);

            DataFato.setText(cvlifatosematualizar.getDtFato());

            HorarioFato.setText(cvlifatosematualizar.getHsFato());

            if (cvlifatosematualizar.getCkbDataFatoIndefinido() == 0) {
                CbNaoInformadoDataFato.setChecked(false);
            } else {
                CbNaoInformadoDataFato.setChecked(true);
            }

            if (cvlifatosematualizar.getCkbHorarioFatoIndefinido() == 0) {
                CbNaoInformadoHorarioFato.setChecked(false);
            } else {
                CbNaoInformadoHorarioFato.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkTortura() == 0) {
                CbTortura.setChecked(false);
            } else {
                CbTortura.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkAfogamentoFato() == 0) {
                CbAfogamento.setChecked(false);
            } else {
                CbAfogamento.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkAsfixiaFato() == 0) {
                CbAsfixia.setChecked(false);
            } else {
                CbAsfixia.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkTropelamentoFato() == 0) {
                CbAtropelamento.setChecked(false);
            } else {
                CbAtropelamento.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkDisparoArmaFato() == 0) {
                CbDisparoArmaFogo.setChecked(false);
            } else {
                CbDisparoArmaFogo.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkEmpurraoAlturaFato() == 0) {
                CbEmpurraoAltura.setChecked(false);
            } else {
                CbEmpurraoAltura.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkEmpurraoSobVeiculoFato() == 0) {
                CbEmpurraoSobVeiculo.setChecked(false);
            } else {
                CbEmpurraoSobVeiculo.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkEvenenadoFato() == 0) {
                CbEvenenamento.setChecked(false);
            } else {
                CbEvenenamento.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkEstanaduraFato() == 0) {
                CbEsganadura.setChecked(false);
            } else {
                CbEsganadura.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkEstrangulamentoFato() == 0) {
                CbEstrangulamento.setChecked(false);
            } else {
                CbEstrangulamento.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkGolpeFacaFato() == 0) {
                CbGolpeFacas.setChecked(false);
            } else {
                CbGolpeFacas.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkGolpeFacaoFato() == 0) {
                CbGolpeFacao.setChecked(false);
            } else {
                CbGolpeFacao.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkIncendioFato() == 0) {
                CbIncendio.setChecked(false);
            } else {
                CbIncendio.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkOmissaoSocorroFato() == 0) {
                CbOmissaoSocorro.setChecked(false);
            } else {
                CbOmissaoSocorro.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkPauladaFato() == 0) {
                CbPauladas.setChecked(false);
            } else {
                CbPauladas.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkPedradaFato() == 0) {
                CbPedradas.setChecked(false);
            } else {
                CbPedradas.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkPerfuracoesFato() == 0) {
                CbPerfuracao.setChecked(false);
            } else {
                CbPerfuracao.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkQueimaduraFato() == 0) {
                CbQueimadura.setChecked(false);
            } else {
                CbQueimadura.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkQueimarudasAcidoFato() == 0) {
                CbQueimaduraAcido.setChecked(false);
            } else {
                CbQueimaduraAcido.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkSocosPontapesFato() == 0) {
                CbSocosPontapes.setChecked(false);
            } else {
                CbSocosPontapes.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkOutrosNaoRelacionadoFato() == 0) {
                CbOutrosNaoRelacionadosMeioEmpregado.setChecked(false);
            } else {
                CbOutrosNaoRelacionadosMeioEmpregado.setChecked(true);
            }

            if (cvlifatosematualizar.getCbkNaoIdentificadoFato() == 0) {
                CbNaoIdentificadoMeioEmpregado.setChecked(false);
            } else {
                CbNaoIdentificadoMeioEmpregado.setChecked(true);
            }

            RuaLogradouroFato.setText(cvlifatosematualizar.getDsRuaFato());
            NLogradouroFato.setText(cvlifatosematualizar.getDsNRuaFato());
            ReferenciaLocalFato.setText(cvlifatosematualizar.getDsReferenciaLocalFato());
            BairroFato.setText(cvlifatosematualizar.getDsBairroFato());
            DisVilPovoFato.setText(cvlifatosematualizar.getDsDistVilPovoFato());
            DetalhamentoOutroFato.setText(cvlifatosematualizar.getDsOutroDetalhamento());

           // Log.i("Valores cod pass",""+codigorecebidosem);
        }

        cvli = new Cvli();
        cvliDao = new CvliDao(this);
        carroDao = new CarroDao(this);
        ListaCarros = carroDao.retornarCarros();

        ListaCarrosFiltrados.addAll(ListaCarros);
        ArrayAdapter<Carro> adaptadorCarro = new ArrayAdapter<Carro>(this, android.R.layout.simple_list_item_1, ListaCarrosFiltrados);

        LvCarros.setAdapter(adaptadorCarro);
        registerForContextMenu(LvCarros);

        //Inicio do codigo para o calendario

        c = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        DataFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FatoActivity.this, date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //adicionando uma adapter para os Spinner selecionar os conteudos de strings
        ArrayAdapter adaptadorMotivacaoCrime = ArrayAdapter.createFromResource(this, R.array.MotivacaoCrimes, android.R.layout.simple_spinner_item);
        MotivacaoCrime.setAdapter(adaptadorMotivacaoCrime);

        ArrayAdapter adaptadorNatureza = ArrayAdapter.createFromResource(this, R.array.natureza, android.R.layout.simple_spinner_item);
        Natureza.setAdapter(adaptadorNatureza);

        ArrayAdapter adaptadorlogradouro = ArrayAdapter.createFromResource(this, R.array.Logradouro, android.R.layout.simple_spinner_item);
        Logradouro.setAdapter(adaptadorlogradouro);

        ArrayAdapter adaptadorDistVilPovo = ArrayAdapter.createFromResource(this, R.array.Distrito, android.R.layout.simple_spinner_item);
        DistVilPovo.setAdapter(adaptadorDistVilPovo);

        ArrayAdapter adaptadorEstado = ArrayAdapter.createFromResource(this, R.array.Estado, android.R.layout.simple_spinner_item);
        Estado.setAdapter(adaptadorEstado);

        ArrayAdapter adaptadorZona = ArrayAdapter.createFromResource(this, R.array.Zona, android.R.layout.simple_spinner_item);
        Zona.setAdapter(adaptadorZona);

        ArrayAdapter adaptadorDetalhamentoLocal = ArrayAdapter.createFromResource(this, R.array.Detalhelocal, android.R.layout.simple_spinner_item);
        DetalhamentoLocal.setAdapter(adaptadorDetalhamentoLocal);

        municipiolista = municipioDao.retornarMunicipioFato();

        ArrayAdapter<String> adaptadorMunicipioFato = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, municipiolista);
        adaptadorMunicipioFato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpMunicipioFato.setAdapter(adaptadorMunicipioFato);


        //Eventos dos objetos
        RgEnderecoFato.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RbEscolhidoEnderecoFato = (RadioButton) RgEnderecoFato.findViewById(checkedId);
                int indiceEnderecoFatoEscolhido = RgEnderecoFato.indexOfChild(RbEscolhidoEnderecoFato);

                if (indiceEnderecoFatoEscolhido == 1) {
                    EnderecoNDefinidoFato = 1;
                    EnderecoDefinidoFato = 0;
                    tornarCamposInvisiveisEndereco();
                } else {
                    EnderecoDefinidoFato = 1;
                    EnderecoNDefinidoFato = 0;
                    tornarCamposHabilitados();
                }
            }
        });

        BtnNaturezaFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Natureza.setVisibility(View.VISIBLE);
                if (atualizar == 1) {
                    BtnDataFato.setVisibility(View.VISIBLE);
                    String Dsnaturezafato = cvlifato.getDsNaturezaFato();
                    if (Dsnaturezafato == null) {
                        Natureza.setSelection(0);
                        Dsnaturezafato = Natureza.getItemAtPosition(0).toString();
                    }

                    if (Dsnaturezafato.equals("Selecione...") && Dsnaturezafato != null) {
                        Natureza.setSelection(0);
                    }
                    if (Dsnaturezafato.equals("Homicídio(s)") && Dsnaturezafato != null) {
                        Natureza.setSelection(1);
                    }
                    if (Dsnaturezafato.equals("Feminicídio(s)") && Dsnaturezafato != null) {
                        Natureza.setSelection(2);
                    }
                    if (Dsnaturezafato.equals("Latrocínio(s)") && Dsnaturezafato != null) {
                        Natureza.setSelection(3);
                    }
                    if (Dsnaturezafato.equals("Lesão Corporal Seguida de Morte") && Dsnaturezafato != null) {
                        Natureza.setSelection(4);
                    }
                    if (Dsnaturezafato.equals("Morte(s) a Esclarecer") && Dsnaturezafato != null) {
                        Natureza.setSelection(5);
                    }
                }else if(atualizar == 2){
                    BtnDataFato.setVisibility(View.VISIBLE);
                    String Dsnaturezafato = cvlifatosematualizar.getDsNaturezaFato();

                    if (Dsnaturezafato == null) {
                        Natureza.setSelection(0);
                        Dsnaturezafato = Natureza.getItemAtPosition(0).toString();
                    }

                    if (Dsnaturezafato.equals("Selecione...") && Dsnaturezafato != null) {
                        Natureza.setSelection(0);
                    }
                    if (Dsnaturezafato.equals("Homicídio(s)") && Dsnaturezafato != null) {
                        Natureza.setSelection(1);
                    }
                    if (Dsnaturezafato.equals("Feminicídio(s)") && Dsnaturezafato != null) {
                        Natureza.setSelection(2);
                    }
                    if (Dsnaturezafato.equals("Latrocínio(s)") && Dsnaturezafato != null) {
                        Natureza.setSelection(3);
                    }
                    if (Dsnaturezafato.equals("Lesão Corporal Seguida de Morte") && Dsnaturezafato != null) {
                        Natureza.setSelection(4);
                    }
                    if (Dsnaturezafato.equals("Morte(s) a Esclarecer") && Dsnaturezafato != null) {
                        Natureza.setSelection(5);
                    }
                }
            }
        });

        Natureza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                NaturezaFato = Natureza.getItemAtPosition(position).toString();

                if (NaturezaFato.equals("Homicídio(s)") && NaturezaFato != null) {
                    BtnDataFato.setVisibility(View.VISIBLE);
                }
                if (NaturezaFato.equals("Feminicídio(s)") && NaturezaFato != null) {
                    BtnDataFato.setVisibility(View.VISIBLE);
                }
                if (NaturezaFato.equals("Latrocínio(s)") && NaturezaFato != null) {
                    BtnDataFato.setVisibility(View.VISIBLE);
                }
                if (NaturezaFato.equals("Lesão Corporal Seguida de Morte") && NaturezaFato != null) {
                    BtnDataFato.setVisibility(View.VISIBLE);
                }
                if (NaturezaFato.equals("Morte(s) a Esclarecer") && NaturezaFato != null) {
                    BtnDataFato.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnDataFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataFato.setVisibility(View.VISIBLE);
                CbNaoInformadoDataFato.setVisibility(View.VISIBLE);

            }
        });

        BtnHorarioFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                HorarioFato.setVisibility(View.VISIBLE);
                CbNaoInformadoHorarioFato.setVisibility(View.VISIBLE);
                if (atualizar == 1) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }else if(atualizar == 2){
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
            }
        });

        CbNaoInformadoDataFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnHorarioFato.setVisibility(View.VISIBLE);
                if (CbNaoInformadoDataFato.isChecked()) {
                    NaoInformadoDataFato = 1;
                    DataFato.setEnabled(false);
                } else {
                    NaoInformadoDataFato = 0;
                    DataFato.setEnabled(true);
                }
            }
        });

        CbNaoInformadoHorarioFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnEnderecoFato.setVisibility(View.VISIBLE);
                if (CbNaoInformadoHorarioFato.isChecked()) {
                    NaoInformadoHorarioFato = 1;
                    HorarioFato.setEnabled(false);
                } else {
                    NaoInformadoHorarioFato = 0;
                    HorarioFato.setEnabled(true);
                }
            }
        });

        BtnMotivacaoFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotivacaoCrime.setVisibility(View.VISIBLE);
                if (atualizar == 1) {
                    String Dsmotivacaocrime = cvlifato.getDsMotivacaoFato();
                    Log.i("Valores moti",""+Dsmotivacaocrime);
                    if (Dsmotivacaocrime == null) {
                        MotivacaoCrime.setSelection(0);
                        Dsmotivacaocrime = MotivacaoCrime.getItemAtPosition(0).toString();
                    }

                    switch (Dsmotivacaocrime) {

                        case "Selecione...":
                            MotivacaoCrime.setSelection(0);
                            break;
                        case "Dívida de drogas":
                            MotivacaoCrime.setSelection(1);
                            break;
                        case "Briga do tráfico":
                            MotivacaoCrime.setSelection(2);
                            break;
                        case "Disputa entre facções":
                            MotivacaoCrime.setSelection(3);
                            break;
                        case "Violência doméstica":
                            MotivacaoCrime.setSelection(4);
                            break;
                        case "Crime passional":
                            MotivacaoCrime.setSelection(5);
                            break;
                        case "Roubo seguido de morte":
                            MotivacaoCrime.setSelection(6);
                            break;
                        case "Briga de trânsito":
                            MotivacaoCrime.setSelection(7);
                            break;
                        case "Briga de bar":
                            MotivacaoCrime.setSelection(8);
                            break;
                        case "Briga de vizinhos":
                            MotivacaoCrime.setSelection(9);
                            break;
                        case "Discussão por embriaguez":
                            MotivacaoCrime.setSelection(10);
                            break;
                        case "Discussão em família":
                            MotivacaoCrime.setSelection(11);
                            break;
                        case "Vingança":
                            MotivacaoCrime.setSelection(12);
                            break;
                        case "Vingança privada (fazer justiça)":
                            MotivacaoCrime.setSelection(13);
                            break;
                        case "Legítima defesa":
                            MotivacaoCrime.setSelection(14);
                            break;
                        case "Preterdolo":
                            MotivacaoCrime.setSelection(15);
                            break;
                        case "Assegurar a impunidade de outro crime":
                            MotivacaoCrime.setSelection(16);
                            break;
                        case "Extorsão mediante seqüestro":
                            MotivacaoCrime.setSelection(17);
                            break;
                        case "Estupro seguido de morte":
                            MotivacaoCrime.setSelection(18);
                            break;
                        case "Motim/ rebelião":
                            MotivacaoCrime.setSelection(19);
                            break;
                        case "Intolerância política":
                            MotivacaoCrime.setSelection(20);
                            break;
                        case "Intolerância religiosa":
                            MotivacaoCrime.setSelection(21);
                            break;
                        case "Homofobia":
                            MotivacaoCrime.setSelection(22);
                            break;
                        case "Crime de ódio ou racismo":
                            MotivacaoCrime.setSelection(23);
                            break;
                        case "Ritual religioso":
                            MotivacaoCrime.setSelection(24);
                            break;
                        case "Rivalidade profissional":
                            MotivacaoCrime.setSelection(25);
                            break;
                        case "Prêmio de seguro":
                            MotivacaoCrime.setSelection(26);
                            break;
                        case "Dívida contraída pelo autor":
                            MotivacaoCrime.setSelection(27);
                            break;
                        case "Dívida contraída pela vítima":
                            MotivacaoCrime.setSelection(28);
                            break;
                        case "Sucessão política":
                            MotivacaoCrime.setSelection(29);
                            break;
                        case "Conflito agrário":
                            MotivacaoCrime.setSelection(30);
                            break;
                        case "Desentendimento fútil":
                            MotivacaoCrime.setSelection(31);
                            break;
                        case "Outra não relacionada":
                            MotivacaoCrime.setSelection(32);
                            break;
                        default:
                            MotivacaoCrime.setSelection(33);
                            break;
                    }
                    BtnCarroFato.setVisibility(View.VISIBLE);
                    LvCarros.setVisibility(View.VISIBLE);
                }else if(atualizar == 2){
                    String Dsmotivacaocrime = cvlifatosematualizar.getDsMotivacaoFato();

                    if (Dsmotivacaocrime == null) {
                        MotivacaoCrime.setSelection(0);
                        Dsmotivacaocrime = MotivacaoCrime.getItemAtPosition(0).toString();
                    }

                    switch (Dsmotivacaocrime) {

                        case "Selecione...":
                            MotivacaoCrime.setSelection(0);
                            break;
                        case "Dívida de drogas":
                            MotivacaoCrime.setSelection(1);
                            break;
                        case "Briga do tráfico":
                            MotivacaoCrime.setSelection(2);
                            break;
                        case "Disputa entre facções":
                            MotivacaoCrime.setSelection(3);
                            break;
                        case "Violência doméstica":
                            MotivacaoCrime.setSelection(4);
                            break;
                        case "Crime passional":
                            MotivacaoCrime.setSelection(5);
                            break;
                        case "Roubo seguido de morte":
                            MotivacaoCrime.setSelection(6);
                            break;
                        case "Briga de trânsito":
                            MotivacaoCrime.setSelection(7);
                            break;
                        case "Briga de bar":
                            MotivacaoCrime.setSelection(8);
                            break;
                        case "Briga de vizinhos":
                            MotivacaoCrime.setSelection(9);
                            break;
                        case "Discussão por embriaguez":
                            MotivacaoCrime.setSelection(10);
                            break;
                        case "Discussão em família":
                            MotivacaoCrime.setSelection(11);
                            break;
                        case "Vingança":
                            MotivacaoCrime.setSelection(12);
                            break;
                        case "Vingança privada (fazer justiça)":
                            MotivacaoCrime.setSelection(13);
                            break;
                        case "Legítima defesa":
                            MotivacaoCrime.setSelection(14);
                            break;
                        case "Preterdolo":
                            MotivacaoCrime.setSelection(15);
                            break;
                        case "Assegurar a impunidade de outro crime":
                            MotivacaoCrime.setSelection(16);
                            break;
                        case "Extorsão mediante seqüestro":
                            MotivacaoCrime.setSelection(17);
                            break;
                        case "Estupro seguido de morte":
                            MotivacaoCrime.setSelection(18);
                            break;
                        case "Motim/ rebelião":
                            MotivacaoCrime.setSelection(19);
                            break;
                        case "Intolerância política":
                            MotivacaoCrime.setSelection(20);
                            break;
                        case "Intolerância religiosa":
                            MotivacaoCrime.setSelection(21);
                            break;
                        case "Homofobia":
                            MotivacaoCrime.setSelection(22);
                            break;
                        case "Crime de ódio ou racismo":
                            MotivacaoCrime.setSelection(23);
                            break;
                        case "Ritual religioso":
                            MotivacaoCrime.setSelection(24);
                            break;
                        case "Rivalidade profissional":
                            MotivacaoCrime.setSelection(25);
                            break;
                        case "Prêmio de seguro":
                            MotivacaoCrime.setSelection(26);
                            break;
                        case "Dívida contraída pelo autor":
                            MotivacaoCrime.setSelection(27);
                            break;
                        case "Dívida contraída pela vítima":
                            MotivacaoCrime.setSelection(28);
                            break;
                        case "Sucessão política":
                            MotivacaoCrime.setSelection(29);
                            break;
                        case "Conflito agrário":
                            MotivacaoCrime.setSelection(30);
                            break;
                        case "Desentendimento fútil":
                            MotivacaoCrime.setSelection(31);
                            break;
                        case "Outra não relacionada":
                            MotivacaoCrime.setSelection(32);
                            break;
                        default:
                            MotivacaoCrime.setSelection(33);
                            break;
                    }
                    BtnCarroFato.setVisibility(View.VISIBLE);
                    LvCarros.setVisibility(View.VISIBLE);
                }
            }
        });


        DataFato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                BtnHorarioFato.setVisibility(View.VISIBLE);
            }
        });

        HorarioFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int minutos = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(FatoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (minute < 10) {
                            HorarioFato.setText(hourOfDay + ":0" + minute);
                        } else {
                            HorarioFato.setText(hourOfDay + ":" + minute);
                        }

                    }
                }, hora, minutos, android.text.format.DateFormat.is24HourFormat(getApplicationContext()));
                tpd.show();

            }
        });

        HorarioFato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                BtnEnderecoFato.setVisibility(View.VISIBLE);
            }
        });

        MotivacaoCrime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MotivacaoCrimeFato = MotivacaoCrime.getItemAtPosition(position).toString();

                if (MotivacaoCrimeFato.equals("Dívida de drogas")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Briga do tráfico")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Disputa entre facções")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Violência doméstica")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Crime passional")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Roubo seguido de morte")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Briga de trânsito")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Briga de bar")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Briga de vizinhos")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Discussão por embriaguez")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Discussão em família")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Vingança")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Vingança privada (fazer justiça)")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Legítima defesa")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Preterdolo")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Assegurar a impunidade de outro crime")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Extorsão mediante seqüestro")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Estupro seguido de morte")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Motim/ rebelião")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Intolerância política")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Intolerância religiosa")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Homofobia")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Crime de ódio ou racismo")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Ritual religioso")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Rivalidade profissional")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Prêmio de seguro")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Dívida contraída pelo autor")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Dívida contraída pela vítima")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Sucessão política")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Conflito agrário")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Desentendimento fútil")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Outra não relacionada")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoCrimeFato.equals("Motivação não identificada")) {
                    BtnEnderecoFato.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnEnderecoFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                esconderTeclado(FatoActivity.this);
                BtnMeioEmpregadoFato.setVisibility(View.VISIBLE);
                RgEnderecoFato.setVisibility(View.VISIBLE);
                tornarCamposVisiveisEndereco();

                if (atualizar == 1) {

                    String Dslogradouro = cvlifato.getDsLogradouroFato();

                    BtnMeioEmpregadoFato.setVisibility(View.VISIBLE);
                    if (Dslogradouro == null) {
                        Logradouro.setSelection(0);
                        Dslogradouro = Logradouro.getItemAtPosition(0).toString();
                    }

                    switch (Dslogradouro) {

                        case "Selecione...":
                            Logradouro.setSelection(0);
                            break;
                        case "Rua":
                            Logradouro.setSelection(1);
                            break;
                        case "Avenida":
                            Logradouro.setSelection(2);
                            break;
                        case "Estrada":
                            Logradouro.setSelection(3);
                            break;
                        case "Alameda":
                            Logradouro.setSelection(4);
                            break;
                        case "Caminho":
                            Logradouro.setSelection(5);
                            break;
                        case "Cemiterio":
                            Logradouro.setSelection(6);
                            break;
                        case "Feira":
                            Logradouro.setSelection(7);
                            break;
                        case "Rodovia":
                            Logradouro.setSelection(8);
                            break;
                        case "Parque":
                            Logradouro.setSelection(9);
                            break;
                        case "Travessa":
                            Logradouro.setSelection(10);
                            break;
                        case "Largo":
                            Logradouro.setSelection(11);
                            break;
                        case "Jardim":
                            Logradouro.setSelection(12);
                            break;
                        case "Praça":
                            Logradouro.setSelection(13);
                            break;
                        case "Quadra":
                            Logradouro.setSelection(14);
                            break;
                        case "Fazenda":
                            Logradouro.setSelection(15);
                            break;
                        case "Mata":
                            Logradouro.setSelection(16);
                            break;
                        case "Plantio Agricula":
                            Logradouro.setSelection(17);
                            break;
                        case "Pasto":
                            Logradouro.setSelection(18);
                            break;
                        case "Praia":
                            Logradouro.setSelection(19);
                            break;
                        case "Estabelecimento Prisional":
                            Logradouro.setSelection(20);
                            break;
                        case "Rio":
                            Logradouro.setSelection(21);
                            break;
                        case "Aldeia Indigena":
                            Logradouro.setSelection(22);
                            break;
                        case "Assentamento":
                            Logradouro.setSelection(23);
                            break;
                        case "Anel Viário":
                            Logradouro.setSelection(24);
                            break;
                        default:
                            Logradouro.setSelection(25);
                            break;
                    }

                    String Dsdistvilpovo = cvlifato.getDsDistVilPovoFato();
                    Log.i("Valores log",""+Dsdistvilpovo);
                    if (Dsdistvilpovo == null) {
                        DistVilPovo.setSelection(0);
                        Dsdistvilpovo = DistVilPovo.getItemAtPosition(0).toString();
                    }

                    switch (Dsdistvilpovo) {

                        case "Selecione...":
                            DistVilPovo.setSelection(0);
                            break;
                        case "Sede":
                            DistVilPovo.setSelection(1);
                            break;
                        case "Distrito":
                            DistVilPovo.setSelection(2);
                            break;
                        case "Vilarejo":
                            DistVilPovo.setSelection(3);
                            break;
                        default:
                            DistVilPovo.setSelection(13);
                            break;
                    }

                    String Dsestadofato = cvlifato.getDsEstadoFato();
                    Log.i("Valores log",""+Dsestadofato);
                    if (Dsestadofato == null) {
                        Estado.setSelection(0);
                        Dsestadofato = Estado.getItemAtPosition(0).toString();
                    }

                    switch (Dsestadofato) {

                        case "BA":
                            Estado.setSelection(0);
                            break;
                        case "AC":
                            Estado.setSelection(1);
                            break;
                        case "AL":
                            Estado.setSelection(2);
                            break;
                        case "AP":
                            Estado.setSelection(3);
                            break;
                        case "AM":
                            Estado.setSelection(4);
                            break;
                        case "CE":
                            Estado.setSelection(5);
                            break;
                        case "DF":
                            Estado.setSelection(6);
                            break;
                        case "ES":
                            Estado.setSelection(7);
                            break;
                        case "GO":
                            Estado.setSelection(8);
                            break;
                        case "MA":
                            Estado.setSelection(9);
                            break;
                        case "MT":
                            Estado.setSelection(10);
                            break;
                        case "MS":
                            Estado.setSelection(11);
                            break;
                        case "MG":
                            Estado.setSelection(12);
                            break;
                        case "PA":
                            Estado.setSelection(13);
                            break;
                        case "PB":
                            Estado.setSelection(14);
                            break;
                        case "PR":
                            Estado.setSelection(15);
                            break;
                        case "PE":
                            Estado.setSelection(16);
                            break;
                        case "PI":
                            Estado.setSelection(17);
                            break;
                        case "RJ":
                            Estado.setSelection(18);
                            break;
                        case "RN":
                            Estado.setSelection(19);
                            break;
                        case "RS":
                            Estado.setSelection(20);
                            break;
                        case "RO":
                            Estado.setSelection(21);
                            break;
                        case "RR":
                            Estado.setSelection(22);
                            break;
                        case "SC":
                            Estado.setSelection(23);
                            break;
                        case "SP":
                            Estado.setSelection(24);
                            break;
                        case "SE":
                            Estado.setSelection(25);
                            break;
                        default:
                            Estado.setSelection(26);
                            break;
                    }

                    String Dszona = cvlifato.getDsZonaFato();
                    Log.i("Valores log",""+Dszona);
                    if (Dszona == null) {
                        Zona.setSelection(0);
                        Dszona = Zona.getItemAtPosition(0).toString();
                    }

                    switch (Dszona) {

                        case "Selecione...":
                            Zona.setSelection(0);
                            break;
                        case "Urbana":
                            Zona.setSelection(1);
                            break;
                        default:
                            Zona.setSelection(2);
                            break;
                    }

                    String Dsdetalhamentolocal = cvlifato.getDsDetalhamentoLocal();
                    Log.i("Valores log",""+Dsdetalhamentolocal);
                    if (Dsdetalhamentolocal == null) {
                        DetalhamentoLocal.setSelection(0);
                        Dsdetalhamentolocal = DetalhamentoLocal.getItemAtPosition(0).toString();
                    }


                    switch (Dsdetalhamentolocal) {

                        case "Selecione...":
                            DetalhamentoLocal.setSelection(0);
                            break;
                        case "Via Pública":
                            DetalhamentoLocal.setSelection(1);
                            break;
                        case "Interior Residência":
                            DetalhamentoLocal.setSelection(2);
                            break;
                        case "Estabelecimento Comercial":
                            DetalhamentoLocal.setSelection(3);
                            break;
                        case "Repartição Pública":
                            DetalhamentoLocal.setSelection(4);
                            break;
                        case "Terreno Baldio":
                            DetalhamentoLocal.setSelection(5);
                            break;
                        case "Mar":
                            DetalhamentoLocal.setSelection(6);
                            break;
                        case "Rio":
                            DetalhamentoLocal.setSelection(7);
                            break;
                        case "Praia":
                            DetalhamentoLocal.setSelection(8);
                            break;
                        case "Estabelecimento Prisional":
                            DetalhamentoLocal.setSelection(9);
                            break;
                        case "Mata":
                            DetalhamentoLocal.setSelection(10);
                            break;
                        case "Plantação Agricula":
                            DetalhamentoLocal.setSelection(11);
                            break;
                        case "Pasto":
                            DetalhamentoLocal.setSelection(12);
                            break;
                        case "Terreiro de Candomblé":
                            DetalhamentoLocal.setSelection(13);
                            break;
                        case "Terreiro de Umbanda":
                            DetalhamentoLocal.setSelection(14);
                            break;
                        case "Igreja Católica":
                            DetalhamentoLocal.setSelection(15);
                            break;
                        case "Igreja Evangélica":
                            DetalhamentoLocal.setSelection(16);
                            break;
                        case "Quintal":
                            DetalhamentoLocal.setSelection(17);
                            break;
                        case "Ponto de Ônibus":
                            DetalhamentoLocal.setSelection(18);
                            break;
                        case "Interior de Veiculo Automotor":
                            DetalhamentoLocal.setSelection(19);
                            break;
                        default:
                            DetalhamentoLocal.setSelection(20);
                            break;
                    }
                    BtnMeioEmpregadoFato.setVisibility(View.VISIBLE);
                }else if(atualizar == 2){
                    String Dslogradouro = cvlifatosematualizar.getDsLogradouroFato();
                    BtnMeioEmpregadoFato.setVisibility(View.VISIBLE);
                    if (Dslogradouro == null) {
                        Logradouro.setSelection(0);
                        Dslogradouro = Logradouro.getItemAtPosition(0).toString();
                    }

                    switch (Dslogradouro) {

                        case "Selecione...":
                            Logradouro.setSelection(0);
                            break;
                        case "Rua":
                            Logradouro.setSelection(1);
                            break;
                        case "Avenida":
                            Logradouro.setSelection(2);
                            break;
                        case "Estrada":
                            Logradouro.setSelection(3);
                            break;
                        case "Alameda":
                            Logradouro.setSelection(4);
                            break;
                        case "Caminho":
                            Logradouro.setSelection(5);
                            break;
                        case "Cemiterio":
                            Logradouro.setSelection(6);
                            break;
                        case "Feira":
                            Logradouro.setSelection(7);
                            break;
                        case "Rodovia":
                            Logradouro.setSelection(8);
                            break;
                        case "Parque":
                            Logradouro.setSelection(9);
                            break;
                        case "Travessa":
                            Logradouro.setSelection(10);
                            break;
                        case "Largo":
                            Logradouro.setSelection(11);
                            break;
                        case "Jardim":
                            Logradouro.setSelection(12);
                            break;
                        case "Praça":
                            Logradouro.setSelection(13);
                            break;
                        case "Quadra":
                            Logradouro.setSelection(14);
                            break;
                        case "Fazenda":
                            Logradouro.setSelection(15);
                            break;
                        case "Mata":
                            Logradouro.setSelection(16);
                            break;
                        case "Plantio Agricula":
                            Logradouro.setSelection(17);
                            break;
                        case "Pasto":
                            Logradouro.setSelection(18);
                            break;
                        case "Praia":
                            Logradouro.setSelection(19);
                            break;
                        case "Estabelecimento Prisional":
                            Logradouro.setSelection(20);
                            break;
                        case "Rio":
                            Logradouro.setSelection(21);
                            break;
                        case "Aldeia Indigena":
                            Logradouro.setSelection(22);
                            break;
                        case "Assentamento":
                            Logradouro.setSelection(23);
                            break;
                        case "Anel Viário":
                            Logradouro.setSelection(24);
                            break;
                        default:
                            Logradouro.setSelection(25);
                            break;
                    }

                    String Dsdistvilpovo = cvlifatosematualizar.getDsDistVilPovoFato();

                    if (Dsdistvilpovo == null) {
                        DistVilPovo.setSelection(0);
                        Dsdistvilpovo = DistVilPovo.getItemAtPosition(0).toString();
                    }

                    switch (Dsdistvilpovo) {

                        case "Selecione...":
                            DistVilPovo.setSelection(0);
                            break;
                        case "Sede":
                            DistVilPovo.setSelection(1);
                            break;
                        case "Distrito":
                            DistVilPovo.setSelection(2);
                            break;
                        case "Vilarejo":
                            DistVilPovo.setSelection(3);
                            break;
                        default:
                            DistVilPovo.setSelection(13);
                            break;
                    }

                    String Dsestadofato = cvlifatosematualizar.getDsEstadoFato();

                    if (Dsestadofato == null) {
                        Estado.setSelection(0);
                        Dsestadofato = Estado.getItemAtPosition(0).toString();
                    }

                    switch (Dsestadofato) {

                        case "BA":
                            Estado.setSelection(0);
                            break;
                        case "AC":
                            Estado.setSelection(1);
                            break;
                        case "AL":
                            Estado.setSelection(2);
                            break;
                        case "AP":
                            Estado.setSelection(3);
                            break;
                        case "AM":
                            Estado.setSelection(4);
                            break;
                        case "CE":
                            Estado.setSelection(5);
                            break;
                        case "DF":
                            Estado.setSelection(6);
                            break;
                        case "ES":
                            Estado.setSelection(7);
                            break;
                        case "GO":
                            Estado.setSelection(8);
                            break;
                        case "MA":
                            Estado.setSelection(9);
                            break;
                        case "MT":
                            Estado.setSelection(10);
                            break;
                        case "MS":
                            Estado.setSelection(11);
                            break;
                        case "MG":
                            Estado.setSelection(12);
                            break;
                        case "PA":
                            Estado.setSelection(13);
                            break;
                        case "PB":
                            Estado.setSelection(14);
                            break;
                        case "PR":
                            Estado.setSelection(15);
                            break;
                        case "PE":
                            Estado.setSelection(16);
                            break;
                        case "PI":
                            Estado.setSelection(17);
                            break;
                        case "RJ":
                            Estado.setSelection(18);
                            break;
                        case "RN":
                            Estado.setSelection(19);
                            break;
                        case "RS":
                            Estado.setSelection(20);
                            break;
                        case "RO":
                            Estado.setSelection(21);
                            break;
                        case "RR":
                            Estado.setSelection(22);
                            break;
                        case "SC":
                            Estado.setSelection(23);
                            break;
                        case "SP":
                            Estado.setSelection(24);
                            break;
                        case "SE":
                            Estado.setSelection(25);
                            break;
                        default:
                            Estado.setSelection(26);
                            break;
                    }

                    String Dszona = cvlifatosematualizar.getDsZonaFato();

                    if (Dszona == null) {
                        Zona.setSelection(0);
                        Dszona = Zona.getItemAtPosition(0).toString();
                    }

                    switch (Dszona) {

                        case "Selecione...":
                            Zona.setSelection(0);
                            break;
                        case "Urbana":
                            Zona.setSelection(1);
                            break;
                        default:
                            Zona.setSelection(2);
                            break;
                    }

                    String Dsdetalhamentolocal = cvlifatosematualizar.getDsDetalhamentoLocal();

                    if (Dsdetalhamentolocal == null) {
                        DetalhamentoLocal.setSelection(0);
                        Dsdetalhamentolocal = DetalhamentoLocal.getItemAtPosition(0).toString();
                    }


                    switch (Dsdetalhamentolocal) {

                        case "Selecione...":
                            DetalhamentoLocal.setSelection(0);
                            break;
                        case "Via Pública":
                            DetalhamentoLocal.setSelection(1);
                            break;
                        case "Interior Residência":
                            DetalhamentoLocal.setSelection(2);
                            break;
                        case "Estabelecimento Comercial":
                            DetalhamentoLocal.setSelection(3);
                            break;
                        case "Repartição Pública":
                            DetalhamentoLocal.setSelection(4);
                            break;
                        case "Terreno Baldio":
                            DetalhamentoLocal.setSelection(5);
                            break;
                        case "Mar":
                            DetalhamentoLocal.setSelection(6);
                            break;
                        case "Rio":
                            DetalhamentoLocal.setSelection(7);
                            break;
                        case "Praia":
                            DetalhamentoLocal.setSelection(8);
                            break;
                        case "Estabelecimento Prisional":
                            DetalhamentoLocal.setSelection(9);
                            break;
                        case "Mata":
                            DetalhamentoLocal.setSelection(10);
                            break;
                        case "Plantação Agricula":
                            DetalhamentoLocal.setSelection(11);
                            break;
                        case "Pasto":
                            DetalhamentoLocal.setSelection(12);
                            break;
                        case "Terreiro de Candomblé":
                            DetalhamentoLocal.setSelection(13);
                            break;
                        case "Terreiro de Umbanda":
                            DetalhamentoLocal.setSelection(14);
                            break;
                        case "Igreja Católica":
                            DetalhamentoLocal.setSelection(15);
                            break;
                        case "Igreja Evangélica":
                            DetalhamentoLocal.setSelection(16);
                            break;
                        case "Quintal":
                            DetalhamentoLocal.setSelection(17);
                            break;
                        case "Ponto de Ônibus":
                            DetalhamentoLocal.setSelection(18);
                            break;
                        case "Interior de Veiculo Automotor":
                            DetalhamentoLocal.setSelection(19);
                            break;
                        default:
                            DetalhamentoLocal.setSelection(20);
                            break;
                    }
                    BtnMeioEmpregadoFato.setVisibility(View.VISIBLE);
                }

            }
        });



        Logradouro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                LogradouroFato = Logradouro.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DistVilPovo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DistVilPovoFato = DistVilPovo.getItemAtPosition(position).toString();
                if (DistVilPovoFato.equals("Sede")) {
                    DisVilPovoFato.setText("Sede");
                } else {
                    DisVilPovoFato.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpMunicipioFato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MunicipioFato = SpMunicipioFato.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                EstadoFato = Estado.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Zona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ZonaFato = Zona.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DetalhamentoLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DetalhamentoLocalFato = DetalhamentoLocal.getItemAtPosition(position).toString();
                if (DetalhamentoLocalFato.equals("Outros")) {
                    DetalhamentoOutroFato.setVisibility(View.VISIBLE);
                }
                if (DetalhamentoLocalFato.equals("Via Pública")) {
                    NLogradouroFato.setEnabled(false);
                    BairroFato.setEnabled(true);

                }
                if (DetalhamentoLocalFato.equals("Terreno Baldio")) {
                    NLogradouroFato.setEnabled(false);
                }
                if (DetalhamentoLocalFato.equals("Mar")) {
                    RuaLogradouroFato.setEnabled(false);
                    NLogradouroFato.setEnabled(false);
                    Logradouro.setEnabled(false);
                    BairroFato.setEnabled(false);
                }
                if (DetalhamentoLocalFato.equals("Praia")) {
                    RuaLogradouroFato.setEnabled(false);
                    NLogradouroFato.setEnabled(false);
                    Logradouro.setEnabled(false);
                    BairroFato.setEnabled(false);
                }
                if (DetalhamentoLocalFato.equals("Assentamento")) {
                    RuaLogradouroFato.setEnabled(false);
                    NLogradouroFato.setEnabled(false);
                    Logradouro.setEnabled(false);
                    BairroFato.setEnabled(false);
                    TvReferenciaLocalFato.setText("Detalhamento");
                }
                if (DetalhamentoLocalFato.equals("Praia")) {
                    RuaLogradouroFato.setEnabled(false);
                    NLogradouroFato.setEnabled(false);
                    Logradouro.setEnabled(false);
                    BairroFato.setEnabled(false);
                }
                if (DetalhamentoLocalFato.equals("Rio")) {
                    RuaLogradouroFato.setEnabled(false);
                    NLogradouroFato.setEnabled(false);
                    Logradouro.setEnabled(false);
                    BairroFato.setEnabled(false);
                }
                if (DetalhamentoLocalFato.equals("Aldeia indígena")) {
                    RuaLogradouroFato.setEnabled(false);
                    NLogradouroFato.setEnabled(false);
                    Logradouro.setEnabled(false);
                    BairroFato.setEnabled(false);
                }
                if (DetalhamentoLocalFato.equals("Estabelecimento Prisional")) {
                    RuaLogradouroFato.setEnabled(false);
                    NLogradouroFato.setEnabled(false);
                    Logradouro.setEnabled(false);
                    BairroFato.setEnabled(false);
                }
                if (DetalhamentoLocalFato.equals("Interior Residência")) {
                    RuaLogradouroFato.setEnabled(true);
                    NLogradouroFato.setEnabled(true);
                    Logradouro.setEnabled(true);
                    BairroFato.setEnabled(true);
                }
                if (DetalhamentoLocalFato.equals("Estabelecimento Comercial")) {
                    RuaLogradouroFato.setEnabled(true);
                    NLogradouroFato.setEnabled(true);
                    Logradouro.setEnabled(true);
                    BairroFato.setEnabled(true);
                }
                if (DetalhamentoLocalFato.equals("Repartição Pública")) {
                    RuaLogradouroFato.setEnabled(true);
                    NLogradouroFato.setEnabled(true);
                    Logradouro.setEnabled(true);
                    BairroFato.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnMeioEmpregadoFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                esconderTeclado(FatoActivity.this);
                CbTortura.setVisibility(View.VISIBLE);
                CbAfogamento.setVisibility(View.VISIBLE);
                CbAsfixia.setVisibility(View.VISIBLE);
                CbAtropelamento.setVisibility(View.VISIBLE);
                CbDisparoArmaFogo.setVisibility(View.VISIBLE);
                CbEmpurraoAltura.setVisibility(View.VISIBLE);
                CbEmpurraoSobVeiculo.setVisibility(View.VISIBLE);
                CbEvenenamento.setVisibility(View.VISIBLE);
                CbEsganadura.setVisibility(View.VISIBLE);
                CbEstrangulamento.setVisibility(View.VISIBLE);
                CbGolpeFacas.setVisibility(View.VISIBLE);
                CbGolpeFacao.setVisibility(View.VISIBLE);
                CbIncendio.setVisibility(View.VISIBLE);
                CbOmissaoSocorro.setVisibility(View.VISIBLE);
                CbPauladas.setVisibility(View.VISIBLE);
                CbPedradas.setVisibility(View.VISIBLE);
                CbPerfuracao.setVisibility(View.VISIBLE);
                CbQueimadura.setVisibility(View.VISIBLE);
                CbQueimaduraAcido.setVisibility(View.VISIBLE);
                CbSocosPontapes.setVisibility(View.VISIBLE);
                CbOutrosNaoRelacionadosMeioEmpregado.setVisibility(View.VISIBLE);
                CbNaoIdentificadoMeioEmpregado.setVisibility(View.VISIBLE);

                if (atualizar == 1) {
                    BtnMotivacaoFato.setVisibility(View.VISIBLE);
                }else if(atualizar == 2){
                    BtnMotivacaoFato.setVisibility(View.VISIBLE);
                }

            }
        });

        CbTortura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);

                if(CbTortura.isChecked()){
                    Tortura = 1;
                }else{
                    Tortura = 0;
                }
            }
        });

        CbAfogamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);

                if (CbAfogamento.isChecked()) {
                    Afogamento = 1;
                } else {
                    Afogamento = 0;
                }
            }
        });

        CbAsfixia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                esconderTeclado(FatoActivity.this);
                if (CbAsfixia.isChecked()) {
                    Asfixia = 1;
                } else {
                    Asfixia = 0;
                }
            }
        });

        CbAtropelamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbAtropelamento.isChecked()) {
                    Atropelamento = 1;
                } else {
                    Atropelamento = 0;
                }
            }
        });

        CbDisparoArmaFogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbDisparoArmaFogo.isChecked()) {
                    DisparoArmaFoto = 1;
                } else {
                    DisparoArmaFoto = 0;
                }
            }
        });

        CbEmpurraoAltura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbEmpurraoAltura.isChecked()) {
                    EmpurraoALtura = 1;
                } else {
                    EmpurraoALtura = 0;
                }

            }
        });

        CbEmpurraoSobVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbEmpurraoSobVeiculo.isChecked()) {
                    EmpurraoSobVeiculo = 1;
                } else {
                    EmpurraoSobVeiculo = 0;
                }
            }
        });

        CbEvenenamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbEvenenamento.isChecked()) {
                    Evenenamento = 1;
                } else {
                    Evenenamento = 0;
                }
            }
        });

        CbEsganadura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbEsganadura.isChecked()) {
                    Esganadura = 1;
                } else {
                    Esganadura = 0;
                }
            }
        });

        CbEstrangulamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbEstrangulamento.isChecked()) {
                    Estrangulamento = 1;
                } else {
                    Estrangulamento = 0;
                }

            }
        });

        CbGolpeFacas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbGolpeFacas.isChecked()) {
                    GolpeFaca = 1;
                } else {
                    GolpeFaca = 0;
                }
            }
        });

        CbGolpeFacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbGolpeFacao.isChecked()) {
                    GolpeFacao = 1;
                } else {
                    GolpeFacao = 0;
                }
            }
        });

        CbIncendio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbIncendio.isChecked()) {
                    Incendio = 1;
                } else {
                    Incendio = 0;
                }
            }
        });

        CbOmissaoSocorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbOmissaoSocorro.isChecked()) {
                    OmissaoSocorro = 1;
                } else {
                    OmissaoSocorro = 0;
                }
            }
        });

        CbPauladas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbPauladas.isChecked()) {
                    Pauladas = 1;
                } else {
                    Pauladas = 0;
                }

            }
        });

        CbPedradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbPedradas.isChecked()) {
                    Pedradas = 1;
                } else {
                    Pedradas = 0;
                }
            }
        });

        CbPerfuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbPerfuracao.isChecked()) {
                    Perfuracao = 1;
                } else {
                    Perfuracao = 0;
                }

            }
        });

        CbQueimadura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbQueimadura.isChecked()) {
                    Queimaduras = 1;
                } else {
                    Queimaduras = 0;
                }
            }
        });

        CbQueimaduraAcido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbQueimaduraAcido.isChecked()) {
                    QueimaduraAcido = 1;
                } else {
                    QueimaduraAcido = 0;
                }
            }
        });

        CbSocosPontapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbSocosPontapes.isChecked()) {
                    SocosPontapes = 1;
                } else {
                    SocosPontapes = 0;
                }

            }
        });

        CbOutrosNaoRelacionadosMeioEmpregado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbOutrosNaoRelacionadosMeioEmpregado.isChecked()) {
                    OutrosNaoRelacionados = 1;
                } else {
                    OutrosNaoRelacionados = 0;
                }
            }
        });

        CbNaoIdentificadoMeioEmpregado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderTeclado(FatoActivity.this);
                BtnMotivacaoFato.setVisibility(View.VISIBLE);
                if (CbNaoIdentificadoMeioEmpregado.isChecked()) {
                    Naoidentificado = 1;
                } else {
                    Naoidentificado = 0;
                }
            }
        });

        MotivacaoCrime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MotivacaoFato = MotivacaoCrime.getItemAtPosition(position).toString();


                if (MotivacaoFato.equals("Dívida de drogas")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Briga do tráfico")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Disputa entre facções")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Violência doméstica")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Crime passional")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Roubo seguido de morte")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Briga de trânsito")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Briga de bar")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Briga de vizinhos")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Discussão por embriaguez")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Discussão em família")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Vingança")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Vingança privada (fazer justiça)")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Legítima defesa")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Preterdolo")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Assegurar a impunidade de outro crime")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Extorsão mediante seqüestro")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Estupro seguido de morte")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Motim/ rebelião")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Intolerância política")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Intolerância religiosa")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Homofobia")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Crime de ódio ou racismo")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Ritual religioso")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Rivalidade profissional")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Prêmio de seguro")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Dívida contraída pelo autor")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Dívida contraída pela vítima")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Sucessão política")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Conflito agrário")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Outra não relacionada")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
                if (MotivacaoFato.equals("Motivação não identificada")) {
                    BtnCarroFato.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RuaLogradouroFato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        NLogradouroFato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ReferenciaLocalFato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        BairroFato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        DisVilPovoFato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        BtnCarroFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (atualizar == 1) {
                    LvCarros.setVisibility(View.VISIBLE);
                    final int co = cvlifato.getId();
                    Intent it = new Intent(FatoActivity.this, AutomovelCrimeFatoActivity.class);
                    it.putExtra("codigocarroatualizar", co);
                    startActivity(it);
                } else {
                    LvCarros.setVisibility(View.VISIBLE);
                    startActivity(new Intent(FatoActivity.this, AutomovelCrimeFatoActivity.class));
                }

            }
        });

        BtnSalvarFato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cvli.setDsNaturezaFato(NaturezaFato);
                cvli.setRbEnderecoFatoDefinido(EnderecoDefinidoFato);
                cvli.setRbEnderecoFatoNIndefinido(EnderecoNDefinidoFato);
                cvli.setDtFato(DataFato.getText().toString());
                cvli.setHsFato(HorarioFato.getText().toString());
                cvli.setDsRuaFato(RuaLogradouroFato.getText().toString());
                cvli.setDsNRuaFato(NLogradouroFato.getText().toString());
                cvli.setDsReferenciaLocalFato(ReferenciaLocalFato.getText().toString());
                cvli.setDsBairroFato(BairroFato.getText().toString());
                cvli.setDsMunicipioFato(MunicipioFato);
                cvli.setDsOutroDetalhamento(TvDetalhamentoOutro.getText().toString());
                cvli.setCbkAfogamentoFato(Afogamento);
                cvli.setDsNaturezaFato(NaturezaFato);
                cvli.setDsMotivacaoFato(MotivacaoFato);
                cvli.setDsLogradouroFato(LogradouroFato);
                cvli.setDsDistVilPovoFato(DistVilPovoFato);
                cvli.setDsEstadoFato(EstadoFato);
                cvli.setDsZonaFato(ZonaFato);
                cvli.setDsDetalhamentoLocal(DetalhamentoLocalFato);
                cvli.setDsDescrDistVilPovoFato(DisVilPovoFato.getText().toString());
                cvli.setCkbDataFatoIndefinido(NaoInformadoDataFato);
                cvli.setCkbHorarioFatoIndefinido(NaoInformadoHorarioFato);
                cvli.setCbkTortura(Tortura);
                cvli.setCbkAsfixiaFato(Asfixia);
                cvli.setCbkTropelamentoFato(Atropelamento);
                cvli.setCbkDisparoArmaFato(DisparoArmaFoto);
                cvli.setCbkEmpurraoAlturaFato(EmpurraoALtura);
                cvli.setCbkEvenenadoFato(Evenenamento);
                cvli.setCbkEstanaduraFato(Esganadura);
                cvli.setCbkEstrangulamentoFato(Estrangulamento);
                cvli.setCbkGolpeFacaFato(GolpeFaca);
                cvli.setCbkGolpeFacaoFato(GolpeFacao);
                cvli.setCbkIncendioFato(Incendio);
                cvli.setCbkPerfuracoesFato(Perfuracao);
                cvli.setCbkEmpurraoSobVeiculoFato(EmpurraoSobVeiculo);
                cvli.setCbkOmissaoSocorroFato(OmissaoSocorro);
                cvli.setCbkPauladaFato(Pauladas);
                cvli.setCbkPedradaFato(Pedradas);
                cvli.setCbkQueimaduraFato(Queimaduras);
                cvli.setCbkQueimarudasAcidoFato(QueimaduraAcido);
                cvli.setCbkSocosPontapesFato(SocosPontapes);
                cvli.setCbkOutrosNaoRelacionadoFato(OutrosNaoRelacionados);
                cvli.setCbkNaoIdentificadoFato(Naoidentificado);

                int codigocvli = cvliDao.retornarCodigoCvliSemParametro();

                if (atualizar == 1) {
                    long certo = cvliDao.AtualizarCVLIFatos(cvli, cvlifato.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(FatoActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FatoActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else if(atualizar == 2) {
                    long certo = cvliDao.AtualizarCVLIFatos(cvli, cvlifatosematualizar.getId(), controleenvio);
                    if (certo > 0) {
                        Toast.makeText(FatoActivity.this, "Atualizado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FatoActivity.this, "Erro ao atualizar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    }else{
                    long certo = cvliDao.CadastrarCVLIFato(cvli, codigocvli);
                    if (certo > 0) {
                        Toast.makeText(FatoActivity.this, "Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FatoActivity.this, "Erro ao Cadastrar!!!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fato, menu);

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

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto_carro, menu);

    }

    public void AtualizarCarro(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Carro cvliAtualizarCarro = ListaCarrosAtualizarFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, AutomovelCrimeFatoActivity.class);
        it.putExtra("codigocarroatualizar", cvliAtualizarCarro);
        startActivity(it);
    }

    public void tornarCamposDesabilitados() {

        tornarCamposVisiveisEndereco();

        RuaLogradouroFato.setEnabled(false);
        NLogradouroFato.setEnabled(false);
        ReferenciaLocalFato.setEnabled(false);
        BairroFato.setEnabled(false);
        DisVilPovoFato.setEnabled(false);
        SpMunicipioFato.setEnabled(false);
        DetalhamentoOutroFato.setEnabled(false);
        DistVilPovo.setEnabled(false);
        Estado.setEnabled(false);
        Zona.setEnabled(false);
        DetalhamentoLocal.setEnabled(false);

        BtnMeioEmpregadoFato.setVisibility(View.VISIBLE);

    }

    public void tornarCamposInvisiveisEndereco() {

        TvLogradouroFato.setVisibility(View.GONE);
        Logradouro.setVisibility(View.GONE);
        RuaLogradouroFato.setVisibility(View.GONE);
        TvNFato.setVisibility(View.GONE);
        NLogradouroFato.setVisibility(View.GONE);
        TvReferenciaLocalFato.setVisibility(View.GONE);
        ReferenciaLocalFato.setVisibility(View.GONE);
        TvBairroFato.setVisibility(View.GONE);
        BairroFato.setVisibility(View.GONE);
        TvDisVilPovoFato.setVisibility(View.GONE);
        DistVilPovo.setVisibility(View.GONE);
        DisVilPovoFato.setVisibility(View.GONE);
        TvMunicipioFato.setVisibility(View.GONE);
        SpMunicipioFato.setVisibility(View.GONE);
        Estado.setVisibility(View.GONE);
        TvZonaFato.setVisibility(View.GONE);
        Zona.setVisibility(View.GONE);
        TvDetalhamentolocalFato.setVisibility(View.GONE);
        DetalhamentoLocal.setVisibility(View.GONE);
        TvDetalhamentoOutro.setVisibility(View.GONE);

    }

    public void tornarCamposHabilitados() {

        tornarCamposVisiveisEndereco();

        RuaLogradouroFato.setEnabled(true);
        NLogradouroFato.setEnabled(true);
        ReferenciaLocalFato.setEnabled(true);
        BairroFato.setEnabled(true);
        DisVilPovoFato.setEnabled(true);
        SpMunicipioFato.setEnabled(true);
        DetalhamentoOutroFato.setEnabled(true);
        DistVilPovo.setEnabled(true);
        Estado.setEnabled(true);
        Zona.setEnabled(true);
        DetalhamentoLocal.setEnabled(true);

        BtnMeioEmpregadoFato.setVisibility(View.VISIBLE);

    }

    public void tornarCamposVisiveisEndereco() {

        TvLogradouroFato.setVisibility(View.VISIBLE);
        Logradouro.setVisibility(View.VISIBLE);
        RuaLogradouroFato.setVisibility(View.VISIBLE);
        TvNFato.setVisibility(View.VISIBLE);
        NLogradouroFato.setVisibility(View.VISIBLE);
        TvReferenciaLocalFato.setVisibility(View.VISIBLE);
        ReferenciaLocalFato.setVisibility(View.VISIBLE);
        TvBairroFato.setVisibility(View.VISIBLE);
        BairroFato.setVisibility(View.VISIBLE);
        TvDisVilPovoFato.setVisibility(View.VISIBLE);
        DistVilPovo.setVisibility(View.VISIBLE);
        DisVilPovoFato.setVisibility(View.VISIBLE);
        TvMunicipioFato.setVisibility(View.VISIBLE);
        SpMunicipioFato.setVisibility(View.VISIBLE);
        Estado.setVisibility(View.VISIBLE);
        TvZonaFato.setVisibility(View.VISIBLE);
        Zona.setVisibility(View.VISIBLE);
        TvDetalhamentolocalFato.setVisibility(View.VISIBLE);
        DetalhamentoLocal.setVisibility(View.VISIBLE);
        TvDetalhamentoOutro.setVisibility(View.VISIBLE);
    }

    public void habilitaTodosCampos() {
        TvLogradouroFato.setVisibility(View.VISIBLE);
        Logradouro.setVisibility(View.VISIBLE);
        RuaLogradouroFato.setVisibility(View.VISIBLE);
        TvNFato.setVisibility(View.VISIBLE);
        NLogradouroFato.setVisibility(View.VISIBLE);
        TvReferenciaLocalFato.setVisibility(View.VISIBLE);
        ReferenciaLocalFato.setVisibility(View.VISIBLE);
        TvBairroFato.setVisibility(View.VISIBLE);
        BairroFato.setVisibility(View.VISIBLE);
        TvDisVilPovoFato.setVisibility(View.VISIBLE);
        DistVilPovo.setVisibility(View.VISIBLE);
        DisVilPovoFato.setVisibility(View.VISIBLE);
        TvMunicipioFato.setVisibility(View.VISIBLE);
        SpMunicipioFato.setVisibility(View.VISIBLE);
        Estado.setVisibility(View.VISIBLE);
        TvZonaFato.setVisibility(View.VISIBLE);
        Zona.setVisibility(View.VISIBLE);
        TvDetalhamentolocalFato.setVisibility(View.VISIBLE);
        DetalhamentoLocal.setVisibility(View.VISIBLE);
        TvDetalhamentoOutro.setVisibility(View.VISIBLE);
        BtnMotivacaoFato.setVisibility(View.VISIBLE);
        BtnEnderecoFato.setVisibility(View.VISIBLE);
        BtnMeioEmpregadoFato.setVisibility(View.VISIBLE);
        BtnCarroFato.setVisibility(View.VISIBLE);
        BtnSalvarFato.setVisibility(View.VISIBLE);
        BtnNaturezaFato.setVisibility(View.VISIBLE);
        BtnDataFato.setVisibility(View.VISIBLE);
        CbTortura.setVisibility(View.VISIBLE);
        CbAfogamento.setVisibility(View.VISIBLE);
        CbAsfixia.setVisibility(View.VISIBLE);
        CbAtropelamento.setVisibility(View.VISIBLE);
        CbDisparoArmaFogo.setVisibility(View.VISIBLE);
        CbEmpurraoAltura.setVisibility(View.VISIBLE);
        CbEmpurraoSobVeiculo.setVisibility(View.VISIBLE);
        CbEvenenamento.setVisibility(View.VISIBLE);
        CbEsganadura.setVisibility(View.VISIBLE);
        CbEstrangulamento.setVisibility(View.VISIBLE);
        CbGolpeFacas.setVisibility(View.VISIBLE);
        CbGolpeFacao.setVisibility(View.VISIBLE);
        CbIncendio.setVisibility(View.VISIBLE);
        CbOmissaoSocorro.setVisibility(View.VISIBLE);
        CbPauladas.setVisibility(View.VISIBLE);
        CbPedradas.setVisibility(View.VISIBLE);
        CbPerfuracao.setVisibility(View.VISIBLE);
        CbQueimadura.setVisibility(View.VISIBLE);
        CbQueimaduraAcido.setVisibility(View.VISIBLE);
        CbSocosPontapes.setVisibility(View.VISIBLE);
        CbOutrosNaoRelacionadosMeioEmpregado.setVisibility(View.VISIBLE);
        CbNaoIdentificadoMeioEmpregado.setVisibility(View.VISIBLE);
        Natureza.setVisibility(View.VISIBLE);
        DataFato.setVisibility(View.VISIBLE);
        BtnHorarioFato.setVisibility(View.VISIBLE);
        HorarioFato.setVisibility(View.VISIBLE);
        RgEnderecoFato.setVisibility(View.VISIBLE);
        MotivacaoCrime.setVisibility(View.VISIBLE);
        LvCarros.setVisibility(View.VISIBLE);
        CbNaoInformadoHorarioFato.setVisibility(View.VISIBLE);
        CbNaoInformadoDataFato.setVisibility(View.VISIBLE);
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        DataFato.setText(sdf.format(c.getTime()));
    }

    @Override
    public void onResume() {
        super.onResume();


        if (atualizar == 1) {
            ListaCarros = carroDao.retornarCarrosAtualizar(cvlifato.getId());
            ListaCarrosFiltrados.clear();
            ListaCarrosFiltrados.addAll(ListaCarros);
            LvCarros.invalidateViews();
        } else {
            ListaCarros = carroDao.retornarCarros();
            ListaCarrosFiltrados.clear();
            ListaCarrosFiltrados.addAll(ListaCarros);
            LvCarros.invalidateViews();
        }
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
