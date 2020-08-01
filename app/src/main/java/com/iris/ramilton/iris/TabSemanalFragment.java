package com.iris.ramilton.iris;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.iris.ramilton.iris.banco.DatabaseHelper;
import com.iris.ramilton.iris.dao.MunicipioDao;
import com.iris.ramilton.iris.dao.UsuarioDao;
import com.iris.ramilton.iris.modelo.Municipio;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class TabSemanalFragment extends Fragment {
    private static final String TAG = "TabSemanalFragment";

    private Spinner SpAreaTerritorial, SpAno, SpSemana, SpCanalSelecao;
    private EditText EdtCanalSelecao, EdtAno;
    private SQLiteDatabase db;
    private DatabaseHelper conexao = null;
    private String anorequerido;
    private UsuarioDao usuarioDao;
    private ArrayList<String> datas = new ArrayList<String>();
    private ArrayList<String> AreaEscolhida = new ArrayList<String>();
    private String ResultadoArea;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabrelatoriosemanal_fragment,container,false);

        SpAreaTerritorial = (Spinner) view.findViewById(R.id.spAreaTerritorial);
        SpAno = (Spinner) view.findViewById(R.id.spAno);
        SpSemana = (Spinner) view.findViewById(R.id.spSemana);
        SpCanalSelecao = (Spinner) view.findViewById(R.id.spCanalSelecao);

        ArrayAdapter adaptadorAreaTerritorial = ArrayAdapter.createFromResource(getContext(), R.array.AreaTerritorial, android.R.layout.simple_spinner_item);
        SpAreaTerritorial.setAdapter(adaptadorAreaTerritorial);

        ArrayAdapter adaptadorAno = ArrayAdapter.createFromResource(getContext(), R.array.Ano, android.R.layout.simple_spinner_item);
        SpAno.setAdapter(adaptadorAno);

        ArrayAdapter<String> adaptadorSemana = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,datas);
        SpSemana.setAdapter(adaptadorSemana);

        //ArrayAdapter<String> adaptadorCanalSelecao = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,AreaEscolhida);
        //SpCanalSelecao.setAdapter(adaptadorCanalSelecao);

        usuarioDao = new UsuarioDao(getContext());

        SpAreaTerritorial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ResultadoArea = SpAreaTerritorial.getItemAtPosition(position).toString();

                switch (ResultadoArea){
                    case "ESTADO":
                        ArrayAdapter adaptadorCanalSelecao = ArrayAdapter.createFromResource(getContext(), R.array.AreaTerritorialBahia,android.R.layout.simple_spinner_item);
                        SpCanalSelecao.setAdapter(adaptadorCanalSelecao);
                        break;
                    case "CAPITAL":
                        ArrayAdapter adaptadorCanalSelecaos = ArrayAdapter.createFromResource(getContext(), R.array.AreaTerritorialSalvador,android.R.layout.simple_spinner_item);
                        SpCanalSelecao.setAdapter(adaptadorCanalSelecaos);
                        break;
                    case "DEPOM":
                        ArrayAdapter adaptadorCanalSelecaoP = ArrayAdapter.createFromResource(getContext(), R.array.AreaTerritorialDepom,android.R.layout.simple_spinner_item);
                        SpCanalSelecao.setAdapter(adaptadorCanalSelecaoP);
                        break;
                    case "DEPIN":
                        ArrayAdapter adaptadorCanalSelecaoPI = ArrayAdapter.createFromResource(getContext(), R.array.AreaTerritorialDepin,android.R.layout.simple_spinner_item);
                        SpCanalSelecao.setAdapter(adaptadorCanalSelecaoPI);
                        break;
                    case "RISP":
                        ArrayAdapter adaptadorCanalSelecaoRIS = ArrayAdapter.createFromResource(getContext(), R.array.AreaTerritorialRisp,android.R.layout.simple_spinner_item);
                        SpCanalSelecao.setAdapter(adaptadorCanalSelecaoRIS);
                        break;
                    default:
                    case "AISP":
                        ArrayAdapter adaptadorCanalSelecaoAI = ArrayAdapter.createFromResource(getContext(), R.array.AreaTerritorialAisp,android.R.layout.simple_spinner_item);
                        SpCanalSelecao.setAdapter(adaptadorCanalSelecaoAI);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpAno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                anorequerido = SpAno.getItemAtPosition(position).toString();
                String idservidor = String.valueOf(usuarioDao.user.getId());
                String matricula = usuarioDao.user.getDsMatricula();
                Receberdatas(idservidor,matricula);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void Receberdatas(String idservidor, String matricula) {

        HttpClient httpClient = new DefaultHttpClient();

        try {
            conexao = DatabaseHelper.getInstancia(getContext());
            db = conexao.getReadableDatabase();

            HttpGet httpGet = new HttpGet("https://irispoliciacivilba.com.br/app/modulo/cvli/listardatas?id=" + idservidor + "&matricula=" + matricula + "&anorequeridoapp=" +anorequerido);

            final HttpResponse response = httpClient.execute(httpGet);

            ((Activity) getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {

                        String resposta = EntityUtils.toString(response.getEntity());

                        JSONObject dados = new JSONObject(resposta);

                        if (dados.getString("resposta") == "1" || dados.getInt("resposta") == 1) {

                            JSONArray informacao = new JSONArray(dados.getString("informacoes"));

                            if(datas.size() > 0){

                                datas.clear();

                            }
                            for (int j = 0; j < informacao.length(); j++) {

                                datas.add(informacao.getString(j));
                            }


                            Toast.makeText(getContext(), "Sincronizado com Sucesso!!", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(getContext(), "Erro ao Sincronizar!!", Toast.LENGTH_LONG).show();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.i("Valores Pars", "" + e.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("Valores IO", "" + e.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("Valores JOs", "" + e.getMessage());
                    }
                }
            });
        } catch (ClientProtocolException e) {
        } catch (Exception e) {

            Log.i("Valores Excep", "" + e.getMessage());
        }
    }
}
