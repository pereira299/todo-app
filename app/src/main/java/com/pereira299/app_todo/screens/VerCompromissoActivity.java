package com.pereira299.app_todo.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pereira299.app_todo.R;
import com.pereira299.app_todo.dao.CompromissoDao;
import com.pereira299.app_todo.database.AppDatabase;
import com.pereira299.app_todo.database.DatabaseProvider;
import com.pereira299.app_todo.entities.Compromisso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VerCompromissoActivity extends AppCompatActivity {
    private TextView textViewDescricao;
    private TextView textViewValor;
    private TextView textViewData;
    private TextView textViewTitulo;
    private TextView textViewRecebido;
    private TextView textViewExecutado;
    private Button voltarBtn;
    private Button removerBtn;
    private Compromisso compromisso;
    private AppDatabase banco;
    private CompromissoDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_compromisso);

        textViewDescricao = findViewById(R.id.textViewDescricao);
        textViewValor = findViewById(R.id.textViewValor);
        textViewData = findViewById(R.id.textViewData);
        textViewTitulo = findViewById(R.id.textTitulo);
        textViewRecebido = findViewById(R.id.recebidoTxt);
        textViewExecutado = findViewById(R.id.executadoTxt);
        voltarBtn = findViewById(R.id.voltarBtn);
        removerBtn = findViewById(R.id.removeBtn);

        compromisso = (Compromisso) getIntent().getSerializableExtra("compromisso");

        textViewTitulo.setText(compromisso.getTitulo());
        textViewDescricao.setText(compromisso.getDescricao());
        textViewValor.setText(String.valueOf(compromisso.getValor()));
        textViewExecutado.setText(compromisso.isExecutado() ? "Sim" : "Não");
        textViewRecebido.setText(compromisso.isRecebido() ? "Sim" : "Não");

        banco = DatabaseProvider.getDatabase(this);
        dao = banco.compromissoDao();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dataFormatada = dateFormat.format(compromisso.getDataHora());
        textViewData.setText(dataFormatada);
        voltarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        removerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });
    }

    public  void remove(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Remover compromisso")
                .setMessage("Deseja realmente remover este compromisso")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.delete(compromisso);
                        Toast.makeText(VerCompromissoActivity.this, "Compromisso removido com sucesso!", Toast.LENGTH_SHORT);
                        finish();
                    }
                })
                .setNegativeButton("Não", null);
        alert.show();
    }
}