package com.iris.ramilton.iris;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TabCustomizadoFragment extends Fragment {
    private static final String TAG = "TabCustomizadoFragment";

    private Spinner SpAreaTerritorial;
    private EditText EdtCanalSelecao, EdtDataIncial, EdtDataFinal;
    private CheckBox CbNatureza, CbMeioEmpregado, CbMotivacao, CbElucidacao, CbDistribuicao, CbZoneamento;
    private CheckBox CbDetalhamento, CbComparativoPeriodo, CbComparativoAnual, CbComparativoMensal, CbHistoricoDiario;
    private CheckBox CbIncidenciaDiaSemana;
    DatePickerDialog.OnDateSetListener date, date1;
    Calendar c, c1;
    DatePickerDialog dpd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabrelatoriocustomizado_fragment,container,false);

        SpAreaTerritorial = (Spinner) view.findViewById(R.id.spAreaTerritorial);
        EdtCanalSelecao = (EditText) view.findViewById(R.id.edtCanalSelecao);
        EdtDataIncial = (EditText) view.findViewById(R.id.edtDataInicial);
        EdtDataFinal = (EditText) view.findViewById(R.id.edtDataFinal);
        CbNatureza = (CheckBox) view.findViewById(R.id.cbNatureza);
        CbMeioEmpregado = (CheckBox) view.findViewById(R.id.cbMeioEmpregado);
        CbMotivacao = (CheckBox) view.findViewById(R.id.cbMotivacao);
        CbElucidacao = (CheckBox) view.findViewById(R.id.cbElucidacao);
        CbDistribuicao = (CheckBox) view.findViewById(R.id.cbDistribuicao);
        CbZoneamento = (CheckBox) view.findViewById(R.id.cbZoneamentoEstado);
        CbDetalhamento = (CheckBox) view.findViewById(R.id.cbDetalhamentoEstado);
        CbComparativoPeriodo = (CheckBox) view.findViewById(R.id.cbComparativoPeriodico);
        CbComparativoAnual = (CheckBox) view.findViewById(R.id.cbComparativoAnual);
        CbComparativoMensal = (CheckBox) view.findViewById(R.id.cbComparativoMensal);
        CbHistoricoDiario = (CheckBox) view.findViewById(R.id.cbHistóricoDiario);
        CbIncidenciaDiaSemana = (CheckBox) view.findViewById(R.id.cbIncidenciaDiaSemana);

        ArrayAdapter adaptadorAreaTerritorial = ArrayAdapter.createFromResource(getContext(), R.array.AreaTerritorial, android.R.layout.simple_spinner_item);
        SpAreaTerritorial.setAdapter(adaptadorAreaTerritorial);

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

        EdtDataIncial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        c1 = Calendar.getInstance();
        date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };

        EdtDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date1, c1
                        .get(Calendar.YEAR), c1.get(Calendar.MONTH),
                        c1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return view;
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        EdtDataIncial.setText(sdf.format(c.getTime()));
    }

    private void updateLabel1() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        EdtDataFinal.setText(sdf.format(c1.getTime()));
    }
}
