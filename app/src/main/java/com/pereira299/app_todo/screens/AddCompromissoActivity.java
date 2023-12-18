package com.pereira299.app_todo.screens;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pereira299.app_todo.R;
import com.pereira299.app_todo.database.AppDatabase;
import com.pereira299.app_todo.database.DatabaseProvider;
import com.pereira299.app_todo.entities.Compromisso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCompromissoActivity extends AppCompatActivity {
    private AppDatabase banco;
    private EditText editTextDescricao;
    private EditText editTextValor;
    private EditText editTextTitulo;
    private EditText editTextData;
    private Switch execSw;
    private Switch recebeSw;
    private Button buttonAdicionar;
    private Button buttonVoltar;

    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_compromisso);

        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextValor = findViewById(R.id.editTextValor);
        editTextData = findViewById(R.id.editTextData);
        editTextTitulo = findViewById(R.id.editTextTitulo);
        buttonAdicionar = findViewById(R.id.buttonSalvar);
        buttonVoltar = findViewById(R.id.buttonVoltar);
        execSw = findViewById(R.id.executadoSw);
        recebeSw = findViewById(R.id.recebidoSw);

        banco = DatabaseProvider.getDatabase(this);

        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        editTextData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    openDateDialog();
                }
            }
        });

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void save(){
        String descricao = "";
        String title = "";
        double valor;
        Date datetime;
        boolean recebido;
        boolean executado;
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            descricao = editTextDescricao.getText().toString();
            valor = Double.parseDouble(editTextValor.getText().toString());
            title = editTextTitulo.getText().toString();
            datetime = formato.parse(editTextData.getText().toString());
            recebido = recebeSw.isChecked();
            executado = execSw.isChecked();
        } catch (ParseException e) {
            new AlertDialog.Builder(AddCompromissoActivity.this)
                    .setTitle("Data invalida")
                    .setMessage("A data deve ter o formato dia/mês/ano\n\nExemplo: 02/02/2012")
                    .show();
            return;
        } catch (NumberFormatException ex) {
            new AlertDialog.Builder(AddCompromissoActivity.this)
                    .setTitle("Valor invalido")
                    .setMessage("Insira apenas numeros e ponto\n\nExemplo:247.35")
                    .show();
            return;
        }

        if(title.isEmpty()){
            new AlertDialog.Builder(AddCompromissoActivity.this)
                    .setTitle("Titulo invalido")
                    .setMessage("Você deve informar um titulo")
                    .show();
            return;
        }

        Compromisso compromisso = new Compromisso();
        compromisso.setTitulo(title);
        compromisso.setDataHora(datetime);
        compromisso.setDescricao(descricao);
        compromisso.setValor(valor);
        compromisso.setExecutado(executado);
        compromisso.setRecebido(recebido);
        banco.compromissoDao().insert(compromisso);

        Toast.makeText(AddCompromissoActivity.this, "Compromisso adicionado!", Toast.LENGTH_SHORT)
                .show();
        finish();
    }

    private void openDateDialog(){
        DatePickerDialog picker = new DatePickerDialog(this);
        picker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextData.setText(dayOfMonth+"/"+month+"/"+year);
                editTextData.clearFocus();
            }
        });
        picker.show();
    }
}