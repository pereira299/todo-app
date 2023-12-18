package com.pereira299.app_todo.screens;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.pereira299.app_todo.R;
import com.pereira299.app_todo.adapters.CompromissoAdapter;
import com.pereira299.app_todo.dao.CompromissoDao;
import com.pereira299.app_todo.database.AppDatabase;
import com.pereira299.app_todo.database.DatabaseProvider;
import com.pereira299.app_todo.entities.Compromisso;
import com.pereira299.app_todo.utils.DateFilterType;
import com.pereira299.app_todo.utils.UtilsFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText titleTxt;
    private EditText dateStartTxt;
    private EditText dateEndTxt;
    private SwitchMaterial recebeSw;
    private SwitchMaterial feitoSw;
    private Button filterBtn;
    private Button cleanBtn;
    private CompromissoAdapter compromissoAdapter;
    private List<Compromisso> compromissoList;
    private CompromissoDao dao;
    private CompromissoObserver listObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCompromissos);
        Button addBtn = findViewById(R.id.buttonAddCompromisso);
        titleTxt = findViewById(R.id.titleFilterTxt);
        dateStartTxt = findViewById(R.id.dateStartTxt);
        dateEndTxt = findViewById(R.id.dateEndTxt);
        filterBtn = findViewById(R.id.filterBtn);
        cleanBtn = findViewById(R.id.cleanBtn);
        recebeSw = findViewById(R.id.jaRecebeSw);
        feitoSw = findViewById(R.id.jaFeitoSw);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        compromissoList = new ArrayList<>();
        listObserver = new CompromissoObserver();
        AppDatabase db = DatabaseProvider.getDatabase(this);
        dao = db.compromissoDao();
        dao.getAll().observe(this, listObserver);

        compromissoAdapter = new CompromissoAdapter(this, compromissoList, dao);
        recyclerView.setAdapter(compromissoAdapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, AddCompromissoActivity.class);
                startActivity(in);
            }
        });

        cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter();
            }
        });

        dateStartTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    openDateStartDialog();
                }
            }
        });

        dateEndTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    openDateEndDialog();
                }
            }
        });
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    filter(titleTxt.getText().toString(),
                            dateStartTxt.getText().toString(),
                            dateEndTxt.getText().toString(),
                            feitoSw.isChecked(),
                            recebeSw.isChecked());
                } catch (ParseException e){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Data invalida")
                            .setMessage("A data deve ter o formato dia/mês/ano\n\nExemplo: 02/02/2012")
                            .show();
                }
            }

        });
    }

    private void openDateStartDialog(){
        DatePickerDialog picker = new DatePickerDialog(this);
        picker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateStartTxt.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
                dateStartTxt.clearFocus();
            }
        });
        picker.show();
    }

    private void openDateEndDialog(){
        DatePickerDialog picker = new DatePickerDialog(this);
        picker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateEndTxt.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
                dateEndTxt.clearFocus();
            }
        });
        picker.show();
    }
    private void filter(String title, String dateStart, String dateEnd, boolean jaFeito, boolean jaRecebeu) throws ParseException {
        if(title.isEmpty() && dateEnd.isEmpty() && dateStart.isEmpty() && !jaFeito && !jaRecebeu){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Filtro obrigatorio")
                    .setMessage("Preencha ao menos um campo para filtrar")
                    .show();
            return;
        }
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
        Date searchDateStart = null;
        Date searchDateEnd = null;
        boolean oneDay = false;

        if(!dateStart.isEmpty()) searchDateStart = formato.parse(dateStart);
        if(!dateEnd.isEmpty()) searchDateEnd = formato.parse(dateEnd);

        if(!dateEnd.isEmpty() && !dateStart.isEmpty()) {
            assert searchDateStart != null;
            if (searchDateStart.equals(searchDateEnd)) {
                oneDay = true;
            }
        }
        UtilsFilter filteredList = new UtilsFilter(compromissoList);

        if(!title.isEmpty()) filteredList.textFilter(title);
        if(!oneDay){
            if(!dateStart.isEmpty() && !dateEnd.isEmpty()){
                filteredList.dateFilter(searchDateStart, searchDateEnd);
            } else if(!dateStart.isEmpty()){
                filteredList.dateFilter(searchDateStart, DateFilterType.AFTER );
            } else if(!dateEnd.isEmpty()){
                filteredList.dateFilter(searchDateEnd, DateFilterType.BEFORE);
            }
        } else {
            filteredList.dateFilter(searchDateStart);
        }
        if(jaFeito) filteredList.finalizadoFilter();
        if(jaRecebeu) filteredList.recebidoFilter();


        filterBtn.setEnabled(false);
        cleanBtn.setEnabled(true);
        updateList(filteredList.getResult());

    }

    private void clearFilter(){
        titleTxt.setText("");
        dateEndTxt.setText("");
        dateStartTxt.setText("");
        feitoSw.setChecked(false);
        recebeSw.setChecked(false);
        dao.getAll().observe(this, listObserver);
        filterBtn.setEnabled(true);
        cleanBtn.setEnabled(false);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateList(List<Compromisso> compromissoEntities){
        if (compromissoEntities == null) return;
        compromissoList.clear();
        compromissoList.addAll(compromissoEntities);
        compromissoAdapter.notifyDataSetChanged();
    }

    class CompromissoObserver implements Observer<List<Compromisso>> {

        @Override
        public void onChanged(List<Compromisso> compromissoEntities) {
            updateList(compromissoEntities);
        }
    }
}