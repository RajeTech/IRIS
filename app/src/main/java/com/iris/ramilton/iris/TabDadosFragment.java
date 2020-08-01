package com.iris.ramilton.iris;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TabDadosFragment extends Fragment {
    private static final String TAG = "TabDadosFragment";

    private Spinner SpAreaTerritorial;
    private EditText EdtCanalSelecao, EdtDataInicial, EdtDatafinal;
    DatePickerDialog.OnDateSetListener date, date1;
    Calendar c, c1;
    DatePickerDialog dpd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabrelatoriodados_fragment,container,false);

        SpAreaTerritorial = (Spinner) view.findViewById(R.id.spAreaTerritorial);
        EdtCanalSelecao = (EditText) view.findViewById(R.id.edtCanalSelecao);
        EdtDataInicial = (EditText) view.findViewById(R.id.edtDataInicial);
        EdtDatafinal = (EditText) view.findViewById(R.id.edtDataFinal);

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

        EdtDataInicial.setOnClickListener(new View.OnClickListener() {
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

        EdtDatafinal.setOnClickListener(new View.OnClickListener() {
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

        EdtDataInicial.setText(sdf.format(c.getTime()));
    }

    private void updateLabel1() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        EdtDatafinal.setText(sdf.format(c1.getTime()));
    }
}
